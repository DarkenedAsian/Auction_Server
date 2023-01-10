package com.example.javafxloginvideo;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

/*
* Utility Class
* - Contains ONLY static methods
* - Can't be instantiated
* - Contains a bunch of related methods
* - Also known as a helper class
* */
public class DBUtils {
    public static void changeScene(ActionEvent event, String fxmlFile, String title, String username, String favChannel){
        Parent root = null; //new scene
        if(username != null && favChannel != null){
            try{
                FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
                root = loader.load();
                LoggedInController loggedInController = loader.getController();
                loggedInController.setUserInformation(username, favChannel);
            }catch (IOException e){
                e.printStackTrace();
            }
        } else{
            try{
                root = FXMLLoader.load(DBUtils.class.getResource(fxmlFile));
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        //get source of button, cast to stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void signUpUser(ActionEvent event, String username, String password, String favChannel){
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUserExists = null;
        ResultSet resultSet = null;

        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafx-video", "root", "Uixt2952");
            psCheckUserExists = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
            psCheckUserExists.setString(1, username);
            resultSet = psCheckUserExists.executeQuery();

            if(resultSet.isBeforeFirst()){ //is result set empty
                System.out.println("User already exists!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("You cannot use this username.");
                alert.show();
            } else{
                psInsert = connection.prepareStatement("INSERT INTO users (username, password, favChannel) VALUES (?, ?, ?)");
                psInsert.setString(1, username);
                psInsert.setString(2, password);
                psInsert.setString(3, favChannel);
                psInsert.executeUpdate();

                changeScene(event, "logged-in.fxml", "Welcome!", username, favChannel);
            }
        }catch(SQLException e){
            e.printStackTrace();
        } finally{
            if(resultSet != null){
                try{
                    resultSet.close();
                } catch(SQLException e){
                    e.printStackTrace();
                }
            }
            if(psCheckUserExists != null){
                try{
                    psCheckUserExists.close();
                } catch(SQLException e){
                    e.printStackTrace();
                }
            }
            if(psInsert != null){
                try{
                    psInsert.close();
                } catch(SQLException e){
                    e.printStackTrace();
                }
            }
            if(connection != null){
                try{
                    connection.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public static void logInUser(ActionEvent event, String username, String password){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafx-video", "root", "Uixt2952");
            preparedStatement = connection.prepareStatement("SELECT password, favChannel FROM users WHERE username = ?");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if(!resultSet.isBeforeFirst()){
                System.out.println("User not found in the database!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Provided credentials are incorrect!");
                alert.show();
            }else{
                while(resultSet.next()){
                    //column password
                    String retrievedPassword = resultSet.getString("password");
                    String retrievedChannel = resultSet.getString("favChannel");
                    if(retrievedPassword.equals(password)){
                        changeScene(event, "logged-in.fxml", "Welcome!", username, retrievedChannel);
                    } else{
                        System.out.println("Passwords did not match!");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("The provided credentials are incorrect!");
                        alert.show();
                    }
                }
            }
        } catch(SQLException e){
            e.printStackTrace();
        } finally{
            if(resultSet != null){
                try{
                    resultSet.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
            if(preparedStatement != null){
                try{
                    preparedStatement.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
            if(connection != null){
                try{
                    connection.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }

}

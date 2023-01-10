package com.example.javafxloginvideo;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {

    @FXML
    private Button button_sign_up;
    @FXML
    private Button button_log_in;

    @FXML
    private RadioButton rb_wittcode;
    @FXML
    private RadioButton rb_rbd_trades;

    @FXML
    private TextField tf_username;
    @FXML
    private  TextField tf_password;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ToggleGroup toggleGroup = new ToggleGroup();
        rb_wittcode.setToggleGroup(toggleGroup);
        rb_rbd_trades.setToggleGroup(toggleGroup);

        rb_wittcode.setSelected(true);

        button_sign_up.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String toggleName = ((RadioButton) toggleGroup.getSelectedToggle()).getText();
                if(!tf_username.getText().trim().isEmpty() && !tf_password.getText().trim().isEmpty()){
                    DBUtils.signUpUser(event, tf_username.getText(), tf_password.getText(), toggleName);
                } else{
                    System.out.println("Please fill in all information");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please fill in all information to sign up!");
                    alert.show();
                }
            }
        });

        button_log_in.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "hello-view.fxml", "Log in!", null, null);
            }
        });
    }
}

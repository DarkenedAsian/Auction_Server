package com.example.javafxloginvideo;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.*;

public class LoggedInController implements Initializable {

    private String username;
    private Client client;

    @FXML
    private Button button_logout;
    @FXML
    private Label label_welcome;

    @FXML
    private Label label_cur_bid_amount;
    @FXML
    private TextField tf_bid_amount;
    @FXML
    private Button button_set_bid;

    //item0
    Double bid_amount;

    @FXML
            private Label label_item1_name;
    @FXML
            private Label label_item1_bidder;
    @FXML
            private Label label_item1_bids;
    @FXML
            private Button button_item1;

    @FXML
    private Label label_item2_name;
    @FXML
    private Label label_item2_bidder;
    @FXML
    private Label label_item2_bids;
    @FXML
    private Button button_item2;

    @FXML
    private Label label_item3_name;
    @FXML
    private Label label_item3_bidder;
    @FXML
    private Label label_item3_bids;
    @FXML
    private Button button_item3;

    @FXML
    private Label label_item4_name;
    @FXML
    private Label label_item4_bidder;
    @FXML
    private Label label_item4_bids;
    @FXML
    private Button button_item4;





    ArrayList<ArrayList<Label>> labels_list = new ArrayList<>();
    ArrayList<Button> buttons_list = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeLabels();
        /*
        * 1. Initialize client-server connection
        * 2. Receive initial bid packet
        * 3. Create GUI
        * 4. Receive Bids
        * 5. Button events
        * */

        try{
            username = "N/A";
            client = new Client(new Socket("localhost", 1234), username);
            System.out.println("Connected to server");
        }catch(IOException e){
            e.printStackTrace();
        }

        System.out.println("logged in screen reached");

        Packet initialBidSet = client.initPacket;
        int test = 20;

        initializeGUI(initialBidSet);

        client.receivePacketFromServer(label_item1_bids, label_item1_bidder, labels_list);

        //When user clicks on button, this happens
        button_logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DBUtils.changeScene(actionEvent, "hello-view.fxml", "Log in!", null, null);
            }
        });

        //bid setter
        button_set_bid.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try{
                    System.out.println(label_welcome.getText());
                    bid_amount = Double.parseDouble(tf_bid_amount.getText());
                    label_cur_bid_amount.setText("Current Bid Amount: " + bid_amount);
                }catch (NumberFormatException e){
                    tf_bid_amount.setText("Numbers only");
                }

            }
        });

        button_item1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try{
                    String bids_text = label_cur_bid_amount.getText();
                    Scanner tokenizer = new Scanner(bids_text);
                    tokenizer.next();
                    tokenizer.next();
                    tokenizer.next();
                    Double bid = Double.parseDouble(tokenizer.next());
                    Scanner tokenizer2 = new Scanner(label_welcome.getText());
                    tokenizer2.next();
                    String username = tokenizer2.next();

                    String item = label_item1_name.getText();

                    String min_bid_text = label_item1_bids.getText();
                    Scanner tokenizer3 = new Scanner(min_bid_text);
                    tokenizer3.next(); tokenizer3.next(); tokenizer3.next(); tokenizer3.next();
                    Double min_bid = Double.parseDouble(tokenizer3.next());

                    if(bid < min_bid){
                        System.out.println("Invalid Bid Amount");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Invalid Bid Amount (Bid < Min_Bid)");
                        alert.show();
                    }

                    System.out.println("Current item is: " + item + " bid amount is: " + bid + " Username is " + username);

                    Packet bidPacket = new Packet(username, item, bid, min_bid);
                    bidPacket.item_number = 1;

                    client.sendPacketToServer(bidPacket);
                }catch(NumberFormatException e){
                    System.out.println("Set bid first");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Set Bid Amount First");
                    alert.show();
                }

            }
        });

        button_item2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try{
                    String bids_text = label_cur_bid_amount.getText();
                    Scanner tokenizer = new Scanner(bids_text);
                    tokenizer.next();
                    tokenizer.next();
                    tokenizer.next();
                    Double bid = Double.parseDouble(tokenizer.next());
                    Scanner tokenizer2 = new Scanner(label_welcome.getText());
                    tokenizer2.next();
                    String username = tokenizer2.next();

                    String item = label_item2_name.getText();

                    String min_bid_text = label_item2_bids.getText();
                    Scanner tokenizer3 = new Scanner(min_bid_text);
                    tokenizer3.next(); tokenizer3.next(); tokenizer3.next(); tokenizer3.next();
                    Double min_bid = Double.parseDouble(tokenizer3.next());

                    if(bid < min_bid){
                        System.out.println("Invalid Bid Amount");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Invalid Bid Amount (Bid < Min_Bid)");
                        alert.show();
                    }

                    System.out.println("Current item is: " + item + " bid amount is: " + bid + " Username is " + username);

                    Packet bidPacket = new Packet(username, item, bid, min_bid);
                    bidPacket.item_number = 2;

                    client.sendPacketToServer(bidPacket);
                }catch(NumberFormatException e){
                    System.out.println("Set bid first");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Set Bid Amount First");
                    alert.show();
                }

            }
        });

        button_item3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try{
                    String bids_text = label_cur_bid_amount.getText();
                    Scanner tokenizer = new Scanner(bids_text);
                    tokenizer.next();
                    tokenizer.next();
                    tokenizer.next();
                    Double bid = Double.parseDouble(tokenizer.next());
                    Scanner tokenizer2 = new Scanner(label_welcome.getText());
                    tokenizer2.next();
                    String username = tokenizer2.next();

                    String item = label_item3_name.getText();

                    String min_bid_text = label_item3_bids.getText();
                    Scanner tokenizer3 = new Scanner(min_bid_text);
                    tokenizer3.next(); tokenizer3.next(); tokenizer3.next(); tokenizer3.next();
                    Double min_bid = Double.parseDouble(tokenizer3.next());

                    if(bid < min_bid){
                        System.out.println("Invalid Bid Amount");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Invalid Bid Amount (Bid < Min_Bid)");
                        alert.show();
                    }

                    System.out.println("Current item is: " + item + " bid amount is: " + bid + " Username is " + username);

                    Packet bidPacket = new Packet(username, item, bid, min_bid);
                    bidPacket.item_number = 3;

                    client.sendPacketToServer(bidPacket);
                }catch(NumberFormatException e){
                    System.out.println("Set bid first");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Set Bid Amount First");
                    alert.show();
                }

            }
        });

        button_item4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try{
                    String bids_text = label_cur_bid_amount.getText();
                    Scanner tokenizer = new Scanner(bids_text);
                    tokenizer.next();
                    tokenizer.next();
                    tokenizer.next();
                    Double bid = Double.parseDouble(tokenizer.next());
                    Scanner tokenizer2 = new Scanner(label_welcome.getText());
                    tokenizer2.next();
                    String username = tokenizer2.next();

                    String item = label_item4_name.getText();

                    String min_bid_text = label_item4_bids.getText();
                    Scanner tokenizer3 = new Scanner(min_bid_text);
                    tokenizer3.next(); tokenizer3.next(); tokenizer3.next(); tokenizer3.next();
                    Double min_bid = Double.parseDouble(tokenizer3.next());

                    if(bid < min_bid){
                        System.out.println("Invalid Bid Amount");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Invalid Bid Amount (Bid < Min_Bid)");
                        alert.show();
                    }

                    System.out.println("Current item is: " + item + " bid amount is: " + bid + " Username is " + username);

                    Packet bidPacket = new Packet(username, item, bid, min_bid);
                    bidPacket.item_number = 4;

                    client.sendPacketToServer(bidPacket);
                }catch(NumberFormatException e){
                    System.out.println("Set bid first");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Set Bid Amount First");
                    alert.show();
                }

            }
        });



    }

    public void initializeGUI(Packet packet){
        Map<String, Item> itemSet = new HashMap<>();
        itemSet = packet.itemSet;
        Iterator<String> key = itemSet.keySet().iterator();
        int i = 0;
        while(key.hasNext()){
            String item_name = key.next();
            Item item = itemSet.get(item_name);
            Double price = item.cur_bid;
            Double min_bid = item.min_bid;


                Label name_label = labels_list.get(i).get(0);
                Label bidder_label = labels_list.get(i).get(1);
                Label price_label = labels_list.get(i).get(2);

            name_label.setText(item_name);
            price_label.setText("Price: " + price + " Min Bid: " + min_bid);

            i++; //increment arraylist item index
        }
    }

    public static void updateBidAndBidder(Double price, Double min_bid, String bidder, Label bid_label, Label bidder_label){


        //runs code on java application thread
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                bid_label.setText("Price: " + price + " Min Bid: " + min_bid);
                bidder_label.setText("Current Bidder: " + bidder);
                //vBox.getChildren().add(hBox);

            }
        });
    }

    public void setUserInformation(String username, String favChannel){
        this.username = username;
        label_welcome.setText("Welcome " + username);
        //label_fav_channel.setText("Your favorite Youtube channel is " + favChannel + "!");
    }

    public void initializeLabels(){
        ArrayList<Label> item1_labels = new ArrayList<>();
        item1_labels.add(label_item1_name);
        item1_labels.add(label_item1_bidder);
        item1_labels.add(label_item1_bids);
        labels_list.add(item1_labels);

        ArrayList<Label> item2_labels = new ArrayList<>();
        item2_labels.add(label_item2_name);
        item2_labels.add(label_item2_bidder);
        item2_labels.add(label_item2_bids);
        labels_list.add(item2_labels);

        ArrayList<Label> item3_labels = new ArrayList<>();
        item3_labels.add(label_item3_name);
        item3_labels.add(label_item3_bidder);
        item3_labels.add(label_item3_bids);
        labels_list.add(item3_labels);

        ArrayList<Label> item4_labels = new ArrayList<>();
        item4_labels.add(label_item4_name);
        item4_labels.add(label_item4_bidder);
        item4_labels.add(label_item4_bids);
        labels_list.add(item4_labels);
    }
}

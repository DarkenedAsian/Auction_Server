package com.example.javafxloginvideo;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Client {
    Packet initPacket;
    private String username;
    private Socket socket;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;

    public Client(Socket socket, String username){
        try{
            this.socket = socket;
            this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            this.objectInputStream = new ObjectInputStream(socket.getInputStream());

            //send Packet with username first
            initPacket = new Packet(username);
            System.out.println("my username is " + username);
            objectOutputStream.writeObject(initPacket);

            //receive initial packet
            initPacket = (Packet)objectInputStream.readObject();
            int test = 0;
        }catch(IOException e){
            System.out.println("Error creating client.");
            e.printStackTrace();
            closeEverything(socket, objectOutputStream, objectInputStream);
        } catch (ClassNotFoundException e) {
            System.out.println("Trouble receiving packet");
            e.printStackTrace();
        }
    }

    public void sendPacketToServer(Packet packet){
        try{
            objectOutputStream.writeObject(packet);
        }catch(IOException e){
            e.printStackTrace();
            System.out.println("Error sending message to the server");
            closeEverything(socket, objectOutputStream, objectInputStream);
        }
    }

    public void receivePacketFromServer(Label bid_label, Label bidder_label, ArrayList<ArrayList<Label>> labels_list){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(socket.isConnected()){
                    try{
                        Packet packet = (Packet)objectInputStream.readObject();
                        System.out.println("Packet received");
                        System.out.println("Item is: " + packet.item + " Bid is: " + packet.bid + " Username is: "
                                + packet.username + "Min bid is: " + packet.min_bid);

                        ArrayList<Label> item_labels = labels_list.get(packet.item_number-1);
                        Label bid_label_test = item_labels.get(2);
                        Label bidder_label_test = item_labels.get(1);

                        LoggedInController.updateBidAndBidder(packet.bid, packet.min_bid, packet.username, bid_label_test, bidder_label_test);
                    }catch(IOException e){
                        e.printStackTrace();
                        System.out.println("Error receiving message from client");
                        closeEverything(socket, objectOutputStream, objectInputStream);
                        break;
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        }).start();
    }

    public void closeEverything(Socket socket, ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream){
        try{
            if(objectInputStream != null){ objectInputStream.close();}
            if(objectOutputStream != null){ objectOutputStream.close();}
            if(socket != null){ socket.close();}
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}

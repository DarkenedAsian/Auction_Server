package com.example.javafxloginvideo;
import java.io.Serializable;
import java.util.Map;

public class Packet implements Serializable {
    Map<String, Item> itemSet;
    String username;
    String item;
    Double bid; //if bid -1 then it was invalid
    Double min_bid;
    Integer item_number;
    public Packet(String username, String item, Double bid, Double min_bid){
        this.username = username;
        this.item = item;
        this.bid = bid;
        this.min_bid = min_bid;
    }

    public Packet(String username){
        this.username = username;
        itemSet = null;
        item = null;
        bid = null;
    }
}

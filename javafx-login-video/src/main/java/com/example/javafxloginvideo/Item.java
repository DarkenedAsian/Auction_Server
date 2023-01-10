package com.example.javafxloginvideo;

import java.io.Serializable;

public class Item implements Serializable {
    String name;
    String cur_bidder;
    Double cur_bid;
    Double min_bid;
    Integer duration;
    public Item(String name, Double bid, Double min_bid, Integer duration){
        this.name = name;
        this.cur_bid = bid;
        this.min_bid = min_bid;
        this.duration = duration;
        cur_bidder = "No one yet";
    }
}

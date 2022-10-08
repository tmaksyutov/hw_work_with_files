package ru.itone.Model;

import java.util.ArrayList;

public class Order {
    public int orderID;
    public String shopperName;
    public String shopperEmail;
    public ArrayList<Content> contents;
    public boolean orderCompleted;
    public static class Content {
        public int productID;
        public String productName;
        public int quantity;
    }
}

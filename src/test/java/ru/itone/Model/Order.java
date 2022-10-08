package ru.itone.Model;

import java.util.ArrayList;

public class Order {
    public int orderID;
    public String FirstName;
    public String LastName;
    public String Email;
    public ArrayList<Content> contents;
    public boolean orderCompleted;
    public static class Content {
        public int productID;
        public String productName;
        public int quantity;
    }
}

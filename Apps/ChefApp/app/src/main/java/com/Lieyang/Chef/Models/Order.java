package com.Lieyang.Chef.Models;

import java.util.ArrayList;
import java.util.List;

public class Order {

    public String id;
    public String datetime;
    public String userid;
    public List<MenuItem> menuItems = new ArrayList<>();
    public String extra;
    public boolean paid;
    public String payment_method;
    public boolean completed;
    public boolean served;

//    public Order(String OrderId, User user, ArrayList<MenuItem> MenuItems, Date date, boolean Paid, boolean Completed, boolean Served){
//        mOrderId = OrderId;
//        mUser = user;
//        mMenuItems = MenuItems;
//        mDate = date;
//        mPaid = Paid;
//        mCompleted = Completed;
//        mServed = Served;
//    }
//
//    public Order(String OrderId, User user, ArrayList<MenuItem> MenuItems, Date date, boolean Completed){
//        mOrderId = OrderId;
//        mUser = user;
//        mMenuItems = MenuItems;
//        mDate = date;
//        mCompleted = Completed;
//    }
//
//    public Order(){
//
//    }
}

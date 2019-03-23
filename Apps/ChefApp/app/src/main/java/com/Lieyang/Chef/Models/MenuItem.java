package com.Lieyang.Chef.Models;

import java.util.Date;

public class MenuItem {
    public String mId;
    public String mName;
    public String mDescription;
    public String mPrice;

    public MenuItem(String Name, String Price){
        mPrice =Price;
        mName = Name;
    }
}

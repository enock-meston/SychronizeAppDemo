package com.nigoote.synchronizedemo;

public class Contact {

    private String Name;
    private String Quantity;
    private String Price;
    private int Sync_status;

    Contact(String Name,String Quantity,String Price,int Sync_status){
        this.setName(Name);
        this.setQuantity(Quantity);
        this.setPrice(Price);
        this.setSync_status(Sync_status);
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public int getSync_status() {
        return Sync_status;
    }

    public void setSync_status(int sync_status) {
        Sync_status = sync_status;
    }
}

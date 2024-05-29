package com.model;

public class Bill {
    @Override
    public String toString() {
        return "Bill{" +
                "user_Name='" + user_Name + '\'' +
                ", email='" + email + '\'' +
                ", amount='" + amount + '\'' +
                ", package_id='" + package_id + '\'' +
                ", package_validity='" + package_validity + '\'' +
                ", package_music='" + package_music + '\'' +
                '}';
    }

    private String user_Name;
    private String email;
    private String amount;
    private String package_id;
    private String package_validity;
    private String package_music;


    public String getUser_Name() {
        return user_Name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPackage_id() {
        return package_id;
    }
    public String getPackage_validity() {
        return package_validity;
    }

    public String getEmail() {
        return email;
    }

    public String getAmount() {
        return amount;
    }

    public String getPackage_music() {
        return package_music;
    }


    public void setUser_Name(String user_Name) {
        this.user_Name = user_Name;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setPackage_music(String package_music) {
        this.package_music = package_music;
    }

    public Bill(String user_Name, String email, String amount, String package_id, String package_validity, String package_music) {
        this.user_Name = user_Name;
        this.email = email;
        this.amount = amount;
        this.package_id = package_id;
        this.package_validity = package_validity;
        this.package_music = package_music;
    }


    public Bill(String user_Name, String email, String amount, Package _package) {
        this.user_Name = user_Name;
        this.email = email;
        this.amount = amount;
    }

    public Bill() {

    }


}

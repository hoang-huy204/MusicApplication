package com.model;

import java.time.LocalDate;

public class Package_Auth {
    public String getPackage_Name() {
        return package_Name;
    }

    public void setPackage_Name(String package_Name) {
        this.package_Name = package_Name;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Package_Auth() {
    }

    public Package_Auth(String package_Name, LocalDate expiryDate) {
        this.package_Name = package_Name;
        this.expiryDate = expiryDate;
    }

    @Override
    public String toString() {
        return "Package_Auth{" +
                "package_Name='" + package_Name + '\'' +
                ", expiryDate=" + expiryDate +
                '}';
    }

    private String package_Name;
    private LocalDate expiryDate;

}

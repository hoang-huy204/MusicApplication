package com.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Package {
    public Package(Integer id, String packageName, BigDecimal packagePrice, Integer validity, LocalDateTime createAt) {
        this.id = id;
        this.packageName = packageName;
        this.packagePrice = packagePrice;
        this.validity = validity;
        this.create_at = createAt;
    }

    public Package(Integer id, String packageName, BigDecimal packagePrice, Integer validity) {
        this.id = id;
        this.packageName = packageName;
        this.packagePrice = packagePrice;
        this.validity = validity;
    }

    public Package() {
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public BigDecimal getPackagePrice() {
        return packagePrice;
    }

    public void setPackagePrice(BigDecimal packagePrice) {
        this.packagePrice = packagePrice;
    }

    public Integer getValidity() {
        return validity;
    }

    public void setValidity(Integer validity) {
        this.validity = validity;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreate_at() {
        return create_at;
    }

    public void setCreate_at(LocalDateTime create_at) {
        this.create_at = create_at;
    }


    private Integer id;
    private String packageName;
    private BigDecimal packagePrice;
    private Integer validity;
    private String status;
    private LocalDateTime create_at;

    @Override
    public String toString() {
        return "Package{" +
                "id=" + id +
                ", packageName='" + packageName + '\'' +
                ", packagePrice=" + packagePrice +
                ", validity=" + validity +
                ", status='" + status + '\'' +
                ", create_at=" + create_at +
                '}';
    }
}

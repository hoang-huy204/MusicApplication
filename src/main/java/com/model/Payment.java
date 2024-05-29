package com.model;

public class Payment {

    private String payment_Id;
    private String payer_Id;

    public String getPayment_Id() {
        return payment_Id;
    }

    public void setPayment_Id(String payment_Id) {
        this.payment_Id = payment_Id;
    }

    public String getPayer_Id() {
        return payer_Id;
    }

    public void setPayer_Id(String payer_Id) {
        this.payer_Id = payer_Id;
    }

    public Payment() {
    }

    public Payment(String payment_Id, String payer_Id) {
        this.payment_Id = payment_Id;
        this.payer_Id = payer_Id;
    }
}

package com.controller;

import com.dao.PackageAuthDao;
import com.dao.PackageDao;
import com.google.gson.Gson;
import com.model.*;
import com.model.Package;
import javafx.concurrent.Worker;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CheckOutController implements Initializable {
    private final Package _package;
    private final Gson gson = new Gson();
    private final PackageDao packageDao = new PackageDao();
    private final PackageAuthDao packageAuthDao = new PackageAuthDao();
    private Auth user = LoginController.authLogin;
    private Boolean isExtend = false;

    public CheckOutController(Package _package, Boolean isExtend) {
        this._package = _package;
        this.isExtend = isExtend;
    }

    @FXML
    private WebView show_Web;
    @FXML
    private AnchorPane success_container;
    @FXML
    private Button btn_donePayment;
    @FXML
    private AnchorPane error_container;
    @FXML
    private Button btn_doneError;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        payment();
    }

    public void payment() {
        if (_package != null) {
            WebEngine webEngine = show_Web.getEngine();
            String response_Url = request_Bill();
            if (response_Url != null) {
                webEngine.load(response_Url);
                webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue == Worker.State.SUCCEEDED) {
                        String currentURL = webEngine.getLocation();
                        if (currentURL != null) {
                            String payment_Id = null;
                            String payer_Id = null;
                            String[] URL = currentURL.split("\\?");
                            String[] params = URL[1].split("&");
                            for (String param : params) {
                                String[] values = param.split("=");
                                if (values[0].equals("paymentId")) {
                                    payment_Id = values[1];
                                }

                                if (values[0].equals("PayerID")) {
                                    payer_Id = values[1];
                                }
                            }
                            if (payment_Id != null && payer_Id != null) {
                                String json = gson.toJson(new Payment(payment_Id, payer_Id));
                                HttpResponse<String> response = post_Request(json, "http://localhost:8080/Payment/checkPayment");
                                if (response != null) {
                                    Bill bill_Response = gson.fromJson(response.body(), Bill.class);
                                    if (bill_Response != null) {
                                        if (check_Bill(bill_Response)) {
                                            HttpResponse<String> execute_Response = post_Request(json, "http://localhost:8080/Payment/execute");
                                            if (execute_Response.body().equals("finish")) {
                                                LocalDate currentDate = LocalDate.now();
                                                LocalDate expiryDate = currentDate.plusDays(Integer.parseInt(bill_Response.getPackage_validity()));
                                                if (isExtend) {
                                                    if (packageAuthDao.extendPremium(_package.getId(), user.getId(), expiryDate)) {
                                                        handle_success();
                                                    }
                                                } else {
                                                    if (packageAuthDao.update_Package4User(_package.getId(), user.getId(), expiryDate)) {
                                                        handle_success();
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                });
            }
        }
    }

    public void handle_success() {
        show_Web.setVisible(false);
        success_container.setVisible(true);
        if (success_container.isVisible()) {
            btn_donePayment.setOnAction(event -> {
                Stage stage = (Stage) btn_donePayment.getScene().getWindow();
                stage.close();
            });
        }
    }

    public boolean check_Bill(Bill bill) {
        if (bill != null) {
            ArrayList<Package> packages = packageDao.select_all_package("active");
            for (Package __package : packages) {
                if (__package.getId() == Integer.parseInt(bill.getPackage_id()) && __package.getPackageName().equals(bill.getPackage_music()) && String.valueOf(__package.getPackagePrice()).equals(bill.getAmount()) && __package.getValidity() == Integer.parseInt(bill.getPackage_validity())) {
                    return true;
                }
            }
        } else {
            show_error();
        }
        return false;
    }

    private void show_error() {
        show_Web.setVisible(false);
        error_container.setVisible(true);
        if (error_container.isVisible()) {
            btn_doneError.setOnAction(event -> {
                Stage stage = (Stage) btn_doneError.getScene().getWindow();
                stage.close();
            });
        }
    }

    public String request_Bill() {
        try {
            Bill bill = new Bill(user.getFullname(), user.getEmail(), String.valueOf(_package.getPackagePrice()), String.valueOf(_package.getId()), String.valueOf(_package.getValidity()), _package.getPackageName());
            String json = gson.toJson(bill);
            HttpResponse<String> response = post_Request(json, "http://localhost:8080/Payment/createPayment");
            return response.body();
        } catch (NullPointerException e) {
            System.err.println("loi sever");
        }
        return null;
    }

    public HttpResponse<String> post_Request(String object_Json, String url) {
        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(object_Json))
                    .build();
            return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(CheckOutController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(CheckOutController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }


}

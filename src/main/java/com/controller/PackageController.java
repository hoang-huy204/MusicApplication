package com.controller;

import com.App;
import com.dao.PackageAuthDao;
import com.dao.PackageDao;
import com.model.Auth;
import com.model.Package;
import com.model.Package_Auth;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PackageController implements Initializable {
    private final PackageDao packageDao = new PackageDao();
    private final PackageAuthDao packageAuthDao = new PackageAuthDao();
    private Stage checkoutStage;

    @FXML
    private AnchorPane buy_premiumContainer;
    @FXML
    private ScrollPane scroll_pane;
    @FXML
    private FlowPane package_wrap;

    @FXML
    private AnchorPane user_currentPackage;

    @FXML
    private Label package_name;

    @FXML
    private Label expiryDay;

    @FXML
    private Label expiryMonth;

    @FXML
    private Label expiryYear;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Package_Auth user_Package = packageAuthDao.select_UserCurrentPackage(LoginController.authLogin.getId(), "active");
        if (user_Package != null) {
            show_userCurrentPackage(user_Package);
            check_Expiry(user_Package);
        } else {
            show_Package();
        }
    }

    private void check_Expiry(Package_Auth user_Package) {
        if (user_Package != null) {
            LocalDate current_Date = LocalDate.now();
            if (current_Date.equals(user_Package.getExpiryDate()) || current_Date.isAfter(user_Package.getExpiryDate())) {
                dialogPackage();
            }
        }
    }

    private void dialogPackage() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Premium Has Expired");

        ButtonType button_Extend = new ButtonType("Extend The Premium", ButtonBar.ButtonData.YES);
        ButtonType button_Change = new ButtonType("Change The Premium", ButtonBar.ButtonData.APPLY);
        ButtonType Cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        dialog.getDialogPane().getButtonTypes().addAll(button_Extend, button_Change, Cancel);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == button_Extend) {
                user_currentPackage.setVisible(true);
                Package __package = packageDao.select_PackageByAuth(LoginController.authLogin.getId(), "active");
                if (__package != null) {
                    show_CheckOut(__package, true);
                }
            }

            if (dialogButton == button_Change) {
                if (packageAuthDao.delete_Package(LoginController.authLogin.getId())) {
                    user_currentPackage.setVisible(false);
                    show_Package();
                }
            }

            return null;
        });
        dialog.showAndWait();
    }


    public void show_userCurrentPackage(Package_Auth user_Package) {
        if (user_Package.getExpiryDate() != null) {
            user_currentPackage.setVisible(true);
            expiryDay.setText(String.valueOf(user_Package.getExpiryDate().getDayOfMonth()));
            expiryMonth.setText(String.format("%02d", user_Package.getExpiryDate().getMonthValue()));
            expiryYear.setText(String.valueOf(user_Package.getExpiryDate().getYear()));
        }

        if (user_Package.getPackage_Name() != null) {
            package_name.setText(user_Package.getPackage_Name());
        }
    }

    private void show_Package() {
        buy_premiumContainer.setVisible(true);
        scroll_pane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll_pane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        ArrayList<Package> packages = packageDao.select_all_package("active");
        if (packages != null && !packages.isEmpty()) {
            for (Package _package : packages) {
                AnchorPane anchorPane = new AnchorPane();
                anchorPane.setPrefHeight(270.0);
                anchorPane.setPrefWidth(303.0);

                AnchorPane childAnchorPane = new AnchorPane();
                childAnchorPane.setLayoutX(22.0);
                childAnchorPane.setLayoutY(3.0);
                childAnchorPane.setPrefHeight(265.0);
                childAnchorPane.setPrefWidth(259.0);
                childAnchorPane.setStyle("-fx-background-color: #242424;");
                childAnchorPane.getStyleClass().add("border_wrap");

                Label premiumLabel = new Label("Premium");
                premiumLabel.setLayoutX(59.0);
                premiumLabel.setLayoutY(35.0);
                premiumLabel.setPrefHeight(32.0);
                premiumLabel.setPrefWidth(104.0);
                premiumLabel.setTextFill(javafx.scene.paint.Color.WHITE);
                premiumLabel.setFont(new Font("System Bold", 22.0));

                ImageView imageView = new ImageView();
                imageView.setFitHeight(37.0);
                imageView.setFitWidth(40.0);
                imageView.setLayoutX(12.0);
                imageView.setLayoutY(32.0);
                imageView.setPickOnBounds(true);
                imageView.setPreserveRatio(true);
                String currentDirectory = System.getProperty("user.dir");
                String imagePath = currentDirectory + "/src/main/resources/application/static/images/Home/logo.png";
                File file = new File(imagePath);
                String imageUrl = file.toURI().toString();
                imageView.setImage(new Image(imageUrl));

                Label miniLabel = new Label(_package.getPackageName());
                miniLabel.setLayoutX(12.0);
                miniLabel.setLayoutY(76.0);
                miniLabel.setPrefHeight(51.0);
                miniLabel.setPrefWidth(236.0);
                miniLabel.setTextFill(javafx.scene.paint.Color.web("#c4b1d4"));
                miniLabel.setFont(new Font("System Bold", 40.0));

                Button buyButton = new Button("Mua Premium");
                buyButton.setId("buyBtn");
                buyButton.setLayoutX(22.0);
                buyButton.setLayoutY(190.0);
                buyButton.setPrefHeight(37.0);
                buyButton.setPrefWidth(215.0);
                buyButton.setStyle("-fx-background-color: #c4b1d4;");
                buyButton.getStyleClass().add("border_wrap");
                buyButton.setCursor(Cursor.HAND);
                buyButton.setFont(new Font("System Bold", 17.0));
                buyButton.setMnemonicParsing(false);

                buyButton.setOnAction(event -> {
                    show_CheckOut(_package, false);
                });

                Label priceLabel = new Label(_package.getPackagePrice() + "$/" + _package.getValidity() + " Days");
                priceLabel.setLayoutX(18.0);
                priceLabel.setLayoutY(141.0);
                priceLabel.setPrefHeight(28.0);
                priceLabel.setPrefWidth(223.0);
                priceLabel.setTextFill(javafx.scene.paint.Color.WHITE);
                priceLabel.setFont(new Font("System Bold", 17.0));

                childAnchorPane.getChildren().addAll(premiumLabel, imageView, miniLabel, buyButton, priceLabel);

                anchorPane.getChildren().add(childAnchorPane);

                package_wrap.getChildren().add(anchorPane);
            }
        }
    }

    private void show_CheckOut(Package _package, Boolean isExtend) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/application/views/UI/CheckOut.fxml"));
            CheckOutController checkOutController = new CheckOutController(_package, isExtend);
            fxmlLoader.setController(checkOutController);
            Scene scene = null;
            scene = new Scene(fxmlLoader.load());
            checkoutStage = new Stage();
            checkoutStage.setScene(scene);

            disable_BuyBtn();
            final PackageAuthDao _packageAuthDao = new PackageAuthDao();
            checkoutStage.showingProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue) {
                    enable_BuyBtn();
                    payment_Success(_packageAuthDao, isExtend);
                }
            });
            checkoutStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void payment_Success(PackageAuthDao _packageAuthDao, Boolean isExtend) {
        Package_Auth user_Package = _packageAuthDao.select_UserCurrentPackage(LoginController.authLogin.getId(), "active");
        if (isExtend != null) {
            if (user_Package != null) {
                buy_premiumContainer.setVisible(false);
                user_currentPackage.setVisible(true);
                show_userCurrentPackage(user_Package);
            }
        } else {
            if (user_Package != null) {
                show_userCurrentPackage(user_Package);
            }
        }

    }

    private void disable_BuyBtn() {
        for (Node node : package_wrap.getChildren()) {
            if (node instanceof AnchorPane) {
                AnchorPane anchorPane = (AnchorPane) node;
                for (Node childNode : anchorPane.getChildren()) {
                    if (childNode instanceof AnchorPane) {
                        AnchorPane childAnchorPane = (AnchorPane) childNode;
                        for (Node buttonNode : childAnchorPane.getChildren()) {
                            if (buttonNode instanceof Button) {
                                Button button = (Button) buttonNode;
                                button.setDisable(true);
                            }
                        }
                    }
                }
            }
        }
    }

    private void enable_BuyBtn() {
        for (Node node : package_wrap.getChildren()) {
            if (node instanceof AnchorPane) {
                AnchorPane anchorPane = (AnchorPane) node;
                for (Node childNode : anchorPane.getChildren()) {
                    if (childNode instanceof AnchorPane) {
                        AnchorPane childAnchorPane = (AnchorPane) childNode;
                        for (Node buttonNode : childAnchorPane.getChildren()) {
                            if (buttonNode instanceof Button) {
                                Button button = (Button) buttonNode;
                                button.setDisable(false);
                            }
                        }
                    }
                }
            }
        }
    }

}

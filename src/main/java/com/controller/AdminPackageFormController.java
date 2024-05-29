/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.controller;

import static com.controller.AdminPackageController.adminPackageController;
import com.dao.PackageDao;
import com.model.Package;
import com.utils.Utils;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author daoqu
 */
public class AdminPackageFormController implements Initializable {

    @FXML
    private TextField namePackageInp;
    @FXML
    private TextField pricePackageInp;
    @FXML
    private Button save;
    private Utils utils = new Utils();
    private PackageDao packageDao = new PackageDao();

    private Scene currentScene;
    private Stage currentStage;

    private boolean isEditing = false;

    private Package packageEditing;

    public AdminPackageFormController() {
    }

    public AdminPackageFormController(Package packageEditing) {
        this.packageEditing = packageEditing;
        this.isEditing = true;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            currentScene = namePackageInp.getScene();
            currentStage = (Stage) currentScene.getWindow();
        });
        if (isEditing) {
            namePackageInp.setText(packageEditing.getPackageName());
            pricePackageInp.setText(packageEditing.getPackagePrice().toString());
        }
        save.setOnAction(event -> {
//            System.out.println("hey");
            if (isEditing) {
//                System.out.println("update");
                update();
            } else {
                add();
            }
        });
    }

    public void add() {
        String name = namePackageInp.getText();
        BigDecimal price = null;
        try {
            price = new BigDecimal(pricePackageInp.getText());
        } catch (NumberFormatException e) {
            price = null;
        }
        
        Boolean isValidate = validate(name, price);
        if (isValidate) {
            Package newPackage = new Package(null, name, price, null);
            boolean isSuccess = packageDao.add(newPackage);
            if (isSuccess) {
                adminPackageController.printRecords("active");
                currentStage.close();
//                System.out.println("add");
            } else {
                utils.showNotification("Error", "Add package error", Utils.NotificationType.ERROR, javafx.util.Duration.seconds(3));
            }
        }
    }

    public void update() {
        String name = namePackageInp.getText();
        BigDecimal price = null;
        try {
            price = new BigDecimal(pricePackageInp.getText());
        } catch (NumberFormatException e) {
            price = null;
        }
        
        Boolean isValidate = validate(name, price);
//        System.out.println("success");
        if (isValidate) {
//            System.out.println("Hey");
            packageEditing.setPackageName(name);
            packageEditing.setPackagePrice(price);
            boolean isSuccess = packageDao.update(packageEditing);
//            System.out.println(isSuccess);
            if (isSuccess) {
                adminPackageController.printRecords("active");
//                System.out.println("update");
                currentStage.close();
            } else {
                utils.showNotification("Error", "Update Genre error", Utils.NotificationType.ERROR, javafx.util.Duration.seconds(3));
            }
        }
    }

    public Boolean validate(String name, BigDecimal price) {
        Boolean isValid = true;
        if (name.isEmpty()) {
            utils.showNotification("Error", "Name field can't be empty", Utils.NotificationType.ERROR, javafx.util.Duration.seconds(3));
            isValid = false;
        }
        
        if (price == null) {
            utils.showNotification("Error", "Empty or improperly formatted rack", Utils.NotificationType.ERROR, javafx.util.Duration.seconds(3));
            isValid = false;
        }
        return isValid;
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.controller;

import static com.controller.AdminGenreController.adminGenreController;
import com.dao.GenreTrackDao;
import com.model.GenreTrack;
import com.utils.Utils;
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
public class AdminGenreFormController implements Initializable{
@FXML
private TextField namegenre;
@FXML
    private Button save;
    private Utils utils = new Utils();
      private GenreTrackDao genreTrackDao = new GenreTrackDao();
      
        private Scene currentScene;
    private Stage currentStage;

    private boolean isEditing = false;

    private GenreTrack genreEditing;

    public AdminGenreFormController() {
    }

    public AdminGenreFormController(GenreTrack genreEditing) {
        this.genreEditing = genreEditing;
        this.isEditing = true;
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         Platform.runLater(() -> {
            currentScene = namegenre.getScene();
            currentStage = (Stage) currentScene.getWindow();
         });
          if (isEditing) {
           namegenre.setText(genreEditing.getName());
    }
     save.setOnAction(event -> {
            if (isEditing) {
                System.out.println("update");
                update();
            } else {
                add();
            }
        });
}
    public void add() {
        String name = namegenre.getText();
           Boolean isValidate = validate(name);
             if (isValidate) {
           GenreTrack genretrack = new GenreTrack(null,name,null,null);
           boolean isSuccess =  genreTrackDao.add(genretrack);
             if (isSuccess) {
                utils.showNotification("Success", "Add Genre successfully", Utils.NotificationType.INFORMATION, javafx.util.Duration.seconds(3));
                currentStage.close();
                adminGenreController.printRecords("active");
            } else {
                utils.showNotification("Error", "Add Genre error", Utils.NotificationType.ERROR, javafx.util.Duration.seconds(3));
            }
        }
                   }
    public void update(){
        String name = namegenre.getText();
         Boolean isValidate = validate(name);
         if (isValidate) {
             genreEditing.setName(name);
               boolean isSuccess =  genreTrackDao.update(genreEditing);
               if (isSuccess) {
                utils.showNotification("Success", "Update Genre successfully", Utils.NotificationType.INFORMATION, javafx.util.Duration.seconds(3));
                currentStage.close();
                adminGenreController.printRecords( "active");
            } else {
                utils.showNotification("Error", "Update Genre error", Utils.NotificationType.ERROR, javafx.util.Duration.seconds(3));
            }
         }
    }
 public Boolean validate(String name) {
        Boolean isValid = true;
        if (name.isEmpty()) {
            utils.showNotification("Error", "Name field can't be empty", Utils.NotificationType.ERROR, javafx.util.Duration.seconds(3));
            isValid = false;
        }
     return isValid;
}
}

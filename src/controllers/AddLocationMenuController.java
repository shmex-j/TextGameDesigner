package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.Main;
import unnamed.Location;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static main.Main.ew;
import static main.Main.getCurrentProject;

public class AddLocationMenuController {

    @FXML
    public TextField nameTextField;
    @FXML
    public TextField imagePathTextField;

    @FXML
    public void initialize(){

    }

    @FXML
    void choseImage(){
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        File openedFile = fileChooser.showOpenDialog(stage);
        if (openedFile == null) return;
        imagePathTextField.setText(openedFile.getAbsolutePath());
    }

    @FXML
    void okPressed() throws FileNotFoundException {
        String name = nameTextField.getText();

        //Verifying
        if (name.equals("")){
            ew.throwError("Enter name, please.");
            return;
        }else if (getCurrentProject().getLocations().containsKey(name)){
            ew.throwError("Location name \""+name+"\" already taken");
            return;
        }

        if (imagePathTextField.getText().equals("")){
            getCurrentProject().getLocations().put(
                    name,
                    new Location(name, Main.getMainController().getLocationChoiceBox())
            );
            closeAddLocationMenu();
            return;
        }

        Image image = new Image(new FileInputStream(imagePathTextField.getText()));

        getCurrentProject().getLocations().put(
                name,
                new Location(name, Main.getMainController().getLocationChoiceBox(), image)
        );
        closeAddLocationMenu();
    }

    private void closeAddLocationMenu(){
        nameTextField.setText("");
        imagePathTextField.setText("");
        Main.getMainController().getAddLocationMenuStage().close();
    }

    @FXML
    void cancelPressed(){
        closeAddLocationMenu();
    }

    public AddLocationMenuController() {
    }
}
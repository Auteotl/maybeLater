package com.example.maybelater;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import SomePack.DataHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import org.w3c.dom.events.MouseEvent;

public class HelloController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addButton;

    @FXML
    private Button buttonEditCat;

    @FXML
    private ChoiceBox<?> catedoryField;

    @FXML
    private Button delButton;

    @FXML
    private TextField descriptionField;

    @FXML
    private DialogPane dialogPaneEditCat;

    @FXML
    private MenuItem menuEditCat;

    @FXML
    private TextField textEditCat;

    @FXML
    private Button urlButton;

    @FXML
    private TextField urlDescriptionField;

    @FXML
    private TextField urlField;
     DataHandler db = null;
    @FXML
    void initialize() {

        menuEditCat.setOnAction(actionEvent -> {
            dialogPaneEditCat.setDisable(false);
            dialogPaneEditCat.setVisible(true);
            buttonEditCat.setDisable(false);
            buttonEditCat.setVisible(true);
            textEditCat.setDisable(false);
            textEditCat.setVisible(true);
        });

        DataHandler dbHandler = new DataHandler();
        buttonEditCat.setOnAction(actionEvent ->
        {
            try {
                dbHandler.addNewCatInDB(textEditCat.getText().trim());
                System.out.println("good");
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println( "no");
            } catch (ClassNotFoundException e) {
                System.out.println("still no");
                e.printStackTrace();
            }
        });


    }



}

package com.example.maybelater;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import SomePack.DataHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.w3c.dom.events.MouseEvent;

public class HelloController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addButton;

    @FXML
    private ChoiceBox<String> categoryChoiseBox;

    @FXML
    private ChoiceBox<String> choiseChapterAddCat;

    @FXML
    private TextField descriptionField;

    @FXML
    private DialogPane dialogChaptAdd;

    @FXML
    private DialogPane dialogPaneAddCat;

    @FXML
    private MenuItem menuAddCat;

    @FXML
    private MenuItem menuAddChapter;

    @FXML
    private MenuBar menuBar;

    @FXML
    private TableView<?> tableMain;

    @FXML
    private TextField textAddCat;

    @FXML
    private TextField textAddChapt;

    @FXML
    private TreeView<?> treeMenu;

    @FXML
    private TextField urlField;

    DataHandler db = null;
    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        DataHandler dbHandler = new DataHandler();


//Добавление строки с URL в БД
    addButton.setOnAction(actionEvent -> {
        try {
            dbHandler.addInfoInURLTab(urlField.getText().trim(), descriptionField.getText().trim(),dbHandler.takeCatWithCatId(categoryChoiseBox.getValue()));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("no no no");
        }
        urlField.setText("URL");
        descriptionField.setText("Описание");
        categoryChoiseBox.setValue("Выбери категорию: ");
    });
//Список ChoiseBox категории
        categoryChoiseBox.getItems().addAll(dbHandler.takeCatNameForChoise());
        categoryChoiseBox.setValue("Без категории: ");

//Окно "Добавить категорию"

        menuAddCat.setOnAction(actionEvent -> {
            dialogPaneAddCat.setVisible(true);
        });
//Закрыть окно "Добавить категорию"
        dialogPaneAddCat.lookupButton(ButtonType.CLOSE).addEventFilter(ActionEvent.ACTION, event -> {
            dialogPaneAddCat.setVisible(false);
            if(textAddCat.getText()!=""){
                textAddCat.clear();
            }
        });

//Добавление категории
        dialogPaneAddCat.lookupButton(ButtonType.OK).addEventFilter(ActionEvent.ACTION, event -> {
            try {
                if(dialogChaptAdd.isVisible()){
                    //нужно сделаать так, чтобы нельзя было открыть два окна одновременно
                }
                else if(textAddCat.getText()!="") {
                    dbHandler.addNewCatInDB(textAddCat.getText().trim(),dbHandler.takeChaptWithCatId(choiseChapterAddCat.getValue()));
                    dialogPaneAddCat.setVisible(false);
                    textAddCat.clear();
                }
                else dialogPaneAddCat.setVisible(false);

            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println( "no");
            } catch (ClassNotFoundException e) {
                System.out.println("still no");
                e.printStackTrace();
            }
        });
//Окно "Добавить раздел"

        menuAddChapter.setOnAction(actionEvent -> {
            dialogChaptAdd.setVisible(true);
        });

//Закрыть окно "Добавить раздел"
        dialogChaptAdd.lookupButton(ButtonType.CLOSE).addEventFilter(ActionEvent.ACTION, event -> {
            dialogChaptAdd.setVisible(false);
            if(textAddChapt.getText()!=""){
                textAddChapt.clear();
            }
        });

//Добавление раздела
        dialogChaptAdd.lookupButton(ButtonType.OK).addEventFilter(ActionEvent.ACTION, event -> {
            try {
                if(textAddChapt.getText()!=""){
                    dbHandler.addNewChaptInDB(textAddChapt.getText().trim());
                    dialogChaptAdd.setVisible(false);
                    textAddChapt.clear();
                }
               else dialogChaptAdd.setVisible(false);

            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println( "no");
            } catch (ClassNotFoundException e) {
                System.out.println("still no");
                e.printStackTrace();
            }
        });

//ChoiseBox для раздела
        choiseChapterAddCat.getItems().addAll(dbHandler.takeChaptNameForChoise());
        choiseChapterAddCat.setValue("Chapter 2");
    }



}

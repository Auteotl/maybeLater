package com.example.maybelater;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import SomePack.DataHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;


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
    private DialogPane errorAddCat;

    @FXML
    private DialogPane errorAddChapt;

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
    private TreeView<String> treeMenu;

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
        categoryChoiseBox.setValue("Выбери категорию: ");
    });
//Список ChoiseBox категории
        categoryChoiseBox.getItems().addAll(dbHandler.takeCatNameForChoise());
        categoryChoiseBox.setValue("Без категории: ");

//Окно "Добавить категорию"

        menuAddCat.setOnAction(actionEvent -> {
            if(dialogChaptAdd.isVisible()){
                dialogChaptAdd.setVisible(false);
            }
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
                if(textAddCat.getText().trim()!="") {
                    if (dbHandler.takeCatNameForChoise().contains(textAddCat.getText().trim())) {
                        errorAddCat.setVisible(true);
                        errorAddCat.lookupButton(ButtonType.OK).addEventFilter(ActionEvent.ACTION, event1 -> {
                            errorAddCat.setVisible(false);
                        });
                    } else {
                        dbHandler.addNewCatInDB(textAddCat.getText().trim(), dbHandler.takeChaptWithCatId(choiseChapterAddCat.getValue()));
                        dialogPaneAddCat.setVisible(false);
                        textAddCat.clear();
                    }
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
            if(dialogPaneAddCat.isVisible()){
                dialogPaneAddCat.setVisible(false);
            }
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
                if(textAddChapt.getText().trim()!="") {
                    if (dbHandler.takeChaptNameForChoise().contains(textAddChapt.getText().trim())) {
                        errorAddChapt.setVisible(true);
                        errorAddChapt.lookupButton(ButtonType.OK).addEventFilter(ActionEvent.ACTION, event1 -> {
                            errorAddChapt.setVisible(false);
                        });
                    } else {
                        dbHandler.addNewChaptInDB(textAddChapt.getText().trim());
                        dialogChaptAdd.setVisible(false);
                        textAddChapt.clear();
                    }
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
        choiseChapterAddCat.setValue("Chapter 1");

//Дерево
        List<TreeItem> listTreeItem = new ArrayList<>();
       for (int i = 0; i < dbHandler.takeChaptNameForChoise().size(); i++){
            listTreeItem.add(new TreeItem<String>(dbHandler.takeChaptNameForChoise().get(i)));
        }
        TreeItem<String> root = new TreeItem<>("root");
        root.setExpanded(true);
        for (int i = 0; i < dbHandler.takeChaptNameForChoise().size(); i++){
            root.getChildren().add(listTreeItem.get(i));
        }

        for (int i = 0; i < dbHandler.takeChaptNameForChoise().size(); i++){
            for(int j = 0; j < dbHandler.takeCatArrayForTree(dbHandler.takeChaptNameForChoise().get(i)).size(); j++){
                makeBranch(dbHandler.takeCatArrayForTree(dbHandler.takeChaptNameForChoise().get(i)).get(j),listTreeItem.get(i));
             }
            //listTreeItem.get(i).getChildren().add(new TreeItem<String>(dbHandler.takeCatArrayForTree(dbHandler.takeChaptNameForChoise().get(i)).get(i)));
            //System.out.println(dbHandler.takeCatArrayForTree(dbHandler.takeChaptNameForChoise().get(i)).get(i));
        }
        treeMenu.setRoot(root);
        treeMenu.setShowRoot(false);


        //MultipleSelectionModel<TreeItem<String>> selectionModel = treeMenu.getSelectionModel();
        //selectionModel.setSelectionMode(SelectionMode.SINGLE);
    }
//Ветка дерева
    public TreeItem<String> makeBranch(String title, TreeItem<String> parent){
        TreeItem<String> item = new TreeItem<>(title);
        item.setExpanded(true);
        parent.getChildren().add(item);
        return item;
    }
}

package com.example.maybelater;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import SomePack.DataHandler;
import SomePack.TableBody;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


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
    private ChoiceBox<String> choiseEditTable;

    @FXML
    private DialogPane dialogPaneEditTable;

    @FXML
    private TextField textEditTableDesc;

    @FXML
    private TextField textEditTableUrl;

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
    private DialogPane confirmWindow;

    @FXML
    private MenuItem menuAddCat;

    @FXML
    private MenuItem menuAddChapter;

    @FXML
    private MenuBar menuBar;

    @FXML
    private TableView<TableBody> tableMain;

    @FXML
    private TextField textAddCat;

    @FXML
    private TextField textAddChapt;

    @FXML
    private TreeView<String> treeMenu;

    @FXML
    private TextField urlField;

    @FXML
    private MenuItem contextDelCat;

    @FXML
    private MenuItem contextEditCat;

    @FXML
    private DialogPane confirmWindowTable;

    @FXML
    private DialogPane dialogPaneEditCat;

    @FXML
    private TextField textEditCat;

    @FXML
    private ChoiceBox<String> choiseChapterEditCat;

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        DataHandler dbHandler = new DataHandler();

//Добавление строки с URL в БД
        addButton.setOnAction(actionEvent -> {
            try {
                dbHandler.addInfoInURLTab(urlField.getText().trim(), descriptionField.getText().trim(),
                        dbHandler.takeCatWithCatId(categoryChoiseBox.getValue()));
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
            if (dialogChaptAdd.isVisible()) {
                dialogChaptAdd.setVisible(false);
            }
            dialogPaneAddCat.setVisible(true);

        });
//Закрыть окно "Добавить категорию"
        dialogPaneAddCat.lookupButton(ButtonType.CLOSE).addEventFilter(ActionEvent.ACTION, event -> {
            dialogPaneAddCat.setVisible(false);
            if (textAddCat.getText() != "") {
                textAddCat.clear();
            }
        });

//Добавление категории
        dialogPaneAddCat.lookupButton(ButtonType.OK).addEventFilter(ActionEvent.ACTION, event -> {
            try {
                if (textAddCat.getText().trim() != "") {
                    if (dbHandler.takeCatNameForChoise().contains(textAddCat.getText().trim())) {
                        errorAddCat.setVisible(true);
                        errorAddCat.lookupButton(ButtonType.OK).addEventFilter(ActionEvent.ACTION, event1 -> {
                            errorAddCat.setVisible(false);
                        });
                    } else {
                        dbHandler.addNewCatInDB(textAddCat.getText().trim(),
                                dbHandler.takeChaptId(choiseChapterAddCat.getValue()));
                        dialogPaneAddCat.setVisible(false);
                        textAddCat.clear();
                    }
                } else dialogPaneAddCat.setVisible(false);

            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("no");
            } catch (ClassNotFoundException e) {
                System.out.println("still no");
                e.printStackTrace();
            }
        });
//Окно "Добавить раздел"

        menuAddChapter.setOnAction(actionEvent -> {
            if (dialogPaneAddCat.isVisible()) {
                dialogPaneAddCat.setVisible(false);
            }
            dialogChaptAdd.setVisible(true);
        });

//Закрыть окно "Добавить раздел"
        dialogChaptAdd.lookupButton(ButtonType.CLOSE).addEventFilter(ActionEvent.ACTION, event -> {
            dialogChaptAdd.setVisible(false);
            if (textAddChapt.getText() != "") {
                textAddChapt.clear();
            }
        });

//Добавление раздела
        dialogChaptAdd.lookupButton(ButtonType.OK).addEventFilter(ActionEvent.ACTION, event -> {
            try {
                if (textAddChapt.getText().trim() != "") {
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
                } else dialogChaptAdd.setVisible(false);

            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("no");
            } catch (ClassNotFoundException e) {
                System.out.println("still no");
                e.printStackTrace();
            }
        });

//ChoiseBox для раздела
        choiseChapterAddCat.getItems().addAll(dbHandler.takeChaptNameForChoise());
        choiseChapterAddCat.setValue("Chapter 1");
        choiseChapterEditCat.getItems().addAll(dbHandler.takeChaptNameForChoise());
//Дерево
        List<TreeItem> listTreeItem = new ArrayList<>();
        for (int i = 0; i < dbHandler.takeChaptNameForChoise().size(); i++) {
            listTreeItem.add(new TreeItem<String>(dbHandler.takeChaptNameForChoise().get(i)));
        }
        TreeItem<String> root = new TreeItem<>("root");
        root.setExpanded(true);
        for (int i = 0; i < dbHandler.takeChaptNameForChoise().size(); i++) {
            root.getChildren().add(listTreeItem.get(i));
        }
        for (int i = 0; i < dbHandler.takeChaptNameForChoise().size(); i++) {
            for (int j = 0; j < dbHandler.takeCatArrayForTree(dbHandler.takeChaptNameForChoise().get(i)).size(); j++) {
                makeBranch(
                        dbHandler.takeCatArrayForTree(
                                dbHandler.takeChaptNameForChoise().get(i)).get(j),
                        listTreeItem.get(i));
            }

        }


        treeMenu.setRoot(root);
        treeMenu.setShowRoot(true);
        //Колонки

        //url
        TableColumn<TableBody, String> columnSomeURL = new TableColumn<>("URL");
        columnSomeURL.setMinWidth(200);
        columnSomeURL.setCellValueFactory(new PropertyValueFactory<>("someURL"));

        //deacription
        TableColumn<TableBody, String> columnDescription = new TableColumn<>("Описание");
        columnDescription.setMinWidth(200);
        columnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

        //date
        TableColumn<TableBody, String> columnDate = new TableColumn<>("Дата");
        columnDate.setMinWidth(50);
        columnDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        tableMain.getColumns().addAll(columnSomeURL, columnDescription, columnDate);
//        HBox placeHolder = new HBox();
//        tableMain.getColumns().forEach(tc->{
//            StackPane colHolder = new StackPane();
//            colHolder.getStyleClass().add("table-view-place-holder");
//            colHolder.prefWidthProperty().bind(tc.widthProperty());
//            placeHolder.getChildren().add(colHolder);
//        });
//        tableMain.setPlaceholder(placeHolder);

        //Левый щелчок мыши по TreeMenu
        treeMenu.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY){
                treeMenu.getSelectionModel().selectedItemProperty()
                        .addListener((v, oldValue, newValue) -> {
                            ObservableList<TableBody> obsURLList
                                    = FXCollections.observableArrayList(dbHandler.URLListView(newValue.getValue()));
                            tableMain.setItems(obsURLList);
                        });
            }
        //Правый щелчок мыши по TreeMenu
          else if(mouseEvent.getButton()==MouseButton.SECONDARY){

//Контекстное меню -> Изменить
                contextEditCat.setOnAction(event -> {
            dialogPaneEditCat.setVisible(true);
            String buffCat = treeMenu.getSelectionModel().getSelectedItem().getValue();
            String buffChapt = treeMenu.getSelectionModel().getSelectedItem().getParent().getValue();
            choiseChapterEditCat.setValue(buffChapt);
            textEditCat.setText(buffCat);
            dialogPaneEditCat.lookupButton(ButtonType.OK).addEventFilter(ActionEvent.ACTION, event1 -> {
                if (!(textEditCat.getText().equals(buffCat))){
                    try {
                        dbHandler.updateCategoryName(dbHandler.takeCatWithCatId(buffCat), textEditCat.getText());
                        System.out.println("Cat is ok");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Next");
                }
                if(!(choiseChapterEditCat.getValue().equals(buffChapt))){
                    System.out.println("urwellcome");
                    try {
                        System.out.println(dbHandler.takeCatWithCatId(buffCat) + choiseChapterEditCat.getValue());
                        dbHandler.updateCategoryChapt
                                (dbHandler.takeCatWithCatId(buffCat), choiseChapterEditCat.getValue());
                        System.out.println("Chapt is ok");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                dialogPaneEditCat.setVisible(false);
            });
                dialogPaneEditCat.lookupButton(ButtonType.CLOSE).addEventFilter(ActionEvent.ACTION, event1 -> {
                    dialogPaneEditCat.setVisible(false);
                });
            });

//Контекстное меню -> Удалить
            contextDelCat.setOnAction(event -> {
                confirmWindow.setVisible(true);
                confirmWindow.lookupButton(ButtonType.YES).addEventFilter(ActionEvent.ACTION, event1 -> {
                    if(treeMenu.getSelectionModel().getSelectedItem().getParent().equals(root)){
                    try {
                        dbHandler.deleteChaptFromDB(treeMenu.getSelectionModel().getSelectedItem().getValue());
                        confirmWindow.setVisible(false);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                else {
                        try {
                            dbHandler.deleteCatFromDB(treeMenu.getSelectionModel().getSelectedItem().getValue());
                            confirmWindow.setVisible(false);
                        } catch (SQLException e) {
                            System.out.println("Loooooser");
                            e.printStackTrace();
                        }
                    }
                });
                    confirmWindow.lookupButton(ButtonType.NO).addEventFilter(ActionEvent.ACTION, event1 -> {
                    confirmWindow.setVisible(false);
                });
            });
            }
        });
        try {
            choiseEditTable.getItems().addAll(dbHandler.takeCatNameForChoise());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        tableMain.setOnMouseClicked(mouseEvent -> {
            if(mouseEvent.getButton()==MouseButton.PRIMARY&(tableMain.getSelectionModel().getSelectedItem()!=null)){
                if(mouseEvent.getClickCount()==2){
                   Application app = new Application() {
                       @Override
                       public void start(Stage stage) throws Exception {

                       }
                   };
                    app.getHostServices().showDocument(tableMain.getSelectionModel().getSelectedItem().getSomeURL());
                    try {
                        dbHandler.updateIsVisited(tableMain.getSelectionModel().getSelectedItem().getUrlId());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }

            else if(mouseEvent.getButton()==MouseButton.SECONDARY){
                contextEditCat.setOnAction(event -> {
                if(tableMain.getSelectionModel().getSelectedItem()!=null){
                dialogPaneEditTable.setVisible(true);
                String buffUrl = tableMain.getSelectionModel().getSelectedItem().getSomeURL();
                String buffDesc = tableMain.getSelectionModel().getSelectedItem().getDescription();
                int urlId = tableMain.getSelectionModel().getSelectedItem().getUrlId();
                int catId = tableMain.getSelectionModel().getSelectedItem().getUrlCat();
                textEditTableUrl.setText(buffUrl);
                textEditTableDesc.setText(buffDesc);
                    try {
                        choiseEditTable.setValue(dbHandler.takeCatNameWithCatId
                                (tableMain.getSelectionModel().getSelectedItem().getUrlCat()));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    dialogPaneEditTable.lookupButton(ButtonType.OK).addEventFilter(ActionEvent.ACTION, event1 -> {
                        if(!(textEditTableUrl.getText().equals(buffUrl))){
                            try {
                                dbHandler.updateTableUrl(urlId, textEditTableUrl.getText());
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                        if(!(textEditTableDesc.getText().equals(buffDesc))){
                            try {
                                dbHandler.updateTableDescription(urlId, textEditTableDesc.getText());
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                        try {
                            if(!(dbHandler.takeCatNameWithCatId(catId).equals(choiseEditTable.getValue()))){
                                dbHandler.updateTableCat(urlId, dbHandler.takeCatWithCatId(choiseEditTable.getValue()));
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        dialogPaneEditTable.setVisible(false);
                    });
                    dialogPaneEditTable.lookupButton(ButtonType.CLOSE).addEventFilter(ActionEvent.ACTION, event1 -> {
                        dialogPaneEditTable.setVisible(false);
                    });
                }});
                contextDelCat.setOnAction(event -> {
                    if(tableMain.getSelectionModel().getSelectedItem()!=null){
                    confirmWindowTable.setVisible(true);
                    confirmWindowTable.lookupButton(ButtonType.YES).addEventFilter(ActionEvent.ACTION, event1 -> {
                        try {
                            dbHandler.deleteStringFromDB(tableMain.getSelectionModel().getSelectedItem().getUrlId());
                            confirmWindowTable.setVisible(false);
                        } catch (SQLException e) {
                            System.out.println("Ha ha ha");
                            e.printStackTrace();
                        }
                    });
                    confirmWindowTable.lookupButton(ButtonType.NO).addEventFilter(ActionEvent.ACTION, event1 -> {
                        confirmWindowTable.setVisible(false);
                    });
                }});
            }
        });
    }

    //Ветка дерева
    public TreeItem<String> makeBranch(String title, TreeItem<String> parent) {
        TreeItem<String> item = new TreeItem<>(title);
        item.setExpanded(true);
        parent.getChildren().add(item);
        return item;
    }
}

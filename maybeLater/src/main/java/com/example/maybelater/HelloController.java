package com.example.maybelater;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.util.*;

import SomePack.DataHandler;
import SomePack.TableBody;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
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
    private DialogPane dialogPaneEditCat;

    @FXML
    private TextField textEditCat;

    @FXML
    private ChoiceBox<String> choiseChapterEditCat;

    @FXML
    private MenuItem contextDelURL;

    @FXML
    private MenuItem contextEditURL;

    @FXML
    private Label statusBar;

    @FXML
    private Label labelForChoiseEditCat;

    @FXML
    private Label labelForEditCat;

    @FXML
    private Label labelMainForEditCat;

    @FXML
    private Label labelConfirmWindow;

    @FXML
    private Text textConfirmWindow;

    @FXML
    private ContextMenu contextMenuTreeView;


    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        DataHandler dbHandler = new DataHandler();
        List<TreeItem> listTreeItem = new ArrayList<>();
        for (int i = 0; i < dbHandler.takeChaptNameForChoise().size(); i++) {
            listTreeItem.add(new TreeItem<>(dbHandler.takeChaptNameForChoise().get(i)));
        }
        createChoiseForAdd();
//Без категории
        if (!(dbHandler.takeCatNameForChoise().contains("Без категории"))) {
            dbHandler.addNewChaptInDB("Main");
            dbHandler.addNewCatInDB("Без категории",
                    dbHandler.takeChaptId("Main"));
            treeMenu.getRoot().getChildren().get(
                    dbHandler.takeChaptNameForChoise().indexOf(
                            "Main")).getChildren().add(new TreeItem<>("Без категории"));
            categoryChoiseBox.getItems().add("Без категории");
        }


//Добавление строки с URL в БД
        addButton.setOnAction(actionEvent -> {
            String addURL = urlField.getText().trim();
            try {
                if (!(addURL.contains("http://") || addURL.contains("https://"))) {
                    addURL = "http://" + urlField.getText().trim();
                }
                boolean valid = true;
                try {
                    URL url = new URL(addURL);
                    URLConnection conn = url.openConnection();
                    conn.connect();
                } catch (MalformedURLException e) {
                    valid = false;
                    system_message(statusBar, "Введите корректный URL адрес", Color.RED);
                } catch (IOException e) {
                    valid = false;
                    system_message(statusBar, "Введите корректный URL адрес", Color.RED);
                }
                if (valid) {

                    dbHandler.addInfoInURLTab(addURL, descriptionField.getText().trim(),
                            dbHandler.takeCatWithCatId(categoryChoiseBox.getValue()));
                    ObservableList<TableBody> obsURLList
                            = FXCollections.observableArrayList(dbHandler.URLListView(
                            categoryChoiseBox.getValue()));
                    tableMain.setItems(obsURLList);
                    system_message(statusBar, "Добавлено", Color.GREEN);
                    urlField.clear();
                    descriptionField.clear();
                    categoryChoiseBox.setValue("Без категории");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });


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
                        system_message(statusBar, "Категория с таким названием уже существует", Color.RED);
                    } else {
                        String newCat = textAddCat.getText().trim();
                        dbHandler.addNewCatInDB(newCat,
                                dbHandler.takeChaptId(choiseChapterAddCat.getValue()));
                        dialogPaneAddCat.setVisible(false);
                        textAddCat.clear();
                        treeMenu.getRoot().getChildren().get(
                                dbHandler.takeChaptNameForChoise().indexOf(
                                        choiseChapterAddCat.getValue())).getChildren().add(new TreeItem<>(newCat));
                        categoryChoiseBox.getItems().add(newCat);
                        system_message(statusBar, "Категория добавлена", Color.GREEN);
                    }
                } else dialogPaneAddCat.setVisible(false);

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
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
                        system_message(statusBar, "Раздел с таким названием уже существует", Color.RED);
                    } else {
                        String newChapt = textAddChapt.getText().trim();
                        dbHandler.addNewChaptInDB(newChapt);
                        treeMenu.getRoot().getChildren().add(new TreeItem<>(newChapt));
                        choiseChapterAddCat.getItems().add(newChapt);
                        choiseChapterEditCat.getItems().add(newChapt);
                        dialogChaptAdd.setVisible(false);
                        textAddChapt.clear();
                        system_message(statusBar, "Раздел добавлен", Color.GREEN);
                    }
                } else dialogChaptAdd.setVisible(false);

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

//ChoiseBox для раздела
        choiseChapterAddCat.getItems().addAll(dbHandler.takeChaptNameForChoise());
        choiseChapterAddCat.setValue(dbHandler.takeChaptNameForChoise().get(0));
        choiseChapterEditCat.getItems().addAll(dbHandler.takeChaptNameForChoise());
//Дерево
        makeTree(listTreeItem);
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
//      HBox placeHolder = new HBox();
//        tableMain.getColumns().forEach(tc->{
//            StackPane colHolder = new StackPane();
//            colHolder.getStyleClass().add("table-view-place-holder");
//            colHolder.prefWidthProperty().bind(tc.widthProperty());
//            placeHolder.getChildren().add(colHolder);
//        });
//      tableMain.setPlaceholder("Hello");

        //Левый щелчок мыши по TreeMenu

        treeMenu.contextMenuProperty().bind(
                Bindings.when(Bindings.isNotNull(treeMenu.getSelectionModel().selectedItemProperty()))
                        .then(contextMenuTreeView)
                        .otherwise((ContextMenu) null));
//        tableMain.contextMenuProperty().bind(
//                Bindings.when(Bindings.isNotNull(tableMain.getSelectionModel().selectedItemProperty()))
//                        .then()
//                        .otherwise((ContextMenu)null));

        treeMenu.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                String badIdea = mouseEvent.getTarget().toString();
                if (badIdea.contains("null"))
                    treeMenu.getSelectionModel().clearSelection();
                else {
                    treeMenu.getSelectionModel().selectedItemProperty()
                            .addListener((v, oldValue, newValue) -> {
                                if (newValue != null) {
                                    ObservableList<TableBody> obsURLList
                                            = FXCollections.observableArrayList(
                                            dbHandler.URLListView(newValue.getValue()));
                                    tableMain.setItems(obsURLList);
                                }
                            });
                }
            }

            //Правый щелчок мыши по TreeMenu
            else if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                String badIdea = mouseEvent.getTarget().toString();
                if (badIdea.contains("null"))
                    treeMenu.getSelectionModel().clearSelection();


//Контекстное меню -> Изменить
                contextEditCat.setOnAction(event -> {
                    if (treeMenu.getSelectionModel().getSelectedItem().getValue().equals("Main") ||
                            treeMenu.getSelectionModel().getSelectedItem().getValue().equals("Без категории")) {
                        system_message(statusBar, "Не стоит этого делать", Color.RED);
                    } else {
                        if (treeMenu.getSelectionModel().getSelectedItem().getParent().getValue().equals("root")) {
                            choiseChapterEditCat.setVisible(false);
                            labelForChoiseEditCat.setVisible(false);
                            labelMainForEditCat.setText("Изменить раздел");
                            labelForEditCat.setText("Введите название раздела: ");
                        }
                        dialogPaneEditCat.setVisible(true);
                        String buffCat = treeMenu.getSelectionModel().getSelectedItem().getValue();
                        String buffChapt = treeMenu.getSelectionModel().getSelectedItem().getParent().getValue();
                        choiseChapterEditCat.setValue(buffChapt);
                        textEditCat.setText(buffCat);
                        dialogPaneEditCat.lookupButton(ButtonType.OK).setOnMouseClicked(mouseEvent1 -> {
                            if (mouseEvent1.getButton().equals(MouseButton.PRIMARY)) {
                                if (treeMenu.getSelectionModel().getSelectedItem().getParent().getValue().equals(
                                        "root")) {
                                    if (!(textEditCat.getText().equals(buffCat))) {
                                        try {
                                            dbHandler.updateChaptName(buffCat, textEditCat.getText().trim());
                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        }
                                        choiseChapterAddCat.getItems().set(
                                                choiseChapterAddCat.getItems().indexOf(buffCat),
                                                textEditCat.getText().trim());
                                        choiseChapterEditCat.getItems().set(
                                                choiseChapterEditCat.getItems().indexOf(buffCat),
                                                textEditCat.getText().trim());
                                        treeMenu.getSelectionModel().getSelectedItem().setValue(
                                                textEditCat.getText().trim());
                                        textEditCat.clear();
                                        choiseChapterEditCat.setVisible(true);
                                        labelForChoiseEditCat.setVisible(true);
                                        system_message(statusBar, "Изменения внесены успешно", Color.GREEN);
                                        dialogPaneEditCat.setVisible(false);
                                        labelMainForEditCat.setText("Изменить категорию");
                                        labelForEditCat.setText("Введите название категории: ");

                                    }
                                } else {
                                    if (!(textEditCat.getText().equals(buffCat))) {
                                        try {
                                            dbHandler.updateCategoryName(
                                                    dbHandler.takeCatWithCatId(buffCat), textEditCat.getText());
                                            treeMenu.getSelectionModel().getSelectedItem().setValue(
                                                    textEditCat.getText().trim());
//////////////////////treeMenu.getSelectionModel().getSelectedItem().getParent().getChildren() обновление имени

                                            int indexOldCat = categoryChoiseBox.getItems().indexOf(buffCat);
                                            categoryChoiseBox.getItems().set(indexOldCat, textEditCat.getText().trim());
                                            textEditCat.clear();

                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    if (!(choiseChapterEditCat.getValue().equals(buffChapt))) {
                                        try {
                                            dbHandler.updateCategoryChapt
                                                    (dbHandler.takeCatWithCatId(buffCat),
                                                            choiseChapterEditCat.getValue());
                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    TreeItem<String> test1 = treeMenu.getSelectionModel().getSelectedItem();
                                    if (test1 != null) {
                                        TreeItem<String> test2 = test1.getParent();
                                        if (test2 != null) {
                                            test2.getChildren().removeAll(test1);
                                            for (int i = 0; i < treeMenu.getRoot().getChildren().size(); i++) {
                                                if (treeMenu.getRoot().getChildren().get(i).getValue().equals(
                                                        choiseChapterEditCat.getValue())) {
                                                    treeMenu.getRoot().getChildren().get(i).getChildren().add(test1);
                                                }
                                            }
                                        }
                                    }
                                }
                                system_message(statusBar, "Изменения внесены успешно", Color.GREEN);
                                dialogPaneEditCat.setVisible(false);
                            }
                        });
                        dialogPaneEditCat.lookupButton(ButtonType.CLOSE).setOnMouseClicked(mouseEvent1 -> {
                            if (mouseEvent1.getButton().equals(MouseButton.PRIMARY)) {
                                dialogPaneEditCat.setVisible(false);
                                choiseChapterEditCat.setVisible(true);
                                labelForChoiseEditCat.setVisible(true);
                                labelMainForEditCat.setText("Изменить категорию");
                                labelForEditCat.setText("Введите название категории: ");

                            }
                        });

                    }
                });

//Контекстное меню -> Удалить
                contextDelCat.setOnAction(event -> {
                    if (treeMenu.getSelectionModel().getSelectedItem().getValue().equals("Main") ||
                            treeMenu.getSelectionModel().getSelectedItem().getValue().equals("Без категории")) {
                        system_message(statusBar, "Не стоит этого делать", Color.RED);
                    } else {

                        if (treeMenu.getSelectionModel().getSelectedItem().getParent().getValue().equals("root")) {
                            labelConfirmWindow.setText("Удаление раздела");
                            textConfirmWindow.setText("Уверены, что хотите удалить выбранный раздел? " +
                                    "Это приведет к удалению всех записей, которые относятся к данному разделу.");
                        }
                        confirmWindow.setVisible(true);
                        confirmWindow.lookupButton(ButtonType.YES).setOnMouseClicked(mouseEvent1 -> {
                            String selection = treeMenu.getSelectionModel().getSelectedItem().getValue();
                            if (mouseEvent1.getButton().equals(MouseButton.PRIMARY)) {
                                if (treeMenu.getSelectionModel().getSelectedItem().getParent().getValue().equals(
                                        "root")) {
                                    try {
                                        dbHandler.deleteChaptFromDB(
                                                selection);
                                        confirmWindow.setVisible(false);
                                        treeMenu.getRoot().getChildren().remove((
                                                treeMenu.getSelectionModel().getSelectedItem()));
                                        choiseChapterAddCat.getItems().remove(selection);
                                        choiseChapterEditCat.getItems().remove(selection);
                                        labelConfirmWindow.setText("Удаление категории");
                                        textConfirmWindow.setText("Уверены, что хотите удалить выбранную категорию? " +
                                                "Это приведет к удалению всех записей, которые относятся" +
                                                " к данной категории.");

                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    try {
                                        dbHandler.deleteCatFromDB(
                                                selection);
                                        confirmWindow.setVisible(false);
                                        treeMenu.getSelectionModel().getSelectedItem().getParent().getChildren().remove(
                                                treeMenu.getSelectionModel().getSelectedItem());
                                        categoryChoiseBox.getItems().remove(
                                                selection);

                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                }
                                system_message(statusBar, "Успешно удалено", Color.GREEN);
                            }
                        });
                        confirmWindow.lookupButton(ButtonType.NO).addEventFilter(ActionEvent.ACTION, event1 -> {
                            confirmWindow.setVisible(false);
                            labelConfirmWindow.setText("Удаление категории");
                            textConfirmWindow.setText("Уверены, что хотите удалить выбранную категорию? " +
                                    "Это приведет к удалению всех записей, которые относятся" +
                                    " к данной категории.");

                        });
                    }
                });
            }
        });
//Снятие выделения при клике на пустой области
        tableMain.addEventFilter(MouseEvent.MOUSE_CLICKED, evt -> {
            Node source = evt.getPickResult().getIntersectedNode();


            // move up through the node hierarchy until a TableRow or scene root is found
            while (source != null && !(source instanceof TableRow)) {
                source = source.getParent();
            }

            // clear selection on click anywhere but on a filled row
            if (source == null || (source instanceof TableRow && ((TableRow) source).isEmpty())) {
                tableMain.getSelectionModel().clearSelection();
            }
        });

        try {
            choiseEditTable.getItems().addAll(dbHandler.takeCatNameForChoise());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
//Смена цвета текста строки при переходе
        tableMain.setRowFactory(tv -> new TableRow<>() {
            @Override
            public void updateItem(TableBody item, boolean empty) {
                setStyle("");
                super.updateItem(item, empty);
                if (item == null) {
                    setStyle("");
                } else {
                    if (item.getisVisited()) {
                        setStyle("-fx-text-background-color: #808080BB");
                    }
                }
            }
        });

        tableMain.setStyle("-fx-selection-bar: transparent;");
        tableMain.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY & (
                    tableMain.getSelectionModel().getSelectedItem() != null)) {
                if (mouseEvent.getClickCount() == 2) {
                    Application app = new Application() {
                        @Override
                        public void start(Stage stage) throws Exception {
                        }
                    };
                    app.getHostServices().showDocument(tableMain.getSelectionModel().getSelectedItem().getSomeURL());
                    system_message(statusBar, "Выполняется переход...", Color.BLACK);
                    if (tableMain.getSelectionModel().getSelectedItem().getisVisited() == false) {
                        try {
                            dbHandler.updateIsVisited(tableMain.getSelectionModel().getSelectedItem().getUrlId());
                            tableMain.getSelectionModel().getSelectedItem().setIsVisited(true);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }

//Изменение и удаление tableMain
            } else if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                contextEditURL.setOnAction(event -> {
                    if (tableMain.getSelectionModel().getSelectedItem() != null) {
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
                        dialogPaneEditTable.lookupButton(ButtonType.OK).setOnMouseClicked(mouseEvent1 -> {
                            if (mouseEvent1.getButton().equals(MouseButton.PRIMARY)) {
                                if (!(textEditTableUrl.getText().equals(buffUrl))) {
                                    try {
                                        dbHandler.updateTableUrl(urlId, textEditTableUrl.getText());
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                }
                                if (!(textEditTableDesc.getText().equals(buffDesc))) {
                                    try {
                                        dbHandler.updateTableDescription(urlId, textEditTableDesc.getText());
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                }
                                try {
                                    if (!(dbHandler.takeCatNameWithCatId(catId).equals(choiseEditTable.getValue()))) {
                                        dbHandler.updateTableCat(
                                                urlId, dbHandler.takeCatWithCatId(choiseEditTable.getValue()));
                                    }
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }

                                ObservableList<TableBody> obsURLList
                                        = FXCollections.observableArrayList(dbHandler.URLListView(
                                        treeMenu.getSelectionModel().getSelectedItem().getValue()));
                                tableMain.setItems(obsURLList);
                                system_message(statusBar, "Изменения внесены успешно", Color.GREEN);
                                dialogPaneEditTable.setVisible(false);
                            }
                        });
                        dialogPaneEditTable.lookupButton(ButtonType.CLOSE).setOnMouseClicked(mouseEvent1 -> {
                            if (mouseEvent1.getButton().equals(MouseButton.PRIMARY)) {
                                dialogPaneEditTable.setVisible(false);
                            }
                        });
                    }
                });
                contextDelURL.setOnAction(actionEvent -> {
                    if (tableMain.getSelectionModel().getSelectedItem() != null) {
                        try {
                            dbHandler.deleteStringFromDB(
                                    tableMain.getSelectionModel().getSelectedItem().getUrlId());
                            tableMain.getItems().remove(tableMain.getSelectionModel().getSelectedItem());
                        } catch (SQLException e) {
                            system_message(statusBar, "Удаление не удалось", Color.RED);
                            e.printStackTrace();
                        }

                    }
                });
            }
        });
    }

    public void createChoiseForAdd() throws SQLException, ClassNotFoundException {
        DataHandler dbHandler = new DataHandler();
        categoryChoiseBox.getItems().addAll(dbHandler.takeCatNameForChoise());
        categoryChoiseBox.setValue("Без категории");
    }

    //Ветка дерева
    public TreeItem<String> makeBranch(String title, TreeItem<String> parent) {
        TreeItem<String> item = new TreeItem<>(title);
        item.setExpanded(true);
        parent.getChildren().add(item);
        return item;
    }


    public void makeTree(List<TreeItem> listTreeItem) throws SQLException, ClassNotFoundException {
        DataHandler dbHandler = new DataHandler();
        ArrayList<String> newArr;
        TreeItem<String> root = new TreeItem<>("root");
        root.setExpanded(true);
        for (int i = 0; i < dbHandler.takeChaptNameForChoise().size(); i++) {
            root.getChildren().add(listTreeItem.get(i));
        }
        for (int i = 0; i < dbHandler.takeChaptNameForChoise().size(); i++) {
            newArr = dbHandler.takeCatArrayForTree(dbHandler.takeChaptNameForChoise().get(i));
            Collections.sort(newArr);
            for (int j = 0; j < dbHandler.takeCatArrayForTree(dbHandler.takeChaptNameForChoise().get(i)).size(); j++) {
                makeBranch(
                        dbHandler.takeCatArrayForTree(
                                dbHandler.takeChaptNameForChoise().get(i)).get(j), listTreeItem.get(i));
            }
        }
        treeMenu.setRoot(root);
        treeMenu.setShowRoot(false);

    }

    public static void system_message(Label label, String what, Color color) {

        label.setText(what);
        label.setTextFill(color);
        Thread system_message_thread = new Thread(new Runnable() {

            public void run() {

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {

                }
                Platform.runLater(new Runnable() {
                    public void run() {
                        label.setText("");
                        //label.setStyle();

                    }
                });
            }
        });
        system_message_thread.start();
    }
}
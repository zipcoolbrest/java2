package ru.jchat.core.client;


import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    final String SERVER_IP = "localhost";
    final int SERVER_PORT = 61189;

    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;
    private boolean authorised;
    private ObservableList<String> clientList;

    private String myNick;

    @FXML
    TextArea textArea;
    @FXML
    TextField textField;
    @FXML
    TextField loginField;
    @FXML
    PasswordField passField;
    @FXML
    HBox authPanel;
    @FXML
    HBox msgPanel;
    @FXML
    ListView<String> clientsListView;


    //начальный вид окна и вид окна после авторизации
    private void setAuthorised(boolean authorised) {
        this.authorised = authorised;
        if (authorised) {
            msgPanel.setVisible(true);
            msgPanel.setManaged(true);
            authPanel.setVisible(false);
            authPanel.setManaged(false);
            clientsListView.setVisible(true);
            clientsListView.setManaged(true);
        } else {
            msgPanel.setVisible(false);
            msgPanel.setManaged(false);
            authPanel.setVisible(true);
            authPanel.setManaged(true);
            clientsListView.setVisible(false);
            clientsListView.setManaged(false);
            myNick = "";
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setAuthorised(false);//начальная авторизация пользователя
    }

    public void connect(){
        try {
            socket = new Socket(SERVER_IP, SERVER_PORT);
            in = new DataInputStream(socket.getInputStream()); //входящий поток
            out = new DataOutputStream(socket.getOutputStream()); //исходящий поток
            clientList = FXCollections.observableArrayList(); // создаем список клиентов в ваде массива
            clientsListView.setItems(clientList); //добавляем в контейнер ListView список клиентов

            //выделяем ник авторизованного пользователя. обращаемся к конструктору ячеек
            clientsListView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
                @Override
                public ListCell<String> call(ListView<String> param) {
                    return new ListCell<String>(){
                        @Override
                        protected void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);
                            if (!empty) {
                                setText(item);
                                if (item.equals(myNick)){
                                    setStyle("-fx-font-weight: bold;");
                                }
                            } else {
                                setGraphic(null);
                            }
                        }
                    };
                }
            });


            Thread chatStream = new Thread(() -> {
                try {
                    //проверяем авторизацию или выводим сообщения об ошибках авторизации
                    while (true) {
                        String s = in.readUTF();
                        if (s.startsWith("/")) { //если входящий текст начинается с / проверяем команду по списку
                            String[] data = s.split("\\s"); //в строке может быть больше одного слова поэтому делим его на слова по пробелу
                            switch (data[0]) {
                                case "/authok":
                                    setAuthorised(true);
                                    myNick = data[1];
                                    textArea.appendText("Вы успешно авторизовались\n");
                                    break;
                                case "/authbad":
                                    showAlert("Неверный логин или пароль");
                                    textArea.appendText("Введен неверный логин или пароль\n");
                                    break;
                                case "/busynick":
                                    showAlert("Логин уже занят");
                                    textArea.appendText("Логин уже занят\n");
                                    break;
                                case "/clientslist": //заполняем наш контейнер ListView списком пользователей
                                    //выполняем все это в основном потоке

                                    Platform.runLater(() -> {
                                        clientList.clear();
                                        for (int i = 1; i < data.length; i++) {
                                            clientList.addAll(data[i]);
                                            textArea.appendText(data[i] + " ");
                                        }
                                        textArea.appendText("\n");
                                    });
                                    break;
                            }
                        }else {
                            //выводим сообщения пользователей на экран
                            textArea.appendText(s + "\n");
                        }
                    }
                } catch (IOException e) {
                    showAlert("Соединение с сервером было потеряно.\n" +
                            "Если это сделали не Вы, то обратитесь к системному администратору.");
                } finally {
                    setAuthorised(false); //у нас отключилась сетевая или что-то случилось со связью
                    //в любом случае отключаем пользователя.
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.getStackTrace();
                    }
                }
            });

            chatStream.setDaemon(true);
            chatStream.start();

        } catch (IOException e) {
            showAlert("Не удалось подключиться к серверу. \nВозможно, сервер еще не зпущен. " +
                    "\nВозможно, нет соединения с сетью или порт 61189 закрыт.");
        }
    }

    //посылаем на сервак данные авторизации
    public void sendAuthMsg() {
        //если поле логин или пароль пустые
        if (loginField.getText().isEmpty() || passField.getText().isEmpty()) {
            showAlert("Поле логина или пароля осталось пустым");
            return;
        }

        //если мы только что открыли окно или не было сети - создаем новое подключение
        if(socket == null || socket.isClosed()){
            connect();
        }

        // /auth login pass
        try {
            out.writeUTF("/auth " + loginField.getText() + " " + passField.getText());
            loginField.clear();
            passField.clear();
        } catch (IOException e) {
            showAlert("Не удалось подключиться к серверу. \nВозможно, сервер еще не зпущен. " +
                    "\nВозможно, нет соединения с сетью или порт 61189 закрыт.");
        }
    }

    //посылаем на сервак сообщение пользователя
    public void sendMessage() {
        try {
            out.writeUTF(textField.getText());
            textField.clear();
            textField.requestFocus();
        } catch (IOException e) {
            showAlert("Сообщение не доставлено.\nНе удалось подключиться к серверу. " +
                    "\nВозможно, сервер еще не зпущен.\nВозможно, нет соединения с сетью или порт 61189 закрыт.");
        }
    }
    //всплывающее окно с сообщениями разного характера
    public void showAlert(String msg) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Что-то не так");
            alert.setHeaderText(null);
            alert.setContentText(msg);
            alert.showAndWait();
        });
    }

    public void clientListClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2){
            textField.setText("/w " + clientsListView.getSelectionModel().getSelectedItem() + " ");
            textField.requestFocus();
            textField.selectEnd();
        }
    }
}

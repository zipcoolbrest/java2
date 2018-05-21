package ru.jchat.core.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Vector;

public class Server {

    private Vector<ClientHandler> clients;
    private AuthHandler authHandler = null;

    Server() {
        try(ServerSocket serverSocket = new ServerSocket(61189)){
            clients = new Vector<>();
            authHandler = new AuthHandler();
            authHandler.connect();
            System.out.println("server started...");
            System.out.println("waiting clients...");

            while (true){
                Socket socket = serverSocket.accept();
                System.out.println("clients connected! " + socket.getInetAddress() + " " + socket.getPort() + " " + socket.getLocalPort());
                new ClientHandler(this,socket);
            }
        }catch (IOException e){
            e.printStackTrace();
        }catch (ClassNotFoundException | SQLException e){
            System.out.println("Не удальсь запустить сервис авторизации");
        }finally {
            authHandler.disconnect();
        }
    }

    public AuthHandler getAuthHandler() {
        return authHandler;
    }

    public void subscribe(ClientHandler clientHandler){
        clients.add(clientHandler);
        broadcastClientList();
    }

    public void unsubscribe(ClientHandler clientHandler){
        clients.remove(clientHandler);
        broadcastClientList();
    }

    public boolean isNickBusy(String nick){
        for (ClientHandler o: clients) {
            if (o.getNick().equals(nick)) {
                return true;
            }
        }
        return false;
    }

    public void sendPrivateMsg(ClientHandler from, String nickTo, String msg){
        for (ClientHandler o : clients) {
            if( o.getNick().equals(nickTo)){
                o.sendMsg("Лично от " + from.getNick() + ": " + msg);
                from.sendMsg("Лично для " + nickTo + ": " + msg);
                return;
            }
        }
        from.sendMsg("Пользователя с ником " + nickTo + " не найден.");
    }

    public void broadcastClientList(){
        //для того, чтобы не создавать копии списка пользователей используем билдер
        StringBuilder sb = new StringBuilder("/clientslist ");
        for (ClientHandler o: clients) {
            sb.append(o.getNick() + " ");
        }
        String out = sb.toString();
        for (ClientHandler o: clients) {
            o.sendMsg(out);
        }
    }

    public void broadcastMsg(String msg){
        for (ClientHandler o: clients) {
            o.sendMsg(msg);
        }
    }
}

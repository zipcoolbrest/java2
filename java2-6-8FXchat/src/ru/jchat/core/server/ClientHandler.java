package ru.jchat.core.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {

    private Server server;
    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;
    private String nick = "";

    public ClientHandler(Server server, Socket socket) {
        try {
            this.server = server;
            this.socket = socket;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
            new Thread(() -> {
                try {
                    //авторизация пользователя
                    while (true) {
                        String msg = in.readUTF();
                        if (msg.startsWith("/auth ")) {
                            String[] data = msg.split("\\s");
                            //sql injection check: /auth login pass
                            if (data.length == 3) {
                                String newnick = server.getAuthHandler().getNickByLoginAndPass(data[1], data[2]);
                                if (newnick != null) {
                                    if (!server.isNickBusy(newnick)) {
                                        nick = newnick;
                                        sendMsg("/authok " + newnick);
                                        server.subscribe(this);
                                        break;
                                    } else sendMsg("/busynick ");
                                } else {
                                    sendMsg("/authbad ");
                                }
                            }
                        }
                    }
                    //отправка сообщений пользователям
                    while (true) {
                        String msg = in.readUTF();
                        System.out.println(nick + ": " + msg);
                        if(msg.startsWith("/")){
                            if (msg.equals("/end")) {
                                sendMsg("Вы вышли из системы");
                                break;
                            }
                            if (msg.startsWith("/w ")){
                                // /w nickTo msg => (limit 3)
                                String[] data = msg.split("\\s", 3);
                                server.sendPrivateMsg(this, data[1],data[2]);
                            }
                        }else {
                            server.broadcastMsg(nick + ": " + msg);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    nick = null;
                    server.unsubscribe(this);
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void sendMsg(String msg){
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getNick() {
        return nick;
    }
}

package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Connected extends Thread {

    public Socket client;
    public Server server;
    private String nickName;
    private DataOutputStream dos;
    private DataInputStream dis;
    private boolean run;

    public Connected(Server server, Socket client) {
        try {
            this.server = server;
            this.client = client;
            dos = new DataOutputStream(client.getOutputStream());
            dis = new DataInputStream(client.getInputStream());
            run = true;
            this.start();
        } catch (IOException ex) {
            Logger.getLogger(Connected.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void run() {
        // xữ lý đăng nhập
        String msg = null;
        while (run) {
            nickName = getMSG();
            if (nickName.compareTo("0") == 0) {
                logout();
            } else if (checkNick(nickName)) {
                sendMSG("0");
            } else {
                server.setStatus(nickName + " joined\n");
                server.sendAll(nickName, nickName + " joined\n");
                server.listUser.put(nickName, this);
                server.sendAllUpdate(nickName);
                sendMSG("1");
                diplayAllUser();
                while (run) {
                    int stt = Integer.parseInt(getMSG());
                    switch (stt) {
                        case 0:
                            run = false;
                            server.listUser.remove(this.nickName);
                            exit();
                            break;
                        case 1:
                            msg = getMSG();
                            server.sendAll(nickName, nickName + " : " + msg);
                            break;
                    }
                }
            }
        }
    }

    private void logout() {
        try {
            dos.close();
            dis.close();
            client.close();
        } catch (IOException ex) {
            Logger.getLogger(Connected.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void exit() {
        try {
            server.sendAllUpdate(nickName);
            dos.close();
            dis.close();
            client.close();
            server.setStatus(nickName + " out\n");
            server.sendAll(nickName, nickName + " out\n");
        } catch (IOException ex) {
            Logger.getLogger(Connected.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean checkNick(String nick) {
        return server.listUser.containsKey(nick);
    }

    private void sendMSG(String data) {

        try {
            dos.writeUTF(data);
            dos.flush();
        } catch (IOException ex) {
            Logger.getLogger(Connected.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendMSG(String msg1, String msg2) {
        sendMSG(msg1);
        sendMSG(msg2);
    }

    private String getMSG() {
        String data = null;
        try {
            data = dis.readUTF();
        } catch (IOException ex) {
            Logger.getLogger(Connected.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }

    private void diplayAllUser() {
        String name = server.getAllName();
        sendMSG("4");
        sendMSG(name);
    }

}

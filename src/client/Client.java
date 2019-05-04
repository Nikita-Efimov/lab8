import java.net.*;
import java.io.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import org.w3c.dom.*;
import org.json.*;
import java.util.PriorityQueue;
import java.util.stream.Collectors;
import org.xml.sax.InputSource;

class ClientInteraction {
    private static Socket socket;
    private static BufferedReader in; // поток чтения из сокета
    private static BufferedWriter out; // поток чтения в сокет
    private static String login;
    private static String password;
    private static volatile boolean status;
    private static Gui gui;

    public ClientInteraction(String addr, int port) {
        status = true;

        try {
            socket = new Socket(addr, port);
        } catch (IOException e) {}

        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            System.out.println("Успешное подключение");
            login = null;
            password = null;
            new ReadMsg().start(); // нить читающая сообщения из сокета в бесконечном цикле
            gui = new Gui();
        } catch (IOException e) {
            ClientInteraction.downService();
        }
    }

    private static void downService() {
        try {
            if (!ClientInteraction.status) return;
            System.err.println("Сервер недоступен");
            ClientInteraction.status = false;
            if (!socket.isClosed()) {
                socket.close();
                in.close();
                out.close();
                System.exit(0);
            }
        } catch (IOException ignored) {}
    }

    // нить чтения сообщений с сервера
    private class ReadMsg extends Thread {
        @Override
        public void run() {
            String str;
            try {
                while (status) {
                    str = in.readLine(); // ждем сообщения с сервера
                    if (str.equals(null)) break;
                    if (str.trim().equals("")) continue;

                    gui.getTextArea().print("-> " + str);
                    // System.out.println("-> " + str); // пишем сообщение с сервера на консоль
                }
            } catch (Exception e) {
                ClientInteraction.downService();
            }
        }
    }

    public static void send(String str) {
        try {
            out.write(str + ' ' + password + ' ' + login + '\n');
            out.flush(); // чистим (вилкой)
        } catch (IOException e) {
            ClientInteraction.downService();
        }
    }

    public static void setLoginAndPassword(String login, String password) {
        ClientInteraction.login = login;
        ClientInteraction.password = SHA1.encrypt(password);
    }
}

public class Client {
    public static String ipAddr = null;
    public static int port = 8080;

    public static void main(String[] args) {
        try {
            new ClientInteraction(ipAddr, port);
        } catch (Exception e) {
            System.out.println("Сервер недоступен");
        }
    }
}

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
    private Socket socket;
    private BufferedReader in; // поток чтения из сокета
    private BufferedWriter out; // поток чтения в сокет
    private BufferedReader inputUser; // поток чтения с консоли
    private String addr; // ip адрес клиента
    private int port; // порт соединения
    private String login;
    private String password;
    private volatile boolean status;

    public ClientInteraction(String addr, int port) {
        this.addr = addr;
        this.port = port;
        this.status = true;

        try {
            this.socket = new Socket(addr, port);
        } catch (IOException e) {}

        try {
            // потоки чтения из сокета / записи в сокет, и чтения с консоли
            inputUser = new BufferedReader(new InputStreamReader(System.in));
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            System.out.println("Успешное подключение");
            login = null;
            password = null;
            new ReadMsg().start(); // нить читающая сообщения из сокета в бесконечном цикле
            new WriteMsg().start(); // нить пишущая сообщения в сокет приходящие с консоли в бесконечном цикле
        } catch (IOException e) {
            ClientInteraction.this.downService();
        }
    }

    private void downService() {
        try {
            if (!this.status) return;
            System.err.println("Сервер недоступен");
            this.status = false;
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
                    System.out.println("-> " + str); // пишем сообщение с сервера на консоль
                }
            } catch (Exception e) {
                ClientInteraction.this.downService();
            }
        }
    }

    // нить обрабатывающая сообщения приходящие с консоли
    private class WriteMsg extends Thread {
        @Override
        public void run() {
            while (status) {
                String str;
                try {
                    str = inputUser.readLine(); // сообщения с консоли

                    final String firstPart = str.split(" ")[0].trim();
                    if (firstPart.equals("import")) {
                        if (str.split(" ").length != 2) {
                            System.out.println("uncorrect syntax, correct syntax import <filename>");
                            continue;
                        }
                        load(str.split(" ")[1].trim());
                    } else if (firstPart.equals("auth")) {
                        if (str.split(" ").length != 3) {
                            System.out.println("uncorrect syntax, correct syntax auth <login> <password>");
                            continue;
                        }
                        login = str.split(" ")[1].trim();
                        password = str.split(" ")[2].trim();
                        password = SHA1.encrypt(password);
                    } else {
                        // Send cmd to server
                        out.write(str + ' ' + password + ' ' + login  + '\n');
                        out.flush(); // чистим
                    }
                } catch (IOException e) {
                    ClientInteraction.this.downService();
                }
            }
        }
    }

    private void load(final String filename) {
        PriorityQueue<City> priorityQueue = new PriorityQueue<>();
        try {
            FileReader reader = new FileReader(filename);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new InputSource(reader));
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("city");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element)nNode;
                    priorityQueue.add(
                    new City(eElement.getElementsByTagName("name").item(0).getTextContent(),
                             Integer.parseInt(eElement.getElementsByTagName("size").item(0).getTextContent()),
                             Integer.parseInt(eElement.getElementsByTagName("x").item(0).getTextContent()),
                             Integer.parseInt(eElement.getElementsByTagName("y").item(0).getTextContent())));
                }
            }

            String str = "";
            final String PREAMBLE = "#####";
            try {
                str = Convertr.convertToByteString(priorityQueue);
            } catch (IOException e) {}

            try {
                out.write(PREAMBLE + str + '\n');
                out.flush();
            } catch (IOException e) {
                ClientInteraction.this.downService(); // в случае исключения тоже харакири
            }
        } catch (java.io.FileNotFoundException e) {
            System.out.println("file not found");
        } catch (Exception e) {
            System.out.println("error while parsing");
        }
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

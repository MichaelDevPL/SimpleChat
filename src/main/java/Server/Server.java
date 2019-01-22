package Server;

import Server.databaseUtil.DBUtil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Server {

    private static List<Client> clients;
    private static DataOutputStream dos;
    private DataInputStream dis;

    public Server() {

        System.out.println("Server ON");

        String name;
        Socket client;

        clients = new ArrayList<Client>();

        try {
            ServerSocket servSock = new ServerSocket(5555);

            while(true) {
                client = servSock.accept();
                dis = new DataInputStream(client.getInputStream());
                dos = new DataOutputStream(client.getOutputStream());

                name = dis.readUTF() ;
                Client user = new Client(name, dos, dis);
                System.out.println("Connected : " + name);
                clients.add(user);

                String sqlQuary = "INSERT INTO login_history (nick_name, address) VALUES ('"+ name +"', '"+ client.getRemoteSocketAddress().toString() +"')";

                try {
                    DBUtil.dbExcecuteQuery(sqlQuary);
                } catch (ClassNotFoundException | SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                String enter_message = "{ \"name\" : \"" + "[ SERVER NOTICE ]" + "\", \"message\" : \""+  name +" Connected" + "\"}";
                System.out.println(enter_message);
                List<Client> entry = Server.clients;
                for (Client cli : entry) {
                    DataOutputStream edos = cli.getDos();
                    edos.writeUTF(enter_message);
                }

                System.out.println("[Current User : " + Server.clients.size() + "]");

            }
        } catch(IOException E) {
            E.printStackTrace();
        }
    }

    public static List<Client> getClients() {
        return clients;
    }

    public static void setClients(List<Client> clients) {
        Server.clients = clients;
    }

    public static DataOutputStream getDos() {
        return dos;
    }

    public DataInputStream getDis() {
        return dis;
    }
}

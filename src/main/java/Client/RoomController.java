package Client;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class RoomController implements Runnable {

    @FXML
    private TextField myMsg;

    @FXML
    private TextArea chatLog;

    private Socket sock;
    private DataOutputStream dos;
    private DataInputStream dis;

    private ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newScheduledThreadPool(1);

    public RoomController() {
        try {
            sock = new Socket(ClientData.getIp(), ClientData.getPort());
            dos = new DataOutputStream(sock.getOutputStream());
            dis = new DataInputStream(sock.getInputStream());

            dos.writeUTF(ClientData.getName());

            executor.execute(this::run);
        } catch (IOException E) {
            E.printStackTrace();
        }
    }

    public void onClickSend() {
        try {
            String msg = myMsg.getText();

            //String json = "{" + " 'name' : '" + ClientData.name + "', 'message' : '" + msg + "'" + "}";

            JSONObject js = new JSONObject();
            js.put("name", ClientData.getName());
            js.put("message", msg);

            String json = js.toJSONString();


            System.out.println(json);

            dos.writeUTF(json);
            myMsg.setText("");
            myMsg.requestFocus();

        } catch (IOException E) {
            E.printStackTrace();
        }

    }

    public void buttonPressed(KeyEvent e) {
        if (e.getCode().toString().equals("ENTER")) {
            onClickSend();
        }
    }

    @Override
    public void run() {
        try {
            JSONParser parser = new JSONParser();

            while (true) {
                String newMsgJson = dis.readUTF();

                System.out.println("RE : " + newMsgJson);
                Message newMsg = new Message();

                Object obj = parser.parse(newMsgJson);
                JSONObject msg = (JSONObject) obj;

                newMsg.setName((String) msg.get("name"));
                newMsg.setMessage((String) msg.get("message"));

                chatLog.appendText(newMsg.getName() + " : " + newMsg.getMessage() + "\n");
            }
        } catch (Exception E) {
            E.printStackTrace();
        }
    }
}



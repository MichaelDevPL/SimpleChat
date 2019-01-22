package Client;

public class ClientData {

    private static String ip;
    private static String name;
    private static int port;

    public static String getIp() {
        return ip;
    }

    public static String getName() {
        return name;
    }

    public static int getPort() {
        return port;
    }

    public static void setIp(String ip) {
        ClientData.ip = ip;
    }

    public static void setName(String name) {
        ClientData.name = name;
    }

    public static void setPort(int port) {
        ClientData.port = port;
    }
}

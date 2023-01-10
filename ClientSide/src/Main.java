import java.io.IOException;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException {
        //pop-up with login
        //have gui with one item and bid
        //button for send big higher
        //button for send bid lower
        //read from bufferedReader constantly and update labels when new info comes in
        Socket socket = new Socket("localhost", 1234);
        Client client = new Client(socket, "rbd trades");
        client.sendMessage();
        client.listenForMessage();

    }
}
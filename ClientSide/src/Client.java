import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private String username;
    private Packet packet = new Packet();

    public Client(Socket socket, String username){
        try{
            /*
            * 1. Initialize socket and streams
            * 2. Send Packet With Client Username To Server
            * 3. Receive Packet With Current Bids
            * 4. Start GUI
            * */
            this.socket = socket;
            this.objectInputStream = new ObjectInputStream(socket.getInputStream());
            this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

            this.username = username;
            packet.username = this.username;
            objectOutputStream.writeObject(packet);

            packet = (Packet)objectInputStream.readObject();

        } catch(IOException e){
            closeEverything(socket, objectOutputStream, objectInputStream);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMessage() throws IOException {
        objectOutputStream.writeObject(packet);
    }

    public void listenForMessage(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(socket.isConnected()){
                    try{
                        /*
                        * 1. Receive Packet
                        * 2. Update GUI
                        * */
                        packet = (Packet)objectInputStream.readObject();

                    }catch(IOException e){
                        closeEverything(socket, objectOutputStream, objectInputStream);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }).start();
    }


    public void closeEverything(Socket socket, ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream){
        try{
            if(objectInputStream != null){ objectInputStream.close();}
            if(objectOutputStream != null){ objectOutputStream.close();}
            if(socket != null){ socket.close();}
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    //connect to server
    //input username
    //send username through socket
    //send bids

}

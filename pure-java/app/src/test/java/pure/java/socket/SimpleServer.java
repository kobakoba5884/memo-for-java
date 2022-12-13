package pure.java.socket;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleServer {
    public static void main(String[] args){
        try(ServerSocket serverSocket = new ServerSocket(1600);
            Socket socket = serverSocket.accept();
            InputStream inputStream = socket.getInputStream()){
            System.out.println("waiting");
            System.out.printf("connected from %s\n", socket.getInetAddress());
            System.out.println(inputStream.read());
        }catch(IOException e){
            System.err.println(e.getMessage());
        }

        
    }  
}

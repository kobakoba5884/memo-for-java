package pure.java.socket;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class SimpleClient {
    public static void main(String[] args) {
        try(Socket socket = new Socket("localhost", 1600);
            OutputStream outputStream = socket.getOutputStream()){
            outputStream.write(123);
        }catch(IOException e){
            System.err.println(e.getMessage());
        }

    }
}

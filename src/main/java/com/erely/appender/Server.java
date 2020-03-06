package com.erely.appender;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(9898);

        while(true){
            Socket socket = server.accept();
            InputStream in = socket.getInputStream();
            byte[] len = new byte[4];
            in.read(len);
            int length = Integer.parseInt(new String(len));
            byte[] msgByte = new byte[length];
            in.read(msgByte);
            System.out.println(new String(msgByte));
            in.close();
            socket.close();
         }
    }
}

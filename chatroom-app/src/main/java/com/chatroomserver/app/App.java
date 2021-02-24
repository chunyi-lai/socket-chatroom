package com.chatroomserver.app;

import java.lang.*;
import java.net.*;
import java.io.*;

/**
    Server application of the chat room
 */
public class App
{
    public static void main( String[] args ) throws Exception
    {
        RequestHandler rh1 = new RequestHandler("Command");
        rh1.insertDB();

        ServerSocket appSocket = new ServerSocket(4999);
        Socket socket1 = appSocket.accept();

        System.out.println("client connected");

        InputStreamReader in = new InputStreamReader(socket1.getInputStream());
        BufferedReader bf = new BufferedReader(in);

        String message = bf.readLine();
        System.out.println("Client: " + message);

        System.exit(0);
    }
}

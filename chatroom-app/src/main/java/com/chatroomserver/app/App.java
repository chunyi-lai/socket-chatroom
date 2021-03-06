package com.chatroomserver.app;

import java.lang.*;
import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.*;

/**
    Server application of the chat room
 */
public class App extends Thread
{
    DatabaseHandler databaseHandler;

    // Database credentials
    String url = "jdbc:mysql://localhost:3306/chatroom_db";
    String username = "vagrant";
    String password = "Vagrantadmin123!";

    ServerSocket appSocket;
    Socket socket;

    public String getSocketClientMessage() throws IOException{
        try{
            // Establish socket connection
            this.appSocket = new ServerSocket(4999);
            this.socket = this.appSocket.accept();

            // Establish the buffer reader
            InputStreamReader in = new InputStreamReader(this.socket.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(in);

            //Extract the message from the buffer reader
            String userInput = "";
            userInput = bufferedReader.readLine();
            System.out.println("User Input: " + userInput);

            //Close input stream
//            in.close();
            return userInput;
        }
        catch(IOException e){
            System.out.println(e.getMessage());
            throw e;
        }
    }

    /**
     * Execute all corresponding user commands
     * @param userInput
     * @return
     * @throws SQLException
     */
    public String executeUserInput(String userInput) throws SQLException {
        String[] userInputParts = userInput.split(" ");
        String type = userInputParts[0].toLowerCase();
        String message, sentBy, roomName, createdBy;
        String responseMessage = "";
        try {
            switch (type) {
                case "message":
                    message = userInputParts[1].toLowerCase();
                    sentBy = userInputParts[2].toLowerCase();
                    roomName = userInputParts[3].toLowerCase();
                    this.databaseHandler.placeMessage(message, sentBy, roomName);
                    responseMessage = "Action Successful!";
                    break;
                case "createroom":
                    roomName = userInputParts[1].toLowerCase();
                    createdBy = userInputParts[2].toLowerCase();
                    this.databaseHandler.createChatRoom(roomName, createdBy);
                    responseMessage = "Action Successful!";
                    break;
                case "deleteroom":
                    roomName = userInputParts[1].toLowerCase();
                    this.databaseHandler.removeChatroom(roomName);
                    responseMessage = "Action Successful!";
                    break;
                case "listroom":
                    responseMessage = this.databaseHandler.getChatrooms().toString();
                    break;
                case "gotoroom":
                    roomName = userInputParts[1].toLowerCase();
                    responseMessage = this.databaseHandler.getMessagesFromRoom(roomName).toString();
                    break;
                default:
                    break;
            }
            System.out.println("Server Response: " + responseMessage);
            return responseMessage;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());

            return "Error encountered during action: " + e.getMessage();
        }
    }

    /**
     * sends client response to the client
     * @return true if the response is sent successfully
     * @throws IOException
     */
    public void sendResponseToClient(String serverResponse) throws IOException{
        try {
            ObjectOutputStream out = new ObjectOutputStream(this.socket.getOutputStream());
            out.writeObject(serverResponse);
        }
        catch(IOException e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    public void closeSocketConnections() throws IOException{
        try{
            this.appSocket.close();
            this.socket.close();
        }
        catch(IOException e){
            System.out.println("Error encountered while closing the socket interfaces");
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void run() {

        System.out.println("Chatroom Server Starting...");
        while(true) {
            try {
                // Create the database handler
                databaseHandler = new DatabaseHandler(url, username, password);
                databaseHandler.removeRoomOlderThan7Days();

                String socketClientMessage = this.getSocketClientMessage();
                String serverResponse = this.executeUserInput(socketClientMessage);

                sendResponseToClient(serverResponse);
                this.closeSocketConnections();
                databaseHandler.closeDBConnection();

            } catch (Exception e) {
                System.out.println("Error while creating the App");
                System.out.println(e.getMessage());
                System.exit(0);
            }
        }
    }

    public static void main( String[] args ) throws Exception {
        App app = new App();
        app.start();
    }
}

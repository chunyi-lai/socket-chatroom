package com.chatroomserver.app;

import java.sql.*;
import java.util.*;

public class DatabaseHandler {
    private Connection conn;

    // Database credentials
    private String url;
    private String user;
    private String password;

    public DatabaseHandler(String url, String user, String password) throws SQLException{
        this.url = url;
        this.user = user;
        this.password = password;
        this.conn = DriverManager.getConnection(url, user, password);
    }

    /**************************************
     * Chatroom related database requests *
     **************************************/

    /**
     *
     * @return true if database connection is established successfully
     * @throws SQLException
     */
    public boolean setupDBConnection() throws SQLException{
        try {
            this.conn = DriverManager.getConnection(url, user, password);
            return true;
        }
        catch (SQLException e) {
            throw e;
        }
    }

    /**
     *
     * @return true if the DBconnection is closed properly
     * @throws SQLException
     */
    public boolean closeDBConnection() throws SQLException{
        try {
            this.conn.close();
            return true;
        }
        catch (SQLException e) {
            throw e;
        }
    }

    /**
     *
     * @param roomName Name of the chatroom to removed
     * @param createdBy Username of the user that created the chatroom
     * @return
     * @throws SQLException
     */
    public boolean createChatRoom(String roomName, String createdBy) throws SQLException{
        try {
            this.conn.setAutoCommit(false);
            PreparedStatement queryStatement = this.conn
                    .prepareStatement("INSERT INTO chatroom_db.Rooms (room_name, created_by) "
                            + "VALUES (?, ?);");
            queryStatement.setString(1, roomName);
            queryStatement.setString(2, createdBy);
            queryStatement.executeUpdate();
            this.conn.commit(); //Commit the transaction
            queryStatement.close();
            return true;
        }
        catch (SQLException e) {
            //Transaction rollback in case of errors
            this.conn.rollback();
            throw e;
        }
    }

    /**
     *
     * @param roomName Name of the room to be removed
     * @return True if the chatroom is
     * @throws SQLException
     */
    public boolean removeChatroom(String roomName) throws SQLException {
        try {
            this.conn.setAutoCommit(false);
            // Remove all messages from the room that is to be deleted
            PreparedStatement deleteMessagesQuery = this.conn
                    .prepareStatement("DELETE FROM chatroom_db.Messages WHERE room_id in " +
                            "(SELECT room_id FROM chatroom_db.Rooms WHERE room_name=?);");
            deleteMessagesQuery.setString(1, roomName);
            deleteMessagesQuery.executeUpdate();

            //Remove the room based on the room's name
            PreparedStatement deleteRoomQuery = this.conn
                    .prepareStatement("DELETE FROM chatroom_db.Rooms WHERE room_name = ?;");
            deleteRoomQuery.setString(1, roomName);
            deleteRoomQuery.executeUpdate();

            //Commit the transaction
            this.conn.commit();

            deleteMessagesQuery.close();
            deleteRoomQuery.close();

            return true;
        }
        catch (SQLException e) {
            //Transaction rollback in case of errors
            this.conn.rollback();
            throw e;
        }
    }

    /**
     *
     * @return true if the chatrooms are
     * @throws SQLException
     */
    public boolean removeRoomOlderThan7Days() throws SQLException {
        try {
            this.conn.setAutoCommit(false);

            //Remove messages in that chat room
            PreparedStatement deleteMessagesQuery = this.conn
                    .prepareStatement("DELETE FROM chatroom_db.Messages WHERE chatroom_db.Messages.room_id IN " +
                            "(SELECT room_id FROM chatroom_db.Rooms WHERE date_created < now() - INTERVAL 7 DAY);");
            deleteMessagesQuery.executeUpdate();

            //Remove chatrooms that are older than 7 days
            PreparedStatement deleteRoomsQuery = this.conn
                    .prepareStatement("DELETE FROM chatroom_db.Rooms " +
                            "WHERE date_created < now() - INTERVAL 7 DAY;");
            deleteRoomsQuery.executeUpdate();

            //Commit the transaction
            this.conn.commit();

            deleteMessagesQuery.close();
            deleteRoomsQuery.close();

            return true;
        }
        catch(SQLException e) {
            this.conn.rollback();
            throw e;
        }
    }

    /**
     *
     * @return All chat rooms avaliable as an array list of room objects
     * @throws SQLException
     */
    public ArrayList<Room> getChatrooms() throws SQLException{
        try{
            this.conn.setAutoCommit(false);
            PreparedStatement roomQuery = this.conn
                    .prepareStatement("SELECT * FROM chatroom_db.Rooms;");
            ResultSet queryResult = roomQuery.executeQuery();

            ArrayList Rooms = new ArrayList<Room>();
            while(queryResult.next()) {
                Room room = new Room(
                        queryResult.getInt("room_id"),
                        queryResult.getString("room_name"),
                        queryResult.getString("created_by"),
                        queryResult.getTimestamp("date_created")
                );
                Rooms.add(room);
            }

            this.conn.commit();

            roomQuery.close();
            queryResult.close();

            return Rooms;
        }
        catch(Exception e){
            this.conn.rollback();
            throw e;
        }
    }

    /************************************
     * Message related database requests*
     ************************************/

    /**
     *
     * @param message Message to be stored in a database
     * @param sentBy Username of the user who sends the message
     * @param roomName The name of the chatroom that the message is sent to
     * @return True if the message is stored successfully, false otherwise
     * @throws SQLException
     */
    public boolean placeMessage(String message, String sentBy, String roomName) throws SQLException {
        try{
            this.conn.setAutoCommit(false);
            PreparedStatement insertMessage = this.conn
                    .prepareStatement("INSERT INTO chatroom_db.Messages (username, message_content, room_id) " +
                            "VALUES (?, ?, (SELECT room_id FROM chatroom_db.Rooms " +
                            "WHERE chatroom_db.Rooms.room_name = ? LIMIT 0, 1));");
            insertMessage.setString(1, sentBy);
            insertMessage.setString(2, message);
            insertMessage.setString(3, roomName);
            insertMessage.executeUpdate();

            this.conn.commit();
            insertMessage.close();

            return true;
        }
        catch(SQLException e) {
            this.conn.rollback();
            throw e;
        }
    }

    /**
     * Obtain all the message objects from a specific message room
     * @param roomName Name of the room that the message is in
     * @return List of all rooms in an ArrayList
     * @throws SQLException
     */
    public ArrayList<Message> getMessagesFromRoom(String roomName) throws SQLException {
        try{
            this.conn.setAutoCommit(false);
            PreparedStatement messagesQuery = this.conn
                    .prepareStatement("SELECT * FROM chatroom_db.Messages JOIN chatroom_db.Rooms " +
                            "ON chatroom_db.Messages.room_id = chatroom_db.Rooms.room_id " +
                            "WHERE chatroom_db.Rooms.room_name = ?;");
            messagesQuery.setString(1, roomName);
            ResultSet queryResult = messagesQuery.executeQuery();

            this.conn.commit();

            ArrayList messages = new ArrayList<Message>();
            while (queryResult.next()) {
                Message message = new Message(
                        queryResult.getInt("message_id"),
                        queryResult.getInt("room_id"),
                        queryResult.getString("username"),
                        queryResult.getString("message_content")
                );
                messages.add(message);
            }

            messagesQuery.close();
            queryResult.close();

            return messages;
        }
        catch(SQLException e){
            this.conn.rollback();
            throw e;
        }
    }

}

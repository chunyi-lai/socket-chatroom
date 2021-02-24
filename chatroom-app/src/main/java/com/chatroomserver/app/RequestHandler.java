package com.chatroomserver.app;

import java.sql.*;

public class RequestHandler
{
    private String requestType;

    public RequestHandler(String requestType)
    {
        this.requestType = requestType;
    }

    /**
     *
     * @param roomName
     * @return A boolean value indicating if the room is created successfully
     */
    public Integer createRoom(String roomName)
    {
        return 0;
    }

    public void insertDB() throws Exception {
//        Class.forName("com.mysql.jdbc.Driver");
        Connection dbConn = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/chatroom_db",
                        "vagrant", "Vagrantadmin123!");
//        Statement statement = dbConn.createStatement();
        PreparedStatement ps = dbConn
                .prepareStatement("INSERT INTO chatroom_db.Rooms (room_name, created_by) " +
                        "VALUES ('TEST ROOM', 'ADMIN')");
        ps.executeUpdate();
        ps.close();
        dbConn.close();
    }
}
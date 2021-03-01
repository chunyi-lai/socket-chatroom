package com.chatroomserver.app;

import java.sql.Timestamp;

/**
 * Class for the room object
 */
public class Room {

    private Integer roomId;
    private String roomName;
    private String createdBy;
    private Timestamp dateCreated;

    /**
     * Constructor
     */
    public Room(Integer roomId, String roomName, String createdBy, Timestamp dateCreated) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.createdBy = createdBy;
        this.dateCreated = dateCreated;
    }

    /**
     * Getter and setter methods
     */
    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    /**
     * Object information in string
     * @return string information
     */
    @Override
    public String toString() {
        return "Rooms{" +
                "roomId=" + roomId +
                ", roomName='" + roomName + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", dateCreated=" + dateCreated +
                '}';
    }
}

package com.chatroomserver.app;

public class Message {
    private Integer messageId;
    private Integer roomId;
    private Integer username;
    private Integer messageContent;

    /**
     * Constructor
     */
    public Message(Integer messageId, Integer roomId, Integer username, Integer messageContent) {
        this.messageId = messageId;
        this.roomId = roomId;
        this.username = username;
        this.messageContent = messageContent;
    }
    /**
     * Getter and setter methods
     */
    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public Integer getUsername() {
        return username;
    }

    public void setUsername(Integer username) {
        this.username = username;
    }

    public Integer getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(Integer messageContent) {
        this.messageContent = messageContent;
    }

    /**
     * Object information in string
     * @return string information
     */
    @Override
    public String toString() {
        return "Messages{" +
                "messageId=" + messageId +
                ", roomId=" + roomId +
                ", username=" + username +
                ", messageContent=" + messageContent +
                '}';
    }
}

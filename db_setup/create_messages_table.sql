-- This query is used against the chatroom_db database
-- Create the messages table

CREATE TABLE Messages (
    message_id INT NOT NULL AUTO_INCREMENT,
    room_id INT NOT NULL,
    username VARCHAR(255) NOT NULL,
    message_content VARCHAR(1023), 
    PRIMARY KEY(message_id),
    FOREIGN KEY(room_id) REFERENCES rooms(room_id)
);
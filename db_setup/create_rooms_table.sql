-- This query is use against the chatroom_db database

CREATE TABLE Rooms (
    room_id INT NOT NULL AUTO_INCREMENT,
    room_name VARCHAR(255) NOT NULL,
    created_by VARCHAR(255),
    date_created TIMESTAMP,
    PRIMARY KEY (room_id),
    UNIQUE KEY unique_room_name (room_name)
)
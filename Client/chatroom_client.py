import static_info
import sys
import socket
import uuid
import keyboard

class ChatroomClient:
    def __init__(self):
        ## Generate random unique uuid for the current session
        self.userID = uuid.uuid1()
        self.inChatroom = False
        self.isChatting = False
        self.roomID = None
        self.roomName = None
    
    def sendMessageToServer(self):
        print("Message sent")
    
    def executeCommand(self, command):
        args = command.split(" ")
        if args in static_info.COMMANDS:
            ## TODO logic for executing commands
            print("To be finished")

    def userInputHandler(self, userInput):
        if self.inChatroom and self.isChatting:
            ## This is a message; Send the content to the server
            self.sendMessageToServer(userInput)
        else:
            ## This is a command; execute the specific command
            self.executeCommand(userInput)
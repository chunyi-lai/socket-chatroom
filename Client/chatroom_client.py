import static_info
import sys
import socket
import uuid
import binascii

# class ChatroomClient:
#     def __init__(self):
#         ## Generate random unique uuid for the current session
#         self.userName = None
#         self.currentRoomName = None
    
#     def sendMessageToServer(self, message):
        
    
#     def executeCommand(self, command):
#         args = command.split(" ")
#         if args in static_info.COMMANDS:
#             ## TODO logic for executing commands
#             print("In executeCommand - To be finished.")

#     def userInputHandler(self, userInput):
#         if self.inChatroom and self.isChatting:
#             ## This is a message; Send the content to the server
#             self.sendMessageToServer(userInput)
#         else:
#             ## This is a command; execute the specific command
#             self.executeCommand(userInput)

def sendMessageToRoom(sentBy, roomName, content):
    return "message " + content + " " + sentBy + " " + roomName

def createroom(roomName, createdBy):
    return "createroom " + roomName + " " + createdBy 

def gotoroom(roomName):
    return "gotoroom " + roomName

def deleteroom(roomName):
    return "deleteroom " + roomName

if __name__ == "__main__":

    currentRoom = "testroom1"
    currentUser = "testuser1"

    print(static_info.INFO["basic"])
    print(static_info.INFO["commands"])
    print(f"Current User: {currentUser}")
    print(f"Current Room: {currentRoom}")
    userInput = ""

    while userInput != "quit":
        userInput = input("Actions you want to take: ")

        if userInput == "gotoroom":
            roomName = input("Please enter the name of the room to be entered: ")
            data = gotoroom(roomName)
            currentroom = roomName
        elif userInput == "createroom":
            roomName = input("Please enter the name of the room to be created: ")
            data = createroom(roomName, currentUser)
        elif userInput == "sendmessage":
            content = input("Please enter your message: ")
            data = sendMessageToRoom(currentUser, currentRoom, content)
        elif userInput == "deleteroom":
            roomName = input("Please enter the room to be deleted: ")
            data = deleteroom(roomName)
        elif userInput == "listroom":
            data = "listroom"
        elif userInput == "quit":
            break
        else:
            print("Error: Invalid Command")
        
        ## Add line ending for data
        data += "\n"
        ## bind the socket
        try:
            clientsocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        except:
            print("Error creating socket")

        ## establish client and server connection
        host, port = socket.gethostname(), 4999
        clientsocket.connect((host, port))
        clientsocket.send(data.encode())

        # decode the message
        dataFromServer = clientsocket.recv(4096)
        hexData = dataFromServer.hex()
        messages = binascii.a2b_hex(hexData[14:]).decode("ascii")
        print(messages)
        clientsocket.close()

    print("See you next time!")
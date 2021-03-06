COMMANDS = {
  ## All user commands
  "quit": "Quit the current chatroom while in a chatroom.",
  "shut": "Quit the chatroom program; Can be used while in and out of a chatroom.",
  "gotoroom": "Go to a room with the specific room number or name.",
  "listroom": "Go to a room with the specific room number or name.",
  "createroom": "create a room by giving a unique name. User will be prompt with a mesage to be informed if a room is created successfully.",
  "deleteroom": "Delete a room by the room's room name. User will be prompt with a mesage to be informed if a room is deleted successfully.",
  "help": "List all commands and their functionalities.",
  "sendmessage": "Sends the message to the chatroom",
  "deleteroom": "Delete the selected room"

}

INFO = {
  ## Basic instruction prompt
  "basic": """
              ***********************************
              * Terminal-Based Chatroom Program *
              ***********************************
        
  * To enter a chat room: please enter a valid room number.
  * Once in a chat room, you can view all previous messages in this chat room.
  * To view older messages, Use the following following commands to do so.
  * To user a user command while in a chatroom, press the Esc key to enter the 
    command prompt.
  """,

  ## Command instruction prompt
  "commands": """
  COMMANDS:
    gotoroom [room number/name]: {}
    listroom: {}
    createroom [room name]: {}
    sendmessage [message]: {}
    deleteroom [room name]: {}
    help: {}
  """.format(COMMANDS["gotoroom"], COMMANDS["listroom"], COMMANDS["createroom"], 
  COMMANDS["sendmessage"], COMMANDS["deleteroom"],COMMANDS["help"])
}

if __name__ == "__main__":
    print(INFO["basic"])
    print(INFO["commands"])

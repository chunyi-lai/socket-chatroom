BASIC_INSTRUCTION = """
            ***********************************
            * Terminal-Based Chatroom Program *
            ***********************************
      
* To enter a chat room: please enter a valid room number.
* There are 10 rooms in total, the room numbers consist from 0 to 9.
* Once in a chat room, you can view all previous messages in this chat room.
* To view older messages, Use the following following commands to do so.
* To user a user command while in a chatroom, press the Esc key to enter the 
  command prompt.
"""

COMMAND_INSTRUCTION = """
COMMANDS:
  quit: quit the current chatroom while in a chatroom.
  shut: quit the chatroom program; Can be used while in and out of a chatroom.
  gotoroom [room number]: go to a room with the specific room number.
  listroom: List all the available rooms.
"""

## All user commands
QUIT = "quit"
SHUT = "shut"
GOTOROOM = "gotoroom"
LISTROOM = "listroom"


if __name__ == "__main__":
    print(BASIC_INSTRUCTION)
    print(COMMAND_INSTRUCTION)
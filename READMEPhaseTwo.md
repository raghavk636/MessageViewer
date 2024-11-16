##MessageViewer Phase Two
Welcome to the MessageViewer direct messaging app. Our app allows viewers to send and recieve messages to any number of friends! Within this phase we have developed a server that supports multiple clients at once in a thread safe server so that each client's profile information and message history are kept secure. The ClientGUI simulates a client-side interface for a direct messaging application, allowing users to log in to their accounts, create new accounts, interact with the server to manage messages and friendships. 

 ## Main Screen
On the main screen we have included a simple user interface that highlights the main set of actions a user can take after logging in. To begin users can search and interact with messages they have sent and recieved through the arraylist databases that hold all message history by finding messages with a related term. Moreover, users can add and remove friends from the main screen, which manipulates the user friend list. In addition, users can block friends or even sign out from the main screen. 

## Key Features
Some of the key features included in the MessageViewer app is input validation which ensures that the user enters valid choices for menu selections. The user interface of the app provides clear prompts and feedback for various actions. The looping structure of the app allows the user to repeatedly interact with the application without restarting it. Furthermore, the thread safe client does not store information locally as all information is stored on the server side and accessed via network IO using the client.


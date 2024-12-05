## MessageVeiwer Phase 3
Welcome to MessageViewer, a message-viewing app that allows users to send and receive messages with any number of friends. The features included in our app enable users to create password-protected profiles, add and remove friends, and block other users to prevent unwanted communications. User data is stored securely in files to ensure that information is preserved even when the app is closed.
## GUI
In this phase, we have introduced a Graphical User Interface (GUI) that allows users to interact with the app by clicking buttons to send direct messages to their friends. The intuitive and interactive user interface provides the best possible experience.

Upon opening the app, users are prompted to either log in or create an account. New users can create a password-protected account and then return to the home screen to log in. After successfully logging in with their username and password, users are directed to their profile page. This page offers multiple features, including:

Adding friends
Blocking other users
Writing and sending direct messages

## Direct Messaging
Users can send messages to any of their friends by entering the friend’s username and typing the message in the provided text box. Once sent, the message is stored in a message ArrayList, maintaining a history of all past messages for future reference.

Using thread-safe methods, the app ensures users can send messages without interfering with each other's message history. Each user can accurately view their past conversations.

If a user no longer wishes to interact with someone, they can either remove the person as a friend or block them. Blocking a user adds their username to the blockedUsers ArrayList, ensuring further communications from that person are restricted.

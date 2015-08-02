# MultiThreaded-Chat
Similar to the other chat application that I made which was only between a single client and server, this one can host multiple client and allow them to communicate with each other simultaneously.

The server part of the program contains two Java files - Server.java and clientThread.java. Server.java has the main method
for the server application which starts up the server waiting for connections. When a connection is made, it creates a new
clientThread object and stores it in an array list. The clientThread handles the all of the actions taken with the newly
connected client so the Server can go back to waiting for more clients to join the chat.

The client part of the program contains two Java files as well - Client.java and listenThread.java. Client.java has the main
method for the client side portion of the program. When running the client, it connects to the server and initiates a new
listenThread object. The listenThread is responsible for handling incoming messages from the server that other users have sent.
While the listenThread is listening to the server and waiting for incoming messages, the client object handles the client's
messages that he/she is sending to the other users connected.

When first running client.java, the user is prompted to enter their name. This name helps the server identify who is connected
and allows other users connected know who has sent which message. After entering the user's name, the client file continues 
on making the connection with the server. To terminate a connection, the user must enter "END". After which the user will be
removed from the chat party and the remaining users will be notified of who has left the party.

References:

Establishing Client-Server connection:
https://www.youtube.com/watch?v=_1nqY-DKP9A

Establishing Multiple Clients:
https://www.youtube.com/watch?v=gLfuZrrfKes

Connect MySQL DataBase in Intellij:
https://www.youtube.com/watch?v=e8g9eNnFpHQ&list=LL&index=7

JavaFX Login and SignupForm w/ Database SQL:
https://www.youtube.com/watch?v=ltX5AtW9v30&list=LL&index=24


Code explanation:
Code has two projects:

1. ServerSide
2. javafx-login-video (ClientSide)

ServerSide has six classes

1. Main
2. Server
3. ClientHandler
4. FileHandler
5. Packet
6. Item

Main links with Server and Filehandler.

FileHanlder reads input and converts to map which is passed to Server object

Server object connections with ClientHandler, Packet, and Item.

ClientHandler is runnable and creates new thread to handle client interactions for each client that joins.

Packet is a class which sends and receives data from clientside.

Packet contains information related to items.

Item is a class to hold item info.




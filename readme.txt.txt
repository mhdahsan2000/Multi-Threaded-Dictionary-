Multi-threaded Dictionary Server
-------------------------------------------

In this project, I have designed a client-server architecture that would allow concurrent clients 
to search the meaning(s) of a word, add a new word, and remove the existing word. 

The project have designed with the use of two fundamental technologies that are : 
1. Sockets 
2. Threads 


Architecture : 
--------------

- The System would follow a client-server architecture in which multiple clients would be able to connect
to a single Multi-threaded server and perform operations concurrently.

- The multi-threaded server is implemented with a thread-per request architecture. 

Interaction : 
--------------

- All communications would happen with the help of Sockets. These sockets are TCP and all of the connections are very reliable. 
- Message Exchange Protocol mostly is through help of JSON. 

Failure Model :
--------------- 

- All the communication that would take place between the components are reliable. I have implemented TCP and the reliability guarantees 
that are provided by the protocol seems to be sufficient. 

- Many of the errors that would be diagonised by the protocol would include : 
  - Input from the console for what concerns the parameters passed on a command line. 
  - Incorrect input would receive the feedback of correct input to run the program.
  - Network Communication (address not reachable, bad data......)
  - I/O to and from disk (cannot find the dictionary file, error reading the file, etc...).
 

Functionalities of the Dictionary : 
----------------------------------------

1. Query the meaning(s) of a given word

Input: Word to search
Output: Meaning(s) of the word
Error: The client should clearly indicate if the word was not found or if an error occurred

2. Add a new word

Add a new word and one or more of its meanings to the dictionary. 
For the word to be added successfully it should not exist already in the dictionary.
Also, attempting to add a word without an associated meaning should result in an error.
A new word added by one client should be visible to all other clients of the dictionary server.

Input: Word to add, meaning(s)
Output: Status of the operation (e.g., success, duplicate)
Error: The user are informed if any errors occurred while performing the operation.


3.Remove an existing word

Remove a word and all of its associated meanings from the dictionary.
A word deleted by one client should not be visible to any of the clients of the dictionary server. If the word does not exist in the dictionary then no action should be taken.

Input: Word to remove
Output: Status of the operation (e.g., success, not found)
Error: The user are informed if any errors occurred while performing the operation.


4.Update meaning of an existing word

Update associated meanings of a word from the dictionary. If multiple meanings exist, all of them should be replaced by new meaning(s) provided by user. Update made by one client should be visible to any of the clients of the dictionary server.
If the word does not exist in the dictionary, then no action should be taken.

Input: Word to update, meaning(s)
Output: Status of the operation (e.g., success, not found)
Error: The user should is informed if any errors occurred while performing the operation.

User Interface : 
-----------------
A Graphical User Interface(GUI) is present for this project. Once you run the jar files you will
be able to access the GUI. 



INSTRUCTIONS TO RUN THE MULTI THREADED DICTIONARY IN JAVA (from the terminal): 

1. Download the source code. 
2. To run the client and server .jar files : 
a. First setup a server on a convenient port.  
   A sample command to start the server is as follows: 
	
	> java -jar DictionaryServer.jar <port> <dictionary-file>

b.Then setup the client using the following command : 
	A sample command to start the client is as follows: 

       > java -jar DictionaryClient.jar <server-address> <server-port>



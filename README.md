# dodb_p2p
code for p2p file sharing 

main class to run ClientJfram.java
this p2p file sharing code acts both as a server and client.
database server address must be set prior to execution of the code.

how the code works?
if the code is going to be a client it either uploads a file or downloads a file. when it uploads the file it registers the details of the 
file including the ip address of its locaiton and names of chunks of the file to the database. the actual file will be stored on the client.
file chunks are not actually created but only their names(with some programming logic) are stored. if the client wants to download a file
it selects a file from the list and hit the download button. the other peers holding the file chunk the file apart and serve the file to the 
client. the client then reassembles the chunks and and make a single file.if the client wants to be a server it needs to upload its file make it
availabel for other clients use.

             Database Design 

create or replace database p2p_test;
use p2p_test;

create or replace table file_list(
		file_name varchar,
		ip_address decimal, 
		port number,
		hash varchar,
		file_size decimal,
		primary key (hash));
		
create or replace table chunk_list(
		chunk_name varchar,
		ip_address decimal,
		port number,
		hash varchar,
		foreign key (hash));
		
		

group memebers 
  biniyam teshome
  selamu tadesse
  tariku g/sillasie


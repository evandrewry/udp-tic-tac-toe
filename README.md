udp-tic-tac-toe
===============

The Client and Server jarfiles are included, but the project can be also 
be compiled with ant using `ant build`, and the Server and Client jar files 
can also be generated with ant by running `ant jars` (or just `ant` since
it is the default ant task).

To start the server, run
```
$ java -jar Server.jar <server-port>
```

To start a client, run
```
$ java -jar Client.jar <server-ip> <server-port>
```



Client commands:
================

`login <username>` : log in the client under this username

`ls` : retrieve the list of currently online users

`choose <username>` : send a play request to this user

`accept <username>` : accept a play request from this user

`deny <username>` : deny a play request from this user

`play <cellNo>` : place an X or O on the specified cell

`logout` : log out of server

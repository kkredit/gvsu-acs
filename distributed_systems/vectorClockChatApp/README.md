
# Background

This project demonstrates logical vector clocks for event synchronization in a distributed chat
application. See the Wikipedia page on the subject
[here](https://en.wikipedia.org/wiki/Vector_clock).

# Usage

This project uses the IntelliJ IDE as a build system. To build, import the `logical_clocks/`
directory as an IntelliJ project and select `Build > Build Project`.

To run the server, use
```sh
cd server
./runServer.sh
```

To run clients, use
```sh
cd logical_clocks
./runClient.sh USER_NAME
```

Note the following requirements:
- The scripts assume a `bash` shell; Linux or WSL will work
- The server must be launched before the clients
- Clients must be launched on the same host as the server
- Because the server does not unregister users, it must be restarted before a user with the same
  name can join the chatroom

# Expected behavior

The behavior will resemble a typical chat application. The vector clocks come in by correctly
sequencing the messages. What happens is the server simulates a WAN connection by scrambling the
order of received messages. Without the vector clocks, messages would be appear in different, random
orders for each user. To show this, each message is prepended with a tag of the order in which it
was received. A new user joining a conversation may look like the following:

```
Connected to the target VM, address: '127.0.0.1:59219', transport: 'socket'
You are registered as 'alfred'.

Type to talk. Type ':exit' to exit.

1:[alice has logged on]
5:[jim has logged on]
4:alice: Hi Jim!
3:jim: Oh hey Alice. Lovely snow today. :)
6:alice: No, not really.
2:[alice has logged off]
---- 'alfred' just logged in, and with the above messages is caught up ----
Darn, I just missed Alice
7:jim: yep
```

As you can see, the messages arrived in an illogical, indeed, incorrect order, but they still
displayed correctly.

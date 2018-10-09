
# Usage

This project is based the Apache Ant build system. I added helper scripts because the ant commands
are just annoying enough to not want to type in each time. The following scripts launch the RMI
registry, the chat Presence server, and the chat client. Usage is as follows:
```sh
./rmiRun.sh [port]
./serverRun.sh [port of rmi service]
./clientRun.sh username [host:port of rmi service]
```
Note the following requirements:
- The scripts assume a `bash` environment; Cygwin, Linux, or WSL (what I used) should do
- The RMI registry must be launched before the server
- The server must be launched before the clients
- The server must be launched on the same host as the RMI registry (I think)
- Clients must be launched on the same LAN as the server

# Known issues

## Functionality

- Quiting instead of `exit`ing users

If users end their session via killing the process with a `ctrl-c`, for example, the server will not
be notified of their absence. Other users who try to talk to them will experience connection
failures.

## Scalability

- Port assignment

Because ports must be unique to each host but a single host may have multiple clients, clients may
not all use the same port. The presence server handles port assignment. The current implementation
simply maintains an incrementing port assignment counter. This approach is neither efficient nor
sustainable, but works for small sets of users and frequently restarted servers.

- User tracking (vector vs hash table)

The simplest means of tracking users was with a Vector, not least because one of the Presence APIs
requires returning the list of users as a Vector. This implementation is suitable for small numbers
of users, but with O(N) performance on user lookups, it would perform poorly with large numbers.
Some sort of hash table implementation would help there.

## Security

- Stolen identity

In addition to the basic lack of authentication, the API allows for a user's messages to be stolen
or intercepted at runtime. Using the `updateRegistrationInfo` API, any user can steal another active user's
"identity". The attacker would use `friends` to identify other users and "update" that user's
IP address with its own. It could even then forward messages to the original user such that the
user doesn't even notice. This could be changed by a more restrictive specification for the
`updateRegistrationInfo` API.

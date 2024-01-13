1. User starts a WS connection
2. Before establishing the connection check if the document exists, if the user is allowed to interact with the document (API Gateway)
3. Process the body and send the appropriate kafka event
4. Have a subscribe chain to the Kafka stream in order to send back the events to the user
---

1. User connects to a session
2. All the other users are notified when a user connected
3. When user disconnects all the other listeners are notified
4. 
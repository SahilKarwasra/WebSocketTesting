# WebSocket Testing Project

This project demonstrates a basic chat application using **Socket.IO** for real-time communication and **Jetpack Compose** for the Android UI. It includes both client-side logic for connecting to a Socket.IO server and handling messages, as well as a simple UI for sending and receiving chat messages.

## Features
- **Real-time Messaging**: Users can send and receive messages in real-time.
- **Socket.IO Integration**: Seamless connection to a Node.js server using Socket.IO.
- **Jetpack Compose UI**: Modern UI built with Compose.

## Prerequisites
- Node.js server with Socket.IO configured.
- Basic knowledge of Kotlin and Android development.
- Android Studio installed on your system.

## Installation
1. Clone the repository:
   ```bash
   git clone <repository-url>
   ```

2. Open the project in Android Studio.

3. Start your Socket.IO server. Example server code:
   ```javascript
   const io = require('socket.io')(3000);

   io.on('connection', (socket) => {
       console.log('A user connected');

       socket.on('chat message', (msg) => {
           io.emit('chat message', msg);
       });

       socket.on('disconnect', () => {
           console.log('A user disconnected');
       });
   });
   ```

4. Run the application on an emulator or physical device.

## Usage
1. Start the Node.js server using the command:
   ```bash
   node server.js
   ```

2. Open the app on your Android device or emulator.

3. Enter a message in the text field and click **Send**. The message will be sent to the server and broadcasted back to all connected clients.

4. Received messages will appear below the text field.

## Project Structure
- **SocketHandler.kt**: Contains logic for setting up and managing the Socket.IO connection.
- **MainActivity.kt**: The main activity of the app, implementing the chat UI and handling user interactions.
- **UI Theme**: Custom theme for styling the Compose components.

## Libraries Used
- [Socket.IO Client](https://github.com/socketio/socket.io)
- [Jetpack Compose](https://developer.android.com/jetpack/compose)


## Notes
- For testing on an emulator, use `http://10.0.2.2:3000` as the server URL.
- Replace `10.0.2.2` with the actual IP address of your server when testing on a physical device.

## Future Improvements
- Add support for message history.
- Display a list of all connected users.
- Improve UI with animations or additional Compose components.

## License
This project is licensed under the MIT License. Feel free to use and modify it as needed.

---

Happy coding!


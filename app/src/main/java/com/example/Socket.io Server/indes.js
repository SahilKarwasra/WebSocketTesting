const express = require('express');
const http = require('http');
const socketIO = require('socket.io');

const app = express();
const server = http.createServer(app);
const io = socketIO(server);

io.on('connection', (socket) => {
    console.log('A user connected', socket.id);

    socket.on('chat message', (msg) => {
        io.emit('chat message', msg);
    });

    socket.on('disconnect', () => {
        console.log('A user disconnected');
    });
});

// var count = 0;
// io.on('connection', (socket) => {
//     console.log("New Socket Connection:" + socket.id)
//     socket.on('counter' , () => {
//         count++;
//         io.emit('counter', count)
//     })
// })


server.listen(3000, () => {
    console.log('Server is running on port 3000');
});
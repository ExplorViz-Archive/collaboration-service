package net.explorviz.extension.vr.message;

import javax.websocket.Session;

import net.explorviz.extension.vr.service.Room;

public class MessageArgs {

    public Session session;
    
    public Room room;
    
    public MessageArgs(Room room, Session session) {
        this.room = room;
        this.session = session;
    }
}

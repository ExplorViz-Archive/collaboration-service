package net.explorviz.extension.vr.service;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import net.explorviz.extension.vr.service.factory.RoomFactory;

@ApplicationScoped
public class RoomService {

    @Inject
    RoomFactory roomFactory;
    
    @Inject
    IdGenerationService idGenerationService;
    
    private Map<String, Room> rooms = new ConcurrentHashMap<>();
    
    public Room createRoom() {
        var room = roomFactory.makeRoom();
        rooms.put(idGenerationService.nextId(), room);
        return room;
    }
    
    public void deleteRoom(String roomId) {
        rooms.remove(roomId);
    }
    
    public Room lookupRoom(String roomId) {
        return rooms.get(roomId);
    }
    
    public Collection<Room> getRooms() {
        return rooms.values();
    }
}

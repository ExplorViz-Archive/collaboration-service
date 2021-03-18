package net.explorviz.extension.vr.service;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.explorviz.extension.vr.event.UserDisconnectedEvent;
import net.explorviz.extension.vr.service.factory.RoomFactory;

@ApplicationScoped
public class RoomService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoomService.class);

    @Inject
    RoomFactory roomFactory;
    
    @Inject
    IdGenerationService idGenerationService;
    
    private Map<String, Room> rooms = new ConcurrentHashMap<>();
    
    public String createRoom() {
        var roomId = idGenerationService.nextId();
        var room = roomFactory.makeRoom(roomId);
        rooms.put(roomId, room);
        LOGGER.info("Created room with id " + roomId);
        return roomId;
    }
    
    public void deleteRoom(String roomId) {
        rooms.remove(roomId);
        LOGGER.info("Removed room with id " + roomId);
    }
    
    public Room lookupRoom(String roomId) { 
        if (!rooms.containsKey(roomId)) {
            throw new IllegalStateException("Room not found: " + roomId);
        }
        return rooms.get(roomId);
    }
    
    public Set<String> getRooms() {
        return rooms.keySet();
    }
    
    public void deleteRoomIfEmpty(@ObservesAsync UserDisconnectedEvent event) {
        final var room = event.getRoom();
        if (room.getUserService().getUsers().isEmpty()) {
            deleteRoom(room.getRoomId());
        }
    }
}

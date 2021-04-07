package net.explorviz.vr.service;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.explorviz.vr.event.UserDisconnectedEvent;
import net.explorviz.vr.service.factory.RoomFactory;

@ApplicationScoped
public class RoomService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoomService.class);

    @Inject
    RoomFactory roomFactory;

    @Inject
    IdGenerationService idGenerationService;

    private Map<String, Room> rooms = new ConcurrentHashMap<>();

    public Room createRoom() {
        final var roomId = idGenerationService.nextId();
        final var roomName = "Room " + roomId;
        final var room = roomFactory.makeRoom(roomId, roomName);
        rooms.put(roomId, room);
        LOGGER.info("Created room with id " + roomId);
        return room;
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
    
    public boolean roomExists(Room room) {
    	return rooms.containsKey(room.getRoomId());
    }

    public Collection<Room> getRooms() {
        return rooms.values();
    }

    public void deleteRoomIfEmpty(@ObservesAsync UserDisconnectedEvent event) {
        final var room = event.getRoom();
        if (room.getUserService().getUsers().isEmpty()) {
            deleteRoom(room.getRoomId());
        }
    }
}

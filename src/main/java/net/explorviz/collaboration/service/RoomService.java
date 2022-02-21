package net.explorviz.collaboration.service;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;
import net.explorviz.collaboration.event.UserDisconnectedEvent;
import net.explorviz.collaboration.service.factory.RoomFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class RoomService {

  private static final Logger LOGGER = LoggerFactory.getLogger(RoomService.class);

  @Inject
  /* default */RoomFactory roomFactory; // NOCS

  @Inject
  /* default */IdGenerationService idGenerationService; // NOCS

  private final Map<String, Room> rooms = new ConcurrentHashMap<>();

  public Room createRoom() {
    final var roomId = this.idGenerationService.nextId();
    final var roomName = "Room " + roomId;
    final var room = this.roomFactory.makeRoom(roomId, roomName);
    this.rooms.put(roomId, room);
    if (LOGGER.isInfoEnabled()) {
      LOGGER.info("Created room with id " + roomId);
    }
    return room;
  }

  public void deleteRoom(final String roomId) {
    this.rooms.remove(roomId);
    if (LOGGER.isInfoEnabled()) {
      LOGGER.info("Removed room with id " + roomId);
    }
  }

  public Room lookupRoom(final String roomId) {
    if (!this.rooms.containsKey(roomId)) {
      throw new IllegalStateException("Room not found: " + roomId);
    }
    return this.rooms.get(roomId);
  }

  public boolean roomExists(final Room room) {
    return this.rooms.containsKey(room.getRoomId());
  }

  public Collection<Room> getRooms() {
    return this.rooms.values();
  }

  public void deleteRoomIfEmpty(@ObservesAsync final UserDisconnectedEvent event) {
    final var room = event.getRoom();
    if (room.getUserService().getUsers().isEmpty()) {
      this.deleteRoom(room.getRoomId());
    }
  }
}

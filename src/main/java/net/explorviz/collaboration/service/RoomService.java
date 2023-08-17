package net.explorviz.collaboration.service;

import io.quarkus.runtime.StartupEvent;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;
import net.explorviz.collaboration.event.UserDisconnectedEvent;
import net.explorviz.collaboration.service.factory.RoomFactory;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class RoomService {

  private static final Logger LOGGER = LoggerFactory.getLogger(RoomService.class);
  private static final String ROOM_PREFIX = "Room ";
  @ConfigProperty(name = "explorviz.default.roomid")
  /* default */ String defaultRoomId; // NOCS

  @Inject
  /* default */ RoomFactory roomFactory; // NOCS

  @Inject
  /* default */ IdGenerationService idGenerationService; // NOCS

  private final Map<String, Room> rooms = new ConcurrentHashMap<>();

  public Room createRoom() {
    final var roomId = this.idGenerationService.nextId();
    final var roomName = ROOM_PREFIX + roomId;
    final var room = this.roomFactory.makeRoom(roomId, roomName);
    this.rooms.put(roomId, room);
    if (LOGGER.isInfoEnabled()) {
      LOGGER.info("Created room with id " + roomId);
    }
    return room;
  }

  // Overloading function to satisfy the need for setting up a specific 
  // room id instead of generating a random one.
  public Room createRoom(String roomId) {
    if(!this.rooms.containsKey(roomId)) {
      final var roomName = ROOM_PREFIX + roomId;
      final var room = this.roomFactory.makeRoom(roomId, roomName);
      this.rooms.put(roomId, room);
      if (LOGGER.isInfoEnabled()) {
        LOGGER.info("Created room with id " + roomId);
      }
      
      return room;
    }
    // When dublicated, then return a id generated room
    return this.createRoom();
  }

  private void createDefaultRoom() {
    if (defaultRoomId == null || defaultRoomId.isEmpty() || defaultRoomId.equals("-1")) {
      if (LOGGER.isInfoEnabled()) {
        LOGGER.info("Did not create default room due to empty env variable.");
      }
    } else {
      final var roomName = ROOM_PREFIX + defaultRoomId;
      final var room = this.roomFactory.makeRoom(defaultRoomId, roomName);
      this.rooms.put(defaultRoomId, room);
      if (LOGGER.isInfoEnabled()) {
        LOGGER.info("Created default room with id " + defaultRoomId);
      }
    }
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
    if (room.getUserService().getUsers().isEmpty() && !room.getRoomId()
        .equals(this.defaultRoomId)) {
      this.deleteRoom(room.getRoomId());
    }
  }

  /* default */ void onStart(@Observes final StartupEvent ev) {
    this.createDefaultRoom();
  }
}

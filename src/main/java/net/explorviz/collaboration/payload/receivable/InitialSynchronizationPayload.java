package net.explorviz.collaboration.payload.receivable;

/**
 * A JSON object that is sent by the frontend to the room resource when a new room is created.
 * Contains all information to initialize a room.
 */
public class InitialSynchronizationPayload {

    private InitialRoomPayload roomPayload;
    
    public InitialRoomPayload getRoomPayload() {
        return this.roomPayload;
    }

    public void setRoomPayload(final InitialRoomPayload roomPayload) {
        this.roomPayload = roomPayload;
    }
}
package net.explorviz.collaboration.service;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.explorviz.collaboration.model.ProjectorConfigurations;
import net.explorviz.collaboration.model.UserModel;
import net.explorviz.collaboration.payload.sendable.RoomCreatedResponse;
import net.explorviz.collaboration.payload.sendable.SynchronizationStartedResponse;

/**
 * Provides all informations about the synchronization feature of ExplorViz in
 * the ARENA2 of GEOMAR.
 */
@ApplicationScoped
public class SynchronizationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoomService.class);
    @Inject
    RoomService roomService;

    // synchronization room
    private Room room;
    // control instance
    private SynchronizationUser main;
    // synchronized devices
    private final Map<String, SynchronizationUser> projectors = new ConcurrentHashMap<>();

    // set up all relevant informations to this synchronization
    public void setService(SynchronizationStartedResponse response, String deviceId) {
        RoomCreatedResponse roomResponse = response.getRoomResponse();
        String roomId = roomResponse.getRoomId();
        this.setRoom(roomId);

        for (Map.Entry<String, SynchronizationUser> entry : projectors.entrySet()) {
            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
        }

        Collection<UserModel> originalUsers = this.room.getUserService().getUsers();
        for (UserModel userModel : originalUsers) {

            SynchronizationUser projector = new SynchronizationUser();
            projector.setUserModel(userModel);
            projector.setId(deviceId);

            if (deviceId == "Main") {
                this.main = projector;
                if (LOGGER.isInfoEnabled()) {
                    LOGGER.info("Main detected!");
                }
            } else {
                addProjector(projector);
                if (LOGGER.isInfoEnabled()) {
                    LOGGER.info("Add projector " + projector.getId());
                }
            }

        }
        // LobbyJoinedResponse joinResponse = response.getJoinResponse();

    }

    public void setRoom(final String roomId) {
        this.room = this.roomService.lookupRoom(roomId);
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Set Synchronization Room with name: " + roomId);
        }
    }

    public Room getRoom() {
        return this.room;
    }

    public void setMain(final SynchronizationUser main) {
        this.main = main;
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Set main: " + main);
        }
    }

    public SynchronizationUser getMain() {
        return this.main;
    }

    public SynchronizationUser lookUpProjector(final String userId) {
        if (!this.projectors.containsKey(userId)) {
            throw new IllegalStateException("Projector not found: " + userId);
        }
        return this.projectors.get(userId);
    }

    public boolean existsProjector(final String userId) {
        return this.projectors.containsKey(userId);
    }

    public void addProjector(final SynchronizationUser projector) {
        this.projectors.put(projector.getId(), projector);
    }

    /**
     * 
     */
    public class SynchronizationUser {
        private String id;
        private UserModel userModel;
        private ProjectorConfigurations projectorConfiguration;

        public SynchronizationUser() {
        }

        public SynchronizationUser(UserModel userModel, ProjectorConfigurations projectorConfiguration) {
            this.userModel = userModel;
            this.projectorConfiguration = projectorConfiguration;
        }

        public void setUserModel(UserModel userModel) {
            this.userModel = userModel;
        }

        public UserModel getUserModel() {
            return this.userModel;
        }

        public void setProjectorConfiguration(ProjectorConfigurations projectorConfigurations) {
            this.projectorConfiguration = projectorConfigurations;
        }

        public ProjectorConfigurations getProjectorConfiguration() {
            return this.projectorConfiguration;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getId() {
            return this.id;
        }
    }

}

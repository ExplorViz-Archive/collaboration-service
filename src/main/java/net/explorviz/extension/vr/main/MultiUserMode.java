package net.explorviz.extension.vr.main;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.explorviz.api.ExtensionAPIImpl;
import net.explorviz.extension.vr.model.NodeGroupModel;
import net.explorviz.extension.vr.model.SystemModel;
import net.explorviz.extension.vr.model.UserModel;
import net.explorviz.model.landscape.Landscape;
import net.explorviz.model.landscape.NodeGroup;
import net.explorviz.model.landscape.System;

/**
 * Main class for multi user experience. Contains a WebSocket for communication
 * with clients.
 *
 * @author Daniel K�nig & Malte Hansen
 *
 */
public class MultiUserMode extends WebSocketServer implements Runnable {

	private Thread multiUserThread;
	private final ArrayList<SystemModel> systems = new ArrayList<>();
	private static final int TCP_PORT = 4444;
	private final HashMap<Long, WebSocket> conns;
	private final HashMap<Long, UserModel> users;

	private static final Logger LOGGER = LoggerFactory.getLogger(MultiUserMode.class);

	public void run2() {
		LOGGER.info("MultiUserMode: Main loop entered");
	}

	@Override
	public void start() {
		super.start();
		LOGGER.info("MultiUserMode: starting");

		if (multiUserThread == null) {
			multiUserThread = new Thread(this::run2);
			multiUserThread.start();
		}

	}

	MultiUserMode() {
		super(new InetSocketAddress(TCP_PORT));
		conns = new HashMap<>();
		users = new HashMap<>();
		LOGGER.info("MultiUserMode: Websocket constructed");
	}

	private void initializeLandscapeModel() {
		LOGGER.info("Initialize landscape");
		systems.clear();
		final ExtensionAPIImpl coreAPI = ExtensionAPIImpl.getInstance();
		final Landscape landscape = coreAPI.getLatestLandscape();

		final List<System> landscapeSystems = landscape.getSystems();

		LOGGER.info("landscapeSystems length: " + landscapeSystems.size());

		// copy ids of systems and nodegroups to own model in order to keep track of
		// their visual state in the frontend
		for (final System LandscapeSystem : landscapeSystems) {
			final List<NodeGroup> nodeGroups = LandscapeSystem.getNodeGroups();
			final ArrayList<NodeGroupModel> nodeModels = new ArrayList<NodeGroupModel>();
			for (final NodeGroup nodeModel : nodeGroups) {
				final NodeGroupModel nodeGroup = new NodeGroupModel();
				nodeGroup.setId(nodeModel.getId());
				nodeModels.add(nodeGroup);
			}
			final SystemModel system = new SystemModel(nodeModels);
			system.setId(LandscapeSystem.getId());
			systems.add(system);
		}
		LOGGER.info("systems length: " + systems.size());

	}

	private void sendLandscape(final Long userID) {
		final JSONArray systemArray = new JSONArray();
		for (final SystemModel systemModel : systems) {
			final JSONArray nodeGroupArray = new JSONArray();
			for (final NodeGroupModel nodeGroupModel : systemModel.getNodeGroups()) {
				final JSONObject nodeGroupObj = new JSONObject();
				nodeGroupObj.put("id", nodeGroupModel.getId());
				nodeGroupObj.put("opened", nodeGroupModel.isOpened());
				nodeGroupArray.put(nodeGroupObj);
			}
			final JSONObject systemObj = new JSONObject();
			systemObj.put("id", systemModel.getId());
			systemObj.put("opened", systemModel.isOpened());
			systemObj.put("nodeGroups", nodeGroupArray);
			systemArray.put(systemObj);
		}

		final JSONObject landscapeObj = new JSONObject();
		landscapeObj.put("event", "receive_landscape");
		landscapeObj.put("systems", systemArray);

		final WebSocket conn = conns.get(userID);
		conn.send(landscapeObj.toString());
	}

	@Override
	public void onOpen(final WebSocket conn, final ClientHandshake handshake) {
		// initialize landscape before first user connects
		if (users.keySet().isEmpty()) {
			initializeLandscapeModel();
		}

		final UserModel user = new UserModel();
		final long clientID = user.getId();
		user.setState("connecting");

		conns.put(clientID, conn);
		users.put(clientID, user);

		LOGGER.info("New connection from " + conn.getRemoteSocketAddress().getAddress().getHostAddress());

		// inform users about new user with ID
		userConnecting(clientID);
	}

	private void userConnecting(final long userID) {
		// message other user about the new user
		final JSONObject connectingMessage = new JSONObject();
		connectingMessage.put("event", "user_connecting");
		connectingMessage.put("id", userID);
		broadcastAllBut(connectingMessage.toString(), userID);

		connectingMessage.remove("event");
		connectingMessage.put("event", "connecting");
		final WebSocket conn = conns.get(userID);
		conn.send(connectingMessage.toString());

	}

	private void userConnected(final long userID, final String name, final String device) {
		users.get(userID).setState("connected");
		// message other user about the new user
		final JSONObject connectedMessage = new JSONObject();
		final JSONObject user = new JSONObject();
		connectedMessage.put("event", "user_connected");
		user.put("name", users.get(userID).getUserName());
		user.put("id", userID);
		user.put("device", device);
		connectedMessage.put("user", user);
		broadcastAllBut(connectedMessage.toString(), userID);

		// send user their id and all other users' id and name
		final JSONObject initMessage = new JSONObject();
		final JSONArray usersArray = new JSONArray();
		initMessage.put("event", "connected");
		initMessage.put("id", userID);
		for (final UserModel userData : users.values()) {
			if (userData.getState().equals("connected")) {
				final JSONObject userObject = new JSONObject();
				userObject.put("id", userData.getId());
				userObject.put("name", userData.getUserName());
				userObject.put("device", userData.getDevice());
				usersArray.put(userObject);
			}
		}
		initMessage.put("users", usersArray);
		final WebSocket conn = conns.get(userID);
		conn.send(initMessage.toString());

		// send current state of landscape to new user
		sendLandscape(userID);
	}

	/**
	 * Sends a message (usually JSON as a string) to all connected users
	 *
	 * @param msg
	 *            The message which all users should receive
	 */
	public void broadcastAll(final String msg) {
		for (final WebSocket conn : conns.values()) {
			conn.send(msg);
		}
	}

	/**
	 * Sends a message (usually JSON as a string) to all but one connected users
	 *
	 * @param msg
	 *            The message which all users should receive
	 * @param userID
	 *            The user that should be excluded from the message
	 */
	public void broadcastAllBut(final String msg, final long userID) {
		for (final WebSocket conn : conns.values()) {
			if (conns.get(userID) != conn)
				conn.send(msg);
		}
	}

	@Override
	public void onClose(final WebSocket conn, final int code, final String reason, final boolean remote) {
		final long id = getIDByWebSocket(conn);
		if (id != -1) {
			conns.remove(id);
			users.remove(id);
		}
		LOGGER.info("Closed connection to " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
	}

	@Override
	public void onMessage(final WebSocket conn, final String message) {
		LOGGER.info("Message from client: " + message);
		new Thread(() -> {
			JSONObject JSONmessage = new JSONObject(message);
			final String event = JSONmessage.getString("event");
			final long id = getIDByWebSocket(conn);

			if (event.equals("position")) {
				JSONmessage.put("id", id);
				broadcastAllBut(JSONmessage.toString(), id);
				JSONmessage = null;
			} else if (event.equals("request_connect")) {
				final String name = JSONmessage.getString("name");
				final String device = JSONmessage.getString("device");
				final UserModel user = users.get(id);
				user.setUserName(name);
				user.setDevice(device);
				userConnected(id, name, device);
			}
		}).start();

	}

	private long getIDByWebSocket(final WebSocket conn) {
		for (final long id : conns.keySet()) {
			if (conns.get(id) == conn)
				return id;
		}
		return -1;
	}

	@Override
	public void onError(final WebSocket conn, final Exception ex) {
		// ex.printStackTrace();
		if (conn != null) {
			final long clientID = getIDByWebSocket(conn);
			conns.remove(clientID);
		}
		LOGGER.info("ERROR from " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
	}

	@Override
	public void onStart() {
		LOGGER.info("Server has started");
	}

}

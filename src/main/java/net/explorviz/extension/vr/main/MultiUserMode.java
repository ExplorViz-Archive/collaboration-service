package net.explorviz.extension.vr.main;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.explorviz.api.ExtensionAPIImpl;
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
	// private final ArrayList<SystemModel> systems = new ArrayList<>();
	private static final int TCP_PORT = 4444;
	private final HashMap<Long, WebSocket> conns;
	private final HashMap<Long, UserModel> users;
	private final HashMap<Long, Boolean> systemState;
	private final HashMap<Long, Boolean> nodeGroupState;
	private final boolean running = true;
	private final HashMap<Long, JSONArray> queues;

	private static final Logger LOGGER = LoggerFactory.getLogger(MultiUserMode.class);

	public void run2() {
		init();

		final int fps = 90; // number of update per second.
		final double tickPerSecond = 1000000000 / fps;
		double delta = 0;
		long now;
		long lastTime = java.lang.System.nanoTime();

		while (running) {
			now = java.lang.System.nanoTime();
			delta += (now - lastTime) / tickPerSecond;
			lastTime = now;

			if (delta >= 1) {
				tick();
				delta--;
			}
		}
	}

	private void init() {
		// initializeLandscapeModel();
	}

	private void tick() {
		for (final long userID : queues.keySet()) {
			final JSONArray queue = queues.get(userID);
			final WebSocket conn = conns.get(userID);
			conn.send(queue.toString());
		}
		queues.clear();
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
		queues = new HashMap<>();
		systemState = new HashMap<>();
		nodeGroupState = new HashMap<>();
		LOGGER.info("MultiUserMode: Websocket constructed");
	}

	private void initializeLandscapeModel() {
		LOGGER.info("Initialize landscape");
		// empty old Hashmaps for new incoming data
		systemState.clear();
		nodeGroupState.clear();

		final ExtensionAPIImpl coreAPI = ExtensionAPIImpl.getInstance();
		final Landscape landscape = coreAPI.getLatestLandscape();

		final List<System> landscapeSystems = landscape.getSystems();

		// copy ids of systems and nodegroups to Hashmaps and initialize open state with
		// true
		for (final System LandscapeSystem : landscapeSystems) {
			systemState.put(LandscapeSystem.getId(), true);
			final List<NodeGroup> nodeGroups = LandscapeSystem.getNodeGroups();
			for (final NodeGroup nodeModel : nodeGroups) {
				nodeGroupState.put(nodeModel.getId(), true);
			}
		}

	}

	private void sendLandscape(final Long userID) {
		final JSONArray systemArray = new JSONArray();
		for (final Map.Entry<Long, Boolean> entry : systemState.entrySet()) {
			final Long id = entry.getKey();
			final Boolean isOpen = entry.getValue();
			final JSONObject systemObj = new JSONObject();
			systemObj.put("id", id);
			systemObj.put("opened", isOpen);
			systemArray.put(systemObj);
		}

		final JSONArray nodeGroupArray = new JSONArray();
		for (final Map.Entry<Long, Boolean> entry : nodeGroupState.entrySet()) {
			final Long id = entry.getKey();
			final Boolean isOpen = entry.getValue();
			final JSONObject nodeGroupObj = new JSONObject();
			nodeGroupObj.put("id", id);
			nodeGroupObj.put("opened", isOpen);
			nodeGroupArray.put(nodeGroupObj);
		}

		final JSONObject landscapeObj = new JSONObject();
		landscapeObj.put("event", "receive_landscape");
		landscapeObj.put("systems", systemArray);
		landscapeObj.put("nodeGroups", nodeGroupArray);

		enqueue(userID, landscapeObj);
	}

	private void enqueue(final long userID, final JSONObject message) {
		if (!queues.containsKey(userID)) {
			queues.put(userID, new JSONArray());
		}
		final JSONArray queue = queues.get(userID);
		queue.put(message);
	}

	@Override
	public void onOpen(final WebSocket conn, final ClientHandshake handshake) {

		// initialize landscape when first user connects
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
		connectingMessage.put("event", "receive_user_connecting");
		connectingMessage.put("id", userID);
		broadcastAllBut(connectingMessage, userID);

		connectingMessage.remove("event");
		connectingMessage.put("event", "receive_self_connecting");

		enqueue(userID, connectingMessage);

	}

	private void userConnected(final long userID, final String name) {
		final UserModel user = users.get(userID);
		user.setState("connected");
		// message other user about the new user
		final JSONObject connectedMessage = new JSONObject();
		final JSONObject userObj = new JSONObject();
		connectedMessage.put("event", "receive_user_connected");
		userObj.put("name", user.getUserName());
		userObj.put("id", userID);
		connectedMessage.put("user", userObj);
		broadcastAllBut(connectedMessage, userID);

		// send user their id and all other users' id and name
		final JSONObject initMessage = new JSONObject();
		final JSONArray usersArray = new JSONArray();
		initMessage.put("event", "receive_self_connected");
		initMessage.put("id", userID);
		for (final UserModel userData : users.values()) {
			if (userData.getState().equals("connected")) {
				final JSONObject userObject = new JSONObject();
				userObject.put("id", userData.getId());
				userObject.put("name", userData.getUserName());
				final JSONObject controllers = new JSONObject();
				controllers.put("controller1", userData.getController1().getName());
				controllers.put("controller2", userData.getController2().getName());
				userObject.put("controllers", controllers);
				usersArray.put(userObject);
			}
		}
		initMessage.put("users", usersArray);
		enqueue(userID, initMessage);

		// send current state of landscape to new user
		sendLandscape(userID);
	}

	/**
	 * Sends a message (usually JSON as a string) to all connected users
	 *
	 * @param msg
	 *            The message which all users should receive
	 */
	public void broadcastAll(final JSONObject msg) {
		for (final long userID : users.keySet()) {
			enqueue(userID, msg);
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
	public void broadcastAllBut(final JSONObject msg, final long userID) {
		for (final long id : users.keySet()) {
			if (userID != id)
				enqueue(id, msg);
		}
	}

	@Override
	public void onClose(final WebSocket conn, final int code, final String reason, final boolean remote) {
		final long id = getIDByWebSocket(conn);
		if (id != -1) {
			conns.remove(id);
			users.remove(id);
			queues.remove(id);
		}
		LOGGER.info("Closed connection to " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
	}

	@Override
	public void onMessage(final WebSocket conn, final String message) {
		LOGGER.info("Message from client: " + message);
		// new Thread(() -> {
		final JSONArray queue = new JSONArray(message);
		for (int i = 0; i < queue.length(); i++) {
			final JSONObject JSONmessage = queue.getJSONObject(i);
			final String event = JSONmessage.getString("event");
			final long id = getIDByWebSocket(conn);

			if (event.equals("receive_user_positions")) {
				JSONmessage.put("id", id);
				broadcastAllBut(JSONmessage, id);
			} else if (event.equals("receive_connect_request")) {
				final String name = JSONmessage.getString("name");
				final UserModel user = users.get(id);
				user.setUserName(name);
				userConnected(id, name);
			} else if (event.equals("receive_user_controllers")) {
				final UserModel user = users.get(id);
				if (JSONmessage.has("connect")) {
					final JSONObject controllers = JSONmessage.getJSONObject("connect");
					if (controllers.has("controller1")) {
						user.getController1().setName(controllers.getString("controller1"));
					}
					if (controllers.has("controller2")) {
						user.getController2().setName(controllers.getString("controller2"));
					}
				}
				if (JSONmessage.has("disconnect")) {
					final JSONArray controllers = JSONmessage.getJSONArray("disconnect");
					for (int j = 0; j < controllers.length(); j++) {
						if (controllers.get(j) == "controller1") {
							user.getController1().setName(null);
						}
						if (controllers.get(j) == "controller2") {
							user.getController2().setName(null);
						}
					}
				}
				JSONmessage.put("id", id);
				broadcastAllBut(JSONmessage, id);
			}
		}
		// }).start();

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
		ex.printStackTrace();
		if (conn != null) {
			final long clientID = getIDByWebSocket(conn);
			conns.remove(clientID);
			users.remove(clientID);
			queues.remove(clientID);
			LOGGER.info("ERROR from " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
		}
	}

	@Override
	public void onStart() {
		LOGGER.info("Server has started");
	}

}

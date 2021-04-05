package net.explorviz.extension.vr.message;

import static org.assertj.core.api.Assertions.assertThat;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.websocket.Decoder;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import net.explorviz.extension.vr.message.receivable.AppClosedMessage;
import net.explorviz.extension.vr.message.receivable.AppOpenedMessage;
import net.explorviz.extension.vr.message.receivable.ComponentUpdateMessage;
import net.explorviz.extension.vr.message.receivable.ObjectMovedMessage;
import net.explorviz.extension.vr.message.receivable.ObjectReleasedMessage;
import net.explorviz.extension.vr.message.receivable.SpectatingUpdateMessage;
import net.explorviz.extension.vr.message.receivable.UserControllersMessage;
import net.explorviz.extension.vr.message.receivable.UserControllersMessage.Controllers;
import net.explorviz.extension.vr.message.receivable.UserPositionsMessage;
import net.explorviz.extension.vr.message.respondable.ObjectGrabbedResponse;
import net.explorviz.extension.vr.message.sendable.SelfConnectedMessage;
import net.explorviz.extension.vr.message.sendable.InitialLandscapeMessage;
import net.explorviz.extension.vr.message.sendable.UserConnectedMessage;
import net.explorviz.extension.vr.message.sendable.UserDisconnectedMessage;

public class SendableMessageEncoderTest {
	/**
	 * A custom comparator for strings that ignores whitespace.
	 */
	private static Comparator<String> ignoreWhitespace = new Comparator<>() {
		@Override
		public int compare(String s1, String s2) {
			return s1.replaceAll("\\s+", "").compareTo(s2.replaceAll("\\s+", ""));
		}
	};

	private SendableMessageEncoder encoder;

	@BeforeEach
	void initEncodwr() {
		encoder = new SendableMessageEncoder();
		encoder.init(new EndpointConfig() {
			@Override
			public Map<String, Object> getUserProperties() {
				return new HashMap<>();
			}

			@Override
			public List<Class<? extends Encoder>> getEncoders() {
				return Arrays.asList(SendableMessageEncoder.class);
			}

			@Override
			public List<Class<? extends Decoder>> getDecoders() {
				return new ArrayList<>();
			}
		});
	}

	@AfterEach
	void destroyDecoder() {
		encoder.destroy();
		encoder = null;
	}

	@Test
	public void testForwardedAppClosesdMessage() throws EncodeException, IOException {
		final var message = new AppClosedMessage();
		message.setNonce(42);
		message.setAppID("foo");
		final var actual = encoder.encodeMessage(new ForwardedMessage("alice", message));
		final var expected = "{" //
				+ "  \"event\": \"forward\"," //
				+ "  \"userID\": \"alice\"," //
				+ "  \"originalMessage\": {" //
				+ "    \"event\": \"app_closed\"," //
				+ "    \"nonce\": 42," //
				+ "    \"appID\": \"foo\"" //
				+ "  }" //
				+ "}";
		assertThat(actual).usingComparator(ignoreWhitespace).isEqualTo(expected);
	}

	@Test
	public void testObjectGrabbedResponse() throws EncodeException, IOException {
		final var message = new ResponseMessage(1, new ObjectGrabbedResponse(true));
		final var actual = encoder.encodeMessage(message);
		final var expected = "{" //
				+ "  \"event\": \"response\"," //
				+ "  \"nonce\": 1," //
				+ "  \"response\": {" //
				+ "    \"event\": \"object_grabbed\"," //
				+ "    \"isSuccess\": true" //
				+ "  }" //
				+ "}";
		assertThat(actual).usingComparator(ignoreWhitespace).isEqualTo(expected);
	}

	@Test
	public void testForwardedAppOpenedMessage() throws EncodeException, IOException {
		final var message = new AppOpenedMessage();
		message.setId("foo");
		message.setPosition(new double[] { 1.0, 2.0, 3.0 });
		message.setQuaternion(new double[] { 1.0, 2.0, 3.0, 4.0 });
		message.setScale(new double[] { 1.0, 1.0, 1.0 });
		final var actual = encoder.encodeMessage(new ForwardedMessage("alice", message));
		final var expected = "{" //
				+ "  \"event\": \"forward\"," //
				+ "  \"userID\": \"alice\"," //
				+ "  \"originalMessage\": {" //
				+ "    \"event\": \"app_opened\"," //
				+ "    \"id\": \"foo\"," //
				+ "    \"position\": [1.0, 2.0, 3.0]," //
				+ "    \"quaternion\": [1.0, 2.0, 3.0, 4.0]," //
				+ "    \"scale\": [1.0, 1.0, 1.0]" //
				+ "  }" //
				+ "}";
		assertThat(actual).usingComparator(ignoreWhitespace).isEqualTo(expected);
	}

	@Test
	public void testForwardedObjectReleasedMessage() throws EncodeException, IOException {
		final var message = new ObjectReleasedMessage();
		message.setObjectId("foo");
		final var actual = encoder.encodeMessage(new ForwardedMessage("alice", message));
		final var expected = "{" //
				+ "  \"event\": \"forward\"," //
				+ "  \"userID\": \"alice\"," //
				+ "  \"originalMessage\": {" //
				+ "    \"event\": \"object_released\"," //
				+ "    \"objectId\": \"foo\"" //
				+ "  }" //
				+ "}";
		assertThat(actual).usingComparator(ignoreWhitespace).isEqualTo(expected);
	}

	@Test
	public void testForwardedObjectMovedMessage() throws EncodeException, IOException {
		final var message = new ObjectMovedMessage();
		message.setObjectId("foo");
		message.setPosition(new double[] { 1.0, 2.0, 3.0 });
		message.setQuaternion(new double[] { 1.0, 2.0, 3.0, 4.0 });
		message.setScale(new double[] { 1.0, 1.0, 1.0 });
		final var actual = encoder.encodeMessage(new ForwardedMessage("alice", message));
		final var expected = "{" //
				+ "  \"event\": \"forward\"," //
				+ "  \"userID\": \"alice\"," //
				+ "  \"originalMessage\": {" //
				+ "    \"event\": \"object_moved\"," //
				+ "    \"objectId\": \"foo\"," //
				+ "    \"position\": [1.0, 2.0, 3.0]," //
				+ "    \"quaternion\": [1.0, 2.0, 3.0, 4.0]," //
				+ "    \"scale\": [1.0, 1.0, 1.0]" //
				+ "  }" //
				+ "}";
		assertThat(actual).usingComparator(ignoreWhitespace).isEqualTo(expected);
	}

	@Test
	public void testForwardedComponentUpdateMessage() throws EncodeException, IOException {
		final var message = new ComponentUpdateMessage();
		message.setAppID("foo");
		message.setComponentID("bar");
		message.setIsOpened(true);
		message.setIsFoundation(true);
		final var actual = encoder.encodeMessage(new ForwardedMessage("alice", message));
		final var expected = "{" //
				+ "  \"event\": \"forward\"," //
				+ "  \"userID\": \"alice\"," //
				+ "  \"originalMessage\": {" //
				+ "    \"event\": \"component_update\"," //
				+ "    \"appID\": \"foo\"," //
				+ "    \"componentID\": \"bar\"," //
				+ "    \"isOpened\": true," //
				+ "    \"isFoundation\": true" //
				+ "  }" //
				+ "}";
		assertThat(actual).usingComparator(ignoreWhitespace).isEqualTo(expected);
	}

	@Test
	public void testForwardedSpectatingUpdateMessage() throws EncodeException, IOException {
		final var message = new SpectatingUpdateMessage();
		message.setUserID("foo");
		message.setIsSpectating(true);
		message.setSpectatedUser("bar");
		final var actual = encoder.encodeMessage(new ForwardedMessage("alice", message));
		final var expected = "{" //
				+ "  \"event\": \"forward\"," //
				+ "  \"userID\": \"alice\"," //
				+ "  \"originalMessage\": {" //
				+ "    \"event\": \"spectating_update\"," //
				+ "    \"userID\": \"foo\"," //
				+ "    \"isSpectating\": true," //
				+ "    \"spectatedUser\": \"bar\"" //
				+ "  }" //
				+ "}";
		assertThat(actual).usingComparator(ignoreWhitespace).isEqualTo(expected);
	}

	@Test
	public void testForwardedUserControllersMessage() throws EncodeException, IOException {
		final var message = new UserControllersMessage();
		message.setConnect(new Controllers());
		message.getConnect().setController1("oculus-left");
		message.getConnect().setController2("oculus-right");
		message.setDisconnect(new Controllers());
		message.getDisconnect().setController1("vive-left");
		message.getDisconnect().setController2("vive-right");
		final var actual = encoder.encodeMessage(new ForwardedMessage("alice", message));
		final var expected = "{" //
				+ "  \"event\": \"forward\"," //
				+ "  \"userID\": \"alice\"," //
				+ "  \"originalMessage\": {" //
				+ "    \"event\": \"user_controllers\"," //
				+ "    \"connect\": {" //
				+ "      \"controller1\": \"oculus-left\"," //
				+ "      \"controller2\": \"oculus-right\"" //
				+ "    }," //
				+ "    \"disconnect\": {" //
				+ "      \"controller1\": \"vive-left\"," //
				+ "      \"controller2\": \"vive-right\"" //
				+ "    }" //
				+ "  }" //
				+ "}";
		assertThat(actual).usingComparator(ignoreWhitespace).isEqualTo(expected);
	}

	@Test
	public void testForwardedUserPositionsMessage() throws EncodeException, IOException {
		final var message = new UserPositionsMessage();
		message.setController1(new UserPositionsMessage.ControllerPose());
		message.getController1().setPosition(new double[] { 1.0, 2.0, 3.0 });
		message.getController1().setQuaternion(new double[] { 1.0, 2.0, 3.0, 4.0 });
		message.getController1().setIntersection(new double[] { 5.0, 6.0, 7.0 });
		message.setController2(new UserPositionsMessage.ControllerPose());
		message.getController2().setPosition(new double[] { 1.0, 2.0, 3.0 });
		message.getController2().setQuaternion(new double[] { 1.0, 2.0, 3.0, 4.0 });
		message.getController2().setIntersection(new double[] { 5.0, 6.0, 7.0 });
		message.setCamera(new UserPositionsMessage.Pose());
		message.getCamera().setPosition(new double[] { 1.0, 2.0, 3.0 });
		message.getCamera().setQuaternion(new double[] { 1.0, 2.0, 3.0, 4.0 });
		message.setTime(new Date(884300400000L));
		final var actual = encoder.encodeMessage(new ForwardedMessage("alice", message));
		final var expected = "{" //
				+ "  \"event\": \"forward\"," //
				+ "  \"userID\": \"alice\"," //
				+ "  \"originalMessage\": {" //
				+ "    \"event\": \"user_positions\"," //
				+ "    \"controller1\": {" //
				+ "      \"position\": [1.0, 2.0, 3.0]," //
				+ "      \"quaternion\": [1.0, 2.0, 3.0, 4.0]," //
				+ "      \"intersection\": [5.0, 6.0, 7.0]" //
				+ "    }," //
				+ "    \"controller2\": {" //
				+ "      \"position\": [1.0, 2.0, 3.0]," //
				+ "      \"quaternion\": [1.0, 2.0, 3.0, 4.0]," //
				+ "      \"intersection\": [5.0, 6.0, 7.0]" //
				+ "    }," //
				+ "    \"camera\": {" //
				+ "      \"position\": [1.0, 2.0, 3.0]," //
				+ "      \"quaternion\": [1.0, 2.0, 3.0, 4.0]" //
				+ "    }," //
				+ "    \"time\": 884300400000" //
				+ "  }" //
				+ "}";
		assertThat(actual).usingComparator(ignoreWhitespace).isEqualTo(expected);
	}

	@Test
	public void testSelfConnectedMessage() throws EncodeException, IOException {
		final var message = new SelfConnectedMessage();

		// Add current user.
		message.setSelf(new SelfConnectedMessage.User());
		message.getSelf().setId("foo");
		message.getSelf().setName("alice");
		message.getSelf().setColor(new Color(0, 42, 148));

		// Add one other user.
		message.setUsers(new SelfConnectedMessage.OtherUser[] { new SelfConnectedMessage.OtherUser() });
		message.getUsers()[0].setId("bar");
		message.getUsers()[0].setName("bob");
		message.getUsers()[0].setColor(new Color(148, 42, 0));
		message.getUsers()[0].setControllers(new SelfConnectedMessage.Controllers());
		message.getUsers()[0].getControllers().setController1("baz");
		message.getUsers()[0].getControllers().setController2("qux");

		final var actual = encoder.encodeMessage(message);
		final var expected = "{" //
				+ "  \"event\": \"self_connected\"," //
				+ "  \"self\": {" //
				+ "    \"id\": \"foo\"," //
				+ "    \"name\": \"alice\"," //
				+ "    \"color\": [0.0, 0.16470588235294117, 0.5803921568627451]" //
				+ "  }," //
				+ "  \"users\": [{" //
				+ "    \"id\": \"bar\"," //
				+ "    \"name\": \"bob\"," //
				+ "    \"color\": [0.5803921568627451, 0.16470588235294117, 0.0]," //
				+ "    \"controllers\": {" //
				+ "      \"controller1\": \"baz\"," //
				+ "      \"controller2\": \"qux\"" //
				+ "    }" //
				+ "  }]" //
				+ "}";
		assertThat(actual).usingComparator(ignoreWhitespace).isEqualTo(expected);
	}

	@Test
	public void testUserConnectedMessage() throws EncodeException, IOException {
		final var message = new UserConnectedMessage();
		message.setId("foo");
		message.setName("bar");
		message.setColor(new Color(0, 42, 148));
		final var actual = encoder.encodeMessage(message);
		final var expected = "{" //
				+ "  \"event\": \"user_connected\"," //
				+ "  \"id\": \"foo\"," //
				+ "  \"name\": \"bar\"," //
				+ "  \"color\": [0.0, 0.16470588235294117, 0.5803921568627451]" //
				+ "}";
		assertThat(actual).usingComparator(ignoreWhitespace).isEqualTo(expected);
	}

	@Test
	public void testUserDisconnectedMessage() throws EncodeException, IOException {
		final var message = new UserDisconnectedMessage();
		message.setId("foo");
		final var actual = encoder.encodeMessage(message);
		final var expected = "{ \"event\": \"user_disconnect\", \"id\": \"foo\" }";
		assertThat(actual).usingComparator(ignoreWhitespace).isEqualTo(expected);
	}

	@Test
	public void testInitialLandscapeMessage() throws EncodeException, IOException {
		final var message = new InitialLandscapeMessage();

		// Create a test app.
		message.setOpenApps(new InitialLandscapeMessage.App[] { new InitialLandscapeMessage.App() });
		message.getOpenApps()[0].setId("baz");
		message.getOpenApps()[0].setPosition(new double[] { 1.0, 2.0, 3.0 });
		message.getOpenApps()[0].setQuaternion(new double[] { 1.0, 2.0, 3.0, 4.0 });
		message.getOpenApps()[0].setScale(new double[] { 1.0, 1.0, 1.0 });
		message.getOpenApps()[0].setOpenComponents(new String[] { "x", "y", "z" });

		// Create a highlighted component.
		message.getOpenApps()[0].setHighlightedComponents(
				new InitialLandscapeMessage.HighlightingObject[] { new InitialLandscapeMessage.HighlightingObject() });
		message.getOpenApps()[0].getHighlightedComponents()[0].setUserID("alice");
		message.getOpenApps()[0].getHighlightedComponents()[0].setAppID("baz");
		message.getOpenApps()[0].getHighlightedComponents()[0].setEntityType("v");
		message.getOpenApps()[0].getHighlightedComponents()[0].setEntityID("w");
		message.getOpenApps()[0].getHighlightedComponents()[0].setHighlighted(true);

		// Set landscape position and rotation.
		message.setLandscape(new InitialLandscapeMessage.Landscape());
		message.getLandscape().setLandscapeToken("foo");
		message.getLandscape().setTimestamp(884345696789L);
		message.getLandscape().setPosition(new double[] { 1.0, 2.0, 3.0 });
		message.getLandscape().setQuaternion(new double[] { 1.0, 2.0, 3.0, 4.0 });
		message.getLandscape().setScale(new double[] { 1.0, 1.0, 1.0 });

		// Create a detached menu.
		message.setDetachedMenus(
				new InitialLandscapeMessage.DetachedMenu[] { new InitialLandscapeMessage.DetachedMenu() });
		message.getDetachedMenus()[0].setObjectId("menu");
		message.getDetachedMenus()[0].setEntityType("v");
		message.getDetachedMenus()[0].setEntityId("w");
		message.getDetachedMenus()[0].setPosition(new double[] { 1.0, 2.0, 3.0 });
		message.getDetachedMenus()[0].setQuaternion(new double[] { 1.0, 2.0, 3.0, 4.0 });
		message.getDetachedMenus()[0].setScale(new double[] { 1.0, 1.0, 1.0 });

		final var actual = encoder.encodeMessage(message);
		final var expected = "{" //
				+ "  \"event\": \"landscape\"," //
				+ "  \"openApps\": [{" //
				+ "    \"id\": \"baz\"," //
				+ "    \"position\": [1.0, 2.0, 3.0]," //
				+ "    \"quaternion\": [1.0, 2.0, 3.0, 4.0]," //
				+ "    \"scale\": [1.0, 1.0, 1.0]," //
				+ "    \"openComponents\": [\"x\", \"y\", \"z\"]," //
				+ "    \"highlightedComponents\": [{" //
				+ "      \"userID\": \"alice\"," //
				+ "      \"appID\": \"baz\"," //
				+ "      \"entityType\": \"v\"," //
				+ "      \"entityID\": \"w\"," //
				+ "      \"isHighlighted\": true" //
				+ "    }]" //
				+ "  }]," //
				+ "  \"landscape\": {" //
				+ "    \"landscapeToken\": \"foo\"," //
				+ "    \"timestamp\": 884345696789," //
				+ "    \"position\": [1.0, 2.0, 3.0]," //
				+ "    \"quaternion\": [1.0, 2.0, 3.0, 4.0]," //
				+ "    \"scale\": [1.0, 1.0, 1.0]" //
				+ "  }," //
				+ "  \"detachedMenus\": [{" //
				+ "    \"objectId\": \"menu\"," //
				+ "    \"entityType\": \"v\"," //
				+ "    \"entityId\": \"w\"," //
				+ "    \"position\": [1.0, 2.0, 3.0]," //
				+ "    \"quaternion\": [1.0, 2.0, 3.0, 4.0]," //
				+ "    \"scale\": [1.0, 1.0, 1.0]" //
				+ "  }]" //
				+ "}";
		assertThat(actual).usingComparator(ignoreWhitespace).isEqualTo(expected);
	}
}

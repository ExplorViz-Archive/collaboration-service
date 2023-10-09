package net.explorviz.vr.message;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.websocket.Decoder;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import net.explorviz.collaboration.message.ForwardedMessage;
import net.explorviz.collaboration.message.ResponseMessage;
import net.explorviz.collaboration.message.SendableMessageEncoder;
import net.explorviz.collaboration.message.receivable.AppClosedMessage;
import net.explorviz.collaboration.message.receivable.AppOpenedMessage;
import net.explorviz.collaboration.message.receivable.ComponentUpdateMessage;
import net.explorviz.collaboration.message.receivable.ObjectMovedMessage;
import net.explorviz.collaboration.message.receivable.ObjectReleasedMessage;
import net.explorviz.collaboration.message.receivable.SpectatingUpdateMessage;
import net.explorviz.collaboration.message.receivable.UserPositionsMessage;
import net.explorviz.collaboration.message.respondable.ObjectGrabbedResponse;
import net.explorviz.collaboration.message.sendable.InitialLandscapeMessage;
import net.explorviz.collaboration.message.sendable.SelfConnectedMessage;
import net.explorviz.collaboration.message.sendable.UserConnectedMessage;
import net.explorviz.collaboration.message.sendable.UserDisconnectedMessage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SendableMessageEncoderTest {

  private ObjectMapper mapper = new ObjectMapper();

  private SendableMessageEncoder encoder;

  @BeforeEach
  void initEncodwr() {
    this.encoder = new SendableMessageEncoder();
    this.encoder.init(new EndpointConfig() {
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

    this.mapper = new ObjectMapper();


  }

  @AfterEach
  void destroyDecoder() {
    this.encoder.destroy();
    this.encoder = null;
  }

  @Test
  public void testForwardedAppClosesdMessage() throws EncodeException, IOException {
    final var message = new AppClosedMessage();
    message.setNonce(42);
    message.setAppId("foo");
    final var actual = this.encoder.encodeMessage(new ForwardedMessage("alice", message));
    final var expected =
        "{ \"event\" : \"forward\" , \"userId\" : \"alice\" , \"originalMessage\" : { \"event\" : \"app_closed\" , \"nonce\" : 42 , \"appId\" : \"foo\" } }";
    assertEquals(this.mapper.readTree(expected), this.mapper.readTree(actual));
  }

  @Test
  public void testObjectGrabbedResponse() throws EncodeException, IOException {
    final var message = new ResponseMessage(1, new ObjectGrabbedResponse(true));
    final var actual = this.encoder.encodeMessage(message);
    final var expected = "{" //
        + "  \"event\": \"response\"," //
        + "  \"nonce\": 1," //
        + "  \"response\": {" //
        + "    \"event\": \"object_grabbed\"," //
        + "    \"isSuccess\": true" //
        + "  }" //
        + "}";
    assertEquals(this.mapper.readTree(expected), this.mapper.readTree(actual));
  }

  @Test
  public void testForwardedAppOpenedMessage() throws EncodeException, IOException {
    final var message = new AppOpenedMessage();
    message.setId("foo");
    message.setPosition(new double[] {1.0, 2.0, 3.0});
    message.setQuaternion(new double[] {1.0, 2.0, 3.0, 4.0});
    message.setScale(new double[] {1.0, 1.0, 1.0});
    final var actual = this.encoder.encodeMessage(new ForwardedMessage("alice", message));
    final var expected = "{" //
        + "  \"event\": \"forward\"," //
        + "  \"userId\": \"alice\"," //
        + "  \"originalMessage\": {" //
        + "    \"event\": \"app_opened\"," //
        + "    \"id\": \"foo\"," //
        + "    \"position\": [1.0, 2.0, 3.0]," //
        + "    \"quaternion\": [1.0, 2.0, 3.0, 4.0]," //
        + "    \"scale\": [1.0, 1.0, 1.0]" //
        + "  }" //
        + "}";
    assertEquals(this.mapper.readTree(expected), this.mapper.readTree(actual));
  }

  @Test
  public void testForwardedObjectReleasedMessage() throws EncodeException, IOException {
    final var message = new ObjectReleasedMessage();
    message.setObjectId("foo");
    final var actual = this.encoder.encodeMessage(new ForwardedMessage("alice", message));
    final var expected = "{" //
        + "  \"event\": \"forward\"," //
        + "  \"userId\": \"alice\"," //
        + "  \"originalMessage\": {" //
        + "    \"event\": \"object_released\"," //
        + "    \"objectId\": \"foo\"" //
        + "  }" //
        + "}";
    assertEquals(this.mapper.readTree(expected), this.mapper.readTree(actual));
  }

  @Test
  public void testForwardedObjectMovedMessage() throws EncodeException, IOException {
    final var message = new ObjectMovedMessage();
    message.setObjectId("foo");
    message.setPosition(new double[] {1.0, 2.0, 3.0});
    message.setQuaternion(new double[] {1.0, 2.0, 3.0, 4.0});
    message.setScale(new double[] {1.0, 1.0, 1.0});
    final var actual = this.encoder.encodeMessage(new ForwardedMessage("alice", message));
    final var expected = "{" //
        + "  \"event\": \"forward\"," //
        + "  \"userId\": \"alice\"," //
        + "  \"originalMessage\": {" //
        + "    \"event\": \"object_moved\"," //
        + "    \"objectId\": \"foo\"," //
        + "    \"position\": [1.0, 2.0, 3.0]," //
        + "    \"quaternion\": [1.0, 2.0, 3.0, 4.0]," //
        + "    \"scale\": [1.0, 1.0, 1.0]" //
        + "  }" //
        + "}";
    assertEquals(this.mapper.readTree(expected), this.mapper.readTree(actual));
  }

  @Test
  public void testForwardedComponentUpdateMessage() throws EncodeException, IOException {
    final var message = new ComponentUpdateMessage();
    message.setAppId("foo");
    message.setComponentId("bar");
    message.setIsOpened(true);
    message.setIsFoundation(true);
    final var actual = this.encoder.encodeMessage(new ForwardedMessage("alice", message));
    final var expected = "{" //
        + "  \"event\": \"forward\"," //
        + "  \"userId\": \"alice\"," //
        + "  \"originalMessage\": {" //
        + "    \"event\": \"component_update\"," //
        + "    \"appId\": \"foo\"," //
        + "    \"componentId\": \"bar\"," //
        + "    \"forward\": false," //
        + "    \"isOpened\": true," //
        + "    \"isFoundation\": true" //
        + "  }" //
        + "}";
    assertEquals(this.mapper.readTree(expected), this.mapper.readTree(actual));
  }

  @Test
  public void testForwardedSpectatingUpdateMessage() throws EncodeException, IOException {
    final var message = new SpectatingUpdateMessage();
    message.setUserId("foo");
    message.setIsSpectating(true);
    message.setSpectatedUser("bar");
    final var actual = this.encoder.encodeMessage(new ForwardedMessage("alice", message));
    final var expected = "{" //
        + "  \"event\": \"forward\"," //
        + "  \"userId\": \"alice\"," //
        + "  \"originalMessage\": {" //
        + "    \"event\": \"spectating_update\"," //
        + "    \"userId\": \"foo\"," //
        + "    \"isSpectating\": true," //
        + "    \"spectatedUser\": \"bar\"" //
        + "  }" //
        + "}";

    assertEquals(this.mapper.readTree(expected), this.mapper.readTree(actual));
  }

  @Test
  public void testForwardedUserPositionsMessage() throws EncodeException, IOException {
    final var message = new UserPositionsMessage();
    message.setController1(new UserPositionsMessage.ControllerPose());
    message.getController1().setPosition(new double[] {1.0, 2.0, 3.0});
    message.getController1().setQuaternion(new double[] {1.0, 2.0, 3.0, 4.0});
    message.getController1().setIntersection(new double[] {5.0, 6.0, 7.0});
    message.setController2(new UserPositionsMessage.ControllerPose());
    message.getController2().setPosition(new double[] {1.0, 2.0, 3.0});
    message.getController2().setQuaternion(new double[] {1.0, 2.0, 3.0, 4.0});
    message.getController2().setIntersection(new double[] {5.0, 6.0, 7.0});
    message.setCamera(new UserPositionsMessage.Pose());
    message.getCamera().setPosition(new double[] {1.0, 2.0, 3.0});
    message.getCamera().setQuaternion(new double[] {1.0, 2.0, 3.0, 4.0});
    final var actual = this.encoder.encodeMessage(new ForwardedMessage("alice", message));
    final var expected = "{" //
        + "  \"event\": \"forward\"," //
        + "  \"userId\": \"alice\"," //
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
        + "    }" //
        + "  }" //
        + "}";
    assertEquals(this.mapper.readTree(expected), this.mapper.readTree(actual));
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
    message.setUsers(new SelfConnectedMessage.OtherUser[] {new SelfConnectedMessage.OtherUser()});
    message.getUsers()[0].setId("bar");
    message.getUsers()[0].setName("bob");
    message.getUsers()[0].setColor(new Color(148, 42, 0));
    message.getUsers()[0].setPosition(new double[] {1.0, 2.0, 3.0});
    message.getUsers()[0].setQuaternion(new double[] {1.0, 2.0, 3.0, 4.0});
    message.getUsers()[0].setControllers(
        new SelfConnectedMessage.Controller[] {new SelfConnectedMessage.Controller()});
    message.getUsers()[0].getControllers()[0].setControllerId(1);
    message.getUsers()[0].getControllers()[0].setAssetUrl("baz");
    message.getUsers()[0].getControllers()[0].setPosition(new double[] {1.0, 2.0, 3.0});
    message.getUsers()[0].getControllers()[0].setQuaternion(new double[] {1.0, 2.0, 3.0, 4.0});
    message.getUsers()[0].getControllers()[0].setIntersection(new double[] {1.0, 2.0, 3.0});

    final var actual = this.encoder.encodeMessage(message);
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
        + "    \"controllers\": [{" //
        + "      \"controllerId\": 1," //
        + "      \"assetUrl\": \"baz\"," //
        + "      \"position\": [1.0, 2.0, 3.0]," //
        + "      \"quaternion\": [1.0, 2.0, 3.0, 4.0]," //
        + "      \"intersection\": [1.0, 2.0, 3.0]" //
        + "    }]," //
        + "    \"position\": [1.0, 2.0, 3.0]," //
        + "    \"quaternion\": [1.0, 2.0, 3.0, 4.0]" //
        + "  }]" //
        + "}";
    assertEquals(this.mapper.readTree(expected), this.mapper.readTree(actual));
  }

  @Test
  public void testUserConnectedMessage() throws EncodeException, IOException {
    final var message = new UserConnectedMessage();
    message.setId("foo");
    message.setName("bar");
    message.setColor(new Color(0, 42, 148));
    message.setPosition(new double[] {1.0, 2.0, 3.0});
    message.setQuaternion(new double[] {1.0, 2.0, 3.0, 4.0});
    final var actual = this.encoder.encodeMessage(message);
    final var expected = "{" //
        + "  \"event\": \"user_connected\"," //
        + "  \"id\": \"foo\"," //
        + "  \"name\": \"bar\"," //
        + "  \"color\": [0.0, 0.16470588235294117, 0.5803921568627451]," //
        + "  \"position\": [1.0, 2.0, 3.0]," //
        + "  \"quaternion\": [1.0, 2.0, 3.0, 4.0]" //
        + "}";
    assertEquals(this.mapper.readTree(expected), this.mapper.readTree(actual));
  }

  @Test
  public void testUserDisconnectedMessage() throws EncodeException, IOException {
    final var message = new UserDisconnectedMessage();
    message.setId("foo");
    final var actual = this.encoder.encodeMessage(message);
    final var expected = "{ \"event\": \"user_disconnect\", \"highlightedComponents\": [], \"id\": \"foo\" }";
    assertEquals(this.mapper.readTree(expected), this.mapper.readTree(actual));
  }

  @Test
  public void testInitialLandscapeMessage() throws EncodeException, IOException {
    final var message = new InitialLandscapeMessage();

    // Create a test app.
    message.setOpenApps(new InitialLandscapeMessage.App[] {new InitialLandscapeMessage.App()});
    message.getOpenApps()[0].setId("baz");
    message.getOpenApps()[0].setPosition(new double[] {1.0, 2.0, 3.0});
    message.getOpenApps()[0].setQuaternion(new double[] {1.0, 2.0, 3.0, 4.0});
    message.getOpenApps()[0].setScale(new double[] {1.0, 1.0, 1.0});
    message.getOpenApps()[0].setOpenComponents(new String[] {"x", "y", "z"});

    // Create a highlighted component.
    message.getOpenApps()[0]
        .setHighlightedComponents(new InitialLandscapeMessage.HighlightingObject[] {
            new InitialLandscapeMessage.HighlightingObject()});
    message.getOpenApps()[0].getHighlightedComponents()[0].setUserId("alice");
    message.getOpenApps()[0].getHighlightedComponents()[0].setAppId("baz");
    message.getOpenApps()[0].getHighlightedComponents()[0].setEntityType("v");
    message.getOpenApps()[0].getHighlightedComponents()[0].setEntityId("w");
    message.getOpenApps()[0].getHighlightedComponents()[0].setHighlighted(true);
    message.getOpenApps()[0].getHighlightedComponents()[0].setColor(new Color(255, 255, 255));

    // Set landscape position and rotation.
    message.setLandscape(new InitialLandscapeMessage.Landscape());
    message.getLandscape().setLandscapeToken("foo");
    message.getLandscape().setTimestamp(884345696789L);

    // Create a highlighted extern communcation link.
    message.setHighlightedExternCommunicationLinks(new InitialLandscapeMessage.HighlightingObject[] {
      new InitialLandscapeMessage.HighlightingObject()});
    message.getHighlightedExternCommunicationLinks()[0].setAppId("");
    message.getHighlightedExternCommunicationLinks()[0].setEntityId("m");
    message.getHighlightedExternCommunicationLinks()[0].setEntityType("n");
    message.getHighlightedExternCommunicationLinks()[0].setHighlighted(true);
    message.getHighlightedExternCommunicationLinks()[0].setColor(new Color(255, 255, 255));
    message.getHighlightedExternCommunicationLinks()[0].setUserId("alice");

    // Create a detached menu.
    message.setDetachedMenus(
        new InitialLandscapeMessage.DetachedMenu[] {new InitialLandscapeMessage.DetachedMenu()});
    message.getDetachedMenus()[0].setObjectId("menu");
    message.getDetachedMenus()[0].setEntityType("v");
    message.getDetachedMenus()[0].setEntityId("w");
    message.getDetachedMenus()[0].setPosition(new double[] {1.0, 2.0, 3.0});
    message.getDetachedMenus()[0].setQuaternion(new double[] {1.0, 2.0, 3.0, 4.0});
    message.getDetachedMenus()[0].setScale(new double[] {1.0, 1.0, 1.0});

    final var actual = this.encoder.encodeMessage(message);
    final var expected = "{" //
        + "  \"event\": \"landscape\"," //
        + "  \"openApps\": [{" //
        + "    \"id\": \"baz\"," //
        + "    \"position\": [1.0, 2.0, 3.0]," //
        + "    \"quaternion\": [1.0, 2.0, 3.0, 4.0]," //
        + "    \"scale\": [1.0, 1.0, 1.0]," //
        + "    \"openComponents\": [\"x\", \"y\", \"z\"]," //
        + "    \"highlightedComponents\": [{" //
        + "      \"userId\": \"alice\"," //
        + "      \"color\": [255.0, 255.0, 255.0]," //
        + "      \"appId\": \"baz\"," //
        + "      \"entityType\": \"v\"," //
        + "      \"entityId\": \"w\"," //
        + "      \"isHighlighted\": true" //
        + "    }]" //
        + "  }]," //
        + "  \"landscape\": {" //
        + "    \"landscapeToken\": \"foo\"," //
        + "    \"timestamp\": 884345696789" //
        + "  }," //
        + "  \"detachedMenus\": [{" //
        + "    \"objectId\": \"menu\"," //
        + "    \"userId\": null," //
        + "    \"detachId\": null," //
        + "    \"entityType\": \"v\"," //
        + "    \"entityId\": \"w\"," //
        + "    \"position\": [1.0, 2.0, 3.0]," //
        + "    \"quaternion\": [1.0, 2.0, 3.0, 4.0]," //
        + "    \"scale\": [1.0, 1.0, 1.0]" //
        + "  }]," //
        + " \"highlightedExternCommunicationLinks\": [{" //
        + "      \"userId\": \"alice\"," //
        + "      \"color\": [255.0, 255.0, 255.0]," //
        + "      \"appId\": \"\"," //
        + "      \"entityType\": \"n\"," //
        + "      \"entityId\": \"m\"," //
        + "      \"isHighlighted\": true" //
        + " }]" //
        + "}";
    assertEquals(this.mapper.readTree(expected), this.mapper.readTree(actual));
  }
}

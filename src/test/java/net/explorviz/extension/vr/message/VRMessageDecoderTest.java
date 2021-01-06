package net.explorviz.extension.vr.message;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

import net.explorviz.extension.vr.message.receivable.AppClosedMessage;
import net.explorviz.extension.vr.message.receivable.AppGrabbedMessage;
import net.explorviz.extension.vr.message.receivable.AppOpenedMessage;
import net.explorviz.extension.vr.message.receivable.AppReleasedMessage;
import net.explorviz.extension.vr.message.receivable.AppTranslatedMessage;
import net.explorviz.extension.vr.message.receivable.ComponentUpdateMessage;
import net.explorviz.extension.vr.message.receivable.LandscapePositionMessage;
import net.explorviz.extension.vr.message.receivable.NodegroupUpdateMessage;
import net.explorviz.extension.vr.message.receivable.SpectatingUpdateMessage;
import net.explorviz.extension.vr.message.receivable.SystemUpdateMessage;
import net.explorviz.extension.vr.message.receivable.UserControllersMessage;
import net.explorviz.extension.vr.message.receivable.UserControllersMessage.Controllers;
import net.explorviz.extension.vr.message.receivable.UserPositionsMessage;

public class VRMessageDecoderTest {
    private VRMessageDecoder decoder;

    @BeforeEach
    void initDecoder() {
        decoder = new VRMessageDecoder();
        decoder.init(new EndpointConfig() {
            @Override
            public Map<String, Object> getUserProperties() {
                return new HashMap<>();
            }

            @Override
            public List<Class<? extends Encoder>> getEncoders() {
                return new ArrayList<>();
            }

            @Override
            public List<Class<? extends Decoder>> getDecoders() {
                return Arrays.asList(VRMessageDecoder.class);
            }
        });
    }

    @AfterEach
    void destroyDecoder() {
        decoder.destroy();
        decoder = null;
    }

    @Test
    public void testTrailingToken() {
        final var json = "{ \"event\": \"app_closed\", \"appID\": \"foo\" } \"extra token\"";
        assertThrows(MismatchedInputException.class, () -> decoder.decodeMessage(json));
    }

    @Test
    public void testUnknownMessage() {
        final var json = "{ \"event\": \"unknown\" }";
        assertThrows(JsonMappingException.class, () -> decoder.decodeMessage(json));
    }

    @Test
    public void testMissingField() throws DecodeException, IOException {
        final var json = "{ \"event\": \"app_closed\" }";
        final var actual = decoder.decodeMessage(json);
        final var expected = new AppClosedMessage();
        expected.setEvent("app_closed");
        expected.setAppID(null);
        assertThat(actual).hasSameClassAs(expected).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    public void testExtraField() throws DecodeException, IOException {
        final var json = "{ \"event\": \"app_closed\", \"appID\": \"foo\", \"foo\": \"bar\" }";
        assertThrows(UnrecognizedPropertyException.class, () -> decoder.decodeMessage(json));
    }

    @Test
    public void testForwardedMessage() throws DecodeException, IOException {
        final var json = "{ \"event\": \"forward\", \"userID\": \"foo\", \"originalMessage\": { \"event\": \"app_closed\", \"appID\": \"bar\" } }";
        final var actual = decoder.decodeMessage(json);
        final var originalMessage = new AppClosedMessage();
        originalMessage.setEvent("app_closed");
        originalMessage.setAppID("bar");
        final var expected = new ForwardedMessage();
        expected.setEvent("forward");
        expected.setUserID("foo");
        expected.setOriginalMessage(originalMessage);
        assertThat(actual).hasSameClassAs(expected).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    public void testAppClosesdMessage() throws DecodeException, IOException {
        final var json = "{ \"event\": \"app_closed\", \"appID\": \"foo\" }";
        final var actual = decoder.decodeMessage(json);
        final var expected = new AppClosedMessage();
        expected.setEvent("app_closed");
        expected.setAppID("foo");
        assertThat(actual).hasSameClassAs(expected).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    public void testAppGrabbedMessage() throws DecodeException, IOException {
        final var json = "{ \"event\": \"app_grabbed\", \"appID\": \"foo\", \"appPosition\": [1.0, 2.0, 3.0],"
                + "  \"appQuaternion\": [1.0, 2.0, 3.0, 4.0],"
                + "  \"isGrabbedByController1\": true, \"controllerPosition\": [1.0, 2.0, 3.0],"
                + "  \"controllerQuaternion\": [1.0, 2.0, 3.0, 4.0] }";
        final var actual = decoder.decodeMessage(json);
        final var expected = new AppGrabbedMessage();
        expected.setEvent("app_grabbed");
        expected.setAppID("foo");
        expected.setAppPosition(new double[] { 1.0, 2.0, 3.0 });
        expected.setAppQuaternion(new double[] { 1.0, 2.0, 3.0, 4.0 });
        expected.setIsGrabbedByController1(true);
        expected.setControllerPosition(new double[] { 1.0, 2.0, 3.0 });
        expected.setControllerQuaternion(new double[] { 1.0, 2.0, 3.0, 4.0 });
        assertThat(actual).hasSameClassAs(expected).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    public void testAppOpenedMessage() throws DecodeException, IOException {
        final var json = "{ \"event\": \"app_opened\", \"id\": \"foo\", \"position\": [1.0, 2.0, 3.0], \"quaternion\": [1.0, 2.0, 3.0, 4.0] }";
        final var actual = decoder.decodeMessage(json);
        final var expected = new AppOpenedMessage();
        expected.setEvent("app_opened");
        expected.setId("foo");
        expected.setPosition(new double[] { 1.0, 2.0, 3.0 });
        expected.setQuaternion(new double[] { 1.0, 2.0, 3.0, 4.0 });
        assertThat(actual).hasSameClassAs(expected).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    public void testAppReleasedMessage() throws DecodeException, IOException {
        final var json = "{ \"event\": \"app_released\", \"id\": \"foo\",  \"position\": [1.0, 2.0, 3.0], \"quaternion\": [1.0, 2.0, 3.0, 4.0] }";
        final var actual = decoder.decodeMessage(json);
        final var expected = new AppReleasedMessage();
        expected.setEvent("app_released");
        expected.setId("foo");
        expected.setPosition(new double[] { 1.0, 2.0, 3.0 });
        expected.setQuaternion(new double[] { 1.0, 2.0, 3.0, 4.0 });
        assertThat(actual).hasSameClassAs(expected).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    public void testAppTranslatedMessage() throws DecodeException, IOException {
        final var json = "{ \"event\": \"app_translated\", \"appId\": \"foo\", \"direction\": [1.0, 2.0, 3.0], \"length\": 4.0 }";
        final var actual = decoder.decodeMessage(json);
        final var expected = new AppTranslatedMessage();
        expected.setEvent("app_translated");
        expected.setAppId("foo");
        expected.setDirection(new double[] { 1.0, 2.0, 3.0 });
        expected.setLength(4.0);
        assertThat(actual).hasSameClassAs(expected).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    public void testComponentUpdateMessage() throws DecodeException, IOException {
        final var json = "{ \"event\": \"component_update\", \"appID\": \"foo\", \"componentID\": \"bar\", \"isOpened\": true, \"isFoundation\": true }";
        final var actual = decoder.decodeMessage(json);
        final var expected = new ComponentUpdateMessage();
        expected.setEvent("component_update");
        expected.setAppID("foo");
        expected.setComponentID("bar");
        expected.setIsOpened(true);
        expected.setIsFoundation(true);
        assertThat(actual).hasSameClassAs(expected).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    public void testNodegroupUpdateMessage() throws DecodeException, IOException {
        final var json = "{ \"event\": \"nodegroup_update\", \"id\": \"foo\", \"isOpen\": true }";
        final var actual = decoder.decodeMessage(json);
        final var expected = new NodegroupUpdateMessage();
        expected.setEvent("nodegroup_update");
        expected.setId("foo");
        expected.setIsOpen(true);
        assertThat(actual).hasSameClassAs(expected).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    public void testLandscapePositionMessage() throws DecodeException, IOException {
        final var json = "{ \"event\": \"landscape_position\", \"deltaPosition\": [1.0, 2.0, 3.0], \"offset\": [1.0, 2.0, 3.0], \"quaternion\": [1.0, 2.0, 3.0, 4.0] }";
        final var actual = decoder.decodeMessage(json);
        final var expected = new LandscapePositionMessage();
        expected.setEvent("landscape_position");
        expected.setDeltaPosition(new double[] { 1.0, 2.0, 3.0 });
        expected.setOffset(new double[] { 1.0, 2.0, 3.0 });
        expected.setQuaternion(new double[] { 1.0, 2.0, 3.0, 4.0 });
        assertThat(actual).hasSameClassAs(expected).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    public void testSpectatingUpdateMessage() throws DecodeException, IOException {
        final var json = "{ \"event\": \"spectating_update\", \"userID\": \"foo\", \"isSpectating\": true, \"spectatedUser\": \"bar\" }";
        final var actual = decoder.decodeMessage(json);
        final var expected = new SpectatingUpdateMessage();
        expected.setEvent("spectating_update");
        expected.setUserID("foo");
        expected.setIsSpectating(true);
        expected.setSpectatedUser("bar");
        assertThat(actual).hasSameClassAs(expected).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    public void testSystemUpdateMessage() throws DecodeException, IOException {
        final var json = "{ \"event\": \"system_update\", \"id\": \"foo\", \"isOpen\": true }";
        final var actual = decoder.decodeMessage(json);
        final var expected = new SystemUpdateMessage();
        expected.setEvent("system_update");
        expected.setId("foo");
        expected.setIsOpen(true);
        assertThat(actual).hasSameClassAs(expected).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    public void testUserControllersMessage() throws DecodeException, IOException {
        final var json = "{ \"event\": \"user_controllers\", "
                + "\"connect\": {\"controller1\": \"oculus-left\", \"controller2\": \"oculus-right\"}, "
                + "\"disconnect\": {\"controller1\": \"vive-left\", \"controller2\": \"vive-right\"} }";
        final var actual = decoder.decodeMessage(json);
        final var expected = new UserControllersMessage();
        expected.setEvent("user_controllers");
        expected.setConnect(new Controllers());
        expected.getConnect().setController1("oculus-left");
        expected.getConnect().setController2("oculus-right");
        expected.setDisconnect(new Controllers());
        expected.getDisconnect().setController1("vive-left");
        expected.getDisconnect().setController2("vive-right");
        assertThat(actual).hasSameClassAs(expected).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    public void testUserPositionsMessage() throws DecodeException, IOException {
        final var json = "{ \"event\": \"user_positions\", "
                + "\"controller1\": { \"position\": [1.0, 2.0, 3.0], \"quaternion\": [1.0, 2.0, 3.0, 4.0] }, "
                + "\"controller2\": { \"position\": [1.0, 2.0, 3.0], \"quaternion\": [1.0, 2.0, 3.0, 4.0] }, "
                + "\"camera\": { \"position\": [1.0, 2.0, 3.0], \"quaternion\": [1.0, 2.0, 3.0, 4.0] },"
                + "\"time\": 884300400000}";
        final var actual = decoder.decodeMessage(json);
        final var expected = new UserPositionsMessage();
        expected.setEvent("user_positions");
        expected.setController1(new UserPositionsMessage.Pose());
        expected.getController1().setPosition(new double[] { 1.0, 2.0, 3.0 });
        expected.getController1().setQuaternion(new double[] { 1.0, 2.0, 3.0, 4.0 });
        expected.setController2(new UserPositionsMessage.Pose());
        expected.getController2().setPosition(new double[] { 1.0, 2.0, 3.0 });
        expected.getController2().setQuaternion(new double[] { 1.0, 2.0, 3.0, 4.0 });
        expected.setCamera(new UserPositionsMessage.Pose());
        expected.getCamera().setPosition(new double[] { 1.0, 2.0, 3.0 });
        expected.getCamera().setQuaternion(new double[] { 1.0, 2.0, 3.0, 4.0 });
        expected.setTime(new Date(884300400000L));
        assertThat(actual).hasSameClassAs(expected).usingRecursiveComparison().isEqualTo(expected);
    }
}

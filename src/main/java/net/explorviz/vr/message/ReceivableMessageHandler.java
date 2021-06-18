package net.explorviz.vr.message;

import net.explorviz.vr.message.receivable.*;

/**
 * Interface for classes that can handle {@link VrMessage}s.
 * 
 * Provides a double dispatch mechanism (visitor pattern) for messages.
 *
 * @param <R> The return type of the handler methods.
 * @param <A> Type for additional arguments of the handler methods.
 */
public interface ReceivableMessageHandler<R, A> {
	R handleAppClosedMessage(AppClosedMessage message, A arg);

	R handleObjectGrabbedMessage(ObjectGrabbedMessage message, A arg);

	R handleAppOpenedMessage(AppOpenedMessage message, A arg);

	R handleObjectReleasedMessage(ObjectReleasedMessage message, A arg);

	R handleComponentUpdateMessage(ComponentUpdateMessage message, A arg);

	R handleHighlightingUpdateMessage(HighlightingUpdateMessage message, A arg);

	R handleSpectatingUpdateMessage(SpectatingUpdateMessage message, A arg);

	R handleUserControllerConnectMessage(UserControllerConnectMessage userControllerConnectedMessage, A arg);

	R handleUserControllerDisconnectMessage(UserControllerDisconnectMessage userControllerDisconnectedMessage, A arg);

	R handleUserPositionsMessage(UserPositionsMessage message, A arg);

	R handleObjectMovedMessage(ObjectMovedMessage message, A arg);

	R handleMenuDetachedMessage(MenuDetachedMessage message, A arg);

	R handleDetachedMenuClosedMessage(DetachedMenuClosedMessage detachedMenuClosedMessage, A arg);

	R handlePingUpdateMessage(PingUpdateMessage message, A arg);

	R handleMousePingUpdateMessage(MousePingUpdateMessage message, A arg);

	R handleTimestampUpdateMessage(TimestampUpdateMessage message, A arg);
}

package net.explorviz.collaboration.message;

import net.explorviz.collaboration.message.receivable.AppClosedMessage;
import net.explorviz.collaboration.message.receivable.AppOpenedMessage;
import net.explorviz.collaboration.message.receivable.ComponentUpdateMessage;
import net.explorviz.collaboration.message.receivable.DetachedMenuClosedMessage;
import net.explorviz.collaboration.message.receivable.HeatmapUpdateMessage;
import net.explorviz.collaboration.message.receivable.HighlightingUpdateMessage;
import net.explorviz.collaboration.message.receivable.JoinVrMessage;
import net.explorviz.collaboration.message.receivable.MenuDetachedMessage;
import net.explorviz.collaboration.message.receivable.MousePingUpdateMessage;
import net.explorviz.collaboration.message.receivable.ObjectGrabbedMessage;
import net.explorviz.collaboration.message.receivable.ObjectMovedMessage;
import net.explorviz.collaboration.message.receivable.ObjectReleasedMessage;
import net.explorviz.collaboration.message.receivable.PingUpdateMessage;
import net.explorviz.collaboration.message.receivable.SpectatingUpdateMessage;
import net.explorviz.collaboration.message.receivable.TimestampUpdateMessage;
import net.explorviz.collaboration.message.receivable.UserControllerConnectMessage;
import net.explorviz.collaboration.message.receivable.UserControllerDisconnectMessage;
import net.explorviz.collaboration.message.receivable.UserPositionsMessage;

/**
 * Interface for classes that can handle {@link VrMessage}s.
 * <p>
 * Provides a double dispatch mechanism (visitor pattern) for messages.
 *
 * @param <R> The return type of the handler methods.
 * @param <A> Type for additional arguments of the handler methods.
 */
public interface ReceivableMessageHandler<R, A> { // NOPMD
  R handleAppClosedMessage(AppClosedMessage message, A arg);

  R handleObjectGrabbedMessage(ObjectGrabbedMessage message, A arg);

  R handleAppOpenedMessage(AppOpenedMessage message, A arg);

  R handleObjectReleasedMessage(ObjectReleasedMessage message, A arg);

  R handleComponentUpdateMessage(ComponentUpdateMessage message, A arg);

  R handleHighlightingUpdateMessage(HighlightingUpdateMessage message, A arg);

  R handleSpectatingUpdateMessage(SpectatingUpdateMessage message, A arg);

  R handleHeatmapUpdateMessage(HeatmapUpdateMessage message, A arg);

  R handleUserControllerConnectMessage(UserControllerConnectMessage userControllerConnectedMessage,
      A arg);

  R handleUserControllerDisconnectMessage(UserControllerDisconnectMessage userControllerDisconnMsg,
      A arg);

  R handleJoinVrMessage(JoinVrMessage joinVrMessage,
      A arg);

  R handleUserPositionsMessage(UserPositionsMessage message, A arg);

  R handleObjectMovedMessage(ObjectMovedMessage message, A arg);

  R handleMenuDetachedMessage(MenuDetachedMessage message, A arg);

  R handleDetachedMenuClosedMessage(DetachedMenuClosedMessage detachedMenuClosedMessage, A arg);

  R handlePingUpdateMessage(PingUpdateMessage message, A arg);

  R handleMousePingUpdateMessage(MousePingUpdateMessage message, A arg);

  R handleTimestampUpdateMessage(TimestampUpdateMessage message, A arg);
}

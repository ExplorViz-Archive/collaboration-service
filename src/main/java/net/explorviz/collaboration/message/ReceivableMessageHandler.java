package net.explorviz.collaboration.message;

import net.explorviz.collaboration.message.receivable.AllHighlightsResetMessage;
import net.explorviz.collaboration.message.receivable.AppClosedMessage;
import net.explorviz.collaboration.message.receivable.AppOpenedMessage;
import net.explorviz.collaboration.message.receivable.ChangeLogRemoveEntryMessage;
import net.explorviz.collaboration.message.receivable.ChangeLogRestoreEntriesMessage;
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
import net.explorviz.collaboration.message.receivable.RestructureCommunicationMessage;
import net.explorviz.collaboration.message.receivable.RestructureCopyAndPasteClassMessage;
import net.explorviz.collaboration.message.receivable.RestructureCopyAndPastePackageMessage;
import net.explorviz.collaboration.message.receivable.RestructureCreateOrDeleteMessage;
import net.explorviz.collaboration.message.receivable.RestructureCutAndInsertMessage;
import net.explorviz.collaboration.message.receivable.RestructureDeleteCommunicationMessage;
import net.explorviz.collaboration.message.receivable.RestructureDuplicateAppMessage;
import net.explorviz.collaboration.message.receivable.RestructureMessage;
import net.explorviz.collaboration.message.receivable.RestructureModeMessage;
import net.explorviz.collaboration.message.receivable.RestructureRenameOperationMessage;
import net.explorviz.collaboration.message.receivable.RestructureRestoreAppMessage;
import net.explorviz.collaboration.message.receivable.RestructureRestoreClassMessage;
import net.explorviz.collaboration.message.receivable.RestructureRestorePackageMessage;
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

  R handleRestructureModeMessage(RestructureModeMessage message, A arg);

  R handleRestructureMessage(RestructureMessage message, A arg);

  R handleRestructureCreateOrDeleteMessage(RestructureCreateOrDeleteMessage message, A arg);
  
  R handleRestructureCutAndInsertMessage(RestructureCutAndInsertMessage message, A arg);

  R handleRestructureCommunicationMessage(RestructureCommunicationMessage message, A arg);

  R handleRestructureCopyAndPasteClassMessage(RestructureCopyAndPasteClassMessage message, A arg);

  R handleRestructureCopyAndPastePackageMessage(
      RestructureCopyAndPastePackageMessage message, A arg);
  
  R handleRestructureDeleteCommunicationMessage(
      RestructureDeleteCommunicationMessage message, A arg);

  R handleRestructureDuplicateAppMessage(
      RestructureDuplicateAppMessage message, A arg);

  R handleRestructureRenameOperationMessage(RestructureRenameOperationMessage message, A arg);

  R handleRestructureRestoreAppMessage(RestructureRestoreAppMessage message, A arg);

  R handleRestructureRestorePackageMessage(RestructureRestorePackageMessage message, A arg);

  R handleRestructureRestoreClassMessage(RestructureRestoreClassMessage message, A arg);

  R handleChangeLogRemoveEntryMessage(ChangeLogRemoveEntryMessage message, A arg);
  
  R handleChangeLogRestoreEntriesMessage(ChangeLogRestoreEntriesMessage message, A arg);

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

  R handleAllHighlightsResetMessage(AllHighlightsResetMessage allHighlightsResetMessage, A arg);
}

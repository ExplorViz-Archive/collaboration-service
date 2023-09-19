package net.explorviz.collaboration.message;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeId;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
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
 * Base class for all messages that are exchanged via the websocket connection between the frontend
 * and VR service.
 * <p>
 * There should be a {@like Type} entry in the {@link JsonSubTypes} annotation for every receivable
 * message that maps the message's event name to its class object.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY, property = "event", visible = true)
@JsonSubTypes({@Type(value = AppClosedMessage.class, name = AppClosedMessage.EVENT),
    @Type(value = ObjectGrabbedMessage.class, name = ObjectGrabbedMessage.EVENT),
    @Type(value = AppOpenedMessage.class, name = AppOpenedMessage.EVENT),
    @Type(value = ObjectReleasedMessage.class, name = ObjectReleasedMessage.EVENT),
    @Type(value = ObjectMovedMessage.class, name = ObjectMovedMessage.EVENT),
    @Type(value = ComponentUpdateMessage.class, name = ComponentUpdateMessage.EVENT),
    @Type(value = HighlightingUpdateMessage.class, name = HighlightingUpdateMessage.EVENT),
    @Type(value = RestructureModeMessage.class, name = RestructureModeMessage.EVENT),
    @Type(value = RestructureMessage.class, name = RestructureMessage.EVENT),
    @Type(value = RestructureCreateOrDeleteMessage.class,
      name = RestructureCreateOrDeleteMessage.EVENT),
    @Type(value = RestructureCutAndInsertMessage.class,
      name = RestructureCutAndInsertMessage.EVENT),
    @Type(value = RestructureCommunicationMessage.class,
    name = RestructureCommunicationMessage.EVENT),
    @Type(value = RestructureCopyAndPasteClassMessage.class,
    name = RestructureCopyAndPasteClassMessage.EVENT),
    @Type(value = RestructureCopyAndPastePackageMessage.class,
    name = RestructureCopyAndPastePackageMessage.EVENT),
    @Type(value = RestructureDeleteCommunicationMessage.class,
    name = RestructureDeleteCommunicationMessage.EVENT),
    @Type(value = RestructureDuplicateAppMessage.class,
    name = RestructureDuplicateAppMessage.EVENT),
    @Type(value = RestructureRenameOperationMessage.class,
    name = RestructureRenameOperationMessage.EVENT),
    @Type(value = RestructureRestoreAppMessage.class, name = RestructureRestoreAppMessage.EVENT),
    @Type(value = RestructureRestorePackageMessage.class,
    name = RestructureRestorePackageMessage.EVENT),
    @Type(value = RestructureRestoreClassMessage.class,
    name = RestructureRestoreClassMessage.EVENT),
    @Type(value = ChangeLogRemoveEntryMessage.class, name = ChangeLogRemoveEntryMessage.EVENT),
    @Type(value = ChangeLogRestoreEntriesMessage.class,
    name = ChangeLogRestoreEntriesMessage.EVENT),
    @Type(value = AllHighlightsResetMessage.class, name = AllHighlightsResetMessage.EVENT),
    @Type(value = MenuDetachedMessage.class, name = MenuDetachedMessage.EVENT),
    @Type(value = DetachedMenuClosedMessage.class, name = DetachedMenuClosedMessage.EVENT),
    @Type(value = SpectatingUpdateMessage.class, name = SpectatingUpdateMessage.EVENT),
    @Type(value = HeatmapUpdateMessage.class, name = HeatmapUpdateMessage.EVENT),
    @Type(value = UserControllerConnectMessage.class, name = UserControllerConnectMessage.EVENT),
    @Type(value = UserControllerDisconnectMessage.class,
    name = UserControllerDisconnectMessage.EVENT),
    @Type(value = JoinVrMessage.class, name = JoinVrMessage.EVENT),
    @Type(value = UserPositionsMessage.class, name = UserPositionsMessage.EVENT),
    @Type(value = PingUpdateMessage.class, name = PingUpdateMessage.EVENT),
    @Type(value = MousePingUpdateMessage.class, name = MousePingUpdateMessage.EVENT),
    @Type(value = TimestampUpdateMessage.class, name = TimestampUpdateMessage.EVENT)})
public class VrMessage {
  @JsonTypeId private String event;

  /**
   * Creates a new message of the given type.
   * <p>
   * Every subclass should define a static {@code EVENT} attribute and call {@code super(EVENT)} in
   * its default constructor.
   *
   * @param event The type identifier of the message.
   */
  public VrMessage(final String event) {
    this.event = event;
  }

  public String getEvent() {
    return this.event;
  }

  public void setEvent(final String event) {
    this.event = event;
  }
}

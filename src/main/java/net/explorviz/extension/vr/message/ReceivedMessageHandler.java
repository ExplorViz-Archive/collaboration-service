package net.explorviz.extension.vr.message;

import net.explorviz.extension.vr.message.receivable.AppClosedMessage;
import net.explorviz.extension.vr.message.receivable.AppGrabbedMessage;
import net.explorviz.extension.vr.message.receivable.AppOpenedMessage;
import net.explorviz.extension.vr.message.receivable.AppReleasedMessage;
import net.explorviz.extension.vr.message.receivable.AppTranslatedMessage;
import net.explorviz.extension.vr.message.receivable.ComponentUpdateMessage;
import net.explorviz.extension.vr.message.receivable.HightlightingUpdateMessage;
import net.explorviz.extension.vr.message.receivable.LandscapePositionMessage;
import net.explorviz.extension.vr.message.receivable.SpectatingUpdateMessage;
import net.explorviz.extension.vr.message.receivable.UserControllersMessage;
import net.explorviz.extension.vr.message.receivable.UserPositionsMessage;

/**
 * Interface for classes that can handle {@link VRMessage}s.
 * 
 * Provides a double dispatch mechanism (visitor pattern) for messages.
 *
 * @param <R> The return type of the handler methods.
 * @param <A> Type for additional arguments of the handler methods.
 */
public interface ReceivedMessageHandler<R, A> {
    R handleAppClosedMessage(AppClosedMessage message, A arg);

    R handleAppGrabbedMessage(AppGrabbedMessage message, A arg);

    R handleAppOpenedMessage(AppOpenedMessage message, A arg);

    R handleAppReleasedMessage(AppReleasedMessage message, A arg);

    R handleAppTranslatedMessage(AppTranslatedMessage message, A arg);

    R handleComponentUpdateMessage(ComponentUpdateMessage message, A arg);

    R handleHightlightingUpdateMessage(HightlightingUpdateMessage message, A arg);

    R handleLandscapePositionMessage(LandscapePositionMessage message, A arg);

    R handleSpectatingUpdateMessage(SpectatingUpdateMessage message, A arg);

    R handleUserControllersMessage(UserControllersMessage message, A arg);

    R handleUserPositionsMessage(UserPositionsMessage message, A arg);
}

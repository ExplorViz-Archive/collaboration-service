package net.explorviz.extension.vr.message;

import net.explorviz.extension.vr.message.receivable.AppClosedMessage;
import net.explorviz.extension.vr.message.receivable.AppOpenedMessage;
import net.explorviz.extension.vr.message.receivable.ComponentUpdateMessage;
import net.explorviz.extension.vr.message.receivable.HighlightingUpdateMessage;
import net.explorviz.extension.vr.message.receivable.MenuDetachedMessage;
import net.explorviz.extension.vr.message.receivable.ObjectGrabbedMessage;
import net.explorviz.extension.vr.message.receivable.ObjectMovedMessage;
import net.explorviz.extension.vr.message.receivable.ObjectReleasedMessage;
import net.explorviz.extension.vr.message.receivable.SpectatingUpdateMessage;
import net.explorviz.extension.vr.message.receivable.UserControllersMessage;
import net.explorviz.extension.vr.message.receivable.UserPositionsMessage;

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

    R handleUserControllersMessage(UserControllersMessage message, A arg);

    R handleUserPositionsMessage(UserPositionsMessage message, A arg);

    R handleObjectMovedMessage(ObjectMovedMessage message, A arg);

    R handleMenuDetachedMessage(MenuDetachedMessage message, A arg);
}

package net.explorviz.extension.vr.message;

import net.explorviz.extension.vr.message.receivable.AppClosedMessage;
import net.explorviz.extension.vr.message.receivable.AppGrabbedMessage;
import net.explorviz.extension.vr.message.receivable.AppOpenedMessage;
import net.explorviz.extension.vr.message.receivable.AppReleasedMessage;
import net.explorviz.extension.vr.message.receivable.AppTranslatedMessage;
import net.explorviz.extension.vr.message.receivable.ComponentUpdateMessage;
import net.explorviz.extension.vr.message.receivable.HightlightingUpdateMessage;
import net.explorviz.extension.vr.message.receivable.LandscapePositionMessage;
import net.explorviz.extension.vr.message.receivable.NodegroupUpdateMessage;
import net.explorviz.extension.vr.message.receivable.SpectatingUpdateMessage;
import net.explorviz.extension.vr.message.receivable.SystemUpdateMessage;
import net.explorviz.extension.vr.message.receivable.UserControllersMessage;
import net.explorviz.extension.vr.message.receivable.UserPositionsMessage;

/**
 * Interface for classes that can handle {@link VRMessage}s.
 * 
 * Provides a double dispatch mechanism (visitor pattern) for messages.
 *
 * @param <T> The return type of the handler methods.
 */
public interface ReceivedMessageHandler<T> {
    T handleAppClosedMessage(AppClosedMessage message);
    T handleAppGrabbedMessage(AppGrabbedMessage message);
    T handleAppOpenedMessage(AppOpenedMessage message);
    T handleAppReleasedMessage(AppReleasedMessage message);
    T handleAppTranslatedMessage(AppTranslatedMessage message);
    T handleComponentUpdateMessage(ComponentUpdateMessage message);
    T handleHightlightingUpdateMessage(HightlightingUpdateMessage message);
    T handleLandscapePositionMessage(LandscapePositionMessage message);
    T handleNodegroupUpdateMessage(NodegroupUpdateMessage message);
    T handleSpectatingUpdateMessage(SpectatingUpdateMessage message);
    T handleSystemUpdateMessage(SystemUpdateMessage message);
    T handleUserControllersMessage(UserControllersMessage message);
    T handleUserPositionsMessage(UserPositionsMessage message);
    
}

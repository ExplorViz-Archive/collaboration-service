package net.explorviz.extension.vr.messages;

/**
 * Interface for classes that can handle {@link VRMessage}s.
 * 
 * Provides a double dispatch mechanism (visitor pattern) for messages.
 *
 * @param <T> The return type of the handler methods.
 */
public interface VRMessageHandler<T> {
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

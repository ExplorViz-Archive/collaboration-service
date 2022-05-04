package net.explorviz.collaboration.message.sendable.factory;

import net.explorviz.collaboration.message.sendable.InitialLandscapeMessage;
import net.explorviz.collaboration.message.sendable.InitialLandscapeMessage.App;
import net.explorviz.collaboration.message.sendable.InitialLandscapeMessage.DetachedMenu;
import net.explorviz.collaboration.message.sendable.InitialLandscapeMessage.HighlightingObject;
import net.explorviz.collaboration.message.sendable.InitialLandscapeMessage.Landscape;
import net.explorviz.collaboration.message.sendable.TimestampUpdateTimerMessage;
import net.explorviz.collaboration.model.*;
import net.explorviz.collaboration.service.Room;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;

@ApplicationScoped
public class TimestampUpdateTimerMessageFactory {

  public TimestampUpdateTimerMessage makeMessage(final Room room) {
    final var timestamp = System.currentTimeMillis() - 60000;
    final var message  = new TimestampUpdateTimerMessage();
    message.setTimestamp(timestamp);
    room.getLandscapeService().updateTimestamp(timestamp);
    return message;
  }

}

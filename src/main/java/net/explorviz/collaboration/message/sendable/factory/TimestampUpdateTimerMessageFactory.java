package net.explorviz.collaboration.message.sendable.factory;

import javax.enterprise.context.ApplicationScoped;
import net.explorviz.collaboration.message.sendable.TimestampUpdateTimerMessage;
import net.explorviz.collaboration.service.Room;

@ApplicationScoped
public class TimestampUpdateTimerMessageFactory {

  public TimestampUpdateTimerMessage makeMessage(final Room room) {
    final var timestamp = System.currentTimeMillis() - 60_000;
    final var message = new TimestampUpdateTimerMessage();
    message.setTimestamp(timestamp);
    room.getLandscapeService().updateTimestamp(timestamp);
    return message;
  }

}

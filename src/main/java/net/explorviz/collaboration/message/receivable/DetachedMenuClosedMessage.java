package net.explorviz.collaboration.message.receivable;

import net.explorviz.collaboration.message.ReceivableMessageHandler;
import net.explorviz.collaboration.message.RequestMessage;

public class DetachedMenuClosedMessage extends RequestMessage {

  public static final String EVENT = "detached_menu_closed";

  private String menuId;

  public DetachedMenuClosedMessage() {
    super(EVENT);
  }

  public String getMenuId() {
    return this.menuId;
  }

  public void setMenuId(final String menuId) {
    this.menuId = menuId;
  }

  @Override
  public <R, A> R handleWith(final ReceivableMessageHandler<R, A> handler, final A arg) {
    return handler.handleDetachedMenuClosedMessage(this, arg);
  }
}

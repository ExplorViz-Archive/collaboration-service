package net.explorviz.vr.message.receivable;

import net.explorviz.vr.message.ReceivableMessageHandler;
import net.explorviz.vr.message.RequestMessage;

public class DetachedMenuClosedMessage extends RequestMessage {

    public static final String EVENT = "detached_menu_closed";

    private String menuId;

    public DetachedMenuClosedMessage() {
        super(EVENT);
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    @Override
    public <R, A> R handleWith(ReceivableMessageHandler<R, A> handler, A arg) {
        return handler.handleDetachedMenuClosedMessage(this, arg);
    }
}

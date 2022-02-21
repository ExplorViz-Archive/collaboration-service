package net.explorviz.collaboration.payload.sendable;

public class LobbyJoinedResponse {
  private final String ticketId;
  private final long validUntil;

  public LobbyJoinedResponse(final String ticketId, final long validUntil) {
    this.ticketId = ticketId;
    this.validUntil = validUntil;
  }

  public String getTicketId() {
    return this.ticketId;
  }

  public long getValidUntil() {
    return this.validUntil;
  }
}

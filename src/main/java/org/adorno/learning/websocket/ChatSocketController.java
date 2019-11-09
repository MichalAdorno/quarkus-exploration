package org.adorno.learning.websocket;


import javax.enterprise.context.ApplicationScoped;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/chat/{userFrom}/{chatRoom}")
@ApplicationScoped
public class ChatSocketController {

    Map<String, CustomSession> sessions = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(final Session session,
                       @PathParam("userFrom") final String userFrom,
                       @PathParam("chatRoom") final String chatRoom) {
        putSession(userFrom, session, chatRoom);
        final String message = String.format("User %s joined the room: %s", userFrom, chatRoom);
        broadcast(message, chatRoom);
    }

    @OnClose
    public void onClose(final Session session,
                        @PathParam("userFrom") final String userFrom,
                        @PathParam("chatRoom") final String chatRoom) {
        removeSession(userFrom);
        final String message = String.format("User %s left", userFrom);
        broadcast(message, chatRoom);
    }

    private void removeSession(@PathParam("userFrom") final String userFrom) {
        sessions.remove(userFrom);
    }

    @OnError
    public void onError(final Session session,
                        @PathParam("userFrom") final String userFrom,
                        @PathParam("chatRoom") final String chatRoom,
                        final Throwable throwable) {
        removeSession(userFrom);
        final String message = String.format("User %s left on error", userFrom, throwable);
        broadcast(message, chatRoom);
    }

    @OnMessage
    public void onMessage(final String message,
                          @PathParam("userFrom") final String userFrom,
                          @PathParam("chatRoom") final String chatRoom) {
        final String decoratedMessage = String.format(">> %s: %s", userFrom, message);
        broadcast(decoratedMessage, chatRoom);
    }

    private void broadcast(final String message,
                           final String chatRoom) {
        sessions.values()
                .stream()
                .filter(customSession -> customSession.getChatRoom().equals(chatRoom))
                .forEach(customSession -> {

                    System.out.println(customSession.getUserFrom());
                    System.out.println(customSession.getSession().getId());
                    System.out.println(customSession.getSession().getUserPrincipal());

                    customSession.getSession().getAsyncRemote().sendObject(message, result -> {
                        if (result.getException() != null) {
                            System.out.println("Unable to send message: " + result.getException());
                        }
                    });

                });
    }

    private void putSession(final String userFrom,
                            final Session session,
                            final String chatRoom) {
        CustomSession customSession = CustomSession.builder()
                .session(session)
                .userFrom(userFrom)
                .chatRoom(chatRoom)
                .build();

        sessions.put(userFrom, customSession);
    }


}
package org.adorno.learning.websocket;

import lombok.Builder;
import lombok.Value;

import javax.websocket.Session;

@Value
@Builder(toBuilder = true)
public class CustomSession {

    private Session session;
    private String userFrom;
    private String chatRoom;

}

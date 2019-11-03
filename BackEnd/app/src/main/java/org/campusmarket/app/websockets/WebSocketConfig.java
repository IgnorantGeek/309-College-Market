<<<<<<< HEAD:BackEnd/app/src/main/java/org/campusmarket/app/endpoints/ChatEndpointConfig.java
// package org.campusmarket.app.endpoints;
=======
package org.campusmarket.app.websockets;
>>>>>>> 37-lily-websockets:BackEnd/app/src/main/java/org/campusmarket/app/websockets/WebSocketConfig.java

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.web.socket.server.standard.ServerEndpointExporter;

<<<<<<< HEAD:BackEnd/app/src/main/java/org/campusmarket/app/endpoints/ChatEndpointConfig.java
// @Configuration
// public class ChatEndpointConfig
// {
//     @Bean
//     public ServerEndpointExporter serverEndpointExporter()
//     {
//         return new ServerEndpointExporter();
//     }
// }
=======
@Configuration
public class WebSocketConfig
{
    @Bean
    public ServerEndpointExporter serverEndpointExporter()
    {
        return new ServerEndpointExporter();
    }
}
>>>>>>> 37-lily-websockets:BackEnd/app/src/main/java/org/campusmarket/app/websockets/WebSocketConfig.java

package justdust.websocketserver;

import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketServlet;

import javax.servlet.http.HttpServletRequest;

/**
 * Simple webserver listening on a web socket and sending back whatever it receives.
 */
public class BasicWebSocketServer {

    public static void start() throws Exception {
        org.eclipse.jetty.server.Server server = new org.eclipse.jetty.server.Server(8081);
        ServletContextHandler contextHandler = new ServletContextHandler(server, "/", true, false);
        contextHandler.addServlet(BasicWebSocketServlet.class, "/ws");
        server.start();
        server.join();
    }

    public static class BasicWebSocketServlet extends WebSocketServlet {
        @Override
        public WebSocket doWebSocketConnect(HttpServletRequest httpServletRequest, String s) {
            return new BasicWebSocket();
        }
    }

    public static void main(String[] args) throws Exception {
        start();
    }
}

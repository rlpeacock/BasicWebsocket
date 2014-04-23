package justdust.websocketserver;

import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketServlet;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Simple webserver listening on a web socket and sending back whatever it receives.
 */
public class BasicWebSocketServer {

    public static void start() throws Exception {
        org.eclipse.jetty.server.Server server = new org.eclipse.jetty.server.Server(8888);
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

    public static class BasicWebSocket implements WebSocket.OnTextMessage {

        private Connection connection;

        @Override
        public void onOpen(Connection connection) {
            this.connection = connection;
        }

        @Override
        public void onClose(int i, String s) {
            System.out.println("Closing");
        }

        @Override
        public void onMessage(String s) {
            System.out.println("Got message: " + s);
            try {
                connection.sendMessage("Got " + s);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        start();
    }
}

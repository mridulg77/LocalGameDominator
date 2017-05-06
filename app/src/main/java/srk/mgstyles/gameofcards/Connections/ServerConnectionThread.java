package srk.mgstyles.gameofcards.Connections;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import srk.mgstyles.gameofcards.Fragments.HostFragment;


public class ServerConnectionThread extends Thread {

    static final int SocketServerPORT = 8080;
    public static HashMap<Socket, String> socketUserMap = new HashMap();
    public static boolean serverStarted = false;
    public static ServerSocket serverSocket;
    public static boolean allPlayersJoined = false;

    public ServerConnectionThread() {

    }

    @Override
    public void run() {
        if (serverSocket == null) {
            try {
                serverSocket = new ServerSocket(SocketServerPORT);
                serverStarted = true;
                while (true) {
                    Socket socket = serverSocket.accept();
                    if (!allPlayersJoined) {
                        Thread socketListenThread = new Thread(new ServerListenerThread(socket));
                        socketListenThread.start();
                        ServerSenderThread sendGameName = new ServerSenderThread(socket, HostFragment.gameName.getText().toString());
                        sendGameName.start();
                        socketUserMap.put(socket, null);
                        if (socketUserMap.size() == HostFragment.numberPlayers) {
                            allPlayersJoined = true;
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

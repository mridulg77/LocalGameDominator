package srk.mgstyles.gameofcards.Connections;


import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import srk.mgstyles.gameofcards.Model.PlayerInfo;
import srk.mgstyles.gameofcards.Utils.WifiHelper;

public class ClientConnectionThread extends Thread {
    public static Socket socket;
    String dstAddress;
    int dstPort = 8080;
    public static boolean serverStarted = false;
    String userName;

    public ClientConnectionThread(String userName) {
        this.userName = userName;
    }

    @Override
    public void run() {
        if (socket == null) {
            try {
                ArrayList<String> deviceList = WifiHelper.getDeviceList();
                if (deviceList.size() > 0) {
                    dstAddress = deviceList.get(0);
                    if (dstAddress != null) {
                        socket = new Socket(dstAddress, dstPort);
                        if (socket.isConnected()) {
                            serverStarted = true;
                            ClientListenerThread clientListener = new ClientListenerThread(socket);
                            clientListener.start();
                            PlayerInfo playerInfo = new PlayerInfo(userName);
                            ClientSenderThread sendUserName = new ClientSenderThread(socket, playerInfo);
                            sendUserName.start();
                        }
                    }
                }
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

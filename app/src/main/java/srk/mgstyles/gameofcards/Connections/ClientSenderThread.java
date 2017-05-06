package srk.mgstyles.gameofcards.Connections;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import srk.mgstyles.gameofcards.Fragments.MainFragment;
import srk.mgstyles.gameofcards.Model.Game;
import srk.mgstyles.gameofcards.Utils.Constants;

public class ClientSenderThread extends Thread {

    private Socket hostThreadSocket;
    Object message;
    public static boolean isActive = true;

    public ClientSenderThread(Socket socket, Object message) {
        hostThreadSocket = socket;
        this.message = message;
    }

    @Override
    public void run() {
        OutputStream outputStream;
        ObjectOutputStream objectOutputStream;
        if (hostThreadSocket.isConnected()) {
            try {
                if (isActive) {
                    if (message instanceof Game && !Constants.isPlayerActive(MainFragment.userName.getText().toString(), (Game) message)) {
                        isActive = false;
                    }
                    outputStream = hostThreadSocket.getOutputStream();
                    objectOutputStream = new ObjectOutputStream(outputStream);
                    objectOutputStream.writeObject(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }



}

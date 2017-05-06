package srk.mgstyles.gameofcards.Connections;


import android.os.Bundle;
import android.os.Message;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

import srk.mgstyles.gameofcards.Fragments.MainFragment;
import srk.mgstyles.gameofcards.Model.Game;
import srk.mgstyles.gameofcards.Utils.Constants;

public class ClientListenerThread extends Thread {

    Socket socket;

    ClientListenerThread(Socket soc) {
        socket = soc;
    }

    @Override
    public void run() {
        try {
            while (true) {
                ObjectInputStream objectInputStream;
                InputStream inputStream = null;
                inputStream = socket.getInputStream();
                objectInputStream = new ObjectInputStream(inputStream);
                Bundle data = new Bundle();
                Object serverObject = (Object) objectInputStream.readObject();
                if (serverObject != null) {
                    if (serverObject instanceof String) {
                        data.putSerializable(Constants.DATA_KEY, (String) serverObject);
                        data.putInt(Constants.ACTION_KEY, Constants.UPDATE_GAME_NAME);
                    }
                    if (serverObject instanceof Game) {
                        data.putSerializable(Constants.DATA_KEY, (Game) serverObject);
                    }
                    Message msg = new Message();
                    msg.setData(data);
                    MainFragment.clientHandler.sendMessage(msg);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

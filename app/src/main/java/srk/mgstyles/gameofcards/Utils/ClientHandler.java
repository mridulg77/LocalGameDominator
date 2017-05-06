package srk.mgstyles.gameofcards.Utils;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import srk.mgstyles.gameofcards.Connections.ClientConnectionThread;
import srk.mgstyles.gameofcards.Connections.ClientSenderThread;
import srk.mgstyles.gameofcards.Fragments.GameFragment;
import srk.mgstyles.gameofcards.Fragments.JoinGameFragment;
import srk.mgstyles.gameofcards.Model.Game;


public class ClientHandler extends Handler {


    Bundle messageData;

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        messageData = msg.getData();
        int value = messageData.getInt(Constants.ACTION_KEY);
        Object clientObject = messageData.getSerializable(Constants.DATA_KEY);
        if (value == Constants.UPDATE_GAME_NAME) {
            String gameName = (String) clientObject;
            JoinGameFragment.gameName.setText(gameName);
        }
        if (clientObject instanceof Game) {
            if (GameFragment.gameObject != null) {
                if (((Game) clientObject).senderUsername.equals(String.valueOf(Constants.NEW_GAME))) {
                    ClientSenderThread.isActive = true;
//                    ((Game) clientObject).senderUsername = "";
                }
                GameFragment.gameObject = (Game) clientObject;
                GameFragment.updatePlayerStatus();
                GameFragment.updateTable();
                GameFragment.updateHand();
            } else {
                JoinGameFragment.gameobject = (Game) clientObject;
            }
        }
    }

    public static void sendToServer(Object gameObject) {
        ClientSenderThread sendGameChange = new ClientSenderThread(ClientConnectionThread.socket, gameObject);
        sendGameChange.start();
    }
}

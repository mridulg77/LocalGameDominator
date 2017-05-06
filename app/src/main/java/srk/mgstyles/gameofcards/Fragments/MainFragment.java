package srk.mgstyles.gameofcards.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import srk.mgstyles.gameofcards.Connections.ClientConnectionThread;
import srk.mgstyles.gameofcards.DataB;
import srk.mgstyles.gameofcards.LoginActivity;
import srk.mgstyles.gameofcards.R;
import srk.mgstyles.gameofcards.Utils.ClientHandler;


public class MainFragment extends Fragment {

    public static ClientHandler clientHandler;
    public static MaterialEditText userName;

    public MainFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
//Button loginButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_screen_layout, container, false);
        Button hostGame = (Button) rootView.findViewById(R.id.hostGame);
        Button joinGame = (Button) rootView.findViewById(R.id.joinGame);
        Button infoButton = (Button) rootView.findViewById(R.id.infoButton);
        Button loginButton=(Button) rootView.findViewById(R.id.button2);
        Button record=(Button) rootView.findViewById(R.id.button3);
        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new InformationFragment()).addToBackStack(InformationFragment.class.getName())
                        .commit();
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DataB.class);
                startActivity(intent);
            }
        });
        WifiManager wifi = (WifiManager) getActivity().getSystemService(Context.WIFI_SERVICE);
        Method[] wmMethods = wifi.getClass().getDeclaredMethods();
        userName = (MaterialEditText) rootView.findViewById(R.id.userName);

        for (Method method : wmMethods) {
            if (method.getName().equals("isWifiApEnabled")) {
                try {
                    boolean isWifiAPEnabled = (Boolean) method.invoke(wifi);
                    final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    if (isWifiAPEnabled) {
                        joinGame.setVisibility(View.GONE);
                        hostGame.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (userName.getText() != null && userName.getText().toString().trim().length() > 0) {
                                    fragmentManager.beginTransaction()
                                            .replace(R.id.container, new HostFragment()).addToBackStack(HostFragment.class.getName())
                                            .commit();
                                } else {
                                    Toast.makeText(getActivity(), "Please enter a UserName", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        if (clientHandler == null) {
                            clientHandler = new ClientHandler();
                        }
                        hostGame.setVisibility(View.GONE);
                        joinGame.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ClientConnectionThread clientConnect = new ClientConnectionThread(userName.getText().toString());
                                clientConnect.start();
                                if (userName.getText() != null && userName.getText().toString().trim().length() > 0) {
//                                    if (ClientConnectionThread.serverStarted) {
                                    fragmentManager.beginTransaction()
                                            .replace(R.id.container, new JoinGameFragment()).addToBackStack(JoinGameFragment.class.getName())
                                            .commit();
//                                    } else {
//                                        Toast.makeText(getActivity(), "Game yet to be hosted", Toast.LENGTH_SHORT).show();
//                                    }
                                } else {
                                    Toast.makeText(getActivity(), "Please enter a UserName", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return rootView;
    }
  //  public void abc(View v)
   // {
   //     Intent intent = new Intent(getActivity(), LoginActivity.class);
    //    startActivity(intent);
    //}


}

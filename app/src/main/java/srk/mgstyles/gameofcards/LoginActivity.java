package srk.mgstyles.gameofcards;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;

import srk.mgstyles.gameofcards.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FirebaseAuth auth=FirebaseAuth.getInstance();
        startActivityForResult(AuthUI.getInstance()
        .createSignInIntentBuilder()
        .setProviders(
                AuthUI.FACEBOOK_PROVIDER,
                AuthUI.EMAIL_PROVIDER,
                AuthUI.GOOGLE_PROVIDER
        ).build(),1);


    }

}

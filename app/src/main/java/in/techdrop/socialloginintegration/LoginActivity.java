package in.techdrop.socialloginintegration;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class LoginActivity extends AppCompatActivity
{

    Button loginButton;
    TextView loginStatus;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);


        loginButton = (LoginButton) findViewById(R.id.fb_login_button);
        loginStatus = (TextView) findViewById(R.id.login_error);

        callbackManager = CallbackManager.Factory.create();
        // Callback registration
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult)
            {
                loginStatus.setText("Login Success\n"+loginResult.getAccessToken());
            }

            @Override
            public void onCancel()
            {
                loginStatus.setText("Login Cancel\n");
            }

            @Override
            public void onError(FacebookException error)
            {
                loginStatus.setText("Login Error :\n" + error.getMessage());
                System.out.println("Key :- " + error.getMessage());
            }
        });

    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}

package in.techdrop.socialloginintegration;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity
{

    Button loginButton;
    String facebookId ,facebookEmail , fName , fImage ;
    TextView loginStatus;
    private CallbackManager callbackManager;
    AccessToken accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        loginButton = (LoginButton) findViewById(R.id.fb_login_button);
        loginStatus = (TextView) findViewById(R.id.login_error);

        callbackManager = CallbackManager.Factory.create();
        processtofblogin();
    }

    private void processtofblogin()
    {

        if (accessToken != null)
        {
            Log.i("login accesstoken :" , "ok");
            accessToken = com.facebook.AccessToken.getCurrentAccessToken();
            LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult)
                {
                    Log.i("login before :" , "ok");
                    loginStatus.setText("Login Success\n" + loginResult.getRecentlyGrantedPermissions());
                    GraphRequest.newMeRequest(loginResult.getAccessToken(),new GraphRequest.GraphJSONObjectCallback(){

                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response)
                        {
                            Log.i("login complate :" , "ok");
                            String userDetails = response.getRawResponse();
                            mainActivity(userDetails);
                        }
                    });
                    /* NOT IN USE
                    Bundle parameter = new Bundle();
                    parameter.putString("Fileds" , "name,email");*/
                }

                @Override
                public void onCancel() {
                    loginStatus.setText("Login Cancel\n");
                }

                @Override
                public void onError(FacebookException error) {
                    loginStatus.setText("Login Error :\n" + error.getMessage());
                    System.out.println("Key :- " + error.getMessage());
                }
            });
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    Log.i("login onclick before :" , "ok");
                    LoginManager.getInstance().logInWithPublishPermissions(LoginActivity.this , Arrays.asList("public_profile , email , user_videos "));
                    Log.i("login onclick after :" , "ok");
                }
            });
        }
    }

    // Callback registration

    private void mainActivity(String userDetails)
    {
        Toast.makeText(getApplicationContext(),"Login Successful.." ,Toast.LENGTH_SHORT).show();

        JSONObject jsonobject = null;
        try {
            jsonobject = new JSONObject(userDetails);
            System.out.println("JsoObject" + jsonobject);

            facebookId = jsonobject.getString("id");
            facebookEmail = jsonobject.getString("email");
            fName = jsonobject.getString("name");
            fImage = "http://graph.facebook.com/" + facebookId + "/picture?type=large";

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Intent i = new Intent(getApplicationContext(),MainActivity.class);
        //i.putExtra("access_token",a);
        i.putExtra("facebookId", facebookId );
        i.putExtra("facebookEmail",facebookEmail );
        i.putExtra("fName", fName);
        i.putExtra("fImage",fImage);
        startActivity(i);
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}

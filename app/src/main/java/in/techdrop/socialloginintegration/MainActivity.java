package in.techdrop.socialloginintegration;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


public class MainActivity extends AppCompatActivity {

    TextView id, Name , Surname ;
    ImageView ImgURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        id = (TextView) findViewById(R.id.AccessToken);
        Name = (TextView) findViewById(R.id.Name);
        Surname = (TextView) findViewById(R.id.Surname);
        ImgURL = (ImageView) findViewById(R.id.ImgUrl);

        Intent intent = getIntent();
        //String Token = intent.getStringExtra("access_token");
        String fid = intent.getStringExtra("facebookId");
        String femail = intent.getStringExtra("facebookEmail");
        String fname = intent.getStringExtra("fName");
        String imageUrl = intent.getStringExtra("fImage");

        //txtToken.setText(Token);
        id.setText(fid);
        Name.setText(fname);
        Surname.setText(femail);
        Glide.with(getApplicationContext()).load(imageUrl).into(ImgURL);

    }
}

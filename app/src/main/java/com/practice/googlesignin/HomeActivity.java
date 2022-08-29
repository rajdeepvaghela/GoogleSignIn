package com.practice.googlesignin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";

    public static final String USER_DATA = "user_data";

    private ImageView imgDisplayPicture;
    private TextView tvName, tvEmail;
    private Button btnSignOut;

    private final GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder()
            .requestEmail()
            .requestProfile()
            .build();

    private GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_home);
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        initViews();
        initListeners();

        UserData userData = (UserData) getIntent().getParcelableExtra(USER_DATA);
        setData(userData);
    }

    private void initViews() {
        imgDisplayPicture = findViewById(R.id.imgDisplayPicture);
        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        btnSignOut = findViewById(R.id.btnSignOut);
    }

    private void initListeners() {
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleSignInClient.signOut()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                goToSignIn();
                            }
                        });
            }
        });
    }

    private void setData(UserData userData) {
        // using glide library to load the user's profile picture
        Glide.with(this)
                .load(userData.getDisplayPicture())
                .error(R.drawable.ic_round_account_circle_24)
                .placeholder(R.drawable.ic_round_account_circle_24)
                .circleCrop()
                .into(imgDisplayPicture);

        tvName.setText(userData.getName());
        tvEmail.setText(userData.getEmail());
    }

    private void goToSignIn() {
        Intent signInIntent = new Intent(this, SignInActivity.class);
        startActivity(signInIntent);
        finish();
    }
}

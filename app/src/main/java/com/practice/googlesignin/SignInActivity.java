package com.practice.googlesignin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;

public class SignInActivity extends AppCompatActivity {

    private static final String TAG = "SignInActivity";

    private SignInButton btnSignIn;

    private final GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder()
            .requestEmail()
            .requestProfile()
            .build();

    private GoogleSignInClient googleSignInClient;

    private final ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {

        @Override
        public void onActivityResult(ActivityResult result) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
            handleSignInResult(task);
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        initViews();
        initListeners();

        checkIfAlreadyLoggedIn();
    }

    private void checkIfAlreadyLoggedIn() {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            goToHome(account);
        }
    }

    private void initViews() {
        btnSignIn = findViewById(R.id.btnSignIn);
    }

    private void initListeners() {
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSignIn();
            }
        });
    }

    private void startSignIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        launcher.launch(signInIntent);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            goToHome(account);

        } catch (ApiException e) {
            Log.e(TAG, "signInResult:failed code=" + e.getStatusCode(), e);
        }
    }

    private void goToHome(GoogleSignInAccount account) {

        UserData userData = new UserData(
                account.getDisplayName(),
                account.getEmail(),
                account.getPhotoUrl()
        );

        Log.d(TAG, "goToHome: UserData=" + userData);

        Intent homeIntent = new Intent(this, HomeActivity.class);
        homeIntent.putExtra(HomeActivity.USER_DATA, userData);
        startActivity(homeIntent);
        finish();
    }
}
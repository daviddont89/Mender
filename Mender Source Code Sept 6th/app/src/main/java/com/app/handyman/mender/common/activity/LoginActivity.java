package com.app.handyman.mender.common.activity;


import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.handyman.mender.R;
import com.app.handyman.mender.common.utils.AppUtils;
import com.app.handyman.mender.common.utils.DataManager;
import com.app.handyman.mender.handyman.activity.ApplyAsHandymanActivity;
import com.app.handyman.mender.common.utils.Icon_Manager;
import com.app.handyman.mender.utils.Validation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Activity to login existing users back into the app!
 */

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.ediTextEmail)
    EditText ediTextEmail;
    @BindView(R.id.ediTextPassword)
    EditText ediTextPassword;
    @BindView(R.id.textViewForgotPassword)
    TextView textViewForgotPassword;
    @BindView(R.id.buttonLogin)
    Button buttonLogin;
    @BindView(R.id.buttonSignup)
    Button buttonSignup;
    @BindView(R.id.textViewTitle)
    TextView textViewTitle;
    @BindView(R.id.textViewSubtitle)
    TextView textViewSubtitle;

    private static final String TAG = LoginActivity.class.getSimpleName();

    // Widgets
    private Button sign_up_instead, apply;
    private TextView learn_more;
    private TextView title;
    private TextView subtitle;

    // private ProgressWheel mProgresWheel;

    // Firebase Authentication
    private FirebaseAuth auth;

    //Icon Manager
    Icon_Manager icon_manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
        icon_manager = new Icon_Manager();

        ((TextView) findViewById(R.id.textViewIcon)).setTypeface(icon_manager.get_icons("fonts/Icons.ttf", this));

        ((TextView) findViewById(R.id.textViewPassword)).setTypeface(icon_manager.get_icons("fonts/Icons.ttf", this));


        learn_more = (TextView) findViewById(R.id.learn_more);
        Typeface myCustomFont8 = Typeface.createFromAsset(getAssets(), "fonts/Quicksand Book Oblique.otf");
        learn_more.setTypeface(myCustomFont8);

        Typeface myCustomFont= Typeface.createFromAsset(getAssets(), "fonts/Quicksand Book.otf");
        setFontStyle(myCustomFont);

        auth = FirebaseAuth.getInstance();
        SharedPreferences sp1 = this.getSharedPreferences("Login", 0);
        String email = sp1.getString("email", "");
        //ediTextEmail.setText(email);

        learn_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, LearnMoreActivity.class);
                startActivity(intent);
            }
        });
    }


    public void setFontStyle(Typeface myCustomFont) {
        buttonSignup.setTypeface(myCustomFont);
        buttonLogin.setTypeface(myCustomFont);
        textViewTitle.setTypeface(myCustomFont);
        ediTextEmail.setTypeface(myCustomFont);
        ediTextPassword.setTypeface(myCustomFont);
        textViewForgotPassword.setTypeface(myCustomFont);
        textViewSubtitle.setTypeface(myCustomFont);

    }

    private void applyAsHandyman() {
        Intent intent = new Intent(LoginActivity.this, ApplyAsHandymanActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.buttonLogin)
    public void login() {
        if (AppUtils.isConnected(getApplicationContext())) {
            String email = ediTextEmail.getText().toString();
            String password = ediTextPassword.getText().toString();

            SharedPreferences sp = getSharedPreferences("Login", 0);
            SharedPreferences.Editor ed = sp.edit();
            ed.putString("email", email);
            ed.apply();

            if (!Validation.isEmpty(email)) {
                Toast.makeText(LoginActivity.this, "Email id field is required.", Toast.LENGTH_SHORT).show();
            } else if (!Validation.isValidEmail(email)) {
                Toast.makeText(LoginActivity.this, "Email id field must be valid.", Toast.LENGTH_SHORT).show();
            } else if (!Validation.isEmpty(password)) {
                Toast.makeText(LoginActivity.this, "Password field must be valid.", Toast.LENGTH_SHORT).show();
            } else {
                DataManager.getInstance().showProgressMessage(LoginActivity.this);
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //  DataManager.getInstance().hideProgressMessage();
                        if (task.isSuccessful()) {
                            DataManager.getInstance().hideProgressMessage();
                            // User Created take him to SplashActivity
                            // Toast.makeText(LoginActivity.this, "Successfully Logged In", Toast.LENGTH_LONG).show();
                            // mProgresWheel.stopSpinning();
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        } else {
                            DataManager.getInstance().hideProgressMessage();
                            // mProgresWheel.stopSpinning();
                            Toast.makeText(LoginActivity.this, "Incorrect Username or Password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        } else {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
        }


    }

    @OnClick(R.id.buttonSignup)
    public void signUp() {
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
        //finish();
    }

    @OnClick(R.id.textViewForgotPassword)
    public void forgotPassword() {
        final Dialog dialog = new Dialog(LoginActivity.this);
        dialog.setContentView(R.layout.dialog_forgot_password);
        dialog.setTitle("Enter Registered Email Address");

        Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "fonts/Quicksand Book.otf");

        final EditText mEmail = (EditText) dialog.findViewById(R.id.enter_email);
        final Button mForgotPassword = (Button) dialog.findViewById(R.id.forgot_password);
        final TextView learn_more = (TextView) dialog.findViewById(R.id.learn_more);
        mEmail.setTypeface(myCustomFont);
        mForgotPassword.setTypeface(myCustomFont);

        // mEmail.setTypeface(icon_manager.get_icons("fonts/Icons.ttf", LoginActivity.this));
        // mForgotPassword.setTypeface(icon_manager.get_icons("fonts/Icons.ttf", LoginActivity.this));

        auth = FirebaseAuth.getInstance();

        mForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // mProgresWheel.spin();
                String email = mEmail.getText().toString().trim();
                if (email.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please enter your email address", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d(TAG, email + ediTextPassword.getText().toString());
                    auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                dialog.dismiss();
                                // mProgresWheel.stopSpinning();
                                Toast.makeText(LoginActivity.this, "We have sent you an Password Reset email", Toast.LENGTH_SHORT).show();
                            } else {
                                mEmail.setText("");
                                // mProgresWheel.stopSpinning();
                                Toast.makeText(LoginActivity.this, "Please enter registered email id", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

                }
            }
        });

        dialog.setCancelable(true);
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}






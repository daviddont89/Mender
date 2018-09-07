package com.app.handyman.mender.common.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.handyman.mender.R;
import com.app.handyman.mender.common.utils.AppUtils;
import com.app.handyman.mender.common.utils.CustomOnItemSelectedListener;
import com.app.handyman.mender.common.utils.Icon_Manager;
import com.app.handyman.mender.utils.Validation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

// Registration Activity!
public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.signUpButton)
    Button signUpButton;
    @BindView(R.id.editTextFirstName)
    EditText editTextFirstName;
    @BindView(R.id.editTextLastName)
    EditText editTextLastName;
    @BindView(R.id.editTextEmail)
    EditText editTextEmail;
    @BindView(R.id.editTextPhoneNumber)
    EditText editTextPhoneNumber;
    @BindView(R.id.editTextMailingAddress)
    EditText editTextMailingAddress;
    @BindView(R.id.editTextZipcode)
    EditText editTextZipcode;
    @BindView(R.id.editTextCity)
    TextView editTextCity;
    @BindView(R.id.editTextPassword)
    EditText editTextPassword;
    @BindView(R.id.editTextConfirmPassword)
    EditText editTextConfirmPassword;
    @BindView(R.id.checkBoxAcceptTerms)
    CheckBox checkBoxAcceptTerms;
    @BindView(R.id.textViewTermsText)
    TextView textViewTermsText;
    @BindView(R.id.textViewRegisterAs)
    TextView textViewRegisterAs;
    @BindView(R.id.textViewTitleText)
    TextView textViewTitleText;

    private EditText tvicon;
    private TextView tvaddress, photodesc;
    private TextView tvName;
    private TextView tvlastName, tvpassword, tvmap, tvemail, tvRegisterTitle, mPaymentInformation;
    // private RadioGroup radioUserGroup;
    // private RadioButton radioUserButton, radioClient, radioHandyman;
    private ProgressWheel mProgressWheel;
    private FirebaseAuth firebaseAuth;

//    Card cardToSave;
//    Context mContext;
//    Stripe stripe;
//    Token mToken;
//    String customerId;

    boolean error;
    String firstname = "";
    String lastname = "";
    String phonenumber = "";
    String city = "";
    String address = "";
    String email = "";
    String password = "";
    String confirmPassword = "";
    String zipcode = "";
    FirebaseAuth firebaseauth;
    private DatabaseReference mDatabase;

    //ProgressDialog
    private ProgressDialog mRegProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ButterKnife.bind(this);

        //addItemsOnSpinner();
        //addListenerOnSpinnerItemSelection();
        firebaseauth = FirebaseAuth.getInstance();

        mRegProgress = new ProgressDialog(this);
        Icon_Manager icon_manager = new Icon_Manager();
        ((TextView) findViewById(R.id.tvicon)).setTypeface(icon_manager.get_icons("fonts/Icons.ttf", this));
        ((TextView) findViewById(R.id.tvaddress)).setTypeface(icon_manager.get_icons("fonts/Icons.ttf", this));
        ((TextView) findViewById(R.id.tvName)).setTypeface(icon_manager.get_icons("fonts/Icons.ttf", this));
        ((TextView) findViewById(R.id.tvlastName)).setTypeface(icon_manager.get_icons("fonts/Icons.ttf", this));
        ((TextView) findViewById(R.id.tvaddress)).setTypeface(icon_manager.get_icons("fonts/Icons.ttf", this));
        ((TextView) findViewById(R.id.tvpassword)).setTypeface(icon_manager.get_icons("fonts/Icons.ttf", this));
        ((TextView) findViewById(R.id.tvmap)).setTypeface(icon_manager.get_icons("fonts/Icons.ttf", this));
        ((TextView) findViewById(R.id.tvemail)).setTypeface(icon_manager.get_icons("fonts/Icons.ttf", this));
        ((TextView) findViewById(R.id.tvcity)).setTypeface(icon_manager.get_icons("fonts/Icons.ttf", this));
        ((TextView) findViewById(R.id.tvConfirmPassword)).setTypeface(icon_manager.get_icons("fonts/Icons.ttf", this));
        ((TextView) findViewById(R.id.textView13)).setTypeface(icon_manager.get_icons("fonts/Icons.ttf", this));

        firebaseAuth = FirebaseAuth.getInstance();


        Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "fonts/Quicksand Book.otf");
        setFontStyle(myCustomFont);

        setUi();


        signUpButton = (Button) findViewById(R.id.signUpButton);
        Typeface myCustomFont17 = Typeface.createFromAsset(getAssets(), "fonts/Quicksand Book.otf");
        signUpButton.setTypeface(myCustomFont17);
        signUpButton.setOnClickListener(this);

        // radioUserGroup = (RadioGroup) findViewById(R.id.radioUserType);
        mProgressWheel = (ProgressWheel) findViewById(R.id.progress_wheel);
        mProgressWheel.setBarColor(Color.LTGRAY);

    }

    public void setFontStyle(Typeface myCustomFont) {
        editTextFirstName.setTypeface(myCustomFont);
        editTextLastName.setTypeface(myCustomFont);
        editTextPhoneNumber.setTypeface(myCustomFont);
        editTextCity.setTypeface(myCustomFont);
        editTextMailingAddress.setTypeface(myCustomFont);
        editTextEmail.setTypeface(myCustomFont);
        editTextPassword.setTypeface(myCustomFont);
        editTextConfirmPassword.setTypeface(myCustomFont);
        editTextZipcode.setTypeface(myCustomFont);
        checkBoxAcceptTerms.setTypeface(myCustomFont);
        textViewTermsText.setTypeface(myCustomFont);
        textViewRegisterAs.setTypeface(myCustomFont);
        textViewTitleText.setTypeface(myCustomFont);

    }

    public void setUi() {
        checkBoxAcceptTerms.setText("");
        textViewTermsText.setText(Html.fromHtml("I have read and agree to the " +
                "<a href='com.app.david.mender.TermsOfUse://'>Terms of Use</a> and <a href='com.app.david.mender.TermsOfServiceActivity://'>Terms of Service</a>"));
        textViewTermsText.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // add items into spinner dynamically
    public void addItemsOnSpinner() {

        Spinner spinner = (Spinner) findViewById(R.id.et6);
        List<String> list = new ArrayList<String>();
        list.add("WA");
        list.add("HI");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                R.layout.state_spinner, list);
        dataAdapter.setDropDownViewResource(R.layout.spinner_list_item);
        spinner.setAdapter(dataAdapter);
    } //

    public void addListenerOnSpinnerItemSelection() {
        Spinner spinner = (Spinner) findViewById(R.id.et6);
        spinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());

    }
//signUpButton is "register"

    /*@OnClick(R.id.signUpButton)
    private void registerUser() {
        firstname = editTextFirstName.getText().toString().trim();
        lastname = editTextLastName.getText().toString().trim();
        phonenumber = editTextPhoneNumber.getText().toString().trim();
        city = editTextCity.getText().toString().trim();
        address = editTextMailingAddress.getText().toString().trim();
        email = editTextEmail.getText().toString().trim();
        password = editTextPassword.getText().toString().trim();
        confirmPassword = editTextConfirmPassword.getText().toString().trim();
        zipcode = editTextZipcode.getText().toString().trim();


//        int selectedId = radioUserGroup.getCheckedRadioButtonId();
//        radioUserButton = (RadioButton) findViewById(selectedId);
//        final String userType = radioUserButton.getText().toString();

        if (!Validation.isEmpty(firstname)) {
            Toast.makeText(this, "Please enter your First Name", Toast.LENGTH_LONG).show();
        } else if (!Validation.isEmpty(lastname)) {
            Toast.makeText(this, "Please enter your Last Name", Toast.LENGTH_LONG).show();
        } else if (!Validation.isEmpty(phonenumber)) {
            Toast.makeText(this, "Please enter your Phone Number", Toast.LENGTH_LONG).show();
        } else if (phonenumber.length() < 10) {
            Toast.makeText(this, "Please enter valid Phone Number", Toast.LENGTH_LONG).show();
        } else if (!Validation.isEmpty(address)) {
            Toast.makeText(this, "Please enter your Physical Address", Toast.LENGTH_LONG).show();
        } else if (!Validation.isEmpty(city)) {
            Toast.makeText(this, "Please enter the City", Toast.LENGTH_LONG).show();
        } else if (!Validation.isEmpty(email)) {
            Toast.makeText(this, "Please enter your Email Address", Toast.LENGTH_LONG).show();
        } else if (!Validation.isValidEmail(email)) {
            Toast.makeText(this, "Please enter valid Email Address", Toast.LENGTH_LONG).show();
        } else if (!Validation.isEmpty(password)) {
            Toast.makeText(this, "Please enter Password", Toast.LENGTH_LONG).show();
        } else if (!Validation.isEmpty(zipcode)) {
            Toast.makeText(this, "Please enter Zipcode", Toast.LENGTH_LONG).show();
        } else if (!checkBoxAcceptTerms.isChecked()) {
            Toast.makeText(this, "Please accept the terms of use and terms of service.", Toast.LENGTH_LONG).show();
        }

        // ToDo : Signup User!

        mProgressWheel.spin();


        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task1) {



                         *//*   firebaseauth.signInWithEmailAndPassword(email, password)
                                    .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {*//*
     *//*     @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {*//*
                        if (task1.isSuccessful()) {

                            // Save Customer Details in Firebase Backend.
                            DatabaseReference mRootReference = FirebaseDatabase.getInstance().getReference();
                            DatabaseReference usersReference = mRootReference.child("Users");
                            // TODO : For push notifications!
                            // DatabaseReference handyManReference = mRootReference.child("Handyman");

                            // Saving user details in database after registration!
                            String refreshedToken = FirebaseInstanceId.getInstance().getToken();
                            String key = usersReference.push().getKey();
                            HashMap<String, Object> f = new HashMap<>();

                            f.put("firstName", firstname);
                            f.put("lastName", lastname);
                            f.put("userEmail", email);
                            f.put("cityState", city);
                            f.put("address", address);
                            f.put("key", key);
                            f.put("zipcode", zipcode);
                            f.put("userType", "Client");
                            f.put("phonenumber", phonenumber);
                            f.put("token", refreshedToken);
                            //f.put("dateofbirth", state);

                            Map<String, Object> child = new HashMap<String, Object>();
                            child.put(key, f);

                            Map<String, Object> map = new HashMap<>();
                            map.put(key, refreshedToken);

                            // TODO : For Push Notifications!
                            // handyManReference.updateChildren(map);

                            usersReference.updateChildren(child, new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                    if (databaseError == null) {
                                        Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                            // Toast.makeText(SignUpActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                            mProgressWheel.stopSpinning();


                        } else {
                            mProgressWheel.stopSpinning();
                            Toast.makeText(SignUpActivity.this, "Could Not Complete Registration ", Toast.LENGTH_SHORT).show();
                        }


                    }


                });


    }*/

    private void register_user(final String display_name, final String email, String password) {
        if (!error) {
            mRegProgress.show();
            firebaseauth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                        String uid = current_user.getUid();
                        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
                        String key = mDatabase.push().getKey();
                        String device_token = FirebaseInstanceId.getInstance().getToken();
                        HashMap<String, String> userMap = new HashMap<>();
                        userMap.put("firstName", firstname);
                        userMap.put("lastName", lastname);
                        userMap.put("userEmail", email);
                        userMap.put("cityState", city);
                        userMap.put("address", address);
                        userMap.put("key", key);
                        userMap.put("zipcode", zipcode);
                        userMap.put("userType", "Client");
                        userMap.put("phonenumber", phonenumber);
                        userMap.put("token", device_token);
                        mDatabase.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    mRegProgress.dismiss();
                                    Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();
                                    //Toast.makeText(SignUpActivity.this, " Complete Registration ", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        mRegProgress.hide();
                        // Toast.makeText(SignUpActivity.this, "Cannot Sign in. Please check the form and try again.", Toast.LENGTH_LONG).show();
                        if (!task.isSuccessful()) {
                            FirebaseAuthException e = (FirebaseAuthException) task.getException();
                            Toast.makeText(SignUpActivity.this, "Failed Registration: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            //  message.hide();
                            return;
                        }
                    }
                }
            });
        } else {

        }

    }

    @Override
    public void onClick(View view) {
        if (view == signUpButton) {
            // registerUser();
            input();
            if (AppUtils.isConnected(getApplicationContext())) {
                register_user(firstname, email, password);
            } else {
                Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void input() {
        firstname = editTextFirstName.getText().toString().trim();
        lastname = editTextLastName.getText().toString().trim();
        phonenumber = editTextPhoneNumber.getText().toString().trim();
        city = editTextCity.getText().toString().trim();
        address = editTextMailingAddress.getText().toString().trim();
        email = editTextEmail.getText().toString().trim();
        password = editTextPassword.getText().toString().trim();
        confirmPassword = editTextConfirmPassword.getText().toString().trim();
        zipcode = editTextZipcode.getText().toString().trim();

//        int selectedId = radioUserGroup.getCheckedRadioButtonId();
//        radioUserButton = (RadioButton) findViewById(selectedId);
//        final String userType = radioUserButton.getText().toString();
        if (!Validation.isEmpty(firstname)) {
            Toast.makeText(this, "Please enter your First Name", Toast.LENGTH_SHORT).show();
            error = true;
            return;
        }
        if (!Validation.isEmpty(lastname)) {
            Toast.makeText(this, "Please enter your Last Name", Toast.LENGTH_SHORT).show();
            error = true;
            return;
        }
        if (!Validation.isEmpty(email)) {
            Toast.makeText(this, "Please enter your Email Address", Toast.LENGTH_SHORT).show();
            error = true;
            return;
        }
        if (!Validation.isValidEmail(email)) {
            Toast.makeText(this, "Please enter valid Email Address", Toast.LENGTH_SHORT).show();
            error = true;
            return;
        }
        if (!Validation.isEmpty(phonenumber)) {
            Toast.makeText(this, "Please enter your Phone Number", Toast.LENGTH_SHORT).show();
            error = true;
            return;
        }
        if (phonenumber.length() < 10||phonenumber.length()!=10) {
            Toast.makeText(this, "Please enter valid Phone Number", Toast.LENGTH_SHORT).show();
            error = true;
            return;
        }
//        if (TextUtils.isEmpty(state)) {
//            Toast.makeText(this, "Please chose a State", Toast.LENGTH_SHORT).show();
//            return;
//        }
        if (!Validation.isEmpty(address)) {
            Toast.makeText(this, "Please enter your Physical Address", Toast.LENGTH_SHORT).show();
            error = true;
            return;
        }
        /*if (TextUtils.isEmpty(city)) {
            Toast.makeText(this, "Please enter the City", Toast.LENGTH_SHORT).show();
            error = true;
            return;
        }*/
        if (!Validation.isEmpty(zipcode)) {
            Toast.makeText(this, "Please enter Zipcode", Toast.LENGTH_SHORT).show();
            error = true;
            return;
        }

        if (!Validation.isEmpty(password)) {
            Toast.makeText(this, "Please enter Password", Toast.LENGTH_SHORT).show();
            error = true;
            return;
        }
        if (!Validation.isEmpty(confirmPassword)) {
            Toast.makeText(this, "Please enter Confirm Password", Toast.LENGTH_SHORT).show();
            error = true;
            return;
        }
        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Confirm password do not match", Toast.LENGTH_SHORT).show();
            error = true;
            return;
        }
//        if (TextUtils.isEmpty(userType)) {
//            Toast.makeText(this, "Please select a User Type", Toast.LENGTH_SHORT).show();
//            return;
//        }

//        if (confirmPassword != password) {
//            Toast.makeText(this, "Make sure both passwords you entered are same.", Toast.LENGTH_SHORT).show();
//            return;
//        }

        if (!checkBoxAcceptTerms.isChecked()) {
            Toast.makeText(this, "Please accept the terms of use and terms of service.", Toast.LENGTH_SHORT).show();
            error = true;
            return;
        } else {
            error = false;
        }
    }
}





package apps.kinmniekan_code.pedidosfirebase.VIEW;

import android.accounts.AccountManager;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.common.AccountPicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import apps.kinmniekan_code.pedidosfirebase.R;
import apps.kinmniekan_code.pedidosfirebase.MODEL.User;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "EmailPassword";

    private EditText et_name,mPasswordField;
    private TextView tv_email;
    private Button btnRegister ,btnLogin;
    private Toolbar toolbar;
    int ACCOUNT_PICKER_REQ_CODE = 2;

    private FirebaseAuth mAuth;

    String email;
    String name;
    String pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_name = (EditText)findViewById(R.id.et_nombre_logIn);
        mPasswordField=(EditText)findViewById(R.id.et_pass_logIn);
        tv_email=(TextView)findViewById(R.id.tv_email_logIn);
        btnRegister =(Button)findViewById(R.id.btn_register_logIn);
        btnRegister.setOnClickListener(this);
        btnLogin = (Button)findViewById(R.id.btn_login_logIn);
        btnLogin.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
        createIntentPiker();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null) {
            goToMainActivity();
        }
    }

    private void goToMainActivity() {
        startActivity(new Intent(this,MainActivity.class));
    }

    private void createAccount(String email, String password) {

        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            goToMainActivity();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        if (user!=null){
            User myuser = new User();
            myuser.setEmail(user.getEmail());
            myuser.setFullname(name);
            myuser.setId(user.getUid());
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference();
            myRef.child("Users").child(user.getUid()).setValue(myuser);
        } else {
            Toast.makeText(this,"user fail",Toast.LENGTH_SHORT).show();
        }
    }


    private void signIn(String email, String password) {

        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            goToMainActivity();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            String message = task.getException().getMessage();
                            Toast.makeText(LoginActivity.this, message,
                                    Toast.LENGTH_LONG).show();
                        }



                    }
                });
    }

    private void sendEmailVerification() {
        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // [START_EXCLUDE]

                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this,
                                    "Verification email sent to " + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e(TAG, "sendEmailVerification", task.getException());
                            Toast.makeText(LoginActivity.this,
                                    "Failed to send verification email.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // [END_EXCLUDE]
                    }
                });
        // [END send_email_verification]
    }
    private boolean validateForm() {
        boolean valid = true;

        if (TextUtils.isEmpty(email)) {
            createIntentPiker();
            valid = false;
        }

        String name = et_name.getText().toString();
        if (TextUtils.isEmpty(name)) {
            et_name.setError("Required.");
            valid = false;
        } else {
            mPasswordField.setError(null);
        }

        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError("Required.");
            valid = false;
        } else {
            mPasswordField.setError(null);
        }

        return valid;
    }


    private void createIntentPiker(){
        Intent intent = AccountPicker.newChooseAccountIntent(null, null, new String[]{GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE},
                false, null, null, null, null);
        startActivityForResult(intent, ACCOUNT_PICKER_REQ_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ACCOUNT_PICKER_REQ_CODE && resultCode == RESULT_OK) {
            String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
            tv_email.setText(accountName);
            email = accountName;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_register_logIn:
                name = et_name.getText().toString();
                email = tv_email.getText().toString();
                pass = mPasswordField.getText().toString();
                createAccount(email, pass);
                break;

            case R.id.btn_login_logIn:
                email = tv_email.getText().toString();
                pass = mPasswordField.getText().toString();
                signIn(email,pass);
        }

    }
}

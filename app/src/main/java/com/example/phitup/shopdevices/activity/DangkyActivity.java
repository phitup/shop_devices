package com.example.phitup.shopdevices.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.phitup.shopdevices.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class DangkyActivity extends AppCompatActivity {

    private FirebaseAuth mAuth, mAuthListener;
    private DatabaseReference mDatabase;
    private FirebaseUser user;

    private EditText edtDisplayName, edtEmail, edtPassword;
    private Button btnDangKy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangky);

        AnhXa();
        mAuth = FirebaseAuth.getInstance();

        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getDisplay = edtDisplayName.getText().toString();
                String getEmail = edtEmail.getText().toString();
                String getPassword = edtPassword.getText().toString();
                if(!getDisplay.isEmpty() && !getEmail.isEmpty() && !getPassword.isEmpty()){
                    SignUp(getDisplay, getEmail, getPassword);
                    user = mAuth.getCurrentUser();
                    user.sendEmailVerification();
                    boolean emailVerified = user.isEmailVerified();
                    if(emailVerified){
                        startActivity(new Intent(DangkyActivity.this, MainActivity.class));
                    }
                }else{
                    Toast.makeText(DangkyActivity.this, "Yêu cầu điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void SignUp(String display, String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            user = mAuth.getCurrentUser();
                            String uid = user.getUid();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.d("BB", "Lỗi Đăng Ký");
                        }

                        // ...
                    }
                });
    }

    private void AnhXa() {
        edtDisplayName = findViewById(R.id.edittextDisplayname);
        edtEmail = findViewById(R.id.edittextemail);
        edtPassword = findViewById(R.id.edittextPassword);
        btnDangKy = findViewById(R.id.button);
    }
}

package com.example.chatapp2020;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    EditText etid,etpw;
    private static final String TAG = "MainActivity";
    ProgressBar progressBar;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 컨텐츠뷰가 엑티비티 메인 레이아웃으로 설정되어 있어서
        // 다른 레이아웃에 동일한 이름의 ID를 가진 버튼이 있어도
        // 혼동없이 사용할수 있다.
        mAuth = FirebaseAuth.getInstance();

         etid = (EditText)findViewById(R.id.etId);
         etpw = (EditText)findViewById(R.id.etPassword);
         progressBar = (ProgressBar)findViewById(R.id.progressBar);


        Button btnlogin = (Button)findViewById(R.id.btnLogin);




        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText( MainActivity.this, "로그인",Toast.LENGTH_LONG).show();
//                this를 그냥 쓰게 되면 버튼로그인 함수를 가리키게 되므로 에러가 발생한다
//                MainActivity를 작성해야만 이 자바 파일 자체를 선택 하므로
//                에러가 발생하지 않는다.
                final String stEmail = etid.getText().toString();
                String stPassword = etpw.getText().toString();
                if(stEmail.isEmpty()) {
                    Toast.makeText(MainActivity.this,"이메일을 입력 해주세요",Toast.LENGTH_LONG).show();
                    return;
                }
                if(stPassword.isEmpty()) {
                    Toast.makeText(MainActivity.this,"비밀번호를 입력 해주세요",Toast.LENGTH_LONG).show();
                    return;
                }
                progressBar.setVisibility(view.VISIBLE);
                mAuth.signInWithEmailAndPassword(stEmail, stPassword)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    String stUserEmail = user.getEmail();
                                    String stUserName = user.getDisplayName();
                                    Log.d(TAG,"stUserEmail" +stUserEmail);
                                    Intent in = new Intent(MainActivity.this, ChatActivity.class);
                                    in.putExtra("email",stEmail);
                                    startActivity(in);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(MainActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }

                                // ...
                            }
                        });

            }
        });

        Button btnregister = (Button)findViewById(R.id.btnRegister);
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stEmail = etid.getText().toString();
                String stPassword = etpw.getText().toString();
                if(stEmail.isEmpty()) {
                    Toast.makeText(MainActivity.this,"이메일을 입력 해주세요",Toast.LENGTH_LONG).show();
                    return;
                }
                if(stPassword.isEmpty()) {
                    Toast.makeText(MainActivity.this,"비밀번호를 입력 해주세요",Toast.LENGTH_LONG).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
//                Toast.makeText(MainActivity.this, "Email : "+ stEmail + ", Password : "
                mAuth.createUserWithEmailAndPassword(stEmail, stPassword)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    //updatedateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(MainActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
//                                    updateUI(null);
                                }

                                // ...
                            }
                        });
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
    }
}

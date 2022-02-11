package com.sinhvien.lorenzo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    EditText edtUserName,edtPass;
    DBHelper db;
    Button btnLogin;
    TextView dangKi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtPass=findViewById(R.id.txtPass);
        edtUserName=findViewById(R.id.txtUserName);
        btnLogin=findViewById(R.id.btnLogin);
        dangKi=findViewById(R.id.txtDangKi);
        db=new DBHelper(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtPass.getText().toString().matches("")||edtUserName.getText().toString().matches(""))
                {
                    Toast.makeText(LoginActivity.this,"Vui lòng nhập đầy đủ thông tin đăng nhập!",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    boolean flag=false;
                    ArrayList<Account>lstAccount=new ArrayList<Account>();
                    lstAccount=db.getAllAccountFromCustomer();
                    for (int i=0;i<lstAccount.size();i++)
                    {
                        if(edtUserName.getText().toString().matches(lstAccount.get(i).getAccount())&&edtPass.getText().toString().matches(lstAccount.get(i).getPassword()))
                        {
                            if(db.insertAccount(edtUserName.getText().toString(),edtPass.getText().toString()))
                            {
                                Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(LoginActivity.this,MainActivity.class);

//                                Bundle bundle=new Bundle();
//                                bundle.putString("account",edtUserName.getText().toString());
//
//                                intent.putExtra("data",bundle);
                                startActivity(intent);
                                flag=true;
                                finish();
                            }
                            else
                            {
                                Toast.makeText(LoginActivity.this, "Gặp sự cố khi đăng nhập!", Toast.LENGTH_SHORT).show();
                                flag=true;
                            }
                        }
                    }
                    if(flag==false)
                    {
                        Toast.makeText(LoginActivity.this, "Tài Khoản hoặc mật khẩu không đúng. Vui lòng kiểm tra lại!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        dangKi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoveToDangKi(v);
            }

            private void MoveToDangKi(View v) {

                Intent i=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(i);

            }
        });

    }
}
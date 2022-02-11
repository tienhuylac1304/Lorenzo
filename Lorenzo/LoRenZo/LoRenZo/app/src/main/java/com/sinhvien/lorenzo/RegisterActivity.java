package com.sinhvien.lorenzo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    TextInputEditText edtname,edtDate,edtPhone,edtAddress,edtEmail,edtUserName,edtPass,edtXNPass;
    RadioButton rdoBtnNam,rdoBtnNu;
    Button btnHuy,btnDK;
    ImageButton btnback;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtname=findViewById(R.id.txtname);
        edtAddress=findViewById(R.id.txtAddres);
        edtDate=findViewById(R.id.txtDate);
        edtEmail=findViewById(R.id.txtEmail);
        edtPass=findViewById(R.id.txtPass);
        edtPhone=findViewById(R.id.txtPhone);
        edtXNPass=findViewById(R.id.txtXNpass);
        edtUserName=findViewById(R.id.txtUserName);
        btnback=findViewById(R.id.btnBack);
        btnDK=findViewById(R.id.btnDangki);
        btnHuy=findViewById(R.id.btnHuy);
        rdoBtnNam=findViewById(R.id.rdobtnNam);
        rdoBtnNu=findViewById(R.id.rdobtnNu);

         rdoBtnNam.setChecked(true);

        rdoBtnNam.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (rdoBtnNam.isChecked())
                {
                    rdoBtnNam.setChecked(false);
                    rdoBtnNu.setChecked(true);
                }
                else
                {
                    rdoBtnNam.setChecked(true);
                    rdoBtnNu.setChecked(false);
                }
            }
        });

        rdoBtnNu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (rdoBtnNu.isChecked())
                {
                    rdoBtnNam.setChecked(true);
                    rdoBtnNu.setChecked(false);
                }
                else
                {
                    rdoBtnNam.setChecked(false);
                    rdoBtnNu.setChecked(true);
                }
            }
        });

        View.OnClickListener listener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        };
        btnHuy.setOnClickListener(listener);
        btnback.setOnClickListener(listener);
        btnDK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(edtUserName.getText().toString().matches("")||edtPass.getText().toString().matches("")||edtDate.getText().toString().matches("")||edtname.getText().toString().matches("")||edtAddress.getText().toString().matches("")||edtEmail.getText().toString().matches("")||edtXNPass.getText().toString().matches("")||edtPhone.getText().toString().matches(""))
                {
                    Toast.makeText(RegisterActivity.this, "Vui lòng điền đầy dủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else
                {

                    if(edtXNPass.getText().toString().matches(edtPass.getText().toString()))
                    {
                        Insert(edtname.getText().toString(),SexType(),edtDate.getText().toString(),edtEmail.getText().toString(),edtAddress.getText().toString(),edtUserName.getText().toString(),edtPass.getText().toString(),edtPhone.getText().toString());
                    }
                    else
                    {
                        Toast.makeText(RegisterActivity.this, "Xác nhận mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }
    private void Insert(String name,int sex,String date,String email,String address,String account,String pass,String phone)
    {

        API_Service.api.postCustomer(name,sex,date,address,phone,email,account,pass).enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                Toast.makeText(RegisterActivity.this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Đăng kí thất bại", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private int SexType()
    {
        if(rdoBtnNam.isChecked())
            return 1;
        else
            return 0;
    }
}
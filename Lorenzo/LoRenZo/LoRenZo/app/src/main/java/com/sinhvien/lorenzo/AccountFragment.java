package com.sinhvien.lorenzo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

public class AccountFragment extends Fragment {

    DBHelper db;
    Button btnLogout;
    TextView txtDate,txtSex,txtPhone,txtEmail,txtHoTen,txtDiaChi;
    //TextView txtName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_accout,container,false);

        db=new DBHelper(getActivity());
        btnLogout=v.findViewById(R.id.btnLogout);
        txtHoTen=v.findViewById(R.id.txtHoTen);
        //txtName=v.findViewById(R.id.txtname);
        txtDate=v.findViewById(R.id.txtNgaySinh);
        txtSex=v.findViewById(R.id.txtGioiTinh);
        txtEmail=v.findViewById(R.id.txtEmail);
        txtPhone=v.findViewById(R.id.txtSDT);
        txtDiaChi=v.findViewById(R.id.txtAddres);
        db=new DBHelper(getActivity());

        LoadData();

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(db.deleteAccount())
                {
                    Toast.makeText(getActivity(), "Đăng xuất thành công!", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(getActivity(),MainActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
                else
                {
                    Toast.makeText(getActivity(), "Đăng xuất thất bại!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return v;
    }
    private void LoadData()
    {
        Customer khachHang=new Customer();
        khachHang=db.getCustomerByAccount(db.getAccount());

        txtHoTen.setText(khachHang.getName());
        txtDiaChi.setText(khachHang.getAdress());
        txtPhone.setText(khachHang.getPhone());
        txtEmail.setText(khachHang.getEmail());
        txtDate.setText(khachHang.getDate());
        //txtName.setText(khachHang.getAccount());
        if(khachHang.getSex()==1)
        {
            txtSex.setText("Nam");
        }
        else
        {
            txtSex.setText("Nữ");
        }

    }
}

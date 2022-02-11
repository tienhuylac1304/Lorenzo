package com.sinhvien.lorenzo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class DBHelper{
    Context context;
    String DB_NAME = "LoRenZo2.db";

    public DBHelper(Context context) {
        this.context = context;
        copyDatabase();
        creteTableCart();
        createTableAccount();
        LoadData1();
        LoadCustomerData1();
    }

    private boolean insertProduct(Product product) {
        //them hàng vào giỏ hàng
        boolean flag = false;
        //1
        SQLiteDatabase db = openDB();
        //2
        ContentValues contentValues = new ContentValues();

        contentValues.put("MaSP",product.getIdSP());
        contentValues.put("TenSP",product.getTenSP());
        contentValues.put("HinhAnh",product.getHinhAnh());
        contentValues.put("Loai",getIdLoai(product.getLoai()));
        contentValues.put("MoTa",product.getMoTa());
        contentValues.put("Gia",product.getGia());
        contentValues.put("SoLuong",product.getSoLuong());
        //3
        flag = db.insert("SanPham", null, contentValues) > 0;
        //4
        closeDB(db);
        return flag;
    }
    private boolean insertCustomer(Customer customer) {
        //them hàng vào giỏ hàng
        boolean flag = false;
        //1
        SQLiteDatabase db = openDB();
        //2
        ContentValues contentValues = new ContentValues();

        contentValues.put("MaKH",customer.getId());
        contentValues.put("HoTen",customer.getName());
        contentValues.put("GioiTinh",customer.getSex());
        contentValues.put("NgaySinh",customer.getDate());
        contentValues.put("DiaChi",customer.getAdress());
        contentValues.put("SoDT",customer.getPhone());
        contentValues.put("Email",customer.getEmail());
        contentValues.put("TaiKhoan",customer.getAccount());
        contentValues.put("MatKhau",customer.getPassword());
        //3
        flag = db.insert("KhachHang", null, contentValues) > 0;
        //4
        closeDB(db);
        return flag;
    }

    public int getCountAccount(){
        int account = 0;

        SQLiteDatabase db = openDB();
        String sql = "SELECT count(*) FROM Account;";
        Cursor cursor = db.rawQuery(sql,null);

        while (cursor.moveToNext()){

            account=cursor.getInt(0);
        }
        //5
        closeDB(db);
        return account;
    }

    public String getAccount(){
        String account = "";

        SQLiteDatabase db = openDB();
        String sql = "SELECT account FROM Account;";
        Cursor cursor = db.rawQuery(sql,null);

        while (cursor.moveToNext()){

            account=cursor.getString(0);
        }
        //5
        closeDB(db);
        return account;
    }

    public boolean deleteAccount(){
        boolean flag = false;
        //1
        SQLiteDatabase db = openDB();
        flag = db.delete("Account", null, null) > 0;
        closeDB(db);
        return flag;
    }

    public boolean insertAccount(String userName,String pass) {
        //them hàng vào giỏ hàng
        boolean flag = false;
        //1
        SQLiteDatabase db = openDB();
        //2
        ContentValues contentValues = new ContentValues();
        contentValues.put("account", userName);
        contentValues.put("pass",pass);
        //3
        flag = db.insert("Account", null, contentValues) > 0;
        //4
        closeDB(db);
        return flag;
    }

    private void createTableAccount() {
        SQLiteDatabase db = openDB();

        String sql = "create table if not exists Account(" +
                "id integer PRIMARY KEY AUTOINCREMENT," +
                "account text NOT NULL,"+
                "pass text NOT NULL)";

        db.execSQL(sql);

        closeDB(db);
    }

    public ArrayList<Account> getAllAccountFromCustomer(){
        ArrayList<Account> lstAccount = new ArrayList<Account>();

        SQLiteDatabase db = openDB();
        String sql = "SELECT TaiKhoan, MatKhau FROM KhachHang;";
        Cursor cursor = db.rawQuery(sql,null);

        while (cursor.moveToNext()){
            Account account=new Account();
            account.setAccount(cursor.getString(0));
            account.setPassword(cursor.getString(1));
            lstAccount.add(account);
        }
        //5
        closeDB(db);
        return lstAccount;
    }

    private void creteTableCart()
    {
        //tạo bảng giỏ hàng
        SQLiteDatabase db = openDB();

        String sql = "create table if not exists Cart(" +
                "id integer PRIMARY KEY AUTOINCREMENT," +
                "MaSP integer NOT NULL,"+
                "SoLuong intrger NOT NULL," +
                "ThanhTien integer NOT NULL) ";

        db.execSQL(sql);

        closeDB(db);

    }
    private void copyDatabase(){

        File dbFile = context.getDatabasePath(DB_NAME);
        if (!dbFile.exists()) {
            try {
                InputStream is = context.getAssets().open(DB_NAME);
                OutputStream os = new FileOutputStream(dbFile);
                byte[] buffer = new byte[1024];
                while (is.read(buffer) > 0) {
                    os.write(buffer);
                }
                os.flush();
                os.close();
                is.close();
            } catch (IOException e) {
                throw new RuntimeException("Error creating source database", e);
            }
        }
    }
    public SQLiteDatabase openDB(){
        return context.openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE, null);
    }

    public void closeDB(SQLiteDatabase db){
        db.close();
    }
    public ArrayList<Product> getAllProduct(){
        ArrayList<Product> arrayList = new ArrayList<>();

        SQLiteDatabase db = openDB();
        String sql = "SELECT MaSP, TenSP, HinhAnh, TenLoai, MoTa, Gia, SoLuong FROM SanPham, Loai WHERE SanPham.Loai=Loai.MaLoai;";
        Cursor cursor = db.rawQuery(sql,null);

        while (cursor.moveToNext()){

            int id=cursor.getInt(0);
            String name =  cursor.getString(1);
            String image = cursor.getString(2);
            String loai=cursor.getString(3);
            String moTa=cursor.getString(4);
            int gia=cursor.getInt(5);
            int soLuong=cursor.getInt(6);

            arrayList.add(new Product(id, name, image,loai,moTa,gia,soLuong));
        }
        //5
        closeDB(db);
        return arrayList;
    }

    public ArrayList<Product> getProductsWithType(String type){
        ArrayList<Product> arrayList = new ArrayList<>();

        SQLiteDatabase db = openDB();
        String sql = "SELECT MaSP, TenSP, HinhAnh, TenLoai, MoTa, Gia, SoLuong FROM SanPham, Loai WHERE SanPham.Loai=Loai.MaLoai AND TenLoai LIKE'"+type+"'";
        Cursor cursor = db.rawQuery(sql,null);

        while (cursor.moveToNext()){

            int id=cursor.getInt(0);
            String name =  cursor.getString(1);
            String image = cursor.getString(2);
            String loai=cursor.getString(3);
            String moTa=cursor.getString(4);
            int gia=cursor.getInt(5);
            int soLuong=cursor.getInt(6);

            arrayList.add(new Product(id, name, image,loai,moTa,gia,soLuong));
        }
        //5
        closeDB(db);
        return arrayList;
    }

    public int getIdLoai(String typeName){

        int typeId=-1;
        SQLiteDatabase db = openDB();
        String sql = "SELECT MaLoai FROM Loai WHERE  TenLoai ='"+typeName+"'";
        Cursor cursor = db.rawQuery(sql,null);

        while (cursor.moveToNext()){
            typeId=cursor.getInt(0);
        }
        //5
        closeDB(db);
        return typeId;
    }

    public ArrayList<Product> getProductsWithName(String Pname){
        ArrayList<Product> arrayList = new ArrayList<>();

        SQLiteDatabase db = openDB();
        String sql = "SELECT MaSP, TenSP, HinhAnh, TenLoai, MoTa, Gia, SoLuong FROM SanPham, Loai WHERE SanPham.Loai=Loai.MaLoai AND TenSP LIKE'%"+Pname+"%'";
        Cursor cursor = db.rawQuery(sql,null);

        while (cursor.moveToNext()){

            int id=cursor.getInt(0);
            String name =  cursor.getString(1);
            String image = cursor.getString(2);
            String loai=cursor.getString(3);
            String moTa=cursor.getString(4);
            int gia=cursor.getInt(5);
            int soLuong=cursor.getInt(6);

            arrayList.add(new Product(id, name, image,loai,moTa,gia,soLuong));
        }
        //5
        closeDB(db);
        return arrayList;
    }

    public Product getProductsWithID(int maSP){
        Product product=new Product();

        SQLiteDatabase db = openDB();
        String sql = "SELECT MaSP, TenSP, HinhAnh, TenLoai, MoTa, Gia, SoLuong FROM SanPham, Loai WHERE SanPham.Loai=Loai.MaLoai AND MaSP='"+String.valueOf(maSP)+"'";
        Cursor cursor = db.rawQuery(sql,null);

        while (cursor.moveToNext()){

            product.setIdSP(cursor.getInt(0));
            product.setTenSP(cursor.getString(1));
            product.setHinhAnh(cursor.getString(2));
            product.setLoai(cursor.getString(3));
            product.setMoTa(cursor.getString(4));
            product.setGia(cursor.getInt(5));
            product.setSoLuong(cursor.getInt(6));

        }
        //5
        closeDB(db);
        return product;
    }

    public boolean insertCart(int id, int soluong,int thanhTien) {
        //them hàng vào giỏ hàng
        boolean flag = false;
        //1
        SQLiteDatabase db = openDB();
        //2
        ContentValues contentValues = new ContentValues();
        contentValues.put("MaSP", id);
        contentValues.put("SoLuong", soluong);
        contentValues.put("ThanhTien",thanhTien);
        //3
        flag = db.insert("Cart", null, contentValues) > 0;
        //4
        closeDB(db);
        return flag;
    }
    public ArrayList<Cart> getAllCart(){
        ArrayList<Cart> arrayList = new ArrayList<>();

        SQLiteDatabase db = openDB();
        String sql = "SELECT Cart.id,TenSP, HinhAnh,ThanhTien, Cart.SoLuong FROM SanPham,Cart WHERE SanPham.MaSP=Cart.MaSP;";
        Cursor cursor = db.rawQuery(sql,null);

        while (cursor.moveToNext()){

            int id=cursor.getInt(0);
            String name =  cursor.getString(1);
            String image = cursor.getString(2);
            int thanhTien=cursor.getInt(3);
            int soLuong=cursor.getInt(4);

            arrayList.add(new Cart(id, name, image,soLuong,thanhTien));
        }
        //5
        closeDB(db);
        return arrayList;
    }
    public boolean deleteCart(int id){
        boolean flag = false;
        //1
        SQLiteDatabase db = openDB();
        flag = db.delete("Cart", "id="+id, null) > 0;
        closeDB(db);
        return flag;
    }

    public ArrayList<String> getAllProductName() {
        ArrayList<String> arrayList = new ArrayList<>();

        SQLiteDatabase db = openDB();
        String sql = "SELECT TenSP FROM SanPham;";
        Cursor cursor = db.rawQuery(sql,null);

        while (cursor.moveToNext()){

            String name =  cursor.getString(0);
            arrayList.add(name);

        }
        closeDB(db);
        return arrayList;
    }
    public ArrayList<String> getAllProctType() {
        ArrayList<String> arrayList = new ArrayList<>();

        SQLiteDatabase db = openDB();
        String sql = "SELECT TenLoai FROM Loai;";
        Cursor cursor = db.rawQuery(sql,null);

        while (cursor.moveToNext()){
            String name =  cursor.getString(0);
            arrayList.add(name);
        }
        closeDB(db);
        return arrayList;
    }

    public boolean deleteTable(String table){
        boolean flag = false;
        //1
        SQLiteDatabase db = openDB();
        flag = db.delete(table,null, null) > 0;
        closeDB(db);
        return flag;
    }

    //Customer

    public Customer getCustomerByAccount(String account){
        Customer kh=new Customer();

        SQLiteDatabase db = openDB();
        String sql = "SELECT * FROM KhachHang WHERE TaiKhoan='"+account+"'";
        Cursor cursor = db.rawQuery(sql,null);

        while (cursor.moveToNext()){

            kh.setId(cursor.getInt(0));
            kh.setName(cursor.getString(1));
            kh.setSex(cursor.getInt(2));
            kh.setDate(cursor.getString(3));
            kh.setAdress(cursor.getString(4));
            kh.setPhone(cursor.getString(5));
            kh.setEmail(cursor.getString(6));
            kh.setAccount(cursor.getString(7));
            kh.setPassword(cursor.getString(8));

        }
        //5
        closeDB(db);
        return kh;
    }

    /*public boolean insertStudent( student){
        boolean flag = false;
        //1
        SQLiteDatabase db = openDB();
        //2
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", student.getName());
        contentValues.put("address", student.getAddress());
        //3
        flag = db.insert("tblStudent", null, contentValues) > 0;
        //4
        closeDB(db);
        return flag;
    }

    /*public boolean updateStudent(int id, Student student){
        boolean flag = false;
        //1
        SQLiteDatabase db = openDB();
        //2
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", student.getName());
        contentValues.put("address", student.getAddress());
        //3
        flag = db.update("tblStudent",
                contentValues,
                "id="+id,
                null) > 0;
        //4
        closeDB(db);
        return flag;
    }
    */
    private void LoadData1()
    {
        API_Service.api.getAllProduct().enqueue(new Callback<ListProducts>() {
            @Override
            public void onResponse(Call<ListProducts> call, retrofit2.Response<ListProducts> response) {
                ArrayList<Product>lstProduct=new ArrayList<>();
                ListProducts listP=response.body();
                if(listP!=null)
                {
                    deleteTable("SanPham");
                    for (int i=0;i<listP.product.size();i++)
                    {
                        Product item=new Product();

                        item.setIdSP(listP.product.get(i).getIdSP());
                        item.setTenSP(listP.product.get(i).getTenSP());
                        item.setGia(listP.product.get(i).getGia());
                        item.setHinhAnh(listP.product.get(i).getHinhAnh());
                        item.setSoLuong(listP.product.get(i).getSoLuong());
                        item.setMoTa(listP.product.get(i).getMoTa());
                        item.setLoai(listP.product.get(i).getLoai());

                        insertProduct(item);
                    }
                }

            }

            @Override
            public void onFailure(Call<ListProducts> call, Throwable t) {

                Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void LoadCustomerData1()
    {
        API_Service.api.getAllKhachHang().enqueue(new Callback<ListCustomer>() {
            @Override
            public void onResponse(Call<ListCustomer> call, retrofit2.Response<ListCustomer> response) {
                ArrayList<Customer>lstProduct=new ArrayList<>();
                ListCustomer listP=response.body();
                if(listP!=null)
                {
                    deleteTable("KhachHang");
                    for (int i=0;i<listP.customer.size();i++)
                    {
                        Customer item=new Customer();

                        item.setId(listP.customer.get(i).getId());
                        item.setName(listP.customer.get(i).getName());
                        item.setSex(listP.customer.get(i).getSex());
                        item.setDate(listP.customer.get(i).getDate());
                        item.setAdress(listP.customer.get(i).getAdress());
                        item.setEmail(listP.customer.get(i).getEmail());
                        item.setAccount(listP.customer.get(i).getAccount());
                        item.setPassword(listP.customer.get(i).getPassword());
                        item.setPhone(listP.customer.get(i).getPhone());

                        insertCustomer(item);
                    }
                }

            }

            @Override
            public void onFailure(Call<ListCustomer> call, Throwable t) {

                Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();

            }
        });
    }

//    public void getCustomerDataFromAPI()
//    {
//        String url="http://192.168.1.7:4000/customer";
//        jsonArrayRequest=new JsonObjectRequest(Request.Method.GET,url,null,new com.android.volley.Response.Listener<JSONObject>(){
//            @Override
//            public void onResponse(JSONObject response) {
//
//                try {
//                    JSONArray products=response.getJSONArray("product");
//                    for(int i=0;i<products.length();i++)
//                    {
//                        JSONObject jsonObject= products.getJSONObject(i);
//                        Product customListViewItem=new Product();
//                        customListViewItem.setIdSP(jsonObject.getInt("id"));
//                        customListViewItem.setTenSP(jsonObject.getString("name"));
//                        customListViewItem.setGia(jsonObject.getInt("price"));
//                        customListViewItem.setHinhAnh(jsonObject.getString("image"));
//                        customListViewItem.setIdSP(jsonObject.getInt("numProduct"));
//                        customListViewItem.setMoTa(jsonObject.getString("description"));
//                        customListViewItem.setLoai(jsonObject.getString("type"));
//                        insertCustomer(customListViewItem);
//                    }
//
//                }
//
//                catch (JSONException e) {
//                    Toast.makeText(context,e.toString(),Toast.LENGTH_LONG).show();               }
//
//            }
//
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
//            }
//        });
//
//        mQueue= Volley.newRequestQueue(context);
//        mQueue.add(jsonArrayRequest);
//    }
//
//    private boolean insertCustomer(Product customListViewItem) {
//        boolean flag = false;
//        //1
//        SQLiteDatabase db = openDB();
//        //2
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("MaKH", id);
//        contentValues.put("SoLuong", soluong);
//        contentValues.put("ThanhTien",thanhTien);
//        //3
//        flag = db.insert("KhachHang", null, contentValues) > 0;
//        //4
//        closeDB(db);
//        return flag;
//    }
}

package com.chzu.txgc.pdd.Dao;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.blankj.utilcode.util.StringUtils;
import com.chzu.txgc.pdd.DbHelper.MyDbHelper;
public class MyDao {
    //把数据库创建出来
    private MyDbHelper dbHelper;
    private String account_num = "account_num";//用户表
    private String account_gwc="account_gwc";//加入购物车表
    private MyDao(Context ctx) {
        dbHelper = new MyDbHelper(ctx, "pdd.db", null, 1);
    }
    private static MyDao instance;//使用单例模式
    public static synchronized MyDao getInstance(Context ctx) {
        if (instance == null) {
            instance = new MyDao(ctx);
        }
        return instance;
    }
    public void addUser(String login_num, String password) {
        //获得一个可写的数据库的一个引用
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("login_num", login_num);
        values.put("password", password);
        db.insert(account_num, null, values);
        db.close();//关闭数据库、防止溢出
    }

    public void addGwc(int wp_img, String wp_number,String wp_pri) {
        //获得一个可写的数据库的一个引用
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("wp_img", wp_img);
        values.put("wp_number", wp_number);
        values.put("wp_pri",wp_pri);
        db.insert(account_gwc, null, values);
        db.close();//关闭数据库、防止溢出
    }


    public void deleteUser(String login_num) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();        //表名  删除的条件
        db.delete(account_num, "login_num = ?", new String[]{login_num});
        db.close();
    }
    public void updateUser(String login_num, String newpassword) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("password", newpassword);
        db.update(account_num, values, " login_num = ?", new String[]{login_num});
        db.close();
    }
//    public String query(String login_num,String password) {
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        String sql="select * from account_num where login_num=? and password=?";
//        Cursor cursor=db.rawQuery(sql, new String[]{login_num,password});
//        if(cursor.moveToFirst()==true){
//            String type=cursor.getString(cursor.getColumnIndex("user_type"));
//            cursor.close();
//            if(type.equals("1")) {
//                return "1";
//            }else{
//                return "0";
//            }
//        }
//        return "-1";
//    }
    public Boolean query_phone(String login_num){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql="select * from account_num where login_num=?";
        Cursor cursor=db.rawQuery(sql, new String[]{login_num});
        if(cursor.moveToFirst()==true){
            String phone=cursor.getString(cursor.getColumnIndex("login_num"));
            cursor.close();
            if(StringUtils.equals(phone,login_num)) {
                db.close();
                return true;
            }else{
                db.close();
                return false;
            }
        }
        db.close();
        return false;
    }


}

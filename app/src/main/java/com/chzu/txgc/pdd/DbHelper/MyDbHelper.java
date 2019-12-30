package com.chzu.txgc.pdd.DbHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/**
 * 数据库创建
 *
 * */

public class MyDbHelper extends SQLiteOpenHelper {
    public MyDbHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        System.out.println("onCreate 数据库被创建了");
        sqLiteDatabase.execSQL("create table account_num(login_num varchar(20) PRIMARY KEY, password varchar(20),nickname varchar(20))");//电话、密码、昵称
        sqLiteDatabase.execSQL("create table account_gwc(id INTEGER PRIMARY KEY AUTOINCREMENT,wp_img integer,wp_number varchar(20),wp_pri varchar(20))");//图片、价格、数量
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        System.out.println("onUpgrade 数据库被更新了");
    }
}

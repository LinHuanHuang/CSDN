package com.qianfeng.killer.day21baidumapdemo01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void fun1(View view){
        Intent intent = new Intent(this,BaseBaiDuMapActivity.class);
        startActivity(intent);
    }
    public void fun2(View view){
        Intent intent = new Intent(this,BaseBaiduMapDemo02.class);
        startActivity(intent);
    }
    public void fun3(View view){
        Intent intent = new Intent(this,BaseBaiduMapDemo03.class);
        startActivity(intent);
    }
    public void fun4(View view){
        Intent intent = new Intent(this,BaseBaiduMapDemo04.class);
        startActivity(intent);
    }
    public void fun5(View view){
        Intent intent = new Intent(this,BaseBaiduMapDemo05.class);
        startActivity(intent);
    }

}

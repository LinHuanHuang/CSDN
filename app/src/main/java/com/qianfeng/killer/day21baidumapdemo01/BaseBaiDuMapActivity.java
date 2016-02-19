package com.qianfeng.killer.day21baidumapdemo01;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;

public class BaseBaiDuMapActivity extends AppCompatActivity {
    private MapView mapView;
    private BaiduMap map;
    private boolean trrOn = false;
    private boolean heatOn = false;
    private boolean startOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_base_bai_du_map);

        mapView = (MapView) findViewById(R.id.bmapView);
        map = mapView.getMap();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }


    /**
     * 普通地图
     * @param view
     */
    public void fun1(View view){
        map.setMapType(BaiduMap.MAP_TYPE_NORMAL);

    }

    /**
     * 卫星地图
     * @param view
     */
    public void fun2(View view){
        if(startOn == false){
            map.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
            startOn = true;
        }else if(startOn == true){
            map.setMapType(BaiduMap.MAP_TYPE_NORMAL);
            startOn = false;
        }

    }

    /**
     * 打开交通图
     * @param view
     */
    public void fun3(View view){
        if(trrOn == false){
            map.setTrafficEnabled(true);
            trrOn = true;
        }else if(trrOn == true){
            map.setTrafficEnabled(false);
            trrOn = false;
        }
    }

    /**
     * 热力地图
     * @param view
     */
    public void fun4(View view){
        if(heatOn == false){
            map.setBaiduHeatMapEnabled(true);
            heatOn = true;
        }else if(heatOn == true){
            map.setBaiduHeatMapEnabled(false);
            heatOn = false;
        }
    }




}

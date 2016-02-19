package com.qianfeng.killer.day21baidumapdemo01;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;

public class BaseBaiduMapDemo05 extends AppCompatActivity {
    private MapView mapView;
    private BaiduMap baiduMap;
    private LocationClient client;
    private LocationClientOption option;

    private BDLocationListener listener = new BDLocationListener() {
        /**
         * 异步接收服务端返回的数据
         * @param bdLocation
         */
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            //获取定位数据
            //Log.i("-------","定位数据返回了");
            //获取定位返回的标志
            int type = bdLocation.getLocType();
            //判断类型
            switch (type){
                case BDLocation.TypeGpsLocation:
                case BDLocation.TypeNetWorkLocation:
                case BDLocation.TypeOffLineLocation:
                    double latitude = bdLocation.getLatitude();
                    double longitude = bdLocation.getLongitude();
                    LatLng latLng = new LatLng(latitude,longitude);
                    MapStatusUpdate state = MapStatusUpdateFactory.newLatLng(latLng);
                    baiduMap.setMapStatus(state);
                    //TODO 表示定位成功，我们需要在对应的位置添加覆盖物
                    OverlayOptions overlayOptions = new CircleOptions()
                                                        .center(latLng)
                                                        .radius(100)
                                                        .fillColor(0xAAFF0000)
                                                        .stroke(new Stroke(3,0xAA00FF00));

                    baiduMap.addOverlay(overlayOptions);
                    break;
                default:
                    Toast.makeText(BaseBaiduMapDemo05.this,"定位失败",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_base_baidu_map_demo05);

        mapView = (MapView) findViewById(R.id.id_baiduMapView5);
        baiduMap = mapView.getMap();

        //定位操作
        option = new LocationClientOption();
        initLocation();

        //实例化LocationClient需要传递一个全局的上下文，通过gerApplicationContext获取
        client = new LocationClient(getApplicationContext(),option);
        //注册监听器
        client.registerLocationListener(listener);


    }


    /**
     * 配置SDK相关参数
     */
    private void initLocation() {
        //配置定位模式
        /**
         * 高精度：Hight_Accuracy
         */
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //设置结果返回的坐标系
        option.setCoorType("bd09ll");
        //设置发送定位的时间间隔，默认为0，仅定位一次，需要大于等于1000ms才有效
        option.setScanSpan(1000);
        //设置是否返回信息包含的地址信息
        option.setIsNeedAddress(true);
        //设置是否开启GPS
        option.setOpenGps(true);
        //默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setLocationNotify(true);
        //



    }

    /**
     * 用户定位到当前位置
     * @param view
     */
    public void location(View view){
        client.start();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        client.unRegisterLocationListener(listener);
        super.onDestroy();
    }
}

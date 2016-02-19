package com.qianfeng.killer.day21baidumapdemo01;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.GroundOverlayOptions;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;

import java.util.ArrayList;

public class BaseBaiduMapDemo02 extends AppCompatActivity {

    private MapView mapView;
    private BaiduMap map;
    private  Marker marker;
    private Overlay textOptions;

    private BitmapDescriptor bdA;
    private BitmapDescriptor bdB;
    private BitmapDescriptor bdC;
    private BitmapDescriptor bdD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_base_baidu_map_demo02);

        mapView = (MapView) findViewById(R.id.bmapView);
        map = mapView.getMap();

        initIcon();
    }

    /**
     * 初始化
     */
    private void initIcon() {
        bdA = BitmapDescriptorFactory.fromResource(R.drawable.icon_marka);
        bdB = BitmapDescriptorFactory.fromResource(R.drawable.icon_markb);
        bdC = BitmapDescriptorFactory.fromResource(R.drawable.icon_markc);
        bdD = BitmapDescriptorFactory.fromResource(R.drawable.icon_markd);
    }

    /**
     * 添加一个标注
     * @param view
     */
    public void fun1(View view){
        //指定经纬度  113.950267,22.536098
        LatLng point = new LatLng(39.963175, 116.400244);
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.icon_marka);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions options = new MarkerOptions()
                                        .position(point)
                                        .icon(bitmap)
                                        .zIndex(9)
                                        .draggable(true);
        map.addOverlay(options);

        map.setOnMarkerDragListener(new BaiduMap.OnMarkerDragListener() {
            /**
             * 拖拽中
             * @param marker
             */
            @Override
            public void onMarkerDrag(Marker marker) {
                Log.i("------","onMarkerDrag"+marker.getPosition().longitude+","+marker.getPosition().latitude);

            }

            /**
             * 拖拽结束
             * @param marker
             */
            @Override
            public void onMarkerDragEnd(Marker marker) {
                Log.i("------","onMarkerDragEnd"+marker.getPosition().longitude+","+marker.getPosition().latitude);
            }

            /**
             * 开始拖拽
             * @param marker
             */
            @Override
            public void onMarkerDragStart(Marker marker) {
                Log.i("------","onMarkerDragStart"+marker.getPosition().longitude+","+marker.getPosition().latitude);
            }
        });
    }


    /**
     * 动画标注
     * @param view
     */
    public void fun2(View view){
        ArrayList<BitmapDescriptor> list = new ArrayList<>();
        list.add(bdA);
        list.add(bdB);
        list.add(bdC);
        list.add(bdD);

        //获取坐标点
        LatLng point = new LatLng(39.973190, 116.400244);
        OverlayOptions options = new MarkerOptions()
                                        .position(point)
                                        .icons(list)
                                        .zIndex(9)
                                        .draggable(true)
                                        .period(10);//这是隔多久刷新一次
        marker = (Marker) map.addOverlay(options);
    }

    /**
     * 隐藏标记
     * @param view
     */
    public void fun3(View view){
        marker.remove();
        fun2(null);
    }

    /**
     * 多边形覆盖物
     * @param view
     */
    public void fun4(View view){
      /*  //获取一组数据
        LatLng pt1 = new LatLng(39.93923, 116.357428);
        LatLng pt2 = new LatLng(39.91923, 116.327428);
        LatLng pt3 = new LatLng(39.89923, 116.347428);
        LatLng pt4 = new LatLng(39.89923, 116.367428);
        LatLng pt5 = new LatLng(39.91923, 116.387428);

        ArrayList<LatLng> list = new ArrayList<>();
        list.add(pt1);
        list.add(pt2);
        list.add(pt3);
        list.add(pt4);
        list.add(pt5);

        //获取OverLayOption
        OverlayOptions options = new PolygonOptions()
                                        .points(list)
                                        .stroke(new Stroke(5,0xAA00FF00)) //设置多边形，5宽度（像素），边框颜色
                                        .fillColor(0xAAFFFF00);//多边形填充颜色
        map.addOverlay(options);*/

        LatLng point = new LatLng(39.973190, 116.400244);
        OverlayOptions options = new CircleOptions().center(point).radius(1000).stroke(new Stroke(5,0xBB00FF00)).visible(true).fillColor(0xBBFFFF00);
        map.addOverlay(options);



    }

    /**
     * 文字覆盖物
     * @param view
     */
    public void fun5(View view){
        if(textOptions == null){
            //定义文字所显示的坐标点
            LatLng llText = new LatLng(39.86923, 116.397428);
            OverlayOptions textoptions = new TextOptions().bgColor(0xAAFFFF00)
                    .fontSize(24)
                    .fontColor(0xFFFF00FF)
                    .text("百度地图")
                    .rotate(-30)
                    .position(llText);
            textOptions = map.addOverlay(textoptions);
        }else {
            textOptions.remove();
            textOptions = null;
        }

    }

    /**
     * 弹出覆盖物
     * @param view
     */
    public void fun6(View view){
        //坐标
        LatLng latLng = new LatLng(39.86923, 116.397428);
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.icon_marka);
        OverlayOptions options = new MarkerOptions()
                                        .position(latLng)
                                        .icon(bitmapDescriptor)
                                        .zIndex(9)
                                        .draggable(true);
        map.addOverlay(options);

        //设置单击事件监听器
        map.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            //标注物被点击回调的方法
            @Override
            public boolean onMarkerClick(Marker marker) {
                //弹出窗口
                //获取需要弹出的View对象
                Button button = new Button(getApplicationContext());
                button.setBackgroundResource(R.drawable.popup);
                //指定窗口弹出的位置
                LatLng latLng1 = new LatLng(39.86923, 116.397428);
                //InfoWindow 第三个参数： 偏移量
                //InfoWindow infoWindow = new InfoWindow(button,latLng1,-150);
                InfoWindow infoWindow = new InfoWindow(BitmapDescriptorFactory.fromView(button), latLng1, -150, new InfoWindow.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick() {
                        Log.i("---------------","onInfoWindowClick");
                        map.hideInfoWindow();
                    }
                });
                map.showInfoWindow(infoWindow);
                return false;
            }
        });
    }

    /**
     * 地形图层
     * @param view
     */
    public void fun7(View view){
        LatLng southwest = new LatLng(39.92235, 116.380338);
        LatLng northeast = new LatLng(39.947246, 116.414977);
        LatLngBounds bounds = new LatLngBounds.Builder()
                .include(northeast)
                .include(southwest)
                .build();
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher);

        OverlayOptions options = new GroundOverlayOptions()
                                        .positionFromBounds(bounds)
                                        .image(bitmapDescriptor)
                                        .transparency(0.6f);
        map.addOverlay(options);
    }
}

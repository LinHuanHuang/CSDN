package com.qianfeng.killer.day21baidumapdemo01;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.overlayutil.TransitRouteOverlay;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;

public class BaseBaiduMapDemo04 extends AppCompatActivity {

    private EditText et1,et2;
    private MapView mapView;
    private BaiduMap baiduMap;
    private RoutePlanSearch planSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_base_baidu_map_demo04);

        mapView  = (MapView) findViewById(R.id.id_baiduMapView4);
        et1 = (EditText) findViewById(R.id.id_start);
        et2 = (EditText) findViewById(R.id.id_end);

        planSearch = RoutePlanSearch.newInstance();
        planSearch.setOnGetRoutePlanResultListener(listener);

    }

    private OnGetRoutePlanResultListener listener = new OnGetRoutePlanResultListener() {
        @Override
        public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {

        }

        @Override
        public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {
            if(transitRouteResult == null || transitRouteResult.error != SearchResult.ERRORNO.NO_ERROR){
                Toast.makeText(BaseBaiduMapDemo04.this,"抱歉，没有检索到相关信息",Toast.LENGTH_SHORT).show();
            }
            if(transitRouteResult.error == SearchResult.ERRORNO.NO_ERROR){
                TransitRouteOverlay overlay = new TransitRouteOverlay(baiduMap){
                    @Override
                    public boolean onRouteNodeClick(int i) {
                        return super.onRouteNodeClick(i);
                    }
                };

                overlay.setData(transitRouteResult.getRouteLines().get(0));
                overlay.addToMap();
                overlay.zoomToSpan();

            }
        }

        @Override
        public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {

        }
    };


    /**
     * 公交线路
     * @param view
     */
    public void bus (View view){
        PlanNode stNode = PlanNode.withCityNameAndPlaceName("北京",et1.getText().toString());
        PlanNode edNode = PlanNode.withCityNameAndPlaceName("北京",et2.getText().toString());

        planSearch .transitSearch(new TransitRoutePlanOption()
                                    .from(stNode)
                                    .to(edNode)
                                    .city("北京"));
    }


    /**
     * 步行线路
     * @param view
     */
    public void walk (View view){

    }


    /**
     * 驾车线路
     * @param view
     */
    public void car (View view){

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
        super.onDestroy();
        planSearch.destroy();
        mapView.onDestroy();
    }
}

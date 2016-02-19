package com.qianfeng.killer.day21baidumapdemo01;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.overlayutil.PoiOverlay;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;

/**
 * POI检索，城市健硕
 */
public class BaseBaiduMapDemo03 extends AppCompatActivity {
    private MapView mapView;
    private BaiduMap baiduMap;
    private EditText et1,et2;

    private int index; //检索显示的页数

    //声明检索实例
    private PoiSearch poiSearch;
    //声明回调方法
    private OnGetPoiSearchResultListener listener = new OnGetPoiSearchResultListener() {
        /**
         * 服务器返回检索信息的回调方法
         * @param poiResult
         */
        @Override
        public void onGetPoiResult(final PoiResult poiResult) {
            Log.i("---------","onGetPoiResult");
            //打印相关的信息
           /* if(poiResult != null){
                for(PoiInfo info: poiResult.getAllPoi()){
                    Log.i("-------","信息"+info.name+" "+ info.address+"  "+ info.phoneNum);
                }
            }*/

            if(poiResult == null || poiResult.error == SearchResult.ERRORNO.RESULT_NOT_FOUND){
                //TODO 表示没有检索到相关的信息，提示用户
                Toast.makeText(BaseBaiduMapDemo03.this,"未检索到相关信息",Toast.LENGTH_SHORT).show();
            }
            if(poiResult != null && poiResult.error == SearchResult.ERRORNO.NO_ERROR){

                //检索的时候将上一次检索的信息清空
                baiduMap.clear();
                //将结果显示在地图上
                PoiOverlay poiOverlay = new PoiOverlay(baiduMap){
                    //点击后的回调方法
                    @Override
                    public boolean onPoiClick(int i) {
                        //Log.i("---------","onPoiClick");
                        PoiInfo info = poiResult.getAllPoi().get(i);
                        poiSearch.searchPoiDetail(new PoiDetailSearchOption().poiUid(info.uid));
                        return super.onPoiClick(i);
                    }
                };

                //添加一个标注物的点击事件的监听器 poiOverlay已经实现了BaiduMap.OnMarkerClickListener
                //点击检索标注物之后会回调onMarkerClick该方法
                baiduMap.setOnMarkerClickListener(poiOverlay);

                //将获取的数据添加到PoiOverlay中
                poiOverlay.setData(poiResult);

                poiOverlay.addToMap();
                poiOverlay.zoomToSpan();
            }


        }

        /**
         * 在进行详细信息检索后服务器返回数据的回调方法
         * 服务器返回检索详细信息的回调方法
         * @param poiDetailResult
         */
        @Override
        public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
            //Log.i("---------","onGetPoiDetailResult");
            Toast.makeText(BaseBaiduMapDemo03.this,
                    poiDetailResult.getName()+" "+poiDetailResult.getAddress()+" "+poiDetailResult.getTelephone(),
                    Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_base_baidu_map_demo03);

        //初始化
        mapView = (MapView) findViewById(R.id.id_mapView);
        baiduMap = mapView.getMap();

        et1 = (EditText) findViewById(R.id.id_city);
        et2 = (EditText) findViewById(R.id.id_content);

        //实例化PoiSearch
        poiSearch = PoiSearch.newInstance();

        //设置POI监听器
        poiSearch.setOnGetPoiSearchResultListener(listener);

        index = 1;

    }

    /**
     * 开始
     * @param view
     */
    public void begin(View view){
        //发起检索
        poiSearch.searchInCity(new PoiCitySearchOption().
                //city(et1.getText().toString())
                city("深圳").
                keyword(et2.getText().toString()).
                pageNum(index).
                pageCapacity(10));

    }

    /**
     * 下一组
     * @param view
     */
    public void next(View view){
        index ++;
        begin(null);
    }


    //生命周期--------------------------------------------------------------------------------------
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
        poiSearch.destroy();
    }

}

package com.softeem.orderapp.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softeem.orderapp.adapter.MenuGridViewAdapter;
import com.softeem.orderapp.bean.MenuBean;
import com.softeem.orderapp.bean.TypeBean;
import com.softeem.orderapp.http.HttpCallback;
import com.softeem.orderapp.http.MenuHttpUtils;
import com.softeem.orderapp.R;

import java.util.ArrayList;
import java.util.List;

// 点菜页面
public class MenuActivity extends AppCompatActivity {
    private LinearLayout topTypeLinearLayout;
    private GridView menuGridView;
    private MenuGridViewAdapter menuGridViewAdapter;
    private List<TypeBean> typeList;
    private List<MenuBean> menuList = new ArrayList<MenuBean>();

    private HttpCallback typeCallBack;
    private MenuCallBack menuCallBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // 先联网获取菜谱类型
        typeCallBack = new TypeCallBack();
        new MenuHttpUtils().getAllTypes(typeCallBack);

        // 设置适配器
        menuGridView = (GridView) findViewById(R.id.menu_GridView);
        menuGridViewAdapter = new MenuGridViewAdapter(menuList, LayoutInflater.from(this), this);
        menuGridView.setAdapter(menuGridViewAdapter);

        // TODO 给 gridView 添加条目点击事件
        menuGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //创建 Intent
                Intent i = new Intent(MenuActivity.this, MenuDetailActivity.class);

                //当前条目对应的数据
                MenuBean m = menuList.get(position);
                //携带数据到 MenuDetailActivity
                i.putExtra("menuBean", m);

                startActivity(i);
            }
        });


        //加载菜谱
        menuCallBack = new MenuCallBack();
        new MenuHttpUtils().getAllMenu(menuCallBack);
    }


    // 初始化类型
    public void initTopTypes() {
        topTypeLinearLayout = (LinearLayout
                ) findViewById(R.id.top_type_LinearLayout);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL;


        final List<TextView> tvList = new ArrayList<TextView>();
        for (int i = 0; i <= typeList.size(); i++) {
            TypeBean typeItem = new TypeBean(-1,"主页",-1);
            if (i > 0) {
                typeItem = typeList.get(i - 1);
            }

            TextView text = new TextView(this);
            text.setTextSize(30);
            text.setPadding(40, 10, 40, 10);
            text.setId(typeItem.getTypeId());
            text.setTextColor(Color.WHITE);
            text.setText(typeItem.getTypeName());
            text.setGravity(Gravity.CENTER);

            updateBg(-1,tvList);

            // 给每个 TextView 设置了点击事件
            text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //加载网络
                    new MenuHttpUtils().getMenuByType(v.getId(), menuCallBack);

                    updateBg(v.getId(),tvList);

                }
            });

            tvList.add(text);

            // 将 TextView 加入到 LinearLayout
            topTypeLinearLayout.addView(text, params);
        }
    }

    // 修改 TextView 的样式
    public void updateBg(int selectedId,List<TextView> tvList){
        for (TextView tv : tvList) {
            if (tv.getId() == selectedId) {
                tv.setBackgroundColor(getResources().getColor(R.color.white));
                tv.setTextColor(getResources().getColor(R.color.colorPrimary));
            } else {
                tv.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                tv.setTextColor(getResources().getColor(R.color.white));
            }
        }
    }


    class MenuCallBack implements HttpCallback {
        @Override
        public void onSuccess(Object data) {
            // 修改集合的数据
            menuList = (List<MenuBean>) data;

            Log.i("menuList", menuList.toString());
            // menuGridViewAdapter 是适配器,动态显示所有的菜单
            menuGridViewAdapter.setData(menuList);
            //刷新适配器
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // 使用适配器刷新 GridView
                    menuGridViewAdapter.notifyDataSetChanged();
                }
            });
        }

        @Override
        public void onFailure(String message) {

        }
    }


    class TypeCallBack implements HttpCallback {

        @Override
        public void onSuccess(final Object data) {
            // 当前的 Runnable 在 UI 线程执行
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    typeList = (List<TypeBean>) data;
                    initTopTypes();
                }
            });
        }

        @Override
        public void onFailure(String message) {

        }
    }
}

package com.softeem.orderapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.softeem.orderapp.R;
import com.softeem.orderapp.utils.Config;
import com.softeem.orderapp.utils.OrderService;
import com.softeem.orderapp.utils.OrderServiceImpl;

import java.util.ArrayList;

public class DetailActivity extends Activity {

    OrderService order;
    ArrayList<View> mListData;
    ListView listView;
    SwipeRefreshLayout swipeRefreshLayout;
    Handler mHandler;
    BaseAdapter adapter;
    View list_foot;
    View list_foot_child;
    boolean isLoadMore=false;
    boolean isNotMore=false;
    int mStart=10;
    int mRow=10;
    private final int NO_MORE_DATA=100;
    private final int SUCCESS_GET_DATA=101;
    private final int SWIPE_DOWN=102;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginJudge();
        setContentView(R.layout.activity_detail);
        initData();
        initView();
    }
    public void loginJudge(){
        if(Config.getUsername()==null){
            if(!LoginActivity.autoLogin(this)){
                Intent t =new Intent(this,LoginActivity.class);
                startActivity(t);
            }
        }
    }

    public  void initView(){
        listView= (ListView) findViewById(R.id.main_list);
        swipeRefreshLayout= (SwipeRefreshLayout) findViewById(R.id.main_swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorForRefresh);
        listView.setAdapter(adapter);
        list_foot= View.inflate(this,R.layout.foot_main_list,null);
        list_foot_child = list_foot.findViewById(R.id.list_foot_child);
        listView.addFooterView(list_foot);
        setSwipeListener();
        setListScrollListener();

    }
    public void setListScrollListener(){
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                 int lastVisiblePosition=listView.getLastVisiblePosition();
                 if(lastVisiblePosition==listView.getCount()-1){
                     if(!isNotMore) {
                         list_foot_child.setVisibility(View.VISIBLE);
                         getMoreListData();
                     }
                }
            }
        });
    }

    private void getMoreListData() {
        new Thread(){
            @Override
            public void run() {
                if(!isLoadMore){
                    isLoadMore=true;
                    ArrayList<View> tempList=getRemoteData();
                    if(tempList==null||tempList.isEmpty()){
                        isNotMore=true;
                        mHandler.sendEmptyMessage(NO_MORE_DATA);
                        return;
                    }
                    mListData.addAll(tempList);
                    mHandler.sendEmptyMessage(SUCCESS_GET_DATA);
                }
            }
        }.start();

    }
    ArrayList<View> getRemoteData(){
        ArrayList<View> tempList=order.getMoreView(mStart,mRow);
        mStart+=mRow;
        return tempList;
    }

    public void initData(){
        order=new OrderServiceImpl();
        mListData=new ArrayList<View>();
        ArrayList<View> temp =order.getStartView(mStart);
        if(temp!=null){
            mListData.addAll(temp);
        }

        mHandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {

                switch (msg.what){
                    case NO_MORE_DATA:
                        list_foot_child.setVisibility(View.GONE);
                        Toast.makeText(DetailActivity.this,"没有更多的数据了╮(╯▽╰)╭",Toast.LENGTH_SHORT).show();
                        break;
                    case SUCCESS_GET_DATA:
                        adapter.notifyDataSetChanged();
                        list_foot_child.setVisibility(View.GONE);
                        break;
                    case SWIPE_DOWN:
                        adapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                        break;
                }
                isLoadMore=false;

            }
        };
        initListAdapter();
    }
    void initListAdapter(){
        adapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return  mListData.size();
            }

            @Override
            public Object getItem(int position) {
                return position;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                return mListData.get(position);
            }

        };
    }
    void setSwipeListener(){
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(){
                    @Override
                    public void run() {

                        System.out.println("now ,get message");
                        mHandler.sendEmptyMessage(SWIPE_DOWN);

                    }
                }.start();
            }
        });
    }



}

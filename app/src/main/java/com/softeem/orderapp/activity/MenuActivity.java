package com.softeem.orderapp.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flipboard.bottomsheet.BottomSheetLayout;
import com.softeem.orderapp.MyApplication;
import com.softeem.orderapp.R;
import com.softeem.orderapp.adapter.MenuGridViewAdapter;
import com.softeem.orderapp.adapter.SelectAdapter;
import com.softeem.orderapp.bean.MenuBean;
import com.softeem.orderapp.bean.OrderItemBean;
import com.softeem.orderapp.bean.TypeBean;
import com.softeem.orderapp.http.HttpCallback;
import com.softeem.orderapp.http.MenuHttpUtils;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;


// 点菜页面
public class MenuActivity extends AppCompatActivity implements View.OnClickListener{
    private LinearLayout topTypeLinearLayout;
    private GridView menuGridView;
    private ViewGroup anim_mask_layout;

    private List<TypeBean> typeList;
    private List<MenuBean> menuList = new ArrayList<MenuBean>();
    private SparseArray<OrderItemBean> selectedList;
    private ImageView imgCart;
    private Handler mHanlder;
    private TextView tvCount, tvCost, tvSubmit;
    private RecyclerView rvSelected;
    private BottomSheetLayout bottomSheetLayout;
    private View bottomSheet;

    private NumberFormat nf;
    private MenuGridViewAdapter menuGridViewAdapter;
    private SelectAdapter selectAdapter;
    private HttpCallback typeCallBack;
    private MenuCallBack menuCallBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        nf = NumberFormat.getCurrencyInstance();
        nf.setMaximumFractionDigits(1);
        selectedList = new SparseArray<>();
        // 先联网获取菜谱类型
        typeCallBack = new TypeCallBack();
        new MenuHttpUtils().getAllTypes(typeCallBack);
        anim_mask_layout = (LinearLayout) findViewById(R.id.containerLayout);
        bottomSheetLayout = (BottomSheetLayout) findViewById(R.id.bottomSheetLayout);
        // 设置适配器
        menuGridView = (GridView) findViewById(R.id.menu_GridView);
        menuGridViewAdapter = new MenuGridViewAdapter(menuList, LayoutInflater.from(this), this);
        menuGridView.setAdapter(menuGridViewAdapter);
        imgCart = (ImageView)findViewById(R.id.imgCart);
        tvCount = (TextView) findViewById(R.id.tvCount);
        tvCost = (TextView) findViewById(R.id.tvCost);
        tvSubmit  = (TextView) findViewById(R.id.tvSubmit);
        mHanlder = new Handler(getMainLooper());

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
                //Intent it = new Intent(MenuActivity.this, MenuDetailActivity.class);
                //startActivityForResult(i, 1);
            }
        });

        //加载菜谱
        menuCallBack = new MenuCallBack();
        new MenuHttpUtils().getAllMenu(menuCallBack);
    }

    //添加商品
    public void add(OrderItemBean item){
        OrderItemBean temp = selectedList.get(item.menuBean.getId());
        if(temp == null){
            item.count = 1;
            selectedList.append(item.menuBean.getId(), item);
        }else{
            temp.count++;
            temp.itemTotalPrice += temp.menuBean.getPrice();
            temp.itemCutPrice += temp.menuBean.getDiscount();
        }
        update();
    }
    //移除商品
    public void remove(OrderItemBean item){
        OrderItemBean temp = selectedList.get(item.menuBean.getId());
        if(temp != null){
            if(temp.count < 2){
                selectedList.remove(item.menuBean.getId());
            }else{
                item.count--;
                item.itemTotalPrice -= item.menuBean.getPrice();
                item.itemCutPrice -= item.menuBean.getDiscount();
            }
        }
        update();
    }

    //刷新布局 总价、购买数量等
    private void update(){
        int size = selectedList.size();
        int count = 0;
        double cost = 0;
        for(int i = 0; i < size; i++){
            OrderItemBean item = selectedList.valueAt(i);
            count += item.count;
            cost += item.getItemTotalPrice()-item.getItemCutPrice();
        }
        if(count < 1){
            tvCount.setVisibility(View.GONE);
        }else{
            tvCount.setVisibility(View.VISIBLE);
        }

        tvCount.setText(String.valueOf(count));

        if(cost > 0){
            tvSubmit.setVisibility(View.VISIBLE);
        }else{
            tvSubmit.setVisibility(View.GONE);
        }
        tvCost.setText(nf.format(cost));
        if(selectAdapter != null){
            selectAdapter.notifyDataSetChanged();
        }
        if(bottomSheetLayout.isSheetShowing() && selectedList.size() < 1){
            bottomSheetLayout.dismissSheet();
        }
    }
    //清空购物车
    public void clearCart(){
        selectedList.clear();
        update();
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.bottom:
                showBottomSheet();
                break;
            case R.id.clear:
                clearCart();
                break;
            case R.id.tvSubmit:
                submitOrder(selectedList);
                break;
            default:
                break;
        }
    }

    private void submitOrder(SparseArray<OrderItemBean> list) {//结算跳转到提交订单页面
        int size = list.size();
        MyApplication application = (MyApplication)getApplication();
        for(int i = 0; i < size; i++) {
            OrderItemBean item = selectedList.valueAt(i);
            application.orderBean.orderItemBeanList.add(item);
        }
        startActivity(new Intent(MenuActivity.this, OrderActivity.class));
        MenuActivity.this.finish();
    }

    private View createBottomSheetView(){
        View view = LayoutInflater.from(this).inflate(R.layout.layout_bottom_sheet,(ViewGroup) getWindow().getDecorView(),false);
        rvSelected = (RecyclerView) view.findViewById(R.id.selectRecyclerView);
        rvSelected.setLayoutManager(new LinearLayoutManager(this));
        TextView clear = (TextView) view.findViewById(R.id.clear);
        clear.setOnClickListener(this);
        selectAdapter = new SelectAdapter(this, selectedList);
        rvSelected.setAdapter(selectAdapter);
        return view;
    }

    private void showBottomSheet(){
        if(bottomSheet == null){
            bottomSheet = createBottomSheetView();
        }
        if(bottomSheetLayout.isSheetShowing()){
            bottomSheetLayout.dismissSheet();
        }else {
            if(selectedList.size() != 0){
                bottomSheetLayout.showWithSheetView(bottomSheet);
            }
        }
    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    OrderItemBean oib = (OrderItemBean) data.getSerializableExtra("data_return");
                    Log.d("-----------", oib.toString());
                    add(oib);
                }
                break;
            default:
                break;
        }
    }*/

    public void playAnimation(int[] start_location){
        ImageView img = new ImageView(this);
        img.setImageResource(R.drawable.ball);
        setAnim(img,start_location);
    }

    private View addViewToAnimLayout(final ViewGroup parent, final View view,
                                     int[] location) {
        int x = location[0];
        int y = location[1];
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = x;
        lp.topMargin = y;
        view.setLayoutParams(lp);
        return view;
    }

    private ViewGroup createAnimLayout() {
        ViewGroup rootView = (ViewGroup) this.getWindow().getDecorView();
        LinearLayout animLayout = new LinearLayout(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        animLayout.setLayoutParams(lp);
        animLayout.setId(Integer.MAX_VALUE-1);
        animLayout.setBackgroundResource(android.R.color.transparent);
        rootView.addView(animLayout);
        return animLayout;
    }

    public void setAnim(final View v, int[] startLocation) {
        anim_mask_layout = null;
        anim_mask_layout = createAnimLayout();
        anim_mask_layout.addView(v);//把动画小球添加到动画层
        final View view = addViewToAnimLayout(anim_mask_layout, v,
                startLocation);
        int[] endLocation = new int[2];// 存储动画结束位置的X、Y坐标
        imgCart.getLocationInWindow(endLocation);

        // 计算位移
        int endX = 0 - startLocation[0] + 40;// 动画位移的X坐标
        int endY = endLocation[1] - startLocation[1];// 动画位移的y坐标
        TranslateAnimation translateAnimationX = new TranslateAnimation(0,
                endX, 0, 0);
        translateAnimationX.setInterpolator(new LinearInterpolator());
        translateAnimationX.setRepeatCount(0);// 动画重复执行的次数
        translateAnimationX.setFillAfter(true);

        TranslateAnimation translateAnimationY = new TranslateAnimation(0, 0,
                0, endY);
        translateAnimationY.setInterpolator(new AccelerateInterpolator());
        translateAnimationY.setRepeatCount(0);// 动画重复执行的次数
        translateAnimationX.setFillAfter(true);

        AnimationSet set = new AnimationSet(false);
        set.setFillAfter(false);
        set.addAnimation(translateAnimationY);
        set.addAnimation(translateAnimationX);
        set.setDuration(400);// 动画的执行时间
        view.startAnimation(set);
        // 动画监听事件
        set.setAnimationListener(new Animation.AnimationListener() {
            // 动画的开始
            @Override
            public void onAnimationStart(Animation animation) {
                v.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            // 动画的结束
            @Override
            public void onAnimationEnd(Animation animation) {
                v.setVisibility(View.GONE);
            }
        });
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
            text.setTextSize(25);
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
                tv.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                tv.setTextColor(getResources().getColor(R.color.white));
            } else {
                tv.setBackgroundColor(getResources().getColor(R.color.white));
                tv.setTextColor(getResources().getColor(R.color.colorPrimary));
            }
        }
    }


    class MenuCallBack implements HttpCallback {
        @Override
        public void onSuccess(Object data) {
            // 修改集合的数据
            menuList = (List<MenuBean>) data;
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

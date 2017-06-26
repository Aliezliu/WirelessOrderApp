package com.softeem.orderapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.softeem.orderapp.MyApplication;
import com.softeem.orderapp.R;
import com.softeem.orderapp.bean.MenuBean;
import com.softeem.orderapp.bean.OrderItemBean;
import com.softeem.orderapp.bean.TypeBean;
import com.softeem.orderapp.http.HttpCallback;
import com.softeem.orderapp.http.MenuHttpUtils;

public class MenuDetailActivity extends AppCompatActivity {
    private TextView menuNameTextView;
    private ImageView detailPicImageView;
    private Button backButton;
    //加入订单
    private Button addOrderButton;
    private TextView menuTypeTextView;
    private TextView menuPriceTextView;
    private TextView menuCutPriceTextView;
    private TextView menuIntroduceTextView;
    private TextView minusTextView;
    private TextView plusTextView;
    private TextView countTextView;

    private MenuBean menuBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_detail);

        initView();
        initData();
    }

    private void initView() {
        menuNameTextView = (TextView) findViewById(R.id.menu_name_TextView);
        detailPicImageView = (ImageView) findViewById(R.id.detail_pic_ImageView);
        backButton = (Button) findViewById(R.id.back_Button);
        addOrderButton = (Button) findViewById(R.id.add_order_Button);
        menuTypeTextView = (TextView) findViewById(R.id.menu_type_TextView);
        menuPriceTextView = (TextView) findViewById(R.id.menu_price_TextView);
        menuCutPriceTextView = (TextView) findViewById(R.id.menu_cutprice_TextView);
        menuIntroduceTextView = (TextView) findViewById(R.id.menu_introduce_TextView);
        minusTextView = (TextView) findViewById(R.id.minus_TextView);
        plusTextView = (TextView) findViewById(R.id.plus_TextView);
        countTextView = (TextView) findViewById(R.id.count_TextView);

        //减少数量
        minusTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.parseInt(countTextView.getText().toString());
                if (count >= 2) {
                    count--;
                    countTextView.setText(count + "");
                }
            }
        });


        plusTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.parseInt(countTextView.getText().toString());
                count++;
                countTextView.setText(count + "");
            }
        });

        // 返回按钮:
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuDetailActivity.this.finish();
            }
        });

        //添加订单(加入到集合中,临时)
        addOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 添加订单:
                //(1)封装 OrderItem
                OrderItemBean i = new OrderItemBean();
                i.menuBean = menuBean;

                int count = Integer.parseInt(countTextView.getText().toString());
                i.count = count;

                i.itemTotalPrice = count * menuBean.getPrice();
                i.itemCutPrice = count * menuBean.getDiscount();

                // 添加到临时订单中
                MyApplication application = (MyApplication) getApplication();
                application.orderBean.orderItemBeanList.add(i);

                Toast.makeText(MenuDetailActivity.this, "加入成功", Toast.LENGTH_SHORT).show();

                //关闭当前 Activity
                MenuDetailActivity.this.finish();
            }
        });
    }

    private void initData() {
        //获取 MenuBean
        menuBean = (MenuBean) getIntent().getSerializableExtra("menuBean");
        if (menuBean == null)
            return;

        menuNameTextView.setText("菜谱名称:    " + menuBean.getName());

        menuPriceTextView.setText("价格:   " + menuBean.getPrice() + "");
        menuCutPriceTextView.setText("折后价格:    " + (menuBean.getPrice()-menuBean.getDiscount()) + "");
        menuIntroduceTextView.setText(menuBean.getIntroduce());

        //使用框架加载图片
        Glide
                .with(this)
                .load(menuBean.getUrl())
                .centerCrop()
                .into(detailPicImageView);

        // 加载菜谱类型名称
        new MenuHttpUtils().getTypeName(menuBean.getId(), new HttpCallback() {
            @Override
            public void onSuccess(Object data) {
                final TypeBean t = (TypeBean) data;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        menuTypeTextView.setText("菜谱类型:    " + t.getTypeName());
                    }
                });
            }

            @Override
            public void onFailure(String message) {

            }
        });
    }
}

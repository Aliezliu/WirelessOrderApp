package com.softeem.orderapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.softeem.orderapp.R;
import com.softeem.orderapp.activity.MenuActivity;
import com.softeem.orderapp.bean.MenuBean;
import com.softeem.orderapp.bean.OrderItemBean;

import java.util.List;

/**
 * Created by Edward on 2017/6/21.
 * 适配器: 动态将 List<MenuBean>  加入到 GridView
 */
public class MenuGridViewAdapter extends BaseAdapter {
    // 上下文: 网络请求框架
    private Context context;
    // 数据来源
    private List<MenuBean> data;
    // inflater:创建View
    private LayoutInflater inflater;

    /*private MenuBean menuBean;*/
    public MenuGridViewAdapter(List<MenuBean> data,LayoutInflater inflater,Context context){
        this.data = data;
        this.inflater = inflater;
        this.context = context;
    }

    public void setData(List<MenuBean> data) {
        this.data = data;
    }

    // 获取 item 的数量
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // getView 提供 View 对象,每一个 View 对应一个 MenuBean
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 缓存: 减少 View 的创建和 findViewById 的操作
        ViewHolder holder = null;
        if(convertView == null){
            //创建 Item 对应的 View 对象
            convertView = inflater.inflate(R.layout.menu_item, null);
            holder = new ViewHolder(convertView);
            //将 holder 存入 View 中
            convertView.setTag(holder);
        }else{
            //直接 从 View 中获取 holder
            holder = (ViewHolder) convertView.getTag();
        }
        // 获取当前 Item 对应的数据
        MenuBean menuBean = data.get(position);
        holder.bindData(menuBean);
        return convertView;
    }

    class ViewHolder implements View.OnClickListener{
        MenuBean menuBean;
        ImageView picIV;
        TextView priceTV;
        TextView nameTV;
        TextView addTV;
        public ViewHolder(View itemView) {
            picIV = (ImageView) itemView.findViewById(R.id.menu_item_ImageView);
            priceTV=(TextView) itemView.findViewById(R.id.menu_item_price_TextView);
            nameTV = (TextView) itemView.findViewById(R.id.menu_item_name_TextView);
            addTV = (TextView) itemView.findViewById(R.id.menu_item_add_TextView);
            addTV.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            MenuActivity activity = (MenuActivity)context;
            switch (v.getId()) {
                case R.id.menu_item_add_TextView:
                    OrderItemBean i = new OrderItemBean();
                    i.menuBean = menuBean;
                    i.count = 1;
                    i.itemTotalPrice = menuBean.getPrice();
                    i.itemCutPrice = menuBean.getDiscount();
                    // 添加到临时订单中

                    activity.add(i);
                    int[] loc = new int[2];
                    v.getLocationInWindow(loc);

                    activity.playAnimation(loc);
                    break;
            }
        }

        public void bindData(MenuBean menuBean) {
            this.menuBean = menuBean;
            nameTV.setText(menuBean.getName());
            priceTV.setText("价格:" + menuBean.getPrice() + "    折扣价:" + (menuBean.getPrice()-menuBean.getDiscount()));
            // ImageView加载网络图片
            Glide
                    .with(context)
                    .load(menuBean.getUrl())
                    .centerCrop()
                    .into(picIV);

        }
    }
}

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
import com.softeem.orderapp.bean.MenuBean;

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
        // 获取当前 Item 对应的数据
        MenuBean menuBean = data.get(position);

        // 缓存: 减少 View 的创建和 findViewById 的操作
        ViewHolder holder;

        if(convertView==null){
            //创建 Item 对应的 View 对象
            convertView= inflater.inflate(R.layout.menu_item,null);

            holder=new ViewHolder();
            // 从 convertView 中获取每一个控件,将控件缓存到 ViewHolder 中
            holder.picIV=(ImageView)convertView.findViewById(R.id.menu_item_ImageView);
            holder.priceTV=(TextView)convertView.findViewById(R.id.menu_item_price_TextView);
            holder.nameTV = (TextView)convertView.findViewById(R.id.menu_item_name_TextView);
            holder.addTV = (TextView)convertView.findViewById(R.id.menu_item_add_TextView);

            //将 holder 存入 View 中
            convertView.setTag(holder);
        }else{
            //直接 从 View 中获取 holder
            holder=(ViewHolder) convertView.getTag();
        }

        // 动态给定数据
        holder.nameTV.setText(menuBean.getName());
        holder.priceTV.setText("价格:" + menuBean.getPrice() + "    折扣价:" + (menuBean.getPrice()-menuBean.getDiscount()));
       // holder.addTV.setOnClickListener(this);Ø

        // ImageView加载网络图片
        Glide
                .with(context)
                .load(menuBean.getUrl())
                .centerCrop()
                .into(holder.picIV);

        return convertView;
    }

    class ViewHolder{
        ImageView picIV;
        TextView priceTV;
        TextView nameTV;
        TextView addTV;
    }
}

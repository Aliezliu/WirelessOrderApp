package com.softeem.orderapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.softeem.orderapp.R;
import com.softeem.orderapp.activity.OrderActivity;
import com.softeem.orderapp.bean.OrderItemBean;

import java.util.List;

/**
 * Created by Edward on 2017/6/22.
 */

public class OrderItemListAdapter extends BaseAdapter {
    //数据来源
    private List<OrderItemBean> data;
    private OrderActivity context;
    private LayoutInflater inflater;

    public OrderItemListAdapter(List<OrderItemBean> data,OrderActivity context){
        this.data = data;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }


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

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        OrderItemBean itemBean = data.get(position);

        // 创建 View
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.order_item, null);

            holder = new ViewHolder();

            holder.item1 = (TextView) convertView.findViewById(R.id.item1);
            holder.item2 = (TextView) convertView.findViewById(R.id.item2);
            holder.item3 = (TextView) convertView.findViewById(R.id.item3);
            holder.item4 = (TextView) convertView.findViewById(R.id.item4);
            holder.item5 = (TextView) convertView.findViewById(R.id.item5);
            holder.item6 = (TextView) convertView.findViewById(R.id.item6);
            holder.item7 = (Button) convertView.findViewById(R.id.item7);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        // 设置数据
        holder.item1.setText(itemBean.menuBean.getName());
        holder.item2.setText("X " + itemBean.count);
        holder.item3.setText(""+itemBean.menuBean.getPrice());
        holder.item4.setText(""+itemBean.menuBean.getDiscount());
        holder.item5.setText(""+(itemBean.menuBean.getPrice()-itemBean.menuBean.getDiscount()));
        holder.item6.setText(itemBean.state);

        holder.item7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //删除数据
                data.remove(position);
                //刷新 ListView
                notifyDataSetChanged();

                // 希望刷新价格
                context.updatePrice();
            }
        });

        return convertView;
    }

    class ViewHolder{
        TextView item1;
        TextView item2;
        TextView item3;
        TextView item4;
        TextView item5;
        TextView item6;
        Button item7;
    }
}

package com.softeem.orderapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.softeem.orderapp.R;
import com.softeem.orderapp.bean.TableBean;

import java.util.List;

/**
 * Created by Edward on 2017/6/22.
 */

public class TableItemAdapter extends BaseAdapter {
    //数据来源
    private List<TableBean> data;
    private Context context;
    private LayoutInflater inflater;

    public TableItemAdapter(List<TableBean> data, Context context) {
        this.data = data;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setData(List<TableBean> data) {
        this.data = data;
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
        TableBean itemBean = data.get(position);

        // 创建 View
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.table_item, null);
            holder = new ViewHolder();
            holder.tableNumberTextView = (TextView) convertView.findViewById(R.id.table_number_TextView);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // 设置数据
        holder.tableNumberTextView.setText(itemBean.getSite() + "");

        return convertView;
    }

    class ViewHolder {
        TextView tableNumberTextView;
    }
}

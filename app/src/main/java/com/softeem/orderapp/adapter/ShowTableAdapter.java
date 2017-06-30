package com.softeem.orderapp.adapter;

import android.content.Context;
import android.support.v7.app.AlertDialog;
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

public class ShowTableAdapter extends BaseAdapter {
    //数据来源
    private List<TableBean> data;
    private Context context;
    private LayoutInflater inflater;
    private AlertDialog dialog;
    private TextView tableMessage;

    public ShowTableAdapter(List<TableBean> data, Context context) {
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
        final TableBean itemBean = data.get(position);

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
        holder.tableNumberTextView.setText(itemBean.getId() + "");
        convertView.setBackgroundResource(itemBean.isStatus() ? R.drawable.table_selected : R.drawable.table_normal);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(itemBean);
            }

        });

        return convertView;
    }

    private String getShownMessage(TableBean tableBean)
    {
        return "餐桌状态："+(tableBean.isStatus()==true?"有人":"无人")+"\n"
                +"座位数量："+tableBean.getPersonNum()+"\n"
                +"可否抽烟："+(tableBean.isSmoke()==true?"可以":"不可以")+"\n"
                +"餐桌形状："+tableBean.getShape()+"\n"
                +"餐桌餐牌："+tableBean.getSite()+"\n"
                +"备注信息："+(tableBean.getRemark()==null?"无备注":tableBean.getRemark());
    }

    public void showDialog(TableBean tableBean) {
        //显示对话框
        //创建 View
        View view = LayoutInflater.from(context).inflate(R.layout.show_table_item, null);
        tableMessage = (TextView)view.findViewById(R.id.tableMessage);
        tableMessage.setText(getShownMessage(tableBean));
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);

        view.findViewById(R.id.close_TextView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        builder.setView(view);
        dialog = builder.create();
        dialog.show();
    }

    class ViewHolder {
        TextView tableNumberTextView;
    }
}

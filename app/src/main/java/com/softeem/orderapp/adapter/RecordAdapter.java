package com.softeem.orderapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.softeem.orderapp.R;
import com.softeem.orderapp.bean.ShoucangBean;
import com.softeem.orderapp.constant.ServerUrl;
import com.softeem.orderapp.http.HttpCallback;
import com.softeem.orderapp.http.HttpUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/6/30.
 */

public class RecordAdapter extends ArrayAdapter<ShoucangBean> {
    private int resourceId;
    public RecordAdapter(Context context, int textViewResourceId, List<ShoucangBean> objects)
    {
        super(context,textViewResourceId,objects);
        resourceId = textViewResourceId;
    }

    private TextView name;
    private TextView price;
   // private  TextView description;
    private TextView idView;
    private Button recordDeleteButton;
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ShoucangBean bean = getItem(position);
        final int id = bean.getId();
        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        name = (TextView)view.findViewById(R.id.name_TextView);
        price = (TextView)view.findViewById(R.id.price_textView);
        //description = (TextView)view.findViewById(R.id.description_textView);
        idView = (TextView)view.findViewById(R.id.recore_id);
        recordDeleteButton = (Button)view.findViewById(R.id.record_delete_button);


        idView.setText(String.valueOf(bean.id));
        name.setText(bean.getName());
        //description.setText(bean.getDescription());
        price.setText(String.valueOf(bean.getPrice()));
        final HttpCallback deleteRecordCallBack = new HttpCallback() {
            @Override
            public void onSuccess(Object data) {
                Log.d("success",data.toString());
            }

            @Override
            public void onFailure(String message) {

                Log.d("error",message.toString());
            }
        };
        View.OnClickListener recordDeleteButtonOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new HttpUtils().getData(ServerUrl.DELETE_SHOUCANGBEAN + "?id="+id,deleteRecordCallBack);
            }
        };
        recordDeleteButton.setOnClickListener(recordDeleteButtonOnClickListener);
        return view;
    }


}

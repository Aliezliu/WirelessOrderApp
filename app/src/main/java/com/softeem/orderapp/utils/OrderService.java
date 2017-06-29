package com.softeem.orderapp.utils;

import android.view.View;

import java.util.ArrayList;

/**
 * Created by 王致尧 on 2017/6/29.
 */

public interface OrderService {
     ArrayList<View> getStartView(int num);
    ArrayList<View> getMoreView(int start, int row);
}

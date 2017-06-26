package com.softeem.orderapp.http;

import java.util.Objects;

/**
 * Created by Edward on 2017/6/20.
 */

public interface HttpCallback {
    // 当成功调用
    void onSuccess(Object data);

    void onFailure(String message);
}

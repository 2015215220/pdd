package com.chzu.txgc.pdd.Implement;

public interface OnRequestResult {

    void onSuccess(int code, String json);

    void onFailed(int code, String msg);
}

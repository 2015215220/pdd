package com.chzu.txgc.pdd.Utils;


import androidx.fragment.app.FragmentActivity;

import com.chzu.txgc.pdd.Implement.PermissionCallback;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;

public class PermissionUtils {

    public static void getPermission(FragmentActivity context, final PermissionCallback callback, String... permissions){
        new RxPermissions(context)
                .request(permissions)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (!aBoolean) {
                           callback.onFailed();
                        }else {
                            callback.onSuccess();
                        }
                    }
                });
    }
}

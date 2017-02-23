package util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import xiaolong.homeaccount.LoginActivity;

/**
 * Created by xiao on 17-2-22.
 */

public class NetWorkState {

    private Context mContext;
    private static NetWorkState instance =null;
    private ConnectivityManager manager;
    private NetworkInfo mNetworkInfo;

    private NetWorkState(Context context){
        this.mContext = context;
    }

    private NetWorkState(){};

    public static NetWorkState getInstance(){
        if (instance == null){
            synchronized (NetWorkState.class){
                if (instance == null){
                    instance = new NetWorkState();
                }
            }
        }
        return instance;
    }


    /*
    * 检测网络是否连接
    * */
    public boolean isNetworkConnected(){
        manager = (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        mNetworkInfo = manager.getActiveNetworkInfo();
        if (mNetworkInfo != null){
            return mNetworkInfo.isAvailable();
        }
        return false;
    }

}

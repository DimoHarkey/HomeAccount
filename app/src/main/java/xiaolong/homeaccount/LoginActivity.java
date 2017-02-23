package xiaolong.homeaccount;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import model.TB_userInfo;
import util.NetWorkState;

/**
 * Created by xiao on 17-2-22.
 */

public class LoginActivity extends Activity implements View.OnClickListener{

    private EditText l_userName;
    private EditText l_passWord;
    private Button l_register;
    private Button login;
    private Button quit;
    private TB_userInfo userInfo = null;
    private Intent intent = null;
    private NetWorkState netWorkState = NetWorkState.getInstance();
    private String userName = null;
    private String passWord = null;
    private static final int QUERYCODE = 1;
    private static final int RESULTCODE = 2;
    private boolean isSuccess = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login);
        Bmob.initialize(this,"2d759dbe50846b0ebf38d237d4440179");
        checkNetwork();
        initView();
    }

    private void login() {
        userInfo = new TB_userInfo();
        BmobQuery<TB_userInfo> bmobQuery = new BmobQuery<TB_userInfo>();
        if (userName != null && userName.length() < 8 && passWord != null
                && passWord.length() > 6 && passWord.length() < 16){
            bmobQuery.getObject(userName, new QueryListener<TB_userInfo>() {
                @Override
                public void done(TB_userInfo tb_userInfo, BmobException e) {
                    if (e == null){
                        if (passWord.equals(userInfo.getPwd())){
                            isSuccess = true;
                        }
                    }else {
                        Toast.makeText(getApplication(),"用户名/密码错误!!!",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else {
            Toast.makeText(getApplication(),"用户名/密码不规则,请重新输入!!!",Toast.LENGTH_SHORT).show();
            l_userName.setText("");
            l_passWord.setText("");
        }

    }

    private void checkNetwork() {
        if (!netWorkState.isNetworkConnected()){
            Toast.makeText(getApplication(),"请连接网络!!!",Toast.LENGTH_SHORT).show();
        }
    }

    private void initView() {
        l_userName = (EditText)findViewById(R.id.l_username);
        l_passWord = (EditText)findViewById(R.id.l_passWord);
        userName = l_userName.getText().toString();
        passWord = l_passWord.toString().toString();
        l_register = (Button)findViewById(R.id.l_register);
        login = (Button)findViewById(R.id.login);
        quit = (Button)findViewById(R.id.quit);

        l_register.setOnClickListener(this);
        login.setOnClickListener(this);
        quit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                login();
                if (isSuccess){
                    startActivity(new Intent(this,MainActivity.class));
                }
                break;
            case R.id.l_register:
                startActivityForResult(new Intent(this,RegisterActivity.class),QUERYCODE);
                break;
            case R.id.quit:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (QUERYCODE == requestCode && requestCode == RESULTCODE){
            String username = data.getStringExtra("username");
            String password = data.getStringExtra("password");
            l_userName.setText(username);
            l_passWord.setText(password);
        }
    }
}

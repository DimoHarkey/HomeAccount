package xiaolong.homeaccount;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import model.TB_userInfo;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText r_userName;
    private EditText r_passWord;
    private Button r_register;
    private Button r_goBack;
    private TB_userInfo userInfo = null;
    private Intent intent = null;

    private boolean isExist = false;
    private boolean isSuccess = false;
    private String userName;
    private String passWord;
    private static final int REAULTCODE = 0x2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void saveData() {
        userInfo = new TB_userInfo();
        BmobQuery<TB_userInfo> bmobQuery = new BmobQuery<TB_userInfo>();
        userName = r_userName.getText().toString();
        passWord = r_passWord.getText().toString();

        if (userName != null && userName.length() < 8 && passWord != null
                && passWord.length() > 6 && passWord.length() < 16){
            bmobQuery.getObject(userName, new QueryListener<TB_userInfo>() {
                @Override
                public void done(TB_userInfo tb_userInfo, BmobException e) {
                    if (e == null){
                        isExist = true;
                        Toast.makeText(getApplication(),"用户名已存在,请重新输入!",Toast.LENGTH_SHORT).show();
                    }else {
                        Log.i("Bmob","no userInfo");
                    }

                }
            });
            if (!isExist){
                userInfo.setName(userName);
                userInfo.setPwd(passWord);
                userInfo.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e == null){
                            isSuccess = true;
                            Toast.makeText(getApplication(),"注册成功!",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getApplication(),"注册失败!请检查网络是否已连接!",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }else {
            r_userName.setText("");
            r_passWord.setText("");
            Toast.makeText(getApplication(),"请重新输入!!",Toast.LENGTH_SHORT).show();
        }
    }

    private void initView() {
        r_userName = (EditText)findViewById(R.id.r_username);
        r_passWord = (EditText)findViewById(R.id.r_passWord);
        r_register = (Button)findViewById(R.id.r_register);
        r_goBack = (Button)findViewById(R.id.goBack);

        r_register.setOnClickListener(this);
        r_goBack.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register:
                saveData();
                if (isSuccess){
                    Bundle bundle = new Bundle();
                    bundle.putString("username",userName);
                    bundle.putString("password",passWord);
                    intent = new Intent(this,LoginActivity.class);
                    intent.putExtra("userInfo",bundle);
                    this.setResult(REAULTCODE,intent);
                }
                break;
            case R.id.goBack:
                startActivity(new Intent(this,LoginActivity.class));
                break;
            default:
                break;

        }

    }
}

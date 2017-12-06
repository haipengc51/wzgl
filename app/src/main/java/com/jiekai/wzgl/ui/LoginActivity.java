package com.jiekai.wzgl.ui;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiekai.wzgl.MainActivity;
import com.jiekai.wzgl.R;
import com.jiekai.wzgl.config.SqlUrl;
import com.jiekai.wzgl.entity.UserInfoEntity;
import com.jiekai.wzgl.ui.base.MyBaseActivity;
import com.jiekai.wzgl.utils.InputPasswordUtils;
import com.jiekai.wzgl.utils.StringUtils;
import com.jiekai.wzgl.utils.dbutils.DbCallBack;
import com.jiekai.wzgl.utils.dbutils.ExecutorManager;
import com.jiekai.wzgl.weight.ClickDrawableEdit;

import java.util.List;

import butterknife.BindView;

/**
 * Created by LaoWu on 2017/12/6.
 * 登录页面
 */

public class LoginActivity extends MyBaseActivity implements View.OnClickListener {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.menu)
    ImageView menu;
    @BindView(R.id.input_username)
    ClickDrawableEdit inputUsername;
    @BindView(R.id.cancle_username)
    ImageView cancleUsername;
    @BindView(R.id.input_password)
    ClickDrawableEdit inputPassword;
    @BindView(R.id.cancle_password)
    ImageView canclePassword;
    @BindView(R.id.loginBtn)
    TextView loginBtn;

    private InputPasswordUtils userInputUtils;
    private InputPasswordUtils passwordInputUtils;

    @Override
    public void initView() {
        setContentView(R.layout.activity_login);
        isAnimation = false;
    }

    @Override
    public void initData() {
        title.setText(getResources().getString(R.string.login));
    }

    @Override
    public void initOperation() {
        back.setOnClickListener(this);
        loginBtn.setOnClickListener(this);

        userInputUtils = new InputPasswordUtils(inputUsername, cancleUsername);
        passwordInputUtils = new InputPasswordUtils(inputPassword, canclePassword);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.loginBtn:
                login();
                break;
        }
    }

    private void login() {
        String username = inputUsername.getText().toString();
        String password = inputPassword.getText().toString();
        if (StringUtils.isEmpty(username)) {
            alert(R.string.please_input_username);
            return;
        }
        if (StringUtils.isEmpty(password)) {
            alert(R.string.please_input_password);
            return;
        }
        ExecutorManager.dbDeal(ExecutorManager.SELECT)
                .sql(SqlUrl.LoginSql)
                .params(new String[]{username, password})
                .clazz(UserInfoEntity.class)
                .execut(new DbCallBack() {
                    @Override
                    public void onError(String err) {
                        alert(err);
                    }

                    @Override
                    public void onResponse(List result) {
                        if (result != null && result.size() != 0) {
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        } else {
                            alert("用户名或密码错误");
                        }
                    }
                });
    }
}

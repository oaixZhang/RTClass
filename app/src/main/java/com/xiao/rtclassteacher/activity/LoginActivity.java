package com.xiao.rtclassteacher.activity;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.xiao.rtclassteacher.R;
import com.xiao.rtclassteacher.bean.HomeWorkBean;
import com.xiao.rtclassteacher.bean.QuestionBean;
import com.xiao.rtclassteacher.bean.UserBean;
import com.xiao.rtclassteacher.utils.JsonUtil;
import com.xiao.rtclassteacher.utils.SharePreUtil;
import com.xiao.rtclassteacher.utils.StringUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Button loginBtn;
    private TextView registerTv;
    private boolean isLogin = true;
    private LinearLayout registerPart, bottomPart;
    private Toolbar mToolbar;

    private EditText ed_account, ed_pwd;
    private RadioGroup radioGroup;
    private RadioButton stuBtn, teacherBtn;
    private String account;
    private String password;
    private int tag;
    private UserBean user;

    private int firstUse = 0;
    /**
     * progressDialog
     */
    protected ProgressDialog mDialog;
    protected Activity mContext = this;
    protected SharePreUtil sp;


    /**
     * @return
     * @Description: 显示进度条
     */
    protected ProgressDialog getProgressDialog() {
        if (mDialog == null) {
            String mMessage = "正在获取...";

            mDialog = new ProgressDialog(mContext);
            mDialog.setTitle(null);
            mDialog.setMessage(mMessage);
            mDialog.setIndeterminate(true);
        }
        return mDialog;
    }

    /**
     * @Description: 关闭进度条
     */
    protected void dismissDialog() {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sp = new SharePreUtil(this);

//        String str = "1,2,3,";
//        String[] sttr = str.split(",");
//        List<Integer> test = new ArrayList<>();
//        for (int i = 0; i < sttr.length; i++) {
//            Log.d("test", "split: " + sttr[i]);
//            test.add(Integer.parseInt(sttr[i]));
//            Log.d("test", "split to int: " + test.get(i));
//        }
//        Log.d("test", "sum: " + (test.get(2) + 3));

        loginBtn = (Button) findViewById(R.id.btn_login);
        registerTv = (TextView) findViewById(R.id.tv_for_register);
//        stuBtn = (Button) findViewById(R.id.btn_stu);
        ed_account = (EditText) findViewById(R.id.et_account);
        ed_pwd = (EditText) findViewById(R.id.et_password);
        radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        stuBtn = (RadioButton) findViewById(R.id.radio_stu);
        teacherBtn = (RadioButton) findViewById(R.id.radio_teacher);
        registerPart = (LinearLayout) findViewById(R.id.register_part);
        bottomPart = (LinearLayout) findViewById(R.id.bottom_part);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("实时课堂");
        setSupportActionBar(mToolbar);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.radio_teacher)
                    tag = 1;
            }
        });

        loginBtn.setOnClickListener(this);
        registerTv.setOnClickListener(this);
//        stuBtn.setOnClickListener(this);
        initData();

    }

    private void initData() {
        firstUse = sp.getValue("used", 1);
        if (firstUse == 1) {
            initHomework();
            initQuestion();
//        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
//        String date = formatter.format(curDate);
        }
    }

    private void initQuestion() {
        List<QuestionBean> questionList = new ArrayList<>();
        questionList.add(new QuestionBean(1, 1, "第十二届全运会于2013年8月31日在沈阳举行，运动会期间从自A大学的2名志愿者和自B大学的4名志愿者中随机抽取2人到体操比赛场馆服务，至少有一名A大学志愿者的概率是",
                "0.6", "/"));
        questionList.add(new QuestionBean(2, 2, "一个圆柱形容器的容积为V立方米，开始用一根小水管向容器内注水，水面高度达到容器高度一半后，改用一根口径为小水管2倍的大水管注水．向容器中注满水的全过程共用时间t分．求两根水管各自注水的速度",
                "解：......", "/"));
        questionList.add(new QuestionBean(3, 1, "4的平方根是",
                "±2", "/"));
        questionList.add(new QuestionBean(4, 1, "抛物线y＝2x2－bx＋3的对称轴是直线x＝l，则b的值为",
                "4", "=-+x"));
        questionList.add(new QuestionBean(5, 1, "已知一次函数，它的图像不经过哪个象限",
                "第三象限", "/"));
        questionList.add(new QuestionBean(6, 1, "在Rt△ABC中，∠A=90°，如果BC=5，sinB=0.6，那么AC=",
                "3", "/"));
        questionList.add(new QuestionBean(7, 2, "从甲学校到乙学校有A1、A2、A3三条线路，从乙学校到丙学校有B1、B2二条线路．\n" +
                "（1）利用树状图或列表的方法表示从甲学校到丙学校的线路中所有可能出现的结果；\n" +
                "（2）小张任意走了一条从甲学校到丙学校的线路，求小张恰好经过了B1线路的概率是多少？",
                "（2）∴小张从甲学校到丙学校共有6条不同的线路，其中经过B1线路有3....." +
                        "∴P（小张恰好经过了B1线路的概率）=.....", "/"));
        questionList.add(new QuestionBean(8, 1, "菱形的周长为20cm，较短的一条对角线长是6cm，则这个菱形的面积为",
                "24", "/"));
        sp.setValue("questions", JsonUtil.toJson(questionList));
    }

    private void initHomework() {
        HomeWorkBean homeWorkBean = new HomeWorkBean
                ("05月27日", new int[]{1, 2, 4, 6, 7}, new int[]{1, 2, 4, 6}, 1, 1);
        HomeWorkBean homeWorkBean1 = new HomeWorkBean
                ("05月26日", new int[]{1, 3, 4, 8}, new int[]{1}, 1, 1);
        HomeWorkBean homeWorkBean2 = new HomeWorkBean
                ("05月25日", new int[]{1, 2, 4, 5}, new int[]{1, 2, 4}, 1, 1);
        HomeWorkBean homeWorkBean3 = new HomeWorkBean
                ("05月24日", new int[]{1, 3, 2, 4, 6}, new int[]{1}, 1, 1);
        HomeWorkBean homeWorkBean4 = new HomeWorkBean
                ("05月23日", new int[]{1, 3, 2, 4, 5, 7}, new int[]{1, 2, 5, 7}, 1, 1);
        HomeWorkBean homeWorkBean5 = new HomeWorkBean
                ("05月22日", new int[]{1, 3, 4, 5, 8}, new int[]{1, 3}, 1, 1);
        HomeWorkBean homeWorkBean6 = new HomeWorkBean
                ("05月21日", new int[]{1, 3, 2, 4, 6, 7}, new int[]{1, 3, 6, 7}, 1, 1);
        List<HomeWorkBean> list = new ArrayList<>();
        list.add(homeWorkBean);
        list.add(homeWorkBean1);
        list.add(homeWorkBean2);
        list.add(homeWorkBean3);
        list.add(homeWorkBean4);
        list.add(homeWorkBean5);
        list.add(homeWorkBean6);
        sp.setValue("homeworkRecord", JsonUtil.getGson().toJson(list));

        SimpleDateFormat formatter = new SimpleDateFormat("MM月dd日");
        Date curDate = new Date(System.currentTimeMillis());
        String date = formatter.format(curDate);
        HomeWorkBean homeWork = new HomeWorkBean
                (date, new int[]{1, 3, 2, 4}, new int[]{}, 0, 1);
        sp.setValue("homework", JsonUtil.getGson().toJson(homeWork));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
//                Handler handler = new Handler() {
//                    @Override
//                    public void handleMessage(Message msg) {
//                        super.handleMessage(msg);
//                    }
//                };
//                register(handler);
                account = ed_account.getText().toString();
                password = ed_pwd.getText().toString();
                if (loginBtn.getText().equals("点击登录"))
                    login();
                else
                    register();
                break;
            case R.id.tv_for_register:
                if (isLogin)
                    animForRegister();
                else
                    animForLogin();
                break;
//            case R.id.btn_stu:
//                Intent stuIntent = new Intent(LoginActivity.this, MainActivity.class);
//                stuIntent.putExtra("isTeacher", false);
//                startActivity(stuIntent);
//                break;
        }

    }

    private void register() {
        EditText ed_name = (EditText) findViewById(R.id.et_name);
        String name = ed_name.getText().toString();
        user = new UserBean(name, account, password, tag);
        sp.setValue(user.getAccount(), JsonUtil.getGson().toJson(user));
        Intent intent = new Intent();
        intent.setClass(LoginActivity.this, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", user);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void login() {
        String str = sp.getValue(account, "");
        if (str.equals("")) {
            Toast.makeText(mContext, "未注册", Toast.LENGTH_SHORT).show();
        } else {
            user = JsonUtil.getGson().fromJson(str, UserBean.class);
            Intent intent = new Intent();
            intent.setClass(LoginActivity.this, MainActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("user", user);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    private void animForRegister() {
        ObjectAnimator.ofFloat(bottomPart, "translationY", -80f, 0f).setDuration(300).start();
        ObjectAnimator.ofFloat(registerPart, "alpha", 0f, 1f).setDuration(300).start();
        registerPart.setVisibility(View.VISIBLE);
        loginBtn.setText("点击注册");
        registerTv.setText("已有账号，点击登录");
        isLogin = false;
    }

    private void animForLogin() {
        ObjectAnimator.ofFloat(bottomPart, "translationY", 80f, 0f).setDuration(300).start();
        ObjectAnimator.ofFloat(registerPart, "alpha", 1f, 0f).setDuration(200).start();
        registerPart.setVisibility(View.GONE);
        loginBtn.setText("点击登录");
        registerTv.setText("还没有账号？点击注册");
        isLogin = true;
    }

//    private void register(final Handler handler) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//                String urlString = StringUtil.baseUrl + "queryUser/3";
//                try {
//                    URL url = new URL(urlString);
//                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
//                    con.connect();
//                    int code = con.getResponseCode();
//                    System.out.println("response code: " + code);
//                    System.out.println("response msg: " + con.getResponseMessage());
//                    if (code == 200) {
//                        InputStream is = con.getInputStream();
//                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//                        byte[] buffer = new byte[1024];
//                        int len = 0;
//                        while ((len = is.read(buffer)) != -1) {
//                            bos.write(buffer, 0, len);
//                        }
//                        is.close();
//                        bos.close();
//                        System.out.println("response body: " + bos.toString());
//                        Message msg = new Message();
//                        msg.obj = bos.toString();
//                        handler.sendMessage(msg);
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }).start();
//
//    }

}

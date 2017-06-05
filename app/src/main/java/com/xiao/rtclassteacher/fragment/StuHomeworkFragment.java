package com.xiao.rtclassteacher.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.xiao.rtclassteacher.R;
import com.xiao.rtclassteacher.activity.QuestionDisplayActivity;
import com.xiao.rtclassteacher.bean.HomeWorkBean;
import com.xiao.rtclassteacher.bean.QuestionBean;
import com.xiao.rtclassteacher.utils.JsonUtil;
import com.xiao.rtclassteacher.utils.SharePreUtil;
import com.xiao.rtclassteacher.utils.StringUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.view.LineChartView;

/*
 * Created by xiao on 2017/4/20.
 */

public class StuHomeworkFragment extends Fragment {
    //    private RecyclerView mRecyclerView;
    private Context mContext;

    private RelativeLayout rl;
    private LinearLayout record;

    private List<QuestionBean> questionList;

    private SharePreUtil sp;

    private Handler mHandler;

    private LineChartView lineChart;

    String[] date = {"5.21", "5.22", "5.23", "5.24", "5.25", "5.26", "5.27"};//X轴的标注
    int[] score = {50, 42, 90, 100, 60, 74, 60};//图表的数据点
    private List<PointValue> mPointValues = new ArrayList<>();
    private List<AxisValue> mAxisXValues = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        sp = new SharePreUtil(mContext);
        View view = inflater.inflate(R.layout.fragment_homework_stu, container, false);
//        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        rl = (RelativeLayout) view.findViewById(R.id.rl);
        record = (LinearLayout) view.findViewById(R.id.homework_record);
        lineChart = (LineChartView) view.findViewById(R.id.chart);

        inintChartView();

        initRecordData();

        final HomeWorkBean homeWorkBean;
//        SimpleDateFormat formatter = new SimpleDateFormat("MM月dd日");
//        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
//        String date = formatter.format(curDate);
//        homeWorkBean.setDate(date);
//        homeWorkBean.setStatus(0);
//        homeWorkBean.setTarget(1);
//        homeWorkBean.setQuestionIds(new int[]{1, 2, 4});
//        sp.setValue("homework", JsonUtil.getGson().toJson(homeWorkBean));
        String str = sp.getValue("homework", "");
        Log.i("test", str);
        homeWorkBean = JsonUtil.getGson().fromJson(str, HomeWorkBean.class);

        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionList = new ArrayList<>();
                List<QuestionBean> list;
                String questionStr = sp.getValue("questions", "");
                Log.i("test", questionStr);
                list = JsonUtil.getGson().fromJson(questionStr,
                        new TypeToken<ArrayList<QuestionBean>>() {
                        }.getType());
                for (int i : homeWorkBean.getQuestionIds()) {
                    for (QuestionBean question : list) {
                        if (question.getQuestionid() == i) {
                            questionList.add(question);
                            break;
                        }
                    }
                }
                Intent intent = new Intent(mContext, QuestionDisplayActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("questionList", (Serializable) questionList);
                intent.putExtras(bundle);
                intent.putExtra("titleName", "xx的作业");
                startActivity(intent);
            }
        });

        return view;
    }

//    private void withBackend() {
//
//        String msg =
//            JsonArray jsonArray = new JsonParser().parse(msg).getAsJsonArray();
//            for (int i = 0; i < jsonArray.size(); i++) {
//                JsonObject jsonObject = (JsonObject) jsonArray.get(i);
//                QuestionBean questionBean = new QuestionBean();
//                questionBean.setContent(jsonObject.get("content").getAsString());
//                questionBean.setQuestionid(jsonObject.get("questionid").getAsInt());
//                questionBean.setType(jsonObject.get("type").getAsInt());
//                questionBean.setAnswer(jsonObject.get("answer").getAsString());
//
//                View view = LayoutInflater.from(mContext).inflate(R.layout.view_question, null);
//                TextView tv_title = (TextView) view.findViewById(R.id.content);
//                TextView tv_id = (TextView) view.findViewById(R.id.tv_id);
//                TextView tv_type = (TextView) view.findViewById(R.id.tv_type);
//                LinearLayout input = (LinearLayout) view.findViewById(R.id.ll_input);
//                MathLinearView mathLinearView = new MathLinearView(mContext, input);
//                tv_title.setText(questionBean.getContent());
//                tv_id.setText(questionBean.getQuestionid() + "");
//                tv_type.setText(questionBean.getType());
//                questions.add(questionBean);
//                viewList.add(view);
//            }
//    }

    private void initRecordData() {

        for (int i = 0; i < 7; i++) {
            LinearLayout aline = (LinearLayout) LayoutInflater.from(mContext)
                    .inflate(R.layout.homework_list_stu_item, null);
            aline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    questionList = new ArrayList<>();
                    questionList.add(new QuestionBean(1, 2, "当数据发生变化时，可以通过notifyDataSetChanged来刷新UI，通过getItemViewType来获取对应位置的",
                            "zheshidaan", "/&sqrt"));
                    questionList.add(new QuestionBean(2, 1, "一个圆柱形容器的容积为V立方米，开始用一根小水管向容器内注水，水面高度达到容器高度一半后，改用一根口径为小水管2倍的大水管注水．向容器中注满水的全过程共用时间t分．求两根水管各自注水的速度",
                            "zheshidaan", "/&sqrt"));
                    questionList.add(new QuestionBean(3, 1, "若a的值使得x2+4x+a=（x+2）2﹣1成立，则a的值为",
                            "zheshidaan", "/&sqrt"));
                    questionList.add(new QuestionBean(4, 0, "当数据发生变化时，可以通过notifyDataSetChanged来刷新UI，通过getItemViewType来获取对应位置的",
                            "zheshidaan", "/&sqrt"));
                    Intent intent = new Intent(mContext, QuestionDisplayActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("questionList", (Serializable) questionList);
                    intent.putExtras(bundle);
                    intent.putExtra("titleName", "xx的作业");
                    startActivity(intent);
                }
            });
            record.addView(aline);
        }
    }

    private void rlClick() {
        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "click", Toast.LENGTH_SHORT).show();
                mHandler = new Handler(new Handler.Callback() {
                    @Override
                    public boolean handleMessage(Message msg) {
                        String s = (String) msg.obj;
                        Intent intent = new Intent(mContext, QuestionDisplayActivity.class);
                        intent.putExtra("msg", s);
                        startActivity(intent);
                        return false;
                    }
                });
                httpRequest(mHandler);
            }
        });
    }

    private void inintChartView() {
        getAxisXLables();//获取x轴的标注
        getAxisPoints();//获取坐标点
        initLineChart();//初始化
    }

    /**
     * * 设置X 轴的显示
     */
    private void getAxisXLables() {
        for (int i = 0; i < date.length; i++) {
            mAxisXValues.add(new AxisValue(i).setLabel(date[i]));
        }
    }

    /**
     * 图表的每个点的显示
     */
    private void getAxisPoints() {
        for (int i = 0; i < score.length; i++) {
            mPointValues.add(new PointValue(i, score[i]));
        }
    }

    private void initLineChart() {
        Line line = new Line(mPointValues).setColor(Color.YELLOW);  //折线的颜色（橙色）
        List<Line> lines = new ArrayList<>();
        line.setShape(ValueShape.CIRCLE);//折线图上每个数据点的形状  这里是圆形 （有三种 ：ValueShape.SQUARE  ValueShape.CIRCLE  ValueShape.DIAMOND）
        line.setCubic(false);//曲线是否平滑，即是曲线还是折线
        line.setFilled(false);//是否填充曲线的面积
        line.setHasLabels(true);//曲线的数据坐标是否加上备注
//      line.setHasLabelsOnlyForSelected(true);//点击数据坐标提示数据（设置了这个line.setHasLabels(true);就无效）
        line.setHasLines(true);//是否用线显示。如果为false 则没有曲线只有点显示
        line.setHasPoints(true);//是否显示圆点 如果为false 则没有原点只有点显示（每个数据点都是个大的圆点）
        lines.add(line);
        LineChartData data = new LineChartData();
        data.setLines(lines);

        //坐标轴
        Axis axisX = new Axis(); //X轴
        axisX.setHasTiltedLabels(true);  //X坐标轴字体是斜的显示还是直的，true是斜的显示
        axisX.setTextColor(Color.GRAY);  //设置字体颜色
//        axisX.setName("本周作业记录");  //表格名称
        axisX.setTextSize(10);//设置字体大小
        axisX.setMaxLabelChars(8); //最多几个X轴坐标，意思就是你的缩放让X轴上数据的个数7<=x<=mAxisXValues.length
        axisX.setValues(mAxisXValues);  //填充X轴的坐标名称
        data.setAxisXBottom(axisX); //x 轴在底部
        //data.setAxisXTop(axisX);  //x 轴在顶部
        axisX.setHasLines(true); //x 轴分割线

        // Y轴是根据数据的大小自动设置Y轴上限(在下面我会给出固定Y轴数据个数的解决方案)
        Axis axisY = new Axis();  //Y轴
        axisY.setName("");//y轴标注
        axisY.setTextSize(10);//设置字体大小
        data.setAxisYLeft(axisY);  //Y轴设置在左边
        //data.setAxisYRight(axisY);  //y轴设置在右边


        //设置行为属性，支持缩放、滑动以及平移
//        lineChart.setInteractive(true);
//        lineChart.setZoomType(ZoomType.HORIZONTAL);
//        lineChart.setMaxZoom((float) 2);//最大方法比例
//        lineChart.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        lineChart.setLineChartData(data);
        lineChart.setVisibility(View.VISIBLE);

        //设置x轴可以显示的坐标个数，由于缩放的原因，下面的设置为可以显示0-7个
//        Viewport v = new Viewport(lineChart.getMaximumViewport());
//        v.left = 0;
//        v.right = 7;
//        lineChart.setCurrentViewport(v);
    }

    private void httpRequest(final Handler handler) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL urlString = new URL(StringUtil.baseUrl + "test/user.html");
                    HttpURLConnection conn = (HttpURLConnection) urlString.openConnection();
                    conn.setRequestMethod("POST");
                    conn.connect();
                    int code = conn.getResponseCode();
                    System.out.println("response code: " + code);
                    System.out.println("response msg: " + conn.getResponseMessage());
                    if (code == 200) {
                        InputStream is = conn.getInputStream();
                        String response = StringUtil.readFromStream(is);
                        System.out.println("response body: " + response);
                        Message msg = new Message();
                        msg.obj = response;
                        handler.sendMessage(msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

//    class MyAdapter extends RecyclerView.Adapter<StuHomeworkFragment.MyAdapter.MyViewHolder> {
//
//
//        @Override
//        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            MyViewHolder viewHolder = new MyViewHolder(LayoutInflater.from(mContext)
//                    .inflate(R.layout.homework_list_stu_item, parent, false));
//            return viewHolder;
//        }
//
//        @Override
//        public void onBindViewHolder(MyViewHolder holder, int position) {
//
//        }
//
//        @Override
//        public int getItemCount() {
//            return 7;
//        }
//
//        class MyViewHolder extends RecyclerView.ViewHolder {
//
//            public MyViewHolder(View itemView) {
//                super(itemView);
//            }
//        }
//    }
}

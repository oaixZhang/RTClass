package com.xiao.rtclassteacher.service;

import android.content.Context;
import android.inputmethodservice.InputMethodService;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.BaseInputConnection;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnectionWrapper;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiao.rtclassteacher.R;
import com.xiao.rtclassteacher.adapter.KeyboardAdapter;
import com.xiao.rtclassteacher.adapter.RecyclerItemClickListener;
import com.xiao.rtclassteacher.utils.DeleteEvent;
import com.xiao.rtclassteacher.utils.InputEvent;
import com.xiao.rtclassteacher.utils.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class KeyBoardService extends InputMethodService {
    private InputMethodManager mInputMethodManager;

    private Context mContext;
    private View mKeyboard;
    private RecyclerView recyclerView;
    private KeyboardAdapter keyboardAdapter;

    private String DEFAULT_NOTATION = "＞＜＋－×÷＝";

    private String DEFAULT_CHARACTOR = "1234567890ABCDabcd±≠／√^_∠⊥△";

    @Override
    public void onCreate() {
        super.onCreate();
        mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        mContext = getBaseContext();
        //注册service到eventBus，以便接收事件
        EventBus.getDefault().register(this);

        initView();

        setUpRecyclerView();

    }

    private void setUpRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 7));
        keyboardAdapter = new KeyboardAdapter(mContext, DEFAULT_CHARACTOR);
        keyboardAdapter.setOnItemClickListener(new RecyclerItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {
                String str = ((TextView) v).getText().toString();
                switch (str) {
                    case "／":
                        EventBus.getDefault().post(new InputEvent(str));
                        break;
                    case "√":
                        EventBus.getDefault().post(new InputEvent(str));
                        break;
                    case "^":
                        EventBus.getDefault().post(new InputEvent(str));
                        break;
                    case "_":
                        EventBus.getDefault().post(new InputEvent(str));
                        break;
                    default:
                        getCurrentInputConnection().commitText(str, 1);
                        break;
                }
            }
        });
        recyclerView.setAdapter(keyboardAdapter);
    }

    private void initView() {
        mKeyboard = getLayoutInflater().inflate(R.layout.layout_keyboard, null);
        LinearLayout mFunctionLine = (LinearLayout) mKeyboard.findViewById(R.id.function_line);
        LinearLayout mNotationLine = (LinearLayout) mKeyboard.findViewById(R.id.notation_line);
        recyclerView = (RecyclerView) mKeyboard.findViewById(R.id.recycler_view);

        //默认的基本符号
        for (int i = 0; i < mNotationLine.getChildCount(); i++) {
            TextView tv = (TextView) mNotationLine.getChildAt(i);
            tv.setText(DEFAULT_NOTATION.charAt(i) + "");
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getCurrentInputConnection().commitText(((TextView) v).getText(), 1);
                }
            });
        }

        //右侧的功能键
        TextView switTv = (TextView) mFunctionLine.getChildAt(0);
        switTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInputMethodManager.showInputMethodPicker();
            }
        });
        TextView spaceTv = (TextView) mFunctionLine.getChildAt(1);
        spaceTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCurrentInputConnection().commitText(" ", 1);
            }
        });
        TextView backTv = (TextView) mFunctionLine.getChildAt(2);
        backTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new DeleteEvent(new DeleteEvent.HandleDeleteListener() {
                    @Override
                    public void onHandledResult(boolean isHandled) {
                        if (!isHandled) {
                            getCurrentInputConnection().deleteSurroundingText(1, 0);
                        }
                    }
                }));
            }
        });
        TextView enTv = (TextView) mFunctionLine.getChildAt(3);
        enTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendKeyChar('\n');
            }
        });
    }

    //eventbus的处理逻辑
    @Subscribe
    public void onMyMessageEvent(MessageEvent messageEvent) {
        keyboardAdapter.setKeys(messageEvent.getMsg());
        keyboardAdapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateInputView() {
        return mKeyboard;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}

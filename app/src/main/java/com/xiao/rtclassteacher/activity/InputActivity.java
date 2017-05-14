package com.xiao.rtclassteacher.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xiao.rtclassteacher.R;
import com.xiao.rtclassteacher.widget.MathInputContainer;
import com.xiao.rtclassteacher.widget.MathLinearView;

public class InputActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv;
    private MathInputContainer container;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        tv = (TextView) findViewById(R.id.textv);
        Button getBtn = (Button) findViewById(R.id.btn_get);
        Button setBtn = (Button) findViewById(R.id.btn_input_settings);
        container = (MathInputContainer) findViewById(R.id.container);
        new MathLinearView(InputActivity.this, container);
        setBtn.setOnClickListener(this);
        getBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_input_settings:
                startActivity(new Intent(Settings.ACTION_INPUT_METHOD_SETTINGS));
                break;
            case R.id.btn_get:
                tv.setText(container.getInputContent());
                break;
        }
    }

}

package com.atguigu.myapplication;

import android.app.Activity;
import android.app.ActivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.bilibili.magicasakura.utils.ThemeUtils;

import java.util.Random;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private int mCurrentTheme;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mCurrentTheme = ThemeHelper.getTheme(this);

    }

    @OnClick({R.id.bt1, R.id.bt2, R.id.bt3, R.id.bt4})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt1:
                mCurrentTheme = ThemeHelper.CARD_FIREY;
                onConfirm(mCurrentTheme);
                break;
            case R.id.bt2:
                mCurrentTheme = ThemeHelper.CARD_STORM;
                onConfirm(mCurrentTheme);
                break;
            case R.id.bt3:
                mCurrentTheme = ThemeHelper.CARD_SAKURA;
                onConfirm(mCurrentTheme);
                break;
            case R.id.bt4:
                mCurrentTheme = ThemeHelper.CARD_HOPE;
                onConfirm(mCurrentTheme);
                break;
        }
    }

    public void onConfirm(int currentTheme) {
        if (ThemeHelper.getTheme(MainActivity.this) != currentTheme) {
            ThemeHelper.setTheme(MainActivity.this, currentTheme);
            ThemeUtils.refreshUI(MainActivity.this, new ThemeUtils.ExtraRefreshable() {
                        @Override
                        public void refreshGlobal(Activity activity) {
                            //for global setting, just do once
                            if (Build.VERSION.SDK_INT >= 21) {
                                final MainActivity context = MainActivity.this;
                                ActivityManager.TaskDescription taskDescription = new ActivityManager.TaskDescription(null, null, ThemeUtils.getThemeAttrColor(context, android.R.attr.colorPrimary));
                                setTaskDescription(taskDescription);
                                getWindow().setStatusBarColor(ThemeUtils.getColorById(context, R.color.theme_color_primary_dark));
                            }
                        }

                        @Override
                        public void refreshSpecificView(View view) {
                            //TODO: will do this for each traversal
                        }
                    }
            );
            View view = findViewById(R.id.snack_layout);
            if (view != null) {
                TextView textView = (TextView) view.findViewById(R.id.content);
                textView.setText(getSnackContent(currentTheme));
                SnackAnimationUtil.with(this, R.anim.snack_in, R.anim.snack_out)
                        .setDismissDelayTime(1000)
                        .setTarget(view)
                        .play();
            }
        }
    }

    private String getSnackContent(int current){
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        return getResources().getString(getResources().getIdentifier("magicasrkura_prompt_" + random.nextInt(3), "string", getPackageName())) + ThemeHelper.getName(current);
    }
}

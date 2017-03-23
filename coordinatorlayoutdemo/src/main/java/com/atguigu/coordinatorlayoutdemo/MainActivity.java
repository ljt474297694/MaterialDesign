package com.atguigu.coordinatorlayoutdemo;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @Bind(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @Bind(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;
    @Bind(R.id.floatingActionButton)
    FloatingActionButton floatingActionButton;
    boolean isOpen = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if(Math.abs(verticalOffset)>(collapsingToolbarLayout.getHeight() - toolbar.getHeight())*0.75) {
                    if(isOpen) {
                        ScaleAnimation animation =new ScaleAnimation(1,0,1,0,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
                        animation.setDuration(200);
                        animation.setFillAfter(true);
                        floatingActionButton.startAnimation(animation);
                        isOpen = false;
                    }

                }else{
                    if(!isOpen) {
                        ScaleAnimation animation =new ScaleAnimation(0,1,0,1, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
                        animation.setDuration(200);
                        animation.setFillAfter(true);
                        floatingActionButton.startAnimation(animation);
                        isOpen = true;
                    }
                }
            }
        });
    }
}

package com.atguigu.toolbardemo;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 李金桐 on 2017/3/16.
 * QQ: 474297694
 * 功能: ToolBar + CoordinatorLayout  Toolbar滑动隐藏 滑动隐藏回弹 常见功能实现
 */
public class MainActivity extends AppCompatActivity {

    @Bind(R.id.toolBar)
    Toolbar toolBar;
    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.appbar)
    AppBarLayout appbar;
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    @Bind(R.id.listview)
    ListView listview;
    @Bind(R.id.drawerlayout)
    DrawerLayout drawerlayout;
    List<Fragment> fragments;

    List<String> datas;
    @Bind(R.id.coordinatorlayout)
    CoordinatorLayout coordinatorlayout;

    private ActionBarDrawerToggle mDrawerToggle;
    private boolean isOpen = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化 👇
        initView();
        initData();
        initListener();

    }

    private void initData() {
        fragments = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            fragments.add(new RecyclerFragment(i));
        }
        viewpager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewpager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        datas = new ArrayList<>();
        for (int i = 0; i < fragments.size(); i++) {
            datas.add("第" + (i + 1) + "页");
        }

        listview.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datas));


    }

    private void initListener() {

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                drawerlayout.closeDrawers();
                viewpager.setCurrentItem(position);
            }
        });

        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "NavigationIcon", Toast.LENGTH_SHORT).show();

            }
        });

        toolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int menuItemId = item.getItemId();

                if (menuItemId == R.id.action_item1) {
                    Toast.makeText(MainActivity.this, R.string.item_01, Toast.LENGTH_SHORT).show();

                } else if (menuItemId == R.id.action_item2) {
                    Toast.makeText(MainActivity.this, R.string.item_02, Toast.LENGTH_SHORT).show();

                }
                return true;
            }
        });
    }

    private void initView() {
        ButterKnife.bind(this);
        toolBar.setLogo(R.mipmap.ic_launcher);
        toolBar.setTitle("L.T");
        toolBar.setSubtitle("L.T副标题");
        toolBar.setNavigationIcon(android.R.drawable.ic_menu_delete);
        toolBar.inflateMenu(R.menu.toolbar_menu);//设置右上角的填充菜单

//        setSupportActionBar(toolBar);
//        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //设置按键监听 绑定drawerlayout 和 toolBar 让有效果
//        mDrawerToggle = new ActionBarDrawerToggle(this, drawerlayout, toolBar, R.string.open, R.string.close) {
//            @Override
//            public void onDrawerOpened(View drawerView) {
//                super.onDrawerOpened(drawerView);
//            }
//
//            @Override
//            public void onDrawerClosed(View drawerView) {
//                super.onDrawerClosed(drawerView);
//            }
//        };
//        mDrawerToggle.syncState();//设置动画样式
        //绑定到drawerlayout上
//        drawerlayout.addDrawerListener(mDrawerToggle);

    }

    class PagerAdapter extends FragmentPagerAdapter {

        @Override
        public CharSequence getPageTitle(int position) {
            return "第" + (position + 1) + "页";
        }

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == android.R.id.home) {
//            Toast.makeText(MainActivity.this, "回退键", Toast.LENGTH_SHORT).show();
//        }
//        return super.onOptionsItemSelected(item);
//    }

    private int startY;
    private int startX;
    private boolean isScrollY;
    private boolean isFirst;

    //tollBar 回弹效果
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int eventY = (int) ev.getY();
        int eventX = (int) ev.getX();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = eventY;
                startX = eventX;
                isFirst = true;
                break;
            case MotionEvent.ACTION_MOVE:
                if (isFirst) {
                    if (Math.abs(eventX - startX) > Math.abs(eventY - startY) && Math.abs(eventX - startX) > toolBar.getHeight()*0.30) {
                        isScrollY = false;
                        isFirst = false;
                        appbar.setExpanded(isOpen);
                    } else if (Math.abs(eventY - startY) > Math.abs(eventX - startX) && Math.abs(eventY - startY) > toolBar.getHeight()*0.30) {
                        isScrollY = true;
                        isFirst = false;
                    }
                }
                if (isOpen) {
                    if (startY < eventY) {
                        startY = eventY;
                    }
                } else {
                    if (startY > eventY) {
                        startY = eventY;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (isScrollY) {
                    if (isOpen) {
                        if (startY - eventY > toolBar.getHeight() * 0.36) {
                            appbar.setExpanded(false);
                            isOpen = false;
                        } else {
                            appbar.setExpanded(true);
                            isOpen = true;
                        }
                    } else {
                        if (eventY - startY > toolBar.getHeight() * 0.36) {
                            appbar.setExpanded(true);
                            isOpen = true;
                        } else {
                            appbar.setExpanded(false);
                            isOpen = false;
                        }
                    }
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}

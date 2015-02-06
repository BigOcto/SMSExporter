package com.bigocto.smsexporter.ui.activity;

import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bigocto.smsexporter.app.R;
import com.bigocto.smsexporter.database.SmsDbManager;
import com.bigocto.smsexporter.model.SmsContent;
import com.bigocto.smsexporter.ui.fragment.ExporterFragment;
import com.bigocto.smsexporter.ui.fragment.SafeFragment;
import com.bigocto.smsexporter.ui.fragment.SettingFragment;
import com.bigocto.smsexporter.ui.fragment.SmsFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * Created by zhangyu
 * on 2015/2/4.
 */

public class MainFrameActivity extends FragmentActivity {
    private ViewPager mPager;

    private TextView mTextSMS;
    private TextView mTextExprot;
    private TextView mTextSafe;
    private TextView mTextSet;//set top tab labels
    private ImageView cursor;
    private int offset = 0;
    private int currIndex = 0;
    private int bmpW;

    public void onCreate(Bundle save) {
        super.onCreate(save);

        SmsDbManager manager = new SmsDbManager(this);
        HashMap<String, SmsContent> list = manager.getSmsWithNumber("18691078264");
        System.out.print(list.toString());
        setContentView(R.layout.mainframe_activity);
        InitImageView();
        InitTextView();
        InitViewPager();

        Toast t = Toast.makeText(this, list.get("18691078264").getAmount()+"", Toast.LENGTH_LONG);
        t.show();

    }

    /**
     * show the header content******
     */

    private void InitTextView() {

        mTextSMS = (TextView) findViewById(R.id.mainframe_text1);
        mTextExprot = (TextView) findViewById(R.id.mainframe_text2);
        mTextSafe = (TextView) findViewById(R.id.mainframe_text3);
        mTextSet = (TextView) findViewById(R.id.mainframe_text4);

        mTextSMS.setOnClickListener(new MainFrameMyOnClickListener(0));
        mTextExprot.setOnClickListener(new MainFrameMyOnClickListener(1));
        mTextSafe.setOnClickListener(new MainFrameMyOnClickListener(2));
        mTextSet.setOnClickListener(new MainFrameMyOnClickListener(3));
    }

    private void InitViewPager() {
        mPager = (ViewPager) findViewById(R.id.mainframe_Pager);
        List<Fragment> fragmentList = new ArrayList<Fragment>();

        fragmentList.add(new SmsFragment());
        fragmentList.add(new ExporterFragment());
        fragmentList.add(new SafeFragment());
        fragmentList.add(new SettingFragment());

        mPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), fragmentList));
        mPager.setCurrentItem(0);
        mPager.setOnPageChangeListener(new MyOnPageChangeListener());


    }


    public class MyPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragmentList;

        public MyPagerAdapter(android.support.v4.app.FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            this.fragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int arg0) {
            if (fragmentList == null || fragmentList.size() == 0)
                return null;
            else
                return fragmentList.get(arg0);
        }

        @Override
        public int getCount() {
            if (fragmentList == null) {
                return 0;
            } else {
                return fragmentList.size();
            }

        }

    }

    /**
     * ***click to jump a different page************
     */
    public class MainFrameMyOnClickListener implements View.OnClickListener {

        private int index = 0;

        public MainFrameMyOnClickListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            mPager.setCurrentItem(index);
        }

    }


    private void InitImageView() {
        cursor = (ImageView) findViewById(R.id.cursor);
        bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.mainframe_underline)
                .getWidth();//图片宽度
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;//
        offset = (screenW / 4 - bmpW) / 2;//偏移量
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        cursor.setImageMatrix(matrix);//设置初始动画的位置
    }


    /**
     *  动画切换逻辑
     */
    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        int one = offset * 2 + bmpW;
        int two = one * 2;
        int three = one * 3;

        @Override
        public void onPageSelected(int arg0) {
            Animation animation = null;
            switch (arg0) {
                case 0:
                    if (currIndex == 1) {
                        pageColorChange(1, 0);
                        animation = new TranslateAnimation(one, 0, 0, 0);
                    } else if (currIndex == 3) {
                        pageColorChange(3,0);
                        animation = new TranslateAnimation(three, 0, 0, 0);
                    } else if (currIndex == 2) {
                        pageColorChange(2,0);
                        animation = new TranslateAnimation(two, 0, 0, 0);
                    }
                    break;
                case 1:
                    if (currIndex == 0) {
                        pageColorChange(0,1);
                        animation = new TranslateAnimation(0, one, 0, 0);
                    } else if (currIndex == 2) {
                        pageColorChange(2,1);
                        animation = new TranslateAnimation(two, one, 0, 0);
                    } else if (currIndex == 3) {
                        pageColorChange(3,1);
                        animation = new TranslateAnimation(three, one, 0, 0);
                    }
                    break;
                case 2:
                    if (currIndex == 1) {
                        pageColorChange(1,2);
                        animation = new TranslateAnimation(one, two, 0, 0);
                    } else if (currIndex == 3) {
                        pageColorChange(3,2);
                        animation = new TranslateAnimation(three, two, 0, 0);
                    } else if (currIndex == 0) {
                        pageColorChange(0,2);
                        animation = new TranslateAnimation(0, two, 0, 0);
                    }
                    break;
                case 3:
                    if (currIndex == 0) {
                        pageColorChange(0,3);
                        animation = new TranslateAnimation(0, three, 0, 0);
                    } else if (currIndex == 1) {
                        pageColorChange(1,3);
                        animation = new TranslateAnimation(one, three, 0, 0);
                    } else if (currIndex == 2) {
                        pageColorChange(2,3);
                        animation = new TranslateAnimation(two, three, 0, 0);
                    }
                    break;
            }


            currIndex = arg0;
            animation.setFillAfter(true);//
            animation.setDuration(300);
            cursor.startAnimation(animation);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        /**
         * set move page textView color
         * @param arg0 start move page
         * @param arg1 result moved page
         */
        public void pageColorChange(int arg0, int arg1){
            getTextView(arg0).setTextColor(getResources().getColor(R.color.pagerColorNoSelected));
            getTextView(arg1).setTextColor(getResources().getColor(R.color.pagerColor));
        }

    }

    public TextView getTextView(int arg){
        switch (arg){
            case 0:
                return mTextSMS;
            case 1:
                return mTextExprot;
            case 2:
                return mTextSafe;
            case 3:
                return mTextSet;
            default:
                return mTextSMS;
        }
    }
}

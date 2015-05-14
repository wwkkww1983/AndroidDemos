package com.sunplus.view;

import com.sunplus.control.OnViewChangeListener;
import com.sunplus.control.SwitchLayout;

import yanbin.switchDemo.R;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class GuideActivity extends Activity{
    /** Called when the activity is first created. */
	SwitchLayout mSwitchLayout;//自定义的控件
	LinearLayout mLinearLayout;
	SharedPreferences mSharedPreferences;
	int mViewCount;//自定义控件中子控件的个数
	ImageView mImageView[];//底部的imageView
	int mCurSel;//当前选中的imageView
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //利用SharedPreferences使GuideActivity只在安装应用后的第一次才有！
        mSharedPreferences = getSharedPreferences("count",MODE_WORLD_READABLE);
        int count = mSharedPreferences.getInt("count", 0);
        //每启动一次应用，count +1，如果是第一次就启动GuideActivity，不是第一次则开启主activity
//        if (count == 0) {
//        	 init();
//        }else{
//        	Intent intent = new Intent();
//        	intent.setClass(GuideActivity.this, com.sunplus.view.OtherActivity.class);
//        	this.startActivity(intent);
//        	this.finish();
//        }
        init();
        Editor editor =  mSharedPreferences.edit();
        editor.putInt("count", ++count);    //存入数据
        editor.commit();   //提交修改
       
    }

	private void init() {
		mSwitchLayout = (SwitchLayout) findViewById(R.id.switchLayoutID);
		mLinearLayout = (LinearLayout) findViewById(R.id.linerLayoutID);
		
		//得到子控件的个数
		mViewCount = mSwitchLayout.getChildCount();
		mImageView = new ImageView[mViewCount];
		//设置imageView
		for(int i = 0;i < mViewCount;i++){
			//得到LinearLayout中的子控件
			mImageView[i] = (ImageView) mLinearLayout.getChildAt(i);
			mImageView[i].setEnabled(true);//控件激活
			mImageView[i].setOnClickListener(new MOnClickListener());
			mImageView[i].setTag(i);//设置与view相关的标签
		}
		//设置第一个imageView不被激活
		mCurSel = 0;
		mImageView[mCurSel].setEnabled(false);
		mSwitchLayout.setOnViewChangeListener(new MOnViewChangeListener());
		
	}
	
	//底部导航点点击事件的监听器
	private class MOnClickListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			int pos = (Integer) v.getTag();
			System.out.println("pos:--" + pos);
			//设置当前显示的ImageView
			setCurPoint(pos);
			//设置自定义控件中的哪个子控件展示在当前屏幕中
			mSwitchLayout.snapToScreen(pos);
		}
	}
	

	/**
	 * 设置当前显示的ImageView，当前显示第几页底部导航点是白色（不可点击），其他页是灰色（可以点击）
	 * @param pos
	 */
	private void setCurPoint(int pos) {
		if(pos < 0 || pos > mViewCount -1 || mCurSel == pos)
			return;
		//当前的imgaeView将可以被激活
		mImageView[mCurSel].setEnabled(true);
		//将要跳转过去的那个imageView变成不可激活
		mImageView[pos].setEnabled(false);
		mCurSel = pos;
	}
	
	//自定义控件中View改变的事件监听
	private class MOnViewChangeListener implements OnViewChangeListener{
		@Override
		public void onViewChange(int view) {
			System.out.println("view:--" + view);
			if(view < 0 || mCurSel == view){
				return ;
			}else if(view > mViewCount - 1){
				//当滚动到第五个的时候activity会被关闭
				System.out.println("finish activity");
				finish();
			}
			setCurPoint(view);//设置底部导航点的显示
		}
		
	}
	
}
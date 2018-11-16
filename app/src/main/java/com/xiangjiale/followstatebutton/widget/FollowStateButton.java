package com.xiangjiale.followstatebutton.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xiangjiale.followstatebutton.R;

import java.lang.ref.WeakReference;

/**
 * Created by zengqiang on 2018/11/16
 * 从入门到放弃
 */
public class FollowStateButton extends RelativeLayout {
    private Context mContext;
    private TextView mTextView;
    private ProgressBar mProgressBar;
    private MyHand mMyHand;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    };

    public  static  class  MyHand extends  Handler {
        WeakReference<FollowStateButton> weakReference;
        public MyHand(FollowStateButton followStateButton){
            weakReference =new WeakReference(followStateButton);
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            weakReference.get().updataUI(msg.what,msg.obj.toString());
        }
    }
    private  void updataUI(int what , String text){
        switch (what) {
            case 0:
                mProgressBar.clearAnimation();
                mProgressBar.setVisibility(View.GONE);
                mTextView.setTextColor(getResources().getColor(R.color.colorWhite));
                mTextView.setText(text);
                mTextView.setBackgroundResource(R.drawable.border_cicle_color_blue_3);
                Toast.makeText(mContext, "关注失败！", Toast.LENGTH_SHORT).show();
                break;
            case 1:
                mProgressBar.setVisibility(View.GONE);
                mTextView.setTextColor(getResources().getColor(R.color.colorBlack));
                mTextView.setText(text);
                mTextView.setBackgroundResource(R.drawable.fill_blue_circle_3);
                Toast.makeText(mContext, "关注成功！", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                //设定按钮颜色以及进度条可见性
                mProgressBar.setVisibility(View.VISIBLE);
                mTextView.setText(text);
                break;
        }

    }


    public FollowStateButton(Context context) {
        super(context);
        this.mContext=context;
        mMyHand = new MyHand(this);
    }

    public FollowStateButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext=context;
        mMyHand = new MyHand(this);
    }

    public FollowStateButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext=context;
        mMyHand = new MyHand(this);
    }

    private void initView(){

        for (int i=0 ; i < getChildCount() ;i++){
            View view =getChildAt(i);
            if(view instanceof TextView ){
                mTextView = (TextView) view;
            }else  if(view instanceof ProgressBar){
                mProgressBar = (ProgressBar) view;
            }
        }
        mTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Message message =Message.obtain();
                message.what = 2 ;
                message.obj  = "" ;
                mMyHand.sendMessage(message);
                Message message2 =Message.obtain();
                message2.what = 1 ;
                message2.obj  = "关注成功" ;
                mMyHand.sendMessageDelayed(message2,2000);
//                Toast.makeText(mContext,"I am is TextView ",Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        initView();
    }
}

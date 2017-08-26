package com.example.phone;

import com.example.tiantian.R;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;

public class ClearEditText extends EditText implements  
        OnFocusChangeListener, TextWatcher { 
	
    private Drawable mClearDrawable; 
 
    public ClearEditText(Context context) { 
    	this(context, null); 
    } 
 
    public ClearEditText(Context context, AttributeSet attrs) { 
    	
    	this(context, attrs, android.R.attr.editTextStyle); 
    } 
    
    public ClearEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }
    
    
    private void init() { 
    	
    	mClearDrawable = getCompoundDrawables()[2]; 
        if (mClearDrawable == null) { 
        	mClearDrawable = getResources() 
                    .getDrawable(R.drawable.emotionstore_progresscancelbtn); 
        } 
        mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(), mClearDrawable.getIntrinsicHeight()); 
        setClearIconVisible(false); 
        setOnFocusChangeListener(this); 
        addTextChangedListener(this); 
    } 
 
 
    @Override 
    public boolean onTouchEvent(MotionEvent event) { 
        if (getCompoundDrawables()[2] != null) { 
            if (event.getAction() == MotionEvent.ACTION_UP) { 
            	boolean touchable = event.getX() > (getWidth() 
                        - getPaddingRight() - mClearDrawable.getIntrinsicWidth()) 
                        && (event.getX() < ((getWidth() - getPaddingRight())));
                if (touchable) { 
                    this.setText(""); 
                } 
            } 
        } 
 
        return super.onTouchEvent(event); 
    } 
 
    /**
     * 褰揅learEditText鐒︾偣鍙戠敓鍙樺寲鐨勬椂鍊欙紝鍒ゆ柇閲岄潰瀛楃涓查暱搴﹁缃竻闄ゅ浘鏍囩殑鏄剧ず涓庨殣钘�
     */
    @Override 
    public void onFocusChange(View v, boolean hasFocus) { 
        if (hasFocus) { 
            setClearIconVisible(getText().length() > 0); 
        } else { 
            setClearIconVisible(false); 
        } 
    } 
 
 
    /**
     * 璁剧疆娓呴櫎鍥炬爣鐨勬樉绀轰笌闅愯棌锛岃皟鐢╯etCompoundDrawables涓篍ditText缁樺埗涓婂幓
     * @param visible
     */
    protected void setClearIconVisible(boolean visible) { 
        Drawable right = visible ? mClearDrawable : null; 
        setCompoundDrawables(getCompoundDrawables()[0], 
                getCompoundDrawables()[1], right, getCompoundDrawables()[3]); 
    } 
     
    
    /**
     * 褰撹緭鍏ユ閲岄潰鍐呭鍙戠敓鍙樺寲鐨勬椂鍊欏洖璋冪殑鏂规硶
     */
    @Override 
    public void onTextChanged(CharSequence s, int start, int count, 
            int after) { 
        setClearIconVisible(s.length() > 0); 
    } 
 
    @Override 
    public void beforeTextChanged(CharSequence s, int start, int count, 
            int after) { 
         
    } 
 
    @Override 
    public void afterTextChanged(Editable s) { 
         
    } 
    
   
    /**
     * 璁剧疆鏅冨姩鍔ㄧ敾
     */
    public void setShakeAnimation(){
    	this.setAnimation(shakeAnimation(5));
    }
    
    
    /**
     * 鏅冨姩鍔ㄧ敾
     * @param counts 1绉掗挓鏅冨姩澶氬皯涓�
     * @return
     */
    public static Animation shakeAnimation(int counts){
    	Animation translateAnimation = new TranslateAnimation(0, 10, 0, 0);
    	translateAnimation.setInterpolator(new CycleInterpolator(counts));
    	translateAnimation.setDuration(1000);
    	return translateAnimation;
    }
 
 
}
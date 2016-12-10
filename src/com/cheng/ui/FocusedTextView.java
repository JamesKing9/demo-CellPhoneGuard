package com.cheng.ui;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TextView;


/**
 * 自定义 View 重写 View 中的方法，扩展 View的能力
 * 
 */
public class FocusedTextView extends TextView {

	public FocusedTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public FocusedTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public FocusedTextView(Context context) {
		super(context);
	}

	// 重写父类的方法，欺骗系统 TextView 直接获取到焦点。
	@Override
	public boolean isFocused() {
		// 返回true 让系统认为有焦点
		return true;
	}

	// 焦点的改变
	@Override
	protected void onFocusChanged(boolean focused, int direction,
			Rect previouslyFocusedRect) {
		// 有焦点的时候执行移动动画 没有的时候 什么都不做 保持动画效果
		if (focused) {
			super.onFocusChanged(focused, direction, previouslyFocusedRect);
		}
	}

}
package com.mahfa.colorpop;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.animation.Animator.AnimatorListener;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

interface AnimationListener {
	public void onAnimationEnd(int optional_id);
}

public class PopBackgroundView extends FrameLayout implements AnimationListener {
	private static final int CIRCLE_POP_ID = 0;
	private static final int RECT_POP_ID = 1;
	private static final int CIRCLE_DEFAULT_COLOR = Color.parseColor("#3F51B5");
	private static final int RECT_DEFAULT_COLOR = Color.parseColor("#ffffff");
	private Context context;
	private boolean is_animating = false;
	private boolean have_rect_animation = false;
	private ImageView circle_imageView;
	private ImageView rect_imageView;
	private int screen_width;
	private int screen_height;
	private CirclePop circle_pop;
	private RectPop rect_pop;
	private AnimationListener anim_listener;

	public PopBackgroundView(Context context) {
		this(context, null, 0);
	}

	public PopBackgroundView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	public PopBackgroundView(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.context = context;
		init();
	}
	
	private void init(){
		if (!isInEditMode()) {
			LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT);
			circle_imageView = new ImageView(context);
			circle_imageView.setLayoutParams(lp);
			rect_imageView = new ImageView(context);
			rect_imageView.setLayoutParams(lp);
			addView(circle_imageView, 0);
			addView(rect_imageView, 1);
			DisplayMetrics display_metrics = context.getResources()
					.getDisplayMetrics();
			screen_width = display_metrics.widthPixels;
			screen_height = display_metrics.heightPixels;
			circle_pop = new CirclePop(this);
			rect_pop = new RectPop(this);
			circle_imageView.setImageDrawable(circle_pop);
			rect_imageView.setImageDrawable(rect_pop);
		}
	}
	public void animatePop() {
		if (!is_animating) {
			is_animating = true;
			circle_pop.animateCirlce();
		}
	}

	@Override
	public void onAnimationEnd(int optional_id) {
		switch (optional_id) {
		case CIRCLE_POP_ID:
			if (have_rect_animation) {
				rect_pop.animateRect();
			}else {
				is_animating = false;
				if (anim_listener != null) {
					anim_listener.onAnimationEnd(0);
				}
			}
			break;
		case RECT_POP_ID:
			is_animating = false;
			if (anim_listener != null) {
				anim_listener.onAnimationEnd(0);
			}
			break;
		}
	}
	public void setHaveRectAnimation (boolean have_rect_animation){
		this.have_rect_animation = have_rect_animation;
		if (!have_rect_animation) {
			rect_imageView.setVisibility(View.GONE);
		}
	}
	public void setCircleColor(int circle_color) {
		circle_pop.setCircleColor(circle_color);
	}

	public void setRectColor(int rect_color) {
		rect_pop.setRectColor(rect_color);
	}

	public void setCircleStartPointCoordinates(int x, int y) {
		circle_pop.setCircleStartPointCoordinates(x, y);
	}

	public void setRectHeaderHeight(int height_pixls) {
		rect_pop.setRectHeaderHeight(height_pixls);
	}

	public void setAnimationListener(AnimationListener anim_listener) {
		this.anim_listener = anim_listener;
	}
	
	private class CirclePop extends Drawable implements AnimatorListener {
		private AnimationListener anim_listener;
		private Paint circle_paint;
		private Paint shadow_circle_paint;
		private int start_point_x = 0;
		private int start_point_y = 0;
		private int circle_radius = 0;
		private int anim_duration;
		public CirclePop(AnimationListener anim_listener) {
			this.anim_listener = anim_listener;
			circle_paint = new Paint();
			circle_paint.setColor(CIRCLE_DEFAULT_COLOR);
			circle_paint.setAntiAlias(true);
			shadow_circle_paint = new Paint();
			shadow_circle_paint.setColor(Color.BLACK);
			shadow_circle_paint.setAntiAlias(true);
			shadow_circle_paint.setAlpha(50);
			anim_duration = 1000;
		}

		@Override
		public void draw(Canvas canvas) {
			canvas.save();
			canvas.drawCircle(start_point_x, start_point_y, circle_radius+20,
					shadow_circle_paint);
			canvas.drawCircle(start_point_x, start_point_y, circle_radius,
					circle_paint);
			canvas.restore();
		}

		@Override
		public void setAlpha(int alpha) {
			circle_paint.setAlpha(alpha);
			invalidateSelf();
		}

		@Override
		public void setColorFilter(ColorFilter colorFilter) {
			circle_paint.setColorFilter(colorFilter);
			invalidateSelf();
		}

		@Override
		public int getOpacity() {
			return PixelFormat.TRANSLUCENT;
		}

		public void setCircleColor(int color) {
			circle_paint.setColor(color);
			invalidateSelf();
		}

		public void setCircleStartPointCoordinates(int x, int y) {
			start_point_x = x;
			start_point_y = y;
			invalidateSelf();
		}

		public void animateCirlce() {
			int orientation = getResources().getConfiguration().orientation;
			int max_value =0;
			if (orientation == Configuration.ORIENTATION_PORTRAIT) {
				max_value = screen_height + (screen_height / 4);
			}else {
				max_value = screen_width + (screen_width / 4);
			}
			ValueAnimator va = ValueAnimator.ofInt(0, max_value/3);
			va.setDuration(anim_duration);
			va.addListener(this);
			va.setInterpolator(new AccelerateInterpolator());
			va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
				public void onAnimationUpdate(ValueAnimator animation) {
					int value = (int) animation.getAnimatedValue();
					circle_radius = value*3;
					invalidateSelf();
				}
			});
			va.start();
		}

		@Override
		public void onAnimationCancel(Animator arg0) {
		}

		@Override
		public void onAnimationEnd(Animator arg0) {
			anim_listener.onAnimationEnd(CIRCLE_POP_ID);
		}

		@Override
		public void onAnimationRepeat(Animator arg0) {
		}

		@Override
		public void onAnimationStart(Animator arg0) {
		}

	}

	private class RectPop extends Drawable implements AnimatorListener {
		private AnimationListener anim_listener;
		private Paint rect_paint;
		private int rect_header_height = 0;
		private Rect rect;

		public RectPop(AnimationListener anim_listener) {
			this.anim_listener = anim_listener;
			rect_paint = new Paint();
			rect_paint.setColor(RECT_DEFAULT_COLOR);
			rect_paint.setAntiAlias(true);
			rect = new Rect(0, screen_height, screen_width, screen_height);
		}

		@Override
		public void draw(Canvas canvas) {
			canvas.save();
			canvas.drawRect(rect, rect_paint);
			canvas.restore();
		}

		@Override
		public void setAlpha(int alpha) {
			rect_paint.setAlpha(alpha);
			invalidateSelf();
		}

		@Override
		public void setColorFilter(ColorFilter colorFilter) {
			rect_paint.setColorFilter(colorFilter);
			invalidateSelf();
		}

		@Override
		public int getOpacity() {
			return PixelFormat.TRANSLUCENT;
		}

		public void setRectColor(int color) {
			rect_paint.setColor(color);
			invalidateSelf();
		}

		public void setRectHeaderHeight(int height_pixls) {
			rect_header_height = height_pixls;
			invalidateSelf();
		}

		public void animateRect() {
			ValueAnimator va = ValueAnimator.ofInt(rect_header_height/2, screen_height/2);
			va.setDuration(1000);
			va.addListener(this);
			va.setInterpolator(new DecelerateInterpolator());
			va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
				public void onAnimationUpdate(ValueAnimator animation) {
					int value = ((int) animation.getAnimatedValue())*2;
					int rect_top = -((value - rect_header_height) - screen_height);
					rect.top = rect_top;
					invalidateSelf();
				}
			});
			va.start();
		}

		@Override
		public void onAnimationCancel(Animator arg0) {
		}

		@Override
		public void onAnimationEnd(Animator arg0) {
			anim_listener.onAnimationEnd(RECT_POP_ID);
		}

		@Override
		public void onAnimationRepeat(Animator arg0) {
		}

		@Override
		public void onAnimationStart(Animator arg0) {

		}
	}

}

package com.mahfa.colorpop;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

/**
 * @author Mahdi Fallahi
 * 
 *         <p>
 *         PopBackgroundView is a {@link View} used for drawing background
 *         animations like a growing circle or a rectangle that grows from
 *         bottom of screen
 *         </p>
 */
public class PopBackgroundView extends View implements AnimatorListener {
	/**
	 * A simple interface to be notified when {@link PopBackgroundView} 's
	 * animation end
	 *
	 */
	public static interface AnimationListener {
		public void onAnimationEnd();
	}

	// id of circle animation
	private static final int CIRCLE_POP_ID = 0;
	// id of rectangle animation
	private static final int RECT_POP_ID = 1;
	// the circle animation default color
	private static final int CIRCLE_DEFAULT_COLOR = Color.parseColor("#3F51B5");
	// the rect animation default color
	private static final int RECT_DEFAULT_COLOR = Color.parseColor("#ffffff");
	// the circles fill type
	public static final int CIRLCES_FILL_WIDTH_TYPE = 0;
	public static final int CIRLCES_FILL_HEIGHT_TYPE = 1;
	private Context context;

	private boolean is_animating = false;
	private boolean have_rect_animation = false;

	private int screen_width;
	private int screen_height;

	private int current_pop_id;
	// paint used for drawing circles
	private Paint circle_paint;
	// paint used for drawing the shadows of circles
	private Paint shadow_circle_paint;
	// the coordinates of the center of circles
	private int start_point_x = 0;
	private int start_point_y = 0;

	private int circle_color = CIRCLE_DEFAULT_COLOR;
	private int circle_radius = 0;
	private int circle_max_radius = 0;

	private int circles_fill_type;
	// paint used for drawing rectangles
	private Paint rect_paint;
	// the space at the top of the last rectangle that would be drawn
	private int rect_space_top = 0;

	private Rect rect;
	// an instance of the AnimationListener interface used for notifying when
	// the background animation ends
	private AnimationListener anim_listener;

	public PopBackgroundView(Context context) {
		super(context);
		this.context = context;
		init();
	}

	public PopBackgroundView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init();
	}

	public PopBackgroundView(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.context = context;
		init();
	}

	private void init() {
		setClickable(true);
		DisplayMetrics display_metrics = context.getResources()
				.getDisplayMetrics();
		screen_width = display_metrics.widthPixels;
		screen_height = display_metrics.heightPixels;
		current_pop_id = CIRCLE_POP_ID;
		circle_paint = new Paint();
		circle_paint.setColor(circle_color);
		circle_paint.setAntiAlias(true);
		circle_paint.setStyle(Paint.Style.FILL);
		shadow_circle_paint = new Paint();
		shadow_circle_paint.setColor(Color.BLACK);
		shadow_circle_paint.setAntiAlias(true);
		shadow_circle_paint.setAlpha(50);
		shadow_circle_paint.setStyle(Paint.Style.FILL);
		int orientation = getResources().getConfiguration().orientation;
		if (orientation == Configuration.ORIENTATION_PORTRAIT) {
			circles_fill_type = CIRLCES_FILL_HEIGHT_TYPE;
		} else {
			circles_fill_type = CIRLCES_FILL_WIDTH_TYPE;
		}
		rect_paint = new Paint();
		rect_paint.setColor(RECT_DEFAULT_COLOR);
		rect_paint.setAntiAlias(true);
		rect_paint.setStyle(Paint.Style.FILL);
		rect = new Rect(0, screen_height, screen_width, screen_height);
	}

	/**
	 * call this void to start the {@link PopBackgroundView}'s animations
	 */
	public void animatePop() {
		if (!is_animating) {
			is_animating = true;
			animateCirlce();
		}
	}

	/**
	 * an internal void to respond to animation ends it would start the
	 * rectangles animation after the circles animation if have_rect_animation
	 * is set to true
	 * 
	 * @param optional_id
	 *            the id of the background animation
	 */
	private void onAnimationEnd(int optional_id) {
		switch (optional_id) {
		case CIRCLE_POP_ID:
			if (have_rect_animation) {
				current_pop_id = RECT_POP_ID;
				animateRect();
			} else {
				is_animating = false;
				if (anim_listener != null) {
					anim_listener.onAnimationEnd();
				}
			}
			break;
		case RECT_POP_ID:
			is_animating = false;
			if (anim_listener != null) {
				anim_listener.onAnimationEnd();
			}
			break;
		}
	}

	/**
	 * Internal void to start the circles animation.
	 * <p>
	 * when this void is called the circles radius would be updated by a
	 * {@link ValueAnimator} and then it will call the {@link View}'s
	 * invalidate() void witch calls the onDraw void each time so a bigger
	 * circle would be drawn each time till the cirlce's fill the whole screen.
	 * </p>
	 */
	private void animateCirlce() {
		if (circles_fill_type == CIRLCES_FILL_HEIGHT_TYPE) {
			circle_max_radius = screen_height + (screen_height / 4);
		} else {
			circle_max_radius = screen_width + (screen_width / 4);
		}
		ValueAnimator va = ValueAnimator.ofInt(0, circle_max_radius / 3);
		va.setDuration(1000);
		va.addListener(this);
		va.setInterpolator(new AccelerateInterpolator());
		va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			public void onAnimationUpdate(ValueAnimator animation) {
				int value = (int) animation.getAnimatedValue();
				circle_radius = value * 3;
				invalidate();
			}
		});
		va.start();
	}

	/**
	 * Internal void to start the rectangle animation.
	 * <p>
	 * when this void is called the space at the top of the rectangle would be
	 * updated by a {@link ValueAnimator} and then it will call the {@link View}
	 * 's invalidate() void witch calls the onDraw void each time so a bigger
	 * rectangle would be drawn each time till the it the rectangles height is
	 * enough
	 * </p>
	 */
	private void animateRect() {
		ValueAnimator va = ValueAnimator.ofInt(rect_space_top / 2,
				screen_height / 2);
		va.setDuration(500);
		va.addListener(this);
		va.setInterpolator(new DecelerateInterpolator());
		va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			public void onAnimationUpdate(ValueAnimator animation) {
				int value = ((int) animation.getAnimatedValue()) * 2;
				int rect_top = -((value - rect_space_top) - screen_height);
				rect.top = rect_top;
				invalidate();
			}
		});
		va.start();
	}

	/**
	 * draw based on the id of the animation and the informations that gets
	 * updated each time
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.save();
		if (current_pop_id == CIRCLE_POP_ID) {
			//draw circle's shadow
			canvas.drawCircle(start_point_x, start_point_y, circle_radius + 20,
					shadow_circle_paint);
			// draw the circle
			canvas.drawCircle(start_point_x, start_point_y, circle_radius,
					circle_paint);
		} else {
			canvas.drawColor(circle_color);
			canvas.drawRect(rect, rect_paint);
		}
		canvas.restore();
	}

	@Override
	public void onAnimationEnd(Animator arg0) {
		if (current_pop_id == CIRCLE_POP_ID) {
			this.onAnimationEnd(CIRCLE_POP_ID);
		} else {
			this.onAnimationEnd(RECT_POP_ID);
		}
	}

	@Override
	public void onAnimationRepeat(Animator arg0) {
	}

	@Override
	public void onAnimationStart(Animator arg0) {

	}

	@Override
	public void onAnimationCancel(Animator arg0) {
	}

	/**
	 * @param have_rect_animation
	 *            if set to true there is going to be a rectangle animation that
	 *            grows from bottom of the screen after the circles animations
	 */
	public void setHaveRectAnimation(boolean have_rect_animation) {
		this.have_rect_animation = have_rect_animation;
	}

	/**
	 * 
	 * @param circle_color
	 *            the color of the circles animation
	 */
	public void setCircleColor(int circle_color) {
		this.circle_color = circle_color;
		circle_paint.setColor(circle_color);
		invalidate();
	}

	/**
	 * 
	 * @param rect_color
	 *            the color of rectangles animation
	 */
	public void setRectColor(int rect_color) {
		rect_paint.setColor(rect_color);
		invalidate();
	}

	/**
	 * 
	 * @param x
	 *            the x coordinate of start point of the circles animation
	 * @param y
	 *            the y coordinate of start point of the circles animation
	 */
	public void setCircleStartPointCoordinates(int x, int y) {
		start_point_x = x;
		start_point_y = y;
		invalidate();
	}

	/**
	 * 
	 * @param circles_fill_type
	 *            if set to {@code CIRLCES_FILL_WIDTH_TYPE} the
	 *            circles animation would end when the circles have filled the
	 *            width of screen and if set to
	 *            {@code CIRLCES_FILL_HEIGHT_TYPE} circles animation
	 *            would end when the circles have filled the height of screen
	 * 
	 */
	public void setCirclesFillType(int circles_fill_type) {
		this.circles_fill_type = circles_fill_type;
		invalidate();
	}

	/**
	 * 
	 * @param space_pixls
	 *            the space at the top of the last rectangle that would be drawn
	 */
	public void setRectSpaceTop(int space_pixls) {
		rect_space_top = space_pixls;
		invalidate();
	}

	public void setAnimationListener(AnimationListener anim_listener) {
		this.anim_listener = anim_listener;
	}

}

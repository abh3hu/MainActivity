package edu.virignia.cs2110.UseGoogleMaps;
import android.content.Context;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class ScribbleView extends View {

public ScribbleView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.init();
		// TODO Auto-generated constructor stub
	}
	Paint paint;
	public void init() {
		paint = new Paint();
		paint.setARGB(255, 255, 0, 0);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(5);
	}
	Bitmap heart = BitmapFactory.decodeResource(getResources(),R.drawable.heart);
	Bitmap  bomb= BitmapFactory.decodeResource(getResources(),R.drawable.bombbig);

	@Override
	public void onDraw(Canvas c) {
		super.onDraw(c);
		int height = this.getMeasuredHeight();
		int width = this.getMeasuredWidth();
		c.drawBitmap(heart, 12, 12, paint);
		c.drawBitmap(heart, 24, 12, paint);
		c.drawBitmap(heart, 36, 12, paint);
		c.drawBitmap(bomb, 60, 12, paint);

	}

}
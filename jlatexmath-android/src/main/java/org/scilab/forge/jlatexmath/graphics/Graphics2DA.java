package org.scilab.forge.jlatexmath.graphics;

import org.scilab.forge.jlatexmath.ColorUtil;
import org.scilab.forge.jlatexmath.font.FontA;
import org.scilab.forge.jlatexmath.font.FontRenderContextA;
import org.scilab.forge.jlatexmath.geom.Line2DA;
import org.scilab.forge.jlatexmath.platform.font.Font;
import org.scilab.forge.jlatexmath.platform.font.FontRenderContext;
import org.scilab.forge.jlatexmath.platform.geom.Line2D;
import org.scilab.forge.jlatexmath.platform.geom.Rectangle2D;
import org.scilab.forge.jlatexmath.platform.geom.RoundRectangle2D;
import org.scilab.forge.jlatexmath.platform.graphics.Color;
import org.scilab.forge.jlatexmath.platform.graphics.Graphics2DInterface;
import org.scilab.forge.jlatexmath.platform.graphics.Image;
import org.scilab.forge.jlatexmath.platform.graphics.RenderingHints;
import org.scilab.forge.jlatexmath.platform.graphics.Stroke;
import org.scilab.forge.jlatexmath.platform.graphics.Transform;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.view.View;

@SuppressLint("NewApi")
public class Graphics2DA implements Graphics2DInterface {

	private Canvas mCanvas;
	private View mView;

	private Paint mDrawPaint;
	private Paint mFontPaint;

	// The canvas is never scaled directly because with hardwareAcceleration:on
	// the drawing be pixelated. Instead the scale values are saved in a stack
	private ScaleStack mScaleStack;

	private FontA mFont;
	private ColorA mColor;

	public Graphics2DA() {
		mDrawPaint = new Paint();
		mDrawPaint.setStyle(Style.STROKE);
		mDrawPaint.setSubpixelText(true);
		mDrawPaint.setAntiAlias(true);
		mDrawPaint.setLinearText(true);

		mFontPaint = new Paint();
		mFontPaint.set(mDrawPaint);

		mScaleStack = new ScaleStack();

		mFont = new FontA("Serif", Font.PLAIN, 10);
		
		mColor = (ColorA) ColorUtil.BLACK;
	}

	public Graphics2DA(Canvas canvas) {
		this();
		setCanvas(canvas);
	}

	public Graphics2DA(Canvas canvas, View view) {
		this(canvas);
		setView(view);
	}

	public void setCanvas(Canvas canvas) {
		mCanvas = canvas;
	}

	public void setView(View view) {
		mView = view;
	}

	public void setStroke(Stroke stroke) {
		BasicStrokeA basicStroke = (BasicStrokeA) stroke;
		setBasicStroke(basicStroke);
	}

	private void setBasicStroke(BasicStrokeA basicStroke) {
		mDrawPaint.setStrokeWidth(basicStroke.getWidth());
		mDrawPaint.setStrokeMiter(basicStroke.getMiterLimit());
		mDrawPaint.setStrokeCap(basicStroke.getNativeCap());
		mDrawPaint.setStrokeJoin(basicStroke.getNativeJoin());
	}

	public Stroke getStroke() {
		return new BasicStrokeA(mDrawPaint.getStrokeWidth(),
				mDrawPaint.getStrokeMiter(), mDrawPaint.getStrokeCap(),
				mDrawPaint.getStrokeJoin());
	}

	public void setColor(Color color) {
		mColor = (ColorA) color;
		mDrawPaint.setColor(mColor.getColor());
	}

	public Color getColor() {
		return mColor;
	}

	@SuppressWarnings("deprecation")
	public Transform getTransform() {
		Matrix matrix = null;
		if (mView != null && android.os.Build.VERSION.SDK_INT >= 11) {
			matrix = mView.getMatrix();
		}
		if (matrix == null) {
			matrix = mCanvas.getMatrix();
		}
		return new TransformA(matrix);
	}

	public Font getFont() {
		return mFont;
	}

	public void setFont(Font font) {
		mFont = (FontA) font;
		mDrawPaint.setTypeface((Typeface) mFont.getNativeObject());
		mDrawPaint.setTextSize(mScaleStack.scaleFontSize(mFont.getSize()));
	}

	public void fillRect(int x, int y, int width, int height) {
		beforeFill();

		mCanvas.drawRect(mScaleStack.scaleX(x), mScaleStack.scaleY(y),
				mScaleStack.scaleX(x + width), mScaleStack.scaleY(y + height),
				mDrawPaint);

		afterFill();
	}

	public void fill(Rectangle2D rectangle) {
		beforeFill();

		draw(rectangle);

		afterFill();
	}

	public void draw(Rectangle2D rectangle) {
		RectF rect = (RectF) rectangle.getNativeObject();
		RectF copy = new RectF(rect);

		mCanvas.drawRect(mScaleStack.scaleRectF(copy), mDrawPaint);
	}

	public void draw(RoundRectangle2D rectangle) {
		RectF rect = (RectF) rectangle.getNativeObject();
		RectF copy = new RectF(rect);

		mCanvas.drawRoundRect(mScaleStack.scaleRectF(copy),
				mScaleStack.scaleX((float) rectangle.getArcW()),
				mScaleStack.scaleY((float) rectangle.getArcH()), mDrawPaint);
	}

	public void draw(Line2D line) {
		Line2DA impl = (Line2DA) line;
		PointF start = impl.getStartPoint();
		PointF end = impl.getEndPoint();

		mCanvas.drawLine(mScaleStack.scaleX(start.x),
				mScaleStack.scaleY(start.y), mScaleStack.scaleX(end.x),
				mScaleStack.scaleY(end.y), mDrawPaint);
	}

	public void drawChars(char[] data, int offset, int length, int x, int y) {
		beforeFill();
		
		mDrawPaint.setTextSize(mScaleStack.scaleFontSize(mFont.getSize()));
		mCanvas.drawText(data, offset, length, mScaleStack.scaleX(x),
				mScaleStack.scaleY(y), mDrawPaint);
		
		afterFill();
	}

	public void drawString(String text, int x, int y, Paint paint) {
		mCanvas.drawText(text, mScaleStack.scaleX(x), mScaleStack.scaleY(y),
				paint);
	}

	public void drawArc(int x, int y, int width, int height, int startAngle,
			int arcAngle) {
		RectF oval = new RectF(x, y, (x + width), (y + height));
		
		mCanvas.drawArc(mScaleStack.scaleRectF(oval), startAngle, arcAngle,
				false, mDrawPaint);
	}

	public void fillArc(int x, int y, int width, int height, int startAngle,
			int arcAngle) {
		beforeFill();

		drawArc(x, y, width, height, startAngle, arcAngle);

		afterFill();
	}

	public void translate(double x, double y) {
		mCanvas.translate(mScaleStack.scaleX((float) x),
				mScaleStack.scaleY((float) y));
	}

	public void scale(double x, double y) {
		mScaleStack.appendScale((float) x, (float) y);
	}

	public void rotate(double theta, double x, double y) {
		translate(x, y);
		rotate(theta);
		translate(-x, -y);
	}

	public void rotate(double theta) {
		// theta is in radians
		// change to degrees
		float degrees = (float) Math.toDegrees(theta);
		mCanvas.rotate(degrees);
	}

	public void drawImage(Image image, int x, int y) {
		ImageA imageA = (ImageA) image;
		Bitmap bitmap = imageA.getBitmap();

		mCanvas.drawBitmap(mScaleStack.scaleBitmap(bitmap),
				mScaleStack.scaleX(x), mScaleStack.scaleY(y), mDrawPaint);

	}

	public void drawImage(Image image, Transform transform) {
		ImageA imageA = (ImageA) image;
		Bitmap bitmap = imageA.getBitmap();
		
		mCanvas.drawBitmap(mScaleStack.scaleBitmap(bitmap), (Matrix) transform,
				mDrawPaint);
	}

	public FontRenderContext getFontRenderContext() {
		mFontPaint.set(mDrawPaint);
		return new FontRenderContextA(mFontPaint);
	}

	public void setRenderingHint(int key, int value) {
		if (key == RenderingHints.KEY_ANTIALIASING
				&& value == RenderingHints.VALUE_ANTIALIAS_ON) {
			mDrawPaint.setAntiAlias(true);
		} else {
			// No other rendering hint is supported
		}
	}

	public int getRenderingHint(int key) {
		// Not supported
		return -1;
	}

	public void dispose() {
		// NO-OP
	}

	private Style mOldDrawPaintStyle;

	private void saveDrawPaintStyle() {
		mOldDrawPaintStyle = mDrawPaint.getStyle();
	}

	private void restoreDrawPaintStyle() {
		mDrawPaint.setStyle(mOldDrawPaintStyle);
	}

	private void setDrawPaintFillStyle() {
		mDrawPaint.setStyle(Style.FILL);
	}

	private void beforeFill() {
		saveDrawPaintStyle();
		setDrawPaintFillStyle();
	}

	private void afterFill() {
		restoreDrawPaintStyle();
	}

	public void saveTransformation() {
		mCanvas.save(Canvas.MATRIX_SAVE_FLAG);
		mScaleStack.pushScaleValues();
	}

	public void restoreTransformation() {
		mCanvas.restore();
		mScaleStack.popScaleValues();
	}
}

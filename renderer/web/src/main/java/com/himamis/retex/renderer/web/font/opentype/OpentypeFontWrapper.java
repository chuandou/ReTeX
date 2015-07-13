package com.himamis.retex.renderer.web.font.opentype;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.core.client.JavaScriptObject;
import com.himamis.retex.renderer.web.font.FontWrapper;

public class OpentypeFontWrapper implements FontWrapper {

	private JavaScriptObject impl;

	public OpentypeFontWrapper(JavaScriptObject impl) {
		this.impl = impl;
	}
	
	@Override
	public void drawGlyph(String c, int x, int y, int size, Context2d ctx) {
		drawGlyphNative(c, x, y, size, ctx);
	}

	private native void drawGlyphNative(String c, int x, int y, int size, Context2d ctx) /*-{
		var that = this;
		var font = that.@com.himamis.retex.renderer.web.font.opentype.OpentypeFontWrapper::impl;
		var path = font.getPath(c, x, y, size);

		path.fill = ctx.fillStyle;
		path.stroke = null;
		path.strokeWidth = 1;

		// reset the path before to ensure that the previous drawing doesn't interfere with this one
		ctx.beginPath();

		path.draw(ctx);

		// reset the path, so that the following calls to stroke() and fill() will not draw again the character
		ctx.beginPath();
	}-*/;
}
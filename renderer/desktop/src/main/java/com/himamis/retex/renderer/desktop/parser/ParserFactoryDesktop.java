package com.himamis.retex.renderer.desktop.parser;

import com.himamis.retex.renderer.share.platform.parser.Parser;
import com.himamis.retex.renderer.share.platform.parser.ParserFactory;

public class ParserFactoryDesktop extends ParserFactory {

	@Override
	public Parser createParser() {
		return new ParserD();
	}

}
package com.himamis.retex.renderer.desktop.resources;

import com.himamis.retex.renderer.share.platform.resources.ResourceLoader;
import com.himamis.retex.renderer.share.platform.resources.ResourceLoaderFactory;

public class ResourceLoaderFactoryDesktop implements ResourceLoaderFactory {

	public ResourceLoader createResourceLoader() {
		return new ResourceLoaderD();
	}

}
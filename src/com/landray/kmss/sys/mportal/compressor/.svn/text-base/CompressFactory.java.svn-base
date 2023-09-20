package com.landray.kmss.sys.mportal.compressor;

import java.util.HashMap;
import java.util.List;

import com.landray.kmss.sys.mobile.compressor.config.CompressConfigPlugin;

public class CompressFactory {

	private static final HashMap<String, ICompressRunner> compress = new HashMap<>();
	static {
		compress.put(CompressConfigPlugin.MPORTLET_ID + ".js", new CompressJsRunner());
		// compress.put(CompressConfigPlugin.MPORTLET_ID + ".css", new
		// CompressCssRunner());
	}

	public static void run(String key, List<String> values) throws Exception {
		compress.get(key).run(values);
	}
}

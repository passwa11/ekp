
package com.landray.kmss.sys.ui.compressor;

import com.landray.kmss.sys.ui.compressor.config.PcCompressTaskInfo;

import java.util.HashMap;

/**
 * 根据文件类型创建对应的任务实现
 * @author 林宇超
 * 
 */
public class PcCompressTaskFactory {

	private static final HashMap<String, PcCompressTaskCreator> creators = new HashMap<>();
	static {
		creators.put("js", new JsPcCompressTaskCreator());
	}

	/**
	 * 根据压缩信息获取对应的类型的压缩任务
	 * @param info
	 * @return
	 */
	public static PcCompressTask getTask(PcCompressTaskInfo info) {
		String type = info.getType();
		return creators.get(type).create(info);
	}

	/**
	 * js文件类型创建器
	 */
	static class JsPcCompressTaskCreator implements PcCompressTaskCreator {

		@Override
		public PcCompressTask create(PcCompressTaskInfo info) {

			return new JsPcCompressTask(info);
		}

	}

}


package com.landray.kmss.sys.ui.compressor;

import com.landray.kmss.sys.ui.compressor.config.PcCompressTaskInfo;

import java.util.HashMap;

/**
 *
 * @author 林宇超
 * 
 */
public class PcResourceChannelTaskFactory {

	private static final HashMap<String, PcResourceChannelTaskCreator> creators = new HashMap<>();
	static {
		creators.put("js", new JsPcResourceChannelTaskCreator());
	}

    /**
     * 根据压缩信息获取对应的类型的换源任务
	 * @param info
     * @return
     */
	public static PcResourceChannelTask getTask(PcCompressTaskInfo info) {
		String type = info.getType();
		return creators.get(type).create(info);
	}

	/**
	 * js文件类型创建器
	 */
	static class JsPcResourceChannelTaskCreator implements PcResourceChannelTaskCreator {

		@Override
		public PcResourceChannelTask create(PcCompressTaskInfo info) {

			return new JsPcResourceChannelTask(info);
		}

	}

}

/**
 * 
 */
package com.landray.kmss.sys.ui.compressor.config;

import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.plugin.core.config.IExtensionPoint;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * PC端压缩静态资源扩展点工具类
 * @author 林宇超
 * 
 */
public class PcCompressConfigPlugin {
	private final static Logger logger = LoggerFactory.getLogger(PcCompressConfigPlugin.class);

	public static final String POINT_ID = "com.landray.kmss.sys.ui.compress";

	public static final String ITEM_INFO_PARAM_NAME = "name";

	public static final String ITEM_INFO_PARAM_SRCFILE = "srcFile";

	public static final String ITEM_INFO_PARAM_MUNGE = "munge";
	//js文件类型
	public static final String ITEM_NAME_VALUE_JS_PKG = "js-pkg";
	//js文件类型
	public static final String TYPE_JS = "js";

	/**
	 * 解析扩展点对应的压缩的文件类型
	 * 可见常量PcCompressConfigPlugin#TYPE_*
	 * @param ext
	 * @return
	 */
	private static String getType(IExtension ext) {
		String name = ext.getAttribute("name");
		if (ITEM_NAME_VALUE_JS_PKG.equalsIgnoreCase(name)) {
			return TYPE_JS;
		}
		return null;
	}

	/**
	 * 读取压缩扩展点配置信息并封装到任务信息
	 * @return 任务信息列表
	 */
	public static List<PcCompressTaskInfo> getTaskInfos() {
		IExtensionPoint point = Plugin.getExtensionPoint(POINT_ID);
		List<PcCompressTaskInfo> taskInfos = new ArrayList<>();
		for (IExtension ext : point.getExtensions()) {
			//需要根据model路径确认指定压缩的目标文件是否超出该模块范围。
			if(!fillter(ext)){
				continue;
			};
			String type = getType(ext);
			//目前只处理压缩js,其他类型文件暂不做压缩
			if (TYPE_JS != type) {
				continue;
			}
			String name = Plugin.getParamValueString(ext, ITEM_INFO_PARAM_NAME);
			String srcFile = Plugin.getParamValueString(ext, ITEM_INFO_PARAM_SRCFILE);
			boolean munge = Plugin.getParamValue(ext, ITEM_INFO_PARAM_MUNGE);
			PcCompressTaskInfo taskInfo = new PcCompressTaskInfo();
			taskInfo.setName(name);
			taskInfo.setSrcFile(srcFile);
			taskInfo.setType(type);
			taskInfo.setMunge(munge);
			taskInfos.add(taskInfo);
		}
		
		return taskInfos;
	}

	/**
	 * 判断扩展点指定压缩的文件与model所处文件是否同个模块
	 * @param ext
	 * @return
	 */
	private static boolean fillter(IExtension ext){
		String model = ext.getModel();
		String srcFile = Plugin.getParamValueString(ext, ITEM_INFO_PARAM_SRCFILE);
		if(StringUtil.isNull(model) || StringUtil.isNull(srcFile)){
			return false;
		}
		//去掉公共包路径前缀
		model = StringUtils.removeStart(model,"com.landray.kmss.");
		String[] parts = model.split("\\.");
		String modelPath = null;
		if(parts.length > 2){
			modelPath = "/" + parts[0] + "/" + parts[1];
		}
		//若为/sys/ui模块则不做限制
		if("/sys/ui".equals(modelPath)){
			if(logger.isDebugEnabled()){
				logger.debug("【/sys/ui】为特权扩展点。不对路径进行限制。扩展点model={}, 与srcFile={}", model, srcFile);
			}
			return true;
		}
		if(StringUtils.startsWith(srcFile, modelPath)){
			if(logger.isDebugEnabled()){
				logger.debug("扩展点model={}, 与srcFile={}匹配成功！", model, srcFile);
			}
			return true;
		}
		logger.warn("扩展点model={}, 与srcFile={}所属模块不匹配！", model, srcFile);
		return  false;
	}
}

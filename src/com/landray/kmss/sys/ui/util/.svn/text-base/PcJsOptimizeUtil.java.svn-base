package com.landray.kmss.sys.ui.util;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.sys.ui.service.ISysUiCompressExecutor;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UnicodeReader;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.EvaluatorException;

import com.landray.kmss.framework.util.PluginConfigLocationsUtil;
import com.landray.kmss.sys.mobile.compressor.JSContext;
import com.yahoo.platform.yui.compressor.JavaScriptCompressor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


/**
 * 合并模块化的js
 * 
 * @author lr-linyuchao
 *
 */
public class PcJsOptimizeUtil {

	private static final Logger logger = LoggerFactory.getLogger(PcJsOptimizeUtil.class);

	/**
	 * 扩展点变量
	 * @param extension
	 * @return
	 */
	private static IExtension[] extensions;

	static{
		extensions = Plugin.getExtensions(
				ISysUiCompressExecutor.EXTENSION_POINT_ID,
				ISysUiCompressExecutor.class.getName(), "executor");
	}

	/**
	 *	通过扩展点及文件key获取指定压缩文件的路径
	 * @param extensionUnid 扩展点unid参数值
	 * @param fileNamekey 文件key值，对应压缩文件名称
	 * @return js文件src
	 */
	public static String getScriptSrcByExtension(String extensionUnid, String fileNamekey) {
		String LUI_ContextPath = getContextPath();
		String src = "";
		IExtension extension = getExtensionByUnid(extensionUnid);
		ISysUiCompressExecutor executor = null;
		try{
			executor = Plugin.getParamValue(extension, "bean");
			src = executor.execute(fileNamekey);
		} catch (Exception e){
			logger.error("扩展点ISysUiCompressExecutor实现类{}执行出错,fileNamekey{}",executor, fileNamekey, e);
		}
		return LUI_ContextPath + src;
	}
	/**
	 *	获取commonjs类型js合并压缩后的文件src
	 * @param miniPath
	 * @param jsModuleInfos js文件路径及别名信息（用于common.js注册）
	 * @param isCompress
	 * @param isMunge
	 * @return
	 */
	public static String getCommonJsScriptSrc(String miniPath, Map<String, List<String>> jsModuleInfos, Boolean isCompress, Boolean isMunge) {
		if(isCompress == null){
			isCompress = false;
		}
		if(isMunge == null){
			isMunge = false;
		}
		miniPath = formatMiniPath(miniPath);
		String webPath = PluginConfigLocationsUtil.getWebContentPath();
		String targetFile = webPath + miniPath;
		File file = new File(targetFile);
		if(file.exists()) {
			return miniPath;
		}
		//合并压缩
		mergeAndCompress(targetFile, jsModuleInfos, isCompress, isMunge);
		return miniPath;
	}

	/**
	 *	生成合并压缩文件，当目标文件已存在仍会重新合并生成。
	 * 	适用于commonjs类型
	 * 	此方法在扩展点执行时调用，用于开关打开生成预先配置好的压缩文件。
	 * @param miniPath
	 * @param jsModuleInfos js文件路径及别名信息（用于common.js注册）
	 * @param isCompress 是否压缩，默认不压缩
	 * @param isMunge 是否混淆，默认不混淆
	 * @return
	 */
	public static String genCommonJsMergeCompressResource(String miniPath, Map<String, List<String>> jsModuleInfos, Boolean isCompress, Boolean isMunge) {
		if(isCompress == null){
			isCompress = false;
		}
		if(isMunge == null){
			isMunge = false;
		}
		miniPath = formatMiniPath(miniPath);
		String webPath = PluginConfigLocationsUtil.getWebContentPath();
		String targetFile = webPath + miniPath;
		//合并压缩
		mergeAndCompress(targetFile, jsModuleInfos, isCompress, isMunge);
		return miniPath;
	}

	/**
	 * 合并压缩(commonjs类型的)
	 * @param miniPath 最终结果文件路径
	 * @param jsModuleInfos js文件路径及别名信息（用于common.js注册）
	 ** @param isCompress 是否压缩
	 */
	private static void mergeAndCompress(String miniPath, Map<String, List<String>> jsModuleInfos, boolean isCompress, boolean isMunge) {
		//压缩后文件
		File miniFile = clearInfoForFile(miniPath);
		//合并
		StringBuilder mergedSb = merge(jsModuleInfos);
		String mergedTxt = mergedSb.toString();
		if(isCompress){
			//存放一个合并后的临时文件,方便出错时可以查看问题。
			File tempFile = clearInfoForFile(StringUtils.removeEnd(miniPath, ".js") + ".tmp");
			JSContext ctx;
			try {
				FileUtils.writeStringToFile(tempFile, mergedTxt, "utf-8");
				ctx = new JSContext(new StringReader(mergedTxt),
						new BufferedWriter(new OutputStreamWriter(
								new FileOutputStream(miniFile), "UTF-8")));
				ctx.setVerbose(true);
				ctx.setFile(tempFile.getPath());
				ctx.setMunge(isMunge);
				//压缩
				compressOneJS(ctx);
				//压缩成功，删除合并临时文件
				tempFile.delete();
			} catch (Exception e) {
				logger.error("合并压缩js文件过程出错minPath{}", miniPath);
				throw new RuntimeException(e);
			}
		}else{
			try {
				FileUtils.writeStringToFile(miniFile, mergedTxt, "utf-8");
			} catch (IOException e) {
				logger.error("写入合并后的文件出错");
				throw new RuntimeException(e);
			}
		}

	}

	/**
	 * 合并js内容(用于commonjs类型的合并）</br>
	 * 主要是合并时在文件头部增加注册语句
	 * @param jsModuleInfos js文件路径及别名信息（用于common.js注册）
	 * @return
	 */
	private static StringBuilder merge(Map<String, List<String>> jsModuleInfos) {
		StringBuilder sb = new StringBuilder(1000);
		//注册语句New_Com_RegisterFile("js/xxx.js");写在文档最前
		StringBuilder regStr = new StringBuilder(100);
		Set<String> keys = jsModuleInfos.keySet();
		for(String jsPath:keys){
			List<String> regNames = jsModuleInfos.get(jsPath);
			String filePath = PluginConfigLocationsUtil.getWebContentPath() + File.separatorChar+jsPath;
			String cont = null;
			try {
				cont = read(filePath);
			} catch (Exception e) {
				logger.error("读取文件{}出错，跳过此文件", filePath, e);
				continue;
			}
			sb.append(cont).append(";").append("\n\r");
			if(CollectionUtils.isNotEmpty(regNames)){
				//增加Com_RegisterFile语句
				for(String regName:regNames){
					if(StringUtil.isNotNull(regName)){
						regStr.append("New_Com_RegisterFile(\""+regName+"\");").append("\n\r");
					}
				}
			}
		}
		regStr.append(sb);
		return regStr;
	}

	/**
	 * 生成合并压缩文件，当目标文件已存在仍会重新合并生成。
	 * 适用于seajs及普通js
	 * 此方法在扩展点执行时调用，用于开关打开生成预先配置好的压缩文件。
	 * @param miniPath 压缩文件路径
	 * @param jsModulePaths 参与压缩合并的模块js相对路径集合
	 * @param isCompress 是否压缩，默认不压缩
	 * @param isMunge 是否混淆，默认不混淆
	 * @return 合并压缩后的路径
	 */
	public static String genMergeCompressResource(String miniPath, List<String> jsModulePaths, Boolean isCompress, Boolean isMunge) {
		if(isCompress == null){
			isCompress = false;
		}
		if(isMunge == null){
			isMunge = false;
		}
		miniPath = formatMiniPath(miniPath);
		String webPath = PluginConfigLocationsUtil.getWebContentPath();
		String targetFile = webPath + miniPath;
		//合并压缩
		mergeAndCompress(targetFile, jsModulePaths, isCompress, isMunge);
		return miniPath;
	}

	/**
	 *	获取seajs及普通类型js合并压缩后的文件src
	 * @param miniPath 压缩文件路径
	 * @param jsModulePaths 参与压缩合并的模块js相对路径集合
	 * @param isCompress 是否压缩，默认不压缩
	 * @param isMunge 是否混淆，默认不混淆
	 * @return 合并压缩后的路径
	 */
	public static String getScriptSrc(String miniPath, List<String> jsModulePaths, Boolean isCompress, Boolean isMunge) {
		if(isCompress == null){
			isCompress = false;
		}
		if(isMunge == null){
			isMunge = false;
		}
		miniPath = formatMiniPath(miniPath);
		String webPath = PluginConfigLocationsUtil.getWebContentPath();
		String targetFile = webPath + miniPath;
		File file = new File(targetFile);
		if(file.exists()) {
			return miniPath;
		}
		//合并压缩
		mergeAndCompress(targetFile, jsModulePaths, isCompress, isMunge);
		return miniPath;
	}


	/**
	 *
	 * @param miniPath 压缩文件路径
	 * @param jsModulePaths 参与压缩合并的模块js相对路径集合
	 * @param isCompress 是否压缩，默认不压缩
	 * @return 合并压缩后的路径
	 * <br/>
	 * 此方法不混淆变量，需要混淆变量请
	 * @see PcJsOptimizeUtil#getScriptSrc(java.lang.String, java.util.List, java.lang.Boolean, java.lang.Boolean)
	 */
	@Deprecated
	public static String getScriptSrc(String miniPath, List<String> jsModulePaths, Boolean isCompress) {
		return getScriptSrc(miniPath, jsModulePaths, isCompress, false);
	}
	
	/**
	 * 合并压缩
	 * @param miniPath 最终结果文件路径
	 * @param jsModulePaths 参与的js文件相对路径集合
	 ** @param isCompress 是否压缩
	 */
	private static void mergeAndCompress(String miniPath, List<String> jsModulePaths, boolean isCompress, boolean isMunge) {
		//压缩后文件
		File miniFile = clearInfoForFile(miniPath);
		//合并
		StringBuilder mergedSb = merge(jsModulePaths);
		String mergedTxt = mergedSb.toString();
		if(isCompress){
			//存放一个合并后的临时文件,方便出错时可以查看问题。
			File tempFile = clearInfoForFile(StringUtils.removeEnd(miniPath, ".js") + ".tmp");
			JSContext ctx;
			try {
				FileUtils.writeStringToFile(tempFile, mergedTxt, "utf-8");
				ctx = new JSContext(new StringReader(mergedTxt),
						new BufferedWriter(new OutputStreamWriter(
								new FileOutputStream(miniFile), "UTF-8")));
				ctx.setVerbose(true);
				ctx.setFile(tempFile.getPath());
				ctx.setMunge(isMunge);
				//压缩
				compressOneJS(ctx);
				//压缩成功，删除合并临时文件
				tempFile.delete();
			} catch (Exception e) {
				logger.error("合并压缩js文件过程出错minPath{}", miniPath);
				throw new RuntimeException(e);
			}
		}else{
			try {
				FileUtils.writeStringToFile(miniFile, mergedTxt, "utf-8");
			} catch (IOException e) {
				logger.error("写入合并后的文件出错");
				throw new RuntimeException(e);
			}
		}

	}
	
	
	/**
	 * 合并js内容
	 * @param jsModulePaths 参与合并的js模块路径
	 * @return
	 */
	private static StringBuilder merge(List<String> jsModulePaths) {
		StringBuilder sb = new StringBuilder(1000);
		//将define(function(require, exports, module)..替换为 define('xxx/xxx/xxx', null, function(require, exports, module)..
		for(String jsModulePath :jsModulePaths) {
			String filePath = PluginConfigLocationsUtil.getWebContentPath() + File.separatorChar+jsModulePath;
			String cont = null;
			try {
				cont = read(filePath);
			} catch (Exception e) {
				logger.error("读取文件{}出错，跳过此文件", filePath, e);
				continue;
			}
			/**
			 * 对得到的内容进行处理;
			 * 一般处理去除.js后缀，
			 * 特殊处理如sys/ui/js 替换为 lui
			 */
			String str = genReplaceStr(jsModulePath);
			cont = cont.replaceAll("define\\(\\s*function", str);
			sb.append(cont).append(";").append("\n\r");
		}
		return sb;
	}
	
	/**
	 * 压缩Js
	 */
	public static void compressOneJS(final JSContext ctx) throws EvaluatorException, IOException {
		final Reader in = ctx.getIn();
		final Writer out = ctx.getOut();
		try {

			JavaScriptCompressor compressor = new JavaScriptCompressor(in, new ErrorReporter() {
				@Override
				public void warning(String message, String sourceName, int line, String lineSource, int lineOffset) {
					if (line < 0) {
						logger.debug(message);
					} else {
						logger.debug("" + line + ':' + lineOffset + ':' + message);
					}
				}

				@Override
				public void error(String message, String sourceName, int line, String lineSource, int lineOffset) {
					String file = "[ERROR: " + ctx.getFile() + "]";
					if (line < 0) {
						logger.error(file + ", " + message);
						throw new Error("\n[ERROR] " + message);
					} else {
						logger.error(file + ", " + "第" + line + "行出错:" + message + "\n" + lineSource);
						throw new Error("\n[ERROR] 第" + line + "行出错:" + message + "\n" + lineSource);
					}
				}

				@Override
				public EvaluatorException runtimeError(String message, String sourceName, int line, String lineSource,
													   int lineOffset) {
					error(message, sourceName, line, lineSource, lineOffset);
					return new EvaluatorException(message);
				}
			});

			boolean munge = ctx.isMunge();
			boolean preserveAllSemiColons = ctx.isPreserveAllSemiColons();
			boolean disableOptimizations = ctx.isDisableOptimizations();
			boolean verbose = ctx.isVerbose();// 关闭详细信息（因为可能有大量警告信息）
			int linebreakpos = ctx.getLinebreakpos();// 无断行

			compressor.compress(out, linebreakpos, munge, verbose, preserveAllSemiColons, disableOptimizations);

		} catch (Throwable t) {
			logger.error("JS资源压缩错误", t);
			throw new RuntimeException(t);
		} finally {
			IOUtils.closeQuietly(in);
			IOUtils.closeQuietly(out);
		}
	}
	
	/**
	 * 根据相对路径生成替换字符串
	 * @param jsModulePath 模块文件相对路径
	 * @return
	 */
	private static String genReplaceStr(String jsModulePath) {
		String str = "\'"+jsModulePath.substring(0, jsModulePath.length()-3) + "\', "
				+ "null, ";
		return "define\\("+str+"function";
	}

	/**
	 * 根据路径读取文件文本内容
	 * @param path
	 * @return
	 */
	private static String read(String path) throws Exception{
		FileInputStream fis = null;
		StringBuilder sb = new StringBuilder(1000);
		try {
			File file = new File(path);
			fis = new FileInputStream(file);
			sb = new StringBuilder(1000);
			sb.append(IOUtils.toString(new UnicodeReader(fis, "utf-8")));
			String txt = sb.toString();
		} catch (Exception e){
			logger.error("读取文件出错{}", path ,e);
			throw new RuntimeException(e);
		} finally {
			IOUtils.closeQuietly(fis);
		}
		//以文本形式读取原文件
		return sb.toString();
	}

	/**
	 * 创建空文件或清空文件内容 文件路径不存在会创建路径
	 * 
	 * @param fileName
	 */
	private static File clearInfoForFile(String fileName) {
		FileWriter fileWriter = null;
		File file = new File(fileName);
		try {
			if (!file.exists()) {
				file.getParentFile().mkdirs();
				file.createNewFile();
			}
			fileWriter = new FileWriter(file);
			fileWriter.write("");
			fileWriter.flush();
			return file;
		} catch (IOException e) {
			//创建空文档或清空文件内容出错了
			logger.error("创建空文档或清空文件内容出错了,filePath=" + file.getPath());
		} finally {
			IOUtils.closeQuietly(fileWriter);
		}
		return null;
	}

	/**
	 * 将miniPath 处理成 /resource/dynamic_combination/...格式<br/>
	 * 如xx/yy/zz.js 会处理为 /resource/dynamic_combination/xx/yy/zz.js
	 * @param miniPath
	 */
	private static String formatMiniPath(String miniPath) {
		if(StringUtil.isNull(miniPath)){
			if(logger.isDebugEnabled()){
				logger.debug("miniPath为空");
			}
			return "";
		}
		if(!miniPath.startsWith("/")){
			miniPath = "/" + miniPath;
		}
		//统一存放在/resource/dynamic_combination目录下
		if(miniPath.indexOf(ISysUiCompressExecutor.COM_COMPRESS_SOURCE_PATH) == -1){
			miniPath = ISysUiCompressExecutor.COM_COMPRESS_SOURCE_PATH + miniPath;
		}
		return miniPath;
	}

	/**
	 *
	 * 根据扩展点unid参数值获取对应扩展点<br/>
	 * 仅限point-id=com.landray.kmss.sys.ui.compressExecutor的扩展点
	 * @param extensionUnid
	 * @return
	 */
	private static IExtension getExtensionByUnid(String extensionUnid) {
		if(extensions != null){
			for (IExtension extension : extensions) {
				String unid = Plugin.getParamValueString(
						extension, "unid");
				if (extensionUnid.equals(unid)) {
					return extension;
				}
			}
		}
		return null;
	}

	private static String getContextPath (){
		String LUI_ContextPath = "";
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		if(request != null){
			LUI_ContextPath = request.getContextPath();
		}
		return LUI_ContextPath;
	}
}

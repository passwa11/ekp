package com.landray.kmss.sys.ui.util;

import com.landray.kmss.framework.util.PluginConfigLocationsUtil;
import com.landray.kmss.sys.mobile.compressor.JSContext;
import com.landray.kmss.sys.ui.service.ISysUiCompressExecutor;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UnicodeReader;
import com.yahoo.platform.yui.compressor.JavaScriptCompressor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.EvaluatorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;


/**
 * 合并模块化的js
 * 
 * @author lr-linyuchao
 *
 */
public class PCJsCompressUtil {

	private static final Logger logger = LoggerFactory.getLogger(PCJsCompressUtil.class);
	
	private static String[] lui_prefix = { "lui/", "sys/ui/js/" };//特殊映射
	
	/**
	 * 
	 * @param miniPath 压缩文件路径
	 * @param jsModulePaths 参与压缩合并的模块js相对路径集合
	 * @param isCompress 是否压缩，默认不压缩
	 * @return 合并压缩后的路径
	 */
	public static String getScriptSrc(String miniPath, List<String> jsModulePaths, Boolean isCompress) {
		if(isCompress == null){
			isCompress = false;
		}
		miniPath = formatMiniPath(miniPath);
		String webPath = PluginConfigLocationsUtil.getWebContentPath();
		String targetFile = webPath + miniPath;
		File file = new File(targetFile);
		if(file.exists()) {
			return miniPath;
		}
		//合并压缩
		mergeAndCompress(targetFile, jsModulePaths, isCompress);
		return miniPath;
	}
	
	/**
	 * 合并压缩
	 * @param miniPath 最终结果文件路径
	 * @param jsModulePaths 参与的js文件相对路径集合
	 ** @param isCompress 是否压缩
	 */
	private static void mergeAndCompress(String miniPath, List<String> jsModulePaths, boolean isCompress) {
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
				ctx.setMunge(false);
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
			cont = cont.replaceAll("define\\(function", str);
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
		String str = "";
		if(jsModulePath.startsWith(lui_prefix[1])) {
			// 'lui/xxx', null,  
			str = "\'"+jsModulePath.substring(0, jsModulePath.length()-3).replace(lui_prefix[1], lui_prefix[0]) + "\', "
					+ "null, ";
		}else {
			str = "\'"+jsModulePath.substring(0, jsModulePath.length()-3) + "\', "
					+ "null, ";
		}
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
}

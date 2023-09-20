
package com.landray.kmss.sys.ui.compressor;

import com.landray.kmss.sys.config.loader.ConfigLocationsUtil;
import com.landray.kmss.sys.mobile.compressor.JSContext;
import com.landray.kmss.sys.ui.compressor.config.PcCompressTaskInfo;
import com.landray.kmss.sys.ui.util.PcJsOptimizeUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UnicodeReader;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Arrays;
import java.util.Collection;

/**
 * PC端JS压缩任务执行体
 * @author 林宇超
 * 
 */
public class JsPcCompressTask implements PcCompressTask {

	private final static Logger logger = LoggerFactory.getLogger(JsPcCompressTask.class);
	//文件类型
	private final String type = "js";
	//压缩js文件名后缀及类型
	private final String s_min_js = "_min_.js";
	//压缩js文件名后缀及类型
	private final String s_src_js = "_src_.js";

	private final PcCompressTaskInfo taskInfo;

	public JsPcCompressTask(PcCompressTaskInfo taskInfo) {
		super();
		this.taskInfo = taskInfo;
	}

	//文件过滤器：过滤非压缩的js文件，获取要压缩的文件列表
	private final IOFileFilter fileFilter = FileFilterUtils.asFileFilter(new FilenameFilter() {
		@Override
		public boolean accept(File dir, String name) {
			return name.endsWith(".js") && !name.endsWith("_min_.js") && !name.endsWith("_src_.js");
		}
	});

	private final IOFileFilter dirFilter = FileFilterUtils.trueFileFilter();

	@Override
	public void run() {
		//这个目录下的.js文件进行压缩放在同级目录的_min_.js
		String srcFilePath = taskInfo.getSrcFile();
		if(logger.isDebugEnabled()){
			logger.debug("执行JS线程，threadName:{}; file{}", Thread.currentThread().getName(), srcFilePath);
		}
		if(StringUtil.isNotNull(srcFilePath)){
			// 初始化location
			File location = new File(ConfigLocationsUtil.getWebContentPath());
			File targetFile = new File(location, srcFilePath);
			compressFile(targetFile);
		}

	}

	private void compressFile(File targetFile) {
		if(logger.isDebugEnabled()){
			logger.debug("执行目录范围压缩,filePath:{}", targetFile.getAbsolutePath());
		}
		//待压缩文件列表
		Collection<File> files = null;

		if(targetFile.isDirectory()){
			//指定压缩目标为目录范围 递归扫描文件夹
			files = FileUtils.listFiles(targetFile, fileFilter, dirFilter);
		} else if (targetFile.isFile()){
			//指定压缩目标为具体文件
			files = Arrays.asList(targetFile);
		} else {
			logger.warn("目标文件:{}既不是目录也不是文件", targetFile);
			return;
		}
		//是否混淆标志
		boolean isMunge = taskInfo.isMunge();
		for (File file : files) {
			FileInputStream fis = null;
			Throwable t = null;
			File srcFile = null;//原文件副本
			File miniFile = null;//原文件压缩版
			try {
				//原文件路径
				String filePath = file.getAbsolutePath();
				//用于存放原文件文本内容
				StringBuilder sb = new StringBuilder(1000);
				//压缩后文件的路径 ，规则：在源文件同级目录追加 “_min_”命名
				String miniFilePath = filePath.substring(0, filePath.length() - type.length() - 1) + s_min_js;
				//原文件副本文件
				String srcFilePath = filePath.substring(0, filePath.length() - type.length() - 1) + s_src_js;
				srcFile = new File(srcFilePath);

				fis = new FileInputStream(file);
				sb.append(IOUtils.toString(new UnicodeReader(fis, "utf-8")));
				//以文本形式读取原文件
				String txt = sb.toString();
				if(!srcFile.exists()){
					//拷贝原文件
					FileUtils.writeStringToFile(srcFile, txt, "utf-8");
				}
				miniFile = new File(miniFilePath);
				if(miniFile.exists()){
					if(logger.isDebugEnabled()){
						logger.debug("压缩文件已存在，跳过文件：{}",miniFile.getAbsolutePath());
					}
					continue;
				} else {
					miniFile.createNewFile();
					if(logger.isDebugEnabled()){
						logger.debug("生成新的压缩文件：{}",miniFile.getAbsolutePath());
					}
				}
				JSContext ctx = new JSContext(new StringReader(txt),
						new BufferedWriter(new OutputStreamWriter(new FileOutputStream(miniFile),
								"UTF-8")));
				ctx.setVerbose(true);
				ctx.setMunge(isMunge);
				//压缩
				PcJsOptimizeUtil.compressOneJS(ctx);
			} catch (Exception e) {
				t = e;
				logger.error("执行压缩备份文件:{}过程出错",file.getAbsolutePath(),e);
				//throw new RuntimeException(e);
			} finally {
				IOUtils.closeQuietly(fis);
				//有错误，删除当前文件两个版本文件
				if(t != null){
					if(srcFile != null && srcFile.exists()){
						if(logger.isDebugEnabled()){
							logger.debug("删除文件副本{}", srcFile.getAbsolutePath());
						}
						srcFile.delete();
					}
					if(miniFile != null && miniFile.exists()){
						if(logger.isDebugEnabled()){
							logger.debug("删除文件副本{}", miniFile.getAbsolutePath());
						}
						miniFile.delete();
					}
				}
			}
		}
	}


}

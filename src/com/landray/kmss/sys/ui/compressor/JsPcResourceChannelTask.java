
package com.landray.kmss.sys.ui.compressor;

import com.landray.kmss.sys.config.loader.ConfigLocationsUtil;
import com.landray.kmss.sys.ui.compressor.config.PcCompressTaskInfo;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UnicodeReader;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.Collection;

/**
 * JS文件切换资源（使用压缩还是非压缩）任务
 * @author 林宇超
 * 
 */
public class JsPcResourceChannelTask implements PcResourceChannelTask {

	private final static Logger logger = LoggerFactory.getLogger(JsPcResourceChannelTask.class);
	//文件类型
	private final String type = "js";
	//压缩js文件名后缀及类型
	private final String _min_js = "_min_.js";
	//原文件副本js文件名后缀及类型
	private final String _src_js = "_src_.js";

	private final PcCompressTaskInfo taskInfo;
	//是否使用压缩文件标志
	private boolean isUseCompress;

	public JsPcResourceChannelTask(PcCompressTaskInfo taskInfo) {
		super();
		this.taskInfo = taskInfo;
	}

	//文件过滤器：过滤非压缩的js文件
	private final IOFileFilter fileFilter = FileFilterUtils.asFileFilter(new FilenameFilter() {
		@Override
		public boolean accept(File dir, String name) {
			return name.endsWith(".js") && !name.endsWith("_min_.js") && !name.endsWith("_src_.js");
		}
	});

	private final IOFileFilter dirFilter = FileFilterUtils.trueFileFilter();

	@Override
	public void run() {
		//这个目录下的.js文件不出意外会有一个同名后缀_min_.js和_src_.js文件存在
		String targetFilePath = taskInfo.getSrcFile();
		if(StringUtil.isNotNull(targetFilePath)){
			// 初始化location
			File location = new File(ConfigLocationsUtil.getWebContentPath());
			File targetFile = new File(location, targetFilePath);
			channelFileCont(targetFile);
		}

	}

	/**
	 * 切换目标文件的内容
	 * @param targetFile 被切换的目标文件或目录
	 */
	private void channelFileCont(File targetFile) {
		//待切换文件列表
		Collection<File> files = null;
		if(targetFile.isDirectory()){
			//指定切换目标为目录范围 递归扫描文件夹
			files = FileUtils.listFiles(targetFile, fileFilter, dirFilter);
		} else if (targetFile.isFile()){
			//指定切换目标为具体文件
			files = Arrays.asList(targetFile);
		} else {
			logger.warn("目标文件:{}既不是目录也不是文件", targetFile);
			return;
		}

		for (File file : files) {
			try {
				//进行文件内容替换
				String filePath = file.getAbsolutePath();
				if(isUseCompress){
					//压缩后文件的路径 ，规则：在原文件同级目录追加 “_min_”命名
					String miniFilePath = filePath.substring(0, filePath.length() - type.length() - 1) + _min_js;
					File miniFile = new File(miniFilePath);
					//替换为压缩文件的内容
					replaceFileContent(miniFile ,file);
				}else{
					//原文件副本文件
					String srcFilePath = filePath.substring(0, filePath.length() - type.length() - 1) + _src_js;
					File srcFile = new File(srcFilePath);
					//替换为原文件的内容
					replaceFileContent(srcFile, file);
				}
			} catch (Exception e){
				logger.error("执行文件替换过程出错，对应文件{}", file.getAbsolutePath(), e);
			} finally {

			}
		}
	}

	/**
	 * 用一个文件的内容替换另一个文件的内容
	 * @param sourceFile 源文件（输出文本的文件）
	 * @param targetFile 目标文件（被输入文本的文件）
	 * @throws Exception
	 */
	private void replaceFileContent(File sourceFile, File targetFile) throws Exception{
		FileInputStream fis = null;
		try{
			//先判断源文件存不存在
			if(sourceFile.exists()){
				fis = new FileInputStream(sourceFile);
				StringBuilder sb = new StringBuilder(1000);
				sb.append(IOUtils.toString(new UnicodeReader(fis, "utf-8")));
				//以文本形式读取源文件
				String txt = sb.toString();
				if(targetFile.exists()){
					//将内容替换到目标文件
					if(logger.isDebugEnabled()){
						logger.debug("替换文件内容，{}", sourceFile.getAbsolutePath());
					}
					FileUtils.writeStringToFile(targetFile, txt, "utf-8");
					if(logger.isDebugEnabled()){
						logger.debug("替换结束，{}", sourceFile.getAbsolutePath());
					}
				}
			} else {
				if(logger.isDebugEnabled()){
					logger.debug("源文件不存在，不执行替换，{}", sourceFile.getAbsolutePath());
				}
			}
		}catch (Exception e){
			logger.error("替换静态资源文件内容出错{}", targetFile.getAbsolutePath(), e);
			throw e;
		}finally {
			IOUtils.closeQuietly(fis);
		}
	}


	@Override
	public PcResourceChannelTask isUseCompress(boolean isUseCompress) {
		this.isUseCompress = isUseCompress;
		return this;
	}


}

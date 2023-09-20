package com.landray.kmss.code.upgrade.v15.springmvc.trans;

import static com.landray.kmss.code.upgrade.v15.springmvc.trans.Runner.SCANPATHS;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.code.upgrade.v15.springmvc.trans.FileTranser.Result;
import com.landray.kmss.code.upgrade.utils.ProcessBar;

/**
 * struts到springmvc的转换程序（总流程）
 */
public class SrpingMvcTrans {
	
	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
	
	private List<String> NOTSCANPATH = Arrays
			.asList(new String[] { "WebContent/WEB-INF/classes",
					"src/com/landray/kmss/code" });

	private FileTranser[] transers = {
			new XmlFileTranser(),
			new ValidationTranser(),
			new ReplaceFileTranser(),
			new DependChecker() };

	private ProcessBar process = new ProcessBar();

	private void scan(File dir, String path, boolean doFix) throws Exception {
		if (!(dir.exists() && dir.isDirectory())) {
			return;
		}
		File[] files = dir.listFiles();
		if (files == null) {
			return;
		}
		for (File file : files) {
			String fileName = file.getName();
			if (fileName.startsWith(".")) {
				continue;
			}
			String filePath = path + "/" + fileName;
			if (NOTSCANPATH.contains(filePath)) {
				continue;
			}
			if (file.isDirectory()) {
				scan(file, filePath, doFix);
			} else {
				if (doFix) {
					if (fix(filePath, false)) {
						process.doJob(1);
					}
				} else {
					process.addJob(1);
				}
			}
		}
	}

	private List<String> delayPaths = new ArrayList<String>();
	private void delayFix() throws Exception {
		for (String path : delayPaths) {
			fix(path, true);
			process.doJob(1);
		}
	}

	private boolean fix(String path, boolean delay) throws Exception {
		context.next(path, delay);
		for (FileTranser transer : transers) {
			Result result = transer.execute(context);
			if (result == Result.Delay) {
				if (delay) {
					throw new Exception();
				}
				delayPaths.add(path);
				return false;
			}
			if (result == Result.Break) {
				return true;
			}
		}
		context.saveFile();
		return true;
	}

	private TransContext context;

	public SrpingMvcTrans(TransContext context) {
		super();
		this.context = context;
	}

	public void start() throws Exception {
		// 扫描文件个数
		for (int i = 0; i < SCANPATHS.length; i++) {
			scan(new File(SCANPATHS[i]), SCANPATHS[i], false);
		}
		// 执行转换
		logger.info("发现" + process.getJob() + "个文件需要处理，进度如下：");
		process.start();
		for (int i = 0; i < SCANPATHS.length; i++) {
			scan(new File(SCANPATHS[i]), SCANPATHS[i], true);
		}
		delayFix();
	}
}

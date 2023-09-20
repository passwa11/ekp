package com.landray.kmss.code;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.landray.kmss.code.util.ProcessBar;

import edu.emory.mathcs.backport.java.util.Collections;

/**
 * JSP的编译检查工具，执行时间会比较长
 * 
 * 使用说明： 本地启动Tomcat，修改serverPath，运行本程序
 */
public class JspCompile {
	
	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
	
	// 服务器地址
	private String serverPath = "http://localhost:8081/ekp";
	// 报告文件地址
	private String reportFile = "jspError.txt";
	// 线程数，太大会卡机
	private int THREAD = 50;

	private Queue<String> queue = new ConcurrentLinkedQueue<String>();
	private String TEMPFILE = "/resource/__JspCompile";
	private List<String> NOSCANPATHS = Arrays.asList(
			new String[] { "/WEB-INF",
					"/META-INF",
					"/sys/ui/demo",
					"/sys/common/code.jsp",
					"/resource/jsp/tree_down.jsp" });

	private void scan(File dir, String path) throws Exception {
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
			if (NOSCANPATHS.contains(filePath)
					|| filePath.startsWith(TEMPFILE)) {
				continue;
			}
			if (file.isDirectory()) {
				scan(file, filePath);
			} else if (fileName.endsWith(".jsp")) {
				queue.offer(filePath);
			}
		}
	}

	private ProcessBar process = new ProcessBar();
	private List<String> reportList = new ArrayList<String>();
	private List<String> errorList = new ArrayList<String>();

	private synchronized void refreshProcess(String filePath, boolean success)
			throws IOException {
		if (!success) {
			errorList.add(filePath);
		}
		process.doJob(1);
		if (process.isFinish()) {
			StringBuffer newFiles = new StringBuffer();
			StringBuffer oldFiles = new StringBuffer();
			int newCount = 0;
			int oldCount = 0;
			for (String err : errorList) {
				if (reportList.contains(err)) {
					oldFiles.append("\r\n").append(err);
					oldCount++;
				} else {
					newFiles.append("\r\n").append(err);
					newCount++;
				}
			}
			Collections.sort(errorList);
			FileUtils.writeLines(new File(reportFile), "UTF-8", errorList,
					"\r\n");
			StringBuffer message = new StringBuffer(
					"共发现：" + errorList.size() + "个Jsp文件编译错误");
			if (newCount > 0) {
				message.append("，新增：").append(newCount);
			}
			if (oldCount > 0) {
				message.append("，新增：").append(newCount);
			}

			logger.warn("共发现：" + errorList.size() + "个Jsp文件编译错误。");
			if (newCount > 0) {
				logger.warn("对比上次报告，新增了" + newCount + "个错误：" + newFiles);
			}
			if (oldCount > 0) {
				logger.info("对比上次报告，" + oldCount + "个错误是原来已有：" + oldFiles);
			}
			if (oldCount < reportList.size()) {
				logger.info("对比上次报告，解决了" + (reportList.size() - oldCount) + "个错误。");
			}
		}
	}

	public void doStart(boolean again) throws Exception {
		logger.info("正在扫描需要处理的文件，请稍等...");
		readReport();
		if (again) {
			queue.addAll(errorList);
		} else {
			scan(new File("WebContent"), "");
		}
		process.addJob(queue.size());
		long dt = Math.round(4.3 * queue.size() / THREAD / 60);
		String message = "发现" + queue.size() + "个文件需要处理，预计执行时间";
		if (dt == 0) {
			message += "小于1分钟，处理进度如下：";
		} else {
			message += dt + "分钟，处理进度如下：";
		}
		logger.info(message);
		process.start();
		for (int i = 0; i < THREAD; i++) {
			new Thread(new JspCompileJob(i)).start();
		}
	}

	@SuppressWarnings("unchecked")
	private void readReport() throws IOException {
		File file = new File(reportFile);
		if (file.exists()) {
			reportList = FileUtils.readLines(file, "UTF-8");
		}
	}

	private class JspCompileJob implements Runnable {
		private String tempFilePath;

		public JspCompileJob(int index) {
			super();
			this.tempFilePath = TEMPFILE + "_" + index + ".jsp";
		}

		@Override
		public void run() {
			try {
				boolean success = false;
				while (true) {
					String filePath = queue.poll();
					if (filePath == null) {
						break;
					}
					// 引入common.jsp测试
					if (!success) {
						reset();
					}
					success = execute(filePath, true);

					if (!success) {
						// 失败，尝试不引入common.jsp重试一次
						reset();
						success = execute(filePath, false);
					}
					refreshProcess(filePath, success);
				}
				new File("WebContent" + tempFilePath).delete();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		// 重置JSP
		private void reset() throws Exception {
			FileUtils.writeStringToFile(new File("WebContent" + tempFilePath),
					token, "UTF-8");
			for (int i = 0; i < 1000; i++) {
				String result = httpGet();
				if (result != null && token.equals(result.trim())) {
					return;
				}
				Thread.sleep(200);
			}
			throw new Exception("jsp reset error!");
		}

		private String token = JspCompile.class.getName() + " token";

		private boolean execute(String path, boolean includeCommon)
				throws Exception {
			// 写临时的JSP文件
			String content = includeCommon
					? "<%@ include file=\"/resource/jsp/common.jsp\" %>" : "";
			content += "<%@ page import=\"com.landray.kmss.util.*\" %><% if(false){ %><%@ include file=\""
					+ path + "\"%><% }else{out.write(\"" + path + token
					+ "\");} %>";
			FileUtils.writeStringToFile(new File("WebContent" + tempFilePath),
					content, "UTF-8");
			// 重试1000次（200秒）
			for (int i = 0; i < 1000; i++) {
				String result = httpGet();
				if (result != null) {
					result = result.trim();
					if (result.equals(path + token)) {
						// jsp返回了预期值，编译通过
						return true;
					}
					if (!result.endsWith(token)) {
						// jsp返回了未知页面，编译失败
						return false;
					}
				}
				Thread.sleep(200);
			}
			return false;
		}

		private long seq = 0;
		private HttpClient client = HttpClientBuilder.create().build();

		private String httpGet() throws Exception {
			seq++;
			HttpGet request = new HttpGet(
					serverPath + tempFilePath + "?seq=" + seq);
			HttpResponse response = client.execute(request);
			return EntityUtils.toString(response.getEntity(), "UTF-8");
		}
	}

	public void start() throws Exception {
		doStart(false);
	}

	public void tryAgain() throws Exception {
		doStart(true);
	}

	public static void main(String[] args) throws Exception {
		// 开始检测所有jsp
		new JspCompile().start();
		// 重新检测上次的错误结果
		// new JspCompile().tryAgain();
	}
}

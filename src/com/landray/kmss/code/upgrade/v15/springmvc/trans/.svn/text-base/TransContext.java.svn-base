package com.landray.kmss.code.upgrade.v15.springmvc.trans;

import static com.landray.kmss.code.upgrade.v15.springmvc.trans.Runner.REPORTFILE;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 转换上下文
 */
public class TransContext {
	
	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
	
	// =================== 当前文件操作 ================
	private File file;

	private String path;

	private String content;

	private int status = 0;

	private boolean delay;

	public boolean isDelay() {
		return delay;
	}

	/** 下一个文件 */
	public void next(String path, boolean delay) {
		this.file = new File(path);
		this.path = path;
		this.content = null;
		this.status = 0;
		this.delay = delay;
	}

	public String getPath() {
		return path;
	}

	public String getContent() throws IOException {
		if (status == 0) {
			if (file.exists()) {
				content = FileUtils.readFileToString(file, "UTF-8");
			}
			status = 1;
		}
		return content;
	}

	public void setContent(String content) throws IOException {
		getContent();
		if (this.content == content) {
			return;
		}
		if (this.content != null && this.content.equals(content)) {
			return;
		}
		this.content = content;
		this.status = 2;
	}

	public void saveFile() throws IOException {
		if (status == 2 && content != null) {
			FileUtils.writeStringToFile(file, content, "UTF-8");
			saveCount++;
			status = 1;
		}
	}

	public void deleteFile(String path) {
		File file = new File(path);
		if (file.exists()) {
			if (file.delete()) {
				deleteCount++;
			} else {
				addError("以下文件由于被占用无法删除，请手工删除", path);
			}
		}
	}

	public void addFile(String path, String content) throws IOException {
		File file = new File(path);
		if (file.exists()) {
			String _content = FileUtils.readFileToString(file, "UTF-8");
			if (!content.equals(_content)) {
				FileUtils.writeStringToFile(file, content, "UTF-8");
				saveCount++;
			}
		} else {
			FileUtils.writeStringToFile(file, content, "UTF-8");
			addCount++;
		}
	}

	public void copyFile(File source, File target) throws IOException {
		FileUtils.copyFile(source, target);
		saveCount++;
	}

	// =================== 统计数据 ================
	private int saveCount = 0;
	private int deleteCount = 0;
	private int addCount = 0;
	private Map<String, List<String>> errors = new HashMap<String, List<String>>();

	public void addError(String group, String message) {
		List<String> messages = errors.get(group);
		if (messages == null) {
			messages = new ArrayList<String>();
			errors.put(group, messages);
		}
		if (!messages.contains(message)) {
			messages.add(message);
		}
	}

	public boolean hasError() {
		return !errors.isEmpty();
	}

	public void report() throws IOException {
		FileOutputStream output = FileUtils
				.openOutputStream(new File(REPORTFILE));
		try {
			StringBuffer message = new StringBuffer();
			message.append("成功修订了").append(saveCount).append("个文件");
			if (addCount > 0) {
				message.append("，追加了").append(addCount).append("个文件");
			}
			if (deleteCount > 0) {
				message.append("，删除了").append(deleteCount).append("个文件");
			}
			if (!errors.isEmpty()) {
				message.append("，迁移过程中发现了错误需要手工修订，详情请查阅：").append(REPORTFILE);
				List<Entry<String, List<String>>> entries = new ArrayList<Entry<String, List<String>>>(
						errors.entrySet());
				Collections.sort(entries,
						new Comparator<Entry<String, List<String>>>() {
							@Override
							public int compare(Entry<String, List<String>> o1,
									Entry<String, List<String>> o2) {
								return o1.getKey().compareTo(o2.getKey());
							}
						});
				for (Entry<String, List<String>> entry : entries) {
					IOUtils.write(entry.getKey(), output, "UTF-8");
					for (String value : entry.getValue()) {
						IOUtils.write("\r\n\t" + value, output, "UTF-8");
					}
					IOUtils.write("\r\n\r\n", output, "UTF-8");
				}
			}
			logger.info(message.toString());
		} finally {
			IOUtils.closeQuietly(output);
		}
	}
}

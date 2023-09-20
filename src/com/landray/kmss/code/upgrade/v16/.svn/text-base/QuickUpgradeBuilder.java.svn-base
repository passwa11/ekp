package com.landray.kmss.code.upgrade.v16;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.landray.kmss.code.upgrade.v16.QuickUpgradeBuilder;

public class QuickUpgradeBuilder {

	private static Map<String, String> replaceContentMap = new HashMap<String, String>();
	private static Map<String, String> excludeMap = new HashMap<String, String>();
	private static Map<String, String> suggestiveMap = new HashMap<String, String>();

	private static String upgradeFileName = "upgrade.txt";
	private static String excludeFileName = "exclude.txt";

	private static String upgradeLog = "v16_upgrade.log";
	private static String upgradeDetailLog = "v16_upgrade_detail_auto.log";
	private static String manualUpgradeLog = "v16_upgrade_detail_manual.log";
	private static List<String> upgradedAutoList = new ArrayList<String>();
	private static List<String> upgradedManualList = new ArrayList<String>();

	private static String projectPath = System.getProperty("user.dir");
	private static String upgradeFilePath = projectPath + "/v16_upgrade";
	private static File upgradeLogFile = new File(upgradeFilePath + "/" + upgradeLog);
	private static File upgradeDetailLogFile = new File(upgradeFilePath + "/" + upgradeDetailLog);
	private static File manualUpgradeLogFile = new File(upgradeFilePath + "/" + manualUpgradeLog);
	private static File upgradeSpecifiedFile;

	private static FileWriter upgradeLogWritter;
	private static FileWriter upgradeLogDetailWritter;
	private static FileWriter manualUpgradeLogFileWritter;

	private static int fileNumber = 0;
	private static int upgradeNumber = 0;
	private static int javaUpgradeNumber = 0;
	private static int jspUpgradeNumber = 0;
	private static int springUpgradeNumber = 0;
	private static int hibernateUpgradeNumber = 0;
	private static int hbmUpgradeNumber = 0;
	private static boolean backUpFile = false;
	private static boolean recoverFile = false;
	private static ExecutorService executor = Executors.newFixedThreadPool(5);

	public QuickUpgradeBuilder(File replaceFileContent, File excludeFile) {
		try {
			if (replaceFileContent.exists()) {
				replaceContentMap.clear();
				FileReader in = new FileReader(replaceFileContent);
				BufferedReader bufIn = new BufferedReader(in);

				String line = null;
				while ((line = bufIn.readLine()) != null) {
					if (!line.startsWith("#")) {
						String[] texts = line.split("\\|");
						if (texts.length == 2) {
							replaceContentMap.put(texts[0], texts[1]);
						}
					}
				}
				bufIn.close();
			}

			if (null != excludeFile && excludeFile.exists()) {
				excludeMap.clear();
				FileReader in = new FileReader(excludeFile);
				BufferedReader bufIn = new BufferedReader(in);

				String line = null;
				while ((line = bufIn.readLine()) != null) {
					if (!"".equals(line)) {
						excludeMap.put(line, line);
					}
				}
				bufIn.close();

			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("resource")
	private void replaceContent(String path, String suffix) throws IOException, PatternSyntaxException {
		//if (path.contains("TibJdbcFormTemplateSQLSelectDataBean.java")) {
		//printMsg("升級文件：" + path);
		//}
		File upgradeFile = new File(path);
		File backupFile = new File(upgradeFile.getAbsoluteFile() + ".backup");
		FileInputStream fis = null;
		boolean exclude = false;

		//如果备份文件存在，表示之前做过替换，所以取备份文件再次扫描替换，避免同样的文件内容重复替换，造错误
		if (backupFile.exists() && null != upgradeLogWritter) {
			fis = new FileInputStream(backupFile);
		} else {
			fis = new FileInputStream(path);
		}

		InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
		BufferedReader br = new BufferedReader(isr);

		CharArrayWriter tempStream = new CharArrayWriter();
		String line = null;
		List<String> lineList = new Vector<String>();
		while ((line = br.readLine()) != null) {
			lineList.add(line);
		}

		//排除文件
		for (Map.Entry<String, String> entry : excludeMap.entrySet()) {
			String key = entry.getKey();
			if (!key.startsWith("package")) {
				String filePath = upgradeFile.getCanonicalPath();
				filePath = filePath.replaceAll("\\\\", "/");
				key = projectPath + "/" + key;
				key = key.replaceAll("\\\\", "/");
				if (filePath.equals(key)) {
					exclude = true;
					return;
				}
			}
		}

		Boolean isReplace = false;
		Map<String, String> sugMap = new LinkedHashMap<String, String>();
		List<String> replacedList = new ArrayList<String>();
		StringBuilder mergeLines = null;

		for (int i = 0; i < lineList.size(); i++) {
			line = lineList.get(i);
			int lineNumber = i + 1;

			// java 文件替换
			if ("java".equals(suffix)) {
				if (i == 0) {
					String firstLine = null;
					for (int z = 0; z < lineList.size(); z++) {
						firstLine = lineList.get(z).trim();
						if (firstLine.startsWith("package")) {
							break;
						}
					}
					//特殊文件不做升级，已经在对比工具对比合并了
					for (Map.Entry<String, String> entry : excludeMap.entrySet()) {
						String packageName = firstLine.substring(0, firstLine.length() - 1);
						// 排除src 包
						String key = entry.getKey();
						if (key.startsWith("package")) {
							boolean result = Pattern.compile(key).matcher(packageName).find();
							if (firstLine.length() > 0 && result) {
								exclude = true;
								break;
							}
						}
					}
				}

				if (exclude) {
					break;
				}

				boolean b = javaFileReplace(mergeLines, lineList, lineNumber, sugMap, replacedList, upgradeFile, suffix,
						line, tempStream);

				if (b) {
					isReplace = b;
				}
			} else {
				// jsp,xml,hbm.xml 文件类做替换
				boolean b = otherFileReplace(lineNumber, sugMap, replacedList, upgradeFile, suffix, line, tempStream);
				if (b) {
					isReplace = b;
				}
			}

			tempStream.append(System.getProperty("line.separator"));
		}

		br.close();
		isr.close();
		fis.close();

		if (sugMap.size() > 0 && null != manualUpgradeLogFileWritter) {
			manualUpgradeLogFileWritter.write("--\r");
			manualUpgradeLogFileWritter.write(path + "\r");
			sugMap.forEach((key, value) -> {
				try {
					manualUpgradeLogFileWritter.write(key + "," + value + "\r");
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			manualUpgradeLogFileWritter.write("\r");
		}

		String newFilePath = path.replaceAll("\\\\", "/");
		if (isReplace) {
			//printMsg("升級文件：" + upgradeFile.getAbsolutePath());

			// 生成备份文件
			if (backUpFile) {
				upgradeFilePath = projectPath;
				if (backupFile.exists()) {
					//printMsg("备份文件已经存在：" + backupFile.getAbsolutePath());
				} else {
					copyFile(upgradeFile, backupFile);
				}
				upgradeFile.delete();

			} else {
				if ("java".equals(suffix)) {
					newFilePath = upgradeFilePath + "/src" + substringAfterLast(newFilePath, "/src");
				}

				if (upgradeFile.getName().contains("hbm.xml")) {
					newFilePath = upgradeFilePath + "/src" + substringAfterLast(newFilePath, "/src");
				}

				if (!upgradeFile.getName().contains("hbm.xml") && ("xml".equals(suffix) || "jsp".equals(suffix))) {
					newFilePath = upgradeFilePath + "/WebContent" + substringAfterLast(newFilePath, "/WebContent");
				}
			}

			File newFile = new File(newFilePath);

			// 不做备份情况下
			if (!newFile.exists()) {
				newFile.getParentFile().mkdirs();
				newFile.createNewFile();
			}

			FileOutputStream fos = new FileOutputStream(newFile);
			OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
			tempStream.writeTo(osw);
			osw.close();
			fos.close();
			upgradeNumber++;
			if ("java".equals(suffix)) {
				javaUpgradeNumber++;
			} else if ("jsp".equals(suffix)) {
				jspUpgradeNumber++;
			} else if ("spring.xml".equals(upgradeFile.getName())) {
				springUpgradeNumber++;
			} else if ("hibernate.xml".equals(upgradeFile.getName())) {
				hibernateUpgradeNumber++;
			} else if (upgradeFile.getName().contains("hbm.xml")) {
				hbmUpgradeNumber++;
			}

			if (null != upgradeLogWritter && null != upgradeLogDetailWritter) {
				upgradedAutoList.add(newFilePath);
				upgradeLogDetailWritter.write("-- \r");
				upgradeLogDetailWritter.write(newFilePath + "\r");
				replacedList.forEach(item -> {
					try {
						upgradeLogDetailWritter.write(item + "\r");
					} catch (IOException e) {
						e.printStackTrace();
					}
				});

				upgradeLogDetailWritter.write("\r\r");
			}

		}

		replacedList.clear();
		replacedList = null;
		tempStream.close();

	}

	private Boolean javaFileReplace(StringBuilder mergeLines, List<String> lineList, int nextLineIndex,
			Map<String, String> sugMap, List<String> replacedList, File file, String suffix, String line,
			CharArrayWriter tempStream) {
		String trimLine = line.trim();
		boolean isReplace = false;

		if (!trimLine.startsWith("*/") && !trimLine.startsWith("*") && !trimLine.startsWith("/*")
				&& !trimLine.startsWith("//") && !"".equals(trimLine)) {

			// 存在代码换行处理：如果是以“.”开头的，认为是换行
			if (trimLine.startsWith(".")) {
				if (null == mergeLines) {
					mergeLines = new StringBuilder();
					mergeLines.append(lineList.get(nextLineIndex - 2));
					mergeLines.append(trimLine);
					String nextLine = null;
					if (nextLineIndex < lineList.size()) {
						nextLine = lineList.get(nextLineIndex);
					}
					if (null != nextLine && nextLine.startsWith(".")) {
						return isReplace;
					} else {
						mergeLines = null;
						return isReplace = writeLine(nextLineIndex, sugMap, replacedList, file, suffix, tempStream,
								line);
					}
				} else {
					mergeLines.append(trimLine);
					String nextLine = null;
					if (nextLineIndex < lineList.size()) {
						nextLine = lineList.get(nextLineIndex);
					}
					if (null != nextLine && !nextLine.startsWith(".")) {
						isReplace = writeLine(nextLineIndex, sugMap, replacedList, file, suffix, tempStream, line);
						mergeLines = null;
					} else {
						return isReplace;
					}
				}

			} else {
				// 判断下一行是否是以“.”开头的
				String nextLine = null;
				if (nextLineIndex < lineList.size()) {
					nextLine = lineList.get(nextLineIndex);
				}
				if (null != nextLine && nextLine.startsWith(".")) {
					return isReplace;
				} else {
					isReplace = writeLine(nextLineIndex, sugMap, replacedList, file, suffix, tempStream, line);
				}

			}
		} else {
			try {
				mergeLines = null;
				tempStream.write(line);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return isReplace;
	}

	private Boolean otherFileReplace(int lineNumber, Map<String, String> sugMap, List<String> replacedList, File file,
			String suffix, String line, CharArrayWriter tempStream) {
		return writeLine(lineNumber, sugMap, replacedList, file, suffix, tempStream, line);
	}

	private String addReplaceItem(int lineNumber, Map<String, String> sugMap, List<String> replacedList, File file,
			String suffix, String originalCharacter, String oldString, String newString) {

		// jsp 文件getSession() 不做替换
		if ("jsp".equals(suffix)) {
			if ("getSession()".equals(oldString)) {
				return originalCharacter;
			}
		}
		String replaceBefore = originalCharacter;
		String replaceAfter = originalCharacter;

		// 替换日志类
		if (originalCharacter.contains("(this.getClass())")) {
			replaceAfter = replaceAfter.replace("(this.getClass())", "(" + file.getName() + ")");
			replaceAfter = replaceAfter.replace(".java", ".class");
		}

		// 替换日志类
		if (originalCharacter.contains("(getClass())")) {
			replaceAfter = replaceAfter.replace("(getClass())", "(" + file.getName() + ")");
			replaceAfter = replaceAfter.replace(".java", ".class");
		}

		if ("local=\"".equals(oldString) || "local = \"".equals(oldString) || "local =\"".equals(oldString)) {
			return replaceAfter;
		}
		// 替换debug
		else if (".debug(".equals(oldString)) {
			if ((originalCharacter.contains(".debug("))) {
				int start = originalCharacter.indexOf(".debug(");
				String str = originalCharacter.substring(start, originalCharacter.length());
				if (str.contains(",") || str.contains("\"")) {
					return replaceAfter;
				} else {
					replaceAfter = originalCharacter.replace(oldString, newString);
				}
			}

			// 替换error参数
		} else if (".error(".equals(oldString)) {
			if ((originalCharacter.contains(".error("))) {
				if (!originalCharacter.contains(".getMessage()")) {
					int start = originalCharacter.indexOf(".error(");
					String str = originalCharacter.substring(start, originalCharacter.length());
					if (str.contains(",") || str.contains("\"")) {
						return replaceAfter;
					} else {
						replaceAfter = originalCharacter.replace(oldString, newString);
					}
				}
			}
			// 替换 logger.warn
		} else if (".warn(".equals(oldString)) {
			if ((originalCharacter.contains(".warn("))) {
				if (!originalCharacter.contains(".getMessage()")) {
					int start = originalCharacter.indexOf(".warn(");
					String str = originalCharacter.substring(start, originalCharacter.length());
					if (str.contains(",") || str.contains("\"")) {
						return replaceAfter;
					} else {
						replaceAfter = originalCharacter.replace(oldString, newString);
					}
				}
			}
			// 替换 logger.info
		} else if (".info(".equals(oldString)) {
			if (originalCharacter.contains(".info(")) {
				int start = originalCharacter.indexOf(".info(");
				String str = originalCharacter.substring(start, originalCharacter.length());
				if (str.contains(",") || str.contains("\"")) {
					return replaceAfter;
				} else {
					replaceAfter = originalCharacter.replace(oldString, newString);
				}
			}
		} else if (oldString.contains(".getLog(")) {
			if (originalCharacter.contains("ctx.getLog()") || originalCharacter.contains("context.getLog()")
					|| originalCharacter.contains("baseData.getLog()") || originalCharacter.contains("request.getLog()")
					|| originalCharacter.contains("baseData.getLog()")
					|| originalCharacter.contains("logContext.getLog()")
					|| originalCharacter.contains("transferContext.getLog()")) {
				return replaceAfter;
			} else {
				//常规替换
				replaceAfter = originalCharacter.replace(oldString, newString);
			}
		} else if ("= Logger".equals(oldString)) {
			if (originalCharacter.contains("LoggerFactory")) {
				return replaceAfter;
			} else {
				//常规替换
				replaceAfter = originalCharacter.replace(oldString, newString);
			}
		} else if ("Log log".equals(oldString)
				&& (originalCharacter.contains("SyncLog log") || originalCharacter.contains("DefaultOperationLog log")
						|| originalCharacter.contains("DefaultFlowStartLog log")
						|| originalCharacter.contains("HrSalaryLog log")
						|| originalCharacter.contains("HrStaffPersonInfoLog log")
						|| originalCharacter.contains("IFlowDefLog log")
						|| originalCharacter.contains("IFlowDefNodeLog log")
						|| originalCharacter.contains("IFlowDocEndLog log")
						|| originalCharacter.contains("IFlowDocStartLog log")
						|| originalCharacter.contains("IFlowNodeEndLog log")
						|| originalCharacter.contains("IFlowNodeStartLog log")
						|| originalCharacter.contains("IFlowOperationLog log")
						|| originalCharacter.contains("IFlowWorkItemEndLog log")
						|| originalCharacter.contains("IFlowWorkItemStartLog log")
						|| originalCharacter.contains("LbpmFlowEndLog log")
						|| originalCharacter.contains("LbpmFlowStartLog log")
						|| originalCharacter.contains("LbpmNodeEndLog log")
						|| originalCharacter.contains("LbpmNodeStartLog log")
						|| originalCharacter.contains("LbpmOperationLog log")
						|| originalCharacter.contains("LbpmWorkitemEndLog log")
						|| originalCharacter.contains("LbpmWorkitemStartLog log")
						|| originalCharacter.contains("LdingConsoleCallbackLog log")
						|| originalCharacter.contains("LdingConsoleIoLog log")
						|| originalCharacter.contains("LdingConsoleMobileLog log")
						|| originalCharacter.contains("LdingConsoleOperLog log")
						|| originalCharacter.contains("LdingOapiGetDDAvatarLog log")
						|| originalCharacter.contains("LdingOapiNotifyLog log")
						|| originalCharacter.contains("LdingOapiSystemLog log")
						|| originalCharacter.contains("LdingShudiCallLog log")
						|| originalCharacter.contains("HrStaffPersonInfoLog log")
						|| originalCharacter.contains("HrStaffRatifyLog log")
						|| originalCharacter.contains("IntegralServerLog log")
						|| originalCharacter.contains("KmsIstorageBatchLog log")
						|| originalCharacter.contains("LbpmExpecterLog log")
						|| originalCharacter.contains("ThirdFeishuNotifyLog log")
						|| originalCharacter.contains("ThirdDingNotifyLog log")
						|| originalCharacter.contains("ThirdGovDingCallbackLog log")
						|| originalCharacter.contains("ThirdWeixinAuthLog log")
						|| originalCharacter.contains("ThirdWeixinNotifyLog log")
						|| originalCharacter.contains("ToolsDatatransferLog log")
						|| originalCharacter.contains("ToolsLbpmtransferLog log")
						|| originalCharacter.contains("HrOrganizationLog log")
						|| originalCharacter.contains("LbpmFlowDefLog log")
						|| originalCharacter.contains("LbpmFlowDefNodeLog log")
						|| originalCharacter.contains("ComponentLockerVersionLog log")
						|| originalCharacter.contains("DefaultFlowDefLog log")
						|| originalCharacter.contains("DefaultFlowDefNodeLog log")
						|| originalCharacter.contains("DefaultFlowEndLog log")
						|| originalCharacter.contains("DefaultNodeStartLog log")
						|| originalCharacter.contains("DefaultWorkitemEndLog log")
						|| originalCharacter.contains("DefaultWorkitemStartLog log")
						|| originalCharacter.contains("ElecEqbLog log")
						|| originalCharacter.contains("FsscBudgetingApprovalLog log")
						|| originalCharacter.contains("ThirdCalendar365Log log")
						|| originalCharacter.contains("ThirdDingCallbackLog log")
						|| originalCharacter.contains("ThirdEkpJavaNotifyLog log")
						|| originalCharacter.contains("ThirdWelinkNotifyLog log"))
				|| originalCharacter.contains("DefaultNodeEndLog log")) {

			return replaceAfter;
		} else {
			//常规替换
			replaceAfter = originalCharacter.replace(oldString, newString);
		}

		if (!replaceAfter.equals(replaceBefore)) {
			replacedList.add(originalCharacter.trim() + " -> 替换为：" + replaceAfter.trim());
		}

		return replaceAfter;

	}

	private void addSuggestiveItem(int lineNumber, Map<String, String> sugMap, List<String> replacedList, File file,
			String replaceBefore, String oldString, String newString) {
		if (replaceBefore.contains(oldString)) {
			String[] splits = newString.split("->");
			replacedList.add(replaceBefore.trim() + " -> 修改建议：" + splits[1].trim());
			if (suggestiveMap.containsKey(oldString)) {
				String[] values = suggestiveMap.get(oldString).split("\\|");
				Integer times = Integer.valueOf(values[0]) + 1;
				suggestiveMap.put(oldString, times + "|" + splits[1]);
			} else {
				String value = "1|" + splits[1];
				suggestiveMap.put(oldString, value);
			}
			upgradedManualList.add(file.getAbsolutePath());
			sugMap.put("第" + lineNumber + "行", "包含[" + oldString + "]代码，提示修改为:" + splits[1].trim());
		}
	}

	/**
	 * 写一行，替换一行
	 * @param fileName
	 * @param suffix
	 * @param tempStream
	 * @param line
	 * @return
	 */
	private boolean writeLine(int lineNumber, Map<String, String> sugMap, List<String> replacedList, File file,
			String suffix, CharArrayWriter tempStream, String line) {
		String replaceBefore = line;
		String replaceAfter = line;
		boolean isReplace = false;

		for (Map.Entry<String, String> entry : replaceContentMap.entrySet()) {
			String mapKey = entry.getKey();
			String mapValue = entry.getValue();

			//判断是否是提示性修改
			if (mapValue.startsWith("@tip")) {
				addSuggestiveItem(lineNumber, sugMap, replacedList, file, replaceBefore, mapKey, mapValue);
			} else {
				replaceAfter = addReplaceItem(lineNumber, sugMap, replacedList, file, suffix, replaceBefore, mapKey,
						mapValue);
			}
			if (!replaceAfter.equals(replaceBefore)) {
				isReplace = true;
				line = replaceAfter;
				replaceBefore = replaceAfter;
			}
		}
		try {
			tempStream.write(line);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return isReplace;
	}

	private static void resetUpgradeLogPath() {
		upgradeFilePath = projectPath + "/v16_upgrade";
		upgradeLogFile = new File(upgradeFilePath + "/" + upgradeLog);
		upgradeDetailLogFile = new File(upgradeFilePath + "/" + upgradeDetailLog);
		manualUpgradeLogFile = new File(upgradeFilePath + "/" + manualUpgradeLog);
	}

	private static void copyFile(File source, File dest) {
		try {
			Files.copy(source.toPath(), dest.toPath());
		} catch (IOException e) {
			//e.printStackTrace();
			//printMsg("备份文件已经存在：" + dest.getAbsolutePath());
		}
	}

	private static String substringAfterLast(final String str, final String separator) {
		final int pos = str.lastIndexOf(separator);
		return str.substring(pos + separator.length());
	}

	private static void printMsg(String msg) {
		printMsg(msg, false);
	}

	private static void printMsg(String msg, boolean isWriteLog) {
		System.out.println("" + msg);
		if (isWriteLog) {
			try {
				if (null != upgradeLogWritter) {
					upgradeLogWritter.write(msg + "\r");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static void writeLog(double start) throws IOException {
		printMsg("3.从 v15 升级到 v16 ", true);
		printMsg("被扫描升级文件总数量:[" + fileNumber + "]个文件", true);
		printMsg("文件升级总数量:[" + upgradeNumber + "]个文件", true);
		printMsg("java文件升级总数量:[" + javaUpgradeNumber + "]个文件", true);
		printMsg("*.hbm.xml文件升级总数量:[" + hbmUpgradeNumber + "]个文件", true);
		printMsg("jsp文件升级总数量:[" + jspUpgradeNumber + "]个文件", true);
		printMsg("spring.xml文件升级总数量:[" + springUpgradeNumber + "]个文件", true);
		printMsg("hibernate.xml文件升级总数量:[" + hibernateUpgradeNumber + "]个文件", true);

		printMsg("", true);
		printMsg("4.日志文件：", true);
		printMsg("升级日志文件：" + upgradeLogFile.getAbsoluteFile(), true);
		printMsg("升级自动更新日志文件：" + upgradeDetailLogFile.getAbsoluteFile(), true);
		printMsg("升级手工更新日志文件：" + manualUpgradeLogFile.getAbsoluteFile(), true);
		printMsg("", true);

		printMsg("5.需要核对部分", true);
		printMsg("建议核对后再判断是否需要修改,如果想查看核对内容,请查看详细文件:" + manualUpgradeLogFile.getAbsolutePath(), true);
		suggestiveMap.forEach((key, value) -> {
			String[] values = value.split("\\|");
			printMsg(key + "->[" + values[0] + "]个处需要修改,修改建议：" + values[1]);
		});

		upgradedManualList.forEach((value) -> {
			try {
				upgradeLogWritter.write(value + "\r");
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

		printMsg("", true);

		printMsg("6.自动升级部分", true);
		printMsg("自动升级文件列表,如果想查看更新内容,请查看详细文件:" + upgradeDetailLogFile.getAbsolutePath(), true);

		upgradedAutoList.forEach((value) -> {
			try {
				upgradeLogWritter.write(value + "\r");
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

		double end = System.currentTimeMillis();
		BigDecimal b = new BigDecimal(((end - start) / 1000 / 60));
		double f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

		printMsg("", true);
		printMsg("升级耗时 : " + f1 + "分", true);

	}

	@SuppressWarnings("unused")
	private static void deleteFile() {
		List<String> needDeleteFiles = new ArrayList<String>();
		needDeleteFiles.add("src/main/java/log4j.properties");
		needDeleteFiles.add("src/main/java/com/landray/kmss/sys/config/loader/SessionFactoryWraper.java");
		needDeleteFiles.add("src/main/java/com/landray/kmss/sys/cache/hibernate/HbmRedisCache.java");
		needDeleteFiles.add("src/main/java/com/landray/kmss/sys/cache/hibernate/HbmRedisCacheProvider.java");
		needDeleteFiles.add("src/main/java/com/landray/kmss/sys/cache/hibernate/HbmRedisTimestampsCache.java");
		needDeleteFiles.add("src/main/java/landray/kmss/sys/log/util/MyDailyRollingFileAppender.java");
		needDeleteFiles
				.add("src/main/java/landray/kmss/sys/admin/dbchecker/configuration/KmssConfigurationFactory.java");
		needDeleteFiles.forEach(item -> {
			File file = new File(projectPath + "/" + item);
			copyFile(file, new File(file.getAbsoluteFile() + ".backup"));
			file.delete();
		});
	}

	private void upgradeSimpleFile(File dir, boolean isUseThread) throws IOException {
		String fileName = dir.getName();
		String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);

		if (recoverFile) {
			if ("backup".equals(suffix)) {
				if (recoverFile) {
					File upgradeFile = new File(dir.getPath().replace(".backup", ""));
					upgradeFile.delete();
					File backupFile = dir;
					if (isUseThread) {
						executor.submit(() -> {
							copyFile(backupFile, upgradeFile);
						});
					} else {
						copyFile(backupFile, upgradeFile);
					}

					return;
				}
			}
			return;
		}

		if ("backup".equals(suffix)) {
			return;
		}

		//升级Java文件
		if ("java".equals(suffix)) {
			replaceContent(dir.getPath(), suffix);
		}

		//升级JSP文件
		if ("jsp".equals(suffix)) {
			replaceContent(dir.getPath(), suffix);
		}

		//升级spring.xml,hibernate.xml,*.hbm.xml文件
		if ("xml".equals(suffix) && ("spring.xml".equals(fileName) || "hibernate.xml".equals(fileName)
				|| fileName.contains("hbm.xml"))) {
			replaceContent(dir.getPath(), suffix);
		}
	}

	private static InputStream getResourceAsStream(ClassLoader loader, String resource) throws IOException {
		InputStream in = null;
		if (loader != null) {
			in = loader.getResourceAsStream(resource);
		}
		if (in == null) {
			in = ClassLoader.getSystemResourceAsStream(resource);
		}
		if (in == null) {
			throw new IOException("Could not find resource " + resource);
		}
		return in;
	}

	private static void inputStreamToFile(InputStream is, File file) throws IOException {
		BufferedInputStream in = null;
		BufferedOutputStream out = null;
		in = new BufferedInputStream(is);
		out = new BufferedOutputStream(new FileOutputStream(file));
		int len = -1;
		byte[] b = new byte[1024];
		while ((len = in.read(b)) != -1) {
			out.write(b, 0, len);
		}
		in.close();
		out.close();
	}

	private static void execUpgrade() {

		double start = System.currentTimeMillis();
		File file = new File(projectPath + "/" + upgradeFileName);
		File excludeFile = new File(projectPath + "/" + excludeFileName);
		projectPath = projectPath.replaceAll("\\\\", "/");
		resetUpgradeLogPath();

		try {

			if (!upgradeLogFile.exists()) {
				if (!upgradeLogFile.getParentFile().exists()) {
					upgradeLogFile.getParentFile().mkdirs();
				}
				upgradeLogFile.createNewFile();
				upgradeDetailLogFile.createNewFile();
				manualUpgradeLogFile.createNewFile();
			} else {
				upgradeLogFile.delete();
				upgradeDetailLogFile.delete();
				manualUpgradeLogFile.delete();

				upgradeLogFile.createNewFile();
				upgradeDetailLogFile.createNewFile();
				manualUpgradeLogFile.createNewFile();
			}

			upgradeLogWritter = new FileWriter(upgradeLogFile, false);
			upgradeLogDetailWritter = new FileWriter(upgradeDetailLogFile, false);
			manualUpgradeLogFileWritter = new FileWriter(manualUpgradeLogFile, false);

			printMsg("1.升级配置说明", true);
			if (!file.exists()) {
				printMsg("升级替换配置文件不存在：" + file.getAbsolutePath(), true);
				file = new File(System.getProperty("user.dir") + "/" + upgradeFileName);
				if (!file.exists()) {
					InputStream is = getResourceAsStream(QuickUpgradeBuilder.class.getClassLoader(),
							"META-INF/" + upgradeFileName);
					if (null == is) {
						printMsg("工程目录升级替换配置文件不存在：" + file.getAbsolutePath());
						return;
					} else {
						inputStreamToFile(is, file);
					}
				}
			}

			if (!excludeFile.exists()) {
				printMsg("升级排除配置文件不存在：" + excludeFile.getAbsolutePath(), true);
				excludeFile = new File(System.getProperty("user.dir") + "/" + excludeFileName);
				if (!excludeFile.exists()) {
					InputStream is = getResourceAsStream(QuickUpgradeBuilder.class.getClassLoader(),
							"META-INF/" + excludeFileName);
					if (null == is) {
						printMsg("工程目录升级排除配置文件不存在：" + excludeFile.getAbsolutePath(), true);
						return;
					} else {
						inputStreamToFile(is, excludeFile);
					}
				}
			}

			printMsg("升级替换配置文件：" + file.getAbsolutePath(), true);
			printMsg("升级排除配置文件：" + excludeFile.getAbsolutePath(), true);
			printMsg("升级日志目录：" + upgradeFilePath, true);
			QuickUpgradeBuilder quickReplaceBuilder = new QuickUpgradeBuilder(file, excludeFile);

			printMsg("", true);
			printMsg("2.升级扫描路径", true);
			if (0 != replaceContentMap.size()) {
				if (null == upgradeSpecifiedFile) {
					File upgradeSrcFile = new File(projectPath + "/src");
					if (upgradeSrcFile.exists()) {
						printMsg("扫描 src路径：" + upgradeSrcFile.getAbsolutePath(), true);
						quickReplaceBuilder.upgrade(upgradeSrcFile, true);
					}

					File upgradeWebFile = new File(projectPath + "/WebContent");
					if (upgradeWebFile.exists()) {
						printMsg("扫描 WebContent路径：" + upgradeWebFile.getAbsolutePath(), true);
						quickReplaceBuilder.upgrade(upgradeWebFile, true);
					}
				} else {
					if (upgradeSpecifiedFile.exists()) {
						quickReplaceBuilder.upgrade(upgradeSpecifiedFile, true);
						printMsg("扫描 src路径：" + upgradeSpecifiedFile.getAbsolutePath(), true);
					}
				}

				CountThread countThread = quickReplaceBuilder.new CountThread();
				countThread.setDaemon(true);
				countThread.start();

			} else {
				printMsg("升级替换配置文件内容为空：" + file.getAbsolutePath(), true);
				return;
			}

			executor.shutdown();
			while (true) {
				if (executor.isTerminated()) {
					//printMsg("所有的子线程都结束了！");
					printMsg("");
					break;
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			writeLog(start);
			upgradeLogWritter.close();
			upgradeLogDetailWritter.close();
			manualUpgradeLogFileWritter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void upgrade(File path, boolean isUseThread) {
		if (path.isDirectory()) {
			File[] files = path.listFiles();
			for (File file : files) {
				if (file.isDirectory()) {
					upgrade(file, isUseThread);
				} else {
					fileNumber++;
					if (isUseThread) {
						executor.submit(() -> {
							try {
								upgradeSimpleFile(file, isUseThread);
							} catch (IOException e) {
								e.printStackTrace();
							}
						});
					} else {
						try {
							upgradeSimpleFile(file, isUseThread);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}

				}
			}
		} else {
			fileNumber++;
			if (isUseThread) {
				executor.submit(() -> {
					try {
						upgradeSimpleFile(path, isUseThread);
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
			} else {
				try {
					upgradeSimpleFile(path, isUseThread);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public static void main(String[] args) {

		CommandLineParser parser = new DefaultParser();
		Options options = new Options();
		options.addOption("h", "help", false, "输出帮助信息");
		options.addOption("p", "base_path", true, "工程根目录,默认值是当前工程根目录");
		options.addOption("b", "backup", true, "是否备份被升级的文件,默认值:false");
		options.addOption("r", "recover", true, "是否恢复升级,默认值:false");
		options.addOption("f", "file", true, "默认值:空,值可以是某个文件或者某个文件夹(绝对路径)");
		options.addOption("u", "upgrade", true, "默认值:空,升级替换配置文件(绝对路径)");
		options.addOption("e", "exclude", true, "默认值:空,升级排除配置文件不(绝对路径)");

		CommandLine commandLine;
		HelpFormatter formatter = new HelpFormatter();
		try {
			commandLine = parser.parse(options, args);

			if (commandLine.hasOption('h')) {
				formatter.printHelp("command-line", options);
				System.exit(0);
			}

			if (commandLine.hasOption('p')) {
				String basePath = commandLine.getOptionValue('p');
				projectPath = basePath;
			}

			if (commandLine.hasOption('b')) {
				String backup = commandLine.getOptionValue('b');
				backUpFile = Boolean.valueOf(backup);
			}

			if (commandLine.hasOption('r')) {
				String recover = commandLine.getOptionValue('r');
				recoverFile = Boolean.valueOf(recover);
			}

			if (commandLine.hasOption('f')) {
				String fileName = commandLine.getOptionValue('f');
				upgradeSpecifiedFile = new File(fileName);
			}

			if (commandLine.hasOption('u')) {
				String fileName = commandLine.getOptionValue('u');
				if (null != fileName) {
					upgradeFileName = fileName;
				}
			}

			if (commandLine.hasOption('e')) {
				String fileName = commandLine.getOptionValue('e');
				if (null != fileName) {
					excludeFileName = fileName;
				}
			}

		} catch (ParseException e) {
			printMsg(e.getMessage());
			formatter.printHelp("utility-name", options);
			System.exit(1);
		}

		//执行升级
		execUpgrade();
		//deleteFile();
	}

	private class CountThread extends Thread {

		private int oldUpgradeNumber = 0;

		@Override
        public void run() {
			while (true) {
				try {
					Thread.sleep(2000);
					if (oldUpgradeNumber != upgradeNumber) {
						printMsg("被扫描升级文件数量:[" + fileNumber + "]个文件,已经升级的文件数量:[" + upgradeNumber + "]个文件");
					}
					oldUpgradeNumber = upgradeNumber;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		}
	}

}

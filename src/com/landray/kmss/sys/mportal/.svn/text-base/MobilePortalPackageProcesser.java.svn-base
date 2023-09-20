package com.landray.kmss.sys.mportal;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.lang.StringUtils;

import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.sys.mobile.offline.PackageConstant;
import com.landray.kmss.sys.mobile.offline.PackageUtils;
import com.landray.kmss.sys.mobile.offline.SysMobilePackagePlugin;
import com.landray.kmss.sys.mobile.offline.handler.PackageHandlerContext;
import com.landray.kmss.sys.mobile.offline.interfaces.IPackageProcesser;
import com.landray.kmss.sys.mobile.offline.wrapper.WrapperRequest;
import com.landray.kmss.sys.mobile.offline.wrapper.WrapperResponse;
import com.landray.kmss.sys.mportal.plugin.MportalMportletUtil;
import com.landray.kmss.sys.mportal.xml.SysMportalMportlet;
import com.landray.kmss.util.StringUtil;

import net.sf.json.JSONObject;

public class MobilePortalPackageProcesser implements IPackageProcesser, PackageConstant {

	private static final IOFileFilter jspfileFilter = FileFilterUtils.asFileFilter(new FilenameFilter() {
		@Override
		public boolean accept(File dir, String name) {
			return name.endsWith("jsp");
		}
	});

	@Override
	public void process(String appId, PackageHandlerContext context) throws Exception {
		File root = (File) context.get("root");
		List<SysMportalMportlet> sysMportalMportlets = MportalMportletUtil.getMportletListByModule(null);
		List<String> done = new ArrayList<String>();
		for (SysMportalMportlet sysMportalMportlet : sysMportalMportlets) {
			if (done.contains(sysMportalMportlet.getFdModule())) {
				continue;
			}
			String p = folderPath(sysMportalMportlet);
			if (p != null) {
				File folder = new File(p);
				Collection<File> files = PackageUtils.listFiles(folder);
				for (File file : files) {
					String name = StringUtils.removeStart(file.getAbsolutePath(), location.getAbsolutePath());
					File target = new File(root, name);
					if (!target.exists()) {
						PackageUtils.createFile(target);
					}
					PackageUtils.fileCopy(file, target);
				}
			}
			handlePage(context, sysMportalMportlet);
			done.add(sysMportalMportlet.getFdModule());
		}
		writeMappringFile(context);
	}

	@SuppressWarnings("unchecked")
	private void handlePage(PackageHandlerContext context, SysMportalMportlet sysMportalMportlet) throws Exception {
		String fullPath = StringUtils.removeStart(sysMportalMportlet.getFdJspUrl(), location.getAbsolutePath());
		String[] paths = fullPath.split(Pattern.quote(File.separator));
		if (paths.length < 3) {
            return;
        }
		fullPath = paths[1] + "/" + paths[2];
		IExtension extension = null;
		extension = SysMobilePackagePlugin.getExtension(fullPath);
		if (extension == null && paths.length > 3) {
			fullPath = paths[1] + "/" + paths[2] + "/" + paths[3];
			extension = SysMobilePackagePlugin.getExtension(fullPath);
		}
		if (extension != null) {
			WrapperRequest request = context.getRequest();
			WrapperResponse response = context.getResponse();
			String homepage = Plugin.getParamValueString(extension, "homepage");
			String page = Plugin.getParamValueString(extension, "page");
			String folder = Plugin.getParamValueString(extension, "folder");
			File root = (File) context.get("root");
			Set<String> processFiles = new HashSet<String>();
			// 主页必转换
			processFiles.add(PackageUtils.formatPath(homepage));
			// 其他需要转换的文件
			String[] pages = null;
			if (StringUtil.isNotNull(page)) {
				pages = page.split(";");
				for (String _page : pages) {
					processFiles.add(PackageUtils.formatPath(_page));
				}
			}
			// folder/offline目录文件默认转换
			File offlineFolder = new File(location, PackageUtils.formatPath(folder) + "offline");
			if (offlineFolder.exists()) {
				Collection<File> fs = FileUtils.listFiles(offlineFolder, jspfileFilter,
						FileFilterUtils.trueFileFilter());
				for (File file : fs) {
					processFiles.add(StringUtils.removeStart(file.getAbsolutePath(), location.getAbsolutePath()));
				}
			}
			for (String processFile : processFiles) {
				request.getRequestDispatcher(processFile.replace(File.separator, "/")).include(request, response);
				String html = new String(response.getResponseData(), "UTF-8");
				File target = new File(root, processFile.replace(".jsp", ".html"));
				writeFile(target, html);
				request.removeAttribute("tag_template_block_replace_varible");
				response.reset();
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void writeMappringFile(PackageHandlerContext context) throws Exception {
		JSONObject mapping = new JSONObject();
		Set<String> processFiles = new HashSet<String>();
		IExtension[] extensions = SysMobilePackagePlugin.getExtensions();
		for (IExtension extension : extensions) {
			String homepage = Plugin.getParamValueString(extension, "homepage");
			String page = Plugin.getParamValueString(extension, "page");
			String folder = Plugin.getParamValueString(extension, "folder");
			// 主页必转换
			processFiles.add(PackageUtils.formatPath(homepage));
			// 其他需要转换的文件
			String[] pages = null;
			if (StringUtil.isNotNull(page)) {
				pages = page.split(";");
				for (String _page : pages) {
					processFiles.add(PackageUtils.formatPath(_page));
				}
			}
			// folder/offline目录文件默认转换
			File offlineFolder = new File(location, PackageUtils.formatPath(folder) + "offline");
			if (offlineFolder.exists()) {
				Collection<File> fs = FileUtils.listFiles(offlineFolder, jspfileFilter,
						FileFilterUtils.trueFileFilter());
				for (File file : fs) {
					processFiles.add(StringUtils.removeStart(file.getAbsolutePath(), location.getAbsolutePath()));
				}
			}
		}
		for (String processFile : processFiles) {
			String _processFile = processFile.replace(File.separator, "/");
			mapping.put(_processFile, _processFile.replace(".jsp", ".html"));
			mapping.put(urlPrefix + _processFile, _processFile.replace(".jsp", ".html"));
		}
		String filePath = PackageUtils.formatPath("/sys/mobile/js/mui/device/kk5/mapping.js");
		File root = (File) context.get("root");
		File target = new File(root, filePath);
		if (target.exists()) {
			target.delete();
		}
		writeFile(target, "dojoConfig.fileMapping = \n" + mapping.toString() + "\n;");
	}

	// 获取所在目录
	private String folderPath(SysMportalMportlet sysMportalMportlet) throws Exception {
		String url = sysMportalMportlet.getFdJspUrl();
		if (StringUtil.isNotNull(url) && url.indexOf("?") > -1) {
			url = url.substring(0, url.indexOf("?"));
		}
		File file = new File(location, PackageUtils.formatPath(url));
		if (file.exists()) {
			String parentName = file.getParent();
			while (file.getParentFile() != null) {
				file = file.getParentFile();
				if ("mobile".equals(file.getName())) {
					return file.getAbsolutePath();
				}
			}
			return parentName;
		}
		return null;
	}

	private void writeFile(File target, String html) throws Exception {
		if (!target.exists()) {
			PackageUtils.createFile(target);
		}
		PackageUtils.writeFile(target, html);
	}

}

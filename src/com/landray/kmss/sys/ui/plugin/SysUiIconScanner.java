package com.landray.kmss.sys.ui.plugin;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import com.landray.kmss.framework.util.PluginConfigLocationsUtil;
import com.landray.kmss.sys.config.xml.XmlReaderContext;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class SysUiIconScanner {
	// key为前缀，如：“.lui_icon_on .lui_icon_s”或“.lui_icon_s”
	private List<IconInfo> iconList;
	private Map<String, List<IconInfo>> iconClsNamesMap = null;

	private static class IconInfo implements Comparable<IconInfo> {
		public String name;
		public String type;
		public String fileName;
		public boolean isOn;
		public int x;
		public int y;

		public IconInfo(String name, String type, String fileName,
				boolean isOn, int x, int y) {
			super();
			this.name = name.toLowerCase();
			this.type = type;
			this.fileName = fileName;
			this.isOn = isOn;
			this.x = x;
			this.y = y;
		}

		public String getCssName() {
			StringBuffer s = new StringBuffer();
			if (isOn) {
				s.append(".lui_icon_on ");
			}
			return s.append(".lui_icon_").append(type).append('_').append(name)
					.toString();
		}

		public String getFilePath() {
			StringBuffer s = new StringBuffer();
			s.append("../icon/").append(type).append(isOn ? "-on/" : "/")
					.append(fileName);
			return s.toString();
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == null){
				return false;
			}
			if (this.getClass() != obj.getClass()) {
                return false;
            }
			IconInfo other = (IconInfo) obj;
			return name.equals(other.name) && type.equals(other.type)
					&& isOn == other.isOn;
		}
		
		@Override
		public int hashCode() {
			return super.hashCode();
		}

		@Override
		public String toString() {
			// .lui_icon_on .lui_icon_s_add
			// {background-image:url(../icon/s-on/opt.png);
			// background-position: 0px -0px !important;}
			StringBuffer s = new StringBuffer();
			s.append(getCssName()).append("\t{");
			s.append(" background-image:url(").append(getFilePath())
					.append(");");
			if (x > -1) {
				int size = "s".equals(type) ? 16 : ("m".equals(type) ? 32 : 48);
				s.append(" background-position:").append(-size * x)
						.append("px ").append(-size * y)
						.append("px !important;");
			}
			return s.append(" }").toString();
		}

		@Override
		public int compareTo(IconInfo o) {
			if (!name.equals(o.name)) {
				return name.compareTo(o.name);
			}
			if (isOn == o.isOn) {
                return 0;
            }
			if (isOn) {
                return 1;
            }
			return -1;
		}
	}

	/**
	 * 读文件
	 */
	private String readFile(String path) throws IOException {
		FileInputStream input = null;
		ByteArrayOutputStream out = null;
		try {
			File file = new File(path);
			if (!file.exists()) {
                return null;
            }
			input = new FileInputStream(file);
			out = new ByteArrayOutputStream();
			int bufsize = 1024;
			byte[] bs = new byte[bufsize]; //input.available()
			int count = 0;
			while ((count = input.read(bs)) != -1) { //将二进制流信息读入数组
				out.write(bs, 0, count); //通过输出流将数组信息显示到页面
			}			
			return new String(out.toByteArray(), "UTF-8").trim();
		} finally {
			IOUtils.closeQuietly(input);
			IOUtils.closeQuietly(out);			
		}
	}

	/**
	 * 写文件
	 */
	private void writeFile(String path, String content) throws IOException {
		FileOutputStream output = null;
		try {
			File file = new File(path);
			if (!file.exists()) {
                file.createNewFile();
            }
			output = new FileOutputStream(file);
			output.write(content.getBytes("UTF-8"));
		} finally {
			if (output != null) {
				output.close();
			}
		}
	}

	/**
	 * 添加图标
	 */
	private void addIcon(IconInfo icon, StringBuffer errorInfo) {
		int index = iconList.indexOf(icon);
		if (index > -1) {
			IconInfo oicon = iconList.get(index);
			errorInfo.append("发现重名（" + icon.getCssName() + "）：")
					.append(icon.getFilePath()).append(" < - > ")
					.append(oicon.getFilePath()).append("<br>");
			return;
		}
		if (icon.isOn) {
			if (!iconList.contains(new IconInfo(icon.name, icon.type,
					icon.fileName, false, 0, 0))) {
				errorInfo.append("图标的默认状态未定义（" + icon.getCssName() + "）：")
						.append(icon.getFilePath()).append("<br>");
			}
		}
		iconList.add(icon);

	}

	/**
	 * 扫描目录
	 */
	private void scanDir(File dir, String type, boolean isOn,
			StringBuffer errorInfo) throws IOException {
		if (!dir.exists() || !dir.isDirectory()) {
			return;
		}
		File[] files = dir.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
                continue;
            }
			String fileName = file.getName();
			int index = fileName.lastIndexOf('.');
			if (index == -1) {
				continue;
			}
			String name = fileName.substring(0, index).replace('-', '_');
			String ext = fileName.substring(index + 1);
			if (!SysUiTools.isImageFile(ext)) {
				continue;
			}
			String group = readFile(dir.getAbsolutePath() + "/" + name + ".txt");
			if (StringUtil.isNull(group)) {
				// String name, String type, String fileName, boolean isOn, int
				// x, int y
				addIcon(new IconInfo(name, type, fileName, isOn, -1, -1),
						errorInfo);
			} else {
				String[] iconNames = group.trim().split("[\\s+]");
				int i = 0;
				for (String iconName : iconNames) {
					if (iconName.length() == 0) {
                        continue;
                    }
					if (iconName.endsWith(":on")) {
						iconName = iconName.substring(0, iconName.length() - 3);
						addIcon(new IconInfo(iconName, type, fileName, false,
								0, i), errorInfo);
						addIcon(new IconInfo(iconName, type, fileName, true, 1,
								i), errorInfo);
					} else {
						addIcon(new IconInfo(iconName, type, fileName, isOn, 0,
								i), errorInfo);
					}
					i++;
				}
			}
		}
	}

	private void scanPath(String themePath, String type, StringBuffer errorInfo)
			throws IOException {
		File dir = new File(themePath + "icon/" + type);
		scanDir(dir, type, false, errorInfo);
		dir = new File(themePath + "icon/" + type + "-on");
		scanDir(dir, type, true, errorInfo);
	}

	/**
	 * 加载图标定义
	 */
	private void loadIconDefine(String themePath, String type,
			StringBuffer result, StringBuffer errorInfo) throws IOException {
		iconList = new ArrayList<IconInfo>();
		List<IconInfo> typeClsNamsInfos = new ArrayList<IconInfo>();
		scanPath(themePath, type, errorInfo);
		Collections.sort(iconList);
		for (IconInfo icon : iconList) {
			if (!icon.isOn) {
				typeClsNamsInfos.add(icon);
			}
			result.append("\r\n").append(icon.toString());
		}
		if ("s".equals(type)) {
			iconClsNamesMap.put("small", typeClsNamsInfos);
		} else if ("m".equals(type)) {
			iconClsNamesMap.put("medium", typeClsNamsInfos);
		} else if ("l".equals(type)) {
			iconClsNamesMap.put("large", typeClsNamsInfos);
		}
		iconList = null;
	}

	/**
	 * 扫描图标CSS类名
	 * 
	 * @param type
	 * @param isStatus
	 * @return
	 * @throws IOException
	 */
	public List<String> scanIconCssName(String type, boolean isStatus)
			throws IOException {
		iconList = new ArrayList<IconInfo>();
		String path = PluginConfigLocationsUtil.getWebContentPath()
				+ "/sys/ui/extend/theme/default/";
		StringBuffer errorInfo = new StringBuffer();
		scanPath(path, type, errorInfo);
		String prefix = "lui_icon_" + type + "_";
		List<String> result = new ArrayList<String>();
		for (IconInfo icon : iconList) {
			if (icon.isOn == isStatus) {
                result.add(prefix + icon.name);
            }
		}
		iconList = null;
		return result;
	}

	/**
	 * 重新加载icon.css
	 */
	public String reloadIcon(String theme) throws Exception {
		// String themePath = "/sys/ui/extend/theme/default/";
		if (iconClsNamesMap == null) {
			iconClsNamesMap = new HashMap<String, List<IconInfo>>();
		} else {
			iconClsNamesMap.clear();
		}
		String themePath = SysUiPluginUtil.getThemes().get(theme).getFdPath();

		if (themePath.startsWith("/" + XmlReaderContext.UIEXT + "/")) {
			themePath = ResourceUtil.KMSS_RESOURCE_PATH + themePath;
		} else {
			themePath = PluginConfigLocationsUtil.getWebContentPath()
					+ themePath;
		}

		StringBuffer content = new StringBuffer();
		String header = readFile(themePath + "style/icon.tmpl");
		if (header != null) {
			content.append(header);
		}
		StringBuffer errorInfo = new StringBuffer();
		content.append("\r\n\r\n/*小图标*/");
		loadIconDefine(themePath, "s", content, errorInfo);
		content.append("\r\n\r\n/*中图标*/");
		loadIconDefine(themePath, "m", content, errorInfo);
		content.append("\r\n\r\n/*大图标*/");
		loadIconDefine(themePath, "l", content, errorInfo);

		writeFile(themePath + "style/icon.css", content.toString());
		writeFile(themePath + "style/icon.js", generateJsClsNames());
		return errorInfo.toString();
	}

	private String generateJsClsNames() {
		String rtnJsClsNames = "var CDP_ICONS = ";
		JSONObject icons = new JSONObject();
		icons.put("small", generateTypeIconArray("small"));
		icons.put("medium", generateTypeIconArray("medium"));
		icons.put("large", generateTypeIconArray("large"));
		return rtnJsClsNames + icons.toString();
	}
	
	private JSONArray generateTypeIconArray(String type) {
		JSONArray rtnIconArray = new JSONArray();
		List<IconInfo> typeIconInfos = iconClsNamesMap.get(type);
		if (typeIconInfos != null && typeIconInfos.size() > 0) {
			for (IconInfo item : typeIconInfos) {
				JSONObject iconInfo = new JSONObject();
				iconInfo.put("clsName", item.getCssName().substring(1));
				rtnIconArray.add(iconInfo);
			}
		}
		return rtnIconArray;
	}

	public static void main(String[] args) throws Exception {
		System.out.println(new SysUiIconScanner().reloadIcon("default"));
		System.out.println("OK!");
	}
}

package com.landray.kmss.third.pda.util;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictAttachmentProperty;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.config.loader.ConfigLocationsUtil;
import com.landray.kmss.util.FileUtil;
import com.landray.kmss.util.StringUtil;

public class PdaModuleConfigUtil {

	public final static String ICON_DIR = "/third/pda/resource/images/icon";
	public static String[] extensions = new String[] { "jpg", "JPG", "jpeg",
			"JPEG", "gif", "GIF", "png", "PNG", "bmp", "BMP" };

	private static Document doc = null;

	/**
	 * 返回模块图标目录
	 * 
	 * @param contextPath
	 * @return
	 */
	public static String getIconDir(String contextPath, String iconDir) {
		return contextPath + iconDir;
	}

	/**
	 * 根据文件目录返回指定扩展名的文件集合
	 * 
	 * @param filePath
	 * @param extensions
	 * @return
	 */
	public static Collection<File> getListFiles(String filePath,
			String[] extensions) {
		File dir = new File(filePath);
		return FileUtils.listFiles(dir, extensions, false);
	}

	/**
	 * 返回模块下面所有数据字典域模型列表
	 * 
	 * @param urlPrefix
	 * @return
	 * @throws DocumentException
	 * @throws Exception
	 */
	public static List<SysDictModel> getDictModelList(String urlPrefix)
			throws DocumentException, Exception {
		List<SysDictModel> dictModelList = new ArrayList<SysDictModel>();
		
		if(StringUtil.isNull(urlPrefix)) {
            return dictModelList;
        }
		
		String path = urlPrefix.replace("/", ".");
		SysDataDict dict = SysDataDict.getInstance();

		List ftsearches = dict.getModelInfoList();
		
		for (int i = 0; i < ftsearches.size(); i++) {
			if(ftsearches.get(i).toString().contains(path)){

				SysDictModel model = dict.getModel(ftsearches.get(i).toString());
				dictModelList.add(model);
			}
		}

		return dictModelList;
	}

	/**
	 * 返回配置搜索model名称
	 * 
	 * @param urlPrefix
	 * @return
	 * @throws DocumentException
	 * @throws Exception
	 */
	public static String getFtSearchModelName(String urlPrefix)
			throws DocumentException, Exception {
		String modelName = "";
		String path = ConfigLocationsUtil.getKmssConfigPath() + "/" + urlPrefix
				+ "/" + "design.xml";
		File file = new File(path);
		if (file.exists() && file.isFile()) {
			Document doc = new SAXReader().read(FileUtil.getInputStream(path));
			Element element = doc.getRootElement();
			Element ftSearch = element.element("ftSearch");
			if (ftSearch != null) {
				modelName = ftSearch.attributeValue("modelName");
			}
		}
		return modelName;
	}

	/**
	 * 返回数据字典中常用属性列表
	 * 
	 * @param modelName
	 * @return
	 */
	public static List<SysDictCommonProperty> getDictPropertyList(
			String modelName) {
		SysDataDict dict = SysDataDict.getInstance();
		SysDictModel model = dict.getModel(modelName);
		return model.getPropertyList();
	}

	/**
	 * 返回数据字典中附近属性列表
	 * 
	 * @param modelName
	 * @return
	 */
	public static List<SysDictAttachmentProperty> getDictAttachmentPropertyList(
			String modelName) {
		SysDataDict dict = SysDataDict.getInstance();
		SysDictModel model = dict.getModel(modelName);
		return model.getAttachmentPropertyList();
	}

	/**
	 * 返回文件名称
	 * 
	 * @param filePath
	 * @return
	 */
	public static String getFileName(String filePath) {
		filePath = ConfigLocationsUtil.getWebContentPath() + filePath;
		File dir = new File(filePath);
		return dir.getName();
	}

}

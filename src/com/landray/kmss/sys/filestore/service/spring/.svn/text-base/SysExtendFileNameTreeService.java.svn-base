package com.landray.kmss.sys.filestore.service.spring;

import java.util.ArrayList;
import java.util.List;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.sys.authentication.util.StringUtil;

/**
 * 待转换扩展名
 *
 * @date 2020/10/23
 */
public class SysExtendFileNameTreeService implements IXMLDataBean{
    private ExtendFileNameTreeBuilder builder = ExtendFileNameTreeBuilder.getInstance();
	private final static String CONVERTER_TYPE_ASPOSE = "aspose";
	private final static String CONVERTER_TYPE_YOZO = "yozo";
	private final static String CONVERTER_TYPE_WPS_CENTER = "wpsCenter";
	private final static String CONVERTER_TYPE_FOXIT = "foxit";
	
	
	@Override
	public List getDataList(RequestContext xmlContext) throws Exception {
		String fdParentId = xmlContext.getParameter("parentId"); //父类ID二级信息
		String converterType = xmlContext.getParameter("converterType"); //转换服务
		String tagSuffix =  xmlContext.getParameter("tagSuffix"); //目标转换格式
		
		if(StringUtil.isNull(fdParentId)) {
			if(StringUtil.isNotNull(converterType) && StringUtil.isNotNull(tagSuffix)) {
				builder.list().clear();
				if(CONVERTER_TYPE_ASPOSE.equals(converterType) || CONVERTER_TYPE_YOZO.equals(converterType)) {
					return converterArray(tagSuffix, converterType);
				}

				return converterAlly(tagSuffix);
			}
			
			return new ArrayList();
		}

		return suffix(converterType, tagSuffix, fdParentId);
	}

	/**
	 * ASPOSE 和永中
	 * @param tagSuffix
	 * @return
	 */
	public List converterArray(String tagSuffix, String converterType) {
		if("PDF" .equals(tagSuffix)) {
			if(CONVERTER_TYPE_YOZO.equals(converterType)) {
				// Office系列  WPS系列
				return builder.buildOffice().buildWPS().list();
			}

			// Office系列  WPS系列  压缩
			return builder.buildOffice().buildWPS().buildPress().list();
		} else if("HTML".equals(tagSuffix)) {
			if(CONVERTER_TYPE_YOZO.equals(converterType)) {
				//  Office系列 WPS系列 版式文档 文本
				return builder.buildOffice().buildWPS().buildLayout().buildTxt().list();
			}

			// Office系列  WPS系列  版式文档  压缩  文本  图纸
			return builder.buildOffice().buildWPS().buildLayout().buildPress()
					.buildTxt().buildPicPaper().list();
		} else if("JPG".equals(tagSuffix)) {
			// Office系列  WPS系列 版式文档
			return builder.buildOffice().buildWPS().buildLayout().list();
		} else if("MP4".equals(tagSuffix)) {
			// 音频 视频
			return builder.buildAudio().buildVideo().list();
		}

		return new ArrayList();
	}

	/**
	 * 数科OFD 、 WPS 、点聚、福昕
	 * @param tagSuffix
	 * @return
	 */
	public List converterAlly(String tagSuffix) {
		List rtnList = new ArrayList();
		if("OFD".equals(tagSuffix) || "PDF".equals(tagSuffix)) {
			// Office系列  WPS系列  其它
			return builder.buildOffice().buildWPS().buildOthers().list();
		}
		
		return rtnList;
	}
	
	/**
	 * 转换前的文件类型
	 * 
	 * @param converterType 转换服务
	 * @param tagSuffix   目标转换文件
	 * @param extendFileName  类型名
	 * @return
	 */
	public List suffix(String converterType,String tagSuffix,String extendFileName) {
		switch(extendFileName) {
		case "office":
			return officeSuffix(converterType, tagSuffix);
		case "wps":
			return wpsSuffix(converterType, tagSuffix);
		case "layoutDocument":
			return layoutSuffix();
		case "picture":
			return  pictureSuffix();
		case "press" :
			return pressSuffix();
		case "txt":
			return txtSuffix();
		case "picturePaper":
			return picturePaperSuffix(converterType, tagSuffix);
		case "audio" :
			return audioSuffix();
		case "video":
			return videoSuffix();
		case "others":
				return othersSuffix(converterType, tagSuffix);
		
		}
			
		return new ArrayList();
	}
	
	/**
	 * Office系列
	 * @param converterType
	 * @param tagSuffix
	 * @return
	 */
	public List officeSuffix(String converterType,String tagSuffix) {
		List<String> suName = new ArrayList<String>();
		if(CONVERTER_TYPE_ASPOSE.equals(converterType) || CONVERTER_TYPE_YOZO.equals(converterType)) {
			switch(tagSuffix) {
				case "PDF":
					suName.add("doc");
					suName.add("docx");
					break;
				case "OFD":
					suName.add("doc");
					suName.add("docx");
					break;
				case "HTML":
					suName.add("doc");
					suName.add("docx");
					suName.add("xls");
					suName.add("xlsx");
					break;
				case "JPG":
					suName.add("doc");
					suName.add("docx");
					suName.add("ppt");
					suName.add("pptx");
					break;
			}
		}
		else if(CONVERTER_TYPE_FOXIT.equalsIgnoreCase(converterType)) {
			suName.add("doc");
			suName.add("docx");
			suName.add("xls");
			suName.add("xlsx");
			suName.add("ppt");
			suName.add("pptx");
		}
		else {
			suName.add("doc");
			suName.add("docx");
		}
		
		List rtnList = new ArrayList();
		for(String subfix : suName) {
			Object[] object = new Object[3];
			object[0] = subfix;
			object[1] = subfix;
			object[2] = subfix;
			rtnList.add(object);
		}
		return rtnList;
	}
	
	/**
	 * WPS系列
	 * @param converterType
	 * @param tagSuffix
	 * @return
	 */
	public List wpsSuffix(String converterType,String tagSuffix) {
		List<String> suName = new ArrayList<String>();
		if(CONVERTER_TYPE_ASPOSE.equals(converterType) || CONVERTER_TYPE_YOZO.equals(converterType)) {
			switch(tagSuffix) {
				case "PDF":
					suName.add("wps");
					break;
				case "OFD":
					suName.add("wps");
					break;
				case "HTML":
					suName.add("wps");
					suName.add("et");
					break;
				case "JPG":
					suName.add("wps");
					suName.add("dps");
					break;
			}
		}
		else if(CONVERTER_TYPE_FOXIT.equalsIgnoreCase(converterType)) {
			suName.add("wps");
			suName.add("et");
			suName.add("dps");
			//suName.add("ett");
		}
		else {
			suName.add("wps");
		}
		
		
		List rtnList = new ArrayList();
		for(String subfix : suName) {
			Object[] object = new Object[3];
			object[0] = subfix;  
			object[1] = subfix;
			object[2] = subfix;
			rtnList.add(object);
		}
		return rtnList;
	}

	/**
	 * 其它
	 * @return
	 */
	public List othersSuffix(String converterType, String tagSuffix) {

		List<String> suName = new ArrayList<String>();
		suName.add("html");
		if(CONVERTER_TYPE_WPS_CENTER.equals(converterType)) {
			if("OFD".equals(tagSuffix)) {
				suName.add("pdf");
			}

			if("PDF".equals(tagSuffix)) {
				suName.add("ofd");
			}

		}


		List rtnList = new ArrayList();
		for(String subfix : suName) {
			Object[] object = new Object[3];
			object[0] = subfix;
			object[1] = subfix;
			object[2] = subfix;
			rtnList.add(object);
		}
		return rtnList;
	}
	
	/**
	 * 版式文档
	 * @return
	 */
	public List layoutSuffix() {
		String[] suName = {"pdf"};
		List rtnList = new ArrayList();
		for(String subfix : suName) {
			Object[] object = new Object[3];
			object[0] = subfix;
			object[1] = subfix;
			object[2] = subfix;
			rtnList.add(object);
		}
		return rtnList;
	}
	
	/**
	 * 图片
	 * @return
	 */
	public List pictureSuffix() {
		String[] suName = {"jpg","jpeg","png","bmp"};
		List rtnList = new ArrayList();
		for(String subfix : suName) {
			Object[] object = new Object[3];
			object[0] = subfix;
			object[1] = subfix;
			object[2] = subfix;
			rtnList.add(object);
		}
		return rtnList;
	}
	
	/**
	 * 压缩
	 * @return
	 */
	public List pressSuffix() {
		String[] suName = {"rar","zip","7z"};
		
		
		List rtnList = new ArrayList();
		for(String subfix : suName) {
			Object[] object = new Object[3];
			object[0] = subfix;
			object[1] = subfix;
			object[2] = subfix;
			rtnList.add(object);
		}
		return rtnList;
	}
	
	/**
	 * 文本
	 * @return
	 */
	public List txtSuffix() {
		String[] suName = {"rtf","asx","txt"};
		List rtnList = new ArrayList();
		for(String subfix : suName) {
			Object[] object = new Object[3];
			object[0] = subfix;
			object[1] = subfix;
			object[2] = subfix;
			rtnList.add(object);
		}
		return rtnList;
	}
	
	/**
	 * 图纸
	 * @param converterType
	 * @param tagSuffix
	 * @return
	 */
	public List picturePaperSuffix(String converterType,String tagSuffix) {
		List<String> suName = new ArrayList<String>();
		if(CONVERTER_TYPE_YOZO.equals(converterType)) {
			suName.add("cad");
		}
		else {
			suName.add("dwg");
			suName.add("dwx");
			suName.add("dxf");
		}

		List rtnList = new ArrayList();
		for(String subfix : suName) {
			Object[] object = new Object[3];
			object[0] = subfix;
			object[1] = subfix;
			object[2] = subfix;
			rtnList.add(object);
		}
		return rtnList;
	}
	
	/**
	 * 音频
	 * @return
	 */
	public List audioSuffix() {
		String[] suName = {"wrf","ogg"};
		List rtnList = new ArrayList();
		for(String subfix : suName) {
			Object[] object = new Object[3];
			object[0] = subfix;
			object[1] = subfix;
			object[2] = subfix;
			rtnList.add(object);
		}
		return rtnList;
	}
	
	/**
	 * 视频
	 * @return
	 */
	public List videoSuffix() {
		String[] suName = {"rm","rmvb","mp4","mov","avi","wmv9","wmv","f4v","m4v","asf","flv","3gp","mpg"};
		List rtnList = new ArrayList();
		for(String subfix : suName) {
			Object[] object = new Object[3];
			object[0] = subfix;
			object[1] = subfix;
			object[2] = subfix;
			rtnList.add(object);
		}
		return rtnList;
	}
}

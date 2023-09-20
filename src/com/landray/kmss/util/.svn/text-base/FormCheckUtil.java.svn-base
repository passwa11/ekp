package com.landray.kmss.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.sys.xform.base.model.SysFormTemplate;
import com.landray.kmss.sys.xform.base.service.ISysFormTemplateService;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;


public class FormCheckUtil {

	private static Logger logger = org.slf4j.LoggerFactory.getLogger(FormCheckUtil.class);

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String checkFieldIsExist(RequestContext requestContext,String formTemplateId) {
		Map<String, Object> values = (Map) requestContext
				.getAttribute(INIT_MODELDATA_KEY);
		if (values == null) {
			return setJsonParam(false,MESSAGE_KEY,"当前没有自定义表单字段");
		}
		List<String> list=FormCheckUtil.getFormFields(formTemplateId);
		
		Set<String> keys = values.keySet();
		for (String key : keys) {	
			if("docSubject".equals(key) || "docStatus".equals(key) || "docCreator".equals(key)
					|| "docContent".equals(key) || "docKeyword".equals(key) || "docProperties".equals(key)){
				continue;
			}
			if(!list.contains(key)){
				return setJsonParam(false,MESSAGE_KEY,"'"+key+"'字段不存在");
			}
		}
		return "";
	}
	public static String setJsonParam(boolean isSuccess,String msgKey,String msg){
		JSONObject rtn = new JSONObject();
		if(isSuccess){
			rtn.put(FLAG_KEY, "1");
			rtn.put(msgKey, msg);
		}else{
			logger.error("EKP流程集成报错:"+msg);
			rtn.put(FLAG_KEY, "0");
			rtn.put(msgKey, msg);
		}
		return rtn.toString();
	}
	/**
	 * 根据模板关键字解析表单KEY值
	 * @param keyword
	 * @return
	 */
	private  static List<String> getFormFields(String formTemplateId){
		List<String> keyList = new ArrayList<String>();
		try {
			ISysFormTemplateService sysFormTemplateService = (ISysFormTemplateService)SpringBeanUtil.getBean("sysFormTemplateService");			
			SysFormTemplate template=(SysFormTemplate) sysFormTemplateService.findByPrimaryKey(formTemplateId);
			
			String xml = null;
			//如果是通用表单，需要到通用表单里面获取
			if(template.getFdCommonTemplate()!=null&&template.getFdCommonTemplate().getFdId()!=null) {
				xml=template.getFdCommonTemplate().getFdMetadataXml();
			}else {
				xml = template.getFdMetadataXml();
			}
			
			Document doc = DocumentHelper.parseText(xml);
			Element rootElt = doc.getRootElement(); 
			//解析第一层的extendSimpleProperty
			Iterator iterSimple = rootElt.elementIterator("extendSimpleProperty");
			keyPrase(iterSimple, keyList);
			//解析第一层的extendElementProperty
			Iterator iterElement = rootElt.elementIterator("extendElementProperty");
			keyPrase(iterElement, keyList);
			//解析第一层的extendSubTableProperty  分录
			Iterator iterSubTable = rootElt.elementIterator("extendSubTableProperty");
			while (iterSubTable.hasNext()) {
				Element recordEle = (Element) iterSubTable.next();
				List attrList = recordEle.attributes();
				String key_main = "" ;
				for (int i = 0; i < attrList.size(); i++) {
					Attribute item = (Attribute)attrList.get(i);
					String name = item.getName();
					if("name".equals(name)){
						key_main = item.getValue();
					}
				}
				//解析分录下的extendSimpleProperty
				Iterator iterEntity = recordEle.elementIterator("extendSimpleProperty");
				while(iterEntity.hasNext()){
					Element elemEntity = (Element)iterEntity.next();
					List entityList = elemEntity.attributes();
					for (int j = 0; j < entityList.size(); j++) {
						Attribute itemEntity = (Attribute)entityList.get(j);
						String nameEntity = itemEntity.getName();
						if("name".equals(nameEntity)){
							String key = itemEntity.getValue();
							key = key_main + "." + key ;
							if(StringUtil.isNotNull(key) && !keyList.contains(key)){
								keyList.add(key);
							}
						}
					}
				}
				
				//解析分录下的extendElementProperty
				Iterator iterElementEntity = recordEle.elementIterator("extendElementProperty");
				while(iterElementEntity.hasNext()){
					Element elemEntity = (Element)iterElementEntity.next();
					List entityList = elemEntity.attributes();
					for (int j = 0; j < entityList.size(); j++) {
						Attribute itemEntity = (Attribute)entityList.get(j);
						String nameEntity = itemEntity.getName();
						if("name".equals(nameEntity)){
							String key = itemEntity.getValue();
							key = key_main + "." + key ;
							if(StringUtil.isNotNull(key) && !keyList.contains(key)){
								keyList.add(key);
							}
						}
					}
				}
				
				
				
			}
		} catch (Exception e) {
			logger.error(e.toString());
			e.printStackTrace();
		}
		return keyList;
	}
	public static void keyPrase (Iterator iter ,List keyList){
		while (iter.hasNext()) {
			Element recordEle = (Element) iter.next();
			List attrList = recordEle.attributes();
			for (int i = 0; i < attrList.size(); i++) {
				Attribute item = (Attribute)attrList.get(i);
				String name = item.getName();
				if("name".equals(name)){
					String key = item.getValue();
					if(StringUtil.isNotNull(key) && !keyList.contains(key)){
						keyList.add(key);
					}
				}
			}
		}
	}
	

	public static String INIT_MODELDATA_KEY = "INIT_MODELDATA_KEY";
	public static String MESSAGE_KEY = "msg";
	public static String FLAG_KEY = "flag";
}

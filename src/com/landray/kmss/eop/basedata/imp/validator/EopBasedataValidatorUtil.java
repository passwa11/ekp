package com.landray.kmss.eop.basedata.imp.validator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;

import com.landray.kmss.eop.basedata.imp.EopBasedataImportUtil;
import com.landray.kmss.sys.config.loader.ConfigLocationsUtil;
import com.landray.kmss.util.ResourceUtil;

public class EopBasedataValidatorUtil {
	private static Map<String,EopBasedataValidateContext> validators;
	public static EopBasedataValidateContext getContext(String name) {
		if(validators==null||!validators.containsKey(name)){
			parseConfig();
		}
		return validators.get(name);
	}
	private static void parseConfig(){
		validators = new HashMap<String,EopBasedataValidateContext>();
		String basePath = ConfigLocationsUtil.getWebContentPath();
		String[] dirs = ConfigLocationsUtil.getConfigLocationArray(
				basePath, "validation.xml", basePath);
		for (int i = 0; i < dirs.length; i++) {
			//防止文件重名
			if(dirs[i].indexOf("design-xml")==-1){
				continue;
			}
			Document document = EopBasedataImportUtil.getDocument(dirs[i]);
			Element rootElement = document.getRootElement();
			List<Element> elements = rootElement.elements();
			EopBasedataValidateContext context = null;
			for(Element childrenLV1:elements){
				context = new EopBasedataValidateContext();
				context.setFdBean(childrenLV1.attributeValue("bean"));
				context.setFdName(childrenLV1.attributeValue("name"));
				String []msg = childrenLV1.attributeValue("message").split(":");
				context.setFdMessage(ResourceUtil.getString(msg[1],msg[0]));
				validators.put(context.getFdName(), context);
			}
		}
	}

}

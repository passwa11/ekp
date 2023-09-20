package com.landray.kmss.sys.webservice2.service.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.DocumentException;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

/**
 * @author yezhengping 2018.3.29
 *
 */
public class RestDictModelSelectDialog implements IXMLDataBean {
	@Override
	public List getDataList(RequestContext requestInfo) throws Exception {
		String urlPrefix = requestInfo.getParameter("urlPrefix");
		List rtnVal = new ArrayList();
		if (StringUtil.isNull(urlPrefix)) {
            return rtnVal;
        }
		List<SysDictModel> dictModelList = getDictModelList(urlPrefix);
		for (SysDictModel dictModel : dictModelList) {
			String insertText = ResourceUtil.getString(dictModel
					.getMessageKey(), requestInfo.getLocale());
			Map node = new HashMap();
			node.put("name", insertText);
			node.put("id", dictModel.getModelName());
			rtnVal.add(node);
		}
		return rtnVal;
	}
	
	/**
	 * 返回模块下面所有数据字典域模型列表
	 * 
	 * @param urlPrefix
	 * @return
	 * @throws DocumentException
	 * @throws Exception
	 */
	private List<SysDictModel> getDictModelList(String urlPrefix)
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

}

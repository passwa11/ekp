package com.landray.kmss.sys.webservice2.actions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictAttachmentProperty;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.webservice2.forms.SysWebserviceDictConfigForm;
import com.landray.kmss.sys.webservice2.forms.SysWebserviceRestConfigForm;
import com.landray.kmss.sys.webservice2.service.ISysWebserviceRestConfigService;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

 
/**
 * REST服务配置 Action
 * 
 * @author 
 * @version 1.0 2017-12-21
 */
public class SysWebserviceRestConfigAction extends ExtendAction {
	protected ISysWebserviceRestConfigService sysWebserviceRestConfigService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if(sysWebserviceRestConfigService == null){
			sysWebserviceRestConfigService = (ISysWebserviceRestConfigService)getBean("sysWebserviceRestConfigService");
		}
		return sysWebserviceRestConfigService;
	}
	
	/**
	 * 根据http请求，获取model，将model转化为form并返回。<br>
	 * URL中必须包含fdId参数，该参数为记录id。<br>
	 * 若获取model不成功，则抛出errors.norecord的错误信息。
	 * 
	 * @param form
	 * @param request
	 * @return form对象
	 * @throws Exception
	 */
	@Override
	protected void loadActionForm(ActionMapping mapping, ActionForm form,
								  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		form.reset(mapping, request);
		IExtendForm rtnForm = null;
		String id = request.getParameter("fdId");
		if (!StringUtil.isNull(id)) {
			IBaseModel model = getServiceImp(request).findByPrimaryKey(id,
					null, true);
			if (model != null) {
                rtnForm = getServiceImp(request).convertModelToForm(
                        (IExtendForm) form, model, new RequestContext(request));
            }
		}
		if (rtnForm == null) {
            throw new NoRecordException();
        }
		
		SysWebserviceRestConfigForm restForm = (SysWebserviceRestConfigForm)rtnForm;
		
		List<SysWebserviceDictConfigForm> dictList = (List<SysWebserviceDictConfigForm>)restForm.getFdDictItems();
		
		for(SysWebserviceDictConfigForm dictForm : dictList){
			String fdMainDisplay = dictForm.getFdMainDisplay();
			String fdListDisplay = dictForm.getFdListDisplay();
			
			//重新加载对应messagekey
			if(fdMainDisplay !=null && StringUtil.isNotNull(fdMainDisplay)){ 
				
				JSONObject obj = JSON.parseObject(fdMainDisplay);
				JSONArray array = obj.getJSONArray("propertyList");
				
				if(array.size()>0){
					SysDictModel model = SysDataDict.getInstance().getModel(dictForm.getFdModelName());
					
					List<SysDictAttachmentProperty> attList = model.getAttachmentPropertyList();
					
					Map<String,String> attMessageKeyMap = new HashMap();
					
					for(SysDictAttachmentProperty att : attList){
						if(StringUtil.isNotNull(att.getName())) {
                            attMessageKeyMap.put(att.getName(), att.getMessageKey());
                        }
					}
					
					Map pro = model.getPropertyMap();
					for(int i=0;i<array.size();i++){
						JSONObject j = array.getJSONObject(i);
						String propertyName = j.getString("propertyName");
						if(pro.containsKey(propertyName)){
							SysDictCommonProperty com = (SysDictCommonProperty)pro.get(propertyName);
							if (StringUtil.isNull(com.getMessageKey())) {
                                continue;
                            }
							String fdPropertyText = ResourceUtil.getString(com.getMessageKey(), request.getLocale());
							j.put("propertyText", fdPropertyText);
						}else if(attMessageKeyMap.containsKey(propertyName)){
							if (StringUtil.isNull(attMessageKeyMap.get(propertyName))) {
                                continue;
                            }
							String fdPropertyText = ResourceUtil.getString(attMessageKeyMap.get(propertyName), request.getLocale());
							j.put("propertyText", fdPropertyText);
						}
							
					}
				}
				
				dictForm.setFdMainDisplay(obj.toString());
			}
			
			
			if(fdListDisplay !=null && StringUtil.isNotNull(fdListDisplay)){
				
				JSONObject obj = JSON.parseObject(fdListDisplay);
				JSONArray array = obj.getJSONArray("propertyList");
				
				if(array.size()>0){
					SysDictModel model = SysDataDict.getInstance().getModel(dictForm.getFdModelName());
					
					List<SysDictAttachmentProperty> attList = model.getAttachmentPropertyList();
					
					Map<String,String> attMessageKeyMap = new HashMap();
					
					for(SysDictAttachmentProperty att : attList){
						if(StringUtil.isNotNull(att.getName())) {
                            attMessageKeyMap.put(att.getName(), att.getMessageKey());
                        }
					}
					
					Map pro = model.getPropertyMap();
					for(int i=0;i<array.size();i++){
						JSONObject j = array.getJSONObject(i);
						String propertyName = j.getString("propertyName");
						if(pro.containsKey(propertyName)){
							SysDictCommonProperty com = (SysDictCommonProperty)pro.get(propertyName);
							if (StringUtil.isNull(com.getMessageKey())) {
                                continue;
                            }
							String fdPropertyText = ResourceUtil.getString(com.getMessageKey(), request.getLocale());
							j.put("propertyText", fdPropertyText);
						}else if(attMessageKeyMap.containsKey(propertyName)){
							if (StringUtil.isNull(attMessageKeyMap.get(propertyName))) {
                                continue;
                            }
							String fdPropertyText = ResourceUtil.getString(attMessageKeyMap.get(propertyName), request.getLocale());
							j.put("propertyText", fdPropertyText);
						}
							
					}
				}
				
				dictForm.setFdListDisplay(obj.toString());
			}
		}
		
		request.setAttribute(getFormName(restForm, request), restForm);
	}
}


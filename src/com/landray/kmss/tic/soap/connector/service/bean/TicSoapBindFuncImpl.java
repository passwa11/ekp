/**
 * 
 */
package com.landray.kmss.tic.soap.connector.service.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.tic.soap.connector.impl.TicSoapProjectFactory;
import com.landray.kmss.tic.soap.connector.interfaces.ITicSoap;
import com.landray.kmss.tic.soap.connector.model.TicSoapSetting;
import com.landray.kmss.tic.soap.connector.service.ITicSoapSettingService;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

/**
 * @author 邱建华
 * @version 1.0
 * @2012-8-15
 */
public class TicSoapBindFuncImpl implements IXMLDataBean {
	private ITicSoapSettingService ticSoapSettingService;
	
	public void setTicSoapSettingService(
			ITicSoapSettingService ticSoapSettingService) {
		this.ticSoapSettingService = ticSoapSettingService;
	}
	
	@Override
    public List getDataList(RequestContext requestInfo) throws Exception {
		List<Map<String, String>> rtnList = new ArrayList<Map<String, String>>();
		try {
			String serviceId = requestInfo.getParameter("serviceId");
			String localPart = requestInfo.getParameter("wsBindFunc");
			String soapversion = requestInfo.getParameter("soapversion");
			String curFdId=requestInfo.getParameter("curId");
			if (StringUtil.isNotNull(serviceId)) {
				TicSoapSetting TicSoapSetting = (TicSoapSetting) ticSoapSettingService
						.findByPrimaryKey(serviceId);
				TicSoapProjectFactory.cleanCacheByServiceId(serviceId);

				ITicSoap TicSoap = (ITicSoap) SpringBeanUtil.getBean("ticSoap");
				String xml=null;
				if(TicSoapSetting.getFdProtectWsdl()){
					 xml = TicSoap.toAllXmlTemplate(TicSoapSetting, localPart, soapversion);
				}else{
					 xml = TicSoap.toAllXmlTemplate(TicSoapSetting, localPart, soapversion);
				}
				
				Pattern p = Pattern.compile(">\\s*[?]<");
				Matcher m = p.matcher(xml);
				xml = m.replaceAll("><");
				
				String[] mergeXml=new String[4];
				mergeXml[0]="<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
				mergeXml[1]="<web ID=\"!{fdId}\">".replace("!{fdId}", curFdId);
				mergeXml[2]=xml;
				mergeXml[3]="</web>";
				String result=StringUtils.join(mergeXml);
				Map<String, String> map = new HashMap<String, String>();
				map.put("xml", result);
				rtnList.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rtnList;
	}
	
}

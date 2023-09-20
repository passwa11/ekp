package com.landray.kmss.tic.soap.connector.actions;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.tic.core.cacheindb.util.ToolUtil;
import com.landray.kmss.tic.core.common.util.TicCommonUtil;
import com.landray.kmss.tic.core.log.constant.TicCoreLogConstant;
import com.landray.kmss.tic.core.log.model.TicCoreLogMain;
import com.landray.kmss.tic.core.log.service.ITicCoreLogMainService;
import com.landray.kmss.tic.core.util.DOMHelper;
import com.landray.kmss.tic.core.util.DomUtil;
import com.landray.kmss.tic.core.util.XmlConvertToJsonUtil;
import com.landray.kmss.tic.soap.connector.forms.TicSoapQueryForm;
import com.landray.kmss.tic.soap.connector.interfaces.ITicSoap;
import com.landray.kmss.tic.soap.connector.model.TicSoapMain;
import com.landray.kmss.tic.soap.connector.service.ITicSoapMainService;
import com.landray.kmss.tic.soap.connector.service.ITicSoapQueryService;
import com.landray.kmss.tic.soap.connector.util.header.HeaderOperation;
import com.landray.kmss.tic.soap.connector.util.header.SoapInfo;
import com.landray.kmss.tic.soap.connector.util.xml.ParseSoapXmlUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONArray;
import com.landray.kmss.tic.core.mapping.constant.Constant;


/**
 * 函数查询 Action
 * 
 * @author 
 * @version 1.0 2012-08-28
 */
public class TicSoapQueryAction extends ExtendAction {
	protected ITicSoapQueryService TicSoapQueryService;

	protected ITicCoreLogMainService ticCoreLogMainService;

	protected ITicCoreLogMainService getTicCoreLogMainService() {
		if (ticCoreLogMainService == null) {
			ticCoreLogMainService = (ITicCoreLogMainService) getBean(
					"ticCoreLogMainService");
		}
		return ticCoreLogMainService;
	}

	@Override
    protected IBaseService getServiceImp(HttpServletRequest request) {
		if(TicSoapQueryService == null) {
			TicSoapQueryService = (ITicSoapQueryService)getBean("ticSoapQueryService");
		}
		return TicSoapQueryService;
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
			HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		String funcId = request.getParameter("funcId");
		hqlInfo.setWhereBlock("ticSoapQuery.ticSoapMain.fdId=:ticSoapMainFdId");
		hqlInfo.setParameter("ticSoapMainFdId", funcId);
	}
	
	/**
	 * 打开JSON接口预览页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @author 严海星
	 * 2018年11月27日
	 */
	public ActionForward getJsonResultView(ActionMapping mapping,
			ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		TicSoapQueryForm ticSoapQueryForm = (TicSoapQueryForm)form;
		return getActionForward("viewJsonResult", mapping, ticSoapQueryForm, request,
				response);
	}

	public ActionForward getXmlResult(ActionMapping mapping,
			ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		KmssMessages messages = new KmssMessages();
		TicSoapQueryForm ticSoapQueryForm = (TicSoapQueryForm)form;
		String xmlString=ticSoapQueryForm.getDocInputParam();
		String outString=ticSoapQueryForm.getDocOutputParam();
		String soapuiMainId =ticSoapQueryForm.getTicSoapMainId();
		if (StringUtil.isNotNull(outString)) {
			xmlString = replaceXml(xmlString + outString, soapuiMainId);
		}
		ITicSoapMainService erpMainService=(ITicSoapMainService)SpringBeanUtil.getBean("ticSoapMainService");
		TicCoreLogMain log = new TicCoreLogMain();
		log.setFdLogType(Constant.FD_TYPE_SOAP);//Soap集成方法
		log.setFuncId(soapuiMainId);
		log.setFdStartTime(new Date());
		log.setFdExecSource(
				TicCoreLogConstant.TIC_CORE_LOG_SOURCE_TEST + "");

		try {
			TicSoapMain ticSoapMain=(TicSoapMain)erpMainService.findByPrimaryKey(soapuiMainId);
			log.setFdAppType(ticSoapMain.getFdAppType());
			log.setFuncName(ticSoapMain.getFdName());
			log.setFdUrl(ticSoapMain.getTicSoapSetting().getFdWsdlUrl());
			ITicSoap ticSoap=(ITicSoap)SpringBeanUtil.getBean("ticSoap");
			// SoapInfo设值
			SoapInfo soapInfo=new SoapInfo();
			soapInfo.setTicSoapMain(ticSoapMain);
			//soapInfo.setRequestXml(xmlString);
			String inputXml = HeaderOperation.allToPartXml(xmlString, "//Input");
			log.setFdSourceFuncInXml(inputXml);
			Document doc =DOMHelper.parseXmlString(xmlString);
			soapInfo.setRequestDocument(doc);
			Document rtnDoc=ticSoap.inputToOutputDocument(soapInfo);
			
			String resultXml =DOMHelper.nodeToString(rtnDoc,true);
			// System.out.println("end:"+resultXml);
			
			//String resultXml = ticSoap.inputToAllXml(soapInfo);
			// resultXml = replaceXml(resultXml, soapuiMainId);

			// 把Input,Output,Fault分别拿出来
			//String inputXml = HeaderOperation.allToPartXml(resultXml, "//Input");
			//log.setFdSourceFuncInXml(inputXml);
			if (ParseSoapXmlUtil.isFault(resultXml)) {
				String faultXml = HeaderOperation.allToPartXml(resultXml, "//Fault");
				log.setFdMessages(faultXml);
			} else {
				String outputXml = HeaderOperation.allToPartXml(resultXml, "//Output");
				log.setFdSourceFuncOutXml(outputXml);
				//log.setFdExportParOri(outputXml);
			}
			log.setFdEndTime(new Date());
			
			request.setAttribute("resultXml",resultXml);
			try{
				String resultJsonStr = XmlConvertToJsonUtil.soapXmlConvertToJson(resultXml);
				ticSoapQueryForm.setFdJsonResult(resultJsonStr);
			}catch (Exception e) {
				e.printStackTrace();
			}
			log.setFdIsErr("0");
			
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
			log.setFdIsErr("1");
			log.setFdMessages(TicCommonUtil.getExceptionToString(e));
		}finally {
			getTicCoreLogMainService().saveTicCoreLogMain(log);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, ticSoapQueryForm, request,
					response);
		} else {
			return getActionForward("viewResult", mapping, ticSoapQueryForm, request,
					response);
		}
	}
	
	private String replaceXml(String resultXml,String mainId){
		String allXmlStr="<?xml version=\"1.0\" encoding=\"UTF-8\"?><web ID=\"!{ID}\" timestamp=\"!{timestamp}\">!{content}</web>";
		allXmlStr =allXmlStr.replace("!{ID}", mainId).replace("!{content}", resultXml).replace("!{timestamp}", new Date().getTime()+"");
		return allXmlStr;
	}
	
	/**
	 * 点击进入查询记录时,再点重新查询所要进入的方法
	 * (就是把查询记录值保存后，继续查询)
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward reQuery(ActionMapping mapping,
			ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		KmssMessages messages = new KmssMessages();
		TicSoapQueryForm TicSoapQueryForm = (TicSoapQueryForm)form;
		try {
			String fdMainId = request.getParameter("fdMainId");
//			TicSoapQuery TicSoapQuery = (TicSoapQuery) getServiceImp(request).
//					findByPrimaryKey(fdId);
			String idXml = TicSoapQueryForm.getDocInputParam();
			if (!idXml.contains("<Input>")) {

				idXml = "<Input>"
						+ idXml + "</Input><Output>"
						+ TicSoapQueryForm.getDocOutputParam()
						+ "</Output><Fault>"
						+ TicSoapQueryForm.getDocFaultInfo() + "</Fault>";
				idXml = replaceXml(idXml, fdMainId);
				TicSoapQueryForm.setDocOutputParam("");
			}
			request.setAttribute("ticSoapMainId", fdMainId);
			request.setAttribute("idXml", idXml);
			request.setAttribute("docOutputParam", TicSoapQueryForm.getDocOutputParam());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, TicSoapQueryForm, request,
					response);
		} else {
			return getActionForward("reViewQuery", mapping, TicSoapQueryForm, request,
					response);
		}
	}
	
	public ActionForward transCacheData(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		KmssMessages messages = new KmssMessages();
		TicSoapQueryForm ticSoapQueryForm = (TicSoapQueryForm) form;
		String xmlString = ticSoapQueryForm.getDocInputParam();
		String outString = ticSoapQueryForm.getDocOutputParam();
		String soapuiMainId = ticSoapQueryForm.getTicSoapMainId();
		if (StringUtil.isNotNull(outString)) {
			xmlString = replaceXml(xmlString + outString, soapuiMainId);
		}

		ITicSoapMainService erpMainService = (ITicSoapMainService) SpringBeanUtil
				.getBean("ticSoapMainService");
		try {
			TicSoapMain ticSoapMain = (TicSoapMain) erpMainService.findByPrimaryKey(soapuiMainId);
			ITicSoap ticSoap = (ITicSoap) SpringBeanUtil.getBean("ticSoap");
			// SoapInfo设值
			SoapInfo soapInfo = new SoapInfo();
			soapInfo.setTicSoapMain(ticSoapMain);
			// soapInfo.setRequestXml(xmlString);

			Document doc = DOMHelper.parseXmlString(xmlString);
			soapInfo.setRequestDocument(doc);
			Document rtnDoc = ticSoap.inputToOutputDocument(soapInfo);

			// String resultXml =DOMHelper.nodeToString(rtnDoc,true);
			NodeList nodeList = DomUtil.selectNode("//Output/Envelope/Body/*", rtnDoc);
			Node node = ToolUtil.findCacheDataNode(nodeList);
			String interData = "";
			if (node != null) {
				interData = node.getTextContent();
				request.setAttribute("dataList", interData);
			}
			request.setAttribute("transImpl", ticSoapMain.getFdTransCode());

			String transImpl = request.getParameter("transImpl");
			if (StringUtil.isNotNull(transImpl) && StringUtil.isNotNull(interData)) {
				Object value = ToolUtil.transDataByImpl(transImpl, interData);
				if (value != null) {
					if (value instanceof String) {
						request.setAttribute("transData", value.toString());
					}else if (value instanceof JSONArray) {
						JSONArray arr = (JSONArray)value;
						request.setAttribute("transData", arr.toString(2));
					}else {
						request.setAttribute("transData", value.toString());
					}
				}
			}

			// ITicSoapRtn rtn = ticSoap.inputToOutputRtn(soapInfo);
			// tem.out.println("getRtnContent:"+rtn.getRtnContent());

			// tem.out.println("end:"+resultXml);
			// NodeList nodeList = DomUtil.selectNode("//Envelope/Body/*/return", outDoc);

		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, ticSoapQueryForm, request, response);
		} else {
			return getActionForward("transCacheDataEdit", mapping, ticSoapQueryForm, request, response);
		}
	}

	
	public ActionForward saveTransCode(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-update", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			ITicSoapMainService erpMainService = (ITicSoapMainService) SpringBeanUtil
					.getBean("ticSoapMainService");
			String soapuiMainId = request.getParameter("ticSoapMainId");
			TicSoapMain ticSoapMain = (TicSoapMain) erpMainService.findByPrimaryKey(soapuiMainId);
			
			String transImpl = request.getParameter("transImpl");
			ticSoapMain.setFdTransCode(transImpl);
			erpMainService.update(ticSoapMain);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-update", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return getActionForward("transCacheDataEdit", mapping, form, request, response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);			
			return getActionForward("success", mapping, form, request, response);
		}
	}
	
	@Override
    public ActionForward save(ActionMapping mapping, ActionForm form,
                              HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-save", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			TicSoapQueryForm ticSoapQueryForm = (TicSoapQueryForm) form;
			ticSoapQueryForm.setFdId(null);
			String resultXml = request.getParameter("resultXml");

			// 把Input,Output,Fault分别拿出来
			String inputXml = HeaderOperation.allToPartXml(resultXml,
					"//Input");
			ticSoapQueryForm.setDocInputParam(inputXml);
			if (ParseSoapXmlUtil.isFault(resultXml)) {
				String faultXml = HeaderOperation.allToPartXml(resultXml,
						"//Fault");
				ticSoapQueryForm.setDocFaultInfo(faultXml);
			} else {
				String outputXml = HeaderOperation.allToPartXml(resultXml,
						"//Output");
				ticSoapQueryForm.setDocOutputParam(outputXml);
			}

			getServiceImp(request).add(ticSoapQueryForm,
					new RequestContext(request));
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-save", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return getActionForward("edit", mapping, form, request, response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("success", mapping, form, request,
					response);
		}
	}

}


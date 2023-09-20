package com.landray.kmss.km.smissive.actions;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.actions.SimpleCategoryNodeAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.km.smissive.service.IKmSmissiveMainService;
import com.landray.kmss.km.smissive.service.IKmSmissiveTemplateService;
import com.landray.kmss.km.smissive.service.spring.KmSmissivePortlet;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.portal.util.PortletTimeUtil;
import com.landray.kmss.sys.simplecategory.service.ISysSimpleCategoryService;
import com.landray.kmss.sys.workflow.interfaces.SysFlowUtil;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 创建日期 2013-11-16
 * 
 * @author 朱湖强
 */
public class KmSmissiveMainPortletAction extends SimpleCategoryNodeAction

{
	protected IKmSmissiveMainService kmSmissiveMainService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (kmSmissiveMainService == null) {
            kmSmissiveMainService = (IKmSmissiveMainService) getBean("kmSmissiveMainService");
        }
		return kmSmissiveMainService;
	}

	// 获取类别
	protected IKmSmissiveTemplateService kmSmissiveTemplateService;

	protected IKmSmissiveTemplateService getTreeServiceImp() {
		if (kmSmissiveTemplateService == null) {
            kmSmissiveTemplateService = (IKmSmissiveTemplateService) getBean("kmSmissiveTemplateService");
        }
		return kmSmissiveTemplateService;
	}

	@Override
	protected String getParentProperty() {
		return "fdTemplate";
	}

	@Override
	public ISysSimpleCategoryService getSysSimpleCategoryService() {
		return (ISysSimpleCategoryService) getTreeServiceImp();
	}
	
	public ActionForward listPortlet(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-listCalendar", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String myFlow = request.getParameter("myFlow");
			HQLInfo hqlInfo = new HQLInfo();
			String param = request.getParameter("rowsize");
			int rowsize = 6;
			if (!StringUtil.isNull(param)) {
                rowsize = Integer.parseInt(param);
            }
			if (StringUtil.isNotNull(myFlow)) {
				getMyFlowDate(request, myFlow,hqlInfo);
			}
			 //时间范围参数
		      String scope=request.getParameter("scope");
		      if(StringUtil.isNotNull(scope)&&!"no".equals(scope)){
		        String block=hqlInfo.getWhereBlock();
		        hqlInfo.setWhereBlock(StringUtil.linkString(block, " and ","kmSmissiveMain.docCreateTime > :fdStartTime"));
		        hqlInfo.setParameter("fdStartTime",PortletTimeUtil.getDateByScope(scope));
		      }
			hqlInfo.setOrderBy("kmSmissiveMain.docCreateTime desc");
			hqlInfo.setRowSize(rowsize);
			hqlInfo.setGetCount(false);
			Page page = getServiceImp(request).findPage(hqlInfo);
			if (UserOperHelper.allowLogOper("listPortlet", getServiceImp(request).getModelName())) {
				if (!ArrayUtil.isEmpty(page.getList()) && page.getList().get(0) instanceof IBaseModel) {
					UserOperContentHelper.putFinds(page.getList());
				}
			}
			request.setAttribute("queryPage", page);
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-listCalendar", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("listPortlet", mapping, form, request,
					response);
		}
	}

	private void getMyFlowDate(HttpServletRequest request, String myFlow,HQLInfo hqlInfo)
		throws Exception {
				String eventType = "";
				if ("executed".equals(myFlow)) {
					eventType = ResourceUtil.getString("km-smissive:smissive.approved.my");
					SysFlowUtil.buildLimitBlockForMyApproved("kmSmissiveMain", hqlInfo);
				} else if ("unExecuted".equals(myFlow)) {
					eventType = ResourceUtil.getString("km-smissive:smissive.approval.my");
					SysFlowUtil.buildLimitBlockForMyApproval("kmSmissiveMain", hqlInfo);
				} else if("myCreate".equals(myFlow)){
					eventType = ResourceUtil.getString("km-smissive:smissive.create.my");
					String whereBlock = hqlInfo.getWhereBlock();
					whereBlock = StringUtil.linkString(whereBlock, " and ", " kmSmissiveMain.docCreator.fdId=:creatorId");
					hqlInfo.setParameter("creatorId", UserUtil.getUser().getFdId());
					hqlInfo.setWhereBlock(whereBlock);
				}
				if (UserOperHelper.allowLogOper("listPortlet", getServiceImp(request).getModelName())) {
					UserOperHelper.setEventType(eventType);
				}
		}

	
	protected KmSmissivePortlet kmSmissivePortlet;
	
	protected KmSmissivePortlet getKmSmissvePortlet(HttpServletRequest request) {
		if (kmSmissivePortlet == null) {
            kmSmissivePortlet = (KmSmissivePortlet) getBean("kmSmissivePortlet");
        }
		return kmSmissivePortlet;
	}

	
	public ActionForward getlatest(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception {
		TimeCounter.logCurrentTime("Action-getNewsWithImg", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			List mainList = getKmSmissvePortlet(request).getDataList(new RequestContext(request));
			
			//JSONArray rtnArray = this.toJsonArray(mainList,request);
				
			//request.setAttribute("lui-source", rtnArray);
			request.setAttribute("queryPage", mainList.get(0));
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-getImg", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("lui-failure", mapping, form, request, response);
		} else {
			return getActionForward("getlatest", mapping, form, request, response);
		}
	}
	
	protected JSONArray toJsonArray(List mainList,HttpServletRequest request) throws Exception{
		JSONArray rtnArray = new JSONArray();
			if(mainList != null) {
				for(int i = 0; i < mainList.size(); i++) {
					Map map = (Map)mainList.get(i);
					JSONObject json = new JSONObject();
					json.put("label", map.get("text"));
					json.put("created", map.get("publishTime"));
					json.put("listIcon", "mui-bookOpenLogo");
					json.put("href", map.get("href"));
					json.put("creator", map.get("docDeptName"));
					json.put("otherText", map.get("docReadCount"));
					
					rtnArray.add(json);
				}
			}
			return rtnArray;
		}
}

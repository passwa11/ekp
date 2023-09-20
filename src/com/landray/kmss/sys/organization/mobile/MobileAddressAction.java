package com.landray.kmss.sys.organization.mobile;

import java.io.PrintWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.HashedMap;

import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.person.interfaces.PersonInfoServiceGetter;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssPinyinUtil;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class MobileAddressAction extends BaseAction {

	protected IMobileAddressService mobileAddressService;

	public IMobileAddressService getMobileAddressService() {
		if (mobileAddressService == null) {
			mobileAddressService = (IMobileAddressService) SpringBeanUtil
					.getBean("mobileAddressService");
		}
		return mobileAddressService;
	}

	private ActionForward commonHandler(JSON data, ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			request.setAttribute("lui-source", data);
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("lui-source");
		}
	}

	/**
	 * 获取当前用户所在部门
	 */
	public ActionForward parentId(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> map = new HashedMap();
		SysOrgPerson user = UserUtil.getUser(request);
		if(user.getFdParent()!=null){
			map.put("parentId", user.getFdParent().getFdId());
			map.put("parentName", user.getFdParent().getFdName());
		}
		return commonHandler(JSONObject
				.fromObject(map), mapping, form, request, response);
	}

	/**
	 * 获取同部门成员
	 */
	public ActionForward deptMemberList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List members = getMobileAddressService()
				.getDeptMembers(new RequestContext(request));
		return commonHandler(JSONArray.fromObject(members), mapping,
				form, request, response);
	}

	/**
	 * 获取我的下属路径
	 */
	public ActionForward subordinatePath(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List path = getMobileAddressService()
				.getSubordinatePath(new RequestContext(request));
		return commonHandler(JSONArray.fromObject(path), mapping,
				form, request, response);
	}

	/**
	 * 获取下属组织架构列表
	 */
	public ActionForward subordinateList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List subordinateOrgs = getMobileAddressService()
				.getAllSubordinateOrgs(new RequestContext(request));
		return commonHandler(JSONArray.fromObject(subordinateOrgs), mapping,
				form, request, response);
	}

	/**
	 * 获取公共群组路径
	 */
	public ActionForward commonGroupPath(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List commonGroupPath = getMobileAddressService()
				.commonGroupPath(new RequestContext(request));
		return commonHandler(JSONArray.fromObject(commonGroupPath), mapping,
				form, request, response);
	}

	/**
	 * 获取公共群组分类列表
	 */
	public ActionForward commonGroupCate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List commonGroupCates = getMobileAddressService()
				.commonGroupCate(new RequestContext(request));
		return commonHandler(JSONArray.fromObject(commonGroupCates), mapping,
				form, request, response);
	}

	/**
	 * 获取公共群组列表
	 */
	public ActionForward commonGroupList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List commonGroup = getMobileAddressService()
				.commonGroupList(new RequestContext(request));
		return commonHandler(JSONArray
				.fromObject(commonGroup), mapping, form, request, response);
	}

	/**
	 * 获取个人群组列表
	 */
	public ActionForward myGroupList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List myGroup = getMobileAddressService()
				.myGroupList(new RequestContext(request));
		return commonHandler(JSONArray
				.fromObject(myGroup), mapping, form, request, response);
	}

	public ActionForward addressList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			List addressList = getMobileAddressService().addressList(
					new RequestContext(request));
			request.setAttribute("lui-source", JSONArray
					.fromObject(addressList));
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("lui-source");
		}

	}

	public ActionForward detailList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			List detailList = getMobileAddressService().detailList(
					new RequestContext(request));
			request
					.setAttribute("lui-source", JSONArray
							.fromObject(detailList));
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("lui-source");
		}

	}

	public ActionForward searchList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			List searchList = getMobileAddressService().searchList(
					new RequestContext(request));
			request
					.setAttribute("lui-source", JSONArray
							.fromObject(searchList));
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("lui-source");
		}

	}

	public ActionForward personList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			JSONObject result = getMobileAddressService().personList(
					new RequestContext(request));
			request.setAttribute("lui-source", JSONObject.fromObject(result));
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("lui-source");
		}
	}

	public ActionForward personDetailList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			JSONObject result = getMobileAddressService().personDetailList(
					new RequestContext(request));
			request.setAttribute("lui-source", JSONObject.fromObject(result));
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("lui-source");
		}
	}

	public ActionForward personIcon(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			String personId = request.getParameter("personId");
			String src = PersonInfoServiceGetter
					.getPersonHeadimageUrl(personId);
			request.getRequestDispatcher(src).forward(request, response);
		} catch (Exception e) {
			String src = PersonInfoServiceGetter.DEFAULT_IMG;
			request.getRequestDispatcher(src).forward(request, response);
		}
		return null;
	}

	public ActionForward addCustomList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			List addCustomList = getMobileAddressService().addCustomList(
					new RequestContext(request));
			JSONArray array = new JSONArray();
			if(addCustomList!=null){
				// 排序号排序
				for (Object object : addCustomList) {
				        array.add(object);
				 }
				//拼音排序
				Collections.sort(array, new PinyinComparator());
			}
			request.setAttribute("lui-source", array);
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("lui-source");
		}
	}
	
	/**
	 * 汉字按照拼音排序的比较器
	 */
	public class PinyinComparator implements Comparator<Object> {
		@Override
		public int compare(Object o1, Object o2) { 
			
			if(!o1.toString().contains("pinyin")){
				return 0;
			}
			if(!o2.toString().contains("pinyin")){
				return 0;
			}
			if(((JSONObject) o1).getString("order").equals(((JSONObject) o2).getString("order"))){
				char c1 = ((JSONObject) o1).getString("pinyin").charAt(0);
				char c2 = ((JSONObject) o2).getString("pinyin").charAt(0);
				return KmssPinyinUtil.toPinyin(c1,true).compareTo(KmssPinyinUtil.toPinyin(c2,true)); 
			}
			return 0;
	    }
	}


	public ActionForward include(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		String personId = request.getParameter("personId");
		String selfId = request.getParameter("selfId");
		ISysOrgCoreService sysOrgCoreService = (ISysOrgCoreService) SpringBeanUtil
				.getBean("sysOrgCoreService");
		if (StringUtil.isNotNull(personId)) {
			List<String> l = ArrayUtil.convertArrayToList(personId.split(";"));
			List<String> personIds = sysOrgCoreService.expandToPersonIds(l);
			if (personIds.contains(selfId)) {
				out.print(true);
			} else {
				out.print(false);
			}
		} else {
			out.print(false);
		}
		out.close();
		return null;
	}
}

package com.landray.kmss.sys.portal.actions.anonym;

import com.landray.kmss.common.actions.DataAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.constant.SysAuthConstant.CheckType;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.portal.model.SysPortalTopic;
import com.landray.kmss.sys.portal.service.ISysPortalTopicService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 匿名推荐专题
 */
public class SysPortalTopicAnonymAction extends DataAction {
	protected ISysPortalTopicService sysPortalTopicService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (sysPortalTopicService == null) {
            sysPortalTopicService = (ISysPortalTopicService) getBean("sysPortalTopicService");
        }
		return sysPortalTopicService;
	}

	@Override
	protected String getParentProperty() {
		return null;
	}

	@Override
	protected IBaseService getCategoryServiceImp(HttpServletRequest request) {
		return null;
	}

	public ActionForward portlet(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-getPortalTopic", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String fdIds = request.getParameter("fdIds");
			String s_rowsize = request.getParameter("rowsize");
			String orderby = request.getParameter("orderby");
			String ordertype = request.getParameter("ordertype");
			boolean isReserve = false;
			if (ordertype != null && "down".equalsIgnoreCase(ordertype)) {
				isReserve = true;
			}
			int rowsize = SysConfigParameters.getRowSize();
			if (s_rowsize != null && s_rowsize.length() > 0) {
				rowsize = Integer.parseInt(s_rowsize);
			}
			if (isReserve) {
                orderby += " desc";
            }
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setOrderBy(orderby);
			hqlInfo.setRowSize(rowsize);
			if (StringUtil.isNotNull(fdIds)) {
				String[] ids = fdIds.split(",");
				List<String> idList = ArrayUtil.convertArrayToList(ids);
				String whereBlock = " sysPortalTopic.fdId in (:fdIds)";
				hqlInfo.setParameter("fdIds", idList);
				hqlInfo.setWhereBlock(whereBlock);
			}
			hqlInfo.setCheckParam(CheckType.AllCheck, SysAuthConstant.AuthCheck.SYS_NONE);
			List introList = getServiceImp(request).findPage(hqlInfo).getList();
			JSONArray rtnArray = new JSONArray();
			request.setAttribute("lui-source", rtnArray);
			if(null!=introList&&introList.size()>0){
				List anonymList = new ArrayList();
				for (Iterator iterator = introList.iterator(); iterator.hasNext();) {
					SysPortalTopic object = (SysPortalTopic) iterator.next();
					if(null==object.getFdAnonymous()?false:object.getFdAnonymous()){
						anonymList.add(object);
					}
				}
				rtnArray = this.getIntroArray(request, anonymList);
			}
			request.setAttribute("lui-source", rtnArray);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-getPortalTopic", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("lui-failure", mapping, form, request, response);
		} else {
			return getActionForward("lui-source", mapping, form, request, response);
		}
	}

	protected JSONArray getIntroArray(HttpServletRequest request, List introList) throws Exception {
		JSONArray rtnArray = new JSONArray();
		if (introList != null) {
			for (int i = 0; i < introList.size(); i++) {
				SysPortalTopic s = (SysPortalTopic) introList.get(i);
				JSONObject json = new JSONObject();
				json.put("text", s.getFdName());
				json.put("image", getImgUrl(s, request));
				json.put("href", s.getFdTopUrl());
				rtnArray.add(json);
			}
		}
		return rtnArray;
	}

	public String getImgUrl(SysPortalTopic portalTopic, HttpServletRequest request) throws Exception {
		// 默认图
		String imgAttUrl = "/sys/portal/sys_portal_topic/resource/image/default.jpg";
		if (portalTopic != null) {
			String fdId = portalTopic.getFdId();
			SysAttMain imgAttMain = null;
			ISysAttMainCoreInnerService sysAttMainCoreInnerService = (ISysAttMainCoreInnerService) SpringBeanUtil
					.getBean("sysAttMainService");
			List<SysAttMain> attMainList = sysAttMainCoreInnerService
					.findByModelKey("com.landray.kmss.sys.portal.model.SysPortalTopic", fdId, "sysPortalTopic_fdKey");
			// 如果上传了图片
			if (attMainList.size() > 0) {
				imgAttMain = attMainList.get(0);
				imgAttUrl = "/sys/portal/anonym/sysPortalTopicAnonym.do?method=dataDownload&fdId=" + imgAttMain.getFdId();
			}
			//如果fdimg不为空，则是素材库的图片
			if(StringUtil.isNotNull(portalTopic.getFdImg())){
				imgAttUrl=portalTopic.getFdImg();
				/*// 通过base64转化图片 fdFileId
				InputStream is = sysAttMainCoreInnerService.readAttachment("17db2476b4de037df219a4d4a36bfcf2");
				byte[] data = null;
				try {
					if (is != null) {
						data = IOUtils.toByteArray(is);
					} else {
						data = null;
					}
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						if (is != null) {
							is.close();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (data != null) {
					String encode = Base64.encodeBase64String(data);
					return "data:image/png;base64," + encode;
				}*/
				imgAttUrl = imgAttUrl.replace("sys/attachment/sys_att_main/sysAttMain.do?method=download","sys/print/data/sysPrintWordData.do?method=loadImage");
			}
		}
		return imgAttUrl;
	}




	/**
	 * 匿名附件代理接口
	 * 
	 * 附件下载接口 ，参数同附件机制download接口
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 跳转的展现页或者数据集
	 * @throws Exception
	 */
	public ActionForward dataDownload(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String fdId = request.getParameter("fdId");

		ISysAttMainCoreInnerService sysAttMainService = (ISysAttMainCoreInnerService) SpringBeanUtil
				.getBean("sysAttMainService");

		IBaseModel baseModel = sysAttMainService.findByPrimaryKey(fdId);

		if (null != baseModel) {
			/**
			 * 匿名模型下的附加才能代理展示
			 */
			SysAttMain sysAttMain = (SysAttMain) baseModel;
			//String fdModelName = sysAttMain.getFdModelName();
			if(StringUtil.isNotNull(sysAttMain.getFdModelId())){
				SysPortalTopic sysPortalTopic = (SysPortalTopic)getServiceImp(request).findByPrimaryKey(sysAttMain.getFdModelId());
				if(sysPortalTopic!=null){
					if(sysPortalTopic.getFdAnonymous()) {
                        request.getRequestDispatcher("/sys/attachment/sys_att_main/sysAttMain.do?method=download")
                                .forward(request, response);
                    }
				}
			}
		}

		return null;
	}
}

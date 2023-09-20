package com.landray.kmss.third.ding.action;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.third.ding.forms.ThirdDingWorkForm;
import com.landray.kmss.third.ding.model.ThirdDingWork;
import com.landray.kmss.third.ding.service.IThirdDingWorkService;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONObject;

public class ThirdDingWorkAction extends ExtendAction {

    private IThirdDingWorkService thirdDingWorkService;

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdDingWorkAction.class);

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (thirdDingWorkService == null) {
            thirdDingWorkService = (IThirdDingWorkService) getBean("thirdDingWorkService");
        }
        return thirdDingWorkService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, ThirdDingWork.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        com.landray.kmss.third.ding.util.ThirdDingUtil.buildHqlInfoDate(hqlInfo, request, com.landray.kmss.third.ding.model.ThirdDingWork.class);
        com.landray.kmss.third.ding.util.ThirdDingUtil.buildHqlInfoModel(hqlInfo, request);
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ThirdDingWorkForm thirdDingWorkForm = (ThirdDingWorkForm) super.createNewForm(mapping, form, request, response);
        ((IThirdDingWorkService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return thirdDingWorkForm;
    }

	public void checkModel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("UTF-8");
		JSONObject result = new JSONObject();
		String data = request.getParameter("data");
		String fdModelName = request.getParameter("fdModelName");
		String addOrUpdate = request.getParameter("addOrUpdate");
		String fdId = request.getParameter("fdId");
		logger.debug("data:" + data + "  fdModelName:" + fdModelName
				+ "  addOrUpdate:" + addOrUpdate + "  fdId:" + fdId);
		if (StringUtil.isNotNull(data)) {
			List<ThirdDingWork> list = getServiceImp(request)
					.findList(new HQLInfo());
			if (list == null || list.isEmpty()) {
				result.put("error", "0");
				logger.error("还没有应用");
			} else {
				String[] dataArray = data.split(";");
				String[] nameArray = fdModelName.split(";");
				String repeat = "";
				for (int i = 0; i < list.size(); i++) {
					ThirdDingWork thirdDingWork = list.get(i);
					if (StringUtil.isNotNull(addOrUpdate)
							&& "edit".equals(addOrUpdate)
							&& thirdDingWork.getFdId().equals(fdId)) {
						logger.warn("排除应用：" + thirdDingWork.getFdName());
						continue;
					}
					String preUrl = thirdDingWork.getFdUrlPrefix();
					if (StringUtil.isNotNull(preUrl)) {
						List<String> appList = Arrays.asList(preUrl.split(";"));
						for (int j = 0; j < dataArray.length; j++) {
							if (appList.contains(dataArray[j])) {
								if (StringUtil.isNotNull(repeat)) {
									repeat += ";";
								}
								repeat += nameArray[j];
							}
						}
					}
				}
				if (StringUtil.isNotNull(repeat)) {
					result.put("error", repeat);
				} else {
					result.put("error", "0");
				}
			}
		} else {
			result.put("error", "0");
		}

		logger.error("data:" + data);
		try {


		} catch (Exception e) {
			result.put("error", "校验过程中发生了异常！");
		}
		response.getWriter().write(result.toString());
	}
}

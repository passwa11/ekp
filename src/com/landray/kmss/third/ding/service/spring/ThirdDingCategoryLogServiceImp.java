package com.landray.kmss.third.ding.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.category.model.SysCategoryMain;
import com.landray.kmss.sys.category.service.ISysCategoryMainService;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.third.ding.model.ThirdDingCategoryLog;
import com.landray.kmss.third.ding.model.ThirdDingCategoryXform;
import com.landray.kmss.third.ding.service.IThirdDingCategoryLogService;
import com.landray.kmss.third.ding.service.IThirdDingCategoryXformService;
import com.landray.kmss.third.ding.util.DingUtils;
import com.landray.kmss.third.ding.util.ThirdDingUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;

public class ThirdDingCategoryLogServiceImp extends ExtendDataServiceImp implements IThirdDingCategoryLogService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

	private IThirdDingCategoryXformService thirdDingCategoryXformService;

	private ISysCategoryMainService sysCategoryMainService;

	public ISysCategoryMainService getSysCategoryMainService() {
		if(sysCategoryMainService ==null){
			sysCategoryMainService = (ISysCategoryMainService) SpringBeanUtil
					.getBean("sysCategoryMainService");
		}
		return sysCategoryMainService;
	}

	public void setSysCategoryMainService(
			ISysCategoryMainService sysCategoryMainService) {
		this.sysCategoryMainService = sysCategoryMainService;
	}

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdDingCategoryLogServiceImp.class);

	public IThirdDingCategoryXformService getThirdDingCategoryXformService() {
		return thirdDingCategoryXformService;
	}

	public void setThirdDingCategoryXformService(
			IThirdDingCategoryXformService thirdDingCategoryXformService) {
		this.thirdDingCategoryXformService = thirdDingCategoryXformService;
	}

	@Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model,
                                            ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof ThirdDingCategoryLog) {
            ThirdDingCategoryLog thirdDingCategoryLog = (ThirdDingCategoryLog) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        ThirdDingCategoryLog thirdDingCategoryLog = new ThirdDingCategoryLog();
        ThirdDingUtil.initModelFromRequest(thirdDingCategoryLog, requestContext);
        return thirdDingCategoryLog;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        ThirdDingCategoryLog thirdDingCategoryLog = (ThirdDingCategoryLog) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }

	@SuppressWarnings("unchecked")
	@Override
	public void synCategoryFromDing(String corpId) throws Exception {
		logger.debug("同步钉钉分组，corpId:"+corpId);
		try {
			ThirdDingCategoryLog categoryLog = new ThirdDingCategoryLog();
			categoryLog.setFdCorpId(corpId);
			categoryLog.setFdSynType("all");
			JSONObject param = new JSONObject();
			param.put("corpId", corpId);
			JSONObject rs = DingUtils.getDingApiService().getDirFrom(param);
			logger.debug("rs => " + rs);
			categoryLog.setFdInput(rs == null ? null : rs.toString());
			if (rs != null && rs.getInt("errcode") == 0) {
				categoryLog.setFdSynStatus("1");
				JSONArray result = rs.getJSONArray("result");
				String content = "";
				for (int i = 0; i < result.size(); i++) {

					JSONObject dir = result.getJSONObject(i);
					if (StringUtil.isNotNull(content)) {
						content += ";";
					}
					String name = dir.getString("dirName");
					content += "name";
					String dirId = dir.getString("dirId");
					String children = dir.getString("children");

					HQLInfo hqlInfo = new HQLInfo();
					hqlInfo.setWhereBlock(
							"thirdDingCategoryXform.fdDirid=:fdDirid and fdCorpid=:fdCorpid");
					hqlInfo.setParameter("fdDirid", dirId);
					hqlInfo.setParameter("fdCorpid", corpId);
					ThirdDingCategoryXform thirdDingCategoryXform = (ThirdDingCategoryXform) thirdDingCategoryXformService
							.findFirstOne(hqlInfo);
					if (thirdDingCategoryXform != null) {
						thirdDingCategoryXform.setFdName(name);
						thirdDingCategoryXform.setFdChildren(children);
						thirdDingCategoryXform.setFdIsAvailable(true);
						thirdDingCategoryXformService
								.update(thirdDingCategoryXform);
					} else {
						// 添加分类
						SysCategoryMain sysCategoryMain = new SysCategoryMain();
						sysCategoryMain.setFdName(name);
						sysCategoryMain.setFdDesc("来自钉钉分类   corpId:" + corpId);
						sysCategoryMain.setFdOrder(0);
						sysCategoryMain.setFdModelName(
								"com.landray.kmss.km.review.model.KmReviewTemplate");
						sysCategoryMain.setFdIsinheritUser(true);
						sysCategoryMain.setFdIsinheritMaintainer(true);
						sysCategoryMain.setAuthReaderFlag(false);
						String id = getSysCategoryMainService()
								.add(sysCategoryMain);
						logger.error("id:" + id);
					}

				}
				categoryLog.setFdContent(content);
			} else {
				categoryLog.setFdSynStatus("0");
			}
			getBaseDao().add(categoryLog);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}

}

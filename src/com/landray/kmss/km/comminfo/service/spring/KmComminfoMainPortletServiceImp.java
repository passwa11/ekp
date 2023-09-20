package com.landray.kmss.km.comminfo.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.km.comminfo.model.KmComminfoMain;
import com.landray.kmss.km.comminfo.service.IKmComminfoMainService;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.portal.cloud.util.ListDataUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.StringUtil;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author 徐乃瑞 portlet实现调用的接口实现
 * 
 */
public class KmComminfoMainPortletServiceImp implements IXMLDataBean {
	protected IKmComminfoMainService kmComminfoMainService;

	public void setKmComminfoMainService(
			IKmComminfoMainService kmComminfoMainService) {
		this.kmComminfoMainService = kmComminfoMainService;
	}

	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

	@Override
	public List getDataList(RequestContext requestInfo) throws Exception {
		String para = requestInfo.getParameter("rowsize");
		// 类别ID
		String fdCategoryId = requestInfo.getParameter("fdCategoryId");
		int rowsize = 10;
		HQLInfo hqlInfo = new HQLInfo();
		StringBuffer whereBlock = new StringBuffer();
		if (!StringUtil.isNull(para)) {
            rowsize = Integer.parseInt(para);
        }
		if (StringUtil.isNotNull(fdCategoryId)) {
			whereBlock.append("kmComminfoMain.docCategory.fdId=:fdCategoryId");
			hqlInfo.setParameter("fdCategoryId", fdCategoryId);
		}
		hqlInfo.setWhereBlock(whereBlock.toString());
		hqlInfo
				.setOrderBy("kmComminfoMain.fdOrder asc,kmComminfoMain.docCreateTime desc");
		hqlInfo.setPageNo(1);
		hqlInfo.setRowSize(rowsize);
		hqlInfo.setGetCount(false);
		// 增加按排序号排序
		List rtnList = kmComminfoMainService.findPage(hqlInfo).getList();
		// 记录操作日志
		if (UserOperHelper.allowLogOper("kmComminfoPortletService",
				kmComminfoMainService.getModelName())) {
			UserOperContentHelper.putFinds(rtnList);
			UserOperHelper.setOperSuccess(true);
		}
		logger.debug("rtnList.size()=" + rtnList.size());
		for (int i = 0; i < rtnList.size(); i++) {
			KmComminfoMain kmComminfoMain = (KmComminfoMain) rtnList.get(i);
			Map map = new HashMap();
			// 在portlet显示的文档标题
			map.put("text", kmComminfoMain.getDocSubject());

			StringBuffer sb = new StringBuffer();
			sb
					.append("/km/comminfo/km_comminfo_main/kmComminfoMain.do?method=view");
			sb.append("&fdId=" + kmComminfoMain.getFdId());
			// 点击显示的内容进入具体的view页面
			map.put("href", sb.toString());

			if (requestInfo.isCloud()) {
				String isNew = requestInfo.getParameter("isNew");
				if (kmComminfoMain.getDocCreator() != null) {
					map.put("creator", ListDataUtil.buildCreator(kmComminfoMain.getDocCreator()));
				} else {
					map.put("creator", "");
				}
				if("true".equals(isNew)){
					// 在portlet里面显示的时间
					map.put("created", ListDataUtil.buildIinfo(kmComminfoMain.getDocCreateTime().getTime()));
				} else {
					// 在portlet里面显示的时间
					map.put("created", kmComminfoMain.getDocCreateTime().getTime());
				}
				if (kmComminfoMain.getDocCategory() != null) {
					if("true".equals(isNew)){
						// 在portlet里面显示的时间
						map.put("created", ListDataUtil.buildIinfo(kmComminfoMain.getDocCreateTime().getTime()));
						map.put("cateName", ListDataUtil.buildIinfo(null, kmComminfoMain.getDocCategory().getFdName(),
								"/km/comminfo/?categoryId=" + kmComminfoMain.getDocCategory().getFdId(), null, null));
					} else {
						// 在portlet里面显示的时间
						map.put("created", kmComminfoMain.getDocCreateTime().getTime());
						map.put("cateName", kmComminfoMain.getDocCategory().getFdName());
						map.put("cateHref", "/km/comminfo/?categoryId=" + kmComminfoMain.getDocCategory().getFdId());
					}
				} else {
					map.put("cateName", "");
					map.put("cateHref", "");
				}
			} else {
				// 在portlet里面显示的时间
				map.put("created", DateUtil.convertDateToString(kmComminfoMain
						.getDocCreateTime(), DateUtil.TYPE_DATE, requestInfo
								.getLocale()));
				// 在portlet里面显示的创建人
				if (kmComminfoMain.getDocCreator() != null) {
					map.put("creator", kmComminfoMain.getDocCreator().getFdName());
				} else {
					map.put("creator", "");
				}
				if (kmComminfoMain.getDocCategory() != null) {
					map.put("catename", kmComminfoMain.getDocCategory().getFdName());
					map.put("catehref", "/km/comminfo/?categoryId=" + kmComminfoMain.getDocCategory().getFdId());
				} else {
					map.put("cateName", "");
					map.put("cateHref", "");
				}
			}
			rtnList.set(i, map);
		}
		return rtnList;
	}

}

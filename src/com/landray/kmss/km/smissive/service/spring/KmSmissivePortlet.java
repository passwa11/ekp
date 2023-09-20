package com.landray.kmss.km.smissive.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.km.smissive.model.KmSmissiveMain;
import com.landray.kmss.km.smissive.service.IKmSmissiveMainService;
import com.landray.kmss.km.smissive.service.IKmSmissiveTemplateService;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.portal.cloud.util.ListDataUtil;
import com.landray.kmss.sys.portal.util.PortletTimeUtil;
import com.landray.kmss.sys.simplecategory.interfaces.SimpleCategoryUtil;
import com.landray.kmss.sys.simplecategory.model.ISysSimpleCategoryModel;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.sunbor.web.tag.Page;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 主页显示最新公文
 * 
 * @author 张鹏xn
 * 
 */
public class KmSmissivePortlet implements IXMLDataBean {
	protected IKmSmissiveMainService kmSmissiveMainService;

	protected IKmSmissiveTemplateService kmSmissiveTemplateService;

	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

	public void setKmSmissiveMainService(
			IKmSmissiveMainService kmSmissiveMainService) {
		this.kmSmissiveMainService = kmSmissiveMainService;
	}

	public void setKmSmissiveTemplateService(
			IKmSmissiveTemplateService kmSmissiveTemplateService) {
		this.kmSmissiveTemplateService = kmSmissiveTemplateService;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Map<String, String>> getDataList(RequestContext requestInfo)
			throws Exception {
		String fdCategoryId = requestInfo.getParameter("fdCategoryId");
		String paramRowsize = requestInfo.getParameter("rowsize");
		String paramPageno = requestInfo.getParameter("pageno");
		String isMportal = requestInfo.getParameter("mPortal");
		int rowsize = 10;
		int pageno = 1;
		if (!StringUtil.isNull(paramRowsize)) {
            rowsize = Integer.parseInt(paramRowsize);
        }
		if (!StringUtil.isNull(paramPageno)) {
            pageno = Integer.parseInt(paramPageno);
        }
		//menglei begin
		//公文查询 根据docStatus进行查询，新增''(docStatus在数据库中为字符串类型，若其值不加单引号，则需要做强制类型转换，查询效率低1s)
		HQLInfo hql = new HQLInfo();
		String whereBlock = "kmSmissiveMain.docStatus ="
				+ "'"+ SysDocConstant.DOC_STATUS_PUBLISH +"'";
		//menglei end
		String templateProperty = "kmSmissiveMain.fdTemplate";
		if (!StringUtil.isNull(fdCategoryId)) {
			ISysSimpleCategoryModel category = (ISysSimpleCategoryModel) kmSmissiveTemplateService
					.findByPrimaryKey(fdCategoryId);
			whereBlock = SimpleCategoryUtil.buildChildrenWhereBlock(category,
					templateProperty, whereBlock);
		}
		//时间参数
		 String scope=requestInfo.getParameter("scope");
		  if(StringUtil.isNotNull(scope)&&!"no".equals(scope)){
		      whereBlock=StringUtil.linkString(whereBlock, " and ","kmSmissiveMain.docPublishTime > :fdStartTime");
		      hql.setParameter("fdStartTime",PortletTimeUtil.getDateByScope(scope));
		  }
		hql.setOrderBy("kmSmissiveMain.docPublishTime desc");
		hql.setPageNo(pageno);
		hql.setRowSize(rowsize);
		hql.setGetCount(true);
		hql.setWhereBlock(whereBlock);
		Page page = kmSmissiveMainService.findPage(hql);
		List rtnList  = new ArrayList();
		if(StringUtil.isNotNull(isMportal)){
			rtnList.add(page);
		}else{
			rtnList= page.getList();
			if (UserOperHelper.allowLogOper("kmSmissivePortlet", null)) {
				UserOperHelper.setModelNameAndModelDesc("com.landray.kmss.km.smissive.model.KmSmissiveMain");
			}
			logger.debug("rtnList.size()=" + rtnList.size());
			for (int i = 0; i < rtnList.size(); i++) {
				KmSmissiveMain smissiveMain = (KmSmissiveMain) rtnList.get(i);
				if (UserOperHelper.allowLogOper("kmSmissivePortlet", null)) {
					UserOperContentHelper.putFind(smissiveMain);
				}
				Map map = new HashMap();
				map.put("text", smissiveMain.getDocSubject());
				map.put("publishTime", DateUtil.convertDateToString(smissiveMain
						.getDocPublishTime(), DateUtil.TYPE_DATE, requestInfo
						.getLocale()));
				StringBuffer sb = new StringBuffer();
				sb
						.append("/km/smissive/km_smissive_main/kmSmissiveMain.do?method=view");
				sb.append("&fdId=" + smissiveMain.getFdId());
	
				map.put("href", sb.toString());
				map.put("id", smissiveMain.getFdId());
				//移动门户使用
				if(smissiveMain.getFdMainDept()!=null){
					map.put("docDeptName", smissiveMain.getFdMainDept().getFdName());
				}
				map.put("docReadCount", smissiveMain.getDocReadCount());
				
				if (requestInfo.isCloud()) {
					boolean isNew = "true".equals(requestInfo.getParameter("isNew"));
					String fdUrgency = smissiveMain.getFdUrgency();
					if ("1".equals(fdUrgency)) {
						if (isNew) {
							map.put("statusInfo", ListDataUtil.buildIinfo(null, ResourceUtil.getString("km-smissive:urgency.one"),
									null, "primary", null));
						} else {
							map.put("statusInfo", ResourceUtil.getString("km-smissive:urgency.one"));
							map.put("statusColor", "primary");
						}
					} else if ("2".equals(fdUrgency)) {
						if (isNew) {
							map.put("statusInfo", ListDataUtil.buildIinfo(null, ResourceUtil
									.getString("km-smissive:urgency.two"),
									null, "warning", null));
						} else {
							map.put("statusInfo", ResourceUtil
									.getString("km-smissive:urgency.two"));
							map.put("statusColor", "warning");
						}
					} else if ("3".equals(fdUrgency)) {
						if (isNew) {
							map.put("statusInfo", ListDataUtil.buildIinfo(null, ResourceUtil.getString("km-smissive:urgency.three"),
									null, "error", null));
						} else {
							map.put("statusInfo", ResourceUtil.getString("km-smissive:urgency.three"));
							map.put("statusColor", "error");
						}
					} else if ("4".equals(fdUrgency)) {
						if (isNew) {
							map.put("statusInfo", ListDataUtil.buildIinfo(null, ResourceUtil.getString("km-smissive:urgency.four"),
									null, "error", null));
						} else {
							map.put("statusInfo", ResourceUtil.getString("km-smissive:urgency.four"));
							map.put("statusColor", "error");
						}
					}
					long created = smissiveMain.getDocPublishTime() != null
							? smissiveMain
									.getDocPublishTime().getTime()
							: smissiveMain.getDocCreateTime().getTime();
					if (isNew) {
						map.put("created", ListDataUtil.buildIinfo(created));
						map.put("cateName",
								ListDataUtil.buildIinfo(null, smissiveMain.getFdTemplate().getFdName(),
										"/km/smissive/?categoryId="
												+ smissiveMain.getFdTemplate().getFdId(), null, null));
					} else {
						map.put("created", created);
						map.put("cateName",
								smissiveMain.getFdTemplate().getFdName());
						map.put("cateHref", "/km/smissive/?categoryId="
								+ smissiveMain.getFdTemplate().getFdId());
					}
					map.put("creator", ListDataUtil
							.buildCreator(smissiveMain.getDocCreator()));
					// List<IconDataVO> icons = new ArrayList<>(1);
					// IconDataVO icon = new IconDataVO();
					// icon.setName("tree-navigation");
					// icons.add(icon);
					// map.put("icons", icons);
				} else {
					map.put("created", DateUtil.convertDateToString(smissiveMain
							.getDocPublishTime(), DateUtil.TYPE_DATE,
							requestInfo
									.getLocale()));
					map.put("creator",
							smissiveMain.getDocCreator().getFdName());
					map.put("catename",
							smissiveMain.getFdTemplate().getFdName());
					map.put("catehref", "/km/smissive/?categoryId="
							+ smissiveMain.getFdTemplate().getFdId());
				}

				rtnList.set(i, map);
			}
		}
		return rtnList;
	}

}

package com.landray.kmss.sys.news.service.spring;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.authorization.constant.ISysAuthConstant;
import com.landray.kmss.sys.cache.CacheConfig;
import com.landray.kmss.sys.cache.CacheLoader;
import com.landray.kmss.sys.cache.KmssCache;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.news.model.SysNewsConfig;
import com.landray.kmss.sys.news.model.SysNewsMain;
import com.landray.kmss.sys.news.model.SysNewsTemplate;
import com.landray.kmss.sys.news.service.ISysNewsMainService;
import com.landray.kmss.sys.news.service.ISysNewsTemplateService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.util.SysOrgEcoUtil;
import com.landray.kmss.sys.portal.cloud.dto.IconDataVO;
import com.landray.kmss.sys.portal.cloud.util.ListDataUtil;
import com.landray.kmss.sys.portal.util.PortletTimeUtil;
import com.landray.kmss.sys.simplecategory.interfaces.SimpleCategoryUtil;
import com.landray.kmss.sys.simplecategory.model.ISysSimpleCategoryModel;
import com.landray.kmss.util.*;
import com.sunbor.web.tag.Page;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.InitializingBean;

import java.util.*;

public class SysNewsMainPortlet implements IXMLDataBean, InitializingBean {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysNewsMainPortlet.class);
	protected ISysNewsMainService sysNewsMainService = null;

	protected ISysNewsTemplateService sysNewsTemplateService = null;

	protected ISysAttMainCoreInnerService sysAttMainCoreInnerService = null;

	@Override
	public List getDataList(RequestContext requestInfo) throws Exception {
		Boolean isPermission = getPermission();
		List<Map> rtnList = new ArrayList<Map>();
		List authOrgIds = new ArrayList();
		String parentId = requestInfo.getParameter("cateid");
		String extend = requestInfo.getParameter("extend");
		// 如果为空则表示没有类别，取所有
		if (StringUtil.isNull(parentId)) {
			parentId = "all";
		}
		String para = requestInfo.getParameter("rowsize");
		int rowsize = 10;
		if (!StringUtil.isNull(para)) {
            rowsize = Integer.parseInt(para);
        }
		String type = requestInfo.getParameter("type");
		List<Map> newsList = null;

		String scope = requestInfo.getParameter("scope");
		if (StringUtil.isNull(scope)) {
			scope = "no";
		}
		// 记录日志
		if (UserOperHelper.allowLogOper("sysNewsMainPortletService", null)) {
			// 记录操作的模块
			UserOperHelper.setModelNameAndModelDesc(null,
					ResourceUtil.getString("sys-portal:sys.portal.data.log")
							+ "(" + ResourceUtil.getString("sys-news:module.sys.news") + ")");
		}
		KmssCache cache = new KmssCache(SysNewsMainPortlet.class);
		String cacheKey = parentId + "_" + scope;
		// 获取多语言
		String country = SysLangUtil.getCurrentLocaleCountry();
		if (country == null) {
			country = "CN";
		}
		cacheKey += "_" + country;
		// 判断是否启用集团分级
		if (ISysAuthConstant.IS_AREA_ENABLED) {
			String authAreaId = UserUtil.getKMSSUser().getAuthAreaId();
			cacheKey += "_" + authAreaId;
		}
		if (requestInfo.isCloud()) {
			String isNew2 = requestInfo.getParameter("isNew2");
			if (StringUtil.isNull(isNew2)) {
				isNew2 = "false";
			}
			String isnew = requestInfo.getParameter("isnew");
			int day = 0;
			if (StringUtil.isNotNull(isnew)) {
				day = Integer.parseInt(isnew);
			}
			cacheKey += "_" + isNew2 + "_" + day + "_cloud";
		}
		if (SysOrgEcoUtil.IS_ENABLED_ECO && SysOrgEcoUtil.isExternal()) {
			// 外部组织获取数据时增加外部标识
			cacheKey += "_external";
		}
		// 从缓存中取数据
		newsList = (List<Map>) cache.get(cacheKey);
		if (ArrayUtil.isEmpty(newsList)) {
			// 如果缓存没有数据，则取带权限数据
			rtnList = getNewsWithAuth(rowsize, type, parentId, requestInfo);
			// 记录日志
			if (UserOperHelper.allowLogOper("sysNewsMainPortletService", null)) {
				if (rtnList != null) {
					// 记录查询到的数据
					for (Map map : rtnList) {
						UserOperContentHelper.putFind(map.get("id").toString(),
								map.get("text").toString(), null);
					}
				}
			}
			return rtnList;
		}

		// 如果是管理员或拥有模块权限则无需取登录用户相关组织架构
		if (!isPermission) {
			authOrgIds = UserUtil.getKMSSUser().getUserAuthInfo()
					.getAuthOrgIds();
		}
		//数据库中获取缓存中新闻的阅读数（动态变量），以便正确展示阅读量或点击数
		Map docReadSumMap = getDocReadSumList(newsList);
		// 剩余条数
		int num = rowsize;
		for (int i = 0; i < newsList.size() && num != 0; i++) {
			Map rtnMap = new HashMap();
			Map map = new HashMap(newsList.get(i));

			String _fdId = String.valueOf(map.get("id"));
			Object _readCount = docReadSumMap.get(_fdId);
			//阅读数赋值
			if(map.containsKey("infos")){
				JSONArray infos = (JSONArray)map.get("infos");
				JSONObject info = infos.getJSONObject(0);
				info.put("title",_readCount);
				infos.set(0,info);
				map.put("infos", infos);
			}
			if(map.containsKey("docReadCount")){
				map.put("docReadCount",_readCount);
			}

			String created_temp = (String) map.get("created_temp");
			// 多语言下时间显示问题
			if (StringUtil.isNotNull(created_temp)) {
				Date d = DateUtil.convertStringToDate(created_temp, DateUtil.PATTERN_DATETIME);
				if (!requestInfo.isCloud()) {
					map.put("created_temp", created_temp);
					map.put("created", DateUtil.convertDateToString(d,
							DateUtil.TYPE_DATE, requestInfo.getLocale()));
				}
			}
			String publishTime_temp = (String) map.get("publishTime_temp");
			if (StringUtil.isNotNull(publishTime_temp)
					&& !requestInfo.isCloud()) {
				Date d = DateUtil.convertStringToDate(publishTime_temp, DateUtil.PATTERN_DATE);
				map.put("publishTime_temp", publishTime_temp);
				map.put("publishTime", DateUtil.convertDateToString(d, DateUtil.TYPE_DATE, requestInfo.getLocale()));
			}
			
			// 如果是管理员或拥有模块权限则不进行权限过滤，直接获取rowsize的新闻记录即可
			if (!isPermission) {
				Boolean authReaderFlag = (Boolean) map.get("authReaderFlag");
				// 判断权限标志位,如果不是所有可以阅读则进行权限过滤
				if (authReaderFlag == null || !authReaderFlag) {
					List<String> authPermissions = (List<String>) map
							.get("authPermissions");
					// 判断有权限的组织架构是否和该登录用户的相关组织架构有交集，如果没有交集则没有权限
					Boolean permission = ArrayUtil.isListIntersect(
							authPermissions, authOrgIds);
					if (!permission) {
						continue;
					}
				}
			}
			try {
				// 减少数据传输，这些不相关的数据就不需要传到前端了
				map.remove("authReaderFlag");
				map.remove("authPermissions");
			} catch (Exception e) {
			}
			rtnMap.putAll(map);
			if ("att".equals(type)) {
				// 链接地址为附件使用较少，缓存中取消了缓存 附件地址来进行优化 ，当链接地址为附件时再动态获取附件链接
				String link = getAttachmentLink((String) map.get("id"),
						"fdAttachment");
				if (StringUtil.isNotNull(link)) {
					rtnMap.put("href", link);
					rtnMap.put("target", "_self");
				} else {
					rtnMap.put("target", "_blank");
				}
			} else if ("pic".equals(type)) {
				rtnMap.put("image",
						requestInfo.isCloud() && map.get("image") != null
						? ListDataUtil.formatUrl(
								map.get("image").toString())
						: map.get("image"));
				String href = (String) map.get("href");
				if (StringUtil.isNotNull(href)) {
					rtnMap.put("href", href);
				}
				Boolean fdIsPic = (Boolean) map.get("fdIsPic");
				if (fdIsPic == null || !fdIsPic) {
					continue;
				}
			} else if ("image".equals(type)) {
				if (map.get("image") != null) {
					rtnMap.put("image",
							requestInfo.isCloud() && map.get("image") != null
							? ListDataUtil.formatUrl(
									map.get("image").toString())
							: map.get("image"));
				} else {
					String defaultImgUrl = "/sys/news/images/default.jpg";
					rtnMap.put("image", requestInfo.isCloud()
							? ListDataUtil.formatUrl(defaultImgUrl)
							: defaultImgUrl);
				}
			} else {
				if ("firstPic".equals(type)) {
					if(map.get("image")!=null){
						rtnMap.put("image",
								requestInfo.isCloud()
										&& map.get("image") != null
										? ListDataUtil.formatUrl(
												map.get("image").toString())
										: map.get("image"));
					}else{
						String defaultImgUrl = "/sys/news/images/default.jpg";
						rtnMap.put("image",
								requestInfo.isCloud() ? ListDataUtil
										.formatUrl(defaultImgUrl)
										: defaultImgUrl);
					}
					
					String href = (String) map.get("href");
					if (StringUtil.isNotNull(href)) {
						rtnMap.put("href", href);
					}
					String detail=ResourceUtil.getString("sys-news:sysNewsMain.portlet.detail", requestInfo.getLocale());
					if (StringUtil.isNotNull(detail)) {
						rtnMap.put("detail", detail);
					}
				}
				
				rtnMap.putAll(map);
				rtnMap.put("target", "_blank");
			}
			num--;
			rtnList.add(rtnMap);
		}
		// 如果缓存数据为200条，但是取的数据还不够，则直接从数据库中查询数据
		if (num != 0 && newsList.size() == 200) {
			rtnList = getNewsWithAuth(rowsize, type, parentId, requestInfo);
		}
		// 记录日志
		if (UserOperHelper.allowLogOper("sysNewsMainPortletService", null)) {
			if (rtnList != null) {
				// 记录查询到的数据
				for (Map map : rtnList) {
					UserOperContentHelper.putFind(map.get("id").toString(),
							map.get("text").toString(), null);
				}
			}
		}
		return rtnList;
	}

	/**
	 * 获取类别下新闻数据(用于取缓存数据不够的时候才进行查询，带权限查询)
	 * 
	 * @param rowsize
	 * @param type
	 * @param parentId
	 * @param requestInfo
	 * @return
	 * @throws Exception
	 */
	private List<Map> getNewsWithAuth(int rowsize, String type,
			String parentId, RequestContext requestInfo) throws Exception {
		boolean isNew2 = "true".equals(requestInfo.getParameter("isNew2"));
		String whereBlock = "sysNewsMain.docStatus=:docStatus";
		String templateProperty = "sysNewsMain.fdTemplate";
		if (StringUtil.isNotNull(parentId) && !"all".equals(parentId)) {
			ISysSimpleCategoryModel category = (ISysSimpleCategoryModel) sysNewsTemplateService
					.findByPrimaryKey(parentId);
			whereBlock = SimpleCategoryUtil.buildChildrenWhereBlock(category,
					templateProperty, whereBlock);
		}
		HQLInfo hqlInfo = new HQLInfo();
		if ("pic".equals(type)) {
			whereBlock += " and sysNewsMain.fdIsPicNews = :fdIsPicNews";
			hqlInfo.setParameter("fdIsPicNews", Boolean.TRUE);
		}
		// 时间范围参数
		String scope = requestInfo.getParameter("scope");
		if (StringUtil.isNotNull(scope) && !"no".equals(scope)) {
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					"sysNewsMain.docPublishTime > :fdStartTime");
			hqlInfo.setParameter("fdStartTime", PortletTimeUtil
					.getDateByScope(scope));
		}
		// 发布时间不能超过当前日期
		whereBlock = StringUtil.linkString(whereBlock, " and ", "sysNewsMain.docPublishTime < :fdEndTime");
		hqlInfo.setParameter("fdEndTime", DateUtil.getDayEndTime(new Date()));
		hqlInfo.setWhereBlock(whereBlock);
		hqlInfo
				.setOrderBy("sysNewsMain.fdIsTop desc,sysNewsMain.fdTopTime desc,sysNewsMain.docPublishTime desc,sysNewsMain.docAlterTime desc,sysNewsMain.docCreateTime desc");
		hqlInfo.setPageNo(1);
		hqlInfo.setRowSize(rowsize);
		hqlInfo.setGetCount(false);
		hqlInfo.setParameter("docStatus", SysDocConstant.DOC_STATUS_PUBLISH);
		hqlInfo.setCheckParam(SysAuthConstant.CheckType.AreaCheck,
				SysAuthConstant.AreaCheck.YES);
		List<SysNewsMain> newsList = sysNewsMainService.findPage(hqlInfo)
				.getList();
		List<Map> rtnList = new ArrayList<Map>();
		for (int i = 0; i < newsList.size(); i++) {
			SysNewsMain sysNewsMain = newsList.get(i);
			Map<String, Object> map = new HashMap<>();
			map.put("id", sysNewsMain.getFdId());
			map.put("text", sysNewsMain.getDocSubject());

			StringBuffer sb = new StringBuffer();
			sb.append("/sys/news/sys_news_main/sysNewsMain.do?method=view");
			sb.append("&fdId=" + sysNewsMain.getFdId());
			if ("att".equals(type)) {
				String link = getAttachmentLink(sysNewsMain.getFdId(),
						"fdAttachment");
				if (link == null) {
					map.put("href", sb.toString());
					map.put("target", "_blank");
				} else {
					map.put("href", link);
					map.put("target", "_self");
				}
			} else {
				map.put("href", sb.toString());
			}
			// 类别
			SysNewsTemplate template = sysNewsMain.getFdTemplate();
			if (template != null) {
				if (requestInfo.isCloud()) {
					if (isNew2) {
						map.put("cateName", ListDataUtil.buildIinfo(null,
								template.getFdName(),"/sys/news/?categoryId=" + template.getFdId(), null, null)); // cloud
					} else {
						map.put("cateName", template.getFdName()); // cloud
						map.put("catehref", "/sys/news/?categoryId=" + template.getFdId());
					}
				} else {
					map.put("catename", template.getFdName()); // ekp
					map.put("catehref", "/sys/news/?categoryId=" + template.getFdId());
				}
			}
			if (requestInfo.isCloud()) {
				if (sysNewsMain.getFdIsTop() != null
						&& sysNewsMain.getFdIsTop()) {
					if (isNew2) {
						map.put("statusInfo", ListDataUtil.buildIinfo(null,
								ResourceUtil.getString("sys-news:sysNewsMain.fdIsTop"),
								null,"primary",null));
					} else {
						map.put("statusInfo", ResourceUtil
								.getString("sys-news:sysNewsMain.fdIsTop"));
						map.put("statusColor", "primary");
					}
				}
				if (isNew2) {
					map.put("created", ListDataUtil.buildIinfo(sysNewsMain.getDocPublishTime().getTime()));
				} else {
					map.put("created", sysNewsMain.getDocPublishTime().getTime());
				}
				SysOrgElement ele = sysNewsMain.getFdAuthor() != null
						? sysNewsMain.getFdAuthor()
						: sysNewsMain.getDocCreator();
				map.put("creator", ListDataUtil.buildCreator(ele));
				String isnew = requestInfo.getParameter("isnew");
				if (isNew(isnew, sysNewsMain)) {
					List<IconDataVO> icons = new ArrayList<>(1);
					IconDataVO icon = new IconDataVO();
					icon.setName("new");
					icon.setType("bitmap");
					icons.add(icon);
					map.put("icons", icons);
				}
				if (isNew2) {
					// 摘要
					map.put("desc", ListDataUtil.buildIinfo(sysNewsMain.getFdDescription()));
				} else {
					// 摘要
					map.put("desc", sysNewsMain.getFdDescription());
				}
				JSONArray infos = new JSONArray();
				JSONObject info = new JSONObject();
				info.put("title", sysNewsMain.getDocReadCount());
				IconDataVO icon = new IconDataVO();
				icon.setName("eye");
				info.put("icon", icon);
				infos.add(info);
				map.put("infos", infos);
			} else {
				map.put("importance", sysNewsMain.getFdImportance());
				map.put("created", DateUtil.convertDateToString(sysNewsMain
						.getDocPublishTime(), DateUtil.TYPE_DATE, requestInfo
								.getLocale()));
				String author = sysNewsMain.getFdAuthor() != null ? sysNewsMain
						.getFdAuthor().getFdName() : sysNewsMain.getFdWriter();
				String creator = sysNewsMain.getDocCreator() != null
						? sysNewsMain
								.getDocCreator().getFdName()
						: "";
				map.put("creator",
						StringUtil.isNotNull(author) ? author : creator);
				map.put("fdAuthor",
						StringUtil.isNotNull(author) ? author : creator);
				map.put("publishTime", DateUtil.convertDateToString(sysNewsMain
						.getDocPublishTime(), DateUtil.TYPE_DATE, requestInfo
								.getLocale()));
				// 摘要
				map.put("description", sysNewsMain.getFdDescription());
			}
			//阅读量
			map.put("docReadCount", sysNewsMain.getDocReadCount());

			if ("pic".equals(type)) {
				String link = getAttachmentLink(sysNewsMain.getFdId(),
						"Attachment");
				if (requestInfo.isCloud()) {
					map.put("image", ListDataUtil.formatUrl(link));
				} else {
					map.put("image", link);
				}
			}
			if ("firstPic".equals(type)) {
				String link = getAttachmentLink(sysNewsMain.getFdId(),
						"Attachment");
				if(StringUtil.isNull(link)){
					link="/sys/news/images/default.jpg";
				}
				String detail=ResourceUtil.getString("sys-news:sysNewsMain.portlet.detail", requestInfo.getLocale());
				if (StringUtil.isNotNull(detail)) {
					map.put("detail", detail);
				}
				if (requestInfo.isCloud()) {
					map.put("image", ListDataUtil.formatUrl(link));
				} else {
					map.put("image", link);
				}
			}
			if ("image".equals(type)) {
				String link = getAttachmentLink(sysNewsMain.getFdId(),
						"Attachment");
				if (StringUtil.isNull(link)) {
					link = "/sys/news/images/default.jpg";
				}
				if (requestInfo.isCloud()) {
					map.put("image", ListDataUtil.formatUrl(link));
				} else {
					map.put("image", link);
				}
			}
			if (!requestInfo.isCloud()) {
				SysNewsConfig sysNewsConfig = new SysNewsConfig();
				map.put("width", sysNewsConfig.getfdImageW());
				map.put("height", sysNewsConfig.getfdImageH());
			}
			rtnList.add(map);
		}
		return rtnList;
	}

	private boolean isNew(String isnew, SysNewsMain news) {
		// MK-PAAS的NEW图标统一在前端呈现中处理
		// if (StringUtil.isNotNull(isnew) && news.getDocPublishTime() != null)
		// {
		// int day = Integer.parseInt(isnew);
		// if (day > 0) {
		// Calendar now = Calendar.getInstance();
		// Calendar date = Calendar.getInstance();
		// date.setTime(news.getDocPublishTime());
		// date.add(Calendar.DATE, day);
		// return date.after(now);
		// }
		// }
		return false;
	}

	/**
	 * 获取类别下新闻缓存数据（不带权限查询）
	 * 
	 * @throws Exception
	 */
	private List<Map> getNewsCacheWithOutAuth(String cacheKey) throws Exception {
		List<Map> dataList = new ArrayList<Map>();
		if (SysOrgEcoUtil.IS_ENABLED_ECO) {
			// 不带权限查询仅限内部组织，外部人员只能访问带权限的接口
			if (cacheKey.contains("_external")) {
				return dataList;
			}
		}

		// 缓存cacheKey结构为：parentId_scope_land_authAreaId
		String[] __cacheKeys = cacheKey.split("_");
		String parentId = __cacheKeys[0];
		// 时间范围参数
		String scope = __cacheKeys[1];
		// 多语言
		String _lang = __cacheKeys[2].toUpperCase();

		logger.debug("重新加载该类别下新闻数据，不带权限查询前200条数据进行缓存");
		String whereBlock = "sysNewsMain.docStatus=:docStatus";
		if (StringUtil.isNotNull(parentId) && !"all".equals(parentId)) {
			String templateProperty = "sysNewsMain.fdTemplate";
			ISysSimpleCategoryModel category = (ISysSimpleCategoryModel) sysNewsTemplateService
					.findByPrimaryKey(parentId);
			whereBlock = SimpleCategoryUtil.buildChildrenWhereBlock(category,
					templateProperty, whereBlock);
		}

		String orderBy = "sysNewsMain.fdIsTop desc,sysNewsMain.fdTopTime desc,sysNewsMain.docPublishTime desc,sysNewsMain.docAlterTime desc,sysNewsMain.docCreateTime desc";
		HQLInfo hqlInfo = new HQLInfo();
		boolean isCloud = cacheKey.endsWith("_cloud");
		String isNewDay = __cacheKeys[__cacheKeys.length - 2];
		boolean isNew2 = "true".equals(__cacheKeys[__cacheKeys.length - 3]);
		// 时间范围参数
		// String scope=requestInfo.getParameter("scope");
		if (StringUtil.isNotNull(scope) && !"no".equals(scope)) {
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					"sysNewsMain.docPublishTime > :fdStartTime");
			hqlInfo.setParameter("fdStartTime", PortletTimeUtil
					.getDateByScope(scope));
		}
		// 发布时间不能超过当前日期
		whereBlock = StringUtil.linkString(whereBlock, " and ", "sysNewsMain.docPublishTime < :fdEndTime");
		hqlInfo.setParameter("fdEndTime", DateUtil.getDayEndTime(new Date()));
		hqlInfo.setDistinctType(HQLInfo.DISTINCT_YES);
		hqlInfo.setWhereBlock(whereBlock);
		hqlInfo.setOrderBy(orderBy);
		hqlInfo.setPageNo(1);
		hqlInfo.setRowSize(200); // 不带权限增加到200条数据
		hqlInfo.setGetCount(false);
		hqlInfo.setParameter("docStatus", SysDocConstant.DOC_STATUS_PUBLISH);
		hqlInfo.setCheckParam(SysAuthConstant.CheckType.AreaCheck, SysAuthConstant.AreaCheck.YES);
		hqlInfo.setCheckParam(SysAuthConstant.CheckType.AuthCheck, SysAuthConstant.AuthCheck.SYS_NONE);
		List<SysNewsMain> rtnList = sysNewsMainService.findPage(hqlInfo).getList();
		for (int i = 0; i < rtnList.size(); i++) {
			SysNewsMain sysNewsMain = rtnList.get(i);
			Map map = new HashMap();
			map.put("id", sysNewsMain.getFdId());
			map.put("text", sysNewsMain.getDocSubject());
			map.put("importance", sysNewsMain.getFdImportance());
			map.put("created_temp", DateUtil.convertDateToString(sysNewsMain
					.getDocPublishTime(), DateUtil.TYPE_DATETIME, null));

			String author = getNameByLang(sysNewsMain.getFdAuthor(), _lang, sysNewsMain.getFdWriter());
			String creator = getNameByLang(sysNewsMain.getDocCreator(), _lang, "");
			// 类别
			SysNewsTemplate template = sysNewsMain.getFdTemplate();
			if (template != null) {
				if (isCloud) {
					if (isNew2) {
						map.put("cateName",ListDataUtil.buildIinfo(null,
								getNameByLang(template, _lang, ""),
								"/sys/news/?categoryId=" + template.getFdId(), null,null)); // cloud
					} else {
						map.put("cateName", getNameByLang(template, _lang, "")); // cloud
						map.put("catehref", "/sys/news/?categoryId=" + template.getFdId());
					}
				} else {
					map.put("catename", getNameByLang(template, _lang, "")); // ekp
					map.put("catehref", "/sys/news/?categoryId=" + template.getFdId());
				}
			}
			if (isCloud) {
				if (sysNewsMain.getFdIsTop() != null
						&& sysNewsMain.getFdIsTop()) {
					if (isNew2) {
						map.put("statusInfo", ListDataUtil.buildIinfo(null,
								ResourceUtil.getString("sys-news:sysNewsMain.fdIsTop"),
								null,"primary",null
								));
					} else {
						map.put("statusInfo", ResourceUtil
								.getString("sys-news:sysNewsMain.fdIsTop"));
						map.put("statusColor", "primary");
					}
				}
				SysOrgElement ele = sysNewsMain.getFdAuthor() != null
						? sysNewsMain.getFdAuthor()
						: sysNewsMain.getDocCreator();
				map.put("creator", ListDataUtil.buildCreator(ele));
				if (isNew2) {
					map.put("created", ListDataUtil.buildIinfo(sysNewsMain.getDocPublishTime().getTime()));
					// 摘要
					map.put("desc", ListDataUtil.buildIinfo(sysNewsMain.getFdDescription()));
				} else {
					map.put("created", sysNewsMain.getDocPublishTime().getTime());
					// 摘要
					map.put("desc", sysNewsMain.getFdDescription());
				}
				if (isNew(isNewDay, sysNewsMain)) {
					List<IconDataVO> icons = new ArrayList<>(1);
					IconDataVO icon = new IconDataVO();
					icon.setName("new");
					icon.setType("bitmap");
					icons.add(icon);
					map.put("icons", icons);
				}
				JSONArray infos = new JSONArray();
				JSONObject info = new JSONObject();
				info.put("title", sysNewsMain.getDocReadCount());
				IconDataVO icon = new IconDataVO();
				icon.setName("eye");
				info.put("icon", icon);
				infos.add(info);
				map.put("infos", infos);
			} else {
				map.put("creator",
						StringUtil.isNotNull(author) ? author : creator);
				// 摘要
				map.put("description", sysNewsMain.getFdDescription());
			}

			map.put("fdAuthor", StringUtil.isNotNull(author) ? author : creator);
			map.put("docAlterTime_temp", sysNewsMain.getDocAlterTime());
			map.put("publishTime", DateUtil.convertDateToString(sysNewsMain
					.getDocPublishTime(), DateUtil.TYPE_DATE, null));
			map.put("fdIsLink", sysNewsMain.getFdIsLink());
			map.put("docReadCount", sysNewsMain.getDocReadCount());
			map.put("href",
					"/sys/news/sys_news_main/sysNewsMain.do?method=view&fdId="
							+ sysNewsMain.getFdId());
			map.put("fdIsPic", sysNewsMain.getFdIsPicNews());
			// 如果是图片新闻则获取图片链接
			if (sysNewsMain.getFdIsPicNews() != null
					&& sysNewsMain.getFdIsPicNews()) {
				String link = getAttachmentLink(sysNewsMain.getFdId(),
						"Attachment");
				map.put("image", link);
			}

			// 链接地址为附件使用较少,缓存中取消了缓存 附件地址来进行优化 ,当链接地址为附件时再动态获取附件链接
			// String link = getAttachmentLink(sysNewsMain.getFdId(),"fdAttachment");
			// 获取附件新闻链接，如果不空则保存链接
			/*
			 * if (link != null) { map.put("link", link); }
			 */

			// 如果所有阅读者中有所有everyone，则缓存权限标志位，不再缓存权限信息
			if (sysNewsMain.getAuthAllReaders().contains(
					UserUtil.getEveryoneUser())
					|| (sysNewsMain.getAuthReaderFlag() != null && sysNewsMain
							.getAuthReaderFlag())) {
				map.put("authReaderFlag", new Boolean(true));
			} else {
				List<String> authPermissions = new ArrayList<String>();
				authPermissions.addAll(lists2Ids(sysNewsMain
						.getAuthAllReaders()));
				authPermissions.addAll(lists2Ids(sysNewsMain
						.getAuthAllEditors()));
				map.put("authPermissions", authPermissions);
			}
			SysNewsConfig sysNewsConfig = new SysNewsConfig();
			map.put("width", sysNewsConfig.getfdImageW());
			map.put("height", sysNewsConfig.getfdImageH());
			dataList.add(map);
		}
		return dataList;
	}

	private String getNameByLang(BaseModel model, String lang, String defValue) throws Exception {
		String value = defValue;
		if (model != null) {
			// 取多语言名称
			value = model.getDynamicMap().get("fdName" + lang);
			if (StringUtil.isNull(value)) {
				// 如果没有多语言，则取原始名称
				value = BeanUtils.getProperty(model, "fdName");
			}
		}
		return value;
	}

	/**
	 * 将可阅读者和可编辑者转换为id列表
	 * 
	 * @param list
	 * @return
	 */
	private List<String> lists2Ids(List list) {
		List<String> ids = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			BaseModel m = (BaseModel) list.get(i);
			ids.add(m.getFdId());
		}
		return ids;
	}

	/**
	 * 获取该模块角色信息
	 * 
	 * @return
	 * @throws Exception
	 */
	private List<String> getModuleRoles() throws Exception {
		List<String> roleList = new ArrayList<String>();
		List roles = ModelUtil.getModelRoles(SysNewsMain.class.getName());
		for (int i = 0; i < roles.size(); i++) {
			roleList.add((String) roles.get(i));
			if (logger.isDebugEnabled()) {
				logger.debug(SysNewsMain.class.getName() + " ROLES ::"
						+ roles.get(i));
			}
		}
		return roleList;
	}

	/**
	 * 获取当前登录用户是否拥有模块权限或为管理员
	 * 
	 * @return
	 */
	private Boolean getPermission() throws Exception {
		Boolean isPermission = false;
		isPermission = UserUtil.getKMSSUser().isAdmin();
		List<String> authRoleAliases = new ArrayList<String>();
		List<String> moduleRoles = new ArrayList<String>();
		// 如果不是管理员则获取当前登录用户的角色列表以及模块角色信息
		if (!isPermission) {
			authRoleAliases = UserUtil.getKMSSUser().getUserAuthInfo()
					.getAuthRoleAliases();
			moduleRoles = getModuleRoles();
		}
		for (int i = 0; i < moduleRoles.size(); i++) {
			// 拥有模块其中的一个权限则为true
			if (authRoleAliases.contains(moduleRoles.get(i))) {
				isPermission = true;
				break;
			}
		}
		return isPermission;
	}

	private String File_EXT_READ = ".doc;.xls;.ppt;.xlc;.docx;.xlsx;.pptx;.xlcx;.wps;.et;.vsd;.mpp;.mppx;.pdf";

	private String getAttachmentLink(String newsId, String fdKey)
			throws Exception {
		List list = sysAttMainCoreInnerService.findByModelKey(
				"com.landray.kmss.sys.news.model.SysNewsMain", newsId, fdKey);
		if (list.isEmpty()) {
            return null;
        }
		SysAttMain att = (SysAttMain) list.get(0);
		int index = att.getFdFileName().lastIndexOf(".");
		if(index != -1){
			String fileExt = att.getFdFileName().substring(index);
			if(StringUtil.isNotNull(fileExt)){
				fileExt = fileExt.toLowerCase();
			}
			if (File_EXT_READ.indexOf(fileExt) > -1) {
				return "/sys/attachment/sys_att_main/sysAttMain.do?method=view&fdId="
						+ att.getFdId();
			}
		}
		return "/sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId="
				+ att.getFdId();
	}

	public void setSysNewsMainService(ISysNewsMainService sysNewsMainService) {
		this.sysNewsMainService = sysNewsMainService;
	}

	public void setSysNewsTemplateService(
			ISysNewsTemplateService sysNewsTemplateService) {
		this.sysNewsTemplateService = sysNewsTemplateService;
	}

	public void setSysAttMainCoreInnerService(
			ISysAttMainCoreInnerService sysAttMainCoreInnerService) {
		this.sysAttMainCoreInnerService = sysAttMainCoreInnerService;
	}

	// 定时清理缓存
	public void clearCache() {
		KmssCache cache = new KmssCache(SysNewsMainPortlet.class);
		cache.clear();
		logger.debug("每天定时清理门户新闻缓存成功...");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		CacheConfig config = CacheConfig.get(SysNewsMainPortlet.class);
		config.setCacheLoader(new CacheLoader() {
			@Override
			public Object load(String key) throws Exception {
				// 查询不带权限数据
				return getNewsCacheWithOutAuth(key);
			}
		});
	}

	public Page getNewsMportal(RequestContext requestInfo) throws Exception {
		String parentId = requestInfo.getParameter("cateid");
		// 如果为空则表示没有类别，取所有
		if (StringUtil.isNull(parentId)) {
			parentId = "all";
		}
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

		String type = requestInfo.getParameter("type");

		String scope = requestInfo.getParameter("scope");
		if (scope == null) {
			scope = "no";
		}
		String whereBlock = "sysNewsMain.docStatus=:docStatus";
		String templateProperty = "sysNewsMain.fdTemplate";
		if (StringUtil.isNotNull(parentId) && !"all".equals(parentId)) {
			ISysSimpleCategoryModel category = (ISysSimpleCategoryModel) sysNewsTemplateService
					.findByPrimaryKey(parentId);
			whereBlock = SimpleCategoryUtil.buildChildrenWhereBlock(category,
					templateProperty, whereBlock);
		}
		HQLInfo hqlInfo = new HQLInfo();
		if ("pic".equals(type)) {
			whereBlock += " and sysNewsMain.fdIsPicNews = :fdIsPicNews";
			hqlInfo.setParameter("fdIsPicNews",Boolean.TRUE);
		}
		// 时间范围参数
		if (StringUtil.isNotNull(scope) && !"no".equals(scope)) {
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					"sysNewsMain.docPublishTime > :fdStartTime");
			hqlInfo.setParameter("fdStartTime", PortletTimeUtil
					.getDateByScope(scope));
		}
		// 发布时间不能超过当前日期
		whereBlock = StringUtil.linkString(whereBlock, " and ", "sysNewsMain.docPublishTime < :fdEndTime");
		hqlInfo.setParameter("fdEndTime", DateUtil.getDayEndTime(new Date()));
		hqlInfo.setWhereBlock(whereBlock);
		hqlInfo
				.setOrderBy("sysNewsMain.fdIsTop desc,sysNewsMain.fdTopTime desc,sysNewsMain.docPublishTime desc,sysNewsMain.docAlterTime desc,sysNewsMain.docCreateTime desc");
		hqlInfo.setPageNo(pageno);
		hqlInfo.setRowSize(rowsize);
		hqlInfo.setParameter("docStatus", SysDocConstant.DOC_STATUS_PUBLISH);
		hqlInfo.setCheckParam(SysAuthConstant.CheckType.AreaCheck,
				SysAuthConstant.AreaCheck.YES);
		Page page = sysNewsMainService.findPage(hqlInfo);
		return page;
	}

	/**
	 * 数据库获取缓存中的阅读数
	 * @param newsList
	 * @return
	 */
	private Map getDocReadSumList(List<Map> newsList) throws Exception {
		Map docReadSumMap = Maps.newHashMap();
		List<String> fdIds = Lists.newArrayList();
		for(Map news:newsList){
			if(news.get("id") != null){
				String fdId = String.valueOf(news.get("id"));
				fdIds.add(fdId);
			}
		}

		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("sysNewsMain.fdId, sysNewsMain.docReadCount");

		if(fdIds.size() >= 1000) {
			StringBuffer whereBlock = new StringBuffer(" 1 = 1 ");
			whereBlock.append(" and " + HQLUtil.buildLogicIN("sysNewsMain.fdId", fdIds));
			hqlInfo.setWhereBlock(whereBlock.toString());
		}else {
			hqlInfo.setWhereBlock(" sysNewsMain.fdId in (:fdIds) ");
			hqlInfo.setParameter("fdIds", fdIds);
		}

		//hqlInfo.setCheckParam(SysAuthConstant.CheckType.AuthCheck, SysAuthConstant.AuthCheck.SYS_NONE);
		List rtnls = sysNewsMainService.findValue(hqlInfo);
		for(Object value:rtnls){
			 Object[] objects = (Object[]) value;
			 //id-->count
			if(objects[0] != null) {
				objects[1] = (objects[1] == null ? 0 : objects[1]);
				docReadSumMap.put(objects[0], objects[1]);
			}
		}

		return docReadSumMap;
	}
}

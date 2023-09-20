package com.landray.kmss.third.wechat.service.spring;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.dao.IBaseDao;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.constant.SysNotifyConstant;
import com.landray.kmss.lic.SystemParameter;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.authentication.background.IBackgroundAuthService;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.ftsearch.config.LksField;
import com.landray.kmss.sys.ftsearch.search.LksHit;
import com.landray.kmss.sys.news.model.SysNewsMain;
import com.landray.kmss.sys.news.service.ISysNewsMainService;
import com.landray.kmss.sys.notify.interfaces.NotifyContext;
import com.landray.kmss.sys.notify.model.SysNotifyTodo;
import com.landray.kmss.sys.notify.model.SysNotifyTodoDoneInfo;
import com.landray.kmss.sys.notify.service.ISysNotifyTodoDoneInfoService;
import com.landray.kmss.sys.notify.service.ISysNotifyTodoService;
import com.landray.kmss.sys.notify.service.spring.NotifyContextImp;
import com.landray.kmss.sys.notify.util.SysNotifyConfigUtil;
import com.landray.kmss.sys.webservice2.forms.AttachmentForm;
import com.landray.kmss.sys.webservice2.interfaces.ISysWsAttService;
import com.landray.kmss.sys.workflow.interfaces.SysFlowUtil;
import com.landray.kmss.third.wechat.dto.SearchBean;
import com.landray.kmss.third.wechat.dto.SearchResult;
import com.landray.kmss.third.wechat.dto.WeChatKmReviewDto;
import com.landray.kmss.third.wechat.dto.WeChatKnowledgeTempDto;
import com.landray.kmss.third.wechat.dto.WeChatNewsTempDto;
import com.landray.kmss.third.wechat.dto.WeChatNotifyTempDto;
import com.landray.kmss.third.wechat.forms.SearchParamForm;
import com.landray.kmss.third.wechat.forms.WeChatConfigParamterForm;
import com.landray.kmss.third.wechat.forms.WeChatNotifyForm;
import com.landray.kmss.third.wechat.forms.WeChatNotifyParamterForm;
import com.landray.kmss.third.wechat.forms.WeChatParamterForm;
import com.landray.kmss.third.wechat.model.WechatConfig;
import com.landray.kmss.third.wechat.service.IWechatConfigService;
import com.landray.kmss.third.wechat.service.IWechatWebserviceService;
import com.landray.kmss.third.wechat.util.AESUtil;
import com.landray.kmss.third.wechat.util.LicenseCheckUtil;
import com.landray.kmss.third.wechat.util.WeChatWebServiceUtil;
import com.landray.kmss.third.wechat.util.WeSearchUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.Runner;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.annotation.RestApi;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/api/third-wechat/wechatWebserviceRestService", method = RequestMethod.POST)
@RestApi(docUrl = "/third/wechat/restService_help.jsp", name = "wechatWebserviceRestService", resourceKey = "third-wechat:third.wechat.rest")
public class WechatWebserviceRestServiceImp implements IWechatWebserviceService {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(WechatWebserviceRestServiceImp.class);
	private ISysWsAttService sysWsAttService;

	private ISysNotifyTodoDoneInfoService sysNotifyTodoDoneInfoService;

	private ISysNotifyTodoService sysNotifyTodoService;

	private ISysNewsMainService sysNewsMainService;

	private IBackgroundAuthService backgroundAuthService;

	private IWechatConfigService wechatConfigService;

	public void setBackgroundAuthService(
			IBackgroundAuthService backgroundAuthService) {
		this.backgroundAuthService = backgroundAuthService;
	}

	public void setSysWsAttService(ISysWsAttService sysWsAttService) {
		this.sysWsAttService = sysWsAttService;
	}

	public void setSysNotifyTodoDoneInfoService(
			ISysNotifyTodoDoneInfoService sysNotifyTodoDoneInfoService) {
		this.sysNotifyTodoDoneInfoService = sysNotifyTodoDoneInfoService;
	}

	public void setSysNotifyTodoService(
			ISysNotifyTodoService sysNotifyTodoService) {
		this.sysNotifyTodoService = sysNotifyTodoService;
	}

	public void setSysNewsMainService(ISysNewsMainService sysNewsMainService) {
		this.sysNewsMainService = sysNewsMainService;
	}

	public void setWechatConfigService(IWechatConfigService wechatConfigService) {
		this.wechatConfigService = wechatConfigService;
	}

	@Override
    @ResponseBody
    @RequestMapping("/addForum")
	public String addForum(@ModelAttribute WeChatParamterForm webParamForm) throws Exception {

		List<AttachmentForm> attForms = webParamForm.getAttachmentForms();
		sysWsAttService.validateAttSize(attForms); // 校验附件大小

		String modelId = webParamForm.getModelId();
		String modelName = webParamForm.getModelName();
		sysWsAttService.save(attForms, modelId, modelName);

		// RequestContext rc = getContext(webParamForm);
		return "";
	}

	@Override
    @ResponseBody
    @RequestMapping("/findPhone")
	public String findPhone(@ModelAttribute WeChatParamterForm webForm) throws Exception {
		String resultJson = "";

		String sql = "";
		if ("1".equals(webForm.getUtype())) {
			sql = "select b.fdName,a.fdMobileNo,c.fdName from SysOrgPerson a ,"
					+ "SysOrgElement b , SysOrgElement c where "
					+ "b.fdIsAvailable = '1' and b.hbmParent.fdId = c.fdId and b.fdId = a.fdId and"
					+ " c.fdId= (select d.hbmParent.fdId from SysOrgElement d where d.fdId = :uparam)";
		} else {
			sql = "select b.fdName,a.fdMobileNo,c.fdName from SysOrgPerson a ,"
					+ "SysOrgElement b , SysOrgElement c where b.fdName like :uparam"
					+ " and b.fdIsAvailable = '1' and b.hbmParent.fdId = c.fdId and b.fdId = a.fdId";
		}

		IBaseDao baseDao = (IBaseDao) SpringBeanUtil.getBean("KmssBaseDao");
		Query query = baseDao.getHibernateSession().createQuery(sql);
		if ("1".equals(webForm.getUtype())) {
			query.setParameter("uparam", webForm.getUparam());
		} else {
			query.setParameter("uparam", "%" + webForm.getUparam() + "%");
			query.setFirstResult(0);
			query.setMaxResults(webForm.getUnum());
		}
		List list = query.list();
		String[] c = new String[] { "name", "mobile", "dept" };
		JSONArray array = new JSONArray();
		if (list != null && list.size() > 0) {
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Object[] obj = (Object[]) iterator.next();
				JSONObject jsonObj = new JSONObject();
				for (int i = 0; i < obj.length; i++) {
					String value = (String) obj[i];
					jsonObj.put(c[i].trim(), value);
				}
				array.add(jsonObj);
			}
			resultJson = array.toString();
		}
		return resultJson;
	}

	/*
	 * 查待审 查待阅 查已审
	 */
	@Override
    @ResponseBody
    @RequestMapping("/findSysNotifyTodo")
	public String findSysNotifyTodo(@RequestBody WeChatNotifyParamterForm webForm)
			throws Exception {
		int pageno = 0;
		int rowsize = SysConfigParameters.getRowSize();
		String para = webForm.getPageno();

		if (!StringUtil.isNull(para)) {
            pageno = Integer.parseInt(para);
        }
		para = webForm.getRowsize();
		if (!StringUtil.isNull(para)) {
            rowsize = Integer.parseInt(para);
        }

		boolean owner = false;// !"false".equals(webForm.getOwner());
		HQLInfo hqlInfo = new HQLInfo();
		Page queryPage = null;

		String ordertype = webForm.getOrdertype();
		String orderBy = webForm.getOrderBy();
		String oprType = webForm.getOprType();

		loadOrderBy(ordertype, orderBy, oprType, hqlInfo);

		hqlInfo.setPageNo(pageno);
		hqlInfo.setRowSize(rowsize);

		if ("done".equals(oprType)) {
			String userId = webForm.getUserId();
			String fdType = webForm.getFdType();
			loadDoneWhereBlock(userId, fdType, hqlInfo, owner);
			queryPage = sysNotifyTodoDoneInfoService
					.findDistinctSubjectNotifyPage(hqlInfo);

		} else {
			String userId = webForm.getUserId();
			String fdType = webForm.getFdType();
			loadTodoWhereBlock(userId, fdType, hqlInfo, owner);
			queryPage = sysNotifyTodoService.findPage(hqlInfo);

		}

		if (queryPage != null) {
			List resultList = queryPage.getList();
			if (resultList != null && resultList.size() > 0) {
				List returnList = new ArrayList();
				SimpleDateFormat formatter = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				if ("done".equals(oprType)) {
					for (int i = 0; i < resultList.size(); i++) {
						SysNotifyTodoDoneInfo sysNotifyTodoDoneInfo = (SysNotifyTodoDoneInfo) resultList
								.get(i);
						SysNotifyTodo sysNotifyTodo = sysNotifyTodoDoneInfo
								.getTodo();

						String id = sysNotifyTodo.getFdId();
						Date createDate = sysNotifyTodo.getFdCreateTime();
						String createTime = formatter.format(createDate);
						String key = sysNotifyTodo.getFdKey();
						String link = sysNotifyTodo.getFdLink();
						String subject = sysNotifyTodo.getFdSubject();
						int type = sysNotifyTodo.getFdType();
						String subject4view = sysNotifyTodo.getSubject4View();

						WeChatNotifyTempDto weChatNotifyTempDto = new WeChatNotifyTempDto();
						weChatNotifyTempDto.setId(id);
						weChatNotifyTempDto.setCreateTime(createTime);
						weChatNotifyTempDto.setKey(key);
						weChatNotifyTempDto.setLink(link);
						weChatNotifyTempDto.setSubject(subject);
						weChatNotifyTempDto.setSubject4view(subject4view);
						weChatNotifyTempDto.setType(type);
						weChatNotifyTempDto.setTotal(queryPage.getTotalrows());
						returnList.add(weChatNotifyTempDto);

					}
				} else {
					for (int i = 0; i < resultList.size(); i++) {
						SysNotifyTodo sysNotifyTodo = (SysNotifyTodo) resultList
								.get(i);
						String id = sysNotifyTodo.getFdId();
						Date createDate = sysNotifyTodo.getFdCreateTime();
						String createTime = formatter.format(createDate);
						String key = sysNotifyTodo.getFdKey();
						String link = sysNotifyTodo.getFdLink();
						String subject = sysNotifyTodo.getFdSubject();
						int type = sysNotifyTodo.getFdType();
						String subject4view = sysNotifyTodo.getSubject4View();

						WeChatNotifyTempDto weChatNotifyTempDto = new WeChatNotifyTempDto();
						weChatNotifyTempDto.setId(id);
						weChatNotifyTempDto.setCreateTime(createTime);
						weChatNotifyTempDto.setKey(key);
						weChatNotifyTempDto.setLink(link);
						weChatNotifyTempDto.setSubject(subject);
						weChatNotifyTempDto.setSubject4view(subject4view);
						weChatNotifyTempDto.setType(type);
						weChatNotifyTempDto.setTotal(queryPage.getTotalrows());
						returnList.add(weChatNotifyTempDto);
					}
				}
				net.sf.json.JSONArray jsonObject = net.sf.json.JSONArray
						.fromObject(returnList);
				return jsonObject.toString();
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 获取待办信息的查询条件
	 * 
	 * @param request
	 * @param hqlInfo
	 * @param owner
	 * @throws Exception
	 */
	protected void loadTodoWhereBlock(String userId, String fdType,
			HQLInfo hqlInfo, boolean owner) throws Exception {
		StringBuffer sb = new StringBuffer();
		if (owner) {
			sb.append("sysNotifyTodo.hbmTodoTargets.fdId=:userId");
			hqlInfo.setParameter("userId", UserUtil.getKMSSUser().getUserId());
		} else {
			if (StringUtil.isNotNull(userId)) {
				sb.append("sysNotifyTodo.hbmTodoTargets.fdId=:userId");
				hqlInfo.setParameter("userId", userId);
			} else {
				sb.append("sysNotifyTodo.hbmTodoTargets.fdId is not null");
			}
		}

		if (StringUtil.isNotNull(fdType)) {
			if ("13".equals(fdType)) {
				// “待审”和“暂挂”
				List<Integer> fdTypes = new ArrayList<Integer>();
				fdTypes.add(SysNotifyConstant.NOTIFY_TODOTYPE_MANUAL);
				fdTypes.add(SysNotifyConstant.NOTIFY_TODOTYPE_MANUAL_SUSPEND);
				sb.append(" and sysNotifyTodo.fdType in (:fdTypes)");
				hqlInfo.setParameter("fdTypes", fdTypes);
			} else {
				sb.append(" and sysNotifyTodo.fdType=:fdType");
				hqlInfo.setParameter("fdType", Integer.valueOf(fdType));
			}
		}
		String fdAppName = "";
		if (StringUtil.isNotNull(fdAppName)) {
			if (SysNotifyConfigUtil.LOCAL_SYSTEM_NOTIFY_FLAG
					.equalsIgnoreCase(fdAppName)) {
				sb.append(" and sysNotifyTodo.fdAppName is null");
			} else {
				sb.append(" and sysNotifyTodo.fdAppName=:fdAppName");
				hqlInfo.setParameter("fdAppName", fdAppName);
			}
		}

		String fdSubject = "";
		if (StringUtil.isNotNull(fdSubject)) {
			sb.append(" and sysNotifyTodo.fdSubject like :fdSubject");
			hqlInfo.setParameter("fdSubject", "%" + fdSubject + "%");
		}

		String fdBeginCreateTime = "";
		Locale fdLocal = null;

		if (StringUtil.isNotNull(fdBeginCreateTime)) {
			sb.append(" and sysNotifyTodo.fdCreateTime >=:fdBeginCreateTime");
			hqlInfo.setParameter("fdBeginCreateTime", DateUtil
					.convertStringToDate(fdBeginCreateTime, DateUtil.TYPE_DATE,
							fdLocal));
		}

		String fdEndCreateTime = "";
		if (StringUtil.isNotNull(fdEndCreateTime)) {
			sb.append(" and sysNotifyTodo.fdCreateTime <=:fdEndCreateTime");
			Date d = DateUtil.convertStringToDate(fdEndCreateTime,
					DateUtil.TYPE_DATE, fdLocal);
			Calendar c = Calendar.getInstance();
			c.setTime(d);
			c.add(Calendar.DATE, 1);
			hqlInfo.setParameter("fdEndCreateTime", c.getTime());
		}
		hqlInfo.setWhereBlock(sb.toString());
		hqlInfo.setDistinctType(HQLInfo.DISTINCT_YES);
	}

	/**
	 * 获取已办信息的查询条件
	 * 
	 * @param request
	 * @param hqlInfo
	 * @param owner
	 * @throws Exception
	 */
	protected void loadDoneWhereBlock(String userId, String fdType,
			HQLInfo hqlInfo, boolean owner) throws Exception {
		StringBuffer sb = new StringBuffer();

		if (owner) {
			sb.append("sysNotifyTodoDoneInfo.orgElement.fdId='"
					+ UserUtil.getUser().getFdId() + "'");
		} else {
			if (StringUtil.isNotNull(userId)) {
				sb.append("sysNotifyTodoDoneInfo.orgElement.fdId = '" + userId
						+ "'");
			} else {
				sb.append("sysNotifyTodoDoneInfo.orgElement.fdId is not null");
			}
		}

		Locale fdLocal = null;
		if (StringUtil.isNotNull(fdType)) {
			if ("13".equals(fdType)) {
				// “待审”和“暂挂”
				List<Integer> fdTypes = new ArrayList<Integer>();
				fdTypes.add(SysNotifyConstant.NOTIFY_TODOTYPE_MANUAL);
				fdTypes.add(SysNotifyConstant.NOTIFY_TODOTYPE_MANUAL_SUSPEND);
				sb
						.append(" and sysNotifyTodoDoneInfo.todo.fdType in (:fdTypes)");
				hqlInfo.setParameter("fdTypes", fdTypes);
			} else {
				sb.append(" and sysNotifyTodoDoneInfo.todo.fdType=" + fdType);
			}
		}
		String fdBeginFinishTime = "";
		if (StringUtil.isNotNull(fdBeginFinishTime)) {
			sb
					.append(" and sysNotifyTodoDoneInfo.fdFinishTime >=:fdBeginFinishTime");
			hqlInfo.setParameter("fdBeginFinishTime", DateUtil
					.convertStringToDate(fdBeginFinishTime, DateUtil.TYPE_DATE,
							fdLocal));
		}
		String fdEndFinishTime = "";
		if (StringUtil.isNotNull(fdEndFinishTime)) {
			sb
					.append(" and sysNotifyTodoDoneInfo.fdFinishTime <=:fdEndFinishTime");
			Date d = DateUtil.convertStringToDate(fdEndFinishTime,
					DateUtil.TYPE_DATE, fdLocal);
			Calendar c = Calendar.getInstance();
			c.setTime(d);
			c.add(Calendar.DATE, 1);
			hqlInfo.setParameter("fdEndFinishTime", c.getTime());
		}
		String fdAppName = "";
		if (StringUtil.isNotNull(fdAppName)) {
			if (SysNotifyConfigUtil.LOCAL_SYSTEM_NOTIFY_FLAG
					.equalsIgnoreCase(fdAppName)) {
                sb.append(" and sysNotifyTodoDoneInfo.todo.fdAppName is null");
            } else {
                sb.append(" and sysNotifyTodoDoneInfo.todo.fdAppName='"
                        + fdAppName + "'");
            }
		}

		String fdBeginCreateTime = "";
		if (StringUtil.isNotNull(fdBeginCreateTime)) {
			sb
					.append(" and sysNotifyTodoDoneInfo.todo.fdCreateTime >=:fdBeginCreateTime");
			hqlInfo.setParameter("fdBeginCreateTime", DateUtil
					.convertStringToDate(fdBeginCreateTime, DateUtil.TYPE_DATE,
							fdLocal));
		}
		String fdEndCreateTime = "";
		if (StringUtil.isNotNull(fdEndCreateTime)) {
			sb
					.append(" and sysNotifyTodoDoneInfo.todo.fdCreateTime <=:fdEndCreateTime");
			Date d = DateUtil.convertStringToDate(fdEndCreateTime,
					DateUtil.TYPE_DATE, fdLocal);
			Calendar c = Calendar.getInstance();
			c.setTime(d);
			c.add(Calendar.DATE, 1);
			hqlInfo.setParameter("fdEndCreateTime", c.getTime());
		}
		String fdSubject = "";
		if (StringUtil.isNotNull(fdSubject)) {
			sb
					.append(" and sysNotifyTodo.fdSubject like '%" + fdSubject
							+ "%'");
		}
		hqlInfo.setWhereBlock(sb.toString());
		hqlInfo.setDistinctType(HQLInfo.DISTINCT_YES);
	}

	private void loadOrderBy(String ordertype, String orderBy, String oprType,
			HQLInfo hqlInfo) throws Exception {
		boolean isReserve = false;
		if (!StringUtil.isNull(ordertype) && "down".equalsIgnoreCase(ordertype)) {
            isReserve = true;
        }

		if (StringUtil.isNull(orderBy)) {
			if ("doing".equals(oprType)) {
				orderBy = "sysNotifyTodo.fdType, sysNotifyTodo.fdCreateTime desc";
			} else if ("done".equals(oprType)) {
				orderBy = "t.fd_finish_time desc";
			} else {
				orderBy = "sysNotifyTodo.fdCreateTime desc";
			}
		} else {
			if (isReserve) {
                orderBy += " desc";
            }
		}
		hqlInfo.setOrderBy(orderBy);
	}

	/**
	 * 查询新闻数据
	 * 
	 * @param userId
	 * @param webForm
	 * @return
	 * @throws Exception
	 */
	private String getNewsData(String userId, WeChatNotifyParamterForm webForm)
			throws Exception {

		return (String) backgroundAuthService.switchUserById(userId,
				new Runner() {

					@Override
                    public Object run(Object parameter) throws Exception {
						WeChatNotifyParamterForm webForm = (WeChatNotifyParamterForm) parameter;

						String s_pageno = webForm.getPageno();
						String s_rowsize = webForm.getRowsize();
						int pageno = 0;
						int rowsize = SysConfigParameters.getRowSize();
						if (s_pageno != null && s_pageno.length() > 0) {
							pageno = Integer.parseInt(s_pageno);
						}
						if (s_rowsize != null && s_rowsize.length() > 0) {
							rowsize = Integer.parseInt(s_rowsize);
						}

						HQLInfo hqlInfo = new HQLInfo();
						hqlInfo.setPageNo(pageno);
						hqlInfo.setRowSize(rowsize);
						String para = webForm.getStatus();
						String m_where = "";

						if (StringUtil.isNotNull(para)) {
							m_where = "sysNewsMain.docStatus=:docStatus";
							hqlInfo.setParameter("docStatus", para);// SysDocConstant.DOC_STATUS_PUBLISH;
						}

						hqlInfo.setWhereBlock(m_where);
						// 增加权限过滤
						hqlInfo.setCheckParam(
								SysAuthConstant.CheckType.AuthCheck,
								SysAuthConstant.AuthCheck.SYS_READER);

						String m_order = "";
						if (m_order.indexOf("docPublishTime") == -1) {
							m_order = ",sysNewsMain.docPublishTime desc";
						}
						hqlInfo.setOrderBy(m_order.substring(1));
						Page queryPage = sysNewsMainService.findPage(hqlInfo);

						if (queryPage != null) {
							List resultList = queryPage.getList();
							if (resultList != null && resultList.size() > 0) {
								List<WeChatNewsTempDto> returnList = new ArrayList();

								for (int i = 0; i < resultList.size(); i++) {
									SysNewsMain sysNewsMain = (SysNewsMain) resultList
											.get(i);
									String linkUrl = ModelUtil
											.getModelUrl(sysNewsMain);
									String subject = sysNewsMain
											.getDocSubject();
									WeChatNewsTempDto weChatNewsTempDto = new WeChatNewsTempDto();
									weChatNewsTempDto.setSubject(subject);
									weChatNewsTempDto.setLink(linkUrl);
									weChatNewsTempDto.setTotal(queryPage
											.getTotalrows());
									returnList.add(weChatNewsTempDto);
								}
								net.sf.json.JSONArray jsonObject = net.sf.json.JSONArray
										.fromObject(returnList);
								return jsonObject.toString();
							} else {
								return null;
							}
						} else {
							return null;
						}
					}

				}, webForm);

	}

	/**
	 * 查询新闻实现方法
	 */
	@Override
    @ResponseBody
    @RequestMapping("/findSysNewsTodo")
	public String findSysNewsTodo(@RequestBody WeChatNotifyParamterForm webForm)
			throws Exception {
		String userId = webForm.getUserId();
		return getNewsData(userId, webForm);
	}

	/**
	 * 我的流程
	 */
	@Override
    @ResponseBody
    @RequestMapping("/myReviewList")
	public String myReviewList(@RequestBody WeChatNotifyParamterForm webForm)
			throws Exception {
		String s_pageno = webForm.getPageno();
		String s_rowsize = webForm.getRowsize();
		String orderby = webForm.getOrderBy();
		String ordertype = webForm.getOrdertype();
		boolean isReserve = false;
		if (ordertype != null && "down".equalsIgnoreCase(ordertype)) {
			isReserve = true;
		}
		int pageno = 0;
		int rowsize = SysConfigParameters.getRowSize();
		if (s_pageno != null && s_pageno.length() > 0) {
			pageno = Integer.parseInt(s_pageno);
		}
		if (s_rowsize != null && s_rowsize.length() > 0) {
			rowsize = Integer.parseInt(s_rowsize);
		}
		if (isReserve) {
            orderby += " desc";
        }

		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setOrderBy(orderby);
		hqlInfo.setPageNo(pageno);
		hqlInfo.setRowSize(rowsize);
		hqlInfo.setWhereBlock(changeFindPageWhereBlock(webForm, hqlInfo));
		hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck,
				SysAuthConstant.AllCheck.NO);

		Page queryPage = null;
		try {
			IBaseService base = (IBaseService) SpringBeanUtil
					.getBean("kmReviewMainService");
			queryPage = base.findPage(hqlInfo);
		} catch (Exception e) {
			logger.error("", e);
		}

		if (queryPage != null) {
			List resultList = queryPage.getList();
			if (resultList != null && resultList.size() > 0) {
				List<WeChatKmReviewDto> returnList = new ArrayList<WeChatKmReviewDto>();
				String appFlag = "&isAppflag=1";
				for (int i = 0; i < resultList.size(); i++) {
					BaseModel kmReviewModel = (BaseModel) resultList.get(i);
					String subject = BeanUtils.getProperty(kmReviewModel,
							"docSubject");// KmReviewMain
					String link = ModelUtil.getModelUrl(kmReviewModel)
							+ (StringUtil.isNull(appFlag) ? "" : appFlag);
					WeChatKmReviewDto weChatKmReviewDto = new WeChatKmReviewDto();
					weChatKmReviewDto.setSubject(subject);
					weChatKmReviewDto.setLink(link);
					weChatKmReviewDto.setTotal(queryPage.getTotalrows());
					returnList.add(weChatKmReviewDto);
				}
				net.sf.json.JSONArray jsonObject = net.sf.json.JSONArray
						.fromObject(returnList);
				return jsonObject.toString();
			} else {
				return null;
			}
		}
		return null;
	}

	protected String changeFindPageWhereBlock(WeChatNotifyParamterForm webForm,
			HQLInfo hqlInfo) throws Exception {
		String status = webForm.getStatus();
		if (null != status) {
			String owner = webForm.getMydoc();
			StringBuilder hql = new StringBuilder();
			if ("all".equals(status)) {
				hql.append("1=1");
			}
			if ("true".equals(owner)) {
				hql.append(" AND kmReviewMain.docCreator.fdId=:userid");
				hqlInfo.setParameter("userid", webForm.getUserId());
			}
			return hql.toString();
		}
		return null;
	}

	/**
	 * 搜知识
	 */
	@Override
    @ResponseBody
    @RequestMapping("/findSysKnowledgeTodo")
	public String findSysKnowledgeTodo(@RequestBody WeChatNotifyParamterForm webForm)
			throws Exception {

		String s_pageno = webForm.getPageno();
		String s_rowsize = webForm.getRowsize();
		String orderby = webForm.getOrderBy();
		String ordertype = webForm.getOrdertype();
		boolean isReserve = false;
		if (ordertype != null && "down".equalsIgnoreCase(ordertype)) {
			isReserve = true;
		}
		int pageno = 0;
		int rowsize = SysConfigParameters.getRowSize();
		if (s_pageno != null && s_pageno.length() > 0) {
			pageno = Integer.parseInt(s_pageno);
		}
		if (s_rowsize != null && s_rowsize.length() > 0) {
			rowsize = Integer.parseInt(s_rowsize);
		}
		if (isReserve) {
            orderby += " desc";
        }
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setOrderBy(orderby);
		hqlInfo.setPageNo(pageno);
		hqlInfo.setRowSize(rowsize);
		hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck,
				SysAuthConstant.AllCheck.NO);
		changeKnowledgeFindPageHQLInfo(webForm, hqlInfo);
		// 插入搜索条件查询语句
		// changeKnowledgeSearchInfoFindPageHQLInfo(webForm, hqlInfo);
		Page queryPage = null;
		try {
			IBaseService base = (IBaseService) SpringBeanUtil
					.getBean("kmDocKnowledgeService");
			queryPage = base.findPage(hqlInfo);
		} catch (Exception e) {
			logger.error("", e);
		}

		if (queryPage != null) {
			List resultList = queryPage.getList();
			if (resultList != null && resultList.size() > 0) {
				List<WeChatKnowledgeTempDto> returnList = new ArrayList<WeChatKnowledgeTempDto>();
				String appFlag = "&isAppflag=1";
				for (int i = 0; i < resultList.size(); i++) {
					BaseModel kmDocKnowledge = (BaseModel) resultList.get(i);
					String subject = BeanUtils.getProperty(kmDocKnowledge,
							"docSubject");
					// String subject =kmDocKnowledge.getDocSubject();
					String link = ModelUtil.getModelUrl(kmDocKnowledge)
							+ (StringUtil.isNull(appFlag) ? "" : appFlag);
					WeChatKnowledgeTempDto weChatKnowledgeTempDto = new WeChatKnowledgeTempDto();
					weChatKnowledgeTempDto.setSubject(subject);
					weChatKnowledgeTempDto.setLink(link);
					weChatKnowledgeTempDto.setTotal(queryPage.getTotalrows());
					returnList.add(weChatKnowledgeTempDto);
				}
				net.sf.json.JSONArray jsonObject = net.sf.json.JSONArray
						.fromObject(returnList);
				return jsonObject.toString();
			} else {
				return null;
			}
		}
		return null;
	}

	protected void changeKnowledgeFindPageHQLInfo(
			WeChatNotifyParamterForm webForm, HQLInfo hqlInfo) throws Exception {
		String whereBlock = hqlInfo.getWhereBlock();
		String ownerId = webForm.getOwner();
		if (!StringUtil.isNull(ownerId)) {
			whereBlock = "(kmDocKnowledge.docCreator.fdId = :ownerId or kmDocKnowledge.docAuthor.fdId = :ownerId)";
			hqlInfo.setParameter("ownerId", ownerId);
		}

		String departmentId = webForm.getDepartmentId();
		if (!StringUtil.isNull(departmentId)) {
			whereBlock = "(kmDocKnowledge.docDept.fdId = :departmentId)";
			hqlInfo.setParameter("departmentId", departmentId);
		}

		String propertyId = webForm.getPropertyId();
		if (!StringUtil.isNull(propertyId)) {
			whereBlock = "(kmDocKnowledge.docProperties.fdId = :propertyId)";
			hqlInfo.setParameter("propertyId", propertyId);
		}

		String para = webForm.getMydoc();
		String m_where = "kmDocKnowledge.docIsNewVersion=1";
		if (!StringUtil.isNull(para)) {
            m_where += " and kmDocKnowledge.docCreator.fdId='"
                    + UserUtil.getUser().getFdId() + "'";
        }

		para = webForm.getPink();
		if (!StringUtil.isNull(para)) {
            m_where += " and kmDocKnowledge.docIsIntroduced=1";
        }

		para = webForm.getStatus();
		String d_where = null;
		if (StringUtil.isNull(para)) {
			d_where = "kmDocKnowledge.docStatus like '3%'";
		} else if (!"all".equals(para)) {
			d_where = "kmDocKnowledge.docStatus=:status";
			hqlInfo.setParameter("status", para);
		}
		d_where = StringUtil.linkString(StringUtil.isNull(m_where) ? null : "("
				+ m_where + ")", " and ", d_where);

		whereBlock = StringUtil.linkString(StringUtil.isNull(whereBlock) ? null
				: "(" + whereBlock + ")", " and ", d_where);
		hqlInfo.setWhereBlock(whereBlock);
		String myFlow = webForm.getMyflow();
		String myDoc = webForm.getMydoc();

		// 我的文档或者流程，不作过滤
		if (StringUtil.isNotNull(myFlow) || StringUtil.isNotNull(myDoc)) {
			hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck,
					SysAuthConstant.AllCheck.NO);
		}

		if ("1".equals(myFlow)) {

			SysFlowUtil.buildLimitBlockForMyApproved("kmDocKnowledge", hqlInfo);

		}
		if ("0".equals(myFlow)) {

			SysFlowUtil.buildLimitBlockForMyApproval("kmDocKnowledge", hqlInfo);
		}
	}

	public String getCreatTimeNextDay(String endTime) {
		Date date = DateUtil
				.convertStringToDate(endTime, DateUtil.PATTERN_DATE);
		Calendar cla = Calendar.getInstance();
		cla.setTime(date);
		cla.add(Calendar.DAY_OF_MONTH, 1);
		String endTimeNextDay = DateUtil.convertDateToString(cla.getTime(),
				DateUtil.PATTERN_DATE);
		return endTimeNextDay;
	}

	/**
	 * 搜索
	 */
	@Override
    @ResponseBody
    @RequestMapping("/search4Wechat")
	public String search4Wechat(@RequestBody SearchParamForm searchParamForm)
			throws Exception {
		String userId = searchParamForm.getUser();
		return (String) backgroundAuthService.switchUserById(userId,
				new Runner() {
					@Override
                    public Object run(Object parameter) throws Exception {
						SearchParamForm searchParamForm = (SearchParamForm) parameter;
						Map<String, String> parameters = new HashMap<String, String>();
						String s_pageno = searchParamForm.getPageno();
						String s_rowsize = searchParamForm.getRowsize();

						int pageno = 0;
						int rowsize = 10;
						if (s_pageno != null && s_pageno.length() > 0) {
							pageno = Integer.parseInt(s_pageno);
						}
						if (s_rowsize != null && s_rowsize.length() > 0) {
							rowsize = Integer.parseInt(s_rowsize);
						}
						parameters.put("pageno", "" + pageno);
						parameters.put("rowsize", "" + rowsize);

						String dateFormat = ResourceUtil.getString(
								"search.format", "sys-ftsearch-db");
						WeSearchUtil.setParameters(parameters, searchParamForm,
								dateFormat);

						Map<String, Integer> modelMap = new HashMap<String, Integer>();
						Page page = new Page();
						Object searchService = null;

						Class<?> clazz = com.landray.kmss.util.ClassUtils.forName("com.landray.kmss.sys.ftsearch.db.service.spring.SearchBuilderImp");
						searchService = clazz.newInstance();
						Class[] args1 = new Class[1];
						args1[0] = Map.class;
						String type = ResourceUtil
								.getKmssConfigString("sys.ftsearch.config.engineType");

						if (type == null || "elasticsearch".equals(type)) {
							Object fsr = null;
							Method method = clazz.getDeclaredMethod(
									"facetSearch", args1);
							fsr = method.invoke(searchService, parameters);
							while (page.getList().size() == 0 && pageno > 1) {
								parameters.put("pageno", "" + pageno--);
								fsr = method.invoke(searchService, parameters);
							}

							Field pageF = fsr.getClass().getDeclaredField(
									"page");
							pageF.setAccessible(true);
							page = (Page) pageF.get(fsr);

							Field facetMapF = fsr.getClass().getDeclaredField(
									"_facetMap");
							facetMapF.setAccessible(true);
							Map m1 = (Map) facetMapF.get(fsr);
							Object[] array = m1.values().toArray();

							for (int i = 0; i < array.length; i++) {
								String[] array1 = array[i].toString()
										.split(";");
								int beginIndex1 = array1[1].indexOf("%");
								int beginIndex2 = array1[2].indexOf("%");
								String modelNameKey = array1[1]
										.substring(beginIndex1 + 1);
								int modelCount = Integer.parseInt(array1[2]
										.substring(beginIndex2 + 1));
								modelMap.put(modelNameKey, modelCount);
							}
						}

						else {
							Method method = clazz.getDeclaredMethod("search",
									args1);
							page = (Page) method.invoke(searchService,
									parameters);
							while (page.getList().size() == 0 && pageno > 1) {
								parameters.put("pageno", "" + pageno--);
								page = (Page) method.invoke(searchService,
										parameters);
							}
						}

						List<SearchBean> searchBeanList = new ArrayList<SearchBean>();
						for (int i = 0; i < page.getList().size(); i++) {
							LksHit lksHit = (LksHit) page.getList().get(i);
							Map lksFieldsMap = lksHit.getLksFieldsMap();
							LksField link = (LksField) lksFieldsMap
									.get("linkStr");
							LksField subject = (LksField) lksFieldsMap
									.get("subject");
							LksField title = (LksField) lksFieldsMap
									.get("title");
							LksField time = (LksField) lksFieldsMap
									.get("createTime");
							LksField creat = (LksField) lksFieldsMap
									.get("creator");
							String linkUrl = "";
							String fdDocSubject = "";
							String createTime = "";
							String creator = "";
							if (link != null) {
								linkUrl = link.getValue();
							}
							if (subject != null) {
								fdDocSubject = subject.getValue();
							} else if (title != null) {
								fdDocSubject = title.getValue();
							}
							if (time != null) {
								createTime = time.getValue();
							}
							if (creat != null) {
								creator = creat.getValue();
							}
							//			
							SearchBean sb = new SearchBean();
							sb.setCreateTime(createTime);
							sb.setCreator(creator);
							sb.setLink(linkUrl);
							sb.setSubject(fdDocSubject);
							searchBeanList.add(sb);
						}
						SearchResult sr = new SearchResult();
						sr.setModelMap(modelMap);
						sr.setList(searchBeanList);
						System.out.println("==========>"
								+ JSONObject.fromObject(sr).toString());
						return JSONObject.fromObject(sr).toString();
					}
				}, searchParamForm);
	}

	/**
	 * 根据从lwe接收到的信息 判读是否需要发送短信和邮件
	 */
	@Override
    @ResponseBody
    @RequestMapping("/receiveMessage")
	public String receiveMessage(@RequestBody WeChatNotifyForm weChatNotifyForm)
			throws Exception {
		NotifyContext notifyContext = new LweNotifyContext();
		String fromType = "1";
		WeChatWebServiceUtil.doProvider(weChatNotifyForm, notifyContext,
				fromType);
		return null;
	}

	/**
	 * 提供对wechatConfig表操作的的webservice接口
	 */
	@Override
    @ResponseBody
    @RequestMapping("/addWechatConfig")
	public String addWechatConfig(@RequestBody String param) {
		String resultInfor = "";
		boolean bindFlag = true;
		if (StringUtils.isNotBlank(param)) {
			String license = "";

			if (license == LicenseCheckUtil.checkLicense()) {
				// 兼容老系统
				license = SystemParameter.get("license-to") + ","
						+ SystemParameter.get("license-title") + ","
						+ SystemParameter.get("license-type") + ","
						+ SystemParameter.get("license-expire");
			} else {
				license = LicenseCheckUtil.checkLicense();
			}

			// license="13e6837803e95ec64b47643485696cea";
			if (StringUtils.isNotEmpty(license)) {
				String paraData = "";

				try {
					paraData = AESUtil.decrypt(param, license);
				} catch (Exception e) {
					resultInfor = "数据解密失败,请联系管理员!";
					bindFlag = false;
					e.printStackTrace();
				}

				String[] parameter = paraData.split("#@\\$");
				if (parameter != null && parameter.length >= 3) {
					String fdEkpid = parameter[0];
					String fdTimestamp = parameter[1];
					String fdLicense = parameter[2];
					String fdRandom = "";
					if (parameter.length > 3) {
						fdRandom = parameter[3];
					}

					Long currentTime = System.currentTimeMillis();
					Long intervalTime = (currentTime - Long
							.valueOf(fdTimestamp)) / 1000;

					if (intervalTime > 3600L) {
						resultInfor = "时效已过期,请联系管理员!";
						bindFlag = false;
						System.out.println("时效已过，请联系管理员！");
					} else {
						List<WechatConfig> wechatConfigList = new ArrayList<WechatConfig>();

						try {
							wechatConfigList = wechatConfigService
									.findWechatConfigWithCondition(fdEkpid);
							WechatConfig wechatConfig = null;

							if (wechatConfigList != null
									&& wechatConfigList.size() > 0) {
								wechatConfig = wechatConfigList.get(0);
								wechatConfig.setFdAlterTime(String
										.valueOf(currentTime));
							} else {
								wechatConfig = new WechatConfig();
								wechatConfig.setFdBindFlag("1");
								wechatConfig.setFdEkpid(fdEkpid);
								wechatConfig.setFdPushRead("1");
							}

							// fdRandom为传输则表明此次操作时解绑，否则为绑定操作
							if (StringUtils.isNotEmpty(fdRandom)) {
								wechatConfig.setFdBindFlag("1");
							} else {
								wechatConfig.setFdBindFlag("0");
							}

							wechatConfig.setFdQyRandom(fdRandom);
							wechatConfigService.add(wechatConfig);

						} catch (Exception e) {
							resultInfor = "操作失败,请联系管理员!";
							bindFlag = false;
							e.printStackTrace();
						}
					}
				}
			}

			try {
				if (bindFlag) {
					resultInfor = "操作成功.";
				}

				resultInfor = URLEncoder.encode(resultInfor, "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

		return resultInfor;
	}

	/**
	 * 回写企业号randcode
	 */
	@Override
    @ResponseBody
    @RequestMapping("/insertWeChatConfig")
	public String insertWeChatConfig(@RequestBody WeChatConfigParamterForm webForm)
			throws Exception {
		try {
			// 检查是否已经绑定过该系统
			List<WechatConfig> wechatConfigList = wechatConfigService
					.findWechatConfigWithCondition(webForm.getFdEkpid());
			WechatConfig wechatConfig = null;
			if (wechatConfigList != null && wechatConfigList.size() > 0) {
				wechatConfig = wechatConfigList.get(0);
			} else {
				wechatConfig = new WechatConfig();
				wechatConfig.setFdEkpid(webForm.getFdEkpid());
			}
			wechatConfig.setFdAlterTime(webForm.getFdAlterTime());
			wechatConfig.setFdBindFlag(webForm.getFdBindFlag());
			wechatConfig.setFdOpenid(webForm.getFdOpenid());
			wechatConfig.setFdPushProcess(webForm.getFdPushProcess());
			wechatConfig.setFdPushRead(webForm.getFdPushRead());
			wechatConfig.setFdQyRandom(webForm.getFdQyRandom());
			wechatConfig.setFdScene(webForm.getFdScene());
			wechatConfig.setFdUrlAccess(webForm.getFdUrlAccess());
			wechatConfigService.update(wechatConfig);
			
		} catch (Exception e) {
			logger.error("", e);
			e.printStackTrace();
			return "0";
		}
		return "1";
	}


	/**
	 * 回写服务号randcode
	 */
	@Override
    @ResponseBody
    @RequestMapping("/insertWeChatConfigByWeiYun")
	public String insertWeChatConfigByWeiYun(@RequestBody WeChatConfigParamterForm webForm)
			throws Exception {

		try {
			// 检查是否已经绑定过该系统
			List<WechatConfig> wechatConfigList = wechatConfigService
					.findWechatConfigWithCondition(webForm.getFdEkpid());
			WechatConfig wechatConfig = null;
			if (wechatConfigList != null && wechatConfigList.size() > 0) {
				wechatConfig = wechatConfigList.get(0);
			} else {
				wechatConfig = new WechatConfig();
				wechatConfig.setFdEkpid(webForm.getFdEkpid());
			}
			wechatConfig.setFdAlterTime(webForm.getFdAlterTime());
			wechatConfig.setFdBindFlag(webForm.getFdBindFlag());
			wechatConfig.setFdOpenid(webForm.getFdOpenid());
			wechatConfig.setFdPushProcess(webForm.getFdPushProcess());
			wechatConfig.setFdPushRead(webForm.getFdPushRead());
			wechatConfig.setFdRandom(webForm.getFdRandom());
			wechatConfig.setFdScene(webForm.getFdScene());
			wechatConfig.setFdUrlAccess(webForm.getFdUrlAccess());
			wechatConfigService.update(wechatConfig);
		} catch (Exception e) {
			logger.error("", e);
			e.printStackTrace();
			return "0";
		}
		return "1";
	}

	// 由于NotifyContextImp的构造方法未暴露出来,故采用继承办法来实现构造
	private class LweNotifyContext extends NotifyContextImp {

	}

	@Override
    @ResponseBody
    @RequestMapping("/getAttachement")
	public Object getAttachement(@RequestBody String fdId) throws Exception {
		// 进行Base64编码
		InputStream fis = null;
		ByteArrayOutputStream baos = null;
		try {

			ISysAttMainCoreInnerService iSysAttMainCoreInnerService =(ISysAttMainCoreInnerService) SpringBeanUtil.getBean("sysAttMainService");
			fis =iSysAttMainCoreInnerService.getInputStream(fdId);
			baos = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int count = 0;
			while ((count = fis.read(buffer)) >= 0) {
				baos.write(buffer, 0, count);
			}
			String uploadBuffer = new String(Base64.encodeBase64(baos
					.toByteArray()));
			return uploadBuffer;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		} finally {
			if (fis != null) {
				fis.close();
			}
			if (baos != null) {
				baos.close();
			}
		}

	}

}

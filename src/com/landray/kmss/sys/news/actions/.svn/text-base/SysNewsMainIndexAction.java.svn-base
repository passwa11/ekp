package com.landray.kmss.sys.news.actions;

import com.landray.kmss.common.actions.DataAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.dao.HQLParameter;
import com.landray.kmss.common.dao.IBaseDao;
import com.landray.kmss.common.model.IBaseTreeModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.sys.category.service.ISysCategoryMainService;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.news.model.SysNewsMain;
import com.landray.kmss.sys.news.service.ISysNewsMainService;
import com.landray.kmss.sys.news.service.ISysNewsTemplateService;
import com.landray.kmss.sys.simplecategory.service.ISysSimpleCategoryService;
import com.landray.kmss.sys.workflow.interfaces.SysFlowUtil;
import com.landray.kmss.util.*;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;
import edu.emory.mathcs.backport.java.util.Arrays;
import net.sf.json.JSONObject;
import org.hibernate.CacheMode;
import org.hibernate.query.NativeQuery;
import com.landray.kmss.util.ResourceUtil;
import org.hibernate.type.StandardBasicTypes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * 创建日期 2013-10-31
 * 
 * @author 谭又豪
 */
public class SysNewsMainIndexAction extends DataAction {
	protected ISysNewsMainService sysNewsMainService;
	protected ISysNewsTemplateService sysNewsTemplateService;

	@Override
	protected IBaseService getCategoryServiceImp(HttpServletRequest request) {
		return (ISysSimpleCategoryService) getSysNewsTemplateService();
	}

	public ISysNewsTemplateService getSysNewsTemplateService() {
		if (sysNewsTemplateService == null) {
            sysNewsTemplateService = (ISysNewsTemplateService) getBean("sysNewsTemplateService");
        }
		return sysNewsTemplateService;
	}

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (sysNewsMainService == null) {
            sysNewsMainService = (ISysNewsMainService) getBean("sysNewsMainService");
        }
		return sysNewsMainService;
	}

	@Override
	protected String getParentProperty() {
		return "fdTemplate";
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
			HQLInfo hqlInfo) throws Exception {
		// 设置排序
		if (StringUtil.isNotNull(hqlInfo.getOrderBy())) {
			if (hqlInfo.getOrderBy().indexOf(";") > -1) {// 联合查询
				String[] ors = hqlInfo.getOrderBy().split(";");
				StringBuffer order = new StringBuffer(" ");
				String orderType="";
				if (hqlInfo.getOrderBy().indexOf("desc") > -1) {
					orderType =" desc ";
				}
				for (String orderStr : ors) {
					if(orderStr.indexOf("fdIsTop") > -1 || orderStr.indexOf("fdTopTime") > -1 ) {
						continue;
					}
					order.append("sysNewsMain.").append(orderStr);
					if (StringUtil.isNotNull(orderType) && orderStr.indexOf("desc") == -1) {
						order.append(orderType);
					}
					order.append(",");
				} 
				hqlInfo.setOrderBy(order.substring(0,order.length()-1).toString());
			}
		}
		String orderBy=hqlInfo.getOrderBy();
		// oracle数据库字段数据为null的问题
		String orderByIndex;
		String driverClass =ResourceUtil.getKmssConfigString("hibernate.connection.driverClass");
		String booleanValueString = HibernateUtil.toBooleanValueString(false);
		if ("oracle.jdbc.driver.OracleDriver".equals(driverClass)) {
		    orderByIndex = "nvl(sysNewsMain.fdIsTop," + booleanValueString + ") desc,sysNewsMain.fdTopTime desc";
		} else {
		    orderByIndex = "coalesce(sysNewsMain.fdIsTop,"+booleanValueString+") desc,sysNewsMain.fdTopTime desc";
		}
		orderBy = StringUtil.linkString(orderByIndex, ",", orderBy);
		hqlInfo.setOrderBy(orderBy);
		// 组装hql
		CriteriaValue cv = new CriteriaValue(request);
		
		String docCategory = cv.poll("docCategory");
		if (StringUtil.isNotNull(docCategory)) {
			hqlInfo.setWhereBlock(
					StringUtil.linkString(hqlInfo.getWhereBlock(), " and ",
							"sysNewsMain.fdTemplate.fdHierarchyId like:docCategory"));
			hqlInfo.setParameter("docCategory","%" +docCategory+"%");
		}
		

		
		// 创建者不调用CriterialUtil中查询层级id的方式去拼接（失效人员层级id为0）,start
		if (cv.containsKey("fdAuthor")) {
			String[] values = cv.get("fdAuthor");
			List<String> vals = Arrays.asList(values);
			hqlInfo.setWhereBlock(
					StringUtil.linkString(hqlInfo.getWhereBlock(), " and ",
							"sysNewsMain.fdAuthor.fdId in(:fdAuthorIds)"));
			hqlInfo.setParameter("fdAuthorIds", vals);
			cv.poll("fdAuthor");
		}
		// 创建者不调用CriterialUtil中查询层级id的方式去拼接（失效人员层级id为0）,end

		doBuildPersonAndZoneHql(request, hqlInfo);
		CriteriaUtil.buildHql(cv, hqlInfo,
				SysNewsMain.class);

		List<HQLParameter> hqlParmeters = hqlInfo.getParameterList();
		String status = null;
		String top = null;
		for (int i = 0; i < hqlParmeters.size(); i++) {
			if ("docStatus".equals(hqlParmeters.get(i).getName())) {
				status = hqlParmeters.get(i).getValue().toString();
			}
			if ("fdIsTop".equals(hqlParmeters.get(i).getName())) {
				top = hqlParmeters.get(i).getValue().toString();
			}
		}
		request.setAttribute("docStatus", status);
		request.setAttribute("top", top);
	}

	// 重写list方法，主要是为了查询标签，提供给摘要视图
	@Override
	public ActionForward listChildren(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionForward forward = super.listChildren(mapping, form, request,
				response);

		// 查询标签机制中的标签
		Page p = (Page) request.getAttribute("queryPage");
		if (p != null) {
			List<SysNewsMain> list = p.getList();
			String fdIds = "";
			int i = 0;
			for (SysNewsMain sysNewsMain : list) {
				fdIds += i == 0 ? "'" + sysNewsMain.getFdId() + "'" : ",'"
						+ sysNewsMain.getFdId() + "'";
				i++;
			}
			JSONObject tagJson = new JSONObject();
			if (StringUtil.isNotNull(fdIds)) {
				IBaseDao baseDao = (IBaseDao) SpringBeanUtil
						.getBean("KmssBaseDao");
				String sql = "select m.fd_model_id as fd_model_id,r.fd_tag_name as fd_tag_name from sys_tag_main_relation r left join sys_tag_main m on r.fd_main_id = m.fd_id where m.fd_model_id in ("
						+ fdIds + ") and r.doc_is_delete = " + HibernateUtil.toBooleanValueString(false);
				NativeQuery query = baseDao.getHibernateSession().createNativeQuery(
						sql);
                // 启用二级缓存
                query.setCacheable(true);
                // 设置缓存模式
                query.setCacheMode(CacheMode.NORMAL);
                // 设置缓存区域
                query.setCacheRegion("sys-news");
				query.addScalar("fd_model_id", StandardBasicTypes.STRING)
						.addScalar("fd_tag_name", StandardBasicTypes.STRING);
				for (Object obj : query.list()) {
					Object[] k = (Object[]) obj;
					String key = k[0].toString();
					if (tagJson.get(k[0]) != null) {
						tagJson
								.element(key, tagJson
										.getString(k[0].toString())
										+ " | "
										+ buildTagUrl(request, k[1].toString()));
					} else {
						tagJson.element(key, buildTagUrl(request, k[1]
								.toString()));
					}
				}
				request.setAttribute("tagJson", tagJson);
			}

		}
		return forward;
	}
	
	// 重写manageList函数，允许查看草稿状态文档（ #58677）
	@Override
	public ActionForward manageList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Class<?> clz = getCategoryServiceImp(request).getClass();
		if (ISysSimpleCategoryService.class.isAssignableFrom(clz)) {
            return myListSimpleChildrenBase(mapping, form, request, response, "manageList", SysAuthConstant.AUTH_CHECK_NONE);
        } else if (ISysCategoryMainService.class.isAssignableFrom(clz)) {
            return myListCategoryChildrenBase(mapping, form, request, response, "manageList", SysAuthConstant.AUTH_CHECK_NONE);
        }
		return null;
	}
	
	private ActionForward myListCategoryChildrenBase(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response, String forwordPage, String checkAuth)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			String s_pageno = request.getParameter("pageno");
			String s_rowsize = request.getParameter("rowsize");
			String orderby = request.getParameter("orderby");
			String ordertype = request.getParameter("ordertype");
			String parentId = request.getParameter("categoryId");
			String nodeType = request.getParameter("nodeType");
			String excepteIds = request.getParameter("excepteIds");
			if (StringUtil.isNull(nodeType)) {
                nodeType = "node";
            }
			String s_IsShowAll = request.getParameter("isShowAll");
			boolean isShowAll = true;
			if (StringUtil.isNotNull(s_IsShowAll)
					&& "false".equals(s_IsShowAll)) {
                isShowAll = false;
            }
			boolean isReserve = false;
			if (ordertype != null && "down".equalsIgnoreCase(ordertype)) {
				isReserve = true;
			}
			int pageno = 0;
			int rowsize = SysConfigParameters.getRowSize();
			if (s_pageno != null && s_pageno.length() > 0 && Integer.parseInt(s_pageno) > 0) {
				pageno = Integer.parseInt(s_pageno);
			}
			if (s_rowsize != null && s_rowsize.length() > 0 && Integer.parseInt(s_rowsize) > 0) {
				rowsize = Integer.parseInt(s_rowsize);
			}
			if (isReserve) {
                orderby += " desc";
            }
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setOrderBy(orderby);
			hqlInfo.setPageNo(pageno);
			hqlInfo.setRowSize(rowsize);
			if (checkAuth != null) {
                hqlInfo.setAuthCheckType(checkAuth);
            }
			changeFindPageHQLInfo(request, hqlInfo);
			String whereBlock = hqlInfo.getWhereBlock();
			if (!StringUtil.isNull(parentId)) {
				if (StringUtil.isNull(whereBlock)) {
                    whereBlock = "";
                } else {
                    whereBlock = "(" + whereBlock + ") and ";
                }
				String tableName = ModelUtil.getModelTableName(getServiceImp(
						request).getModelName());
				if (nodeType.indexOf("CATEGORY") == -1) {
					if ("propertyNode".equals(nodeType)) {
						whereBlock += tableName
								+ ".docProperties.fdId=:parentId ";
						hqlInfo.setParameter("parentId", parentId);
					} else {
						whereBlock += tableName + "." + getParentProperty()
								+ ".fdId=:parentId ";
						hqlInfo.setParameter("parentId", parentId);
					}

				} else if (isShowAll) {
					IBaseTreeModel treeModel = (IBaseTreeModel) getCategoryServiceImp(
							request).findByPrimaryKey(parentId);
					//优化
					whereBlock += tableName + "." + getParentProperty()
						+ ".docCategory.fdHierarchyId like :fdHierarchyId";
						hqlInfo.setParameter("fdHierarchyId", treeModel.getFdHierarchyId()+ "%");
				} else {
					whereBlock += tableName + "." + getParentProperty()
							+ ".docCategory.fdId=:parentId ";
					hqlInfo.setParameter("parentId", parentId);
				}
				if (StringUtil.isNotNull(excepteIds)) {
					whereBlock += " and "
							+ HQLUtil.buildLogicIN(tableName + ".fdId not",
									ArrayUtil.convertArrayToList(excepteIds
											.split("\\s*[;,]\\s*")));
				}
				
				/*
				if (("manageList").equals(forwordPage)) {
					whereBlock += " and " + tableName + ".docStatus <>'"
							+ SysDocConstant.DOC_STATUS_DRAFT + "'";
				}
				*/
			}
			hqlInfo.setWhereBlock(whereBlock);
			Page page = getServiceImp(request).findPage(hqlInfo);
			// 添加日志信息
			UserOperHelper.logFindAll(page.getList(),
					getServiceImp(request).getModelName());
			request.setAttribute("queryPage", page);
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("failure");
		} else {
			return getActionForward(forwordPage, mapping, form, request,
					response);
		}
	}

	private ActionForward myListSimpleChildrenBase(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response, String forwordPage, String checkAuth)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			String s_pageno = request.getParameter("pageno");
			String s_rowsize = request.getParameter("rowsize");
			String orderby = request.getParameter("orderby");
			String ordertype = request.getParameter("ordertype");
			String parentId = request.getParameter("categoryId");
			String s_IsShowAll = request.getParameter("isShowAll");
			String excepteIds = request.getParameter("excepteIds");
			String nodeType = request.getParameter("nodeType");
			boolean isShowAll = true;

			if (StringUtil.isNull(nodeType)) {
                nodeType = "node";
            }
			if (StringUtil.isNotNull(s_IsShowAll)
					&& "false".equals(s_IsShowAll)) {
                isShowAll = false;
            }
			boolean isReserve = false;
			if (ordertype != null && "down".equalsIgnoreCase(ordertype)) {
				isReserve = true;
			}
			int pageno = 0;
			int rowsize = SysConfigParameters.getRowSize();
			if (s_pageno != null && s_pageno.length() > 0 && Integer.parseInt(s_pageno) > 0) {
				pageno = Integer.parseInt(s_pageno);
			}
			if (s_rowsize != null && s_rowsize.length() > 0 && Integer.parseInt(s_rowsize) > 0) {
				rowsize = Integer.parseInt(s_rowsize);
			}
			if (isReserve) {
                orderby += " desc";
            }
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setOrderBy(orderby);
			hqlInfo.setPageNo(pageno);
			hqlInfo.setRowSize(rowsize);
			String modelName = getServiceImp(request).getModelName();
			if (checkAuth != null) {
                hqlInfo.setAuthCheckType(checkAuth);
            }
			changeFindPageHQLInfo(request, hqlInfo);
			String whereBlock = hqlInfo.getWhereBlock();
			if (!StringUtil.isNull(parentId)) {
				if (StringUtil.isNull(whereBlock)) {
                    whereBlock = "";
                } else {
                    whereBlock = "(" + whereBlock + ") and ";
                }
				String tableName = ModelUtil.getModelTableName(modelName);
				if (isShowAll) {
					IBaseTreeModel treeModel = (IBaseTreeModel) getCategoryServiceImp(
							request).findByPrimaryKey(parentId);
					whereBlock += this.buildCategoryHQLException(hqlInfo, treeModel,
							tableName);
				} else {
					whereBlock += tableName + "." + getParentProperty()
							+ ".fdId=:_treeParentId";
					hqlInfo.setParameter("_treeParentId", parentId);
				}
				if (StringUtil.isNotNull(excepteIds)) {
					whereBlock += " and "
							+ HQLUtil.buildLogicIN(tableName + ".fdId not",
									ArrayUtil.convertArrayToList(excepteIds
											.split("\\s*[;,]\\s*")));
				}
				
				/*
				if (("manageList").equals(forwordPage)) {
					whereBlock += " and " + tableName
							+ ".docStatus <> :_treeDocStatus";
					hqlInfo.setParameter("_treeDocStatus",
							SysDocConstant.DOC_STATUS_DRAFT);
				}
				*/
			}
			hqlInfo.setWhereBlock(whereBlock);
			Page page = getServiceImp(request).findPage(hqlInfo);
			// 添加日志信息
			UserOperHelper.logFindAll(page.getList(),
					getServiceImp(request).getModelName());
			request.setAttribute("queryPage", page);
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("failure");
		} else {
			return getActionForward(forwordPage, mapping, form, request,
					response);
		}
	}

	/**
	 * 组装标签链接
	 * 
	 * @param tagName
	 * @return
	 */
	public String buildTagUrl(HttpServletRequest request, String tagName)
			throws UnsupportedEncodingException {
		String preUrl = request.getContextPath();
		String htmlText;
		htmlText = "<a class=\"com_subject\" target=\"_blank\" href=\""
				+ preUrl
				+ "/sys/ftsearch/searchBuilder.do?method=search&modelName=SysNewsMain&searchFields=tag&newLUI=true&queryString="
				+ URLEncoder.encode(tagName, "UTF-8") + "\">" + tagName
				+ "</a>";
		return htmlText;
	}

	/**
	 * 组装个人中心hql
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void doBuildPersonAndZoneHql(HttpServletRequest request,
			HQLInfo hqlInfo) throws Exception {
		String news = StringUtil.isNotNull(new CriteriaValue(request)
				.poll("myNews")) ? new CriteriaValue(request).poll("myNews")
				: new CriteriaValue(request).poll("taNews");
		String type = request.getParameter("type");
		String userId = StringUtil.isNotNull(type) && "person".equals(type) ? UserUtil
				.getUser().getFdId()
				: request.getParameter("userId");
		if (StringUtil.isNotNull(news)) {
			String whereBlock = "1=1";
			// xx创建的
			if ("create".equals(news)) {
				whereBlock += " and sysNewsMain.docCreator.fdId=:userId";
				hqlInfo.setParameter("userId", userId);
				hqlInfo.setWhereBlock(whereBlock);
				// xx点评的
			} else if ("ev".equals(news)) {
				StringBuffer hqlBuffer = new StringBuffer();
				hqlBuffer.append("sysNewsMain.fdId in ");
				// 拼接子查询
				hqlBuffer
						.append("(select distinct sysEvaluationMain.fdModelId from ");
				hqlBuffer
						.append(" com.landray.kmss.sys.evaluation.model.SysEvaluationMain"
								+ " as sysEvaluationMain ");
				hqlBuffer
						.append("where sysEvaluationMain.fdModelName = :fdModelName ");
				hqlBuffer
						.append("and sysEvaluationMain.fdEvaluator.fdId = :fdEvaluatorId)");
				hqlInfo.setWhereBlock(hqlBuffer.toString());
				hqlInfo.setParameter("fdModelName",
						"com.landray.kmss.sys.news.model.SysNewsMain");
				hqlInfo.setParameter("fdEvaluatorId", userId);
				// 待xx审的
			} else if ("approval".equals(news)) {
				SysFlowUtil
						.buildLimitBlockForMyApproval("sysNewsMain", hqlInfo);
				hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck,
						SysAuthConstant.AllCheck.NO);
				// xx审批的
			} else if ("approvaled".equals(news)) {
				SysFlowUtil
						.buildLimitBlockForMyApproved("sysNewsMain", hqlInfo);
				hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck,
						SysAuthConstant.AllCheck.NO);
			}
		}
	}

	public ActionForward showKeydataUsed(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		String whereBlock = "";
		String keydataIdStr = "";
		String keydataId = request.getParameter("keydataId");
		if (StringUtil.isNotNull(keydataId)) {
			keydataIdStr = " and kmKeydataUsed.keydataId = '" + keydataId + "'";
		}
		whereBlock += "sysNewsMain.fdId in (select kmKeydataUsed.modelId from com.landray.kmss.km.keydata.base.model.KmKeydataUsed kmKeydataUsed"
				+ " where kmKeydataUsed.formName='sysNewsMainForm'"
				+ keydataIdStr + ")";
		try {
			String s_pageno = request.getParameter("pageno");
			String s_rowsize = request.getParameter("rowsize");
			String orderby = request.getParameter("orderby");
			String ordertype = request.getParameter("ordertype");
			String nodeType = request.getParameter("nodeType");

			if (StringUtil.isNull(nodeType)) {
                nodeType = "node";
            }

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
			// if (checkAuth != null)
			// hqlInfo.setAuthCheckType(checkAuth);
			changeFindPageHQLInfo(request, hqlInfo);
			String whereBlockOri = hqlInfo.getWhereBlock();
			if (StringUtil.isNotNull(whereBlockOri)) {
				whereBlock = whereBlockOri + " and (" + whereBlock + ")";
			}
			hqlInfo.setWhereBlock(whereBlock);
			Page page = getServiceImp(request).findPage(hqlInfo);
			if (page != null) {
				List<SysNewsMain> list = page.getList();
				// 添加日志信息
				UserOperHelper.logFindAll(list,
						getServiceImp(request).getModelName());
				String fdIds = "";
				int i = 0;
				for (SysNewsMain sysNewsMain : list) {
					fdIds += i == 0 ? "'" + sysNewsMain.getFdId() + "'" : ",'"
							+ sysNewsMain.getFdId() + "'";
					i++;
				}
				JSONObject tagJson = new JSONObject();
				if (StringUtil.isNotNull(fdIds)) {
					IBaseDao baseDao = (IBaseDao) SpringBeanUtil
							.getBean("KmssBaseDao");
					String sql = "select m.fd_model_id,r.fd_tag_name from sys_tag_main_relation r left join sys_tag_main m on r.fd_main_id = m.fd_id where m.fd_model_id in ("
							+ fdIds + ") and r.doc_is_delete = " + HibernateUtil.toBooleanValueString(false);
					NativeQuery query = baseDao.getHibernateSession()
							.createNativeQuery(sql);
					// 启用二级缓存
					query.setCacheable(true);
					// 设置缓存模式
					query.setCacheMode(CacheMode.NORMAL);
					// 设置缓存区域
					query.setCacheRegion("sys-news");

					for (Object obj : query.list()) {
						Object[] k = (Object[]) obj;
						String key = k[0].toString();
						if (tagJson.get(k[0]) != null) {
							tagJson.element(key, tagJson.getString(k[0]
									.toString())
									+ " | "
									+ buildTagUrl(request, k[1].toString()));
						} else {
							tagJson.element(key, buildTagUrl(request, k[1]
									.toString()));
						}
					}
					request.setAttribute("tagJson", tagJson);
				}

			}
			request.setAttribute("queryPage", page);
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("failure");
		} else {
			return getActionForward("list", mapping, form, request, response);
		}
	}
}

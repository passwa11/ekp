package com.landray.kmss.km.signature.actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.query.Query;

import com.landray.kmss.common.actions.DataAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.dao.HQLParameter;
import com.landray.kmss.common.dao.HQLWrapper;
import com.landray.kmss.common.model.IBaseTreeModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.km.signature.model.KmSignatureMain;
import com.landray.kmss.km.signature.service.IKmSignatureCategoryService;
import com.landray.kmss.km.signature.service.IKmSignatureMainService;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.model.SysOrgPost;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.simplecategory.service.ISysSimpleCategoryService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;

/**
 * 签章库 Action
 * 
 * @author
 * @version 1.0 2013-09-23
 */
public class KmSignatureMainIndexAction extends DataAction {

	protected IKmSignatureMainService signatureService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (signatureService == null) {
            signatureService = (IKmSignatureMainService) getBean("kmSignatureMainService");
        }
		return signatureService;
	}

	private IKmSignatureMainService kmSignatureMainService = null;

	public IKmSignatureMainService getKmSignatureMainService(
			HttpServletRequest request) {
		if (this.kmSignatureMainService == null) {
			this.kmSignatureMainService = (IKmSignatureMainService) SpringBeanUtil
					.getBean("kmSignatureMainService");
		}
		return this.kmSignatureMainService;
	}

	private IKmSignatureCategoryService kmSignatureCategoryService;

	protected IKmSignatureCategoryService getkmSignatureCategoryServiceImp() {
		if (kmSignatureCategoryService == null) {
            kmSignatureCategoryService = (IKmSignatureCategoryService) getBean("kmSignatureCategoryService");
        }
		return kmSignatureCategoryService;
	}

	public ISysSimpleCategoryService getSysSimpleCategoryService() {
		return (ISysSimpleCategoryService) getkmSignatureCategoryServiceImp();
	}

	@Override
	protected IBaseService getCategoryServiceImp(HttpServletRequest request) {
		return getkmSignatureCategoryServiceImp();
	}

	private ISysOrgElementService sysOrgElementService;
	
	protected ISysOrgElementService getSysOrgElementService(HttpServletRequest request){
		if(sysOrgElementService == null){
			sysOrgElementService = (ISysOrgElementService) getBean("sysOrgElementService");
		}
		return sysOrgElementService;
	}
	
	@Override
	protected String getParentProperty() {
		return "fdTemp";
	}

	private Map<String, String[]> map = new HashMap<String, String[]>();

	@SuppressWarnings("unchecked")
	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
			HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		// 附件视图列表展现
		String dataType = request.getParameter("dataType");
		if ("pic".equals(dataType)) {
			request.setAttribute("loadImg", true);
		}

		String whereBlock = hqlInfo.getWhereBlock();
		if (StringUtil.isNull(whereBlock)) {
			whereBlock = "1=1";
		}
		CriteriaValue cv = new CriteriaValue(request);
		// 是否有效
		String fdIsAvailable = cv.poll("fdIsAvailable");
		if ("1".equals(fdIsAvailable)) {// ||StringUtil.isNotNull(fdIsAvailable)
			whereBlock = StringUtil
					.linkString(
							whereBlock,
							" and ",
							"( kmSignatureMain.fdIsAvailable = :fdIsAvailable or kmSignatureMain.fdIsAvailable is null)");
			hqlInfo.setParameter("fdIsAvailable",Boolean.TRUE);
		} else if ("0".equals(fdIsAvailable)) {
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					"kmSignatureMain.fdIsAvailable = :fdIsAvailable");
			hqlInfo.setParameter("fdIsAvailable",Boolean.FALSE);
		}
		// 签章名称
		String fdMarkName = cv.poll("fdMarkName");
		if (StringUtil.isNotNull(fdMarkName)) {
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					"kmSignatureMain.fdMarkName like :fdMarkName");
			hqlInfo.setParameter("fdMarkName", "%" + fdMarkName.trim() + "%");
		}
		// 我的签名
		String mydoc = cv.poll("mydoc");
		if (StringUtil.isNotNull(mydoc)) {
			mydoc = mydoc.trim().toLowerCase();
			// 我上传的文档
			if ("create".equals(mydoc)) {
				whereBlock = StringUtil.linkString(whereBlock, " and ",
						"kmSignatureMain.docCreator.fdId=:docCreator");
				hqlInfo.setParameter("docCreator", UserUtil.getKMSSUser()
						.getUserId());
				hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck, "creator");
			} else if ("authorize".equals(mydoc)) {
				String sql = "select s.fd_id from km_signature_main s,km_signature_users su where s.fd_id=su.fd_signature_id and su.fd_org_id in ("
						+ getAuthEditIds(UserUtil.getKMSSUser().getUserId())
						+ ")";
				//getSQLQuery(UserUtil.getKMSSUser().getUserId());
				// String mMarkList = "";
				IKmSignatureMainService signatureService = (IKmSignatureMainService) SpringBeanUtil
						.getBean("kmSignatureMainService");
				List<String> list = signatureService.getBaseDao().getHibernateSession().createNativeQuery(sql).list();
				StringBuffer wherebuffer = new StringBuffer(" ('' ");
				for (int i = 0; i < list.size(); i++) {
					wherebuffer.append(",'" + list.get(i).toString() + "'");
					// mMarkList += list.get(i).toString() + "\r\n";
				}
				wherebuffer.append(" )");
				whereBlock = StringUtil.linkString(whereBlock, " and ",
						"kmSignatureMain.fdId in " + wherebuffer.toString());
			} 
//			else if ("isnotavailable".equals(mydoc)) {
//				whereBlock = StringUtil.linkString(whereBlock, " and ",
//						"kmSignatureMain.fdIsAvailable = 0");
//			}
		}
		// 签章分类
//		String fdTemp = cv.poll("fdTemp");
//		if (StringUtil.isNotNull(fdTemp)) {
//			whereBlock = StringUtil.linkString(whereBlock, " and ",
//					"(kmSignatureMain.fdTemp.fdId =:fdTemp");
//			hqlInfo.setParameter("fdTemp", fdTemp);
//
//			IBaseTreeModel treeModel = (IBaseTreeModel) getCategoryServiceImp(
//					request).findByPrimaryKey(fdTemp);
//			whereBlock = StringUtil
//					.linkString(whereBlock, " or ",
//							"kmSignatureMain.fdTemp.fdHierarchyId like :_treeHierarchyId)");
//			hqlInfo.setParameter("_treeHierarchyId", "%"
//					+ treeModel.getFdHierarchyId() + "%");
//		}

		// 签章类型（个人签名、单位印章）
		String fdDocTypeString = request.getParameter("fdDocType");
		if (StringUtil.isNotNull(fdDocTypeString)) {
			long fdDocTypeLong = Long.parseLong(fdDocTypeString);
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					"(kmSignatureMain.fdDocType =:fdDocType) ");
			hqlInfo.setParameter("fdDocType", fdDocTypeLong);
		}

		// 用户名称
		String fdUserName = cv.poll("fdUserName");
		if (StringUtil.isNotNull(fdUserName)) {
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					"kmSignatureMain.fdUserName like :fdUserName");
			hqlInfo.setParameter("fdUserName", "%" + fdUserName.trim() + "%");
		}
		// 签章保存时间
		String[] fdMarkDate = cv.polls("fdMarkDate");
		if (fdMarkDate != null) {
			if (fdMarkDate.length > 1) {
				Date values0 = new Date();
				Date values1 = new Date();
				if (StringUtil.isNull(fdMarkDate[0])) {
					fdMarkDate[0] = "min";
				} else {
					values0 = DateUtil.convertStringToDate(fdMarkDate[0],
							DateUtil.TYPE_DATE, UserUtil.getKMSSUser()
									.getLocale());
					Calendar c = Calendar.getInstance();
					c.setTime(values0);
					c.add(Calendar.DATE, -1);
					c.add(Calendar.HOUR, 23);
					c.add(Calendar.MINUTE, 59);
					c.add(Calendar.SECOND, 59);
					values0 = c.getTime();
				}
				if (StringUtil.isNull(fdMarkDate[1])) {
					fdMarkDate[1] = "max";
				} else {
					values1 = DateUtil.convertStringToDate(fdMarkDate[1],
							DateUtil.TYPE_DATE, UserUtil.getKMSSUser()
									.getLocale());
					Calendar c = Calendar.getInstance();
					c.setTime(values1);
					c.add(Calendar.HOUR, 23);
					c.add(Calendar.MINUTE, 59);
					c.add(Calendar.SECOND, 59);
					values1 = c.getTime();
				}
				if ("min".equalsIgnoreCase(fdMarkDate[0])) {
					whereBlock = StringUtil.linkString(whereBlock, " and ",
							"(kmSignatureMain.fdMarkDate >= :fdMarkDate0 )");
					hqlInfo.setParameter("fdMarkDate0", values1);
				} else if ("max".equalsIgnoreCase(fdMarkDate[1])) {
					whereBlock = StringUtil.linkString(whereBlock, " and ",
							"(kmSignatureMain.fdMarkDate >= :fdMarkDate0 )");
					hqlInfo.setParameter("fdMarkDate0", values0);
				} else {
					whereBlock = StringUtil
							.linkString(whereBlock, " and ",
									"(kmSignatureMain.fdMarkDate BETWEEN :fdMarkDate0 and :fdMarkDate1 ) ");
					hqlInfo.setParameter("fdMarkDate0", values0);
					hqlInfo.setParameter("fdMarkDate1", values1);
				}
			}
		}
		//创建者
		String[] docCreatorId = cv.polls("docCreatorId");
		if(docCreatorId!=null){
			whereBlock = StringUtil.linkString(whereBlock, " and ","kmSignatureMain.docCreator.fdId in (:docCreators) ");
			hqlInfo.setParameter("docCreators", getOrgAndPost(request,docCreatorId));
		}
		CriteriaUtil.buildHql(cv, hqlInfo, KmSignatureMain.class);
		if (StringUtil.isNotNull(hqlInfo.getWhereBlock())) {
			whereBlock = StringUtil.linkString(whereBlock, " and ",hqlInfo.getWhereBlock());
		}
		hqlInfo.setWhereBlock(whereBlock);
	}

	// 重写SimpleCategoryNodeAction中的listChildren方法、manageList方法和listChildrenBase方法
	@Override
	public ActionForward listChildren(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Class<?> clz = getCategoryServiceImp(request).getClass();
		if (ISysSimpleCategoryService.class.isAssignableFrom(clz)) {
            return listSimpleChildrenBase(mapping, form, request, response,
                    "listChildren", null);
        }
		return null;
	}

	private ActionForward listSimpleChildrenBase(ActionMapping mapping,
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
			String modelName = getServiceImp(request).getModelName();
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
					whereBlock += this.buildCategoryHQL(hqlInfo, treeModel,
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
				if (("manageList").equals(forwordPage)) {
					whereBlock += " and " + tableName
							+ ".docStatus <> :_treeDocStatus";
					hqlInfo.setParameter("_treeDocStatus",
							SysDocConstant.DOC_STATUS_DRAFT);
				}
			}
			hqlInfo.setWhereBlock(whereBlock);
			Page page = getServiceImp(request).findPage(hqlInfo);
			request.setAttribute("queryPage", page);
			UserOperHelper.logFindAll(page.getList(), modelName);
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("failure");
		} else {
			if("true".equals(request.getParameter("backstage"))){
				return getActionForward("backstage", mapping, form, request,
						response);
			}
			return getActionForward(forwordPage, mapping, form, request,
					response);
		}
	}

	@SuppressWarnings("unchecked")
	public ActionForward getSysAttList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
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
			if (s_pageno != null && s_pageno.length() > 0) {
				pageno = Integer.parseInt(s_pageno);
			}
			if (s_rowsize != null && s_rowsize.length() > 0) {
				rowsize = Integer.parseInt(s_rowsize);
			}
			if (isReserve) {
                orderby += " desc";
            }
			HQLInfo hqlAtt = new HQLInfo();
			// hqlAtt.setOrderBy(orderby);
			hqlAtt.setPageNo(pageno);
			hqlAtt.setRowSize(rowsize);
			changeFindPageHQLInfo(request, hqlAtt);
			String whereBlock = hqlAtt.getWhereBlock();
			if (!StringUtil.isNull(parentId)) {
				if (StringUtil.isNull(whereBlock)) {
                    whereBlock = "";
                } else {
                    whereBlock = "(" + whereBlock + ") and ";
                }
				String tableName = ModelUtil.getModelTableName(getServiceImp(
						request).getModelName());
				if (isShowAll) {
					IBaseTreeModel treeModel = (IBaseTreeModel) getSysSimpleCategoryService()
							.findByPrimaryKey(parentId);

					whereBlock += buildCategoryHQL(hqlAtt, treeModel, tableName);
				} else {
					whereBlock += tableName + "." + getParentProperty()
							+ ".fdId=:_treeParentId";
					hqlAtt.setParameter("_treeParentId", parentId);
				}
				if (StringUtil.isNotNull(excepteIds)) {
					whereBlock += " and "
							+ HQLUtil.buildLogicIN(tableName + ".fdId not",
									ArrayUtil.convertArrayToList(excepteIds
											.split("\\s*[;,]\\s*")));
				}
			}

			// 文件格式筛选处理
			Iterator<Entry<String, String[]>> iterator = new CriteriaValue(
					request).entrySet().iterator();
			List<String> fileTypeList = new ArrayList<String>();

			String __joinBlock = "com.landray.kmss.km.signature.model.KmSignatureMain kmSignatureMain";
			__joinBlock += StringUtil.isNotNull(hqlAtt.getJoinBlock()) ? hqlAtt
					.getJoinBlock() : "";
			// 经过筛选器筛选后的文档hql（已权限处理）
			HQLWrapper _docHqlWrapper = getKmSignatureMainService(request)
					.getDocHql(whereBlock, __joinBlock, request);
			String _docHql = _docHqlWrapper.getHql();
			List<HQLParameter> _docHqlPara = _docHqlWrapper.getParameterList();

			if (StringUtil.isNotNull(orderby)) {
				if (orderby.trim().startsWith("kmSignatureMain.")) {
                    hqlAtt.setOrderBy(orderby);
                } else {
                    hqlAtt.setOrderBy(" kmSignatureMain." + orderby);
                }
			} else {
				hqlAtt.setOrderBy("");
			}
			hqlAtt
					.setFromBlock("com.landray.kmss.sys.attachment.model.SysAttMain sysAttMain");
			hqlAtt
					.setSelectBlock("sysAttMain.fdId,sysAttMain.fdCreatorId,sysAttMain.fdSize,sysAttMain.docCreateTime,sysAttMain.fdFileName,kmSignatureMain.fdId,kmSignatureMain.fdMarkName");
			hqlAtt
					.setModelName("com.landray.kmss.sys.attachment.model.SysAttMain");
			hqlAtt
					.setJoinBlock(",com.landray.kmss.km.signature.model.KmSignatureMain kmSignatureMain");

			String where = " and sysAttMain.fdModelId in (" + _docHql + ")";

			hqlAtt
					.setWhereBlock("sysAttMain.fdKey = 'sigPic' and sysAttMain.fdModelId = kmSignatureMain.fdId"
							+ where);

			Page page = new Page();
			Boolean hqlGetCount = false;
			Query query = null;
			HQLWrapper hqlWrap = null;
			int total = hqlAtt.getRowSize();
			if (hqlAtt.isGetCount()) {
				TimeCounter.logCurrentTime("Dao-findPage-count", true,
						getClass());
				hqlGetCount = true;
				hqlWrap = getHQL(hqlAtt, hqlGetCount, hqlAtt.getWhereBlock());
				query = getServiceImp(request).getBaseDao()
						.getHibernateSession().createQuery(hqlWrap.getHql());
				HQLUtil.setParameters(query, _docHqlPara);
				HQLUtil.setParameters(query, hqlWrap.getParameterList());

				total = ((Long) query.iterate().next()).intValue();
				TimeCounter.logCurrentTime("Dao-findPage-count", false,
						getClass());
			}
			TimeCounter.logCurrentTime("Dao-findPage-list", true, getClass());
			if (total > 0) {
				hqlGetCount = false;
				// Oracle的排序列若出现重复值，那排序的结果可能不准确，为了避免该现象，若出现了排序列，则强制在最后加上按fdId排序
				String order = hqlAtt.getOrderBy();
				if (StringUtil.isNotNull(order)) {
					Pattern p = Pattern.compile(",\\s*" + "kmSignatureMain"
							+ "\\.fdId\\s*|,\\s*fdId\\s*");
					if (!p.matcher("," + order).find()) {
						hqlAtt.setOrderBy(order + "," + "kmSignatureMain"
								+ ".fdId desc");
					}
				}
				page = new Page();
				page.setRowsize(hqlAtt.getRowSize());
				page.setPageno(hqlAtt.getPageNo());
				page.setTotalrows(total);
				page.excecute();
				hqlWrap = getHQL(hqlAtt, hqlGetCount, hqlAtt.getWhereBlock());

				Query q = getServiceImp(request).getBaseDao()
						.getHibernateSession().createQuery(hqlWrap.getHql());
				HQLUtil.setParameters(q, _docHqlPara);
				HQLUtil.setParameters(q, hqlWrap.getParameterList());
				q.setFirstResult(page.getStart());
				q.setMaxResults(page.getRowsize());
				page.setList(q.list());

			}

			request.setAttribute("attPage", page);
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("failure");
		} else {
			return getActionForward("attList", mapping, form, request, response);
		}
	}

	public HQLWrapper getHQL(HQLInfo hqlInfo, Boolean hqlGetCount,
			String extendWhereBlock) {
		StringBuffer hql = new StringBuffer();
		if (hqlGetCount) {
			hql.append("select count(distinct " + hqlInfo.getModelTable()
					+ ".fdId) ");
			hql.append("from " + hqlInfo.getModelName() + " "
					+ hqlInfo.getModelTable() + " ");
		} else {

			hql
					.append("select "
							+ " sysAttMain.fdId,sysAttMain.fdCreatorId,sysAttMain.fdSize,sysAttMain.docCreateTime, "
							+ " sysAttMain.fdFileName,kmSignatureMain.fdId,kmSignatureMain.fdMarkName "
							+ " ");
			String andSet = " ";

			hql
					.append(" from com.landray.kmss.sys.attachment.model.SysAttMain sysAttMain,"
							+ " com.landray.kmss.km.signature.model.KmSignatureMain kmSignatureMain "
							+ " where sysAttMain.fdModelId = kmSignatureMain.fdId "
							+ andSet);
			hql.append(replaceTempName(HQLUtil.getAutoFetchInfo(hqlInfo),
					hqlInfo.getModelTable(), "sysAttMain"));
			hql.append("and " + "sysAttMain" + ".fdId in (");
			hql.append("select " + hqlInfo.getModelTable() + ".fdId ");
			if (StringUtil.isNull(hqlInfo.getFromBlock())) {
                hql.append("from " + hqlInfo.getModelName() + " "
                        + hqlInfo.getModelTable() + " ");
            } else {
                hql.append("from " + hqlInfo.getFromBlock() + " ");
            }
		}
		if (!StringUtil.isNull(hqlInfo.getJoinBlock())) {
            hql.append(hqlInfo.getJoinBlock() + " ");
        }

		if (!StringUtil.isNull(extendWhereBlock)) {
            hql.append("where " + extendWhereBlock);
        }
		if (!hqlGetCount) {
			hql.append(")");
			if (!StringUtil.isNull(hqlInfo.getOrderBy())) {
                hql.append(" order by "
                        + replaceTempName(hqlInfo.getOrderBy(), hqlInfo
                                .getModelTable(), "sysAttMain"));
            }
		}
		return new HQLWrapper(hql.toString(), hqlInfo.getParameterList());
	}

	private static String replaceTempName(String srcName, String fromName,
			String toName) {
		return srcName.replaceAll("(^|\\W)" + fromName + "(\\.|\\W)", "$1"
				+ toName + "$2");
	}

	// 文件格式筛选组合
	@SuppressWarnings("unchecked")
	public static String getFileTypeHql(List valueList,
			List<String> fileTypeList, String allFileType) {
		if (valueList.contains("doc")) {
			fileTypeList.add("application/msword");
			fileTypeList
					.add("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
		}
		if (valueList.contains("ppt")) {
			fileTypeList.add("application/vnd.ms-powerpoint");
			fileTypeList
					.add("application/vnd.openxmlformats-officedocument.presentationml.presentation");
		}
		if (valueList.contains("pdf")) {
			fileTypeList.add("application/pdf");
		}
		if (valueList.contains("excel")) {
			fileTypeList.add("application/vnd.ms-excel");
			fileTypeList
					.add("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		}
		if (valueList.contains("pic")) {
			String[] imgType = { "image/bmp", "image/jpeg", "image/gif",
					"image/cis-cod", "image/ief", "image/png", "image/pipeg",
					"image/x-icon", "image/x-xwindowdump",
					"image/x-portable-anymap", "image/tiff" };
			fileTypeList.addAll(Arrays.asList(imgType));
		}
		if (valueList.contains("sound")) {
			String[] soundType = { "audio/mpeg", "audio/x-wav", "audio/ogg" };
			fileTypeList.addAll(Arrays.asList(soundType));
		}
		if (valueList.contains("video")) {
			String[] videoType = { "audio/x-pn-realaudio", "audio/wrf",
					"audio/f4v", "video/mp4", "video/3gpp", "video/wmv9",
					"video/x-ms-wmv", "video/x-flv", "video/x-ms-asf",
					"video/x-msvideo", "video/x-sgi-movie", "video/quicktime",
					"video/mpeg", "video/x-la-asf" };
			fileTypeList.addAll(Arrays.asList(videoType));
		}
		if (valueList.contains("others")) {
			String[] allType = {
					"application/vnd.openxmlformats-officedocument.presentationml.presentation",
					"application/vnd.openxmlformats-officedocument.wordprocessingml.document",
					"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
					"audio/x-pn-realaudio", "audio/wrf", "audio/f4v",
					"video/mp4", "video/3gpp", "video/wmv9", "video/x-ms-wmv",
					"video/x-flv", "video/x-ms-asf", "video/x-msvideo",
					"video/x-sgi-movie", "video/quicktime", "video/mpeg",
					"video/x-la-asf", "audio/mpeg", "audio/x-wav", "audio/ogg",
					"image/bmp", "image/jpeg", "image/gif", "image/cis-cod",
					"image/ief", "image/png", "image/pipeg", "image/x-icon",
					"image/x-xwindowdump", "image/x-portable-anymap",
					"image/tiff", "application/vnd.ms-excel",
					"application/pdf", "application/vnd.ms-powerpoint",
					"application/msword" };

			List<String> interType = ArrayUtil.convertArrayToList(allType);
			interType.removeAll(fileTypeList);
			StringBuilder sBuilder = new StringBuilder();
			for (String inter : interType) {
				sBuilder.append("'" + inter + "',");
			}
			allFileType = " not in ("
					+ sBuilder.substring(0, sBuilder.length() - 1) + ")";
		} else {
			StringBuilder sBuilder = new StringBuilder();
			for (String ls : fileTypeList) {
				sBuilder.append("'" + ls + "',");
			}
			allFileType = " in ("
					+ sBuilder.substring(0, sBuilder.length() - 1) + ")";

		}
		return allFileType;
	}

	/**
	 * 授权人员查询语句
	 * 
	 * @param userId
	 * @return
	 */
	private String getNativeQuery(String userId) {
		String resultSQL = "select s.fd_id from km_signature_main s,km_signature_users su where s.fd_id=su.fd_signature_id and su.fd_org_id in ("
				+ getAuthEditIds(userId) + ")";
		return resultSQL;
	}

	/**
	 * 通过当前登陆用户id解析当前登陆用户的机构、部门、岗位
	 * 
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String getAuthEditIds(String userId) {
		StringBuffer sb = new StringBuffer("");
		ISysOrgPersonService sysOrgPersonService = (ISysOrgPersonService) SpringBeanUtil
				.getBean("sysOrgPersonService");
		SysOrgPerson person;
		try {
			person = (SysOrgPerson) sysOrgPersonService
					.findByPrimaryKey(userId);
			if (person != null) {
				List<SysOrgPost> postsList = person.getFdPosts();
				for (SysOrgPost post : postsList) {
					sb.append("'").append(post.getFdId()).append("',");
				}
				String[] parentIds = person.getFdHierarchyId().split("x");
				for (String parentId : parentIds) {
					if (StringUtil.isNotNull(parentId)) {
						sb.append("'").append(parentId).append("',");
					}
				}
				sb.append("'").append(userId).append("'");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	/**
	 * 
	 * 
	 * @param orgId
	 * @return
	 * @throws Exception 
	 */
	private List getOrgAndPost(HttpServletRequest request,String[] orgIds) throws Exception{
		List<String> orgIdList = new ArrayList<String>();
		for(String orgId:orgIds){
			orgIdList.add(orgId);
		}
		
		List<String> postList = new ArrayList<String>();
		for(String orgId:orgIds){
			SysOrgElement org = (SysOrgElement) getSysOrgElementService(request).findByPrimaryKey(orgId);
			List<SysOrgElement> posts = org.getFdPosts();
			for(SysOrgElement post:posts){
				if(!postList.contains(orgId)){
					postList.add(post.getFdId());
				}
			}
			
		}
		for(String post:postList){
			orgIdList.add(post);
		}
		return orgIdList;
	}
}

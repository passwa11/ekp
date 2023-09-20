package com.landray.kmss.third.ding.service.spring;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.third.ding.constant.DingConstant;
import com.landray.kmss.third.ding.event.ThirdDingOmsRelationAddEvent;
import com.landray.kmss.third.ding.event.ThirdDingOmsRelationUpdateEvent;
import com.landray.kmss.third.ding.model.DingConfig;
import com.landray.kmss.third.ding.model.OmsRelationModel;
import com.landray.kmss.third.ding.oms.DingApiService;
import com.landray.kmss.third.ding.service.IOmsRelationService;
import com.landray.kmss.third.ding.util.DingUtils;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.TransactionUtils;
import com.landray.kmss.web.upload.FormFile;
import com.sunbor.web.tag.Page;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.hibernate.query.NativeQuery;
import org.slf4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.transaction.TransactionStatus;

import java.util.*;
import java.util.stream.Collectors;

public class OmsRelationServiceImp extends BaseServiceImp
		implements
			DingConstant,
			IOmsRelationService, ApplicationContextAware {
	protected ISysOrgElementService sysOrgElementService;
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(OmsRelationServiceImp.class);
	public void setSysOrgElementService(
			ISysOrgElementService sysOrgElementService) {
		this.sysOrgElementService = sysOrgElementService;
	}

	@Override
    public void deleteByKey(String fdEkpId, String appKey) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("fdId");
		hqlInfo.setWhereBlock("fdEkpId=:fdEkpId and fdAppKey=:appKey");
		hqlInfo.setParameter("fdEkpId", fdEkpId);
		hqlInfo.setParameter("appKey", appKey);
		String fdId = (String) this.findFirstOne(hqlInfo);
		if (StringUtils.isNotBlank(fdId)) {
			this.delete(fdId);
		}
	}

	@Override
    public void deleteByKey(String fdEkpId) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("fdId");
		hqlInfo.setWhereBlock("fdEkpId=:fdEkpId");
		hqlInfo.setParameter("fdEkpId", fdEkpId);
		String fdId = (String) this.findFirstOne(hqlInfo);
		if (StringUtils.isNotBlank(fdId)) {
			this.delete(fdId);
		}
	}

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		OmsRelationModel model = (OmsRelationModel) modelObj;
		if (StringUtil.isNull(model.getFdAppKey())) {
            model.setFdAppKey(getAppKey());
        }
		String fdId = super.add(modelObj);
		applicationContext.publishEvent(new ThirdDingOmsRelationAddEvent(model.getFdEkpId(),model.getFdType(),model.getFdAppPkId()));
		return fdId;
	}
	
	@Override
	public void update(IBaseModel modelObj) throws Exception {
		OmsRelationModel model = (OmsRelationModel) modelObj;
		super.update(modelObj);
		applicationContext.publishEvent(new ThirdDingOmsRelationUpdateEvent(model.getFdEkpId(),model.getFdType(),model.getFdAppPkId()));
	}

	@Override
	public Page getPage(HQLInfo hqlInfo, String type) throws Exception {
		String sql = "";
		if ("dept".equals(type)) {
			sql = "select m.fd_id,m.fd_ekp_id,m.fd_app_pk_id,d.fd_name from oms_relation_model m,sys_org_element d where m.fd_ekp_id=d.fd_id and d.fd_org_type in (1,2)";
		} else {

			sql = "select m.fd_id,m.fd_ekp_id,m.fd_app_pk_id,d.fd_name,p.fd_login_name,m.fd_union_id,m.fd_account_type from oms_relation_model m,sys_org_person p,sys_org_element d where m.fd_ekp_id=p.fd_id and m.fd_ekp_id=d.fd_id";
		}
		if (StringUtil.isNotNull(hqlInfo.getWhereBlock())) {
            sql += hqlInfo.getWhereBlock();
        }
		if (StringUtil.isNotNull(hqlInfo.getOrderBy())) {
            sql += " order by " + hqlInfo.getOrderBy();
        }
		Page page = Page.getEmptyPage();
		NativeQuery sqlQuery = getBaseDao().getHibernateSession()
				.createNativeQuery(sql);
		int total = sqlQuery.list().size();
		if (total > 0) {
			page.setRowsize(hqlInfo.getRowSize());
			page.setPageno(hqlInfo.getPageNo());
			page.setTotalrows(total);
			page.setOrderby(hqlInfo.getOrderBy());
			page.excecute();
			sqlQuery.setFirstResult(page.getStart());
			sqlQuery.setMaxResults(page.getRowsize());
			Map<String, String> map = null;
			List list = new ArrayList();
			List dlist = sqlQuery.list();
			Object[] o = null;
			for (int i = 0; i < dlist.size(); i++) {
				o = (Object[]) dlist.get(i);
				map = new HashMap<String, String>();
				map.put("fdId", o[0] == null ? "" : o[0].toString());
				map.put("fdEkpId", o[1] == null ? "" : o[1].toString());
				map.put("fdAppPKId", o[2] == null ? "" : o[2].toString());
				map.put("fdName", o[3] == null ? "" : o[3].toString());

				if (!"dept".equals(type)){
					map.put("fdLoginName", o[4] == null ? "" : o[4].toString());
					map.put("fdUnionId", o[5] == null ? "" : o[5].toString());
					map.put("fdAccountType", o[6] == null ? "" : o[6].toString());
				}
				list.add(map);
			}
			page.setList(list);
		}
		return page;
	}

	@Override
	public boolean checkThird(String fdId, String fdAppPkId) throws Exception {
		boolean rtn = true;
		List list = findList(
				"fdAppPkId='" + fdAppPkId + "' and fdId!='" + fdId + "'", null);
		if (list != null && list.size() > 0) {
			rtn = false;
		}
		return rtn;
	}

	@Override
	public boolean checkEKP(String fdId, String fdEkpId) throws Exception {
		boolean rtn = true;
		List list = findList(
				"fdEkpId='" + fdEkpId + "' and fdId!='" + fdId + "'", null);
		if (list != null && list.size() > 0) {
			rtn = false;
		}
		return rtn;
	}

	private DingApiService dingApiService = null;

	@Override
    public Map<String, String> handle(String fdId, String fdAppPkId,
                                      String type) throws Exception {
		Map<String, String> rtn = new HashMap<String, String>();
		List<OmsRelationModel> list = findList(
				"fdAppPkId='" + fdAppPkId + "' and fdId!='" + fdId + "'", null);
		if (list != null && list.size() > 0) {
			rtn.put("status", "0");
			rtn.put("appid", fdAppPkId);
			rtn.put("msg", "");
			return rtn;
		}
		dingApiService = DingUtils.getDingApiService();
		if ("dept".equals(type)) {
			rtn = checkDept(fdAppPkId, rtn);
		} else {
			rtn = checkPerson(fdAppPkId, rtn);
		}
		return rtn;
	}

	private Map<String, String> checkPerson(String fdAppPkId,
			Map<String, String> rtn) throws Exception {
		JSONObject rtnmsg = DingUtils.getDingApiService().userGet_v2(fdAppPkId,
				null);
		if (rtnmsg.containsKey("errcode") && rtnmsg.getInt("errcode") == 0) {
			rtn.put("status", "1");
			rtn.put("appid", fdAppPkId);
			rtn.put("fdType", "8");
			rtn.put("unionId", rtnmsg.getJSONObject("result").getString("unionid"));
			rtn.put("fdAccountType", rtnmsg.getJSONObject("result").getBoolean("exclusive_account")?rtnmsg.getJSONObject("result").getString("exclusive_account_type"):"common");
			rtn.put("msg", "ok");
		} else {
			rtn.put("status", "0");
			rtn.put("appid", "-1");
			rtn.put("msg", rtnmsg.get("errmsg") == null
					? ""
					: rtnmsg.getString("errmsg"));
		}
		return rtn;
	}

	private Map<String, String> checkDept(String fdAppPkId,
			Map<String, String> rtn) throws Exception {
		JSONObject rtnmsg = DingUtils.getDingApiService()
				.departGet(Long.parseLong(fdAppPkId));
		if (rtnmsg.containsKey("errcode") && rtnmsg.getInt("errcode") == 0) {
			rtn.put("status", "1");
			rtn.put("fdType", "2");
			rtn.put("appid", fdAppPkId);
			rtn.put("msg", "ok");
		} else {
			rtn.put("status", "0");
			rtn.put("appid", "-1");
			rtn.put("msg", rtnmsg.get("errmsg") == null
					? ""
					: rtnmsg.getString("errmsg"));
		}
		return rtn;
	}

	@Override
    public JSONArray addExcel(FormFile file) throws Exception {
		JSONArray jsonArr = new JSONArray();
		JSONArray rtnJsonArray = new JSONArray();
		JSONObject jsonObj = null;
		JSONObject rtnJson = null;
		if (file.getFileSize() == 0) {
			return null;
		} else {
			// 数据装载
			POIFSFileSystem fs = new POIFSFileSystem(file.getInputStream());
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb.getSheetAt(0);
			if (sheet.getLastRowNum() < 1
					|| sheet.getRow(0).getLastCellNum() < 3) {
				return null;
			} else {
				String cellVal = null;
				HSSFCell cell = null;
				for (int i = 1; i <= sheet.getLastRowNum(); i++) {
					if (!skipBlankLine(sheet.getRow(i), 3)) {
						jsonObj = new JSONObject();
						cell = sheet.getRow(i).getCell(1);
						jsonObj.put("fdNo", (i + 1) + "");// 行号
						cellVal = getCellValue(cell);
						jsonObj.put("fdEkpId", cellVal);// EKPID
						cell = sheet.getRow(i).getCell(2);
						cellVal = getCellValue(cell);
						jsonObj.put("fdAppPkId", cellVal);// 钉钉的ID
						jsonArr.add(jsonObj);
					}
				}
			}
			Map<String, String> ekpmap = new HashMap<String, String>();
			List<OmsRelationModel> list = findList(null, null);
			for (OmsRelationModel model : list) {
				ekpmap.put(model.getFdEkpId(), model.getFdAppPkId());
			}
			// 数据分析（为空判断，EKPID分析，微信分析）
			String msg = "";
			SysOrgElement ele = null;
			Map<String, String> tmap = null;
			Map<String, Map<String, String>> elementMap = new HashMap<String, Map<String, String>>();
			for (int i = 0; i < jsonArr.size(); i++) {
				jsonObj = jsonArr.getJSONObject(i);
				rtnJson = new JSONObject();
				msg = "第" + jsonObj.get("fdNo") + "行";
				if (!jsonObj.containsKey("fdEkpId")
						|| jsonObj.get("fdEkpId") == null
						|| !jsonObj.containsKey("fdAppPkId")
						|| jsonObj.get("fdAppPkId") == null) {
					rtnJson.put("msg", msg + "的数据为空(整行或某些列)");
					rtnJsonArray.add(rtnJson);
					continue;
				}
				if (ekpmap.containsKey(jsonObj.getString("fdEkpId"))) {
					if (!rtnJson.containsKey("msg")) {
						rtnJson.put("msg",
								msg + "第2列(" + jsonObj.getString("fdEkpId")
										+ ")已经映射，不能重复映射");
					} else {
						rtnJson.put("msg",
								rtnJson.getString("msg") + ",第2列("
										+ jsonObj.getString("fdEkpId")
										+ ")已经映射，不能重复映射");
					}
				}
				if (ekpmap.containsValue(jsonObj.getString("fdAppPkId"))) {
					if (!rtnJson.containsKey("msg")) {
						rtnJson.put("msg",
								msg + "第3列(" + jsonObj.getString("fdAppPkId")
										+ ")已经映射，不能重复映射");
					} else {
						rtnJson.put("msg",
								rtnJson.getString("msg") + ",第3列("
										+ jsonObj.getString("fdAppPkId")
										+ ")已经映射，不能重复映射");
					}
				}
				ele = (SysOrgElement) sysOrgElementService.findByPrimaryKey(
						jsonObj.getString("fdEkpId"), null, true);
				if (ele == null) {
					if (!rtnJson.containsKey("msg")) {
						rtnJson.put("msg",
								msg + "第2列(" + jsonObj.getString("fdEkpId")
										+ ")在组织架构中找不到相关组织数据");
					} else {
						rtnJson.put("msg",
								rtnJson.getString("msg") + ",第2列("
										+ jsonObj.getString("fdEkpId")
										+ ")在组织架构中找不到相关组织数据");
					}
				} else {
					tmap = new HashMap<String, String>();
					if (ele.getFdOrgType() == 1 || ele.getFdOrgType() == 2) {
						tmap = checkDept(jsonObj.getString("fdAppPkId"), tmap);
					} else {
						tmap = checkPerson(jsonObj.getString("fdAppPkId"),
								tmap);
					}
					if (tmap.containsKey("status")) {
						if("0".equals(tmap.get("status").toString())){
							if (!rtnJson.containsKey("msg")) {
								rtnJson.put("msg",
										msg + "第3列("
												+ jsonObj.getString("fdAppPkId")
												+ ")在钉钉中找不到相关组织数据");
							} else {
								rtnJson.put("msg",
										rtnJson.getString("msg") + ",第2列("
												+ jsonObj.getString("fdAppPkId")
												+ ")在钉钉中找不到相关组织数据");
							}
						}else{
							elementMap.put(jsonObj.getString("fdEkpId"),new HashMap(tmap));
						}
					}
					tmap.clear();
				}
				if (rtnJson.containsKey("msg") && rtnJson.get("msg") != null) {
                    rtnJsonArray.add(rtnJson);
                }
			}
			if (rtnJsonArray.size() == 0) {
				OmsRelationModel model = null;
				Map<String,String> tempMap=null;
				for (int i = 0; i < jsonArr.size(); i++) {
					jsonObj = jsonArr.getJSONObject(i);
					if (jsonObj.get("fdEkpId") != null
							&& jsonObj.get("fdAppPkId") != null && elementMap.containsKey(jsonObj.get("fdEkpId"))) {
						tempMap=elementMap.get(jsonObj.get("fdEkpId"));
						model = new OmsRelationModel();
						model.setFdEkpId(jsonObj.get("fdEkpId").toString());
						model.setFdAppPkId(jsonObj.get("fdAppPkId").toString());
						if("8".equals(tempMap.get("fdType"))){
                            model.setFdAccountType(tempMap.get("fdAccountType"));
                            model.setFdUnionId(tempMap.get("unionId"));
                            model.setFdType("8");
						}else if("2".equals(tempMap.get("fdType"))){
							model.setFdType("2");
						}
						model.setFdAppKey(getAppKey());
						if (UserOperHelper.allowLogOper("addExcel",
								getModelName())) {
							UserOperContentHelper.putAdd(model, "fdEkpId",
									"fdAppPkId", "fdAppKey");
						}
						add(model);
					}
				}
				elementMap.clear();
			}
		}
		return rtnJsonArray;
	}

	/**
	 * 返回Excel表格中的值
	 */
	private String getCellValue(HSSFCell cell) {
		String value = "";
		if (cell == null) {
			value = "";
		} else if (cell.getCellType() == org.apache.poi.ss.usermodel.CellType.NUMERIC) {
			value = String.valueOf(cell.getNumericCellValue());
			value = value.replaceAll(".0", "");
		} else if (cell.getCellType() == org.apache.poi.ss.usermodel.CellType.BLANK) {
			value = null;
		} else {
			value = cell.getStringCellValue();
		}
		return value;
	}

	/**
	 * 空白行跳过
	 */
	private boolean skipBlankLine(HSSFRow row, int columnCount) {
		boolean result = true;
		if (row == null) {
			return result;
		}
		int i = 0;
		for (i = 1; i < columnCount; i++) {
			HSSFCell cell = row.getCell(i);
			if (getCellValue(cell) != null) {
				break;
			}
		}
		if (i == columnCount) {
			result = true;
		} else {
			result = false;
		}
		return result;
	}

	private String getAppKey() {
		return StringUtil.isNull(DING_OMS_APP_KEY)
				? "default"
				: DING_OMS_APP_KEY;
	}

	@Override
	public String getDingUserIdByEkpUserId(String ekpUserId) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("fdAppPkId");
		hqlInfo.setWhereBlock("fdEkpId=:fdEkpId and fdType=:fdType");
		hqlInfo.setParameter("fdEkpId", ekpUserId);
		hqlInfo.setParameter("fdType", "8");
		return (String) findFirstOne(hqlInfo);
	}

	@Override
	public String getEkpUserIdByDingUserId(String dingUserId) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("fdEkpId");
		hqlInfo.setWhereBlock("fdAppPkId=:fdAppPkId");
		hqlInfo.setParameter("fdAppPkId", dingUserId);
		return (String) findFirstOne(hqlInfo);
	}

	@Override
	public List<String> getDeptUserDisable(List<String> deptList,
			String type) {
		List<String> orgIds = new ArrayList<String>();
		if (deptList.size() > 0) {
			String dids = "(";
			for (int i = 0; i < deptList.size(); i++) {
				dids += "'" + deptList.get(i);
				if (i + 1 < deptList.size()) {
					dids += "',";
				} else {
					dids += "'";
				}
			}
			dids += ")";
			String sql = null;
			if("dept".equals(type)){				
				sql = "select m.fd_ekp_id from oms_relation_model m,sys_org_element d where m.fd_ekp_id=d.fd_id and d.fd_hierarchy_id != '0' and d.fd_org_type in (1,2) and m.fd_app_pk_id not in "
						+ dids + "ORDER BY m.fd_app_pk_id DESC";
				
			}else if("person".equals(type)){
				sql = "select m.fd_ekp_id from oms_relation_model m,sys_org_element d where m.fd_ekp_id=d.fd_id and d.fd_org_type = 8 and m.fd_app_pk_id not in "
						+ dids;
			}
			 
			NativeQuery sqlQuery = getBaseDao().getHibernateSession()
					.createNativeQuery(sql);

			// 如果删除的是部门则要把该部门下待岗位也删除
			if ("dept".equals(type) && sqlQuery.list().size() > 0) {
				// 先查出部门名称
				NativeQuery deptNameQuery = null;
				NativeQuery postIdQuery = null;
				String postSql = null;

				for (int j = 0; j < sqlQuery.list().size(); j++) {
					deptNameQuery = getBaseDao().getHibernateSession().createNativeQuery(
									"select fd_name from sys_org_element where fd_id='"
											+ sqlQuery.list().get(j) + "'");
					deptNameQuery.setMaxResults(1);
					if (deptNameQuery.list().size() > 0) {
						postSql = "select fd_id from sys_org_element where fd_org_type = 4 and fd_name in ('"
								+ deptNameQuery.list().get(0) + "_成员','"
								+ deptNameQuery.list().get(0) + "_主管')";
						postIdQuery = getBaseDao().getHibernateSession().createNativeQuery(postSql);
						if (postIdQuery.list().size() > 0) {
							orgIds.addAll(postIdQuery.list());
						}
					}
				}
			}
			orgIds.addAll(sqlQuery.list());
		}

		return orgIds;
	}

	@Override
	public void deleteEkpOrg() throws Exception {
		String sql = "SELECT fd_id FROM sys_org_element WHERE fd_is_external = 0 and fd_is_business = 1 and fd_org_type in(1,2,8) and fd_is_available=1 and fd_id NOT IN (SELECT fd_ekp_id FROM oms_relation_model) and fd_name_pinyin != 'guanliyuan' ORDER BY fd_hierarchy_id DESC";
		NativeQuery sqlQuery = getBaseDao().getHibernateSession()
				.createNativeQuery(sql);
		List<String> orgList = sqlQuery.list();
		if (orgList.size() > 0) {
			logger.debug("ekp端存在多余数据，将执行禁用操作！！！");
			SysOrgElement org;
			// 如果ekp端有自定义的顶层部门
			// 获取根目录id
			String orgId = DingConfig.newInstance().getDingInOrgId();
			if (StringUtil.isNotNull(orgId)) {
				String orgsql = "SELECT fd_hierarchy_id FROM sys_org_element WHERE fd_id='"
						+ orgId + "'";
				NativeQuery orgsqlQuery = getBaseDao().getHibernateSession()
						.createNativeQuery(orgsql);
				orgsqlQuery.setMaxResults(1);
				if (orgsqlQuery.list().size() > 0) {
					final String hierarchyId = (String) orgsqlQuery.list().get(0);
					if (StringUtil.isNotNull(hierarchyId)) {
						orgList = orgList.stream().filter(id->{
							if (hierarchyId.contains(id)) {
								if(logger.isDebugEnabled()){
									logger.debug("id: " + id + " 是顶层id或顶层的父级！");
								}
								return false;
							}
							return true;
						}).collect(Collectors.toList());
					}
				}
			}

			int batchSize = 100;
			int startIndex = 0;
			int endIndex = (orgList.size() < batchSize) ? orgList.size() : (startIndex + batchSize);
			do{
				if(orgList.size() <= startIndex){
					break;
				}
				List<String> subOrgList = orgList.subList(startIndex, endIndex);
				List<SysOrgElement> subOrgEles = sysOrgElementService.findByPrimaryKeys(subOrgList.toArray(new String[]{}));
				TransactionStatus status = null;
				try {
					status = TransactionUtils.beginNewTransaction(30);
					for(SysOrgElement ele : subOrgEles){
						ele.setFdIsAvailable(false);
						ele.setFdHierarchyId("0");
						ele.setFdAlterTime(new Date());
						sysOrgElementService.update(ele);
					}
					TransactionUtils.commit(status);
				}
				catch (Exception e){
					logger.error(e.getMessage(),e);
					if(status!=null && status.isRollbackOnly()){
						TransactionUtils.getTransactionManager().rollback(status);
					}
				}
				startIndex = endIndex;
				endIndex += batchSize;
				if(endIndex > orgList.size()){
					endIndex = orgList.size();
				}
			}
			while(orgList.size() >= endIndex);
		} else {
			logger.debug("ekp端没有需要删除的数据");
		}
	}

	public ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
    public OmsRelationModel findByEkpId(String ekpId) throws Exception {
		HQLInfo info = new HQLInfo();
		info.setWhereBlock("fdEkpId = :ekpId");
		info.setParameter("ekpId", ekpId);
		List list = this.findList(info);
		if (list == null || list.isEmpty()) {
			return null;
		} else if (list.size() == 1) {
			return (OmsRelationModel) list.get(0);
		} else {
			throw new Exception("映射表数据重复,ekpId:" + ekpId);
		}
	}

	@Override
    public OmsRelationModel findByAppPkId(String appPkId)
			throws Exception {
		HQLInfo info = new HQLInfo();
		info.setWhereBlock(
				"fdAppPkId = :appPkId");
		info.setParameter("appPkId", appPkId);
		List list = this.findList(info);
		if (list == null || list.isEmpty()) {
			return null;
		} else if (list.size() == 1) {
			return (OmsRelationModel) list.get(0);
		} else {
			throw new Exception("映射表数据重复：appPkId" + appPkId);
		}
	}
}

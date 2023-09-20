package com.landray.kmss.third.weixin.work.spi.service.spring;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.third.weixin.work.api.WxworkApiService;
import com.landray.kmss.third.weixin.work.constant.WxworkConstant;
import com.landray.kmss.third.weixin.work.model.WeixinWorkConfig;
import com.landray.kmss.third.weixin.work.model.api.WxDepart;
import com.landray.kmss.third.weixin.work.model.api.WxUser;
import com.landray.kmss.third.weixin.work.spi.model.WxworkOmsRelationModel;
import com.landray.kmss.third.weixin.work.spi.service.IWxworkOmsRelationService;
import com.landray.kmss.third.weixin.work.util.WxworkUtils;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.upload.FormFile;
import com.sunbor.web.tag.Page;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.hibernate.SQLQuery;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WxworkOmsRelationServiceImp extends BaseServiceImp
		implements
			WxworkConstant,
			IWxworkOmsRelationService {

	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(WxworkOmsRelationServiceImp.class);

	protected ISysOrgElementService sysOrgElementService;

	public void setSysOrgElementService(
			ISysOrgElementService sysOrgElementService) {
		this.sysOrgElementService = sysOrgElementService;
	}

	@Override
    public void deleteByKey(String fdEkpId, String appKey) throws Exception {
		IBaseModel model = (IBaseModel) this.findFirstOne(
				"fdEkpId='" + fdEkpId + "' and fdAppKey='" + appKey + "'",
				null);
		if (model != null) {
			this.delete(model);
		}
	}

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		WxworkOmsRelationModel model = (WxworkOmsRelationModel) modelObj;
		if (StringUtil.isNull(model.getFdAppKey())) {
            model.setFdAppKey("default");
        }
		return super.add(modelObj);
	}

	@Override
	public Page getPage(HQLInfo hqlInfo, String type) throws Exception {
		String sql = "";
		if ("dept".equals(type)) {
			sql = "select m.fd_id,m.fd_ekp_id,m.fd_app_pk_id,d.fd_name from wxwork_oms_relation_model m,sys_org_element d where m.fd_ekp_id=d.fd_id and d.fd_org_type in (1,2)";
		} else {
			sql = "select m.fd_id,m.fd_ekp_id,m.fd_app_pk_id,d.fd_name,p.fd_login_name from wxwork_oms_relation_model m,sys_org_person p,sys_org_element d where m.fd_ekp_id=p.fd_id and m.fd_ekp_id=d.fd_id";
		}
		if (StringUtil.isNotNull(hqlInfo.getWhereBlock())) {
            sql += hqlInfo.getWhereBlock();
        }
		if (StringUtil.isNotNull(hqlInfo.getOrderBy())) {
            sql += " order by " + hqlInfo.getOrderBy();
        }
		Page page = Page.getEmptyPage();
		SQLQuery sqlQuery = getBaseDao().getHibernateSession()
				.createSQLQuery(sql);
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
				if (!"dept".equals(type)) {
                    map.put("fdLoginName", o[4] == null ? "" : o[4].toString());
                }
				list.add(map);
			}
			page.setList(list);
		}
		return page;
	}

	@Override
	public boolean checkThird(String fdId, String fdAppPkId, String type)
			throws Exception {
		return checkMapping(fdId,fdAppPkId,type,null);
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

	private WxworkApiService wxworkApiService = null;

	@Override
    public Map<String, String> handle(String fdId, String fdAppPkId,
                                      String type,String fdEkpId) throws Exception {
		Map<String, String> rtn = new HashMap<String, String>();
		//校验ekp的企业微信映射关系
		boolean passFlag=checkMapping(fdId,fdAppPkId,type,fdEkpId);
		if (!passFlag) {
			rtn.put("status", "0");
			rtn.put("appid", fdAppPkId);
			rtn.put("msg", "映射表数据重复");
			return rtn;
		}

		//校验企业微信端的userid
		wxworkApiService = WxworkUtils.getWxworkApiService();
		if ("dept".equals(type)) {
			rtn = checkDept(fdAppPkId, rtn);
		} else {
			rtn = checkPerson(fdAppPkId, rtn);
		}
		return rtn;
	}

	/**
	 * 校验ekp映射关系是否有重复
	 * @param fdId
	 * @param fdAppPkId
	 * @param type
	 * @param fdEkpId
	 * @return
	 */
	private boolean checkMapping(String fdId, String fdAppPkId, String type, String fdEkpId) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("fdEkpId");
		if(StringUtil.isNotNull(fdEkpId)){
			hqlInfo.setWhereBlock("(fdEkpId=:fdEkpId or fdAppPkId=:fdAppPkId) and fdId!=:fdId");
			hqlInfo.setParameter("fdEkpId",fdEkpId);
		}else{
            //仅根据userid检查映射关系
			hqlInfo.setWhereBlock("fdAppPkId=:fdAppPkId and fdId!=:fdId");
		}
		hqlInfo.setParameter("fdAppPkId",fdAppPkId);
		hqlInfo.setParameter("fdId",fdId);
		List<Object> list= findList(hqlInfo);
		if (list != null && list.size() > 0) {
			ISysOrgElementService elementService = (ISysOrgElementService) SpringBeanUtil.getBean("sysOrgElementService");
			SysOrgElement ekpModel = null;
			for (Object id : list) {
				ekpModel = (SysOrgElement) elementService.findByPrimaryKey(id.toString(), null,true);
				if (ekpModel.getFdOrgType().intValue() == 8) {
					if ("person".equals(type)) {
						return false;
					}
				} else {
					if ("dept".equals(type)) {
						return false;
					}
				}
			}
		}
		return true;
	}

	private Map<String, String> checkPerson(String fdAppPkId,
			Map<String, String> rtn) throws Exception {
		try {
			String result = WxworkUtils.getWxworkApiService().userGet(fdAppPkId);

			JSONObject resulrObj = JSONObject.fromObject(result);

			if (resulrObj.getInt("errcode") == 0) {
				rtn.put("status", "1");
				rtn.put("appid", fdAppPkId);
				rtn.put("msg", "ok");
			} else {
				String msg = null;
				if (60111 == resulrObj.getInt("errcode")) {
					msg = ResourceUtil.getString("wxOmsRelation.wx.no.data",
							"third-weixin-work");
				} else {
					msg = "出错信息：errorCode=" + resulrObj.getInt("errcode")
							+ ",errorMsg=" + resulrObj.getString("errmsg");
				}

				rtn.put("status", "0");
				rtn.put("appid", "-1");
				rtn.put("msg", msg);
			}
		} catch (Exception e) {

			rtn.put("status", "0");
			rtn.put("appid", "-1");
			rtn.put("msg", e.getMessage());
		}
		return rtn;
	}

	private Map<String, String> checkDept(String fdAppPkId,
			Map<String, String> rtn) throws Exception {
		String msg = "";
		try {
			boolean flag = false;
			List<WxDepart> depts = WxworkUtils.getWxworkApiService().departGet(fdAppPkId);

			if (depts != null && depts.size() > 0) {
				if (UserOperHelper.allowLogOper("handle", getModelName())) {
					UserOperContentHelper.putFinds(depts);
				}
				for (int i = 0; i < depts.size(); i++) {
					if (fdAppPkId.equals(depts.get(i).getId() + "")) {
						flag = true;
						break;
					}
				}
			}
			if (flag) {
				rtn.put("status", "1");
				rtn.put("appid", fdAppPkId);
				rtn.put("msg", "ok");
			} else {
				msg = ResourceUtil.getString("wxOmsRelation.wx.no.data.org",
						"third-weixin-work");
				rtn.put("status", "0");
				rtn.put("appid", "-1");
				rtn.put("msg", msg);
			}
		} catch (Exception e) {
			rtn.put("status", "0");
			rtn.put("appid", "-1");
			rtn.put("msg", e.getMessage());
		}
		return rtn;
	}

	private WxUser getUser(SysOrgPerson element, Long pid)
			throws Exception {
		WxUser user = new WxUser();
		String sex = element.getFdSex();
		if ("M".equalsIgnoreCase(sex)) {
			user.setGender("1");
		} else if ("F".equalsIgnoreCase(sex)) {
			user.setGender("2");
		}
		// 根据配置来确定是选择那种作为企业号的userid，默认是登录名
		String wxln = WeixinWorkConfig.newInstance().getWxLoginName();
		if ("id".equalsIgnoreCase(wxln)) {
			user.setUserId(element.getFdId());
		} else {
			user.setUserId(element.getFdLoginName());
		}
		user.setName(element.getFdName());
		user.setDepartIds(new Long[]{pid});
		if (StringUtil.isNotNull(element.getFdMobileNo())) {
			user.setMobile(element.getFdMobileNo());
		}
		if (StringUtil.isNotNull(element.getFdEmail())) {
			user.setEmail(element.getFdEmail());
		}
		List<SysOrgElement> posts = element.getHbmPosts();
		if (posts != null && !posts.isEmpty() && posts.get(0) != null) {
			user.setPosition(posts.get(0).getFdName());
		}
		return user;
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
						jsonObj.put("fdAppPkId", cellVal);// 微信企业号的ID
						jsonArr.add(jsonObj);
					}
				}
			}
			Map<String, String> ekpmap = new HashMap<String, String>();
			List<WxworkOmsRelationModel> list = findList(null, null);
			for (WxworkOmsRelationModel model : list) {
				ekpmap.put(model.getFdEkpId(), model.getFdAppPkId());
			}
			// 数据分析（为空判断，EKPID分析，微信分析）
			String msg = "";
			SysOrgElement ele = null;
			Map<String, String> tmap = null;
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
					if (tmap.containsKey("status")
							&& "0".equals(tmap.get("status").toString())) {
						if (!rtnJson.containsKey("msg")) {
							rtnJson.put("msg",
									msg + "第3列("
											+ jsonObj.getString("fdAppPkId")
											+ ")在企业微信中找不到相关组织数据");
						} else {
							rtnJson.put("msg",
									rtnJson.getString("msg") + ",第2列("
											+ jsonObj.getString("fdAppPkId")
											+ ")在企业微信中找不到相关组织数据");
						}
					}
					tmap.clear();
				}
				if (rtnJson.containsKey("msg") && rtnJson.get("msg") != null) {
                    rtnJsonArray.add(rtnJson);
                }
			}
			if (rtnJsonArray.size() == 0) {
				WxworkOmsRelationModel model = null;
				for (int i = 0; i < jsonArr.size(); i++) {
					jsonObj = jsonArr.getJSONObject(i);
					if (jsonObj.get("fdEkpId") != null
							&& jsonObj.get("fdAppPkId") != null) {
						model = new WxworkOmsRelationModel();
						model.setFdEkpId(jsonObj.get("fdEkpId").toString());
						model.setFdAppPkId(jsonObj.get("fdAppPkId").toString());
						model.setFdAppKey(getAppKey());
						if (UserOperHelper.allowLogOper("addExcel",
								getModelName())) {
							UserOperContentHelper.putAdd(model, "fdEkpId",
									"fdAppPkId", "fdAppKey");
						}
						add(model);
					}
				}
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
		return StringUtil.isNull(WXWORK_OMS_APP_KEY)
				? "default"
				: WXWORK_OMS_APP_KEY;
	}
	
	@Override
	public WxworkOmsRelationModel findByEkpId(String ekpId) throws Exception {
		if (StringUtil.isNull(ekpId)) {
			return null;
		}
		HQLInfo info = new HQLInfo();
		info.setWhereBlock("wxworkOmsRelationModel.fdEkpId=:ekpId");
		info.setParameter("ekpId", ekpId);
		List list = this.findList(info);
		if (list == null || list.isEmpty()) {
			return null;
		}
		if (list.size() > 1) {
			throw new Exception("映射表数据重复，" + ekpId);
		}
		return (WxworkOmsRelationModel) list.get(0);
	}

	@Override
	public WxworkOmsRelationModel findByUserId(String userId) throws Exception {
		if (StringUtil.isNull(userId)) {
			return null;
		}
		HQLInfo info = new HQLInfo();
		info.setWhereBlock("wxworkOmsRelationModel.fdAppPkId=:userId");
		info.setParameter("userId", userId);
		List list = this.findList(info);
		if (list == null || list.isEmpty()) {
			return null;
		}
		if (list.size() > 1) {
			throw new Exception("映射表数据重复，" + userId);
		}
		return (WxworkOmsRelationModel) list.get(0);
	}

	@Override
	public List<Map> getListByType(String type) throws Exception {

		List<Map> list = new ArrayList<Map>();
		String sql = "";
		if ("dept".equals(type)) {
			sql = "select m.fd_id,m.fd_ekp_id,m.fd_app_pk_id,d.fd_name from wxwork_oms_relation_model m,sys_org_element d where m.fd_ekp_id=d.fd_id and d.fd_org_type in (1,2)";
		} else {
			sql = "select m.fd_id,m.fd_ekp_id,m.fd_app_pk_id,d.fd_name,p.fd_login_name from wxwork_oms_relation_model m,sys_org_person p,sys_org_element d where m.fd_ekp_id=p.fd_id and m.fd_ekp_id=d.fd_id";
		}
		SQLQuery sqlQuery = getBaseDao().getHibernateSession()
				.createSQLQuery(sql);
		int total = sqlQuery.list().size();
		if (total > 0) {
			Map<String, String> map = null;
			List dlist = sqlQuery.list();
			Object[] o = null;
			for (int i = 0; i < dlist.size(); i++) {
				o = (Object[]) dlist.get(i);
				map = new HashMap<String, String>();
				map.put("fdId", o[0] == null ? "" : o[0].toString());
				map.put("fdEkpId", o[1] == null ? "" : o[1].toString());
				map.put("fdAppPKId", o[2] == null ? "" : o[2].toString());
				map.put("fdName", o[3] == null ? "" : o[3].toString());
				if (!"dept".equals(type)) {
                    map.put("fdLoginName", o[4] == null ? "" : o[4].toString());
                }
				list.add(map);
			}

		}
		return list;
	}

	@Override
	public String findEkpfdIdByWxId(String wxId, String type) throws Exception {
		String sql = "";
		if ("dept".equals(type)) {
			sql = "select m.fd_id,m.fd_ekp_id,m.fd_app_pk_id,d.fd_name from wxwork_oms_relation_model m,sys_org_element d where m.fd_ekp_id=d.fd_id and d.fd_org_type in (1,2) and m.fd_app_pk_id='"
					+ wxId + "'";
		} else {
			sql = "select m.fd_id,m.fd_ekp_id,m.fd_app_pk_id,d.fd_name,p.fd_login_name from wxwork_oms_relation_model m,sys_org_person p,sys_org_element d where m.fd_ekp_id=p.fd_id and m.fd_ekp_id=d.fd_id and m.fd_app_pk_id='"
					+ wxId + "'";
		}
		SQLQuery sqlQuery = getBaseDao().getHibernateSession()
				.createSQLQuery(sql);
		int total = sqlQuery.list().size();
		if (total > 1) {
			throw new Exception("映射表数据重复，微信wxId：" + wxId + "  type:" + type);
		} else if (total == 1) {
			Object[] model = (Object[]) sqlQuery.list().get(0);
			return model[1].toString();
		}
		return null;
	}

	@Override
	public WxworkOmsRelationModel updateOpenIdByEkpId(String ekpId) throws Exception {
		if (StringUtil.isNull(ekpId)) {
			return null;
		}
		WxworkOmsRelationModel model = findByEkpId(ekpId);
		if(model==null || StringUtil.isNull(model.getFdAppPkId())){
			return null;
		}
		String fdOpenId = model.getFdOpenId();
		if(StringUtil.isNull(fdOpenId)){
			fdOpenId = WxworkUtils.getWxworkApiService().getUserOpenId(model.getFdAppPkId());
			model.setFdOpenId(fdOpenId);
			update(model);
		}
		return model;
	}

	@Override
	public WxworkOmsRelationModel findByOpenId(String openId) throws Exception {
		if (StringUtil.isNull(openId)) {
			return null;
		}
		HQLInfo info = new HQLInfo();
		info.setWhereBlock("wxworkOmsRelationModel.fdOpenId=:openId");
		info.setParameter("openId", openId);
		List list = this.findList(info);
		if (list == null || list.isEmpty()) {
			return null;
		}
		if (list.size() > 1) {
			throw new Exception("映射表数据重复，" + openId);
		}
		return (WxworkOmsRelationModel) list.get(0);
	}

	@Override
	public WxworkOmsRelationModel updateOpenIdByOpenId(String openId) throws Exception {
		if (StringUtil.isNull(openId)) {
			return null;
		}
		WxworkOmsRelationModel model = findByOpenId(openId);
		if(model!=null){
			return model;
		}
		String userId =  WxworkUtils.getWxworkApiService().getUserId(openId);
		if(StringUtil.isNotNull(userId)){
			model = findByUserId(userId);
			if(model==null){
				logger.warn("根据userId找不到映射，userId:"+userId+"，openId:"+openId);
				return null;
			}
			model.setFdOpenId(openId);
			update(model);
		}
		return model;
	}

}

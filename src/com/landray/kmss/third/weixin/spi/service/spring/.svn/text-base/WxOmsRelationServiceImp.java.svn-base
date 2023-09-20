package com.landray.kmss.third.weixin.spi.service.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.hibernate.query.NativeQuery;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.third.weixin.api.WxApiService;
import com.landray.kmss.third.weixin.constant.WxConstant;
import com.landray.kmss.third.weixin.model.WeixinConfig;
import com.landray.kmss.third.weixin.model.api.WxDepart;
import com.landray.kmss.third.weixin.model.api.WxUser;
import com.landray.kmss.third.weixin.spi.model.WxOmsRelationModel;
import com.landray.kmss.third.weixin.spi.service.IWxOmsRelationService;
import com.landray.kmss.third.weixin.util.WxUtils;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.upload.FormFile;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class WxOmsRelationServiceImp extends BaseServiceImp
		implements
			WxConstant,
			IWxOmsRelationService {
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
		WxOmsRelationModel model = (WxOmsRelationModel) modelObj;
		if (StringUtil.isNull(model.getFdAppKey())) {
            model.setFdAppKey("default");
        }
		return super.add(modelObj);
	}

	@Override
	public Page getPage(HQLInfo hqlInfo, String type) throws Exception {
		String sql = "";
		if ("dept".equals(type)) {
			sql = "select m.fd_id,m.fd_ekp_id,m.fd_app_pk_id,d.fd_name from wx_oms_relation_model m,sys_org_element d where m.fd_ekp_id=d.fd_id and d.fd_org_type in (1,2)";
		} else {
			sql = "select m.fd_id,m.fd_ekp_id,m.fd_app_pk_id,d.fd_name,p.fd_login_name from wx_oms_relation_model m,sys_org_person p,sys_org_element d where m.fd_ekp_id=p.fd_id and m.fd_ekp_id=d.fd_id";
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
	public boolean checkThird(String fdId, String fdAppPkId) throws Exception {
		boolean rtn = true;
		List list = findList(
				"fdAppPkId='" + fdAppPkId + "' and fdId!='" + fdId + "'", null);
		if (list != null && list.size() > 0) {
			if (UserOperHelper.allowLogOper("checkThird", getModelName())) {
				UserOperContentHelper.putFinds(list);
			}
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

	private WxApiService wxApiService = null;

	@Override
    public Map<String, String> handle(String fdId, String fdAppPkId,
                                      String type) throws Exception {
		Map<String, String> rtn = new HashMap<String, String>();
		List list = findList(
				"fdAppPkId='" + fdAppPkId + "' and fdId!='" + fdId + "'", null);
		if (list != null && list.size() > 0) {
			if (UserOperHelper.allowLogOper("handle", getModelName())) {
				UserOperContentHelper.putFinds(list);
			}
			rtn.put("status", "0");
			rtn.put("appid", fdAppPkId);
			rtn.put("msg", "");
			return rtn;
		}
		wxApiService = WxUtils.getWxApiService();
		if ("dept".equals(type)) {
			rtn = checkDept(fdAppPkId, rtn);
		} else {
			rtn = checkPerson(fdAppPkId, rtn);
		}
		return rtn;
	}

	private Map<String, String> checkPerson(String fdAppPkId,
			Map<String, String> rtn) throws Exception {
		try {
			String result = wxApiService.userGet(fdAppPkId);

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
			List<WxDepart> depts = wxApiService.departGet(fdAppPkId);

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
				msg = ResourceUtil.getString("wxOmsRelation.wx.no.data",
						"third-weixin");
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
		String wxln = WeixinConfig.newInstance().getWxLoginName();
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
						jsonObj.put("fdNo", (i+1) + "");// 行号
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
			List<WxOmsRelationModel> list = findList(null, null);
			for (WxOmsRelationModel model : list) {
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
						rtnJson.put("msg", msg + "第2列("
								+ jsonObj.getString("fdEkpId") + ")已经映射，不能重复映射");
					} else {
						rtnJson.put("msg", rtnJson.getString("msg") + ",第2列("
								+ jsonObj.getString("fdEkpId") + ")已经映射，不能重复映射");
					}
				}
				if (ekpmap.containsValue(jsonObj.getString("fdAppPkId"))) {
					if (!rtnJson.containsKey("msg")) {
						rtnJson.put("msg", msg + "第3列("
								+ jsonObj.getString("fdAppPkId") + ")已经映射，不能重复映射");
					} else {
						rtnJson.put("msg", rtnJson.getString("msg") + ",第3列("
								+ jsonObj.getString("fdAppPkId") + ")已经映射，不能重复映射");
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
				WxOmsRelationModel model = null;
				for (int i = 0; i < jsonArr.size(); i++) {
					jsonObj = jsonArr.getJSONObject(i);
					if (jsonObj.get("fdEkpId") != null
							&& jsonObj.get("fdAppPkId") != null) {
						model = new WxOmsRelationModel();
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
		return StringUtil.isNull(WX_OMS_APP_KEY) ? "default" : WX_OMS_APP_KEY;
	}
}

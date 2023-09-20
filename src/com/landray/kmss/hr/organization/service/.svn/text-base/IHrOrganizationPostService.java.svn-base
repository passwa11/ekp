package com.landray.kmss.hr.organization.service;

import java.io.InputStream;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;

import com.landray.kmss.hr.organization.model.HrOrganizationPost;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;

import net.sf.json.JSONObject;

public interface IHrOrganizationPostService extends IHrOrganizationElementService {

	/**
	 * <p>获取岗位下所有人员列表(不包含兼岗人员)</p>
	 * @return
	 * @throws Exception
	 * @author sunj
	 */
	public List<HrStaffPersonInfo> getPostPersons(String fdId) throws Exception;

	/**
	 * <p>获取机构、部门下所有岗位</p>
	 * @param fdId
	 * @return
	 * @throws Exception
	 * @author sunj
	 */
	public List<HrOrganizationPost> getPostsByOrgId(String fdId) throws Exception;

	/**
	 * <p>导入</p>
	 * @param inputStream
	 * @param locale
	 * @return
	 * @author sunj
	 */
	public JSONObject addImportData(InputStream inputStream, Locale locale) throws Exception;

	/**
	 * <p>下载导入模板</p>
	 * @param request
	 * @return
	 */
	@Override
    public HSSFWorkbook buildTemplateWorkbook(HttpServletRequest request)
			throws Exception;

	/**
	 * <p>通过职级获取岗位信息</p>
	 * @param fdId
	 * @return
	 * @throws Exception
	 * @author sunj
	 */
	public List<HrOrganizationPost> getPostsByRankId(String fdId) throws Exception;

	/**
	 * <p>获取职级是否数据关联</p>
	 * @param fdId
	 * @return
	 * @throws Exception
	 * @author xch
	 */
	public boolean getPostAndPersonByRankId(String fdId) throws Exception;

	/**
	 * <p>获取岗位序列下所有岗位</p>
	 * @return
	 * @throws Exception
	 * @author sunj
	 */
	public List<HrOrganizationPost> getPostById(String fdId) throws Exception;

	public void buildPostSheet(HSSFWorkbook wb) throws Exception;

	public JSONObject addImportData(Sheet sheetAt, Locale locale)
			throws Exception;

	public List findPostsByName(String value) throws Exception;

}

package com.landray.kmss.km.archives.service;

import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.usermodel.Row;

import com.landray.kmss.km.archives.forms.KmArchivesMainForm;
import com.landray.kmss.km.archives.model.ErrorRowInfo;
import com.landray.kmss.km.archives.model.ExcelImportResult;
import com.landray.kmss.km.archives.model.KmArchivesCategory;
import com.landray.kmss.km.archives.model.KmArchivesMain;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;
import com.landray.kmss.util.excel.WorkBook;

import net.sf.json.JSONArray;

public interface IKmArchivesMainService extends IExtendDataService {

	public static final String FD_ATT_KEY = "attArchivesMain";
	
	public static final String FD_TEMP_ATT_KEY = "attArchivesMain_temp";

    public abstract List<KmArchivesMain> findByDocTemplate(KmArchivesCategory docTemplate) throws Exception;

	/**
	 * 构建导入模板
	 * 
	 * @param docTemplate
	 * @throws Exception
	 */
	public WorkBook buildTemplateWorkbook(HttpServletRequest request)
			throws Exception;

	/**
	 * 导入模板数据
	 * 
	 * @param inputStream
	 * @param docTemplate
	 * @param locale
	 * @return
	 */
	public abstract ExcelImportResult addImportData(InputStream inputStream,
			String docTemplate, Locale locale) throws Exception;

	public ErrorRowInfo importRowData(KmArchivesCategory category, Row row,
			ExcelImportResult result, Locale locale) throws Exception;
	/**
	 * 替换特殊字符
	 * 
	 * @param oriString
	 * @return
	 * @throws Exception
	 */
	public String replaceCharacter(String oriString) throws Exception;

	/**
	 * 档案到期提醒
	 * 
	 * @throws Exception
	 */
	public void sendExpireWarn() throws Exception;

	/**
	 * 判断档案是否可以删除
	 * 
	 * @param fdId
	 * @throws Exception
	 */
	public boolean validateArchives(String fdId) throws Exception;

	public String getCount(String type, Boolean isDraft) throws Exception;

	/**
	 * 获取移动端首页数据
	 * 
	 * @return
	 * @throws Exception
	 */
	public JSONArray getArchivesMobileIndex() throws Exception;

	/**
	 * 修改文件级可阅读者权限
	 * 
	 * @param mainForm
	 * @param fdIds
	 * @param optType
	 * @throws Exception
	 */
	public void updateDocRight(KmArchivesMainForm mainForm, String[] fdIds,
			String oprType) throws Exception;

	public void updatePreFiles(String[] fdIds) throws Exception;

	public void updatePreFile(String fdId) throws Exception;

	public void updateChangeCates(String[] fdIds, String cateGoryId) throws Exception;

	public void updateChangeCate(String fdId, String cateGoryId) throws Exception;
	
    /**
     * 获取(我已审)档案审批统计数字
     * @param startTime  统计范围起始时间（可为空）
     * @param endTime 统计范围截至时间（可为空）  
     * @return
     * @throws Exception
     */	
	public Long getApprovedStatisticalCount(Date startTime, Date endTime) throws Exception;
	
	/**
	 * 更新档案信息
	 * @param model
	 * @throws Exception
	 */
	public void updateArchivesMain(KmArchivesMain model) throws Exception;
}

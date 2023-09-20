package com.landray.kmss.fssc.config.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.landray.kmss.fssc.config.forms.FsscConfigScoreForm;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONObject;

/**
  * 点赞积分配置 服务接口
  */
public interface IFsscConfigScoreService extends IExtendDataService {
	
	/**
	 * 导入积分数据
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public List<String> addInfoByImport(FsscConfigScoreForm form,HttpServletRequest request) throws Exception;
	
	
	public abstract Page getDataPage(HttpServletRequest request, JSONObject params) throws Exception;
	
	public abstract HSSFWorkbook getExportDataList(HttpServletRequest request,HttpServletResponse response) throws Exception;


}

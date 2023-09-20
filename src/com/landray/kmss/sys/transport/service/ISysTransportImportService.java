package com.landray.kmss.sys.transport.service;

import java.io.InputStream;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import com.landray.kmss.web.upload.FormFile;

import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.transport.model.SysTransportImportConfig;
import com.landray.kmss.sys.transport.service.spring.ImportContext;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;

public interface ISysTransportImportService extends IBaseService {
	/**
	 * 导入数据
	 * 
	 * @param input
	 *            数据输入流
	 * @param id
	 *            配置ID
	 * @param locale
	 * @param check
	 *            是否为检查模式
	 * @return 错误消息
	 * @throws Exception
	 */
	public KmssMessages importData(InputStream input, String id, Locale locale,
			boolean check) throws Exception;
	
	/**
	 * 导入单行数据
	 */
	public void importRowData(ImportContext context, Row row, Locale locale,
			SysTransportImportConfig importConfig) throws Exception;
	
	/**
	 * excel导入操作出错返回出错信息
	 */
	public String saveExcelError(KmssMessages messages,HttpServletRequest request);

	/**
	 * excel导入操作出错返回出错信息的JSON字符串
	 */
	public String saveExcelErrorJson(KmssMessages messages,
			HttpServletRequest request);

	public String getMessageInfo(KmssMessage msg);

	public void detailTableValidate(FormFile file, HttpServletRequest request,
			HttpServletResponse response, Locale locale) throws Exception;
}

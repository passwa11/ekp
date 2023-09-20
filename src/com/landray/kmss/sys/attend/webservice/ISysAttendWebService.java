package com.landray.kmss.sys.attend.webservice;

import javax.jws.WebService;

import com.landray.kmss.sys.attend.model.SysAttendImportLog;
import com.landray.kmss.sys.webservice2.interfaces.ISysWebservice;

import net.sf.json.JSONArray;

/**
 * @author linxiuxian
 *
 */
@WebService
public interface ISysAttendWebService extends ISysWebservice {

	/**
	 * 增加用户考勤记录
	 * 
	 * @param addContext
	 * @return
	 * @throws Exception
	 */
	public SysAttendResult addAttend(SysAttendAddContext addContext)
			throws Exception;
}

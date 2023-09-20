package com.landray.kmss.sys.oms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.landray.kmss.sys.oms.model.SysOmsTempDept;
import com.landray.kmss.sys.oms.model.SysOmsTempDp;
import com.landray.kmss.sys.oms.model.SysOmsTempPerson;
import com.landray.kmss.sys.oms.model.SysOmsTempPost;
import com.landray.kmss.sys.oms.model.SysOmsTempPp;
import com.landray.kmss.sys.oms.model.SysOmsTempTrx;
import com.landray.kmss.sys.oms.service.ISysOmsTempTrxService;
import com.landray.kmss.sys.oms.service.ISysOmsTempTrxSynService;
import com.landray.kmss.sys.oms.temp.OmsTempSynModel;
import com.landray.kmss.sys.oms.temp.OmsTempSynResult;
import com.landray.kmss.sys.oms.temp.SysOmsSynConfig;
import com.landray.kmss.sys.oms.temp.SysOmsTempConstants;
import com.landray.kmss.web.RestResponse;
import com.landray.kmss.web.annotation.RestApi;

/**
* 组织架构接入restful接口
* @author yuLiang
* @version 1.0 创建时间：2019年12月13日
*/
@Controller
@RequestMapping("/api/sys-oms/tempOrg")
@RestApi(docUrl = "/sys/oms/restService/sysOMSTempOrgDataHelp.jsp", name = "sysOMSTempOrgDataController", resourceKey = "sys-oms:sys.oms.restservice.sysOMSTempOrgDataController")
public class SysOMSTempOrgDataController {
	protected static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysOMSTempOrgDataController.class);
	private ISysOmsTempTrxService sysOmsTempTrxService;
	private ISysOmsTempTrxSynService sysOmsTempTrxSynService;
	
	/**
	 * 同步事务开始
	 * @param synModel  1： 同步部门、人员，2：同步部门、人员、部门人员关系 
	 * 3、同步部门、岗位、人员、岗位人员关系， 4、同步部门、岗位、人员、岗位人员关系、部门人员关系
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value = "/begin")
	public Object begin(Integer synModel,SysOmsSynConfig synConfig)
			throws Exception {
		OmsTempSynResult<Object> omsTempSynResult = null;
		OmsTempSynModel omsTempSynModel = OmsTempSynModel.getEnumByValue(synModel);
		if(omsTempSynModel == null) {
			logger.error("模式选择错误");
			return RestResponse.error("模式选择错误");
		}else if(synModel < 100) {
			omsTempSynResult = sysOmsTempTrxService.begin(omsTempSynModel,synConfig.getFdDeptIsAsc(),synConfig.getFdPersonIsAsc());
		}else {
			omsTempSynResult = sysOmsTempTrxSynService.begin(omsTempSynModel,synConfig);
		}
		Map<String,Object> data = null;
		if(omsTempSynResult.getCode() != SysOmsTempConstants.SYS_OMS_TEMP_SYN_CODE_FAIL){
			data = new HashMap<String,Object>();
			data.put("trxId", omsTempSynResult.getTrxId());
		}
		
		return result(omsTempSynResult.getCode(),omsTempSynResult.getMsg(),data);
	
	}
	
	/**
	 * 新增部门
	 * @param trxId 事务ID
 	 * @param tempDeptList 部门列表 前端需要通过body raw格式传
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST,value = "/dept/add")
	public Object addTempDept(String trxId,@RequestBody List<SysOmsTempDept> tempDeptList)
			throws Exception {
		SysOmsTempTrx omsTempTrx = (SysOmsTempTrx) sysOmsTempTrxService.findByPrimaryKey(trxId);
		int synModel = 0;
		if(omsTempTrx != null) {
			synModel = omsTempTrx.getFdSynModel();
		}
		OmsTempSynResult<SysOmsTempDept> omsTempSynResult = null;
		if(synModel < 100) {
			omsTempSynResult = sysOmsTempTrxService.addTempDept(trxId, tempDeptList);
		}else {
			omsTempSynResult = sysOmsTempTrxSynService.addTempDept(trxId, tempDeptList);
		}
		if(synModel < 100) {
			return result(omsTempSynResult.getCode(),omsTempSynResult.getMsg(),null);
		}else {
			return result(omsTempSynResult.getCode(),omsTempSynResult.getMsg(),omsTempSynResult.getIllegalDataMap());
		}
	}
	
	/**
	 * 新增岗位
	 * @param trxId 事务ID
	 * @param tempPostList 岗位列表 前端需要通过body raw格式传
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST,value = "/post/add")
	public Object addTempPost(String trxId,@RequestBody List<SysOmsTempPost> tempPostList)
			throws Exception {
		SysOmsTempTrx omsTempTrx = (SysOmsTempTrx) sysOmsTempTrxService.findByPrimaryKey(trxId);
		int synModel = 0;
		if(omsTempTrx != null) {
			synModel = omsTempTrx.getFdSynModel();
		}
		OmsTempSynResult<SysOmsTempPost> omsTempSynResult = null;
		if(synModel < 100) {
			omsTempSynResult = sysOmsTempTrxService.addTempPost(trxId, tempPostList);
		}else {
			omsTempSynResult = sysOmsTempTrxSynService.addTempPost(trxId, tempPostList);
		}
		if(synModel < 100) {
			return result(omsTempSynResult.getCode(),omsTempSynResult.getMsg(),null);
		}else {
			return result(omsTempSynResult.getCode(),omsTempSynResult.getMsg(),omsTempSynResult.getIllegalDataMap());
		}
	}
	
	/**
	 * 新增人员
	 * @param trxId 事务ID
	 * @param fdExtra 扩展字段 JSON字符串 如[{"fdPersonId":111,"fdExra1":1,"fdExtra2":2}]
	 * @param tempPersonList 人员列表 前端需要通过body raw格式传
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST,value = "/person/add")
	public Object addTempPerson(@RequestParam String trxId,@RequestBody List<SysOmsTempPerson> tempPersonList)
			throws Exception {  
		
		SysOmsTempTrx omsTempTrx = (SysOmsTempTrx) sysOmsTempTrxService.findByPrimaryKey(trxId);
		int synModel = 0;
		if(omsTempTrx != null) {
			synModel = omsTempTrx.getFdSynModel();
		}
		OmsTempSynResult<SysOmsTempPerson> omsTempSynResult = null;
		if(synModel < 100) {
			omsTempSynResult = sysOmsTempTrxService.addTempPerson(trxId, tempPersonList);
		}else {
			omsTempSynResult = sysOmsTempTrxSynService.addTempPerson(trxId, tempPersonList);
		}
		if(synModel < 100) {
			return result(omsTempSynResult.getCode(),omsTempSynResult.getMsg(),null);
		}else {
			return result(omsTempSynResult.getCode(),omsTempSynResult.getMsg(),omsTempSynResult.getIllegalDataMap());
		}
	}
	
	/**
	 * 新增岗位人员关系
	 * @param trxId 事务ID
	 * @param tempPpList 岗位人员关系列表，前端需要通过body raw格式传
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST,value = "/postperson/add")
	public Object addTempPostPerson(String trxId,@RequestBody List<SysOmsTempPp> tempPpList)
			throws Exception {

		SysOmsTempTrx omsTempTrx = (SysOmsTempTrx) sysOmsTempTrxService.findByPrimaryKey(trxId);
		int synModel = 0;
		if(omsTempTrx != null) {
			synModel = omsTempTrx.getFdSynModel();
		}
		OmsTempSynResult<SysOmsTempPp> omsTempSynResult = null;
		if(synModel < 100) {
			omsTempSynResult = sysOmsTempTrxService.addTempPostPerson(trxId, tempPpList);
		}else {
			omsTempSynResult = sysOmsTempTrxSynService.addTempPostPerson(trxId, tempPpList);
		}
		if(synModel < 100) {
			return result(omsTempSynResult.getCode(),omsTempSynResult.getMsg(),null);
		}else {
			return result(omsTempSynResult.getCode(),omsTempSynResult.getMsg(),omsTempSynResult.getIllegalDataMap());
		}
	}
	
	/**
	 * 新增部门人员关系
	 * @param trxId 事务ID
	 * @param tempDpList 部门人员关系列表，前端需要通过body raw格式传
	 * @return 
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST,value = "/deptperson/add")
	public Object addTempDeptPerson(String trxId,@RequestBody List<SysOmsTempDp> tempDpList)
			throws Exception {

		SysOmsTempTrx omsTempTrx = (SysOmsTempTrx) sysOmsTempTrxService.findByPrimaryKey(trxId);
		int synModel = 0;
		if(omsTempTrx != null) {
			synModel = omsTempTrx.getFdSynModel();
		}
		OmsTempSynResult<SysOmsTempDp> omsTempSynResult = null;
		if(synModel < 100) {
			omsTempSynResult = sysOmsTempTrxService.addTempDeptPerson(trxId, tempDpList);
		}else {
			omsTempSynResult = sysOmsTempTrxSynService.addTempDeptPerson(trxId, tempDpList);
		} 

		if(synModel < 100) {
			return result(omsTempSynResult.getCode(),omsTempSynResult.getMsg(),null);
		}else {
			return result(omsTempSynResult.getCode(),omsTempSynResult.getMsg(),omsTempSynResult.getIllegalDataMap());
		}
	}
	
	/**
	 * 同步事务结束
	 * @param trxId 事务ID
	 * @return 
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET,value = "/end")
	public Object end(String trxId)
			throws Exception {
		SysOmsTempTrx omsTempTrx = (SysOmsTempTrx) sysOmsTempTrxService.findByPrimaryKey(trxId);
		int synModel = 0;
		if(omsTempTrx != null) {
			synModel = omsTempTrx.getFdSynModel();
		}
		OmsTempSynResult<Object> omsTempSynResult = null;
		if(synModel < 100) {
			omsTempSynResult = sysOmsTempTrxService.end(trxId);
		}else {
			omsTempSynResult = sysOmsTempTrxSynService.end(trxId);
		} 
		if(synModel < 100) {
			return result(omsTempSynResult.getCode(),omsTempSynResult.getMsg(),null);
		}else {
			return result(omsTempSynResult.getCode(),omsTempSynResult.getMsg(),omsTempSynResult.getIllegalDataMap());
		}
		
	}

	public ISysOmsTempTrxService getSysOmsTempTrxService() {
		return sysOmsTempTrxService;
	}

	public void setSysOmsTempTrxService(ISysOmsTempTrxService sysOmsTempTrxService) {
		this.sysOmsTempTrxService = sysOmsTempTrxService;
	}

	public ISysOmsTempTrxSynService getSysOmsTempTrxSynService() {
		return sysOmsTempTrxSynService;
	}

	public void setSysOmsTempTrxSynService(ISysOmsTempTrxSynService sysOmsTempTrxSynService) {
		this.sysOmsTempTrxSynService = sysOmsTempTrxSynService;
	}
	
	public final RestResponse result(int code,String msg,Object data) {
		if(code == SysOmsTempConstants.SYS_OMS_TEMP_SYN_CODE_FAIL){
			return new RestResponse<>(false,code+"",msg,null,null);
		}else if(code == SysOmsTempConstants.SYS_OMS_TEMP_SYN_CODE_WARN) {
			return new RestResponse<>(true,code+"",msg,null,data);
		}
		return new RestResponse<>(true,code+"",msg,null,data);
	}

	
	
}

package com.landray.kmss.sys.oms.temp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.druid.sql.ast.statement.SQLIfStatement.Else;
import com.landray.kmss.sys.oms.model.SysOmsTempDept;
import com.landray.kmss.sys.oms.model.SysOmsTempDp;
import com.landray.kmss.sys.oms.model.SysOmsTempPerson;
import com.landray.kmss.sys.oms.model.SysOmsTempPost;
import com.landray.kmss.sys.oms.model.SysOmsTempPp;

/**
* 同步到临时表 开始的返回结果
* @author yuLiang
* @version 1.0 创建时间：2019年12月11日
*/
public class OmsTempSynResult<T>{
	//本次同步的事务ID
	private String trxId;
	//本次发起同步请求是否成功
	private int code;
	//错误信息
	private String msg;
	private Map<OmsTempSynFailType,List<T>> illegalData;
	
	public OmsTempSynResult(){
		illegalData = new HashMap<OmsTempSynFailType, List<T>>();
		illegalData.put(OmsTempSynFailType.DATA_ERR_TYPE_DEPT_ID, new ArrayList<T>());
		illegalData.put(OmsTempSynFailType.DATA_ERR_TYPE_POST_ID, new ArrayList<T>());
		illegalData.put(OmsTempSynFailType.DATA_ERR_TYPE_PERSON_ID, new ArrayList<T>());
		illegalData.put(OmsTempSynFailType.DATA_ERR_TYPE_NAME, new ArrayList<T>());
		illegalData.put(OmsTempSynFailType.DATA_ERR_TYPE_IS_AVAILABLE, new ArrayList<T>());
		illegalData.put(OmsTempSynFailType.DATA_ERR_TYPE_PARENT_ID_NOT_FOUND, new ArrayList<T>());
		illegalData.put(OmsTempSynFailType.DATA_ERR_TYPE_DEPT_ID_NOT_FOUND, new ArrayList<T>());
		illegalData.put(OmsTempSynFailType.DATA_ERR_TYPE_POST_ID_NOT_FOUND, new ArrayList<T>());
		illegalData.put(OmsTempSynFailType.DATA_ERR_TYPE_PERSON_ID_NOT_FOUND, new ArrayList<T>());
		illegalData.put(OmsTempSynFailType.DATA_ERR_TYPE_DEPT_ID_DUPLICATE, new ArrayList<T>());
		illegalData.put(OmsTempSynFailType.DATA_ERR_TYPE_POST_ID_DUPLICATE, new ArrayList<T>());
		illegalData.put(OmsTempSynFailType.DATA_ERR_TYPE_PERSON_ID_DUPLICATE, new ArrayList<T>());
		illegalData.put(OmsTempSynFailType.DATA_ERR_TYPE_SYN_FAIL, new ArrayList<T>());
		illegalData.put(OmsTempSynFailType.DATA_ERR_TYPE_PERSON_LOGIN_NAME, new ArrayList<T>());
	}
	
	/**
	 * 获取不合法的数据（字符串）
	 * @param linefeed 换行符：每个不合法数据用什么换行符号 可以是/r/n 或者</br>
	 * @return
	 */
	public String getIllegalDataMsg(String linefeed){
		StringBuffer stringBuffer = new StringBuffer();
		for (OmsTempSynFailType key : illegalData.keySet()) {
			List<T> list = illegalData.get(key);
			if(!list.isEmpty()){
				stringBuffer.append(key.getDesc()+"：["+linefeed);
				for (Object obj : list) {
					if(obj instanceof SysOmsTempDept){
						SysOmsTempDept sysOmsTempDept = (SysOmsTempDept)obj;
						stringBuffer.append("部门名称："+sysOmsTempDept.getFdName());
						stringBuffer.append("，部门ID："+sysOmsTempDept.getFdDeptId());
						stringBuffer.append("，是否有效："+sysOmsTempDept.getFdIsAvailable());
						stringBuffer.append("，父部门ID：" + sysOmsTempDept.getFdParentid()+"；");
					}else if (obj instanceof SysOmsTempPerson) {
						SysOmsTempPerson sysOmsTempPerson = (SysOmsTempPerson)obj;
						stringBuffer.append("人员名称："+sysOmsTempPerson.getFdName());
						stringBuffer.append("，人员ID："+sysOmsTempPerson.getFdPersonId());
						stringBuffer.append("，是否有效："+sysOmsTempPerson.getFdIsAvailable());
						stringBuffer.append("，所属主部门ID：" + sysOmsTempPerson.getFdParentid()+"；");
					}else if(obj instanceof SysOmsTempPost){
						SysOmsTempPost sysOmsTempPost = (SysOmsTempPost)obj;
						stringBuffer.append("岗位名称："+sysOmsTempPost.getFdName());
						stringBuffer.append("，岗位ID："+sysOmsTempPost.getFdPostId());
						stringBuffer.append("，是否有效："+sysOmsTempPost.getFdIsAvailable());
						stringBuffer.append("，所属部门ID：" + sysOmsTempPost.getFdParentid()+"；");
					}else if (obj instanceof SysOmsTempPp) {
						SysOmsTempPp sysOmsTempPp = (SysOmsTempPp)obj;
						stringBuffer.append("fdPersonId："+sysOmsTempPp.getFdPersonId());
						stringBuffer.append("，fdPostId："+sysOmsTempPp.getFdPostId()+"；");
						stringBuffer.append("，fdIsAvailable："+sysOmsTempPp.getFdIsAvailable()+"；");
					}else if (obj instanceof SysOmsTempDp) {
						SysOmsTempDp sysOmsTempDp = (SysOmsTempDp)obj;
						stringBuffer.append("fdPersonId："+sysOmsTempDp.getFdPersonId());
						stringBuffer.append("，fdDeptId："+sysOmsTempDp.getFdDeptId()+"；");
						stringBuffer.append("，fdIsAvailable："+sysOmsTempDp.getFdIsAvailable()+"；");
					}
					stringBuffer.append(linefeed);
				}
				stringBuffer.append("]"+linefeed);
			}
		}
		return stringBuffer.toString();
	}
	
	/**
	 * 获取不合法的数据（字符串）
	 * @param linefeed 换行符：每个不合法数据用什么换行符号 可以是/r/n 或者</br>
	 * @return
	 */
	public Map<String,List<Map>> getIllegalDataMap(){
		if(illegalData.isEmpty()) {
			return null;
		}
		
		Map<String,List<Map>> illegalDataMap = new HashMap<String, List<Map>>();
		Map<String,Object> illegal= null;
		List<Map> illegalDataList = null;
		for (OmsTempSynFailType key : illegalData.keySet()) {
			List<T> list = illegalData.get(key);
			illegalDataList = new ArrayList<Map>();
			if(!list.isEmpty()){
				for (Object obj : list) {
					illegal = new HashMap<String, Object>();
					if(obj instanceof SysOmsTempDept){
						SysOmsTempDept sysOmsTempDept = (SysOmsTempDept)obj;
						illegal.put("fdName", sysOmsTempDept.getFdName());
						illegal.put("fdDeptId",sysOmsTempDept.getFdDeptId());
						illegal.put("fdIsAvailable",sysOmsTempDept.getFdIsAvailable());
						illegal.put("fdParentid" ,sysOmsTempDept.getFdParentid());
					}else if (obj instanceof SysOmsTempPerson) {
						SysOmsTempPerson sysOmsTempPerson = (SysOmsTempPerson)obj;
						illegal.put("fdName",sysOmsTempPerson.getFdName());
						illegal.put("fdPersonId",sysOmsTempPerson.getFdPersonId());
						illegal.put("fdIsAvailable",sysOmsTempPerson.getFdIsAvailable());
						illegal.put("fdParentid" , sysOmsTempPerson.getFdParentid());
					}else if(obj instanceof SysOmsTempPost){
						SysOmsTempPost sysOmsTempPost = (SysOmsTempPost)obj;
						illegal.put("fdName",sysOmsTempPost.getFdName());
						illegal.put("fdPostId",sysOmsTempPost.getFdPostId());
						illegal.put("fdIsAvailable",sysOmsTempPost.getFdIsAvailable());
						illegal.put("fdParentid" ,sysOmsTempPost.getFdParentid());
					}else if (obj instanceof SysOmsTempPp) {
						SysOmsTempPp sysOmsTempPp = (SysOmsTempPp)obj;
						illegal.put("fdPersonId",sysOmsTempPp.getFdPersonId());
						illegal.put("fdPostId",sysOmsTempPp.getFdPostId());
						illegal.put("fdIsAvailable",sysOmsTempPp.getFdIsAvailable());
					}else if (obj instanceof SysOmsTempDp) {
						SysOmsTempDp sysOmsTempDp = (SysOmsTempDp)obj;
						illegal.put("fdPersonId",sysOmsTempDp.getFdPersonId());
						illegal.put("fdDeptId",sysOmsTempDp.getFdDeptId());
						illegal.put("fdIsAvailable",sysOmsTempDp.getFdIsAvailable());
					}
					illegalDataList.add(illegal);
				}
				illegalDataMap.put(key.getValue(), illegalDataList);
				
			}
		}
		return illegalDataMap;
	}
	
	
	
	public String getTrxId() {
		return trxId;
	}
	public void setTrxId(String trxId) {
		this.trxId = trxId;
	}
	
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Map<OmsTempSynFailType, List<T>> getIllegalData() {
		return illegalData;
	}
	public void setIllegalData(Map<OmsTempSynFailType, List<T>> illegalData) {
		this.illegalData = illegalData;
	}
	
	
}

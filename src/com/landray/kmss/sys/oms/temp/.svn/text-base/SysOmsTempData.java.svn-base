package com.landray.kmss.sys.oms.temp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.landray.kmss.sys.oms.model.SysOmsTempDept;
import com.landray.kmss.sys.oms.model.SysOmsTempDp;
import com.landray.kmss.sys.oms.model.SysOmsTempPerson;
import com.landray.kmss.sys.oms.model.SysOmsTempPost;
import com.landray.kmss.sys.oms.model.SysOmsTempPp;
import com.landray.kmss.sys.oms.model.SysOmsTempTrx;

/**
* 临时表数据
* @author yuLiang
* @version 1.0 创建时间：2019年12月11日
*/
public class SysOmsTempData {
	private List<SysOmsTempDept> tempDeptList;
	
	private List<SysOmsTempPerson> tempPersonList;
	
	private List<SysOmsTempPost> tempPostList;
	
	private List<SysOmsTempPp> tempPpList;
	
	private List<SysOmsTempDp> tempDpList;
	
	private Map<String, SysOmsTempPerson> tempPersonMap;
	private Map<String, SysOmsTempDept> tempDeptMap;
	private Map<String, SysOmsTempPost> tempPostMap;
	private Map<String,String> sysOrgElementIdMap;
	private Map<String, String> availableSysOrgElementIdMap;
	private Map<String, String> loginNameMap = new HashMap<String, String>();
	
	private List<SysOmsTempDept> addDeptList = new ArrayList<SysOmsTempDept>();
	private List<SysOmsTempDept> updateDeptList = new ArrayList<SysOmsTempDept>();
	private List<SysOmsTempDept> delDeptList = new ArrayList<SysOmsTempDept>();
	
	private List<SysOmsTempPost> addPostList = new ArrayList<SysOmsTempPost>();
	private List<SysOmsTempPost> updatePostList = new ArrayList<SysOmsTempPost>();
	private List<SysOmsTempPost> delPostList = new ArrayList<SysOmsTempPost>();
	
	private List<SysOmsTempPerson> addPersonList = new ArrayList<SysOmsTempPerson>();
	private List<SysOmsTempPerson> updatePersonList = new ArrayList<SysOmsTempPerson>();
	private List<SysOmsTempPerson> delPersonList = new ArrayList<SysOmsTempPerson>();
	
	private Long fdSynTimestamp;
	
	private SysOmsTempTrx tempTrx;
	private OmsTempSynResult<Object> result;
	private SyncLog log;
	
	//同步配置
	private SysOmsSynConfig synConfig;
	
	

	public SysOmsTempData(SysOmsTempTrx tempTrx) {
		super();
		this.tempTrx = tempTrx;
		this.synConfig = SysOmsTempUtil.getSynConfig(tempTrx.getFdSynConfigJson());
	}

	public List<SysOmsTempDept> getTempDeptList() {
		if(tempDeptList == null) {
            tempDeptList = new ArrayList<SysOmsTempDept>();
        }
		return tempDeptList;
	}

	public void setTempDeptList(List<SysOmsTempDept> tempDeptList) {
		this.tempDeptList = tempDeptList;
	}

	public List<SysOmsTempPerson> getTempPersonList() {
		if(tempPersonList == null) {
            tempPersonList = new ArrayList<SysOmsTempPerson>();
        }
		return tempPersonList;
	}

	public void setTempPersonList(List<SysOmsTempPerson> tempPersonList) {
		this.tempPersonList = tempPersonList;
	}

	public List<SysOmsTempPost> getTempPostList() {
		if(tempPostList == null) {
            tempPostList = new ArrayList<SysOmsTempPost>();
        }
		return tempPostList;
	}

	public void setTempPostList(List<SysOmsTempPost> tempPostList) {
		this.tempPostList = tempPostList;
	}

	public List<SysOmsTempPp> getTempPpList() {
		if(tempPpList == null) {
            tempPpList = new ArrayList<SysOmsTempPp>();
        }
		return tempPpList;
	}

	public void setTempPpList(List<SysOmsTempPp> tempPpList) {
		this.tempPpList = tempPpList;
	}

	public List<SysOmsTempDp> getTempDpList() {
		if(tempDpList == null) {
            tempDpList = new ArrayList<SysOmsTempDp>();
        }
		return tempDpList;
	}

	public void setTempDpList(List<SysOmsTempDp> tempDpList) {
		this.tempDpList = tempDpList;
	}
	
	
	
	public Map<String, SysOmsTempPerson> getTempPersonMap() {
		if(tempPersonMap == null) {
            tempPersonMap = new HashMap<String, SysOmsTempPerson>();
        }
		return tempPersonMap;
	}

	public void setTempPersonMap(Map<String, SysOmsTempPerson> tempPersonMap) {
		this.tempPersonMap = tempPersonMap;
	}

	public Map<String, SysOmsTempDept> getTempDeptMap() {
		if(tempDeptMap ==  null) {
            tempDeptMap = new HashMap<String, SysOmsTempDept>();
        }
		return tempDeptMap;
	}

	public void setTempDeptMap(Map<String, SysOmsTempDept> tempDeptMap) {
		this.tempDeptMap = tempDeptMap;
	}

	public Map<String, SysOmsTempPost> getTempPostMap() {
		if(tempPostMap == null) {
            tempPostMap = new HashMap<String, SysOmsTempPost>();
        }
		return tempPostMap;
	}

	public void setTempPostMap(Map<String, SysOmsTempPost> tempPostMap) {
		this.tempPostMap = tempPostMap;
	}
	
	
	public Map<String, String> getSysOrgElementIdMap() {
		return sysOrgElementIdMap;
	}

	public void setSysOrgElementIdMap(Map<String, String> sysOrgElementIdMap) {
		this.sysOrgElementIdMap = sysOrgElementIdMap;
	}

	public List<SysOmsTempDept> getAddDeptList() {
		return addDeptList;
	}

	public void setAddDeptList(List<SysOmsTempDept> addDeptList) {
		this.addDeptList = addDeptList;
	}

	public List<SysOmsTempDept> getUpdateDeptList() {
		return updateDeptList;
	}

	public void setUpdateDeptList(List<SysOmsTempDept> updateDeptList) {
		this.updateDeptList = updateDeptList;
	}

	public List<SysOmsTempDept> getDelDeptList() {
		return delDeptList;
	}

	public void setDelDeptList(List<SysOmsTempDept> delDeptList) {
		this.delDeptList = delDeptList;
	}

	public List<SysOmsTempPost> getAddPostList() {
		return addPostList;
	}

	public void setAddPostList(List<SysOmsTempPost> addPostList) {
		this.addPostList = addPostList;
	}

	public List<SysOmsTempPost> getUpdatePostList() {
		return updatePostList;
	}

	public void setUpdatePostList(List<SysOmsTempPost> updatePostList) {
		this.updatePostList = updatePostList;
	}

	public List<SysOmsTempPost> getDelPostList() {
		return delPostList;
	}

	public void setDelPostList(List<SysOmsTempPost> delPostList) {
		this.delPostList = delPostList;
	}

	public List<SysOmsTempPerson> getAddPersonList() {
		return addPersonList;
	}

	public void setAddPersonList(List<SysOmsTempPerson> addPersonList) {
		this.addPersonList = addPersonList;
	}

	public List<SysOmsTempPerson> getUpdatePersonList() {
		return updatePersonList;
	}

	public void setUpdatePersonList(List<SysOmsTempPerson> updatePersonList) {
		this.updatePersonList = updatePersonList;
	}

	public List<SysOmsTempPerson> getDelPersonList() {
		return delPersonList;
	}

	public void setDelPersonList(List<SysOmsTempPerson> delPersonList) {
		this.delPersonList = delPersonList;
	}
	
	
	public Long getFdSynTimestamp() {
		return fdSynTimestamp;
	}

	public void setFdSynTimestamp(Long fdSynTimestamp) {
		this.fdSynTimestamp = fdSynTimestamp;
	}
	
	

	public SysOmsTempTrx getTempTrx() {
		return tempTrx;
	}

	public void setTempTrx(SysOmsTempTrx tempTrx) {
		this.tempTrx = tempTrx;
	}

	public OmsTempSynResult<Object> getResult() {
		return result;
	}

	public void setResult(OmsTempSynResult<Object> result) {
		this.result = result;
	}

	public SyncLog getLog() {
		return log;
	}

	public void setLog(SyncLog log) {
		this.log = log;
	}
	
	

	public Map<String, String> getAvailableSysOrgElementIdMap() {
		return availableSysOrgElementIdMap;
	}

	public void setAvailableSysOrgElementIdMap(Map<String, String> availableSysOrgElementIdMap) {
		this.availableSysOrgElementIdMap = availableSysOrgElementIdMap;
	}
	
	

	public Map<String, String> getLoginNameMap() {
		return loginNameMap;
	}

	public void setLoginNameMap(Map<String, String> loginNameMap) {
		this.loginNameMap = loginNameMap;
	}
	
	

	public SysOmsSynConfig getSynConfig() {
		return synConfig;
	}

	public void setSynConfig(SysOmsSynConfig synConfig) {
		this.synConfig = synConfig;
	}
	

	public boolean isEmpty(){
		return (tempDeptList == null || tempDeptList.isEmpty()) 
				&& (tempDpList == null || tempDpList.isEmpty()) 
				&& (tempPersonList == null || tempPersonList.isEmpty())
				&& (tempPostList == null || tempPostList.isEmpty())
				&& (tempPpList == null || tempPpList.isEmpty());
	}

	
	
	
}

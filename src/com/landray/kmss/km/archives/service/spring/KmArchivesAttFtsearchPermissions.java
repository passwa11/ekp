package com.landray.kmss.km.archives.service.spring;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.landray.kmss.km.archives.model.KmArchivesMain;
import com.landray.kmss.sys.ftsearch.config.IFullTextPropertyGetter;
import com.landray.kmss.sys.lbpm.engine.service.ProcessExecuteService;
import com.landray.kmss.sys.lbpm.engine.service.ProcessInstanceInfo;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.SpringBeanUtil;

public class KmArchivesAttFtsearchPermissions implements IFullTextPropertyGetter {

	private ProcessExecuteService processExecuteService;
	
	protected ProcessExecuteService getProcessExecuteService() {
		if (processExecuteService == null) {
			processExecuteService = (ProcessExecuteService) SpringBeanUtil
					.getBean("lbpmProcessExecuteServiceTarget");
		}
		return processExecuteService;
	}
	
	@Override
	public String getPropertyToString(String proerty, Object model) {
		if(model instanceof KmArchivesMain) {
			KmArchivesMain kmArchivesMain = (KmArchivesMain)model;
			List<SysOrgElement> authReaders = new ArrayList<SysOrgElement>();
			SysOrgElement creator = kmArchivesMain.getDocCreator();
			if(creator != null) {
				authReaders.add(creator);
			}
			// 是否是档案的保管员
			ISysOrgCoreService sysOrgCoreService = (ISysOrgCoreService) SpringBeanUtil
					.getBean("sysOrgCoreService");
			SysOrgElement fdStorekeeper = kmArchivesMain.getFdStorekeeper();
			if (fdStorekeeper != null) {
				authReaders.add(fdStorekeeper);
			}
			
			List<SysOrgElement> fileReaders = kmArchivesMain.getAuthFileReaders();
			if(fileReaders != null) {
				authReaders.addAll(fileReaders);
			}
			
			Set<String> authSet = new HashSet<String>();
			authSet.add("ROLE_KMARCHIVES_VIEW_ALLFILE");
			authSet.add("ROLE_KMARCHIVES_VIEW_ALL");
			for(SysOrgElement orgId : authReaders) {
				authSet.add(orgId.getFdId());
			}
			ProcessInstanceInfo processInstanceInfo = getProcessExecuteService().load(kmArchivesMain.getFdId());
			if(processInstanceInfo != null) {
				 List<String[]> processIds = processInstanceInfo.getHistoryHandlers();
				 if(processIds != null) {
					 for(String[] processId : processIds) {
						 if(processId != null&&processId.length>0&&processId[0]!= null) {
							 authSet.add(processId[0]);
						 }
					 }
				 }
			}
			
			StringBuilder authIds = new StringBuilder();
			for(String authId : authSet) {
				authIds.append(" ").append(authId);
			}
			
			
			return authIds.toString().trim();
		}
		throw new RuntimeException("权限字段配置错模块");
	}

}

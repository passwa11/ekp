package com.landray.kmss.sys.filestore.service;

import java.util.Map;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.filestore.model.SysFileConvertClient;
import com.landray.kmss.sys.filestore.model.SysFileConvertQueue;
import com.landray.kmss.sys.filestore.scheduler.remote.interfaces.IDuplexCommunication;
import com.sunbor.web.tag.Page;

public interface ISysFileConvertClientService extends IBaseService {

	public Page findOKPage(HQLInfo hqlInfo) throws Exception;

	public void operateClients(String clientId, String cmd, String... cmdParams) throws Exception;

	public String registerClient(Map<String, String> receiveInfos) throws Exception;

	public SysFileConvertClient getAliveAvailIdleClient(IDuplexCommunication communication,
			SysFileConvertQueue taskQueue);

	public boolean isClientRegistered(String clientIP, String processID, String clientProcessID);

	public Integer getMaxPort(String clientIP);

	public void refreshClients(boolean checkQueuesNum);

	public boolean isAlive(SysFileConvertClient client);

}

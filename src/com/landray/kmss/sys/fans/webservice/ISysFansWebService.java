package com.landray.kmss.sys.fans.webservice;

import javax.jws.WebService;

import com.landray.kmss.sys.fans.webservice.exception.SysFansFaultException;
import com.landray.kmss.sys.webservice2.interfaces.ISysWebservice;
@WebService
public interface ISysFansWebService extends ISysWebservice {
	
	//检查是否已关注某用户，已关注返回true
	boolean isFollowPerson(String userId,String targetUserId) throws SysFansFaultException;
	
	//关注某用户,返回关注结果
	boolean followPerson(String userId,String targetUserId) throws SysFansFaultException;
	
}

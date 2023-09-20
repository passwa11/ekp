<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn"%>
<%@ page import="java.lang.reflect.Method" %>
<%@ page import="com.landray.kmss.util.ResourceUtil" %>
<%@ page import="com.landray.kmss.util.StringUtil" %>
<%@ page import="com.landray.kmss.util.UserUtil" %>
<%@ page import="com.landray.kmss.sys.authentication.user.KMSSUser" %>

<%@ page import="com.landray.kmss.sys.cache.core.KmssCacheManager" %>
<%@ page import="com.landray.kmss.sys.cache.core.ICacheInfo" %>
<%@ page import="com.landray.kmss.sys.cache.core.KmssCacheManager" %>
<%@ page import="java.util.*" %>
<%@ page import="com.landray.kmss.sys.cache.CacheConfig" %>
<%@ page import="com.landray.kmss.sys.cache.core.Cache" %>
<%
KMSSUser user = UserUtil.getKMSSUser(request);
if(user.isAdmin()){

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title></title>
	</head>
	<body>



		<form>
		<table width="95%" border="1" align="center">
        			<tr>
        				<th width="20%">Cache Name</th>
        				<th width="50%">Keys</th>
        				<th width="15%">Count</th>
        				<th width="15%">Operation</th>
        			</tr>
        		</table>
		<%
			KmssCacheManager kcm = KmssCacheManager.getInstance();
			//kcm.removeCaches("com.landray.kmss.sys.config.design.SysCfgModule",1);
			kcm.removeKeys("com.landray.kmss.sys.config.design.SysCfgModule", Arrays.asList("/sys/material/","/sys/lbpmext/voteweight/"), 1);
			CacheConfig cacheConfig = CacheConfig.get("testetestestestsetet",true);
			cacheConfig.setCacheType(3);
			Cache cache = kcm.getCache("ttttttttcacccccc",cacheConfig);
			cache.put("dddddddddddd","79749769ssssssssssssssssssss");

			Map<Integer, List<ICacheInfo>> allCacheInfo = kcm.getAllCacheInfo();
			Set<Map.Entry<Integer, List<ICacheInfo>>> entries = allCacheInfo.entrySet();
            for(Map.Entry<Integer, List<ICacheInfo>> entry:entries){
            String scope = null;
            if(entry.getKey().equals(1)){
            scope="Local";
            }else if(entry.getKey().equals(2)){
            scope="Cluster";
            }else{
            scope="Redis";
            }
		%>
			<fieldset>
			<legend><%=scope%></legend>
			<table width="95%" border="1" align="center">
			<%
			    List<ICacheInfo> value = entry.getValue();
			    if(value!=null){
                for(ICacheInfo info : value){
                    String name = info.getName();
                    List<String> keys = info.getKeys();
			%>
				<tr>
                    <th width="20%"><%=name%></th>
                    <th width="50%"><%=keys%></th>
                    <th width="15%"><%=keys.size()%></th>
                    <th width="15%">
                        <p><span>clean</span></p>
                        <p><span>removeKey</span></p>
                        <p><span>removeKeys</span></p>
                     </th>
                </tr>
            <%}
            }%>
			</table>
			</fieldset>
		<%
			}
		%>
		</form>
	</body>
</html>
<%}%>
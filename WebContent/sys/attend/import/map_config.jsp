<%@page import="java.util.Map" %>
<%@page import="com.landray.kmss.sys.attend.model.SysAttendMapConfig" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    SysAttendMapConfig mapCfg = new SysAttendMapConfig();
    Map<String, String> map = mapCfg.getDataMap();
    request.setAttribute("fdMapServiceType", map.get("fdMapServiceType"));
    request.setAttribute("fdMapServiceAMapKey", map.get("fdMapServiceAMapKey"));
    request.setAttribute("fdMapServiceQMapKey", map.get("fdMapServiceQMapKey"));
    request.setAttribute("fdMapServiceBMapKey", map.get("fdMapServiceBMapKey"));
    request.setAttribute("fdMapServiceBMapVer", map.get("fdMapServiceBMapVer"));
    request.setAttribute("fdMapServiceAMapPcKey", map.get("fdMapServiceAMapPcKey"));
    request.setAttribute("fdMapServiceQMapName", map.get("fdMapServiceQMapName"));
    request.setAttribute("fdMapServiceAMapPcSecurityKey", map.get("fdMapServiceAMapPcSecurityKey"));
%>
,
map :{
mapType : '${fdMapServiceType}',
bMapKey : '${fdMapServiceBMapKey}',
qMapKey : '${fdMapServiceQMapKey}',
qMapKeyName : '${fdMapServiceQMapName}',
aMapKey : '${fdMapServiceAMapKey}',
aMapKeyPc : '${fdMapServiceAMapPcKey}',
bMapVer : '${fdMapServiceBMapVer}',
aMapKeyPcSecurityKey:'${fdMapServiceAMapPcSecurityKey}'
}
			
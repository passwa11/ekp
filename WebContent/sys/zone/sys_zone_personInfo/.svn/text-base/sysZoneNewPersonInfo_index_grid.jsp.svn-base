<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/ui.tld" prefix="ui"%>
<%@ page import="java.util.*" %>
<%
   String timstap = System.currentTimeMillis()+"";
	request.setAttribute("timstap",timstap);
%>
{$
    <a class="thumbnail"  target="_blank"
       href="${LUI_ContextPath }/sys/person/sys_person_zone/sysPersonZone.do?method=view&fdId={%grid['id']%}" title="{%grid['fdName']%}">
              <div class="imgbox"><img src="{%env.fn.formatUrl(grid['icon'])%}&timstap=${timstap}"></div>
              <ul class="content-list">
                <li>
                  <span class="title">${lfn:message('sys-zone:sysZonePerson.name')}</span>
                  {%grid['fdName']%}
                </li>
                <li title="{%grid['fdDept']%}">
                  <span class="title">${lfn:message('sys-zone:sysZonePerson.dept')}</span>
                  {%grid['fdDept']%}
                </li>
              </ul>
     </a>
$}

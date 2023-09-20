<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-bean.tld" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
{$
	<tr>
		<td width="15%" class="td_normal_title">
	  		<bean:message bundle="sys-ui" key="ui.recurrence.interval" />
	  	</td> 
	  	<td>
	  		<bean:message bundle="sys-ui" key="ui.recurrence.interval.every" />
	  		<select class="rTypeInterval">
	  			<c:forEach begin="1" end="50" var="interval">
	  				<option value="${ interval }">${ interval }</option>
	  			</c:forEach>
	  		</select>
	  		{% param.text %}
	  	</td>
	</tr>
$}

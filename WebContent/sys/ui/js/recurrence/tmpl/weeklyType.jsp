<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-bean.tld" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
{$
	<tr>
		<td width="15%" class="td_normal_title">
	  		<bean:message bundle="sys-ui" key="ui.recurrence.time"/>
	  	</td> 
	  	<td>
	  		<input type="text" style="display: none;" class="weeklyTypeValidation" name="{% param.cid + '_weeklyType_validation' %}"/>
	  		<input class="weeklyType" name="{% param.cid + '_weeklyType' %}" type="checkbox" value="SU" checked="checked"/>
	  		<span><bean:message bundle="sys-ui" key="ui.recurrence.time.week.sun"/></span>
	  		<input class="weeklyType" name="{% param.cid + '_weeklyType' %}" type="checkbox" value="MO" />
	  		<span><bean:message bundle="sys-ui" key="ui.recurrence.time.week.mon"/></span>
	  		<input class="weeklyType" name="{% param.cid + '_weeklyType' %}" type="checkbox" value="TU"/>
	  		<span><bean:message bundle="sys-ui" key="ui.recurrence.time.week.tues"/></span>
	  		<input class="weeklyType" name="{% param.cid + '_weeklyType' %}" type="checkbox" value="WE"/>
	  		<span><bean:message bundle="sys-ui" key="ui.recurrence.time.week.wed"/></span>
	  		<input class="weeklyType" name="{% param.cid + '_weeklyType' %}" type="checkbox" value="TH"/>
	  		<span><bean:message bundle="sys-ui" key="ui.recurrence.time.week.thur"/></span>
	  		<input class="weeklyType" name="{% param.cid + '_weeklyType' %}" type="checkbox" value="FR"/>
	  		<span><bean:message bundle="sys-ui" key="ui.recurrence.time.week.fri"/></span>
	  		<input class="weeklyType" name="{% param.cid + '_weeklyType' %}" type="checkbox" value="SA"/>
	  		<span><bean:message bundle="sys-ui" key="ui.recurrence.time.week.sat"/></span>
	  	</td>
	</tr>
$}

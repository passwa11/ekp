<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-bean.tld" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
{$
	<tr>
		<td width="15%" class="td_normal_title">
	  		<bean:message bundle="sys-ui" key="ui.recurrence.endType" />
	  	</td> 
	  	<td>
	  		<div class="endTypeItem">
	  			<input class="endType" name="{% param.cid + '_endType' %}" type="radio" value="never" checked="checked" />
	  			<span><bean:message bundle="sys-ui" key="ui.recurrence.endType.never" /></span>
	  		</div>
	  		<div class="endTypeItem" style="margin-top: 5px;">
	  			<input class="endType" name="{% param.cid + '_endType' %}" type="radio" value="count" />
	  			<span><bean:message bundle="sys-ui" key="ui.recurrence.endType.happen" /></span>
	  			<input class="endTypeCount" type="text" validate="min(1)"/>
	  			<span><bean:message bundle="sys-ui" key="ui.recurrence.endType.times" /></span>
	  		</div>
	  		<div class="endTypeItem" style="margin-top: 5px;">
	  			<input class="endType" name="{% param.cid + '_endType' %}" type="radio" value="until" />
	  			<span><bean:message bundle="sys-ui" key="ui.recurrence.endType.util" /></span>
	  			<div class="inputselectsgl endTypeSelectDateContainer" data-input-name="{% param.cid + '_endTypeSelectDate' %}" style="width:150px;height: 24px;">
	  				<div class="input">
	  					<input class="endTypeSelectDate" type="text" name="{% param.cid + '_endTypeSelectDate' %}" />
	  				</div>
	  				<div class="inputdatetime"></div>
	  			</div>
	  		</div>
	  	</td>
	</tr>
$}

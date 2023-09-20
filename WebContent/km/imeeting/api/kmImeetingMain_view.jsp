<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>


<%
Boolean dis = true;
request.setAttribute("value1", dis);
%>
<api:response>
	<api:data-object name="data">
		<api:prop-auto exclude=""/>
		<api:prop name="fdFinishDate">
			<api:prop-attr name="canDisplay" value="${value1}"/>
			<api:validators>
				<api:validator type="past" value="__$now"/>
				<api:validator type="notNull" value="${value1}"/>
				<api:validator type="email" value="false" />
			</api:validators>
		</api:prop>
		<api:prop name="fdAttendNum">
		    <api:validators>
		        <api:validator type="digits" integer="9" fraction="2" />
		    </api:validators>
		</api:prop>
		<api:prop name="docStatus">
		    <api:enum>
		        <api:enum-item value="00" labelKey="kmImeeting.status.abandom" bundle="km-imeeting" />
		        <api:enum-item value="10" labelKey="kmImeeting.status.draft" bundle="km-imeeting" />
		    </api:enum>
		</api:prop>
		<api:prop name="docStatus2">
		    <api:optionValue type="dynamic" uri="/km/imeeting/dynamicOptionValue"/>
		    <api:render type="radio" level="warn"/>
		</api:prop>
		<api:prop name="fdTemplate">
			<api:children>
			    <api:child key="fdName" dataType="string" value="helloR" />
			</api:children>
		</api:prop>
</api:data-object>
<api:data-object name="$meta">
		<api:prop-auto exclude=""/>
</api:data-object>
<api:data-object name="evaluation">
		<api:prop-auto exclude=""/>
</api:data-object>
<c:import url="/km/imeeting/api/workflow_view.jsp" charEncoding="UTF-8">
    <c:param value="formName" name="kmImeetingMainForm"/>
</c:import>
</api:response>


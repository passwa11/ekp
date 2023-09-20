<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- 入职人员姓名 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/resource/jsp/common.jsp" %>
<%@ include file="/hr/ratify/fieldlayout/common/param_parser.jsp" %>
<%
    parse.addStyle("width", "control_width", "95%");
    required = Boolean.parseBoolean(parse.getParamValue("control_required", "true"));
%>
<span id="isRecruitSpan">
	<c:choose>
        <c:when test="${param.mobile eq 'true'}">
		<div id="_fdRecruitEntryId" xform_type="select">
			<xform:select property="fdRecruitEntryId" mobile="true" showPleaseSelect="true" showStatus="edit"
                          mul="false" htmlElementProperties="id='fdRecruitEntryId'" required="true"
                          onValueChange="reloadForm(this.value)">
                <xform:beanDataSource serviceBean="hrStaffEntryService" selectBlock="fdId,fdName"
                                      whereBlock="fdStatus=1"></xform:beanDataSource>
            </xform:select>
			   </div>
        </c:when>
        <c:otherwise>
            <xform:viewShow showStatus="view">
				<script type="text/javascript">
					Com_IncludeFile("edit.js", "${LUI_ContextPath}/hr/ratify/resource/js/", 'js', true);
				</script>
            </xform:viewShow>
            <div id="_fdRecruitEntry" valField="fdRecruitEntryId" xform_type="dialog">
			<xform:dialog propertyId="fdRecruitEntryId" propertyName="fdRecruitEntryName" showStatus="edit"
                          mulSelect="false"
                          className="inputsgl" style="<%=parse.getStyle()%>"
                          subject="${lfn:message('hr-ratify:hrRatifyEntry.fdEntryName')}">
                selectStaffEntry();
            </xform:dialog>
			<span class="txtstrong">*</span>
			</div>
        </c:otherwise>
    </c:choose>
</span>
<span id="notRecruitSpan">
	<c:choose>
        <c:when test="${param.mobile eq 'true'}">
            <xform:xtext property="fdEntryName" mobile="true" style="<%=parse.getStyle()%>"
                         subject="${lfn:message('hr-ratify:hrRatifyEntry.fdEntryName')}"
                         htmlElementProperties="id='fdEntryName'" required="true"/>
        </c:when>
        <c:otherwise>
            <xform:xtext property="fdEntryName" mobile="false" style="<%=parse.getStyle()%>"
                         subject="${lfn:message('hr-ratify:hrRatifyEntry.fdEntryName')}"/>
            <span class="txtstrong">*</span>
        </c:otherwise>
    </c:choose>
</span>	
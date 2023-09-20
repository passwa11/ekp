<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.util.DateUtil"%>
<%@ page import="com.landray.kmss.km.imeeting.forms.KmImeetingStatForm"%>
<%@ page import="java.util.Date"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<!-- 暂时支持查看页面 -->
<c:set var="queryForm" value="${requestScope[param['formName']] }"/>
<%--统计周期 --%>
<tr>
	<td class="muiTitle">
		<bean:message key="kmImeetingStat.fdDateType" bundle="km-imeeting"/>
	</td>
	<td>
		<xform:radio property="fdDateType" onValueChange="chgTimeSelect" value="${queryForm.fdDateType }" showStatus="${param.mode }" mobile="true">
		   <c:choose>
		       <c:when test="${JsParam.selfDefine==false}">
		            <xform:enumsDataSource enumsType="km_imeeting_stat_fd_date_type_withOutDay">
					</xform:enumsDataSource>
		        </c:when>
		       <c:otherwise>
		        	<xform:enumsDataSource enumsType="km_imeeting_stat_fd_date_type">
					</xform:enumsDataSource>
		       </c:otherwise>
		   </c:choose>
		</xform:radio>
		<c:if test="${not empty param.mode && param.mode == 'view' }">
			<input type="hidden" name="fdDateType" value="${queryForm.fdDateType}"/>
		</c:if>
	</td>
</tr>
<tr>
	<td class="muiTitle">
		<bean:message key="kmImeetingStat.fdDateType.scope" bundle="km-imeeting"/>
	</td>
	<td>
		<div id="fdDateTypeScope"></div>	
		<input type="hidden" name="fdStartDate" value="${queryForm.fdStartDate}"/>
		<input type="hidden" name="fdEndDate" value="${queryForm.fdEndDate}"/>
	</td>
</tr>
<script type="text/javascript">
	Com_IncludeFile('data.js');
</script>
<script type="text/javascript">
	require(['dojo/query'],function(query){
		var fdStartDate = "${queryForm.fdStartDate}",
			fdEndDate = "${queryForm.fdEndDate}",
			fdDateType = '${queryForm.fdDateType}';
		var scopeStr =  getDateNameByType(fdDateType,fdStartDate) + ' ~ ' + getDateNameByType(fdDateType,fdEndDate) ;
		var dom = query('#fdDateTypeScope')[0];
		dom.innerHTML = scopeStr;
		function getDateNameByType(periodType,value){
			if(periodType == 7){
				return value;
			}
			var data = new KMSSData(),
				beanName = 'periodService&periodType='+ periodType + '&value=' + value;
			data.AddBeanData(beanName);
			var datas = data.Parse();
			for(var i =0;i < datas.data.length;i++){
				var data = datas.data[i];
				if(data.id == value){
					return data.name;
				}
			}
			return null;
		}
	});
</script>


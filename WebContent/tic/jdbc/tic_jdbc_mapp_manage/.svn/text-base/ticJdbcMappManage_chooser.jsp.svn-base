<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/list_top.jsp"%>
<html:form action="/tic/jdbc/tic_jdbc_mapp_manage/ticJdbcMappManage.do">
<c:if test="${queryPage.totalrows==0}">
	<%@ include file="/resource/jsp/list_norecord.jsp"%>
</c:if>
<c:if test="${queryPage.totalrows>0}">
	<%@ include file="/resource/jsp/list_pagenav_top.jsp"%>
	<table id="List_ViewTable">
		<tr>
			<sunbor:columnHead htmlTag="td">
				<td width="30pt">
					<bean:message bundle="tic-jdbc" key="ticJdbcMappManage.select"/>
				</td>
				<td width="30pt">
					<bean:message key="page.serial"/>
				</td>
				<sunbor:column property="ticJdbcMappManage.docSubject">
					<bean:message bundle="tic-jdbc" key="ticJdbcMappManage.docSubject"/>
				</sunbor:column>
				<sunbor:column property="ticJdbcMappManage.fdDataSource">
					<bean:message bundle="tic-jdbc" key="ticJdbcMappManage.fdDataSource"/>
				</sunbor:column>
				<sunbor:column property="ticJdbcMappManage.fdIsEnabled">
					<bean:message bundle="tic-jdbc" key="ticJdbcMappManage.fdIsEnabled"/>
				</sunbor:column>
				<sunbor:column property="ticJdbcMappManage.fdDataSourceSql">
					<bean:message bundle="tic-jdbc" key="ticJdbcMappManage.fdDataSourceSql"/>
				</sunbor:column>
				<sunbor:column property="ticJdbcMappManage.fdTargetSource">
					<bean:message bundle="tic-jdbc" key="ticJdbcMappManage.fdTargetSource"/>
				</sunbor:column>
				<sunbor:column property="ticJdbcMappManage.fdTargetSourceSelectedTable">
					<bean:message bundle="tic-jdbc" key="ticJdbcMappManage.fdTargetSourceSelectedTable"/>
				</sunbor:column>
				<sunbor:column property="ticJdbcMappManage.docCategory.fdName">
					<bean:message bundle="tic-jdbc" key="ticJdbcMappManage.docCategory"/>
				</sunbor:column>
			</sunbor:columnHead>
		</tr>
		<c:forEach items="${queryPage.list}" var="ticJdbcMappManage" varStatus="vstatus">
			<tr >
				<td>
					<input type="radio"" name="List_Selected" value="${ticJdbcMappManage.fdId}">
				</td>
				
				<td>
					${vstatus.index+1}
				</td>
				
				<td>
					${ticJdbcMappManage.docSubject}
				</td>
				
				<td>
				    ${dataSoure[ticJdbcMappManage.fdDataSource]}
				</td>
				
				<td>
					<xform:radio value="${ticJdbcMappManage.fdIsEnabled}" property="fdIsEnabled" showStatus="view">
						<xform:enumsDataSource enumsType="common_yesno" />
					</xform:radio>
				</td>
				
				<td>
					${ticJdbcMappManage.fdDataSourceSql}
				</td>
				
				<td>
					${dataSoure[ticJdbcMappManage.fdTargetSource]}	
				</td>
				
				<td>
					"${ticJdbcMappManage.fdTargetSourceSelectedTable}"
				</td>
				
				<td>
					"${ticJdbcMappManage.docCategory.fdName}"
				</td>
			</tr>
		</c:forEach>
	</table>
	
	<%@ include file="/resource/jsp/list_pagenav_down.jsp" %>
	<!-- 
      <div align="center">
    	  <input type="button" class="btndialog" style="width:50px" value="选择" onclick=""/>
    	  <input type="button" class="btndialog" style="width:50px" value="取消" onclick=""/>
    </div>	
    --> 
</c:if>

<script  type="text/javascript">
Com_IncludeFile("jquery.js");

</script>
</html:form>
<%@ include file="/resource/jsp/list_down.jsp"%>
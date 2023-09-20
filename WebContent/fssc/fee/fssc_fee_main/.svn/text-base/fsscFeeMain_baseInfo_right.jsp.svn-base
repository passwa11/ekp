<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<ui:content title="${ lfn:message('fssc-fee:py.JiBenXinXi') }" titleicon="lui-fm-icon-2">
<c:if test="${fsscFeeMainForm.docStatus=='10'}">
		<script>
			LUI.ready(function(){
				setTimeout(function(){
					$("i.lui-fm-icon-2").closest(".lui_tabpanel_vertical_icon_navs_item_l").click();
				},200);
			});
		</script>
	</c:if>
	<table class="tb_simple" width=100%>
		<!--关键字-->
		<tr>
			<td class="td_normal_title" width=25%>
				<bean:message bundle="fssc-fee" key="fsscFeeMain.docCreator" />
			</td>
			<td colspan=3>
				${ fsscFeeMainForm.docCreatorName}
			</td>
		</tr>
		<!--流程类别-->
		<tr>
			<td class="td_normal_title" width=25%>
				<bean:message bundle="fssc-fee" key="fsscFeeMain.docCreateTime" />
			</td>
			<td colspan=3>
				${ fsscFeeMainForm.docCreateTime}
			</td>
		</tr>
		<!--申请人-->
		<tr>
			<td class="td_normal_title" width=25%>
				<bean:message bundle="fssc-fee" key="fsscFeeMain.docStatus" />
			</td>
			<td colspan=3>
				<sunbor:enumsShow value="${ fsscFeeMainForm.docStatus}" enumsType="common_status"/>
			</td>
		</tr>
		<tr>
			<td class="td_normal_title" width=25%>
				<bean:message bundle="fssc-fee" key="fsscFeeMain.docPublishTime" />
			</td>
			<td colspan=3>
				${ fsscFeeMainForm.docPublishTime}
			</td>
		</tr>
	</table>
</ui:content>

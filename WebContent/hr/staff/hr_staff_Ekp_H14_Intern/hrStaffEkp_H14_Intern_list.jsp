<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="Ekp_H14_Intern" list="${queryPage.list }" varIndex="status">
		<list:data-column property="fdId">
		</list:data-column>
	 	<list:data-column col="personInfoId">
			${Ekp_H14_Intern.fdBeiKaoHeRenXingMing.fdId}
		</list:data-column>
		<list:data-column col="index">
		  ${status+1}
		</list:data-column>
		<list:data-column headerClass="width100" col="nameAccount" title="${ lfn:message('hr-staff:hrStaffEkp_H14_Intern.fdBeiKaoHeRenXingMing') }">
		  ${Ekp_H14_Intern.fdBeiKaoHeRenXingMing.fdName}
		</list:data-column>
			<list:data-column headerClass="width100" col="fdSuoShuFenBu" title="${ lfn:message('hr-staff:hrStaffEkp_H14_Intern.fdSuoShuFenBu') }">
		
		
		<c:choose>
						<c:when test="${not empty Ekp_H14_Intern.fdSuoShuFenBu}">
				
		${Ekp_H14_Intern.fdSuoShuFenBu.fdName}
				</c:when>
				<c:otherwise>
					
				</c:otherwise>
			</c:choose>
		</list:data-column>
			<list:data-column headerClass="width100" property="fdYiJiBuMen" title="${ lfn:message('hr-staff:hrStaffEkp_H14_Intern.fdYiJiBuMen') }">
		
		
		</list:data-column>
			<list:data-column headerClass="width100" property="fdErJiBuMen" title="${ lfn:message('hr-staff:hrStaffEkp_H14_Intern.fdErJiBuMen') }">
		
	
		</list:data-column>
			<list:data-column headerClass="width100" property="fdSanJiBuMen" title="${ lfn:message('hr-staff:hrStaffEkp_H14_Intern.fdSanJiBuMen') }">
	
		</list:data-column>
			<list:data-column headerClass="width100" property="fdGangWeiMingChen" title="${ lfn:message('hr-staff:hrStaffEkp_H14_Intern.fdGangWeiMingChen') }">
		</list:data-column>
			<list:data-column headerClass="width100" property="fdGangWeiMingChen" title="${ lfn:message('hr-staff:hrStaffEkp_H14_Intern.fdGangWeiMingChen') }">
	
		</list:data-column>
		<list:data-column headerClass="width100" col="fdRuZhiRiQi" title="${ lfn:message('hr-staff:hrStaffEkp_H14_Intern.fdRuZhiRiQi') }">
		<kmss:showDate value="${Ekp_H14_Intern.fdRuZhiRiQi}" type="date" /> 
		</list:data-column>
		<list:data-column headerClass="width100" col="fdShenQingRiQi" title="${ lfn:message('hr-staff:hrStaffEkp_H14_Intern.fdShenQingRiQi') }">
		<kmss:showDate value="${Ekp_H14_Intern.fdShenQingRiQi}" type="date" /> 
		</list:data-column>
		<list:data-column headerClass="width100" property="fdKaoHeZhouQi" title="${ lfn:message('hr-staff:hrStaffEkp_H14_Intern.fdKaoHeZhouQi') }">
		</list:data-column>
		<list:data-column headerClass="width100" col="fdKaoHeKaiShiShiJian" title="${ lfn:message('hr-staff:hrStaffEkp_H14_Intern.fdKaoHeKaiShiShiJian') }">
		<kmss:showDate value="${Ekp_H14_Intern.fdKaoHeKaiShiShiJian}" type="date" /> 
		</list:data-column>
		<list:data-column headerClass="width100" col="fdKaoHeJieShuShiJian" title="${ lfn:message('hr-staff:hrStaffEkp_H14_Intern.fdKaoHeJieShuShiJian') }">
		<kmss:showDate value="${Ekp_H14_Intern.fdKaoHeJieShuShiJian}" type="date" /> 
		</list:data-column>
		<list:data-column headerClass="width100" property="fdZiPingDeFen" title="${ lfn:message('hr-staff:hrStaffEkp_H14_Intern.fdZiPingDeFen') }">
		</list:data-column>
		<list:data-column headerClass="width100" property="fdZiPingDengJi" title="${ lfn:message('hr-staff:hrStaffEkp_H14_Intern.fdZiPingDengJi') }">
		</list:data-column>
		<list:data-column headerClass="width200" property="fdGongZuoXiaoJie" title="${ lfn:message('hr-staff:hrStaffEkp_H14_Intern.fdGongZuoXiaoJie') }" >
		</list:data-column>
		<list:data-column headerClass="width100" property="fdZhiDengLeiXing" title="${ lfn:message('hr-staff:hrStaffEkp_H14_Intern.fdZhiDengLeiXing') }">
		</list:data-column>
		<list:data-column headerClass="width100" property="fdBuMenJiBie" title="${ lfn:message('hr-staff:hrStaffEkp_H14_Intern.fdBuMenJiBie') }">
		</list:data-column>
		<list:data-column headerClass="width100"  property="fdZhiJi" title="${ lfn:message('hr-staff:hrStaffEkp_H14_Intern.fdZhiJi') }">
		</list:data-column>
		<list:data-column headerClass="width100" property="fd3b1c68bd6cac0a_text" title="${ lfn:message('hr-staff:hrStaffEkp_H14_Intern.fdZhiJieShangJi') }">
		</list:data-column>
		<list:data-column headerClass="width100" property="fdRenYuanLeiBie" title="${ lfn:message('hr-staff:hrStaffEkp_H14_Intern.fdRenYuanLeiBie') }">
		</list:data-column>
		<list:data-column headerClass="width200" property="fdZhiJieShangJiKaoHeDeFen" title="${ lfn:message('hr-staff:hrStaffEkp_H14_Intern.fdZhiJieShangJiKaoHeDeFen') }">
		</list:data-column>
		<list:data-column headerClass="width200" property="fdZhiJieShangJiKaoHePingJi" title="${ lfn:message('hr-staff:hrStaffEkp_H14_Intern.fdZhiJieShangJiKaoHePingJi') }">
		</list:data-column>
		<list:data-column headerClass="width200" property="fdErJiBuMenFuZeRenPingJia" title="${ lfn:message('hr-staff:hrStaffEkp_H14_Intern.fdErJiBuMenFuZeRenPingJia') }">
		</list:data-column>
		<list:data-column headerClass="width200" property="fdErJiBuMenFuZeRenKaoHeDeFen" title="${ lfn:message('hr-staff:hrStaffEkp_H14_Intern.fdErJiBuMenFuZeRenKaoHeDeFen') }">
		</list:data-column>
		<list:data-column headerClass="width200" property="fdErJiBuMenFuZeRenKaoHePingJi" title="${ lfn:message('hr-staff:hrStaffEkp_H14_Intern.fdErJiBuMenFuZeRenKaoHePingJi') }">
		</list:data-column>
		<list:data-column headerClass="width200" property="fdYiJiBuMenFuZeRenPingJia" title="${ lfn:message('hr-staff:hrStaffEkp_H14_Intern.fdYiJiBuMenFuZeRenPingJia') }">
		</list:data-column>
		<list:data-column headerClass="width200" property="fdZhiJieShangJiPingJia" title="${ lfn:message('hr-staff:hrStaffEkp_H14_Intern.fdZhiJieShangJiPingJia') }">
		</list:data-column>
		<list:data-column headerClass="width200" property="fdYiJiBuMenFuZeRenKaoHeDeFen" title="${ lfn:message('hr-staff:hrStaffEkp_H14_Intern.fdYiJiBuMenFuZeRenKaoHeDeFen') }">
		</list:data-column>
		<list:data-column headerClass="width200" property="fdYiJiBuMenFuZeRenKaoHePingJi" title="${ lfn:message('hr-staff:hrStaffEkp_H14_Intern.fdYiJiBuMenFuZeRenKaoHePingJi') }">
		</list:data-column>
	<!-- 其它操作 -->
<%-- 		<list:data-column headerClass="width120" col="operations" title="${ lfn:message('list.operation') }" escape="false"> --%>
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<!-- 编辑 -->
<%-- 					<a class="btn_txt" href="javascript:edit('${Ekp_H14_Intern.fdId}')">${ lfn:message('button.edit') }</a> --%>
					<!-- 删除 -->
<%-- 					<a class="btn_txt" href="javascript:_delete('${Ekp_H14_Intern.fdId}')">${ lfn:message('button.delete') }</a> --%>
				</div>
			</div>
			<!--操作按钮 结束-->
<%-- 		</list:data-column> --%>
	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>
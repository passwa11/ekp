<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="Ekp_H14_nS" list="${queryPage.list }" varIndex="status">
		<list:data-column property="fdId">
		</list:data-column>
<%-- 	 	<list:data-column col="personInfoId"> --%>
<%-- 			${Ekp_H14_nS.fdBeiKaoHeRenXingMing.fdId} --%>
<%-- 		</list:data-column> --%>
		<list:data-column col="index">
		  ${status+1}
		</list:data-column>
<%-- 		<list:data-column headerClass="width100" col="nameAccount" title="${ lfn:message('hr-staff:hrStaffEkp_H14_nS.fdBeiKaoHeRenXingMing') }"> --%>
<%-- 		  ${Ekp_H14_nS.fdBeiKaoHeRenXingMing.fdName} --%>
<%-- 		</list:data-column> --%>
		<list:data-column headerClass="width100" property="fdBeiKaoHeRenXingMing" title="${ lfn:message('hr-staff:hrStaffEkp_H14_nS.fdBeiKaoHeRenXingMing') }">
		  
		</list:data-column>
		<list:data-column headerClass="width100" property="fdYiJiBuMen" title="${ lfn:message('hr-staff:hrStaffEkp_H14_nS.fdYiJiBuMen') }">
		 
		</list:data-column>
		<list:data-column headerClass="width100" property="fdErJiBuMen" title="${ lfn:message('hr-staff:hrStaffEkp_H14_nS.fdErJiBuMen') }">
		  
		</list:data-column>
		<list:data-column headerClass="width100" property="fdZhuYaoGongZuoYeJi" title="${ lfn:message('hr-staff:hrStaffEkp_H14_nS.fdZhuYaoGongZuoYeJi') }">
		 
		</list:data-column>
		<list:data-column headerClass="width100" property="fdZiWoPingJia" title="${ lfn:message('hr-staff:hrStaffEkp_H14_nS.fdZiWoPingJia') }">
		</list:data-column>
		<list:data-column headerClass="width100" property="fdJiXiaoDengJi" title="${ lfn:message('hr-staff:hrStaffEkp_H14_nS.fdJiXiaoDengJi') }">
		</list:data-column>
		<list:data-column headerClass="width100" property="fdJiXiaoPingYu" title="${ lfn:message('hr-staff:hrStaffEkp_H14_nS.fdJiXiaoPingYu') }">
		</list:data-column>
		<list:data-column headerClass="width100" property="fdShangJiPingJiaDeFen" title="${ lfn:message('hr-staff:hrStaffEkp_H14_nS.fdShangJiPingJiaDeFen') }">
		</list:data-column>
		<list:data-column headerClass="width100" property="fdBeiKaoHeRenGangWei" title="${ lfn:message('hr-staff:hrStaffEkp_H14_nS.fdBeiKaoHeRenGangWei') }">
		</list:data-column>
		<list:data-column headerClass="width100" col="fdJiDuJiXiaoDengJi" title="${ lfn:message('hr-staff:hrStaffEkp_H14_nS.fdJiDuJiXiaoDengJi') }">
		<kmss:showDate value="${Ekp_H14_nS.fdRuZhiRiQi}" type="date" /> 
		</list:data-column>
		<list:data-column headerClass="width120" property="fdJiDuJiXiaoDengJi" title="${ lfn:message('hr-staff:hrStaffEkp_H14_nS.fdJiDuJiXiaoDengJi') }">
		</list:data-column>
		<list:data-column headerClass="width120" property="fdNianDuJiXiaoDengJi" title="${ lfn:message('hr-staff:hrStaffEkp_H14_nS.fdNianDuJiXiaoDengJi') }">
		</list:data-column>
		<list:data-column headerClass="width120" property="fdYeJiShiFuDaBiao" title="${ lfn:message('hr-staff:hrStaffEkp_H14_nS.fdYeJiShiFuDaBiao') }">
		</list:data-column>
		<list:data-column headerClass="width100" col="fdJiHuaWanChengShiJian" title="${ lfn:message('hr-staff:hrStaffEkp_H14_nS.fdJiHuaWanChengShiJian') }">
		<kmss:showDate value="${Ekp_H14_nS.fdJiHuaWanChengShiJian}" type="date" /> 
		</list:data-column>
		<list:data-column headerClass="width100" property="fdYeJiKaoHeJieGuoYingYong" title="${ lfn:message('hr-staff:hrStaffEkp_H14_nS.fdYeJiKaoHeJieGuoYingYong') }">
		</list:data-column>
		<list:data-column headerClass="width100" col="fdKaoHeKaiShiShiJian" title="${ lfn:message('hr-staff:hrStaffEkp_H14_nS.fdKaoHeKaiShiShiJian') }">
		<kmss:showDate value="${Ekp_H14_nS.fdKaoHeKaiShiShiJian}" type="date" /> 
		</list:data-column>
		<list:data-column headerClass="width100" col="fdKaoHeJieShuShiJian" title="${ lfn:message('hr-staff:hrStaffEkp_H14_nS.fdKaoHeJieShuShiJian') }">
		<kmss:showDate value="${Ekp_H14_nS.fdKaoHeJieShuShiJian}" type="date" /> 
		</list:data-column>
		<list:data-column headerClass="width100" col="fdKaoHeJieShuShiJian" title="${ lfn:message('hr-staff:hrStaffEkp_H14_nS.fdKaoHeJieShuShiJian') }">
		<kmss:showDate value="${Ekp_H14_nS.fdKaoHeJieShuShiJian}" type="date" /> 
		</list:data-column>
		<list:data-column headerClass="width100" property="fdYJKHJGYY_qita" title="${ lfn:message('hr-staff:hrStaffEkp_H14_nS.fdYJKHJGYY_qita') }">
		</list:data-column>
		<list:data-column headerClass="width100" property="fdChengJiYuBuZu" title="${ lfn:message('hr-staff:hrStaffEkp_H14_nS.fdChengJiYuBuZu') }">
		</list:data-column>
		<list:data-column headerClass="width120" property="fdFenQiDian" title="${ lfn:message('hr-staff:hrStaffEkp_H14_nS.fdFenQiDian') }">
		</list:data-column>
		<list:data-column headerClass="width100" property="fdMianTanJieGuo" title="${ lfn:message('hr-staff:hrStaffEkp_H14_nS.fdMianTanJieGuo') }">
		</list:data-column>
		<list:data-column headerClass="width100" property="fdQiWangJiGaiShan" title="${ lfn:message('hr-staff:hrStaffEkp_H14_nS.fdQiWangJiGaiShan') }">
		</list:data-column>
		<list:data-column headerClass="width100" property="fdYouShiYuLieShi" title="${ lfn:message('hr-staff:hrStaffEkp_H14_nS.fdYouShiYuLieShi') }">
		</list:data-column>
		
		<list:data-column headerClass="width120" property="fdGaiJinShiXiang" title="${ lfn:message('hr-staff:hrStaffEkp_H14_nS.fdGaiJinShiXiang') }">
		</list:data-column>
		<list:data-column headerClass="width100" property="fdKunNanHuoSuoXuZhiChi" title="${ lfn:message('hr-staff:hrStaffEkp_H14_nS.fdKunNanHuoSuoXuZhiChi') }">
		</list:data-column>
		<list:data-column headerClass="width100" property="fdShiJiZhi" title="${ lfn:message('hr-staff:hrStaffEkp_H14_nS.fdShiJiZhi') }">
		</list:data-column>
		<list:data-column headerClass="width120" property="fdJiXiaoDaChengQingKuangShuoM" title="${ lfn:message('hr-staff:hrStaffEkp_H14_nS.fdJiXiaoDaChengQingKuangShuoM') }">
		</list:data-column>
		<list:data-column headerClass="width100" property="fdZiPingDeFen1" title="${ lfn:message('hr-staff:hrStaffEkp_H14_nS.fdZiPingDeFen1') }">
		</list:data-column>
	<!-- 其它操作 -->
		<list:data-column headerClass="width120" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<!-- 编辑 -->
					<a class="btn_txt" href="javascript:edit('${Ekp_H14_nS.fdId}')">${ lfn:message('button.edit') }</a>
					<!-- 删除 -->
					<a class="btn_txt" href="javascript:_delete('${Ekp_H14_nS.fdId}')">${ lfn:message('button.delete') }</a>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>
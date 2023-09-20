<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="Ekp_H14_S1" list="${queryPage.list }" varIndex="status">
		<list:data-column property="fdId">
		</list:data-column>
	 	<list:data-column col="personInfoId">
			${Ekp_H14_S1.fdBeiKaoHeRenXingMing.fdId}
		</list:data-column>
		<list:data-column col="index">
		  ${status+1}
		</list:data-column>
		<list:data-column headerClass="width100" col="nameAccount" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S1.fdBeiKaoHeRenXingMing') }">
		  ${Ekp_H14_S1.fdBeiKaoHeRenXingMing.fdName}
		</list:data-column>
<%-- 		<list:data-column headerClass="width100" property="fdBeiKaoHeRenXingMing" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S1.fdBeiKaoHeRenXingMing') }"> --%>
		  
<%-- 		</list:data-column> --%>
		<list:data-column headerClass="width100" property="fdYiJiBuMen" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S1.fdYiJiBuMen') }">
		 
		</list:data-column>
		<list:data-column headerClass="width100" col="fdSuoShuFenBu" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S1.fdSuoShuFenBu') }">
		 ${Ekp_H14_S1.fdBeiKaoHeRenXingMing.fdName}
		</list:data-column>
<%-- 		<list:data-column headerClass="width100" property="fdZhiJieShangJiKaoHeDengJi" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S1.fdZhiJieShangJiKaoHeDengJi') }"> --%>
		 
<%-- 		</list:data-column> --%>
		<list:data-column headerClass="width100" property="fdYuanGongBianHao" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S1.fdYuanGongBianHao') }">
		 
		</list:data-column>
<%-- 		<list:data-column headerClass="width100" property="fdEjbmfzrKaoHeDengJi" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S1.fdEjbmfzrKaoHeDengJi') }"> --%>
		 
<%-- 		</list:data-column> --%>
<%-- 		<list:data-column headerClass="width100" property="fdYjbmfzrPingYu" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S1.fdYjbmfzrPingYu') }"> --%>
		 
<%-- 		</list:data-column> --%>
<%-- 		<list:data-column headerClass="width100" property="fdYjbmfzrKaoHeDeFen" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S1.fdYjbmfzrKaoHeDeFen') }"> --%>
		 
<%-- 		</list:data-column> --%>
<%-- 		<list:data-column headerClass="width100" property="fdFgfzrKaoHePingFen" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S1.fdFgfzrKaoHePingFen') }"> --%>
		 
<%-- 		</list:data-column> --%>
<%-- 		<list:data-column headerClass="width100" property="fdFgfzrKaoHeDengJi" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S1.fdFgfzrKaoHeDengJi') }"> --%>
		 
<%-- 		</list:data-column> --%>
<%-- 		<list:data-column headerClass="width100" property="fdZongCaiShenPi" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S1.fdZongCaiShenPi') }"> --%>
		 
<%-- 		</list:data-column> --%>
<%-- 		<list:data-column headerClass="width100" property="fdZongCaiKaoHePingFen" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S1.fdZongCaiKaoHePingFen') }"> --%>
		 
<%-- 		</list:data-column> --%>
<%-- 		<list:data-column headerClass="width100" property="fdZhiJieShangJiKaoHeDeFen" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S1.fdZhiJieShangJiKaoHeDeFen') }"> --%>
		 
<%-- 		</list:data-column> --%>
<%-- 		<list:data-column headerClass="width100" property="fdDongShiChangShenPi" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S1.fdDongShiChangShenPi') }"> --%>
		 
<%-- 		</list:data-column> --%>
<%-- 		<list:data-column headerClass="width100" property="fdGongZuoMuBiao2" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S1.fdGongZuoMuBiao2') }"> --%>
		 
<%-- 		</list:data-column> --%>
<%-- 		<list:data-column headerClass="width100" property="fdGongZuoMuBiao3" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S1.fdGongZuoMuBiao3') }"> --%>
		 
<%-- 		</list:data-column> --%>
<%-- 		<list:data-column headerClass="width100" property="fdDongShiChangKaoHePingFen" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S1.fdDongShiChangKaoHePingFen') }"> --%>
		 
<%-- 		</list:data-column> --%>
<%-- 		<list:data-column headerClass="width100" property="fdZongCaiKaoHeDengJi" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S1.fdZongCaiKaoHeDengJi') }"> --%>
		 
<%-- 		</list:data-column> --%>
<%-- 		<list:data-column headerClass="width100" property="fdEjbmfzrKaoHeDeFen" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S1.fdEjbmfzrKaoHeDeFen') }"> --%>
		 
<%-- 		</list:data-column> --%>
<%-- 		<list:data-column headerClass="width100" property="fdYjbmfzrKaoHeDengJi" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S1.fdYjbmfzrKaoHeDengJi') }"> --%>
		 
<%-- 		</list:data-column> --%>
<%-- 		<list:data-column headerClass="width100" property="fdFgfzrShenPi" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S1.fdFgfzrShenPi') }"> --%>
		 
<%-- 		</list:data-column> --%>
<%-- 		<list:data-column headerClass="width100" property="fdRlxzzxfzrPingYu" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S1.fdRlxzzxfzrPingYu') }"> --%>
		 
<%-- 		</list:data-column> --%>
<%-- 		<list:data-column headerClass="width100" property="fdXcjxfzrShenPi" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S1.fdXcjxfzrShenPi') }"> --%>
		 
<%-- 		</list:data-column> --%>
<%-- 		<list:data-column headerClass="width100" property="fdKPIDaChengQingKuang" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S1.fdKPIDaChengQingKuang') }"> --%>
		 
<%-- 		</list:data-column> --%>
<%-- 		<list:data-column headerClass="width100" property="fdEjbmfzrPingYu" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S1.fdEjbmfzrPingYu') }"> --%>
		 
<%-- 		</list:data-column> --%>
<%-- 		<list:data-column headerClass="width100" property="fdKaoHeJiDu" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S1.fdKaoHeJiDu') }"> --%>
		 
<%-- 		</list:data-column> --%>
		<list:data-column headerClass="width100" property="fdErJiBuMen" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S1.fdErJiBuMen') }">
		  
		</list:data-column>
		<list:data-column headerClass="width100" property="fdSanJiBuMen" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S1.fdSanJiBuMen') }">
		 
		</list:data-column>
		<list:data-column headerClass="width100" property="fdGangWeiMingChen" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S1.fdGangWeiMingChen') }">
		 
		</list:data-column>
		<list:data-column headerClass="width100" property="fdZhiLei" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S1.fdZhiLei') }">
		 
		</list:data-column>
		<list:data-column headerClass="width100" property="fdZhiJiXiShu" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S1.fdZhiJiXiShu') }">
		 
		</list:data-column>
		<list:data-column headerClass="width100" property="fdRenYuanLeiBie" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S1.fdRenYuanLeiBie') }">
		 
		</list:data-column>
<%-- 		<list:data-column headerClass="width100" property="fdZhijieshangji_text" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S1.fdZhijieshangji_text') }"> --%>
		 
<%-- 		</list:data-column> --%>
		<list:data-column headerClass="width100" property="fdGangWeiXingZhi" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S1.fdGangWeiXingZhi') }">
		 
		</list:data-column>
<%-- 		<list:data-column headerClass="width100" property="fdZhuYaoGongZuoYeJi" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S1.fdZhuYaoGongZuoYeJi') }"> --%>
		 
<%-- 		</list:data-column> --%>
<%-- 		<list:data-column headerClass="width100" property="fdZiWoPingJia" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S1.fdZiWoPingJia') }"> --%>
<%-- 		</list:data-column> --%>
		<list:data-column headerClass="width100" property="fdJiXiaoDengJi" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S1.fdJiXiaoDengJi') }">
		
		</list:data-column>
<%-- 		<list:data-column headerClass="width120" property="fdJiXiaoPingYu" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S1.fdJiXiaoPingYu') }"> --%>
<%-- 		</list:data-column> --%>
		<list:data-column headerClass="width100" property="fdBeiKaoHeRenDeChengJiYuBuZu" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S1.fdBeiKaoHeRenDeChengJiYuBuZu') }">
		</list:data-column>
		<list:data-column headerClass="width100" property="fdBeiKaoHeRenGangWei" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S.fdBeiKaoHeRenGangWei') }">
		</list:data-column>
		<list:data-column headerClass="width100" col="fdRuZhiRiQi" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S1.fdRuZhiRiQi') }">
		<kmss:showDate value="${Ekp_H14_S1.fdRuZhiRiQi}" type="date" /> 
		</list:data-column>
<%-- 		<list:data-column headerClass="width100" col="fdQiWangWanChengShiJian3" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S1.fdQiWangWanChengShiJian3') }"> --%>
<%-- 		<kmss:showDate value="${Ekp_H14_S1.fdQiWangWanChengShiJian3}" type="date" />  --%>
<%-- 		</list:data-column> --%>
<%-- 		<list:data-column headerClass="width100" col="fdKaoHeNianFen" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S1.fdKaoHeNianFen') }"> --%>
<%-- 		<kmss:showDate value="${Ekp_H14_S1.fdKaoHeNianFen}" type="date" />  --%>
<%-- 		</list:data-column> --%>
		<list:data-column headerClass="width100" col="fdShenQingRiQi" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S1.fdShenQingRiQi') }">
		<kmss:showDate value="${Ekp_H14_S1.fdShenQingRiQi}" type="date" /> 
		</list:data-column>
		<list:data-column headerClass="width120" property="fdJiDuJiXiaoDengJi" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S1.fdJiDuJiXiaoDengJi') }">
		</list:data-column>
		<list:data-column headerClass="width120" property="fdNianDuJiXiaoDengJi" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S1.fdNianDuJiXiaoDengJi') }">
		</list:data-column>
		<list:data-column headerClass="width120" property="fdYeJiShiFuDaBiao" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S1.fdYeJiShiFuDaBiao') }">
		</list:data-column>
<%-- 		<list:data-column headerClass="width120" property="fdZhongDianGongZuo3" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S1.fdZhongDianGongZuo3') }"> --%>
<%-- 		</list:data-column> --%>
		<list:data-column headerClass="width100" col="fdJiHuaWanChengShiJian" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S.fdJiHuaWanChengShiJian') }">
		<kmss:showDate value="${Ekp_H14_S1.fdJiHuaWanChengShiJian}" type="date" /> 
		</list:data-column>
		<list:data-column headerClass="width100" col="fdDuiYingYeJikhjgyy" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S1.fdYeJiKaoHeJieGuoYingYong') }">
		<c:if test="${Ekp_H14_S1.fdDuiYingYeJikhjgyy == 1.0}">
		升级
		</c:if>
		<c:if test="${Ekp_H14_S1.fdDuiYingYeJikhjgyy == 2.0}">
		职级/岗位不变
		</c:if>
			<c:if test="${Ekp_H14_S1.fdDuiYingYeJikhjgyy == 3.0}">
		予以换岗
		</c:if>
		<c:if test="${Ekp_H14_S1.fdDuiYingYeJikhjgyy == 4.0}">
		降级降薪
		</c:if>
			<c:if test="${Ekp_H14_S1.fdDuiYingYeJikhjgyy == 5.0}">
		淘汰
		</c:if>
		<c:if test="${Ekp_H14_S1.fdDuiYingYeJikhjgyy == 6.0}">
		其他
		</c:if>
		</list:data-column>
		<list:data-column headerClass="width100" property="fdKaoHeKaiShiShiJian" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S1.fdKaoHeKaiShiShiJian') }">
		<kmss:showDate value="${Ekp_H14_S1.fdKaoHeKaiShiShiJian}" type="date" /> 
		</list:data-column>
<%-- 		<list:data-column headerClass="width100" property="fdQiWangWanChengShiJian2" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S1.fdQiWangWanChengShiJian2') }"> --%>
<%-- 		<kmss:showDate value="${Ekp_H14_S1.fdQiWangWanChengShiJian2}" type="date" />  --%>
<%-- 		</list:data-column> --%>
		<list:data-column headerClass="width100" col="fdKaoHeJieShuShiJian" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S1.fdKaoHeJieShuShiJian') }">
		<kmss:showDate value="${Ekp_H14_S1.fdKaoHeJieShuShiJian}" type="date" /> 
		</list:data-column>
		<list:data-column headerClass="width100" property="fdKaoHeZhouQi" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S1.fdKaoHeZhouQi') }">
		</list:data-column>
<%-- 		<list:data-column headerClass="width100" col="fdQiWangWanChengShiJian1" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S1.fdQiWangWanChengShiJian1') }"> --%>
<%-- 		<kmss:showDate value="${Ekp_H14_S1.fdQiWangWanChengShiJian1}" type="date" />  --%>
<%-- 		</list:data-column> --%>
<%-- 		<list:data-column headerClass="width100" property="fdGongZuoMuBiao1" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S1.fdGongZuoMuBiao1') }"> --%>
<%-- 		</list:data-column> --%>
<%-- 		<list:data-column headerClass="width100" property="fdZhongDianGongZuo1" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S1.fdZhongDianGongZuo1') }"> --%>
<%-- 		</list:data-column> --%>
<%-- 		<list:data-column headerClass="width100" property="fdDongShiChangKaoHeDengJi" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S1.fdDongShiChangKaoHeDengJi') }"> --%>
<%-- 		</list:data-column> --%>
<%-- 		<list:data-column headerClass="width100" property="fdZhongDianGongZuo2" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S1.fdZhongDianGongZuo2') }"> --%>
<%-- 		</list:data-column> --%>
<%-- 		<list:data-column headerClass="width100" property="fdZhiJieShangJiPingJia" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S1.fdZhiJieShangJiPingJia') }"> --%>
<%-- 		</list:data-column> --%>
		<list:data-column headerClass="width100" property="fdZiPingDengJi" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S1.fdZiPingDengJi') }">
		</list:data-column>
		<list:data-column headerClass="width100" property="fdChengJiYuBuZu" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S1.fdChengJiYuBuZu') }">
		</list:data-column>
		<list:data-column headerClass="width120" property="fdFenQiDian" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S.fdFenQiDian') }">
		</list:data-column>
		<list:data-column headerClass="width100" col="fdMianTanJieGuo" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S1.fdMianTanJieGuo') }">
		<c:if test="${Ekp_H14_S1.fdMianTanJieGuo == 1.0}">
		完全或基本达成一致
		</c:if>
		<c:if test="${Ekp_H14_S1.fdMianTanJieGuo == 2.0}">
		存在分歧
		</c:if>
		</list:data-column>
		<list:data-column headerClass="width120" property="fdGaiJinShiXiang" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S1.fdGaiJinShiXiang') }">
		</list:data-column>
		<list:data-column headerClass="width100" property="fdKunNanHuoSuoXuZhiChi" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S.fdKunNanHuoSuoXuZhiChi') }">
		</list:data-column>
<%-- 		<list:data-column headerClass="width100" property="fdShiJiZhi" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S.fdShiJiZhi') }"> --%>
<%-- 		</list:data-column> --%>
<%-- 		<list:data-column headerClass="width120" property="fdJiXiaoDaChengQingKuangShuoM" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S.fdJiXiaoDaChengQingKuangShuoM') }"> --%>
<%-- 		</list:data-column> --%>
<%-- 		<list:data-column headerClass="width100" property="fdZiPingDeFen1" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S.fdZiPingDeFen1') }"> --%>
<%-- 		</list:data-column> --%>
	<!-- 其它操作 -->
<%-- 		<list:data-column headerClass="width120" col="operations" title="${ lfn:message('list.operation') }" escape="false"> --%>
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<!-- 编辑 -->
<%-- 					<a class="btn_txt" href="javascript:edit('${Ekp_H14_S.fdId}')">${ lfn:message('button.edit') }</a> --%>
					<!-- 删除 -->
<%-- 					<a class="btn_txt" href="javascript:_delete('${Ekp_H14_S.fdId}')">${ lfn:message('button.delete') }</a> --%>
				</div>
			</div>
			<!--操作按钮 结束-->
<%-- 		</list:data-column> --%>
	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>
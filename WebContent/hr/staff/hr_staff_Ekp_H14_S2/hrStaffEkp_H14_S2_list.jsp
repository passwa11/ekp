<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="Ekp_H14_S2" list="${queryPage.list }" varIndex="status">
		<list:data-column property="fdId">
		</list:data-column>
	 	<list:data-column col="personInfoId">
			${Ekp_H14_S2.fdBeiKaoHeRenXingMing.fdId}
		</list:data-column>
		<list:data-column col="index">
		  ${status+1}
		</list:data-column>
		<list:data-column headerClass="width100" col="nameAccount" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S2.fdBeiKaoHeRenXingMing') }">
		  ${Ekp_H14_S2.fdBeiKaoHeRenXingMing.fdName}
		</list:data-column>
<%-- 		<list:data-column headerClass="width100" property="fdBeiKaoHeRenXingMing" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S2.fdBeiKaoHeRenXingMing') }"> --%>
		  
<%-- 		</list:data-column> --%>
		<list:data-column headerClass="width100" property="fdYiJiBuMen" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S2.fdYiJiBuMen') }">
		 
		</list:data-column>
		<list:data-column headerClass="width100" col="fdSuoShuFenBu" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S2.fdSuoShuFenBu') }">
		 ${Ekp_H14_S2.fdBeiKaoHeRenXingMing.fdName}
		</list:data-column>
<%-- 		<list:data-column headerClass="width100" property="fdZhiJieShangJiKaoHeDengJi" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S2.fdZhiJieShangJiKaoHeDengJi') }"> --%>
		 
<%-- 		</list:data-column> --%>
		<list:data-column headerClass="width100" property="fdYuanGongBianHao" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S2.fdYuanGongBianHao') }">
		 
		</list:data-column>
<%-- 		<list:data-column headerClass="width100" property="fdEjbmfzrKaoHeDengJi" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S2.fdEjbmfzrKaoHeDengJi') }"> --%>
		 
<%-- 		</list:data-column> --%>
<%-- 		<list:data-column headerClass="width100" property="fdYjbmfzrPingYu" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S2.fdYjbmfzrPingYu') }"> --%>
		 
<%-- 		</list:data-column> --%>
<%-- 		<list:data-column headerClass="width100" property="fdYjbmfzrKaoHeDeFen" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S2.fdYjbmfzrKaoHeDeFen') }"> --%>
		 
<%-- 		</list:data-column> --%>
<%-- 		<list:data-column headerClass="width100" property="fdFgfzrKaoHePingFen" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S2.fdFgfzrKaoHePingFen') }"> --%>
		 
<%-- 		</list:data-column> --%>
<%-- 		<list:data-column headerClass="width100" property="fdFgfzrKaoHeDengJi" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S2.fdFgfzrKaoHeDengJi') }"> --%>
		 
<%-- 		</list:data-column> --%>
<%-- 		<list:data-column headerClass="width100" property="fdZongCaiShenPi" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S2.fdZongCaiShenPi') }"> --%>
		 
<%-- 		</list:data-column> --%>
<%-- 		<list:data-column headerClass="width100" property="fdZongCaiKaoHePingFen" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S2.fdZongCaiKaoHePingFen') }"> --%>
		 
<%-- 		</list:data-column> --%>
<%-- 		<list:data-column headerClass="width100" property="fdZhiJieShangJiKaoHeDeFen" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S2.fdZhiJieShangJiKaoHeDeFen') }"> --%>
		 
<%-- 		</list:data-column> --%>
<%-- 		<list:data-column headerClass="width100" property="fdDongShiChangShenPi" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S2.fdDongShiChangShenPi') }"> --%>
		 
<%-- 		</list:data-column> --%>
<%-- 		<list:data-column headerClass="width100" property="fdGongZuoMuBiao2" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S2.fdGongZuoMuBiao2') }"> --%>
		 
<%-- 		</list:data-column> --%>
<%-- 		<list:data-column headerClass="width100" property="fdGongZuoMuBiao3" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S2.fdGongZuoMuBiao3') }"> --%>
		 
<%-- 		</list:data-column> --%>
<%-- 		<list:data-column headerClass="width100" property="fdDongShiChangKaoHePingFen" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S2.fdDongShiChangKaoHePingFen') }"> --%>
		 
<%-- 		</list:data-column> --%>
<%-- 		<list:data-column headerClass="width100" property="fdZongCaiKaoHeDengJi" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S2.fdZongCaiKaoHeDengJi') }"> --%>
		 
<%-- 		</list:data-column> --%>
<%-- 		<list:data-column headerClass="width100" property="fdEjbmfzrKaoHeDeFen" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S2.fdEjbmfzrKaoHeDeFen') }"> --%>
		 
<%-- 		</list:data-column> --%>
<%-- 		<list:data-column headerClass="width100" property="fdYjbmfzrKaoHeDengJi" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S2.fdYjbmfzrKaoHeDengJi') }"> --%>
		 
<%-- 		</list:data-column> --%>
<%-- 		<list:data-column headerClass="width100" property="fdFgfzrShenPi" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S2.fdFgfzrShenPi') }"> --%>
		 
<%-- 		</list:data-column> --%>
<%-- 		<list:data-column headerClass="width100" property="fdRlxzzxfzrPingYu" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S2.fdRlxzzxfzrPingYu') }"> --%>
		 
<%-- 		</list:data-column> --%>
<%-- 		<list:data-column headerClass="width100" property="fdXcjxfzrShenPi" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S2.fdXcjxfzrShenPi') }"> --%>
		 
<%-- 		</list:data-column> --%>
<%-- 		<list:data-column headerClass="width100" property="fdKPIDaChengQingKuang" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S2.fdKPIDaChengQingKuang') }"> --%>
		 
<%-- 		</list:data-column> --%>
<%-- 		<list:data-column headerClass="width100" property="fdEjbmfzrPingYu" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S2.fdEjbmfzrPingYu') }"> --%>
		 
<%-- 		</list:data-column> --%>
<%-- 		<list:data-column headerClass="width100" property="fdKaoHeJiDu" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S2.fdKaoHeJiDu') }"> --%>
		 
<%-- 		</list:data-column> --%>
		<list:data-column headerClass="width100" property="fdErJiBuMen" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S2.fdErJiBuMen') }">
		  
		</list:data-column>
<%-- 		<list:data-column headerCLASS="WIDTH100" PROPERTY="FDSANJIBUMEN" TITLE="${ LFN:MESSAGE('HR-STAFF:HRSTAFFEKP_H14_S2.FDSANJIBUMEN') }"> --%>
		 
<%-- 		</LIST:DATA-COLUMN> --%>
		<list:data-column headerClass="width100" property="fdGangWeiMingChen" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S2.fdGangWeiMingChen') }">
		 
		</list:data-column>
		<list:data-column headerClass="width100" property="fdKaoHeZhouQi" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S2.fdKaoHeZhouQi') }">
		</list:data-column>
		<list:data-column headerClass="width100" property="fdZhiLei" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S2.fdZhiLei') }">
		 
		</list:data-column>
		<list:data-column headerClass="width100" property="fdZhiJiXiShu" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S2.fdZhiJiXiShu') }">
		 
		</list:data-column>
		<list:data-column headerClass="width100" property="fdRenYuanLeiBie" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S2.fdRenYuanLeiBie') }">
		 
		</list:data-column>
<%-- 		<list:data-column headerClass="width100" property="fdZhijieshangji_text" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S2.fdZhijieshangji_text') }"> --%>
		 
<%-- 		</list:data-column> --%>
		<list:data-column headerClass="width100" property="fdGangWeiXingZhi" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S2.fdGangWeiXingZhi') }">
		 
		</list:data-column>
<%-- 		<list:data-column headerClass="width100" property="fdZhuYaoGongZuoYeJi" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S2.fdZhuYaoGongZuoYeJi') }"> --%>
		 
<%-- 		</list:data-column> --%>
<%-- 		<list:data-column headerClass="width100" property="fdZiWoPingJia" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S2.fdZiWoPingJia') }"> --%>
<%-- 		</list:data-column> --%>
		<list:data-column headerClass="width100" property="fdJiXiaoDengJi" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S2.fdJiXiaoDengJi') }">
		
		</list:data-column>
		<list:data-column headerClass="width100" property="fdKunNanHuoSuoXuZhiChi" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S2.fdKunNanHuoSuoXuZhiChi') }">
		
		</list:data-column>
<%-- 		<list:data-column headerClass="width120" property="fdJiXiaoPingYu" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S2.fdJiXiaoPingYu') }"> --%>
<%-- 		</list:data-column> --%>
<%-- 		<list:data-column headerClass="width100" property="fdShangJiPingJiaDeFen" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S2.fdShangJiPingJiaDeFen') }"> --%>
<%-- 		</list:data-column> --%>
		<list:data-column headerClass="width100" property="fdBeiKaoHeRenGangWei" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S2.fdBeiKaoHeRenGangWei') }">
		</list:data-column>
		<list:data-column headerClass="width100" property="fdYouDaiGaiJinDeFangMian" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S2.fdYouDaiGaiJinDeFangMian') }">
		</list:data-column>
		<list:data-column headerClass="width100" property="fdXyjdQiWangJiGaiShan" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S2.fdXyjdQiWangJiGaiShan') }">
		</list:data-column>
		<list:data-column headerClass="width100" col="fdRuZhiRiQi" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S2.fdRuZhiRiQi') }">
		<kmss:showDate value="${Ekp_H14_S2.fdRuZhiRiQi}" type="date" /> 
		</list:data-column>
<%-- 		<list:data-column headerClass="width100" col="fdQiWangWanChengShiJian3" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S2.fdQiWangWanChengShiJian3') }"> --%>
<%-- 		<kmss:showDate value="${Ekp_H14_S2.fdQiWangWanChengShiJian3}" type="date" />  --%>
<%-- 		</list:data-column> --%>
<%-- 		<list:data-column headerClass="width100" col="fdKaoHeNianFen" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S2.fdKaoHeNianFen') }"> --%>
<%-- 		<kmss:showDate value="${Ekp_H14_S2.fdKaoHeNianFen}" type="date" />  --%>
<%-- 		</list:data-column> --%>
		<list:data-column headerClass="width100" col="fdShenQingRiQi" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S2.fdShenQingRiQi') }">
		<kmss:showDate value="${Ekp_H14_S2.fdShenQingRiQi}" type="date" /> 
		</list:data-column>
		<list:data-column headerClass="width120" property="fdJiDuJiXiaoDengJi" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S2.fdJiDuJiXiaoDengJi') }">
		</list:data-column>
		<list:data-column headerClass="width120" property="fdNianDuJiXiaoDengJi" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S2.fdNianDuJiXiaoDengJi') }">
		</list:data-column>
		<list:data-column headerClass="width120" property="fdZhuYaoChengJiHuoJieGuo" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S2.fdZhuYaoChengJiHuoJieGuo') }">
		</list:data-column>
		<list:data-column headerClass="width120" property="fdYeJiShiFuDaBiao" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S2.fdYeJiShiFuDaBiao') }">
		</list:data-column>
<%-- 		<list:data-column headerClass="width120" property="fdZhongDianGongZuo3" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S2.fdZhongDianGongZuo3') }"> --%>
<%-- 		</list:data-column> --%>
		<list:data-column headerClass="width100" col="fdJiHuaWanChengShiJian" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S2.fdJiHuaWanChengShiJian') }">
		<kmss:showDate value="${Ekp_H14_S2.fdJiHuaWanChengShiJian}" type="date" /> 
		</list:data-column>
		<list:data-column headerClass="width100" property="fdYeJiKaoHeJieGuoYingYong" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S2.fdYeJiKaoHeJieGuoYingYong') }">
		</list:data-column>
		<list:data-column headerClass="width100" property="fdKaoHeKaiShiShiJian" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S2.fdKaoHeKaiShiShiJian') }">
		<kmss:showDate value="${Ekp_H14_S2.fdKaoHeKaiShiShiJian}" type="date" /> 
		</list:data-column>
<%-- 		<list:data-column headerClass="width100" property="fdQiWangWanChengShiJian2" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S2.fdQiWangWanChengShiJian2') }"> --%>
<%-- 		<kmss:showDate value="${Ekp_H14_S2.fdQiWangWanChengShiJian2}" type="date" />  --%>
<%-- 		</list:data-column> --%>
		<list:data-column headerClass="width100" col="fdKaoHeJieShuShiJian" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S2.fdKaoHeJieShuShiJian') }">
		<kmss:showDate value="${Ekp_H14_S2.fdKaoHeJieShuShiJian}" type="date" /> 
		</list:data-column>
<%-- 		<list:data-column headerClass="width100" col="fdQiWangWanChengShiJian1" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S2.fdQiWangWanChengShiJian1') }"> --%>
<%-- 		<kmss:showDate value="${Ekp_H14_S2.fdQiWangWanChengShiJian1}" type="date" />  --%>
<%-- 		</list:data-column> --%>
<%-- 		<list:data-column headerClass="width100" property="fdGongZuoMuBiao1" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S2.fdGongZuoMuBiao1') }"> --%>
<%-- 		</list:data-column> --%>
<%-- 		<list:data-column headerClass="width100" property="fdZhongDianGongZuo1" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S2.fdZhongDianGongZuo1') }"> --%>
<%-- 		</list:data-column> --%>
<%-- 		<list:data-column headerClass="width100" property="fdDongShiChangKaoHeDengJi" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S2.fdDongShiChangKaoHeDengJi') }"> --%>
<%-- 		</list:data-column> --%>
<%-- 		<list:data-column headerClass="width100" property="fdZhongDianGongZuo2" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S2.fdZhongDianGongZuo2') }"> --%>
<%-- 		</list:data-column> --%>
<%-- 		<list:data-column headerClass="width100" property="fdZhiJieShangJiPingJia" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S2.fdZhiJieShangJiPingJia') }"> --%>
<%-- 		</list:data-column> --%>
		<list:data-column headerClass="width100" property="fdZiPingDengJi" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S2.fdZiPingDengJi') }">
		</list:data-column>
		<list:data-column headerClass="width100" property="fdChengJiYuBuZu" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S2.fdChengJiYuBuZu') }">
		</list:data-column>
<%-- 		<list:data-column headerClass="width120" property="fdFenQiDian" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S.fdFenQiDian') }"> --%>
<%-- 		</list:data-column> --%>
		<list:data-column headerClass="width100" property="fdMianTanJieGuo" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S2.fdMianTanJieGuo') }">
		</list:data-column>
		<list:data-column headerClass="width120" property="fdGaiJinShiXiang" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S2.fdGaiJinShiXiang') }">
		</list:data-column>
<%-- 		<list:data-column headerClass="width100" property="fdKunNanHuoSuoXuZhiChi" title="${ lfn:message('hr-staff:hrStaffEkp_H14_S.fdKunNanHuoSuoXuZhiChi') }"> --%>
<%-- 		</list:data-column> --%>
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
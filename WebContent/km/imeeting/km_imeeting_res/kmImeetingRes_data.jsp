<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="com.landray.kmss.km.imeeting.model.KmImeetingRes"%>
<list:data>
	<list:data-columns var="kmImeetingRes" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<list:data-column headerClass="width30"  property="fdOrder" title="${ lfn:message('km-imeeting:kmImeetingRes.fdOrder') }" />
		<!-- 会议室名称 -->	
		<list:data-column  property="fdName" title="${ lfn:message('km-imeeting:kmImeetingRes.fdName') }" />
		<!-- 会议室分类 -->
		<list:data-column property="docCategory.fdName" title="${ lfn:message('km-imeeting:kmImeetingRes.docCategory') }" />
		<!-- 地点楼层 -->
		<list:data-column property="fdAddressFloor" title="${ lfn:message('km-imeeting:kmImeetingRes.fdAddressFloor') }" />
		<!-- 容纳人数 -->
		<list:data-column property="fdSeats" title="${ lfn:message('km-imeeting:kmImeetingRes.fdSeats') }" />
		<!-- 是否已设置 -->
		<%
  			if(pageContext.getAttribute("kmImeetingRes")!=null){
  					KmImeetingRes imeetingRes = (KmImeetingRes)pageContext.getAttribute("kmImeetingRes");
  					String seatDetail = imeetingRes.getFdSeatDetail();
				if(StringUtil.isNotNull(seatDetail)&& seatDetail.length() > 2){
					pageContext.setAttribute("fdIsSeatPlan", "true");
				}else{
					pageContext.setAttribute("fdIsSeatPlan", "false");
				}
  			}
		%>
		<list:data-column col="fdIsSeatPlan" title="${ lfn:message('km-imeeting:kmImeetingRes.fdIsSeatPlan') }" escape="false">
			<c:choose>
				<c:when test="${fdIsSeatPlan == 'false'}">
					<kmss:message bundle="km-imeeting" key="kmImeetingRes.notPlan" />
				</c:when>
				<c:otherwise>
					<kmss:message bundle="km-imeeting" key="kmImeetingRes.isPlan" />
				</c:otherwise>
			</c:choose>
		</list:data-column>
		<!-- 保管者 -->
		<list:data-column property="docKeeper.fdName" title="${ lfn:message('km-imeeting:kmImeetingRes.docKeeper') }" />
		<!-- 是否有效 -->
		<list:data-column col="fdIsAvailable" title="${ lfn:message('km-imeeting:kmImeetingRes.fdIsAvailable') }" >
			<sunbor:enumsShow value="${kmImeetingRes.fdIsAvailable}" enumsType="common_yesno"/>
		</list:data-column>
		<list:data-column headerClass="width180" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!-- 操作列 -->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/km/imeeting/km_imeeting_res/kmImeetingRes.do?method=edit&fdId=${kmImeetingRes.fdId}" requestMethod="GET">
						<!-- 编辑 -->
						<a class="btn_txt" href="javascript:edit('${kmImeetingRes.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/km/imeeting/km_imeeting_res/kmImeetingRes.do?method=delete&fdId=${kmImeetingRes.fdId}" requestMethod="POST">
						<!-- 删除 -->
						<a class="btn_txt" href="javascript:deleteAll('${kmImeetingRes.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
					</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>
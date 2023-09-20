<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.view" sidebar="no">
	<template:replace name="head">
		<style type="text/css">
			#hover_noColor_button {  
				padding: 3px 10px;
				line-height: 20px;
				color: #fff;
				text-decoration: none;
				border: 0;
				border-radius: 4px;
				background-color: #4285f4;
				cursor: pointer!important;
				transition-duration: .3s;
			} 
		</style>
		<link rel="Stylesheet" href="${LUI_ContextPath}/km/imeeting/resource/css/view.css?s_cache=${MUI_Cache}" />
	</template:replace>
	<template:replace name="title">
		<c:out value="${ kmImeetingBookForm.fdName} - ${lfn:message('km-imeeting:table.kmImeetingBook') }"></c:out>
	</template:replace>
	<%--操作栏--%>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
			<ui:button text="${ lfn:message('button.close') }" order="5"  onclick="Com_CloseWindow()"></ui:button>
		</ui:toolbar>
	</template:replace>
	<template:replace name="content">
		<html:form action="/km/imeeting/km_imeeting_book/kmImeetingBook.do"  styleId="bookform">
			<p class="txttitle">
				<bean:message bundle="km-imeeting" key="table.kmImeetingBook" />
			</p>
			<c:if test="${(kmImeetingBookForm.fdExamerId eq KMSS_Parameter_CurrentUserId || isfdExamer == 'true') && kmImeetingBookForm.isNotify == 'true'}">
				<c:if test="${kmImeetingBookForm.fdHasExam == null}">
					<div class="lui_metting_notice_container">
						<i class="lui_icon_s icon_warn"></i>
						<span class="tips"><bean:message key="kmImeetingBook.exam.empty" bundle="km-imeeting"/></span>
						<a class="lui_form_button" id="hover_noColor_button" onclick="exam('${kmImeetingBookForm.fdHasExam}');" target="_blank">
                            	<bean:message key="kmImeetingBook.exam.please" bundle="km-imeeting"/>   
                        </a>
					</div>
				</c:if>
				<c:if test="${kmImeetingBookForm.fdHasExam == 'true'}">
					<div class="lui_metting_notice_container">
						<i class="lui_icon_s icon_success"></i>
						<span class="tips"><bean:message key="kmImeetingBook.exam.yes" bundle="km-imeeting"/></span>
					</div>
				</c:if>
				<c:if test="${kmImeetingBookForm.fdHasExam == 'false'}">
					<div class="lui_metting_notice_container">
						<i class="lui_icon_s icon_success"></i>
						<span class="tips"><bean:message key="kmImeetingBook.exam.no" bundle="km-imeeting"/></span>
					</div>
				</c:if>
			</c:if>
			<html:hidden property="fdId" />
			<html:hidden property="docCreatorId" />
			<div style="float: right;margin:10px;">
				<span>
					<bean:message bundle="km-imeeting" key="kmImeetingBook.exam.status"/>：
					<%--未召开--%>
					<c:if test="${kmImeetingBookForm.fdHasExam eq 'true'}">
						<bean:message bundle="km-imeeting" key="kmImeetingBook.exam.status.yes"/>
					</c:if>
					<%--正在召开--%>
					<c:if test="${kmImeetingBookForm.fdHasExam eq 'false'}">
						<bean:message bundle="km-imeeting" key="kmImeetingBook.exam.status.no"/>
					</c:if>
					<%--已召开--%>
					<c:if test="${empty kmImeetingBookForm.fdHasExam}">
						<bean:message bundle="km-imeeting" key="kmImeetingCalendar.res.wait"/>
					</c:if>
				</span>
			</div>
			<table class="tb_normal" width="98%" style="margin-top: 15px;">
				<c:if test="${kmImeetingBookForm.docCreatorId eq KMSS_Parameter_CurrentUserId && kmImeetingBookForm.isNotify == 'true' && kmImeetingBookForm.fdHasExam != null}">
					<tr>
						<td colspan="4" class="com_subject" style="font-weight: bold;"><bean:message bundle="km-imeeting" key="kmImeetingBook.examInfo" /></td>
					</tr>
					<tr>
						<td class="td_normal_title">
							<bean:message key="kmImeetingBook.fdExamer" bundle="km-imeeting"/>
						</td>
						<td colspan="3">
							<xform:text property="fdExamerName" />
						</td>
					</tr>
					<tr>
						<td class="td_normal_title">
							<bean:message key="kmImeetingBook.exam.status" bundle="km-imeeting"/>
						</td>
						<td colspan="3">
							<xform:radio property="fdHasExam" showStatus="view">
								<xform:simpleDataSource value="true"><bean:message key="kmImeetingBook.exam.status.yes" bundle="km-imeeting"/></xform:simpleDataSource>
								<xform:simpleDataSource value="false"><bean:message key="kmImeetingBook.exam.status.no" bundle="km-imeeting"/></xform:simpleDataSource>
							</xform:radio>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title">
							<bean:message key="kmImeetingBook.exam.remark" bundle="km-imeeting"/>
						</td>
						<td colspan="3">
							<xform:textarea property="fdExamRemark" showStatus="view" />
						</td>
					</tr>
				</c:if>
				<tr>
					<td colspan="4" class="com_subject" style="font-weight: bold;"><bean:message bundle="km-imeeting" key="kmImeetingBook.baseInfo" /></td>
				</tr>
				<tr>
	     			<%--会议名称--%>
	              	<td width="15%" class="td_normal_title" >
	              		<bean:message bundle="km-imeeting" key="kmImeetingBook.fdName" />
	              	</td>
	              	<td width="85%" colspan="3" >
	              		<xform:text property="fdName" style="width:90%;"></xform:text>
	              	</td>
	            </tr>
				<tr>
					<%--召开时间/结束时间--%>
					<td width="15%" class="td_normal_title" >
						<bean:message bundle="km-imeeting" key="kmImeetingBook.fdHoldDate" />
					</td>
					<td width="35%" >
						<xform:datetime property="fdHoldDate" dateTimeType="datetime" validators="after"></xform:datetime>
					</td>
					<td width="15%" class="td_normal_title" >
						<bean:message bundle="km-imeeting" key="kmImeetingBook.fdFinishDate" />
					</td>
					<td width="35%" >
						<xform:datetime property="fdFinishDate" dateTimeType="datetime" validators="after"></xform:datetime>
					</td>
				</tr>
				<c:if test="${ not empty kmImeetingBookForm.fdRepeatType}">
					<!-- 周期性会议设置 -->
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="km-imeeting" key="kmImeetingMain.fdRepeatType"/>
						</td>
						<td width="85%" colspan="3">
							<c:out value="${kmImeetingBookForm.fdRepeatType }"></c:out>
						</td>
					</tr>
				</c:if>
				<c:if test="${ not empty kmImeetingBookForm.fdRepeatFrequency}">
					<!-- 周期性会议设置 -->
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="km-imeeting" key="kmImeetingMain.fdRepeatFrequency"/>
						</td>
						<td width="85%" colspan="3">
							<c:out value="${kmImeetingBookForm.fdRepeatFrequency }"></c:out>
						</td>
					</tr>
				</c:if>
				<c:if test="${ not empty kmImeetingBookForm.fdRepeatTime}">
					<!-- 周期性会议设置 -->
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="km-imeeting" key="kmImeetingMain.fdRepeatTime"/>
						</td>
						<td width="85%" colspan="3">
							<c:out value="${kmImeetingBookForm.fdRepeatTime }"></c:out>
						</td>
					</tr>
				</c:if>
				<c:if test="${ not empty kmImeetingBookForm.fdRepeatUtil}">
					<!-- 周期性会议设置 -->
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="km-imeeting" key="kmImeetingMain.fdRepeatUtil"/>
						</td>
						<td width="85%" colspan="3">
							<c:out value="${kmImeetingBookForm.fdRepeatUtil }"></c:out>
						</td>
					</tr>
				</c:if>
				<%-- 所属场所 --%>
				<c:import url="/sys/authorization/sys_auth_area/sysAuthArea_field.jsp" charEncoding="UTF-8">
			            <c:param name="id" value="${kmImeetingBookForm.authAreaId}"/>
			    </c:import>
				<tr>
	     			<%--备注--%>
	              	<td width="15%" class="td_normal_title"  valign="top">
	              		<bean:message bundle="km-imeeting" key="kmImeetingBook.fdRemark" />
	              	</td>
	              	<td width="85%" colspan="3" >
	              		<xform:textarea property="fdRemark"  style="width:90%"/>
	              	</td>
	            </tr>
	            <tr>
					<td colspan="4" class="com_subject" style="font-weight: bold;"><bean:message bundle="km-imeeting" key="kmImeetingBook.fdPlace.detail" /></td>
				</tr>
				<tr>
					<%--会议地点--%>
					<td width="15%" class="td_normal_title" >
						<bean:message bundle="km-imeeting" key="kmImeetingBook.fdPlace" />
					</td>
					<td width="35%"  >
						<input type="hidden" name="fdPlaceId" value="${kmImeetingBookForm.fdPlaceId }">
						<input type="hidden" name="fdPlaceName" value="${kmImeetingBookForm.fdPlaceName }">
						<!-- 兼容下属机制做处理 -->
						<c:choose>
						    <c:when test="${not empty res}">
						    	<c:out value="${res.fdName}"></c:out>
						    </c:when>
						    <c:otherwise>
						    	<c:out value="${kmImeetingBookForm.fdPlaceName}"></c:out>
						    </c:otherwise>
						</c:choose>
					</td>
					<%--保管员--%>
					<td width="15%" class="td_normal_title" >
						<bean:message bundle="km-imeeting" key="kmImeetingRes.docKeeper" />
					</td>
					<td width="35%" >
						<!-- 兼容下属机制做处理 -->
						<c:choose>
						    <c:when test="${not empty res}">
						    	<c:out value="${res.docKeeper.fdName}"></c:out>
						    </c:when>
						    <c:otherwise>
						    	<c:out value="${kmImeetingBookForm.docKeeperName}"></c:out>
						    </c:otherwise>
						</c:choose>
						
					</td>
				</tr>
				<tr>
					<%--地点楼层--%>
					<td width="15%" class="td_normal_title" >
						<bean:message bundle="km-imeeting" key="kmImeetingRes.fdAddressFloor" />
					</td>
					<td width="35%" >
						<!-- 兼容下属机制做处理 -->
						<c:choose>
						    <c:when test="${not empty res}">
						    	<c:out value="${res.fdAddressFloor}"></c:out>
						    </c:when>
						    <c:otherwise>
						    	<c:out value="${kmImeetingBookForm.fdPlaceAddressFloor}"></c:out>
						    </c:otherwise>
						</c:choose>
					</td>
						<%--容纳人数--%>
					<td width="15%" class="td_normal_title" >
						<bean:message bundle="km-imeeting" key="kmImeetingRes.fdSeats" />
					</td>
					<td width="35%" >
						<!-- 兼容下属机制做处理 -->
						<c:choose>
						    <c:when test="${not empty res}">
						    	<c:out value="${res.fdSeats}"></c:out>
						    </c:when>
						    <c:otherwise>
						    	<c:out value="${kmImeetingBookForm.fdPlaceSeats}"></c:out>
						    </c:otherwise>
						</c:choose>
					</td>
				</tr>
				<tr>
					<%--设备详情--%>
					<td width="15%" class="td_normal_title" >
						<bean:message bundle="km-imeeting" key="kmImeetingRes.fdDetail" />
					</td>
					<td width="85%" colspan="3" >
						<!-- 兼容下属机制做处理 -->
						<c:choose>
						    <c:when test="${not empty res}">
								<xform:textarea property="fdPlaceDetail" showStatus="view" style="width:95%" />
						    </c:when>
						    <c:otherwise>
								<c:out value="${kmImeetingBookForm.fdPlaceDetail}"></c:out>
						    </c:otherwise>
						</c:choose>
					</td>
				</tr>
			</table>
		</html:form>
	</template:replace>
</template:include>
<script type="text/javascript">
	seajs.use(['sys/ui/js/dialog','lui/topic'], function(dialog,topic) {
		window.exam = function(fdHasExam){
			if(fdHasExam == '')
				fdHasExam = 'true';
			var url = '/km/imeeting/km_imeeting_book/kmImeetingBook_exam.jsp?bookId=${JsParam.fdId}&fdHasExam='+fdHasExam;
			dialog.iframe(url,'<bean:message key="kmImeetingBook.exam" bundle="km-imeeting"/>',function(value){
				if(value == 'success')
					location.reload();
			},{width:600,height:300});
		}
	});
</script>
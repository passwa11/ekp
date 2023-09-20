<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@page import="com.landray.kmss.km.imeeting.util.KmImeetingConfigUtil"%>
<% response.addHeader("X-UA-Compatible", "IE=edge"); %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<%
	 pageContext.setAttribute("_isJGEnabled", new Boolean(
			com.landray.kmss.sys.attachment.util.JgWebOffice
					.isJGEnabled()));
     pageContext.setAttribute("ImageW",312);
     pageContext.setAttribute("ImageH",210);
     pageContext.setAttribute("isBoenEnable",KmImeetingConfigUtil.isBoenEnable());
%>
<script>
	function confirmDelete(msg){
	var del = confirm('<bean:message key="page.comfirmDelete"/>');
	return del;
}
</script>
<link href="${LUI_ContextPath}/km/imeeting/resource/css/seat.css?s_cache=${LUI_Cache}" rel="stylesheet">
<div id="optBarDiv">
	<kmss:auth requestURL="/km/imeeting/km_imeeting_res/kmImeetingRes.do?method=edit&fdId=${JsParam.fdId}" requestMethod="GET">
		<input type="button"
			value="<bean:message key="button.edit"/>"
			onclick="Com_OpenWindow('kmImeetingRes.do?method=edit&fdId=${JsParam.fdId}','_self');">
	</kmss:auth>
	<kmss:auth requestURL="/km/imeeting/km_imeeting_res/kmImeetingRes.do?method=delete&fdId=${JsParam.fdId}" requestMethod="GET">
		<input type="button"
			value="<bean:message key="button.delete"/>"
			onclick="if(!confirmDelete())return;Com_OpenWindow('kmImeetingRes.do?method=delete&fdId=${JsParam.fdId}','_self');">
	</kmss:auth>
	<input type="button"
		value="<bean:message key="button.close"/>"
		onclick="Com_CloseWindow();">
</div>

<p class="txttitle"><bean:message bundle="km-imeeting" key="table.kmImeetingRes"/></p>

<center>
	<table id="Label_Tabel" width="95%" LKS_OnLabelSwitch="resize();">
		<tr LKS_LabelName="${ lfn:message('km-imeeting:py.JiBenXinXi') }">
			<td>
				<table class="tb_normal" width=100%>
					<tr>
						<%--会议室名称--%>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="km-imeeting" key="kmImeetingRes.fdName"/>
						</td>
						<td width="85%" colspan="3">
							<xform:text property="fdName" style="width:85%" />
						</td>
					</tr>
					<tr>
						<%-- 会议室图片--%>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="km-imeeting" key="kmImeetingRes.picture"/>
						</td>
						<td width="85%" colspan="3">
							<c:import url="/sys/attachment/sys_att_main/sysAttMain_view.jsp"  charEncoding="UTF-8">
								<c:param name="fdKey" value="Attachment" />
								<c:param name="fdMulti" value="false" />
								<c:param name="fdAttType" value="pic" />
								<c:param name="fdImgHtmlProperty" value="width=120" />
								<c:param name="fdModelId" value="${JsParam.fdId }" />
								<c:param name="fdModelName" value="com.landray.kmss.km.imeeting.model.KmImeetingRes" />
								<%-- 图片设定大小 --%>
								<c:param name="picWidth" value="${ImageW}" />
								<c:param name="picHeight" value="${ImageH}" />
								<c:param name="proportion" value="false" />
								<c:param name="fdLayoutType" value="pic"/>
								<c:param name="fdPicContentWidth" value="${ImageW}"/>
								<c:param name="fdPicContentHeight" value="${ImageH}"/>
								<c:param name="fdViewType" value="pic_single"/>
								
							</c:import>
						</td>
					</tr>
					<tr>
						<%--会议室分类--%>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="km-imeeting" key="kmImeetingRes.docCategory"/>
						</td>
						<td width="85%" colspan="3">
							<xform:text property="docCategoryName"  style="width:85%" showStatus="view"></xform:text>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message key="model.fdOrder" />
						</td>
							<td colspan="3">
								<xform:text property="fdOrder" style="width:85%"/>
							</td>
						</tr>
					<tr>
					<tr>
						<%--设备详情--%>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="km-imeeting" key="kmImeetingRes.fdDetail"/>
						</td>
						<td width="85%" colspan="3">
							<xform:textarea property="fdDetail" style="width:85%"></xform:textarea>
						</td>
					</tr>
					<c:if test="${isBoenEnable}">
					<!-- 是否有内屏 -->
					<tr>
				 		<td class="td_normal_title" width=15%>
				 			<bean:message bundle="km-imeeting" key="kmImeetingRes.fdInnerScreenEnable"/>
				 		</td>
				 		<td colspan="3" >
							<ui:switch property="fdInnerScreenEnable" showType="show" checked="${kmImeetingResForm.fdInnerScreenEnable}"  checkVal="true" unCheckVal="false"/>
						
						
							<table id="TABLE_DocList_Inner" tbdraggable="true" style="width:100%;margin-top:10px;">
					    		<tr>
					                <td class="td_normal_title" style="width: 50%">
					    				<bean:message bundle="km-imeeting" key="kmImeetingInnerScreen.fdName"/>
					    			</td>
					    			<td class="td_normal_title" style="width: 50%">
					    				<bean:message bundle="km-imeeting" key="kmImeetingInnerScreen.fdCode"/>
					    			</td>
					            </tr>
					            
					            <!-- 基准行 -->
								<tr KMSS_IsReferRow="1" style="display: none" align="center">
									<td >
										<xform:text property="fdInnerScreenForms[!{index}].fdName" style="width:80%" required="true"/>
									</td>
									<td>
										<xform:text property="fdInnerScreenForms[!{index}].fdCode" style="width:80%" required="true"/>
									</td>
								</tr>
								<c:forEach items="${kmImeetingResForm.fdInnerScreenForms}"  var="fdInnerScreens" varStatus="vstatus">
									<tr KMSS_IsContentRow="1">
										<td >
											<xform:text property="fdInnerScreenForms[!{index}].fdName" style="width:80%" showStatus="view" value="${fdInnerScreens.fdName}"/>
										</td>
										<td>
											<xform:text property="fdInnerScreenForms[!{index}].fdCode" style="width:80%" showStatus="view" value="${fdInnerScreens.fdCode}"/>
										</td>
									</tr>
								</c:forEach>
							</table>
						</td>
							
					 </tr>
					 	
				 	<!-- 是否有外屏 -->
				 	<tr>
				 		<td class="td_normal_title" width=15%>
				 			<bean:message bundle="km-imeeting" key="kmImeetingRes.fdOuterScreenEnable"/>
				 		</td>
				 		<td colspan="3" >
							<ui:switch property="fdOuterScreenEnable" showType="show" checked="${kmImeetingResForm.fdOuterScreenEnable}"  checkVal="true" unCheckVal="false" />
						
							<table id="TABLE_DocList_Outer" tbdraggable="true" style="width:100%;margin-top:10px;">
					    		<tr>
					                <td class="td_normal_title" style="width: 50%">
					    				<bean:message bundle="km-imeeting" key="kmImeetingOuterScreen.fdName"/>
					    			</td>
					    			<td class="td_normal_title" style="width: 50%">
					    				<bean:message bundle="km-imeeting" key="kmImeetingOuterScreen.fdCode"/>
					    			</td>
					            </tr>
					            
					            <!-- 基准行 -->
								<tr KMSS_IsReferRow="1" style="display: none" align="center">
									<td >
										<xform:text property="fdOuterScreenForms[!{index}].fdName" style="width:80%" required="true"/>
									</td>
									<td>
										<xform:text property="fdOuterScreenForms[!{index}].fdCode" style="width:80%" required="true"/>
									</td>
								</tr>
								<c:forEach items="${kmImeetingResForm.fdOuterScreenForms}"  var="fdOuterScreens" varStatus="vstatus">
									<tr KMSS_IsContentRow="1">
										<td >
											<xform:text property="fdOuterScreenForms[!{index}].fdName" style="width:80%" showStatus="view" value="${fdOuterScreens.fdName}"/>
										</td>
										<td>
											<xform:text property="fdOuterScreenForms[!{index}].fdCode" style="width:80%" showStatus="view" value="${fdOuterScreens.fdCode}"/>
										</td>
									</tr>
								</c:forEach>
							</table>
						</td>
				 	</tr>
				 	<!-- 是否开启人脸签到 -->
				 	<%-- <tr>
				 		<td class="td_normal_title" width=15%>
				 			<bean:message bundle="km-imeeting" key="kmImeetingRes.fdSignInEnable"/>
				 		</td>
				 		<td colspan="3" >
							<ui:switch property="fdSignInEnable" showType="show" checked="${kmImeetingResForm.fdSignInEnable}"  checkVal="true" unCheckVal="false" />
							<table id="signInTable" style="margin-top:10px;">
								<tr>
									<td>
										<bean:message bundle="km-imeeting" key="kmImeetingRes.fdSignInUserName"/>
									</td>
									<td>
										<xform:text property="fdSignInUserName" style="width:80%" required="true"/>
									</td>
								</tr>
								<tr>
									<td>
										<bean:message bundle="km-imeeting" key="kmImeetingRes.fdSignInPassword"/>
									</td>
									<td>
										<xform:text property="fdSignInPassword" style="width:80%" required="true"/>
									</td>
								</tr>
								<tr>
									<td>
										<bean:message bundle="km-imeeting" key="kmImeetingRes.fdSignInIp"/>
									</td>
									<td>
										<xform:text property="fdSignInIp" style="width:80%" required="true"/>
									</td>
								</tr>
								<tr>
									<td>
										<bean:message bundle="km-imeeting" key="kmImeetingRes.fdSignInPort"/>
									</td>
									<td>
										<xform:text property="fdSignInPort" style="width:80%" required="true"/>
									</td>
								</tr>
								<tr>
									<td>
										<bean:message bundle="km-imeeting" key="kmImeetingRes.fdSignInTypeCode"/>
									</td>
									<td>
										<xform:select property="fdSignInTypeCode" style="width:80%" required="true" showStatus="view">
											<xform:enumsDataSource enumsType="km_imeeting_sign_in_type_code"></xform:enumsDataSource>
										</xform:select>
									</td>
								</tr>
							</table>
						</td>
				 	</tr> --%>
				 	</c:if>
					 	
					<tr>
						<%--地点楼层--%>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="km-imeeting" key="kmImeetingRes.fdAddressFloor"/>
						</td>
						<td width="85%" colspan="3">
							<xform:text property="fdAddressFloor" style="width:85%" />
						</td>
					</tr>
					<tr>
						<%--容纳人数--%>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="km-imeeting" key="kmImeetingRes.fdSeats"/>
						</td>
						<td width="85%" colspan="3">
							<xform:text property="fdSeats" style="width:85%" />
						</td>
					</tr>
					<tr>
						<%--最大使用时长--%>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="km-imeeting" key="kmImeetingRes.fdUserTime"/>（<bean:message key="date.interval.hour"/>）
						</td>
						<td width="85%" colspan="3">
							<xform:text property="fdUserTime" style="width:85%"/>
						</td>
					</tr>
					<tr>
						<%--保管员--%>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="km-imeeting" key="kmImeetingRes.docKeeper"/>
						</td>
						<td width="85%" colspan="3">
							<xform:address propertyId="docKeeperId" propertyName="docKeeperName" orgType="ORG_TYPE_ALL" style="width:85%" />
						</td>
					</tr>
					<tr>
						<%--是否开启会议室预约审核--%>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="km-imeeting" key="kmImeetingRes.fdNeedExamFlag"/>
						</td>
						<td width="85%" colspan="3">
							<xform:radio property="fdNeedExamFlag">
								<xform:enumsDataSource enumsType="common_yesno" />
							</xform:radio>
						</td>
					</tr>
						<%-- 可维护者 --%>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message key="model.tempEditorName" />
						</td>
						<td width="85%" colspan="3">
							<c:out value="${kmImeetingResForm.authEditorNames}" />
						</td>
					</tr>
					<tr>
						<!-- 可使用者 -->
						<td class="td_normal_title" width=15%>
							<bean:message	bundle="km-imeeting" key="kmImeetingRes.authReader" />
						</td>
						<td width="85%" colspan="3">
							<c:out value="${kmImeetingResForm.authReaderNames}" />
						</td>
					</tr>
					 <%-- 所属场所 --%>
				     <c:import url="/sys/authorization/sys_auth_area/sysAuthArea_field.jsp" charEncoding="UTF-8">
				         <c:param name="id" value="${kmImeetingResForm.authAreaId}"/>
				     </c:import>  
					<tr>
						<%--是否有效--%>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="km-imeeting" key="kmImeetingRes.fdIsAvailable"/>
						</td>
						<td width="85%" colspan="3">
							<xform:radio property="fdIsAvailable">
								<xform:enumsDataSource enumsType="common_yesno" />
							</xform:radio>
						</td>
					</tr>

				</table>
			</td>
		</tr>
		
		<tr LKS_LabelName="${ lfn:message('km-imeeting:py.zuoXiSheZhi') }" >
		    <td>
		    	<div class="lui_seat_setting_wrap">
			    	<div class="lui_seat_setting_aside">
				      	<div class="lui_seat_setting_aside_inner">
					      	<div class="lui_seat_setting_aside_item">
					        	<div class="lui_seat_setting_seat_wrapper">
						        	<span class="lui_seat_setting_seat">座位数：</span>
									<c:choose>
										<c:when test="${kmImeetingResForm.fdSeatCount != null }">
											<span id="seatCount" class="lui_seat_setting_seat_number">${kmImeetingResForm.fdSeatCount}</span>
										</c:when>
										<c:otherwise>
											<span id="seatCount" class="lui_seat_setting_seat_number">0</span>
										</c:otherwise>
									</c:choose>
					        	</div>
					      	</div>
					      	<div class="lui_seat_setting_aside_item">
					          	<h3 class="lui_seat_setting_aside_item_title">会议室元素</h3>
					         	<div id="seatElement">
					          	</div>
					      	</div>
				      	</div>
				      	<div class="lui_seat_setting_aside_item_save_module" onclick="saveAsTemplate();">
				      		<p>另存为座席模板</p>
				      	</div>
			    	</div>
			    	<div id="seat" class="lui_seat_setting_content">
			    	</div>
			  	</div>
				<%@include file="/km/imeeting/km_imeeting_res/kmImeetingRes_view_js.jsp"%>
		    </td>
		</tr>
	</table>
</center>
<script>
	LUI.ready(function(){
		/* var signInValue = $("input[name='fdSignInEnable']").val();
		disableSignIn(signInValue); */
		var innerValue = $("input[name='fdInnerScreenEnable']").val();
		disableInnerScreen(innerValue);
		var outerValue = $("input[name='fdOuterScreenEnable']").val();
		disableOuterScreen(outerValue);
	});
	
	/* function disableSignIn(flag){
		if("true" == flag){
			$("#signInTable").css("display","block");
		}else{
			$("#signInTable").css("display","none");
		}
	} */
	
	function disableInnerScreen(flag){
		if("true" == flag){
			$("#TABLE_DocList_Inner").css("display","block");
		}else{
			$("#TABLE_DocList_Inner").css("display","none");
		}
	}
	
	function disableOuterScreen(flag){
		if("true" == flag){
			$("#TABLE_DocList_Outer").css("display","block");
		}else{
			$("#TABLE_DocList_Outer").css("display","none");
		}
	}
</script>
<%@ include file="/resource/jsp/view_down.jsp"%>
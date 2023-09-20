<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.landray.kmss.km.imeeting.util.KmImeetingConfigUtil"%>
<% response.addHeader("X-UA-Compatible", "IE=edge"); %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<%@ include file="/resource/jsp/edit_top.jsp" %>
<%
	 pageContext.setAttribute("_isJGEnabled", new Boolean(
			com.landray.kmss.sys.attachment.util.JgWebOffice
					.isJGEnabled()));
	 pageContext.setAttribute("isBoenEnable",KmImeetingConfigUtil.isBoenEnable());
     pageContext.setAttribute("ImageW",312);
     pageContext.setAttribute("ImageH",210);
%>
<script>
	Com_IncludeFile("dialog.js|jquery.js");
	Com_IncludeFile("doclist.js");
</script>
<script language="JavaScript">
	//防止没有选择类别而直接进入编辑页面
	var fdModelId='${JsParam.fdModelId}';
	var fdModelName='${JsParam.fdModelName}';
	var url='<c:url value="/km/imeeting/km_imeeting_res/kmImeetingRes.do" />?method=add&categoryId=!{id}&categoryName=!{name}';
	if(fdModelId!=null&&fdModelId!=''){
		url+="&fdModelId="+fdModelId+"&fdModelName="+fdModelName;
	}   
	Com_NewFileFromSimpleCateory('com.landray.kmss.km.imeeting.model.KmImeetingResCategory',url);
	$(document).ready(function (){
		var val ="${kmImeetingResForm.fdIsAvailable}";  
		if(val=='1'||val=='true'){
			document.getElementsByName("fdIsAvailable")[0].checked="checked";
		}else{
			document.getElementsByName("fdIsAvailable")[1].checked="checked";
		}
	});
</script>
<link href="${LUI_ContextPath}/km/imeeting/resource/css/seat.css?s_cache=${LUI_Cache}" rel="stylesheet">
<html:form action="/km/imeeting/km_imeeting_res/kmImeetingRes.do">
	<div id="optBarDiv">
		<c:if test="${kmImeetingResForm.method_GET=='edit'}">
			<input type=button value="<bean:message key="button.update"/>"
				onclick="commitMethod('update')">
		</c:if>
		<c:if test="${kmImeetingResForm.method_GET=='add'}">
			<input type=button value="<bean:message key="button.save"/>"
				onclick="commitMethod('save')">
			<input type=button value="<bean:message key="button.saveadd"/>"
				onclick="commitMethod('saveadd');">
		</c:if>
		<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
	</div>

	<p class="txttitle"><bean:message bundle="km-imeeting" key="table.kmImeetingRes"/></p>

	<center>
		<table id="Label_Tabel" width="95%" LKS_OnLabelSwitch="resize();">
			<tr LKS_LabelName="${ lfn:message('km-imeeting:py.JiBenXinXi') }" >
		    	<td>
		        	<table class="tb_normal" width="100%">
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
								<c:import url="/sys/attachment/sys_att_main/sysAttMain_edit.jsp"  charEncoding="UTF-8">
									<c:param name="fdKey" value="Attachment" />
									<c:param name="fdMulti" value="false" />
									<c:param name="fdAttType" value="pic" />
									<c:param name="fdImgHtmlProperty" value="width=120" />
									<c:param name="fdModelId" value="${kmImeetingResForm.fdId }" />
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
								 <bean:message bundle="km-imeeting" key="kmImeetingRes.picture.desc" />
								<font color="red">${ImageW}(<bean:message bundle="km-imeeting" key="kmImeetingRes.picture.width" />)*${ImageH}(<bean:message bundle="km-imeeting" key="kmImeetingRes.picture.height" />)</font>							
							</td>
						</tr>
						<tr>
							<%--会议室分类--%>
							<td class="td_normal_title" width=15%>
								<bean:message bundle="km-imeeting" key="kmImeetingRes.docCategory"/>
							</td>
							<td width="85%" colspan="3">
								<c:if test="${kmImeetingResForm.method_GET=='add'}">
									<html:hidden property="docCategoryId" />
									<xform:text property="docCategoryName" style="width:85%" showStatus="view"></xform:text>
								</c:if>
								<c:if test="${kmImeetingResForm.method_GET=='edit'}">
									<xform:dialog propertyId="docCategoryId" propertyName="docCategoryName" style="width:85%">
										Dialog_SimpleCategory('com.landray.kmss.km.imeeting.model.KmImeetingResCategory','docCategoryId','docCategoryName',false,null,'02');
									</xform:dialog>
								</c:if>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								<bean:message key="model.fdOrder" />
							</td>
								<td colspan="3">
									<xform:text property="fdOrder" validators="digits min(0)" style="width:85%"/>
								</td>
							</tr>
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
								<ui:switch property="fdInnerScreenEnable" showType="edit" checked="${kmImeetingResForm.fdInnerScreenEnable}"  checkVal="true" unCheckVal="false" onValueChange="changeInnerScreen()"/>
							
							
								<table id="TABLE_DocList_Inner" tbdraggable="true" style="width:100%;margin-top:10px;">
						    		<tr>
						                <td class="td_normal_title" style="width: 45%">
						    				<bean:message bundle="km-imeeting" key="kmImeetingInnerScreen.fdName"/>
						    			</td>
						    			<td class="td_normal_title" style="width: 45%">
						    				<bean:message bundle="km-imeeting" key="kmImeetingInnerScreen.fdCode"/>
						    			</td>
						    			<td class="td_normal_title" style="width: 10%">
						    				<bean:message key="list.operation"/>
						    			</td>
						            </tr>
						            
						            <!-- 基准行 -->
									<tr KMSS_IsReferRow="1" style="display: none" align="center">
										<td >
											<xform:text property="fdInnerScreenForms[!{index}].fdName" style="width:80%" required="true" validators="maxLength(100)"/>
										</td>
										<td>
											<xform:text property="fdInnerScreenForms[!{index}].fdCode" style="width:80%" required="true" validators="maxLength(50) checkUnique"/>
										</td>
										<!-- 删除、上移、下移按钮 -->
										<td align="center">
											<a href="javascript:void(0);" onclick="DocList_DeleteRow();" title="${lfn:message('doclist.delete')}">
												<img src="${KMSS_Parameter_StylePath}/icons/delete.gif" border="0" />
											</a>
										</td>
									</tr>
									<c:forEach items="${kmImeetingResForm.fdInnerScreenForms}" var="fdInnerScreens" varStatus="vstatus">
										<tr KMSS_IsContentRow="1">
											<td >
												<xform:text property="fdInnerScreenForms[${vstatus.index}].fdName" style="width:80%"  value="${fdInnerScreens.fdName}" required="true" validators="maxLength(100)"/>
											</td>
											<td>
												<xform:text property="fdInnerScreenForms[${vstatus.index}].fdCode" style="width:80%"  value="${fdInnerScreens.fdCode}" required="true" validators="maxLength(50) checkUnique"/>
											</td>
											<!-- 删除、上移、下移按钮 -->
											<td align="center">
												<a href="javascript:void(0);" onclick="DocList_DeleteRow();"><img
													src="${KMSS_Parameter_StylePath}/icons/delete.gif" border="0" /></a>
											</td>
										</tr>
									</c:forEach>
									
									<tr type="optRow" class="tr_normal_opt" invalidrow="true">
										<td colspan="9">
											<a href="javascript:void(0);" onclick="DocList_AddRow();" title="${lfn:message('doclist.add')}">
												<img src="${KMSS_Parameter_StylePath}/icons/icon_add.png" border="0" />
											</a>
										</td>
									</tr>
								</table>
							</td>
							
					 	</tr>
					 	
					 	<!-- 是否有外屏 -->
					 	<tr>
					 		<td class="td_normal_title" width=15%>
					 			<bean:message bundle="km-imeeting" key="kmImeetingRes.fdOuterScreenEnable"/>
					 		</td>
					 		<td colspan="3" >
								<ui:switch property="fdOuterScreenEnable" showType="edit" checked="${kmImeetingResForm.fdOuterScreenEnable}"  checkVal="true" unCheckVal="false" onValueChange="changeOuterScreen()"/>
							
								<table id="TABLE_DocList_Outer" tbdraggable="true" style="display:none;width:100%;margin-top:10px;">
						    		<tr>
						                <td class="td_normal_title" style="width: 45%">
						    				<bean:message bundle="km-imeeting" key="kmImeetingOuterScreen.fdName"/>
						    			</td>
						    			<td class="td_normal_title" style="width: 45%">
						    				<bean:message bundle="km-imeeting" key="kmImeetingOuterScreen.fdCode"/>
						    			</td>
						    			<td class="td_normal_title" style="width: 10%">
						    				<bean:message key="list.operation"/>
						    			</td>
						            </tr>
						            
						            <!-- 基准行 -->
									<tr KMSS_IsReferRow="1" style="display: none" align="center">
										<td >
											<xform:text property="fdOuterScreenForms[!{index}].fdName" style="width:80%" required="true" validators="maxLength(100)"/>
										</td>
										<td>
											<xform:text property="fdOuterScreenForms[!{index}].fdCode" style="width:80%" required="true" validators="maxLength(50) checkUnique"/>
										</td>
										<!-- 删除、上移、下移按钮 -->
										<td align="center">
											<a href="javascript:void(0);" onclick="DocList_DeleteRow();" title="${lfn:message('doclist.delete')}">
												<img src="${KMSS_Parameter_StylePath}/icons/delete.gif" border="0" />
											</a>
										</td>
									</tr>
									<c:forEach items="${kmImeetingResForm.fdOuterScreenForms}"  var="fdOuterScreens" varStatus="vstatus">
										<tr KMSS_IsContentRow="1">
											<td >
												<xform:text property="fdOuterScreenForms[${vstatus.index}].fdName" style="width:80%" value="${fdOuterScreens.fdName}" required="true" validators="maxLength(100)"/>
											</td>
											<td>
												<xform:text property="fdOuterScreenForms[${vstatus.index}].fdCode" style="width:80%" value="${fdOuterScreens.fdCode}" required="true" validators="maxLength(50) checkUnique"/>
											</td>
											<!-- 删除、上移、下移按钮 -->
											<td align="center">
												<a href="javascript:void(0);" onclick="DocList_DeleteRow();"><img
													src="${KMSS_Parameter_StylePath}/icons/delete.gif" border="0" /></a>
											</td>
										</tr>
									</c:forEach>
									
									<tr type="optRow" class="tr_normal_opt" invalidrow="true">
										<td colspan="9">
											<a href="javascript:void(0);" onclick="DocList_AddRow();" title="${lfn:message('doclist.add')}">
												<img src="${KMSS_Parameter_StylePath}/icons/icon_add.png" border="0" />
											</a>
										</td>
									</tr>
								</table>
							</td>
					 	</tr>
					 	<!-- 是否开启人脸签到 -->
					 	<%-- <tr>
					 		<td class="td_normal_title" width=15%>
					 			<bean:message bundle="km-imeeting" key="kmImeetingRes.fdSignInEnable"/>
					 		</td>
					 		<td colspan="3" >
								<ui:switch property="fdSignInEnable" showType="edit" checked="${kmImeetingResForm.fdSignInEnable}"  checkVal="true" unCheckVal="false" onValueChange="changeSignIn()"/>
								<table id="signInTable" style="display:none;margin-top:10px;">
									<tr>
										<td>
											<bean:message bundle="km-imeeting" key="kmImeetingRes.fdSignInUserName"/>
										</td>
										<td>
											<xform:text property="fdSignInUserName" style="width:80%" required="true" validators="maxLength(100)"/>
										</td>
									</tr>
									<tr>
										<td>
											<bean:message bundle="km-imeeting" key="kmImeetingRes.fdSignInPassword"/>
										</td>
										<td>
											<xform:text property="fdSignInPassword" style="width:80%" required="true" validators="maxLength(30)"/>
										</td>
									</tr>
									<tr>
										<td>
											<bean:message bundle="km-imeeting" key="kmImeetingRes.fdSignInIp"/>
										</td>
										<td>
											<xform:text property="fdSignInIp" style="width:80%" required="true" validators="validateIp"/>
										</td>
									</tr>
									<tr>
										<td>
											<bean:message bundle="km-imeeting" key="kmImeetingRes.fdSignInPort"/>
										</td>
										<td>
											<xform:text property="fdSignInPort" style="width:80%" required="true" validators="validatePort"/>
										</td>
									</tr>
									<tr>
										<td>
											<bean:message bundle="km-imeeting" key="kmImeetingRes.fdSignInTypeCode"/>
										</td>
										<td>
											<xform:select property="fdSignInTypeCode" style="width:80%" required="true">
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
								<xform:text property="fdAddressFloor" required="true"  style="width:85%" />
							</td>
						</tr>
						<tr>
							<%--容纳人数--%>
							<td class="td_normal_title" width=15%>
								<bean:message bundle="km-imeeting" key="kmImeetingRes.fdSeats"/>
							</td>
							<td width="85%" colspan="3">
								<xform:text property="fdSeats" style="width:85%" required="true"  validators="digits min(1)"/>
							</td>
						</tr>
						<tr>
							<%--最大使用时长--%>
							<td class="td_normal_title" width=15%>
								<bean:message bundle="km-imeeting" key="kmImeetingRes.fdUserTime"/>（<bean:message key="date.interval.hour"/>）
							</td>
							<td width="85%" colspan="3">
								<xform:text property="fdUserTime" style="width:85%" validators="number min(0)"/>
								<div class="description_txt">
									（<bean:message bundle="km-imeeting" key="kmImeetingRes.fdUserTime.tip"/>）
								</div>
							</td>
						</tr>
						<tr>
							<%--保管员--%>
							<td class="td_normal_title" width=15%>
								<bean:message bundle="km-imeeting" key="kmImeetingRes.docKeeper"/>
							</td>
							<td width="85%" colspan="3">
								<xform:address subject="${lfn:message('km-imeeting:kmImeetingRes.docKeeper')}" propertyId="docKeeperId" propertyName="docKeeperName" orgType="ORG_TYPE_POSTORPERSON" style="width:85%" />
								<span style="display: none;" class="txtstrong" id="isRequiredFlag">*</span>			
								<br>
								<input type="checkbox" name="fdNeedExamFlag" value="${kmImeetingResForm.fdNeedExamFlag}" onclick="changeValue()" <c:if test="${kmImeetingResForm.fdNeedExamFlag eq 'true'}">checked</c:if>>
								<bean:message bundle="km-imeeting" key="kmImeetingRes.fdNeedExamFlag"/>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%><bean:message
								key="model.tempEditorName" /></td>
							<td colspan="3"><xform:address textarea="true" mulSelect="true" propertyId="authEditorIds" propertyName="authEditorNames" orgType="ORG_TYPE_ALL" style="width:97%;height:90px;" ></xform:address>
							<div class="description_txt">
							<bean:message	bundle="km-imeeting" key="kmImeeting.authEditor.tip" />
							</div>
							</td>
						</tr>
						<tr>
							<!-- 可使用者 -->
							<td class="td_normal_title" width=15%>
								<bean:message bundle="km-imeeting" key="kmImeetingRes.authReader"/>
							</td>
							<td colspan="3">
								<xform:address propertyId="authReaderIds" propertyName="authReaderNames" mulSelect="true" orgType="ORG_TYPE_ALL" textarea="true" style="width:97%;height:90px;" />
								<div class="description_txt">
									<!-- <bean:message	bundle="km-imeeting" key="kmImeetingRes.authReader.tip" /> -->
									<% if (com.landray.kmss.sys.organization.util.SysOrgEcoUtil.IS_ENABLED_ECO) { %>
									<c:set var="formName" value="kmImeetingResForm" scope="request"/>
									    <% if(com.landray.kmss.sys.organization.util.SysOrgEcoUtil.isExternal()) { %>
									        <!-- （为空则本组织人员可使用） -->
									        <bean:message  bundle="km-imeeting" key="kmImeetingRes.noorganizationOrgniazation.new" arg0="${ecoName}" />
									    <% } else { %>
									        <!-- （为空则所有内部人员可使用） -->
									        <bean:message  bundle="km-imeeting" key="kmImeetingRes.noorganizationUse" />
									    <% } %>
									<% } else { %>
									    <!-- （为空则所有人可使用） -->
									    <bean:message  bundle="km-imeeting" key="kmImeetingRes.authReader.tip" />
									<% } %>
								</div>
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
			
			<tr LKS_LabelName="${ lfn:message('km-imeeting:py.zuoXiSheZhi') }">
				<html:hidden property="fdSeatDetail"/>
				<html:hidden property="fdSeatCount" value="0"/>
				<html:hidden property="fdCols"/>
				<html:hidden property="fdRows"/>
		    	<td>
		    		<table width="100%">
		    			<tr>
		    				<td>
		    					<div style="float:right;">
									<div style="vertical-align: middle;">
										<ui:toolbar id="Btntoolbar">
											<!-- 添加行-->
											<ui:button text="${lfn:message('km-imeeting:py.addRow')}" onclick="addRow()" order="1" ></ui:button>
											<!-- 添加列-->
											<ui:button text="${lfn:message('km-imeeting:py.addCol')}" onclick="addCol()" order="1" ></ui:button>
											<!-- 清空全部 -->
											<ui:button text="清空全部" order="1" onclick="window.clearAllCustomData()">
											</ui:button>
										</ui:toolbar>
									</div>
								</div>
							  	<div class="lui_seat_setting_wrap">
							    	<div class="lui_seat_setting_aside">
								      	<div class="lui_seat_setting_aside_inner">
									      	<div class="lui_seat_setting_aside_item">
										        <h3 class="lui_seat_setting_aside_item_title">座席模板</h3>
										        <xform:select property="fdSeatTemplateId" showStatus="edit" onValueChange="changeSeatTemplate(this.value);" >
			                                        <xform:beanDataSource serviceBean="kmImeetingSeatTemplateService" selectBlock="fdId,fdName" />
			                                    </xform:select>
			                                        
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
								<!-- 坐席设置 Ends -->
			    				
								<%@include file="/km/imeeting/km_imeeting_res/kmImeetingRes_edit_js.jsp"%>
		    				</td>
		    			</tr>
		    		</table>
		    	</td>
		    </tr>
		</table>
	</center>
	<html:hidden property="docStatus" value="30"/>
	<html:hidden property="fdId" />
	<html:hidden property="method_GET" />
	<script>
		var validation = $KMSSValidation(document.forms['kmImeetingResForm']);
		validation.addValidator('validateIp','请输入正确的IP',function(v,e,o){
			var reg = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/;
			return reg.test(v);
		});
		
		validation.addValidator('validatePort','请输入正确的端口',function(v,e,o){
			if (!(/^[1-9]\d*$/.test(v) && 1 <= 1 * v && 1 * v <= 65535)){
			    return false
			}
			return true;
		});
		
		validation.addValidator('checkUnique','设备编码必须唯一',function(v,e,o){
			var codeArr = new Array();
			var currentIndex = 0;
			var flag = false;
			var optTB = document.getElementById("TABLE_DocList_Inner");
			var tbInfo = DocList_TableInfo[optTB.id];
			var innerIndex = tbInfo.lastIndex;
			for(var i = 1 ;i < innerIndex ;i++){
				var value = $("input[name='fdInnerScreenForms["+(i-1)+"].fdCode']").val();
				console.log(value)
				if(value == v){
					if(currentIndex != 0){
						codeArr.push(value);
					}
					currentIndex++;
				}else{
					codeArr.push(value);
				}
			}
			optTB = document.getElementById("TABLE_DocList_Outer");
			tbInfo = DocList_TableInfo[optTB.id];
			var outerIndex = tbInfo.lastIndex;
			for(var i = 1 ;i < outerIndex ;i++){
				var value = $("input[name='fdOuterScreenForms["+(i-1)+"].fdCode']").val();
				console.log(value)
				if(value == v){
					if(currentIndex != 0){
						codeArr.push(value);
					}
					currentIndex++;
				}else{
					codeArr.push(value);
				}
			}
			console.log(codeArr);
			
			if(codeArr.indexOf(v) == -1){
				var fdId = document.getElementsByName("fdId")[0].value;
				$.ajax({
					url: Com_Parameter.ContextPath+'km/imeeting/km_imeeting_res/kmImeetingRes.do?method=checkUnique',
					type: 'POST',
					data:$.param({"fdCode":v,"resId":fdId},true),
					dataType: 'json',
					async: false,
					error: function(data){
						dialog.failure('${lfn:message("return.optFailure")}');
					},
					success: function(data){
						if(data == true){
							flag = true
						}else{
							flag= false;
						}
					}
				});
			}
					
			return flag;
		});
		
		var examCheckbox = document.getElementsByName("fdNeedExamFlag")[0];
		LUI.ready(function(){
			if(examCheckbox.value == 'true'){
				$("input[name='docKeeperName']").attr("validate","required");
				$("input[data-propertyname='docKeeperName']").attr("validate","required");
				$('#isRequiredFlag').show();
			}else{
				$("input[name='docKeeperName']").attr("validate","");
				$("input[data-propertyname='docKeeperName']").attr("validate","");
				$('#isRequiredFlag').hide();
			}
			
			<c:if test="${isBoenEnable}">
			DocList_Info.push("TABLE_DocList_Inner");
			DocList_Info.push("TABLE_DocList_Outer");
			
			/* var signInValue = $("input[name='fdSignInEnable']").val();
			disableSignIn(signInValue); */
			var innerValue = $("input[name='fdInnerScreenEnable']").val();
			disableInnerScreen(innerValue);
			var outerValue = $("input[name='fdOuterScreenEnable']").val();
			disableOuterScreen(outerValue);
			</c:if>
		});
		function changeValue(){
			if(examCheckbox.checked){
				examCheckbox.value = 'true';
				$("input[name='docKeeperName']").attr("validate","required");
				$("input[data-propertyname='docKeeperName']").attr("validate","required");
				$('#isRequiredFlag').show();
			}else{
				examCheckbox.value = 'false';
				$("input[name='docKeeperName']").attr("validate","");
				$("input[data-propertyname='docKeeperName']").attr("validate","");
				$('#isRequiredFlag').hide();
			}
		}
		<c:if test="${isBoenEnable}">
		function changeInnerScreen(){
			var value = $("input[name='fdInnerScreenEnable']").val();
			if("true" == value){
				var optTB = document.getElementById("TABLE_DocList_Inner");
				var tbInfo = DocList_TableInfo[optTB.id];
				var index = tbInfo.lastIndex;
				if(index < 2){
					DocList_AddRow("TABLE_DocList_Inner");
				}
				
				$("#TABLE_DocList_Inner").css("display","block");
				$("#TABLE_DocList_Inner").find("input").each(function() {
					$(this).attr("disabled", false);
				});
			}else{
				$("#TABLE_DocList_Inner").css("display","none");
				$("#TABLE_DocList_Inner").find("input").each(function() {
					$(this).attr("disabled", true);
				});
			}
		}
		
		function changeOuterScreen(){
			var value = $("input[name='fdOuterScreenEnable']").val();
			if("true" == value){
				var optTB = document.getElementById("TABLE_DocList_Outer");
				var tbInfo = DocList_TableInfo[optTB.id];
				var index = tbInfo.lastIndex;
				if(index < 2){
					DocList_AddRow("TABLE_DocList_Outer");
				}
				$("#TABLE_DocList_Outer").css("display","block");
				$("#TABLE_DocList_Outer").find("input").each(function() {
					$(this).attr("disabled", false);
				});
			}else{
				$("#TABLE_DocList_Outer").css("display","none");
				$("#TABLE_DocList_Outer").find("input").each(function() {
					$(this).attr("disabled", true);
				});
			}
		}
	
		
		/* function changeSignIn(){
			var value = $("input[name='fdSignInEnable']").val();
			disableSignIn(value);
		}
		
		function disableSignIn(flag){
			if("true" == flag){
				$("#signInTable").css("display","block");
				$("#signInTable").find("input").each(function() {
					$(this).attr("disabled", false);
				});
				$("#signInTable").find("select").each(function() {
					$(this).attr("disabled", false);
				});
			}else{
				$("#signInTable").css("display","none");
				$("#signInTable").find("input").each(function() {
					$(this).attr("disabled", true);
				});
				$("#signInTable").find("select").each(function() {
					$(this).attr("disabled", true);
				});
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
		</c:if>
	</script>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp"%>
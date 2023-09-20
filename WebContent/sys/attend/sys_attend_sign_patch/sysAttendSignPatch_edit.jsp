<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.edit">
	<template:replace name="title">
		${ lfn:message('sys-attend:table.sysAttendSignPatch') }
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3"> 
			<c:choose>
				<c:when test="${ sysAttendSignPatchForm.method_GET == 'edit' }">
					<%-- <ui:button text="${ lfn:message('button.update') }" onclick="doSubmit('update');"></ui:button> --%>
				</c:when>
				<c:when test="${ sysAttendSignPatchForm.method_GET == 'add' }">
				<kmss:auth requestURL="/sys/attend/sys_attend_sign_patch/sysAttendSignPatch.do?method=add&cateId=${param.cateId}">
					<ui:button text="${lfn:message('button.save') }" onclick="doSubmit('save');"></ui:button>
				</kmss:auth>
				</c:when>
			</c:choose>
			<ui:button text="${ lfn:message('button.close') }" onclick="Com_CloseWindow();"></ui:button>	
		</ui:toolbar>
	</template:replace>
	<template:replace name="head">
		<link rel="Stylesheet" href="${LUI_ContextPath}/sys/attend/resource/css/attend.css" />
		<script>
			Com_IncludeFile("doclist.js");
		</script>
	</template:replace>
	<template:replace name="content">
		<p class="txttitle" style="margin: 15px 0;">
			${ lfn:message('sys-attend:sysAttendCategory.custom') } ${ lfn:message('sys-attend:table.sysAttendSignPatch') }
		</p>
		
		<html:form action="/sys/attend/sys_attend_sign_patch/sysAttendSignPatch.do">
			<html:hidden property="fdId" />
			<html:hidden property="method_GET" />
			<html:hidden property="fdCateName" />
			<html:hidden property="fdPatchPersonId" />
			<html:hidden property="fdPatchPersonName" />
			<html:hidden property="fdPatchTime" />
			<html:hidden property="cateId" value="${param.cateId }" />
			<html:hidden property="signedIds" value="${signedIds }" />
			
			<div class="lui_form_content_frame" style="padding-top:20px">
				<table class="tb_normal" width=100%>
					<tr>
						<td width="15%" class="td_normal_title" style="text-align: center;">
							${ lfn:message('sys-attend:sysAttendCategory.custom') }
						</td>
						<td colspan="3">
							${sysAttendSignPatchForm.fdCateName }
						</td>
					</tr>
					<tr>
						<td width="15%" class="td_normal_title" style="text-align: center;">
							${ lfn:message('sys-attend:sysAttendSignPatch.fdPatchPerson') }
						</td>
						<td width="35%">
							${sysAttendSignPatchForm.fdPatchPersonName }
						</td>
						<td width="15%" class="td_normal_title" style="text-align: center;">
							${ lfn:message('sys-attend:sysAttendSignPatch.fdPatchTime') }
						</td>
						<td width="35%">
							${sysAttendSignPatchForm.fdPatchTime }
						</td>
					</tr>
					<tr>
						<td colspan="4">
							<table id="TABLE_DocList" class="tb_normal" width="100%" style="text-align: center;">
								<tr>
									<td class="td_normal_title" width="40%">
					    				${ lfn:message('sys-attend:sysAttendSignPatchDetail.fdSignPerson') }
					    			</td>
					    			<td class="td_normal_title" width="40%">
					    				${ lfn:message('sys-attend:sysAttendSignPatchDetail.fdSignTime') }
					    			</td>
					    			<td class="td_normal_title" width="20%">
					    				<div class="lui-attend-bus-setting-add" onclick="addSignRow();">
										</div>
					    			</td>
								</tr>
								<tr KMSS_IsReferRow="1" style="display:none">
									<input type="hidden" name="fdPatchDetail[!{index}].fdId" />
			    					<input type="hidden" name="fdPatchDetail[!{index}].fdPatchId" value="${sysAttendSignPatchForm.fdId }" />
									<td>
										<xform:address propertyId="fdPatchDetail[!{index}].fdSignPersonId" propertyName="fdPatchDetail[!{index}].fdSignPersonName" orgType="ORG_TYPE_PERSON" required="true" mulSelect="false" subject="${ lfn:message('sys-attend:sysAttendSignPatchDetail.fdSignPerson') }" style="width: 90%" validators="attendPerson signedPerson"></xform:address>
					    			</td>
					    			<td>
					    				<xform:datetime property="fdPatchDetail[!{index}].fdSignTime" required="true" subject="${ lfn:message('sys-attend:sysAttendSignPatchDetail.fdSignTime') }" style="width: 90%" validators="signTime"></xform:datetime>
					    			</td>
					    			<td>
					    				<div class="lui-attend-bus-setting-delete" onclick="deleteSignRow();">
										</div>
					    			</td>
								</tr>
								<c:forEach items="${sysAttendSignPatchForm.fdPatchDetail}" var="detailItem" varStatus="vstatus">
									<tr KMSS_IsContentRow="1">
										<input type="hidden" name="fdPatchDetail[${vstatus.index }].fdId" value="${detailItem.fdId }" />
				    					<input type="hidden" name="fdPatchDetail[${vstatus.index }].fdPatchId" value="${sysAttendSignPatchForm.fdId }" />
					    				<td>
					    					<xform:address propertyId="fdPatchDetail[${vstatus.index }].fdSignPersonId" propertyName="fdPatchDetail[${vstatus.index }].fdSignPersonName" orgType="ORG_TYPE_PERSON" required="true" mulSelect="false" subject="${ lfn:message('sys-attend:sysAttendSignPatchDetail.fdSignPerson') }" style="width: 90%" validators="attendPerson signedPerson"></xform:address>
						    			</td>
						    			<td>
						    				<xform:datetime property="fdPatchDetail[${vstatus.index }].fdSignTime" required="true" subject="${ lfn:message('sys-attend:sysAttendSignPatchDetail.fdSignTime') }" style="width: 90%" validators="signTime"></xform:datetime>
						    			</td>
						    			<td>
						    				<div class="lui-attend-bus-setting-delete" onclick="deleteSignRow();">
											</div>
						    			</td>
									</tr>
								</c:forEach>
							</table>
							<div class="validation-advice" id="validateErr" style="display: none">
								<table class="validation-table">
									<tbody>
										<tr>
											<td><div
													class="lui_icon_s lui_icon_s_icon_validator"></div></td>
											<td class="validation-advice-msg"><span
												class="validation-advice-title">${ lfn:message('sys-attend:sysAttendSignPatch.fdPatchDetail') }</span> ${ lfn:message('sys-attend:sysAttendSignPatch.notNull') }</td>
										</tr>
									</tbody>
								</table>
							</div>
						</td>
					</tr>
				</table>
			</div>
		</html:form>
		
		<script type="text/javascript">
		seajs.use(['lui/jquery','lui/dialog','lui/topic'], function($, dialog , topic){
			var __validation = $KMSSValidation(document.forms['sysAttendMainExcForm']);
			
			__validation.addValidator('attendPerson', '<bean:message  bundle="sys-attend" key="sysAttendSignPatch.outCustom"/>', function(value, elem){
				if(!value){
					return true;
				}
				if($(elem).attr('name').indexOf('fdSignPersonName') > -1){
					var filedName = $(elem).attr('name').replace('fdSignPersonName', 'fdSignPersonId');
					value = $('[name="'+ filedName +'"]').val();
				}
				var targetIds = '${targetIds}' || '';
				if(targetIds == 'all'){
					return true;
				} else {
					return targetIds.indexOf(value) > -1;
				}
			});
			
			__validation.addValidator('signedPerson', '<bean:message  bundle="sys-attend" key="sysAttendSignPatch.signed"/>', function(value, elem){
				if(!value){
					return true;
				}
				var signedIds = $('[name="signedIds"]:hidden').val();
				if(signedIds){
					if($(elem).attr('name').indexOf('fdSignPersonName') > -1){
						var filedName = $(elem).attr('name').replace('fdSignPersonName', 'fdSignPersonId');
						value = $('[name="'+ filedName +'"]').val();
					}
					return signedIds.indexOf(value) == -1;
				}
				return true;
			});
			
			__validation.addValidator('signTime', '<bean:message  bundle="sys-attend" key="sysAttendSignPatch.time"/>', function(value){
				var startTime = '${startTime}'
				var endTime = '${endTime}'
				if(value && startTime && endTime) {
					var date = Com_GetDate(value, 'datetime',Com_Parameter.DateTime_format);
					startObj = Com_GetDate(startTime, 'datetime', Com_Parameter.DateTime_format);
					endObj = Com_GetDate(endTime, 'datetime', Com_Parameter.DateTime_format);
					if(date && startObj && endObj) {
						return date.getTime() >= startObj.getTime() && date.getTime() <= endObj.getTime();
					}
				}
				return true;
			});
			
			window.doSubmit = function(method){
				checkValidation(function(){
					Com_Submit(document.forms['sysAttendSignPatchForm'], method);
				});
			};
			
			// 防止同一个人补签多次
			var checkValidation = function(callback){
				var signInputs = $('[name^="fdPatchDetail["][name$="].fdSignPersonId"]');
				if(!signInputs || signInputs.length==0){
					$('#validateErr').show();
					$("html,body").animate({scrollTop:$('#validateErr').offset().top - $(window).height()/2},200);
					return;
				}
				var signIds = [];
				for(var i=0; i<signInputs.length; i++){
					signIds.push(signInputs.eq(i).val());
				}
				var loading = dialog.loading();
				$.ajax({ 
					type :'post', 
					url : '${LUI_ContextPath}/sys/attend/sys_attend_sign_patch/sysAttendSignPatch.do?method=checkValidation', 
					data : $.param({'signId': signIds, 'cateId' : '${param.cateId }'},true),
					dataType : 'json', 
					success : function(data){
						loading.hide();
						if(data) {
							if(data.status == '1'){
								callback && callback();
							} else {
								var signedIds = $('[name="signedIds"]:hidden');
								signedIds.val(data.signedIds);
								__validation.validate();
							}
						}
					},
					error : function(e){
						loading.hide();
						console.error(e);
					}
				}); 
			}
			
			window.deleteSignRow = function() {
				DocList_DeleteRow();
				setTimeout(function() {
					var signInputs = $('[name^="fdPatchDetail["][name$="].fdSignPersonId"]');
					if(!signInputs || signInputs.length==0){
						$('#validateErr').show();
					}
				}, 0);
			}
			
			window.addSignRow = function(){
				DocList_AddRow('TABLE_DocList');
				$('#validateErr').hide();
			}
		});
		</script>
	</template:replace>
</template:include>
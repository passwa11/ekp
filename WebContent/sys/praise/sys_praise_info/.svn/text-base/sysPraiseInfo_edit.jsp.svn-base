<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="sysPraise.edit">
	<template:replace name="head">
		<link href="${LUI_ContextPath}/sys/praise/style/zonePage-css.css" rel="stylesheet">
		<style>
        	.tempTB{
        		width:100% !important;
        		margin : 0px !important;
        	}
        	.lui_form_body > .lui_form_path_frame{
        		padding-top: 0px !important;
        	}
        	.lui_form_body{
        		background-image: none !important;
        	}
        	.targetPerson ol.mp_list{
        		max-height: 150px !important;
        	}
        	.gotoPraise{
        		cursor: pointer;
        		margin-left:40px;
        		text-decoration:underline;
        		color:  #4285f4;
        	}
        	.praiseHeight{
        		height: 32px;
        	}
        </style>
	</template:replace>
	<template:replace name="content">
		<html:form action="/sys/praise/sys_praise_info/sysPraiseInfo.do">
		<c:set var="withoutIntegral" value="true"/>
		<kmss:ifModuleExist path="/kms/integral">
		<c:set var="withoutIntegral" value="false"/>
		</kmss:ifModuleExist>
			<table class="lui-zonePage-imforbox-body" style="width:99%;margin-top:20px;">
				<tr class="lui-zonePage-imforbox-body-row admire marBtm2 praiseInfoTr" style="display: none;">
					<td class="lui-zonePage-imforbox-name"><bean:message
							key="sysPraiseInfo.docSubject" bundle="sys-praise" />：</td>
					<td class="lui-zonePage-imforbox-text"><xform:text
							property="docSubject" 
							subject="${lfn:message('sys-praise:sysPraiseInfo.docSubject')}"></xform:text>
					</td>
				</tr>
				<tr class="lui-zonePage-imforbox-body-row marBtm2 praiseInfoTr praiseHeight">
					<td class="lui-zonePage-imforbox-name"><bean:message
							key="sysPraiseInfo.fdType" bundle="sys-praise" />：</td>
					<td>
						<label class="lui-zonePage-radio-inline"> 
							<input type="radio" name="inlineRadioOptions" id="inlineRadio1"
								value="1" onclick="resetDefaultView(this)"> 
							<img src="${LUI_ContextPath}/sys/praise/style/images/icon16.png" class="yellow"> 
							<img src="${LUI_ContextPath}/sys/praise/style/images/icon12.png" class="gray">
						</label> 
						<label class="lui-zonePage-radio-inline"> 
							<input type="radio" name="inlineRadioOptions" id="inlineRadio1"
								value="3" onclick="resetDefaultView(this)"> 
							<img src="${LUI_ContextPath}/sys/praise/style/images/icon18.png" class="yellow"> 
							<img src="${LUI_ContextPath}/sys/praise/style/images/icon17.png" class="gray">
						</label> 
						<c:forEach items="${extendTypes}" varStatus="vStatus" var="item">
								<c:import url="${item}" charEncoding="UTF-8" />
						</c:forEach>
					</td>
				</tr>
				<c:if test="${sysPraiseInfoForm.isReply == 'true'}">
					<tr class="lui-zonePage-imforbox-body-row marBtm2 praiseInfoTr">
						<td class="lui-zonePage-imforbox-name">
							<bean:message key="sysPraiseInfo.openReply" bundle="sys-praise" />：
						</td>
						<td>
							<input type="checkbox" name="isReply" checked style="width: inherit;height: inherit;">
							<bean:message key="sysPraiseInfo.open" bundle="sys-praise" />
						</td>
					</tr>
				</c:if>
				<tr class="lui-zonePage-imforbox-body-row marBtm2 targetPerson praiseInfoTr">
					<td class="lui-zonePage-imforbox-name"><bean:message
							key="sysPraiseInfo.fdTargetPerson" bundle="sys-praise" />：</td>
					<td class="lui-zonePage-imforbox-text"><c:choose>
							<c:when
								test="${empty sysPraiseInfoForm.fdTargetPersonId || 'true' eq param.editPerson}">
								<xform:address textarea="true"
									subject="${lfn:message('sys-praise:sysPraiseInfo.fdTargetPerson')}"
									mulSelect="false" propertyId="fdTargetPersonId"
									propertyName="fdTargetPersonName"
									style="width:92%;height:28px;border:1px solid #e0e0e0"
									orgType="ORG_TYPE_PERSON" required="true">
								</xform:address>
							</c:when>
							<c:otherwise>
								<c:out value="${sysPraiseInfoForm.fdTargetPersonName}"></c:out>
								<html:hidden property="fdTargetPersonId" />
								<html:hidden property="fdTargetPersonName" />
							</c:otherwise>
						</c:choose></td>
				</tr>
				
				<tr class="lui-zonePage-imforbox-body-row praiseInfoTr">
					<td class="lui-zonePage-imforbox-name textarea-name"><bean:message
							key="sysPraiseInfo.fdReason" bundle="sys-praise" />：</td>
					<td class="lui-zonePage-imforbox-text"><xform:textarea
							property="fdReason" style="white-space:normal;" required="true"></xform:textarea></td>
				</tr>
				<tr class="lui-zonePage-imforbox-body-row praiseInfoTr">
					<td class="lui-zonePage-imforbox-name textarea-name">
						<!-- sysPraiseInfo.fdNotifyType -->
						${lfn:message('sys-praise:sysPraiseInfo.fdNotifyType')}：
					</td>
					<td>
						<kmss:editNotifyType property="fdNotifyType" required="true"/>
					</td>
				</tr>
				<tr class="lui-zonePage-imforbox-body-row praiseInfoTr">
					<td class="lui-zonePage-imforbox-name">&nbsp;</td>
					<td class="lui-zonePage-imforbox-text submitTd lui_dialog_buttons clearfloat">
						<div class ="hideNameDiv">
							<input type="checkbox" name = "fdIsHideNameSetting" onclick="changeHideNameValue(this)" style="width: 15px;"/> <bean:message bundle="sys-praise" key="sysPraiseInfo.fdIsHideName.true"/>
							<input type="hidden" name="fdIsHideName" value="${sysPraiseInfoForm.fdIsHideName}"/>
						</div> 
						<ui:button  text="${lfn:message('button.submit')}" onclick="submitContentInfo()">
						</ui:button>
						<ui:button styleClass="lui-component lui_widget_btn lui_toolbar_btn_gray" onclick="closeIframe()" text="${lfn:message('button.close')}">
						</ui:button>
						<div class ="hideNameDiv" style="margin-right: 0px;">
							<a class="gotoPraise" onclick="gotoPraise()"> <bean:message bundle="sys-praise" key="sysPraise.person.my"/></a>
							<kmss:ifModuleExist path="/kms/imall/">
							    <a class="gotoPraise" onclick="gotoImall()"> <bean:message bundle="kms-imall" key="kmsImallExpenseSource.imallStore"/></a>
							</kmss:ifModuleExist>
						</div> 
					</td>
				</tr>
			</table>
			<html:hidden property="fdRich" />
			<html:hidden property="fdType" />
			<html:hidden property="fdId" />
			<html:hidden property="docCreateTime" />
			<html:hidden property="fdPraisePersonId" />
			<html:hidden property="fdPraisePersonName" />
			<html:hidden property="fdSourceTitle" />
			<html:hidden property="fdTargetId" />
			<html:hidden property="fdTargetName" />
		</html:form>
		<script>
            LUI.ready(function () {
                var fdModule = "";
                var fdTitle = "";
                var fdShowTitle = "${lfn:message('sys-praise:sysPraiseInfo.fdSource.lost')}";
                var top = Com_Parameter.top || window.top;
                var titleInfo = top.window.document.getElementsByTagName('title');
                if (titleInfo.length > 0) {
                    fdShowTitle = $.trim(titleInfo[0].innerHTML.replace(/[\r\n]/g, ""));
                    $($(".admire")[0]).find("input[name='docSubject']")[0].value = fdShowTitle;
                }
                $("input[name='fdSourceTitle']")[0].value = fdShowTitle;
                // $("textarea[name='fdReason']").val(fdShowTitle);
                changeImgView();
            });

            function resetDefaultView(obj) {
                $('.lui-zonePage-radio-inline .yellow').css({
                    'display' : 'none'
                });
                $('.lui-zonePage-radio-inline .gray').css({
                    'display' : 'inline-block'
                });
                $('.listTypeExtend').css({
                    'display' : 'none'
                });
                if (obj && obj.parentNode) {
                    $(obj.parentNode).find('.gray').css({
                        'display' : 'none'
                    });
                    $(obj.parentNode).find('.yellow').css({
                        'display' : 'inline-block'
                    });
                }

            }
            
            
            Com_Submit.ajaxSubmit = function(form) {
				var datas = $(form)
							.serializeArray();
				
				$.ajax({url : form.action,
					type : 'POST',
					dataType : 'json',
					data : $.param(datas),
					success : function(
							data,
							textStatus,
							xhr) {
						
						seajs.use(['lui/dialog'],function(dialog){
							var top = Com_Parameter.top || window.top;
		                       if(top.window.closePraise){
		                   		   dialog.success("${lfn:message('return.optSuccess')}");
		                           $dialog.hide();   
		                       }
		                   });
					}});
            }
				

            function submitContentInfo() {
            	var top = Com_Parameter.top || window.top;
                $(document.activeElement).blur();
                var docSubject = $("input[name='docSubject']")[0].value;
                top.window.closePraise = true;
                var checkRich =false;
                $("input[name='inlineRadioOptions']").each(function () {
                    if (this.checked) {
                       if(this.value == 2){
                           top.window.checkPraiseRich = true; 
                       }else{
                           top.window.checkPraiseRich = false;
                       }
                    }
                });
 
                $("input[name='inlineRadioOptions']")
                        .each(
                                function () {
                                    if (this.checked) {
                                        $("input[name = 'fdType']")[0].value = this.value;
                                        if (this.parentNode) {
                                            var fdRichSetting = $(
                                                    this.parentNode)
                                                    .find(
                                                            "input[name='fdRichSeting']");
                                            if (fdRichSetting.length > 0) {
                                                $(this.parentNode)
                                                        .find(
                                                                "input[name='fdRichSeting']")
                                                $("input[name = 'fdRich']")[0].value = fdRichSetting[0].value;
                                                checkRich =true;
                                            }
                                        }
                                        return false;
                                    }
                                });
                if(checkRich){
                    if(window.checkInteRich){
                       if(!window.checkInteRich()){
                           return;
                       }   
                    } 
                }
                
               var rtnVal = sysPraiseInfoValidation.validate();
               if(rtnVal){
                   Com_SubmitForm(document.sysPraiseInfoForm, 'save');
               }
            }

            function changeImgView() {
                var fdType = "${sysPraiseInfoForm.fdType}";
                var withoutIntegral = "${withoutIntegral}";
                if (fdType == "" || isNaN(fdType)) {
                    if(withoutIntegral&&withoutIntegral=="false"){
                        fdType = 2;
                    }else{
                        fdType = 1;
                    }
                   
                } else {
                    fdType = parseInt(fdType)
                }

                $("input[name='inlineRadioOptions']").each(function () {
                    if (this.value == fdType) {
                        this.checked = "checked";
                        $(this.parentNode).find('.gray').css({
                            'display' : 'none'
                        });
                        $(this.parentNode).find('.yellow').css({
                            'display' : 'inline-block'
                        });
                        
                        if(fdType==2){
                            $(this.parentNode).find('.listTypeExtend').css({
                                'display' : 'inline-block'
                            }); 
                        }
                        return false;
                    }
                });
            }
            
            function changeHideNameValue(obj){
                if(obj.checked){
                    $("input[name='fdIsHideName']").val('true');
                }else{
                    $("input[name='fdIsHideName']").val('false');
                }
            }
            
            function closeIframe(){
                $dialog.hide();
            }
          var sysPraiseInfoValidation = $KMSSValidation(document.forms['sysPraiseInfoForm']);
          
          function gotoPraise(){
              Com_OpenWindow("${LUI_ContextPath}/sys/praise/","_blank");
          }
          function gotoImall(){
              Com_OpenWindow("${LUI_ContextPath}/kms/imall/","_blank");
          }
        </script>
	</template:replace>
</template:include>
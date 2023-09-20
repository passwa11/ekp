<%@page import="java.util.Locale"%>
<%@page import="com.landray.kmss.sys.language.utils.SysLangUtil"%>
<%@page import="com.landray.kmss.sys.lbpmservice.support.model.LbpmUsageContent"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<style type="text/css">
    .calcBtn {
        display: block;
        margin: 0;
        padding: 5px 0;
        width: 100%;
        color: #333;
        font-size: 18px;
        font-weight: 400;
        line-height: 1.42857143;
        text-align: center;
        white-space: nowrap;
        vertical-align: middle;
        text-transform: capitalize;
        -ms-touch-action: manipulation;
        touch-action: manipulation;
        cursor: pointer;
        -webkit-user-select: none;
        -moz-user-select: none;
        -ms-user-select: none;
        user-select: none;
        background-image: none;
        background-color: #fff;
        border: 1px solid transparent;
        border-radius: 0;
        outline: 0;
        transition-duration: .3s;
        -wekbit-box-sizing: content-box;
        box-sizing: content-box;
    }
    .calcBtn:hover,
    .calcBtn:focus{
        text-decoration: none;
        color: #fff;
        background-color: #4285f4;
        border-color: #4285f4;
    }
    .calcBtn:active {
        background-image: none;
        outline: 0;
        -webkit-box-shadow: inset 0 3px 5px rgba(0, 0, 0, .15);
        box-shadow: inset 0 3px 5px rgba(0, 0, 0, .15);
    }

    .resultBtn{
        font-size: 14px;
        padding: 2px 10px;
        border-radius: 4px;
        border-color: #d2d2d2;
        margin: 0 5px;
        width: auto;
        display: inline-block;
    }

</style>


<template:include ref="config.profile.edit" sidebar="no">
    <template:replace name="content">
        <script type="text/javascript">
            Com_IncludeFile("dialog.js");
        </script>
        
        <h2 align="center" style="margin: 10px 0">
            <span class="profile_config_title"><bean:message bundle="km-review" key="kmReviewTemplate.message.batchCopyTemplate.copyTemplate"/></span>
        </h2>
        <html:form action="/km/review/km_review_template/kmReviewTemplate.do">
            <center>
                <div style="margin:auto auto 60px;">
                    <table width=95% class="tb_simple">
						 <input name="cloneModelIds" id="cloneModelIds" type="hidden"/>
                        <!-- 默认签字意见 -->
                        <tr>
                            <td class="td_normal_title" width=15% height="65px">
                                	<bean:message bundle="km-review" key="kmReviewTemplate.message.batchCopyTemplate.categories"/>
                            </td>
                            <td width=85%>
                                <html:hidden property="fdCategoryId" /> 
								<html:text property="fdCategoryName" readonly="true" styleClass="inputsgl" style="width:80%;" /> <span class="txtstrong">*</span>
								&nbsp;&nbsp;&nbsp;
								<a href="#" onclick="Dialog_Category('com.landray.kmss.km.review.model.KmReviewTemplate','fdCategoryId','fdCategoryName');">
									<bean:message bundle="km-review" key="kmReviewTemplate.message.batchCopyTemplate.select"/>
								</a>
                            </td>
                        </tr>

                        <!-- 默认审批节点唤醒意见 -->
                        <tr>
                            <td class="td_normal_title" width=15% height="25px">
                                	<bean:message bundle="km-review" key="kmReviewTemplate.message.batchCopyTemplate.userableTemplate"/>
                            </td>
                            <td width=85% >
                            	<input type="radio" value="1" name="useType" checked="checked"><bean:message bundle="km-review" key="kmReviewTemplate.message.batchCopyTemplate.originalTemplateUse"/>   <input type="radio" value="3" name="useType"><bean:message bundle="km-review" key="kmReviewTemplate.message.batchCopyTemplate.blanking"/>   <input type="radio" value="2" name="useType"><bean:message bundle="km-review" key="kmReviewTemplate.message.batchCopyTemplate.replace"/>
                            </td>
                        </tr>
                        
                        <tr>
                            <td class="td_normal_title" width=15% height="65px">&nbsp;</td>
                            <td  width=15% >
                                	<div id="useTemplateDiv" style="display:none;">
                                	<xform:address textarea="true" showStatus="edit" mulSelect="true" propertyId="authReaderIds" propertyName="authReaderNames" orgType="ORG_TYPE_ALL" style="width:81%;height:65px;" ></xform:address>
                                </div>
                            </td>
                        </tr>
                        
                          <!-- 默认审批节点暂停意见 -->
                        <tr>
                            <td class="td_normal_title" width=15% height="25px">
                                	<bean:message bundle="km-review" key="kmReviewTemplate.message.batchCopyTemplate.templateEdit"/>
                            </td>
                            <td width=85% >
                            	<input type="radio" value="1" name="editType" checked="checked"><bean:message bundle="km-review" key="kmReviewTemplate.message.batchCopyTemplate.originalTemplateEdit"/>   <input type="radio" value="3" name="editType"><bean:message bundle="km-review" key="kmReviewTemplate.message.batchCopyTemplate.blanking"/>   <input type="radio" value="2" name="editType"><bean:message bundle="km-review" key="kmReviewTemplate.message.batchCopyTemplate.replace"/>
                            </td>
                        </tr>
                        
                        <tr>
                            <td class="td_normal_title" width=15% height="85px">&nbsp;</td>
                            <td  width=15%  >
                                <div id="editTemplateDiv" style="display:none;">
                                	<xform:address textarea="true" showStatus="edit" mulSelect="true" propertyId="authEditorIds" propertyName="authEditorNames" orgType="ORG_TYPE_ALL" style="width:81%;height:65px;" ></xform:address>
								</div>
                            </td>
                        </tr>
                        
                         <!-- 确定 -->
                        <!-- <tr>
                            <td  colspan="2" align="center" >
                                <input class="calcBtn resultBtn" type=button value="<bean:message key="button.ok"/>" onclick="Com_Submit(document.kmReviewTemplateForm, 'batchClone');">
                                <input class="calcBtn resultBtn" type="button" value="<bean:message key="button.cancel"/>" onClick="window.close();">
                            </td>
                        </tr> -->
                        
                    </table>
                </div>
            </center>
        </html:form>
        
		<html:javascript formName="kmReviewTemplateForm"  cdata="false"
      dynamicJavascript="true" staticJavascript="false"/>
      

        <script type="text/javascript">

        seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
        	 
        	 Com_Parameter.event["submit"].push(function(){
         		var fdCategoryId = document.getElementsByName('fdCategoryId')[0].value;
         		if (null == fdCategoryId||fdCategoryId=="") {
         			dialog.alert('<bean:message bundle="km-review" key="kmReviewTemplate.message.batchCopyTemplate.selectCategories"/>');
         			return false;
         		}		
         		return true;
         	});
        });
        
        
       
        
        

            LUI.ready(function(){
            	
            	$("input[name=editType]").click(function(){
            		var value0 = $(this).val();
            		if(value0 != null&&value0==2) {
            			$('#editTemplateDiv').show();
            		}
            		else {
            			$('#editTemplateDiv').hide();
            		}
            	});
       
            	$("input[name=useType]").click(function(){
            		var value0 = $(this).val();
            		if(value0 != null&&value0==2) {
            			$('#useTemplateDiv').show();
            		}
            		else {
            			$('#useTemplateDiv').hide();
            		}
            	});
            });


        </script>
    </template:replace>
</template:include>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/sys/modeling/base/modeling_head.jsp" %>
<template:include ref="config.edit" sidebar="no">
    <template:replace name="head">
        <link type="text/css" rel="stylesheet"
              href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/override.css?s_cache=${LUI_Cache}"/>
        <script>
            Com_IncludeFile("view.css", "${LUI_ContextPath}/sys/modeling/base/resources/css/", "css", true);
            Com_IncludeFile("dialog.css", "${LUI_ContextPath}/sys/modeling/base/resources/css/", "css", true);
        </script>
        <style>
            .lui_custom_list_boxs {
                border-top: 1px solid #d5d5d5;
                position: fixed;
                bottom: 0;
                width: 100%;
                background-color: #fff;
                z-index: 1000;
                height: 63px;
            }
        </style>
    </template:replace>
    <template:replace name="content">
        <center>
            <form>
                <table class="tb_simple model-view-panel-table" style="margin-top:10px;margin-bottom: 65px;" width=95%>
                    <tr>
                        <td class="td_normal_title" width=15%>
							<span class="title_wrap">
							${lfn:message('sys-modeling-base:modelingAppNav.docSubject')}:
							</span>
						</td>
                        <td width=85% class='model-view-panel-table-td'>
							<div class="inputContainer" style="width:100%">
								<xform:text property="docSubject" required="true" style="width:96%"/>
							</div>
						</td>
                    </tr>
                    <tr>
                       <td class="td_normal_title" width=15%>
							<span class="title_wrap">
							${lfn:message('sys-modeling-base:modelingAppNav.fdOrder')}:
							</span>
						</td>
                       <td width=85% class='model-view-panel-table-td'>
							<div class="inputContainer" style="width:100%">
								<xform:text property="fdOrder" validators="digits"  style="width:96%"/>
							</div>
						</td>
                    </tr>
                     <tr>
                       <!-- 可访问者 -->
						<td class="td_normal_title" width=16%>
							${lfn:message('sys-modeling-base:modelingAppNav.fdAuthReaders')}:
						</td>
                       <td width=85% class='model-view-panel-table-td'>
							<xform:address textarea="true" mulSelect="true" propertyId="authReaderIds" propertyName="authReaderNames" orgType="ORG_TYPE_ALL" style="width:97%;height:90px;" >
							</xform:address>
							<div style="color: #999999;">${lfn:message('sys-modeling-base:modeling.empty.everyone.access')}</div>
						</td>
                    </tr>
                </table>
                <div class="lui_custom_list_boxs" style="margin-top:20px">
                    <center>
                        <div class="lui_custom_list_box_content_col_btn" style="text-align: right;width: 95%">
	                        <c:if test="${modelingAppNavForm.method_GET=='edit'}">
	                        	<ui:button styleClass="lui_custom_list_box_content_blue_btn" onclick="submit('update');" text="${lfn:message('sys-modeling-base:modeling.update.now')}">
	                        	</ui:button>
	                        </c:if>
	                        <c:if test="${modelingAppNavForm.method_GET=='add'}">
	                        	<ui:button styleClass="lui_custom_list_box_content_blue_btn" onclick="submit('save');" text="${lfn:message('sys-modeling-base:modelingApplication.createNow')}">
	                        	</ui:button>
	                        </c:if>
                        	<ui:button styleClass="lui_custom_list_box_content_whith_btn" onclick="cancle();" text="${ lfn:message('button.cancel') }">
                        	</ui:button>
                        </div>
                    </center>
                </div>
                <html:hidden property="fdId"/>
	            <html:hidden property="fdNavContent"/>
                <html:hidden property="docCreatorId"/>
				<html:hidden property="docCreateTime"/>
				<html:hidden property="fdApplicationId"/>
				<html:hidden property="fdAppId" value="${param.fdAppId }"/>
				<html:hidden property="fdNavVersion"/>
		        <html:hidden property="s_path" value="${param.s_path }"/>
		        <input type="hidden" name="isReturnJson" value="true"/>
		        <input type="hidden" name="fdAppName" value="${applicationName}"/>
            </form>
        </center>
        <script>
            var _validation=$KMSSValidation();

            seajs.use(["lui/jquery", "lui/dialog",'lui/topic'], function ($, dialog,topic) {
                window.submit = function (method) {
					if (!_validation.validate()) {
						return
					}
                	<c:if test="${modelingAppNavForm.method_GET=='edit'}">
                		var url = Com_Parameter.ContextPath + "sys/modeling/base/modelingAppNav.do?method=update";
                	</c:if>
                	<c:if test="${modelingAppNavForm.method_GET=='add'}">
                		var url = Com_Parameter.ContextPath + "sys/modeling/base/modelingAppNav.do?method=save";
	            	</c:if>
						$.ajax({
							url: url,
							type: "post",
							data: $('form').serialize(),
							success: function (rtn) {
								if (rtn.status === '11') {
									var docSubject = $("[name='docSubject']").val();
									var fdOrder = $("[name='fdOrder']").val();
									var authReaderIds = $("[name='authReaderIds']").val();
									var authReaderNames = $("[name='authReaderNames']").val();
									$dialog.hide({type: 'success', fdId:rtn.fdId, data:{'docSubject':docSubject,'fdOrder':fdOrder,'authReaderIds':authReaderIds,'authReaderNames':authReaderNames}});
								}else{
									dialog.failure(rtn.errmsg);
								}
							}
						});
                }
            });

            function cancle() {
                $dialog.hide({type: 'cancle'});
            }
        </script>
    </template:replace>
</template:include>
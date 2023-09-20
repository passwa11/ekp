<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<template:include ref="config.edit" sidebar="no">
    <template:replace name="head">
    	<script>
    	Com_IncludeFile("data.js");
    	Com_IncludeFile("approval_dialog.css", "${LUI_ContextPath}/sys/lbpmservice/support/lbpm_summary_approval/config/css/", "css", true);
    	Com_IncludeFile("approval_dialog.js", "${LUI_ContextPath}/sys/lbpmservice/support/lbpm_summary_approval/config/js/", "js", true);
    	</script>
    </template:replace>
    <template:replace name="content">
         <div class='content'>
         	<div class='process_info' id='processInfo' style="display: none">
         		<div>审批信息</div>
         		<div id='processName' class="process_name"></div>
         	</div>
         	<div class='common_usage'>
         		<div>常用意见</div>
         		<div><select name="commonUsages" onchange="changeUsageContent(this);"></select></div>
         	</div>
         	<div class='usage_content'>
         		<textarea name="fdUsageContent" subject="处理意见"></textarea>
         		<span id="mustSignStar" class="txtstrong" style="display: none">*</span>
         	</div>
         </div>
        <div class="lui_custom_list_boxs" style="margin-top:20px">
             <center>
                 <div class="lui_custom_list_box_content_col_btn" style="text-align: right;width: 95%">
                 	<ui:button styleClass="lui_custom_list_box_content_blue_btn" onclick="submit('save');" text="${ lfn:message('button.submit') }">
                 	</ui:button>
                 	<ui:button styleClass="lui_custom_list_box_content_whith_btn" onclick="cancle();" text="${ lfn:message('button.cancel') }">
                 	</ui:button>
                 </div>
             </center>
         </div>
        <script>
            var _validation=$KMSSValidation();

            seajs.use(["lui/jquery", "lui/dialog"], function ($, dialog) {
            	window.beforeSubmit = function(){
            		//校验必填和长度
            		var value = $("[name='fdUsageContent']").val();
            		if(window.requiredUsageContent && !value){
            			alert("请填写处理意见！");
            			return false;
            		}
            		var newvalue = value.replace(/[^\x00-\xff]/g, "***");
    				if(newvalue.length > 4000){
    					alert("处理意见 超出 4000 个英文字符限制！");
    					return false;
    				}
    				return true;
            	}
                window.submit = function () {
                    if (!beforeSubmit()) {
                        return
                    }
                    var processId = $dialog['___params'].processId;
                    var type = $dialog['___params'].type;
                    window.handleProcess(processId,type,parent.isMulti);
                }

                window.handleProcess = function(processId,opType,isMutil){
        			var self = this;
        			var params = {
        				"processId" : processId,
        				"opType" : opType,
        				"usageContent":$("[name='fdUsageContent']").val() || ""
        			};
        			var url = Com_Parameter.ContextPath + "sys/lbpmservice/support/actions/LbpmSummaryApproval.do?method=approveOne";
        			if(isMutil){
        				url = Com_Parameter.ContextPath + "sys/lbpmservice/support/actions/LbpmSummaryApproval.do?method=approveMany";
        			}
        			//Ajax请求后台计算决策节点的分支
        			$.ajax({
        				type : 'post',
        				async : false, //指定是否异步处理
        				data : params,
        				dataType : "json",
        				url : url,
        				success : function(data) {
        					seajs.use("lui/dialog" , function(dialog) {
        						var code = data.code;
        						if(code == 1){//处理成功
        							dialog.success('操作成功!',null,function(){
        								$dialog.hide({type: 'ok'});
        							},null,null,{
        								topWin:window,
        								autoCloseTimeout:1
        							});
        						}else if(code == 2){
        							dialog.success('有部分流程审批失败，请查看列表!',null,function(){
        								$dialog.hide({type: 'ok'});
        							},null,null,{
        								topWin:window,
        								autoCloseTimeout:1
        							});
        						}else{//处理失败
        							dialog.failure('操作失败，请检查流程是否异常！',null,null,null,null,{
        								topWin:window,
        								autoCloseTimeout:1
        							});
        						}
        				  	});
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
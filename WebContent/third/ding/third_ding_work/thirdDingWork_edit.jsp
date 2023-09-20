<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.third.ding.util.ThirdDingUtil" %>
    
        <% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser());
        pageContext.setAttribute("currentPerson", UserUtil.getKMSSUser().getUserId());
        pageContext.setAttribute("currentPost", UserUtil.getKMSSUser().getPostIds());
        pageContext.setAttribute("currentDept", UserUtil.getKMSSUser().getDeptId());
        if(UserUtil.getUser().getFdParentOrg() != null) {
            pageContext.setAttribute("currentOrg", UserUtil.getUser().getFdParentOrg().getFdId());
        } else {
            pageContext.setAttribute("currentOrg", "");
        } %>
    
    <template:include ref="config.edit">
        <template:replace name="head">
            <style type="text/css">
                
                		.lui_paragraph_title{
                			font-size: 15px;
                			color: #15a4fa;
                	    	padding: 15px 0px 5px 0px;
                		}
                		.lui_paragraph_title span{
                			display: inline-block;
                			margin: -2px 5px 0px 0px;
                		}
                		.inputsgl[readonly], .tb_normal .inputsgl[readonly] {
                		             		   
                		}
                		
            </style>
            <script type="text/javascript">
             
                var formInitData = {

                };
                var messageInfo = {

                };

                var initData = {
                    contextPath: '${LUI_ContextPath}'
                };
                Com_IncludeFile("security.js");
                Com_IncludeFile("domain.js");
                Com_IncludeFile("form.js");
                Com_IncludeFile("form_option.js", "${LUI_ContextPath}/third/ding/third_ding_work/", 'js', true);
                Com_IncludeFile("main_edit.js", "${LUI_ContextPath}/third/ding/resource/js/", 'js', true);
                Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
           
	Com_IncludeFile("calendar.js|dialog.js|doclist.js|jquery.js|json2.js");
	//选择模块
	function selectModule(){
		Dialog_List(true, "fdUrlPrefix", "fdModelName", null, "dingModuleSelectDialog",afterModuleSelect,null,null,null,
				"${lfn:message('third-ding:thirdDingWork.select.model')}");
	}
	function afterModuleSelect(dataObj){
		console.log(dataObj);
		$KMSSValidation().validateElement($("input[name='fdModelName']")[0]);		
		if(dataObj==null)
			return ;
		var rtnData = dataObj.GetHashMapArray();
		if(rtnData[0]==null)
			return;
		if(rtnData[0]["countUrl"]!=null)
			$('input[name="fdCountUrl"]').val(rtnData[0]["countUrl"]);
		else
			$('input[name="fdCountUrl"]').val('');
		for(var key in rtnData[0]){
			if(key.indexOf('dynamicMap_') > -1){
				var name = key.replace('_','(').replace('_',')'),
					element = $('[name="'+ name +'"]');
				if(element && element.length > 0){
					element.val( rtnData[0][key]);
				}
			}
		}
	}
	
	function failTip(err) {
 		seajs.use( [ 'sys/ui/js/dialog' ], function(dialog) {
 			dialog.failure("其他应用已存在模块【"+err+"】 不能重复配置", '.lui_accordionpanel_float_contents',
 					function(value, dialog) {
 					});
 		});
 	}
	
</script>
        </template:replace>

        <template:replace name="title">
            <c:choose>
                <c:when test="${thirdDingWorkForm.method_GET == 'add' }">
                    <c:out value="${ lfn:message('operation.create') } - ${ lfn:message('third-ding:table.thirdDingWork') }" />
                </c:when>
                <c:otherwise>
                    <c:out value="${thirdDingWorkForm.fdName} - " />
                    <c:out value="${ lfn:message('third-ding:table.thirdDingWork') }" />
                </c:otherwise>
            </c:choose>
        </template:replace>
        <template:replace name="toolbar">
            <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
                <c:choose>
                    <c:when test="${ thirdDingWorkForm.method_GET == 'edit' }">
                        <ui:button text="${ lfn:message('button.update') }" onclick="if(validateDetail()){Com_Submit(document.thirdDingWorkForm, 'update');}" />
                    </c:when>
                    <c:when test="${ thirdDingWorkForm.method_GET == 'add' }">
                        <ui:button text="${ lfn:message('button.save') }" onclick="if(validateDetail()){Com_Submit(document.thirdDingWorkForm, 'save');}" />
                    </c:when>
                </c:choose>

                <ui:button text="${ lfn:message('button.close') }" order="5" onclick="Com_CloseWindow();" />
            </ui:toolbar>
        </template:replace>
       
        <template:replace name="content">
            <html:form action="/third/ding/third_ding_work/thirdDingWork.do">

                    <ui:content title="${ lfn:message('third-ding:') }" expand="true">
                        <div class='lui_form_title_frame'>
                            <div class='lui_form_subject'>
                         		     ${lfn:message('third-ding:thirdDingWork.create.app')}
                            </div>
                            <div class='lui_form_baseinfo'>

                            </div>
                        </div>
                        <table class="tb_normal" width="100%">
                            <tr>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('third-ding:thirdDingWork.fdName')}
                                </td>
                                <td colspan="3" width="85.0%">
                                    <%-- 应用名称--%>
                                    <div id="_xform_fdName" _xform_type="text">
                                        <xform:text property="fdName" showStatus="edit" style="width:95%;" />
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('third-ding:thirdDingWork.fdAgentid')}
                                </td>
                                <td colspan="3" width="85.0%">
                                    <%-- AgentId--%>
                                    <div id="_xform_fdAgentid" _xform_type="text">
                                        <xform:text property="fdAgentid" showStatus="edit" style="width:95%;" required="true" />
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('third-ding:thirdDingWork.fdAppKey')}
                                </td>
                                <td colspan="3" width="85.0%">
                                    <%-- AppKey--%>
                                    <div id="_xform_fdAppKey" _xform_type="text">
                                        <xform:text property="fdAppKey" showStatus="edit" style="width:95%;" required="true"/>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('third-ding:thirdDingWork.fdSecret')}
                                </td>
                                <td colspan="3" width="85.0%">
                                    <%-- AppSecret--%>
                                    <div id="_xform_fdSecret" _xform_type="text">
                                        <xform:text property="fdSecret" showStatus="edit" style="width:95%;" required="true"/>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('third-ding:thirdDingWork.fdModelName')}
                                </td>
                                <td colspan="3" width="85.0%">
                                    <%-- 推送消息的模块--%>
                                    <div id="_xform_fdModelName" _xform_type="text" onclick="selectModule();return false;">
                                        <xform:text property="fdModelName" style="width:50%" subject="${lfn:message('third-ding:thirdDingWork.some.app')}" validators="checkRepeat"/>
										<xform:text property="fdUrlPrefix" style="display: none;"/>
										<a href="#"><bean:message key="dialog.selectOther" /></a>
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </ui:content>
               
                <html:hidden property="fdId" />


                <html:hidden property="method_GET" />
              
            </html:form>
           <script type="text/javascript">
          //自定义校验器
        	var validation = $KMSSValidation();
        	validation.addValidator('checkRepeat','{name}'+" ${lfn:message('third-ding:thirdDingWork.exit.other.app')}",function(v, e, o){
        		//alert(111);
        	//	$("input[name='fdModelName']").attr("subject","test");
	        	var data = $("input[name='fdUrlPrefix']").val();
				var name = $("input[name='fdModelName']").val();
				var method = '${thirdDingWorkForm.method_GET}';
				var fdId = '${thirdDingWorkForm.fdId}';
				
				console.log("method:"+method);
				console.log("fdId:"+fdId);
				var isFlag = true;
				$.ajaxSettings.async = false;
				$.post('${LUI_ContextPath}/third/ding/third_ding_work/thirdDingWork.do?method=checkModel',{
		        	'data': data,
		        	'fdModelName': name,
		        	'addOrUpdate': method,
		        	'fdId': fdId
		        },function(result){
		        	console.log(result);
		        	var data = $.parseJSON(result);
					if(data.error == 0){
					}else{
						$("input[name='fdModelName']").attr("subject",data.error);
						isFlag = false;
					}
		        }); 
				$.ajaxSettings.async = true;				
        		return isFlag;
        	});
            </script>
        </template:replace>


    </template:include>
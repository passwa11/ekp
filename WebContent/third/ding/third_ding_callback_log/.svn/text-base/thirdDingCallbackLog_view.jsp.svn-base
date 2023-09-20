<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/resource/jsp/view_top.jsp" %>
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
      		border: 0px;
      		color: #868686
    	}
    
</style>
<script type="text/javascript">
    if("${thirdDingCallbackLogForm.fdEventType}" != "") {
        window.document.title = "${thirdDingCallbackLogForm.fdEventType} - ${ lfn:message('third-ding:table.thirdDingCallbackLog') }";
    }
    Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
 
		 function callbackAgain(){
      			var url ="${LUI_ContextPath}/third/ding/third_ding_callback_log/thirdDingCallbackLog.do?method=callbackAgain&fdId=${param.fdId}";       			
      			$.ajax({
						url : url,
						type : 'post',
						async : false,
						dataType : "json",
						success : function(data) {
							
						} ,
						error : function(req) {
							
						}
				});
       	 }
	
</script>
<div id="optBarDiv">

  <%--   <kmss:auth requestURL="/third/ding/third_ding_callback_log/thirdDingCallbackLog.do?method=edit&fdId=${param.fdId}">
        <input type="button" value="${lfn:message('button.edit')}" onclick="Com_OpenWindow('thirdDingCallbackLog.do?method=edit&fdId=${param.fdId}','_self');" order="2" />
    </kmss:auth> --%>
    <c:if test="${thirdDingCallbackLogForm.fdIsSuccess == false}">
    <input type="button" value="重新执行" onclick="callbackAgain()" />
    </c:if>
    <kmss:auth requestURL="/third/ding/third_ding_callback_log/thirdDingCallbackLog.do?method=delete&fdId=${param.fdId}">
        <input type="button" value="${lfn:message('button.delete')}" onclick="if(!confirmDelete())return;Com_OpenWindow('thirdDingCallbackLog.do?method=delete&fdId=${param.fdId}','_self');" />
    </kmss:auth>
    <input type="button" value="${lfn:message('button.close')}" onclick="Com_CloseWindow();" />
</div>
<p class="txttitle">${ lfn:message('third-ding:table.thirdDingCallbackLog') }</p>
<center>

    <div style="width:95%;">
        <table class="tb_normal" width="100%">
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ding:thirdDingCallbackLog.fdEventType')}
                </td>
                <td width="35%">
                    <%-- 事件类型--%>
                    <div id="_xform_fdEventType" _xform_type="text">
                        <xform:text property="fdEventType" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                 <td class="td_normal_title" width="15%">
                    事件说明：
                </td>
                <td width="35%">
                    <%-- 创建时间--%>
                    <div id="_xform_docCreateTime" _xform_type="datetime">
                        <xform:text property="fdEventTypeTip" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ding:thirdDingCallbackLog.docContent')}
                </td>
                <td colspan="3" width="85%" style="word-break: break-word">
                    <%-- 回调内容--%>
                     <xform:text property="docContent" showStatus="view"/>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ding:thirdDingCallbackLog.fdIsSuccess')}
                </td>
                <td width="35%">
                    <%-- 回调是否成功--%>
                    <div id="_xform_fdIsSuccess" _xform_type="radio">
                    	<c:if test="${thirdDingCallbackLogForm.fdIsSuccess == true}"> 成功</c:if>
                    	<c:if test="${thirdDingCallbackLogForm.fdIsSuccess == false}"> 失败</c:if>
                    
                       <%--  <xform:radio property="fdIsSuccess" htmlElementProperties="id='fdIsSuccess'" showStatus="view">
                            <xform:enumsDataSource enumsType="common_yesno" />
                        </xform:radio> --%>
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ding:thirdDingCallbackLog.docCreateTime')}
                </td>
                <td width="35%">
                    <%-- 创建时间--%>
                    <div id="_xform_docCreateTime" _xform_type="datetime">
                        <xform:datetime property="docCreateTime" showStatus="view" dateTimeType="datetime" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <c:if test="${thirdDingCallbackLogForm.fdIsSuccess == false}">
            <tr>
				<td class="td_normal_title" width=15%>
					失败原因
					</td><td colspan=3>
						<div id="_xform_docContent" _xform_type="rtf">
                        <xform:rtf property="fdErrorInfo" showStatus="view" width="95%" />
                    	</div>
				</td>
			</tr>
			</c:if>
        </table>
    </div>
</center>
<script>
    var formInitData = {

    };

    function confirmDelete(msg) {
        return confirm('${ lfn:message("page.comfirmDelete") }');
    }

    function openWindowViaDynamicForm(popurl, params, target) {
        var form = document.createElement('form');
        if(form) {
            try {
                target = !target ? '_blank' : target;
                form.style = "display:none;";
                form.method = 'post';
                form.action = popurl;
                form.target = target;
                if(params) {
                    for(var key in params) {
                        var
                        v = params[key];
                        var vt = typeof
                        v;
                        var hdn = document.createElement('input');
                        hdn.type = 'hidden';
                        hdn.name = key;
                        if(vt == 'string' || vt == 'boolean' || vt == 'number') {
                            hdn.value =
                            v +'';
                        } else {
                            if($.isArray(
                                v)) {
                                hdn.value =
                                v.join(';');
                            } else {
                                hdn.value = toString(
                                    v);
                            }
                        }
                        form.appendChild(hdn);
                    }
                }
                document.body.appendChild(form);
                form.submit();
            } finally {
                document.body.removeChild(form);
            }
        }
    }

    function doCustomOpt(fdId, optCode) {
        if(!fdId || !optCode) {
            return;
        }

        if(viewOption.customOpts && viewOption.customOpts[optCode]) {
            var param = {
                "List_Selected_Count": 1
            };
            var argsObject = viewOption.customOpts[optCode];
            if(argsObject.popup == 'true') {
                var popurl = viewOption.contextPath + argsObject.popupUrl + '&fdId=' + fdId;
                for(var arg in argsObject) {
                    param[arg] = argsObject[arg];
                }
                openWindowViaDynamicForm(popurl, param, '_self');
                return;
            }
            var optAction = viewOption.contextPath + viewOption.basePath + '?method=' + optCode + '&fdId=' + fdId;
            Com_OpenWindow(optAction, '_self');
        }
    }
    window.doCustomOpt = doCustomOpt;
    var viewOption = {
        contextPath: '${LUI_ContextPath}',
        basePath: '/third/ding/third_ding_callback_log/thirdDingCallbackLog.do',
        customOpts: {

            ____fork__: 0
        }
    };
    Com_IncludeFile("security.js");
    Com_IncludeFile("domain.js");
</script>
<%@ include file="/resource/jsp/view_down.jsp" %>
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
    if("${thirdDingDtaskForm.fdName}" != "") {
        window.document.title = "${thirdDingDtaskForm.fdName} - ${ lfn:message('third-ding:table.thirdDingDtask') }";
    }
    Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
</script>
<div id="optBarDiv">

   <%--  <kmss:auth requestURL="/third/ding/third_ding_dtask/thirdDingDtask.do?method=edit&fdId=${param.fdId}">
        <input type="button" value="${lfn:message('button.edit')}" onclick="Com_OpenWindow('thirdDingDtask.do?method=edit&fdId=${param.fdId}','_self');" order="2" />
    </kmss:auth>
    <kmss:auth requestURL="/third/ding/third_ding_dtask/thirdDingDtask.do?method=delete&fdId=${param.fdId}">
        <input type="button" value="${lfn:message('button.delete')}" onclick="if(!confirmDelete())return;Com_OpenWindow('thirdDingDtask.do?method=delete&fdId=${param.fdId}','_self');" />
    </kmss:auth> --%>
    <c:if test="${thirdDingDtaskForm.fdStatus=='10' or thirdDingDtaskForm.fdStatus=='11' or thirdDingDtaskForm.fdStatus=='20' or thirdDingDtaskForm.fdStatus=='21'}">
   		<kmss:auth requestURL="/third/ding/third_ding_dtask/thirdDingDtask.do?method=edit&fdId=${param.fdId}">
        	<input type="button" value="${lfn:message('third-ding:enums.status.opr')}" onclick="sendOpr('${param.fdId}');" order="2" />
    	</kmss:auth>
    </c:if>
    
    <input type="button" value="${lfn:message('button.close')}" onclick="Com_CloseWindow();" />
</div>
<p class="txttitle">${ lfn:message('third-ding:table.thirdDingDtask') }</p>
<center>

    <div style="width:95%;">
        <table class="tb_normal" width="100%">
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ding:thirdDingDtask.fdName')}
                </td>
                <td colspan="3" width="85.0%">
                    <%-- 名称--%>
                    <div id="_xform_fdName" _xform_type="text">
                        <xform:text property="fdName" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ding:thirdDingDtask.fdStatus')}
                </td>
                <td width="35%">
                    <%-- 发送状态--%>
                    <div id="_xform_fdStatus" _xform_type="radio">
                        <xform:radio property="fdStatus" htmlElementProperties="id='fdStatus'" showStatus="view">
                            <xform:enumsDataSource enumsType="third_ding_status" />
                        </xform:radio>
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ding:thirdDingDtask.fdInstance')}
                </td>
                <td width="35%">
                    <%-- 所属待办实例--%>
                    <div id="_xform_fdInstanceId" _xform_type="select">
                        <xform:text property="fdInstanceName" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ding:thirdDingDtask.fdDingUserId')}
                </td>
                <td width="35%">
                    <%-- 钉钉接收人--%>
                    <div id="_xform_fdDingUserId" _xform_type="text">
                        <xform:text property="fdDingUserId" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ding:thirdDingDtask.fdEkpUser')}
                </td>
                <td width="35%">
                    <%-- EKP人员--%>
                    <div id="_xform_fdEkpUserId" _xform_type="address">
                        <xform:address propertyId="fdEkpUserId" propertyName="fdEkpUserName" orgType="ORG_TYPE_PERSON" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ding:thirdDingDtask.fdTaskId')}
                </td>
                <td width="35%">
                    <%-- 任务Id--%>
                    <div id="_xform_fdTaskId" _xform_type="text">
                        <xform:text property="fdTaskId" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ding:thirdDingDtask.fdEkpTaskId')}
                </td>
                <td width="35%">
                    <%-- EKP任务Id--%>
                    <div id="_xform_fdEkpTaskId" _xform_type="text">
                        <xform:text property="fdEkpTaskId" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ding:thirdDingDtask.fdUrl')}
                </td>
                <td width="35%">
                    <%-- 任务地址--%>
                    <div id="_xform_fdUrl" _xform_type="text">
                        <xform:text property="fdUrl" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ding:thirdDingDtask.docCreateTime')}
                </td>
                <td width="35%">
                    <%-- 创建时间--%>
                    <div id="_xform_docCreateTime" _xform_type="datetime">
                        <xform:datetime property="docCreateTime" showStatus="view" dateTimeType="datetime" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ding:thirdDingDtask.fdDesc')}
                </td>
                <td colspan="3" width="85.0%">
                    <%-- 发送详情--%>
                    <div id="_xform_fdDesc" _xform_type="textarea">
                        <xform:textarea property="fdDesc" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
        </table>
    </div>
</center>
<script>
	function sendOpr(id){
		var url = '<c:url value="/third/ding/third_ding_dtask/thirdDingDtask.do?method=updateSend"/>&fdId='+id;
		var r = confirm("${lfn:message('third-ding:enums.status.opr.send')}");
		if(r == true){
			$.ajax({
				url: url,
				type: 'POST',
				data:{fdId:id},
				dataType: 'json',
				error: function(data){
				},
				success: function(data){
					if(data.error=='1'){
						alert(data.msg);
					}else{
						window.location.reload();
					}
				}
		   });
		};
	};
	
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
        basePath: '/third/ding/third_ding_dtask/thirdDingDtask.do',
        customOpts: {

            ____fork__: 0
        }
    };
    Com_IncludeFile("security.js");
    Com_IncludeFile("domain.js");
</script>
<%@ include file="/resource/jsp/view_down.jsp" %>
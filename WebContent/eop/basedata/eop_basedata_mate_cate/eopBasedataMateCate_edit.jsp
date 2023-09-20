<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/sys/ui/jsp/jshead.jsp" %>
<%@ include file="/resource/jsp/edit_top.jsp" %>
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
    if("${eopBasedataMateCateForm.method_GET}" == 'edit') {
        window.document.title = "${ lfn:message('button.edit') } - ${ lfn:message('eop-basedata:table.eopBasedataMateCate') }";
    }
    if("${eopBasedataMateCateForm.method_GET}" == 'add') {
        window.document.title = "${ lfn:message('button.add') } - ${ lfn:message('eop-basedata:table.eopBasedataMateCate') }";
    }
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
    Com_IncludeFile("form_option.js", "${LUI_ContextPath}/eop/basedata/eop_basedata_mate_cate/", 'js', true);
    Com_IncludeFile("config_edit.js", "${LUI_ContextPath}/eop/basedata/resource/js/", 'js', true);
    Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
    Com_IncludeFile("data.js");
    //提交校验
    function checkCodeOrName(flag){
        var fdName=document.getElementsByName("fdName")[0].value ;
        var fdCode=document.getElementsByName("fdCode")[0].value ;
        if(validateDetail()){
            fdName = encodeURIComponent(fdName);//防止url存在敏感字符冒号、正斜杠、问号和井号等
            var url="eopBasedataMateCateService&fdName="+fdName;
            if(fdCode != "" && fdCode != null){
                fdCode = encodeURIComponent(fdCode);
                url += "&fdCode="+fdCode;
            }
            var data = new KMSSData();
            try{
                var isExist =data.AddBeanData(url).GetHashMapArray()[0];
                if(isExist["key0"]=='false'){
                    Com_Submit(document.eopBasedataMateCateForm, flag);
                }else{
                    seajs.use([ 'lui/dialog'], function(dialog) {
                        dialog.alert("${lfn:message('eop-basedata:msg.hasExis')}");
                    })
                    return false;
                }
            }catch (e) {
                seajs.use([ 'lui/dialog'], function(dialog) {
                    dialog.alert("${lfn:message('eop-basedata:noHtml.tip')}");
                })
                return false;
            }
        }
    }
    
    function checkUpdate(flag){
    	var fdName=document.getElementsByName("fdName")[0].value ;
        var fdCode=document.getElementsByName("fdCode")[0].value ;
        var fdId = document.getElementsByName("fdId")[0].value ;
        if(validateDetail()){
        	$.ajax({
                url: '${LUI_ContextPath}' + '/eop/basedata/eop_basedata_mate_cate/eopBasedataMateCate.do' + '?method=isSetup',
                data: {fdId:fdId,fdName:fdName,fdCode:fdCode},
                dataType: 'json',
                type: 'POST',
                success: function(data) {
                     if(data.isSetup){
                    	 seajs.use([ 'lui/dialog'], function(dialog) {
                             dialog.alert("${lfn:message('eop-basedata:error.nameAndCodeAlreadySetupException')}");
                         })
                         return false;
                     }else{
                    	 Com_Submit(document.eopBasedataMateCateForm, flag);
                     }
                }
            });    
        	
        }
    }
</script>

    <% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser()); %>

<html:form action="/eop/basedata/eop_basedata_mate_cate/eopBasedataMateCate.do">
    <div id="optBarDiv">
        <c:choose>
            <c:when test="${eopBasedataMateCateForm.method_GET=='edit'}">
            	<input type="button" value="${ lfn:message('button.update') }" onclick="checkUpdate('update');">
            </c:when>
            <c:when test="${eopBasedataMateCateForm.method_GET=='add'}">
                <input type="button" value="${ lfn:message('button.save') }" onclick="checkCodeOrName('save');">
            </c:when>
        </c:choose>
        <input type="button" value="${ lfn:message('button.close') }" onclick="Com_CloseWindow();">
    </div>
    <p class="txttitle">${ lfn:message('eop-basedata:table.eopBasedataMateCate') }</p>
    <center>

        <div style="width:95%;">
            <table class="tb_normal" width="100%">
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataMateCate.fdName')}
                    </td>
                    <td width="35%">
                        <%-- 类别名称--%>
                        <div id="_xform_fdName" _xform_type="text">
                            <xform:text property="fdName" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                            ${lfn:message('eop-basedata:eopBasedataMateCate.fdCode')}
                    </td>
                    <td width="35%">
                            <%-- 类别编码--%>
                        <div id="_xform_fdCode" _xform_type="text">
                            <xform:text property="fdCode" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataMateCate.fdParent')}
                    </td>
                    <td width="35%">
                        <%-- 父类别名称--%>
                        <div id="_xform_fdParentId" _xform_type="dialog">
                            <xform:dialog propertyId="fdParentId" propertyName="fdParentName" showStatus="edit" style="width:95%;">
                                dialogSelect(false,'eop_basedata_mate_cate_materialCategory','fdParentId','fdParentName');
                            </xform:dialog>
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataMateCate.fdOrder')}
                    </td>
                    <td width="35%">
                        <%-- 排序号--%>
                        <div id="_xform_fdOrder" _xform_type="text">
                            <xform:text property="fdOrder" showStatus="edit" validators=" digits" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataMateCate.fdStatus')}
                    </td>
                    <td width="35%">
                        <%-- 状态--%>
                        <div id="_xform_fdStatus" _xform_type="radio">
                            <xform:radio property="fdStatus" htmlElementProperties="id='fdStatus'" showStatus="edit" validators=" digits">
                                <xform:enumsDataSource enumsType="eop_basedata_mate_status" />
                            </xform:radio>
                        </div>
                    </td>
                    <td colspan="2" width="50.0%">
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataMateCate.fdDesc')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <%-- 类别描述--%>
                        <div id="_xform_fdDesc" _xform_type="textarea">
                            <xform:textarea property="fdDesc" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataMateCate.docCreator')}
                    </td>
                    <td width="35%">
                        <%-- 最近更新人--%>
                        <div id="_xform_docCreatorId" _xform_type="address">
                            <ui:person personId="${eopBasedataMateCateForm.docCreatorId}" personName="${eopBasedataMateCateForm.docCreatorName}" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataMateCate.docCreateTime')}
                    </td>
                    <td width="35%">
                        <%-- 最近更新时间--%>
                        <div id="_xform_docCreateTime" _xform_type="datetime">
                            <xform:datetime property="docCreateTime" showStatus="view" dateTimeType="datetime" style="width:95%;" />
                        </div>
                    </td>
                </tr>
            </table>
        </div>
    </center>
    <html:hidden property="fdId" />
    <html:hidden property="method_GET" />
    <script>
        $KMSSValidation();
        function check(){
            var parentId = $("input[name='fdParentId']").val();
            if(parentId != ""&&parentId == '${eopBasedataMateCateForm.fdId}'){
                alert("<bean:message bundle="sys-simplecategory" key="error.illegalSelected" />");
                return false;
            }else{
                return true;
            }
        }
        Com_Parameter.event["submit"][Com_Parameter.event["submit"].length] = check;
    </script>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp" %>

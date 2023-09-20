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
    if("${eopBasedataSupGradeForm.method_GET}" == 'edit') {
        window.document.title = "${ lfn:message('button.edit') } - ${ lfn:message('eop-basedata:table.eopBasedataSupGrade') }";
    }
    if("${eopBasedataSupGradeForm.method_GET}" == 'add') {
        window.document.title = "${ lfn:message('button.add') } - ${ lfn:message('eop-basedata:table.eopBasedataSupGrade') }";
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
    Com_IncludeFile("form_option.js", "${LUI_ContextPath}/eop/basedata/eop_basedata_sup_grade/", 'js', true);
    Com_IncludeFile("config_edit.js", "${LUI_ContextPath}/eop/basedata/resource/js/", 'js', true);
    Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
    Com_IncludeFile("data.js");
    
    //提交校验
    function checkCodeOrName(flag){
        var fdName=document.getElementsByName("fdName")[0].value ;
    	var fdCode=document.getElementsByName("fdCode")[0].value ;
    	if(validateDetail()){
    		fdName = encodeURIComponent(fdName);//防止url存在敏感字符冒号、正斜杠、问号和井号等
    		var url="eopBasedataSupGradeService&fdName="+fdName;
    		if(fdCode != "" && fdCode != null){
    			fdCode = encodeURIComponent(fdCode);
    			url += "&fdCode="+fdCode;
    		}
    		var data = new KMSSData();
            try{
                var isExist =data.AddBeanData(url).GetHashMapArray()[0];
                if(isExist["key0"]=='false'){
                	Com_Submit(document.eopBasedataSupGradeForm, flag);
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
</script>

    <% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser()); %>

<html:form action="/eop/basedata/eop_basedata_sup_grade/eopBasedataSupGrade.do">
    <div id="optBarDiv">
        <c:choose>
            <c:when test="${eopBasedataSupGradeForm.method_GET=='edit'}">
                <input type="button" value="${ lfn:message('button.update') }" onclick="if(validateDetail()){Com_Submit(document.eopBasedataSupGradeForm, 'update');}">
            </c:when>
            <c:when test="${eopBasedataSupGradeForm.method_GET=='add'}">
                <input type="button" value="${ lfn:message('button.save') }" onclick="if(validateDetail()){Com_Submit(document.eopBasedataSupGradeForm, 'save');}">
            </c:when>
        </c:choose>
        <input type="button" value="${ lfn:message('button.close') }" onclick="Com_CloseWindow();">
    </div>
    <p class="txttitle">${ lfn:message('eop-basedata:table.eopBasedataSupGrade') }</p>
    <center>

        <div style="width:95%;">
            <table class="tb_normal" width="100%">
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataSupGrade.fdName')}
                    </td>
                    <td width="35%">
                        <%-- 供应商等级名称--%>
                        <div id="_xform_fdName" _xform_type="text">
                            <xform:text property="fdName" validators="checkUnique" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                            ${lfn:message('eop-basedata:eopBasedataSupGrade.fdCode')}
                    </td>
                    <td width="35%">
                            <%-- 供应商等级编码--%>
                        <c:choose>
                            <c:when test="${eopBasedataSupGradeForm.method_GET=='add'}">
                                <div id="_xform_fdCode" _xform_type="text">
                                    <xform:text property="fdCode" showStatus="view" style="width:95%;" />
                                </div>
                                <bean:message bundle="eop-basedata" key="generate.onsubmit"/>
                            </c:when>
                            <c:otherwise>
                                <div id="_xform_fdCode" _xform_type="text">
                                    <xform:text property="fdCode" showStatus="view" style="width:95%;" />
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataSupGrade.fdOrder')}
                    </td>
                    <td width="35%">
                        <%-- 排序号--%>
                        <div id="_xform_fdOrder" _xform_type="text">
                            <xform:text property="fdOrder" showStatus="edit" validators=" digits" style="width:95%;" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataSupGrade.fdStatus')}
                    </td>
                    <td width="35%">
                        <%-- 状态--%>
                        <div id="_xform_fdStatus" _xform_type="radio">
                            <xform:radio property="fdStatus" htmlElementProperties="id='fdStatus'" showStatus="edit" validators=" digits">
                                <xform:enumsDataSource enumsType="eop_basedata_mate_status" />
                            </xform:radio>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataSupGrade.docCreator')}
                    </td>
                    <td width="35%">
                        <%-- 最近更新人--%>
                        <div id="_xform_docCreatorId" _xform_type="address">
                            <ui:person personId="${eopBasedataSupGradeForm.docCreatorId}" personName="${eopBasedataSupGradeForm.docCreatorName}" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataSupGrade.docCreateTime')}
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
        var _validation = $KMSSValidation();
        _validation.addValidator("checkUnique", "供应商等级名称已存在！", function (v, e, o) {
            var flag = false;
            var data = {
                fdId: '${param.fdId}',
                fdName: $('[name=fdName]').val()
            };
            $.ajax({
                url: '${LUI_ContextPath}/eop/basedata/eop_basedata_sup_grade/eopBasedataSupGrade.do?method=checkUnique',
                data: data,
                dataType: 'json',
                type: 'POST',
                async: false,
                success: function(data) {
                    if(data.check){
                        flag = true;
                    }
                }
            });
            return flag;
        });
    </script>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp" %>

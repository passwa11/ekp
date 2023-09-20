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
    if("${eopBasedataCurrencyForm.method_GET}" == 'edit') {
        window.document.title = "${ lfn:message('button.edit') } - ${ lfn:message('eop-basedata:table.eopBasedataCurrency') }";
    }
    if("${eopBasedataCurrencyForm.method_GET}" == 'add') {
        window.document.title = "${ lfn:message('button.add') } - ${ lfn:message('eop-basedata:table.eopBasedataCurrency') }";
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
    Com_IncludeFile("form_option.js", "${LUI_ContextPath}/eop/basedata/eop_basedata_currency/", 'js', true);
    Com_IncludeFile("config_edit.js", "${LUI_ContextPath}/eop/basedata/resource/js/", 'js', true);
    Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
    Com_IncludeFile("data.js");
    //提交校验
    function checkCodeOrName(flag){
        var fdName=document.getElementsByName("fdName")[0].value ;
        var fdCode=document.getElementsByName("fdCode")[0].value ;
        if(validateDetail()){
            fdName = encodeURIComponent(fdName);//防止url存在敏感字符冒号、正斜杠、问号和井号等
            var url="eopBasedataCurrencyService&fdName="+fdName;
            if(fdCode != "" && fdCode != null){
                fdCode = encodeURIComponent(fdCode);
                url += "&fdCode="+fdCode;
            }
            var data = new KMSSData();
            try{
                var isExist =data.AddBeanData(url).GetHashMapArray()[0];
                if(isExist["key0"]=='false'){
                    Com_Submit(document.eopBasedataCurrencyForm, flag);
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

<html:form action="/eop/basedata/eop_basedata_currency/eopBasedataCurrency.do">
    <div id="optBarDiv">
        <c:choose>
            <c:when test="${eopBasedataCurrencyForm.method_GET=='edit'}">
                <input type="button" value="${ lfn:message('button.update') }" onclick="if(validateDetail()){Com_Submit(document.eopBasedataCurrencyForm, 'update');}">
            </c:when>
            <c:when test="${eopBasedataCurrencyForm.method_GET=='add'}">
                <input type="button" value="${ lfn:message('button.save') }" onclick="checkCodeOrName('save');">
            </c:when>
        </c:choose>
        <input type="button" value="${ lfn:message('button.close') }" onclick="Com_CloseWindow();">
    </div>
    <p class="txttitle">${ lfn:message('eop-basedata:table.eopBasedataCurrency') }</p>
    <center>

        <div style="width:95%;">
            <table class="tb_normal" width="100%">
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataCurrency.fdCountry')}
                    </td>
                    <td width="35%">
                        <%-- 国家/地区--%>
                        <div id="_xform_fdCountry" _xform_type="text">
                            <xform:text property="fdCountry" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                            ${lfn:message('eop-basedata:eopBasedataCurrency.fdAbbreviation')}
                    </td>
                    <td width="35%">
                            <%-- 货币简称--%>
                        <div id="_xform_fdAbbreviation" _xform_type="text">
                            <xform:text property="fdAbbreviation" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataCurrency.fdName')}
                    </td>
                    <td width="35%">
                        <%-- 货币中文名称--%>
                        <div id="_xform_fdName" _xform_type="text">
                            <xform:text property="fdName" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataCurrency.fdEnglishName')}
                    </td>
                    <td width="35%">
                        <%-- 货币英文名称--%>
                        <div id="_xform_fdEnglishName" _xform_type="text">
                            <xform:text property="fdEnglishName" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                            ${lfn:message('eop-basedata:eopBasedataCurrency.fdCode')}
                    </td>
                    <td width="35%">
                            <%-- 货币编码--%>
                        <div id="_xform_fdCode" _xform_type="text">
                            <xform:text property="fdCode" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataCurrency.fdSymbol')}
                    </td>
                    <td width="35%">
                        <%-- 货币符号--%>
                        <div id="_xform_fdSymbol" _xform_type="radio">
                            <xform:text property="fdSymbol" validators="maxLen(20)" showStatus="edit" />
                        </div>
                    </td>
                </tr>
                <tr>
                	<td class="td_normal_title" width="15%">
	                    ${lfn:message('eop-basedata:eopBasedataCurrency.fdStatus')}
	                </td>
                	<td width="35%">
                        <%-- 状态--%>
                        <div id="_xform_fdStatus" _xform_type="radio">
                            <xform:radio property="fdStatus" htmlElementProperties="id='fdStatus'" showStatus="edit" validators=" digits">
                                <xform:enumsDataSource enumsType="eop_basedata_mate_status" />
                            </xform:radio>
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataCurrency.fdOrder')}
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
                        ${lfn:message('eop-basedata:eopBasedataCurrency.docCreator')}
                    </td>
                    <td width="35%">
                        <%-- 最近更新人--%>
                        <div id="_xform_docCreatorId" _xform_type="address">
                            <ui:person personId="${eopBasedataCurrencyForm.docCreatorId}" personName="${eopBasedataCurrencyForm.docCreatorName}" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataCurrency.docCreateTime')}
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
    </script>
    <kmss:ifModuleExist path="/fssc/common">
    	<script>
    	 LUI.ready(function() {
             //费控设置代码非必填
             setTimeout(function(){
            	 $("input[name='fdCode']").attr("validate","maxLength(200)");
            	 $("#_xform_fdCode").find(".txtstrong").hide();
             },100);
         });
    	</script>
    </kmss:ifModuleExist>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp" %>

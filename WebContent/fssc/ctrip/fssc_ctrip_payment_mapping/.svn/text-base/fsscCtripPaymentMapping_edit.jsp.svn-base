<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
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
	Com_IncludeFile("form_option.js", "${LUI_ContextPath}/fssc/ctrip/fssc_ctrip_payment_mapping/", 'js', true);
	Com_IncludeFile("main_edit.js", "${LUI_ContextPath}/fssc/ctrip/resource/js/", 'js', true);
	Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
</script>

    <% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser()); %>

<html:form action="/fssc/ctrip/fssc_ctrip_payment_mapping/fsscCtripPaymentMapping.do">
    <div id="optBarDiv">
        <c:choose>
            <c:when test="${fsscCtripPaymentMappingForm.method_GET=='edit'}">
                <input type="button" value="${ lfn:message('button.update') }" onclick="if(validateDetail()){Com_Submit(document.fsscCtripPaymentMappingForm, 'update');}">
            </c:when>
            <c:when test="${fsscCtripPaymentMappingForm.method_GET=='add'}">
                <input type="button" value="${ lfn:message('button.save') }" onclick="if(validateDetail()){Com_Submit(document.fsscCtripPaymentMappingForm, 'save');}">
            </c:when>
        </c:choose>
        <input type="button" value="${ lfn:message('button.close') }" onclick="Com_CloseWindow();">
    </div>
    <p class="txttitle">${lfn:message('fssc-ctrip:table.fsscCtripPaymentMapping')}</p>
    <center>

        <div style="width:95%;">
              <table class="tb_normal" width="100%">
                  <tr>
                      <td class="td_normal_title" width="15%">
                          ${lfn:message('fssc-ctrip:fsscCtripPaymentMapping.fdSupplierCode')}
                      </td>
                      <td colspan="3" width="85.0%">
                          <%-- 携程供应商编码--%>
                          <div id="_xform_fdSupplierCode" _xform_type="text">
                              <xform:text property="fdSupplierCode" showStatus="edit" style="width:95%;" />
                              <br><span class="com_help">${lfn:message('fssc-ctrip:create.payment.supplier.tip')}</span>
                          </div>
                      </td>
                  </tr>
                  <tr>
                      <td class="td_normal_title" width="15%">
                          ${lfn:message('fssc-ctrip:fsscCtripPaymentMapping.docCreator')}
                      </td>
                      <td width="35%">
                          <%-- 创建人--%>
                          <div id="_xform_docCreatorId" _xform_type="address">
                              <ui:person personId="${fsscCtripPaymentMappingForm.docCreatorId}" personName="${fsscCtripPaymentMappingForm.docCreatorName}" />
                          </div>
                      </td>
                      <td class="td_normal_title" width="15%">
                          ${lfn:message('fssc-ctrip:fsscCtripPaymentMapping.docCreateTime')}
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
                          ${lfn:message('fssc-ctrip:fsscCtripPaymentMapping.docAlteror')}
                      </td>
                      <td width="35%">
                          <%-- 修改人--%>
                          <div id="_xform_docAlterorId" _xform_type="address">
                              <ui:person personId="${fsscCtripPaymentMappingForm.docAlterorId}" personName="${fsscCtripPaymentMappingForm.docAlterorName}" />
                          </div>
                      </td>
                      <td class="td_normal_title" width="15%">
                          ${lfn:message('fssc-ctrip:fsscCtripPaymentMapping.docAlterTime')}
                      </td>
                      <td width="35%">
                          <%-- 更新时间--%>
                          <div id="_xform_docAlterTime" _xform_type="datetime">
                              <xform:datetime property="docAlterTime" showStatus="view" dateTimeType="datetime" style="width:95%;" />
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
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp" %>

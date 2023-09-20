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
    	.lui_icon_l{
    		width:48px;
    		height:48px;
    	}
    	.lui_icon_l img{
    		width:100%;
    		height:100%;
    	}
    
</style>
<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/common.css?s_cache=${LUI_Cache }"/>
<script type="text/javascript">
    if("${fsscMobileLinkForm.method_GET}" == 'edit') {
        window.document.title = "${ lfn:message('button.edit') } - ${ lfn:message('fssc-mobile:table.fsscMobileLink') }";
    }
    if("${fsscMobileLinkForm.method_GET}" == 'add') {
        window.document.title = "${ lfn:message('button.add') } - ${ lfn:message('fssc-mobile:table.fsscMobileLink') }";
    }
    var formInitData = {

    };
    var messageInfo = {

        'fdLinkDetail': '${lfn:escapeJs(lfn:message("fssc-mobile:table.fsscMobileLinkDetail"))}'
    };
    var initData = {
        contextPath: '${LUI_ContextPath}',
    };
    Com_IncludeFile("security.js");
    Com_IncludeFile("domain.js");
    Com_IncludeFile("form.js");
    Com_IncludeFile("form_option.js", "${LUI_ContextPath}/fssc/mobile/fssc_mobile_link/", 'js', true);
    Com_IncludeFile("config_edit.js", "${LUI_ContextPath}/fssc/mobile/resource/js/", 'js', true);
    Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
</script>

    <% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser()); %>

<html:form action="/fssc/mobile/fssc_mobile_link/fsscMobileLink.do">
    <div id="optBarDiv">
        <c:choose>
            <c:when test="${methodGet=='edit'}">
                <input type="button" value="${ lfn:message('button.update') }" onclick="if(validateDetail()){Com_Submit(document.fsscMobileLinkForm, 'update');}">
            </c:when>
            <c:when test="${methodGet=='add'}">
                <input type="button" value="${ lfn:message('button.save') }" onclick="if(validateDetail()){Com_Submit(document.fsscMobileLinkForm, 'save');}">
            </c:when>
        </c:choose>
        <input type="button" value="${ lfn:message('button.close') }" onclick="Com_CloseWindow();">
    </div>
    <c:if test="${param.fdType=='1' }">
    		<p class="txttitle">${lfn:message('fssc-mobile:fsscMobileLink.fdModuleLink')}</p>
    </c:if>
    <c:if test="${param.fdType=='2' }">
    		<p class="txttitle">${lfn:message('fssc-mobile:fsscMobileLink.fdPersonLink')}</p>
    </c:if>
    <c:if test="${param.fdType=='3' }">
    		<p class="txttitle">${lfn:message('fssc-mobile:fsscMobileLink.fdOthersLink')}</p>
    </c:if>
    <center>
<template:include ref="default.edit" width="95%" sidebar="no">
        <div style="width:80%;">
            <table class="tb_normal" width="100%">
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-mobile:fsscMobileLink.fdName')}
                    </td>
                    <td width="35%">
                        <%-- 名称--%>
                        <div id="_xform_fdName" _xform_type="text">
                        	<xform:text property="fdType"  showStatus="hidden" style="width:95%;"  />
                            <xform:text property="fdName" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-mobile:fsscMobileLink.fdIsAvailable')}
                    </td>
                    <td width="35%">
                        <%-- 是否有效--%>
                        <div id="_xform_fdIsAvailable" _xform_type="radio">
                            <xform:radio property="fdIsAvailable" htmlElementProperties="id='fdIsAvailable'" showStatus="edit">
                                <xform:enumsDataSource enumsType="common_yesno" />
                            </xform:radio>
                        </div>
                    </td>
                </tr>
           
           		<tr>
           		<td colspan="4">
		            <table class="tb_normal" width="100%" id="TABLE_DocList_fdLinkDetail_Form" align="center" tbdraggable="true">
		                <tr align="center" class="tr_normal_title">
		                    <td style="width:20px;"></td>
		                    <td style="width:40px;">
		                        ${lfn:message('page.serial')}
		                    </td>
		                    <td style="width:200px;">
		                        ${lfn:message('fssc-mobile:fsscMobileLinkDetail.fdName')}
		                    </td>
		                    <td>
		                        ${lfn:message('fssc-mobile:fsscMobileLinkDetail.fdUrl')}
		                    </td>
		                    <td  style="width:150px;">
		                        ${lfn:message('fssc-mobile:fsscMobileLinkDetail.fdIcon')}
		                    </td>
		                    <td style="width:200px;">
		                    	<c:if test="${fsscMobileLinkForm.fdType =='1'}">
		                    	<a href="javascript:;" class="com_btn_link" onclick="SysLinksDialog();"><bean:message bundle="sys-person" key="sysPersonSysNavLink.fromSys"/></a>
		                    	</c:if>
		                    	<c:if test="${fsscMobileLinkForm.fdType =='2'}">
		                    	<a href="javascript:;" class="com_btn_link" onclick="SysLinksCategoryDialog();"><bean:message bundle="sys-person" key="sysPersonSysNavLink.category"/></a>
			                  	</c:if>
			                    <c:if test="${fsscMobileLinkForm.fdType =='3'}">
			                    	<a href="javascript:void(0);"  class="com_btn_link" onclick="DocList_AddRow();"> ${lfn:message('doclist.add')}</a>
			                    </c:if>
		                    </td>
		                </tr>
		                <tr KMSS_IsReferRow="1" style="display:none;" class="docListTr">
		                    <td class="docList" align="center">
		                        <input type='checkbox' name='DocList_Selected' />
		                    </td>
		                    <td class="docList" align="center" KMSS_IsRowIndex="1">
		                        !{index}
		                    </td>
		                    <td class="docList" align="center">
		                        <%-- 名称--%>
		                        <input type="hidden" name="fdLinkDetail_Form[!{index}].docMainId" value="" disabled="true" />
		                        <input type="hidden" name="fdLinkDetail_Form[!{index}].fdId" value="" disabled="true" />
		                        <div id="_xform_fdLinkDetail_Form[!{index}].fdName" _xform_type="text">
		                            <xform:text property="fdLinkDetail_Form[!{index}].fdName" showStatus="edit" required="true" subject="${lfn:message('fssc-mobile:fsscMobileLinkDetail.fdName')}" validators=" maxLength(200)" style="width:95%;" />
		                        </div>
		                    </td>
		                    <td class="docList" align="center">
		                        <%-- 连接地址--%>
		                        <div id="_xform_fdLinkDetail_Form[!{index}].fdUrl" _xform_type="text">
		                            <xform:text property="fdLinkDetail_Form[!{index}].fdUrl" showStatus="edit" style="width:95%;" />
		                        </div>
		                    </td>
		                     <td class="docList" align="center">
                                <div class="lui_icon_l" style="cursor: pointer;" title="${lfn:message('sys-person:sysPersonSysNavLink.selectIconTitle') }">
									<div class="icon_bussinessTrip" style="background-color: #C78700;" onclick="SysIconDialog(this);">
										<img src="${LUI_ContextPath }/fssc/mobile/resource/images/icon/ifsdmobile021.png"/>
									</div>
									<input type="hidden" name="fdLinkDetail_Form[!{index}].fdIcon" value="ifsdmobile021.png">
								</div>
		                     </td>
		                    <td class="docList" align="center">
		                        <a href="javascript:void(0);" onclick="DocList_CopyRow();" title="${lfn:message('doclist.copy')}">
		                            <img src="${KMSS_Parameter_StylePath}icons/icon_copy.png" border="0" />
		                        </a>
		                        &nbsp;
		                        <a href="javascript:void(0);" onclick="DocList_DeleteRow();" title="${lfn:message('doclist.delete')}">
		                            <img src="${KMSS_Parameter_StylePath}icons/icon_del.png" border="0" />
		                        </a>
		                    </td>
		                </tr>
		                <c:forEach items="${fsscMobileLinkForm.fdLinkDetail_Form}" var="fdLinkDetail_FormItem" varStatus="vstatus">
		                    <tr KMSS_IsContentRow="1" class="docListTr">
		                        <td class="docList" align="center">
		                            <input type="checkbox" name="DocList_Selected" />
		                        </td>
		                        <td class="docList" align="center">
		                            ${vstatus.index+1}
		                        </td>
		                        <td class="docList" align="center">
		                            <%-- 名称--%>
		                            <input type="hidden" name="fdLinkDetail_Form[${vstatus.index}].docMainId" value="${fdLinkDetail_FormItem.docMainId}" disabled="true" />
		                            <input type="hidden" name="fdLinkDetail_Form[${vstatus.index}].fdId" value="${fdLinkDetail_FormItem.fdId}" />
		                            <div id="_xform_fdLinkDetail_Form[${vstatus.index}].fdName" _xform_type="text">
		                                <xform:text property="fdLinkDetail_Form[${vstatus.index}].fdName" showStatus="edit" required="true" subject="${lfn:message('fssc-mobile:fsscMobileLinkDetail.fdName')}" validators=" maxLength(200)" style="width:95%;" />
		                            </div>
		                        </td>
		                        <td class="docList" align="center">
		                            <%-- 连接地址--%>
		                            <div id="_xform_fdLinkDetail_Form[${vstatus.index}].fdUrl" _xform_type="text">
		                                <xform:text property="fdLinkDetail_Form[${vstatus.index}].fdUrl" showStatus="edit" style="width:95%;" />
		                            </div>
		                        </td>
		                         <td class="docList" align="center">
		                           <div class="lui_icon_l" style="cursor: pointer;" title="${lfn:message('sys-person:sysPersonSysNavLink.selectIconTitle') }">
										<div style="background-color: #C78700;" onclick="SysIconDialog(this);">
											<img src="${LUI_ContextPath }/fssc/mobile/resource/images/icon/${fdLinkDetail_FormItem.fdIcon }"/>
										</div>
									</div>
									<input type="hidden" name="fdLinkDetail_Form[${vstatus.index}].fdIcon" value="${fdLinkDetail_FormItem.fdIcon }">
		                        </td>
		                        <td class="docList" align="center">
		                            <a href="javascript:void(0);" onclick="DocList_CopyRow();" title="${lfn:message('doclist.copy')}">
		                                <img src="${KMSS_Parameter_StylePath}icons/icon_copy.png" border="0" />
		                            </a>
		                            &nbsp;
		                            <a href="javascript:void(0);" onclick="DocList_DeleteRow();" title="${lfn:message('doclist.delete')}">
		                                <img src="${KMSS_Parameter_StylePath}icons/icon_del.png" border="0" />
		                            </a>
		                        </td>
		                    </tr>
		                </c:forEach>
		              </table>
		            </td>
            	</tr>
            	<tr>
            	 <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-mobile:fsscMobileLink.docCreator')}
                    </td>
                    <td width="35%">
                        <%-- 名称--%>
                        <div id="_xform_docCreator" _xform_type="text">
                        	 <ui:person personId="${fsscMobileLinkForm.docCreatorId}" personName="${fsscMobileLinkForm.docCreatorName}" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-mobile:fsscMobileLink.docCreateTime')}
                    </td>
                    <td width="35%">
                        <div id="_xform_docCreateTime" _xform_type="text">
                        	<xform:text property="docCreateTime" showStatus="view" style="width:95%;"  />
                        </div>
                    </td>
            	</tr>
             </table>
            <input type="hidden" name="fdLinkDetail_Flag" value="1">
            <script>
                Com_IncludeFile("doclist.js");
                
                function SysLinksDialog() {
					seajs.use(['lui/dialog','lui/jquery'], function(dialog, $){
						dialog.build({
							config : {
									width : 600,
									height : 580,  
									title : "${lfn:escapeJs(lfn:message('sys-person:sysPersonSysNavLink.selectSysLink'))}",
									content : {
										type : "iframe",
										url : "/sys/person/sys_person_link/sysPersonLink.do?method=dialog&type=shortcut"
									}
							},
							callback : function(data) {
								if(data==null) {
									return;
								}
								AddSelectedNavLink(data);
							}
						}).show(); 
					});
				}
                
                function AddSelectedNavLink(data) {
                	for (var i = 0; i < data.length; i ++) {
						var row = data[i];
						var iconName = getIconName(row.url);
						console.log(row);
						var rowData = {
								'fdLinkDetail_Form[!{index}].fdName': row.name,
								'fdLinkDetail_Form[!{index}].fdUrl': row.url,
								'fdLinkDetail_Form[!{index}].fdId': row.id,
						};
						
						if(row.langNames){
							var langs = row.langNames.split("$$");
							for(var j=0;j<langs.length;j++){
								var lang = langs[j];
								var shortName = lang.split("##")[0];
								var value = lang.split("##")[1];
								var key = 'fdLinkDetail_Form[!{index}].dynamicMap(fdName'+shortName+")";
								rowData[key]=value;
							}
						}
						 DocList_AddRow('TABLE_DocList_fdLinkDetail_Form', null, rowData);
					}
				}
                
                function SysLinksCategoryDialog() {
					seajs.use(['lui/dialog','lui/jquery'], function(dialog, $){
						dialog.build({
							config : {
									width : 800,
									height : 280,  
									title : "${lfn:escapeJs(lfn:message('fssc-mobile:button.select.category'))}",
									content : {
										type : "iframe",
										url : "/fssc/mobile/fssc_mobile_link/fsscMobileLink.do?method=dialog&type=1"
									}
							},
							callback : function(data) {
								
								if(data==null) {
									return;
								}
								AddSelectedNavLink(data);
							}
						}).show(); 
					});
				}
                
                function getIconName(url){
					var newUrl = "";
					if(url && url.charAt(0)){
						newUrl = url.replace('/','_');
						if(newUrl.indexOf('/') > -1){
							newUrl = newUrl.replace('/','_');
							if(newUrl.indexOf('/') > -1){
								newUrl = newUrl.substr(0,newUrl.indexOf('/'));
							}else{
								newUrl = newUrl.replace('.index','');
							}
							return newUrl;
						}
					}
					return "";
				}
                
                function SysIconDialog(dom) {
					seajs.use(['lui/dialog','lui/jquery'], function(dialog, $){
						var _width = 750;
						var url = "/fssc/mobile/fssc_mobile_link/mobileIcon.jsp?type=l&status=false";
						var className = "lui_icon_l";
						dialog.build({
							config : {
									width : 750,
									height : 500,  
									title : "${lfn:escapeJs(lfn:message('sys-person:sysPersonSysNavLink.selectIconTitle')) }",
									content : {  
										type : "iframe",
										url : url
									}
							},
							callback : function(value, dia) {
								if(value==null){
									return ;
								}
								$(dom).removeClass().addClass(className);
								$(dom).closest("td").find('[name$="fdIcon"]').val(value);
								$(dom).closest("td").find('img').remove()
								$(dom).append("<img src=\"${LUI_ContextPath}/fssc/mobile/resource/images/icon/"+value+"\"/>");
							}
						}).show(); 
					});
				}
            </script>
            <script>
                DocList_Info.push('TABLE_DocList_fdLinkDetail_Form');
            </script>
          </div>
       </template:include>
    </center>
    <html:hidden property="fdId" />
    <html:hidden property="method_GET" />
    <script>
        $KMSSValidation();
    </script>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp" %>

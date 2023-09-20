<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<template:include ref="default.edit"  sidebar="no" showQrcode="false">
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
		      		border: 0px;
		      		color: #868686
		    	}
		    
		</style>
		<script type="text/javascript">
		    var formInitData = {
		
		    };
		    var messageInfo = {
		
		    };
		    Com_IncludeFile("security.js");
		    Com_IncludeFile("domain.js");
		    Com_IncludeFile("form.js");
		    Com_IncludeFile("config_edit.js", "${LUI_ContextPath}/kms/category/resource/js/", 'js', true);
		    Com_IncludeFile("form_option.js", "${LUI_ContextPath}/kms/category/kms_category_main/", 'js', true);
		    
		    Com_Parameter.event["submit"][Com_Parameter.event["submit"].length] = function() {
				//提交前，校验类别名称唯一性
				if(checkCategoryName())
					 return true ;
				else 
					return false ;
			}
			
			function checkCategoryName(){
				var fdName=document.getElementsByName("fdName")[0].value ; 
				var fdId='${kmsCategoryMainForm.fdId}';
				var parentId='${kmsCategoryMainForm.fdParentId}'; 
				if(parentId==null||parentId==""){
					parentId = document.getElementsByName("fdParentId")[0].value;
				}
				if(fdName != "" && fdName != null){
					var url="kmsCategoryMainCheckService&fdName="+fdName+"&fdId="+fdId+"&parentId="+parentId;
					url = Com_SetUrlParameter(url, "fdName", fdName);
					var data = new KMSSData(); 
					var isExist =data.AddBeanData(url).GetHashMapArray()[0];
				   	if(isExist["key0"]=='false'){
				   		return true;
				   	}else{
				   		seajs.use([ 'lui/dialog'], function(dialog) {
					   		dialog.alert('<bean:message key="msg.hasExist" bundle="kms-category"/>');
					   	})
				   		return false;
				   	}
				}
			}
		</script>
		
		    <% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser()); %>
	</template:replace>


	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float">

			<c:choose>
				<c:when test="${kmsCategoryMainForm.method_GET=='edit'}">
					<ui:button text="${ lfn:message('button.update') }" order="2"
						onclick="Com_Submit(document.kmsCategoryMainForm, 'update');">
					</ui:button>
				</c:when>
				<c:when test="${kmsCategoryMainForm.method_GET=='add'}">
					<ui:button text="${ lfn:message('button.save') }" order="2"
						onclick="Com_Submit(document.kmsCategoryMainForm, 'save');">
					</ui:button>
				</c:when>
			</c:choose>
			<ui:button text="${ lfn:message('button.close') }" order="2"
				onclick="Com_CloseWindow();">
			</ui:button>
			<p class="txttitle">${ lfn:message('kms-category:table.kmsCategoryMain') }</p>

		</ui:toolbar>
	</template:replace>

	<template:replace name="content">
	    <html:form action="/kms/category/kms_category_main/kmsCategoryMain.do">
	    
	    <center>
	
	        <div style="width:95%;">
	            <table class="tb_normal" width="100%">
	                <tr>
	                    <td class="td_normal_title" width="15%">
	                        ${lfn:message('kms-category:kmsCategoryMain.fdParent')}
	                    </td>
	                    <td colspan="3" width="85.0%">
	                        <div id="_xform_fdParentId" _xform_type="dialog">
	                            <xform:dialog propertyId="fdParentId" propertyName="fdParentName" showStatus="edit" style="width:95%;">
	                                Dialog_SimpleCategory('com.landray.kmss.kms.category.model.KmsCategoryMain','fdParentId','fdParentName',false,null,'01',null,false,'${JsParam.fdId}');
	                            </xform:dialog>
	                        </div>
	                        </a>
	                    </td>
	                </tr>
	                <tr>
	                    <td class="td_normal_title" width="15%">
	                        ${lfn:message('kms-category:kmsCategoryMain.fdName')}
	                    </td>
	                    <td colspan="3" width="85.0%">
	                        <div id="_xform_fdName" _xform_type="text">
	                            <xform:text property="fdName" showStatus="edit" style="width:95%;" />
	                        </div>
	                    </td>
	                </tr>
	                <tr>
	                    <td class="td_normal_title" width="15%">
	                        ${lfn:message('kms-category:kmsCategoryMain.fdOrder')}
	                    </td>
	                    <td colspan="3" width="85.0%">
	                        <div id="_xform_fdOrder" _xform_type="text">
	                            <xform:text property="fdOrder" showStatus="edit" style="width:95%;" />
	                        </div>
	                    </td>
	                </tr>
					
					<kmss:ifModuleExist path="/kms/istorage/">
						<tr>
							<c:import url="/sys/tag/import/sysTagMain_edit.jsp"
								charEncoding="UTF-8">
								<c:param name="formName" value="kmsCategoryMainForm" />
								<c:param name="fdKey" value="categoryMain" />
							</c:import>
						</tr>
					</kmss:ifModuleExist>
					
					<tr>
	                    <td class="td_normal_title" width="15%">
	                        ${lfn:message('kms-category:kmsCategoryMain.authEditors')}
	                    </td>
	                    <td colspan="3" width="85.0%">
	                        <div id="_xform_authEditorIds" _xform_type="address">
	                            <xform:address propertyId="authEditorIds" propertyName="authEditorNames" mulSelect="true" orgType="ORG_TYPE_ALL" showStatus="edit" textarea="true" style="width:95%;" />
	                        </div>
	                        <div class="description_txt">
								${lfn:message('kms-category:kmsCategoryMain.description.main.tempEditor')}
							</div>
	                    </td>
	                </tr>
	                <tr>
	                    <td class="td_normal_title" width="15%">
	                        ${lfn:message('kms-category:kmsCategoryMain.fdDesc')}
	                    </td>
	                    <td colspan="3" width="85.0%">
	                        <div id="_xform_fdDesc" _xform_type="textarea">
	                            <xform:textarea property="fdDesc" showStatus="edit" style="width:95%;" />
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
</template:replace>	
</template:include>
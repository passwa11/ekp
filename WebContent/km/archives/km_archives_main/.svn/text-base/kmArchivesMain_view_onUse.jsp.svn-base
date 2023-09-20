<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="com.landray.kmss.sys.filestore.service.ISysFileConvertQueueService"%>
<%@page import="com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm"%>
<%@page import="com.landray.kmss.km.archives.service.IKmArchivesMainService"%>
<%@page import="java.util.Arrays"%>
<%@page import="com.landray.kmss.util.ArrayUtil"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="com.landray.kmss.km.archives.model.KmArchivesDetails"%>
<%@page import="com.landray.kmss.km.archives.forms.KmArchivesMainForm"%>
<%@page import="com.landray.kmss.km.archives.util.KmArchivesUtil"%>
<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%
	KmArchivesMainForm mainForm = (KmArchivesMainForm)request.getAttribute("kmArchivesMainForm");
	String fdId = mainForm.getFdId();
	KmArchivesDetails detail = KmArchivesUtil.getBorrowDetail(fdId);
	boolean isValidity = KmArchivesUtil.isValidity(fdId);
	pageContext.setAttribute("isValidity", isValidity);
	pageContext.setAttribute("isBorrowed", detail != null);
	pageContext.setAttribute("currentId",UserUtil.getUser().getFdId());
	pageContext.setAttribute("isWork",KmArchivesUtil.isWorkUser(mainForm.getFdId()));
	pageContext.setAttribute("viewAll", UserUtil.checkRole("ROLE_KMARCHIVES_VIEW_ALL"));
	boolean isFileReader = false; //是否是文件级可阅读者
	if(StringUtil.isNotNull(mainForm.getAuthFileReaderIds())) {
		String[] readerIds = mainForm.getAuthFileReaderIds().split(";");
		isFileReader = ArrayUtil.isListIntersect(UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgIds(), Arrays.asList(readerIds));
	}
	pageContext.setAttribute("isFileReader", isFileReader);
	boolean isConverting = false; //是否有正在转换中的附件
	ISysFileConvertQueueService queueService = (ISysFileConvertQueueService) SpringBeanUtil.getBean("sysFileConvertQueueService");
	isConverting = !ArrayUtil.isEmpty(queueService.isAllSuccess(fdId, "toPDF", "aspose"));
	pageContext.setAttribute("isConverting", isConverting);
%>

<template:include ref="default.view">
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
   			.barCodeImg {
   				position:absolute;
   				right:20px;
   			}
        </style>
    </template:replace>
    <template:replace name="title">
        <c:out value="${kmArchivesMainForm.docSubject} - " />
        <c:out value="${ lfn:message('km-archives:table.kmArchivesMain') }" />
    </template:replace>
    <template:replace name="toolbar">
        <script>
            seajs.use(['lui/dialog'], function(dialog) {
             	window.deleteDoc = function(delUrl){
             		var delUrl = '<c:url value="/km/archives/km_archives_main/kmArchivesMain.do"/>?method=delete&fdId=${param.fdId}';
             		dialog.iframe('/sys/edition/import/doc_delete_iframe.jsp?fdModelName=com.landray.kmss.km.archives.model.KmArchivesMain&fdModelId=${param.fdId}',
             					  "<bean:message key='ui.dialog.operation.title' bundle='sys-ui'/>",function (url){
	       		                    if(url) 
	       		                    {
	       		                 	   Com_OpenWindow(url, '_self');
	       		                    }
             					  },{width:400,height:170,params:{url:delUrl,type:'GET'}}
             					 );
 				};
            });
            var docNumber = '${kmArchivesMainForm.docNumber}';
            if(docNumber != null && docNumber != '') {
	            seajs.use(['lui/jquery','lui/barcode'],function($,barcode) {
	            	$(document).ready(function() {
                        try {
                            barcode.Barcode("#imgcode", docNumber, {
                                height : 50
                            });
                        } catch (e) {
                            console.error("档案编号含有中文或者特殊字符，具体信息：", e);
                            console.warn("档案编号含有中文或者特殊字符，导致无法展示条形码！");
                            $("#barCodeImgBox").hide();
                        }
	            	});
	            });
            }
            window.addBorrow = function() {
            	Com_OpenWindow('${LUI_ContextPath}/km/archives/km_archives_borrow/kmArchivesBorrow.do?method=add&fdMainId=${JsParam.fdId}');
            };
            window.returnBack = function(){
            	seajs.use(['lui/jquery','lui/dialog'],function($,dialog){
            		var _url = Com_Parameter.ContextPath+'km/archives/km_archives_details/kmArchivesDetails.do?method=returnBack';
            		$.ajax({
            			type:'post',
            			url:_url,
            			data:{fdArchId:'${kmArchivesMainForm.fdId}'},
            			aysnc:true,
            			success:function(data){
            				var _data = eval(data);
            				if(_data.length==0){
            					dialog.alert("${ lfn:message('km-archives:kmArchivesMain.no.borrow') }");
            				}else{
            					dialog.confirm("${ lfn:message('km-archives:kmArchivesMain.return.confirm') }",function(isOk){
            						if(isOk){
            							var delUrl = Com_Parameter.ContextPath+'km/archives/km_archives_details/kmArchivesDetails.do?method=comfirmReturnBack&fdId='+_data[0].fdId;
            							Com_OpenWindow(delUrl, '_self');
            						}
            					});
            				}	
            			}
            		});
            	});
            }
        </script>
        <!-- 软删除部署 -->
		<c:if test="${kmArchivesMainForm.docDeleteFlag ==1}">
			<ui:toolbar id="toolbar" style="display:none;"></ui:toolbar>
		</c:if>
		<c:if test="${kmArchivesMainForm.docDeleteFlag !=1}">
			<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="5" var-navwidth="90%" style="display:none;">
			<!-- 借阅  -->
			<kmss:authShow roles="ROLE_KMARCHIVES_CREATE_BORROW">
				<c:if test="${isValidity }">
					<ui:button text="${ lfn:message('km-archives:button.borrow') }" order="2" onclick="addBorrow();"></ui:button>
				</c:if>
			</kmss:authShow>
			<!-- 归还 -->
			<c:if test="${isBorrowed }">
				<ui:button text="${ lfn:message('km-archives:button.return') }" order="1" onclick="returnBack();"/>
			</c:if>
            <c:if test="${ kmArchivesMainForm.docStatus=='10' || kmArchivesMainForm.docStatus=='11' || kmArchivesMainForm.docStatus=='20' }">
                <!--edit-->
                <kmss:auth requestURL="/km/archives/km_archives_main/kmArchivesMain.do?method=edit&fdId=${param.fdId}">
                    <ui:button text="${lfn:message('button.edit')}" onclick="Com_OpenWindow('kmArchivesMain.do?method=edit&fdId=${param.fdId}','_self');" order="2" />
                </kmss:auth>
            </c:if>
            <!--delete-->
            <kmss:auth requestURL="/km/archives/km_archives_main/kmArchivesMain.do?method=delete&fdId=${param.fdId}">
                <ui:button text="${lfn:message('button.delete')}" onclick="deleteDoc('kmArchivesMain.do?method=delete&fdId=${param.fdId}');" order="4" />
            </kmss:auth>
            <ui:button text="${lfn:message('button.close')}" order="5" onclick="Com_CloseWindow();" />

        	</ui:toolbar>
		</c:if>
    </template:replace>
    <template:replace name="path">
        <ui:menu layout="sys.ui.menu.nav">
            <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" href="/" target="_self" />
            <ui:menu-item text="${ lfn:message('km-archives:table.kmArchivesMain') }" href="/km/archives/" target="_self" />
        	<ui:menu-source autoFetch="false" target="_self"
				href="/km/archives/index.jsp#j_path=%2FallArchives&mydoc=all&cri.q=docTemplate%3A${kmArchivesMainForm.docTemplateId}">
				<ui:source type="AjaxJson">
					{"url":"/sys/simplecategory/criteria/sysSimpleCategoryCriteria.do?method=path&modelName=com.landray.kmss.km.archives.model.KmArchivesCategory&categoryId=${kmArchivesMainForm.docTemplateId}&currId=!{value}&authType=2&pAdmin=!{pAdmin}"}
				</ui:source>
			</ui:menu-source>
        </ui:menu>
    </template:replace>
    <template:replace name="content">
	<c:import url="/sys/recycle/import/redirect.jsp">
		<c:param name="formBeanName" value="kmArchivesMainForm"></c:param>
	</c:import>    
        <%-- <form name="kmArchivesMainForm" method="post" action="<c:url value='/km/archives/km_archives_main/kmArchivesMain.do'/>"> --%>
			<div class='lui_form_title_frame' id="barCodeImgBox">
                <div class='lui_form_subject' style="height:70px;line-height:70px;position:relative;">
                    ${lfn:message('km-archives:table.kmArchivesMain')}
                    <img class="barCodeImg" id="imgcode" />
                </div>
                <div class='lui_form_baseinfo'>

                </div>
            </div>
            <ui:tabpage expand="false" var-navwidth="90%">
                <ui:content title="${ lfn:message('km-archives:py.JiBenXinXi') }" expand="true">
                    
                    <table class="tb_normal" width="100%">
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('km-archives:kmArchivesMain.docSubject')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <xform:text property="docSubject" showStatus="view" style="width:95%;" />
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('km-archives:kmArchivesMain.docTemplate')}
                            </td>
                            <td width="35%">
                                <xform:dialog propertyId="docTemplateId" propertyName="docTemplateName" showStatus="view" style="width:95%;">

                                </xform:dialog>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('km-archives:kmArchivesMain.docNumber')}
                            </td>
                            <td width="35%">
                                <xform:text property="docNumber" showStatus="view" style="width:95%;" />
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('km-archives:kmArchivesMain.fdLibrary')}
                            </td>
                            <td width="35%">
                            	<xform:text property="fdLibrary" showStatus="view" style="width:95%;" />
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('km-archives:kmArchivesMain.fdVolumeYear')}
                            </td>
                            <td width="35%">
                            	<c:out value="${kmArchivesMainForm.fdVolumeYear}"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('km-archives:kmArchivesMain.fdPeriod')}
                            </td>
                            <td width="35%">
                            	<xform:text property="fdPeriod" showStatus="view" style="width:95%;" />
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('km-archives:kmArchivesMain.fdUnit')}
                            </td>
                            <td width="35%">
                            	<xform:text property="fdUnit" showStatus="view" style="width:95%;" />
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('km-archives:kmArchivesMain.fdStorekeeper')}
                            </td>
                            <td width="35%">
                                <xform:address propertyId="fdStorekeeperId" propertyName="fdStorekeeperName" orgType="ORG_TYPE_PERSON" showStatus="view" style="width:95%;" />
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('km-archives:kmArchivesMain.fdValidityDate')}
                            </td>
                            <td width="35%">
                                <xform:datetime property="fdValidityDate" showStatus="view" style="width:95%;" />
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('km-archives:kmArchivesMain.fdDenseLevel')}
                            </td>
                            <td width="35%">
                            	<xform:text property="fdDenseLevel" showStatus="view" style="width:95%;" />
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('km-archives:kmArchivesMain.fdFileDate')}
                            </td>
                            <td width="35%">
                                <xform:datetime property="fdFileDate" showStatus="view" style="width:95%;" />
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('km-archives:kmArchivesMain.fdRemarks')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <xform:textarea property="fdRemarks" showStatus="view" style="width:95%;" />
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('km-archives:kmArchivesMain.docCreator')}
                            </td>
                            <td width="35%">
                                <%-- <ui:person personId="${kmArchivesMainForm.docCreatorId}" personName="${kmArchivesMainForm.docCreatorName}" /> --%>
                            	<xform:text property="docCreatorName" showStatus="view" style="width:95%;" />
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('km-archives:kmArchivesMain.docCreateTime')}
                            </td>
                            <td width="35%">
                                <xform:datetime property="docCreateTime" showStatus="view" style="width:95%;" />
                            </td>
                        </tr>
                    </table>
                </ui:content>
                <!-- 文件级 -->
                <c:if test="${viewAll||isWork||(kmArchivesMainForm.docCreatorId eq currentId)||(kmArchivesMainForm.fdStorekeeperId eq currentId)||('1' != param.expired and isBorrowed)||(kmArchivesMainForm.docStatus eq '30' and isFileReader)}">
                 <ui:content title="${ lfn:message('km-archives:kmArchivesMain.fileLevel') }" expand="true">
                  <c:if test="${not empty kmArchivesMainForm.extendFilePath }">
                  	<table class="tb_normal" width=100% >
	                  	<c:import url="/sys/property/include/sysProperty_view.jsp" charEncoding="UTF-8">
							<c:param name="formName" value="kmArchivesMainForm" />
							<c:param name="fdDocTemplateId" value="${kmArchivesMainForm.docTemplateId}" />
						</c:import>
					</table>
                  </c:if>
                  <!-- 附件机制 -->
                  <c:choose>
	                  <c:when test="${isConverting }">
	                  		<bean:message bundle="km-archives" key="kmArchivesFileTemplate.converting"/>
	                  </c:when>
	                  <c:otherwise>
		              	<c:import url="/sys/attachment/sys_att_main/sysAttMain_view.jsp" charEncoding="UTF-8">
	                       	<c:param name="fdKey" value="attArchivesMain" />
	                        <c:param name="fdModelId" value="${param.fdId}" />
	                        <c:param name="formBeanName" value="kmArchivesMainForm" />
		              	</c:import>
	                  </c:otherwise>
                  </c:choose>
                 </ui:content>
                </c:if>
                <c:import url="/sys/lbpmservice/import/sysLbpmProcess_view.jsp" charEncoding="UTF-8">
                    <c:param name="formName" value="kmArchivesMainForm" />
                    <c:param name="fdKey" value="kmArchivesMain" />
                    <c:param name="isExpand" value="true" />
                </c:import>

                <%-- <c:import url="/sys/right/import/right_view.jsp" charEncoding="UTF-8">
                    <c:param name="formName" value="kmArchivesMainForm" />
                    <c:param name="moduleModelName" value="com.landray.kmss.km.archives.model.KmArchivesMain" />
                </c:import> --%>
                <c:import url="/km/archives/import/right_view.jsp" charEncoding="UTF-8">
                	<c:param name="formName" value="kmArchivesMainForm" />
                </c:import>
                

                <c:import url="/sys/readlog/import/sysReadLog_view.jsp" charEncoding="UTF-8">
                    <c:param name="formName" value="kmArchivesMainForm" />
                </c:import>
                <!-- 嵌入版本标签的代码 -->
				<c:import url="/sys/edition/import/sysEditionMain_view.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="kmArchivesMainForm" />
				</c:import>
				
				<ui:content title="${ lfn:message('km-archives:py.BorrowRecord') }">
	            	<c:import url="/km/archives/km_archives_main/kmArchivesMain_borrowList.jsp"></c:import>
            	</ui:content>
            </ui:tabpage>
        <!-- </form> -->
    </template:replace>

</template:include>
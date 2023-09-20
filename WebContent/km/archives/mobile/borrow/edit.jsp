<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/mobile/jsp/ajax-accept.jsp" %>
<template:include ref="mobile.edit" compatibleMode="true">
	<template:replace name="title">
		<c:choose>
			<c:when test="${kmArchivesBorrowForm.method_GET == 'add' }">
				<c:out value="${ lfn:message('operation.create') } - ${ lfn:message('km-archives:table.kmArchivesBorrow') }" />
			</c:when>
			<c:otherwise>
				<c:out value="${kmArchivesBorrowForm.docSubject } - " />
				<c:out value="${ lfn:message('km-archives:table.kmArchivesBorrow') }" />
			</c:otherwise>
		</c:choose>
	</template:replace>
	<template:replace name="head">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/km/archives/mobile/resource/css/archives_borrow_edit.css?s_cache=${MUI_Cache}"></link>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/km/archives/mobile/resource/css/archselector.css?s_cache=${MUI_Cache}"></link>
		<script type="text/javascript">
		   	require(["dojo/store/Memory","dojo/topic","dijit/registry"],function(Memory,topic,registry){
		   		var navData = [{'text':'01  /  ${lfn:message("sys-mobile:mui.mobile.info") }',
		   			'moveTo':'scrollView','selected':true},{'text':'02  /  ${lfn:message("sys-mobile:mui.mobile.review") }',
			   		'moveTo':'lbpmView'}]
		   		window._narStore = new Memory({data:navData});
		   		var changeNav = function(view){
		   			var wgt = registry.byId("_flowNav");
		   			for(var i=0;i<wgt.getChildren().length;i++){
		   				var tmpChild = wgt.getChildren()[i];
		   				if(view.id == tmpChild.moveTo){
		   					tmpChild.beingSelected(tmpChild.domNode);
		   					return;
		   				}
		   			}
		   		}
		   		topic.subscribe("mui/form/validateFail",function(view){
		   			changeNav(view);
		   		});
				topic.subscribe("mui/view/currentView",function(view){
					changeNav(view);
		   		});
		   	});
	   	</script>
	</template:replace>
	<template:replace name="content">
		<html:form action="/km/archives/km_archives_borrow/kmArchivesBorrow.do">
			<div>
				<div data-dojo-type="mui/fixed/Fixed" class="muiFlowEditFixed">
					<div data-dojo-type="mui/fixed/FixedItem"
						class="muiFlowEditFixedItem">
						<div data-dojo-type="mui/nav/NavBarStore" id="_flowNav"
							data-dojo-props="store:_narStore"></div>
					</div>
				</div>
				<div data-dojo-type="mui/view/DocScrollableView" 
					data-dojo-mixins="km/archives/mobile/resource/js/SelectArchMixin" id="scrollView">
					<html:hidden property="fdId" />
          			<html:hidden property="method_GET" />
          			<html:hidden property="docStatus"/>
					
					<div class="muiFlowInfoW muiFormContent">
						<table class="muiSimple" cellpadding="0" cellspacing="0">
							<tr>
	                            <td class="muiTitle">
	                                ${lfn:message('km-archives:kmArchivesBorrow.docSubject')}
	                            </td>
	                            <td>
	                                <xform:text property="docSubject" showStatus="edit" style="width:95%;" mobile="true"/>
	                            </td>
                        	</tr>
                        	<tr>
	                            <td class="muiTitle">
	                                ${lfn:message('km-archives:kmArchivesBorrow.docTemplate')}
	                            </td>
	                            <td width="35%">
	                            	<%
	                            		request.setAttribute("fdMainId",request.getParameter("fdMainId"));
	                            	%>
	                            	
	                            	<!--#108089 只有新建时，才能更换流程 -->
	                            	<c:set var="templateShowStatus" value="readOnly" ></c:set>
	                            	<c:if test="${kmArchivesBorrowForm.method_GET eq 'add'}">
	                            		<c:set var="templateShowStatus" value="edit" ></c:set>
	                            	</c:if>
                           			<xform:select property="docTemplateId" showStatus="${templateShowStatus}" mobile="true" value="${kmArchivesBorrowForm.docTemplateId}" style="width:200px">
										<xform:customizeDataSource className="com.landray.kmss.km.archives.service.spring.KmArchivesTemplateDataSource"></xform:customizeDataSource>
									</xform:select>
	                            </td>
                        	</tr>
                        	<tr>
                        		<td class="muiTitle">
	                                ${lfn:message('km-archives:kmArchivesBorrow.fdBorrowDate')}
	                            </td>
	                            <td>
	                                <xform:datetime htmlElementProperties="id='fdBorrowDate'" property="fdBorrowDate" showStatus="edit" dateTimeType="datetime" style="width:95%;" required="true" mobile="true"/>
	                            </td>
                        	</tr>
                        	<tr>
	                        	<td class="muiTitle">
	                                ${lfn:message('km-archives:kmArchivesBorrow.fdBorrower')}
	                            </td>
	                            <td>
	                                <xform:address propertyId="fdBorrowerId" propertyName="fdBorrowerName" orgType="ORG_TYPE_PERSON" showStatus="readOnly" required="true" subject="${lfn:message('km-archives:kmArchivesBorrow.fdBorrower')}" mobile="true" onValueChange="selectDept(this);"/>
	                            </td>
                        	</tr>
                        	<tr>
	                        	<td class="muiTitle">
	                                ${lfn:message('km-archives:kmArchivesBorrow.docDept')}
	                            </td>
	                            <td>
	                                <xform:address propertyId="docDeptId" propertyName="docDeptName" orgType="ORG_TYPE_ORGORDEPT" showStatus="readOnly" htmlElementProperties="id='docDept'" mobile="true"/>
	                            </td>
                        	</tr>
                        	<tr>
                        		<td colspan="2">
	                        		<div style="position: relative;">
										<div class="muiFormEleTip"><span class="muiFormEleTitle" style="display: inherit;">${lfn:message('km-archives:kmArchivesBorrow.fdBorrowDetails')}</span></div>
										<div class="muiFormRequired">*</div>
										<input type="hidden" name="fdBorrowDetail_Flag" value="1">
										<%@include file="/km/archives/mobile/borrow/edit_arch.jsp"%>
									</div>
									<div>
									   	<div 
									   		data-dojo-type="km/archives/mobile/resource/js/ArchivesSelectorButton"
									   		data-dojo-mixins="km/archives/mobile/resource/js/ArchivesSelectorMixin"
									   		data-dojo-props="isMul:true,fdTemplatId:'${kmArchivesBorrowForm.docTemplateId}'" id="selectArchButton">
											+ ${lfn:message('km-archives:kmArchivesDetails.add')}
										</div>
										<div class="muiValidate" style="display: none;" id="selectArchTips">
											<div class="muiValidateContent">
												<div class="muiValidateShape"></div>
												<div class="muiValidateInfo">
													<span class="muiValidateIcon"></span>
													<span class="muiValidateMsg">
														<span class="muiValidateTitle">${lfn:message('km-archives:kmArchivesDetails.record')}</span> 
														${lfn:message('km-archives:kmArchivesDetails.record.notnull')}
													</span>
												</div>
											</div>
										</div>
									</div>
                        		</td>
                        	</tr>
                        	<tr>
                        		<td class="muiTitle">
                        			${lfn:message('km-archives:kmArchivesBorrow.fdBorrowReason')}
                        		</td>
	                            <td>
	                                <xform:textarea property="fdBorrowReason" showStatus="edit" mobile="true" />
	                            </td>
                        	</tr>
                        	<tr>
                        		<td class="muiTitle">
                                	${lfn:message('km-archives:kmArchivesBorrow.attBorrow')}
                            	</td>
	                            <td>
	                                <c:import url="/sys/attachment/mobile/import/edit.jsp" charEncoding="UTF-8">
										<c:param name="formName" value="kmArchivesBorrowForm" />
										<c:param name="fdKey" value="attBorrow" />
	                                    <c:param name="fdMulti" value="true" />
									</c:import>
	                            </td>
                        	</tr>
						</table>
					</div>
					
					<ul data-dojo-type="mui/tabbar/TabBar" fixed="bottom" data-dojo-props='fill:"grid"'>
				  		<li data-dojo-type="mui/tabbar/TabBarButton" class="muiBtnNext " 
				  			data-dojo-props='colSize:2,moveTo:"lbpmView",transition:"slide"'>
				  			${lfn:message('km-archives:button.next')}
				  		</li>
					</ul>
				</div>
				<!-- 权限机制 -->
				<c:import url="/sys/right/mobile/edit_hidden.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="kmArchivesBorrowForm" />
					<c:param name="moduleModelName" value="com.landray.kmss.km.archives.model.KmArchivesBorrow" />
				</c:import>
				<!-- 流程机制 -->		
				<c:import url="/sys/lbpmservice/mobile/import/edit.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="kmArchivesBorrowForm" />
					<c:param name="fdKey" value="kmArchivesBorrow" />
					<c:param name="viewName" value="lbpmView" />
					<c:param name="backTo" value="scrollView" />
					<c:param name="onClickSubmitButton" value="borrow_submit();" />
				</c:import>
				<script type="text/javascript">
					require(["mui/form/ajax-form!kmArchivesBorrowForm"]);
					require(['dojo/ready', 'dijit/registry', 'dojo/topic', 'dojo/request', 
					         'dojo/dom', 'dojo/dom-attr', 'dojo/dom-style', 'dojo/dom-class', 'dojo/query', 'dojo/on','mui/dialog/Confirm', 'dojo/touch'], function(ready, registry, topic, request, dom, domAttr, domStyle, domClass, query, on, Confirm,touch){
						
						topic.subscribe('/mui/form/valueChanged',function(widget,args){
							if(widget && widget.name=="docTemplateId"){
								console.log(args.value);
								Confirm('是否确定更改借阅流程模板？',null,function(data){
									if(data){
										document.kmArchivesBorrowForm.action = Com_SetUrlParameter(
												location.href, "method", "add");
										document.kmArchivesBorrowForm.action = Com_SetUrlParameter(
												document.kmArchivesBorrowForm.action, "type", "change");
										document.kmArchivesBorrowForm.action = Com_SetUrlParameter(
												document.kmArchivesBorrowForm.action, "docTemplateId", args.value);
										document.kmArchivesBorrowForm.submit();
									}
								},false);
							}
						});
						
						
						var selectArchTips = null;
						//校验对象
						var validorObj=null;
						ready(function(){
							// 填充流程信息（临时解决方案）
							query('input[name="sysWfBusinessForm.fdParameterJson"]')[0].value = '${kmArchivesBorrowForm.sysWfBusinessForm.fdParameterJson}';
							selectArchTips = dom.byId('selectArchTips');
							validorObj = registry.byId('scrollView');
							validorObj._validation.addValidator('returnDateValidator(validityDate)',"${lfn:message('km-archives:kmArchivesBorrow.returnDateValidate')}",function(v,e,o){
								var validityDateStr =  o['validityDate'];
								if(validityDateStr != ''){
									var validityDate = Com_GetDate(validityDateStr,'date','yyyy-MM-dd');
							     	var returnDate = Com_GetDate(v,'date','yyyy-MM-dd');
							     	if(returnDate.getTime() > validityDate.getTime()){
							     		return false;
							     	}
								}
						     	return true;
						     });
						});
						topic.subscribe('km/archives/archselector/result', function(res){
							if(Object.prototype.toString.call(res) == '[object Array]') {
								topic.publish('km/archives/selectedarch/res', res);
							} else {
								topic.publish('km/archives/selectedarch/add', res);
							}
							domStyle.set(selectArchTips, 'display', 'none');
						});
						/*************** 提交表单 ***************/
						window.borrow_submit = function(){
							var method = Com_GetUrlParameter(location.href,'method');
							var status = document.getElementsByName("docStatus")[0];
							status.value='20';
							if(method=='add4m' || method=='add'){
								Com_Submit(document.forms[0],'save');
							}else{
								Com_Submit(document.forms[0],'update');
							}
						};
						//提交后返回列表页面
						Com_Submit.ajaxAfterSubmit=function(){
							setTimeout(function(){
								window.location='${LUI_ContextPath}/km/archives/mobile/borrow';
							},1000);
						};
						window.selectDept = function(dom){
							var fdBorrowerId = dom.value;
				        	if(dom.value!=''){
				        		query(".detailBorrows").forEach(function(node){
				        			node.value = fdBorrowerId;
					        	});
				        		var url = "${KMSS_Parameter_ContextPath}km/archives/km_archives_borrow/kmArchivesBorrow.do?method=getApplicantDept&fdBorrowerId="+fdBorrowerId;
				        		request.get(url, {handleAs : 'json',headers: {"accept": "application/json"}})
								.response.then(function(datas) {
									if(datas.status=='200'){
										var data = datas.data;
										registry.byId('docDept')._setCurIdsAttr(data.deptId);
										registry.byId('docDept')._setCurNamesAttr(data.deptName);
									}
								});
				        	}
				        }
					});
				</script>
			</div>		
		</html:form>
	</template:replace>
</template:include>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/resource/jsp/edit_top.jsp" %>
<%@ include file="/fssc/mobile/resource/jsp/mobile_include.jsp" %>
<%@ include file="/fssc/mobile/common/organization/organization_include.jsp" %>
<%
	session.setAttribute("S_PADFlag","1");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>
    	<c:if test="${fsscFeeMainForm.method_GET =='add'}">
    		${lfn:message("operation.create")}
    	</c:if>
    	<c:if test="${fsscFeeMainForm.method_GET =='edit'}">
    		${lfn:message("button.edit")}
    	</c:if>
    		-${lfn:message("fssc-fee:module.fssc.fee")}
    </title>
    <link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/newApplicationForm.css">
    <script>
    var formInitData = {
    		"LUI_ContextPath":"${LUI_ContextPath}",
    		"displayList":"${displayList}",
    		"budgetMatchList":"${budgetMatchList}"
    };
    </script>
    <script src="${LUI_ContextPath}/fssc/mobile/fssc_mobile_fee/fsscMobileFee_edit.js"></script>
    <script type="text/javascript">
		Com_IncludeFile("doclist.js");
		Com_IncludeFile("Number.js", "${LUI_ContextPath}/fssc/common/resource/js/", 'js', true);
		Com_IncludeFile("bmap.js", "${LUI_ContextPath}/fssc/mobile/resource/js/", 'js', true);
        Com_IncludeFile("calculation.js", "${LUI_ContextPath}/sys/xform/designer/calculation/", 'js', true);
		Com_IncludeFile("calculation_script.js", "${LUI_ContextPath}/sys/xform/designer/calculation/", 'js', true);		
		Com_IncludeFile("fee_mobile_expression.js", "${LUI_ContextPath}/fssc/mobile/fssc_mobile_fee/", 'js', true);
	</script>
	<kmss:ifModuleExist path="/fssc/budget">
      	<script>Com_IncludeFile("fee_mobile_submit_edit.js", "${LUI_ContextPath}/fssc/mobile/fssc_mobile_fee/", 'js', true);</script>
    </kmss:ifModuleExist>
        <style>
		      .formTextArea{
		      font-size: 0.28rem; color: #434343; height:100%; width:70%;
		      } 
		     .detailFormTextArea{
		      font-size: 0.28rem; color: #434343; height:100%; width:100%;float:right;
		      }   
    </style> 
</head>

<body>
<form action="${LUI_ContextPath }/fssc/fee/fssc_fee_mobile/fsscFeeMobile.do" name="fsscFeeMainForm"  method="post">
    <div class="ld-newApplicationForm">
        <div class="ld-newApplicationForm-header">
            <div class="ld-newApplicationForm-header-title">
            	<c:if test="${docTemplate.fdSubjectType=='1' }">
                	<input type="text" name="docSubject" style="width:95%;" validator="required" value="${fsscFeeMainForm.docSubject}" subject="${lfn:message('fssc-fee:fsscFeeMain.docSubject')}" placeholder="${lfn:message('fssc-mobile:fssc.mobile.fields.docSubject.placeholder')}">
                 	<span style="color:#d02300;">*</span>
                </c:if>
                <c:if test="${docTemplate.fdSubjectType=='2' }">
                	<input type="text" placeholder="${lfn:message('fssc-fee:py.BianHaoShengCheng')}" name="docSubject" readonly="readonly">
                </c:if>
            </div>
            <div class="ld-newApplicationForm-header-desc">
                <input type="text" value="${docTemplate.fdName }" readonly="readonly">
            </div>
        </div>
        	<c:forEach items="${mainFieldList}" var="mainField" varStatus="status">
        		<c:set var="mainProperty" value="${mainField['name']}"></c:set>
        		<c:if test="${fsscFeeMainForm.method_GET =='add'}">
        			<c:set var="fileNameValue" value="${mainField['init']['text']}"></c:set>
        			<c:set var="fileIdValue" value="${mainField['init']['value']}"></c:set>
        		</c:if>
        		<%-- 文本 --%>
        		<c:if test="${mainField['type']=='1'}">
        		<c:if test="${mainField['showStatus']=='1' or mainField['showStatus']=='2'}">
	        		<c:if test="${fsscFeeMainForm.method_GET =='edit'}">
	        			<c:set var="fileNameValue" value="${fsscFeeMainForm.extendDataFormInfo.formData[mainProperty]}"></c:set>
	        		</c:if>
        		<div class="ld-newApplicationForm-info">
        			<div>
        				 <c:set var="expressKey" value="extendDataFormInfo.value(${mainField['name']})"></c:set>
		                 <span class="ld-remember-label">${mainField['title']}</span>
		                 <c:if test="${mainField['showStatus']=='1'}">
		                 	<input type="text" validator="${mainField['validate']}"  onblur="caculate(this);" class="${expressMap[expressKey]==null?'':'mainCaculate'}" expression="${expressMap[expressKey]}" isRow="false"  calculation="${expressMap[name]==null?'true':'false'}" subject="${mainField['title']}" onchange="changeValue('${funcMap[mainField['name']]}');" value="${fileNameValue}" name="extendDataFormInfo.value(${mainField['name']})" placeholder="${lfn:message('fssc-mobile:fssc.mobile.placeholder.input')}${mainField['title']}" />
		             	 </c:if>
		                 <c:if test="${mainField['showStatus']=='2'}">
		                 	<c:choose>
		                 		<c:when test="${fn:endsWith(mainField['name'],'budget_status')}">
		                 			<c:set var="budgetStatus" value="${fsscFeeMainForm.extendDataFormInfo.formData[mainField['name']]}" />
		                 			<c:if test="${not empty budgetStatus}">
		                 				<span id="mainBudget" style="color:#000;"><bean:message bundle="fssc-fee" key="py.budget.${budgetStatus}"/></span>
		                 			</c:if>
		                 			<xform:text property="extendDataFormInfo.value(${mainField['name']})" showStatus="noShow"></xform:text>
		                 		</c:when>
		                 		<c:when test="${fn:endsWith(mainField['name'],'standard_status')}">
		                 			<c:set var="standardStatus" value="${fsscFeeMainForm.extendDataFormInfo.formData[mainField['name']]}" />
		                 			<c:if test="${not empty standardStatus}">
		                 				<span id="mainStandard" style="color:#000;"><bean:message bundle="fssc-fee" key="py.standard.${standardStatus}"/></span>
		                 			</c:if>
		                 			<xform:text property="extendDataFormInfo.value(${mainField['name']})" showStatus="noShow"></xform:text>
		                 		</c:when>
		                 		<c:otherwise>
		                 			<input readOnly type="text" validator="${mainField['required']}"  onblur="caculate(this);" class="${expressMap[expressKey]==null?'':'mainCaculate'}"  expression="${expressMap[expressKey]}" isRow="false"  calculation="${expressMap[name]==null?'true':'false'}" onchange="changeValue('${funcMap[mainField['name']]}');" value="${fileNameValue}" name="extendDataFormInfo.value(${mainField['name']})">
		                 		</c:otherwise>
		                 	</c:choose>
		             	 </c:if>
		             </div>
		        </div>
		        </c:if>
		        <c:if test="${mainField['showStatus']=='3'}">
                 	 <input type="hidden" initType="${fileNameValue}" onchange="changeValue('${funcMap[detailField['name']]}');" value="${fileNameValue}" name="extendDataFormInfo.value(${detailField['name']})" validator="${detailField['required']}" placeholder="${lfn:message('fssc-mobile:fssc.mobile.placeholder.input')}${mainField['title']}">
                 </c:if>
                  <c:if test="${fn:endsWith(mainField['name'],'budget_status')}">
			        	<input type="hidden" name="extendDataFormInfo.value(${fn:replace(mainField['name'], 'status', 'info')})" />
			        </c:if>
			        <c:if test="${fn:endsWith(mainField['name'],'standard_status')}">
			        	<input type="hidden" name="extendDataFormInfo.value(${fn:replace(mainField['name'], 'status', 'info')})" />
			        </c:if>
        		</c:if>
        		<%-- 日期选择 --%>
        		<c:if test="${mainField['type']=='2'}">
        			<c:if test="${fsscFeeMainForm.method_GET =='edit'}">
	        			<c:set var="fileNameValue" value="${fsscFeeMainForm.extendDataFormInfo.formData[mainProperty]}"></c:set>
	        		</c:if>
        		<div class="ld-newApplicationForm-info">
        			<div>
	                    <span class="ld-remember-label">${mainField['title']}</span>
	                    <c:if test="${mainField['showStatus']=='1'}">
	                    <div class="dateClass">
	                        <input type="text" validator="${mainField['validate']}" subject="${mainField['title']}" onchange="changeValue('${funcMap[mainField['name']]}');" placeholder="${lfn:message('fssc-mobile:fssc.mobile.placeholder.select')}${mainField['title']}" id="extendDataFormInfo.value(${mainField['name']})"
	                        	name="extendDataFormInfo.value(${mainField['name']})" value="${fileNameValue}" onclick="selectTime('extendDataFormInfo.value(${mainField['name']})','extendDataFormInfo.value(${mainField['name']})');" readonly="readonly">
	                        <i></i>
	                    </div>
	                    </c:if>
	                    <c:if test="${mainField['showStatus']=='2'}">
	                    <div class="dateClass">
	                        <input type="text" data-show="readOnly" id="extendDataFormInfo.value(${mainField['name']})"
	                        	name="extendDataFormInfo.value(${mainField['name']})" value="${fileNameValue}" onclick="selectTime('extendDataFormInfo.value(${mainField['name']})','extendDataFormInfo.value(${mainField['name']})');" readonly="readonly">
	                    </div>
	                    </c:if>
	                </div>
	            </div>
        		</c:if>
        		<%-- 对象选择 --%>
        		<c:if test="${mainField['type']=='3'}">
        			<c:set var="mainProperty" value="${mainField['name']}"></c:set>
        			<c:if test="${fsscFeeMainForm.method_GET =='edit'}">
        				<c:set var="fileIdValue" value="${fsscFeeMainForm.extendDataFormInfo.formData[mainProperty]}"></c:set>
        				<c:set var="mainProperty" value="${mainField['name']}_name"></c:set>
	        			<c:set var="fileNameValue" value="${fsscFeeMainForm.extendDataFormInfo.formData[mainProperty]}"></c:set>
	        		</c:if>
        			<div class="ld-newApplicationForm-info">
        			<div>
		                 <span class="ld-remember-label">${mainField['title']}</span>
		                 <c:if test="${mainField['showStatus']=='1'}">
		                 <div>
		                     <input type="text" validator="${mainField['validate']}" initType="${mainField['init']['text']}" subject="${mainField['title']}" onchange="changeValue('${funcMap[mainField['name']]}');" placeholder="${lfn:message('fssc-mobile:fssc.mobile.placeholder.select')}${mainField['title']}" name="extendDataFormInfo.value(${mainField['name']}_name)" value="${fileNameValue}" readonly="readonly" onclick="selectObject('extendDataFormInfo.value(${mainField['name']})','extendDataFormInfo.value(${mainField['name']}_name)','${mainField['dataSource']}','${mainField['baseOn']}');">
		                     <xform:text property="extendDataFormInfo.value(${mainField['name']})"  value="${fileIdValue}"  htmlElementProperties="initType='${mainField['init']['value']}'" showStatus="noShow"></xform:text>
		                    <i></i>
		                </div>
		                </c:if>
		                 <c:if test="${mainField['showStatus']=='2'}">
		                 <div>
		                     <input type="text"  name="extendDataFormInfo.value(${mainField['name']}_name)" initType="${mainField['init']['text']}" value="${fileNameValue}" readonly="readonly">
		                     <xform:text property="extendDataFormInfo.value(${mainField['name']})" htmlElementProperties="initType='${mainField['init']['value']}'"  value="${fileIdValue}" showStatus="noShow"></xform:text>
		                </div>
		                </c:if>
		             </div>
		            </div>
		            <c:forEach items="${mainField['other']}" var="other">
		            	<input type="hidden" name="extendDataFormInfo.value(${other['name']})" initType="${mainField['init']['text']}" />
		            </c:forEach>
        		</c:if>
        		<%-- 下拉选择 --%>
        		<c:if test="${mainField['type']=='9'}">
        			<div class="ld-newApplicationForm-info">
        			<div>
	                 <span class="ld-remember-label">${mainField['title']}</span>
	                 <div>
	                 	<c:set var="defaultValue" value=""/>
	                 	<c:set var="defaultValueText" value=""/>
	                 	<c:if test="${fsscFeeMainForm.method_GET =='add'}">
	                 		<c:set var="itemDefaultValue" value=";${mainField.defaultValue};"/>
	                 	</c:if>
	                 	<c:if test="${fsscFeeMainForm.method_GET =='edit'}">
	                 		<c:set var="itemDefaultValue" value=";${fsscFeeMainForm.extendDataFormInfo.formData[mainProperty]};"/>
	                 	</c:if>
	                 	<c:forEach items="${mainField['enumValues'] }" var="item">
	                 	<c:set var="itemValue" value=";${item.value};"/>
	                 	<c:if test="${fn:indexOf(itemDefaultValue,itemValue)>-1 }">
	                 		<c:set var="defaultValue" value="${item.value }"/>
	                 		<c:set var="defaultValueText" value="${item.text }"/>
	                 	</c:if>
	                 	</c:forEach>
	                 	<!-- 编辑状态 -->
	                 	<c:if test="${mainField['showStatus']=='1'}">
	                     <input validator=="${mainField['validate']}" type="text" validator="${mainField['validate']}" subject="${mainField['title']}" placeholder="${lfn:message('fssc-mobile:fssc.mobile.placeholder.select')}${mainField['title']}" name="extendDataFormInfo.value(${mainField['name']}_name)" value="${defaultValueText}" readonly="readonly" onclick="selectOption('extendDataFormInfo.value(${mainField['name']})','extendDataFormInfo.value(${mainField['name']}_name)','${mainField['enumValuesText'] }');">
	                     <xform:text property="extendDataFormInfo.value(${mainField['name']})" value="${defaultValue}" showStatus="noShow"></xform:text>
	                    <i></i>
	                    </c:if>
	                    <!-- 只读状态 -->
		                <c:if test="${mainField['showStatus']=='2'}">
			                 <div>
			                     <input type="text" validator="${mainField['validate']}" subject="${mainField['title']}" readonly placeholder="${lfn:message('fssc-mobile:fssc.mobile.placeholder.select')}${mainField['title']}" name="extendDataFormInfo.value(${mainField['name']}_name)" value="${defaultValueText}">
	                     		<xform:text property="extendDataFormInfo.value(${mainField['name']})" value="${defaultValue}" showStatus="noShow"></xform:text>
			                </div>
			            </c:if>
	                </div>
	                
	             </div>
	            </div>
        		</c:if>
        		<%-- 单项选择 --%>
        		<c:if test="${mainField['type']=='7'}">
        			<div class="ld-newApplicationForm-info">
        				<div>
		                 <span class="ld-remember-label">${mainField['title']}</span>
		                 <c:set var="defaultValue" value=""/>
		                 <c:forEach items="${mainField['enumValues'] }" var="item">
		                 	<c:if test="${fsscFeeMainForm.method_GET =='add'}">
			                 	<c:set var="itemDefaultValue" value=";${mainField.defaultValue};"/>
			                </c:if>
			                <c:if test="${fsscFeeMainForm.method_GET =='edit'}">
			                 	<c:set var="itemDefaultValue" value=";${fsscFeeMainForm.extendDataFormInfo.formData[mainProperty]};"/>
		                 	</c:if>
			                 	<c:set var="itemValue" value=";${item.value};"/>
			                 	<c:if test="${fn:indexOf(itemDefaultValue,itemValue)>-1 }">
			                 		<c:set var="defaultValue" value="${item.value }"/>
			                 	</c:if>
		                 	<div class="checkbox_item <c:if test="${fn:indexOf(itemDefaultValue,itemValue)>-1 }">checked</c:if>" style="margin-left:0.2rem;">
		                			<div class="checkbox_item_out" style="margin-top:0;">
		                				<div class="checkbox_item_in"></div>
		                			</div>
		                			<div class="checkbox_item_text">${item['text'] }</div>
		                			<!-- 编辑状态 -->
	                 			<c:if test="${mainField['showStatus']=='1'}">
		                			<input type="radio" name="_extendDataFormInfo.value(${mainField['name'] })" value="${item['value'] }" <c:if test="${fn:indexOf(itemDefaultValue,itemValue)>-1 }">checked</c:if>>
	                				</c:if>
	                			</div>
		                 </c:forEach>
		                 <input type="text" subject="${mainField['title']}" style="display:none;" validator="${mainField['validate']}" name="extendDataFormInfo.value(${mainField['name'] })" value="${defaultValue}">
		             </div>
		        </div>
        		</c:if>
        		<%-- 多项选择 --%>
        		<c:if test="${mainField['type']=='8'}">
        			<div class="ld-newApplicationForm-info">
        				<div>
		                 <span class="ld-remember-label">${mainField['title']}</span>
		                 <c:set var="defaultValue" value=""/>
		                 <c:forEach items="${mainField['enumValues'] }" var="item">
		                 <c:if test="${fsscFeeMainForm.method_GET =='add'}">
		                 	<c:set var="itemDefaultValue" value=";${mainField.defaultValue};"/>
		                 </c:if>
		                 <c:if test="${fsscFeeMainForm.method_GET =='edit'}">
		                 	<c:set var="itemDefaultValue" value=";${fsscFeeMainForm.extendDataFormInfo.formData[mainProperty]};"/>
		                 </c:if>
		                 	<c:set var="itemValue" value=";${item.value};"/>
		                 	<c:if test="${fn:indexOf(itemDefaultValue,itemValue)>-1 }">
		                 	<c:if test="${fn:length(defaultValue)>0 }">
		                 		<c:set var="defaultValue" value="${defaultValue };"/>
		                 	</c:if>
		                 		<c:set var="defaultValue" value="${defaultValue }${item.value }"/>
		                 	</c:if>
		                 	<div class="checkbox_item_multi <c:if test="${fn:indexOf(itemDefaultValue,itemValue)>-1 }">checked</c:if>">
		                			<div class="checkbox_item_out">
		                				<div class="checkbox_item_in"></div>
		                			</div>
		                			<div class="checkbox_item_text">${item['text'] }</div>
		                			<!-- 编辑状态 -->
	                 			<c:if test="${mainField['showStatus']=='1'}">
		                			<input type="checkbox" name="_extendDataFormInfo.value(${mainField['name'] })"  <c:if test="${fn:indexOf(itemDefaultValue,itemValue)>-1 }">checked</c:if> value="${item['value'] }">
	                				</c:if>
	                			</div>
		                 </c:forEach>
		                 <input type="text" subject="${mainField['title']}" style="display:none;" name="extendDataFormInfo.value(${mainField['name'] })" value="${defaultValue }">
		             </div>
		        </div>
        		</c:if>
        		<%-- 组织架构 --%>
        		<c:if test="${mainField['type']=='4'}">
        		<c:set var="mainProperty" value="${mainField['name']}"></c:set>
       			<c:if test="${fsscFeeMainForm.method_GET =='edit'}">
       				<c:set var="fileIdValue" value="${fsscFeeMainForm.extendDataFormInfo.formData[mainProperty]['id']}"></c:set>
        			<c:set var="fileNameValue" value="${fsscFeeMainForm.extendDataFormInfo.formData[mainProperty]['name']}"></c:set>
        		</c:if>
        		<div class="ld-newApplicationForm-info">
		            <div>
		                <span class="ld-remember-label">${mainField['title']}</span>
		                <c:if test="${mainField['showStatus']=='1'}">
		                <div class="ld-selectPersion" onclick="selectOrgElement('extendDataFormInfo.value(${mainField['name']}.id)','extendDataFormInfo.value(${mainField['name']}.name)','${mainField['init']['parentId']}','${mainField['multi']}','${mainField['orgType']}');">
		                    <input type="text" validator="${mainField['validate']}" onchange="changeValue('${funcMap[mainField['name']]}');" placeholder="${lfn:message('fssc-mobile:fssc.mobile.placeholder.select')}${mainField['title']}" name="extendDataFormInfo.value(${mainField['name']}.name)" value="${fileNameValue}" readonly="readonly" >
		                    <i></i>
		                </div>
		                </c:if>
		                <c:if test="${mainField['showStatus']=='2'}">
		                <div class="ld-selectPersion">
		                    <input type="text" validator="${mainField['validate']}"  name="extendDataFormInfo.value(${mainField['name']}.name)" value="${fileNameValue}" readonly="readonly" >
		                </div>
		                </c:if>
		                <xform:text property="extendDataFormInfo.value(${mainField['name']}.id)" showStatus="noShow" value="${fileIdValue}"></xform:text>
		            </div>
		        </div>
        		</c:if>
        		      		<%-- 多行文本 --%>
        		<c:if test="${mainField['type']=='10'}">
        		<c:if test="${mainField['showStatus']=='1' or mainField['showStatus']=='2'}">
	        		<c:if test="${fsscFeeMainForm.method_GET =='edit'}">
	        			<c:set var="fileNameValue" value="${fsscFeeMainForm.extendDataFormInfo.formData[mainProperty]}"></c:set>
	        		</c:if>
        		<div class="ld-newApplicationForm-info">
        			<div>
		                 <span class="ld-remember-label">${mainField['title']}</span>
		                 <c:if test="${mainField['showStatus']=='1'}">
		                 	<textArea  validator="${mainField['validate']}" class="formTextArea" subject="${mainField['title']}" onchange="changeValue('${funcMap[mainField['name']]}');" value="${fileNameValue}" name="extendDataFormInfo.value(${mainField['name']})" placeholder="${lfn:message('fssc-mobile:fssc.mobile.placeholder.input')}${mainField['title']}" >${fileNameValue}</textArea>
		             	 </c:if>
		             	 <c:if test="${mainField['showStatus']=='2'}">
		                 	<textArea readonly validator="${mainField['validate']}" class="formTextArea" subject="${mainField['title']}" onchange="changeValue('${funcMap[mainField['name']]}');" value="${fileNameValue}" name="extendDataFormInfo.value(${mainField['name']})" placeholder="${lfn:message('fssc-mobile:fssc.mobile.placeholder.input')}${mainField['title']}" >${fileNameValue}</textArea>		             	
		                 </c:if>
		                 <c:if test="${mainField['showStatus']=='3'}">
                 	     <input type="hidden" initType="${fileNameValue}" onchange="changeValue('${funcMap[detailField['name']]}');" value="${fileNameValue}" name="extendDataFormInfo.value(${detailField['name']})" validator="${detailField['required']}" placeholder="${lfn:message('fssc-mobile:fssc.mobile.placeholder.input')}${mainField['title']}">
                        </c:if>
		             </div>
		        </div>
		        </c:if>
		        </c:if>
        		<%-- 明细表 --%>
        		<c:if test="${mainField['type']=='5'}">
			        <div class="ld-newApplicationForm-costInfo">
			            <div class="ld-newApplicationForm-travelInfo-title">
			                <h3>${mainField['title']}</h3>
			                <i></i>
			            </div>
			            <ul id="${mainField['name']}">
			            	<table  id="TABLE_DL_${mainField['name']}" align="center"  style="width:100%;">
					             <tr KMSS_IsReferRow="1" style="display:none;width:100%;">
							       <td >
							            <input type="hidden" name="extendDataFormInfo.value(${mainField['name']}.!{index}.fdId)" value="" />
							       </td>
							       <td >
						       			<c:forEach items="${detailFieldMap[mainField['name']]}" var="detailField" varStatus="status">
							             	<%-- 文本、日期选择 --%>
							        		<c:if test="${detailField['type']=='1' or detailField['type']=='2' or detailField['type']=='10' }">
							        			<input type="hidden" name="extendDataFormInfo.value(${detailField['refer_name']})" value="" placeholder="${detailField['title']}" />
							        			<c:if test="${fn:endsWith(detailField['name'],'budget_status')}">
										        	<input type="hidden" name="extendDataFormInfo.value(${fn:replace(detailField['refer_name'], 'status', 'info')})" />
										        </c:if>
										        <c:if test="${fn:endsWith(detailField['name'],'standard_status')}">
										        	<input type="hidden" name="extendDataFormInfo.value(${fn:replace(detailField['refer_name'], 'status', 'info')})" />
										        </c:if>
							        		</c:if>
							        		<%-- 对象选择 --%>
							        		<c:if test="${detailField['type']=='3'}">
							                     <input type="hidden" name="extendDataFormInfo.value(${detailField['refer_name']}_name)" value="${detailField['init']['text']}">
							                     <input type="hidden" name="extendDataFormInfo.value(${detailField['refer_name']})" value="${detailField['init']['value']}" placeholder="${detailField['title']}" />
							        			 <c:forEach items="${detailField['other']}" var="other">
										         	<input type="hidden" name="extendDataFormInfo.value(${other['refer_name']})" />
										         </c:forEach>
							        		</c:if>
							        		<%-- 组织架构 --%>
							        		<c:if test="${detailField['type']=='4'}">
							                    <input type="hidden" name="extendDataFormInfo.value(${detailField['refer_name']}.name)" value="${detailField['init']['text']}" >
							                    <input type="hidden" name="extendDataFormInfo.value(${detailField['refer_name']}.id)" value="${detailField['init']['value']}" placeholder="${detailField['title']}" />
							        		</c:if>
							        		<%-- 单选 --%>
							        		<c:if test="${detailField['type']=='7'}">
							                    <input type="hidden" name="extendDataFormInfo.value(${detailField['refer_name']})" value="" >
							        		</c:if>
							        		<%-- 复选框 --%>
							        		<c:if test="${detailField['type']=='8'}">
							                    <input type="hidden" name="extendDataFormInfo.value(${detailField['refer_name']})" value="" >
							        		</c:if>
							        		<%-- 下拉选择 --%>
							        		<c:if test="${detailField['type']=='9'}">
							                    <input type="hidden" name="extendDataFormInfo.value(${detailField['refer_name']})" value="" >
							                    <input type="hidden" name="extendDataFormInfo.value(${detailField['refer_name']}_name)" value="" >
							        		</c:if>
							             </c:forEach>
							             <input name="detailIndex_.!{index}.${mainField['name']}" type="hidden" />
							        </td>
				       			 </tr>
				       			<c:set var="detailId" value="${mainField['name']}"></c:set>
				       			<c:forEach  items="${fsscFeeMainForm.extendDataFormInfo.formData[detailId]}"  var="xformEachBean"  varStatus="vstatus">
								    <tr KMSS_IsContentRow="1">
								    	<td>
								    		<xform:text showStatus="noShow" property="extendDataFormInfo.value(${detailId}.${vstatus.index}.fdId)" />
								    	</td>
										<td>
											<c:if test="${detailId eq fdTableId}">
												<div class="ld-notSubmit-list" style="padding: 0;">
													<ul>
														<li class="ld-notSubmit-list-item"
															onclick="editDetail('${mainField['name']}','1');">
															<div class="ld-newApplicationForm-travelInfo-top">
																<div>
																	<img src="${LUI_ContextPath}/fssc/mobile/resource/images/taxi.png" alt="" />
																	<c:set var="item_name" value="${fn:split(displayList[0],'.')[1]}_name"></c:set>
																	<span class="feeTypeName">${xformEachBean[item_name]}</span>
																	<c:set var="budget_status" value="${fn:split(displayList[4],'.')[1]}"></c:set>
																	<c:set var="standar_status" value="${fn:split(displayList[5],'.')[1]}"></c:set>
																	<c:if test="${not empty xformEachBean[budget_status] or not empty xformEachBean[standar_status]}">
																	<div class="ld-notSubmit-list-top">
																		<c:if test="${not empty xformEachBean[budget_status]}">
																			<span class="ld-notSubmit-entryType_${xformEachBean[budget_status]}">
																				<bean:message bundle="fssc-fee" key="py.budget.${xformEachBean[budget_status]}" />
																			</span>
																		</c:if>
																		<c:if test="${not empty xformEachBean[standar_status]}">
																			<span class="ld-notSubmit-entryType_${xformEachBean[standar_status]}">
																				<bean:message bundle="fssc-fee" key="py.standard.${xformEachBean[standar_status]}" />
																			</span>
																		</c:if>
																	</div>
																	</c:if>
																</div>
																<i onclick="deleteDetail('${detailId}');"></i>
															</div>
															<div class="ld-notSubmit-list-bottom">
																<div class="ld-notSubmit-list-bottom-info">
																	<div>
																		<span>${fn:substring(fsscFeeMainForm.docCreateTime, 0, 10)}</span>
																		<span class="ld-verticalLine"></span>
																		<c:set var="item_name" value="${fn:split(displayList[3],'.')[1]}"></c:set>
																		<span> 
																		<c:if test="${empty  xformEachBean[item_name]['name']}">
								                                        	${fsscFeeMainForm.docCreatorName}
								                                        </c:if> 
								                                        <c:if test="${not empty  xformEachBean[item_name]['name']}">
								                                        	${xformEachBean[item_name]["name"]}
								                                        </c:if>
																		</span>
																	</div>
																	<c:set var="item_name" value="${fn:split(displayList[2],'.')[1]}"></c:set>
																	<c:set var="currency_id" value="${xformEachBean[item_name]}"></c:set>
																	<c:set var="item_name" value="${fn:split(displayList[1],'.')[1]}"></c:set>
																	<span><span class="currency_code">${currencyMap[currency_id]}</span>${xformEachBean[item_name]}</span>
																</div>
																<p></p>
															</div>
														</li>
													</ul>
												</div>
											</c:if> 
											<c:if test="${detailId ne fdTableId}">
												<ul></ul>
												<li class="ld-notSubmit-list-item" onclick="editDetail('${mainField['name']}','1');">
													<div class="ld-newApplicationForm-travelInfo-top">
														<i onclick="deleteDetail('${detailId}');"></i>
													</div>
													<c:forEach items="${detailFieldMap[detailId]}" var="detailField" varStatus="status_">
														<div class="ld-entertain-detail-item">
															<%-- 文本 --%>
															<c:if test="${detailField['type']=='1'  or detailField['type']=='10'}">
																<c:if test="${detailField['showStatus']=='1' or detailField['showStatus']=='2' }">
																	<span>${detailField['title']}</span>：
													                <c:set var="property_name" value="${fn:split(detailField['name'], '.')[1]}"></c:set>
													                ${xformEachBean[property_name]}
															  	</c:if>
															</c:if>
															<%-- 日期选择 --%>
															<c:if test="${detailField['type']=='2'}">
																<span>${detailField['title']}</span>：
											                        <c:set var="property_name" value="${fn:split(detailField['name'], '.')[1]}"></c:set>
												                    ${xformEachBean[property_name]}
													        	</c:if>
															<%-- 对象选择 --%>
															<c:if test="${detailField['type']=='3'}">
																<span>${detailField['title']}</span>：
											                    <c:set var="property_name" value="${fn:split(detailField['name'], '.')[1]}_name"></c:set>
											                    ${xformEachBean[property_name]}
															</c:if>
															<%-- 组织架构 --%>
															<c:if test="${detailField['type']=='4'}">
																<span>${detailField['title']}</span>：
														        <c:set var="property_name" value="${fn:split(detailField['name'], '.')[1]}"></c:set>
														        ${xformEachBean[property_name]["name"]}
															</c:if>
															<%-- 下拉选择 --%>
															<c:if test="${detailField['type']=='9'}">
																<span>${detailField['title']}</span>：
														        <c:set var="property_name" value="${fn:split(detailField['name'], '.')[1]}" />
																<c:forEach items="${detailField['enumValues'] }" var="item">
																	<c:if test="${fn:indexOf(xformEachBean[property_name],item.value)>-1}"> 
																		${item.text };
													                </c:if>
																</c:forEach>
															</c:if>
															<%-- 单项选择 --%>
															<c:if test="${detailField['type']=='7'}">
																<span>${detailField['title']}</span>：
														        <c:set var="property_name" value="${fn:split(detailField['name'], '.')[1]}" />
																<c:forEach items="${detailField['enumValues'] }" var="item">
																	<c:if test="${fn:indexOf(xformEachBean[property_name],item.value)>-1}">
													                	${item.text }
													                </c:if>
																</c:forEach>
															</c:if>
															<%-- 多项选择 --%>
															<c:if test="${detailField['type']=='8'}">
																<span>${detailField['title']}</span>：
														        <c:set var="property_name" value="${fn:split(detailField['name'], '.')[1]}" />
																<c:set var="count" value="0" />
																<c:forEach items="${detailField['enumValues'] }" var="item">
																	<c:if test="${fn:indexOf(xformEachBean[property_name],item.value)>-1}">
																		<c:if test="${count>0 }">;</c:if>
																		<c:set var="count" value="${count+1 }" />
													                     	${item.text }
													                </c:if>
																</c:forEach>
															</c:if>
														</div>
													</c:forEach>
												</li>
											</c:if> 
											<!-- 编辑页面隐藏值 -->
				                            <c:forEach items="${detailFieldMap[mainField['name']]}" var="detailField" varStatus="status">
				                            	<c:set var="editIndex" value="${vstatus.index}"></c:set>
				                            	<c:if test="${not empty detailField['content_name']}">
				                            		<c:set var="content_name" value="${fn:replace(detailField['content_name'],'index',editIndex)}"></c:set>
				                            	</c:if>
				                            	<c:set var="property_name" value="${fn:split(detailField['name'], '.')[1]}"></c:set>
								             	<%-- 文本、日期选择 --%>
								        		<c:if test="${detailField['type']=='1' or detailField['type']=='2' or detailField['type']=='10'}">
								        			<input type="hidden" name="extendDataFormInfo.value(${content_name})" value="${xformEachBean[property_name]}" placeholder="${detailField['title']}" />
								        			<c:if test="${fn:endsWith(detailField['name'],'budget_status')}">
								        				<c:set var="property_name" value="${fn:split(fn:replace(content_name, 'status', 'info'), '.')[2]}"></c:set>
											        	<input type="hidden" name="extendDataFormInfo.value(${fn:replace(content_name, 'status', 'info')})" value="${xformEachBean[property_name]}" />
											        </c:if>
											        <c:if test="${fn:endsWith(detailField['name'],'standard_status')}">
											        	<c:set var="property_name" value="${fn:split(fn:replace(content_name, 'status', 'info'), '.')[2]}"></c:set>
											        	<input type="hidden" name="extendDataFormInfo.value(${fn:replace(content_name, 'status', 'info')})" value="${xformEachBean[property_name]}" />
											        </c:if>
								        		</c:if>
								        		<%-- 对象选择 --%>
								        		<c:if test="${detailField['type']=='3'}">
								        			<input type="hidden" name="extendDataFormInfo.value(${content_name})" value="${xformEachBean[property_name]}" placeholder="${detailField['title']}" />
								        			<c:set var="property_name" value="${fn:split(detailField['name'], '.')[1]}_name"></c:set>
								                    <input type="hidden" name="extendDataFormInfo.value(${content_name}_name)" value="${xformEachBean[property_name]}">
								        			<c:forEach items="${detailField['other']}" var="other">
								        			 	<c:if test="${not empty other['content_name']}">
						                            		<c:set var="other_name" value="${fn:replace(other['content_name'],'index',editIndex)}"></c:set>
						                            		<c:set var="property_name" value="${fn:split(other_name, '.')[2]}"></c:set>
						                            	</c:if>
											         	<input type="hidden" name="extendDataFormInfo.value(${other_name})" value="${xformEachBean[property_name]}" />
											         </c:forEach>
								        		</c:if>
								        		<%-- 组织架构 --%>
								        		<c:if test="${detailField['type']=='4'}">
								                    <input type="hidden" name="extendDataFormInfo.value(${content_name}.name)" value="${xformEachBean[property_name]['name']}" >
								                    <input type="hidden" name="extendDataFormInfo.value(${content_name}.id)" value="${xformEachBean[property_name]['id']}" placeholder="${detailField['title']}" />
								        		</c:if>
								        		<%-- 单选 --%>
								        		<c:if test="${detailField['type']=='7'}">
								                    <input type="hidden" name="extendDataFormInfo.value(${content_name})" value="${xformEachBean[property_name]}" >
								        		</c:if>
								        		<%-- 复选框 --%>
								        		<c:if test="${detailField['type']=='8'}">
								                    <input type="hidden" name="extendDataFormInfo.value(${content_name})" value="${xformEachBean[property_name]}" >
								        		</c:if>
								        		<%-- 下拉选择 --%>
								        		<c:if test="${detailField['type']=='9'}">
								                    <input type="hidden" name="extendDataFormInfo.value(${content_name})" value="${xformEachBean[property_name]}" >
								                 	<c:set var="defaultValueText" value=""/>
								                 	<c:set var="itemDefaultValue" value=";${xformEachBean[property_name]};"/>
								                 	<c:forEach items="${detailField['enumValues'] }" var="item">
									                 	<c:set var="itemValue" value=";${item.value};"/>
									                 	<c:if test="${fn:indexOf(itemDefaultValue,itemValue)>-1 }">
									                 		<c:set var="defaultValueText" value="${item.text }"/>
									                 	</c:if>
								                 	</c:forEach>
								                    <input type="hidden" name="extendDataFormInfo.value(${content_name}_name)" value="${defaultValueText}" >
								        		</c:if>
								             </c:forEach>
								    		<input name="detailIndex_.${vstatus.index}.${mainField['name']}" type="hidden" />
										</td>
									</tr>
							    </c:forEach>
							</table> 
							<script>
								DocList_Info.push("TABLE_DL_${mainField['name']}");
							</script>  
			            </ul>
			            <div class="ld-newApplicationForm-costInfo-btn" onclick="addDetail('${mainField['name']}','0');">
			                <i></i><span>${lfn:message('fssc-mobile:fssc.mobile.add')}${mainField['title']}</span>
			            </div>
			            <jsp:include page="/fssc/mobile/fssc_mobile_fee/fsscMobileFeeDetail_edit.jsp">
			            	<jsp:param name="detail_id" value="${mainField['name']}"/>
			            </jsp:include>
			        </div>
        		</c:if>
        		<%-- 附件 --%>
        		<c:if test="${mainField['type']=='6'}">
			        <div class="ld-line20px"></div>
			        <!-- 附件 -->
			        <div class="ld-newApplicationForm-attach">
			            <div class="ld-newApplicationForm-attach-title">
			                <h3>${mainField['title']}</h3>
			                <i></i>
			            </div>
			            <ul id="att_${mainField['name']}">
			                <c:forEach items="${attData}" var="att">
		                		 <li>
			                        <div class="ld-remember-attact-info" onclick="showAtt('${att['fdId']}','${att['fileName']}')" data-attid="${att['fdId']}">
			                            <img src="" alt="" data-file="${att['fileName']}">
			                            <span>${att['fileName']}</span>
			                        </div>
			                        <span onclick="deleteAtt('${att['fdId']}','${att['fileName']}');"></span>
			                    </li>
		                	</c:forEach>
			            </ul>
			            <div class="ld-newApplicationForm-attach-btn">
			                <i></i><span>${lfn:message('fssc-mobile:fssc.mobile.add')}${mainField['title']}</span>
			                <input type="file" id="${mainField['name']}_id" multiple="multiple" onchange="uploadFile(this.files,'${mainField['name']}');">
			            </div>
			        </div>
        		</c:if>
        	</c:forEach>
        	<%-- <c:import url="/sys/lbpmservice/import/sysLbpmProcess_edit.jsp" charEncoding="UTF-8">
		        <c:param name="formName" value="fsscFeeMainForm" />
		        <c:param name="fdKey" value="fsscFeeMain" />
		        <c:param name="isExpand" value="true" />
		    </c:import> --%>
        <div class="ld-footer">
            <c:if test="${fsscFeeMainForm.method_GET=='add' }">
          	 	<div class="ld-footer-whiteBg" style="width:25%;" onclick="submitForm(document.fsscFeeMainForm,'10','save');" >${ lfn:message('button.savedraft')}</div>
             	<div class="ld-footer-blueBg" style="width:65%;" onclick="submitForm(document.fsscFeeMainForm,'20','save');" >${ lfn:message('fssc-mobile:button.next') }</div>
          	</c:if>
          	<c:if test="${fsscFeeMainForm.method_GET=='edit' }">
          	 	<div class="ld-footer-whiteBg" style="width:25%;" onclick="submitForm(document.fsscFeeMainForm,'10','update');" >${ lfn:message('button.savedraft')}</div>
             	<div class="ld-footer-blueBg" style="width:65%;" onclick="submitForm(document.fsscFeeMainForm,'20','update');" >${ lfn:message('fssc-mobile:button.next') }</div>
          	</c:if>
        </div>
    </div>
    <!-- 侧边栏 -->
   
    <!-- 上传中 -->   
     <div class="ld-main" id="ld-main-upload" style="display: none;">
        <div class="ld-mask">
            <div class="ld-progress-modal">
                <img src="${LUI_ContextPath}/fssc/mobile/resource/images/loading.png" alt="">
                <span>${lfn:message('fssc-mobile:fssc.mobile.list.uploading')}</span>
            </div>
        </div>
    </div>
     <input type="hidden" name="fdId" value="${fsscFeeMainForm.fdId }" />
     <input type="hidden" name="method_GET" value="${fsscFeeMainForm.method_GET }" />
     <input type="hidden" name="fdTemplateId" value="${docTemplate.fdId }" />
     <input type="hidden" name="docCreateTime" value="${fsscFeeMainForm.docCreateTime }" />
     <input type="hidden" name="docCreatorName" value="${fsscFeeMainForm.docCreatorName }" />
      <input type="hidden" name="docCreatorId" value="${fsscFeeMainForm.docCreatorId }" />
     <xform:text property="docStatus" value="${fsscFeeMainForm.docStatus }" showStatus="noShow"></xform:text>
     <input hidden="true" value="${isShowDraftsmanStatus}" name="isShowDraftsmanStatus" />
     <!-- 记账公司ID -->
     <input type="hidden" name="fdCompanyId" value="${fdCompanyId}" />
     <!-- 台账映射字段 -->
     <input type="hidden" name="fdMappFeild" value="${fdMappFeild}" />
    </form>
</body>
</html>

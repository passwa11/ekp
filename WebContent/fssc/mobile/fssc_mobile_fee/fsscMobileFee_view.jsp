<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/resource/jsp/edit_top.jsp" %>
<%@ include file="/fssc/mobile/common/attachement/attachment_view.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>${lfn:message('fssc-mobile:fssc.mobile.view.title')}</title>
    <link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/reset.css">
    <link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/common.css">
    <link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/rememberOne.css">
    <link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/viewApplicationForm.css">
    <link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/feeDetail.css">
    <script src="${LUI_ContextPath}/fssc/mobile/resource/js/common.js"></script>
    <script src="${LUI_ContextPath}/fssc/mobile/resource/js/rem.js"></script>
    <script src="${LUI_ContextPath}/fssc/mobile/fssc_mobile_fee/fsscMobileFee_view.js"></script>
    <script src="${LUI_ContextPath}/fssc/mobile/common/attachement/attachment.js"></script>
    <kmss:ifModuleExist path="/fssc/ctrip/">
    	<script src="${LUI_ContextPath}/fssc/ctrip/fssc_ctrip_control/js/CtripJS.js"></script>
    </kmss:ifModuleExist>
    <style>
		      .textarea-view{
		      font-size: 0.28rem; color: #434343; height:100%; width:100%;
		      }  
		      .textarea-span{
		      width:80%;
		      }
		      .textarea-detail-view{
		      font-size: 0.28rem; color: #434343; height:100%; width:70%;
		      }  
    </style> 
</head>
<body>
    <div class="ld-new-reimbursement-form">
        <div class="ld-new-reimbursement-form-head">
            <div class="ld-new-reimbursement-form-head-customer">
                <span>${fsscFeeMainForm.docSubject}</span>
            </div>
            <div class="ld-new-reimbursement-form-head-info">
                <div class="ld-new-reimbursement-form-head-info-department">
                  <div>
                    <span class="ld-new-reimbursement-form-head-info-name">${fsscFeeMainForm.docCreatorName}</span> 
                    <span class="ld-line"></span>
                    <span>${docDeptName}</span>
                  </div>
                  <span><sunbor:enumsShow value="${fsscFeeMainForm.docStatus}" enumsType="common_status" /></span>
                </div>
                <p></p>
            </div>
        </div>
        <div class="ld-entertain-main">
                <c:forEach items="${mainFieldList}" var="mainField" varStatus="status">
	        		<%-- 文本 --%>
	        		<c:if test="${mainField['type']=='1'}">
	        		<c:if test="${mainField['showStatus']=='1' or mainField['showStatus']=='2'}">
	        		<div class="ld-entertain-detail">
        				<div class="ld-entertain-detail-costInfo">
	        			<div>
			                 <span>${mainField['title']}</span>
			                 <span>
			                 	<c:choose>
			                 		<c:when test="${fn:endsWith(mainField['name'],'budget_status')}">
			                 			<c:set var="budgetStatus" value="${fsscFeeMainForm.extendDataFormInfo.formData[mainField['name']]}" />
			                 			<c:if test="${not empty budgetStatus}">
			                 				<bean:message bundle="fssc-fee" key="py.budget.${budgetStatus}"/>
			                 			</c:if>
			                 			<xform:text property="extendDataFormInfo.value(${mainField['name']})" showStatus="noShow"></xform:text>
			                 		</c:when>
			                 		<c:when test="${fn:endsWith(mainField['name'],'standard_status')}">
			                 			<c:set var="standardStatus" value="${fsscFeeMainForm.extendDataFormInfo.formData[mainField['name']]}" />
			                 			<c:if test="${not empty standardStatus}">
			                 				<bean:message bundle="fssc-fee" key="py.standard.${standardStatus}"/>
			                 			</c:if>
			                 			<xform:text property="extendDataFormInfo.value(${mainField['name']})" showStatus="noShow"></xform:text>
			                 		</c:when>
			                 		<c:otherwise>
			                 			<xform:text property="extendDataFormInfo.value(${mainField['name']})" showStatus="view"></xform:text>
			                 		</c:otherwise>
			                 	</c:choose>
			                 </span>
			             </div>
			             </div>
    				</div>
    				</c:if>
	        		</c:if>
	        		<%-- 日期选择--%>
	        		<c:if test="${mainField['type']=='2'}">
	        		<div class="ld-entertain-detail">
        				<div class="ld-entertain-detail-costInfo">
	        			<div>
		                    <span>${mainField['title']}</span>
		                    <span><xform:text property="extendDataFormInfo.value(${mainField['name']})" showStatus="view"></xform:text></span>
		                </div>
		                </div>
		            </div>
	        		</c:if>
	        		<%-- 对象选择--%>
	        		<c:if test="${mainField['type']=='3'}">
	        		<div class="ld-entertain-detail">
        				<div class="ld-entertain-detail-costInfo">
	        			<div>
			                 <span>${mainField['title']}</span>
			                 <span><xform:text property="extendDataFormInfo.value(${mainField['name']}_name)" showStatus="view"></xform:text></span>
		                     <xform:text property="extendDataFormInfo.value(${mainField['name']})" value="${mainField['init']['value']}" showStatus="noShow"></xform:text>
			             </div>
			             </div>
			        </div>
	        		</c:if>
	        		<%-- 组织架构--%>
	        		<c:if test="${mainField['type']=='4'}">
			        <div class="ld-entertain-detail">
        				<div class="ld-entertain-detail-costInfo">
			            <div>
			                <span>${mainField['title']}</span>
		                    <span><xform:text property="extendDataFormInfo.value(${mainField['name']}.name)" showStatus="view"></xform:text></span>
		                    <xform:text property="extendDataFormInfo.value(${mainField['name']}.id)" showStatus="noShow" value="${mainField['init']['value']}"></xform:text>
			            </div>
			            </div>
			        </div>
	        		</c:if>
	        		<%-- 下拉选择 --%>
	        		<c:if test="${mainField['type']=='9'}">
	        			<div class="ld-entertain-detail">
        				<div class="ld-entertain-detail-costInfo">
        					<div>
			             <span>${mainField['title']}</span>
		                 <c:set var="property_name" value="${mainField['name']}"/>
	                     <c:forEach items="${mainField['enumValues'] }" var="item">
	                     	<c:if test="${fn:indexOf(fsscFeeMainForm.extendDataFormInfo.formData[property_name],item.value)>-1}">
	                     		${item.text };
	                     	</c:if>
	                     </c:forEach>
	                     </div>
			         </div>
			         </div>
	        		</c:if>
	        		<%-- 单项选择 --%>
	        		<c:if test="${mainField['type']=='7'}">
	        			<div class="ld-entertain-detail">
        				<div class="ld-entertain-detail-costInfo">
        					<div>
	        				<span>${mainField['title']}</span>
		                 <c:set var="property_name" value="${mainField['name']}"/>
		                 <span>
	                     <c:forEach items="${mainField['enumValues'] }" var="item">
	                     	<c:if test="${fn:indexOf(fsscFeeMainForm.extendDataFormInfo.formData[property_name],item.value)>-1}">
	                     		${item.text }
	                     	</c:if>
	                     </c:forEach>
	                     </span>
	                     </div>
			        </div>
			        </div>
	        		</c:if>
	        		<%-- 多项选择 --%>
	        		<c:if test="${mainField['type']=='8'}">
	        			<div class="ld-entertain-detail">
        				<div class="ld-entertain-detail-costInfo">
        					<div>
		                 <span>${mainField['title']}</span>
		                 <c:set var="property_name" value="${mainField['name']}"/>
		                 <c:set var="count" value="0"/>
	                     <c:forEach items="${mainField['enumValues'] }" var="item">
	                     	<c:if test="${fn:indexOf(fsscFeeMainForm.extendDataFormInfo.formData[property_name],item.value)>-1}">
	                     		<c:if test="${count>0 }">;</c:if>
								<c:set var="count" value="${count+1 }"/>
	                     		${item.text }
	                     	</c:if>
	                     </c:forEach>
	                     </div>
		             </div>
		             </div>
	        		</c:if>
				<%-- 多行文本 --%>
				<c:if test="${mainField['type']=='10'}">
					<c:if test="${mainField['showStatus']=='1' or mainField['showStatus']=='2'}">
						<div class="ld-entertain-detail">
							<div class="ld-entertain-detail-costInfo">
								<div>
									<span>${mainField['title']}</span>
									 <span class="textarea-span">
									<textarea readonly="readonly" class="textarea-view">${fsscFeeMainForm.extendDataFormInfo.formData[mainField['name']]}</textarea>
									</span>
								</div>
							</div>
						</div>
					</c:if>
				</c:if>

				<%-- 明细表--%>
	        		<c:if test="${mainField['type']=='5'}">
				        <div class="ld-new-reimbursement-form-main">
				            <div class="ld-new-reimbursement-form-main-info">
				                <div class="ld-new-reimbursement-form-main-info-title">
				                    <span>${mainField['title']}</span>
				                    <i></i>
				                </div>
				                <c:set var="detailId" value="${mainField['name']}"></c:set>
				                <c:forEach  items="${fsscFeeMainForm.extendDataFormInfo.formData[detailId]}"  var="xformEachBean"  varStatus="vstatus">
									<tr KMSS_IsContentRow="1">
										<td class="detail_wrap_td">
											<xform:text showStatus="noShow" property="extendDataFormInfo.value(${detailId}.${vstatus.index}.fdId)" />
							                <div class="ld-notSubmit-list" style="padding:0;">
								                <ul>
								                    <li class="ld-notSubmit-list-item">
								                       <label>
								                            <div class="ld-checkBox">
								                            <input type="checkbox" name="costDetail" value="0"  checked />
								                            <span class="checkbox-label"></span>
								                        </div>
								                        <div class="ld-notSubmit-list-item-box" onclick="viewDetail('${mainField['name']}','${vstatus.index}');">
								                        <c:if test="${detailId eq fdTableId}">
								                            <div class="ld-notSubmit-list-top">
								                            	<c:set var="item_name" value="${displayList[0]}_name"></c:set>
								                                <img src="${LUI_ContextPath}/fssc/mobile/resource/images/taxi.png" alt=""><span>${xformEachBean[item_name]}</span>
								                                <c:set var="budget_status" value="${displayList[4]}"></c:set>
								                                <c:if test="${not empty xformEachBean[budget_status]}">
								                                	 <span class="ld-notSubmit-entryType_${xformEachBean[budget_status]}">
									                                	<bean:message bundle="fssc-fee" key="py.budget.${xformEachBean[budget_status]}"/>
									                                </span>
								                                </c:if>
								                                <c:set var="standar_status" value="${displayList[5]}"></c:set>
								                                <c:if test="${not empty xformEachBean[standar_status]}">
								                                <span class="ld-notSubmit-entryType_${xformEachBean[standar_status]}">
																	<bean:message bundle="fssc-fee" key="py.standard.${xformEachBean[standar_status]}"/>
																</span>
																</c:if>
								                            </div>
								                            <div class="ld-notSubmit-list-bottom">
								                                <div class="ld-notSubmit-list-bottom-info">
								                                    <div>
								                                        <span>${fn:substring(fsscFeeMainForm.docCreateTime, 0, 10)}</span>
								                                        <span class="ld-verticalLine"></span>
								                                        <c:set var="item_name" value="${displayList[3]}.name"></c:set>
								                                        <span>
								                                        <c:if test="${empty  xformEachBean[displayList[3]]['name']}">
								                                        	${fsscFeeMainForm.docCreatorName}
								                                        </c:if>
								                                        <c:if test="${not empty  xformEachBean[displayList[3]]['name']}">
								                                        	${xformEachBean[displayList[3]]["name"]}
								                                        </c:if>
								                                        </span>
								                                    </div>
																	<c:set var="currency_id" value="${xformEachBean[displayList[2]]}"></c:set>
								                                    <span>${currencyMap[currency_id]}${xformEachBean[displayList[1]]}</span>
								                                </div>
								                                <p></p>
								                            </div>
								                            </c:if>
								                            <c:if test="${detailId ne fdTableId}">
								                            <c:forEach items="${detailFieldMap[detailId]}" var="detailField" varStatus="status_">
								                				<div class="ld-entertain-detail-item">
												                    <%-- 文本 --%>
													        		<c:if test="${detailField['type']=='1'}">
													        		<c:if test="${detailField['showStatus']=='1' or detailField['showStatus']=='2'}">
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
													                     <input type="hidden" initType="${detailField['init']['value']}" name="extendDataFormInfo.value(${detailField['name']})" value="${detailField['init']['value']}" />
															            <c:forEach items="${detailField['other']}" var="other">
															            	<c:set var="property_name" value="${fn:split(other['name'], '.')[1]}"></c:set>
															            	<input type="hidden" name="extendDataFormInfo.value(${other['name']})" value="${xformEachBean[property_name]}" />
															            </c:forEach>
													        		</c:if>
													        		<%-- 组织架构 --%>
													        		<c:if test="${detailField['type']=='4'}">
														                <span>${detailField['title']}</span>：
														                <c:set var="property_name" value="${fn:split(detailField['name'], '.')[1]}"></c:set>
														                ${xformEachBean[property_name]["name"]}
														                <input type="hidden" name="extendDataFormInfo.value(${detailField['name']}.id)" value="${detailField['init']['value']}" />
													        		</c:if>
													        		<%-- 下拉选择 --%>
													        		<c:if test="${detailField['type']=='9'}">
															             <span>${detailField['title']}</span>：
														                 <c:set var="property_name" value="${fn:split(detailField['name'], '.')[1]}"/>
													                     <c:forEach items="${detailField['enumValues'] }" var="item">
													                     	<c:if test="${fn:indexOf(xformEachBean[property_name],item.value)>-1}">
													                     		${item.text };
													                     	</c:if>
													                     </c:forEach>
													        		</c:if>
													        		<%-- 单项选择 --%>
													        		<c:if test="${detailField['type']=='7'}">
													        			<span>${detailField['title']}</span>：
														                 <c:set var="property_name" value="${fn:split(detailField['name'], '.')[1]}"/>
													                     <c:forEach items="${detailField['enumValues'] }" var="item">
													                     	<c:if test="${fn:indexOf(xformEachBean[property_name],item.value)>-1}">
													                     		${item.text }
													                     	</c:if>
													                     </c:forEach>
													        		</c:if>
													        		<%-- 多项选择 --%>
													        		<c:if test="${detailField['type']=='8'}">
														                 <span>${detailField['title']}</span>：
														                 <c:set var="property_name" value="${fn:split(detailField['name'], '.')[1]}"/>
														                 <c:set var="count" value="0"/>
													                     <c:forEach items="${detailField['enumValues'] }" var="item">
													                     	<c:if test="${fn:indexOf(xformEachBean[property_name],item.value)>-1}">
													                     		<c:if test="${count>0 }">;</c:if>
													                     		<c:set var="count" value="${count+1 }"/>
													                     		${item.text }
													                     	</c:if>
													                     </c:forEach>
													        		</c:if>
													        		<%-- 多行文本 --%>
													        		<c:if test="${detailField['type']=='10'}">
													        		<c:if test="${detailField['showStatus']=='1' or detailField['showStatus']=='2'}">
														                 <span>${detailField['title']}</span>：
														                 <c:set var="property_name" value="${fn:split(detailField['name'], '.')[1]}"></c:set>
																		 <span class="textarea-span">
																		<textarea readonly="readonly" class="textarea-detail-view">${xformEachBean[property_name]}</textarea>
																		</span>
															        </c:if>
													        		</c:if>
													            </div>
													         </c:forEach>
								                			</c:if>
								                        </div>
								                       </label>
								                    </li>
								                </ul>
								    		</div>
										</td>
										<%--明细表详情展现 --%>
										<div class="ld-entertain-main-body" id="div${vstatus.index}_${detailId}">
									     <div class="ld-entertain-detail">
									         <div class="ld-entertain-detail-costInfo">
									             <c:forEach items="${detailFieldMap[detailId]}" var="detailField" varStatus="status">
									             	<%-- 文本 --%>
									        		<c:if test="${detailField['type']=='1'}">
									        		<c:if test="${detailField['showStatus']=='1' or detailField['showStatus']=='2'}">
									        		<div class="ld-entertain-detail-item">
											                 <span class="ld-remember-label">${detailField['title']}</span>
											                 <c:set var="property_name" value="${fn:split(detailField['name'], '.')[1]}"></c:set>
											                 ${xformEachBean[property_name]}
											        </div>
											        </c:if>
									        		</c:if>
									        		<%-- 日期选择 --%>
									        		<c:if test="${detailField['type']=='2'}">
									        		<div class="ld-entertain-detail-item">
										                    <span>${detailField['title']}</span>
										                    <div class="dateClass">
										                        <c:set var="property_name" value="${fn:split(detailField['name'], '.')[1]}"></c:set>
											                    ${xformEachBean[property_name]}
										                    </div>
										            </div>
									        		</c:if>
									        		<%-- 对象选择 --%>
									        		<c:if test="${detailField['type']=='3'}">
									        			<div class="ld-entertain-detail-item">
											                 <span>${detailField['title']}</span>
											                 <div>
											                     <c:set var="property_name" value="${fn:split(detailField['name'], '.')[1]}_name"></c:set>
											                     ${xformEachBean[property_name]}
											                     <input type="hidden" initType="${detailField['init']['value']}" name="extendDataFormInfo.value(${detailField['name']})" value="${detailField['init']['value']}" />
											                </div>
											            </div>
											            <c:forEach items="${detailField['other']}" var="other">
											            	<c:set var="property_name" value="${fn:split(other['name'], '.')[1]}"></c:set>
											            	<input type="hidden" name="extendDataFormInfo.value(${other['name']})" value="${xformEachBean[property_name]}" />
											            </c:forEach>
									        		</c:if>
									        		<%-- 组织架构 --%>
									        		<c:if test="${detailField['type']=='4'}">
									        		<div class="ld-entertain-detail-item">
											                <span>${detailField['title']}</span>
											                <div class="ld-selectPersion">
											                <c:set var="property_name" value="${fn:split(detailField['name'], '.')[1]}"></c:set>
											                	${xformEachBean[property_name]["name"]}
											                    <input type="hidden" name="extendDataFormInfo.value(${detailField['name']}.id)" value="${detailField['init']['value']}" />
											                </div>
											        </div>
									        		</c:if>
									        		<%-- 下拉选择 --%>
									        		<c:if test="${detailField['type']=='9'}">
									        			<div class="ld-entertain-detail-item">
											             <span class="ld-remember-label">${detailField['title']}</span>
										                 <c:set var="property_name" value="${fn:split(detailField['name'], '.')[1]}"/>
									                     <c:forEach items="${detailField['enumValues'] }" var="item">
									                     	<c:if test="${fn:indexOf(xformEachBean[property_name],item.value)>-1}">
									                     		${item.text };
									                     	</c:if>
									                     </c:forEach>
											         </div>
									        		</c:if>
									        		<%-- 单项选择 --%>
									        		<c:if test="${detailField['type']=='7'}">
									        			<div class="ld-entertain-detail-item">
									        				<span class="ld-remember-label">${detailField['title']}</span>
										                 <c:set var="property_name" value="${fn:split(detailField['name'], '.')[1]}"/>
									                     <c:forEach items="${detailField['enumValues'] }" var="item">
									                     	<c:if test="${fn:indexOf(xformEachBean[property_name],item.value)>-1}">
									                     		${item.text }
									                     	</c:if>
									                     </c:forEach>
											        </div>
									        		</c:if>
									        		<%-- 多项选择 --%>
									        		<c:if test="${detailField['type']=='8'}">
									        			<div class="ld-entertain-detail-item">
										                 <span class="ld-remember-label">${detailField['title']}</span>
										                 <c:set var="property_name" value="${fn:split(detailField['name'], '.')[1]}"/>
										                 <c:set var="count" value="0"/>
									                     <c:forEach items="${detailField['enumValues'] }" var="item">
									                     	<c:if test="${fn:indexOf(xformEachBean[property_name],item.value)>-1}">
									                     		<c:if test="${count>0 }">;</c:if>
									                     		<c:set var="count" value="${count+1 }"/>
									                     		${item.text }
									                     	</c:if>
									                     </c:forEach>
										             </div>
									        		</c:if>
									        		    		<%-- 多行文本 --%>
									        		<c:if test="${detailField['type']=='10'}">
									        		<c:if test="${detailField['showStatus']=='1' or detailField['showStatus']=='2'}">
									        		<div class="ld-entertain-detail-item">
											                 <span class="ld-remember-label">${detailField['title']}</span>
											                 <c:set var="property_name" value="${fn:split(detailField['name'], '.')[1]}"></c:set>
											                 <textarea readonly="readonly" class="textarea-detail-view">${xformEachBean[property_name]}</textarea>
											        </div>
											        </c:if>
									        		</c:if>
									             </c:forEach>
									         </div>
									     </div>
									     <div class="ld-entertain-detail-btn" onclick="rtnMain('${detailId}','${vstatus.index}');">${lfn:message('fssc-mobile:fssc.mobile.view.rtn.main.button') }</div>
									 </div>
									</tr>
								</c:forEach>
				            </div>
				        </div>
	        		</c:if>
	        		<%-- 附件--%>
	        		<c:if test="${mainField['type']=='6'}">
	        		<div class="ld-rememberOne" style="padding-bottom:0.001rem;">
       				 <div class="ld-rememberOne-main">
		        		<div class="ld-remember-attach">
			                <div class="ld-remember-attach-title">
			                    <h3>${mainField['title']}</h3>
			                    <i></i>
			                </div>
			                <ul>
			                	<c:forEach items="${attData}" var="att">
			                		 <li onclick="showAtt('${att['fdId']}','${att['fileName']}');">
				                        <div class="ld-remember-attact-info">
				                            <img src="" alt="" data-file="${att['fileName']}">
				                            <span>${att['fileName']}</span>
				                        </div>
				                    </li>
			                	</c:forEach>
			                </ul>
			            </div>
			            </div>
			            </div>
	        		</c:if> 
	        	</c:forEach>
	        	<kmss:ifModuleExist path="/fssc/ctrip/">
	        	<c:if test="${fn:indexOf(docTemplate.fdServiceType,'plane')>-1  and fsscFeeMainForm.docStatus=='30'}">
	        	<div class="ld-entertain-detail">
        			<div class="ld-entertain-detail-costInfo">
		       			<div>
		                    <span>
		                    	<div style="width: 75px;height: 28px;line-height: 28px;text-align: center;color: #fff;background: #47b5ea; border-radius: 5px;" props="{'docNumberId':'docNumber'}"  onclick="bookticketOfPlaneMobile(this);" >${lfn:message('fssc-ctrip:message.sso.plane') }</div>
		                    </span>
		                </div>
	                </div>
                </div>
                </c:if>
                <c:if test="${fn:indexOf(docTemplate.fdServiceType,'hotel')>-1 and fsscFeeMainForm.docStatus=='30'}">
	        	<div class="ld-entertain-detail">
        			<div class="ld-entertain-detail-costInfo">
		       			<div>
		                    <span>
		                    	<div style="width: 75px;height: 28px;line-height: 28px;text-align: center;color: #fff;background: #47b5ea; border-radius: 5px;" props="{'docNumberId':'docNumber'}"  onclick="bookticketOfHotelMobile(this);" >${lfn:message('fssc-ctrip:message.sso.hotel') }</div>
		                    </span>
		                </div>
	                </div>
                </div>
                </c:if>
                <c:if test="${fn:indexOf(docTemplate.fdServiceType,'train')>-1  and fsscFeeMainForm.docStatus=='30'}">
	        	<div class="ld-entertain-detail">
        			<div class="ld-entertain-detail-costInfo">
		       			<div>
		                    <span>
		                    	<div style="width: 75px;height: 28px;line-height: 28px;text-align: center;color: #fff;background: #47b5ea; border-radius: 5px;" props="{'docNumberId':'docNumber'}"  onclick="bookticketOfTrainMobile(this);" >${lfn:message('fssc-ctrip:message.sso.train') }</div>
		                    </span>
		                </div>
	                </div>
                </div>
                </c:if>
                </kmss:ifModuleExist>
				<kmss:ifModuleExist path="/fssc/alibtrip/">
					<c:if test="${(fn:indexOf(docTemplate.fdServiceType,'plane')>-1  or fn:indexOf(docTemplate.fdServiceType,'train')>-1 or fn:indexOf(docTemplate.fdServiceType,'hotel')>-1)  and fsscFeeMainForm.docStatus=='30'}">
						<div class="ld-entertain-detail">
							<div class="ld-entertain-detail-costInfo">
								<div>
									<span>${lfn:message('fssc-mobile:button.alibtrip.home.button')}</span>
									<span>
									<div style="width: 75px;height: 28px;line-height: 28px;text-align: center;color: #fff;background: #47b5ea; border-radius: 5px;"  onclick="Com_OpenWindow('${LUI_ContextPath}/fssc/alibtrip/fssc_alibtrip_sso/fsscAlibtripSso.do?method=openAlitripBackGroup&fdCompanyId=${fdCompanyId}&action_type=4','_self');" >${lfn:message('fssc-mobile:button.alibtrip.home')}</div>
								</span>
								</div>
							</div>
						</div>
					</c:if>
				</kmss:ifModuleExist>
	        	<div class="ld-entertain-detail">
        			<div class="ld-entertain-detail-costInfo">
		       			<div>
		                    <span>${lfn:message('fssc-fee:fsscFeeMain.docCreateTime')}</span>
		                    <span><xform:text property="docCreateTime" value="${fsscFeeMainForm.docCreateTime}" showStatus="view"></xform:text></span>
		                </div>
	                </div>
                </div>
        <!-- 流程处理 -->
        <div class="ld-new-reimbursement-form-progress">
        	<iframe id="frame" width="100%" frameborder="0"  height="500px" src ="${LUI_ContextPath}/fssc/fee/fssc_fee_main/fsscFeeMain.do?method=viewLbpm&fdId=${fsscFeeMainForm.fdId}"></iframe>
        </div>
    </div>
    <kmss:auth requestURL="/fssc/fee/fssc_fee_main/fsscFeeMain.do?method=edit&fdId=${param.fdId}">
    <c:if test="${fsscFeeMainForm.docStatus =='10' or fsscFeeMainForm.docStatus =='11'}">
          <div class="editModel" onclick="javascript:window.location.href='${LUI_ContextPath}/fssc/fee/fssc_fee_mobile/fsscFeeMobile.do?method=edit&fdId=${fsscFeeMainForm.fdId }&i.docTemplate=${fsscFeeMainForm.docTemplateId}'"></div>
    </c:if>
    </kmss:auth>
    </div>
    <div class="backHome" onclick="javascript:window.location.href='${LUI_ContextPath}/fssc/mobile/'"></div>
</body>
</html>

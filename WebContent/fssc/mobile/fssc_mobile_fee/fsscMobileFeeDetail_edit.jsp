<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%-- 新增申请明细 --%>
 <div class="ld-entertain-main-body" id="div_${param.detail_id}">
     <div class="ld-entertain-detail">
         <div class="ld-entertain-detail-costInfo">
             <c:forEach items="${detailFieldMap[param.detail_id]}" var="detailField" varStatus="status">
             	<%-- 文本 --%>
        		<c:if test="${detailField['type']=='1'}">
        		<c:if test="${detailField['showStatus']=='1' or detailField['showStatus']=='2'}">
        		<div class="ld-entertain-detail-item">
        		 <c:set var="expressKey" value="extendDataFormInfo.value(${detailField['name']})"></c:set>
		                 <span class="ld-remember-label">${detailField['title']}</span>
		                 <c:if test="${detailField['showStatus']=='1'}">
		                 	 <input type="text" class="detail" initType="${detailField['init']['text']}" onblur="caculate(this);" expression="${expressMap[expressKey]}" isRow="true"  calculation="${expressMap[expressKey]==null?'false':'true'}" onchange="changeValue('${funcMap[detailField['name']]}');" value="${detailField['init']['text']}" name="extendDataFormInfo.value(${detailField['name']})" validator="${detailField['validate']}" subject="${detailField['title']}" placeholder="${lfn:message('fssc-mobile:fssc.mobile.placeholder.input')}${detailField['title']}">
		                 </c:if>
		                 <c:if test="${detailField['showStatus']=='2'}">
		                 	 <input readOnly type="text" class="detail" initType="${detailField['init']['text']}" onblur="caculate(this);" expression="${expressMap[expressKey]}" isRow="true"  calculation="${expressMap[expressKey]==null?'false':'true'}" onchange="changeValueAndCaculate('${funcMap[detailField['name']]}',this);" value="${detailField['init']['text']}" name="extendDataFormInfo.value(${detailField['name']})">
		                 </c:if>
		        </div>
		        </c:if>
		        <c:if test="${detailField['showStatus']=='3'}">
                 	 <input type="hidden" initType="${detailField['init']['text']}" class="detail" onchange="changeValue('${funcMap[detailField['name']]}');" value="${detailField['init']['text']}" name="extendDataFormInfo.value(${detailField['name']})"  placeholder="${lfn:message('fssc-mobile:fssc.mobile.placeholder.input')}${detailField['title']}">
                 </c:if>
		        <c:if test="${fn:endsWith(detailField['name'],'budget_status')}">
		        	<input type="hidden" class="detail" name="extendDataFormInfo.value(${fn:replace(detailField['name'], 'status', 'info')})" />
		        </c:if>
		        <c:if test="${fn:endsWith(detailField['name'],'standard_status')}">
		        	<input type="hidden" class="detail" name="extendDataFormInfo.value(${fn:replace(detailField['name'], 'status', 'info')})" />
		        </c:if>
        		</c:if>
        		<%-- 日期选择 --%>
        		<c:if test="${detailField['type']=='2'}">
        		<div class="ld-entertain-detail-item">
	                    <span>${detailField['title']}</span>
	                    <c:if test="${detailField['showStatus']=='1'}">
	                    <div class="dateClass">
	                        <input type="text" class="detail" initType="${detailField['init']['text']}"  onchange="changeValue('${funcMap[detailField['name']]}');" validator="${detailField['validate']} __date" subject="${detailField['title']}" placeholder="${lfn:message('fssc-mobile:fssc.mobile.placeholder.select')}${detailField['title']}" id="extendDataFormInfo.value(${detailField['name']})"
	                        	name="extendDataFormInfo.value(${detailField['name']})" value="${detailField['init']['text']}" onclick="selectTime('extendDataFormInfo.value(${detailField['name']})','extendDataFormInfo.value(${detailField['name']})');" readonly="readonly">
	                        <i></i>
	                    </div>
	                    </c:if>
	                    <c:if test="${detailField['showStatus']=='2'}">
	                    <div class="dateClass">
	                        <input type="text" data-show="readOnly" class="detail" initType="${detailField['init']['text']}" readOnly id="extendDataFormInfo.value(${detailField['name']})"
	                        	name="extendDataFormInfo.value(${detailField['name']})" value="${detailField['init']['text']}" readonly="readonly">
	                    </div>
	                    </c:if>
	            </div>
        		</c:if>
        		<%-- 对象选择 --%>
        		<c:if test="${detailField['type']=='3'}">
        			<div class="ld-entertain-detail-item">
		                 <span>${detailField['title']}</span>
		                 <c:if test="${detailField['showStatus']=='1'}">
		                 <div>
		                     <input initType="${detailField['init']['text']}" class="detail" type="text" subject="${detailField['title']}" onchange="changeValue('${funcMap[detailField['name']]}');" validator="${detailField['validate']}" placeholder="${lfn:message('fssc-mobile:fssc.mobile.placeholder.select')}${detailField['title']}" name="extendDataFormInfo.value(${detailField['name']}_name)" value="${detailField['init']['text']}" readonly="readonly" onclick="selectObject('extendDataFormInfo.value(${detailField['name']})','extendDataFormInfo.value(${detailField['name']}_name)','${detailField['dataSource']}','${detailField['baseOn']}');">
		                      <input type="hidden" class="detail" initType="${detailField['init']['value']}" name="extendDataFormInfo.value(${detailField['name']})" value="${detailField['init']['value']}" />
		                    <i></i>
		                </div>
		                </c:if>
		                 <c:if test="${detailField['showStatus']=='2'}">
		                 <div>
		                     <input initType="${detailField['init']['text']}" class="detail" type="text" readOnly name="extendDataFormInfo.value(${detailField['name']}_name)" value="${detailField['init']['text']}" readonly="readonly">
		                      <input type="hidden" class="detail" initType="${detailField['init']['value']}" name="extendDataFormInfo.value(${detailField['name']})" value="${detailField['init']['value']}" />
		                </div>
		                </c:if>
		            </div>
		            <c:forEach items="${detailField['other']}" var="other">
		            	<input type="hidden" class="detail" name="extendDataFormInfo.value(${other['name']})" />
		            </c:forEach>
        		</c:if>
        		<%-- 下拉选择 --%>
        		<c:if test="${detailField['type']=='9'}">
        			<div class="ld-entertain-detail-item">
	                 <span class="ld-remember-label">${detailField['title']}</span>
	                 <div>
	                 	<c:set var="defaultValue" value=""/>
	                 	<c:set var="defaultValueText" value=""/>
	                 	<c:set var="itemDefaultValue" value=";${detailField.defaultValue};"/>
	                 	<c:forEach items="${detailField['enumValues'] }" var="item">
	                 	<c:set var="itemValue" value=";${item.value};"/>
	                 	<c:if test="${fn:indexOf(itemDefaultValue,itemValue)>-1 }">
	                 		<c:set var="defaultValue" value="${item.value }"/>
	                 		<c:set var="defaultValueText" value="${item.text }"/>
	                 	</c:if>
	                 	</c:forEach>
	                 	<!-- 编辑状态 -->
	                 	<c:if test="${detailField['showStatus']=='1'}">
	                     <input type="text" <c:if test="${not empty defaultValue }">inittype="${defaultValueText }"</c:if> class="detail" value="${defaultValueText }" validator="${detailField['validate']}" subject="${detailField['title']}" placeholder="${lfn:message('fssc-mobile:fssc.mobile.placeholder.select')}${detailField['title']}" name="extendDataFormInfo.value(${detailField['name']}_name)" readonly="readonly" onclick="selectOption('extendDataFormInfo.value(${detailField['name']})','extendDataFormInfo.value(${detailField['name']}_name)','${detailField['enumValuesText'] }');">
	                     <input type="hidden" <c:if test="${not empty defaultValue }">inittype="${defaultValue }"</c:if> name="extendDataFormInfo.value(${detailField['name']})" value="${defaultValue }"/>
	                    <i></i>
	                    </c:if>
	                    <c:if test="${detailField['showStatus']=='2'}">
	                     <input type="text" <c:if test="${not empty defaultValue }">inittype="${defaultValueText }"</c:if> class="detail" value="${defaultValueText }" validator="${detailField['validate']}" subject="${detailField['title']}" placeholder="${lfn:message('fssc-mobile:fssc.mobile.placeholder.select')}${detailField['title']}" name="extendDataFormInfo.value(${detailField['name']}_name)" readonly="readonly">
	                     <input type="hidden" <c:if test="${not empty defaultValue }">inittype="${defaultValue }"</c:if> name="extendDataFormInfo.value(${detailField['name']})" value="${defaultValue }"/>
	                    </c:if>
	                </div>
	            </div>
        		</c:if>
        		<%-- 单项选择 --%>
        		<c:if test="${detailField['type']=='7'}">
        			<div class="ld-entertain-detail-item">
	                 <span class="ld-remember-label">${detailField['title']}</span>
	                 <c:set var="defaultValue" value=""/>
	                 <c:forEach items="${detailField['enumValues'] }" var="item">
	                 	<c:set var="itemDefaultValue" value=";${detailField.defaultValue};"/>
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
                 			<c:if test="${detailField['showStatus']=='1'}">
	                			<input type="radio" <c:if test="${not empty itemDefaultValue }">inittype="${itemDefaultValue }"</c:if> <c:if test="${fn:indexOf(itemDefaultValue,itemValue)>-1 }">checked</c:if> name="_extendDataFormInfo.value(${detailField['name'] })" value="${item['value'] }">
                				</c:if>
                			</div>
	                 </c:forEach>
	                 <input type="text" validator="${detailField['validate']}" subject="${detailField['title']}" style="display:none;" <c:if test="${not empty defaultValue }">inittype="${defaultValue }"</c:if> name="extendDataFormInfo.value(${detailField['name'] })" value="${defaultValue}">
		        </div>
        		</c:if>
        		<%-- 多项选择 --%>
        		<c:if test="${detailField['type']=='8'}">
        			<div class="ld-entertain-detail-item">
	                 <span class="ld-remember-label">${detailField['title']}</span>
	                 <c:set var="defaultValue" value=""/>
	                 <c:forEach items="${detailField['enumValues'] }" var="item">
	                 	<c:set var="itemDefaultValue" value=";${detailField.defaultValue};"/>
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
                 			<c:if test="${detailField['showStatus']=='1'}">
	                			<input <c:if test="${not empty itemDefaultValue }">inittype="${itemDefaultValue }"</c:if> type="checkbox" <c:if test="${fn:indexOf(itemDefaultValue,itemValue)>-1 }">checked</c:if> name="_extendDataFormInfo.value(${detailField['name'] })" value="${item['value'] }"/>
                				</c:if>
                			</div>
	                 </c:forEach>
	                 <input type="text" validator="${detailField['validate']}" subject="${detailField['title']}" style="display:none;" <c:if test="${not empty defaultValue }">inittype="${defaultValue}"</c:if> name="extendDataFormInfo.value(${detailField['name'] })" value="${defaultValue }">
		        </div>
        		</c:if>
        		<%-- 组织架构 --%>
        		<c:if test="${detailField['type']=='4'}">
        		<div class="ld-entertain-detail-item">
		                <span>${detailField['title']}</span>
		                <c:if test="${detailField['showStatus']=='1'}">
		                <div class="ld-selectPersion" onclick="selectOrgElement('extendDataFormInfo.value(${detailField['name']}.id)','extendDataFormInfo.value(${detailField['name']}.name)','${detailField['init']['parentId']}','${detailField['multi']}','${detailField['orgType']}');">
		                    <input class="detail" onchange="changeValue('${funcMap[detailField['name']]}');" initType="${detailField['init']['text']}" type="text" validator="${detailField['validate']}" subject="${detailField['title']}" placeholder="${lfn:message('fssc-mobile:fssc.mobile.placeholder.select')}${detailField['title']}" name="extendDataFormInfo.value(${detailField['name']}.name)" value="${detailField['init']['text']}" readonly="readonly" >
		                    <input type="hidden" class="detail" initType="${detailField['init']['value']}" name="extendDataFormInfo.value(${detailField['name']}.id)" value="${detailField['init']['value']}" />
		                    <i></i>
		                </div>
		                </c:if>
		                <c:if test="${detailField['showStatus']=='2'}">
		                <div class="ld-selectPersion">
		                    <input class="detail"  initType="${detailField['init']['text']}" type="text"  name="extendDataFormInfo.value(${detailField['name']}.name)" value="${detailField['init']['text']}" readonly="readonly" >
		                    <input type="hidden" class="detail" initType="${detailField['init']['value']}" name="extendDataFormInfo.value(${detailField['name']}.id)" value="${detailField['init']['value']}" />
		                </div>
		                </c:if>
		        </div>
        		</c:if>
                  <%-- 多行文本 --%>
				<c:if test="${detailField['type']=='10'}">
					<c:if test="${detailField['showStatus']=='1' or detailField['showStatus']=='2'}">
						<div class="ld-entertain-detail-item">
							<span class="ld-remember-label">${detailField['title']}</span>
							<c:if test="${detailField['showStatus']=='1'}">
								<span>
								<textArea class="detailFormTextArea"
									initType="${detailField['init']['text']}" isRow="true"
									onchange="changeValue('${funcMap[detailField['name']]}');"
									value="${detailField['init']['text']}"
									name="extendDataFormInfo.value(${detailField['name']})"
									validator="${detailField['validate']}"
									subject="${detailField['title']}"
									placeholder="${lfn:message('fssc-mobile:fssc.mobile.placeholder.input')}${detailField['title']}">${detailField['init']['text']}</textArea>
									</span>
							</c:if>
							<c:if test="${detailField['showStatus']=='2'}">
								<textArea readOnly  class="detailFormTextArea"
									initType="${detailField['init']['text']}" isRow="true"
									onchange="changeValueAndCaculate('${funcMap[detailField['name']]}',this);"
									value="${detailField['init']['text']}"
									name="extendDataFormInfo.value(${detailField['name']})">${detailField['init']['text']}</textArea>
							</c:if>
						</div>
					</c:if>
					<c:if test="${detailField['showStatus']=='3'}">
						<textArea type="hidden" initType="${detailField['init']['text']}"
							class="detail"
							onchange="changeValue('${funcMap[detailField['name']]}');"
							value="${detailField['init']['text']}"
							name="extendDataFormInfo.value(${detailField['name']})"
							placeholder="${lfn:message('fssc-mobile:fssc.mobile.placeholder.input')}${detailField['title']}">${detailField['init']['text']}</textArea>
					</c:if>
					</c:if>
			</c:forEach>
         </div>
     </div>
     <input name="editFlag" value="" type="hidden" />
     <input name="detailIndex" value="" type="hidden" />
     <input name="currency_code" value="" type="hidden" />
     <div class="detail_btn">
     <div class="ld-addAccount-btn-cancel" onclick="cancelDetail('${param.detail_id}');" style="">${ lfn:message('button.cancel') }</div>
     <div class="ld-entertain-detail-btn" onclick="saveDetail('${param.detail_id}');">${ lfn:message('button.save') }</div>
     </div>
 </div>

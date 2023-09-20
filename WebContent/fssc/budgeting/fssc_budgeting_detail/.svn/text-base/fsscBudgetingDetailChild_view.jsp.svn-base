<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/WEB-INF/KmssConfig/fssc/budgeting/budgeting.tld" prefix="budgeting" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<table class="tb_normal" width="100%" id="TABLE_DocList_fdDetailList_Form" align="center" tbdraggable="true">
	<tr align="center" class="tr_normal_title">
		<c:if test="${(fsscBudgetingMainForm.fdStatus=='2' and approvalAuth) or (fsscBudgetingMainForm.fdStatus=='4' and effectAuth)}">
		<td style="width:20px;">
			<input type="checkbox" name="List_Tongle">
		</td>
		</c:if>
	    <td style="width:40px;">
	        ${lfn:message('page.serial')}
	    </td>
	    <budgeting:showBudgetingDetail type="title" fdSchemeId="${fsscBudgetingMainForm.fdSchemeId}"></budgeting:showBudgetingDetail>
         <td style="text-align: center; min-width: 85px;">
             ${lfn:message('fssc-budgeting:fsscBudgetingDetail.gather.item')}
         </td>
         <td style="width:6%;">
             ${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdTotal')}
         </td>
         <td style="width:5%;">
             ${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodOne')}
         </td>
         <td style="width:5%;">
             ${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodTwo')}
         </td>
         <td style="width:5%;">
             ${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodThree')}
         </td>
         <td style="width:5%;">
             ${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodFour')}
         </td>
         <td style="width:5%;">
             ${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodFive')}
         </td>
         <td style="width:5%;">
             ${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodSix')}
         </td>
         <td style="width:5%;">
             ${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodSeven')}
         </td>
         <td style="width:5%;">
             ${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodEight')}
         </td>
         <td style="width:5%;">
             ${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodNine')}
         </td>
         <td style="width:5%;">
             ${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodTen')}
         </td>
         <td style="width:5%;">
             ${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodEleven')}
         </td>
         <td style="width:5%;">
             ${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodTwelve')}
         </td>
	</tr>
	<c:forEach items="${fsscBudgetingMainForm.fdDetailList_Form}" var="fdDetailList_FormItem" varStatus="vstatus">
		<c:if test="${empty oneLine}">
	    <tr KMSS_IsContentRow="1">
	    	<!-- 只审核最末级 -->
	    	<c:if test="${ fsscBudgetingMainForm.fdStatus=='2'||fsscBudgetingMainForm.fdStatus=='4'}">
	    		<c:choose>
		    		<c:when test="${((fdDetailList_FormItem.fdStatus=='2' and approvalAuth) or (fdDetailList_FormItem.fdStatus=='4' and effectAuth)) and (fdDetailList_FormItem.fdIsLastStage=='1' or noBudgetItem)}">
	    			 <td align="center">
			             <input type='checkbox' name='List_Selected' value="${fdDetailList_FormItem.fdId}" />
			         </td>
			        </c:when>
		    		<c:when test="${(fsscBudgetingMainForm.fdStatus=='2' and approvalAuth) or (fsscBudgetingMainForm.fdStatus=='4' and effectAuth)}">
		    			<td align="center">
			            
			         	</td>
			        </c:when>
			        <c:otherwise>
			        </c:otherwise>
		        </c:choose>
	    	</c:if>
	        <td align="center">
	            ${vstatus.index+1}
	            <xform:text property="fdDetailList_Form[${vstatus.index}].docMainId" htmlElementProperties="id='fdDetailList_Form[${vstatus.index}].docMainId'" showStatus="noShow"></xform:text>
	            <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdId" value="${fdDetailList_FormItem.fdId}" />
	            <xform:text property="fdDetailList_Form[${vstatus.index}].fdParentId" htmlElementProperties="id='fdDetailList_Form[${vstatus.index}].fdParentId'" showStatus="noShow"></xform:text>
                <xform:text property="fdDetailList_Form[${vstatus.index}].fdIsLastStage" htmlElementProperties="id='fdDetailList_Form[${vstatus.index}].fdIsLastStage'" showStatus="noShow"></xform:text>
                <xform:text property="fdDetailList_Form[${vstatus.index}].fdBudgetItemId" htmlElementProperties="id='fdDetailList_Form[${vstatus.index}].fdBudgetItemId'" showStatus="noShow"></xform:text>
	        </td>
	        <budgeting:showBudgetingDetail type="contentline" fdSchemeId="${fsscBudgetingMainForm.fdSchemeId}" detailForm="${fdDetailList_FormItem}" method="view" tdIndex="${vstatus.index}"></budgeting:showBudgetingDetail>
	        <td align="center">
             	${lfn:message('fssc-budgeting:fsscBudgetingDetail.gather.parent')}
           		<hr />
           		${lfn:message('fssc-budgeting:fsscBudgetingDetail.surplus.canApply')}
           		<hr />
           		${lfn:message('fssc-budgeting:fsscBudgetingDetail.gather.new.budgeting')}
             </td>
	        <td align="center">
                 <%-- 全年--%>
                 <div id="_xform_fdDetailList_Form[${vstatus.index}].fdTotal" _xform_type="text">
                 	 <!-- 上级预算 -->
                     <input id="fdDetailList_Form[${vstatus.index}].fdParentTotal" class="inputsgl" style="width:38px;color: #333;text-align: center;" readonly="readonly" />
                     <hr />
                     <input id="fdDetailList_Form[${vstatus.index}].fdTotal" class="inputsgl" style="width:38px;color: #333;text-align: center;" readonly="readonly" />
                     <input id="fdDetailList_noShow_Form[${vstatus.index}].fdTotal" type="hidden" />
                     <hr />
                   	 <xform:text property="fdDetailList_Form[${vstatus.index}].fdTotal" onValueChange="shareAmount" subject="${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdTotal')}" validators="required number" style="width:95%;color: #333;" />
                 </div>
             </td>
             <td align="center">
                 <%-- 1期--%>
                 <div id="_xform_fdDetailList_Form[${vstatus.index}].fdPeriodOne" _xform_type="text">
                     <input id="fdDetailList_Form[${vstatus.index}].fdParentPeriodOne" class="inputsgl" style="width:38px;color: #333;text-align: center;" readonly="readonly" />
                     <hr />
                     <input id="fdDetailList_Form[${vstatus.index}].fdPeriodOne" class="inputsgl" style="width:38px;color: #333;text-align: center;" readonly="readonly" />
                     <input id="fdDetailList_noShow_Form[${vstatus.index}].fdPeriodOne" type="hidden" />
                     <hr />
                     <xform:text property="fdDetailList_Form[${vstatus.index}].fdPeriodOne" onValueChange="recountMonthAmount" subject="${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodOne')}" validators="required number" style="width:95%;color: #333;" />
                 </div>
             </td>
             <td align="center">
                 <%-- 2期--%>
                 <div id="_xform_fdDetailList_Form[${vstatus.index}].fdPeriodTwo" _xform_type="text">
	                 <input id="fdDetailList_Form[${vstatus.index}].fdParentPeriodTwo" class="inputsgl" style="width:38px;color: #333;text-align: center;" readonly="readonly" />
                     <hr />
	                 <input id="fdDetailList_Form[${vstatus.index}].fdPeriodTwo" class="inputsgl" style="width:38px;color: #333;text-align: center;" readonly="readonly" />
                     <input id="fdDetailList_noShow_Form[${vstatus.index}].fdPeriodTwo" type="hidden" />
                     <hr />
                     <xform:text property="fdDetailList_Form[${vstatus.index}].fdPeriodTwo" onValueChange="recountMonthAmount" subject="${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodTwo')}" validators="required number" style="width:95%;color: #333;" />
                 </div>
             </td>
             <td align="center">
                 <%-- 3期--%>
                 <div id="_xform_fdDetailList_Form[${vstatus.index}].fdPeriodThree" _xform_type="text">
                     <input id="fdDetailList_Form[${vstatus.index}].fdParentPeriodThree" class="inputsgl" style="width:38px;color: #333;text-align: center;" readonly="readonly" />
                     <hr />
                     <input id="fdDetailList_Form[${vstatus.index}].fdPeriodThree" class="inputsgl" style="width:38px;color: #333;text-align: center;" readonly="readonly" />
                     <input id="fdDetailList_noShow_Form[${vstatus.index}].fdPeriodThree" type="hidden" />
                     <hr />
                     <xform:text property="fdDetailList_Form[${vstatus.index}].fdPeriodThree" onValueChange="recountMonthAmount" subject="${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodThree')}" validators="required number" style="width:95%;color: #333;" />
                 </div>
             </td>
             <td align="center">
                 <%-- 4期--%>
                 <div id="_xform_fdDetailList_Form[${vstatus.index}].fdPeriodFour" _xform_type="text">
                     <input id="fdDetailList_Form[${vstatus.index}].fdParentPeriodFour" class="inputsgl" style="width:38px;color: #333;text-align: center;" readonly="readonly" />
                     <hr />
                     <input id="fdDetailList_Form[${vstatus.index}].fdPeriodFour" class="inputsgl" style="width:38px;color: #333;text-align: center;" readonly="readonly" />
                     <input id="fdDetailList_noShow_Form[${vstatus.index}].fdPeriodFour" type="hidden" />
                     <hr />
                     <xform:text property="fdDetailList_Form[${vstatus.index}].fdPeriodFour" onValueChange="recountMonthAmount" subject="${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodFour')}" validators="required number" style="width:95%;color: #333;" />
                 </div>
             </td>
             <td align="center">
                 <%-- 5期--%>
                 <div id="_xform_fdDetailList_Form[${vstatus.index}].fdPeriodFive" _xform_type="text">
                     <input id="fdDetailList_Form[${vstatus.index}].fdParentPeriodFive" class="inputsgl" style="width:38px;color: #333;text-align: center;" readonly="readonly" />
                     <hr />
                     <input id="fdDetailList_Form[${vstatus.index}].fdPeriodFive" class="inputsgl" style="width:38px;color: #333;text-align: center;" readonly="readonly" />
                     <input id="fdDetailList_noShow_Form[${vstatus.index}].fdPeriodFive" type="hidden" />
                     <hr />
                     <xform:text property="fdDetailList_Form[${vstatus.index}].fdPeriodFive" onValueChange="recountMonthAmount" subject="${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodFive')}" validators="required number" style="width:95%;color: #333;" />
                 </div>
             </td>
             <td align="center">
                 <%-- 6期--%>
                 <div id="_xform_fdDetailList_Form[${vstatus.index}].fdPeriodSix" _xform_type="text">
                     <input id="fdDetailList_Form[${vstatus.index}].fdParentPeriodSix" class="inputsgl" style="width:38px;color: #333;text-align: center;" readonly="readonly" />
                     <hr />
                     <input id="fdDetailList_Form[${vstatus.index}].fdPeriodSix" class="inputsgl" style="width:38px;color: #333;text-align: center;" readonly="readonly" />
                     <input id="fdDetailList_noShow_Form[${vstatus.index}].fdPeriodSix" type="hidden" />
                     <hr />
                     <xform:text property="fdDetailList_Form[${vstatus.index}].fdPeriodSix" onValueChange="recountMonthAmount" subject="${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodSix')}" validators="required number" style="width:95%;color: #333;" />
                 </div>
             </td>
             <td align="center">
                 <%-- 7期--%>
                 <div id="_xform_fdDetailList_Form[${vstatus.index}].fdPeriodSeven" _xform_type="text">
                     <input id="fdDetailList_Form[${vstatus.index}].fdParentPeriodSeven" class="inputsgl" style="width:38px;color: #333;text-align: center;" readonly="readonly" />
                     <hr />
                     <input id="fdDetailList_Form[${vstatus.index}].fdPeriodSeven" class="inputsgl" style="width:38px;color: #333;text-align: center;" readonly="readonly" />
                     <input id="fdDetailList_noShow_Form[${vstatus.index}].fdPeriodSeven" type="hidden" />
                     <hr />
                     <xform:text property="fdDetailList_Form[${vstatus.index}].fdPeriodSeven" onValueChange="recountMonthAmount" subject="${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodSeven')}" validators="required number" style="width:95%;color: #333;" />
                 </div>
             </td>
             <td align="center">
                 <%-- 8期--%>
                 <div id="_xform_fdDetailList_Form[${vstatus.index}].fdPeriodEight" _xform_type="text">
                     <input id="fdDetailList_Form[${vstatus.index}].fdParentPeriodEight" class="inputsgl" style="width:38px;color: #333;text-align: center;" readonly="readonly" />
                     <hr />
                     <input id="fdDetailList_Form[${vstatus.index}].fdPeriodEight" class="inputsgl" style="width:38px;color: #333;text-align: center;" readonly="readonly" />
                     <input id="fdDetailList_noShow_Form[${vstatus.index}].fdPeriodEight" type="hidden" />
                     <hr />
                     <xform:text property="fdDetailList_Form[${vstatus.index}].fdPeriodEight" onValueChange="recountMonthAmount" subject="${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodEight')}" validators="required number" style="width:95%;color: #333;" />
                 </div>
             </td>
             <td align="center">
                 <%-- 9期--%>
                 <div id="_xform_fdDetailList_Form[${vstatus.index}].fdPeriodNine" _xform_type="text">
                     <input id="fdDetailList_Form[${vstatus.index}].fdParentPeriodNine" class="inputsgl" style="width:38px;color: #333;text-align: center;" readonly="readonly" />
                     <hr />
                     <input id="fdDetailList_Form[${vstatus.index}].fdPeriodNine" class="inputsgl" style="width:38px;color: #333;text-align: center;" readonly="readonly" />
                     <input id="fdDetailList_noShow_Form[${vstatus.index}].fdPeriodNine" type="hidden" />
                     <hr />
                     <xform:text property="fdDetailList_Form[${vstatus.index}].fdPeriodNine" onValueChange="recountMonthAmount" subject="${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodNine')}" validators="required number" style="width:95%;color: #333;" />
                 </div>
             </td>
             <td align="center">
                 <%-- 10期--%>
                 <div id="_xform_fdDetailList_Form[${vstatus.index}].fdPeriodTen" _xform_type="text">
                     <input id="fdDetailList_Form[${vstatus.index}].fdParentPeriodTen" class="inputsgl" style="width:38px;color: #333;text-align: center;" readonly="readonly" />
                     <hr />
                     <input id="fdDetailList_Form[${vstatus.index}].fdPeriodTen" class="inputsgl" style="width:38px;color: #333;text-align: center;" readonly="readonly" />
                     <input id="fdDetailList_noShow_Form[${vstatus.index}].fdPeriodTen" type="hidden" />
                     <hr />
                     <xform:text property="fdDetailList_Form[${vstatus.index}].fdPeriodTen" onValueChange="recountMonthAmount" subject="${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodTen')}" validators="required number" style="width:95%;color: #333;" />
                 </div>
             </td>
             <td align="center">
                 <%-- 11期--%>
                 <div id="_xform_fdDetailList_Form[${vstatus.index}].fdPeriodEleven" _xform_type="text">
                     <input id="fdDetailList_Form[${vstatus.index}].fdParentPeriodEleven" class="inputsgl" style="width:38px;color: #333;text-align: center;" readonly="readonly" />
                     <hr />
                     <input id="fdDetailList_Form[${vstatus.index}].fdPeriodEleven" class="inputsgl" style="width:38px;color: #333;text-align: center;" readonly="readonly" />
                     <input id="fdDetailList_noShow_Form[${vstatus.index}].fdPeriodEleven" type="hidden" />
                     <hr />
                     <xform:text property="fdDetailList_Form[${vstatus.index}].fdPeriodEleven" onValueChange="recountMonthAmount" subject="${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodEleven')}" validators="required number" style="width:95%;color: #333;" />
                 </div>
             </td>
             <td align="center">
                 <%-- 12期--%>
                 <div id="_xform_fdDetailList_Form[${vstatus.index}].fdPeriodTwelve" _xform_type="text">
                     <input id="fdDetailList_Form[${vstatus.index}].fdParentPeriodTwelve" class="inputsgl" style="width:38px;color: #333;text-align: center;" readonly="readonly" />
                     <hr />
                     <input id="fdDetailList_Form[${vstatus.index}].fdPeriodTwelve" class="inputsgl" style="width:38px;color: #333;text-align: center;" readonly="readonly" />
                     <input id="fdDetailList_noShow_Form[${vstatus.index}].fdPeriodTwelve" type="hidden" />
                     <hr />
                     <xform:text property="fdDetailList_Form[${vstatus.index}].fdPeriodTwelve" onValueChange="recountMonthAmount" subject="${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodTwelve')}" validators="required number" style="width:95%;color: #333;" />
                 </div>
             </td>
	    </tr>
	    </c:if>
		<c:if test="${oneLine}">
	    <tr KMSS_IsContentRow="1">
	    	<!-- 只审核最末级 -->
	    	<c:if test="${ fsscBudgetingMainForm.fdStatus=='2'||fsscBudgetingMainForm.fdStatus=='4'}">
	    		<c:choose>
		    		<c:when test="${((fdDetailList_FormItem.fdStatus=='2' and approvalAuth) or (fdDetailList_FormItem.fdStatus=='4' and effectAuth)) and (fdDetailList_FormItem.fdIsLastStage=='1' or noBudgetItem)}">
	    			 <td align="center">
			             <input type='checkbox' name='List_Selected' value="${fdDetailList_FormItem.fdId}" />
			         </td>
			        </c:when>
		    		<c:when test="${(fsscBudgetingMainForm.fdStatus=='2' and approvalAuth) or (fsscBudgetingMainForm.fdStatus=='4' and effectAuth)}">
		    			<td align="center">
			            
			         	</td>
			        </c:when>
			        <c:otherwise>
			        </c:otherwise>
		        </c:choose>
	    	</c:if>
	        <td align="center">
	            ${vstatus.index+1}
	            <xform:text property="fdDetailList_Form[${vstatus.index}].docMainId" htmlElementProperties="id='fdDetailList_Form[${vstatus.index}].docMainId'" showStatus="noShow"></xform:text>
	            <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdId" value="${fdDetailList_FormItem.fdId}" />
	            <xform:text property="fdDetailList_Form[${vstatus.index}].fdParentId" htmlElementProperties="id='fdDetailList_Form[${vstatus.index}].fdParentId'" showStatus="noShow"></xform:text>
                <xform:text property="fdDetailList_Form[${vstatus.index}].fdIsLastStage" htmlElementProperties="id='fdDetailList_Form[${vstatus.index}].fdIsLastStage'" showStatus="noShow"></xform:text>
                <xform:text property="fdDetailList_Form[${vstatus.index}].fdBudgetItemId" htmlElementProperties="id='fdDetailList_Form[${vstatus.index}].fdBudgetItemId'" showStatus="noShow"></xform:text>
	        </td>
	        <budgeting:showBudgetingDetail type="contentline" fdSchemeId="${fsscBudgetingMainForm.fdSchemeId}" detailForm="${fdDetailList_FormItem}" method="view" tdIndex="${vstatus.index}"></budgeting:showBudgetingDetail>
	        <td align="center">
           		${lfn:message('fssc-budgeting:fsscBudgetingDetail.gather.new.budgeting')}
             </td>
	        <td align="center">
                 <%-- 全年--%>
                 <div id="_xform_fdDetailList_Form[${vstatus.index}].fdTotal" _xform_type="text">
                   	 <xform:text property="fdDetailList_Form[${vstatus.index}].fdTotal" onValueChange="shareAmount" subject="${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdTotal')}" validators="required number" style="width:95%;color: #333;" />
                 </div>
             </td>
             <td align="center">
                 <%-- 1期--%>
                 <div id="_xform_fdDetailList_Form[${vstatus.index}].fdPeriodOne" _xform_type="text">
                     <xform:text property="fdDetailList_Form[${vstatus.index}].fdPeriodOne" onValueChange="recountMonthAmount" subject="${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodOne')}" validators="required number" style="width:95%;color: #333;" />
                 </div>
             </td>
             <td align="center">
                 <%-- 2期--%>
                 <div id="_xform_fdDetailList_Form[${vstatus.index}].fdPeriodTwo" _xform_type="text">
                     <xform:text property="fdDetailList_Form[${vstatus.index}].fdPeriodTwo" onValueChange="recountMonthAmount" subject="${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodTwo')}" validators="required number" style="width:95%;color: #333;" />
                 </div>
             </td>
             <td align="center">
                 <%-- 3期--%>
                 <div id="_xform_fdDetailList_Form[${vstatus.index}].fdPeriodThree" _xform_type="text">
                     <xform:text property="fdDetailList_Form[${vstatus.index}].fdPeriodThree" onValueChange="recountMonthAmount" subject="${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodThree')}" validators="required number" style="width:95%;color: #333;" />
                 </div>
             </td>
             <td align="center">
                 <%-- 4期--%>
                 <div id="_xform_fdDetailList_Form[${vstatus.index}].fdPeriodFour" _xform_type="text">
                     <xform:text property="fdDetailList_Form[${vstatus.index}].fdPeriodFour" onValueChange="recountMonthAmount" subject="${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodFour')}" validators="required number" style="width:95%;color: #333;" />
                 </div>
             </td>
             <td align="center">
                 <%-- 5期--%>
                 <div id="_xform_fdDetailList_Form[${vstatus.index}].fdPeriodFive" _xform_type="text">
                     <xform:text property="fdDetailList_Form[${vstatus.index}].fdPeriodFive" onValueChange="recountMonthAmount" subject="${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodFive')}" validators="required number" style="width:95%;color: #333;" />
                 </div>
             </td>
             <td align="center">
                 <%-- 6期--%>
                 <div id="_xform_fdDetailList_Form[${vstatus.index}].fdPeriodSix" _xform_type="text">
                     <xform:text property="fdDetailList_Form[${vstatus.index}].fdPeriodSix" onValueChange="recountMonthAmount" subject="${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodSix')}" validators="required number" style="width:95%;color: #333;" />
                 </div>
             </td>
             <td align="center">
                 <%-- 7期--%>
                 <div id="_xform_fdDetailList_Form[${vstatus.index}].fdPeriodSeven" _xform_type="text">
                     <xform:text property="fdDetailList_Form[${vstatus.index}].fdPeriodSeven" onValueChange="recountMonthAmount" subject="${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodSeven')}" validators="required number" style="width:95%;color: #333;" />
                 </div>
             </td>
             <td align="center">
                 <%-- 8期--%>
                 <div id="_xform_fdDetailList_Form[${vstatus.index}].fdPeriodEight" _xform_type="text">
                     <xform:text property="fdDetailList_Form[${vstatus.index}].fdPeriodEight" onValueChange="recountMonthAmount" subject="${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodEight')}" validators="required number" style="width:95%;color: #333;" />
                 </div>
             </td>
             <td align="center">
                 <%-- 9期--%>
                 <div id="_xform_fdDetailList_Form[${vstatus.index}].fdPeriodNine" _xform_type="text">
                     <xform:text property="fdDetailList_Form[${vstatus.index}].fdPeriodNine" onValueChange="recountMonthAmount" subject="${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodNine')}" validators="required number" style="width:95%;color: #333;" />
                 </div>
             </td>
             <td align="center">
                 <%-- 10期--%>
                 <div id="_xform_fdDetailList_Form[${vstatus.index}].fdPeriodTen" _xform_type="text">
                     <xform:text property="fdDetailList_Form[${vstatus.index}].fdPeriodTen" onValueChange="recountMonthAmount" subject="${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodTen')}" validators="required number" style="width:95%;color: #333;" />
                 </div>
             </td>
             <td align="center">
                 <%-- 11期--%>
                 <div id="_xform_fdDetailList_Form[${vstatus.index}].fdPeriodEleven" _xform_type="text">
                     <xform:text property="fdDetailList_Form[${vstatus.index}].fdPeriodEleven" onValueChange="recountMonthAmount" subject="${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodEleven')}" validators="required number" style="width:95%;color: #333;" />
                 </div>
             </td>
             <td align="center">
                 <%-- 12期--%>
                 <div id="_xform_fdDetailList_Form[${vstatus.index}].fdPeriodTwelve" _xform_type="text">
                     <xform:text property="fdDetailList_Form[${vstatus.index}].fdPeriodTwelve" onValueChange="recountMonthAmount" subject="${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodTwelve')}" validators="required number" style="width:95%;color: #333;" />
                 </div>
             </td>
	    </tr>
	    </c:if>
	</c:forEach>
	</table>
<script>
window.onload=function(){
		
     //让由内容的table 和没有内容的table一样宽，th一样高
   
     $(".tab_one").width($(".tab_two").width());
     $(".tab_one thead").height($(".tab_two  thead").height());
      
     //实时获取table 的宽度和th的高度
     $(window).resize(function(){
         $(".tab_one").width($(".tab_two").width());
         $(".tab_one thead").height($(".tab_two thead").height());
     })
      
     //循环让tab_one 中的每个th 与tab_two中的th高度相同
     $(".tab_two thead tr td").each(function(i){
         $(".tab_one thead tr td").eq(i).width($(this).width()+1);
     });
      
     //滚动调滚动，修改tab_one的top值
     $(".tabdiv").scroll(function(){
         var top = $(this).scrollTop();
         $(".tab_one").css('top',top);
     })
 }
</script>

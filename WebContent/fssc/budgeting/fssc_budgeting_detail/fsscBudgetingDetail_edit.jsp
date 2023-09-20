<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/WEB-INF/KmssConfig/fssc/budgeting/budgeting.tld" prefix="budgeting" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<table class="tb_normal" width="100%" id="TABLE_DocList_fdDetailList_Form" align="center">
     <tr align="center" class="tr_normal_title">
         <td style="width:20px;"></td>
         <td style="width:40px;">
             ${lfn:message('page.serial')}
         </td>
         <budgeting:showBudgetingDetail type="title" fdSchemeId="${lfn:escapeHtml(param.fdSchemeId)}"></budgeting:showBudgetingDetail>
         <td style="width:12%;">
             ${lfn:message('fssc-budgeting:fsscBudgetingDetail.gather.item')}
         </td>
         <td style="width:8%;">
             ${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdTotal')}
         </td>
         <td style="width:6%;">
             ${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodOne')}
         </td>
         <td style="width:6%;">
             ${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodTwo')}
         </td>
         <td style="width:6%;">
             ${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodThree')}
         </td>
         <td style="width:6%;">
             ${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodFour')}
         </td>
         <td style="width:6%;">
             ${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodFive')}
         </td>
         <td style="width:6%;">
             ${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodSix')}
         </td>
         <td style="width:6%;">
             ${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodSeven')}
         </td>
         <td style="width:6%;">
             ${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodEight')}
         </td>
         <td style="width:6%;">
             ${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodNine')}
         </td>
         <td style="width:6%;">
             ${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodTen')}
         </td>
         <td style="width:6%;">
             ${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodEleven')}
         </td>
         <td style="width:6%;">
             ${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodTwelve')}
         </td>
         <td style="width:3%;">
         </td>
     </tr>
     <tr KMSS_IsReferRow="1" style="display:none;">
         <td align="center">
             <input type='checkbox' name='DocList_Selected' />
             <xform:text property="fdDetailList_Form[!{index}].docMainId" htmlElementProperties="id='fdDetailList_Form[!{index}].docMainId'" showStatus="noShow"></xform:text>
             <xform:text property="fdDetailList_Form[!{index}].fdParentId" htmlElementProperties="id='fdDetailList_Form[!{index}].fdParentId'" showStatus="noShow"></xform:text>
             <xform:text property="fdDetailList_Form[!{index}].fdIsLastStage" htmlElementProperties="id='fdDetailList_Form[!{index}].fdIsLastStage'" showStatus="noShow"></xform:text>
             <xform:text property="fdDetailList_Form[!{index}].fdStatus" htmlElementProperties="id='fdDetailList_Form[!{index}].fdStatus'" showStatus="noShow"></xform:text>
         </td>
         <td align="center" KMSS_IsRowIndex="1">
             !{index}
         </td>
         <budgeting:showBudgetingDetail type="datumline" fdSchemeId="${lfn:escapeHtml(param.fdSchemeId)}"></budgeting:showBudgetingDetail>
         <td align="center">
             <%-- 统计项目--%>
             ${lfn:message('fssc-budgeting:fsscBudgetingDetail.gather.new.budgeting')}
         </td>
         <td align="center">
             <%-- 全年--%>
             <div id="_xform_fdDetailList_Form[!{index}].fdTotal" _xform_type="text">
                 <xform:text property="fdDetailList_Form[!{index}].fdTotal" showStatus="edit" onValueChange="reCalculate" subject="${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdTotal')}" validators="required number scaleLength(2)" style="width:95%;color: #333;" />
                 <input name="fdDetailList_noShow_Form[!{index}].fdTotal" type="hidden" />
                 <input name="fdDetailList_Form_display[!{index}].fdTotal" type="hidden" />
                 <span style="cursor:pointer;" onclick="shareAmount(this.parentNode.children[0].value,this.parentNode.children[0]);">${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdShare')}</span>
             </div>
         </td>
         <td align="center">
             <%-- 1期--%>
             <div id="_xform_fdDetailList_Form[!{index}].fdPeriodOne" _xform_type="text">
             	 <input name="fdDetailList_noShow_Form[!{index}].fdPeriodOne" type="hidden" />
                 <input name="fdDetailList_Form_display[!{index}].fdPeriodOne" type="hidden" />
                 <xform:text property="fdDetailList_Form[!{index}].fdPeriodOne" showStatus="edit" onValueChange="gatherAmount" subject="${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodOne')}"  validators="number scaleLength(2)" style="width:95%;color: #333;" />
             </div>
         </td>
         <td align="center">
             <%-- 2期--%>
             <div id="_xform_fdDetailList_Form[!{index}].fdPeriodTwo" _xform_type="text">
             	 <input name="fdDetailList_noShow_Form[!{index}].fdPeriodTwo" type="hidden" />
                 <input name="fdDetailList_Form_display[!{index}].fdPeriodTwo" type="hidden" />
                 <xform:text property="fdDetailList_Form[!{index}].fdPeriodTwo" showStatus="edit" onValueChange="gatherAmount" subject="${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodTwo')}"  validators="number scaleLength(2)" style="width:95%;color: #333;" />
             </div>
         </td>
         <td align="center">
             <%-- 3期--%>
             <div id="_xform_fdDetailList_Form[!{index}].fdPeriodThree" _xform_type="text">
             	<input name="fdDetailList_noShow_Form[!{index}].fdPeriodThree" type="hidden" />
                 <input name="fdDetailList_Form_display[!{index}].fdPeriodThree" type="hidden" />
                 <xform:text property="fdDetailList_Form[!{index}].fdPeriodThree" showStatus="edit" onValueChange="gatherAmount" subject="${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodThree')}"  validators="number scaleLength(2)" style="width:95%;color: #333;" />
             </div>
         </td>
         <td align="center">
             <%-- 4期--%>
             <div id="_xform_fdDetailList_Form[!{index}].fdPeriodFour" _xform_type="text">
             	<input name="fdDetailList_noShow_Form[!{index}].fdPeriodFour" type="hidden" />
                 <input name="fdDetailList_Form_display[!{index}].fdPeriodFour" type="hidden" />
                 <xform:text property="fdDetailList_Form[!{index}].fdPeriodFour" showStatus="edit" onValueChange="gatherAmount" subject="${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodFour')}"  validators="number scaleLength(2)" style="width:95%;color: #333;" />
             </div>
         </td>
         <td align="center">
             <%-- 5期--%>
             <div id="_xform_fdDetailList_Form[!{index}].fdPeriodFive" _xform_type="text">
             	<input name="fdDetailList_noShow_Form[!{index}].fdPeriodFive" type="hidden" />
                 <input name="fdDetailList_Form_display[!{index}].fdPeriodFive" type="hidden" />
                 <xform:text property="fdDetailList_Form[!{index}].fdPeriodFive" showStatus="edit" onValueChange="gatherAmount" subject="${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodFive')}"  validators="number scaleLength(2)" style="width:95%;color: #333;" />
             </div>
         </td>
         <td align="center">
             <%-- 6期--%>
             <div id="_xform_fdDetailList_Form[!{index}].fdPeriodSix" _xform_type="text">	
             	<input name="fdDetailList_noShow_Form[!{index}].fdPeriodSix" type="hidden" />
                 <input name="fdDetailList_Form_display[!{index}].fdPeriodSix" type="hidden" />
                 <xform:text property="fdDetailList_Form[!{index}].fdPeriodSix" showStatus="edit" onValueChange="gatherAmount" subject="${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodSix')}"  validators="number scaleLength(2)" style="width:95%;color: #333;" />
             </div>
         </td>
         <td align="center">
             <%-- 7期--%>
             <div id="_xform_fdDetailList_Form[!{index}].fdPeriodSeven" _xform_type="text">
             	<input name="fdDetailList_noShow_Form[!{index}].fdPeriodSeven" type="hidden" />
                 <input name="fdDetailList_Form_display[!{index}].fdPeriodSeven" type="hidden" />
                 <xform:text property="fdDetailList_Form[!{index}].fdPeriodSeven" showStatus="edit" onValueChange="gatherAmount" subject="${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodSeven')}"  validators="number scaleLength(2)" style="width:95%;color: #333;" />
             </div>
         </td>
         <td align="center">
             <%-- 8期--%>
             <div id="_xform_fdDetailList_Form[!{index}].fdPeriodEight" _xform_type="text">
             	<input name="fdDetailList_noShow_Form[!{index}].fdPeriodEight" type="hidden" />
                 <input name="fdDetailList_Form_display[!{index}].fdPeriodEight" type="hidden" />
                 <xform:text property="fdDetailList_Form[!{index}].fdPeriodEight" showStatus="edit" onValueChange="gatherAmount" subject="${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodEight')}"  validators="number scaleLength(2)" style="width:95%;color: #333;" />
             </div>
         </td>
         <td align="center">
             <%-- 9期--%>
             <div id="_xform_fdDetailList_Form[!{index}].fdPeriodNine" _xform_type="text">
             	<input name="fdDetailList_noShow_Form[!{index}].fdPeriodNine" type="hidden" />
                 <input name="fdDetailList_Form_display[!{index}].fdPeriodNine" type="hidden" />
                 <xform:text property="fdDetailList_Form[!{index}].fdPeriodNine" showStatus="edit" onValueChange="gatherAmount" subject="${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodNine')}"  validators="number scaleLength(2)" style="width:95%;color: #333;" />
             </div>
         </td>
         <td align="center">
             <%-- 10期--%>
             <div id="_xform_fdDetailList_Form[!{index}].fdPeriodTen" _xform_type="text">
             	<input name="fdDetailList_noShow_Form[!{index}].fdPeriodTen" type="hidden" />
                 <input name="fdDetailList_Form_display[!{index}].fdPeriodTen" type="hidden" />
                 <xform:text property="fdDetailList_Form[!{index}].fdPeriodTen" showStatus="edit" onValueChange="gatherAmount" subject="${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodTen')}"  validators="number scaleLength(2)" style="width:95%;color: #333;" />
             </div>
         </td>
         <td align="center">
             <%-- 11期--%>
             <div id="_xform_fdDetailList_Form[!{index}].fdPeriodEleven" _xform_type="text">
             	<input name="fdDetailList_noShow_Form[!{index}].fdPeriodEleven" type="hidden" />
                 <input name="fdDetailList_Form_display[!{index}].fdPeriodEleven" type="hidden" />
                 <xform:text property="fdDetailList_Form[!{index}].fdPeriodEleven" showStatus="edit" onValueChange="gatherAmount" subject="${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodEleven')}"  validators="number scaleLength(2)" style="width:95%;color: #333;" />
             </div>
         </td>
         <td align="center">
             <%-- 12期--%>
             <div id="_xform_fdDetailList_Form[!{index}].fdPeriodTwelve" _xform_type="text">
             	<input name="fdDetailList_noShow_Form[!{index}].fdPeriodTwelve" type="hidden" />
                 <input name="fdDetailList_Form_display[!{index}].fdPeriodTwelve" type="hidden" />
                 <xform:text property="fdDetailList_Form[!{index}].fdPeriodTwelve" showStatus="edit" onValueChange="gatherAmount" subject="${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodTwelve')}"  validators="number scaleLength(2)" style="width:95%;color: #333;" />
             </div>
         </td>
         <td align="center">
             <a href="javascript:void(0);" onclick="DocList_DeleteRow();" title="${lfn:message('doclist.delete')}">
                 <img src="${KMSS_Parameter_StylePath}icons/icon_del.png" border="0" />
             </a>
         </td>
     </tr>
     <c:forEach items="${fsscBudgetingMainForm.fdDetailList_Form}" var="fdDetailList_FormItem" varStatus="vstatus">
     <c:if test="${empty oneLine}">
         <tr KMSS_IsContentRow="1">
             <td align="center">
                 <input type="checkbox" name="DocList_Selected" />
                 <xform:text property="fdDetailList_Form[${vstatus.index}].docMainId" htmlElementProperties="id='fdDetailList_Form[${vstatus.index}].docMainId'" showStatus="noShow"></xform:text>
                 <xform:text property="fdDetailList_Form[${vstatus.index}].fdParentId" htmlElementProperties="id='fdDetailList_Form[${vstatus.index}].fdParentId'" showStatus="noShow"></xform:text>
                 <xform:text property="fdDetailList_Form[${vstatus.index}].fdIsLastStage" htmlElementProperties="id='fdDetailList_Form[${vstatus.index}].fdIsLastStage'" showStatus="noShow"></xform:text>
             	 <xform:text property="fdDetailList_Form[${vstatus.index}].fdStatus" htmlElementProperties="id='fdDetailList_Form[${vstatus.index}].fdStatus'" value="${fdDetailList_FormItem.fdStatus}" showStatus="noShow"></xform:text>
             	  <!-- 不是最末级/已审核通过的则显示状态为readOnly，最末级显示状态为edit -->
             	 <c:set var="showStatus" value=""></c:set>
             	 <c:if test="${fdDetailList_FormItem.fdIsLastStage=='0'  or fdDetailList_FormItem.fdStatus=='4'}">
             	 	<c:set var="showStatus" value="readOnly=\"readOnly\""></c:set>
             	 </c:if>
             	 <!-- 不是最末级则显示一行汇总，最末级显示一行汇总，一行填写 -->
             	 <c:set var="display" value="false"></c:set>
             	 <c:if test="${fdDetailList_FormItem.fdIsLastStage=='1'}">
             	 	<c:set var="display" value="true"></c:set>
             	 </c:if>
             </td>
             <td align="center">
                 ${vstatus.index+1}
             </td>
             <c:choose>
             	<c:when test="${fdDetailList_FormItem.fdStatus=='4' or fdDetailList_FormItem.fdIsLastStage=='0'}">
             		<budgeting:showBudgetingDetail type="contentline" fdSchemeId="${lfn:escapeHtml(param.fdSchemeId)}" detailForm="${fdDetailList_FormItem}" method="view" tdIndex="${vstatus.index}"></budgeting:showBudgetingDetail>
             	</c:when>
             	<c:otherwise>
             		<budgeting:showBudgetingDetail type="contentline" fdSchemeId="${lfn:escapeHtml(param.fdSchemeId)}" detailForm="${fdDetailList_FormItem}" method="edit" tdIndex="${vstatus.index}"></budgeting:showBudgetingDetail>
             	</c:otherwise>
             </c:choose>
             <td align="center">
             	<c:if test="${display=='true'}">
             		${lfn:message('fssc-budgeting:fsscBudgetingDetail.gather.child')}
             		<hr />
             		${lfn:message('fssc-budgeting:fsscBudgetingDetail.gather.new.budgeting')}
             		</c:if>
             </td>
             <td align="center">
                 <%-- 全年--%>
                 <div id="_xform_fdDetailList_Form[${vstatus.index}].fdTotal" _xform_type="text">
                     <input name="fdDetailList_Form_display[${vstatus.index}].fdTotal" class="inputsgl" style="width:38px;color: #333;text-align: center;" readonly="readonly" />
                     <input name="fdDetailList_noShow_Form[${vstatus.index}].fdTotal" type="hidden" />
                     <c:if test="${display=='true'}">
	                     <hr />
	                   	 <input name="fdDetailList_Form[${vstatus.index}].fdTotal" value="<kmss:showNumber value="${fdDetailList_FormItem.fdTotal }" pattern="0.00"/>" onchange="reCalculate(this.value,this);" subject="${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdTotal')}" ${showStatus} validate="required number scaleLength(2)" class="inputsgl" style="width:95%;color: #333;">
	                   	 <c:if test="${fdDetailList_FormItem.fdStatus!='4'}">
	                   	 	<span style="cursor:pointer;font-size: 10px;" onclick="shareAmount(this.previousElementSibling.value,this.previousElementSibling);">${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdShare')}</span>
                   	 	</c:if>
                   	 </c:if>
                 </div>
             </td>
             <td align="center">
                 <%-- 1期--%>
                 <div id="_xform_fdDetailList_Form[${vstatus.index}].fdPeriodOne" _xform_type="text">
                     <input name="fdDetailList_Form_display[${vstatus.index}].fdPeriodOne" class="inputsgl" style="width:38px;color: #333;text-align: center;" readonly="readonly" />
                     <input name="fdDetailList_noShow_Form[${vstatus.index}].fdPeriodOne" type="hidden" />
                     <c:if test="${display=='true'}">
	                     <hr />
	                     <input name="fdDetailList_Form[${vstatus.index}].fdPeriodOne" value="<kmss:showNumber value="${fdDetailList_FormItem.fdPeriodOne }" pattern="0.00"/>" subject="${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodOne')}" onchange="gatherAmount(this.value,this);" ${showStatus} validate="number scaleLength(2)" class="inputsgl" style="width:95%;color: #333;">
                 	 </c:if>
                 </div>
             </td>
             <td align="center">
                 <%-- 2期--%>
                 <div id="_xform_fdDetailList_Form[${vstatus.index}].fdPeriodTwo" _xform_type="text">
                     <input name="fdDetailList_Form_display[${vstatus.index}].fdPeriodTwo" class="inputsgl" style="width:38px;color: #333;text-align: center;" readonly="readonly" />
                     <input name="fdDetailList_noShow_Form[${vstatus.index}].fdPeriodTwo" type="hidden" />
                     <c:if test="${display=='true'}">
	                     <hr />
	                     <input name="fdDetailList_Form[${vstatus.index}].fdPeriodTwo" value="<kmss:showNumber value="${fdDetailList_FormItem.fdPeriodTwo }" pattern="0.00"/>" subject="${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodTwo')}" onchange="gatherAmount(this.value,this);" ${showStatus} validate="number scaleLength(2)" class="inputsgl" style="width:95%;color: #333;">
                 	</c:if>
                 </div>
             </td>
             <td align="center">
                 <%-- 3期--%>
                 <div id="_xform_fdDetailList_Form[${vstatus.index}].fdPeriodThree" _xform_type="text">
                     <input name="fdDetailList_Form_display[${vstatus.index}].fdPeriodThree" class="inputsgl" style="width:38px;color: #333;text-align: center;" readonly="readonly" />
                     <input name="fdDetailList_noShow_Form[${vstatus.index}].fdPeriodThree" type="hidden" />
                     <c:if test="${display=='true'}">
	                     <hr />
	                     <input name="fdDetailList_Form[${vstatus.index}].fdPeriodThree" value="<kmss:showNumber value="${fdDetailList_FormItem.fdPeriodThree }" pattern="0.00"/>" subject="${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodThree')}" onchange="gatherAmount(this.value,this);" ${showStatus} validate="number scaleLength(2)" class="inputsgl" style="width:95%;color: #333;">
                 	</c:if>
                 </div>
             </td>
             <td align="center">
                 <%-- 4期--%>
                 <div id="_xform_fdDetailList_Form[${vstatus.index}].fdPeriodFour" _xform_type="text">
                     <input name="fdDetailList_Form_display[${vstatus.index}].fdPeriodFour" class="inputsgl" style="width:38px;color: #333;text-align: center;" readonly="readonly" />
                     <input name="fdDetailList_noShow_Form[${vstatus.index}].fdPeriodFour" type="hidden" />
                     <c:if test="${display=='true'}">
	                     <hr />
	                     <input name="fdDetailList_Form[${vstatus.index}].fdPeriodFour" value="<kmss:showNumber value="${fdDetailList_FormItem.fdPeriodFour }" pattern="0.00"/>" subject="${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodFour')}" onchange="gatherAmount(this.value,this);" ${showStatus} validate="number scaleLength(2)" class="inputsgl" style="width:95%;color: #333;">
                 	</c:if>
                 </div>
             </td>
             <td align="center">
                 <%-- 5期--%>
                 <div id="_xform_fdDetailList_Form[${vstatus.index}].fdPeriodFive" _xform_type="text">
                     <input name="fdDetailList_Form_display[${vstatus.index}].fdPeriodFive" class="inputsgl" style="width:38px;color: #333;text-align: center;" readonly="readonly" />
                     <input name="fdDetailList_noShow_Form[${vstatus.index}].fdPeriodFive" type="hidden" />
                     <c:if test="${display=='true'}">
	                     <hr />
	                     <input name="fdDetailList_Form[${vstatus.index}].fdPeriodFive" value="<kmss:showNumber value="${fdDetailList_FormItem.fdPeriodFive }" pattern="0.00"/>" subject="${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodFive')}" onchange="gatherAmount(this.value,this);" ${showStatus} validate="number scaleLength(2)" class="inputsgl" style="width:95%;color: #333;">
                 	</c:if>
                 </div>
             </td>
             <td align="center">
                 <%-- 6期--%>
                 <div id="_xform_fdDetailList_Form[${vstatus.index}].fdPeriodSix" _xform_type="text">
                     <input name="fdDetailList_Form_display[${vstatus.index}].fdPeriodSix" class="inputsgl" style="width:38px;color: #333;text-align: center;" readonly="readonly" />
                     <input name="fdDetailList_noShow_Form[${vstatus.index}].fdPeriodSix" type="hidden" />
                     <c:if test="${display=='true'}">
	                     <hr />
	                     <input name="fdDetailList_Form[${vstatus.index}].fdPeriodSix" value="<kmss:showNumber value="${fdDetailList_FormItem.fdPeriodSix }" pattern="0.00"/>" subject="${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodSix')}" onchange="gatherAmount(this.value,this);" ${showStatus} validate="number scaleLength(2)" class="inputsgl" style="width:95%;color: #333;">
                 	</c:if>
                 </div>
             </td>
             <td align="center">
                 <%-- 7期--%>
                 <div id="_xform_fdDetailList_Form[${vstatus.index}].fdPeriodSeven" _xform_type="text">
                     <input name="fdDetailList_Form_display[${vstatus.index}].fdPeriodSeven" class="inputsgl" style="width:38px;color: #333;text-align: center;" readonly="readonly" />
                     <input name="fdDetailList_noShow_Form[${vstatus.index}].fdPeriodSeven" type="hidden" />
                     <c:if test="${display=='true'}">
	                     <hr />
	                     <input name="fdDetailList_Form[${vstatus.index}].fdPeriodSeven" value="<kmss:showNumber value="${fdDetailList_FormItem.fdPeriodSeven }" pattern="0.00"/>" subject="${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodSeven')}" onchange="gatherAmount(this.value,this);" ${showStatus} validate="number scaleLength(2)" class="inputsgl" style="width:95%;color: #333;">
                 	</c:if>
                 </div>
             </td>
             <td align="center">
                 <%-- 8期--%>
                 <div id="_xform_fdDetailList_Form[${vstatus.index}].fdPeriodEight" _xform_type="text">
                     <input name="fdDetailList_Form_display[${vstatus.index}].fdPeriodEight" class="inputsgl" style="width:38px;color: #333;text-align: center;" readonly="readonly" />
                     <input name="fdDetailList_noShow_Form[${vstatus.index}].fdPeriodEight" type="hidden" />
                     <c:if test="${display=='true'}">
	                     <hr />
	                     <input name="fdDetailList_Form[${vstatus.index}].fdPeriodEight" value="<kmss:showNumber value="${fdDetailList_FormItem.fdPeriodEight }" pattern="0.00"/>" subject="${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodEight')}" onchange="gatherAmount(this.value,this);" ${showStatus} validate="number scaleLength(2)" class="inputsgl" style="width:95%;color: #333;">
                 	</c:if>
                 </div>
             </td>
             <td align="center">
                 <%-- 9期--%>
                 <div id="_xform_fdDetailList_Form[${vstatus.index}].fdPeriodNine" _xform_type="text">
                     <input name="fdDetailList_Form_display[${vstatus.index}].fdPeriodNine" class="inputsgl" style="width:38px;color: #333;text-align: center;" readonly="readonly" />
                     <input name="fdDetailList_noShow_Form[${vstatus.index}].fdPeriodNine" type="hidden" />
                     <c:if test="${display=='true'}">
	                     <hr />
	                     <input name="fdDetailList_Form[${vstatus.index}].fdPeriodNine" value="<kmss:showNumber value="${fdDetailList_FormItem.fdPeriodNine }" pattern="0.00"/>" subject="${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodNine')}" onchange="gatherAmount(this.value,this);" ${showStatus} validate="number scaleLength(2)" class="inputsgl" style="width:95%;color: #333;">
                     </c:if>
                 </div>
             </td>
             <td align="center">
                 <%-- 10期--%>
                 <div id="_xform_fdDetailList_Form[${vstatus.index}].fdPeriodTen" _xform_type="text">
                     <input name="fdDetailList_Form_display[${vstatus.index}].fdPeriodTen" class="inputsgl" style="width:38px;color: #333;text-align: center;" readonly="readonly" />
                     <input name="fdDetailList_noShow_Form[${vstatus.index}].fdPeriodTen" type="hidden" />
                     <c:if test="${display=='true'}">
	                     <hr />
	                     <input name="fdDetailList_Form[${vstatus.index}].fdPeriodTen" value="<kmss:showNumber value="${fdDetailList_FormItem.fdPeriodTen }" pattern="0.00"/>" subject="${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodTen')}" onchange="gatherAmount(this.value,this);" ${showStatus} validate="number scaleLength(2)" class="inputsgl" style="width:95%;color: #333;">
                     </c:if>
                 </div>
             </td>
             <td align="center">
                 <%-- 11期--%>
                 <div id="_xform_fdDetailList_Form[${vstatus.index}].fdPeriodEleven" _xform_type="text">
                     <input name="fdDetailList_Form_display[${vstatus.index}].fdPeriodEleven" class="inputsgl" style="width:38px;color: #333;text-align: center;" readonly="readonly" />
                     <input name="fdDetailList_noShow_Form[${vstatus.index}].fdPeriodEleven" type="hidden" />
                     <c:if test="${display=='true'}">
	                     <hr />
	                     <input name="fdDetailList_Form[${vstatus.index}].fdPeriodEleven" value="<kmss:showNumber value="${fdDetailList_FormItem.fdPeriodEleven }" pattern="0.00"/>" subject="${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodEleven')}" onchange="gatherAmount(this.value,this);" ${showStatus} validate="number scaleLength(2)" class="inputsgl" style="width:95%;color: #333;">
                     </c:if>
                 </div>
             </td>
             <td align="center">
                 <%-- 12期--%>
                 <div id="_xform_fdDetailList_Form[${vstatus.index}].fdPeriodTwelve" _xform_type="text">
                     <input name="fdDetailList_Form_display[${vstatus.index}].fdPeriodTwelve" class="inputsgl" style="width:38px;color: #333;text-align: center;" readonly="readonly" />
                     <input name="fdDetailList_noShow_Form[${vstatus.index}].fdPeriodTwelve" type="hidden" />
                     <c:if test="${display=='true'}">
	                     <hr />
	                     <input name="fdDetailList_Form[${vstatus.index}].fdPeriodTwelve" value="<kmss:showNumber value="${fdDetailList_FormItem.fdPeriodTwelve }" pattern="0.00"/>" subject="${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodTwelve')}" onchange="gatherAmount(this.value,this);" ${showStatus} validate="number scaleLength(2)" class="inputsgl" style="width:95%;color: #333;">
                     </c:if>
                 </div>
             </td>
             <td align="center">
                 <c:if test="${fdDetailList_FormItem.fdStatus!='4'}">
                 <a href="javascript:void(0);" onclick="DocList_DeleteRow();" title="${lfn:message('doclist.delete')}">
                     <img src="${KMSS_Parameter_StylePath}icons/icon_del.png" border="0" />
                 </a>
                 </c:if>
             </td>
         </tr>
         </c:if>
         <c:if test="${oneLine}">
         <!-- 只显示一行 -->
         	<tr KMSS_IsContentRow="1">
             <td align="center">
                 <input type="checkbox" name="DocList_Selected" />
                 <xform:text property="fdDetailList_Form[${vstatus.index}].docMainId" htmlElementProperties="id='fdDetailList_Form[${vstatus.index}].docMainId'" showStatus="noShow"></xform:text>
                 <xform:text property="fdDetailList_Form[${vstatus.index}].fdParentId" htmlElementProperties="id='fdDetailList_Form[${vstatus.index}].fdParentId'" showStatus="noShow"></xform:text>
                 <xform:text property="fdDetailList_Form[${vstatus.index}].fdIsLastStage" htmlElementProperties="id='fdDetailList_Form[${vstatus.index}].fdIsLastStage'" showStatus="noShow"></xform:text>
             	 <xform:text property="fdDetailList_Form[${vstatus.index}].fdStatus" htmlElementProperties="id='fdDetailList_Form[${vstatus.index}].fdStatus'" value="${fdDetailList_FormItem.fdStatus}" showStatus="noShow"></xform:text>
             	  <!-- 不是最末级/已审核通过的则显示状态为readOnly，最末级显示状态为edit -->
             	 <c:set var="showStatus" value=""></c:set>
             	 <c:if test="${fdDetailList_FormItem.fdIsLastStage=='0'  or fdDetailList_FormItem.fdStatus=='4'}">
             	 	<c:set var="showStatus" value="readOnly=\"readOnly\""></c:set>
             	 </c:if>
             	 <!-- 不是最末级则显示一行汇总，最末级显示一行汇总，一行填写 -->
             	 <c:set var="display" value="false"></c:set>
             	 <c:if test="${fdDetailList_FormItem.fdIsLastStage=='1'}">
             	 	<c:set var="display" value="true"></c:set>
             	 </c:if>
             </td>
             <td align="center">
                 ${vstatus.index+1}
             </td>
             <c:choose>
             	<c:when test="${fdDetailList_FormItem.fdStatus=='4' or fdDetailList_FormItem.fdIsLastStage=='0'}">
             		<budgeting:showBudgetingDetail type="contentline" fdSchemeId="${lfn:escapeHtml(param.fdSchemeId)}" detailForm="${fdDetailList_FormItem}" method="view" tdIndex="${vstatus.index}"></budgeting:showBudgetingDetail>
             	</c:when>
             	<c:otherwise>
             		<budgeting:showBudgetingDetail type="contentline" fdSchemeId="${lfn:escapeHtml(param.fdSchemeId)}" detailForm="${fdDetailList_FormItem}" method="edit" tdIndex="${vstatus.index}"></budgeting:showBudgetingDetail>
             	</c:otherwise>
             </c:choose>
             <td align="center">
             	${lfn:message('fssc-budgeting:fsscBudgetingDetail.gather.new.budgeting')}
             </td>
             <td align="center">
                 <%-- 全年--%>
                 <div id="_xform_fdDetailList_Form[${vstatus.index}].fdTotal" _xform_type="text">
                   	 <input name="fdDetailList_Form[${vstatus.index}].fdTotal" value="<kmss:showNumber value="${fdDetailList_FormItem.fdTotal }" pattern="0.00"/>" onchange="reCalculate(this.value,this);" subject="${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdTotal')}" ${showStatus} validate="required number scaleLength(2)" class="inputsgl" style="width:95%;color: #333;">
                   	 <c:if test="${fdDetailList_FormItem.fdStatus!='4'}">
                   	 	<span style="cursor:pointer;font-size: 10px;" onclick="shareAmount(this.previousElementSibling.value,this.previousElementSibling);">${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdShare')}</span>
                 	</c:if>
                 </div>
             </td>
             <td align="center">
                 <%-- 1期--%>
                 <div id="_xform_fdDetailList_Form[${vstatus.index}].fdPeriodOne" _xform_type="text">
                     <input name="fdDetailList_Form[${vstatus.index}].fdPeriodOne" value="<kmss:showNumber value="${fdDetailList_FormItem.fdPeriodOne }" pattern="0.00"/>" subject="${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodOne')}" onchange="gatherAmount(this.value,this);" ${showStatus} validate="number scaleLength(2)" class="inputsgl" style="width:95%;color: #333;">
                 </div>
             </td>
             <td align="center">
                 <%-- 2期--%>
                 <div id="_xform_fdDetailList_Form[${vstatus.index}].fdPeriodTwo" _xform_type="text">
                     <input name="fdDetailList_Form[${vstatus.index}].fdPeriodTwo" value="<kmss:showNumber value="${fdDetailList_FormItem.fdPeriodTwo }" pattern="0.00"/>" subject="${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodTwo')}" onchange="gatherAmount(this.value,this);" ${showStatus} validate="number scaleLength(2)" class="inputsgl" style="width:95%;color: #333;">
                 </div>
             </td>
             <td align="center">
                 <%-- 3期--%>
                 <div id="_xform_fdDetailList_Form[${vstatus.index}].fdPeriodThree" _xform_type="text">
                     <input name="fdDetailList_Form[${vstatus.index}].fdPeriodThree" value="<kmss:showNumber value="${fdDetailList_FormItem.fdPeriodThree }" pattern="0.00"/>" subject="${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodThree')}" onchange="gatherAmount(this.value,this);" ${showStatus} validate="number scaleLength(2)" class="inputsgl" style="width:95%;color: #333;">
                 </div>
             </td>
             <td align="center">
                 <%-- 4期--%>
                 <div id="_xform_fdDetailList_Form[${vstatus.index}].fdPeriodFour" _xform_type="text">
                     <input name="fdDetailList_Form[${vstatus.index}].fdPeriodFour" value="<kmss:showNumber value="${fdDetailList_FormItem.fdPeriodFour }" pattern="0.00"/>" subject="${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodFour')}" onchange="gatherAmount(this.value,this);" ${showStatus} validate="number scaleLength(2)" class="inputsgl" style="width:95%;color: #333;">
                 </div>
             </td>
             <td align="center">
                 <%-- 5期--%>
                 <div id="_xform_fdDetailList_Form[${vstatus.index}].fdPeriodFive" _xform_type="text">
                     <input name="fdDetailList_Form[${vstatus.index}].fdPeriodFive" value="<kmss:showNumber value="${fdDetailList_FormItem.fdPeriodFive }" pattern="0.00"/>" subject="${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodFive')}" onchange="gatherAmount(this.value,this);" ${showStatus} validate="number scaleLength(2)" class="inputsgl" style="width:95%;color: #333;">
                 </div>
             </td>
             <td align="center">
                 <%-- 6期--%>
                 <div id="_xform_fdDetailList_Form[${vstatus.index}].fdPeriodSix" _xform_type="text">
                     <input name="fdDetailList_Form[${vstatus.index}].fdPeriodSix" value="<kmss:showNumber value="${fdDetailList_FormItem.fdPeriodSix }" pattern="0.00"/>" subject="${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodSix')}" onchange="gatherAmount(this.value,this);" ${showStatus} validate="number scaleLength(2)" class="inputsgl" style="width:95%;color: #333;">
                 </div>
             </td>
             <td align="center">
                 <%-- 7期--%>
                 <div id="_xform_fdDetailList_Form[${vstatus.index}].fdPeriodSeven" _xform_type="text">
                     <input name="fdDetailList_Form[${vstatus.index}].fdPeriodSeven" value="<kmss:showNumber value="${fdDetailList_FormItem.fdPeriodSeven }" pattern="0.00"/>" subject="${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodSeven')}" onchange="gatherAmount(this.value,this);" ${showStatus} validate="number scaleLength(2)" class="inputsgl" style="width:95%;color: #333;">
                 </div>
             </td>
             <td align="center">
                 <%-- 8期--%>
                 <div id="_xform_fdDetailList_Form[${vstatus.index}].fdPeriodEight" _xform_type="text">
                     <input name="fdDetailList_Form[${vstatus.index}].fdPeriodEight" value="<kmss:showNumber value="${fdDetailList_FormItem.fdPeriodEight }" pattern="0.00"/>" subject="${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodEight')}" onchange="gatherAmount(this.value,this);" ${showStatus} validate="number scaleLength(2)" class="inputsgl" style="width:95%;color: #333;">
                 </div>
             </td>
             <td align="center">
                 <%-- 9期--%>
                 <div id="_xform_fdDetailList_Form[${vstatus.index}].fdPeriodNine" _xform_type="text">
                     <input name="fdDetailList_Form[${vstatus.index}].fdPeriodNine" value="<kmss:showNumber value="${fdDetailList_FormItem.fdPeriodNine }" pattern="0.00"/>" subject="${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodNine')}" onchange="gatherAmount(this.value,this);" ${showStatus} validate="number scaleLength(2)" class="inputsgl" style="width:95%;color: #333;">
                 </div>
             </td>
             <td align="center">
                 <%-- 10期--%>
                 <div id="_xform_fdDetailList_Form[${vstatus.index}].fdPeriodTen" _xform_type="text">
                     <input name="fdDetailList_Form[${vstatus.index}].fdPeriodTen" value="<kmss:showNumber value="${fdDetailList_FormItem.fdPeriodTen }" pattern="0.00"/>" subject="${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodTen')}" onchange="gatherAmount(this.value,this);" ${showStatus} validate="number scaleLength(2)" class="inputsgl" style="width:95%;color: #333;">
                 </div>
             </td>
             <td align="center">
                 <%-- 11期--%>
                 <div id="_xform_fdDetailList_Form[${vstatus.index}].fdPeriodEleven" _xform_type="text">
                     <input name="fdDetailList_Form[${vstatus.index}].fdPeriodEleven" value="<kmss:showNumber value="${fdDetailList_FormItem.fdPeriodEleven }" pattern="0.00"/>" subject="${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodEleven')}" onchange="gatherAmount(this.value,this);" ${showStatus} validate="number scaleLength(2)" class="inputsgl" style="width:95%;color: #333;">
                 </div>
             </td>
             <td align="center">
                 <%-- 12期--%>
                 <div id="_xform_fdDetailList_Form[${vstatus.index}].fdPeriodTwelve" _xform_type="text">
                     <input name="fdDetailList_Form[${vstatus.index}].fdPeriodTwelve" value="<kmss:showNumber value="${fdDetailList_FormItem.fdPeriodTwelve }" pattern="0.00"/>" subject="${lfn:message('fssc-budgeting:fsscBudgetingDetail.fdPeriodTwelve')}" onchange="gatherAmount(this.value,this);" ${showStatus} validate="number scaleLength(2)" class="inputsgl" style="width:95%;color: #333;">
                 </div>
             </td>
             <td align="center">
                 <c:if test="${fdDetailList_FormItem.fdStatus!='4'}">
                 <a href="javascript:void(0);" onclick="DocList_DeleteRow();" title="${lfn:message('doclist.delete')}">
                     <img src="${KMSS_Parameter_StylePath}icons/icon_del.png" border="0" />
                 </a>
                 </c:if>
             </td>
         </tr>
         </c:if>
     </c:forEach>
     <tr type="optRow" class="tr_normal_opt" invalidrow="true">
          <td colspan="22">
              <a href="javascript:void(0);" onclick="DocList_AddRow();">
                  <img src="${KMSS_Parameter_StylePath}icons/icon_add.png" border="0" title="${lfn:message('doclist.add')}" />
              </a>
          </td>
      </tr>
 </table>
 <input type="hidden" name="fdDetailList_Flag" value="1">
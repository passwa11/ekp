<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<div class="jebody" id="AmortizeInfo" style="display:none;">
    <div class="jewarp">
        <h3 class="gray">${lfn:message('fssc-expense:table.fsscExpenseAmortize') }</h3>
        <div class="content">
            <table class="tb_normal" width="100%">
            	<tr>
            		<td class="td_normal_title" width="10">${lfn:message('fssc-expense:fsscExpenseMain.fdAmortizeArea') }</td>
            		<td width="23%">
            			<xform:text style="width:40%" property="fdAmortizeBegin" validators="checkAmortizeMonth" subject="${lfn:message('fssc-expense:fsscExpenseMain.fdAmortizeBegin') }" htmlElementProperties="id='fdAmortizeBegin'"></xform:text>
            			~
            			<xform:text style="width:40%" property="fdAmortizeEnd" validators="checkAmortizeMonth" subject="${lfn:message('fssc-expense:fsscExpenseMain.fdAmortizeEnd') }" htmlElementProperties="id='fdAmortizeEnd'"></xform:text>
            		</td>
            		<td class="td_normal_title" width="10">${lfn:message('fssc-expense:fsscExpenseMain.fdAmortizeMonth') }</td>
            		<td width="23%">
            			<xform:text property="fdAmortizeMonth" showStatus="readOnly" value="${fsscExpenseMainForm.fdAmortizeMonth }"/>
            		</td>
            		<td class="td_normal_title" width="10">${lfn:message('fssc-expense:fsscExpenseMain.fdAmortizeMoney') }</td>
            		<td width="23%">
            			<xform:text property="fdAmortizeMoney" showStatus="readOnly"/>
            		</td>
            	</tr>
            	<tr>
            		<td colspan="6">
            		<table class="tb_normal" width="100%" id="TABLE_DocList_Amortize">
            			<tr align="center" class="tr_normal_title">
					        <td width="5%">
					            ${lfn:message('page.serial')}
					        </td>
					        <td width="30%">
					            ${lfn:message('fssc-expense:fsscExpenseAmortize.fdMonth')}
					        </td>
					        <td width="30%">
					            ${lfn:message('fssc-expense:fsscExpenseAmortize.fdPercent')}(%)
					        </td>
					        <td width="30%">
					            ${lfn:message('fssc-expense:fsscExpenseAmortize.fdMoney')}
					        </td>
					    </tr>
					    <tr KMSS_IsReferRow="1" style="display:none;">
					    	<td align="center" KMSS_IsRowIndex="1">
					            !{index}
					        </td>
					        <td align="center">
					        	<input type="hidden" value="" name="fdAmortizeList_Form[!{index}].fdId">
					            <xform:text property="fdAmortizeList_Form[!{index}].fdMonth" style="width:30%" showStatus="readOnly"/>
					        </td>
					        <td align="center">
					            <xform:text property="fdAmortizeList_Form[!{index}].fdPercent" validators="max(100) min(0)" onValueChange="FSSC_ChangeAmortizePercent" style="width:30%" showStatus="edit"/>%
					        </td>
					        <td align="center">
					            <xform:text property="fdAmortizeList_Form[!{index}].fdMoney" style="width:30%" showStatus="readOnly"/>
					        </td>
					    </tr>
					    <c:forEach items="${fsscExpenseMainForm.fdAmortizeList_Form}" var="fdAmortizeList_FormItem" varStatus="vstatus">
				        <tr KMSS_IsContentRow="1">
				            <td align="center">
				                ${vstatus.index+1}
				            </td>
				            <td align="center">
				            	<input type="hidden" value="${fdAmortizeList_FormItem.fdId }" name="fdAmortizeList_Form[${vstatus.index }].fdId">
					           	<xform:text property="fdAmortizeList_Form[${vstatus.index }].fdMonth" style="width:30%" showStatus="readOnly"/>
					        </td>
					        <td align="center">
					            <xform:text property="fdAmortizeList_Form[${vstatus.index }].fdPercent" validators="max(100) min(0)" onValueChange="FSSC_ChangeAmortizePercent" style="width:30%" showStatus="edit"/>%
					        </td>
					        <td align="center">
					            <xform:text property="fdAmortizeList_Form[${vstatus.index }].fdMoney" style="width:30%" showStatus="readOnly"/>
					        </td>
				        </tr>
				        </c:forEach>
            		</table>
            		</td>
            	</tr>
            </table>
           
        </div>
    </div>
</div>
<link type="text/css" rel="stylesheet" href="${LUI_ContextPath }/fssc/common/resource/css/jedate.css"/>
<script src="${LUI_ContextPath }/fssc/common/resource/js/jedate.js"></script>

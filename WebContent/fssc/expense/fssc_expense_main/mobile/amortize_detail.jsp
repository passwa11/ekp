<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/eop/basedata/fssc.tld" prefix="fssc"%>
<div data-dojo-type="mui/table/ScrollableHContainer" >
	<div data-dojo-type="mui/table/ScrollableHView" style="margin-top:20px;" id="dt_wrap_amortize_hview">
		<table class="muiNormal" width="100%">
		    <tr>
            		<td class="td_normal_title" >${lfn:message('fssc-expense:fsscExpenseMain.fdAmortizeArea') }</td>
            		<td >
            			<xform:text showStatus="view" style="width:40%" property="fdAmortizeBegin" validators="checkAmortizeMonth" subject="${lfn:message('fssc-expense:fsscExpenseMain.fdAmortizeBegin') }" htmlElementProperties="id='fdAmortizeBegin'"></xform:text>
            			~
            			<xform:text showStatus="view" style="width:40%" property="fdAmortizeEnd" validators="checkAmortizeMonth" subject="${lfn:message('fssc-expense:fsscExpenseMain.fdAmortizeEnd') }" htmlElementProperties="id='fdAmortizeEnd'"></xform:text>
            		</td>
            		<td class="td_normal_title">${lfn:message('fssc-expense:fsscExpenseMain.fdAmortizeMonth') }</td>
            		<td>
            			<xform:text property="fdAmortizeMonth" showStatus="view"/>
            		</td>
            		<td class="td_normal_title">${lfn:message('fssc-expense:fsscExpenseMain.fdAmortizeMoney') }</td>
            		<td>
            			<xform:text property="fdAmortizeMoney" showStatus="view"/>
            		</td>
            	</tr>
            	<tr>
            		<td colspan="6">
            		<table class="tb_normal" width="100%" id="TABLE_DocList_Amortize">
            			<tr align="center" class="tr_normal_title">
					        <td >
					            ${lfn:message('page.serial')}
					        </td>
					        <td >
					            ${lfn:message('fssc-expense:fsscExpenseAmortize.fdMonth')}
					        </td>
					        <td >
					            ${lfn:message('fssc-expense:fsscExpenseAmortize.fdPercent')}(%)
					        </td>
					        <td >
					            ${lfn:message('fssc-expense:fsscExpenseAmortize.fdMoney')}
					        </td>
					    </tr>
					    <c:forEach items="${fsscExpenseMainForm.fdAmortizeList_Form}" var="fdAmortizeList_FormItem" varStatus="vstatus">
				        <tr KMSS_IsContentRow="1">
				            <td align="center">
				                ${vstatus.index+1}
				            </td>
				            <td align="center">
				            	<input type="hidden" value="${fdAmortizeList_FormItem.fdId }" name="fdAmortizeList_Form[${vstatus.index }].fdId">
					           	<xform:text property="fdAmortizeList_Form[${vstatus.index }].fdMonth" style="width:30%" showStatus="view"/>
					        </td>
					        <td align="center">
					            <xform:text property="fdAmortizeList_Form[${vstatus.index }].fdPercent" style="width:30%;text-align:right;" showStatus="view"/>%
					        </td>
					        <td align="center">
					            <xform:text property="fdAmortizeList_Form[${vstatus.index }].fdMoney" style="width:30%" showStatus="view"/>
					        </td>
				        </tr>
				        </c:forEach>
            		</table>
            		</td>
            	</tr>
		</table>
		<input type="hidden" name="fdAmortizeList_Flag" value="1">
	</div>
</div>

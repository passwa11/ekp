<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/eop/basedata/fssc.tld" prefix="fssc"%>
<ui:content title="${lfn:message('fssc-expense:table.fsscExpenseTravelDetail') }" expand="true">
<table class="tb_normal" width="100%" id="TABLE_DocList_fdTravelList_Form" align="center" tbdraggable="true">
  <tr align="center" class="tr_normal_title">
      <td width="5%">
          ${lfn:message('page.serial')}
      </td>
      <td width="10%">
          ${lfn:message('fssc-expense:fsscExpenseTravelDetail.fdSubject')}
      </td>
      <td width="10%">
          ${lfn:message('fssc-expense:fsscExpenseTravelDetail.fdBeginDate')}
      </td>
      <td width="10%">
          ${lfn:message('fssc-expense:fsscExpenseTravelDetail.fdEndDate')}
      </td>
      <td width="5%">
          ${lfn:message('fssc-expense:fsscExpenseTravelDetail.fdTravelDays')}
      </td>
      <td width="15%">
          ${lfn:message('fssc-expense:fsscExpenseTravelDetail.fdPersonList')}
      </td>
      <td width="10%">
          ${lfn:message('fssc-expense:fsscExpenseTravelDetail.fdStartPlace')}
      </td>
      <td width="10%">
          ${lfn:message('fssc-expense:fsscExpenseTravelDetail.fdArrivalPlace')}
      </td>
      <fssc:checkVersion version="true">
      <c:if test="${fn:indexOf(docTemplate.fdExtendFields,'6')>-1  }">
      <td width="10%">
          ${lfn:message('fssc-expense:fsscExpenseTravelDetail.fdBerth')}
      </td>
      </c:if>
      </fssc:checkVersion>
      <td width="5%">
      	<img src="${KMSS_Parameter_StylePath}icons/add.gif" border="0" style="cursor:pointer;" onclick="DocList_AddRow();FSSC_SetDefaultValue();"/>
      </td>
  </tr>
  <tr KMSS_IsReferRow="1" style="display:none;">
      <td align="center" KMSS_IsRowIndex="1">
          !{index}
      </td>
      <td align="center">
          <input type="hidden" name="fdTravelList_Form[!{index}].fdId" value="" disabled="true" />
          <input type="hidden" name="fdTravelList_Form[!{index}].fdSubject" value=""/>
          <div id="fdTravelList_Form[!{index}].fdSubject"></div>
      </td>
      <td align="center">
          <div id="_xform_fdTravelList_Form[!{index}].fdBeginDate" _xform_type="datetime">
              <xform:datetime subject="${lfn:message('fssc-expense:fsscExpenseTravelDetail.fdBeginDate') }" validators="checkDate" onValueChange="changeDate" required="true" property="fdTravelList_Form[!{index}].fdBeginDate" showStatus="edit" dateTimeType="date" style="width:90%;" />
          </div>
      </td>
      <td align="center">
          <div id="_xform_fdTravelList_Form[!{index}].fdEndDate" _xform_type="datetime">
              <xform:datetime subject="${lfn:message('fssc-expense:fsscExpenseTravelDetail.fdEndDate') }" validators="checkDate" onValueChange="changeDate" required="true" property="fdTravelList_Form[!{index}].fdEndDate" showStatus="edit" dateTimeType="date" style="width:90%;" />
          </div>
      </td>
      <td align="center">
          <div id="_xform_fdTravelList_Form[!{index}].fdTravelDays" _xform_type="text">
              <xform:text subject="${lfn:message('fssc-expense:fsscExpenseTravelDetail.fdTravelDays') }" property="fdTravelList_Form[!{index}].fdTravelDays" showStatus="readOnly" value="0" style="width:90%;" />
          </div>
      </td>
      <td align="center">
          <div id="_xform_fdTravelList_Form[!{index}].fdPersonListIds" _xform_type="address">
              <xform:address subject="${lfn:message('fssc-expense:fsscExpenseTravelDetail.fdPersonList') }" required="true" propertyId="fdTravelList_Form[!{index}].fdPersonListIds" propertyName="fdTravelList_Form[!{index}].fdPersonListNames" mulSelect="true" orgType="ORG_TYPE_PERSON" showStatus="edit" style="width:90%;" />
          </div>
      </td>
      <td align="center">
          <div id="_xform_fdTravelList_Form[!{index}].fdStartPlace" _xform_type="text">
              <xform:dialog subject="${lfn:message('fssc-expense:fsscExpenseTravelDetail.fdStartPlace') }" required="true" propertyId="fdTravelList_Form[!{index}].fdStartPlace1" propertyName="fdTravelList_Form[!{index}].fdStartPlace" showStatus="edit" style="width:90%;">
                  FSSC_SelectPlace('fdTravelList_Form[*].fdStartPlace1','fdTravelList_Form[*].fdStartPlace');
              </xform:dialog>
          </div>
      </td>
      <td align="center">
          <div id="_xform_fdTravelList_Form[!{index}].fdArrivalPlace" _xform_type="dialog">
              <xform:dialog subject="${lfn:message('fssc-expense:fsscExpenseTravelDetail.fdArrivalPlace') }" required="true" propertyId="fdTravelList_Form[!{index}].fdArrivalId" propertyName="fdTravelList_Form[!{index}].fdArrivalPlace" showStatus="edit" style="width:90%;">
                  FSSC_SelectPlace('fdTravelList_Form[*].fdArrivalId','fdTravelList_Form[*].fdArrivalPlace');
              </xform:dialog>
          </div>
      </td>
      <fssc:checkVersion version="true">
      <c:if test="${fn:indexOf(docTemplate.fdExtendFields,'6')>-1  }">
      <td align="center">
          <div id="_xform_fdTravelList_Form[!{index}].fdBerthId" _xform_type="dialog">
              <xform:dialog subject="${lfn:message('fssc-expense:fsscExpenseTravelDetail.fdBerth') }" required="true" propertyId="fdTravelList_Form[!{index}].fdBerthId" propertyName="fdTravelList_Form[!{index}].fdBerthName" showStatus="edit" style="width:90%;">
                  FSSC_SelectBerth('fdTravelList_Form[*].fdBerthId','fdTravelList_Form[*].fdBerthName');
              </xform:dialog>
          </div>
      </td>
      </c:if>
      </fssc:checkVersion>
      <td align="center">
          <a href="javascript:void(0);" onclick="Fssc_DeleteTravelRow(this);" title="${lfn:message('doclist.delete')}">
              <img src="${KMSS_Parameter_StylePath}icons/icon_del.png" border="0" />
          </a>
      </td>
  </tr>
  <c:forEach items="${fsscExpenseMainForm.fdTravelList_Form}" var="fdTravelList_FormItem" varStatus="vstatus">
      <tr KMSS_IsContentRow="1">
          <td align="center">
              ${vstatus.index+1}
          </td>
          <td align="center">
              <input type="hidden" name="fdTravelList_Form[${vstatus.index}].fdId" value="${fdTravelList_FormItem.fdId}" />
              <input type="hidden" name="fdTravelList_Form[${vstatus.index}].fdSubject" value="${fdTravelList_FormItem.fdSubject }"/>
          <div id="fdTravelList_Form[${vstatus.index}].fdSubject">${fdTravelList_FormItem.fdSubject }</div>

          </td>
          <td align="center">
              <div id="_xform_fdTravelList_Form[${vstatus.index}].fdBeginDate" _xform_type="datetime">
                  <xform:datetime subject="${lfn:message('fssc-expense:fsscExpenseTravelDetail.fdBeginDate') }" validators="checkDate" onValueChange="changeDate" required="true" property="fdTravelList_Form[${vstatus.index}].fdBeginDate" showStatus="edit" dateTimeType="date" style="width:90%;" />
              </div>
          </td>
          <td align="center">
              <div id="_xform_fdTravelList_Form[${vstatus.index}].fdEndDate" _xform_type="datetime">
                  <xform:datetime subject="${lfn:message('fssc-expense:fsscExpenseTravelDetail.fdEndDate') }" validators="checkDate" onValueChange="changeDate" required="true" property="fdTravelList_Form[${vstatus.index}].fdEndDate" showStatus="edit" dateTimeType="date" style="width:90%;" />
              </div>
          </td>
          <td align="center">
              <div id="_xform_fdTravelList_Form[${vstatus.index}].fdTravelDays" _xform_type="text">
                  <xform:text subject="${lfn:message('fssc-expense:fsscExpenseTravelDetail.fdTravelDays') }" property="fdTravelList_Form[${vstatus.index}].fdTravelDays" showStatus="readOnly" style="width:90%;" />
              </div>
          </td>
          <td align="center">
              <div id="_xform_fdTravelList_Form[${vstatus.index}].fdPersonListIds" _xform_type="address">
                  <xform:address subject="${lfn:message('fssc-expense:fsscExpenseTravelDetail.fdPersonList') }" required="true" propertyId="fdTravelList_Form[${vstatus.index}].fdPersonListIds" propertyName="fdTravelList_Form[${vstatus.index}].fdPersonListNames" mulSelect="true" orgType="ORG_TYPE_PERSON" showStatus="edit" style="width:90%;" />
              </div>
          </td>
          <td align="center">
              <div id="_xform_fdTravelList_Form[${vstatus.index}].fdStartPlace" _xform_type="text">
                  <xform:dialog subject="${lfn:message('fssc-expense:fsscExpenseTravelDetail.fdStartPlace') }" required="true" propertyId="fdTravelList_Form[${vstatus.index}].fdStartPlace1" propertyName="fdTravelList_Form[${vstatus.index}].fdStartPlace" showStatus="edit" style="width:90%;">
	                  FSSC_SelectPlace('fdTravelList_Form[*].fdStartPlace1','fdTravelList_Form[*].fdStartPlace');
	              </xform:dialog>
              </div>
          </td>
          <td align="center">
              <div id="_xform_fdTravelList_Form[${vstatus.index}].fdArrivalPlace" _xform_type="text">
                  <xform:dialog subject="${lfn:message('fssc-expense:fsscExpenseTravelDetail.fdArrivalPlace') }" required="true" propertyId="fdTravelList_Form[${vstatus.index}].fdArrivalId" propertyName="fdTravelList_Form[${vstatus.index}].fdArrivalPlace" showStatus="edit" style="width:90%;">
	                  FSSC_SelectPlace('fdTravelList_Form[*].fdArrivalId','fdTravelList_Form[*].fdArrivalPlace');
	              </xform:dialog>
              </div>
          </td>
          <fssc:checkVersion version="true">
          <c:if test="${fn:indexOf(docTemplate.fdExtendFields,'6')>-1  }">
          <td align="center">
              <div id="_xform_fdTravelList_Form[${vstatus.index}].fdBerthId" _xform_type="dialog">
                  <xform:dialog subject="${lfn:message('fssc-expense:fsscExpenseTravelDetail.fdBerth') }" required="true" propertyId="fdTravelList_Form[${vstatus.index}].fdBerthId" propertyName="fdTravelList_Form[${vstatus.index}].fdBerthName" showStatus="edit" style="width:90%;">
                      FSSC_SelectBerth('fdTravelList_Form[*].fdBerthId','fdTravelList_Form[*].fdBerthName');
                  </xform:dialog>
              </div>
          </td>
          </c:if>
          </fssc:checkVersion>
          <td align="center">
              <a href="javascript:void(0);" onclick="Fssc_DeleteTravelRow(this);" title="${lfn:message('doclist.delete')}">
                  <img src="${KMSS_Parameter_StylePath}icons/icon_del.png" border="0" />
              </a>
          </td>
      </tr>
  </c:forEach>
</table>
</ui:content>
<br/>

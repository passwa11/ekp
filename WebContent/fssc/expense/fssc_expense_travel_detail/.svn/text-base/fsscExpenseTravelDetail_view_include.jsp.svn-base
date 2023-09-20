<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/eop/basedata/fssc.tld" prefix="fssc"%>
<ui:content title="${lfn:message('fssc-expense:table.fsscExpenseTravelDetail') }" expand="true">
<table class="tb_normal" width="100%" id="TABLE_DocList_fdTravelList_Form" align="center">
  <tr align="center" class="tr_normal_title">
      <td width="5%">
          ${lfn:message('page.serial')}
      </td>
      <td width="50px">
          ${lfn:message('fssc-expense:fsscExpenseTravelDetail.fdSubject')}
      </td>
      <td width="10%">
          ${lfn:message('fssc-expense:fsscExpenseTravelDetail.fdBeginDate')}
      </td>
      <td width="10%">
          ${lfn:message('fssc-expense:fsscExpenseTravelDetail.fdEndDate')}
      </td>
      <td width="50px">
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
  </tr>
  <c:forEach items="${fsscExpenseMainForm.fdTravelList_Form}" var="fdTravelList_FormItem" varStatus="vstatus">
      <tr KMSS_IsContentRow="1">
          <td align="center">
              ${vstatus.index+1}
          </td>
          <td align="center">
              <input type="hidden" name="fdTravelList_Form[${vstatus.index}].fdId" value="${fdTravelList_FormItem.fdId}" />
              <input type="hidden" name="fdTravelList_Form[${vstatus.index}].fdSubject" value="${fdTravelList_FormItem.fdSubject }"/>
              <input type="hidden" name="fdTravelList_Form[${vstatus.index}].fdVehicleId" value="${fdTravelList_FormItem.fdVehicleId }"/>
              <input type="hidden" name="fdTravelList_Form[${vstatus.index}].fdBerthId" value="${fdTravelList_FormItem.fdBerthId }"/>
              <input type="hidden" name="fdTravelList_Form[${vstatus.index}].fdTravelDays" value="${fdTravelList_FormItem.fdTravelDays }"/>
              <input type="hidden" name="fdTravelList_Form[${vstatus.index}].fdArrivalId" value="${fdTravelList_FormItem.fdArrivalId }"/>
          <div id="fdTravelList_Form[${vstatus.index}].fdSubject">${fdTravelList_FormItem.fdSubject }</div>

          </td>
          <td align="center">
              <div id="_xform_fdTravelList_Form[${vstatus.index}].fdBeginDate" _xform_type="datetime">
                  <xform:datetime subject="${lfn:message('fssc-expense:fsscExpenseTravelDetail.fdBeginDate') }" required="true" property="fdTravelList_Form[${vstatus.index}].fdBeginDate" dateTimeType="date" style="width:90%;" />
              </div>
          </td>
          <td align="center">
              <div id="_xform_fdTravelList_Form[${vstatus.index}].fdEndDate" _xform_type="datetime">
                  <xform:datetime subject="${lfn:message('fssc-expense:fsscExpenseTravelDetail.fdEndDate') }" required="true" property="fdTravelList_Form[${vstatus.index}].fdEndDate" dateTimeType="date" style="width:90%;" />
              </div>
          </td>
          <td align="center">
              <div id="_xform_fdTravelList_Form[${vstatus.index}].fdTravelDays" _xform_type="text">
                  <xform:text subject="${lfn:message('fssc-expense:fsscExpenseTravelDetail.fdTravelDays') }" property="fdTravelList_Form[${vstatus.index}].fdTravelDays" showStatus="readOnly" style="width:90%;" />
              </div>
          </td>
          <td align="center">
              <div id="_xform_fdTravelList_Form[${vstatus.index}].fdPersonListIds" _xform_type="address">
                  <xform:address subject="${lfn:message('fssc-expense:fsscExpenseTravelDetail.fdPersonList') }" required="true" propertyId="fdTravelList_Form[${vstatus.index}].fdPersonListIds" propertyName="fdTravelList_Form[${vstatus.index}].fdPersonListNames" mulSelect="true" orgType="ORG_TYPE_PERSON" style="width:90%;" />
              </div>
          </td>
          <td align="center">
              <div id="_xform_fdTravelList_Form[${vstatus.index}].fdStartPlace" _xform_type="text">
                  <xform:dialog subject="${lfn:message('fssc-expense:fsscExpenseTravelDetail.fdStartPlace') }" required="true" propertyId="fdTravelList_Form[${vstatus.index}].fdStartId" propertyName="fdTravelList_Form[${vstatus.index}].fdStartPlace" style="width:90%;">
	                  FSSC_SelectPlace(${vstatus.index},'fdStartPlace');
	              </xform:dialog>
              </div>
          </td>
          <td align="center">
              <div id="_xform_fdTravelList_Form[${vstatus.index}].fdArrivalPlace" _xform_type="text">
                  <xform:dialog subject="${lfn:message('fssc-expense:fsscExpenseTravelDetail.fdArrivalPlace') }" required="true" propertyId="fdTravelList_Form[${vstatus.index}].fdArrivalPlace1" propertyName="fdTravelList_Form[${vstatus.index}].fdArrivalPlace" style="width:90%;">
	                  FSSC_SelectPlace(${vstatus.index},'fdArrivalPlace');
	              </xform:dialog>
              </div>
          </td>
          <fssc:checkVersion version="true">
          <c:if test="${fn:indexOf(docTemplate.fdExtendFields,'6')>-1  }">
          <td align="center">
              <div id="_xform_fdTravelList_Form[${vstatus.index}].fdBerthId" _xform_type="dialog">
                  <xform:dialog subject="${lfn:message('fssc-expense:fsscExpenseTravelDetail.fdBerth') }" required="true" propertyId="fdTravelList_Form[${vstatus.index}].fdBerthId" propertyName="fdTravelList_Form[${vstatus.index}].fdBerthName" style="width:90%;">
                      FSSC_SelectBerth(${vstatus.index})
                  </xform:dialog>
              </div>
          </td>
          </c:if>
          </fssc:checkVersion>
      </tr>
  </c:forEach>
</table>
</ui:content>
<script>
  Com_IncludeFile("doclist.js");
</script>
<script>
	DocList_Info.push('TABLE_DocList_fdTravelList_Form');
    seajs.use(['lui/dialog'],function(dialog){
    })
</script>
<br/>

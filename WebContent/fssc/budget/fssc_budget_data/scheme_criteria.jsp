<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/fssc/budget/budget.tld" prefix="budget"%>
<%@page import="com.landray.kmss.fssc.budget.util.FsscBudgetUtil" %>
 <script>
 	function refreshCriteria(vals,oldValues,queryType,id){
 		if (vals.length > 0 && vals[0] != null) {
			var val = vals[0].value;
			var data = new KMSSData();
			var rtn = data.AddBeanData("fsscBudgetCriteriaService&fdCompanyId=&&queryType="+queryType+"parentId="+val+"&authCurrent=true").GetHashMapArray();
			if(rtn.length > 0){
				LUI(id).source.setUrl("/sys/common/dataxml.jsp?s_bean=fsscBudgetCriteriaService&fdCompanyId=&&queryType="+queryType+"parentId="+val+"&authCurrent=true");
				LUI(id).source.resolveUrl();
				LUI(id).refresh();
			}
		}
 	}
 	LUI.ready(function() {
 		if(LUI('criteria1').findSelectedValuesByKey("fdYear")){
 			setTimeout(function(){
 				var year = new Date().getFullYear();
 				LUI('criteria1').addValue('fdYear',"5"+year+"0000") ;// 默认为当前年份
 			},500);
 		};
	});
 </script>
 <!-- 筛选 -->
 <list:criteria id="criteria1">
     <!-- 年份 -->
     <budget:budgetScheme fdSchemeId="${param.fdSchemeId}" type="period" value="2">
	<list:cri-criterion title="${ lfn:message('fssc-budget:fsscBudgetData.fdYear')}" key="fdYear" expand="true" multi="false">
		<list:box-select>
			<list:item-select>
				<ui:source type="AjaxXml">
					{"url":"/sys/common/dataxml.jsp?s_bean=fsscBudgetCriteriaService&queryType=fdYear&authCurrent=true"}
				</ui:source>
			</list:item-select>
		</list:box-select>
	</list:cri-criterion>
	</budget:budgetScheme>
     <!-- 月度 -->
     <budget:budgetScheme fdSchemeId="${param.fdSchemeId}" type="period" value="4">
	<list:cri-criterion title="${ lfn:message('fssc-budget:fsscBudgetData.fdPeriod')}" key="fdMonth" expand="true" multi="false">
		<list:box-select>
			<list:item-select >
				<ui:source type="Static">
					[{text:'${ lfn:message('fssc-budget:fsscBudgetDetail.fdYearMoney')}', value:'5'},
					{text:'${ lfn:message('fssc-budget:fsscBudgetDetail.fdFirstQuarterMoney')}', value:'300'},
					{text:'${ lfn:message('fssc-budget:fsscBudgetDetail.fdSecondQuarterMoney')}', value:'301'},
					{text:'${ lfn:message('fssc-budget:fsscBudgetDetail.fdThirdQuarterMoney')}', value:'302'},
					{text:'${ lfn:message('fssc-budget:fsscBudgetDetail.fdFourthQuarterMoney')}', value:'303'},
					{text:'${ lfn:message('fssc-budget:fsscBudgetDetail.fdJanMoney')}', value:'100'},
					{text:'${ lfn:message('fssc-budget:fsscBudgetDetail.fdFebMoney')}', value:'101'},
					{text:'${ lfn:message('fssc-budget:fsscBudgetDetail.fdMarMoney')}', value:'102'},
					{text:'${ lfn:message('fssc-budget:fsscBudgetDetail.fdAprMoney')}', value:'103'},
					{text:'${ lfn:message('fssc-budget:fsscBudgetDetail.fdMayMoney')}', value:'104'},
					{text:'${ lfn:message('fssc-budget:fsscBudgetDetail.fdJunMoney')}', value:'105'},
					{text:'${ lfn:message('fssc-budget:fsscBudgetDetail.fdJulMoney')}', value:'106'},
					{text:'${ lfn:message('fssc-budget:fsscBudgetDetail.fdAugMoney')}', value:'107'},
					{text:'${ lfn:message('fssc-budget:fsscBudgetDetail.fdSeptMoney')}', value:'108'},
					{text:'${ lfn:message('fssc-budget:fsscBudgetDetail.fdOctMoney')}', value:'109'},
					{text:'${ lfn:message('fssc-budget:fsscBudgetDetail.fdNovMoney')}', value:'110'},
					{text:'${ lfn:message('fssc-budget:fsscBudgetDetail.fdDecMoney')}', value:'111'}]
				 
				</ui:source>
			</list:item-select>
		</list:box-select>
	</list:cri-criterion>
	</budget:budgetScheme>
	<!-- 公司组 -->  
	<budget:budgetScheme fdSchemeId="${param.fdSchemeId}" type="dimension" value="1">
		<list:cri-criterion title="${lfn:message('fssc-budget:fsscBudgetData.fdCompanyGroup')}" key="fdCompanyGroupName" expand="true">
            <list:box-select>
                <list:item-select type="lui/criteria/criterion_input!TextInput">
                    <ui:source type="Static">
                        [{placeholder:'${lfn:message('fssc-budget:fsscBudgetData.fdCompanyGroup')}'}]
                    </ui:source>
                </list:item-select>
            </list:box-select>
        </list:cri-criterion>
   </budget:budgetScheme>
   <!-- 公司 -->  
  <%--  <budget:budgetScheme fdSchemeId="${param.fdSchemeId}" type="dimension" value="2">
   		<list:cri-criterion title="${lfn:message('fssc-budget:fsscBudgetData.fdCompany')}" key="fdCompanyName" expand="true">
            <list:box-select>
                <list:item-select type="lui/criteria/criterion_input!TextInput">
                    <ui:source type="Static">
                        [{placeholder:'${lfn:message('fssc-budget:fsscBudgetData.fdCompany')}'}]
                    </ui:source>
                </list:item-select>
            </list:box-select>
        </list:cri-criterion>
	</budget:budgetScheme> --%>
	<!-- 成本中心组 -->
	<budget:budgetScheme fdSchemeId="${param.fdSchemeId}" type="dimension" value="3">
		<list:cri-criterion title="${lfn:message('fssc-budget:fsscBudgetData.fdCostCenterGroup')}" key="fdCostCenterGroupName" expand="true">
            <list:box-select>
                <list:item-select type="lui/criteria/criterion_input!TextInput">
                    <ui:source type="Static">
                        [{placeholder:'${lfn:message('fssc-budget:fsscBudgetData.fdCostCenterGroup')}'}]
                    </ui:source>
                </list:item-select>
            </list:box-select>
        </list:cri-criterion>
	</budget:budgetScheme>
	<!-- 成本中心 -->
	<budget:budgetScheme fdSchemeId="${param.fdSchemeId}" type="dimension" value="4">
		<list:cri-ref ref="criterion.cusledger.customregion" title="成本中心所属组" key="fdCostCenter.parent.fdId" multi="false"/>
	
	<!-- 成本中心所属组 -->
                    <%-- <list:cri-criterion title="成本中心所属组" key="fdCostCenter.parent.fdId"  expand="true"  multi="false">
                        <list:box-select>
                            <list:item-select  id="fdCostCenter-parent-id">
                                <ui:source type="AjaxXml"  >
                                    {"url":"/sys/common/dataxml.jsp?s_bean=eopBasedataCostCenterService&flag=group&fdParentId="}
                                </ui:source>
                                <!-- 根据所选的公司名称，联动查询成本中心和预算科目 -->
                                <ui:event event="selectedChanged" args="evt">
                                    var vals = evt.values;
                                    if(vals.length > 0 && vals[0] != null){
                                    var val = vals[0].value;
                                    var fdparentid=vals[0].fdparentid;
                                     var data = new KMSSData();
                                    var rtn = data.AddBeanData("eopBasedataCostCenterService&flag=group&fdParentId="+val).GetHashMapArray();
                                    if(rtn.length > 0){
                                    LUI('fdCostCenter-parent-id').source.setUrl("/sys/common/dataxml.jsp?s_bean=eopBasedataCostCenterService&flag=group&fdParentId="+val);
                                    LUI('fdCostCenter-parent-id').source.resolveUrl();
                                    LUI('fdCostCenter-parent-id').refresh();
                                    }else{
                                    LUI('fdCostCenter-id').source.setUrl("/sys/common/dataxml.jsp?s_bean=eopBasedataCostCenterService&flag=notGroup&fdGroupId="+val);
                                    LUI('fdCostCenter-id').source.resolveUrl();
                                    LUI('fdCostCenter-id').refresh();
                                    }
                                    }else{
                                    LUI('fdCostCenter-parent-id').source.setUrl("/sys/common/dataxml.jsp?s_bean=eopBasedataCostCenterService&flag=group&fdParentId=");
                                    LUI('fdCostCenter-parent-id').source.resolveUrl();
                                    LUI('fdCostCenter-parent-id').refresh();
                                    
                                    LUI('fdCostCenter-id').source.setUrl("#");
                                    LUI('fdCostCenter-id').source.resolveUrl();
                                    LUI('fdCostCenter-id').refresh();
                                    }
                                </ui:event>

                            </list:item-select>
                        </list:box-select>
                    </list:cri-criterion> --%>
	<!-- 成本中心 fdCostCenter -->
                    <list:cri-criterion title="${lfn:message('fssc-budget:fsscBudgetData.fdCostCenter')}"  key="fdCostCenterId" expand="true" multi="true">
                        <list:box-select>
                            <list:item-select id="fdCostCenter-id">
                                <ui:source type="AjaxXml">
                                    {"url":"#"}
                                </ui:source>
                                <!-- 根据所选的 查询成本中心的子层级，若有则展现出来 -->
                                <ui:event event="selectedChanged" args="evtt">
                                    var fdGroupId = LUI('criteria1').findSelectedValuesByKey("fdCostCenter.parent.fdId").values[0].value;
                                    LUI('fdCostCenter-id').source.setUrl("/sys/common/dataxml.jsp?s_bean=eopBasedataCostCenterService&flag=notGroup&fdGroupId="+fdGroupId);
                                    LUI('fdCostCenter-id').source.resolveUrl();
                                    LUI('fdCostCenter-id').refresh();
                                </ui:event>
                            </list:item-select>
                        </list:box-select>
                    </list:cri-criterion>
		<%-- <list:cri-criterion title="${lfn:message('fssc-budget:fsscBudgetData.fdCostCenter')}" key="fdCostCenterName" expand="true">
            <list:box-select>
                <list:item-select type="lui/criteria/criterion_input!TextInput">
                    <ui:source type="Static">
                        [{placeholder:'${lfn:message('fssc-budget:fsscBudgetData.fdCostCenter')}'}]
                    </ui:source>
                </list:item-select>
            </list:box-select>
        </list:cri-criterion> --%>
	</budget:budgetScheme>
	<!-- 项目 -->
	<budget:budgetScheme fdSchemeId="${param.fdSchemeId}" type="dimension" value="5">
		<list:cri-criterion title="${lfn:message('fssc-budget:fsscBudgetData.fdProject')}" key="fdProjectName" expand="true">
            <list:box-select>
                <list:item-select type="lui/criteria/criterion_input!TextInput">
                    <ui:source type="Static">
                        [{placeholder:'${lfn:message('fssc-budget:fsscBudgetData.fdProject')}'}]
                    </ui:source>
                </list:item-select>
            </list:box-select>
        </list:cri-criterion>
	</budget:budgetScheme>
	<!-- WBS -->
	<budget:budgetScheme fdSchemeId="${param.fdSchemeId}" type="dimension" value="6">
		<list:cri-criterion title="${lfn:message('fssc-budget:fsscBudgetData.fdWbs')}" key="fdWbsName" expand="true">
            <list:box-select>
                <list:item-select type="lui/criteria/criterion_input!TextInput">
                    <ui:source type="Static">
                        [{placeholder:'${lfn:message('fssc-budget:fsscBudgetData.fdWbs')}'}]
                    </ui:source>
                </list:item-select>
            </list:box-select>
        </list:cri-criterion>
	</budget:budgetScheme>
	<!-- 内部订单 -->
	<budget:budgetScheme fdSchemeId="${param.fdSchemeId}" type="dimension" value="7">
		<list:cri-criterion title="${lfn:message('fssc-budget:fsscBudgetData.fdInnerOrder')}" key="fdInnerOrderName" expand="true">
            <list:box-select>
                <list:item-select type="lui/criteria/criterion_input!TextInput">
                    <ui:source type="Static">
                        [{placeholder:'${lfn:message('fssc-budget:fsscBudgetData.fdInnerOrder')}'}]
                    </ui:source>
                </list:item-select>
            </list:box-select>
        </list:cri-criterion>
	</budget:budgetScheme>
	<!-- 预算科目 -->
	<budget:budgetScheme fdSchemeId="${param.fdSchemeId}" type="dimension" value="8">
		<list:cri-criterion title="${lfn:message('fssc-budget:fsscBudgetData.fdBudgetItem')}" key="fdBudgetItemName" expand="true">
            <list:box-select>
                <list:item-select type="lui/criteria/criterion_input!TextInput">
                    <ui:source type="Static">
                        [{placeholder:'${lfn:message('fssc-budget:fsscBudgetData.fdBudgetItem')}'}]
                    </ui:source>
                </list:item-select>
            </list:box-select>
        </list:cri-criterion>
	</budget:budgetScheme>
	<!-- 员工 -->
	<budget:budgetScheme fdSchemeId="${param.fdSchemeId}" type="dimension" value="10">
    	<list:cri-auto modelName="com.landray.kmss.fssc.budget.model.FsscBudgetData" property="fdPerson" />
    </budget:budgetScheme>
    <!-- 部门 -->
	<budget:budgetScheme fdSchemeId="${param.fdSchemeId}" type="dimension" value="11">
    	<list:cri-auto modelName="com.landray.kmss.fssc.budget.model.FsscBudgetData" property="fdDept" />
    </budget:budgetScheme>
     <list:cri-criterion expand="false" title="${lfn:message('fssc-budget:fsscBudgetData.fdBudgetStatus')}" key="fdBudgetStatus" multi="false">
      <list:box-select>
           	<list:item-select type="lui/criteria/select_panel!TabCriterionSelectDatas">
				<ui:source type="Static">
				[{text:'${ lfn:message('fssc-budget:enums.budget.status.enable')}', value:'1'},
				{text:'${ lfn:message('fssc-budget:enums.budget.status.pause')}',value:'2'},
				{text:'${ lfn:message('fssc-budget:enums.budget.status.close')}',value:'3'}]
				</ui:source>
			</list:item-select>
      </list:box-select>
 	 </list:cri-criterion>
<%--  	 <list:cri-auto modelName="com.landray.kmss.fssc.budget.model.FsscBudgetData" property="docCreator" />
 --%> 	 <list:cri-auto modelName="com.landray.kmss.fssc.budget.model.FsscBudgetData" property="docCreateTime" />
 </list:criteria>

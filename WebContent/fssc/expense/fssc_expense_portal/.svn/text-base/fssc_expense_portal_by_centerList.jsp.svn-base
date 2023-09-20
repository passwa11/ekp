<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/eop/basedata/resource/jsp/jshead.jsp" %>
<script type="text/javascript" src="${LUI_ContextPath}/resource/js/common.js"></script>
<link rel="stylesheet" type="text/css" href="${KMSS_Parameter_ContextPath}fssc/expense/resource/css/common.css" /> 
<link rel="stylesheet" type="text/css" href="${KMSS_Parameter_ContextPath}fssc/expense/resource/css/main.css" /> 
<script>
Com_IncludeFile("jquery.js");
Com_IncludeFile("data.js");
Com_IncludeFile("json2.js");
</script>
<script>
		function initSelect(){
				var data = new KMSSData();
				data.UseCache = false;
				var rtn = data.AddBeanData("eopBasedataCompanyService&authCurrent=true").GetHashMapArray();
				if(rtn&&rtn.length > 0){
					var optionstring="";
					$("select[name=fdCompanyId]").html("");
					for (var j = 0; j < rtn.length;j++) {
		                           $("select[name=fdCompanyId]").append("<option value=\"" + rtn[j].value + "\">" + rtn[j].text + "</option>");
		                        }
				}
		}
		
		window.onload = function(){
			initSelect();
			FS_loadEvidenceExpense();
		}
		
		</script>
 <div style="
    padding-top: 10px;
    padding-left: 20px;
" hidden="true" >
					${lfn:message('fssc-budget:fsscBudgetMain.fdCompany')}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<div id="_xform_fdCompanyId" _xform_type="dialog"  >
						<select name="fdCompanyId" showStatus="edit" onchange="FS_loadEvidenceExpense();">
						</select>
					</div>
				</div>
		<div class="leaderBorder-container" >
					<div id="fsBaseSapEvidenceExpenseTd"></div>
		</div>

<script type="text/javascript" src="${KMSS_Parameter_ContextPath}fssc/ledger/resource/js/template-web.js" charset="UTF-8"></script>
<script type="text/javascript" src="${KMSS_Parameter_ContextPath}fssc/ledger/resource/js/jquery.json-2.2.js" charset="UTF-8"></script>
<script>
/*********
 * 加载凭证内容
 */
function FS_loadEvidenceExpense(){
	 var data1 = new KMSSData();
		data1.UseCache = false;
		$("#fsBaseSapEvidenceExpenseTd").html("");
		var fdCompanyId= $('select[name=fdCompanyId] option:selected').val();
		var rtn1 = data1.AddBeanData("fsscExpensePortalService&authCurrent=true&fdType=checkCostList&fdCompanyId="+fdCompanyId).GetHashMapArray();
		if(rtn1.length>0){
		$("#fsBaseSapEvidenceExpenseTd").html(template('test', $.parseJSON(rtn1[0].keyValue)));
	}
}
</script>
<script id="test" type="text/html">

					<table width="100%"  class="tab-table2" >
          <thead>
            <tr>
              <th><span class="table-th-span">排名<span></th>
              <th class="table-th-name"><span>部门名称<span></th>
              <th><span>支出费用<span></th>
            </tr>
          </thead>
				 <tbody id="tbody" class="tbody-line">



							{{each keyValue as value index}} 
{{if value.fdOrder <= 3}}
		<tr>
              <td><span class="span-r{{value.fdOrder}}">{{value.fdOrder}}</span></td>
              <td>
                <div class="tbody-div-complex">
                   <p>{{value.fdName}}</p>
                </div>
              </td>
              <td><p class="bottomSpan">￥{{value.fdMoney}}<p></td>
            </tr>
		{{/if}}

{{if value.fdOrder > 3}}
			<tr>
                  <td><span class="span-normal">{{value.fdOrder}}</span></td>
                  <td>
                    <div class="tbody-div-complex">
                       <p>{{value.fdName}}</p>
                    </div>
                  </td>
                  <td><p class="bottomSpan">￥{{value.fdMoney}}<p></td>
                </tr>
{{/if}}

				{{/each}} 
</tbody>
					</table>
</script>


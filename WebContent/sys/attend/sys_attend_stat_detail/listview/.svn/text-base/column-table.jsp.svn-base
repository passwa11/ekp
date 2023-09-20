var lv = layout.parent;

var datas = lv.getData().datas;
var columns = lv.getData().columns;
{$
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<div class="lui_listview_body">
	<div class="lui_listview_centerL">
		<div class="lui_listview_centerR">
			<div class="lui_listview_centerC">
				<table width="100%" class="lui_listview_columntable_table">
					<thead data-lui-mark="column.table.header">$}
						{$<tr>$}
						var getfdType = function(cols){
							var workTimeCount = 1;
							for(var i = 0 ;i < cols.length;i++){
								var prop = cols[i].property || cols[i].sort;
								if('fdSignTime3'==prop){
									workTimeCount = Math.max(workTimeCount,2);
								}
								if('fdSignTime5'==prop){
									workTimeCount = Math.max(workTimeCount,3);
								}
								if('fdSignTime7'==prop){
									workTimeCount = Math.max(workTimeCount,4);
								}
								if('fdSignTime9'==prop){
									workTimeCount = Math.max(workTimeCount,5);
								}
							}
							return workTimeCount;
						};
						var _isContain = function(values,col){
							var arr = values.split(";");
							for(var i = 0 ;i < arr.length;i++){
								if(col==arr[i]){
									return true;
								}
							}
							return false;
						};
						var fdType =getfdType(columns);
						var workTime1 = "fdStartTime;fdSignTime;docStatus;fdEndTime;fdSignTime2;docStatus2;";
						var workTime2 = "fdSignTime3;docStatus3;fdState3;fdSignTime4;docStatus4;fdState4;";
						var workTime3 = "fdSignTime5;docStatus5;fdState5;fdSignTime6;docStatus6;fdState6;"; 
						var workTime4 = "fdSignTime7;docStatus7;fdState7;fdSignTime8;docStatus8;fdState8;";
						var workTime5 = "fdSignTime9;docStatus9;fdState9;fdSignTime10;docStatus10;fdState10;";
						var workTimeTxt1 = false,workTimeTxt2=false,workTimeTxt3 = false,workTimeTxt4=false,workTimeTxt5=false;

						for (var i = 0; i < columns.length; i ++) {
							var col = columns[i];
							if('true' != col.hide){
								var tdspan = "";
								var prop = col.property || col.sort;
								if(prop && (workTime1+workTime2+workTime3+workTime4+workTime5).indexOf(prop) > -1){
									tdspan = "";
								}else{
									tdspan = "rowspan=2";
								}
								
								if(tdspan.length>0){
									{$<th {%tdspan%} style='{%col.headerStyle%}' class='{%col.headerClass%}' data-lui-mark-row-id='{%col.rowId%}' data-lui-mark-sort='{%col.sort%}' data-lui-mark-toggle-index='{%col.index%}'>{%col.title%}</th>$}
								}else{
									if(prop && _isContain(workTime1,prop)&& !workTimeTxt1){
										workTimeTxt1 = true;
										{$<th colspan=6><bean:message bundle="sys-attend" key="sysAttendStatDetail.workTime.first"/></th>$}
									}
									if(prop && _isContain(workTime2,prop) && !workTimeTxt2 ){
										workTimeTxt2 = true;
										{$<th colspan=4><bean:message bundle="sys-attend" key="sysAttendStatDetail.workTime.second"/></th>$}
									}	
									if(prop && _isContain(workTime3,prop) && !workTimeTxt3){
										workTimeTxt3 = true;
										{$<th colspan=6><bean:message bundle="sys-attend" key="sysAttendStatDetail.workTime.third"/></th>$}
									}
									if(prop && _isContain(workTime4,prop) && !workTimeTxt4){
										workTimeTxt4 = true;
										{$<th colspan=6><bean:message bundle="sys-attend" key="sysAttendStatDetail.workTime.forth"/></th>$}
									}
									if(prop && _isContain(workTime5,prop) && !workTimeTxt5){
										workTimeTxt5 = true;
										{$<th colspan=6><bean:message bundle="sys-attend" key="sysAttendStatDetail.workTime.fifth"/></th>$}
									}
								}
							}	
						}
						{$</tr>$}
						{$<tr>$}
						for (var i = 0; i < columns.length; i ++) {
							var col = columns[i];
							if('true' != col.hide){
								var prop = col.property || col.sort;
								if(prop && (workTime1+workTime2+workTime3+workTime4+workTime5).indexOf(prop) > -1){
								}else{
									continue;
								}
								{$<th style='{%col.headerStyle%}' class='{%col.headerClass%}' data-lui-mark-row-id='{%col.rowId%}' data-lui-mark-sort='{%col.sort%}' data-lui-mark-toggle-index='{%col.index%}'>{%col.title%}</th>$}
							}
								
						}
						{$</tr>$}
					{$</thead>
					<tbody>$}
						for (var i = 0; i < datas.length; i ++) {
							{$<tr data-lui-mark-id='{%lv.kvData[i]['rowId']%}' kmss_fdId='{%lv.kvData[i]['fdId']%}'>$}
							var row = datas[i];
							for (var j = 0; j < row.length; j ++) {
								var cell = row[j];
								if('true' != cell.hide)
									{$<td style='{%cell.style%}' class='{%cell.styleClass%}'>{%cell.value%}</td>$}
							}
							{$</tr>$}
						}
					{$</tbody>
				</table>
			</div>
		</div>
	</div>
	<div class="lui_listview_footL">
		<div class="lui_listview_footR">
			<div class="lui_listview_footC">
			</div>
		</div>
	</div>
</div>
$}
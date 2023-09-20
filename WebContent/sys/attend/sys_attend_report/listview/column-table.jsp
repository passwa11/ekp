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
						var isContain = function(values, col){
							if(values && col) {
								var arr = values.split(";");
								for(var i = 0 ;i < arr.length;i++){
									if(arr[i] && col == $.trim(arr[i])){
										return true;
									}
								}
							}
							return false;
						};
						var overTimeCols = "fdWorkOverTime;fdOffOverTime;fdHolidayOverTime;fdOverTime";
						var offDayCols = document.getElementsByName('fdoffTypeNames')[0].value + "fdOffDays";
						var overTimeMergeNum = 0, OffDaysMergeNum = 0;
						var isOverTimeMerge = false, isOffDaysMerge = false;
						var yearAmountCols = "fdTotalDays;fdUsedDays;fdRestDays"; var yearMergeNum = 0; var isYearAmount = false;
						var yearTxAmountCols = "fdTxTotalDays;fdTxUsedDays;fdTxRestDays"; var yearTxMergeNum = 0; var isTxYearAmount = false;

						var overApplyTimeCols = "fdWorkOverApplyTime;fdOffOverApplyTime;fdHolidayOverApplyTime;fdOverApplyTime";
						var	overTimeApplyMergeNum = 0;
						var isOverTimeApplyMerge = false;

						var overPayApplyTimeCols = "fdWorkOverPayApplyTime;fdOffOverPayApplyTime;fdHolidayOverPayApplyTime;fdOverPayApplyTime";
						var	overPayTimeApplyMergeNum = 0;
						var isOverPayTimeApplyMerge = false;

						var overPayTimeCols = "fdWorkOverPayTime;fdOffOverPayTime;fdHolidayOverPayTime;fdOverPayTime";
						var	overPayTimeMergeNum = 0;
						var isOverPayTimeMerge = false;

						var overTurnApplyTimeCols = "fdWorkOverTurnApplyTime;fdOffOverTurnApplyTime;fdHolidayOverTurnApplyTime;fdOverTurnApplyTime";
						var	overTurnTimeApplyMergeNum = 0;
						var isOverTurnTimeApplyMerge = false;

						var overTurnTimeCols = "fdWorkOverTurnTime;fdOffOverTurnTime;fdHolidayOverTurnTime;fdOverTurnTime";
						var	overTurnTimeMergeNum = 0;
						var isOverTurnTimeMerge = false;

						var overRestTimeCols = "fdWorkOverRestTime;fdOffOverRestTime;fdHolidayOverRestTime;fdOverRestTime";
						var	overRestTimeMergeNum = 0;
						var isOverRestTimeMerge = false;
						
						var restTurnTimeCols = "fdWorkRestTurnTime;fdOffRestTurnTime;fdHolidayRestTurnTime;fdRestTurnTime";
						var	restTurnTimeMergeNum = 0;
						var isRestTurnTimeMerge = false;

						for (var i = 0; i < columns.length; i ++) {
							var col = columns[i];
							if('true' != col.hide){
								var prop = col.property || col.sort;
								if(isContain(overTimeCols, prop)){
									overTimeMergeNum++;
								} else if(isContain(offDayCols, prop)){
									OffDaysMergeNum++;
								}

								if(isContain(yearAmountCols, prop)){
									yearMergeNum++;
								}

								if(isContain(yearTxAmountCols, prop)){
									yearTxMergeNum++;
								}

								if(isContain(overPayApplyTimeCols, prop)){
									overPayTimeApplyMergeNum++;
								}

								if(isContain(overApplyTimeCols, prop)){
									overTimeApplyMergeNum++;
								}
								if(isContain(overPayTimeCols, prop)){
									overPayTimeMergeNum++;
								}
								if(isContain(overTurnApplyTimeCols, prop)){
									overTurnTimeApplyMergeNum++;
								}
								if(isContain(overTurnTimeCols, prop)){
									overTurnTimeMergeNum++;
								}

								if(isContain(overRestTimeCols, prop)){
									overRestTimeMergeNum++;
								}
								
								if(isContain(restTurnTimeCols, prop)){
									restTurnTimeMergeNum++;
								}
							}
						}
						
						for (var i = 0; i < columns.length; i ++) {
							var col = columns[i];
							if('true' != col.hide){
								var prop = col.property || col.sort;
								if(overTimeMergeNum > 0 || OffDaysMergeNum > 0 || yearMergeNum > 0 || yearTxMergeNum > 0 || overTimeApplyMergeNum > 0 || overTimeApplyMergeNum > 0 || overPayTimeMergeNum > 0 || overTurnTimeApplyMergeNum > 0 || overTurnTimeMergeNum > 0 || overRestTimeMergeNum > 0){
									if(isContain(overTimeCols, prop)){
										if(!isOverTimeMerge){
											isOverTimeMerge = true;
											{$<th colspan='{%overTimeMergeNum%}'><bean:message bundle="sys-attend" key="sysAttendStatMonth.fdOverTime"/></th>$}
										}
									} else if(isContain(offDayCols, prop)){
										if(!isOffDaysMerge){
											isOffDaysMerge = true;
											{$<th colspan='{%OffDaysMergeNum%}'><bean:message bundle="sys-attend" key="sysAttendMain.fdStatus.askforleave"/></th>$}
										}
									} else if(isContain(yearAmountCols, prop)){
										if(!isYearAmount){
											isYearAmount = true;
											{$<th colspan='{%yearMergeNum%}'>年假休假明细</th>$}
										}
									}else if(isContain(yearTxAmountCols, prop)){
										if(!isTxYearAmount){
											isTxYearAmount = true;
											{$<th colspan='{%yearTxMergeNum%}'>调休假休假明细</th>$}
										}
									}else if(isContain(overApplyTimeCols, prop)){
										if(!isOverTimeApplyMerge){
											isOverTimeApplyMerge = true;
											{$<th colspan='{%overTimeApplyMergeNum%}'>当月申请加班小时数</th>$}
										}
									}else if(isContain(overPayApplyTimeCols, prop)){
										if(!isOverPayTimeApplyMerge){
											isOverPayTimeApplyMerge = true;
											{$<th colspan='{%overPayTimeApplyMergeNum%}'>申请加班费小时</th>$}
										}
									}else if(isContain(overPayTimeCols, prop)){
										if(!isOverPayTimeMerge){
											isOverPayTimeMerge = true;
											{$<th colspan='{%overPayTimeMergeNum%}'>实际加班费小时</th>$}
										}
									}else if(isContain(overTurnApplyTimeCols, prop)){
										if(!isOverTurnTimeApplyMerge){
											isOverTurnTimeApplyMerge = true;
											{$<th colspan='{%overTurnTimeApplyMergeNum%}'>申请调休小时</th>$}
										}
									}else if(isContain(overTurnTimeCols, prop)){
										if(!isOverTurnTimeMerge){
											isOverTurnTimeMerge = true;
											{$<th colspan='{%overTurnTimeMergeNum%}'>实际调休小时</th>$}
										}
									}else if(isContain(overRestTimeCols, prop)){
										if(!isOverRestTimeMerge){
											isOverRestTimeMerge = true;
											{$<th colspan='{%overRestTimeMergeNum%}'>加班结转小时</th>$}
										}
									}else if(isContain(restTurnTimeCols, prop)){
										if(!isRestTurnTimeMerge){
											isRestTurnTimeMerge = true;
											{$<th colspan='{%restTurnTimeMergeNum%}'>结转调休小时</th>$}
										}
									}else {
										{$<th rowspan="2" style='{%col.headerStyle%}' class='{%col.headerClass%}' data-lui-mark-row-id='{%col.rowId%}' data-lui-mark-sort='{%col.sort%}' data-lui-mark-toggle-index='{%col.index%}'>{%col.title%}</th>$}
									}
								} else {
									{$<th style='{%col.headerStyle%}' class='{%col.headerClass%}' data-lui-mark-row-id='{%col.rowId%}' data-lui-mark-sort='{%col.sort%}' data-lui-mark-toggle-index='{%col.index%}'>{%col.title%}</th>$}
								}
							}
						}
						{$</tr>$}
						if(overTimeMergeNum > 0 || OffDaysMergeNum > 0 || yearMergeNum > 0 || yearTxMergeNum > 0 || overTimeApplyMergeNum > 0 || overTimeApplyMergeNum > 0 || overPayTimeMergeNum > 0 || overTurnTimeApplyMergeNum > 0 || overTurnTimeMergeNum > 0 || overRestTimeMergeNum > 0) {
							{$<tr>$}
							for (var i = 0; i < columns.length; i ++) {
								var col = columns[i];
								if('true' != col.hide){
									var prop = col.property || col.sort;
									if(isContain(overTimeCols, prop) || isContain(offDayCols, prop) || isContain(yearAmountCols, prop) || isContain(yearTxAmountCols, prop) || isContain(overApplyTimeCols, prop) || isContain(overPayApplyTimeCols, prop) || isContain(overPayTimeCols, prop) || isContain(overTurnApplyTimeCols, prop) || isContain(overTurnTimeCols, prop) || isContain(overRestTimeCols, prop) || isContain(restTurnTimeCols, prop)){
										{$<th style='{%col.headerStyle%}' class='{%col.headerClass%}' data-lui-mark-row-id='{%col.rowId%}' data-lui-mark-sort='{%col.sort%}' data-lui-mark-toggle-index='{%col.index%}'>{%col.title%}</th>$}
									}
								}
									
							}
							{$</tr>$}
						}
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
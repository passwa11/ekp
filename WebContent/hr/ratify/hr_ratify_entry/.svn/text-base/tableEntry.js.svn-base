seajs.use(['lui/jquery','lui/topic'],function($,topic){
	//打开招聘岗位表单
	window.customClickRow = function(row,methodNew,rowValueOf,form,element){
		var elementVlue = $('#'+element);
		var rowOrRowValueOf;
		if(rowValueOf!=""&&rowValueOf!=null&&rowValueOf!="undefined"){
			rowOrRowValueOf=rowValueOf;
		}else{
			rowOrRowValueOf=row;
		}
		if("table_of_fdHistory_detail_edit"==element||"table_of_fdHistory_detail_view"==element){
			historyRowOpt(rowOrRowValueOf,{element:elementVlue});
		}else if("table_of_fdEducations_detail_edit"==element||"table_of_fdEducations_detail_view"==element){
			educationsRowOpt(rowOrRowValueOf,{element:elementVlue});
		}else if("table_of_fdTrains_detail_edit"==element||"table_of_fdTrains_detail_view"==element){
			trainsRowOpt(rowOrRowValueOf,{element:elementVlue});
		}else if("table_of_fdCertificate_detail_edit"==element||"table_of_fdCertificate_detail_view"==element){
			certificateRowOpt(rowOrRowValueOf,{element:elementVlue});
		}else if("table_of_fdRewardsPunishments_detail_edit"==element||"table_of_fdRewardsPunishments_detail_view"==element){
			rewardsPunishmentsRowOpt(rowOrRowValueOf,{element:elementVlue});
		}
		var rowIndex=$(row)[0].rowIndex-1;
		$('input[name="status"]').val(methodNew);
		$('input[name="rowIndex"]').val(rowIndex);
		if("TABLE_DocList_fdHistory_Form"==form){
			if("update"==methodNew){ 
				$('input[name="companyName"]').val($('input[name="fdHistory_Form['+rowIndex+'].fdName"]').val());
				$('input[name="fdPost"]').val($('input[name="fdHistory_Form['+rowIndex+'].fdPost"]').val());
				$('input[name="fdStartDateHistory"]').val($('input[name="fdHistory_Form['+rowIndex+'].fdStartDate"]').val());
				$('input[name="fdEndDateHistory"]').val($('input[name="fdHistory_Form['+rowIndex+'].fdEndDate"]').val());
				$('textarea[name="fdDescHistory"]').val($('input[name="fdHistory_Form['+rowIndex+'].fdDesc"]').val());
				$('textarea[name="fdLeaveReason"]').val($('input[name="fdHistory_Form['+rowIndex+'].fdLeaveReason"]').val());
		    }else if("view"==methodNew){ 
				$('input[name="companyName"]').val($('input[name="fdHistory_Form['+rowIndex+'].fdName"]').val());
				$('input[name="fdPost"]').val($('input[name="fdHistory_Form['+rowIndex+'].fdPost"]').val());
				$('input[name="fdStartDateHistory"]').val($('input[name="fdHistory_Form['+rowIndex+'].fdStartDate"]').val());
				$('input[name="fdEndDateHistory"]').val($('input[name="fdHistory_Form['+rowIndex+'].fdEndDate"]').val());
				$('input[name="fdDescHistory"]').val($('input[name="fdHistory_Form['+rowIndex+'].fdDesc"]').val());
				$('input[name="fdLeaveReason"]').val($('input[name="fdHistory_Form['+rowIndex+'].fdLeaveReason"]').val());
		    }
		}else if("TABLE_DocList_fdEducations_Form"==form){
			if("update"==methodNew){
				$('input[name="expName"]').val($('input[name="fdEducations_Form['+rowIndex+'].fdName"]').val());
				$('input[name="fdMajorName"]').val($('input[name="fdEducations_Form['+rowIndex+'].fdMajor"]').val());
				var fdId=$('input[name="fdEducations_Form['+rowIndex+'].fdAcadeRecord"]').val();
				$('select[name="fdAcadeRecordId"]').val(fdId);
				$('input[name="fdAcadeRecordName"]').val($('input[name="fdEducations_Form['+rowIndex+'].fdAcadeRecordName"]').val());
				$('input[name="fdEntranceDate"]').val($('input[name="fdEducations_Form['+rowIndex+'].fdEntranceDate"]').val());
				$('input[name="fdGraduationDate"]').val($('input[name="fdEducations_Form['+rowIndex+'].fdGraduationDate"]').val());
				$('textarea[name="fdRemarkEduExp"]').val($('input[name="fdEducations_Form['+rowIndex+'].fdRemark"]').val());
		    }else if("view"==methodNew){ 
		    	$('input[name="expName"]').val($('input[name="fdEducations_Form['+rowIndex+'].fdName"]').val());
				$('input[name="fdMajor"]').val($('input[name="fdEducations_Form['+rowIndex+'].fdMajor"]').val());
				$('input[name="fdAcadeRecordName"]').val($('input[name="fdEducations_Form['+rowIndex+'].fdAcadeRecordName"]').val());
				$('input[name="fdEntranceDate"]').val($('input[name="fdEducations_Form['+rowIndex+'].fdEntranceDate"]').val());
				$('input[name="fdGraduationDate"]').val($('input[name="fdEducations_Form['+rowIndex+'].fdGraduationDate"]').val());
				$('input[name="fdRemarkEduExp"]').val($('input[name="fdEducations_Form['+rowIndex+'].fdRemark"]').val()); 
		    }
		}else if("TABLE_DocList_fdTrains_Form"==form){
			if("update"==methodNew){ 
				$('input[name="trainName"]').val($('input[name="fdTrains_Form['+rowIndex+'].fdName"]').val());
				$('input[name="fdTrainCompany"]').val($('input[name="fdTrains_Form['+rowIndex+'].fdTrainCompany"]').val());
				$('input[name="fdStartDateTrain"]').val($('input[name="fdTrains_Form['+rowIndex+'].fdStartDate"]').val());
				$('input[name="fdEndDateTrain"]').val($('input[name="fdTrains_Form['+rowIndex+'].fdEndDate"]').val());
				$('textarea[name="fdRemarkTrain"]').val($('input[name="fdTrains_Form['+rowIndex+'].fdRemark"]').val());
		    }else if("view"==methodNew){ 
		    	$('input[name="trainName"]').val($('input[name="fdTrains_Form['+rowIndex+'].fdName"]').val());
				$('input[name="fdTrainCompany"]').val($('input[name="fdTrains_Form['+rowIndex+'].fdTrainCompany"]').val());
				$('input[name="fdStartDateTrain"]').val($('input[name="fdTrains_Form['+rowIndex+'].fdStartDate"]').val());
				$('input[name="fdEndDateTrain"]').val($('input[name="fdTrains_Form['+rowIndex+'].fdEndDate"]').val());
				$('input[name="fdRemarkTrain"]').val($('input[name="fdTrains_Form['+rowIndex+'].fdRemark"]').val());
		    }
		}else if("TABLE_DocList_fdCertificate_Form"==form){
			if("update"==methodNew){ 
				$('input[name="certifiName"]').val($('input[name="fdCertificate_Form['+rowIndex+'].fdName"]').val());
				$('input[name="fdIssuingUnit"]').val($('input[name="fdCertificate_Form['+rowIndex+'].fdIssuingUnit"]').val());
				$('input[name="fdIssueDate"]').val($('input[name="fdCertificate_Form['+rowIndex+'].fdIssueDate"]').val());
				$('input[name="fdInvalidDate"]').val($('input[name="fdCertificate_Form['+rowIndex+'].fdInvalidDate"]').val());
				$('textarea[name="fdRemarkCertificate"]').val($('input[name="fdCertificate_Form['+rowIndex+'].fdRemark"]').val());
		    }else if("view"==methodNew){ 
		    	$('input[name="certifiName"]').val($('input[name="fdCertificate_Form['+rowIndex+'].fdName"]').val());
				$('input[name="fdIssuingUnit"]').val($('input[name="fdCertificate_Form['+rowIndex+'].fdIssuingUnit"]').val());
				$('input[name="fdIssueDate"]').val($('input[name="fdCertificate_Form['+rowIndex+'].fdIssueDate"]').val());
				$('input[name="fdInvalidDate"]').val($('input[name="fdCertificate_Form['+rowIndex+'].fdInvalidDate"]').val());
				$('input[name="fdRemarkCertificate"]').val($('input[name="fdCertificate_Form['+rowIndex+'].fdRemark"]').val());
		    }
		}else if("TABLE_DocList_fdRewardsPunishments_Form"==form){
			if("update"==methodNew){ 
				$('input[name="rewPuniName"]').val($('input[name="fdRewardsPunishments_Form['+rowIndex+'].fdName"]').val());
				$('input[name="fdDate"]').val($('input[name="fdRewardsPunishments_Form['+rowIndex+'].fdDate"]').val());
				$('textarea[name="fdRemarkRewPuni"]').val($('input[name="fdRewardsPunishments_Form['+rowIndex+'].fdRemark"]').val());
		    }else if("view"==methodNew){ 
		    	$('input[name="rewPuniName"]').val($('input[name="fdRewardsPunishments_Form['+rowIndex+'].fdName"]').val());
				$('input[name="fdDate"]').val($('input[name="fdRewardsPunishments_Form['+rowIndex+'].fdDate"]').val());
				$('input[name="fdRemarkRewPuni"]').val($('input[name="fdRewardsPunishments_Form['+rowIndex+'].fdRemark"]').val()); 
		    }
		}
	};
	//关闭招聘岗位表单
	window.closeDetail=function(form,element){
		//clearnValue(form,element);
		if("table_of_fdHistory_detail_edit"==element||"table_of_fdHistory_detail_view"==element){
			topic.publish('historyRowPoplayer.display.hide');
		}else if("table_of_fdEducations_detail_edit"==element||"table_of_fdEducations_detail_view"==element){
			topic.publish('educationsRowPoplayer.display.hide');
		}else if("table_of_fdTrains_detail_edit"==element||"table_of_fdTrains_detail_view"==element){
			topic.publish('trainsRowPoplayer.display.hide');
		}else if("table_of_fdCertificate_detail_edit"==element||"table_of_fdCertificate_detail_view"==element){
			topic.publish('certificateRowPoplayer.display.hide');
		}else if("table_of_fdRewardsPunishments_detail_edit"==element||"table_of_fdRewardsPunishments_detail_view"==element){
			topic.publish('rewardsPunishmentsRowPoplayer.display.hide');
		}
	}
	
	//清除招聘岗位表单缓存
	window.clearnValue=function(form,element){
		$(".ajustReason").hide();
		$('input[name="fdRow"]').val("");
		$(".validation-advice").each(function(){
			$(this).remove();
		});
		$(".lui_validate").each(function(){
			$(this).remove();
		});
		$("#"+form).find("tr").each(function(){
			$(this).removeClass("current");
		});
		$("#"+element).find("input").each(function(){
			$(this).val("");
		});
		$("#"+element).find("li").each(function(){
			$(this).remove();
		});
		$("#"+element).find("textarea").each(function(){
			$(this).val("");
		});
		$("#"+element).find("select").each(function(){
			$(this).val("");
		});
	}
	
		window.historyRowOpt = function(row,config){
			var historyRowPoplayer=null;
			var row = $(row),
				tables = row.parents('table'), 
				config = config || {};
			if(tables.length > 0){
				var table = tables.eq(0),//离tr最近的table
					container = table.parent();
				if(container && ( container.css('position')=='static' || container.css('position')=='' ) ){
					container.css('position','relative');
				}
				if(!container){
					container = $(document.body);
				}
				if( !window.historyRowPoplayer){
					window.historyRowPoplayer = $('<div class="tb_normal_poplayer"/>');
					container.append(window.historyRowPoplayer);
					window.historyRowPoplayer.css({
						'position': 'absolute',
						'border': '1px #d2d2d2 solid',
						'background-color': 'white'
					});
					topic.subscribe('historyRowPoplayer.display.hide',function(){
						window.clearInterval(window.historyRowPoplayer.resize);
						window.historyRowPoplayer.resize = null;
						if(window.historyRowPoplayer.activeRow){
							window.historyRowPoplayer.activeRow.css("height","");
							window.historyRowPoplayer.activeRow.css('vertical-align','');
						}
						window.historyRowPoplayer.hide();
					});
				}
				window.clearInterval(window.historyRowPoplayer.resize);
				if(window.historyRowPoplayer.activeRow){
					window.historyRowPoplayer.activeRow.css("height","");
					window.historyRowPoplayer.activeRow.css('vertical-align','');
				}
				window.historyRowPoplayer.resize = null;
				window.historyRowPoplayer.resize = window.setInterval(function(){
					if(window.historyRowPoplayer.activeRow){
						var __height = window.historyRowPoplayer.height(),
							__rowHeight = window.historyRowPoplayer.rowHeight;
						window.historyRowPoplayer.activeRow.height(__height+__rowHeight);
						window.historyRowPoplayer.activeRow.css('vertical-align','top');
					}
				});//监听高度变化改变tr高度
				var	rowPoplayer = window.historyRowPoplayer,
					height = config.height,
					width = config.width || table.innerWidth(),
					rowHeight = row.height();
				if(!rowPoplayer.rowHeigth){
					rowPoplayer.rowHeight = rowHeight;
				}
			/*	if(rowPoplayer.activeRow){
					rowPoplayer.activeRow.css("height","");
					window.rowPoplayer.activeRow.css('vertical-align','');
				}*/
				rowPoplayer.activeRow = row;
				rowPoplayer.css('min-height',height);
				rowPoplayer.width(width);
				if(config.element){
					rowPoplayer.append(config.element);
				}
				var offsetTop = getOffsetTop(row,container);
				rowPoplayer.css("top",offsetTop+rowHeight );
				rowPoplayer.show();
			}
			return rowPoplayer;
		};
		
		window.educationsRowOpt = function(row,config){
			var educationsRowPoplayer=null;
			var row = $(row),
				tables = row.parents('table'), 
				config = config || {};
			if(tables.length > 0){
				var table = tables.eq(0),//离tr最近的table
					container = table.parent();
				if(container && ( container.css('position')=='static' || container.css('position')=='' ) ){
					container.css('position','relative');
				}
				if(!container){
					container = $(document.body);
				}
				if( !window.educationsRowPoplayer){
					window.educationsRowPoplayer = $('<div class="tb_normal_poplayer"/>');
					container.append(window.educationsRowPoplayer);
					window.educationsRowPoplayer.css({
						'position': 'absolute',
						'border': '1px #d2d2d2 solid',
						'background-color': 'white'
					});
					topic.subscribe('educationsRowPoplayer.display.hide',function(){
						window.clearInterval(window.educationsRowPoplayer.resize);
						window.educationsRowPoplayer.resize = null;
						if(window.educationsRowPoplayer.activeRow){
							window.educationsRowPoplayer.activeRow.css("height","");
							window.educationsRowPoplayer.activeRow.css('vertical-align','');
						}
						window.educationsRowPoplayer.hide();
					});
				}
				window.clearInterval(window.educationsRowPoplayer.resize);
				if(window.educationsRowPoplayer.activeRow){
					window.educationsRowPoplayer.activeRow.css("height","");
					window.educationsRowPoplayer.activeRow.css('vertical-align','');
				}
				window.educationsRowPoplayer.resize = null;
				window.educationsRowPoplayer.resize = window.setInterval(function(){
					if(window.educationsRowPoplayer.activeRow){
						var __height = window.educationsRowPoplayer.height(),
							__rowHeight = window.educationsRowPoplayer.rowHeight;
						window.educationsRowPoplayer.activeRow.height(__height+__rowHeight);
						window.educationsRowPoplayer.activeRow.css('vertical-align','top');
					}
				});//监听高度变化改变tr高度
				var	rowPoplayer = window.educationsRowPoplayer,
					height = config.height,
					width = config.width || table.innerWidth(),
					rowHeight = row.height();
				if(!rowPoplayer.rowHeigth){
					rowPoplayer.rowHeight = rowHeight;
				}
			/*	if(rowPoplayer.activeRow){
					rowPoplayer.activeRow.css("height","");
					window.rowPoplayer.activeRow.css('vertical-align','');
				}*/
				rowPoplayer.activeRow = row;
				rowPoplayer.css('min-height',height);
				rowPoplayer.width(width);
				if(config.element){
					rowPoplayer.append(config.element);
				}
				var offsetTop = getOffsetTop(row,container);
				rowPoplayer.css("top",offsetTop+rowHeight );
				rowPoplayer.show();
			}
			return rowPoplayer;
		};
		
		window.trainsRowOpt = function(row,config){
			var trainsRowPoplayer=null;
			var row = $(row),
				tables = row.parents('table'), 
				config = config || {};
			if(tables.length > 0){
				var table = tables.eq(0),//离tr最近的table
					container = table.parent();
				if(container && ( container.css('position')=='static' || container.css('position')=='' ) ){
					container.css('position','relative');
				}
				if(!container){
					container = $(document.body);
				}
				if( !window.trainsRowPoplayer){
					window.trainsRowPoplayer = $('<div class="tb_normal_poplayer"/>');
					container.append(window.trainsRowPoplayer);
					window.trainsRowPoplayer.css({
						'position': 'absolute',
						'border': '1px #d2d2d2 solid',
						'background-color': 'white'
					});
					topic.subscribe('trainsRowPoplayer.display.hide',function(){
						window.clearInterval(window.trainsRowPoplayer.resize);
						window.trainsRowPoplayer.resize = null;
						if(window.trainsRowPoplayer.activeRow){
							window.trainsRowPoplayer.activeRow.css("height","");
							window.window.trainsRowPoplayer.activeRow.css('vertical-align','');
						}
						window.trainsRowPoplayer.hide();
					});
				}
				window.clearInterval(window.trainsRowPoplayer.resize);
				if(window.trainsRowPoplayer.activeRow){
					window.trainsRowPoplayer.activeRow.css("height","");
					window.trainsRowPoplayer.activeRow.css('vertical-align','');
				}
				window.trainsRowPoplayer.resize = null;
				window.trainsRowPoplayer.resize = window.setInterval(function(){
					if(window.trainsRowPoplayer.activeRow){
						var __height = window.trainsRowPoplayer.height(),
							__rowHeight = window.trainsRowPoplayer.rowHeight;
						window.trainsRowPoplayer.activeRow.height(__height+__rowHeight);
						window.trainsRowPoplayer.activeRow.css('vertical-align','top');
					}
				});//监听高度变化改变tr高度
				var	rowPoplayer = window.trainsRowPoplayer,
					height = config.height,
					width = config.width || table.innerWidth(),
					rowHeight = row.height();
				if(!rowPoplayer.rowHeigth){
					rowPoplayer.rowHeight = rowHeight;
				}
				/*if(rowPoplayer.activeRow){
					rowPoplayer.activeRow.css("height","");
					window.rowPoplayer.activeRow.css('vertical-align','');
				}*/
				rowPoplayer.activeRow = row;
				rowPoplayer.css('min-height',height);
				rowPoplayer.width(width);
				if(config.element){
					rowPoplayer.append(config.element);
				}
				var offsetTop = getOffsetTop(row,container);
				rowPoplayer.css("top",offsetTop+rowHeight );
				rowPoplayer.show();
			}
			return rowPoplayer;
		};
		
		window.certificateRowOpt = function(row,config){
			var certificateRowPoplayer=null;
			var row = $(row),
				tables = row.parents('table'), 
				config = config || {};
			if(tables.length > 0){
				var table = tables.eq(0),//离tr最近的table
					container = table.parent();
				if(container && ( container.css('position')=='static' || container.css('position')=='' ) ){
					container.css('position','relative');
				}
				if(!container){
					container = $(document.body);
				}
				if( !window.certificateRowPoplayer){
					window.certificateRowPoplayer = $('<div class="tb_normal_poplayer"/>');
					container.append(window.certificateRowPoplayer);
					window.certificateRowPoplayer.css({
						'position': 'absolute',
						'border': '1px #d2d2d2 solid',
						'background-color': 'white'
					});
					topic.subscribe('certificateRowPoplayer.display.hide',function(){
						window.clearInterval(window.certificateRowPoplayer.resize);
						window.certificateRowPoplayer.resize = null;
						if(window.certificateRowPoplayer.activeRow){
							window.certificateRowPoplayer.activeRow.css("height","");
							window.certificateRowPoplayer.activeRow.css('vertical-align','');
						}
						window.certificateRowPoplayer.hide();
					});
				}
				window.clearInterval(window.certificateRowPoplayer.resize);
				if(window.certificateRowPoplayer.activeRow){
					window.certificateRowPoplayer.activeRow.css("height","");
					window.certificateRowPoplayer.activeRow.css('vertical-align','');
				}
				window.certificateRowPoplayer.resize = null;
				window.certificateRowPoplayer.resize = window.setInterval(function(){
					if(window.certificateRowPoplayer.activeRow){
						var __height = window.certificateRowPoplayer.height(),
							__rowHeight = window.certificateRowPoplayer.rowHeight;
						window.certificateRowPoplayer.activeRow.height(__height+__rowHeight);
						window.certificateRowPoplayer.activeRow.css('vertical-align','top');
					}
				});//监听高度变化改变tr高度
				var	rowPoplayer = window.certificateRowPoplayer,
					height = config.height,
					width = config.width || table.innerWidth(),
					rowHeight = row.height();
				if(!rowPoplayer.rowHeigth){
					rowPoplayer.rowHeight = rowHeight;
				}
				/*if(rowPoplayer.activeRow){
					rowPoplayer.activeRow.css("height","");
					window.rowPoplayer.activeRow.css('vertical-align','');
				}*/
				rowPoplayer.activeRow = row;
				rowPoplayer.css('min-height',height);
				rowPoplayer.width(width);
				if(config.element){
					rowPoplayer.append(config.element);
				}
				var offsetTop = getOffsetTop(row,container);
				rowPoplayer.css("top",offsetTop+rowHeight );
				rowPoplayer.show();
			}
			return rowPoplayer;
		};
		
		window.rewardsPunishmentsRowOpt = function(row,config){
			var rewardsPunishmentsRowPoplayer=null;
			var row = $(row),
				tables = row.parents('table'), 
				config = config || {};
			if(tables.length > 0){
				var table = tables.eq(0),//离tr最近的table
					container = table.parent();
				if(container && ( container.css('position')=='static' || container.css('position')=='' ) ){
					container.css('position','relative');
				}
				if(!container){
					container = $(document.body);
				}
				if( !window.rewardsPunishmentsRowPoplayer){
					window.rewardsPunishmentsRowPoplayer = $('<div class="tb_normal_poplayer"/>');
					container.append(window.rewardsPunishmentsRowPoplayer);
					window.rewardsPunishmentsRowPoplayer.css({
						'position': 'absolute',
						'border': '1px #d2d2d2 solid',
						'background-color': 'white'
					});
					topic.subscribe('rewardsPunishmentsRowPoplayer.display.hide',function(){
						window.clearInterval(window.rewardsPunishmentsRowPoplayer.resize);
						window.rewardsPunishmentsRowPoplayer.resize = null;
						if(window.rewardsPunishmentsRowPoplayer.activeRow){
							window.rewardsPunishmentsRowPoplayer.activeRow.css("height","");
							window.rewardsPunishmentsRowPoplayer.activeRow.css('vertical-align','');
						}
						window.rewardsPunishmentsRowPoplayer.hide();
					});
				}
				window.clearInterval(window.rewardsPunishmentsRowPoplayer.resize);
				if(window.rewardsPunishmentsRowPoplayer.activeRow){
					window.rewardsPunishmentsRowPoplayer.activeRow.css("height","");
					window.rewardsPunishmentsRowPoplayer.activeRow.css('vertical-align','');
				}
				window.rewardsPunishmentsRowPoplayer.resize = null;
				window.rewardsPunishmentsRowPoplayer.resize = window.setInterval(function(){
					if(window.rewardsPunishmentsRowPoplayer.activeRow){
						var __height = window.rewardsPunishmentsRowPoplayer.height(),
							__rowHeight = window.rewardsPunishmentsRowPoplayer.rowHeight;
						window.rewardsPunishmentsRowPoplayer.activeRow.height(__height+__rowHeight);
						window.rewardsPunishmentsRowPoplayer.activeRow.css('vertical-align','top');
					}
				});//监听高度变化改变tr高度
				var	rowPoplayer = window.rewardsPunishmentsRowPoplayer,
					height = config.height,
					width = config.width || table.innerWidth(),
					rowHeight = row.height();
				if(!rowPoplayer.rowHeigth){
					rowPoplayer.rowHeight = rowHeight;
				}
			/*	if(rowPoplayer.activeRow){
					rowPoplayer.activeRow.css("height","");
					window.rowPoplayer.activeRow.css('vertical-align','');
				}*/
				rowPoplayer.activeRow = row;
				rowPoplayer.css('min-height',height);
				rowPoplayer.width(width);
				if(config.element){
					rowPoplayer.append(config.element);
				}
				var offsetTop = getOffsetTop(row,container);
				rowPoplayer.css("top",offsetTop+rowHeight );
				rowPoplayer.show();
			}
			return rowPoplayer;
		};
		
		function getOffsetTop(child,parent){
			var offsetTop = 0;
			while( child[0] != parent[0] ){
				offsetTop += child[0].offsetTop;
				child = child.parent();
			}
			return offsetTop;
		}
});
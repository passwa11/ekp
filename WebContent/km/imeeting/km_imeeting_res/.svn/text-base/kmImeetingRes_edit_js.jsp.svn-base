<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<script>
	
	seajs.use([ 'lui/topic', 'lui/dialog','km/imeeting/resource/js/seat', 
	            'km/imeeting/resource/js/seatutil',
	            'km/imeeting/resource/js/elementlist'],
			function (topic, dialog,Seat,SeatUtil,ElementList) {	
		
		//////////////////////////////////////////////////////////////////
		// 全局变量
		//////////////////////////////////////////////////////////////////
		var cols = parseInt("${kmImeetingResForm.fdCols}");
		var rows = parseInt("${kmImeetingResForm.fdRows}");
		var tmpData = [];
		var tmpData = '${kmImeetingResForm.fdSeatDetail}';
		if(tmpData == ''){
			tmpData = [];
		}else{
			tmpData = JSON.parse(tmpData);
		}
	   
		//////////////////////////////////////////////////////////////////
		// 动态调整容器高度
		//////////////////////////////////////////////////////////////////
		window.resize = function(){
			setTimeout(function() {
				seat && seat.setOptions({
					maxHeight: (window.screen.height) + 'px',
					maxWidth: (window.screen.width-300) + 'px'
				});
			}, 100);
		}
		
		//////////////////////////////////////////////////////////////////
		// 主逻辑
		//////////////////////////////////////////////////////////////////
		
		//当前选中会议室元素
		var activeClass = null;
	    // 初始化会议室元素
	    var allElementData = [{type:"1",name:'座位',style:'status_seat'},
	                        {type:"2",name:'占位',style:'status_occupation'},
	                        {type:"3",name:'演讲台',style:'status_speech'},
	                        {type:"41",name:'屏幕-上',style:'status_screen_up'},
	                        {type:"42",name:'屏幕-下',style:'status_screen_down'},
	                        {type:"43",name:'屏幕-左',style:'status_screen_left'},
	                        {type:"44",name:'屏幕-右',style:'status_screen_right'},
	                        {type:"51",name:'门口-上',style:'status_door_up'},
	                        {type:"52",name:'门口-下',style:'status_door_down'},
	                        {type:"53",name:'门口-左',style:'status_door_left'},
	                        {type:"54",name:'门口-右',style:'status_door_right'}];
	    var allElementList = new ElementList($('#seatElement'), [], {
	    	mode: 'edit',
	    	onSelect: function(clazz) {
			    activeClass = clazz;
			}
	    });
	    allElementList.setData(allElementData);
	    
		
	    // 初始化坐席画布
	    var seat = new Seat($('#seat'), {
	    	data: []
	    }, {
			mode: 'edit',
			cols:cols,
	    	rows:rows,
	    	renderCol: function(col, year, month, date) {
	    		
	    	},
	    	afterRenderCol: function(col, year, month, date) {
	    		
	    	},
	    	
	    	beforeRenderData: function(cell, d) {
	    		(cell.attr('data-old') == 'true') ? '' : cell.css('background-color', 'transparent');
	    		cell.removeClass('status_seat status_occupation status_speech');
	    		cell.removeClass('status_screen_up status_screen_down status_screen_left status_screen_right');
	    		cell.removeClass('status_door_up status_door_down status_door_left status_door_right');
	    	},
	    	
	    	renderData: function(cell, d) {
				cell.addClass(d.clazz.style);
				if(d.clazz.type == "1"){
					$('<div/>').addClass('status_seat_no')
						.text(d.number).appendTo(cell);
				}else if(d.clazz.type == "2"){
					if(d.name){
						var mask = $('<span/>').addClass("lui_seat_table_text").appendTo(cell);
						$('<div/>').addClass("mask_name").text(d.name).appendTo(mask);
					}else{
						var mask = $('<span/>').addClass("lui_seat_table_text").appendTo(cell);
						$('<div/>').addClass("mask_name").text('右键编辑文字').appendTo(mask);
					}
				}
	    	},
	    	selectRange: function(data) {
	    		if(!activeClass) {
	        		dialog.alert('请选择会议室元素！');
	        		return;
	        	}
	    		var _flag = false;
				$.each(tmpData || [], function(_, t) {
					$.each(data || [], function(_, d) {
						if(d.x == t.x && d.y == t.y){
							_flag = true;
						}
					});
				});
				if(_flag){
					return;
				}
	    		var res = [];
	    		if(activeClass.type == "2" || activeClass.type == "41" || activeClass.type == "42" || activeClass.type == "43" || activeClass.type == "44"){
	    			if(data.length > 1){
						var flag = false;
						$.each(tmpData || [], function(_, t) {
							$.each(data || [], function(_, d) {
								if(d.x == t.x && d.y == t.y){
									if(t.clazz){
										flag = true;
									}
								}
							});
						});
						if(flag){
							dialog.alert('请选择没有数据的位置');
							return;
						}
					}
	    			var startX = data[0].x;
					var startY = data[0].y;
					var endX = data[data.length - 1].x;
					var endY = data[data.length - 1].y; 
					var col = endX - startX ;
					var row = endY - startY;
					res.push({
	        			x:startX,
	        			y:startY,
	        			col:col,
	        			row: row,
	        			clazz:activeClass
	        		});
	    		}else{
	    			var i = 0, l = data.length, t;
		    		
		    		for(i = 0; i < l; i++) {
		        		
		        		t = data[i];
		        		
		        		res.push({
		        			x:t.x,
		        			y:t.y,
		        			clazz:activeClass
		        		});
		        		
		        	}
	    		} 
	    		
	    		tmpData = SeatUtil.resolveData(tmpData.concat(res));
	    		seat.setData({
					data: tmpData
				});
	    		
	    		var seatCount  = getSeatCount(tmpData);
	    		$('[name="fdSeatCount"]').val(seatCount);
				document.getElementById("seatCount").innerHTML = seatCount;
			},
			contextMenuTitle: function(cell, data) {
				var menuTitle = '暂无数据';
				var i = 0, l = tmpData.length;
				for(i; i < l; i++) {
					var t = tmpData[i];
					if(t.x == data.x && t.y == data.y) {
						switch(t.clazz.type) {
						case '2':
							menuTitle = t.name ? t.name : '占位';
							break;
						case '1':
						case '3':
						case '41':
						case '42':
						case '43':
						case '44':
						case '51':
						case '52':
						case '53':
						case '54':
							menuTitle = t.clazz.name;
							break;
						default:
							break;
						}
					}
				}
				
				return menuTitle;
				
			},
			contextMenuItems: [
		    	{ 
		    		text: '删除',
		    		click: function(cell,data) {

		    			var res = [], 
		    				i = 0, l = tmpData.length;
		    			var type;
		    			for(i; i < l; i++) {
		    				var t = tmpData[i];
		    				if(t.x == data.x && t.y == data.y) {
		    					res.push(t);
		    					type = t.clazz.type;
		    				}
		    			}
		    			
		    			tmpData = SeatUtil.resolveData(tmpData.concat(res));
		    			if(type == "2" || type == "41" || type == "42" || type == "43" || type == "44"){
			    			seat.setData({
			    				data: tmpData
			    			},true);
		    			}else{
		    				seat.setData({
			    				data: tmpData
			    			});
		    			}
		    			
		    			if(type == "1"){
		    				var seatCount  = getSeatCount(tmpData);
				    		$('[name="fdSeatCount"]').val(seatCount);
							document.getElementById("seatCount").innerHTML = seatCount;
		    			}
		    		},
		    		visible: function(data) {
		    			var i = 0, l = tmpData.length;
		    			for(i; i < l; i++) {
							var t = tmpData[i];
							if(t.x == data.x && t.y == data.y) {
								return true;
							}
						}
		    			return false;
		    		}
		    	},
		    	{ 
		    		text: '命名',
		    		click: function(cell,data) {
		    			var name = "";
		    			$.each(tmpData || [], function(_, d) {
							if(d.x == data.x && d.y == data.y ){
								if(d.name){
									name = d.name;
								}
							}
						});
		    			dialog.iframe("/km/imeeting/km_imeeting_seat_template/import/template_setName.jsp?fdName="+name,
		                        '命名',function(value){
		    				if(value){
		    					var res = [];
								$.each(tmpData || [], function(_, d) {
									if(d.x == data.x && d.y == data.y ){
										res.push({
						        			x:d.x,
						        			y:d.y,
						        			name:value,
						        			col:d.col ,
						        			row:d.row ,
						        			clazz:d.clazz
						        		});
									}else{
										res.push($.extend({}, d));
									}
								});
								tmpData = res;
								seat.setData({
									data: tmpData
								});
		    				}
		    			},{width:300,height:200});
		    			
		    		},
		    		visible: function(data) {
		    			var i = 0, l = tmpData.length;
		    			for(i; i < l; i++) {
							var t = tmpData[i];
							if(t.x == data.x && t.y == data.y) {
								if(t.clazz.type == '2'){
									return true;
								}
							}
						}
		    			return false;
		    		}
		    	}
		    ]
	    });
	    
	    window.getSeatCount = function(data){
	    	var seatCount = 0;
			$.each(data || [], function(_, d) {
				if(d.clazz.type == "1"){
					seatCount++;
				}
			});
			return seatCount;
	    }
	    
	 	// 清空全部数据
		window.clearAllCustomData = function() {
			dialog.confirm('是否确认清空全部？', function(check) {
				if(check) {
					tmpData = [];
					seat.setData({
						data: tmpData
					},true);
					var seatCount  = getSeatCount(tmpData);
		    		$('[name="fdSeatCount"]').val(seatCount);
					document.getElementById("seatCount").innerHTML = seatCount;
				}
			});
		}
	 	
	 	window.onload = function(){
	 		seat.setData({
				data: tmpData
			});
	 	}
	 	
	 	window.addCol = function(){
	 		cols++;
	 		seat.addCol(tmpData);
	 		dialog.success('已添加成功');
	 	}
	 	
	 	window.addRow = function(){
	 		rows++;
	 		seat.addRow(tmpData);
	 		dialog.success('已添加成功');
	 	}
	 	
	 	window.commitMethod = function(method){
			var value = $("input[name='fdInnerScreenEnable']").val();
			if("true" == value){
				var optTB = document.getElementById("TABLE_DocList_Inner");
				var tbInfo = DocList_TableInfo[optTB.id];
				var index = tbInfo.lastIndex;
				if(index < 2){
					alert("内屏明细不能为空");
					return;
				}
			}
			var value = $("input[name='fdOuterScreenEnable']").val();
			if("true" == value){
				var optTB = document.getElementById("TABLE_DocList_Outer");
				var tbInfo = DocList_TableInfo[optTB.id];
				var index = tbInfo.lastIndex;
				if(index < 2){
					alert("外屏明细不能为空");
					return;
				}
			}
			
			var finalData = [];
			$.each(tmpData || [], function(_, d) {
				finalData.push($.extend({}, d));
			});
			var data = JSON.stringify(finalData);
			$('[name="fdSeatDetail"]').val(data);
			$('[name="fdCols"]').val(cols);
			$('[name="fdRows"]').val(rows);
			Com_Submit(document.kmImeetingResForm, method);
		}
	    
		window.changeSeatTemplate = function(val){
			
			dialog.confirm("切换坐席模板将清空已经设置的数据！！！",function(flag){
				if(flag){
					if(val == ""){
						tmpData = [];
						seat.setData({
							data: tmpData
						},true);
						var seatCount = 0;
			    		$('[name="fdSeatCount"]').val(seatCount);
						document.getElementById("seatCount").innerHTML = seatCount;
					}else{
						$.get('${LUI_ContextPath}/km/imeeting/km_imeeting_seat_template/kmImeetingSeatTemplate.do?method=getSeatDetail&fdSeatTemplateId='+val).then(function(res) {
							cols = parseInt(res.fdCols);
							rows = parseInt(res.fdRows);
							seat.setColAndRow(cols,rows);
							var data = [];
							var detatil = res.seatDetail;
							if(detatil == ""){
								detatil = [];
							}else{
								detatil = JSON.parse(detatil);
							}
							$.each(detatil, function(_, d) {
					    		data.push($.extend({}, d));
					    	});
							tmpData = data || [];
					    	seat.setData({
						    	data: data
						    });
					    	var seatCount  = res.seatCount;
				    		$('[name="fdSeatCount"]').val(seatCount);
							document.getElementById("seatCount").innerHTML = seatCount;
						});
					}
					
				}
			});
		}
		
		
		window.saveAsTemplate = function(){
	    	var fdSeatDetail = JSON.stringify(tmpData);
	    	var fdSeatCount = $('[name="fdSeatCount"]').val();
	    	var fdName = null;
	    	dialog.iframe("/km/imeeting/km_imeeting_seat_template/import/template_add.jsp?fdMainId=${param.fdId}",
                    '另存为座席模版',function(value){
	    		fdName = value;
	    		if(fdName == null){
	    			return;
	    		}else{
	    			window.del_load = dialog.loading();
					$.ajax({
						url: Com_Parameter.ContextPath+'km/imeeting/km_imeeting_seat_template/kmImeetingSeatTemplate.do?method=saveTemplate',
						type: 'POST',
						data:$.param({"fdSeatDetail":fdSeatDetail,"fdSeatCount":fdSeatCount,"fdName":fdName,"fdCols":cols,"fdRows":rows},true),
						dataType: 'json',
						error: function(data){
							if(window.del_load!=null){
								window.del_load.hide(); 
							}
							dialog.failure('${lfn:message("return.optFailure")}');
						},
						success: topCallback
					});		
	    		}
	    	},{width:300,height:200});
	    }
	    
	    window.topCallback = function(data){
			if(window.del_load!=null)
				window.del_load.hide();
			if(data!=null && data.status==true){
				dialog.success('${lfn:message("return.optSuccess")}');
			}else{
				dialog.failure('${lfn:message("return.optFailure")}');
			}
		};
	    
	});

</script>

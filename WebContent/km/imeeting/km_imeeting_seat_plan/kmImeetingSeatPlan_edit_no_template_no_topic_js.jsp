<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<script>
	
	seajs.use([ 'lui/topic', 'lui/dialog','km/imeeting/resource/js/seatPlan', 
	            'km/imeeting/resource/js/seat',
	            'km/imeeting/resource/js/seatutil',
	            'km/imeeting/resource/js/elementlist',
	            'km/imeeting/resource/js/personlist'],
			function (topic, dialog,SeatPlan,Seat,SeatUtil,ElementList,PersonList) {	
		
		
		//////////////////////////////////////////////////////////////////
		// 全局变量
		//////////////////////////////////////////////////////////////////
		var cols = 22;
		var rows = 15;
		var templateData = [];//保存座席设置数据
		
		var planSeatCount = 0;//已安排座位数
		
		var planData = [];// 用于保存当前排位信息
		
		var allPersonData = [];//所有人员信息
		
		var hostPersonData = [];//主持人信息
		
		var attendPersonData = [];// 参加人员信息
		
		var summaryPersonData = [];// 纪要人员信息
		
		var participantPersonData = [];//列席人员信息
		
		var reportersData = [];//上会材料汇报人信息
		
		var reporterData = [];//议题汇报人信息
		
		var attendUnitData = [];//议题列席人员信息
		
		var listenUnitData = [];//议题旁听人员信息
		
		var planElement = new Array();//已安排人员数组
		
		var showPlanFlag = false;//仅显示未排位人员
		
		var showAttendFlag = false;//仅显示回执且参加人员
	   
		//////////////////////////////////////////////////////////////////
		// 动态调整容器高度
		//////////////////////////////////////////////////////////////////
		setTimeout(function() {
			seatPlan && seatPlan.setOptions({
				maxHeight: (window.screen.height) + 'px',
				maxWidth: (window.screen.width-300) + 'px'
			});
		}, 2000);
	   
		setTimeout(function() {
			seat && seat.setOptions({
				maxHeight: (window.screen.height) + 'px',
				maxWidth: (window.screen.width-300) + 'px'
			});
		}, 2000);
		
		/**********************************************************
			座席设置
		**********************************************************/
		
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
	    var allElementList = new ElementList($('#seatTemplateElement'), [], {
	    	mode: 'edit',
			onSelect: function(clazz) {
			    activeClass = clazz;
			}
	    });
	    allElementList.setData(allElementData);
	    
		
	    // 初始化坐席画布
	    var seat = new Seat($('#seatTemplate'), {
	    	data: []
	    }, {
			mode: 'edit',
			cols:cols,
	    	rows:rows,
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
				$.each(templateData || [], function(_, t) {
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
						$.each(templateData || [], function(_, t) {
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
	    		templateData = SeatUtil.resolveData(templateData.concat(res));
	    		seat.setData({
					data: templateData
				});
	    		
	    		var seatCount  = getSeatCount(templateData);
	    		$('[name="fdSeatCount"]').val(seatCount);
				document.getElementById("seatCount").innerHTML = seatCount;
			},
			contextMenuTitle: function(cell, data) {
				var menuTitle = '暂无数据';
				var i = 0, l = templateData.length;
				for(i; i < l; i++) {
					var t = templateData[i];
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
		    				i = 0, l = templateData.length;
		    			var type;
		    			for(i; i < l; i++) {
		    				var t = templateData[i];
		    				if(t.x == data.x && t.y == data.y) {
		    					res.push(t);
		    					type = t.clazz.type;
		    				}
		    			}
		    			
		    			templateData = SeatUtil.resolveData(templateData.concat(res));
		    			if(type == "2" || type == "41" || type == "42" || type == "43" || type == "44"){
			    			seat.setData({
			    				data: templateData
			    			},true);
		    			}else{
		    				seat.setData({
			    				data: templateData
			    			});
		    			}
		    			
		    			if(type == "1"){
		    				var seatCount  = getSeatCount(templateData);
				    		$('[name="fdSeatCount"]').val(seatCount);
							document.getElementById("seatCount").innerHTML = seatCount;
		    			}
		    		},
		    		visible: function(data) {
		    			var i = 0, l = templateData.length;
		    			for(i; i < l; i++) {
							var t = templateData[i];
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
		    			$.each(templateData || [], function(_, d) {
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
								$.each(templateData || [], function(_, d) {
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
								templateData = res;
								seat.setData({
									data: templateData
								});
		    				}
		    			},{width:300,height:200});
		    		},
		    		visible: function(data) {
		    			var i = 0, l = templateData.length;
		    			for(i; i < l; i++) {
							var t = templateData[i];
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
	    
	 	// 清空全部数据
		window.clearAllCustomData = function() {
			dialog.confirm('是否确认清空全部？', function(check) {
				if(check) {
					templateData = [];
					seat.setData({
						data: templateData
					},true);
		    		$('[name="fdSeatCount"]').val(0);
					document.getElementById("seatCount").innerHTML = 0;
				}
			});
		};
	 	
	 	window.addCol = function(){
	 		cols++;
	 		seat.addCol(templateData);
	 		dialog.success('已添加成功');
	 	};
	 	
	 	window.addRow = function(){
	 		rows++;
	 		seat.addRow(templateData);
	 		dialog.success('已添加成功');
	 	};
	 	
		window.changeSeatTemplate = function(val){
			
			dialog.confirm("切换坐席模板将清空已经设置的数据！！！",function(flag){
				if(flag){
					if(val == ""){
						templateData = [];
						seat.setData({
							data: templateData
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
							templateData = data || [];
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
		};
	 	
	 	
	 	/****************************************************************
	 		座席安排
	 	****************************************************************/
		
		//当前选中会议室元素
		var activeClass = null;
		
		var elementList,hostList,participantList,summaryList,attendList,reportersList,reporterList
			,attendUnitList,listenUnitList,seatPlan;
		var isInit = false;
		topic.subscribe('JUMP.STEP', function(evt) {
			if(evt.cur == 1){
				if(!isInit){
					init();
				}
				var res = [];
				$.each(templateData || [], function(_, t) {
					var flag = false;
					$.each(planData || [], function(_, p) {
						if(t.x == p.x && t.y == p.y){
							if(t.clazz.type == p.clazz.type && p.clazz.elementId){
								flag = true;
								res.push({
									x:t.x,
									y:t.y,
									number:t.number,
									clazz:p.clazz
								});
							}
						}
					});
					if(!flag){
						res.push($.extend({}, t));
					}
				}); 
				
				planData = res;
				
				if(isInit){
					seatPlan.setColAndRow(cols,rows);
					initPerson();//初始化人员
				}else{
					seatPlan.setData({
						data: planData
					});
				}
				
				isInit = true;
			}
	 	});
	 	
	 	window.init = function(){
		    
		 	// 初始化主持人
		    hostList = new PersonList($('#hostTab'), [], {
				onSelect: function(clazz) {
				    activeClass = clazz;
				}
		    });
		    
		 	// 初始化参加人员
		    participantList = new PersonList($('#participantTab'), [], {
				onSelect: function(clazz) {
				    activeClass = clazz;
				}
		    });
		 	
		 	// 初始化纪要人员
		    summaryList = new PersonList($('#summaryTab'), [], {
				onSelect: function(clazz) {
				    activeClass = clazz;
				}
		    });
		 	
		 	// 初始化列席人员
		    attendList = new PersonList($('#attendTab'), [], {
				onSelect: function(clazz) {
				    activeClass = clazz;
				}
		    });
		 	
		 	// 初始化上会材料汇报人员
		    reportersList = new PersonList($('#reportersTab'), [], {
				onSelect: function(clazz) {
				    activeClass = clazz;
				}
		    });
		 	
		 	// 初始化议题汇报人员
		    reporterList = new PersonList($('#reporterTab'), [], {
				onSelect: function(clazz) {
				    activeClass = clazz;
				}
		    });
		 	
		 	// 初始化议题列席人员
		    attendUnitList = new PersonList($('#attendUnitTab'), [], {
				onSelect: function(clazz) {
				    activeClass = clazz;
				}
		    });
		 	
		 	// 初始化议题旁听人员
		    listenUnitList = new PersonList($('#listenUnitTab'), [], {
				onSelect: function(clazz) {
				    activeClass = clazz;
				}
		    });
		 	
		    initPerson();//初始化人员
		    
		    // 初始化坐席画布
		    seatPlan = new SeatPlan($('#seat'), {
		    	data: []
		    }, {
		    	cols:cols,
		    	rows:rows,
				mode: 'edit',
		    	renderCol: function(col, year, month, date) {
		    		
		    	},
		    	
		    	afterRenderCol: function(col, year, month, date) {
		    		
		    	},
		    	
		    	beforeRenderData: function(cell, d) {
		    		cell.css('background-color', 'transparent');
		    	},
		    	
		    	renderData: function(cell, d) {
		    		cell.addClass(d.clazz.style);
		    		if(d.clazz.type == "2"){
		    			if(d.name){
							var mask = $('<span/>').addClass("lui_seat_table_text").appendTo(cell);
							$('<div/>').addClass("mask_name").text(d.name).appendTo(mask);
						}else{
							var mask = $('<span/>').addClass("lui_seat_table_text").appendTo(cell);
							$('<div/>').addClass("mask_name").text('占位').appendTo(mask);
						}
					}
		    		if(d.clazz.nodeType == "2"){
		    			var mask = $('<span/>').addClass("lui_seat_table_mask").appendTo(cell);
		    			if(d.clazz.type == "1" || d.clazz.type == "0"){
							$('<span/>').addClass("mask_number").text(d.number).appendTo(mask);
		    			}
		    			
		    			$('<span/>').addClass("mask_name")
							.text(d.clazz.elementName).appendTo(mask);
						
		    			var delNode = $('<span/>').addClass("mask_close").appendTo(mask);
		    			delNode.on('click',function(e){
		    				e.stopPropagation();
		    				window.delData(d);
		    			});
		    			
		    		}else{
		    			if(d.clazz.type == "1" || d.clazz.type == "0"){
							$('<p/>').addClass("status_seat_no")
								.text(d.number).appendTo(cell);
						}
		    		}
		    	},
		    	
		    	selectRange: function(data) {
		    		if(!activeClass) {
		        		dialog.alert('请选择会议室元素或人员！');
		        		return;
		        	}
		    		var i = 0, l = data.length, t;
		    		
		    		var res = [];
		    		var nodeType = activeClass.nodeType;
		    		for(i = 0; i < l; i++) {
		        		
		        		t = data[i];
		        		if(nodeType == "1"){
		        			var _flag = false;
		    				$.each(planData || [], function(_, t) {
		    					$.each(data || [], function(_, d) {
		    						if(d.x == t.x && d.y == t.y){
		    							if(t.clazz.type == activeClass.type){
		    								_flag = true;
		    							}
		    						}
		    					});
		    				});
		    				if(_flag){
		    					return;
		    				}
		        			var flag = false;
		        			$.each(planData || [], function(_, d) {
		        				if(d.x == t.x && d.y == t.y && d.clazz.type != "0"){
		        					dialog.alert('请选择空白位置！');
		        					flag = true;
		        				}
		        			});
		        			if(!flag){
		        				res.push({
	    		        			x:t.x,
	    		        			y:t.y,
	    		        			clazz:activeClass
	    		        		});
		        			}
		        		}else if(nodeType == "2"){
		        			var _flag = false;
		    				$.each(planData || [], function(_, t) {
		    					$.each(data || [], function(_, d) {
		    						if(d.x == t.x && d.y == t.y){
		    							if(t.clazz.elementId){
		    								_flag = true;
		    							}
		    						}
		    					});
		    				});
		    				if(_flag){
		    					dialog.alert('请选择未安排的座位！');
		    					return;
		    				}
		        			$.each(planData, function(_, d) {
		        				if(d.x == t.x && d.y == t.y ){
		        					if(d.clazz.type == "1" || d.clazz.type == "0"){
		        						var elementId = activeClass.elementId;
		        						var elementName = activeClass.elementName;
		        						var cla = {type:d.clazz.type,style:d.clazz.style,name:d.clazz.name,nodeType:nodeType,elementId:elementId,elementName:elementName};
			        					res.push({
			    		        			x:t.x,
			    		        			y:t.y,
			    		        			number:d.number,
			    		        			clazz:cla
			    		        		});
			        					window.unActivePerson(elementId);
			        					planElement.push(elementId);
			        					planSeatCount++;
		        					}else{
		        						dialog.alert('请选择正确的座位！');
		        						res.push($.extend({}, d));
		        					}
		        				}else{
		        					res.push($.extend({}, d));
		        				}
		        			});
		        		}
		        	}
		    		if(nodeType == "1"){
		    			planData = SeatUtil.resolveData(planData.concat(res));
		    		}else if(nodeType == "2"){
		    			planData = res;
		    		}
		    		seatPlan.setData({
						data: planData
					},true);
		    		
				},
				contextMenuTitle: function(cell, data) {
					var menuTitle = '暂无数据';
					var i = 0, l = planData.length;
					for(i; i < l; i++) {
						var t = planData[i];
						if(t.x == data.x && t.y == data.y) {
							switch(t.clazz.type) {
							case '2':
								menuTitle = t.name ? t.name : '占位';
								break;
							case '0':
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
			    				i = 0, l = planData.length;
			    			for(i; i < l; i++) {
			    				var t = planData[i];
			    				if(t.x == data.x && t.y == data.y) {
			    					res.push(t);
			    				}
			    			}
			    			
			    			planData = SeatUtil.resolveData(planData.concat(res));
			    			seatPlan.setData({
			    				data: planData
			    			});
			    			
			    		},
			    		visible: function(data) {
			    			var i = 0, l = planData.length;
			    			for(i; i < l; i++) {
								var t = planData[i];
								if(t.x == data.x && t.y == data.y) {
									if(t.clazz.type == '0'){
										if(!t.clazz.elementId){
											return true;
										}
									}
								}
							}
			    			return false;
			    		}
			    	}
			    ]
		    });
		    
	 	};
	    
	    window.getSeatCount = function(data){
	    	var seatCount = 0;
			$.each(data || [], function(_, d) {
				if(d.clazz.type == "1" || d.clazz.type == "0"){
					seatCount++;
				}
			});
			return seatCount;
	    };
	    
	    
	    window.delData = function(data){
	    	window.activePerson(data.clazz.elementId);
	    	window.removePlanElement(data.clazz.elementId);//从已安排人员中移除当前人员
	    	var res = [];
	    	$.each(planData || [], function(_, d) {
	    		if(d.x == data.x && d.y == data.y ){
					var cla = {type:d.clazz.type,style:d.clazz.style,name:d.clazz.name,nodeType:"1"};
    				res.push({
		        		x:data.x,
		        		y:data.y,
		        		number:data.number,
		        		clazz:cla
		        	});
    				planSeatCount--;
				}else{
					res.push($.extend({}, d));
				}
			});
	    	planData = res;
	    	seatPlan.setData({
				data: planData
			});
	    	
	    };
	    
	    window.removePlanElement = function(elementId){
	    	var index =$.inArray(elementId,planElement);//兼容IE8
			if (index > -1) {
				planElement.splice(index, 1);
			}
	    };
	    
	    window.unActivePerson = function(elementId){
	    	$('.lui_seat_btn[data-elementid='+elementId+']').removeClass('lui_text_primary').addClass('lui_seat_person_selected');
	    	if(showPlanFlag == true){
	    		$('.lui_seat_btn[data-elementid='+elementId+']').addClass('unshow');
	    	}
	    	activeClass = null;
	    };
	    
	    window.activePerson = function(elementId){
	    	$('.lui_seat_btn[data-elementid='+elementId+']').removeClass('lui_seat_person_selected');
	    	$('.lui_seat_btn[data-elementid='+elementId+']').removeClass('unshow');
	    };
	    
	 	window.showPlanChange = function(){
	 		var val = $('[name="showPlan"]').val();
	 		if(val == 'true'){
	 			showPlanFlag = false;
	 			$('.lui_seat_person_selected').removeClass('unshow');
	 		}else{
	 			showPlanFlag = true;
	 			$('.lui_seat_person_selected').addClass('unshow');
	 		}
	 	};
	 	
	 	window.feedbackChange = function(){
	 		var val = $('[name="fdFeedback"]').val();
	 		if(val == 'true'){
	 			showAttendFlag = false;
	 		}else{
	 			showAttendFlag = true;
	 		}
	 		initPerson();
	 	};
	 	
	 	
	 	//初始化会议相关人员
	 	window.initPerson = function(){
	 		planElement.splice(0, planElement.length);//清空已安排人员
	 		planSeatCount = 0;//重置已安排座位数
	 		allPersonData = [];//重置人员信息
	 		hostPersonData = [];
	 		attendPersonData = [];
	 		summaryPersonData = [];
	 		participantPersonData = [];
	 		reportersData = [];
	 		reporterData = [];
	 		attendUnitData = [];
	 		listenUnitData = [];
	 		var url = "${LUI_ContextPath}/km/imeeting/km_imeeting_seat_plan/kmImeetingSeatPlan.do?method=getPersonData&fdImeetingMainId=${kmImeetingSeatPlanForm.fdImeetingMainId}";
 			if(showAttendFlag == true){
 				url += "&isFeedback=true";
 			}
	 		$.get(url).then(function(res) {
	 			
	 			//主持人
 				$.each(res.host || [], function(_, d) {
 					hostPersonData.push($.extend({},d));
 					allPersonData.push($.extend({},d));
 		    	});
 				hostList.setData(hostPersonData);
 		    	
 				//参加人员
 				$.each(res.attend || [], function(_, d) {
 					attendPersonData.push($.extend({},d));
 					allPersonData.push($.extend({},d));
 		    	});
 				attendList.setData(attendPersonData);
 				
 				//纪要人员
 				$.each(res.summary || [], function(_, d) {
 					summaryPersonData.push($.extend({},d));
 					allPersonData.push($.extend({},d));
 		    	});
 				summaryList.setData(summaryPersonData);
 				if(summaryPersonData.length <= 0){
 					$("#summaryWrap").hide();
 				}else{
 					$("#summaryWrap").show();
 				}
 				
 				//列席人员
 				$.each(res.participant || [], function(_, d) {
 					participantPersonData.push($.extend({},d));
 					allPersonData.push($.extend({},d));
 		    	});
 				participantList.setData(participantPersonData);
 				if(participantPersonData.length <= 0){
 					$("#participantWrap").hide();
 				}else{
 					$("#participantWrap").show();
 				}
 				
 				//上会材料汇报人员
 				$.each(res.reporters || [], function(_, d) {
 					reportersData.push($.extend({},d));
 					allPersonData.push($.extend({},d));
 		    	});
 				reportersList.setData(reportersData);
 				if(reportersData.length <= 0){
 					$("#reportersWrap").hide();
 				}else{
 					$("#reportersWrap").show();
 				}
 				
 				//议题汇报人员
 				$.each(res.reporter || [], function(_, d) {
 					reporterData.push($.extend({},d));
 					allPersonData.push($.extend({},d));
 		    	});
 				reporterList.setData(reporterData);
 				if(reporterData.length <= 0){
 					$("#reporterWrap").hide();
 				}else{
 					$("#reporterWrap").show();
 				}
 				
 				//议题列席人员
 				$.each(res.attendUnit || [], function(_, d) {
 					attendUnitData.push($.extend({},d));
 					allPersonData.push($.extend({},d));
 		    	});
 				attendUnitList.setData(attendUnitData);
 				if(attendUnitData.length <= 0){
 					$("#attendUnitWrap").hide();
 				}else{
 					$("#attendUnitWrap").show();
 				}
 				
 				//议题旁听人员
 				$.each(res.listenUnit || [], function(_, d) {
 					listenUnitData.push($.extend({},d));
 					allPersonData.push($.extend({},d));
 		    	});
 				listenUnitList.setData(listenUnitData);
 				if(listenUnitData.length <= 0){
 					$("#listenUnitWrap").hide();
 				}else{
 					$("#listenUnitWrap").show();
 				}
 				
 				removePerson();
 		    	
 			});
	 	};
	 	
	 	window.removePerson = function(){
	    	var res = [];
	    	$.each(planData || [], function(_, t) {
	    		if(t.clazz.elementId){
	    			var flag = false;
	    			$.each(allPersonData || [], function(_, d) {
						if(t.clazz.elementId == d.elementId){
							flag = true;
						}
					});
	    			if(!flag){
		    			var cla = {type:t.clazz.type,style:t.clazz.style,name:t.clazz.name,nodeType:"1"};
	    				res.push({
			        		x:t.x,
			        		y:t.y,
			        		number:t.number,
			        		clazz:cla
			        	});
		    		}else{
		    			res.push($.extend({}, t));
		    		}
	    		}else{
	    			res.push($.extend({}, t));
	    		}
			});
	    	planData = res;
	    	
	    	$.each(planData, function(_, d) {
   				if(d.clazz.elementId){
   					window.unActivePerson(d.clazz.elementId);
   					planElement.push(d.clazz.elementId);
   					planSeatCount++;
   				}
			});
	    	
	    	seatPlan.setData({
				data: planData
			});
	    };
	 	
	 	//一键排位
	 	window.autoPlan = function(type){
	 		var elementData = getPersonData(type);
	 		var planCount = elementData.length;
	 		
			$.each(elementData, function(_, p) {
				var elementId = p.elementId;
				var index =$.inArray(elementId,planElement);//兼容IE8
				if(index != -1){
					planCount--;
				}
			});
						
	 		var sCount = getSeatCount(planData);
	 		var count =  planCount - (sCount - planSeatCount);
	 		if(count > 0){
	 			dialog.confirm('座位数小于待安排人数(缺'+ count +'座)!<br>可通过添加临时坐席给人员安排位置。', function(check) {
		 			if(check){
		 				plan(elementData);
		 			}
	 			});
	 			
	 		}else{
	 			plan(elementData);
	 		}
	 	};
	 	
	 	
	 	window.plan = function(elementData){
	 		var res = [];
			$.each(planData, function(_, d) {
				if(d.clazz.type == "1" || d.clazz.type == "0"){
					if(!d.clazz.elementId){
						var flag = true;
						var elementId = "";
						var elementName = "";
						$.each(elementData, function(_, p) {
							elementId = p.elementId;
							elementName = p.elementName;
							var ind = $.inArray(elementId,planElement);//兼容IE8
							if(ind == -1){
								flag = false;
								return false;
							}
						});
						if(!flag){
							var cla = {type:d.clazz.type,style:d.clazz.style,name:d.clazz.name,nodeType:"2",elementId:elementId,elementName:elementName};
		   					res.push({
			        			x:d.x,
			        			y:d.y,
			        			number:d.number,
			        			clazz:cla
			        		});
		   					window.unActivePerson(elementId);
		   					planElement.push(elementId);
		   					planSeatCount++;
						}else{
							res.push($.extend({}, d));
						}
							
					}else{
						res.push($.extend({}, d));
					}
				}else{
					res.push($.extend({}, d));
				}
	 			
	 		});
			planData = res;
	 		seatPlan.setData({
				data: planData
			});
	 		
	 	};
	 	
	 	window.getPersonData = function(type){
	 		var elementData = [];
	 		switch(type){
		 		case 0:
		 			elementData = allPersonData;
		 			break;
		 		case 1:
		 			elementData = hostPersonData;
		 			break;
		 		case 2:
		 			elementData = attendPersonData;
		 			break;
		 		case 3:
		 			elementData = summaryPersonData;
		 			break;
		 		case 4:
		 			elementData = participantPersonData;
		 			break;
		 		case 5:
		 			elementData = reportersData;
		 			break;
		 		case 6:
		 			elementData = reporterData;
		 			break;
		 		case 7:
		 			elementData = attendUnitData;
		 			break;
		 		case 8:
		 			elementData = listenUnitData;
		 			break;
		 		default:
		 			break;
 			}
	 		elementData=getUniqueArray(elementData);
	 		return elementData;
	 	};
		
		//数组去重
		window.getUniqueArray=function(arr){
			var uniqueArr=[];
			for(var i=0;i<arr.length;i++){
				var index=getIndexInPersonData(arr[i],uniqueArr);
				if(index==-1){
					uniqueArr.push(arr[i]);
				}
			}
			return uniqueArr;
		}
		
		window.getIndexInPersonData=function(person,personArr){
			var index=-1;
			for(var i=0;i<personArr.length;i++){
				if(person.elementId==personArr[i].elementId){
					index=i;
					break;
				}
			}
			return index;
		}
	 	
	 	//重置
	 	window.resetPerson = function(type){
	 		var elementData = getPersonData(type);
	 		var res = [];
	 		
	 		$.each(planData, function(_, d) {
	 			var flag = false;
	 			$.each(elementData, function(_, e) {
	 				if(d.clazz.elementId == e.elementId){
	 					flag = true;
	 					window.activePerson(d.clazz.elementId);
	 			    	window.removePlanElement(d.clazz.elementId);//从已安排人员中移除当前人员
						var cla = {type:d.clazz.type,style:d.clazz.style,name:d.clazz.name,nodeType:"1"};
	    				res.push({
			        		x:d.x,
			        		y:d.y,
			        		number:d.number,
			        		clazz:cla
			        	});
	    				planSeatCount--;
	 				}
	 			});
	 			if(!flag){
	 				res.push($.extend({}, d));
	 			}
	 		});
	 		planData = res;
	    	seatPlan.setData({
				data: planData
			});
	 	};
		
	    // 保存数据
		window.submit = function(method) {
			var	data = JSON.stringify(planData);
			$('[name="fdSeatDetail"]').val(data);
			$('[name="fdCols"]').val(cols);
			$('[name="fdRows"]').val(rows);
			if(window.console){
				console.log(data);
			}
			Com_Submit(document.kmImeetingSeatPlanForm, method);
		};
	    
	});

</script>

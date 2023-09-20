<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<script>
	
	seajs.use(['lui/topic', 'lui/dialog', 'km/imeeting/resource/js/seat',
	           	'km/imeeting/resource/js/seatutil',
	            'km/imeeting/resource/js/elementlist'],
				function (topic, dialog,Seat,SeatUtil,ElementList) {	

		//////////////////////////////////////////////////////////////////
		// 动态调整容器高度
		//////////////////////////////////////////////////////////////////
		setTimeout(function() {
			seat && seat.setOptions({
				maxHeight: (window.screen.height) + 'px'
			});
		}, 100);
		
		//////////////////////////////////////////////////////////////////
		// 全局变量
		//////////////////////////////////////////////////////////////////
		var cols = parseInt("${kmImeetingSeatTemplateForm.fdCols}");
		var rows = parseInt("${kmImeetingSeatTemplateForm.fdRows}");
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
			onSelect: function(clazz) {
			    activeClass = clazz;
			}
	    });
	    allElementList.setData(allElementData);
	    
	    
	    var seat = new Seat($('#seat'), {
	    	data: []
	    }, {
	    	cols:cols,
	    	rows:rows,
			maxHeight: (window.screen.height - 256) + 'px',
	    	renderCol: function(col, year, month, date, element) {
	    		
	    	},
	    	afterRenderCol: function(col, year, month, date, element) {
	    		
	    	},
	    	
	    	beforeRenderData: function(cell, d, element) {
	    		(cell.attr('data-old') == 'true') ? '' : cell.css('background-color', 'transparent');
	    	},
	    	
	    	renderData: function(cell, d, element) {
	    		cell.addClass(d.clazz.style);
	    		if(d.clazz.type == "1"){
					$('<div/>').addClass('status_seat_no').text(d.number).appendTo(cell);
				}else if(d.clazz.type == "2"){
					if(d.name){
						var mask = $('<span/>').addClass("lui_seat_table_text").appendTo(cell);
						$('<div/>').addClass("mask_name").text(d.name).appendTo(mask);
					}else{
						var mask = $('<span/>').addClass("lui_seat_table_text").appendTo(cell);
						$('<div/>').addClass("mask_name").text('占位').appendTo(mask);
					}
				}
	    	},
	    	showContextMenu:false,
			contextMenuTitle: function(cell, data) {
				
			}
	    });
	    
	    var tempData = '${kmImeetingSeatTemplateForm.fdSeatDetail}';
	    tempData = JSON.parse(tempData);
	    seat.setData({
	    	data: tempData
	    }, true);
	    
		
	});

</script>

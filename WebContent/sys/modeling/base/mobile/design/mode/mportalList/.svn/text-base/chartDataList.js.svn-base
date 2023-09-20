/**
 * 
 */
define(function(require, exports, module) {
	
	var $ = require("lui/jquery");
	var base = require("lui/base");
	var env = require("lui/util/env");
	var topic = require("lui/topic");
	var source = require("lui/data/source");
	var render = require("lui/view/render");
	var modelingLang = require("lang!sys-modeling-base");
	var ChartDataList = base.DataView.extend({
		
		initProps: function($super, cfg) {
			$super(cfg);
			this.container = cfg.container;
			this.recordData = [];
			topic.channel("modelingChart").subscribe("leftNavChartClick",this.draw,this);
		},
		
		startup : function($super,cfg){
			$super(cfg);
		},
		
		draw: function(data) {
			var self = this;
			var url = Com_Parameter.ContextPath + "sys/modeling/base/mobile/modelingAppMobile.do?method=getChartData&appId="+data.appId+"&chartType="+data.chartType;
		    $.ajax({
		        url: url,
		        type: "post",
		        async : false,
		        success: function (rtn) {
		        	self.setRecordData(rtn);
		            self.doRender(rtn);
		        },
		        error : function(){
		        	
		        }
		    });
		},
		doRender : function(rtn){
			this.container.html("");
			for(var i =0;i < rtn.length; i++){
				var $trTmpl = $("<tr data-record-value='"+rtn[i].value+"' >");
				$trTmpl.append("<td>"+rtn[i].text+"</td>");
				$trTmpl.append("<td>"+rtn[i].creator+"</td>");
				$trTmpl.append("<td>"+rtn[i].creatTime+"</td>");
				this.container.append($trTmpl);
				// 添加事件
				$trTmpl.on("click",function(){
					$(this).siblings().removeClass("active");
					$(this).addClass("active");
				});
			}
		},
		
		setRecordData : function(data){
			this.recordData = data || [];	//[]
		},
		
		getRecordDataByValue : function(value){
			for(var i = 0;i < this.recordData.length;i++){
				if(this.recordData[i].value === value){
					return this.recordData[i];
				}
			}
		}
	});
	
	exports.ChartDataList = ChartDataList;
})
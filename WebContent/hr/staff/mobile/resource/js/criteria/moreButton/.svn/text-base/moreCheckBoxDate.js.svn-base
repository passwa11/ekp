define(["dojo/_base/declare", 
        "dijit/_WidgetBase",
        "dojo/dom-construct",
        "dijit/_Contained",
        "dijit/_Container",
        "dojo/on",
        "dojo/touch",
        "dojo/dom-class",
        "dojo/query","mui/i18n/i18n!hr-staff:mobile.hr.staff.view"], 
		function(declare, WidgetBase,domConstruct,_Contained, _Container,on,touch,domClass,query,msg) {
		return declare("hr.criteria.moreCheckBoxDate", [WidgetBase], {
			isRange:true,
			data:[],
			mult:false,
			buildRendering:function(){
				this.inherited(arguments);
			},
			startup:function(){
				//获取系统当前时间
		        var now = new Date();
		        this.now = now;
		        var nowDate = this.getCalcDate(now);
		        this.nowDateValue = "q.fdTimeOfEnterprise="+nowDate;
		        //获取系统前一周的时间
		        var beforeWeek = new Date(now-7*24*3600*1000);
		        var beforeWeekDate =this.getCalcDate(beforeWeek);
		        this.setDateValue(msg["mobile.hr.staff.view.9"],beforeWeekDate);
		        //获取系统前一个月的时间
		        this.setDateValue(msg["mobile.hr.staff.view.10"],this.getBeforeDate(1));
		        //获取系统前三个月的时间
		        this.setDateValue(msg["mobile.hr.staff.view.11"],this.getBeforeDate(3));
		        //获取系统前六个月的时间
		        this.setDateValue(msg["mobile.hr.staff.view.12"],this.getBeforeDate(6));
		        //获取系统前一年的时间
		        this.setDateValue(msg["mobile.hr.staff.view.13"],this.getBeforeDate(12));
				this.renderCheckBoxGroup(this.data);
			},
			getBeforeDate:function(month){
		        this.now.setMonth(this.now.getMonth()-month);
		        var beforeDate = this.getCalcDate(this.now);
		        this.now.setMonth(this.now.getMonth()+month);
		        return beforeDate;
			},
			setDateValue:function(label,dateString){
				var obj={label:label,value:"q.fdTimeOfEnterprise="+dateString+"&"+this.nowDateValue};
				this.data.push(obj);
			},
			getCalcDate:function(date){
		        var y = date.getFullYear();
		        var m = date.getMonth()+1;
		        var d = date.getDate();
		        return y+'-'+m+'-'+d;
			}
		});

});
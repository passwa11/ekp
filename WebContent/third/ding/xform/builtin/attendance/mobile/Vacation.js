define([
	"dojo/_base/declare",
	"dojo/dom-construct",
	"mui/form/Select",
	"dojo/topic",
	"dojo/dom-style"
], function(declare, domConstruct, Select, topic, domStyle){
	var Vacation = declare("third.ding.xform.builtin.attendance.mobile.Vacation", [Select], {
		
		// 获取请假数据完成事件
		VACATION_DATA_LOADED : "ding/vacation/data/loaded",
		
		isDataLoad : false,
		
		mul : false,
		
		startup : function() {
			var methodCode = "";
			var fdId = "";
			var urlParm = window.location.search;
			if (urlParm.indexOf("?") != -1) {
				var strs = urlParm.substr(1).split("&");
				for(var i=0;i<strs.length;i++){
			        var kv = strs[i].split('=');
			        if(kv[0] == 'fdId'){
			        	fdId = kv[1];
			        }
			        if(kv[0] == 'method'){
			        	methodCode = kv[1];
			        }
				}
			}
			this.url += "&methodCode="+methodCode+"&fdId="+fdId;
			if(!this.edit){
				// 非编辑状态，不需要发请求
				this.url = null;
			}
			this.inherited(arguments);
		},
		
		onComplete : function(){
			this.inherited(arguments);
			topic.publish(this.VACATION_DATA_LOADED, this);
			this.isDataLoad = true;
		},
		
		// 由于查询请假类型的接口太慢，先【加载中】，获取到数据之后再显示
		buildRendering : function(){
			this.inherited(arguments);
			if(this.edit){
				var _self = this;
				domStyle.set(this.contentNode, "display", "none");
				// 加载中
				var loadingDom = domConstruct.create("div",{
					"className" : "loading"
				},this.domNode);
				loadingDom.innerHTML = "加载中";
				topic.subscribe(this.VACATION_DATA_LOADED, function(){
					domStyle.set(_self.contentNode, "display", "block");
					domConstruct.destroy(loadingDom);
				});
			}
		},
		
		_onClick : function(){
			// 未获取到数据之前，点击无效
			if(this.isDataLoad){
				this.inherited(arguments);
			}
		},
		
		buildView : function() {
			this.selectNode = domConstruct.create('input', {
				name : this.name,
				type : 'hidden',
				value : this.value
			},this.domNode);
			domStyle.set(this.domNode, "display", "none");
		},
		
		//================================== 以下为处理源数据的方法
		formatValues : function(values) {
			this.values = this.handleSourceData(values);
		},
		
		handleSourceData : function(data){
			for(var i = 0;i < data.result.form_data_list.length;i++){
				var record = data.result.form_data_list[i]["extend_value"];
				record = eval("("+record+")");
				var unit = record["leaveViewUnit"];
				var name =  record["leaveName"];
				if(record.hasOwnProperty("leaveBalanceQuotaVo")){
					// 剩余假期
					//var restLeave = this.calculateRestLeave(record.detail["leave_quotas"],record["leave_view_unit"]);
					var unitTxt = (unit === "day" || unit === "halfDay") ? "天" : "小时";
				     if(unit === "day" || unit === "halfDay"){
				       name += "（剩余" + record["leaveBalanceQuotaVo"]["quotaNumPerDay"] + unitTxt + ")";
				       record["leave_rest"] = record["leaveBalanceQuotaVo"]["quotaNumPerDay"];
				     }else{
				       name += "（剩余" + record["leaveBalanceQuotaVo"]["quotaNumPerHour"] + unitTxt + ")";
				       record["leave_rest"] = record["leaveBalanceQuotaVo"]["quotaNumPerHour"];
				     }
				}else{
					record["leave_rest"] = null;
				}
				// 兼容控件的数据结构
				record["text"] = name;
				record["value"] = record["leaveCode"];
				data.result.form_data_list[i]=record;
			}
			return data.result.form_data_list;
		},
		
		getOptionText : function(record){
			var name = record["leaveName"];
			var unit = record["leaveViewUnit"];
			var unitTxt = (unit === "day" || unit === "halfDay") ? "天" : "小时";
			// record["leave_rest"]为null则不需要显示剩余额度
			if(typeof record["leave_rest"] === "number"){
				name += "（剩余" + record["leave_rest"] + unitTxt + ")";
			}
			return name;
		},
		
		findOptionByKey : function(key){
			for(var i = 0; i < this.values.length; i++){
				if(this.values[i]["leaveCode"] === key){
					return this.values[i];
				}
			}
		},
		
		// 计算剩余假期,unit:假期单位（day/halfDay/hour），day/halfDay ==> quota_num_per_day|used_num_per_day;hour ==> quota_num_per_hour|used_num_per_hour
		calculateRestLeave : function(leaveQuotas,unit){
			// 数据转换比例，钉钉规则，得到的数据除以比例才是正确的值
			var ratio = 100;
			var restLeave = 0;
			for(var i = 0;i < leaveQuotas.length; i++){
				var leaveQuota = leaveQuotas[i];
				// 当前时间如果大于结束时间则该假期记录已过期
				if(new Date().getTime() > leaveQuota["end_time"]){
					continue;
				}
				if(unit === "day" || unit === "halfDay"){
					restLeave += leaveQuota["quota_num_per_day"] - leaveQuota["used_num_per_day"];
				}else if(unit === "hour"){
					restLeave += leaveQuota["quota_num_per_hour"] - leaveQuota["used_num_per_hour"];
				}
			}
			return restLeave / ratio;
		}
	});
	return Vacation;
})
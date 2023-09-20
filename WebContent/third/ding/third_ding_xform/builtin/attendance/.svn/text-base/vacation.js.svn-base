/**
 * 假期控件
 */
define(function(require, exports, module) {
	
	var $ = require("lui/jquery");
	var base = require("lui/base");
	var source = require("lui/data/source");
	var render = require("lui/view/render");
	var topic = require("lui/topic");
	var strUtil = require("lui/util/str");
	
	var Vacation = base.DataView.extend({
		
		showStatus : "",
		
		value : "",
		
		curOptionInfo : {},
		
		sourceUrl : "/third/ding/thirdDingAttendance.do?method=getBizsuiteTypes&findRest=!{findRest}",
		
		renderSrc : "/third/ding/third_ding_xform/builtin/attendance/vacationRender.html",
		
		_init: function($super,cfg) {
			cfg.keyName = XForm_GeyKeyName(cfg.name);
			cfg.id = cfg.keyName;
			$super(cfg);
		},
		
		load : function($super,cfg){
			if(this.config.showStatus === "edit"){
				$super(cfg);
			}
		},
		
		startup : function($super,cfg){
			// 查看页面不发送请求了，表单有字段缓存了key对应的显示文字
			if(this.config.showStatus === "view"){
				this.element.append("<input type='hidden' name='"+ this.config.name +"' value='"+ this.config.value +"'/>");
			}else if(this.config.showStatus === "edit"){
				var status = DingCommonnGetStatus();
				var fdId = "";
				if("edit" == status){
					var urlParm = window.location.search;
					if (urlParm.indexOf("?") != -1) {
						var strs = urlParm.substr(1).split("&");
						for(var i=0;i<strs.length;i++){
					        var kv = strs[i].split('=');
					        if(kv[0] == 'fdId'){
					        	fdId = kv[1];
					        	break;
					        }
						}
					}
				}
				this.sourceUrl +="&methodCode="+status+"&fdId="+fdId
				this.sourceUrl = strUtil.variableResolver(this.sourceUrl,{"findRest":true});
				this.source = new source.AjaxJson({
					url : this.sourceUrl,
					parent : this
				});
				this.source.startup();
				this.source.resolveUrl({});
				
				this.render = new render.Template({
					parent : this,
					src : this.renderSrc
				});
				this.render.startup();
			}
			$super(cfg);
		},
		
		doRender : function($super,html){
			$super(html);
			var self = this;
			if(this.config.showStatus === "edit"){
				var $select = this.element.find("select");
				$select.on("change",function(){
					self.value = this.value;
					var optionInfo = self.findOptionByKey(this.value);
					self.curOptionInfo = optionInfo;
					topic.channel("ding").publish("leave.type.change",optionInfo);
					topic.channel("ding").publish("leave.type.unit.change",optionInfo);
				});
			}
		},
		
		//================================== 以下为处理源数据的方法
		handleSourceData : function(data){
			
			/*for(var i = 0;i < data.length;i++){
				var record = data[i];
				if(record.detail && record.detail["leave_quotas"]){
					// 剩余假期
					var restLeave = this.calculateRestLeave(record.detail["leave_quotas"],record["leave_view_unit"]);
					record["leave_rest"] = restLeave;
				}else{
					record["leave_rest"] = null;
				}
			}*/
			this.sourceData = data;
			return data;
		},
		
		findOptionByKey : function(key){
			var record ="";
			for(var i = 0; i < this.sourceData.result.form_data_list.length; i++){
				if(this.sourceData.result.form_data_list[i]["extend_value"]){
					record = this.sourceData.result.form_data_list[i]["extend_value"];
					record = eval("("+record+")");
					if(record["leaveCode"] === key){
						return this.sourceData.result.form_data_list[i]["extend_value"];
					}
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
	
	exports.Vacation = Vacation;
})
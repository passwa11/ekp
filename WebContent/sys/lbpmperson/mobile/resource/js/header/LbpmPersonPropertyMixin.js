/**
 * 筛选静态数据源
 */
define([
	"dojo/_base/declare",
	"dojo/_base/lang",
	"mui/util",
   	"mui/i18n/i18n!sys-lbpmperson:mui",
   	"dojo/json",
   	"dojo/request",
   	"dojo/topic",
   	"mui/dialog/Tip"
], function(declare,lang,util,msg,JSON,request,topic,tip) {
  return declare("sys.lbpmperson.mobile.header.LbpmPersonPropertyMixin", null, {
    filters: [
      {
          filterType: "FilterDatetime",
          type: "date",
          name: "fdCreateTime",//创建时间筛选器
          subject: msg['mui.lbpmperson.person.creatorTime']
      },
      {
          filterType: "FilterRadio",
          name: "fdModelName",//所属模块筛选器
          subject: msg['mui.lbpmperson.flow.order.module'],
          options: []
      }
    ],
    
    startup: function() {
		this.inherited(arguments);
		this.initAllModule();
	},
	
	//替换所属模块
	setModuleFilterOptions:function(allModule){
		var moduleFilter;
		for(var i=0; i<this.filters.length; i++){
			var filter = this.filters[i];
			if(filter.name != 'fdModelName'){
				continue;
			}
			moduleFilter = filter;
			break;
		}
		if(moduleFilter){
			allModule = allModule || [];
			var options = [];
			for(var i=0; i<allModule.length; i++){
				var module = allModule[i];
				var option = {};
				option.name = module.text;
				option.value = module.value;
				options.push(option);
			}
			moduleFilter.options = options;
		}
	},
	
	//初始化所属模块
	initAllModule:function(){
		if(this.allModule){
			this.setModuleFilterOptions(this.allModule);
		}else{
			var _self = this;
			var url = util.formatUrl("/sys/lbpmperson/sys_lbpmperson_myprocess/SysLbpmPersonMyProcess.do?method=getModule");
			request.post(url, {
				handleAs : 'json',
				data : ''
			}).then(function(result){
				if(!result || result.length==0){
					return;
				}
				_self.allModule = result;
				_self.setModuleFilterOptions(result);
			},function(e){
				window.console.error("获取所属某块数据出错,error:" + e);
			});
		}
	},
	
	submit:function(){
		if(!this.validateSelectValues()){//提交前先校验
			return;
		}
		this.inherited(arguments);
	},
	
	validateSelectValues:function(){
		return true;
	},
	
	getStaticFilters:function(){
		var statusFilter;
		for(var i=0; i<this.filters.length; i++){
			var filter = this.filters[i];
			if(filter.name != 'docStatus'){
				continue;
			}
			statusFilter = filter;
			break;
		}
		var statusFilterNew = {
	          filterType: "FilterRadio",
	          name: "docStatus",//状态筛选器
	          subject: msg['mui.lbpmperson.flow.docStatus'],
	          options: [
	            {name: msg['mui.lbpmperson.docReject'], value: "11"},
	            {name: msg['mui.lbpmperson.docPending'], value: "20"},
	            {name: msg['mui.lbpmperson.docPublish'], value: "30"},
	            {name: msg['mui.lbpmperson.docAbandoned'], value: "00"}
	          ]
	      };
		if(statusFilter){
			var index = this.filters.indexOf(statusFilter);
			//更新状态筛选器
			if(this.key == '0'){//我发起的 草稿 代审 驳回 废弃 结束
				this.filters.splice(index, 1);
				statusFilterNew.options.unshift({name: msg['mui.lbpmperson.docDraft'], value: "10"});
				this.filters.push(statusFilterNew);
			}else if(this.key == '1' || this.key == '4' || this.key == '5'){//废弃箱
				this.filters.splice(index, 1);
			}else if(this.key == '2' || this.key == '3'){
				this.filters.splice(index, 1);
				this.filters.push(statusFilterNew);
			}
		}else{
			if(this.key == '0'){//我发起的 草稿 代审 驳回 废弃 结束
				statusFilterNew.options.unshift({name: msg['mui.lbpmperson.docDraft'], value: "10"});
				this.filters.push(statusFilterNew);
			}else if(this.key == '2' || this.key == '3'){
				this.filters.push(statusFilterNew);
			}
		}
		return this.inherited(arguments);
	}
  })
})

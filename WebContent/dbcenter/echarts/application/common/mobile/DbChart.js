/**
 * 
 */
define(["dojo/_base/declare","dijit/_WidgetBase","sys/xform/mobile/controls/xformUtil"],function(declare,WidgetBase,xUtil){
	return declare("dbcenter.echarts.application.common.mobile.DbChart",[WidgetBase],{
		
		name : '',
		
		categoryId : '',
		
		showstatus : 'edit',
		
		inputs : '',
		
		chartType : '',
		
		// 配置模式：dy.开头默认为入参,编程模式：q.
		queryPrefix : "dy.",
		
		executor: null,
		
		buildRendering : function(){
			if(this.chartType == '11'){
				this.queryPrefix = "q.";
			}
			if(this.inputs != ''){
				this.inputs = JSON.parse(this.inputs.replace(/quot;/g,"\""));
				// 添加输入监控
				this.subscribe("/mui/form/valueChanged","addInputBind");
			}
		},
		
		load : function(){
			// 获取入参
			var url = this.origUrl;
			if(this.inputs != ''){
				for(var key in this.inputs){
					var input = this.inputs[key];
					var value = input.value;
					var wgt = xUtil.getXformWidgetsBlur(null,value);
					if(wgt && wgt.length > 0 && wgt[0].value != ''){
						url += "&" + this.queryPrefix + key + "=" + wgt[0].value;
					}else if(value != ''){
						url += "&" + this.queryPrefix + key + "=" + value;
					}
				}
			}
			// 发送请求
			this.executor.set("url",url);
		},
		
		addInputBind : function(srcObj, arguContext){
			if(srcObj){
				// 判断触发的组件是否是输入控件
				// 获取触发控件的ID
				var evtObjName = srcObj.get("name");
				if(evtObjName == null || evtObjName == ''){
					return;
				}
				if(evtObjName.indexOf('.value(') > -1){
					if(/\.(\d+)\./g.test(evtObjName)){
						evtObjName = evtObjName.match(/\((\w+)\.(\d+)\.(\w+)/g)[0].replace("(","");	
					}else{
						evtObjName = evtObjName.match(/\((\w+)/g)[0].replace("(","");
					}
				}else{
					return;
				}
				if(evtObjName != null && evtObjName != ''){
					for(var key in this.inputs){
						var input = this.inputs[key];
						var controlId = input.value;
						if(evtObjName == controlId){
							this.load();
						}
					}
				}
			}
		}
	});
});
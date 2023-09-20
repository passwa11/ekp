/**
 * 
 */
(function(){
	
	function ChartControl(config){
		this.controlId = "";
		this.domNode;
		this.showStatus = "edit";
		this.chartType = ""; // 图表模式：配置模式01、编程模式11、自定义数据00
		this.inputs = {};
		this.executor;
		// 配置模式：dy.开头默认为入参,编程模式：q.
		this.queryPrefix = "dy.";
		
		this._init = _ChartControl_Init;
		this._initInputs = _ChartControl_InitInputs;
		this._addInputsListener = _ChartControl_AddInputsListener;
		this.setRequestParam = ChartControl_SetRequestParam;
		this.load = ChartControl_Load;
		
		this._init(config);
	}
	
	function _ChartControl_Init(config){
		this.controlId = config.controlId || "";
		this.domNode = config.domNode || null;
		this.showStatus = config.showStatus || "edit";
		this.inputs = config.inputs ? this._initInputs(config.inputs) : {};
		this.executor = config.executor || null;
		this.chartType = config.chartType || "";
		if(this.chartType == '11'){
			this.queryPrefix = "q.";
		}
		
		// 添加输入监控
		this._addInputsListener(this.inputs);
		
	}
	
	function _ChartControl_InitInputs(inputs){
		// inputs : {dataSource1.fd_3415df0bf318c2: {value: "fdId", text: "ID"}}
		var rs = []; // [{domNode:input,field:xxxx}]
		var self = this;
		for(var key in inputs){
			var input = {};
			
			input.field = key;
			var inputName = inputs[key].value;
			input.controlId = inputName;
			// 以 {}括起来的字段，为固定字段，一般为当前用户的id、姓名或者当前时间，不需要前端获取，后端会处理
			if(input.controlId.indexOf("{") > -1){
				input.viewVal = input.controlId;
			}else{
				if(self.showStatus == "edit"){
					//获取绑定的事件控件对象
					var bindStr = document.getElementById(input)?"#" + inputName:'[name*="' + inputName + '"]';
					input.domNode = $(bindStr);		
				}else if(self.showStatus == "view"){
					// xform_data_hide 来源于xform:viewValueTag标签
					if(xform_data_hide && xform_data_hide[inputName]){
						input.viewVal = xform_data_hide[inputName];
					}
				}				
			}
			
			rs.push(input);
		}
		return rs;
	}
	
	function _ChartControl_AddInputsListener(inputs){
		if(inputs.length > 0){
			var self = this;
			for(var i = 0;i < inputs.length;i++){
				var input = inputs[i];
				if(input.domNode){
					input.domNode.on('change',function(){
						self.load();
					});					
				}
			}	
		}
	}
	
	function ChartControl_Load(){
		var self = this;
		var params = {};
		// 获取入参的值
		for(var i = 0;i < self.inputs.length;i++){
			var input = self.inputs[i];
			var val = "";
			if(input.domNode){
				if(input.domNode.length > 1){
					for(var j = 0;j < input.domNode.length;j++){
						var elem = input.domNode[j];
						// radio
						if(elem.type && elem.type == 'radio'){
							if(elem.checked){
								val = elem.value;
								break;
							}
						}
					}
				}else{
					val = input.domNode.val();
				}	
			}else if(input.viewVal){
				val = input.viewVal;
			}
			
			if(val != ""){
				params[self.queryPrefix + input.field.replace(/\|/g,".")] = val;				
			}
		}
		self.setRequestParam(params);
		self.executor.load();
	}
	
	function ChartControl_SetRequestParam(params){
		// ui/js/chart/chart.js才有chartdata
		var self = this;
		if(self.executor.chartdata){
			self.executor.chartdata.params = params;
		}else{
			self.executor.requestParams = params;
		}
	}
	
	window.ChartControl = ChartControl;
})()
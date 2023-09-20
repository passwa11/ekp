/**
 * 入参构造
 */
(function(){
	
	function DbEchartsAppInputs(domNodeWrap){
		this.domNodeWrap = domNodeWrap;
		this.values = {}; // 存储的入参信息，入参属性为key
		this.lang = [];
		
		this.buildInput = DbEchartsAppInputs_BuildInput;
		this.buildInputInView = DbEchartsAppInputs_BuildInputInView;
		this.choosePro = DbEchartsAppInputs_ChoosePro;
		this.setValuesAndDom = DbEchartsAppInputs_SetValuesAndDom;
		this.clearValues = DbEchartsAppInputs_ClearValues;
		this.getKeyData = DbEchartsAppInputs_GetKeyData;
		this.buildTable = DbEchartsAppInputs_BuildTable;
		this.buildInputByType = DbEchartsAppInputs_BuildInputByType;
		
		this.init = DbEchartsAppInputs_Init;
		this.init();
	}
	
	function DbEchartsAppInputs_Init(){
		// 初始化多语言 选择;当前用户相关;时间相关;当前时间;人员列表没有和该入参匹配的类型！
		var lang = [];
		lang.push("dbcenter-echarts-application:dbEchartsNavTree.choose");
		lang.push("dbcenter-echarts-application:dbEchartsNavTree.currentUser");
		lang.push("dbcenter-echarts-application:dbEchartsNavTree.timeParamter");
		lang.push("dbcenter-echarts-application:dbEchartsNavTree.currentTime");
		lang.push("dbcenter-echarts-application:dbEchartsNavTree.notPassWarning");
		this.lang = Data_GetResourceString(lang.join(";"));
	}
	
	function DbEchartsAppInputs_BuildInputInView(values){
		var html = [];
		for(var key in values){
			var value = values[key];
			html.push(value.inputText + ": ");
			html.push(value.text);
			html.push("<br/>");
		}
		return this.domNodeWrap.append(html.join(""));
	}
	
	// param : {"item":{"mainModelName":xxx},"value":模板id,"text":模板名称}
	function DbEchartsAppInputs_BuildInput(param,cb){
		this.domNodeWrap.empty();
		this.values = {};
		var inputs = [];
		var data = {};// modelName id 
		data.modelName = param.item.mainModelName;
		data.id = param.value;
		var that = this;
		$.ajax({
			type : "post",
			async : true,//是否异步
			url : Com_Parameter.ContextPath + "dbcenter/echarts/application/dbEchartsApplication.do?method=findDynamic",
			data : data,
			dataType : "json",
			success : function(ajaxRn) {
				inputs = ajaxRn;
				console.log("inputs：",inputs)
				that.buildTable(inputs);
				if(cb){
					cb(inputs);
				}
			},
			error : function (e){
				console.log("调用异常：",e)
			}
		});

	}
	
	function DbEchartsAppInputs_BuildTable(config){
		var insStr = "";
		// config:{tables:配置模式)、长宽设置、图表类型（饼图、折线图）、入参（inputs:编程模式）、类型（type：配置模式01、编程模式11、自定义数据00)}
		var type = config.type;
		// 编程模式
		if(type == "11"){
			/*if(config.inputs){
				var $wrap = $("<div>");
				this.domNodeWrap.append($wrap);
				for(var i = 0;i < config.inputs.length;i++){
					//{name:xxx,type:xxx,text:xxx,textVal:xxx}
					var inputField = config.inputs[i];
					var input = {};
					input.name = inputField.key;
					input.type = inputField.format;
					input.text = inputField.name;
					this.buildInputByType(input,$wrap);
				}
			}*/
		}else{
			if(config.tables){
				var tables = config.tables;
				if(tables.length > 0){
					for ( var i = 0; i < tables.length; i++) {
						var table = tables[i];
						if(table.dynamic && table.dynamic.length > 0){
							var $wrap = $("<div>");
							this.domNodeWrap.append($wrap);
							if(table.titleTxt){
								$wrap.append("<div style='text-align:center;'>" + table.titleTxt + "</div>");
							}
							for(var j = 0;j < table.dynamic.length;j++){
								var dynamic = table.dynamic[j];
								var input = {};
								var fieldName = dynamic.field.name;
								if(table && table.titleVal){
									fieldName = table.titleVal + "." + fieldName;
								}
								input.name = fieldName;
								input.type = dynamic.field.type;
								input.text = dynamic.field.text;
								
								this.buildInputByType(input,$wrap);
							}
						}
					}
				}
			}
		}
	}
	
	// 单个输入项 input:{name:xxx,type:xxx,text:xxx,textVal:xxx}
	function DbEchartsAppInputs_BuildInputByType(input,$wrap){
		var html = [];
		var self = this;
		var paramName = input.text;
		html.push("<span>" + paramName + ":</span>");
		var fieldName = input.name.replace("\|",".");
		html.push("<div style='display:inline-block;margin-left:6px;width:80%;'>");
		html.push("<input type=text name=\"dbcenter_"+fieldName+"\" readonly='readonly' style='width:100px' class='inputsgl'/>");//添加前缀dbcenter_以免name重复
		html.push("<a href='javascript:void(0);' style='color:#4285f4;' >"+ this.lang[0] +"</a>");
		html.push("</div>");
		html.push("<br/>");
		var $html = $(html.join(""));
		$html.find("a").click(function(){
			self.choosePro(fieldName,input.type,paramName);
		});
		$wrap.append($html);
	}
	
	function DbEchartsAppInputs_GetLastFieldBySepa(str,separator){
		var arr = str.split(separator);
		return arr[arr.length - 1];
	}
	
	function DbEchartsAppInputs_ChoosePro(fieldName,inputType,inputText){
		var self = this;
		var type = DbEchartsAppInputs_GetLastFieldBySepa(inputType,"|");
		var items = userInfo.getItems(type);
		if(items.length > 0){
			var url = Com_Parameter.ContextPath + "dbcenter/echarts/application/common/fields_tree.jsp?inputType="+ type;
			var data = [];
			data.push({"text":this.lang[1],"vars":items,"braces":true});
			data.push({"text":this.lang[2],"vars":[{'field':'date_datetime' ,'fieldText':this.lang[3],'fieldType':'dateTime'}],"braces":true});
			var dialog = new DbEchartsApplication_Dialog(url,data,function(rn){
				var value = {};
				value[fieldName] = rn;
				value[fieldName].inputText = inputText; // 查看页面用
				self.setValuesAndDom(value);
			});
			dialog.setWidth("300");
			dialog.setHeight("380");
			dialog.show();
		}else{
			alert(this.lang[4]);
		}
	}
	
	function DbEchartsAppInputs_ClearValues(){
		var self = this;
		self.values = {};
	}
	
	function DbEchartsAppInputs_SetValuesAndDom(rn,dom){
		var self = this;
		for(var key in rn){
			if(!self.values[key]){
				self.values[key] = {};
			}
			self.values[key].text = rn[key].text;
			self.values[key].value = rn[key].value;
			self.values[key].inputText = rn[key].inputText;
			$("[name*='"+key+"']",self.domNodeWrap).val(rn[key].text);
		}
	}
	
	function DbEchartsAppInputs_GetKeyData(){
		return this.values;
	}
	
	window.DbEchartsAppInputs = DbEchartsAppInputs;
})()
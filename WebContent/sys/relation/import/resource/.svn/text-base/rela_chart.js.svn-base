/*压缩类型：标准*/
Com_IncludeFile("rela.css", Com_Parameter.ContextPath + "sys/relation/import/resource/","css",true);
//czk2019
//人员选择配置
function RelationChartSetting(params){
	this.relationType = "9";
	this.params = params;
	this.varName = "_relationCfg";
	this.rela_tmpId = params.tempId;
	this.CurrentSetting = {};
	var self = this;
	window[self.varName] = self;
	if(self.params["varName"]!=null && self.params["varName"]!=''){
		window[self.params["varName"]] = self;
	}
	/*********************************对外调用函数************************************************/
	//加载
	this.onload = function(){
		
	
		var setting = parent[self.varName].CurrentSetting;
		if(setting.fdId!=null){//编辑
			if(setting.fdType==self.relationType){
				self.rela_tmpId = setting.fdId;
				$("input[name='fdModuleName']").val(setting['fdModuleName']);
				if(setting['fdCCType']==null||setting['fdCCType']==""){
					document.getElementsByName("fdCCType")[0].value = "chart";
					$("tr#fct").show();
				}else{
					document.getElementsByName("fdCCType")[0].value = setting['fdCCType'];
					if(setting['fdCCType']=="chart"){
						$("tr#fct").show();
					}else{
						$("tr#fct").hide();
					}
				}
				document.getElementsByName("fdChartId")[0].value = setting['fdChartId'];
				document.getElementsByName("fdChartName")[0].value = setting['fdChartName'];
				document.getElementsByName("fdChartType")[0].value = setting['fdChartType'];
				document.getElementsByName("fdDynamicData")[0].value = setting['fdDynamicData'];
				if(setting['fdDynamicData']!=null&&setting['fdDynamicData']!=""&&setting['fdDynamicData']!="undefined"){
					var dd = JSON.parse(setting['fdDynamicData']);
					var html = "";
					var json = {};
					var repeat = "";
					$.each(dd,function(name,value) {
						if(name.indexOf("_tip")!=-1){
							name = name.replace('_tip','');
						}else if(name.indexOf("_type")!=-1){
							name = name.replace('_type','');
						}else if(name.indexOf("_text")!=-1){
							name = name.replace('_text','');
						}else if(name.indexOf("_value")!=-1){
							name = name.replace('_value','');
						}else if(name.indexOf("_param")!=-1){
							name = name.replace('_param','');
						}
						if(repeat.indexOf(name)==-1){
							repeat += name+";";
						}
					});
					var dds = repeat.split(";");
					var select = Data_GetResourceString("button.select");
					$.each(dds,function(index,value) {
						if(value!=null&&value!=""){
							json = fieldJson(dd,value);
							html += json.tip+":<input type='text' name='dynamic_"+json.name+"' readonly='readonly' class='inputsgl' style='width:65%' value='"+ json.text +"'>";
							html += "<a href='javascript:void(0)' onclick='_Designer_Control_Attr_Dbechart_Input_Choose(\""+ json.name +"\",\""+ json.type+"\",\""+ json.tip+"\",\""+ json.param +"\");'>"+select+"</a>";
							html += "<br>";
						}
					});
					$("#dbEchart_Input_wrap").html(html);
				}
				
			}
		}
		
		//保存按钮事件
		$("#rela_config_save").click(function(){
			var flag = true;
			var fdChartId = document.getElementsByName("fdChartId")[0];
			var fdChartName = document.getElementsByName("fdChartName")[0];
			if (checkValueIsNull("fdChartId") || checkValueIsNull("fdChartName")) {// 人员
				fdChartId.focus();
				alert(Data_GetResourceString("sys-relation:sysRelationEntry.chart.type.chart.select")); //请先选择统计图表
				flag = false;
			}
			var fdDynamicData = document.getElementsByName("fdDynamicData")[0];
			/*if(fdDynamicData){
				$("input[name^='dynamic_']").each(function(){
					if(this.value==""&&flag){
						alert(Data_GetResourceString("sys-relation:sysRelationEntry.chart.type.no"));//入参不能为空！
						flag = false;
					}
				});
			}*/
			if(flag){
				self.saveConfig();
			}
		});
		//取消按钮事件
		$("#rela_config_close").click(function(){
			self.closeConfig();
		});
		//预览配置条件
		$("#rela_config_preview").click(function(){
			self.previewConfig();
		});
		//加载配置
		self._rela_condition_cfg_load();
		
		if($("#rela_condition>tbody>tr").size()>=20){
			$("#viewAllP").show();
		}
		//加载校验
		self._rela_static_validator();
	};
	//预览
	this.previewConfig = function(){
		var confgData = self._rela_get_condition_cfg();
		
		if(confgData!=null){
			if(parent[self.varName]!=null)
				parent[self.varName].previewConfig(confgData);
		}
	};
	//保存
	this.saveConfig = function(){
		if(!self.S_Valudator.validate()){
			return ;
		}
		var confgData = self._rela_get_condition_cfg();
		if(confgData!=null){
			if(parent[self.varName]!=null)
				parent[self.varName].saveConfig(confgData);
		}
	};
	//关闭配置
	this.closeConfig = function(){
		if(parent[self.varName]!=null){
			parent[self.varName].closeConfig();
			
		}
	};
	//iframe高度
	this.resizeFrame = function(){
		try {
			if(window.frameElement!=null && window.frameElement.tagName=="IFRAME"){
				window.frameElement.style.height = ($(document.body).height()+10)+ "px";
				if(parent[self.varName] != null)
					parent[self.varName].resizeFrame();
			}
		} catch(e) {
		}
	};
	//逻辑运算联动，大于小于等于
	this.refreshLogicDisplay = function(obj , id){
		self._rela_condition_refreshLogicDisplay(obj,id);
	};
	//自动匹配
	this.refreshRelationDisplay = function(index , trObj){
		self._rela_condition_refreshRelationDisplay(index , trObj);
	};
	//模糊匹配
	this.refreshMatchDisplay = function(index , trObj){
		self._rela_condition_refreshMatchDisplay(index , trObj);
	};
	/*********************************内部调用函数************************************************/
	//condition详细配置加载
	this._rela_condition_cfg_load = function(){
		if(parent[self.varName]!=null && parent[self.varName].CurrentSetting){
			var relationConditions = parent[self.varName].CurrentSetting;
			var trList = $("#rela_condition tr[kmss_type]");
			for(var i=0; i<trList.length; i++){
				var trObj = $(trList[i]);
				var fdItemName = $("input[name='fdItemName_"+i+"']").val();
				var relationCondition = relationConditions[fdItemName];
				if(relationCondition==null) continue;
				//模糊搜索
				if(relationCondition.fdBlurSearch=="1"){
					$("input[name='fdBlurSearch_"+i+"']").attr("checked","checked");
				}
				//关联搜索
				if(relationCondition.fdVarName!="" && relationCondition.fdVarName==fdItemName){
					$("input[name='fdVarName_"+i+"']").attr("checked","checked");
				}
				var type = trObj.attr("kmss_type");
				var fdParameter1_ = $("[name='fdParameter1_"+i+"']:input");
				var fdParameter2_ = $("[name='fdParameter2_"+i+"']:input");
				var fdParameter3_ = $("[name='fdParameter3_"+i+"']:input");
				var values = relationCondition.fdParameter1.split(";");
				switch(type){
					case "string":
						self._rela_setFieldValue(fdParameter1_,relationCondition.fdParameter1);
						break;
					case "date":
					case "number":
						self._rela_setFieldValue(fdParameter1_,relationCondition.fdParameter1);
						self._rela_setFieldValue(fdParameter2_,relationCondition.fdParameter2);
						self._rela_setFieldValue(fdParameter3_,relationCondition.fdParameter3);
						if(type=='date'){
							self._rela_condition_refreshLogicDisplay(fdParameter1_,"span_fdParameter3_"+i);
						}
						break;
					case "foreign":
						if(fdItemName=="docKeyword"){
							self._rela_setFieldValue(fdParameter2_,relationCondition.fdParameter2);
						}else{
							//一般查询
							if(relationCondition.fdParameter1!=""){
								self._rela_setFieldValue(fdParameter1_,relationCondition.fdParameter1);
								self._rela_setFieldValue($("input[name='t0_"+i+"']"),relationCondition.fdParameter2);
								break;
							}
							//是否模糊查询
							if(relationCondition.fdBlurSearch=="1"){
								self._rela_setFieldValue(fdParameter2_,relationCondition.fdParameter2);
								break;
							}
							//是否包含子节点
							if(relationCondition.fdParameter3!=""){
								$("input[name='t1_"+i+"']").attr("checked","checked");
								self._rela_setFieldValue(fdParameter1_,relationCondition.fdParameter3);
								self._rela_setFieldValue($("input[name='t0_"+i+"']"),relationCondition.fdVarName);
							}
						}
						break;
					case "enum":
						self._rela_setFieldValue(fdParameter1_,relationCondition.fdParameter1);
						break;
				}
				self._rela_condition_refreshMatchDisplay(i,trObj);
				self._rela_condition_refreshRelationDisplay(i,trObj);
			}
		}
		self.resizeFrame();
	};
	
	this._rela_get_condition_cfg = function(){
		var setting = {};
		setting.fdId = self.rela_tmpId;
		setting.fdType = self.relationType;
		setting['fdModuleName'] = $("input[name='fdModuleName']").val();
		var fdCCType = document.getElementsByName("fdCCType")[0];
		var fdChartId = document.getElementsByName("fdChartId")[0];
		var fdChartName = document.getElementsByName("fdChartName")[0];
		var fdChartType = document.getElementsByName("fdChartType")[0];
		var fdDynamicData = document.getElementsByName("fdDynamicData")[0];
		setting['fdParameter'] = currentUserId;
		setting['fdModuleModelName'] = "com.landray.kmss.dbcenter.echarts.model.DbEchartsChart";
		if("custom"==fdCCType.value){
			setting['fdModuleModelName'] = "com.landray.kmss.dbcenter.echarts.model.DbEchartsCustom";
		}
		setting['fdCCType'] = fdCCType.value;
    	setting['fdChartId'] = fdChartId.value;
    	setting['fdChartName'] = fdChartName.value;
    	setting['fdChartType'] = fdChartType.value;
    	setting['fdDynamicData'] = fdDynamicData.value;
		return  setting;
	};
	
	//设置域对应值
	this._rela_setFieldValue = function(elemObj , value){
		var values = value.split(";"); 
		if(elemObj.is(":text")){
			elemObj.val(value);
		}else if(elemObj.is(":radio")||elemObj.is(":checkbox")){
			elemObj.each(function(index,tmpEle){
				var tmpObj = $(tmpEle);
				if(Com_ArrayGetIndex(values,tmpObj.val())>-1){
					tmpObj.attr("checked","checked");
				}
			});
		}else{
			elemObj.val(value);
		}
	};

	
	//获取域值
	this._rela_getFiledValue = function(elemName){
		var elemObj = $("[name='"+elemName+"']:input");
		if(elemObj.is(":text")){
			return elemObj.val();
		}else if(elemObj.is(":radio")||elemObj.is(":checkbox")){
			var rtnStr = "";
			elemObj.each(function(index,ele){
				var tmpObj = $(ele);
				if(tmpObj.is(":checked")){
					rtnStr += ";" + tmpObj.val();
				}
			});
			return rtnStr!=""?rtnStr.substring(1):"";
		}else{
			return elemObj.val();
		}
		return "";
	};
	
	//获取域表述
	this._rela_getFiledText = function(elemName){
		var elemObj = $("[name='"+elemName+"']:input");
		if(elemObj.is(":text")){
			return elemObj.val();
		}else if(elemObj.is(":radio")||elemObj.is(":checkbox")){
			var rtnStr = "";
			elemObj.each(function(index,ele){
				var tmpObj = $(ele);
				if(tmpObj.is(":checked")){
					rtnStr += ";" + tmpObj.parent().text();
				}
			});
			return rtnStr!=""?rtnStr.substring(1):"";
		}else{
			if(elemObj.attr("tabName")=='SELECT'){
				return elemObj.find("option:selected").text();
			}else{
				return elemObj.val();
			}
		}
		return "";
	};
	
	//模糊查询时联动
	this._rela_condition_refreshMatchDisplay = function(index , trObj){
		if(trObj==null){
			trObj = $("#rela_condition tr[kmss_type]:nth-child("+(index + 1)+")");
		}
		var obj = $("input[name='fdBlurSearch_"+index+"']");
		if(obj.length<=0) return;
		var type = trObj.attr("kmss_type");
		if(type=="foreign"){
			if($("input[name='fdItemName_"+index+"']").val()=="docKeyword"){
				return ;
			}
			var detailTr = trObj.find("table[rela_Detail='1'] tr");
			if(obj.is(":checked")){
				detailTr.find("td:nth-child(1)").hide();
				detailTr.find("td:nth-child(2)").show();
			}else{
				detailTr.find("td:nth-child(1)").show();
				detailTr.find("td:nth-child(2)").hide();
			}
		}
	};
	
	//自动关联设置时联动
	this._rela_condition_refreshRelationDisplay = function(index , trObj){
		if(trObj==null){
			trObj = $("#rela_condition tr[kmss_type]:nth-child("+(index + 1)+")");
		}
		var obj =  $("input[name='fdVarName_"+index+"']");
		if(obj.length<=0) return;
		if(obj.is(":checked")){
			trObj.find(">td:nth-child(2)").hide();
			trObj.find(">td:nth-child(3)").show();
			$("#tipDiv_"+index).show();
		}else{
			trObj.find(">td:nth-child(2)").show();
			trObj.find(">td:nth-child(3)").hide();
			$("#tipDiv_"+index).hide();
		}
	};
	
	//用于大于等运算符处理显示逻辑
	this._rela_condition_refreshLogicDisplay = function(obj , id){
		if(obj==null){
			return ;
		}
		var spanObj = $("#" + id);
		var selectObj = $(obj);
		if(selectObj.val() == "bt"){
			spanObj.show();
		}else{
			spanObj.hide();
		}
	};
	
	//建立一个新的配置
	this._rela_condition_createNewCondition = function(){
		return {fdId:"",fdTitle:"",fdItemName:"",fdParameter1:"",fdParameter2:"",fdParameter3:"",fdBlurSearch:"",fdVarName:""};
	};
	
	this._rela_static_validator = function(){
		if(self.S_Valudator == null)
			self.S_Valudator = $GetKMSSDefaultValidation(null,{afterElementValidate:function(){
				self.resizeFrame();
				return true;
			}});
		 $("input[name='fdModuleName']").each(function(){
			 self.S_Valudator.addElements(this);
		 });
	};
	Com_AddEventListener(window, "load", function(){
		self.onload();
	});
	
	//显示全部条件,当条件超过20个时，后面的条件隐藏，点击“显示全部”，可以显示所有条件。
}
function viewAllConditions(){
	var trObj = $("#rela_condition tr").show();
	$("#viewAllP").hide();
	window["_relationCfg"].resizeFrame();
}

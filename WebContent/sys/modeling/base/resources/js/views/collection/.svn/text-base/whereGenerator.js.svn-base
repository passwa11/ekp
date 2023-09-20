/**
 *
 */
define(function(require, exports, module) {

	var $ = require('lui/jquery'),
		base = require('lui/base'),
		topic = require('lui/topic'),
		str = require('lui/util/str'),
		relationDiagram = require('sys/modeling/base/mobile/resources/js/relationDiagram'),
		formulaBuilder = require("sys/modeling/base/relation/trigger/behavior/js/formulaBuilder"),
		selectUnion = require('sys/modeling/base/mobile/resources/js/selectUnion');
	var modelingLang = require("lang!sys-modeling-base");
	var valueOptions = {
		"!{fix}" : {"name":modelingLang['modelingAppListview.enum.fix'],  valueDrawFun:"fixDraw" },
		"!{empty}" : {"name":modelingLang['modelingAppListview.enum.empty'], valueDrawFun:"nullDraw"},
		"!{dynamic}" : {"name":modelingLang['modelingAppListview.enum.dynamic'], valueDrawFun:"nullDraw"},
		"!{formula}" : {"name":modelingLang['modelingAppListview.enum.formula'], valueDrawFun:"formulaDraw"}
	};

	var __tmpHtml = "";
	__tmpHtml += "<div class='model-edit-view-oper'>";
	//头部
	__tmpHtml += "<div class='model-edit-view-oper-head'>";
	__tmpHtml += "<div class='model-edit-view-oper-head-title'><div onclick='changeToOpenOrClose(this)'><i class='open'></i></div>" +
		"<span>"+modelingLang['listview.sort.item']+"<span class='title-index'>!{rowIndex}</span></span></div>";
	__tmpHtml += "<div class='model-edit-view-oper-head-item'><div class='del' onclick='updateRowAttr(0,\"no-position\",this);delTr(this,\"where\");'><i></i></div></div>";
	__tmpHtml += "</div>";
	//内容
	__tmpHtml += "<div class='model-edit-view-oper-content'>";
	__tmpHtml += "<ul class='list-content'>";

	// 类型
	var options = [{
		name : modelingLang['listview.custom.query.items'],
		value : "0"
	},{
		name : modelingLang['listview.built-in.query.items'],
		value : "1"
	}];
	__tmpHtml += "<li class='model-edit-view-oper-content-item first-item' style='display:none;'><div class='item-title'>"+modelingLang['modelingAppListview.fdType']+"</div>";
	__tmpHtml += "<div class='item-content'>";
	__tmpHtml += "<input type='hidden' name='!{randomName}_fdWhereType'/>";
	__tmpHtml += "<div class='view_flag_radio'>";
	for(var i = 0;i < options.length;i++){
		__tmpHtml += "<div class='mobile_list_view view_flag_radio_no' data-where-value='"+ options[i].value +"' style='margin-right: 1px;'>";
		__tmpHtml += "<i class='view_flag_no'></i>";
		__tmpHtml += "<span>" + options[i].name + "</span>";    //自定义查询项||内置查询项
		__tmpHtml += "</div>";
	}
	__tmpHtml += "</div>";
	__tmpHtml += "</div></li>";


	//有流程还是无流程
	if(listviewOption.isEnableFlow.isFlowBoolean == "true" || listviewOption.isEnableFlow.isFlow == "true")
	{
		// 内置查询项
		options = [{
			name : modelingLang['modeling.flow.whereType.create'],
			value : "create"
		},{
			name : modelingLang['modeling.flow.whereType.approval'],
			value : "approval"
		},{
			name : modelingLang['modeling.flow.whereType.approved'],
			value : "approved"
		}];

	}else{

		options = [{
			name : modelingLang['modeling.flow.whereType.create'],
			value : "create"
		}];

	}

	__tmpHtml += "<li class='model-edit-view-oper-content-item last-item' data-bind-type-value='1'><div class='item-title'>"+modelingLang['modeling.builtIn.query']+"</div>";
	__tmpHtml += "<div class='item-content select_union'>";
	__tmpHtml += "<select class='inputsgl' style='margin:0 4px;width:100%' name='!{randomName}_fdPredefinedWhereType'>";
	for(var i = 0;i < options.length;i++){
		__tmpHtml += "<option value='"+ options[i].value +"'>"+ options[i].name +"</option>";
	}
	__tmpHtml += "</select>";
	__tmpHtml += "</div></li>";

	//字段
	__tmpHtml += "<li class='model-edit-view-oper-content-item field' data-bind-type-value='0'><div class='item-title'>"+modelingLang['relation.field']+"</div></li>";

	//运算符
	__tmpHtml += "<li class='model-edit-view-oper-content-item' data-bind-type-value='0'><div class='item-title'>"+modelingLang['modelingAppViewincpara.fdOperator']+"</div>";
	__tmpHtml += "<div class='item-content'><select class='inputsgl selectCover' name='!{randomName}_operator' style='vertical-align: middle;width:100%'>"
	__tmpHtml += "</select></div></li>";
	//值
	__tmpHtml += "<li class='model-edit-view-oper-content-item last-item' data-bind-type-value='0'><div class='item-title'>"+modelingLang['modelingAppViewincpara.fdValue']+"</div>";
	__tmpHtml += "<div class='item-content'>";
	__tmpHtml += "<select class='inputsgl marginWidth selectCover' name='!{randomName}_value' style='width:30%;'>";
	__tmpHtml += "</select>";
	__tmpHtml += "<div class='value_input input_radio height28' style='width: 64%;'></div>";
	__tmpHtml += "</div></li>";

	__tmpHtml += "</ul>";
	__tmpHtml += "</div></div>";

	var WhereGenerator = base.Component.extend({

		initProps: function($super, cfg) {
			$super(cfg);
			this.randomName = "fd_" + parseInt(((new Date().getTime()+Math.random())*10000)).toString(16);
			this.container = cfg.container || null;
			this.fieldWgt = null;
			this.parent = cfg.parent;
			this.storeData = cfg.data || {};
			this.whereType = cfg.wheretype || "";
		},

		startup : function($super, cfg) {
			$super(cfg);
			// 监听下拉框值改变事件
			topic.channel("modeling").subscribe("field.change",this.fieldChange, this);
		},

		draw : function($super, cfg){
			$super(cfg);
			var $tr = $("<tr />").appendTo(this.container);
			$tr.addClass("whereTr");
			this.element = $tr;
			var self = this;
			var rowIndex = this.container.find("tr.whereTr").length;
			var html = "";
			html += "<td>";
			// 替换模板HTML里面的变量
			html += str.variableResolver(__tmpHtml,{
				randomName : this.randomName,
				rowIndex : rowIndex
			});
			html += "</td>";
			$tr.append(html);
			// 属性字段，通过组件的方式处理
			var $field = $tr.find("ul.list-content").find("li.field").eq(0);
			var $fieldTd = $("<div class='item-content' />").appendTo($field);
			if (!listviewOption.baseInfo){
				return
			}
			var fieldOptions = this.filterSubTableField(listviewOption.baseInfo.modelDict);
			this.fieldWgt = new selectUnion({container: $fieldTd,parent:this,options:fieldOptions,type:"where",value:self.storeData.field});
			this.fieldWgt.draw();

			// 恰好下拉框选项的value是!{xxx}格式，把option写在__tmpHtml里面会被替换
			var valueOptionsHtml = "";
			for(var key in valueOptions){
				var option = valueOptions[key];
				valueOptionsHtml += "<option value='"+ key +"'>"+ option.name +"</option>";
			}

			$tr.find("select[name='"+ self.randomName +"_value']").append(valueOptionsHtml);

			/***************** 添加事件 start *******************/
			// 类型选项的切换
			$tr.find(".view_flag_radio_no").on("click", function(){
				// 点亮图标
				$(this).parent().find(".view_flag_no").removeClass("view_flag_yes");
				$(this).find(".view_flag_no").addClass("view_flag_yes");
				// 联动相关项显示和隐藏
				var type = "";
				if(self.whereType == "sys_query"){
					type = "1";
				}else if(self.whereType == "custom_query"){
					type = "0";
				}
				//var type = $(this).attr("data-where-value");
				$tr.find("[data-bind-type-value]").each(function(){
					if($(this).attr("data-bind-type-value") === type){
						$(this).fadeIn();
					}else{
						$(this).hide();
					}
				});
				$(this).closest(".item-content").find("[name*='fdWhereType']").val(type);
				// 发布事件，更新头部标题
				topic.channel("modeling").publish("whereType.change",{
					value : type,
					dom : this,
					wgt : self
				});
			});
			// 值下拉框值改变事件
			$tr.find("select[name='"+ self.randomName +"_value']").on("change",function(){
				var $valueInput = $tr.find(".value_input");
				var operator = $tr.find("select[name='"+ self.randomName +"_operator']").val();
				var valueDrawHtml = self[valueOptions[this.value]["valueDrawFun"]]();
				if(this.value == '!{fix}'){
					valueDrawHtml = self[valueOptions[this.value]["valueDrawFun"]](operator);
				}
				self.updateOperatorTdByFieldValue($tr,this.value);
				$valueInput.html("");
				$valueInput.append(valueDrawHtml);
			});
			//运算符切换改变事件
			$tr.find("select[name='"+ self.randomName +"_operator']").on("change",function(){
				var $valueInput = $tr.find(".value_input");
				var valType = $tr.find("select[name='"+ self.randomName +"_value']").val();
				if(valType === '!{fix}'){
					var valueDrawHtml = self[valueOptions[valType]["valueDrawFun"]](this.value);
					$valueInput.html("");
					$valueInput.append(valueDrawHtml);
				}
			});
			//列表视图优化时发现移动的内置查询项下拉框值改变没有更新它的标题，现修改
			$tr.find("[name*='fdPredefinedWhereType']").on("change",function () {
				topic.channel("modeling").publish("field.change",{dom:this});
			})
			/***************** 添加事件 end *******************/
			/***************** 初始化已有值 start *******************/
			if(self.storeData.fieldOperator){
				$tr.find("select[name='"+ self.randomName +"_operator']").val(self.storeData.fieldOperator);
			}
			if(self.storeData.isIncludeSub){
				$tr.find("[name='"+ self.randomName +"_isIncludeSub']").prop("checked", self.storeData.isIncludeSub === "true" ? true : false);
			}
			if(self.storeData.fieldValue){
				$tr.find("select[name='"+ self.randomName +"_value']").val(self.storeData.fieldValue);
				$tr.find("select[name='"+ self.randomName +"_value']").trigger($.Event("change"));
			}else{
				$tr.find("select[name='"+ self.randomName +"_value']").val("!{fix}");
				$tr.find("select[name='"+ self.randomName +"_value']").trigger($.Event("change"));
			}
			if(self.storeData.fieldInputValue){
				var $inputValDom = $tr.find("[name='"+ self.randomName +"_fieldInputValue']");
				// 多个，默认为单选
				if($inputValDom.length > 1){
					var values = [];
					if(self.storeData.fieldInputValue != ''){
						values = self.storeData.fieldInputValue.split(";");
					}
					for(var i = 0;i < $inputValDom.length;i++){
						var dom = $inputValDom[i];
						if(values && $.inArray(dom.value,values) > -1){
							$(dom).prop("checked", true);
						}
					}
				}else{
					$inputValDom.val(self.storeData.fieldInputValue);
				}
			}
			if(self.storeData.fieldInputValueText){
				$tr.find("[name='"+ self.randomName +"_fieldInputValue_name']").val(self.storeData.fieldInputValueText);
			}
			var fdWhereType = self.storeData.whereType || "0";
			$tr.find("[data-where-value='"+ fdWhereType +"']").trigger($.Event("click"));
			if(self.storeData.predefined){
				$tr.find("[name='"+ self.randomName +"_fdPredefinedWhereType']").val(self.storeData.predefined);
			}
			/***************** 初始化已有值 end *******************/
				//更新角标
			var index = this.container.find("> tbody > tr").last().find(".title-index").text();
			this.container.find("> tbody > tr").last().find(".title-index").text(parseInt(index)+1);
			//更新向下的图标
			this.container.find("> tbody > tr").last().prev("tr").find("div.down").show();
			$tr.find("div.down").hide();
			//修改默认标题
			/*var text = "";
			var fieldId =$tr.find("div.select_union").find("select").eq(0).val();
			var fieldText = $tr.find("div.select_union").find("select").eq(0).find("option[value='"+fieldId+"']").text();
			text = fieldText;
			fieldId =$tr.find("div.select_union").find("select").eq(1).val();
			fieldText = $tr.find("div.select_union").find("select").eq(1).find("option[value='"+fieldId+"']").text();
			if(fieldText){
				text += "|"+fieldText;
			}
			$tr.find(".model-edit-view-oper-head-title span").html(text);*/
		},
		//更新运算符
		updateOperatorTdByFieldValue :function($tr,fieldValue) {
			var $whereRuleFieldOperatorSelect = $tr.find("select[name='"+ this.randomName +"_operator']");
			if(fieldValue === '!{empty}'){
				//当为空值时，只有运算符等于和不等于，其他隐藏
				$whereRuleFieldOperatorSelect.find("option").each(function () {
					if($(this).val() != "="
						&& $(this).val() != "!="
						&& $(this).val() != "!{equal}"
						&& $(this).val() != "!{notequal}"
						&& $(this).val() != "eq" ){
						$(this).css("display","none");
					}
				})
				//为空值时，需要重新设置运算符下拉框选中等于
				var operator = $whereRuleFieldOperatorSelect.find("option:selected").val();
				if(operator != "="
					&& operator != "!="
					&& operator != "!{equal}"
					&& operator != "!{notequal}"
					&& operator != "eq" ){
					$whereRuleFieldOperatorSelect.prop("selectedIndex",0);
				}
			}else{
				//为其他类型时，将隐藏的运算符重新显示出来
				$whereRuleFieldOperatorSelect.find("option").each(function () {
					if($(this).css("display") == "none"){
						$(this).css("display","");
					}
				})
			}

		},

		/*过滤明细表的字段*/
		filterSubTableField : function(fields){
			var allField = fields || [];
			if(typeof allField === 'string'){
				allField = $.parseJSON(fields);
			}
			var newAllField = [];
			for(var i=0; i<allField.length; i++){
				var field = allField[i];
				if(field.isSubTableField){
					continue;
				}
				//#169064 查询条件支持部门查询
				// if(field.orgType === "ORG_TYPE_ORG|ORG_TYPE_DEPT"){
				// 	continue;
				// }
				newAllField.push(field);
			}
			return newAllField;
		},


		// 属性字段值改变时触发，更新运算符的选项
		// {dom:xxx, type:xxx, wgt:xxxx}
		fieldChange : function(argu){
			if(argu.type === "where"){
				//此处兼容思维导图和树形视图start
				var targetModelId = argu.dom.closest(".nodeSettingTable").find("[name='fdTargetModelId']").val();
				if(targetModelId && this.parent && typeof (this.parent.getWidgetData) == "function"){
					this.parent.getWidgetData(targetModelId);
				}
				//此处兼容思维导图和树形视图end
				if(argu.dom.closest("tr")[0] === this.element[0]){
					// 更新运算符
					var item = relationDiagram.getDiagram("where",this.fieldWgt.getCurType()).operator;

					var $operator = this.element.find("select[name='"+ this.randomName +"_operator']");
					$operator.html("");
					// 清除兄弟节点
					$operator.siblings().remove();
					$operator.show();
					var html = "";
					var val = this.fieldWgt.getFieldValue();
					// 约定：如果是字段为地址本的ID，则只能用等于
					if(val && val.indexOf("|fdId") > -1){
						// html += "<option value='!{equal}'>等于</option>";
						//#150803 查询条件涉及到地址本，需要增加运算符不等于
						for(var i = 0;i < item.length;i++){
							//移除包含，只添加不等于
							if(item[i].value == "!{contain}" || item[i].value == "!{notContain}"){
								continue;
							}
							html += "<option value='"+ item[i].value +"'>"+ item[i].name +"</option>";
						}
						// 当地址本类型为部门时，显示“是否包含子部门”
						var info = this.fieldWgt.getCurSelectInfo();
						var isDept = false;
						var orgType = info.orgType || "";
						if(orgType.indexOf("ORG_TYPE_DEPT") > -1){
							isDept = true;
						}
						if(isDept){
							$operator.hide();
							var tmpHtml = "<label><input type='checkbox' value='true' name='"+ this.randomName +"_isIncludeSub'/>"+modelingLang['listview.include.sub-department']+"</label>";
							$operator.after(tmpHtml);
						}
					}else{
						//如果是单选按钮，移除不包含
						var isRemoveFlag = false;
						/*if(listviewOption.baseInfo.fieldInfos){
							for (var i = 0; i < listviewOption.baseInfo.fieldInfos.length; i++) {
								if(this.fieldWgt.getFieldValue() == listviewOption.baseInfo.fieldInfos[i].field
									&& listviewOption.baseInfo.fieldInfos[i].businessType == "inputRadio"){
									for (var j = 0; j < item.length ; j++) {
										if(item[j].value == "!{notContain}"){
											isRemoveFlag = true;
											break;
										}
									}
									break;
								}
							}
						}*/
						for(var i = 0;i < item.length;i++){
							/*if(isRemoveFlag && item[i].value == "!{notContain}"){
								continue;
							}*/
							html += "<option value='"+ item[i].value +"'>"+ item[i].name +"</option>";
						}
					}
					$operator.html(html);
					// 更新值
					this.element.find("select[name='"+ this.randomName +"_value']").trigger($.Event("change"));
				}
			}
		},

		fixDraw : function(operator){
			var html = "";
			var fieldType = this.fieldWgt.getCurType();
			var val = this.fieldWgt.getFieldValue();
			// 约定：如果是字段为地址本的ID，则用地址本控件显示
			if(val && val.indexOf("|fdId") > -1){
				var info = this.fieldWgt.getCurSelectInfo();
				var orgType = info.orgType;
				var orgSelectType = "ORG_TYPE_ALL";
				if(orgType === "ORG_TYPE_PERSON"){
					orgSelectType = "ORG_TYPE_PERSON";
				} else if(orgType === "ORG_TYPE_ORG|ORG_TYPE_DEPT"){
					orgSelectType = "ORG_TYPE_DEPT";
				}
				html += "<div class='inputselectsgl' onclick='Dialog_Address(true, \"" + this.randomName + "_fieldInputValue\",\"" + this.randomName + "_fieldInputValue_name\", \";\", " + orgSelectType + ");'>" +
					"<input name='" + this.randomName + "_fieldInputValue' type='hidden'>" +
					"<div class='input'><input placeholder='"+modelingLang['modeling.page.choose']+"' name='" + this.randomName + "_fieldInputValue_name' type='text' readonly></div>" +
					"<div class='selectitem'></div>" +
					"</div>";
			}else{
				if(fieldType === "String"){
					html += "<input type='text' name='"+ this.randomName +"_fieldInputValue' class='inputsgl inputCover positionCover' style='width: 100%;' />";
				}else if(fieldType === "enum"){
					var info = this.fieldWgt.getCurSelectInfo();
					html += this.getEnumHtml(info.enumValues, this.randomName + "_fieldInputValue",operator,info.field);
				}else if(fieldType === "Date" || fieldType === "DateTime" || fieldType === "Time"){
					//时间控件
					var functionName = "triggleSelectdatetime(event,this,'"+fieldType+"','"+this.randomName + "_fieldInputValue');";
					var validateName = "__" + fieldType.toLowerCase();
					html += "<div class='inputselectsgl ' style='width:100%' onclick=\"" + functionName + "\">";
					html += "<div class='input'><input name='" + this.randomName + "_fieldInputValue' type='text' validate='" + validateName + "'></div>";
					html += "<div class='inputdatetime'></div>";
					html += "</div>";
				}else if(fieldType === "BigDecimal" || fieldType === "Double"){
					html += "<input type='text' name='"+ this.randomName +"_fieldInputValue' validate='number' class='inputsgl inputCover positionCover' style='width: 100%;' />";
				}
			}
			return html;
		},

		nullDraw : function(){
			return "";
		},

		formulaDraw : function(){
			var $formula = formulaBuilder.get(this.randomName +"_fieldInputValue", this.fieldWgt.getCurType());
			$formula.find("input").css("width","70%");
			$formula.find("input").addClass("inputCover positionCover");
			return $formula;
		},

		getEnumHtml : function(options, name, operator,field){
			var html = "";
			var type = "radio";
			var businessType = "inputRadio";
			for (var i = 0; i < listviewOption.baseInfo.fieldInfos.length; i++) {
				if(field == listviewOption.baseInfo.fieldInfos[i].field){
					businessType = listviewOption.baseInfo.fieldInfos[i].businessType;
					break;
				}
			}
			if(businessType != "inputRadio" && businessType != "select"){
				type = "checkbox";
			}else {
				if (operator == '!{contain}' || operator == '!{notContain}') {
					type = "checkbox";
				}
			}
			for(var i = 0; i < options.length;i++){
				if(businessType == "inputRadio" && options[i].fieldEnumValue == "!{notContain}"){
						continue;
				}
				html += "<label><input type='"+type+"' name='"+ name +"'";
				html += " value='" + options[i].fieldEnumValue + "'>"+ options[i].fieldEnumLabel +"</label>&nbsp;&nbsp;";
			}
			return html;
		},

		getKeyData : function(){
			var keyData = {};
			// 类型区分处理
			var type = this.element.find("[name='"+ this.randomName +"_fdWhereType']").val() || "0";
			keyData.whereType = type;
			// 0(自定义)|1(内置)
			if(type === "0"){
				keyData.field = this.fieldWgt.getFieldValue();
				keyData.fieldType = this.fieldWgt.getFieldType();

				keyData.fieldOperator = this.element.find("select[name='"+ this.randomName +"_operator'] option:selected").val() || "";
				keyData.isIncludeSub = this.element.find("[name='"+ this.randomName +"_isIncludeSub']").is(':checked') ? "true" : "false";
				keyData.fieldValue = this.element.find("select[name='"+ this.randomName +"_value'] option:selected").val() || "";

				var inputVal = "";
				var $inputValDom = this.element.find("[name='"+ this.randomName +"_fieldInputValue']");
				// 长度大于1，默认为单选
				if($inputValDom.length > 1){
					var inputValArr = [];
					for(var i = 0;i < $inputValDom.length;i++){
						var dom = $inputValDom[i];
						if($(dom).prop("checked")){
							//inputVal = $(dom).val();
							inputValArr.push($(dom).val());
						}
					}
					inputVal = inputValArr.join(";");
				}else{
					inputVal = $inputValDom.val() || "";
				}
				keyData.fieldInputValue = inputVal;
				// 公式定义器显示值
				var inputValueText = this.element.find("[name='"+ this.randomName +"_fieldInputValue_name']");
				if(inputValueText.length > 0){
					keyData.fieldInputValueText = inputValueText.val() || "";
				}
			}else if(type === "1"){
				keyData.predefined = this.element.find("select[name='"+ this.randomName +"_fdPredefinedWhereType'] option:selected").val() || "";
				// 下面两个属性，由于pc端改不动，这里也跟pc同步
				keyData.field = "fdId";
				keyData.fieldType = "String";
			}

			return keyData;
		}
	})

	module.exports = WhereGenerator;

})
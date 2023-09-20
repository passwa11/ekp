Com_RegisterFile("linkage.js");
Com_IncludeFile("data.js|jquery.js");

checkInitStringReplaceAll();
if(seajs) {
	seajs.use('sys/property/define/style/linkage.css');
}
/**
 * 下拉框联动事件类
 */
// 页面初始化
$(document).ready(function() {
	Com_Parameter.event["submit"].push(propertySubmitEven);
	initPropertyRequiredIcon();
	var len = _values.length;
	for (var i = 0; i < len; i++) {
		initControl(_values[i][2], _values[i][0], _values[i][1]);
	}
});

var PROPERTY_ATTR_NAME_KEY = "data-property-value";

function propertySubmitEven() {
	var els = $("["+PROPERTY_ATTR_NAME_KEY+"]");
	if(els.length > 0){
		$.each(els, function(index, checkboxEl) {
			var eqEl = $(checkboxEl);
			var value = eqEl.attr(PROPERTY_ATTR_NAME_KEY)
			eqEl.val(value);
		});
	}
	return true;
}

function initPropertyRequiredIcon() {
	if(Com_GetUrlParameter) {
		var method = Com_GetUrlParameter(location.href, "method");
		if(method.indexOf("edit") > -1) {
			this.method = "edit";
		}
	}
	var isEdit = this.method == 'add' || this.method == 'edit';
	if(!isEdit)
		return;
	var els = $(".lui-property-content-tr").find("input");
	var sels = $(".lui-property-content-tr").find("select");
	var tels = $(".lui-property-content-tr").find("textarea");
	var requireHtml = "<span style='color: red;'>*</span>";
	$.each(els, function(index, input) {
		setIcon(input);
	});

	$.each(sels, function(index, input) {
		setIcon(input);
	});

	$.each(tels, function(index, input) {
		setIcon(input);
	});
	function setIcon(input) {
		var eqEl = $(input);
		var validate = eqEl.attr("validate");
		if(validate && validate.indexOf("required") > -1) {
			var tdEl = eqEl.closest("td");
			if(tdEl.length > 0 && !tdEl.attr("data-inited-required")){
				var strongEl = tdEl.find(".txtstrong");
				if(strongEl.length > 0 && strongEl.html().indexOf("*") == -1){
					tdEl.attr("data-inited-required", true)
					tdEl.append(requireHtml);
				}
			}
		}
	}
}

function selectCheckBoxData(name){
	var targetName = "[name='"+name+"']";
	var els = $(targetName);
	var firstEl = "";
	var firstVal = "";
	var firstIndex = -1;
	$.each(els, function(index, checkboxEl) {
		var eqEl = $(checkboxEl);
		if(eqEl.is(':checked') == true){
			if(firstIndex == -1) {
				firstIndex = 0;
				firstEl = eqEl;
			}
			firstVal += ";" + eqEl.val();
		}
		eqEl.removeAttr(PROPERTY_ATTR_NAME_KEY)
	});
	if(firstEl && firstVal) {
		var value = firstVal.substring(1, firstVal.length);
		firstEl.attr(PROPERTY_ATTR_NAME_KEY, value);
	}
}

/**
 * [String].replaceAll()方法不存在时初始化
 */
function checkInitStringReplaceAll() {
	// 解决ie不支持replaceAll问题
	if(!String.prototype.hasOwnProperty("replaceAll")){
		/**
		 * 替换所有匹配的字符
		 * @param matchStr
		 * @param replaceStr
		 * @param isReplaceReg（是否转译正则符号）
		 * @return {*}
		 */
		String.prototype.replaceAll = function (matchStr,replaceStr, isReplaceReg){
			if(isReplaceReg){
				//转译匹配字符中的正则符号
				matchStr = matchStr.replace(/\[/gm,"\\[")
					.replace(/\]/gm,"\\]")
					.replace(/\{/gm,"\\{")
					.replace(/\}/gm,"\\}")
					.replace(/\(/gm,"\\(")
					.replace(/\)/gm,"\\)")
					.replace(/\^/gm,"\\^")
					.replace(/\$/gm,"\\$")
					.replace(/\|/gm,"\\|")
					.replace(/\?/gm,"\\?")
					.replace(/\*/gm,"\\*")
					.replace(/\+/gm,"\\+")
					.replace(/\./gm,"\\.")
			}
			return this.replace(new RegExp(matchStr,"gm"),replaceStr);
		}
	}
}



/**
 * 初始化绑定筛选项
 *
 * @param defineId
 * @param parentValue
 * @param defaultValue
 * @return
 */
function initControl(defineId, parentValue, defaultValue) {

	var values = findData(defineId, parentValue);
	buildItem(values, defineId, defaultValue, true);
}

/**
 * 事件绑定筛选项
 *
 * @param defineId
 * @return
 */
function eventListen(defineId, evt, required) {

	var src;

	if (typeof event != 'undefined') {
		src = event.srcElement;
	} else if (evt) {
		src = evt.target;
	}

	var values = findData(defineId, src.value, required);
	buildItem(values, defineId);
};

// ajax查找数据
function findData(defineId, pValue, required) {
	try{
		var data = new KMSSData();
		var beanData = "sysPropertyOptionListService&fdDefineId=" + encodeURIComponent(defineId)
			+ "&pValue=" + encodeURIComponent(pValue) + "&required=" + encodeURIComponent(required);
		data.AddBeanData(beanData);
		return data.GetHashMapArray();
	}catch (e){
		console.log("查找数据出错",e);
	}
	return [];
}

/**
 * 构建选项
 *
 * @param values
 * @param targetDefineId
 * @param defaultValue
 * @param isInt
 * @return
 */
function buildItem(values, targetDefineId, defaultValue, isInt) {
	var len = values.length - 1;
	if (len < 0)
		return;
	var type = values[len].displayType;
	var structureName = values[len].structureName;
	var required = values[len].required;
	switch (type) {
		case "select": {
			if (this.method != 'view') {
				var target = $(document.getElementsByName(structureName)[0]);
				// 判断不是第一次加载
				if (!isInt) {
					target.val("").trigger('change');
				}
				target.empty();
				target.append("<option value=''>==请选择==");
				for (var i = 0; i < len; i++) {
					target.append("<option value='" + values[i].value + "'>"
							+ values[i].text);
				}
				target.val(defaultValue);
			} else {
				for (var i = 0; i < len; i++) {
					if (values[i].value == defaultValue) {
						$("#" + targetDefineId).prepend(values[i].text);
						break;
					}
				}
			}
			break;
		}
		case "radio": {
			var target = $("#" + targetDefineId);
			var isDisabled = this.method == 'add' || this.method == 'edit' ? ""
					: "disabled";
			$("#" + targetDefineId + "> nobr").remove();

			var isEdit = this.method == 'add' || this.method == 'edit';

			var txt = "<nobr><label>"
					+ "<input onclick=\"__rdClick(this.name.substring(1), 'null', null);\"  type='radio' name='_{structureName}' value='{value}' {checked} {validate}/>{text}&nbsp;"
					+ "</label></nobr>";
			for (var i = len - 1; i > -1; i--) {

				var input = txt.replaceAll("{structureName}", structureName)
						.replace(/{value}/, values[i].value).replace(/{text}/,
								values[i].text);
				if (defaultValue == values[i].value) {
					input = input.replace(/{checked}/, 'checked');

				} else {
					input = input.replace(/{checked}/, isDisabled);
				}

				if (required && required !="false") {
					input = input.replace(/{validate}/, "validate='required'");
				} else {
					input = input.replace(/{validate}/, "");
				}

				target.prepend(input);

			}
			// 判断不是第一次加载
			if (!isInt) {
				var structureNameVal = structureName.replace(".", "\\.")
						.replace("(", "\\(").replace(")", "\\)");
				// [checked=true]
				$("input[name=" + structureNameVal + "]")
						.attr("checked", false).trigger('click');
			}
			try {
				if(isEdit) {
					var fun = eval("fun_" + targetDefineId);
					if (typeof (fun) == "function") {
						fun();
					}
					// 绑定  radio change 事件
					bindRadioChangeEvent();
				}
			} catch (e) {
			}
			break;
		}
		case "checkbox": {
			var target = $("#" + targetDefineId);
			var isDisabled = this.method == 'add' || this.method == 'edit' ? ""
					: "disabled";
			$("#" + targetDefineId + "> nobr").remove();

			var txt = "<nobr><label>"
					+ "<input type='checkbox' {attrNameKey}  name='{structureName}' value='{value}' "
					+ "onclick=\"__cbClick('{structureName}','null',false,null); selectCheckBoxData('{structureName}')\" "
					+ "{checked} {validate}/>{text}&nbsp;" + "</label></nobr>";

			var firstIndex = -1;

			for (var i = len - 1; i > -1; i--) {

				var input = txt.replaceAll("{structureName}", structureName)
						.replace(/{value}/, values[i].value).replace(/{text}/,
								values[i].text);

				if (defaultValue
						&& defaultValue.split(";").indexOf(values[i].value)>-1) {
					if(firstIndex == -1 && defaultValue.split(";").indexOf(values[i].value) == 0) {
						var valStr = PROPERTY_ATTR_NAME_KEY + "='"+defaultValue+"'";
						firstIndex = 0;
						input = input.replaceAll("{attrNameKey}", valStr);
					} else {
						input = input.replaceAll("{attrNameKey}", '');
					}
					input = input.replace(/{checked}/, 'checked');
				} else {
					input = input.replace(/{checked}/, isDisabled);
					input = input.replaceAll("{attrNameKey}", '');
				}

				if (required && required !="false") {
					input = input.replace(/{validate}/, "validate='required'");
				} else {
					input = input.replace(/{validate}/, "");
				}

				target.prepend(input);
			}
			break;
		}
		case "input": {
			break;
		}
	}
	initPropertyRequiredIcon();
};

/**
 * 格式化 _expandMap 数据
 * @returns
 */
function formatData() {
	var parentMap = {};
	for(var i=0; i<_expandMap.length; i++) {
		var item = _expandMap[i];
		parentMap[item.fdId] =  item;
	}
	return parentMap;
}

/**
 * 递归获取层级
 * @returns
 */
function getLevel(child, newMap, level, resultMap) {
	var item = newMap[child.parentId];
	if(item != undefined) {
		if(resultMap[child.parentId]) {
			level.index = resultMap[child.parentId].level + level.index;
			return
		}
		level.index = level.index+1;
		getLevel(item, newMap, level, resultMap);
	}
}

/** { fdId: item } **/
var radioIdDATA = undefined;

/** { level: item } **/
var radioLevelDATA = undefined;

/**
 * 根据 _expandMap 获取 对应层级 { fdId: item } 或者 { level: item }
 * @returns
 */
function setMap() {
	var newMap = formatData();
	var resultMap = {};
	var levelMap = {};
	for(var i=0; i<_expandMap.length; i++) {
		var child = _expandMap[i];
		var level = {index: 1};
		var fdId = child.fdId;
		getLevel(child, newMap, level, resultMap);
		child.level = level.index
		resultMap[fdId] = child;
		levelMap[level.index] = child;
		if(level.index == 1) {
			levelMap[0] =  { level: 0, fdId: child.parentId };
			resultMap[child.parentId] = { level: 0, fdId: child.parentId };
		}
	}
	radioIdDATA = resultMap;
	radioLevelDATA = levelMap;
}

/**
 * 绑定 radio change事件
 * @returns
 */
function bindRadioChangeEvent() {
	if(!radioLevelDATA && _expandMap) {
		setMap();
	}
	if(!radioIdDATA && _expandMap) {
		setMap();
	}
	for(fdId in radioIdDATA) {
		radioChangeEvent(fdId);
	}
}

/**
 * radio change事件
 * @returns
 */
function radioChangeEvent(fdId) {
	var elId = "#"+fdId;
	var labelEl = $(elId).find("label");
	var inpEL = labelEl.children("input");
	var i = -1;
	for(key in radioIdDATA) {
		i++;
	}
	var dataLength = i;
	for(var i=0; i<inpEL.length; i++) {
		var el = inpEL.eq(i);
		var changeEvents = $._data(el[0], "events").change;
		if(!changeEvents) {
			el.on("change", {fdId: fdId}, function(){
				var value = $(this).val();
				var level = radioIdDATA[fdId].level;
				var oldVal = radioIdDATA[fdId].value;
				if(oldVal && oldVal == value) {
					return ;
				}
				radioIdDATA[fdId].value = value;
				if(level <= dataLength - 2) {
					var childLevel = level + 2;
					for(index in radioLevelDATA) {
						if(index >= childLevel) {
							var childId = radioLevelDATA[index].fdId;
							var name = radioIdDATA[childId].fieldName;
							var elId = "#" +  childId;
							$(elId).children("nobr").remove();
							$("input[name='"+name+"']").val("");
						}
					}
				}
			});
		}
	}
}

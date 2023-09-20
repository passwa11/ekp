// 要求：外层需要套一个Dom对象，必须有id和_xform_type属性，样例：
// <div id="_xform_docSubject" _xform_type="text"><xform:text property="docSubject" /></div>
// field可以是字段名、DOM对象或是jQuery对象，注意：name="_docSubject"会被认为"docSubject"的显示字段
// 调用样例：
// $form('docSubject').val(); 取值
// $form('fdForm[*].fdName', 'fdForm[0].fdId').val(); 取值：fdForm[0].fdName
// $form('fdForm[*].fdName').val(''); 多字段赋值，不支持多重下标
// 方法见BaseField
define(["mui/i18n/i18n!sys-mobile:mui", "dojo/topic", "dojo/_base/lang", "dijit/registry","mui/i18n/i18n!:date"], 
function(msg, topic, lang, registry, msg1) {
	var util = {isArray:lang.isArray, trim:lang.trim};
	// 以下代码，到移动端接口实现部分之前，请与/resource/js/form.js保持一致
	var XFORMPREFIX = 'extendDataFormInfo.value(';
	var XFORMSUFFIX = ')';
	// ========== 接口声明 ==========
	window.$form = function(field, sample){
		if((field instanceof BaseField) || (field instanceof DetailField)){
			return field;
		}
		if(typeof field=='string'){
			var index = field.indexOf('*');
			if(index>-1){
				if(sample && field.substring(0, index)==sample.substring(0, index)){
					var match = sample.match(/[\[\.](\d+)[\]\.]/g);
					if(match){
						for(var i=0; i<match.length; i++){
							field = field.replace(/([\[\.])\*([\]\.])/, match[i]);
						}
						return new BaseField(field);
					}else{
						return new DetailField(field);
					}
				}else{
					return new DetailField(field);
				}
			}
		}
		return new BaseField(field);
	};
	// 数据格式化
	$form.str =  function(value, type,dimension){
		if(util.isArray(value)){
			var rtnVal = [];
			for(var i=0; i<value.length; i++){
				rtnVal.push(str(value[i], type, dimension));
			}
			return rtnVal;
		}
		return str(value, type,dimension);
	};
	$form.format = function(value, type,dimension){
		if(util.isArray(value)){
			var rtnVal = [];
			for(var i=0; i<value.length; i++){
				rtnVal.push(format(value[i], type, dimension));
			}
			return rtnVal;
		}
		return format(value, type,dimension);
	};
	// 绑定表单事件: bind(listener)/unbind(listener)
	// listener:{field:null/string/array, onValueChange:function(event)/null, onDisplayChange:function(event)/null, onReadOnlyChange:function(event)/null}
	// event:{type:'onValueChange/onDisplayChange/onReadOnlyChange', field:string, target:$form对象}
	$form.bind = function bind(listener){
		listeners.push(listener);
	};
	$form.unbind = function unbind(listener){
		for(var i=0; i<listeners.length; i++){
			if(listeners[i]===listener){
				listeners.splice(i, 1);
				i--;
			}
		}
	};
	// 移动端组件化，不需要扩展
	
	// ========== 基础类 ==========
	// 基础字段
	function BaseField(field){
		this.target = buildTarget(field);
		this._provider = getProvider(this.target);
		this.field = this.target.field;
		// 值（读/写）
		this.val = function(value){
			return this._provider.val(this.target, value==null || typeof value=='string' ? value : value + '');
		};
		// 显示（读/写）
		this.display = function(value){
			return this._provider.display(this.target, toBoolean(value));
		};
		// 只读（读/写）
		this.readOnly = function(value){
			return this._provider.readOnly(this.target, toBoolean(value));
		};
		// 必填（读/写）
		this.required = function(value){
			return this._provider.required(this.target, toBoolean(value));
		};
		// 权限级别（读/写）：0=隐藏，1=只读，2=非必填，3=必填
		this.editLevel = function(value){
			if(value!=null){
				value = parseInt(value, 10);
				if(isNaN(value)){
					return;
				}
			}
			return this._provider.editLevel(this.target, value);
		};
		// 字段个数
		this.size = function(){
			return this.target.type ? 1 : 0;
		};
		function toBoolean(value){
			if(value==null){
				return value;
			}
			if(value===true || value==='true'){
				return true;
			}
			return false;
		}
	}
	// 明细表字段
	function DetailField(field){
		this.target = {field:field, type:'detail'};
		this.field = field;
		this._fields = getFields(field);
		// 值（读/写）
		this.val = function(value){
			return this._exec('val', value);
		};
		// 显示（读/写）
		this.display = function(value){
			return this._exec('display', value);
		};
		// 只读（读/写）
		this.readOnly = function(value){
			return this._exec('readOnly', value);
		};
		// 必填（读/写）
		this.required = function(value){
			return this._exec('required', value);
		};
		// 权限级别（读/写）：0=隐藏，1=只读，2=非必填，3=必填
		this.editLevel = function(value){
			return this._exec('editLevel', value);
		};
		// 字段个数
		this.size = function(){
			return this._fields.length;
		};
		this._exec = function(funcName, value){
			if(value==null){
				// 读
				var rtnVal = [];
				for(var i=0; i<this._fields.length; i++){
					rtnVal.push(this._fields[i][funcName]());
				}
				return rtnVal;
			}
			// 写
			var isArray = util.isArray(value);
			for(var i=0; i<this._fields.length; i++){
				if(isArray){
					if(value.length<=i){
						break;
					}
					if(value[i]==null){
						continue;
					}
					this._fields[i][funcName](value[i]);
				}else{
					this._fields[i][funcName](value);
				}
			}
			return rtnVal;
		};
		function getFields(field){
			var index = field.indexOf('*');
			var left = field.substring(0, index);
			var right = field.substring(index+1);
			var rtnFields = [];
			for(var i=0; true; i++){
				var baseField = new BaseField(left+i+right);
				if(baseField.size()==0){
					break;
				}
				rtnFields.push(baseField);
			}
			return rtnFields;
		}
	}
	
	// ========== 数据格式 ==========
	// 日期格式
	function date2str(date, format) {
		if(!date){
			return;
		}
		var o = { 
			"M+" : date.getMonth()+1,
			"d+" : date.getDate(),
			"H+" : date.getHours(),
			"m+" : date.getMinutes(),
			"s+" : date.getSeconds()
		}; 
		if(/(y+)/.test(format)) {
			//要么2位，要么4位
			format = format.replace(RegExp.$1, (date.getFullYear()+"").substring(RegExp.$1.length==2 ? 2 : 0));
		}
		for(var k in o) {
			if(new RegExp("("+ k +")").test(format)){
				format = format.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substring((""+ o[k]).length)));
			}
		}
		return format; 
	}
	function str2date(str, format){
		if(!str){
			return;
		}
		str = util.trim(str);
		var reg = /([yMdHms]+)/g;
		var nan = /\D/;
		// yyyy-MM-dd, result=[{key:'yyyy',index:0},{key:'MM',split:'-'},{key:'dd',split:'-'}]
		var result = [];
		var pre, current;
		for(var i=0; i<6; i++){
			current = reg.exec(format, i);
			if(current==null){
				break;
			}
			if(pre){
				result.push({key:current[0], split:format.substring(pre.index+pre[0].length, current.index)});
			}else{
				result.push({key:current[0], index:0});
			}
			pre = current;
		}
		if(result.length==0){
			return null;
		}
		var date = new Date(0);
		pre = result[0];
		for(var i=1; i<result.length; i++){
			var current = result[i];
			// 根据分隔符找到字段的值
			current.index = str.indexOf(current.split, pre.index);
			if(current.index==-1){
				return null;
			}
			if(!setDateField(date, pre.key, str.substring(pre.index, current.index))){
				return null;
			}
			// 下个分隔符应该在当前分隔符后面
			current.index += current.split.length;
			pre = current;
		}
		if(!setDateField(date, pre.key, str.substring(pre.index))){
			return null;
		}
		return date;
		function setDateField(date, field, value){
			if(nan.test(value)){
				return false;
			}
			value = parseInt(value, 10);
			switch(field.charAt(0)){
			case 'y':
				if(field.length==2){
					var year = new Date().getFullYear();
					value = year - year % 100 + value % 100;
				}
				date.setFullYear(value);
				break;
			case 'M':
				if(value>12){
					return false;
				}
				date.setMonth(value-1);
				break;
			case 'd':
				if(value>31){
					return false;
				}
				date.setDate(value);
				break;
			case 'H':
				if(value>23){
					return false;
				}
				date.setHours(value);
				break;
			case 'm':
				if(value>59){
					return false;
				}
				date.setMinutes(value);
				break;
			case 's':
				if(value>59){
					return false;
				}
				date.setSeconds(value);
				break;
			}
			return true;
		}
	}
	// 转字符串
	function str(value, type,dimension){
		if(value==null){
			return '';
		}
		if(typeof value == 'string'){
			return value;
		}
		if(typeof value=='number'){
			if(isNaN(value)){
				return '';
			}
			var c = value.toString();
			if (/\.\d*999999/.test(c) || /\.\d*0000000/.test(c)) {
				c = Math.round(value*1000000.0)/1000000.0+'';
			}
			return c;
		}
		type = type==null ? 'string' : type.toLowerCase();
		if(type=='datetime' || type=='date' || type=='time'){
			return date2str(value, getDatePattern(type,dimension));
		}
		return value + '';
	}
	// 转基础类型
	function format(value, type,dimension){
		if(value==null){
			return value;
		}
		type = type==null ? 'string' : type.toLowerCase();
		switch(type){
		case 'string':
		case 'rtf':
			if(typeof value == 'string'){
				return value;
			}else{
				return value + '';
			}
		case 'integer':
		case 'long':
			if(value==''){
				return 0;
			}
			value = parseInt(value, 10);
			if(isNaN(value)){
				return null;
			}else{
				return value;
			}
		case 'double':
			if(value==''){
				return 0.0;
			}
			value = parseFloat(value, 10);
			if(isNaN(value)){
				return null;
			}else{
				return value;
			}
		case 'boolean':
			if(value==true || value=='true' || value=='on'){
				return true;
			}
			if(value==false || value=='false' || value=='off'){
				return false;
			}
			return null;
		case 'date':
		case 'time':
		case 'datetime':
			if(typeof value == 'string'){
				return str2date(value, getDatePattern(type,dimension));
			}
			if(value instanceof Date){
				return value;
			}
			return null;
		}
		return null;
	}
	// 日期格式
	function getDatePattern(type,dimension){
		if(dimension){
			return msg1['date.format.'+dimension];
		}
		return msg1['date.format.'+type];
	}
	
	// ========== 事件处理 ==========
	var events = [];
	var listeners = [];
	// 延迟触发事件
	var eventTimeout = -1;
	function trigger(event){
		if(event.target){
			event.field = event.target.field;
		}
		// 事件合并
		var found = false;
		for(var i=0; i<events.length; i++){
			var e = events[i];
			if(e.type===event.type && e.field===event.field){
				found = true;
				break;
			}
		}
		if(!found){
			events.push(event);
		}
		// 延迟触发
		if(eventTimeout>-1){
			clearTimeout(eventTimeout);
		}
		eventTimeout = setTimeout(_trigger, 100);
	}
	// 触发事件
	function _trigger(){
		var _events = events;
		events = [];
		eventTimeout = -1;
		for(var i=0; i<_events.length; i++){
			var e = _events[i];
			for(var j=0; j<listeners.length; j++){
				var l = listeners[j];
				if(l[e.type]==null){
					continue;
				}
				if(matchField(e.field, l.field, true)){
					e.listener = l;
					try{
						l[e.type](e);
					}catch(ex){
						if(window.console){
							console.error(ex);
						}
					}
				}
			}
		}
		function matchField(eField, lField, nullRtn){
			if(lField==null){
				return nullRtn;
			}
			if(typeof lField=='string'){
				if(eField==lField){
					return true;
				}
				if(lField.indexOf('*')>-1){
					eField = eField.replace(/([\.\[])\d+([\.\]])/g, '$1*$2');
					if(eField==lField){
						return true;
					}
				}
				if(eField.substring(0, XFORMPREFIX.length)==XFORMPREFIX){
					return eField == XFORMPREFIX+lField+XFORMSUFFIX;
				}
			}else if(util.isArray(lField)){
				for(var i=0; i<lField.length; i++){
					if(matchField(eField, lField[i], false)){
						return true;
					}
				}
			}
			return false;
		}
	}
	
	// ========== 接口管理 ==========
	function getProvider(target){
		return providerImpl;
	}
	function Provider(impl){
		for(var o in impl){
			this[o] = impl[o];
		}
		this.val = function(target, value){
			return execFunc(this, 'val', target, value);
		};
		this.display = function(target, value){
			if(target.cache.maxLevel==0){
				return false;
			}
			return execFunc(this, 'display', target, value, 'onDisplayChange');
		};
		this.readOnly = function(target, value){
			if(target.cache.maxLevel<2){
				if(value==null){
					return true;
				}
				return false;
			}
			return execFunc(this, 'readOnly', target, value, 'onReadOnlyChange');
		};
		this.required = function(target, value){
			if(target.cache.maxLevel<2){
				return false;
			}
			return execFunc(this, 'required', target, value);
		};
		this.editLevel = function(target, value){
			var level = 0;
			if(!this.display(target)){
				level = 0;
			}else if(this.readOnly(target)){
				level = 1;
			}else if(!this.required(target)){
				level = 2;
			}else{
				level = 3;
			}
			if(value==null){
				return level;
			}
			if(value==level){
				return false;
			}
			var maxLevel = target.cache.maxLevel==2 ? 3 : target.cache.maxLevel;
			value = value>maxLevel ? maxLevel : value;
			if(value==0){
				this.display(target, false);
				this.required(target, false);
			}else if(value==1){
				this.display(target, true);
				this.readOnly(target, true);
				this.required(target, false);
			}else if(value==2){
				this.display(target, true);
				this.readOnly(target, false);
				this.required(target, false);
			}else if(value>=3){
				this.display(target, true);
				this.readOnly(target, false);
				this.required(target, true);
			}
		};
		function execFunc(self, funcName, target, value, eventName){
			var func = impl[funcName];
			var _value = func.apply(self, [target]);
			if(value==null){
				return _value;
			}
			if(_value==value){
				return false;
			}
			var result = func.apply(self, [target, value]);
			if(eventName && result!==false){
				trigger({type:eventName, target:target, by:'set'});
			}
			return result;
		}
	}
	
	// ========== 移动端接口实现 ==========
	// 获取操作对象
	function buildTarget(field){
		if(typeof field=='string'){
			var target = _buildTarget(field);
			if(target.type){
				return target;
			}else{
				var xformTarget = _buildTarget(XFORMPREFIX+field+XFORMSUFFIX);
				if(xformTarget.type){
					return xformTarget;
				}else{
					return target;
				}
			}
		}else{
			return _buildTarget(field);
		}
	}
	function _buildTarget(field){
		var element, cache, root;
		element = document.getElementsByName(field);
		if(element.length==0){
			element = null;
		}
		if(!element){
			// 无编辑对象
			root = document.getElementById('_xform_'+field);
			if(root)
				element = root.querySelectorAll("*[data-dojo-type]");
			if(checkRoot(root, field)){
				cache = root._xForm_cache;
			}else{
				root = null;
			}
			return buildTargetObject();
		}
		cache = element[0]._xForm_cache;
		if(cache){
			// 有缓存
			if(cache.root){
				if(cache.detailTable){
					// 明细表，索引号会变，优先遍历
					root = findRoot(element);
				}else{
					root = document.getElementById(cache.root);
				}
			}
			return buildTargetObject();
		}
		// 无缓存
		root = findRoot(element);
		cache = root && root._xForm_cache;
		return buildTargetObject();
		
		// ------ 内部函数 ------
		function findRoot(object){
			for(object=object[0]; object && object.getAttribute; object=object.parentNode){
				if(object.getAttribute('_xform_type')){
					return object;
					break;
				}
			}
		}
		function checkRoot(root, field){
			if(!root){
				return false;
			}
			if(root._xForm_cache && !root._xForm_cache.detailTable || !window.DocList_TableInfo){
				return true;
			}
			var fieldIndex = (/[\[\.](\d+)[\]\.]/).exec(field);
			if(fieldIndex==null){
				return true;
			}
			var rowIndex;
			for(var node=root; node!=null && node.nodeType==1; node=node.parentNode){
				switch(node.tagName){
				case 'TR':
					rowIndex = node.rowIndex;
					break;
				case 'TABLE':
					if(node.id && DocList_TableInfo[node.id] && rowIndex!=null){
						return rowIndex-DocList_TableInfo[node.id].firstIndex == fieldIndex[1];
					}
				}
			}
			return true;
		}
		// 依赖上下文变量
		function buildTargetObject(){
			var widget;
			if(cache){
				widget = cache.widget ? registry.byId(cache.widget) : null;
				return {field:field, type:cache.type, widget:widget, root:root, cache:cache};
			}
			// cache:{widget,type,root,detailTable,maxLevel}
			cache = {};
			if(element){
				widget = registry.getEnclosingWidget(element[0]);
				if(widget){
					if(widget.showStatus){
						cache.widget = widget.id;
					}else{
						widget = null;
					}
				}
				element[0]._xForm_cache = cache;
			}
			// type、root
			if(root){
				cache.type = root.getAttribute('_xform_type');
				cache.root = root.id;
				root._xForm_cache = cache;
			}else if(widget){
				cache.type = widget.declaredClass;
			}
			cache.detailTable = (/[\[\.]\d+[\]\.]/).test(field);
			// maxLevel
			if(widget){
				switch(widget.showStatus){
				case 'edit':
					cache.maxLevel = 2;
					break;
				case 'view':
				case 'readOnly':
					cache.maxLevel = 1;
					break;
				default:
					cache.maxLevel = 0;
				}
			}else if(root){
				cache.maxLevel = 1;
			}else{
				cache.maxLevel = 0;
			}
			// console.log('build cache:'+field);
			return {field:field, type:cache.type, widget:widget, root:root, cache:cache};
		}
	}
	// {val, display, readOnly, required}
	var providerImpl = new Provider({
		val : function(target, value){
			var widget = target.widget;
			if(value==null){
				if(widget){
					if(target.field==widget.nameField){
						return target.widget.get('curNames');
					}else{
						return target.widget.get('value');
					}
				}
				return;
			}
			if(widget){
				if(target.field==widget.nameField){
					target.widget.set('curNames', value);
				}else{
					target.widget.set('value', value);
				}
				return true;
			}
			return false;
		},
		display : function(target, value){
			var element = target.root || (target.widget && target.widget.domNode);
			if(element){
				if(value==null){
					if(window.getComputedStyle){
				        return window.getComputedStyle(element)['display']!='none';
				    }else{
				        return element.currentStyle['display']!='none';
				    }
				}
				element.style.display = value ? '' : 'none';
			}else{
				return false;
			}
		},
		readOnly : function(target, value){
			var widget = target.widget;
			if(value==null){
				if(widget){
					return target.widget.get('readOnly');
				}
				return;
			}
			if(widget){
				target.widget.set('readOnly', value);
				return true;
			}
			return false;
		},
		required : function(target, value){
			var widget = target.widget;
			if(value==null){
				if(widget){
					return target.widget.get('required');
				}
				return;
			}
			if(widget){
				target.widget.set('required', value);
				return true;
			}
			return false;
		}
	});
	
	// 监听改变事件
	topic.subscribe("/mui/form/valueChanged", function(widget){
		if(widget && widget.showStatus){
			//window.__widget = widget;
			trigger({type:'onValueChange', field:widget.get('name'), by:'change'});
		}
	});
	
	return window.$form;
});

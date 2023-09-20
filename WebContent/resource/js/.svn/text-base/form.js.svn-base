// 要求：外层需要套一个Dom对象，必须有id和_xform_type属性，样例：
// <div id="_xform_docSubject" _xform_type="text"><xform:text property="docSubject" /></div>
// field可以是字段名、DOM对象或是jQuery对象，注意：name="_docSubject"会被认为"docSubject"的显示字段
// 调用样例：
// $form('docSubject').val(); 取值
// $form('fdForm[*].fdName', 'fdForm[0].fdId').val(); 取值：fdForm[0].fdName
// $form('fdForm[*].fdName').val(''); 多字段赋值，不支持多重下标
// 方法见BaseField
(function(){
	if(window['$form']){
		return;
	}
	var util = {isArray:$.isArray, trim:$.trim};
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
				rtnVal.push(str(value[i], type,dimension));
			}
			return rtnVal;
		}
		return str(value, type,dimension);
	};
	$form.format = function(value, type,dimension){
		if(util.isArray(value)){
			var rtnVal = [];
			for(var i=0; i<value.length; i++){
				rtnVal.push(format(value[i], type,dimension));
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
	// 注册扩展组件：regist(provider)/unregist(provider)，样例见defaultProvider实现
	$form.regist = regist;
	$form.unregist = unregist;
	
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
	var datePattern = window.seajs && seajs.data.env.pattern;
	function getDatePattern(type,dimension){
		if(datePattern){
			if(dimension){
				return datePattern[dimension];
			}
			return datePattern[type];
		}
		if(type=='date'){
			if(dimension=='yearMonth' && Com_Parameter.YearMonth_format){
				return Com_Parameter.YearMonth_format;
			}
			if(dimension=='year' && Com_Parameter.Year_format){
				return Com_Parameter.Year_format;
			}else if(Com_Parameter.Date_format){
				return Com_Parameter.Date_format;
			}
		}
		if(type=='datetime' && Com_Parameter.DateTime_format){
			return Com_Parameter.DateTime_format;
		}
		if(window.Data_GetResourceString){
			var result = Data_GetResourceString('date.format.datetime;date.format.date;date.format.time');
			datePattern = {datetime:result[0], date:result[1], time:result[2]};
		}else{
			datePattern = {datetime:'yyyy-MM-dd HH:mm', date:'yyyy-MM-dd', time:'HH:mm'};
		}
		return datePattern[type];
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
	var providers = [];
	// 注册接口
	function regist(provider){
		providers.push(new Provider(provider));
	}
	function unregist(provider){
		for(var i=0; i<providers.length; i++){
			if(providers[i].impl===provider){
				providers.splice(i, 1);
				i--;
			}
		}
	}
	function getProvider(target){
		for(var i=providers.length-1; i>=0; i--){
			if(providers[i].support(target)){
				return providers[i];
			}
		}
	}
	function Provider(impl){
		// 整合基类的属性方法
		for(var o in defaultProvider){
			this[o] = defaultProvider[o];
		}
		// 整合扩展类的属性方法
		if(impl != defaultProvider){
			for(var o in impl){
				this[o] = impl[o];
			}
			this.impl = impl;
		}
		// 功能实现
		this._support = impl._support;
		this.support = function(target){
			if(this._support){
				return this._support(target);
			}
			if(target.element){
				return this.impl.support.apply(this, [target]);
			}
			return false;
		};
		this.val = function(target, value){
			return execFunc(this, 'val', target, value, 'onValueChange');
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
			var func = impl[funcName] || defaultProvider[funcName];
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
	
	// ========== PC端接口实现 ==========
	// target:{field:'实际字段名', type:'字段类型', element:field对象, display:_field对象, root:跟对象, cache:{type,root:string,maxLevel}}
	// 所有对象都是jQuery对象，为了方便判断，请在设值前将length==0的置null
	// support接口，可完善target信息，target.cache将缓存到DOM对象中，可被下次使用，若需要支持element==null，则用_support替代support
	// target.cache.maxLevel将记录该字段的最大权限级别：0=隐藏，1=只读，2=可编辑
	// PC需要实现：buildTarget（构造上下文对象），defaultProvider（默认的Provider）
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
	function _buildTarget(_field){
		var field = _field, element, display, cache, root;
		// 读取：field、element、display
		if(typeof field=='string'){
			if(field.charAt(0)=='_'){
				field = field.substring(1);
			}
			display = getElement('_'+field);
			element = getElement(field);
		}else{
			element = $(field);
			if(element.length==0){
				return {};
			}
			field = element[0].name;
			if(field.charAt(0)=='_'){
				field = field.substring(1);
				display = element;
				element = getElement(field);
			}else{
				display = getElement('_'+field);
			}
		}
		var object = display || element;
		if(!object){
			// 无编辑对象
			root = document.getElementById('_xform_'+field);
			if(checkRoot(root, field)){
				cache = root._xForm_cache;
				root = $(root);
			}else{
				root = null;
			}
			return buildTargetObject();
		}
		cache = object.prop('_xForm_cache');
		if(cache){
			// 有缓存
			if(cache.root){
				if(cache.detailTable){
					// 明细表，索引号会变，优先遍历
					root = findRoot(object);
				}else{
					root = document.getElementById(cache.root);
					root = root ? $(root) : null;
				}
			}
			return buildTargetObject();
		}
		// 无缓存
		root = findRoot(object);
		cache = root && root.prop('_xForm_cache');
		return buildTargetObject();
		
		// ------ 内部函数 ------
		function getElement(field){
			var element = document.getElementsByName(field);
			return element.length ? $(element) : null;
		}
		function findRoot(object){
			if(object){
				for(object=object[0]; object && object.getAttribute; object=object.parentNode){
					if(object.getAttribute('_xform_type') || object.getAttribute('xform_type')){
						return $(object);
						break;
					}
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
			if(cache){
				return {field:field, type:cache.type, element:element, display:display, root:root, cache:cache};
			}
			// cache:{type,root,detailTable,maxLevel}
			cache = {};
			var object = display || element;
			// type、root、detailTable
			if(root){
				cache.type = root.attr('_xform_type') || root.attr('xform_type');
				cache.root = root.attr('id');
			}else if(object){
				cache.type = object[0].tagName=='INPUT' ? object[0].type : object[0].tagName.toLowerCase();
			}
			cache.detailTable = (/[\[\.]\d+[\]\.]/).test(field);
			// maxLevel
			if(!cache.type || cache.type=='hidden'){
				// 未知类型或隐藏字段
				cache.maxLevel = 0;
			}else{
				if(object){
					if(!object[0].type || object[0].type=='hidden'){
						cache.maxLevel = 1;
					}else if(object[0].disabled || object[0].readOnly == true){
						cache.maxLevel = 1;
					}else{
						cache.maxLevel = 2;
					}
				}else{
					// 无字段
					cache.maxLevel = 1;
				}
			}
			// 写入到相关对象
			if(element){
				element.prop('_xForm_cache', cache);
			}
			if(display){
				display.prop('_xForm_cache', cache);
			}
			if(root){
				root.prop('_xForm_cache', cache);
			}
			// console.log('build cache:'+field);
			return {field:field, type:cache.type, element:element, display:display, root:root, cache:cache};
		}
	}
	// ↓↓↓↓ 基类实现 ↓↓↓↓
	// {val, display, getRootElement, readOnly, required, getValidateElement, displayRequiredFlag, getRequiredFlagPreElement}
	var defaultProvider = {
		// 支持无element的target
		_support : function(target){
			return true;
		},
		val : function(target, value){
			if(value==null){
				if(target.element){
					return target.element.val();
				}
				return;
			}
			if(target.element){
				target.element.val(value);
			}
			if(target.display){
				target.display.val(value);
			}
			return (target.element || target.display) != null;
		},
		display : function(target, value){
			var element = target.root || this.getRootElement(target);
			if(element && element.length){
				if(value==null){
					if(window.getComputedStyle){
				        return window.getComputedStyle(element[0])['display']!='none';
				    }else{
				        return element[0].currentStyle['display']!='none';
				    }
				}
				if(value){
					element.show();
				}else{
					element.hide();
				}
			}else{
				return false;
			}
		},
		// 跟节点
		getRootElement : function(target){
			return target.display || target.element;
		},
		readOnly : function(target, value){
			var element = target.display || target.element;
			if(value==null){
				if(element){
					return element.prop('disabled') || element.prop('readOnly') == true;
				}
				return;
			}
			if(element){
				if(element.prop('readOnly')==null){
					// 无readOnly属性
					element.prop('disabled', value);
				}else{
					element.prop('readOnly', value);
				}
			}else{
				return false;
			}
		},
		required : function(target, value){
			var element = this.getValidateElement(target);
			if(element && element.length){
				var validate = element.attr('validate');
				if(value==null){
					return validate ? (/\brequired\b/.test(validate)) : false;
				}
				if(value){
					if(validate){
						element.attr('validate', validate + ' required');
					}else{
						element.attr('validate', 'required');
					}
				}else{
					if(!validate || validate=='required'){
						element.removeAttr('validate');
					}else{
						element.attr('validate', validate.replace(/(\s*required\s*)/, ''));
					}
					// 校验框架BUG
					if(window.Reminder){
						new Reminder(element).hide();
					}
				}
				this.displayRequiredFlag(target, value);
			}else{
				return false;
			}
		},
		// 校验对象
		getValidateElement : function(target){
			return target.display || target.element;
		},
		// 显示隐藏必填标记
		displayRequiredFlag : function(target, value){
			var element = this.getRequiredFlagPreElement(target);
			if(element && element.length){
				var dom = element.get(element.length - 1);
				var span = dom;
				var found = false;
				for(var i=0; i<5; i++){
					span = span.nextSibling;
					if(span){
						if(span.tagName=='SPAN' && util.trim(span.innerText)=='*'){
							found = true;
							break;
						}
					}else{
						break;
					}
				}
				if(found){
					span = $(span);
					if(value){
						span.css("display","inline-block");
					}else{
						span.hide();
					}
				}else if(value){
					$(dom).after('<span class="txtstrong">*</span>');
				}
			}
		},
		// 必填标签兄弟节点
		getRequiredFlagPreElement : function(target){
			return target.display || target.element;
		}
	};
	regist(defaultProvider);
	// ↑↑↑↑ 基类实现 ↑↑↑↑
	// ↓↓↓↓ select实现 ↓↓↓↓
	regist({
		support : function(target){
			return target.type == 'select';
		},
		readOnly : function(target, value){
			var element = target.display || target.element;
			if(value==null){
				if(element){
					return element.prop('disabled');
				}
				return;
			}
			if(element){
				element.prop('disabled', value);
				if(this.inArray(element,disabledSelect) == -1){
					disabledSelect.push(element);
				}
			}else{
				return false;
			}
		},
		inArray : function(element,arr){
			var index = -1;
			for(var i = 0;i < arr.length;i++){
				if(element[0] === arr[i][0]){
					index = i;
					break;
				}
			}
			return index;
		}
	});
	// ↑↑↑↑ select实现 ↑↑↑↑
	// ↓↓↓↓ radio实现 ↓↓↓↓
	regist({
		support : function(target){
			return target.type=='radio';
		},
		val : function(target, value){
			var fields = target.display || target.element;
			if(value==null){
				for(var i=0; i<fields.length; i++){
					if(fields[i].checked){
						return fields[i].value;
					}
				}
				return '';
			}
			var readOnly = fields.attr('_readOnly')=='true';
			for(var i=0; i<fields.length; i++){
				var field = fields[i];
				field.checked = field.value == value;
				// 只读禁用非选择项
				field.disabled = field.checked ? false : readOnly;
			}
			// 若有隐藏字段也赋值
			if(target.element.attr('type')=='hidden'){
				target.element.val(value);
			}
		},
		readOnly : function(target, value){
			var fields = target.display || target.element;
			if(!fields){
				if(value==null){
					return;
				}
				return false;
			}
			if(value==null){
				if(fields){
					return fields.attr('_readOnly')=='true';
				}
				return;
			}
			fields.attr('_readOnly', value?'true':'false');
			// 只读禁用非选择项
			for(var i=0; i<fields.length; i++){
				var field = fields[i];
				field.disabled = field.checked ? false : value;
			}
		},
		getRootElement : function(target){
			if(target.root){
				return target.root;
			}
			return this.getRequiredFlagPreElement(target);
		},
		getValidateElement : function(target){
			return target.element;
		},
		getRequiredFlagPreElement : function(target){
			if(target.labelElement){
				return target.labelElement;
			}
			var fields = target.display || target.element;
			if(fields){
				var labels = [];
				for(var i=0; i<fields.length; i++){
					var dom = fields.get(i);
					if(dom.parentNode && dom.parentNode.tagName=='LABEL'){
						labels.push(dom.parentNode);
					}else{
						labels.push(dom);
					}
				}
				target.labelElement = $(labels);
				return target.labelElement;
			}
		}
	});
	// ↑↑↑↑ radio实现 ↑↑↑↑
	// ↓↓↓↓ checkbox实现 ↓↓↓↓
	regist({
		support : function(target){
			return target.type=='checkbox';
		},
		val : function(target, value){
			var fields = target.display || target.element;
			if(!fields){
				if(value==null){
					return;
				}
				return false;
			}
			if(value==null){
				var array = [];
				for(var i=0; i<fields.length; i++){
					if(fields[i].checked){
						array.push(fields[i].value);
					}
				}
				return array.join(';');
			}
			var array = value.split(';');
			for(var i=0; i<fields.length; i++){
				fields[i].checked = $.inArray(fields[i].value, array)>-1;
			}
			$(fields[0]).triggerHandler('click');
		},
		readOnly : function(target, value){
			var element = target.display || target.element;
			if(value==null){
				if(element){
					return element.prop('disabled');
				}
				return;
			}
			if(element){
				element.prop('disabled', value);
			}else{
				return false;
			}
		},
		// 同radio
		getRootElement : function(target){
			if(target.root){
				return target.root;
			}
			return this.getRequiredFlagPreElement(target);
		},
		// 同radio
		getRequiredFlagPreElement : function(target){
			if(target.labelElement){
				return target.labelElement;
			}
			var fields = target.display || target.element;
			if(fields){
				var labels = [];
				for(var i=0; i<fields.length; i++){
					var dom = fields.get(i);
					if(dom.parentNode && dom.parentNode.tagName=='LABEL'){
						labels.push(dom.parentNode);
					}else{
						labels.push(dom);
					}
				}
				target.labelElement = $(labels);
				return target.labelElement;
			}
		}
	});
	// ↑↑↑↑ checkbox实现 ↑↑↑↑
	// ↓↓↓↓ dialog/address/datetime实现 ↓↓↓↓
	// {getRootElement, readOnly, required, getValidateElement, displayRequiredFlag, getRequiredFlagPreElement}
	regist({
		support : function(target){
			if(target.type=='dialog' || target.type=='address' || target.type=='datetime'){
				var cache = target.cache;
				if(cache.dialog == null){
					// 缓存中没有，构建缓存
					var dialog = this.getDialog(target);
					if(dialog){
						// 向下找显示的输入框
						var fields = dialog.find('input,textarea');
						var display;
						for(var i=0; i<fields.length; i++){
							var field = fields[i];
							field._xForm_cache = cache;
							if(field.type!='hidden'){
								target.display = $(field);
								display = field.name.replace(/([\[\.])\d+([\]\.])/g,'$1*$2');
								if(field.getAttribute('_readOnly')==null){
									field.setAttribute('_readOnly', field.readOnly?'true':'false');
								}
							}
						}
						var onclick = dialog.attr('onclick');
						if(onclick){
							dialog.attr('_onclick', onclick);
							cache.maxLevel = 2;
						}else{
							cache.maxLevel = dialog.attr('_onclick') ? 2 : 1;
						}
						cache.dialog = {support:true, display:display};
					}else{
						cache.dialog = {support:false};
					}
					// console.log('build dialog cache:'+target.field);
				}else{
					// 从缓存恢复
					if(cache.dialog.display){
						var display = cache.dialog.display;
						if(display.indexOf('*')>-1){
							var match = target.field.match(/[\[\.](\d+)[\]\.]/g);
							if(match){
								for(var i=0; i<match.length; i++){
									display = display.replace(/([\[\.])\*([\]\.])/, match[i]);
								}
							}
						}
						if(target.field==display){
							target.display = target.element;
						}else{
							target.display = $(document.getElementsByName(display));
						}
					}
				}
				return cache.dialog.support;
			}
			return false;
		},
		getDialog : function (target){
			if(target.dialog){
				return target.dialog;
			}
			var parent = target.element[0];
			for(var i=0; i<5; i++){
				parent = parent.parentNode;
				if(parent){
					if(this.isManifest(target)) {
						var jq = $(parent).find('.orgelement');
						if(jq.length > 0){
							target.dialog = jq;
							return jq;
						}
					}else {
						var jq = $(parent);
						if(hasClass(jq, 'inputselectsgl', 'inputselectsglreadonly', 'inputselectmul', 'inputselectmulreadonly')){
							target.dialog = jq;
							return jq;
						}
					}
				}else{
					break;
				}
			}
		},
		val : function(target, value){
			if(value==null){
				if(target.element){
					return target.element.val();
				}
				return;
			}
			if(target.element){
				target.element.val(value);
				this.manifestVal(target,value);
				return true;
			}
			return false;
		},
		readOnly : function(target, value){
			var dialog = this.getDialog(target);
			if(value==null){
				return dialog[0].onclick == null;
			}
			if(this.isManifest(target)) {
				this.manifestReadOnly(target,value);
				return;
			}
				
			if(value){
				var onclick = dialog.attr('onclick');
				if(onclick){
					dialog.attr('_onclick', onclick);
				}
				dialog.removeAttr('onclick');
				if(hasClass(dialog, 'inputselectsgl')){
					dialog.removeClass('inputselectsgl');
					dialog.addClass('inputselectsglreadonly');
				}else{
					dialog.removeClass('inputselectmul');
					dialog.addClass('inputselectmulreadonly');
				}
				if(target.display){
					target.display.css({width:'100%'});
					target.display.prop('readOnly', true);
				}
			}else{
				dialog.attr("onclick", dialog.attr("_onclick"));
				if(hasClass(dialog, 'inputselectsglreadonly')){
					dialog.removeClass('inputselectsglreadonly');
					dialog.addClass('inputselectsgl');
				}else{
					dialog.removeClass('inputselectmulreadonly');
					dialog.addClass('inputselectmul');
				}
				if(target.display){
					target.display.prop('readOnly', target.display.attr('_readOnly')=='true');
				}
			}
		},
		required : function(target, value){
			var element = this.getValidateElement(target);
			if(element && element.length){
				var validate = element.attr('validate');
				if(value==null){
					return validate ? (/\brequired\b/.test(validate)) : false;
				}
				if(value){
					if(validate){
						element.attr('validate', validate + ' required');
						if(this.isManifest(target))
							this.getManifestTarget(target).attr('validate', validate + ' required');
					}else{
						element.attr('validate', 'required');
						if(this.isManifest(target))
							this.getManifestTarget(target).attr('validate', 'required');
					}
				}else{
					if(!validate || validate=='required'){
						element.removeAttr('validate');
						if(this.isManifest(target))
							this.getManifestTarget(target).removeAttr('validate');
					}else{
						element.attr('validate', validate.replace(/(\s*required\s*)/, ''));
						if(this.isManifest(target))
							this.getManifestTarget(target).attr('validate', validate.replace(/(\s*required\s*)/, ''));
					}
					// 校验框架BUG
					if(window.Reminder){
						new Reminder(element).hide();
					}
				}
				this.displayRequiredFlag(target, value);
			}else{
				return false;
			}
		},
		// 校验对象
		getValidateElement : function(target){
			if(this.isManifest(target)) {
				if(Address_isNewAddress(target.element[0])) {
					return target.element;
				}
				var name = target.element.attr('xform-name');
				var address = Address_GetAddressObj(name);
				if(typeof address == 'undefined' ){
					return;
				}
				return $(address.nameField);
			}
			return target.display || target.element;
		},
		getRequiredFlagPreElement : function(target){
			if(this.isManifest(target)) {
				return this.getDialog(target).parent();
			}
			return this.getDialog(target);
		},
		//是否使用了新地址本
		isManifest: function(target) {
			return target.type == 'address' && target.element.attr('xform-name') != null;
		},
		//获得地址本便捷选择的输入框对象
		getManifestTarget: function(target) {
			if(target.manifestTarget) {
				return target.manifestTarget;
			}
			var name = target.element.attr('xform-name');
			var address = Address_GetAddressObj(name);
			if(typeof address == 'undefined' ){
				return;
			}
			target.manifestTarget = address.$inputField;
			return address.$inputField;
		},
		//地址本便捷选择只读设置
		manifestReadOnly: function(target,value) {
			var dialog = this.getDialog(target);
			var manifestTarget = this.getManifestTarget(target); //输入框
			if(typeof manifestTarget == 'undefined' ){
				return;
			}
			//只读
			if(value){
				var onclick = dialog.attr('onclick');
				if(onclick){
					dialog.attr('_onclick', onclick);
				}
				dialog.removeAttr('onclick');
				var $list = manifestTarget.manifest('list');
				$list.children('li.mf_highlighted').removeClass('mf_highlighted');
				$list.undelegate('li','mouseover').undelegate('li','mousedown');
				manifestTarget.hide(); //隐藏输入框
			}else{ //非只读
				dialog.attr("onclick", dialog.attr("_onclick"));
				var $list = manifestTarget.manifest('list');
				$list.children('li.mf_highlighted').removeClass('mf_highlighted');
				$list.delegate('li', 'mouseover', function () {
		           var $item = $(this);
		           manifestTarget.manifest('cuxAddHighlight',$item);
		        });
				var multi = manifestTarget.data('ismulti') == 'true';
				if(!multi && $list.children('li').length > 0) { //单选并且有选项的时候还是隐藏输入框
					manifestTarget.hide();
				}else {
					manifestTarget.show();
				}
			}
		},
		//必须先给ID赋值，再给Name赋值，给Name赋值时才会调用新地址本的新增值函数
		manifestVal: function(target,value) {
			if(Address_isNewAddress(target.element[0])) {
				var fieldName = target.element.attr('xform-name');
				var address = Address_GetAddressObj(fieldName);
				var idValues = address.idField.value;
				var nameValues = address.nameField.value;
				address.emptyAddress(false);  //清空值
				address.idField.value = idValues;
				address.nameField.value = nameValues;
				if(value != "") {
					var splitStr = address.splitStr;
					if(idValues) {
						idValues = idValues.split(splitStr);
						var nameValues = value.split(splitStr);
						var valueArray = [];
						for (var i = 0; i < idValues.length; i++) {
							valueArray.push({id:idValues[i],name:nameValues[i]});
						}
						address.addData(valueArray);
					}
				}
			}
		}
	});
	function hasClass(element){
		for(var i=1; i<arguments.length; i++){
			if(element.is('.'+arguments[i])){
				return true;
			}
		}
		return false;
	}
	// ↑↑↑↑ dialog/address/datetime实现 ↑↑↑↑
	// ↓↓↓↓ attachment实现  暂只做了附件必填这块↓↓↓↓
	regist({
		_support : function(target){
			if(target.type == 'attachment' || target.type == 'docimg'){
				var cache = target.cache;
				// 设置附件对象，处理状态
				if(cache.attachment == null){
					var attachment = this.getAttachment(target);
					if(attachment){
						cache.maxLevel = 2;
						cache.attachment = attachment;	
					}
				}
				return true;
			}
			return false;
		},
		getAttachment : function(target){
			try{
				//#171722
				Attachment_ObjectInfo;
			}catch (e) {
				Attachment_ObjectInfo = (Com_Parameter.top || window.top).window.Attachment_ObjectInfo;
			}
			if(target.element && target.element.length > 0){
				// 明细表内
				var uid = target.element[0].value;
				if(Attachment_ObjectInfo && Attachment_ObjectInfo[uid]){
					return Attachment_ObjectInfo[uid];
				}
			}else{
				//明细表外
				var field = target.field;
				if(/\((\w+)\)/g.test(field)){
					//自定义表单只取括号内的
					var attr = /\((\w+)\)/g.exec(field);
					if(attr && attr.length > 1){
						field = attr[1];
					}
				}
				if(Attachment_ObjectInfo && Attachment_ObjectInfo[field]){
					return Attachment_ObjectInfo[field];
				}
			}
			return null;
		},
		required : function(target, value){
			var attachment;
			if(target.cache.attachment){
				attachment = target.cache.attachment;
			}else{
				attachment = this.getAttachment(target);
			}
			if(value == null){
				return attachment?attachment.required:"";
			}
			return attachment.requiredExec(value);
		}
		
	});
	// ↑↑↑↑ attachment实现 ↑↑↑↑
	// ↓↓↓↓ map实现  暂未支持只读编辑↓↓↓↓
	regist({
		_support : function(target){
			return target.type=='map';
		},
		// 必填标签兄弟节点
		getRequiredFlagPreElement : function(target){
			return target.root.find("[data-location-container]");
		}
	});
	// ↑↑↑↑ map实现 ↑↑↑↑
	
	// ↓↓↓↓ rtf实现  暂未支持只读编辑↓↓↓↓
	regist({
		_support : function(target){
			return target.type=='rtf';
		},
		// 必填标签兄弟节点
		getRequiredFlagPreElement : function(target){
			return target.element.next();
		},
		val : function(target, value){
			if(value==null){
				if(target.element){
					return target.element.val();
				}
				return;
			}
			if(target.element){
				target.element.val(value);
				//rtf设值需要调用RTF_SetContent
				RTF_SetContent && RTF_SetContent(target.field,value);
				this.manifestVal(target,value);
				return true;
			}
			return false;
		}
	});
	// ↑↑↑↑ rtf实现 ↑↑↑↑
	
	// 监听document事件
	var formData = {};
	function fireFieldChange(e, clearData, by){
		var target = e.target;
		if(target && target.name && (target.tagName=='INPUT' || target.tagName=='SELECT' || target.tagName=='TEXTAREA')){
			var field = target.name;
			var oldValue = formData[field];
			var $target = $form(field);
			var newValue = $target.val();
			if(clearData){
				delete formData[field];
			}else{
				formData[field] = newValue;
			}
			if(oldValue!=null && oldValue!=newValue){
				trigger({type:'onValueChange', target:$target, field:target.name, by: by});
			}
		}
	}
	$(document).bind({
		focusin : function(e){
			var target = e.target;
			if(target && target.name && (target.tagName=='INPUT' || target.tagName=='SELECT' || target.tagName=='TEXTAREA')){
				formData[target.name] = $form(target.name).val();
			}
		},
		focusout : function(e){
			fireFieldChange(e, true, 'blur');
		},
		click : function(e){
			fireFieldChange(e, false, 'click');
		},
		change : function(e){
			var target = e.target;
			if(target && target.name && (target.tagName=='INPUT' || target.tagName=='SELECT' || target.tagName=='TEXTAREA')){
				trigger({type:'onValueChange', target:$form(target.name), field:target.name, by:'change'});
			}
		}
	});
	
	var confirmFun = [];
	
	confirmFun.push(removeDisbledPro);
	
	var disabledSelect = [];
	$form.disabledSelect = disabledSelect;
	// 提交时，移除select的disabled属性，不然select的值会丢失
	function removeDisbledPro(){
		for(var i = 0;i < disabledSelect.length;i++){
			disabledSelect[i].prop('disabled', false);
		}
		return true;
	}
	
	// 提交保存时执行
	function submitConfirm(){
		for(var i = 0;i < confirmFun.length;i++){
			if(!confirmFun[i]()){
				return false;
			}
		}
		return true;
	}
	
	Com_Parameter.event["confirm"].push(submitConfirm);
})();

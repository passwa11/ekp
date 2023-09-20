Com_RegisterFile("address.js");

var ADDRESS_PREFIX = "mf_";
function KMSSAddress() {
	if(window.$ === undefined || window.$.fn.manifest === undefined) {
		return false;
	}
	this.idField = null;
	this.nameField = null;
	this.inputField = null;   // 输入框，dom对象
	this.$inputField = null;  // 输入框，jquery对象
	this.selectType = null ;  // 组织架构类型
	this.splitStr = ";";
	this.isMulti = false;
	this.callback = null; 
		
	this.initAddress = AddressFunc_initAddress;  //初始化
	this.changeToOld = AddressFunc_changeToOld;  //变回老版本
	this.emptyAddress = AddressFunc_emptyAddress;  //清空地址本
	this.addData = AddressFunc_addData;  // 新增值
	this.reset = AddressFunc_reset;   //重置
}


/**
 * 初始化新地址本
 * 
 */
function AddressFunc_initAddress() {
	var self = this;
	var HAVE_NO_RESULT = Data_GetResourceString('return.noRecord'),_validate,_oElement;
	//校验器
	if(window.$KMSSValidation) {
		var _form = self.$inputField.parents('form')[0];
		_validate = $.isFunction(window.$KMSSValidation)?$KMSSValidation(_form):window.$KMSSValidation,
		_oElement = new Elements();
	}
	self.$inputField.manifest({
		separator: self.splitStr,
		values: self.initValues,
		formatDisplay: function (data, $item, $mpItem) {
			var temp = document.createElement("div");
			(temp.textContent != null) ? (temp.textContent = data.name) : (temp.innerText = data.name);
			var output = temp.innerHTML;
			temp = null;
			return output.replace(/amp;/g,"");
		},
		formatValue: function (data, $value, $item, $mpItem) {
			return data.id;
		},
		/**
		 * 调用newAddressAdd函数会触发这个方法，newAddressAdd函数会在两个地方调用；
		 * 一个是地址本dialog选择时候的回调函数中调用，这时候已经给id和name的dom元素赋值，并且会调用onValueChange中的函数；
		 * 另一个是使用$form()去新增值的时候，这时候也给id和name的dom元素赋值了，但是不会调用onValueChange中的函数。
		 * 所以在这里不给dom元素赋值，并通过triggered参数（newAddressAdd函数中传入）控制onValueChange函数的执行与否
		 */
		onAdd: function(data,$item,init,triggered) {
			var fields = [{key:'id',domEle:self.idField},
			                  {key:'name',domEle:self.nameField}];  
			var rtn = [],objs = [];
			for (var i = 0; i < fields.length; i++) {
				var field = fields[i].domEle;
				objs[i] = field;
				rtn[i] = field.value;  
			}
			if(self.callback && triggered) {
				self.callback(rtn, objs);
			}
			//校验
			if(_validate && _oElement.valiateElement(self.inputField)) {
				_validate.validateElement(self.inputField);
			}
		},
		/**
		 * 这个函数会在两个地方触发，一个是调用emptyNewAddress函数，另一个是点击“X”(见jquery.manifest.js #520传入参数)，
		 * 通过triggered参数判断是否执行onValueChange中的函数
		 */
		onRemove: function(data,$item,triggered) {
			var fields = [{key:'id',domEle:self.idField},{key:'name',domEle:self.nameField}];  
			var rtn = [],objs = [];
			for (var i = 0; i < fields.length; i++) {
				var field = fields[i].domEle; 
				var arr = field.value.split(self.splitStr);
				for(var j=0; j<arr.length; j++) {      
					if(arr[j] == data[fields[i].key]) {        
						arr.splice(j, 1);        
						break;      
					}
				}
				rtn[i] = field.value = arr.join(self.splitStr);
				if(triggered){
					$(field).trigger("change");
				}
				objs[i] = field;
			}
			if(self.callback && triggered) {
				self.callback(rtn, objs);
			}
			//校验
			if(_validate && _oElement.valiateElement(self.inputField)) {
				_validate.validateElement(self.inputField);
			}
		},
		marcoPolo: {
			url:Com_Parameter.ContextPath+'sys/common/dataxml.jsp?s_bean=organizationDialogSearch&orgType='+self.selectType+'&deptLimit='+self.deptLimit+'&isExternal='+((self.isExternal == null || self.isExternal == undefined) ? '' : self.isExternal),
			formatData: function (data) {
				var datas = new Array();
				var oriValues = self.$inputField.manifest('values');
				$.each($(data).find('data'),function(index,item) {    
					var attrs = item.attributes;    
					var obj = new Object();   
					$.each(attrs,function(index,item) {     
						obj[item.nodeName] = item.nodeValue;    
					});
					//去重
					if($.inArray(obj.id,oriValues) === -1)
						datas.push(obj);  
				});
				return datas;
			},
			formatItem: function (data, $item) {
				var parentName = '';
				if(data.parentName != null && data.parentName !== 'null' && data.parentName !== '') {
					parentName = ' <span>&lt;'+data.parentName+'&gt;</span>';
				}
				return data.name+ ' '+parentName;
			},
			/**
			 * 只会在下拉列表点击的时候触发，肯定会执行onValueChange的函数
			 */
			onSelect: function(data, $item) {
				var url = Com_Parameter.ContextPath+'sys/organization/sys_organization_recent_contact/sysOrganizationRecentContact.do?method=addContacts';
				$.post(url,{contactIds:data.id});
				var fields = [{key:'id',domEle:self.idField},
				                  {key:'name',domEle:self.nameField}];  
				var rtn = [],objs = [];
				for (var i = 0; i < fields.length; i++) {
					var field = fields[i].domEle;
					objs[i] = field;
					rtn[i] = field.value = field.value ? field.value+self.splitStr+data[fields[i].key]:data[fields[i].key];  
				}
				if(self.callback) {
					self.callback(rtn, objs);
				}
				$(self.idField).trigger("change");
				//校验
				if(_validate && _oElement.valiateElement(self.inputField)) {
					_validate.validateElement(self.inputField);
				}
			},
			formatNoResults: function (q) {
		        return '<small>'+HAVE_NO_RESULT+'</small>';
		    },
			formatMinChars:false,
			formatError: false,
			minChars: 1,
			dataType: 'xml',
			param: 'key'
		},
		required: true,
		multi:self.isMulti
	});
}


/** 
 * 新地址本清空值(未初始化则初始化) triggered:控制清空值之后会不会调用回调函数(默认调用)
 *  这个清空的值是已经绑定在输入框上的值（通过调用newAddressAdd或初始化时绑定）
 *  例如：id隐藏域的值是123;456，name输入框的值是aaa;bbb
 *  	而绑定的值是{id:'123',name:'aaa'}
 *  	那么调用该方法后：id隐藏域的值是456，name输入框的值是bbb
 */
function AddressFunc_emptyAddress(triggered) {
	var self = this;
	if(triggered == null) {
		triggered = true;
	}
	try {
		self.$inputField.manifest('remove','li',triggered);
	}catch(err) {
		Address_QuickSelectionByField(self.inputField,self.initValues,self.callback,self.fieldIndex);
	}
}

/** 
 * 新地址本新增值 triggered:控制新增值之后会不会调用回调函数(默认调用)
 * 注意：这个函数不会给id隐藏域和name输入框赋值
 */
function AddressFunc_addData(values,triggered) {
	var self = this;
	if(triggered == null) {
		triggered = true;
	}
	try{
		self.$inputField.manifest('add',values,null,true,false,triggered);
	}catch(err) {
		Address_QuickSelectionByField(self.inputField,values,self.callback,self.fieldIndex);
	}
	
	//校验器
	if(window.$KMSSValidation) {
		var _form = self.$inputField.parents('form')[0] || document.forms[0];
		if(_form != null) {
			var _validate = $.isFunction(window.$KMSSValidation)?$KMSSValidation(_form):window.$KMSSValidation,
				_oElement = new Elements();
			//校验
			if(_oElement.valiateElement(self.inputField)) {
				_validate.validateElement(self.inputField);
			}
		}
	}
}

/**
 * 重置新地址本
 * 
 */
function AddressFunc_reset(splitStr,orgType,isMulti,values,callback,fieldIndex) {
	var self = this;
	
	try {
		self.$inputField.manifest('remove','li',false);
		self.$inputField.manifest('destroy');
	}catch(err) {
	}
	// 由于新地址初始化的时候并不会给id和name的dom元素赋值，这里会出现校验为空的情况，所以这里给其先赋值
	if(values) {
		var ids = [],names = [];
		if($.isArray(values)) {
			for(var i = 0;i < values.length;i++) {
				ids.push(values[i].id);
				names.push(values[i].name);
			}
		} else {
			ids.push(values.id);
			names.push(values.name);
		}
		$(self.idField).val(ids.join(splitStr));
		$(self.nameField).val(names.join(splitStr));
	}
	var propertyId = self.idField.name, propertyName = self.nameField.name;
	Address_QuickSelection(propertyId,propertyName,splitStr,orgType,isMulti,values,callback,fieldIndex);
}

/**
 * 判断ie和ie版本
 * @returns
 */
function Address_IEVersion() {
    var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串  
    var isIE = userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1; //判断是否IE<11浏览器  
    var isEdge = userAgent.indexOf("Edge") > -1 && !isIE; //判断是否IE的Edge浏览器  
    var isIE11 = userAgent.indexOf('Trident') > -1 && userAgent.indexOf("rv:11.0") > -1;
    if(isIE) {
        var reIE = new RegExp("MSIE (\\d+\\.\\d+);");
        reIE.test(userAgent);
        var fIEVersion = parseFloat(RegExp["$1"]);
        if(fIEVersion == 7) {
            return 7;
        } else if(fIEVersion == 8) {
            return 8;
        } else if(fIEVersion == 9) {
            return 9;
        } else if(fIEVersion == 10) {
            return 10;
        } else {
            return 6;//IE版本<=7
        }   
    } else if(isEdge) {
        return 'edge';//edge
    } else if(isIE11) {
        return 11; //IE11  
    }else{
        return -1;//不是ie浏览器
    }
}

/**
 * 切换回老版本 <br>
 * 发生在新地址本初始化之前，所以需要做的就是将新地址本的dom结构改为老的结构
 * @returns
 */
function AddressFunc_changeToOld() {
	var $container = $(this.nameField).parents('.inputselectsgl:first').length > 0
				?$(this.nameField).parents('.inputselectsgl:first')
				:$(this.nameField).parents('.inputselectmul:first');
	this.idField.removeAttribute('xform-name');
	this.nameField.removeAttribute('xform-name');
	this.nameField.removeAttribute('xform-type');
	this.nameField.readonly = true;
	$(this.nameField).show();
	this.nameField.parentNode.removeAttribute('style');
	$(this.inputField).remove();
	var $dialog = $container.find('.orgelement');
	var _onclick = $dialog[0].getAttribute('onclick');
	$container[0].setAttribute('onclick',_onclick);
	$container[0].setAttribute('__id',$dialog[0].getAttribute('__id'));
	$container[0].setAttribute('__name',$dialog[0].getAttribute('__name'));
	$dialog[0].removeAttribute('onclick');
}
/**
 * 判断name属性元素是不是使用新地址本
 * @param ele
 * @returns
 */
function Address_isNewAddress(ele) {
	return ele.getAttribute('xform-type') === 'newAddressHidden';
}
/**
 * 回调
 * @param fn
 * @param rtn
 * @param objs
 * @returns
 */
function _NewXformAddressCallback(fn,rtn, objs) {
	if(fn && $.isFunction(fn)) {
		fn(rtn, objs);
	}
}

/**
 * 外部调用
 * 根据属性字段初始化新地址本
 * 
 */
function Address_QuickSelectionByField(inputField,values,callback,fieldIndex) {
	var $input = $(inputField);
	var propertyId = $input.data('propertyid');
	var propertyName = $input.data('propertyname');
	var splitStr = $input.data('splitchar');
	var orgType = $input.data('orgtype');
	var isMulti = eval($input.data('ismulti'));
	//明细表是复制的DOM结构，组件manifest并未初始化，这里需要删除相应DOM，再重新生成
	var $container = $input.parent();
	if($container.hasClass('mf_container')) {
		var $containerParent = $container.parent();
		$containerParent.next().remove(); //下拉数据列表marcopolo
		$container.remove();//组件manifest
		$containerParent.append($input);
	}
	Address_QuickSelection(propertyId,inputField,splitStr,orgType,isMulti,values,callback,fieldIndex);
}


/**
 * 提供给外部调用，初始化新地址本控件
 * @param propertyId
 * @param propertyName name属性名称，或者是新地址本输入框对象
 * @param splitStr
 * @param orgType
 * @param isMulti
 * @param values
 * @param callback
 * @param fieldIndex
 * @returns
 */
function Address_QuickSelection(propertyId,propertyName,splitStr,orgType,isMulti,values,callback,fieldIndex,deptLimit,isExternal) {
	var address = new KMSSAddress();
	if(address === false) {
		return false;
	}
	if(splitStr != null)
		address.splitStr = splitStr;
	address.isMulti = isMulti;
	address.callback = callback;
	address.deptLimit = deptLimit;
	address.isExternal = isExternal;
	//组织架构类型
	if((typeof orgType) == 'string') {
		orgType = eval(orgType);
	}
	//
	var selectType = orgType & ORG_TYPE_ALLORG;
	if(orgType == ORG_TYPE_GROUP 
			|| orgType == ORG_TYPE_ROLE 
			|| orgType == (ORG_TYPE_GROUP | ORG_TYPE_ROLE) ){
		selectType = orgType;
	}
	if(orgType & ORG_FLAG_AVAILABLEYES)
		selectType = selectType | ORG_FLAG_AVAILABLEYES;
	if(orgType & ORG_FLAG_AVAILABLENO)
		selectType = selectType | ORG_FLAG_AVAILABLENO;
	if(orgType & ORG_FLAG_BUSINESSYES)
		selectType = selectType | ORG_FLAG_BUSINESSYES;
	if(orgType & ORG_FLAG_BUSINESSNO)
		selectType = selectType | ORG_FLAG_BUSINESSNO;
	address.selectType = selectType;
	//元素下标
	if(fieldIndex == null) {
		fieldIndex = 0;
	}
	//初始值
	if(values == null) {
		values = [];
	}
	address.initValues = values;
	address.fieldIndex = fieldIndex;
	address.idField = document.getElementsByName(propertyId)[fieldIndex];  // ID隐藏框
	if(typeof(propertyName)=="string") {
		address.nameField = document.getElementsByName(propertyName)[fieldIndex];
		address.inputField = $("[xform-name='mf_"+propertyName+"']")[fieldIndex];
		address.$inputField = $(address.inputField);
	}else {
		address.inputField = propertyName;
		address.$inputField = $(propertyName);
		propertyName = address.$inputField.data("propertyname");
		address.nameField = document.getElementsByName(propertyName)[fieldIndex];
	}
	
	//如果是IE 并且文档模式是IE8以下（不包括IE8），或者是IE8并且是明细表的地址本，则切换回老版本
	var ieVersion = Address_IEVersion();
	var isDetail = /\.\d\./.test(propertyName) || /\[\d\]/.test(propertyName) || /!\{index\}/.test(propertyName); //是否是明细表
	if(ieVersion === 6 || ieVersion === 7 || (ieVersion === 8)) {
		address.changeToOld();
		return false;
	}
	address.$inputField.data('address',address);
	address.initAddress();
}

/**
 * 外部调用
 * 初始化明细表新增的行的地址本
 * 
 */
function Address_QuickSelectionDetail(htmlCode) {
	if(window.$) {
		try {
			var ads = $(htmlCode).find('[xform-type="newAddress"]');
			$.each(ads,function(index,item) {
				var $item = $(item);
				var xformName = $item.attr('xform-name');
				var _field = $("[xform-name='"+xformName+"']")[0];
				var idField = document.getElementsByName($item.data('propertyid'))[0];
				var nameField = document.getElementsByName($item.data('propertyname'))[0];
				var addressValues = new Array();
				if(idField && idField.value) {
					var ids = idField.value.split(';'),names = nameField.value.split(';');
					for (var j = 0; j < ids.length; j++) {
						addressValues.push({id:ids[j],name:names[j]});
					}
				}
				var callback = $(nameField).attr("callback");
				if (callback && callback != "null"){
					var fn = new Function("return " + callback)();
					var f = function(rtn,objs) {
						fn.apply(this,[rtn,objs]);
					}
				}else{
					var f = function(rtn,objs) {};
				}
				Address_QuickSelectionByField(_field,addressValues,f);
			});
		}catch(err) {
			kmss_console_log(err);
		}
	}
}
/**
 * 获得地址本对象
 * @param name
 * @param index
 * @returns
 */
function Address_GetAddressObj(name,index) {
	if(name.substring(0,3) !== ADDRESS_PREFIX) {
		name = window.ADDRESS_PREFIX + name;
	}
	if(index == null)
		index = 0;
	var field = $("[xform-name='"+name+"']")[index];
	var address = $(field).data('address');
	return address;
}

function kmss_console_log(msg) {
	if(window.console) {
		window.console.log(msg);
	}
}

function Address_GetIdByInputField(inputField){
	if (!inputField) {
		return "";
	}
	var inputField = $(inputField).attr("__id") + "']";
	if (inputField) {
		inputField = inputField.replace("extendDataFormInfo.value(","")
			.replace(".id)","").replace(".name)","");
	} else {
		inputField = "";
	}
	
	return inputField;
}

/**
 * 内数据的数据信息
 */
function Insystem_Context(){
	
	this.dictData = [];
	
	// 权限标志
	this.auth = {};
	
	// 业务过滤的集合
	this.filterDictData = [];
	
	// 字符串的集合
	this.strDictData = [];
	
	this.idProperty = null;
	
	this.nameProperty = null;
	
	this.modelName = null;
	
	// 内部
	this._init = _Insystem__Init;
	
	// 共用
	this.initialize = Insystem_Initialize;
	this.clear = Insystem_Clear;
	this.hasDictData = Insystem_HasDictData;
	
	this._init();
}

function _Insystem__Init(){
	
}

function Insystem_Initialize(dictData){
	if(dictData){
		for(var i = 0;i < dictData.length;i++){
			var pro = new Insystem_Property();
			var data = dictData[i];
			pro.initialize(data);
			if(data.isIdProperty && data.isIdProperty == 'true'){
				this.idProperty = pro;
			}
			if(data.isNameProperty && data.isNameProperty == 'true' && this.idProperty != pro){
				this.nameProperty = pro;
			}
			if(!Insystem_FilterPro(pro)){
				this.filterDictData.push(pro);
			}
			if(data.fieldType.toLowerCase() == "string"){
				this.strDictData.push(pro);
			}
			this.dictData.push(pro);
		}
	}
	return this;
}

function Insystem_Clear(){
	this.dictData = [];
	// isAuth
	this.auth = {};
	this.filterDictData = [];
	this.strDictData = [];
	this.modelName = null;
	this.idProperty = null;
	this.nameProperty = null;
}

// 是否含有数据
function Insystem_HasDictData(){
	var flag = true;
	if(this.dictData.length == 0){
		flag = false;
	}
	return flag;
}

function Insystem_Property_IsModel(type){
	var flag = false;
	if(type.indexOf("com.landray.kmss") > -1){
		flag = true;
	}
	return flag;
}

// 业务过滤
function Insystem_FilterPro(pro){
	var flag = false;
	/**************列表对象属性过滤******************/
	if(pro.isListProperty){
		flag = true;
	}
	return flag;
}

function Insystem_Property(){
	this.field = '';
	this.fieldType = '';
	this.dictType = '';
	this.message = '';
	this.isModel = false;
	this.enumType = '';
	this.enumValues = [];
	this.isListProperty = false;
	this.parent = null;
	
	this.initialize = Insystem_Property_Initialize;
}

function Insystem_Property_Initialize(data){
	this.field = data.field;
	this.fieldType = data.fieldType;
	this.dictType = data.dictType;
	this.message = data.fieldText;
	this.isModel = Insystem_Property_IsModel(this.fieldType);
	if(data.enumType != null){
		this.enumType = data.enumType;
		this.enumValues = data.enumValues;
	}
	if(data.isListProperty == 'true'){
		this.isListProperty = true;
	}
}
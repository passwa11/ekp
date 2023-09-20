/*规则映射*/
(function(){
	if(window.sysRuleTemplate4Node){
		return;
	}
	window.sysRuleTemplate4Node = new createsysRuleTemplate4Node();
	function createsysRuleTemplate4Node(){
		/*属性定义*/
		this.id = null;
		this.ids = [];//机制唯一id，可能多个
		this.modelNames = [];//当前model，可能多个
		this.fdKeys = [];//当前文档key,可能有多个
		this.keyToId = {};//key和id的对象，可以通过key获取对于的id
		this.allParams = new Object();//对应规则集的所有参数
		this.allMaps = [];//所有的映射
		this.quoteInfos = {};//所有引用
		this.unids = Data_GetRadomId(200);//唯一id集
		/*函数定义*/
		this.getMapObjById = getMapObjById;//根据id获取映射数据
		this.getUnid = getUnid;//获取唯一id
		this.getFields = getFields;//获取字段集
		this.getFieldById = getFieldById;//根据字段id从字段数组获取对应的字段
		this.addObjectData = addObjectData;
		this.objClone = objClone;
		this.init = init;
		this.init();
	}
	
	function init(){
		var unid = this.getUnid();
		this.ids.push(unid);
		this.id = unid;
		//this.keyToId[sysRuleTemplate.fdKeys[i]] = sysRuleTemplateId;
	}
	
	//为了解决edge浏览器子窗口中创建的对象无法正常保存到父窗口而特别加的克隆对象到节点属性的方法
	function addObjectData(attr,obj){
		if (typeof obj == "object" && obj != null) {
			this[attr] = this.objClone(obj);
		} else {
			this[attr] = obj;
		}
	}
	
	function objClone(obj) {
		// edge浏览器下优先通过length属性来判断obj是否为数组
		var o;
		if(obj.length || obj.length == 0){
			o = [];
		}else{
			o = {};
		}
		//var o = obj.length ? [] : {};
		for (var k in obj) {
			 o[k] = typeof obj[k] == "object" ? objClone(obj[k]) : obj[k];
		}
		return o;
	}
	
	function getFieldById(fieldId,fields){
		for(var i=0; i<fields.length; i++){
			var field = fields[i];
			if(fieldId && field.name == fieldId){
				return field;
			}
		}
		return {};
	}
	
	function getFields(){
		var fields = [];
		if(FlowChartObject || FlowChartObject.FormFieldList){
			fields = FlowChartObject.FormFieldList;
		}
		return fields;
	}
	
	function getMapObjById(id){
		return {};
	}
	
	function getUnid(){
		if(this.unids.length <= 1){
			//重新加载
			this.unids = Data_GetRadomId(200);
		}
		var unid = this.unids[0];
		this.unids.splice(0,1);
		return unid;
	}
})();
define([ "dojo/_base/declare"], function(declare) {
	var Validator = declare("mui.form.validate.Validator", null, {
		//校验类型
		type:'',
		
		//验证错误提示信息
		error:'',
		
		owner:this,
	
		//test参数信息
		options:{},
		
		//test options参数中key
		params:[],
		
		//设置检验函数,默认返回true
		testFun: function(value, element) { 
			return true; 
		},
		
		constructor:function(type, error, testFun, argus){
			this.inherited(arguments);
			if(type)
				this.type = type;
			if(error)
				this.error = error;
			if(testFun)
				this.testFun = testFun;
			if(argus)
				this.params = argus;
		},
		
		//校验主函数, element可以是dom或组件
		test:function(value , element) {
			return this.testFun.call(this.owner, value, element, this.options);
		},
		
		setOption:function(param , value){
			this.options[param] = value;
		},
		

		setOwner:function(ownerArgu){
			this.owner = ownerArgu || this;
		},
		
		setParams:function(params){
			this.params = params;
		},
		
		setParamValues:function(values){
			for (var i = 0, length = this.params.length; i < length; i++) {
				if (i == values.length) break;
				this.setOption(this.params[i], values[i]);
			}
		}
		
	});
	return Validator;
});
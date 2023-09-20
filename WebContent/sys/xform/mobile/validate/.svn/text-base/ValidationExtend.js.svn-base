define( ["dijit/registry", "mui/i18n/i18n!sys-xform-base:Designer_Lang","dojo/query"], function(registry, Msg, query) {
	var ValidationExtend = {};
	
	//常用检验类型配置
	var XFormExtendValidates = {
		'xform_calculatioFuns' : {
			error : "{name}"+Msg['Designer_Lang.controlCalculation_func_timeWarning'],
			test  : function(value, elem) {
				var w = registry.byNode(elem.valueNode);
				return w.validateFun();
			}
		},
		'xform_addressMaxPersonNum(num)':{
			error : "{name} "+Msg['Designer_Lang.controlAttrMaxPersonNumValidate'],
			test  : function(v, e, o) {
				var num = isNaN(o['num']) ? 0 : parseInt(o['num']);
				if (num == 0 || !v) return true;
				o['maxNum'] = num; 
				if(v.split(";").length > num){
					return false;
				}
				return true;
			}
		}
	};
	
	ValidationExtend.XFormExtendValidates = XFormExtendValidates;
	return ValidationExtend;
});
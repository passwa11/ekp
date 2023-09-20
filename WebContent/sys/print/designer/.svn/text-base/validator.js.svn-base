//===============  属性校验器 =====================
var sysPrintValidator={};


sysPrintValidator.attrRequiredValidator = function(elem, name, attr, value, values) {
	if (value == null || value == '' || value.replace(/\r\n/g, '') == '') {
		alert(DesignerPrint_Lang.GetMessage(DesignerPrint_Lang.controlAttrValRequired,attr.text));
		elem.focus();
		return false;
	}
	return true;
}
sysPrintValidator.attrIntValidator = function(elem, name, attr, value, values) {
	if (value != null && value != '' && !(/^([+]?)(\d+)$/.test(value.toString()))) {
		alert(DesignerPrint_Lang.GetMessage(DesignerPrint_Lang.controlAttrValInt,attr.text));
		return false;
	}
	return true;
}
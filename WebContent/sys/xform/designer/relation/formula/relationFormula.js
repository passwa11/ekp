﻿Com_IncludeFile("dialog.js");

/******************************************
功能：弹出公式选择对话框
参数：
	idField：id字段，可不传
	nameField：name字段，可不传
	varInfo：变量列表，格式为[{name:name, label:label, type:type},…]，可通过Formula_GetVarInfoByModelName获取数据字典中的变量
	returnType：返回值类型，为数据字典中的类型，可用String[]表示字符串类表，用Object标识不限制返回值类型
	action：回调函数
	funcs：自定义函数包，多值用;分隔
	model：给定域模型名称，用于函数等方面的过滤
******************************************/
function Relation_Formula_Dialog(idField, nameField, varInfo, returnType, action, funcs, model){
	var dialog = new KMSSDialog();
	var funcBean = "sysFormulaFuncTree";
	if(funcs!=null)
		funcBean += "&funcs=" + funcs;
	if(model!=null)
		funcBean += "&model=" + model;
	var funcInfo = new KMSSData().AddBeanData(funcBean).GetHashMapArray();
	dialog.formulaParameter = {
			varInfo: varInfo, 
			funcInfo: funcInfo, 
			returnType: returnType || "Object",
			funcs:funcs==null?"":funcs,
			model:model==null?"":model};
	dialog.BindingField(idField, nameField);
	dialog.SetAfterShow(action);
	dialog.URL = Com_Parameter.ContextPath + "sys/xform/designer/relation/formula/relationFormula_dialog.jsp";
	dialog.Show(window.screen.width*550/1366,window.screen.height*400/768);
}
function Relation_Formula_Eval_Dialog(idField, nameField, varInfo, returnType, action, funcs, model){
	var dialog = new KMSSDialog();
	var funcBean = "sysFormulaFuncTree";
	if(funcs!=null)
		funcBean += "&funcs=" + funcs;
	if(model!=null)
		funcBean += "&model=" + model;
	var funcInfo = new KMSSData().AddBeanData(funcBean).GetHashMapArray();
	dialog.formulaParameter = {
			varInfo: varInfo, 
			funcInfo: funcInfo, 
			returnType: returnType || "Object",
			funcs:funcs==null?"":funcs,
			model:model==null?"":model};
	dialog.BindingField(idField, nameField);
	dialog.SetAfterShow(action);
	dialog.URL = Com_Parameter.ContextPath + "sys/xform/designer/relation/formula/relationFormula_eval_dialog.jsp";
	dialog.Show(window.screen.width*750/1366,window.screen.height*500/768);
}
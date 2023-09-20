<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<script>

function MultiLang_OnLabelSwitch_${JsParam.fdKey}(tableName, index){
	var trUU = $("#" + tableName).find("tr[LKS_LabelId='tr_uu_lang']");
	if(trUU.length > 0){
		// 获取多语言页签的索引
		var lks_labelindex = $(trUU[0]).attr('lks_labelindex');
		// 点击多语言页签的时候才加载
		if(lks_labelindex != index){
			return true;
		}
	}
	//富文本框等模式，未引入表单，会导致报错
	if(!window.frames['IFrame_FormTemplate_${JsParam.fdKey}']){
		return true;
	}
	//先判断点击的页签是否是多语言页签
	var customIframe = window.frames['IFrame_FormTemplate_${JsParam.fdKey}'].contentWindow;
	if(!customIframe){
		customIframe = window.frames['IFrame_FormTemplate_${JsParam.fdKey}'];
	}
	//但表单没有加载，先让它加载，不然无法初始化
	if (!(customIframe.Designer && customIframe.Designer.instance && customIframe.Designer.instance.hasInitialized)) {
		// 不能在add的时候加载，不然新建的table宽度有问题（百分比不起效）
		if("${JsParam.method}" != 'add'){
			// 加载表单内容
			//LoadXForm('TD_FormTemplate_${JsParam.fdKey}');
			XForm_Loading_Show();
			Doc_LoadFrame('TD_FormTemplate_${JsParam.fdKey}', '<c:url value="/sys/xform/designer/designPanel.jsp?fdKey=" />${JsParam.fdKey}&fdModelName=${sysFormTemplateForm.modelClass.name}&method=view&sysFormTemplateFormPrefix=sysFormTemplateForms.${JsParam.fdKey}.');
			var frame = document.getElementById('IFrame_FormTemplate_${JsParam.fdKey}');
			Com_AddEventListener(frame, 'load', XForm_Loading_Hide);
		}
	}
	return true;
}


function loadLang(){
	try{
		//Doc_SetCurrentLabel("Label_Tabel",2);
		//Doc_SetCurrentLabel("Label_Tabel",1);
		/* var table = document.getElementById("TD_FormTemplate_${JsParam.fdKey}").parentNode;
		while((table!=null) && (table.tagName!="TABLE")){
			table = table.parentNode;
		} */
		var table = document.getElementById("Label_Tabel");
		if(table!=null){
			//页签切换调用的函数
			Doc_AddLabelSwitchEvent(table, "MultiLang_OnLabelSwitch_${JsParam.fdKey}");
		} 
		if(_getUParam("uukey")!=undefined){
			var documentTitle = window.parent.document.getElementById("documentTitle");
			if(documentTitle){
				var name = document.getElementsByName('docSubject')[0]||document.getElementsByName('fdName')[0];
				if(name){
					documentTitle.value = name.value;
				}
			}
			setTimeout(function(){
				$("#languageUpdate").click();
			},1000)
		}
	}catch(e){
		//setTimeout(loadLang,100);
	}
	
}

// 把即时执行改成页面加载结束后执行
$(function(){
	loadLang();
});
</script>	

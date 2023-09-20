<%@page import="com.landray.kmss.sys.property.service.ISysPropertyDefineService"%>
<%@page import="com.landray.kmss.sys.property.model.SysPropertyDefine"%>
<%@page import="com.landray.kmss.sys.property.forms.SysPropertyReferenceForm"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.landray.kmss.sys.authorization.constant.ISysAuthConstant"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<kmss:windowTitle
	subject="${sysPropertyTemplateForm.fdName}"
	subjectKey="sys-property:table.sysPropertyTemplate"
	moduleKey="sys-property:module.sys.property" />
<script>
Com_IncludeFile("jquery.js");
</script>
<script>
function confirmDelete(msg){
	var del = confirm('<bean:message key="page.comfirmDelete"/>');
	return del;
}
Com_IncludeFile("dialog.js|doclist.js");
function sysPropGetSelectedIds(tb, ref) {
	var ids = [];
	var table = document.getElementById(tb);
	var inputs = table.getElementsByTagName('input');
	for (var i = 0; i < inputs.length; i++){
		// 兼容浏览器
		if (inputs[i].getAttribute('ref') == ref) {
			ids.push(inputs[i].value);
		}
	}
	return ids;
}

var _addPropertyDialog;
function sysPropAddRow(tb, ref, beanData, title, action){
	_addPropertyDialog = new KMSSDialog();
	var lang = {
		optList: '<bean:message key="dialog.optList"/>',
		selList: '<bean:message key="dialog.selList"/>',
		add: '<bean:message key="dialog.add"/>',
		del: '<bean:message key="dialog.delete"/>',
		addAll: '<bean:message key="dialog.addAll"/>',
		delAll: '<bean:message key="dialog.deleteAll"/>',
		moveUp: '<bean:message key="dialog.moveUp"/>',
		moveDown: '<bean:message key="dialog.moveDown"/>',
		ok: '<bean:message key="button.ok"/>',
		cancel: '<bean:message key="button.cancel"/>'
	};
	if(title){
		lang.title = title;
	}
	_addPropertyDialog.Lang = lang;
	_addPropertyDialog.Window = window;
	_addPropertyDialog.SetAfterShow(action);
	_addPropertyDialog.URL = "dialog.html";
	_addPropertyDialog.exceptValue = sysPropGetSelectedIds(tb, ref);
	_addPropertyDialog.categoryId = $("input[name=categoryId]").val();
	_addPropertyDialog.beanData = beanData;
	_addPropertyDialog.Show(550, 480);
}

function findParentRef(e){
	var tr = e.parentNode.parentNode;
	addParentReference(tr.rowIndex-1,tr.id);
}
function sysPropDefineAddRowAfter(dat) {
	if(dat) {
		var data = dat;
		var defineNum = $("#TABLE_DocList > tbody tr").length -1;
		var defineIds = "";
		//添加前先判断是否需要删除
		var delTrObj = []; 
		if(defineNum>0){
			for(var i=0;i<defineNum;i++){
				var defineId = $("input[name='fdReferenceForms["+i+"].fdDefineId']").val();
				var isExit = false;
				for (var j = 0; j < data.length; j ++) {
					var fdId = data[j].fdId;
					if(defineId == fdId){
						isExit = true;
						break;
					}
				}
				if(!isExit){//不存在，则删除
					//获取tr
					var trObj = $("#TABLE_DocList > tbody").find("tr").eq(i+1)[0];
					delTrObj.push(trObj);
				}
			}
		}
		for(var i=0; i<delTrObj.length; i++){
			//删除
			sysPropDefineDeleteRow(delTrObj[i], true);
		}
		defineNum = $("#TABLE_DocList > tbody tr").length -1;
		//新增
		if(defineNum>0){
			for(var i=0;i<defineNum;i++){
				defineIds += $("input[name='fdReferenceForms["+i+"].fdDefineId']").val()+";";
			}
		}
		for (var i = 0; i < data.length; i ++) {
			if(defineIds.indexOf(data[i].fdId)>-1){
				continue;
			}
			var fieldValues = new Object();
			fieldValues["fdReferenceForms[!{index}].fdDefineId"] = data[i].fdId;
			fieldValues["fdReferenceForms[!{index}].fdDefineName"] = data[i].fdName;
			fieldValues["fdReferenceForms[!{index}].fdDisplayName"] = data[i].fdName;
			fieldValues["fdReferenceForms[!{index}].fdType"] = data[i].fdDisplayType;//旧数据是这样存的，虽错但沿袭使用会较稳定
			if(data[i].parent &&  data[i].parentName){
				fieldValues["fdReferenceForms[!{index}].fdParentId"] = data[i].parentId;
				fieldValues["fdReferenceForms[!{index}].fdParentName"] = data[i].parentName;
			}
			
			// 是否为单选多选类型
			var isRadioOrCheckbox = ('radio' == data[i].fdDisplayType || 'checkbox' == data[i].fdDisplayType) ;
			
			var row = DocList_AddRow("TABLE_DocList", null, fieldValues);
			
			var showAllNode = $(row).find('.luiPropertyIsShowAll [name^="_fdReferenceForms"]');
			var showAllNodeHide = $(row).find('.luiPropertyIsShowAll [name^="fdReferenceForms"]');
			if(!isRadioOrCheckbox) {
				$(row).find('.luiPropertyIsShowAll').hide();
			}else{
				showAllNode.attr("checked",true);
				showAllNodeHide.val(true);
			}
			
			$(row).attr("title",data[i].fdName);
			
			$(row).attr("KMSS_IsContentRow","1");
			$(row).attr("id",data[i].fdId);
			
			$("input[name='fdDisplayModel']").each(function(){
				if((this.value=="one"|| this.value=="other" )&& this.checked){
					$(".displayInLine").css("display","none");
				}
			});
			findFilterSettingByDefineId(data[i].fdId) ;//自动加上筛选项目
			
			//增加JSP片段
			JspBuild.add(data[i].fdId,data[i].fdName);
		}
	}
}
function sysPropDefineAddRow() {
//	sysPropAddRow("TABLE_DocList", "fdDefineId", "sysPropertyDefineListService", '<bean:message bundle="sys-property" key="table.sysPropertyReference" />', sysPropDefineAddRowAfter);
	var privateCategory = $("input[name='categoryId']").val();
 	var url = "";
	if(privateCategory){
		url = "/sys/property/sys_property_template/sysPropertyTemplateChoiceDefine.jsp?mulSelect=true&categoryId="+privateCategory;
	}else{
		url = "/sys/property/sys_property_template/sysPropertyTemplateChoiceDefine.jsp?mulSelect=true";
	}
	var defineNum = $("#TABLE_DocList > tbody tr").length -1;
	//if(defineNum>0){
		var defineIds = "";
		for(var i=0;i<defineNum;i++){
			defineIds += $("input[name='fdReferenceForms["+i+"].fdDefineId']").val()+";";
		}
		
		var defineNames = "";
		for(var i=0;i<defineNum;i++){
			defineNames += $("input[name='fdReferenceForms["+i+"].fdDefineName']").val()+";";
		}
		
		var parentIds = "";
		for(var i=0;i<defineNum;i++){
			parentIds += $("input[name='fdReferenceForms["+i+"].fdParentId']").val()+";";
		}
		
		var parentNames = "";
		for(var i=0;i<defineNum;i++){
			parentNames += $("input[name='fdReferenceForms["+i+"].fdParentName']").val()+";";
		}
		
		var displayTypes = "";
		for(var i=0;i<defineNum;i++){
			displayTypes += $("input[name='fdReferenceForms["+i+"].fdType']").val()+";";
		}
		
		top.defineId = defineIds;
		top.defineName = defineNames;
		top.parentId = parentIds;
		top.parentName = parentNames;
		top.displayType = displayTypes;
	//}
	
	
	seajs.use(['lui/dialog', 'lui/util/env','lang!sys-property','lang!sys-ui'],function(dialog, env,lang,ui_lang) {
		dialog.iframe(url, lang["sysPropertyTemplate.fdReferences"], null, {
			width : 900,
			height : 550,
			buttons : [
				{
					name : ui_lang["ui.dialog.button.ok"],
					value : true,
					focus : true,
					fn : function(value,_dialog) {
						if(_dialog.frame && _dialog.frame.length > 0){
							var _frame = _dialog.frame[0];
							var contentWindow = $(_frame).find("iframe")[0].contentWindow;
							if(contentWindow.onSubmit()) {
								
								//移除原先存在的所有属性
								//removeAllDefine();			
								var datas = contentWindow.onSubmit().slice(0);
								if(datas.length>0){			
									sysPropDefineAddRowAfter(datas);
									setTimeout(function() {
										_dialog.hide(value);
									}, 200);
								}else{
									//如果全不选 移除原先存在的所有属性
									removeAllDefine();
									_dialog.hide(value);
								}
							}
						}
						
					}
				}
				,{
					name :ui_lang["ui.dialog.button.cancel"],
					value : false,
					styleClass : 'lui_toolbar_btn_gray',
					fn : function(value, dialog) {
						dialog.hide(value);
					}
				}
			]	
		});
	});
	document.getElementsByName("fdIsHbmChange")[0].value = "true";<%--标识为需要重启hibernate session--%>
}
function sysPropDefineDeleteRow(trObj,isNoConfirm) {
	if(!isNoConfirm && !confirmDelete()) {
		return;
	}
	var isAlert = false;
	var optTR = trObj || DocListFunc_GetParentByTagName("TR");
	var optTB = DocListFunc_GetParentByTagName("TABLE", optTR);
	var rowIndex = Com_ArrayGetIndex(optTB.rows, optTR);
	var defineId = document.getElementsByName("fdReferenceForms[" + (rowIndex-1) + "].fdDefineId")[0].value;
	var table = document.getElementById("TABLE_FilterList");
	var inputs = table.getElementsByTagName('input');
	
	<%--删除引用属性--%>
	DocList_DeleteRow(optTR);

	<%--删除筛选项--%>
	var deleteTr = new Array(); 
	for (var i = 0; i < inputs.length; i++){
		if (inputs[i].getAttribute('ref') == "fdDefineId" && inputs[i].getAttribute('value') == defineId) {
			var filterTR = DocListFunc_GetParentByTagName("TR", inputs[i]);
			deleteTr.push(filterTR) ;
		}
	}
	for(var j = 0;j<deleteTr.length; j++){
		 DocList_DeleteRow(deleteTr[j]);
	 }
	
	document.getElementsByName("fdIsHbmChange")[0].value = "true";<%--标识为需要重启hibernate session--%>
	//删除JSP片段
	JspBuild.remove(defineId);
}

function sysPropFilterAddRowAfter(dat,defineId) {
	if(dat) {
		var data = dat.GetHashMapArray();
		for (var i = 0; i < data.length; i ++) {
			var fieldValues = new Object();
			fieldValues["fdFilterForms[!{index}].fdFilterSettingId"] = data[i].id;
			fieldValues["fdFilterForms[!{index}].fdFilterSettingName"] = data[i].name;
			fieldValues["fdFilterForms[!{index}].fdName"] = data[i].name;
			var HTMLcontent = new Array(null,"<input name='fdFilterForms[!{index}].fdName' class='inputsgl' value='' type='text' validate='required maxLength(100)' style='width:95%'> <span class='txtstrong'>*</span>");
			if(defineId)
				fieldValues["fdFilterForms[!{index}].fdDefineId"] = defineId;
			DocList_AddRow("TABLE_FilterList", HTMLcontent, fieldValues);
		}
	}
	//设置defineId
	setDefineId() ;
}
function sysPropFilterAddRow() {
	var defIds = sysPropGetSelectedIds("TABLE_DocList", "fdDefineId").join(";");
	//Dialog_TreeList(true, null, null, null, 
			//"sysPropertyFilterListService&fdType=define&fdParentId=!{value}&fdModelName=${sysPropertyTemplateForm.fdModelName}&fdDefineIds=" + defIds, 
			//'<bean:message bundle="sys-property" key="sysPropertyFilterSetting.fdDefine" />', 
			//"sysPropertyFilterListService&fdType=filter&fdParentId=!{value}&fdModelName=${sysPropertyTemplateForm.fdModelName}", 
			//sysPropFilterAddRowAfter, null,null, null, null, '<bean:message bundle="sys-property" key="table.sysPropertyFilter" />');
	Dialog_Tree(true, null, null, null, 
			"sysPropertyFilterListService&fdParentId=!{value}&fdModelName=${sysPropertyTemplateForm.fdModelName}&fdDefineIds=" + defIds, 
			'<bean:message bundle="sys-property" key="sysPropertyFilterSetting.fdDefine" />', 
			null, sysPropFilterAddRowAfter, null, null, null, '<bean:message bundle="sys-property" key="table.sysPropertyFilter" />');

}
function validateSysPropertyTemplateFormData(thisObj) {

	var tb2 = document.getElementById("TABLE_FilterList");
	var fields2 = tb2.getElementsByTagName("input");
	for(var i=0; i < fields2.length; i++){
		var fieldName = fields2[i].name.replace(/\d+/g, "!{index}");
		if(fieldName == "fdFilterForms[!{index}].fdFilterSettingId") {
			return true;
		}
	}
	 alert('<bean:message bundle="sys-property" key="sysPropertyTemplate.define.required" />');<%--至少选择引用一项属性！--%>
	 return false;
	 
}
function findFilterSettingByDefineId(defineId){
    //ajax 
	var url="sysPropertyFilterListForMenuService&type=1&defineId="+defineId;
	var data = new KMSSData(); 
	data.SendToBean(url,function kmsExpert_ok_after(rtnData){ 
		if(rtnData){
			//var data = rtnData.GetHashMapArray();
			sysPropFilterAddRowAfter(rtnData,defineId) ;
		}
	});	  
}

function setDefineId(){
	var tb = document.getElementById("TABLE_FilterList");
	var fields = tb.getElementsByTagName("input");
	for(var i=0; i < fields.length; i++){
		if (fields[i].ref == "fdDefineId"  ) {
			if(fields[i].value=='') {
              $(fields[i]).siblings().each(function(index,domEle){
                       if(domEle.ref=="fdFilterSettingId") {
                    	   fields[i].value=findDefineIdByfilterSettingId(domEle.value) ;
                        }
                  });  
		     }
	    }
    }
}
function findDefineIdByfilterSettingId(filterSettingId){
	//ajax 
	var url="sysPropertyFilterListForMenuService&type=2&filterSettingId="+filterSettingId;
	var data = new KMSSData(); 
	data.SendToBean(url,function kmsExpert_ok_after(rtnData){ 
		if(rtnData){
			 var data = rtnData.GetHashMapArray();
			 if(data){
				 if(data[0])
				     return  data[0].id  ;
				 else 
					 return ""  ;   
			 } else 
				 return ""  ;
		}
	});	  
}

var editIndex;
function addParentReference(index,fdId) {
	this.editIndex = index;
	sysPropAddRow("TABLE_DocList", "", "sysPropertyReferenceListService&templateId="+'${JsParam.fdId}'+
			"&fdDefineId="+fdId, '<bean:message bundle="sys-property" key="table.sysPropertyReference" />',
			 addParentReferenceAfter);
}
function addParentReferenceAfter(dat){
	if(dat) {
		var data = dat.GetHashMapArray();
		for (var i = 0; i < data.length; i ++) {
			$("input[name='fdReferenceForms["+editIndex+"].fdParentId']").val(data[i].value);
			$("input[name='fdReferenceForms["+editIndex+"].fdParentName']").val(data[i].text);
			findFilterParentIdByDefineId($("input[name='fdReferenceForms["+editIndex+"].fdDefineId']").val(),data[i].defineId);
		}
	}
}
function findFilterParentIdByDefineId(defineId,parentDefineId){
    //ajax 
	var url="sysPropertyFilterListForMenuService&type=3&templateId="+'${JsParam.fdId}'+"&defineId="+parentDefineId;
	var data = new KMSSData(); 
	data.SendToBean(url,function (rtnData){
		if(rtnData){
			setPropFilterParentAfter(defineId,rtnData) ;
		}
	});	  
}
function setPropFilterParentAfter(defineId,dat) {
	if(dat) {
		var data = dat.GetHashMapArray();
		$("input[areaType=filter][value="+defineId+"]").each(function(){
				$(this).next().val(data[0].id);
		})
	}
}

/**
 * 删除分类时先去除关联引用
 */
function DeleteRowIndex(e){
	var filterId = $(e).closest("tr").find("[name$='.fdId']").val();
	if(filterId) {
		$("input[ref=fdFilterParentId][value="+filterId+"]").each(function(){
			this.value = "";
		});
	}
} 

var JspBuild = {
	//fdJspStructure:$('textarea[name=fdJspStructure]'),
	jspStructure:"",
	docStructure:$('<table>'),
	timer:0,
	//是否自定义显示模式
	isOtherDisplay:false,
	//增加JSP片段
	add:function(id,text){
		if(!this.isOtherDisplay)return;
		var str = _formatHtml("<TR id="+id+"><!--"+text+"--><td valign=top class=td_normal_title>@{text:"+id
		+"}：</td><td id="+id+" colspan=3>@{value:"+id+"}</td></TR>");
		this.jspStructure+=str;
		this.docStructure.append(str);
		this.updateJspFrag();
	},
	//清除所有自定义JSP结构片段
	removeAll:function(){
		this.jspStructure = "";
		this.updateJspFrag();
	},
	//移除JSP片段
	remove:function(id){
		if(!this.isOtherDisplay)return;
		try{
			this.docStructure = $('<table>').append(this.getJspStructure());
			var child = this.docStructure.children().children().filter('#'+id)[0];
			if(child && child.getElementsByTagName("td").length==1){
				this.docStructure.children()[0].removeChild(child);
				var html = this.docStructure.children()[0].innerHTML;
				this.jspStructure = _formatHtml(html);
				this.updateJspFrag();
			}else{
				alert("JSP显示模板更新失败,请手动维护");
			}
		}catch(e){
			alert("JSP显示模板更新失败,请手动维护");
		}
	},
	//下移JSP片段
	moveDown:function(id){
		if(!this.isOtherDisplay)return;
		try{
			this.docStructure = $('<table>').append(this.getJspStructure());
			this.docStructure.children().children().filter('#'+id).insertAfter(
					this.docStructure.children().children().filter('#'+id).next());
			var html = this.docStructure.children()[0].innerHTML;
			this.jspStructure = _formatHtml(html);
			this.updateJspFrag();
		}catch(e){
			alert("JSP模板不能更新,请手动维护");
		}		
	},
	//上移JSP片段
	moveUp:function(id){
		if(!this.isOtherDisplay)return;
		try{
			this.docStructure = $('<table>').append(this.getJspStructure());
			this.docStructure.children().children().filter('#'+id).insertBefore(
					this.docStructure.children().children().filter('#'+id).prev());
			var html = this.docStructure.children()[0].innerHTML;
			this.jspStructure = _formatHtml(html);
			this.updateJspFrag();
		}catch(e){
			alert("JSP模板不能更新,请手动维护");
		}		
	},
	//设置自定义显示模式
	setIsOtherDisplay:function(isOther){
		this.isOtherDisplay = isOther;
	},
	//更新jsp片段
	updateJspFrag:function(){
		window.clearTimeout(this.timer);
		this.timer = window.setTimeout(this.updateView,200);
	},
	//更新view显示
	updateView:function(){
		$('textarea[name=fdJspStructure]').val(JspBuild.jspStructure);
	},
	//获取jsp片段
	getJspStructure:function(){
		this.jspStructure = $('textarea[name=fdJspStructure]').val();
		return this.jspStructure;
	}
}

function _formatHtml(html) {
    var formatted = ''; 
    var reg = /(>)(<)(\/*)/g; 
    var reg2 = /(>\s*)/g;
    html = html.replace(reg2, ">").replace(reg, '$1\r\n$2$3');
    var pad = 0; 
    jQuery.each(html.split('\r\n'), function(index, node) { 
        var indent = 0; 
        if (node.match( /.+<\/\w[^>]*>$/ )) { 
            indent = 0; 
        } else if (node.match( /^<\/\w/ )) { 
            if (pad != 0) { 
                pad -= 1; 
            } 
        } else if (node.match( /^<\w[^>]*[^\/]>.*$/ )) { 
            indent = 1; 
        } else { 
            indent = 0; 
        } 
 
        var padding = ''; 
        for (var i = 0; i < pad; i++) { 
            padding += '  '; 
        } 
 
        formatted += padding + node + '\r\n'; 
        pad += indent; 
    }); 
 
    return formatted; 
}

function sysPropMoreOptionsChange(val, elem){
	var structure =  $('textarea[name=fdJspStructure]');
	if(val.indexOf("other") >= 0) {
		JspBuild.setIsOtherDisplay(true);
		$('#TABLE_DocList').children().children('tr[KMSS_IsContentRow=1]').each(function(index,e){
			JspBuild.add(e.id,e.title);
		})
		structure.show();
		$(".displayInLine").hide();
	}else{
		JspBuild.removeAll();
		JspBuild.setIsOtherDisplay(false);
		structure.hide();
		if(val.indexOf("one") >= 0){
			$(".displayInLine").hide();
		}
		else if(val.indexOf("two") >= 0){
			$(".displayInLine").show();
		}
	}
}


//设置显示模式
function checkDisplayModel(val){
	
	switch (val) {
		case "one":
			$(".displayInLine").hide();
			break;
		case "two":
			$(".displayInLine").show();
			break;
		case "other":
			//设置自定义显示模式
			JspBuild.setIsOtherDisplay(true);
			var structure = $('textarea[name=fdJspStructure]');
			structure.show();
			var jspFlag = structure.val();
			if(!jspFlag){
				$('#TABLE_DocList').children().children('tr[KMSS_IsContentRow=1]').each(function(index,e){
					JspBuild.add(e.id,e.title);
				})
			}else{
				JspBuild.jspStructure = jspFlag;
			}
			$(".displayInLine").hide();
			break;
		default:
			$("input[name='fdDisplayModel'][value='one']").attr("checked", true);
			break;
	}
}

function selectCategory(){
	var keys = "sys-simplecategory:dialog.maintree.title;sys-simplecategory:dialog.main.title";
	var arr = Data_GetResourceString(keys);
	var treeTitle = arr[0];
	var winTitle = arr[1];
	var url = 'sysPropertyCategoryListService&categoryId=!{value}';
	this.Dialog_Tree(false, "categoryId", "categoryName", null,url, treeTitle,null, null, "", null, false, winTitle);
}

//jsp结构模板维护
$(document).ready(function() {
	var val = "";
	$("input[name='fdDisplayModel']").each(function(){
		if( this.checked){
			val = this.value;
		}
	});
	checkDisplayModel(val);
});

function addCategory(){
	seajs.use(['lui/dialog'],function(dialog){
		dialog.simpleCategory('com.landray.kmss.sys.property.model.SysPropertyCategory','categoryId','categoryName',false,null,null,true,null,false);
	});
}
function removeAllDefine(){
	//deleteRow
	
	$("#TABLE_DocList > tbody").find("tr").each(function(index){
		if(index>0){
			DocList_DeleteRow($(this)[0]);
		}
	});
	
	$("#TABLE_FilterList > tbody").find("tr").each(function(index){
		if(index>0){
			DocList_DeleteRow($(this)[0]);
		}
	});

}
</script>
<html:form action="/sys/property/sys_property_template/sysPropertyTemplate.do" onsubmit="return validateSysPropertyTemplateFormData(this);">
<div id="optBarDiv">
	<c:if test="${sysPropertyTemplateForm.method_GET=='edit'}">
		<input type=button value="<bean:message key="button.update"/>"
			onclick="Com_Submit(document.sysPropertyTemplateForm, 'update');">
	</c:if>
	<c:if test="${sysPropertyTemplateForm.method_GET=='add'}">
		<input type=button value="<bean:message key="button.save"/>"
			onclick="Com_Submit(document.sysPropertyTemplateForm, 'save');">
		<input type=button value="<bean:message key="button.saveadd"/>"
			onclick="Com_Submit(document.sysPropertyTemplateForm, 'saveadd');">
	</c:if>
	<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
</div>
<p class="txttitle"><bean:message bundle="sys-property" key="table.sysPropertyTemplate"/></p>
<center>
<table class="tb_normal" width=95%>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-property" key="sysPropertyTemplate.fdName"/>
		</td><td colspan="3" width="85%">
			<xform:text property="fdName" style="width:85%" />
			<xform:text property="fdModelName" showStatus="noShow" />
			<%-- 标识是否重启hibernate session --%>
			<xform:text property="fdIsHbmChange" value="false" showStatus="noShow" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-property" key="sysProperty.sysPropertyCategory"/>
		</td><td colspan="3"  width="35%">
			<xform:text property="categoryName" style="width:85%" showStatus="readOnly"/>
			<xform:text property="categoryId" showStatus="noShow" />
			<a href="javascript:void(0)" onclick="addCategory();"><bean:message key="button.select" /></a>
		</td>
	</tr>	
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-property" key="table.sysPropertyReference"/>
		</td><td colspan="3" width="85%">
			<table class="tb_normal" width=100% id="TABLE_DocList">
				<tr align="center">
					<td class="td_normal_title" width="20%">
						<bean:message bundle="sys-property" key="table.sysPropertyDefine"/>
					</td>
					<td class="td_normal_title" width="20%">
						<bean:message bundle="sys-property" key="sysPropertyReference.fdDisplayName"/>
					</td>
					<td class="td_normal_title" width="10%">
						<bean:message bundle="sys-property" key="sysPropertyReference.fdIsNotNull"/>
					</td>
					<td class="td_normal_title" width="10%">
						${lfn:message('sys-property:sysPropertyReference.fdIsShowAll') }
						<div style="color: red;">
							${lfn:message('sys-property:sysPropertyReference.fdIsShowAll.tip') }
						</div>
					</td>
					<td class="td_normal_title displayInLine" width="10%" >
						<bean:message bundle="sys-property" key="sysPropertyReference.fdDisplayInLine"/>
					</td>
					<td class="td_normal_title" align="center" width=20%>
						<img src="${KMSS_Parameter_StylePath}icons/add.gif" onclick="sysPropDefineAddRow();" style="cursor:pointer;" />
					</td>
				</tr>
				<tr KMSS_IsReferRow="1" style="display:none">
					<td>
						<input type="hidden" name="fdReferenceForms[!{index}].fdId" />
						<input type="hidden" name="fdReferenceForms[!{index}].fdTemplateId" value="${HtmlParam.fdId}" />
						<input type="hidden" name="fdReferenceForms[!{index}].fdDefineId" ref="fdDefineId" />
						<input type="hidden" name="fdReferenceForms[!{index}].fdType" />
						<input type="hidden" name="fdReferenceForms[!{index}].fdParentName" />
						<xform:text property="fdReferenceForms[!{index}].fdParentId" style="width:80%" showStatus="noShow" />
					    <xform:text property="fdReferenceForms[!{index}].fdDefineName" style="border:0px; color:black;width:100%;" showStatus="readOnly" />
					</td>
					<td>
						<xform:text property="fdReferenceForms[!{index}].fdDisplayName" style="width:95%" required="true" />
					</td>
					<td>
						<center>
						<xform:checkbox property="fdReferenceForms[!{index}].fdIsNotNull">
							<xform:simpleDataSource value="true" textKey="message.yes" />
						</xform:checkbox>
						</center>
					</td>
					<td>
						<center>
							<div class="luiPropertyIsShowAll">
								<xform:checkbox property="fdReferenceForms[!{index}].fdIsShowAll">
									<xform:simpleDataSource value="true" textKey="message.yes" />
								</xform:checkbox>
							</div>
						</center>
					</td>
					<td class="displayInLine">
						<center>
						<xform:checkbox property="fdReferenceForms[!{index}].fdDisplayInLine">
							<xform:simpleDataSource value="true" textKey="message.yes" />
						</xform:checkbox>
						</center>
					</td>
					 
					<td>
					   <center>
							<img src="${KMSS_Parameter_StylePath}icons/up.gif" onclick="DocList_MoveRow(-1);" style="cursor:pointer;">
							<img src="${KMSS_Parameter_StylePath}icons/down.gif" onclick="DocList_MoveRow(1);" style="cursor:pointer;">
							<img src="${KMSS_Parameter_StylePath}icons/delete.gif" onclick="sysPropDefineDeleteRow();" style="cursor:pointer;">
						</center>
					</td>
				</tr>
				<c:forEach items="${sysPropertyTemplateForm.fdReferenceForms}" var="sysPropertyReferenceForm" varStatus="vstatus">
					<tr KMSS_IsContentRow="1" id="${sysPropertyReferenceForm.fdDefineId}" title="${sysPropertyReferenceForm.fdDefineName}">
						<td>
							<input type="hidden" name="fdReferenceForms[${vstatus.index}].fdId" value="${sysPropertyReferenceForm.fdId}" />
							<input type="hidden" name="fdReferenceForms[${vstatus.index}].fdTemplateId" value="${sysPropertyReferenceForm.fdTemplateId}" />
							<input type="hidden" name="fdReferenceForms[${vstatus.index}].fdDefineId" value="${sysPropertyReferenceForm.fdDefineId}" ref="fdDefineId" />
							<xform:text property="fdReferenceForms[${vstatus.index}].fdParentId" style="width:80%" showStatus="noShow" />
						    <xform:text property="fdReferenceForms[${vstatus.index}].fdDefineName" style="border:0px; color:black; width:100%;"   />
						</td>
						<td>
							<xform:text property="fdReferenceForms[${vstatus.index}].fdDisplayName" style="width:95%" required="true" />
						</td>
						
						<td>
							<center>
							<xform:checkbox property="fdReferenceForms[${vstatus.index}].fdIsNotNull">
								<xform:simpleDataSource value="true" textKey="message.yes" />
							</xform:checkbox>
							</center>
						</td>
						
						<td>
							<center>
								<div class="luiPropertyIsShowAll">

											<%
												Object form = pageContext.getAttribute("sysPropertyReferenceForm");

												if (form != null) {
													SysPropertyReferenceForm referenceForm = (SysPropertyReferenceForm) form;
													String defineId = referenceForm.getFdDefineId();
													SysPropertyDefine define = (SysPropertyDefine) ((ISysPropertyDefineService) SpringBeanUtil
															.getBean("sysPropertyDefineService")).findByPrimaryKey(defineId);

													if ("checkbox".equals(define.getFdDisplayType()) || "radio".equals(define.getFdDisplayType())) {
												%>
														<xform:checkbox property="fdReferenceForms[${vstatus.index}].fdIsShowAll">
															<xform:simpleDataSource value="true" textKey="message.yes" />
														</xform:checkbox>
												<%		
													}

												}
											%>
								</div>
							</center>
						</td>
						
						<td  class="displayInLine">
							<center>
							<xform:checkbox property="fdReferenceForms[${vstatus.index}].fdDisplayInLine">
								<xform:simpleDataSource value="true" textKey="message.yes" />
							</xform:checkbox>
							</center>
						</td>						 
						<td>
						   <center>
								<img src="${KMSS_Parameter_StylePath}icons/up.gif" onclick="DocList_MoveRow(-1);JspBuild.moveUp('${sysPropertyReferenceForm.fdDefineId}')" style="cursor:pointer;">
								<img src="${KMSS_Parameter_StylePath}icons/down.gif" onclick="DocList_MoveRow(1);JspBuild.moveDown('${sysPropertyReferenceForm.fdDefineId}')" style="cursor:pointer;">
								<img src="${KMSS_Parameter_StylePath}icons/delete.gif" onclick="sysPropDefineDeleteRow();" style="cursor:pointer;">
							</center>
						</td>
					</tr>						
				</c:forEach>
			</table>
			<div style="color:red;padding-top:5px"><bean:message bundle="sys-property" key="sysPropertyTemplate.selectDefine.note" /></div>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-property" key="table.sysPropertyFilter"/>
		</td><td colspan="3" width="85%">
			<table class="tb_normal" width=100% id="TABLE_FilterList">
				<tr align="center">
					<td class="td_normal_title" width="33%">
						<bean:message bundle="sys-property" key="sysPropertyFilter.fdFilterSetting"/>
					</td>
					<td class="td_normal_title" width="33%">
						<bean:message bundle="sys-property" key="sysPropertyFilter.fdName"/>
					</td>
					<td class="td_normal_title" align="center" width=33%>
						<img src="${KMSS_Parameter_StylePath}icons/add.gif" onclick="sysPropFilterAddRow();" style="cursor:pointer;" />
					</td>
				</tr>
				<tr KMSS_IsReferRow="1" style="display:none">
					<td>
						<input type="hidden" name="fdFilterForms[!{index}].fdId" />
						<input type="hidden" name="fdFilterForms[!{index}].fdTemplateId" value="${HtmlParam.fdId}" />
						<input type="hidden" name="fdFilterForms[!{index}].fdFilterSettingId" ref="fdFilterSettingId" />
						<input type="hidden" name="fdFilterForms[!{index}].fdDefineId" areaType="filter" ref="fdDefineId" />
						<input type="hidden" name="fdFilterForms[!{index}].fdParentId" ref="fdFilterParentId"/>
					    <xform:text property="fdFilterForms[!{index}].fdFilterSettingName" style="border:0px; color:black;width:95%;" showStatus="readOnly" />
					</td>
					<td>
						<xform:text property="fdFilterForms[!{index}].fdName" style="width:95%" required="true " />
					</td>
					<td>
					   <center>
							<img src="${KMSS_Parameter_StylePath}icons/up.gif" onclick="DocList_MoveRow(-1);" style="cursor:pointer;">
							<img src="${KMSS_Parameter_StylePath}icons/down.gif" onclick="DocList_MoveRow(1);" style="cursor:pointer;">
							<img src="${KMSS_Parameter_StylePath}icons/delete.gif" onclick="DeleteRowIndex(this);DocList_DeleteRow();" style="cursor:pointer;">
						</center>
					</td>
				</tr>
				<c:forEach items="${sysPropertyTemplateForm.fdFilterForms}" var="sysPropertyFilterForm" varStatus="vstatus">
					<tr KMSS_IsContentRow="1">
						<td>
							<input type="hidden" name="fdFilterForms[${vstatus.index}].fdId" value="${sysPropertyFilterForm.fdId}" />
							<input type="hidden" name="fdFilterForms[${vstatus.index}].fdTemplateId" value="${sysPropertyFilterForm.fdTemplateId}" />
							<input type="hidden" name="fdFilterForms[${vstatus.index}].fdFilterSettingId" value="${sysPropertyFilterForm.fdFilterSettingId}" ref="fdFilterSettingId" />
							<input type="hidden" name="fdFilterForms[${vstatus.index}].fdDefineId" value="${sysPropertyFilterForm.fdDefineId}" ref="fdDefineId" areaType="filter"/>
							<input type="hidden" name="fdFilterForms[${vstatus.index}].fdParentId" value="${sysPropertyFilterForm.fdParentId}" ref="fdFilterParentId"/>
						    <xform:text property="fdFilterForms[${vstatus.index}].fdFilterSettingName" style="border:0px; color:black;width:95%;" showStatus="readOnly" />
						</td>
						<td>
							<xform:text property="fdFilterForms[${vstatus.index}].fdName" style="width:95%"  validators="required maxLength(100)"/>
						</td>
						<td>
						   <center>
								<img src="${KMSS_Parameter_StylePath}icons/up.gif" onclick="DocList_MoveRow(-1);" style="cursor:pointer;">
								<img src="${KMSS_Parameter_StylePath}icons/down.gif" onclick="DocList_MoveRow(1);" style="cursor:pointer;">
								<img src="${KMSS_Parameter_StylePath}icons/delete.gif" onclick="DeleteRowIndex(this);DocList_DeleteRow();" style="cursor:pointer;">
							</center>
						</td>
					</tr>						
				</c:forEach>
			</table>
			<br /><bean:message bundle="sys-property" key="sysPropertyTemplate.example"/><br />
			<% 
			String language = ResourceUtil.getLocaleStringByUser(request);
			%>
			<c:set var="language" value="<%=language %>"></c:set>
			<c:choose>
				<c:when test="${language eq 'en-us'}"><img src="${KMSS_Parameter_ContextPath}sys/property/define/images/selectFilter_en.png" border="0" /></c:when>
				<c:when test="${language eq 'zh-cn'}"><img src="${KMSS_Parameter_ContextPath}sys/property/define/images/selectFilter.jpg" border="0" /></c:when>
				<c:otherwise><img src="${KMSS_Parameter_ContextPath}sys/property/define/images/selectFilter.jpg" border="0" /></c:otherwise>
			</c:choose>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-property"  key="sysPropertyTemplate.fdJspStructure"/>
		</td><td colspan="3" width="85%">
			<xform:radio property="fdDisplayModel" onValueChange="sysPropMoreOptionsChange">
				<xform:simpleDataSource value="one"><bean:message bundle="sys-property" key="sysPropertyTemplate.fdJspStructure.one"/></xform:simpleDataSource>
				<xform:simpleDataSource value="two"><bean:message bundle="sys-property" key="sysPropertyTemplate.fdJspStructure.two"/></xform:simpleDataSource>
				<xform:simpleDataSource value="other"><bean:message bundle="sys-property" key="sysPropertyTemplate.fdJspStructure.other"/></xform:simpleDataSource>
			</xform:radio><br/>
			<xform:textarea property="fdJspStructure" showStatus="edit" style="width:85%;height:300px;display:none" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-property" key="sysPropertyTemplate.belongModelName"/>
		</td><td colspan="3" width="85%">
			<c:out value="${sysPropertyTemplateForm.modelDisplayName}" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-property" key="sysPropertyTemplate.fdLastModify"/>
		</td><td colspan="3" width="85%">
			<xform:datetime property="fdLastModify" showStatus="view" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-property" key="sysPropertyTemplate.docCreator"/>
		</td><td width="35%">
			<xform:address propertyId="docCreatorId" propertyName="docCreatorName" orgType="ORG_TYPE_PERSON" showStatus="view" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-property" key="sysPropertyTemplate.docCreateTime"/>
		</td><td width="35%">
			<xform:datetime property="docCreateTime" showStatus="view" />
		</td>
	</tr>
	<%-- 所属场所 --%>
	<% if(ISysAuthConstant.IS_AREA_ENABLED) { %> 
	<tr>	
	    <td class="td_normal_title" width=15%>
	        <bean:message key="sysAuthArea.authArea" bundle="sys-authorization" />
		</td>
		<td colspan="3">
			<input type="hidden" name="authAreaId" value="${sysPropertyTemplateForm.authAreaId}"> 
			<xform:text property="authAreaName" showStatus="view" />	
		</td>	
	</tr>
	<% } %>
</table>
</center>
<html:hidden property="fdId" />
<html:hidden property="method_GET" />
<script>
	DocList_Info.push("TABLE_FilterList");
	$KMSSValidation();
</script>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp"%>
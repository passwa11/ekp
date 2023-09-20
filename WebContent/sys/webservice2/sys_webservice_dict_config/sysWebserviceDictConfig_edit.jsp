<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<script type="text/javascript">
Com_IncludeFile("jquery.js|json2.js|data.js|select.js|doclist.js|dialog.js");
</script>
<center>
<table class="tb_normal" width=95%>
	<input type="hidden" name="fdMainDisplay">
	<input type="hidden" name="fdListDisplay">	
	<input type="hidden" name="fdPrefix">
	<tr>
		<td class="td_normal_title" width=20%>
			<bean:message bundle="sys-webservice2" key="sysWebserviceDictConfig.fdName"/>
		</td><td>
			<input type="text" style="width:90%;" class="inputSgl" name="fdName"/>
			<a onclick="selectDictModel();return false;" href=""><bean:message key="dialog.selectOther" /></a>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=20%>
			<bean:message bundle="sys-webservice2" key="sysWebserviceDictConfig.fdModelName"/>
		</td><td>
			<input type="text" style="width:90%;" class="inputSgl" name="fdModelName" readonly="readonly"/>
		</td>
	</tr>
	<tr id="systemTrTitle">
		<td class="td_normal_title" colspan="2">
			<bean:message bundle="sys-webservice2" key="restModuleConfigView.docDisplayCfg"/>
		</td>
	</tr>
	<tr id="systemTrContent">
		<td colspan="2" style="padding: 0px;margin: 0px;border: 0px !important;">
			<table class="tb_normal" width="100%">
				<tr class="tr_normal_title">
					<td width="40%">
						<bean:message bundle="sys-webservice2" key="restModuleConfigView.docDisplayCfg.optionBox"/>
					</td>
					<td width="10%"><bean:message bundle="sys-webservice2" key="restModuleConfigView.docDisplayCfg.operation"/></td>
					<td width="40%">
						<bean:message bundle="sys-webservice2" key="restModuleConfigView.docDisplayCfg.selectedBox"/>
					</td>
					<td width="10%"><bean:message bundle="sys-webservice2" key="restModuleConfigView.docDisplayCfg.operation"/></td>
				</tr>
				<tr>
					<td valign="top">
						<select id="optionalList" name="optionalList" multiple ondblclick="listTolist('optionalList','selectedList',false);" style="width:100%" size="15"></select>
					</td>
					<td>
						<center>
							<input type=button class="btnopt" value="<bean:message bundle="sys-webservice2" key="restModuleConfigView.docDisplayCfg.add"/>" onclick="listTolist('optionalList','selectedList',false);">
							<br><br>
							<input type=button class="btnopt" value="<bean:message bundle="sys-webservice2" key="restModuleConfigView.docDisplayCfg.addAll"/>" onclick="listTolist('optionalList','selectedList',true);">
						</center>
					</td>
					<td>
						<select id="selectedList" name="selectedList" multiple ondblclick="listTolist('selectedList','optionalList',false);" style="width:100%" size="15"></select>
					</td>
					<td>
						<center>
							<input type=button class="btnopt" value='<bean:message bundle="sys-webservice2" key="restModuleConfigView.docDisplayCfg.move"/>' onclick="removeOption('selectedList',1);">
							<br><br>
							<input type=button class="btnopt" value='<bean:message bundle="sys-webservice2" key="restModuleConfigView.docDisplayCfg.down"/>' onclick="removeOption('selectedList',-1);">
							<br><br>
							<input type=button class="btnopt" value='<bean:message bundle="sys-webservice2" key="restModuleConfigView.docDisplayCfg.delete"/>' onclick="listTolist('selectedList','optionalList',false);">
							<br><br>
							<input type=button class="btnopt" value='<bean:message bundle="sys-webservice2" key="restModuleConfigView.docDisplayCfg.deleteAll"/>' onclick="listTolist('selectedList','optionalList',true);">
						</center>
					</td>
				</tr>
			</table>
		</td>
	</tr>	

	<tr id="listTrTitle">
		<td class="td_normal_title" colspan="2">
			列表页显示内容配置
		</td>
	</tr>
	<tr id="listTrContent">
		<td colspan="2" style="padding: 0px;margin: 0px;border: 0px !important;">
			<table class="tb_normal" width="100%">
				<tr class="tr_normal_title">
					<td width="40%">
						<bean:message bundle="sys-webservice2" key="restModuleConfigView.docDisplayCfg.optionBox"/>
					</td>
					<td width="10%"><bean:message bundle="sys-webservice2" key="restModuleConfigView.docDisplayCfg.operation"/></td>
					<td width="40%">
						<bean:message bundle="sys-webservice2" key="restModuleConfigView.docDisplayCfg.selectedBox"/>
					</td>
					<td width="10%"><bean:message bundle="sys-webservice2" key="restModuleConfigView.docDisplayCfg.operation"/></td>
				</tr>
				<tr>
					<td valign="top">
						<select id="optionalList2" name="optionalList2" multiple ondblclick="listTolist('optionalList2','selectedList2',false);" style="width:100%" size="15"></select>
					</td>
					<td>
						<center>
							<input type=button class="btnopt" value="<bean:message bundle="sys-webservice2" key="restModuleConfigView.docDisplayCfg.add"/>" onclick="listTolist('optionalList2','selectedList2',false);">
							<br><br>
							<input type=button class="btnopt" value="<bean:message bundle="sys-webservice2" key="restModuleConfigView.docDisplayCfg.addAll"/>" onclick="listTolist('optionalList2','selectedList2',true);">
						</center>
					</td>
					<td>
						<select id="selectedList2" name="selectedList2" multiple ondblclick="listTolist('selectedList2','optionalList2',false);" style="width:100%" size="15"></select>
					</td>
					<td>
						<center>
							<input type=button class="btnopt" value='<bean:message bundle="sys-webservice2" key="restModuleConfigView.docDisplayCfg.move"/>' onclick="removeOption('selectedList2',1);">
							<br><br>
							<input type=button class="btnopt" value='<bean:message bundle="sys-webservice2" key="restModuleConfigView.docDisplayCfg.down"/>' onclick="removeOption('selectedList2',-1);">
							<br><br>
							<input type=button class="btnopt" value='<bean:message bundle="sys-webservice2" key="restModuleConfigView.docDisplayCfg.delete"/>' onclick="listTolist('selectedList2','optionalList2',false);">
							<br><br>
							<input type=button class="btnopt" value='<bean:message bundle="sys-webservice2" key="restModuleConfigView.docDisplayCfg.deleteAll"/>' onclick="listTolist('selectedList2','optionalList2',true);">
						</center>
					</td>
				</tr>
			</table>
		</td>
	</tr>

	<tr>
		<td align="center" colspan="2">
			<a href=javascript:void(0);" onclick="save();"><input class="btnopt" type="button" value="<bean:message key="button.save"/>"></a>
			&nbsp;&nbsp;
			<a href=javascript:void(0);" onclick="window.close();"><input class="btnopt" type="button" value="<bean:message key="button.close"/>"></a>
		</td>
	</tr>
</table>
</center>
<script>

var origModelName;
$(document).ready(function(){ 
	var obj;
	if (window.dialogArguments == undefined){
		obj = window.opener.openObj;
	}else{
		obj = window.dialogArguments;
	}
	origModelName = obj.fdModelName;
	
	$('input[name="fdName"]').val(obj.fdName);
	$('input[name="fdModelName"]').val(obj.fdModelName);
	$('input[name="fdMainDisplay"]').val(obj.fdMainDisplay);
	$('input[name="fdListDisplay"]').val(obj.fdListDisplay);	
	var modelName = obj.fdModelName;
	if(typeof(modelName)!="undefined"){
		var data = new KMSSData();
		var url="sysWebservicePropertySelectList&modelName="+modelName;
		data.SendToBean(url,initOptionalList);
	}
	var fdMainDisplay = obj.fdMainDisplay;
	if(typeof(fdMainDisplay)!="undefined"){
		if(fdMainDisplay != null && fdMainDisplay != ""){
			var propertyList = fdMainDisplay.propertyList;
			for(var key in propertyList){
				var selectedfield = $('#selectedList');
				$("<option value='"+JSON.stringify(propertyList[key])+"'>"+propertyList[key].propertyText+"</option>").appendTo(selectedfield);
			}
		}
	}
	
	var fdListDisplay = obj.fdListDisplay;
	if(typeof(fdListDisplay)!="undefined"){
		if(fdListDisplay != null && fdListDisplay != ""){
			var propertyList = fdListDisplay.propertyList;
			for(var key in propertyList){
				var selectedfield = $('#selectedList2');
				$("<option value='"+JSON.stringify(propertyList[key])+"'>"+propertyList[key].propertyText+"</option>").appendTo(selectedfield);
			}
		}
	}
});

//加载数据字典里面属性数据
function loadDictPropertyData(){
	var modelName = $('input[name="fdModelName"]').val();
	if(modelName == null && modelName == "")
		return;
	var data = new KMSSData();

	$("#optionalList option").remove();
	$("#selectedList option").remove();
	$("#optionalList2 option").remove();
	$("#selectedList2 option").remove();	
	var url="sysWebservicePropertySelectList&modelName="+modelName;
	data.SendToBean(url,initOptionalList);
	
}

var modelNameBefore;

//选择数据字典模型
function selectDictModel(){
	var obj;
	if (window.dialogArguments == undefined)
		obj = window.opener.openObj;
	else
		obj = window.dialogArguments;
	var urlPrefix = obj.fdPrefix;
	modelNameBefore = $('input[name="fdModelName"]').val();
	Dialog_List(false, "fdModelName", "fdName", null, "restDictModelSelectDialog&urlPrefix="+urlPrefix,afterModelSelect,null,null,null,"<bean:message bundle='sys-webservice2' key='restModuleConfigView.docSelectDilog'/>");

/* 	var modelNameAfter = $('input[name="fdModelName"]').val();
	if(modelNameBefore!=modelNameAfter){
		loadDictPropertyData();
	} */
}

function afterModelSelect(dataObj){
	var modelNameAfter = $('input[name="fdModelName"]').val();
	if(modelNameBefore!=modelNameAfter){
		loadDictPropertyData();
	}
}

/*******************************************
功能:两个select中的option的移动
参数:
	fromid:源list的id.  
	toid:目标list的id.  
	isAll参数(true或者false):是否全部移动或添加  
*********************************************/
function listTolist(fromid,toid,isAll) {
	var toSelect = $("#"+toid);
	var selectList = null;
    if(isAll == true) { //全部移动 
    	selectList = $("#"+fromid+" option");
    }else{
    	selectList = $("#"+fromid+" option:selected")
    }  
    selectList.each(function() {   
        var option = $(this);
        var hasVal = false;
        var options = toSelect.find("option");
        for(var i=0; i<options.length;i++){
            if(option.val() == $(options).val()){
            	hasVal = true;
            	break;
            }
        }
        if(!hasVal)
        	option.appendTo(toSelect); 
    });    
}
/*******************************************
功能:select中的option上下移动
参数:
	selectId:必选，list的id.  
	direct：必选，-1：下移动，1上移动
*********************************************/
function removeOption(selectId,direct){
	$("#"+selectId+" option:selected").each(function() {
		if(direct > 0)   
			$(this).prev().before($(this));
		else if(direct < 0)   
			$(this).next().after($(this));   
		
    });   
}

/*
 * 初始化备选列表里面的值
 */
function initOptionalList(rtnData){
	var field = $('#optionalList');
	var obj;
	if (window.dialogArguments == undefined)
		obj = window.opener.openObj;
	else
		obj = window.dialogArguments;
	var fdMainDisplay = obj.fdMainDisplay;
	var fdListDisplay = obj.fdListDisplay;
	if(rtnData){
		for(var i=0; i<rtnData.GetHashMapArray().length; i++){
			var isSelected = false;
			var obj = rtnData.GetHashMapArray()[i];
			var propertyText = obj['propertyText'];
			var propertyType = obj['propertyType'];
			var isFormMapping = obj['isFormMapping'];
			var propertyObj = { 'propertyName':obj['propertyName'],'propertyText':obj['propertyText']
							  };
			if(fdMainDisplay != null && fdMainDisplay != ""){
				var propertyList = fdMainDisplay.propertyList;
				for(var key in propertyList){
					if(propertyList[key].propertyName == obj['propertyName']){
						isSelected = true;
						continue;
					}
				}
			}
			if(!isSelected && (isFormMapping =='true' || propertyType == 'attachment'))
				$("<option value='"+JSON.stringify(propertyObj)+"'>"+propertyText+"</option>").appendTo(field);
		}
	}
	
	var field2 = $('#optionalList2');
	if(rtnData){
		for(var i=0; i<rtnData.GetHashMapArray().length; i++){
			var isSelected = false;
			var obj = rtnData.GetHashMapArray()[i];
			var propertyText = obj['propertyText'];
			var propertyType = obj['propertyType'];
			var isList = obj['isList'];
			var propertyObj = { 'propertyName':obj['propertyName'],'propertyText':obj['propertyText']
							  };
			if(fdListDisplay != null && fdListDisplay != ""){
				var propertyList = fdListDisplay.propertyList;
				for(var key in propertyList){
					if(propertyList[key].propertyName == obj['propertyName']){
						isSelected = true;
						continue;
					}
				}
			}
			if(!isSelected && propertyType != 'attachment' && isList !='true')
				$("<option value='"+JSON.stringify(propertyObj)+"'>"+propertyText+"</option>").appendTo(field2);
		}
	}
}
/*******************************************
功能:两个select中的option的移动
参数:
	fromid:源list的id.  
	toid:目标list的id.  
	isAll参数(true或者false):是否全部移动或添加  
*********************************************/
function listTolist(fromid,toid,isAll) {  
	var fromStyle = $("#"+fromid).attr('style');
	var toStyle = $("#"+toid).attr('style');
	var toSelect = $("#"+toid);
	var selectList = null;
    if(isAll == true) { //全部移动 
    	selectList = $("#"+fromid+" option");
    }else{
    	selectList = $("#"+fromid+" option:selected");
    }  
    selectList.each(function() {   
        var option = $(this);
        var hasVal = false;
        var options = toSelect.find("option");
        for(var i=0; i<options.length;i++){
            if(option.val() == $(options).val()){
            	hasVal = true;
            	break;
            }
        }
        if(!hasVal)
        	option.appendTo(toSelect); 
    });   
	$("#"+fromid).attr('style',fromStyle);
	$("#"+toid).attr('style',toStyle);
}
/*******************************************
功能:select中的option上下移动
参数:
	selectId:必选，list的id.  
	direct：必选，-1：下移动，1上移动
*********************************************/
function removeOption(selectId,direct){
	var style = $("#"+selectId).attr('style');
	$("#"+selectId+" option:selected").each(function() {
		if(direct > 0)   
			$(this).prev().before($(this));
		else if(direct < 0)   
			$(this).next().after($(this));   
		
    });   
	$("#"+selectId).attr('style',style);
}

/*******************************************
 返回配置信息
 
 displayDict 
 		{
 		 propertyList : [{propertyName:属性名,propertyType：属性类型,propertyText:属性文本,messageKey：messageKey},...] ,  // 显示文档属性列表
      	}
*********************************************/
function getMainDisplay(){
	var propertyList = new Array();
	$("#selectedList option").each(function() {
		var property = eval("("+($(this).val())+")");
		propertyList.push(property);
	});
	var fdMainDisplay = {'propertyList':propertyList};
	
	return fdMainDisplay;
}

function getListDisplay(){
	var propertyList = new Array();
	$("#selectedList2 option").each(function() {
		var property = eval("("+($(this).val())+")");
		propertyList.push(property);
	});
	var fdListDisplay = {'propertyList':propertyList};
	
	return fdListDisplay;
}

function save(){
	if(!validate())
		return;
	var obj;
	var index;
	var actionType;
	var modelArr;
	var pageTableId;

	
	if (window.dialogArguments == undefined){
		obj = window.opener.openObj;
		index = window.opener.index;
		actionType = window.opener.actionType;
		modelArr = window.opener.modelArr;
		pageTableId = window.opener.pageTableId;
	}else
		obj = window.dialogArguments;

	obj.fdName = $('input[name="fdName"]').val();
	obj.fdModelName = $('input[name="fdModelName"]').val();
	obj.fdMainDisplay   = getMainDisplay();
	obj.fdListDisplay   = getListDisplay();
	
	
	if (window.dialogArguments == undefined){	
	    if(actionType == 'add'){
	
			if(obj != null){
				if(obj.fdName && obj.fdName != ""){
					if(modelArr.indexOf(obj.fdModelName)==-1){
						window.opener.afterOpenModalDialog(obj,index,actionType,pageTableId);
					}else{
						alert('<bean:message bundle="sys-webservice2" key="sysWebserviceDictConfig.fdSelectedNotice"/>');
						return;
					}
				}
			}
	
	    }else{
			if(obj != null){
				if(origModelName==obj.fdModelName){
					
					window.opener.afterOpenModalDialog(obj,index);
				}else if(modelArr.indexOf(obj.fdModelName)==-1){
					
					window.opener.afterOpenModalDialog(obj,index);
				}else{
					alert('<bean:message bundle="sys-webservice2" key="sysWebserviceDictConfig.fdSelectedNotice"/>');
					return;
				}
			}
		}
	}
	window.close();
}

function validate(){
	var errorMsg = "";
	var canSave = true;
	if($('input[name="fdName"]').val() == ""){
		errorMsg += '<bean:message bundle="sys-webservice2" key="restModuleLabelList.fdName"/><bean:message bundle="sys-webservice2" key="validate.notNull"/>\n';
		canSave = false; 
	}
	if(canSave == false)
		alert(errorMsg);
	return canSave;
}
</script>
<%@ include file="/resource/jsp/edit_down.jsp"%>
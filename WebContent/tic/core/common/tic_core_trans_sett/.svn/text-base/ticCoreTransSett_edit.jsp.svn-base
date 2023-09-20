<%@page import="com.landray.kmss.tic.core.register.RegisterPlugin"%>
<%@page import="com.landray.kmss.tic.core.common.forms.TicCoreTransSettForm,
                com.landray.kmss.util.StringUtil,
                com.landray.kmss.tic.core.common.model.TicCoreTransSett,
                com.landray.kmss.tic.core.common.service.ITicCoreTransSettService,
                com.landray.kmss.sys.config.util.LicenseUtil,
                com.landray.kmss.util.SpringBeanUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
    <% 
    	pageContext.setAttribute("currentUser", UserUtil.getKMSSUser()); 
    	request.setAttribute("registers", RegisterPlugin.getExtensionArray());
    	String ldingCommonDataFormatId = request.getParameter("ldingCommonDataFormatId");
    	if(StringUtil.isNull(ldingCommonDataFormatId)){
    		TicCoreTransSettForm ticCoreTransSettForm = (TicCoreTransSettForm) request.getAttribute("ticCoreTransSettForm");
    		if(ticCoreTransSettForm!=null){
    			ldingCommonDataFormatId = ticCoreTransSettForm.getFdDataSourceFormatId();
    		}
    	}
    	if(StringUtil.isNull(ldingCommonDataFormatId)){
			try{
				TicCoreTransSett ticCoreTransSett = 
    					(TicCoreTransSett)(((ITicCoreTransSettService)SpringBeanUtil.getBean("ticCoreTransSettService"))
    					.findByPrimaryKey(request.getParameter("fdId"),
    							"com.landray.kmss.tic.core.common.model.TicCoreTransSett",
    							true));
				if(ticCoreTransSett!=null){
    				ldingCommonDataFormatId=ticCoreTransSett.getFdDataSourceFormatId();
				}
			}catch(Exception e){
				
			}
		}
    	String licenseLding = LicenseUtil.get("license-lding");
    	if("true".equals(licenseLding)){
        	request.setAttribute("isLdingTrue", true);
    	}else{
    		request.setAttribute("isLdingTrue", false);
    	}
    	request.setAttribute("ldingCommonDataFormatId", StringUtil.getString(ldingCommonDataFormatId));
    	
    %>
<%-- 
<c:set var="isLding" value="false"></c:set>    
<kmss:ifModuleExist path="/lding/console">
<c:set var="isLding" value="true"></c:set>
</kmss:ifModuleExist>
--%>
<template:include ref="default.edit">
    <template:replace name="head">
    <link href="${KMSS_Parameter_ContextPath}tic/core/resource/css/jquery.treeTable.css" rel="stylesheet" type="text/css" />
<script src="${KMSS_Parameter_ContextPath}tic/core/resource/js/jquery.treeTable.js" type="text/javascript"></script>
    
        <style type="text/css">
            
            		.lui_paragraph_title{
            			font-size: 15px;
            			color: #15a4fa;
            	    	padding: 15px 0px 5px 0px;
            		}
            		.lui_paragraph_title span{
            			display: inline-block;
            			margin: -2px 5px 0px 0px;
            		}
            		.inputsgl[readonly], .tb_normal .inputsgl[readonly] {
            		    border: 0px;
            		    color: #868686
            		}
            		.div_title{
            		  margin-bottom:10px;
            		}
            		.td_img{
            		}
            		#table_paraIn [type="text"],
            		#table_paraout  [type="text"],
            		#table_paraIn_trans  [type="text"],
            		#table_paraOut_trans  [type="text"],#table_paraOut_trans  textarea{
            		   max-width:100px;
            		}
            		.lui_toolbar_content,
            		.lui_form_path_frame,
            		table.tempTB{
            		    max-width:100%!important;
            		    width:100%!important;
            		}
            		
            		.lui-lbpm-radio span{
            			padding:0;
            		}
            		
        </style>
        
        <script type="text/javascript">
       
       
    var registers = ${registers};
    function getRegister(mainModelName){
    	//debugger;
    	for(i=0;i<registers.length;i++){
    		var register = registers[i];
    		if(register.mainModelName == mainModelName){
    			return register;
    		}
    	}
    	return;
    }
    
/*     function   utf8CodeToChineseChar(strUtf8) 
    { 
          var   iCode,   iCode1,   iCode2; 
          iCode   =   parseInt("0x"   +   strUtf8.substr(1,   2)); 
          iCode1   =   parseInt("0x"   +   strUtf8.substr(4,   2)); 
          iCode2   =   parseInt("0x"   +   strUtf8.substr(7,   2)); 

          return   String.fromCharCode(((iCode   &   0x0F)   <<   12)   |    
    ((iCode1   &   0x3F)   <<     6)   | 
    (iCode2   &   0x3F)); 
    } */
        
    var formInitData = {

    };
    var messageInfo = {

    };
    Com_IncludeFile("security.js");
    Com_IncludeFile("domain.js");
    Com_IncludeFile("form.js");
    Com_IncludeFile("config_edit.js", "${LUI_ContextPath}/tic/core/common/resource/js/", 'js', true);
    Com_IncludeFile("form_option.js", "${LUI_ContextPath}/tic/core/common/tic_core_trans_sett/", 'js', true);
    Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
    //js公式定义器脚本
    Com_IncludeFile("formula.js");
    Com_IncludeFile("base64.js");
    
    function selectFunction() {
    	//var fdDataSourceFormatId = '${ldingCommonDataFormatId}';
		if('${isLdingTrue}'=='true'){
			Dialog_TreeList(false,"fdFuncBaseId","fdFuncBaseName",";",
			           "ldingTicFindEnvronmentAndFunctionService&selectId=!{value}&type=enviroment&fdEnviromentId=${param.fdEnviromentId}",
			           "系统环境",
			           "ldingTicFindEnvronmentAndFunctionService&selectId=!{value}&type=func&fdEnviromentId=${param.fdEnviromentId}",
			           after_dialogSelect,
			           "ldingTicFindEnvronmentAndFunctionService&type=search&keyword=!{keyword}&fdEnviromentId=${param.fdEnviromentId}",
			           null,null,null,
			           "选择函数");
		}else{
    	    	Dialog_TreeList(false,"fdFuncBaseId","fdFuncBaseName",";",
 			           "ticCoreFindFunctionService&selectId=!{value}&type=cate&fdAppType=${param.fdAppType}&fdEnviromentId=${param.fdEnviromentId}",
 			           "业务分类",
 			           "ticCoreFindFunctionService&selectId=!{value}&type=func&fdAppType=${param.fdAppType}&noTrans=true&fdEnviromentId=${param.fdEnviromentId}",
 			           after_dialogSelect,
 			           "ticCoreFindFunctionService&type=search&keyword=!{keyword}&fdAppType=${param.fdAppType}&noTrans=true&fdEnviromentId=${param.fdEnviromentId}",
 			           null,null,null,
 			           "选择函数");
    	 }
      }
    
    var trs =new Array();
    window.can_submit;
    function genParaInHtml( obj,  parentId){
    	var name = obj.name;
    	var title = obj.title;
    	var id = name;
    	if(parentId){
    		id = parentId+"-"+name;
    	}
    	var tr = {};
    	//判断是否有children,做个标识用于页面展示去除填值信息显示
    	if(obj.children)
    	{
    		tr.child = 1;
    	}
    	//用于编辑页面时数据回显
    	if(obj.display_value)
    	{
    		tr.display_value = obj.display_value;
    		tr.hidden_value = obj.hidden_value;
    	}
    	
    	tr.id = id.replace(":","_");
    	if(tr.id){
			var id=tr.id;
			id=id.replace(/\|/g, '');  
			tr.id=id; 
		}
    	tr.name = name;
    	tr.title = title;
    	tr.parentId = parentId;
    	trs.push(tr);
    	var children = obj.children;
    	if(children){
    		$.each(children, function(idx2, obj2) {
    			genParaInHtml(obj2,tr.id);
    		});
    	}
    }
    
    function checkDingpass(){
    	<%--
    	<c:if test="${'false' == isLding}">
			$("input[name='fdSource'][value='2']").parent().hide();
		</c:if>
		--%>
		$("input[name='fdSource'][value='2']").parent().hide();
		var ldingCommonDataFormatId = '${ldingCommonDataFormatId}';
		//var fdDataSourceFormatId = '${ticCoreTransSettForm.fdDataSourceFormatId}';
		if('${isLdingTrue}'=='true'){
			$("input[name='fdSource'][value='2']").parent().show();
			$("input[name='fdSource'][value='2']").attr("checked",true);
			$("input[name='fdSource']").attr("disabled", true);
			$("#thirdFunc_onload_btn").hide();
			$("#thirdFunc_update_btn").hide();
			$("#dingpass_params").show();
		}
    }
    
    $(function(){
    	checkDingpass();
    	
    	$("#fdTransTypeIn").change(function(){
    		var typeValue = $("#fdTransTypeIn").val();
    		if(typeValue == 1)
    		{
    			$("#table_paraIn .td_img").show();
    			$("#is_all_trans_in").hide();
    		}else if(typeValue == 2)
    		{
    			$("#table_paraIn .td_img").hide();
    			$("#is_all_trans_in").show();
    		}
    	});
    	$("#fdTransTypeOut").change(function(){
    		var typeValue = $("#fdTransTypeOut").val();
    		if(typeValue == 1)
    		{
    			$("#table_paraOut_trans .a_edit").show();
    			$("#is_all_trans_out").hide();
    		}else if(typeValue == 2)
    		{
    			$("#table_paraOut_trans .a_edit").hide();
    			$("#is_all_trans_out").show();
    		}
    	});
    	
    	//测试数据
    	init();
    	
    	
    	$("#is_all_trans_in_img").click(function(){
    		mapping('all_trans_display_in','all_trans_hidden_in','in');
    	});
    	
    	$("#is_all_trans_out_img").click(function(){
			mapping('all_trans_display_out','all_trans_hidden_out','out');
    	});
    	
    	buildBusinessException();
    	
    	updateCache();
    });
    
    
    function init(){
    	//debugger;
    	var fdTransSettStr= '${ticCoreTransSettForm.fdTransSett }';
    	if(isBase64(fdTransSettStr)){
    		fdTransSettStr = Base64.decode(fdTransSettStr);
		}
    	var fdTransSettJson = null;
    	if(fdTransSettStr && fdTransSettStr!=''){
    		fdTransSettStr = fdTransSettStr.replace(/\n/g,"\\\\n");
    		//console.info(fdTransSettStr);
    		fdTransSettJson = JSON.parse(fdTransSettStr);
    	}
    	
    	if(fdTransSettStr)
    	{
    		initTransFunc(fdTransSettJson);
    		initSourceFunc(fdTransSettJson);
    	}
    }
    
    function fdDataSourceFormatIdChange(fdId){
    	if(fdId){
    		$.ajax({
    			type: "POST",
    		   	url: Com_Parameter.ContextPath+"lding/common/lding_common_data_format/ldingCommonDataFormat.do?method=queryLdingCommonDataFormat",
    		   	data: "fdId="+fdId,
    		   	success: function(data){
    		   	   if(data && data.tranc){
    		   		 clearField('in');
    	    		 clearField('out');
    		   		 initTransFunc(data.tranc);
    		   		 initSourceFunc(data.tranc);
    		   	   }
    		   	}
    		});			 
    	}else{
    		clearField('in');
    		clearField('out');
    	}
    }
    
    function isBase64(val){
    	var base64Pattern =new RegExp("^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)$");
    	return base64Pattern.test(val);
    }
    
    function genFieldHtml(field,inOrOut,parentId,hierId){
    	//debugger;
    	var name = field.name;
		var label = field.title;
		var type = field.type;
		var isSearch = field.isSearch;
		var display_value = field.display_value;
		var hidden_value = field.hidden_value;
		if(display_value){
			if(isBase64(display_value)){
				display_value = Base64.decode(display_value);
				hidden_value = Base64.decode(hidden_value);
			}
			display_value =display_value.replace(/\\n/g,"\n");
			hidden_value = hidden_value.replace(/\\n/g,"\n");
		}
		
		//debugger;
		addFieldFromForm(inOrOut,parentId,name,label,type,display_value,hidden_value,hierId,isSearch);
		var children = field.children;
		if(children){
			if(hierId){
				hierId = hierId+","+name;
			}else{
				hierId = name;
			}
			if(children.length>0){
				for(var i=children.length-1;i>=0;i--){
					genFieldHtml(children[i],inOrOut,name,hierId);
				}
			}
		}
    }
    
    function initTransFunc(fdTransSettJson,inOrOut){
    	var transFunc = fdTransSettJson.transFunc;
		var importinfo = transFunc.transIn;
		var exportinfo = transFunc.transOut;
		
		if(transFunc.trans_info){
			var trans_info = transFunc.trans_info;
			$("#fdTransTypeOut").val(trans_info.trans_type);
			$($("#is_all_trans_out").find("textarea")[0]).val(trans_info.display_value.replace(/\\n/g,"\n"));
	    	$($("#is_all_trans_out").find("textarea")[1]).val(trans_info.hidden_value.replace(/\\n/g,"\n"));
	    	if(trans_info.trans_type == 1)
			{
				$("#is_all_trans_out").hide();
			}else if(trans_info.trans_type == 2)
			{
				$("#is_all_trans_out").show();
			}
		}
    	
    	for(var i=0;i<importinfo.length;i++){
    		genFieldHtml(importinfo[i],"in",null);
    	}
    	
    	for(var i=0;i<exportinfo.length;i++){
    		genFieldHtml(exportinfo[i],"out",null);
    	}
		
		$("#table_paraIn_trans").treeTable({ initialState: true,indent:15 }).expandAll();
		
		//如果是整体转换,字段的公式定义器隐藏
		/* if($("#fdTransTypeIn").val() == 2)
		{
			$(".td_img").hide();
		} */
		
		$("#table_paraOut_trans").treeTable({ initialState: true,indent:15 }).expandAll();
    }
    var importArray;
    function initSourceFunc(fdTransSettJson){
    	var sourceFunc = fdTransSettJson.sourceFunc;
    	if(!sourceFunc){
    		return;
    	}
		var importinfo = sourceFunc.import;
		var exportinfo = sourceFunc.export;
		
	    inParamJson = importinfo;
	    outParamJson = exportinfo;
		
		var trans_info = sourceFunc.trans_info;
		$("#fdTransTypeIn").val(trans_info.trans_type);
		var display_value = trans_info.display_value;
		var hidden_value = trans_info.hidden_value;
		if(display_value){
			display_value = display_value.replace(/\\n/g,"\n");
			hidden_value = hidden_value.replace(/\\n/g,"\n");
		}
		
		
		$($("#is_all_trans_in").find("textarea")[0]).val(display_value);
    	$($("#is_all_trans_in").find("textarea")[1]).val(hidden_value);
    	if(trans_info.trans_type == 1)
		{
			$("#is_all_trans_in").hide();
		}else if(trans_info.trans_type == 2)
		{
			$("#is_all_trans_in").show();
		}
    	trs = new Array();
    	$.each(importinfo, function(idx, obj) {
    		genParaInHtml(obj,"");
   	   });
		buildParaInTable(trs);
		
		//如果是整体转换,字段的公式定义器隐藏
		if($("#fdTransTypeIn").val() == 2)
		{
			$(".td_img").hide();
		}
		
		trs = new Array();
		$.each(exportinfo, function(idx, obj) {
			genParaInHtml(obj,"");
		});
		buildParaOutTable(trs);
    }
    function buildParaInTable(trs){
    	for(var i=0;i<trs.length;i++){
			var tr = trs[i];
			var tr_new;
			if(tr.id){
				var id=tr.id;
				id=id.replace(/\|/g, '');  
				tr.id=id; 
			}
			if(tr.child)//判断如果有子节点不显示填值框
			{
				tr_new = $("<tr id='"+tr.id+"'><td>"+tr.name+"</td><td>"+tr.title+"</td><td colspan='2'></td></tr>");
			}else
			{
				tr_new = $("<tr id='"+tr.id+"'><td>"+tr.name+"</td><td>"+tr.title+"</td><td><textarea style='width: 180px;height: 25px' readonly='readonly' name='"+tr.id+"_input'></textarea><textarea style='display:none;' name='"+tr.id+"_hidden'></textarea></td><td align='center'><img id='"+tr.id+"_img' class='td_img' src='${KMSS_Parameter_StylePath}icons/edit.gif' border='0'></td></tr>");
			}
			
			if(tr.parentId){
				tr_new.addClass("child-of-"+tr.parentId);
			}
			$("#table_paraIn").append(tr_new);
			
			
			if(tr.display_value){
				//debug
				var display_value=tr.display_value;
				var hidden_value=tr.hidden_value;
				if(isBase64(display_value)){
					display_value=Base64.decode(display_value);
					hidden_value=Base64.decode(hidden_value);
				}
				$($("#"+tr.id).find('textarea')[0]).val(display_value.replace(/\\n/g,"\n"));
	        	$($("#"+tr.id).find('textarea')[1]).val(hidden_value.replace(/\\n/g,"\n"));
			}
		}
		$("#table_paraIn").treeTable({ initialState: true,indent:15 }).expandAll();
		//给公式定义器图片添加click事件,弹出公式定期器
		addFormulaDefinerEvent();
    }
    function buildParaOutTable(trs){
		for(i=0;i<trs.length;i++){
			var tr = trs[i];
			if(tr.id){
				var id=tr.id;
				id=id.replace(/\|/g, '');  
				tr.id=id; 
			}
			var tr_new = $("<tr id='"+tr.id+"'><td>"+tr.name+"</td><td>"+tr.title+"</td>"+"</tr>");
			if(tr.parentId){
				tr_new.addClass("child-of-"+tr.parentId);
			}
			$("#table_paraOut").append(tr_new);
			
		}
		$("#table_paraOut").treeTable({ initialState: true,indent:15 }).expandAll();
		//构建业务异常参数下拉框值
		//buildBusinessExceptionParamSelect(trs);
    }
    
    function buildBusinessException(){
    	var trs = $("#table_paraOut_trans").find("tr");
    	buildBusinessExceptionParamSelect(trs);
    }
    
    function getParent(tr){
   	    var classNames = $(tr).attr('class').split(' ');
   	    for(var key=0; key<classNames.length; key++) {
   	      if(classNames[key].match('child-of-')) {
   	        return $(tr).siblings("#" + classNames[key].substring(9));
   	      }
   	    }
   	    return null;
    }
    
    function getOptionValue(tr, nameArray){
    	//debugger;
    	while(tr){
    		if(nameArray.length==0){
    			nameArray[0] = $(tr).find("input").get(0).value;
    		}else{
    			nameArray[0] = $(tr).find("input").get(0).value+"."+nameArray[0];
    		}
    		tr = getParent(tr);
    	}
    }
    
    //构建业务异常参数下拉框值
    function buildBusinessExceptionParamSelect(trs){
    	//debugger;
    	//var parentMap = new Map();
    	//var childArray = new Array();
    	//var index = 0;
    	var options=[];
    	options.push("<option value ='' >${lfn:message('tic-core-common:ticCoreCommon.pleaseSelect')}</option>");
    	for(i=1;i<trs.length;i++){
    		var tr = trs[i];
    		var nameStr = "";
    		var nameArray = [];
    		getOptionValue(tr,nameArray);
    		var option = "<option value ='" +nameArray[0]+ "' >"
			+nameArray[0] + "</option>";
			options.push(option);
    	}
    	
    	
    	//设置选项前置空
    	$("#businessExceptionParamSelect").empty();
    	$("#businessExceptionParamSelect").append(options);
    	var fdReturnValue='${ticCoreTransSettForm.fdReturnValue}';
    	if(fdReturnValue){
    		$("#businessExceptionParamSelect option[value='${ticCoreTransSettForm.fdReturnValue}']").attr("selected","selected");
    	}
    }
    
    //递归获取父类名称并用.拼接
    function recursionGetParentName(nameStr,parentId,parentMap){
    	var parent = parentMap.get(parentId);
    	var parentName = parent.name;
    	if(parentName.indexOf(":")>0){
    		parentName = parentName.split(":")[1];
    	}
    	nameStr = parentName + "." + nameStr;
    	if(parent.parentId){
    		return recursionGetParentName(nameStr,parent.parentId,parentMap);
    	}else{
    		return nameStr;
    	}
    }
    
    
    function getImportArray(obj,name){
    	var children =obj.children;
    	   if(name!="")
    	     obj.name=name+ "-"+obj.name;
    	if(children){
    	   $.each(children, function(index,child) {  
    			getImportArray(child,obj.name); 
    		});
    	}else{
    		 importArray.push(obj);
    	}
    }
    function mapping(nameField, idField, type){
    	var varInfo = new Array();
    	if(type=="in"){
    		varInfo = genXformVarInfo($('#table_paraIn_trans'));
    		$('#table_paraIn_trans').find('input[name=name]').each(function(){
    			if($(this).val().indexOf(".")>-1){
    				alert("参数名称"+$(this).val()+"不能带'.'");
    			}
    		});
     		$('#table_paraIn_trans').find('input[name=title]').each(function(){
    			if($(this).val().indexOf(".")>-1){
    				alert("参数说明"+$(this).val()+"不能带'.'");
    			}
    		});
    	}else if(type=="out"){
    		varInfo = genXformVarInfoByJson(outParamJson);
    		/* for(var i=0;i<varInfo.length;i++){
    			var label = varInfo[i].label;
    			if(label.indexOf(":")>-1){
    				varInfo[i].label = label.substring(label.indexOf(":"));
    			}
    		} */
    	}
		//console.log(varInfo);
		Formula_Dialog_ScriptEngine(idField,nameField,varInfo,'Object',null,null);
    }
    
    //递归获取页面入参值并json化
    function recursionGetValueToJson(inParamJson,rtnParamJson,id)
    {
    	rtnParamJson.name = inParamJson.name;
		rtnParamJson.type = inParamJson.type;
		rtnParamJson.title = inParamJson.title;
    	if(inParamJson.children)
    	{
    		var childArray = [];
    		var childObj = inParamJson.children;
    		$.each(childObj, function(idx, obj) {
    			var childParamJson = {};
    			var childId = id+"-"+obj.name;
    			var o = $("#"+childId.replace(/:/g,"_"));
    			if(o.length>0){
    				recursionGetValueToJson(obj,childParamJson,childId);
        			childArray.push(childParamJson);
    			}
    			
    		});
    		rtnParamJson.children = childArray;
    	}else
    	{
    		var o = $("#"+id.replace(/:/g,"_"));
    		if(o.length==0){
    			rtnParamJson.toDel = true;
    			return;
    		}
    		//debugger;
        	var displayValue = $($("#"+id.replace(/:/g,"_")).find('textarea')[0]).val();
        	var hiddenValue = $($("#"+id.replace(/:/g,"_")).find('textarea')[1]).val();
        	try{
        		rtnParamJson.display_value = Base64.encode(displayValue);
            	rtnParamJson.hidden_value = Base64.encode(hiddenValue);
        	}catch(e){
        		console.log(id+rtnParamJson.name);
        		throw e;
        	}
        	
    	}
    }
    
    //解析页面中传入参数信息json化
    var inParamJson;
    var outParamJson;
    function getFuncInParamsToJson()
    {
    	/* if(inParamJson)
    	{ */
    		var rtnParamJsonArray = [];
        	$.each(inParamJson, function(idx, obj) {
    			var rtnParamJson = {};
    			var id = obj.name;
    			recursionGetValueToJson(obj,rtnParamJson,id);
    			rtnParamJsonArray.push(rtnParamJson);
    		});
        	var show_value = $($("#is_all_trans_in").find("textarea")[0]).val();
        	var hidden_value = $($("#is_all_trans_in").find("textarea")[1]).val();
        	//转化信息
        	var trans_info = {};
        	trans_info.trans_type = $("#fdTransTypeIn").val();
        	trans_info.display_value = show_value;
        	trans_info.hidden_value = hidden_value;
        	
        	var rtnJson = {};
        	//console.log(inParamJson);
        	//console.log(rtnParamJsonArray);
        	debugger;
        	rtnJson.import = rtnParamJsonArray;
        	rtnJson.export = outParamJson;
        	rtnJson.trans_info = trans_info;
        	var json = {};
        	json.sourceFunc = rtnJson;
        	
        	var transIn = buildParaJson($("#table_paraIn_trans"));
        	var transOut = buildParaJson($("#table_paraOut_trans"));
        	var transFunc = {};
        	transFunc.transIn = transIn;
        	transFunc.transOut = transOut;
        	var trans_info_out = {};
        	trans_info_out.trans_type = $("#fdTransTypeOut").val();
        	trans_info_out.display_value = $($("#is_all_trans_out").find("textarea")[0]).val();
        	trans_info_out.hidden_value = $($("#is_all_trans_out").find("textarea")[1]).val();
        	transFunc.trans_info = trans_info_out;
        	json.transFunc = transFunc;
        	
        	$("#fdTransSett_json").val(JSON.stringify(json));
    	//}"WebContent/tic/core/common/tic_core_trans_sett/ticCore_searchInfo_view_history.jsp"
    	
    }
    function submitExecutor(method)
    {
    	window.can_submit=true;
    	getFuncInParamsToJson();
    	$.each($("#table_paraIn_trans tr"),function(i,e){
    		   var id=$(e).attr("id");
    		   if(id){
    			   id=id.replace(new RegExp("/","g"), "\\/");
        		   $(e).attr("id",id); 
    		   }
    		});
    	$.each($("#table_paraOut_trans tr"),function(i,e){
 		   console.info($(e).attr("id"));
 		});
    	$.each($("#table_paraOut_trans tr"),function(i,e){
 		   var id=$(e).attr("id");
 		   if(id){
 			  id=id.replace(/\|/g, ''); 
     		   $(e).attr("id",id); 
 		   }
 		});
    	if(window.can_submit)
    	 Com_Submit(document.ticCoreTransSettForm, method);
    }
    
    
    function after_dialogSelect(rtn, soapVersionValue) {
    	//清空table内的html
    	$("#table_paraIn").html('<tr><td class="td_normal_title" width="27%">参数名称</td><td class="td_normal_title" width="30%">参数说明</td><td class="td_normal_title" width="36%">参数值<span><a href="javascript:void(0)" style="color:#47b5ea;" title="清空映射关系" onclick="formulaClear(\'table_paraIn\');">清空</a></span><span><a href="javascript:void(0)" style="color:#47b5ea;" onclick="formulaAdapt(\'table_paraIn\');" title="自动匹配映射关系">适配</a><span></td><td class="td_normal_title" width="7%"></td></tr>');
    	$("#table_paraOut").html('<tr><td class="td_normal_title">参数名称</td><td class="td_normal_title">参数说明</td></tr>');

    	if (!rtn) {
    		return;
    	}

    	var data = rtn.GetHashMapArray();
    	if (data && data.length > 0) {
    		var funcType = data[0]["type"];
    		if(funcType=="1"){funcType="SAP"}
    		else if(funcType=="3"){funcType="SOAP"}
    		else if(funcType=="5"){funcType="REST"}
    		else if(funcType=="4"){funcType="JDBC"}
    		$("#functype").text(funcType);
    		var info = data[0]["para"];
    		var infoObj = JSON.parse(info);
    		var paraIn = infoObj.paraIn;
    		//用于提交时获取函数入参数据
    		inParamJson = paraIn;
    		trs = new Array();
    		$.each(paraIn, function(idx, obj) {
    			genParaInHtml(obj,"");
    		});
    		for(i=0;i<trs.length;i++){
    			var tr = trs[i];
    			if(tr.id){
					var id=tr.id;
					id=id.replace(/\|/g, '');  
					tr.id=id; 
				}
				//var tr_new = $("<tr id='"+tr.id+"'><td>"+tr.name+"</td><td>"+tr.title+"</td><td><input type='text'></input></td><td><img src='${KMSS_Parameter_StylePath}icons/edit.gif' border='0' /></td>"+"</tr>");
				var tr_new;
    			if(tr.child)//判断如果有子节点不显示填值框
    			{
    				tr_new = $("<tr id='"+tr.id+"'><td>"+tr.name+"</td><td>"+tr.title+"</td><td colspan='2'></td></tr>");
    			}else
    			{
    				tr_new = $("<tr id='"+tr.id+"'><td>"+tr.name+"</td><td>"+tr.title+"</td><td><textarea style='width: 180px;height: 25px' readonly='readonly' name='"+tr.id+"_input'></textarea><textarea style='display:none;' name='"+tr.id+"_hidden'></textarea></td><td align='center'><img id='"+tr.id+"_img' class='td_img' src='${KMSS_Parameter_StylePath}icons/edit.gif' border='0'></td></tr>");
    			}
				
				if(tr.parentId){
    				tr_new.addClass("child-of-"+tr.parentId);
    			}
    			$("#table_paraIn").append(tr_new);
    			
    		}
    		$("#table_paraIn").treeTable({ initialState: true,indent:15 }).expandAll();
    		
    		//给公式定义器图片添加click事件,弹出公式定期器
    		addFormulaDefinerEvent();
    		
    		//如果是整体转换,字段的公式定义器隐藏
    		if($("#fdTransTypeIn").val() == 2)
    		{
    			$("#table_paraIn .td_img").hide();
    		}
    		
    		var paraOut = infoObj.paraOut;
    		outParamJson = paraOut;
    		trs = new Array();
    		$.each(paraOut, function(idx, obj) {
    			genParaInHtml(obj,"");
    		});
    		for(i=0;i<trs.length;i++){
    			var tr = trs[i];
    			if(tr.id){
					var id=tr.id;
					id=id.replace(/\|/g, '');  
					tr.id=id; 
				}
				var tr_new = $("<tr id='"+tr.id+"'><td>"+tr.name+"</td><td>"+tr.title+"</td>"+"</tr>");
				if(tr.parentId){
    				tr_new.addClass("child-of-"+tr.parentId);
    			}
    			$("#table_paraOut").append(tr_new);
    			
    		}
    		$("#table_paraOut").treeTable({ initialState: true,indent:15 }).expandAll();
    		
    	}
    }
    
  	//给公式定义器图片添加click事件,弹出公式定期器
    function addFormulaDefinerEvent()
    {
		$("#table_paraIn").find("img").click(function(){
			var imgId = $(this).attr("id");
			var id = imgId.substring(0,imgId.length-4);
			mapping(id+'_input',id+'_hidden','in');
		});
    }
    
    
    function selectForm(){
    	//debugger;
    	//alert($("select[name='fdModelName']").val());
    	var mainModelName = $("select[name='fdModelName'] option:selected").val();
    	if(!mainModelName || mainModelName==null){
    		alert("请先选择表单模块");
    		return;
    	}
    	var register = getRegister(mainModelName);
    	seajs.use(['lui/jquery','lui/dialog','lui/topic'], function($,dialog,topic) {
    		if("global"==register.templateType){
    			dialog.categoryForNewFile(
    					register.templateModelName,
    					null,false,null,function(rtn) {
    						//console.log(rtn);
    						if(rtn){
    							$("#fdTemplateName").val(rtn.name);
    							$("#fdModelId").val(rtn.id);
    						}
    					},'',null,null,true);
    		}else if("simple"==register.templateType){
    				
    			dialog.simpleCategoryForNewFile(
    					//register.templateModelName,
    					"com.landray.kmss.km.doc.model.KmDocTemplate",
    					null,false,function(rtn) {
    						//console.log(rtn);
    						if(rtn){
    							$("#fdTemplateName").val(decodeURIComponent(rtn.name));
    							$("#fdModelId").val(rtn.id);
    						}
    					},'');
    		}
    	}
    	);
    }
    
    var field_id = 0;
    
    function addField(type,parent){
    	var val=$(parent).parent().parent().find('[name="type"]').val()||"object";
    	var tableObj;
    	if(type=='in'){
    		tableObj = $('#table_paraIn_trans');
    	}else{
    		tableObj = $('#table_paraOut_trans');
    	}
    	tableObj.removeClass("treeTable").find("tbody tr").each(function() {
            $(this).removeClass('initialized');
    	});
    	var id = field_id++;
		if(parent){
			id = $(parent).parent().parent().attr("id") + "-" + id;
		}else{
			id = "para"+id;
		}
		var a_edit = "";
		var td_value = "<td><input type='checkbox' name='isSearch'/></td>";
		
		if(type=="out"){
			td_value += "<td><textarea style=\"width: 180px;height: 25px\" readonly=\"readonly\" name='"+id+"_input' class='para_value'></textarea><textarea style='display:none;' name='"+id+"_hidden' class='para_value_hidden'></textarea></td>";
			a_edit = "<img  src=\"${KMSS_Parameter_StylePath}icons/edit.gif\" onClick=\"mapping('"+id+"_input','"+id+"_hidden','out');\" class='a_edit' title='编辑'/>";
		}
    	var tr_new = $("<tr id='"+id+"'><td><input  type='text' name='name' validate='required isSame("+type+")' class='inputsgl'></input></td><td><input type='text' name='title' validate='required' class='inputsgl'></input></td><td><select name='type'><option value='string'>字符串</option>"+
    	"<option value='boolean'>布尔</option><option value='int'>整型</option><option value='long'>长整型</option><option value='double'>浮点</option>"+
    	"<option value='object'>对象</option><option value='arrayObject'>对象数组</option><option value='arrayInt'>整型数组</option>"+
    	"<option value='arrayDouble'>浮点数组</option><option value='arrayBoolean'>布尔数组</option><option value='arrayString'>字符串数组</option>"+
    	"</select></td>"+td_value+"<td align='center'> <img  src=\"${KMSS_Parameter_StylePath}icons/delete.gif\" title=\"删除\" onClick=\"deleteField(this);\"/> "+a_edit+"</td>"+"</tr>");
    	
    	tr_new.find("select").on("change",function(){
    		var selectType=$(this).val();
    		var img_td=$(this).parent().parent().find("img").parent();
    		img_td.find('#img_add').remove();
    		if(selectType=="object"||selectType=="arrayObject"){
    			img_td.prepend("<img id='img_add' src=\"${KMSS_Parameter_StylePath}icons/add.gif\" title=\"新增\" onClick=\"addField('"+type+"',this);\"/> " );
    		}
    	});
		if(!parent){

    	}else{
    		tr_new.addClass("child-of-"+$(parent).parent().parent().attr("id"));
    	}
		if(parent){
			$(parent).parent().parent().after(tr_new);
		}else{
			tableObj.append(tr_new);
		}
    	tableObj.treeTable({ initialState: true,indent:15 }).expandAll();
    	
    	updateCache();
    }
    
  /*   function genSelectStr(valueType){
    	String select = "<secect name='type'>";
    	var types = ["string","boolean","int","long","double","object","array"];
    	var teypNames = ["字符串","布尔","整型","长整型","对象","数组"];
    	for(var i =0;i<types.length;i++){
    		if(types[i]==valueType){
    			select += "<option value='"+types[i]+"' s"
    		}
    	}
    	//<select name='type' value='"+valueType+"'><option value='string'>字符串</option><option value='boolean'>布尔</option><option value='int'>整型</option><option value='long'>长整型</option><option value='double'>浮点</option><option value='object'>对象</option><option value='array'>数组</option></select>
    } */
    
    function addFieldFromForm(type,parentName,objName,name,valueType,display_value,hidden_value,hierId,isSearch){
    	//debugger;
    	var tableObj;
    	if(type=='in'){
    		tableObj = $('#table_paraIn_trans');
    	}else{
    		tableObj = $('#table_paraOut_trans');
    	}
    	tableObj.removeClass("treeTable").find("tbody tr").each(function() {
            $(this).removeClass('initialized');
    	});
    	//console.log(objName+"==="+tableObj.html());
    	var tr_id = "";
    	/* var id = field_id++;
		if(parentId){
			tr_id = "paraIn"+ parentId + "-" + objId;
		}else{
			tr_id = "paraIn"+objId;
		} */
		var id = field_id++;
		var parent;
		if(parentName){
			parent = findInputByValue(type, parentName,hierId);
			tr_id = $(parent).parent().parent().attr("id") + "-" +objName+ id;
		}else{
			tr_id = "para"+objName+"-"+id;
		}
		var td_value = "";
		var a_edit = "";
		var img_add="";
		var input_search = "";
		if(valueType && valueType!="array" && valueType!="arrayObject"&& valueType!="arrayInt"&& valueType!="arrayDouble"&& valueType!="arrayBoolean"&& valueType!="arrayString"&& valueType!="object"){
			input_search = "<input type='checkbox' name='isSearch'/>";
		}
		if(type=="out"){
			var display_value_str = "";
			var hidden_value_str = "";
			if(display_value){
				display_value_str = display_value;
				hidden_value_str = hidden_value;
			}
			td_value = "<td>"+input_search+"</td><td><textarea style=\"width: 180px;height: 25px\" readonly=\"readonly\" name='"+id+"_input' class='para_value'>"+display_value_str+"</textarea><textarea style='display:none;' name='"+id+"_hidden' class='para_value_hidden'>"+hidden_value_str+"</textarea></td>";
			a_edit = " <img  src=\"${KMSS_Parameter_StylePath}icons/edit.gif\" onClick=\"mapping('"+id+"_input','"+id+"_hidden','out');\" class='a_edit' title='编辑'/>";
		}else{
			td_value = "<td>"+input_search+"</td>";
		}

		if(valueType=="object"|| valueType=="arrayObject"){
			img_add="<img id='img_add' src=\"${KMSS_Parameter_StylePath}icons/add.gif\" title=\"新增\" onClick=\"addField('"+type+"',this);\"/> ";
		}
		if(tr_id){
			tr_id=tr_id.replace(/\|/g, '');  
		}
    	var tr_new = $("<tr id='"+tr_id+"'><td><input type='text' name='name' value='"+objName+"' validate='required isSame("+type+")' class='inputsgl'></input></td><td><input type='text' name='title' value='"+name+"' validate='required' class='inputsgl'></input></td><td><select name='type'>"+
    	"<option value='string'>字符串</option><option value='boolean'>布尔</option><option value='int'>整型</option><option value='long'>长整型</option><option value='double'>浮点型</option>"+
    	"<option value='object'>对象</option><option value='arrayObject'>对象数组</option><option value='arrayInt'>整型数组</option>"+
    	"<option value='arrayDouble'>浮点数组</option><option value='arrayBoolean'>布尔数组</option><option value='arrayString'>字符串数组</option>"+
    	"</select></td>"+td_value+"<td align='center'>"+img_add+"<img  src=\"${KMSS_Parameter_StylePath}icons/delete.gif\" title=\"删除\" onClick=\"deleteField(this);\"/> "+a_edit+"</td>"+"</tr>");
    	tr_new.find("select").on("change",function(){
    		var selectType=$(this).val();
    		var img_td=$(this).parent().parent().find("img").parent();
    		var img_add="<img id='img_add'  src=\"${KMSS_Parameter_StylePath}icons/add.gif\" title=\"新增\" onClick=\"addField('"+type+"',this);\"/> ";
    		img_td.find("#img_add").remove();
    		if( selectType=="object"|| selectType=="arrayObject"){ 			
    			img_td.prepend($(img_add));
    		}
    	});
		if(!parentName){
    		
    	}else{
    		tr_new.addClass("child-of-"+$(parent).parent().parent().attr("id"));
    	}
		//tr_new.find("option[text='pxx']").attr("selected",true);
		tr_new.find("select").val(valueType);
		if(isSearch){
			tr_new.find("input[name='isSearch']").attr('checked',isSearch); 
		}
		if(parentName){
			//debugger;
			$(parent).parent().parent().after(tr_new);
		}else{
			tableObj.append(tr_new);
		}
		
    	//tableObj.treeTable({ initialState: true,indent:15 }).expandAll();
    	
    	//console.log(objName+"----"+tableObj.html());
    }
    
    
    function clearField(type){
    	if(type=='in'){
    		tableObj = $('#table_paraIn_trans');
    	}else{
    		tableObj = $('#table_paraOut_trans');
    	}
    	tableObj.find("tbody tr").each(function(index) {
    		if(index!=0){
    			$(this).remove();
    		}
            
    	});
    }
    
    function clearField(type){
    	if(type=='in'){
    		tableObj = $('#table_paraIn_trans');
    	}else{
    		tableObj = $('#table_paraOut_trans');
    	}
    	tableObj.find("tbody tr").each(function(index) {
    		if(index!=0){
    			$(this).remove();
    		}
            
    	});
    }
    
    function deleteField(obj){
    	var id = $(obj).parent().parent().attr("id");
    	id = id.replace(new RegExp("/","g"), "\\/");
    	$(obj).parent().parent().siblings("tr.child-of-" + id).each(function(index) {
    			$(this).remove();
            
    	});
    	$(obj).parent().parent().remove();
    }
    
    function importInFields(){
    	var fdModelName = $("select[name='fdModelName'] option:selected").val();
    	var fdModelId = $("input[name='fdModelId']").val();
    	if(!fdModelName || !fdModelId){
    		alert("请先选择表单模板");
    		return;
    	}
    	var register = getRegister(fdModelName);
    	var serviceUrl = "ticCommonFormTreeService&templateModelName="+register.templateModelName+"&templateId="+fdModelId+"&modelName="+fdModelName;
    	Dialog_List(true, "importInFieldIds", "importInFieldNames", null, serviceUrl,afterInFieldsSelect,null,null,null,
		"");
    }
    
    function importOutFields(){
    	var fdModelName = $("select[name='fdModelName'] option:selected").val();
    	var fdModelId = $("input[name='fdModelId']").val();
    	if(!fdModelName || !fdModelId){
    		alert("请先选择表单模板");
    		return;
    	}
    	var register = getRegister(fdModelName);
    	var serviceUrl = "ticCommonFormTreeService&templateModelName="+register.templateModelName+"&templateId="+fdModelId+"&modelName="+fdModelName;
    	Dialog_List(true, "importOutFieldIds", "importOutFieldNames", null, serviceUrl,afterOutFieldsSelect,null,null,null,
		"");
    }
    
    function isArrayContains(array,value){
    	for(var i=0;i<array.length;i++) {
    	    if(value === array[i].id) {
				return true;
    	    }
    	}
    	return false;
    }
    
    function findRootField(type,name){
    	var tableObj;
    	if(type=='in'){
    		tableObj = $('#table_paraIn_trans');
    	}else{
    		tableObj = $('#table_paraOut_trans');
    	}
    	var inputs = tableObj.find("input[name='name']");
       	for(var i=0;i<inputs.length;i++){
       		if($(inputs[i]).val()==name){
       			var classes = $(inputs[i]).parent().parent().attr("class");
       			if(classes){
       				if(classes.indexOf("child-of-")<0)
       					return inputs[i];
       			}else{
       				return inputs[i];
       			}
       		}
       	}
    }
    
    function findNextByValue(input,name){
    	var trs_ = getChildrenOf($(input).parent().parent());
		if(trs_.length==0){
			return;
		}
		for(var i=0;i<trs_.length;i++){
			var input_name = $(trs_[i]).find("input[name='name']");
			if(name==input_name.val()){
				return input_name;
			}
		}
		return;
    }
    
    
    function findInputByValue(type,parentName,hierId){
    	//debugger;
    	var tableObj;
    	if(type=='in'){
    		tableObj = $('#table_paraIn_trans');
    	}else{
    		tableObj = $('#table_paraOut_trans');
    	}
    	var result = false;
    	
    	if(hierId){
    		var ids = hierId.split(',');
    		var input = findRootField(type,ids[0]);
    		if(ids.length==1){
    			return input;
    		}
    		for(var i=1;i<ids.length;i++){
    			input = findNextByValue(input,ids[i]);
    			if(!(input)){
    				return;
    			}
    		}
    		return input;
    	}else{
    		return findRootField(type,parentName);
    	}
    }
    
    function isNameContains(type,value,parent){
    	var tableObj;
    	if(type=='in'){
    		tableObj = $('#table_paraIn_trans');
    	}else{
    		tableObj = $('#table_paraOut_trans');
    	}
    	var result = false;
    	
    	if(parent){
    		var inputs = tableObj.find("input[name='name']");
        	for(var i=0;i<inputs.length;i++){
        		if($(inputs[i]).val()==parent){
        			var id = $(inputs[i]).parent().parent().attr("id");
        	    	id = id.replace(new RegExp("/","g"), "\\/");
        			var children = $(inputs[i]).parent().parent().siblings("tr.child-of-" + id);
        			
        			for(var i=0;i<children.length;i++){
                		if($(children[i]).find("input[name='name']").val()==value){
                			return true;
                		}
                	}
        			break;
        		}
        	}
    	}else{
    		var inputs = tableObj.find("input[name='name']");
        	for(var i=0;i<inputs.length;i++){
        		if($(inputs[i]).val()==value){
        			return true;
        		}
        	}
    	}
    	return false;    
    }
    
   
    
    function test(){
    	var obj = {};
    	obj.id = "c";
    	obj.name = "c1";
    	
    	var obj1 = {};
    	obj1.id = "a.b";
    	obj1.name = "a1.b1";
    	var array = [obj,obj1];
    	var datas = {};
    	datas.data = array;
    	afterInFieldsSelect(datas,"in");
    }
    
    function afterInFieldsSelect(returnData){
    	afterFieldsSelect(returnData,"in");
    }
    
	function afterOutFieldsSelect(returnData){
		afterFieldsSelect(returnData,"out");
    }
    
    function afterFieldsSelect(returnData,type){
    	if(!returnData){
    		return;
    	}
    	var tableObj;
    	if(type=='in'){
    		tableObj = $('#table_paraIn_trans');
    		trObj=$("#table_paraIn_trans tr:not(:first)");
    	}else{
    		tableObj = $('#table_paraOut_trans');
    		trObj=$("#table_paraOut_trans tr:not(:first)");
    	}
    	var fdModelId = $("input[name='fdModelId']").val();
    	if(!window.modelId||fdModelId!= window.modelId){
    	    window.modelId =fdModelId;
    	    trObj.remove(); 
    	}
    	var result = new Array();
    	var datas = returnData.data;
    	for(var i=0;i<datas.length;i++){
    		//debugger;
    		var id = datas[i].id;
    		var name = datas[i].name;
    		
    		if(id.indexOf(".") != -1){
    			var ids = id.split(".");
    			var names = name.split(".");
    			for(var j=0;j<ids.length;j++){
    				var parent = null;
    				if(j>0){
    					parent = ids[j-1];
    				}
    				//if(!isArrayContains(result,ids[j])){
    					
    	    			if(!isNameContains(type,ids[j],parent)){
    	    				if(j>0){
    	    					addFieldFromForm(type,ids[j-1],ids[j],names[j],"string");
    	    				}else{
    	    					addFieldFromForm(type,null,ids[j],names[j],"array");
    	    				}
    	    			}
    				//}
    			}
    		}else{
    			
    			if(!isNameContains(type,id,null)){
    				addFieldFromForm(type,null,id,name,"string");
    			}
    			
    		}
    		//console.log(id+"***"+tableObj.html());
    		
    	}
    	tableObj.treeTable({ initialState: true,indent:15 }).expandAll();
    	
    }
    
    function buildParaJson(table){
    	var paras = [];
    	table.find("tbody tr").each(function(index) {
    		  if(index>0){
    			  var isRootNode = ($(this)[0].className.search("child-of") == -1);
                  if(isRootNode) {
                	  paras.push(buildParaField($(this)));
                  }
    		  }
          });
    	return paras;
    }
    
    function buildParaField(node) {
        var tr_id = node.attr('id');
        if(tr_id){
			var id=tr_id;
			id=id.replace(/\|/g, '');  
			tr_id=id; 
		}
    	var name = node.find("input[name='name']").val();
    	var title = node.find("input[name='title']").val();
    	var type = node.find("select[name='type'] option:selected").val();
    	var para_value = node.find(".para_value").val();
    	var para_value_hidden = node.find(".para_value_hidden").val();
    	var isSearch = node.find("input[name='isSearch']");
    	//debugger;
    	var field = {};
    	field.name = name;
    	field.type = type;
    	field.title = title;
    	field.tr_id = tr_id;
    	if(isSearch && isSearch.length>0){
    		field.isSearch = isSearch.is(":checked");
    	}
    	if(para_value){
    		field.display_value = Base64.encode(para_value);
        	field.hidden_value = Base64.encode(para_value_hidden);
    	}
    	
    	var childNodes = getChildrenOf(node);
          
          if(node.hasClass("parent")) {
			var children = [];
            childNodes.each(function() {
            	children.push(buildParaField($(this)));
            });
            field.children = children;
          }
          if((field.type=="object"||field.type=="arrayObject")&&!field.children){
              alert(field.name+":对象必须有子节点");
              window.can_submit=false;       
          }
          return field;
      }
    
    function getChildrenOf(node) {
    	var id = node.attr("id");
    	id = id.replace(new RegExp("/","g"), "\\/");
    	
        return $(node).siblings("tr.child-of-" + id);
      };
      
      function genXformVarInfoByJson(fields){
    	//console.log(fields);
     	var varInfo = new Array();
     	if(!fields){
     		return varInfo;
     	}
      	 for(var i=0;i<fields.length;i++){
      		genVarInfo(fields[i],varInfo,"","");
      	 }
     	 
     	 //console.log(varInfo);
      	return varInfo;
      }
    
     function genXformVarInfo(table){
    	 
    	 var fields = buildParaJson(table);
    	 var varInfo = new Array();
     	 for(var i=0;i<fields.length;i++){
     		genVarInfo(fields[i],varInfo,"","");
     	 }
    	 
    	 //console.log(varInfo);
     	return varInfo;
     }
     
     function genVarInfo(field,varInfo,parentName,parentLabel){
    	 var m = new Object();
	 		m.businessType='inputText';
	 		var title = field.title;
	 		if(!title || title==""){
	 			title = field.name;
	 		}
	 		if(parentName && parentName!=""){
	 			m.label= parentLabel+"."+title;
	 			m.name=parentName+"."+field.name;
	 		}else{
	 			m.label= title;
	 			m.name=field.name;
	 		}
	         
	         m.len=200;
	         m.length=0;
	         m.type="String";
	        
	 		varInfo.push(m);
	 		var children = field.children;
	 		if(children){
	 			for(var i=0;i<children.length;i++){
	 	     		genVarInfo(children[i],varInfo,m.name,m.label);
	 	     	 }
	 		}
    	 
     }
function select_source(source){
	if(source.value=="0"){
		document.getElementById("thirdFunc_onload_btn").style.display = "";
		document.getElementById("lbpm_select").style.display = "none";
		document.getElementById("lbpm_params").style.display = "none";
	}else{
		document.getElementById("thirdFunc_onload_btn").style.display = "none"; 
		document.getElementById("lbpm_select").style.display = "";
		document.getElementById("lbpm_params").style.display = "";
	}
}
function  loadTicCoreTransSett(){
	var fdFuncBaseId=$('input[name="fdFuncBaseId"]')[0].value;
	var funcType=$("#functype").text();
		if(!fdFuncBaseId){
			return;
		}else{
		$.ajax({
			type: "POST",
		   	url: Com_Parameter.ContextPath+"tic/core/common/tic_core_trans_sett/ticCoreTransSett.do?method=loadTicCoreTransSett",
		   	data: "funcType="+funcType+"&fdFuncBaseId="+fdFuncBaseId,
		   	success: function(data){
		   		$("#table_paraIn tr:not(:first)").remove(); 
		   		$("#table_paraOut tr:not(:first)").remove(); 
		   		$("#table_paraIn_trans tr:not(:first)").remove(); 
		   		$("#table_paraOut_trans tr:not(:first)").remove(); 
		    	var fdTransSettJson = null;
		    	if(data && data!=''){
		    		data = data.replace(/\n/g,"\\\\n");
		    		fdTransSettJson = JSON.parse(data);
		    		//console.log(JSON.parse(data));
		    		initTransFunc(fdTransSettJson);
		    		initSourceFunc(fdTransSettJson);
		    	}
		   	}
		});			 
	}
}
function  updateTicCoreTransSett(){
	var fdFuncBaseId=$('input[name="fdFuncBaseId"]')[0].value;
		if(!fdFuncBaseId){
			return;
		}else{
			var data = new KMSSData();
			data.SendToBean("ticCoreFindFuncParaInJsonService&funcId="+fdFuncBaseId,after_updateTicCoreTransSett);
	}
}

function compare(paraIn,inParamJson){
	//debugger;
	var result = $.extend(true,[],paraIn);
	//var inParamJson = $.extend(true,[],inParamJson);
	//console.log("result");
	//console.log(result);
	//console.log("inParamJson");
	//console.log(inParamJson);
	if(result.length>0 && inParamJson.length>0){
		if(result.length==1 && inParamJson.length==1){
			updateMapping(result[0],inParamJson[0]);
		}else{
			var top_result = {};
			top_result.name = "top";
			top_result.title = "top";
			top_result.type = "arrayObject";
			top_result.children = result;
			
			var top_inParamJson = {};
			top_inParamJson.name = "top";
			top_inParamJson.title = "top";
			top_inParamJson.type = "arrayObject";
			top_inParamJson.children = inParamJson;
			
			updateMapping(top_result,top_inParamJson);
		}
	}
	return result;
}


function updateMapping(paraNew,paraOld){
	if(paraNew && paraOld){
		var name = paraNew.name;
		var title = paraNew.title;
		if(paraOld.name == name){
			if(paraNew.type=='arrayObject' || paraNew.type=='arrayInt' || paraNew.type=='arrayDouble' || paraNew.type=='arrayBoolean' || paraNew.type=='arrayString'  || paraNew.type=='object'){
				var paraNew_children = paraNew.children;
				var paraOld_children = paraOld.children;
				if(paraNew_children && paraOld_children){
					   $.each(paraNew_children, function(index,child) {
						    var child_name = child.name;
						    for(var i=0;i<paraOld_children.length;i++){
						    	var paraOld_child_name = paraOld_children[i].name;
						    	if(paraOld_child_name== child_name){
						    		updateMapping(child,paraOld_children[i]);
						    		break;
						    	}
						    }
					   });
			}
		}else{
			paraNew.display_value = paraOld.display_value;
			paraNew.hidden_value = paraOld.hidden_value;
		}
	}
	}
}


function  after_updateTicCoreTransSett(rtn){
	if (!rtn) {
		return;
	}
	var data = rtn.GetHashMapArray();
	var paraIn;
	var paraOut;
	if(data){
		//paraIn=JSON.parse(data[0]["paraIn"]);
		//paraOut=JSON.parse(data[0]["paraOut"]);
		paraIn=$.parseJSON(data[0]["paraIn"]);
		paraOut=$.parseJSON(data[0]["paraOut"]);
	}else{
		alert("获取不到函数数据");
	}
	//console.log(1);
	//console.log(paraIn);
	//console.log(inParamJson);
	//var transIn = buildParaJson($("#table_paraIn_trans"));
	var rtnParamJsonArray = [];
	$.each(inParamJson, function(idx, obj) {
		var rtnParamJson = {};
		var id = obj.name;
		recursionGetValueToJson(obj,rtnParamJson,id);
		rtnParamJsonArray.push(rtnParamJson);
	});
	var transIn = rtnParamJsonArray;
	//console.log(3);
	//console.log(transIn);
	inParamJson = compare(paraIn,transIn);
	//console.log(2);
	//console.log(inParamJson);
	outParamJson = paraOut;
	$("#table_paraIn tr:not(:first)").remove(); 
	$("#table_paraOut tr:not(:first)").remove(); 
	
	trs = new Array();
	importArray=[];
	//console.log($.extend(true,{},paraIn));
	$.each(inParamJson, function(idx, obj) {
		genParaInHtml(obj,"");
		getImportArray(obj,"");
	});
	//console.log(inParamJson);
	//console.log(paraIn);
	//var array=$.extend(true,{},inParamJson);
	//$.each( array, function(idx, obj) {
	//	getImportArray(obj,"");
	//});
	inParamJson = paraIn;
	buildParaInTable(trs);
	trs = new Array();
	$.each(paraOut, function(idx, obj) {
		genParaInHtml(obj,"");
	});
	buildParaOutTable(trs);
	$.each(importArray, function() {
	      if(isBase64(this.display_value)){
	    	  this.display_value = Base64.decode(this.display_value);
	    	  this.hidden_value = Base64.decode(this.hidden_value);
	      }
		 $("#table_paraIn").find("textarea[name='"+this.name+"_input']").val(this.display_value);
	     $("#table_paraIn").find("textarea[name='"+this.name+"_hidden']").val(this.hidden_value);
	});
}
function formulaClear(tableName){
	$('#'+tableName+' textarea').each(function(index,obj){
		$(obj).val('');
	});
	$('#'+tableName+' input:hidden').each(function(index,obj){
		$(obj).val('');
	});
}

function formulaAdaptOut(transOut,sourceExport,parentName,parentTitle){
	if(transOut && sourceExport){
		var name = transOut.name;
		var title = transOut.title;
		var tr_id = transOut.tr_id;
		if(sourceExport.name == name || sourceExport.title==title){
			if(transOut.type=='arrayObject' || transOut.type=='arrayInt' || transOut.type=='arrayDouble' || transOut.type=='arrayBoolean' || transOut.type=='arrayString'  || transOut.type=='object'){
				var transOut_children = transOut.children;
				var sourceExport_children = sourceExport.children;
				if(transOut_children){
					   $.each(transOut_children, function(index,child) {
						    var child_name = child.name;
						    var child_title = child.title;
						    for(var i=0;i<sourceExport_children.length;i++){
						    	var sourceExport_child_name = sourceExport_children[i].name;
						    	var sourceExport_child_title = sourceExport_children[i].title;
						    	if(!sourceExport_child_title || sourceExport_child_title==''){
						    		sourceExport_child_title = sourceExport_child_name;
						    	}
						    	if(sourceExport_child_name== child_name || sourceExport_child_title == child_title){
						    		
						    		formulaAdaptOut(child,sourceExport_children[i],parentName+"."+sourceExport_child_name, parentTitle+"."+sourceExport_child_title);
						    		break;
						    	}
						    }
					   });
			}
		}else{
			//findInputByValue(type, parentName,hierId);
			var value_display = "$"+parentTitle+"$";
		    var value_hidden = "$"+parentName+"$";
		    /*
			if(parentName && parentName!=''){
				value_display = "$"+parentTitle+"."+sourceExport.title+"$";
			    value_hidden = "$"+parentName+"."+sourceExport.name+"$";
			}else{
				value_display = "$"+sourceExport.title+"$";
			    value_hidden = "$"+sourceExport.name+"$";
			}*/
			
			if($("#"+tr_id).find("textarea").length>=2){
				$($("#"+tr_id).find("textarea").get(0)).val(value_display);
				$($("#"+tr_id).find("textarea").get(1)).val(value_hidden);
			}
		}
	}
	}
}

var  transInArray;
function formulaAdapt(tableName){
	//debugger;
	//console.info(outParamJson);
	getFuncInParamsToJson();
	//formulaClear(tableName);
	var fdTransSett_json=JSON.parse($("#fdTransSett_json").val());
	//console.info(fdTransSett_json);
	var sourceFunc=fdTransSett_json["sourceFunc"];
	var transFunc=fdTransSett_json["transFunc"];
	var importInfo=sourceFunc["import"];
	var exportInfo=sourceFunc["export"];
	var transIn= transFunc["transIn"];	
	var transOut = transFunc["transOut"];	
	if(tableName=='table_paraOut_trans'){
		if(exportInfo && transOut && exportInfo.length>0 && transOut.length>0){
			var exportInfo_length = exportInfo.length;
			var transOut_length = transOut.length;
			var length = exportInfo_length;
			if(length>transOut_length){
				length = transOut_length;
			}
			for(i=0;i<length;i++){
				formulaAdaptOut(transOut[i],exportInfo[i],exportInfo[i].name,exportInfo[i].title);
			}
		}
		return ;
	}
	 transInArray=[];
	 transInTitleArray=[];
	 transInJson={};
	 importInfoArray=[];
	$.each(transIn, function(idx, obj) {
		 getNameArray(obj,"","transIn");
		 getTitleArray(obj,"");
	});
	for(var i=0;i< transInArray.length;i++){
		 transInJson[transInArray[i]]= transInTitleArray[i];
	}
	$.each(importInfo, function(idx, obj) {
		 getNameArray(obj,"","import");
	});
	 for(var i=0;i< importInfoArray.length;i++){
		 var name=importInfoArray[i].split(".");
		 var inputName=importInfoArray[i].replace(new RegExp(/(:)/g),"_").replace(new RegExp(/\./g),"-")+"_input";
		 var hiddenName=importInfoArray[i].replace(new RegExp(/(:)/g),"_").replace(new RegExp(/\./g),"-")+"_hidden";
		 var nameArray=[];
		 for(var j=0;j< name.length;j++){
		    if(name[j].indexOf(":")>0)
			   name[j]=name[j].substring(name[j].indexOf(":")+1);
		    nameArray.push(name[j]);
		 }
		 name=nameArray.join(".");
		 transInArray.find(function(value) {
			 if(value ==name) {
			      value="$"+transInJson[value]+"$";
			      name="$"+name+"$";
			      $("#table_paraIn").find("textarea[name="+inputName+"]").val(value);
			      $("#table_paraIn").find("textarea[name="+hiddenName+"]").val(name);
			 }
		})
	 }
}
function getNameArray(obj,name,jsonName){
	var children =obj.children;
	   if(name!="")
	     obj.name=name+ "."+obj.name;
	if(children){
	   $.each(children, function(index,child) {  
			getNameArray(child,obj.name,jsonName); 
		});
	}else{
		if(jsonName=="transIn")
		   transInArray.push(obj.name);
		else
		   importInfoArray.push(obj.name);
	}
}
function getTitleArray(obj,title){
	var children =obj.children;
	   if(title!="")
	     obj.title=title+ "."+obj.title;
	if(children){
	   $.each(children, function(index,child) {  
			getTitleArray(child,obj.title); 
		});
	}else{
		   transInTitleArray.push(obj.title);
	}
}

function updateCache(){
	var cache = $("input[name='fdCache']:checked").val();
	if("true"==cache){
		$("#table_paraIn_trans").find("input[name='isSearch']").hide();
		$("#table_paraOut_trans").find("input[name='isSearch']").show();
	}else if("false"==cache){
		$("#table_paraIn_trans").find("input[name='isSearch']").show();
		$("#table_paraOut_trans").find("input[name='isSearch']").hide();
	}else{
		$("#table_paraIn_trans").find("input[name='isSearch']").hide();
		$("#table_paraOut_trans").find("input[name='isSearch']").hide();
	}
}

</script>
    </template:replace>

    <template:replace name="title">
        <c:choose>
            <c:when test="${ticCoreTransSettForm.method_GET == 'add' }">
                <c:out value="${ lfn:message('operation.create') } - ${ lfn:message('tic-core:table.ticCoreTrans') }" />
            </c:when>
            <c:otherwise>
                <c:out value="${ticCoreTransSettForm.fdName} - " />
                <c:out value="${ lfn:message('tic-core:table.ticCoreTransSett') }" />
            </c:otherwise>
        </c:choose>
    </template:replace>
    <template:replace name="toolbar">
        <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
        <ui:button text="${lfn:message('home.help')}" order="2" 
				onclick="window.open(Com_Parameter.ContextPath+ 'tic/core/common/help/transFunc_setting.html');">
		</ui:button>
        <c:choose>
            <c:when test="${ticCoreTransSettForm.method_GET=='edit'}">
                <ui:button text="${ lfn:message('button.update') }" order="2" 
					onclick="submitExecutor('update');">
				</ui:button>
            </c:when>
            <c:when test="${ticCoreTransSettForm.method_GET=='add'}">
                <ui:button text="${ lfn:message('button.save') }" order="2" 
					onclick="submitExecutor('save');">
				</ui:button>
            </c:when>
        </c:choose>
           <ui:button text="${ lfn:message('button.close') }" order="2" 
				onclick="Com_CloseWindow();">
			</ui:button>
        </ui:toolbar>
    </template:replace>
    <template:replace name="path">
        <ui:menu layout="sys.ui.menu.nav">
            <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" />
            <ui:menu-item text="${ lfn:message('tic-core-common:table.ticCoreTransSett') }" />
        </ui:menu>
    </template:replace>
    <template:replace name="content">
       <html:form action="/tic/core/common/tic_core_trans_sett/ticCoreTransSett.do">
    <p class="txttitle">${ lfn:message('tic-core-common:table.ticCoreTransSett') }</p>
    <center>

        <div style="width:100%;">
            <table class="tb_normal" width="100%">
            	<tr>
                    <td class="td_normal_title" width="15%">
                    ${lfn:message('tic-core-common:ticCoreCommon.transFuncName')}
                    </td>
                    <td width="35%">
                        <xform:text
						property="fdName" style="width:85%" />
                    </td>
                    <td class="td_normal_title" width="15%">
                    ${lfn:message('tic-core-common:ticCoreTransSett.transFuncCategory')}
                    </td>
                    <td width="35%">
					<html:hidden property="fdCategoryId" /> <xform:text
						property="fdCategoryName" style="width:34%" /> <a
						href="#" 
						onclick="Dialog_Tree(false, 'fdCategoryId', 'fdCategoryName', ',', 'ticCoreBusiCateTreeService&parentId=!{value}&fdAppType=${JsParam.fdAppType}', 
			'<bean:message key="table.ticCoreTransSett" bundle="tic-core-common"/>', null, null, 
			'${ticCoreBusiCateForm.fdId}', null, null, '业务分类');">
					<bean:message key="dialog.selectOther" /> </a>
					</td>
                </tr> 
                <tr>     
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('tic-core-common:ticCoreFuncBase.fdIsAvailable')}
                    </td>
                    <td width="85%" colspan="3">
                        <%-- 是否默认--%>
                            <div id="_xform_fdIsAvailable" _xform_type="radio">
                                <xform:radio property="fdIsAvailable">
						            <xform:enumsDataSource enumsType="sap_yesno" />
					          </xform:radio>
                            </div>
                           <input type="hidden" name="fdIsDefault" value="false"> 
                    </td>
                 </tr>
                 <tr>
                  <td class="td_normal_title" width="15%">
                        ${lfn:message('tic-core-common:ticCoreFuncBase.fdCache')}
                    </td>
                  <td width="85%" colspan="3">
                            <div id="_xform_fdCache" _xform_type="radio">
                                <xform:radio property="fdCache" onValueChange="updateCache()">
						            <xform:enumsDataSource enumsType="sap_yesno" />
					          </xform:radio>
                            </div>
                           ${lfn:message('tic-core-common:ticCoreFuncBase.fdCache.tip')}
                    </td>
                </tr>          
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('tic-core-common:ticCoreTransSett.fdTransDesc')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <%-- 转换描述--%>
                            <div id="_xform_fdTransDesc" _xform_type="textarea">
                                <xform:textarea property="fdTransDesc" showStatus="edit" style="width:95%;" />
                            </div>
                    </td>
                </tr>
            </table>
            <br/>
             <table class="tb_normal" width="100%">
            	<tr>
                    <td class="td_normal_title" width="15%">
                        	${lfn:message('tic-core-common:ticCoreCommon.thirdFuncSelect')}
                    </td>
                    <td width="35%">
                         <xform:dialog required="true" propertyId="fdFuncBaseId" propertyName="fdFuncBaseName"
						 	subject="${lfn:message('tic-core-common:ticCoreTransSett.fdFunction')}" dialogJs="selectFunction()" style="width:35%;float:left">
						</xform:dialog>
                    </td>
                   <td class="td_normal_title" width="15%">
                        	 ${lfn:message('tic-core-common:ticCoreCommon.thirdFuncInteractive')}
                    </td>
                    <td width="35%">
                           <span id="functype">${funcType}</span>
                    </td>
                  </tr>
                  <tr>
                     <td class="td_normal_title" width="15%">
                        	<bean:message bundle="tic-core-common" key="ticCoreTransSett.fdSource"/>
                    </td>
                     <td width="35%" colspan='3'>
			              <c:choose>
					   <c:when test="${isLdingTrue}">  
					       <xform:radio required="true" property="fdSource" onValueChange="select_source(this);">
				              <xform:simpleDataSource value="0">第三方函数</xform:simpleDataSource>
				              <xform:simpleDataSource value="2">钉钉专属门户</xform:simpleDataSource>
			              </xform:radio>
					   </c:when>
					   <c:otherwise>  
					        <xform:radio required="true" property="fdSource" onValueChange="select_source(this);">
				              <xform:enumsDataSource enumsType="tic_core_source" />
			              </xform:radio>
					   </c:otherwise>
					</c:choose>
			              <input type="button" class="btnopt" id="thirdFunc_onload_btn"
								value="${lfn:message('tic-core-common:ticCoreTransSett.confirmLoad')}" onclick="loadTicCoreTransSett();"/>
						<input type="button" class="btnopt" id="thirdFunc_update_btn"
								value="${lfn:message('tic-core-common:ticCoreTransSett.updateMappingRelation')}" onclick=" updateTicCoreTransSett();"/>
                    </td>
                  </tr>
                 <tr id="lbpm_select" style="display:none;">
                     <td class="td_normal_title" width="15%">
                        	${lfn:message('tic-core-common:ticCoreTransSett.formModelSelect')}
                    </td>
                     <td width="35%">
					 <c:choose>
					   <c:when test="${isLdingTrue}">  
					   </c:when>
					   <c:otherwise>  
					       <xform:select property="fdModelName" showStatus="edit" showPleaseSelect="true">
							  <xform:customizeDataSource className="com.landray.kmss.tic.core.register.RegisterDataSource" />
						   </xform:select>
					   </c:otherwise>
					</c:choose>
                    </td>
                     <td class="td_normal_title" width="15%">
                        	${lfn:message('tic-core-common:ticCoreTransSett.formSelect')}
                    </td>
                       <td width="35%">
                    		<input type="hidden" value="${ticCoreTransSettForm.fdModelId }" id='fdModelId' name='fdModelId' />
					        <xform:text property="fdTemplateName"  htmlElementProperties="id='fdTemplateName'"></xform:text>
        					<a href="javascript:void(0);" onClick="selectForm();">${lfn:message('tic-core-common:ticCoreTransSett.select')}</a>
        				</td>
                   </tr>
                 <tr id="lbpm_params" style="display:none;">
                     <td class="td_normal_title" width="15%">
                        	 ${lfn:message('tic-core-common:ticCoreTransSett.transFuncBuild')}
                    </td>
                     <td width="35%" colspan='3'>
			              <input type="button" class="btnopt" value="${lfn:message('tic-core-common:ticCoreTransSett.transFuncInParamBuild')}" onClick="importInFields();"/>
			              <input type="button" class="btnopt" value="${lfn:message('tic-core-common:ticCoreTransSett.transFuncOutParamBuild')}" onClick="importOutFields()"/>
                    </td>
                 </tr>
                 
                 <tr id="dingpass_params" style="display:none;">
                     <td class="td_normal_title" width="15%">
                        	 ${lfn:message('tic-core-common:ticCoreTransSett.dingpass.dataformat')}
                    </td>
                     <td width="35%" colspan='3'>
			              <xform:select property="fdDataSourceFormatId" style="width:34%" showStatus="edit" onValueChange="fdDataSourceFormatIdChange(this.value);">
			                  <xform:customizeDataSource className="com.landray.kmss.tic.core.register.LdingFormatDataBean" />
			              </xform:select> 
						<%-- 
						<a
						href="#" 
						onclick="Dialog_Tree(false, 'fdCategoryId', 'fdCategoryName', ',', 'ticCoreBusiCateTreeService&parentId=!{value}&fdAppType=${JsParam.fdAppType}', 
			'<bean:message key="table.ticCoreTransSett" bundle="tic-core-common"/>', null, null, 
			'${ticCoreBusiCateForm.fdId}', null, null, '业务分类');">
					<bean:message key="dialog.selectOther" /> </a>
						--%>
                    </td>
                 </tr>
                 
              </table>
            
            <br/>          
            
            <table class="tb_normal" width="100%" style="table-layout: fixed;">         
            	<tr style="text-align:center;">
            	<td class="td_normal_title">
            		${lfn:message('tic-core-common:ticCoreTransSett.thirdFuncInParam')}
            	</td>
            	<td class="td_normal_title">
            		${lfn:message('tic-core-common:ticCoreTransSett.transFuncInParam')}
            	</td>
            	<tr>
                    <td width="50%" style="vertical-align:top;">                     
                    ${lfn:message('tic-core-common:ticCoreTransSett.transRangeSetting')}：    <div id="_xform_fdTransTypeIn" _xform_type="select" style="line-height: 30px;display:inline-block;">
                                <xform:select property="fdTransTypeIn" htmlElementProperties="id='fdTransTypeIn'" showStatus="edit">
                                    <xform:enumsDataSource enumsType="tic_core_trans_type" />
                                </xform:select>
                                <span style="display: none;" id="is_all_trans_in" >
                               		<textarea name="all_trans_display_in" readonly="readonly" style="width: 200px;height: 25px" ></textarea>
                               		<textarea name="all_trans_hidden_in" readonly="readonly" style="display: none;" ></textarea>
	                               <img id="is_all_trans_in_img" style="vertical-align:middle" src="${KMSS_Parameter_StylePath}icons/edit.gif" />
                               </span> 
                            </div>
						<br/>
						<div style="width:100%">
						<table class="tb_normal" style="width:100%" id="table_paraIn">
						<tr>
			            	<td class="td_normal_title" width="27%">
									${lfn:message('tic-core-common:ticCoreTransSett.paramName')}
									</td>
			            	<td class="td_normal_title" width="30%">
									${lfn:message('tic-core-common:ticCoreTransSett.paramExplain')}
									</td>
			            	<td class="td_normal_title" width="36%">
									${lfn:message('tic-core-common:ticCoreTransSett.paramValue')} 
									<span><a style="color:#47b5ea;" href="javascript:void(0)" title="${lfn:message('tic-core-common:ticCoreCommon.clearMapping')}"  onclick="formulaClear('table_paraIn');">${lfn:message('tic-core-common:ticCoreCommon.clear')}</a></span>
									<!-- 
									<img src="../../resource/images/recycle.png" title="清空" onclick="formulaClear();">
									 -->
									<span><a href="javascript:void(0)" style="color:#47b5ea;" onclick="formulaAdapt('table_paraIn');" title="${lfn:message('tic-core-common:ticCoreCommon.autoMatchMapping')}">${lfn:message('tic-core-common:ticCoreTransSett.adapter')}</a></span>
									</td>
			            	<td class="td_normal_title" width="7%">
									
									</td>
						</tr>
						</table>
                    </td>
                    
                    <td width="50%" style="vertical-align:top;">
                        <input type="hidden" name="importInFieldIds" />
                        <input type="hidden" name="importInFieldNames" />
                        
                        <input type="hidden" name="importOutFieldIds" />
                        <input type="hidden" name="importOutFieldNames" />
                        
                        <div style="width:100%">
						<table class="tb_normal" style="width:100%" id="table_paraIn_trans">
						<tr>
			            	<td class="td_normal_title">
									${lfn:message('tic-core-common:ticCoreTransSett.paramName')}
									</td>
			            	<td class="td_normal_title">
									${lfn:message('tic-core-common:ticCoreTransSett.paramExplain')}
									</td>
			            	<td class="td_normal_title">
									${lfn:message('tic-core-common:ticCoreTransSett.paramType')}
									</td>
							<td class="td_normal_title">
									${lfn:message('tic-core-common:ticCoreCommon.search')}
							</td>
			            	<td class="td_normal_title" align='center'>
										<img  src="${KMSS_Parameter_StylePath}icons/add.gif" title="${lfn:message('tic-core-common:ticCoreCommon.add')}" onClick="addField('in');"/> <img  src="../../resource/images/recycle.png" title="${lfn:message('tic-core-common:ticCoreCommon.clear')}" onClick="clearField('in');"/>
										<input type="button" id="jsonButton" title="${lfn:message('tic-core-common:ticCoreTransSett.extractJSON')}" value="${lfn:message('tic-core-common:ticCoreTransSett.extract')}" onclick="loadJSON('bodyJsonIn','in');" />
							</td>
							</tr>
						
						</table>
						<div id="div_bodyJsonIn" style="width:99%;height:92%;display:none">
						${lfn:message('tic-core-common:ticCoreCommon.insertJSONXMLCode')}：
						<xform:textarea property="bodyJsonIn"  style="width:99%;height:96%;" ></xform:textarea>
						</div>
						
						<br/>
						<br/>
					<!-- 	<input type="button" onclick="buildParaJson($('#table_paraOut_trans'));" value="test" />
						<input type="button" onclick="genXformVarInfo($('#table_paraIn_trans'));" value="test2" /> -->
					
                    </td>
                </tr>
            </table>
           <br/>
      <table class="tb_normal" width="100%" style="table-layout: fixed;">         
            	<tr style="text-align:center;">
            	<td class="td_normal_title">
            		${lfn:message('tic-core-common:ticCoreTransSett.thirdFuncOutParam')}
            	</td>
            	<td class="td_normal_title">
            		${lfn:message('tic-core-common:ticCoreTransSett.transFuncOutParam')}
            	</td>
            	</tr>
            	<tr>
                    <td width="50%" style="vertical-align:top;">  
            		<div style="width:100%">
						<table class="tb_normal" style="width:100%" id="table_paraOut">
						<tr>
			            	<td class="td_normal_title">
									${lfn:message('tic-core-common:ticCoreTransSett.paramName')}
									</td>
			            	<td class="td_normal_title">
									${lfn:message('tic-core-common:ticCoreTransSett.paramExplain')}
							</td>
						</tr>
						</table> 
						</div>
						
					</td>
					 <td width="50%" style="vertical-align:top;">  
            		<div style="width:100%">
						${lfn:message('tic-core-common:ticCoreTransSett.transRangeSetting')}：    <div id="_xform_fdTransTypeOut" _xform_type="select" style="line-height: 30px;display:inline-block;">
                                <xform:select property="fdTransTypeOut" htmlElementProperties="id='fdTransTypeOut'" showStatus="edit">
                                    <xform:enumsDataSource enumsType="tic_core_trans_type" />
                                </xform:select>
                                <span style="display: none;" id="is_all_trans_out" >
                               		<textarea name="all_trans_display_out" readonly="readonly" style="width: 200px;height: 25px" ></textarea>
                               		<textarea name="all_trans_hidden_out" readonly="readonly" style="display: none;" ></textarea>
	                               <img id="is_all_trans_out_img" style="vertical-align:middle" src="${KMSS_Parameter_StylePath}icons/edit.gif" />
                               </span> 
                            </div>
                            
						<div style="width:100%">
						<table class="tb_normal" style="width:100%" id="table_paraOut_trans">
						<tr>
			            	<td class="td_normal_title">
									${lfn:message('tic-core-common:ticCoreTransSett.paramName')}
									</td>
			            	<td class="td_normal_title">
									${lfn:message('tic-core-common:ticCoreTransSett.paramExplain')}
									</td>
			            	<td class="td_normal_title">
									${lfn:message('tic-core-common:ticCoreTransSett.paramType')}
									</td>
									<td class="td_normal_title">
									${lfn:message('tic-core-common:ticCoreCommon.search')}
							</td>
							<td class="td_normal_title">
									${lfn:message('tic-core-common:ticCoreTransSett.paramValue')}
									<span><a style="color:#47b5ea;" href="javascript:void(0)" title="${lfn:message('tic-core-common:ticCoreCommon.clearMapping')}"  onclick="formulaClear('table_paraOut_trans');">${lfn:message('tic-core-common:ticCoreCommon.clear')}</a></span>
									<span><a href="javascript:void(0)" style="color:#47b5ea;" onclick="formulaAdapt('table_paraOut_trans');" title="${lfn:message('tic-core-common:ticCoreCommon.autoMatchMapping')}">${lfn:message('tic-core-common:ticCoreTransSett.adapter')}</a></span>
									</td>
							
			            	<td class="td_normal_title" align='center'> 
										<img  src="${KMSS_Parameter_StylePath}icons/add.gif" title="${lfn:message('tic-core-common:ticCoreCommon.add')}" onClick="addField('out');"/> <img  src="../../resource/images/recycle.png" title="${lfn:message('tic-core-common:ticCoreCommon.clear')}" onClick="clearField('out');"/>
										<input type="button" id="jsonButton" title="${lfn:message('tic-core-common:ticCoreTransSett.extractJSON')}" value="${lfn:message('tic-core-common:ticCoreTransSett.extract')}" onclick="loadJSON('bodyJsonOut','out');" />
									</td>
						</tr>
						</table>
						<div id="div_bodyJsonOut" style="width:99%;height:92%;display:none">
						${lfn:message('tic-core-common:ticCoreCommon.insertJSONXMLCode')}：
						<xform:textarea property="bodyJsonOut"  style="width:99%;height:96%;" ></xform:textarea>
						</div>
						</div>	
            		</div>
            		
            		<div style="width:100%;margin-top: 5px" >
							<table class="tb_normal" style="width:100%">
								<tr>
									<td class="td_normal_title" align="center">${lfn:message('tic-core-common:ticCoreCommon.businessExceptionSet')}</td>
								</tr>
								<tr>
									<td align="center"><select id="businessExceptionParamSelect" name="fdReturnValue" onchange="" ></select> <input type="button" onclick="buildBusinessException()" value="加载"></input>
									<div align="center" >
										<bean:message bundle="tic-core-common"
										key="ticSapRfcSetting.lang.return" />
										 <input type="text"
										name="fdSuccess"
										value="${ticCoreTransSettForm.fdSuccess}" class="inputsgl">
										<bean:message
										bundle="tic-core-common"
										key="ticSapRfcSetting.lang.success" />
									<br>
										<bean:message bundle="tic-core-common"
										key="ticSapRfcSetting.lang.return" />
									<input type="text"
										name="fdFail"
										value="${ticCoreTransSettForm.fdFail}" class="inputsgl">
										<bean:message bundle="tic-core-common"
										key="ticSapRfcSetting.lang.fail" />
									</div>
									</td>
								</tr>
							</table>
						</div>
						
            		</td>
				</tr>
		</table>
        
        <input id="fdTransSett_json" name="fdTransSett" readonly="readonly" type="hidden" ></input>
    </center>
    <html:hidden property="fdId" />
    <html:hidden property="method_GET" />
    <script>
         var _validation = $KMSSValidation();
         _validation.addValidator('isSame(type)',
    	'子节点不能同名',
    	function(v,e,o){
        	 var table="";
        	 if(o['type']=="in"){
        	    table="#table_paraIn_trans";
              }else{
            	table="#table_paraOut_trans";
              }
        	 var obj=$(e).parent().parent();
        	 var arr=[];
        	 var hash = {};
        	 if(obj.hasClass("parent")){
             	$(table+" .child-of-"+obj.attr("id")).each(function(){
               	    arr.push($(this).find("[name='name']").val());
            	});
            for(var i in arr) {
             	if(hash[arr[i]]){
             		return false;
             	}
             	   hash[arr[i]] = true;
             	}
             	return true;
        	 } 	 
         return true;
    	});
         
         function loadJSON(name,type){
        	var tableId;
        	if(type=="in"){
        		tableId = "table_paraIn_trans";
        	}else{
        		tableId = "table_paraOut_trans";
        	}
         	seajs.use(['lui/dialog','lui/jquery'], function(dialog,$) {
 			var obj = $('#div_'+name);
 			if (obj != null) {
 				if (obj.is(':hidden')) {
 					dialog.build( {
 						config : {
 							width:600,
 							height:400,
 							lock : true,
 							cache : true,
 							close: true,
 							content : {
 								type : "element",
 								elem : obj,
 								scroll : true,
 								buttons : [ {
 									name : "确定",
 									value : true,
 									focus : true,
 									fn : function(value,dialogFr) {
 										dialogFr.hide();
 									}
 								}]
 							},
 							title : "抽取JSON/XML"
 						},

 						callback : function(value, dialog) {
 							currentIndex = null;
 							//$(obj).undelegate("select[name='fdAttrField']","change");
 							var jsonstr =$('textarea[name='+name+']').val();
 							if(jsonstr){
 								$.ajax({
 									type: "POST",
 								   	url: "${KMSS_Parameter_ContextPath}tic/rest/connector/tic_rest_main/ticRestMain.do?method=paseJsonTransParamInJson",
 								   	data: "jsonstr="+jsonstr,
 								   	dataType: "JSON",
 								   	success: function(data){
 								   		//console.info(data);
 								   		if(data.isSuccess){
 								   			$("#"+tableId +" tbody ").find("tr:not(:first)").remove();
 											//buildTableHtml(data.result,tableId);
	 								   		for(var i=0;i<data.result.length;i++){
	 								    		genFieldHtml(data.result[i],type,null);
	 								    	}
	 										 
	 										$("#"+tableId).treeTable({ initialState: true,indent:15 }).expandAll();
 								   		}else{
 								   			alert(data.errorMsg);
 								   		}
 								   	}
 								});
 							}
 						},
 						actor : {
 							type : "default"
 						},
 						trigger : {
 							type : "default"
 						}
 					}).show();
 					//xform_main_data_custom_enumChangeEvent("TABLE_DocList");
 					/*
 					$(obj).delegate("select[name='fdAttrField']","change",function(event){
 						var option = $(this).find("option:selected");
 						if(option.attr('data-type') == 'enum'){
 							//构建选项
 							var suffix = option.val() + "_" + ++_xform_main_data_show_radioNameNum;
 							$(this).after(xform_main_data_custom_buildEnumHtml(suffix));
 						}
 						xform_main_data_custome_stopBubble(event);
 					});
 					$('.lui_dialog_head_right').hide();
 					*/
 				}
 			}
 		});
         }
         
         function loadJSON2(name,tableId){
				//debugger;
				$("#"+tableId +" tbody ").find("tr:not(:first)").remove();
				var jsonstr =$('textarea[name='+name+']').val();
				if(jsonstr){
					$.ajax({
						type: "POST",
					   	url: "${KMSS_Parameter_ContextPath}tic/rest/connector/tic_rest_main/ticRestMain.do?method=paseJsonTransParamInJson",
					   	data: "jsonstr="+jsonstr,
					   	dataType: "JSON",
					   	success: function(data){
					   		//console.info(data);
					   		if(data.isSuccess){
								buildTableHtml(data.result,tableId);
								
					   		}else{
					   			alert(data.errorMsg);
					   		}
					   	}
					});			
				}
			}
			
			function buildTableHtml(data,tableId){
		    	trs = new Array();
				$.each(data, function(idx, obj) {
					genParaInHtml(obj,"");
				});
	    		for(i=0;i<trs.length;i++){
	    			var tr = trs[i];
	    			if(tr.id){
						var id=tr.id;
						id=id.replace(/\|/g, '');  
						tr.id=id; 
					}
	    			var img_add="";
	    			if(tr.type=="object"|| tr.type=="arrayObject"){
	    				img_add="<img id='img_add'  src=\"${KMSS_Parameter_StylePath}icons/add.gif\" title=\"新增\" onClick=\"addField('"+tableId+"',this);\"/> ";
	    			}
	    			var tr_new = $("<tr id='"+tr.id+"'><td><input  type='text' name='name' value='"+tr.name+"' validate='required' class='inputsgl'></input></td><td><input type='text' name='title'  value='"+tr.title+"'  validate='required' class='inputsgl'></input></td>"+
	    						"<td><select name='type'><option value='string'>字符串</option>"+
	    				    	"<option value='boolean'>布尔</option><option value='int'>整型</option><option value='long'>长整型</option><option value='double'>浮点型</option>"+
	    				    	"<option value='object'>对象</option><option value='arrayObject'>对象数组</option><option value='arrayInt'>整型数组</option>"+
	    				    	"<option value='arrayDouble'>浮点数组</option><option value='arrayBoolean'>布尔数组</option><option value='arrayString'>字符串数组</option></select></td>"+
	    						"<td align='center'>"+img_add+"<img  src=\"${KMSS_Parameter_StylePath}icons/delete.gif\" title=\"删除\" onClick=\"deleteField(this);\"/></td>"+"</tr>");
	    			
	    			tr_new.find("select").val(tr.type);
	    	    	tr_new.find("select").on("change",function(){
	    	    		var type=$(this).val();
	    	    		var img_td=$(this).parent().parent().find("img").parent();
	    	    		img_td.find('#img_add').remove();
	    	    		if( type=="object"|| type=="arrayObject"){
	    	    			img_td.prepend("<img id='img_add'  src=\"${KMSS_Parameter_StylePath}icons/add.gif\" title=\"新增\" onClick=\"addField('"+tableId+"',this);\"/> " );
	    	    		}
	    	    	});
					if(tr.parentId){
	    				tr_new.addClass("child-of-"+tr.parentId);
	    			}
	    			$("#"+tableId).append(tr_new);
	    			
	    		}
	    		$("#"+tableId).treeTable({ initialState: true,indent:15 }).expandAll();
			}
			 function buildRow(trs,tableId){
		 		 $(trs).each(function(){
					 var new_row="<tr><td><input  type='text' name='name' value='"+this.name+"' style='width:85%' validate='required' class='inputsgl'></input></td>";
					 if(tableId=='func_url'){
						  new_row+="<td><input  type='text' name='title' value='"+this.title+"'  style='width:85%' validate='required' class='inputsgl'></input></td>";
					 }
					 if(tableId=='func_header'){
						  new_row+="<td><input  type='text' name='value' value='"+this.value+"' style='width:85%' validate='required' class='inputsgl'></input></td>";
					 } 
					 if(tableId=='func_cookie'){
						  new_row+="<td><input  type='text' name='value' value='"+this.value+"' style='width:85%' validate='required' class='inputsgl'></input></td>";
					 } 
					 new_row+="<td align='center'><img  src=\"${KMSS_Parameter_StylePath}icons/delete.gif\" title=\"删除\" onClick=\"deleteRow(this);\"/></td></tr>";
					 $("#"+tableId).append($(new_row));
		 		});
			 }
			 
			 
       // $KMSSValidation();
    </script>
</html:form>
    </template:replace>
</template:include>
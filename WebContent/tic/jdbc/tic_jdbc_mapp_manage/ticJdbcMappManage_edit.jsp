<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<% response.addHeader("X-UA-Compaticle", "IE=Edge"); %>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<%@ include file="/tic/core/resource/plumb/jsp/includePlumb.jsp" %>
<%
  String type= (String)request.getAttribute("type");
%>
<script type="text/javascript" src="${KMSS_Parameter_ContextPath}tic/jdbc/resource/js/jdbc-jsplumb.js"></script>
<script type="text/javascript" src="${KMSS_Parameter_ContextPath}tic/jdbc/resource/js/zDialog.js"></script>
<script  type="text/javascript">
Com_IncludeFile("dialog.js|formula.js|json2.js");
Com_IncludeFile("jdbcFormula.js","${KMSS_Parameter_ContextPath}tic/jdbc/resource/js/","js",true);
Com_IncludeFile("tools.js","${KMSS_Parameter_ContextPath}tic/core/resource/js/","js",true);


//获取所选择的映射信息
function getMappInfor(){		
	var selectVal=$("select[name='fdSyncSelectType']").find("option:selected").val(); 
	 $('#sync_setting tr:gt(1)').remove();
	//1全量同步  2增量同步	
	 if(selectVal=='1'){
		 clearMappInfor();
	}else{
		getMappType();
	} 
}
//同步数据前先把目标表内容清空，再插入数据
function clearMappInfor(){
    var fullSyncHtml = "<tr><td width='35%'><input type='checkbox' id='fullDelId' name='fullDelId' value='1'/>每次同步前是否删除目标表数据</td></tr>";
    $("#fdSyncSelectType").after(fullSyncHtml);
}
//通过 方式的不同,生成对应的 方式表
function getMappType(){
	getLinkedLineInfo();
	if(!fdMappConfigJson){
		alert("请先配置映射！");
		return;
	}
	var funcBaseId = $("input[name='fdFuncBaseId']").val();
	var data = new KMSSData();
    data.SendToUrl(Com_Parameter.ContextPath +
		"tic/jdbc/tic_jdbc_mapp_manage/ticJdbcMappManage.do?method=queryObjectById&funcBaseId="+funcBaseId,
		function(http_request){afterGetMappType(http_request);}, false);
}
function afterGetMappType(http_request){
	  var mapArray  =  http_request.responseText;
	  var sourceSql=$("textarea[name='fdDataSourceSql']").val();
		if(mapArray!=null && mapArray.length>0){
		     mapArray =eval("("+mapArray+")");
		     var dbId=mapArray[0];
			   var tableInfoHtml="";   
               var obj=fdMappConfigJson;
			    obj=eval("("+obj+")");
		       var sourceField="";
			   var targetTabHtml="";   
			   var targetTabHtml_log = "";
			    var fieldList=mapArray[1];
			    sourceField=setTabField(fieldList); 
			    
				for(var key in obj){ 
				   	   if(key!=null && key!=undefined){
				   		   var fieldInfoArr = obj[key];
				   		   var primaryKeyField="";
				   		   var pkFieldValue="";
				   		   var otherField="";
				   		   var havePkFlag=false;
				   		   var tempField="";
				   		   if(fieldInfoArr!=null && fieldInfoArr!=undefined && fieldInfoArr.length>0){
				       		   for(var j=0;j<fieldInfoArr.length;j++){
				                     var fieldName = fieldInfoArr[j].fieldName;
				            		 var primaryKey= fieldInfoArr[j].primaryKey;
				            		 if(primaryKey=='1'){
			            			     havePkFlag=true;
			            			     pkFieldValue=fieldName;
			            			     primaryKeyField+="<option value='"+fieldName+"' selected='selected'>"+fieldName+"</option>";
				                	  }else{ 
			                    	    if(j==0){
			                		      tempField=fieldName;
			                    	    }
			                		     otherField+="<option value='"+fieldName+"'>"+fieldName+"</option>";
				                      }
				           	    }
					         }
						}
					          if(!havePkFlag){
					        	  pkFieldValue=tempField;
						      }
							   var tabInfor=key+"("+pkFieldValue+")";
					           targetTabHtml+="<input style='width:180px;' style='cursor:pointer;' title='可双击编辑，并确保类似主键类型字段' type='text' id='"+key+"' value="+tabInfor+" name='"+key+"''  ondblclick='showSelect(this);' readonly='readonly' class='inputread'/>";
					           targetTabHtml+="<select  style='display: none;' id='"+key+".fieldPk' name='"+key+".fieldPk'  style='display:none;' onblur ='doChange(this)';>";
					           targetTabHtml+=(primaryKeyField+otherField);
					           targetTabHtml+="</select>&nbsp;&nbsp;&nbsp;";
					           targetTabHtml_log+="<input style='width:180px;' type='text' title='不可编辑，此处不影响任务同步' id='"+key+"''  value="+tabInfor+" name='"+key+"'' readonly='readonly' class='inputread'/>";
					           targetTabHtml_log+="<select  style='display: none;' id='"+key+".fieldPk' name='"+key+".fieldPk'  style='display:none;' onblur ='doChange(this)';>";
					           targetTabHtml_log+=(primaryKeyField+otherField);
					           targetTabHtml_log+="</select>&nbsp;&nbsp;&nbsp;";
				   	   }

	             
		    	  tableInfoHtml+="<tr><td width='10%'>源表 </td>";
		    	  tableInfoHtml+="<td width='35%'>时间戳： <input type='hidden' id='lastUpdateTime' name='lastUpdateTime'/>";
				  if(sourceField!=null && sourceField.length>0){
		  		      tableInfoHtml+="<select id='filter'  name='filter'>";
				  }
			      tableInfoHtml+=sourceField;
			      tableInfoHtml+="</select></td>";
			      
			      tableInfoHtml+='<td width="55%">删除条件:<input class="inputsgl" onblur="deleteConditionBlur(\''+ dbId +'\')" type="text" id="deleteCondition" name="deleteCondition">';
			      
		          tableInfoHtml+='<img style="cursor:pointer" onclick="editFormFieldFunction(\''+ dbId +'\');" src="${KMSS_Parameter_StylePath}icons/edit.gif" />';
			      //tableInfoHtml+='<img style="cursor:pointer" onclick="queryDelData(\''+ dbId +'\');" src="'+Com_Parameter.ContextPath+'resource/style/default/images/form/search.png" /></td>';
			      tableInfoHtml+="</tr>";
			      tableInfoHtml+="<tr ><td width='10%'>目标表</td>";
			      tableInfoHtml+="<td id='targetTab' colspan=2>";
			      tableInfoHtml+=targetTabHtml;
			      tableInfoHtml+="</td></tr>";
		  	      $("#fdSyncSelectType").after(tableInfoHtml);	
		}
}
//构建下拉框选项
function setTabField(fieldInfo){
	var optionHtml="";
	    optionHtml+="<option value=''>==请选择==</option>";
 if(fieldInfo!=null && fieldInfo.length>0){
	  for(var i=0;i<fieldInfo.length;i++){
		  optionHtml+="<option value="+fieldInfo[i]+">";
		  optionHtml+=""+fieldInfo[i]+"";
		  optionHtml+="</option>";
	  }
 }
 return optionHtml;
}
//显示目标表中主键下拉框
function  showSelect(obj){
  var id = obj.id;
  $("input[id='"+id+"']").hide();
  var selectId = id+".fieldPk";
  $("select[id='"+selectId+"']").show();
  $("select[id='"+selectId+"']").focus();
}
//表主键发生变化处理
function doChange(obj){ 
	var selectId=obj.id;
	var tempArray=selectId.split(".");
	var indexNum=selectId.lastIndexOf(".");
	var inputId = selectId.substring(0,indexNum);
	var selectOption = $(obj).find("option:selected").val();
	var inputValue=tempArray[0]+"("+selectOption+")";
	$("input[id='"+inputId+"']").attr("value",inputValue);
	$("input[id='"+inputId+"']").show();
	$("select[id='"+selectId+"']").hide();
}

//得到当前表的字段信息
function editFormFieldFunction(dbId){
	var sourceSql =$("textarea[name='fdDataSourceSql']").val();
	var deleteCondition = $("input[name='deleteCondition']").val();console.log(deleteCondition);
	var url = "ticJdbcLoadTableFieldService&dbId=" + dbId + "&sourceSql="+ sourceSql;
    var data = new KMSSData();
    data.SendToBean(url, function(rtn){destionTabelField_back(rtn, dbId, sourceSql, deleteCondition, "deleteCondition");});
}
 function destionTabelField_back(rtn, dbId, sourceSql, deleteCondition, idField) {
	var mapArray = rtn.GetHashMapArray();
	var tableInfoHtml = "<table border=0 class='tb_normal'>";
	var fieldInfo=[];
	var tempInfo="";
	if (mapArray != null && mapArray.length > 0) {
		var obj = eval("(" + mapArray[0].key0 + ")");
		var fieldName="";
		var dataType="";
		for ( var key in obj) {
			if (key != null && key != undefined) {
				for ( var i = 0; i < obj[key].length; i++) {
					fieldName =eval("(" + obj[key][i] + ")").fieldName;
					dataType =eval("(" + obj[key][i] + ")").dataType;
					tempInfo='{'+'"' + "name" + '"' + ":" +'"' +fieldName+ '"'+ "," 
					            +'"' + "label" + '"' + ":" +'"' +fieldName+ '"'+","
					            +'"' + "type" + '"' + ":" +'"' +dataType+ '"'+ '}';
					fieldInfo.push(JSON.parse(tempInfo));
				}
		 }
		}
	}
	Formula_DialogInput(idField, idField, fieldInfo, null, callBackFunction, null, null, dbId, sourceSql, deleteCondition);		
}
 function  callBackFunction(rtn){
		if(rtn!=null&&rtn.data.length>0){
			var condtion= rtn.data[0].name;
			$("input[id='deleteCondition']").attr("value",condtion);
		}
	}
//查询删除数据前100条
 function queryDelData(dbId) {
 	var sourceSql = $("textarea[name='fdDataSourceSql']").val();
 	var deleteCondition = $("input[name='deleteCondition").val();
 	if (deleteCondition != "") {
 		sourceSql = "select * from ("+ sourceSql +") as queryDelTab where "+ deleteCondition;
 	}
 	var diaog = new Dialog();
 	IMAGESPATH = '${KMSS_Parameter_ContextPath}tic/jdbc/resource/images/';
 	diaog.Drag = true;
 	diaog.Width = 850;
 	diaog.Height = 450;
 	diaog.Title = '<bean:message bundle="tic-jdbc" key="ticJdbcMappManage.viewData"/>';
 	diaog.URL = Com_Parameter.ContextPath +"tic/jdbc/tic_jdbc_task_manage/ticJdbcTaskManage.do?method=getDelData&dbId="
 			+ dbId + "&sourceSql=" + sourceSql;
 	diaog.show();
 }
//增量删除条件失去焦点事件
 function deleteConditionBlur( dbId) {
 	var sourceSql = $("textarea[name='fdDataSourceSql']").val();
 	var deleteCondition  =  $("input[name='deleteCondition").val();
 	if (deleteCondition != "") {
 		sourceSql = "select * from ("+ sourceSql +") as queryDelTab where "+ deleteCondition;
 	}
 	var data = new KMSSData();
 	data.SendToBean("ticJdbcDeleteValidateBean&dbId="+ dbId +"&sourceSql="+sourceSql, ticjdbc_DCBlurCall);
 }
 function ticjdbc_DCBlurCall(rtnData) {
		var rtnObj = rtnData.GetHashMapArray()[0];
		var result = rtnObj["result"];
		if ("false" == result) {
			var errorStr = rtnObj["errorStr"];
			alert("删除条件填写错误，请改正，否则会对同步造成错误，错误信息为："+ errorStr);
		}
	}
 function showTab(o,type){
	$('ul li').each(function(index,obj){
		$(obj).removeClass("selectTab");
	});
	$(o).addClass("selectTab");
	if(type=="mapp"){
		$('#mapp_setting').show();
		$('#table_setting').show();
		$('#sync_setting').hide();
	}else{
		$('#mapp_setting').hide();
		$('#table_setting').hide();
		$('#sync_setting').show();
	}
 }
</script>
<div id="optBarDiv">
	<input type=button id="_save" value="<bean:message key="button.save"/>"
			onclick="saveInfo();">
	<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
</div>
<style>
  ul li{
   float:left;
   list-style:none;
}
.selectTab{
  color:#47b5ea;
}
</style>
<ul style="color:#868686">
   <li class="selectTab" style="padding-left:1.5rem;" onclick="showTab(this,'mapp')"><bean:message bundle="tic-jdbc" key="table.ticJdbcMappManage"/></li><li>|</li><li onclick="showTab(this,'sync')">同步配置</li>
<div>
<center >
<table class="tb_normal" width=95% id="mapp_setting">
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-jdbc" key="ticJdbcMappManage.docSubject"/>
		</td><td width="35%">
			    <input type="hidden" readOnly="readonly" id="fdFuncBaseId" name="fdFuncBaseId"/>
       	   	 	<input type="text" readOnly="readonly" id="fdFuncBaseName" name="fdFuncBaseName" class="inputsgl"/>
		</td>
	<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-jdbc" key="ticJdbcMappManage.fdTargetSource"/>
		</td><td width="35%">
			<xform:select property="fdCompDbcp" onValueChange="getTargetTables();" style="float: left;" showStatus="edit"  required="true">
			 	
			 	<xform:beanDataSource serviceBean="compDbcpService" 
					selectBlock="fdId,fdName" orderBy="" />
				<%-- 
				<xform:customizeDataSource className="com.landray.kmss.tic.scene.service.spring.TicSceneCompDbcpDataSource"/>
				--%>
			</xform:select>
			<img style="display: none; float: left;" id="erp_loading_bar" alt="loading"  src="${KMSS_Parameter_ResPath}style/common/images/loading.gif"   />
		</td>
	</tr>

	<tr>
		 <td colspan="2" style="padding: 0px;" valign="top">
			 <table  width="100%" height="100%" style="background-color: #99CCFE">
				<tr id="inParamValueBefore">
					<td colspan="3">
						<textarea rows="1" cols="" readonly="readonly" name="fdDataSetSql" 
							style="width:100%;background-color: #99CCFE;"></textarea>
						<textarea id="fd_sourceSql" name="fdDataSourceSql" 
							style="display:none;width:100%">${ticJdbcMappManageForm.fdDataSourceSql }</textarea>
					</td>
				</tr>
				<tr valign=middle>
			        <td colspan="3" align=center>
			        	<span><input type="button" class="btnopt" align="middle" value='<bean:message bundle="tic-jdbc" key="ticJdbcMappManage.preview"/>'  onclick="getTableData();"/></span>
				        <span><input type="button" class="btnopt" align="middle" value='<bean:message bundle="tic-jdbc" key="ticJdbcMappManage.extraction"/>'  onclick="getTableField();"/></span>	
			        </td>
       			</tr> 
			</table>
		 </td>
	
		 <td colspan="2" style="padding: 0px;" valign="top">
				<table width="100%"  style="background-color: #FEFECC">
				  <tr><td width=100%>
							<bean:message bundle="tic-jdbc" key="ticJdbcMappManage.targetTables"/>:
							<span id='_tabNum'></span>
					  </td>
				  </tr>
				  
				  <tr>
					<td width="100%" >
					   <select id="dest_tableList" name="dest_tableList" multiple="multiple" ondblclick="selectedTable(this);" style="height: 105px;width:100%;">
					   </select>
				   </td>
				 </tr>
				 
				 <tr>
					 <td  width=100% align=left   style="background-color: #FEFECC">
			         	<bean:message bundle="tic-jdbc" key="ticJdbcMappManage.haveSelectedTables"/>
			         </td>
			     </tr>
			     
			     <tr>
				    <td width="100%" >
				    <select id="have_selected_tableList"  name="have_selected_tableList" multiple="multiple" ondblclick="deleteCurrentRow();"  style="height: 72px;width:100%;">
				    </select>
				    </td>
		         </tr>
				 
				 <tr>
					 <td valign=middle width=100% align=center style="background-color: #FEFECC" >
						<span><input type="button"  align="middle" value='<bean:message bundle="tic-jdbc" key="ticJdbcMappManage.extraction"/>'  onclick="getTableFielInfor();"/></span>		
				     </td>
			    </tr>  
			   </table>
	    </td>
     </tr>
</table>

<table class="tb_normal" width=95% id="table_setting">
    <!-- 显示源表和目标表的字段 -->
    <tr>
    	
	    <td width="42%" style="border: none;">       	  	
	         <table id="source_table_field" border=0 align="right" class="tb_normal"></table>	      
	    </td>
	    
	    <td width="15%" style="border: none;"></td>
        <td width="43%" style="border: none;" border=0>	
			<div id="_dest_table_field"></div>
		</td>
		
    </tr> 
</table>   
<table class="tb_normal" width=95% id="sync_setting" style="display:none;">
     <tr>
		<td class="td_normal_title" width=15% rowspan="100%">
			${lfn:message('tic-core-common:ticCoreCommon.syncWay')}
		</td>
	</tr>
	<tr id="fdSyncSelectType">
		<td width="35%" colspan="100%">
				<select style="float:left;"  name="fdSyncSelectType" onchange="getMappInfor();">
					      <option value="1">${lfn:message('tic-core-common:ticCoreCommon.allSync')}</option>
					      <option value="2">${lfn:message('tic-core-common:ticCoreCommon.addSync')}</option>
				</select>	
		</td>
	</tr>
	<tr>
		<td width="35%">
			<input type='checkbox' id='fullDelId' name='fullDelId' value='1'/>${lfn:message('tic-core-common:ticCoreCommon.beforSyncIsDelData')}
		</td>
	</tr>
</table>
</center>
<script type="text/javascript">
$KMSSValidation();
function init_setInParams() {
	if ("" != Args_Dialog.fdFuncBaseId) {
		$("#fdFuncBaseId").val(Args_Dialog.fdFuncBaseId);
		$("#fdFuncBaseName").val(Args_Dialog.fdFuncBaseName);
		var data = new KMSSData();
		data.SendToBean("ticJdbcDataSetJsonBean&funcId="+Args_Dialog.fdFuncBaseId, init_setInParams_back);
	}
}

function init_setInParams_back(rtnData) {
	var dataObj = rtnData.GetHashMapArray()[0];
	var fdDataSql = dataObj["dataSetSql"];
	var funcId = dataObj["funcId"];
	 $("textarea[name='fdDataSetSql']").text(fdDataSql);
	 $("textarea[name='fdDataSourceSql']").text(dataObj["fdDataSourceSql"]);
	 //初始化入参数据
	 var inJsonObj = $.parseJSON(dataObj["inParam"]);
	 if(inJsonObj){
		 var html = "<tr align='center'><td width='30%'>参数名</td><td>值类型</td><td>参数值</td></tr>"
		 $.each(inJsonObj,function(i,jo) {
			 html += "<tr align='center'><td width='30%'>"+ jo.tagName +
				"</td><td>"+jo.ctype+"</td><td><input type='text' name='inParamsValue' class='inputsgl getValueClass' tagName='"+ jo.tagName +"' "+
				"ctype='"+ jo.ctype +"' value=''/></td></tr>";
		 });
		$("#inParamValueBefore").after(html);
	 };
	 
	//初始化jdbc条件参数
	if(Args_Dialog.fdInParam){
		var inparamJo = JSON.parse(Args_Dialog.fdInParam);
		var inps = $('.getValueClass');
		inps.each(function() {
			$(this).val(inparamJo[$(this).attr("tagname")].value);
		});
	}
}

//预览数据
function getTableData() {
	//将传入的参数json化
	var params = {};
	var inps = $('.getValueClass');
	inps.each(function() {
		if($(this).val()){
			var param = {};
			//param.name = $(this).attr("tagname");
			param.value = $(this).val();
			param.type = $(this).attr("ctype");
			params[$(this).attr("tagname")]=param;
		}
	});
	var inparamstr = JSON.stringify(params);
	//fdFuncBaseId
	
	var dbId = $("#fdFuncBaseId").val();
	if (dbId.length < 1 || dbId == null) {
		alert('<bean:message bundle="tic-jdbc" key="ticJdbcMappManage.dataSourceMessageTip"/>');
		return false;
	}

	var fd_sourceSql =  $("textarea[name='fdDataSetSql']").text();
	if (fd_sourceSql == null || fd_sourceSql.length < 1) {
		alert('<bean:message bundle="tic-jdbc" key="ticJdbcMappManage.dataSourceSqlMessageTip"/>');
		return false;
	}

	var diag = new Dialog();
	IMAGESPATH = '${KMSS_Parameter_ContextPath}tic/jdbc/resource/images/';
	diag.Drag = true;
	diag.Width = 850;
	diag.Height = 450;
	diag.Title = '<bean:message bundle="tic-jdbc" key="ticJdbcMappManage.viewData"/>';
	diag.URL = "${KMSS_Parameter_ContextPath}tic/jdbc/tic_jdbc_mapp_manage/ticJdbcMappManage.do?method=getData&dbId="
			+ dbId + "&sourceSql=" + encodeURI(fd_sourceSql) + "&inparam=" + encodeURI(inparamstr);
	diag.show();  
}

function getInputParamtersBySQL_new(fdSqlExpression, outObjs) {
	var rtnArr = fdSqlExpression.match(/{(\S+)(}|$)/g);
	if (rtnArr == null) return null;
	var rtnResult = new Array();
	for (var i = 0, alength = rtnArr.length; i < alength; i++) {
		var rtnArrStr = rtnArr[i].substring(0,rtnArr[i].length-1).substring(1);
		var columnObj = {};
		columnObj["columnName"] = rtnArrStr;
		columnObj["tagName"] = rtnArrStr;
		for (var j = 0; j < outObjs.length; j++) {
			var outObj = outObjs[j];
			if (outObj.tagName.toLowerCase() == rtnArrStr.toLowerCase()) {
				columnObj["columnName"] = outObj.tagName;
				columnObj["tagName"] = rtnArrStr;
				columnObj["ctype"] = outObj.ctype;
				columnObj["length"] = outObj.length;
			}
		}
		rtnResult.push(columnObj);
	}
	return (rtnResult.length == 0) ? null : rtnResult;
};


var objJson={};

function setTabJson(){
    var options =$("#have_selected_tableList option");
	if(options!=null && options.length>0){
		objJson={};
		for(var i=0;i<options.length;i++){
	        var optionVal = $(options[i]).val();
	        objJson[optionVal] = [];
	        // 保存表字段全部信息，不确定的信息置空
		    $("table[id='mapp_"+ optionVal +"'] tr").each(function(index, obj){
				if (0 != index) {
					var initData = $(obj).children("td:eq(4)").find("input[name='fieldInitData']").val();
					// 存在字段初始值才保存值
					if (initData) {
						var columnName = $(obj).children("td:eq(1)").text();
						var dataType = $(obj).children("td:eq(2)").text();
						var isPK = $(obj).children("td:eq(3)").text();
						//alert("initData="+initData);
						var fieldInfoObj = {};
						fieldInfoObj["fieldName"] = columnName;
						fieldInfoObj["dataType"] = dataType;
						if (isPK.toUpperCase() == 'PRIMARY') {
							fieldInfoObj["required"] = "1";
							fieldInfoObj["primaryKey"] = "1";
						} else {
							fieldInfoObj["required"] = isPK == 'notNull' ? '1' : '0';
							fieldInfoObj["primaryKey"] = "0";
						}
						fieldInfoObj["mappFieldName"] = "";
						fieldInfoObj["tabName"] = "";
						fieldInfoObj["fieldInitData"] = initData;
						objJson[optionVal].push(fieldInfoObj);
					}
				}
		    });
		}
	}
}

function  getFieldDetailInfo( fieldContent){
    if(fieldContent!=null && fieldContent.length>0){
       var startIndex = fieldContent.indexOf("(");
       var endIndex = fieldContent.lastIndexOf(")");
       var fieldInfo = fieldContent.substring(startIndex+1,endIndex);
       var fieldInfoArray= fieldInfo.split(",");
       return fieldInfoArray;
   }
     return null;
}

//保存处理
function saveInfo(){
	//得到所有的连线
    var obj = jsPlumb.getAllConnections();
    var defaultScope = obj.jsPlumb_DefaultScope;
    if(defaultScope==null || defaultScope==undefined){
        alert('<bean:message bundle="tic-jdbc" key="ticJdbcMappManage.linkfFieldsErrorMessage"/>');
        return;
     }
    var flag= checkPrimaryKey();
     if(flag){
	    //处理连线字段信息
		getLinkedLineInfo();
		
		//选中的目标表
		var selectedTabName="";
	    var options =$("#have_selected_tableList option");
		 if(options!=null && options.length>0){
			 for(var i=0;i<options.length;i++){
	            var optionVal = $(options[i]).val();
	            selectedTabName+=optionVal+",";
			 }
		 }
		  
		if(selectedTabName!=null && selectedTabName.length>0){
			selectedTabName=selectedTabName.substring(0,selectedTabName.length-1);
		}
		// 设置弹出窗口参数值
		Args_Dialog.fdFuncBaseId = $("#fdFuncBaseId").val();
		Args_Dialog.fdFuncBaseName = $("#fdFuncBaseName").val();
		Args_Dialog.fdCompDbcpId = $("select[name='fdCompDbcp']").val();
		Args_Dialog.fdCompDbcpName = $("select[name='fdCompDbcp']").find("option:selected").text();
		Args_Dialog.fdMappConfig=fdMappConfigJson;
		
		//将传入的参数json化
		var params = {};
		var inps = $('.getValueClass');
		inps.each(function() {
			if($(this).val()){
				var param = {};
				//param.name = $(this).attr("tagname");
				param.value = $(this).val();
				param.type = $(this).attr("ctype");
				params[$(this).attr("tagname")]=param;
			}
		});
		var inparamstr = JSON.stringify(params);
		Args_Dialog.fdInParam = inparamstr;
		
		//同步配置
		var selectVal=$("select[name='fdSyncSelectType']").find("option:selected").val(); 
		var fdSyncType={};
		var targetTabArray = [];
		fdSyncType.syncType=selectVal;
		 if(selectVal=='1'){
			 if($("#fullDelId").prop("checked"))
			     fdSyncType.isDel='1';
			 else
				 fdSyncType.isDel='';
		}else{
			fdSyncType.filter=$("select[name='filter']").find("option:selected").val(); 
			fdSyncType.deleteCondition=$("input[name='deleteCondition']").val(); 
			$('#targetTab input').each(function(index, obj){
				var targetTabJson={};
				targetTabJson.targetTabName=obj.id;
				targetTabJson.fieldPk=obj.value.substring(obj.value.indexOf("(")+1,obj.value.indexOf(")"));
				 targetTabArray.push(targetTabJson);
			});	
			fdSyncType.targetTab= targetTabArray;
		} 
		 Args_Dialog.fdSyncType=JSON.stringify(fdSyncType);
		opener.editFormEventFunction_callback();
		window.close();
     }
}

//验证目标表中的主键字段是否被映射
function checkPrimaryKey() {
	var options = $("select[name='have_selected_tableList'] option");
	if (options.length > 0) {
		for ( var i = 0; i < options.length; i++) {
			var optionVal = $(options[i]).val();
			var tabInfo =$("#mapp_" + optionVal);
			if(tabInfo!=null && tabInfo.length>0){
				var trArray = $("#mapp_" + optionVal + ">tbody>tr");
				var flag = false;
				if ($(trArray).length > 0) {
					for ( var j = 0; j < trArray.length; j++) {
						var fieldMessage = $(trArray[j]).children("td:eq(3)")
								.text();
						if (fieldMessage.toUpperCase() == 'PRIMARY') {
							flag = true;
							var targetSpan = $(trArray[j]).children("td:eq(0)").children("span");
							var fieldGenerate=$(trArray[j]).children("td:eq(4)").children("input[name='fieldInitData']").val();
							
							var endpointConn = jsPlumb.getEndpoints(targetSpan)[0].connections;
							
							//如果主键及没有被映射也没有设置主键的生成方式,则不能被保存
							if (endpointConn != null && endpointConn.length < 1 && !(fieldGenerate!=null && fieldGenerate.length>0)) {
								alert("表:" + optionVal + "的主键必须有初始值或进行映射");
								return false;
							}
						}
					}
					//说明该表没有主键
					flag = true;
				}
		   }else{
			   alert(optionVal+'<bean:message bundle="tic-jdbc" key="ticJdbcMappManage.noLinkFieldsMessage"/>');
			   return false;
		   }
		}
		return flag;
	} else {
		alert('<bean:message bundle="tic-jdbc" key="ticJdbcMappManage.noLinkFieldsMessage"/>');
		return false;
	}
}

//处理拉线两端的字段数据
function getLinkedLineInfo() {
	//设置Json模板
	setTabJson();

	// 得到所有的连线
	var obj = jsPlumb.getAllConnections();
	var defaultScope = obj.jsPlumb_DefaultScope;
	if (defaultScope == null || defaultScope == undefined) {
		return false;
	}

	var connInfo;
	if (obj != null && obj.jsPlumb_DefaultScope.length > 0) {
		var jsonObj = objJson;
		connInfo = obj.jsPlumb_DefaultScope;
		for ( var i = 0; i < connInfo.length; i++) {
			//得到目标节点所属的表名称
			var belongTabName = $(connInfo[i].target[0].parentNode).parent().parent().children("tr:eq(0)").text();//$(connInfo[i].target[0].parentNode).parent().parent().attr("id");
			//var indexNum = belongTabName.indexOf("mapp_");
			//belongTabName = belongTabName.substring(indexNum + 5,belongTabName.length);

			// 验证当前表是否被删除
			var currentTab = $("table[id='mapp_" + belongTabName + "']");
			if (currentTab == null || currentTab.length < 1) {
				continue;
			}

			//得到目标节点内容
			//var targetContent = $(connInfo[i].target[0]).text();
			// 得到目标字段名称
			//targetContent.substring(0, targetContent.indexOf("("));
			var targetFieldName =$(connInfo[i].target[0].parentNode).parent().children("td:eq(1)").text();
			

			// 得到源节点内容
			//var sourceContent = $(connInfo[i].source[0]).text();
			// 得到源字段名称
			//var sourceFieldName = sourceContent.substring(0, sourceContent.indexOf("("));
            var sourceFieldName =$(connInfo[i].source[0].parentNode).parent().children("td:eq(3)").text();
            var sourceTabName =$(connInfo[i].source[0].parentNode).parent().children("td:eq(0)").text(); 
			// 获得连线的颜色
			var painStyle = connInfo[i].getPaintStyle();

			// 得到关于目标字段的字段类型，长度等信息
			//var targeFieldInfoArr = getFieldDetailInfo(targetContent);
			
            var targeFieldInfoArr=new Array(2);
            var targetFieldDataType=$(connInfo[i].target[0].parentNode).parent().children("td:eq(2)").text();
            targeFieldInfoArr[0]=targetFieldDataType;
            targeFieldInfoArr[1]= $(connInfo[i].target[0].parentNode).parent().children("td:eq(3)").text();
            // 目标字段初始化 
           	var fieldInitData = $(connInfo[i].target[0].parentNode).parent()
           			.children("td:eq(4)").find("input[name='fieldInitData']").val();
            
			// 该字段值是必须还是非必须
			var requirType = "";
			var primaryKey = "";
			if (targeFieldInfoArr[1].toUpperCase() == 'PRIMARY') {
				primaryKey = '1';
				requirType = '1';
			} else {
				primaryKey = '0';
				requirType = targeFieldInfoArr[1] == 'notNull' ? '1' : '0';
			}
			var tabNameObjs = jsonObj[belongTabName];
			// 移除已经存在的字段
			for (var j = 0, len = tabNameObjs.length; j < len; j++) {
				var columnObj = tabNameObjs[j];
				if (columnObj.fieldName == targetFieldName) {
					tabNameObjs.splice(j, 1); 
					break;
				}
			}
			var fieldTemp = "";
			fieldTemp += '{' + '"' + "fieldName" + '"' + ":" + '"'
						+ targetFieldName + '"' + "," + '"' + "dataType" + '"'
						+ ":" + '"' + targeFieldInfoArr[0] + '"' + "," + '"'
						+ "required" + '"' + ":" + '"' + requirType + '"' + ","
						+ '"' + "primaryKey" + '"' + ":" + '"' + primaryKey
						+ '"' + "," + '"' + "mappFieldName" + '"' + ":" + '"'
						+ sourceFieldName + '"' +","
                        + '"' + "tabName" + '"' +  ":" +'"' + sourceTabName
						+ '"'+ ', "fieldInitData" : "'+ fieldInitData +'"}';
			tabNameObjs.push(JSON.parse(fieldTemp));
		}
		fdMappConfigJson = "";
		fdMappConfigJson = JSON.stringify(jsonObj);
	} else {
		//设置源表的边框为红色
		if(obj == null && obj.jsPlumb_DefaultScope.length<1){
		   alert('<bean:message bundle="tic-jdbc" key="ticJdbcMappManage.noLinkFieldsMessage"/>');
		}
	}
	return true;
}

//获得目标数据库中的表
function getTargetTables(){
	$("#erp_loading_bar").show();
	var dbId = document.getElementsByName("fdCompDbcp")[0].value;
	if(dbId.length<1 ||dbId==null){
		alert('<bean:message bundle="tic-jdbc" key="ticJdbcMappManage.dataSourceMessageTip"/>');
		return false;
	}
	clearTargetTab();
	var url = "ticJdbcLoadDBTablService&dbId=" +dbId;
	var data = new KMSSData();
	data.SendToBean(url, afterCallbackFunction);
}

//当数据源发生变化时,将对应的目标库下拉框，已选表，目标映射等信息去掉。
function clearTargetTab(){
	//清空掉原来所选中的目标表
	var tabIds = $('#have_selected_tableList').find("option");
	if(tabIds!=null && tabIds.length>0){
		for(var index=0;index<tabIds.length;index++){
			var tabId= $(tabIds[index]).val();
		    $("#have_selected_tableList option[id='" + tabId + "']").remove();
		    $("table[id='mapp_" + tabId + "']").parent().parent().remove();
		}
	}
	//删除对应的表映射字段
	$("#have_selected_tableList").find("option").remove();
}

//显示目标数据库表
function afterCallbackFunction(rtn) {
	$("#erp_loading_bar").hide();
	var mapArray = rtn.GetHashMapArray();
	$("#dest_tableList").find("option").remove();
	$("#_tabNum").empty();
	if (mapArray != null && mapArray.length > 0) {
		if("error"==mapArray[0].id){
			alert("加载数据库表失败，错误信息："+mapArray[0].name);
			return;
		}
		var tableInfoHtml = "";
		for ( var i = 0; i < mapArray.length; i++) {
			tableInfoHtml += "<option id='" + mapArray[i].name + "' value='"
					+ mapArray[i].name + "' >" + mapArray[i].name + "</option>";
		}
		$("#dest_tableList").append(tableInfoHtml);
		$("#_tabNum").text(mapArray.length);
	}
	checkJsPlumbInit();
}

//设置已经选中的表
function selectedTable(thisObj) {
	if ($(thisObj).val() != null && $(thisObj).val().length > 0) {
		var tabName = $(thisObj).val();
		var options = $("select[name='have_selected_tableList'] option");
		$.each(tabName, function(i, n) {
			var breakFlag = false;
			$.each(options, function(i, m) {
				if ($.trim(n) == $.trim(m.value)) {
					breakFlag = true;
					return;
				}
			});
			if (!breakFlag) {
				var tableInfoHtml = "<option id='" + n + "' value='" + n + "'>"
						+ n + "</option>";
				$("#have_selected_tableList").append(tableInfoHtml);
			}
		});
	}
}


//删除当前行
function deleteCurrentRow() {
	var TabId = $('#have_selected_tableList').find("option:selected").val();
	$("#have_selected_tableList option[id='" + TabId + "']").remove();

	//重新实例化表映射
	//setTimeout("resetTabLine();",50);
	//resetTabLine(TabId);
	//删除对应的表映射字段
	$("table[id='mapp_" + TabId + "']").parent().parent().remove();

	checkJsPlumbInit();
}

function getTableFielInfor() {
	var tableName = "";
	var options = $("select[name='have_selected_tableList'] option");
	if (options != null && options.length > 0) {
		for ( var i = 0; i < options.length; i++) {
			var optionVal = $(options[i]).val();
			tableName += optionVal + ",";
		}

		if (tableName != null && tableName.length > 0) {
			tableName = tableName.substring(0, tableName.length - 1);
		}

		var dbId = document.getElementsByName("fdCompDbcp")[0].value;
		if (dbId.length < 1 || dbId == null) {
			alert('<bean:message bundle="tic-jdbc" key="ticJdbcMappManage.dataSourceMessageTip"/>');
			return false;
		}

		if (tableName.length < 1 || tableName == null) {
			alert('<bean:message bundle="tic-jdbc" key="ticJdbcMappManage.tablesSourceMessageTip"/>');
			return false;
		}
		var data = new KMSSData();
		data.SendToUrl(Com_Parameter.ContextPath
								+ "tic/jdbc/tic_jdbc_mapp_manage/ticJdbcMappManage.do?method=getTabFieldInfo&dbId="
								+ dbId + "&tableName=" + tableName,
						destionTabelField, false);
	}
}


//显示目标表中的字段信息
function destionTabelField(http_request) {
	// 以下得到保存好的映射数据，为了获取字段初始化值，by qiujh
	var fdMappConfigJsonObj = fdMappConfigJson;
	var tabNameJsonObj = null;
	if (fdMappConfigJsonObj != null && fdMappConfigJsonObj.length > 0) {
		tabNameJsonObj = JSON.parse(fdMappConfigJsonObj);
	}
	var fdMappConfigJson = http_request.responseText;
	if (fdMappConfigJson != null && fdMappConfigJson.length > 0) {
		var tabNameJson = JSON.parse(fdMappConfigJson);
		var tableInfoHtml = "<table cellspacing='0' cellpadding='0'>";
		var obj = tabNameJson[0];
		for ( var key in obj) {
			if (key != null && key != undefined) {
				// 字段信息，包括字段初始化值
				var tableInfo = null;
				if (tabNameJsonObj != null) {
					tableInfo = tabNameJsonObj[key];
				}
				var tempHtml="<tr>";
				var tempInfoHtml="<td style='border-color: #FF0000;' >"; 
				var tabHtml= "<table id='mapp_" + key
						+ "' border=0 class='tb_normal' width='100%' cellspacing='0' cellpadding='0'>";
						tabHtml += "<tr><td class='td_normal_title' align=center colspan=5>"
						+ key + "</td></tr>";
                var havePKFlag=false;
				for ( var i = 0; i < obj[key].length; i++) {
					var columnObj = eval("(" + obj[key][i] + ")");
					var fieldValue = columnObj.fieldName;
					// 遍历取字段初始化值
					var fieldInitData = "";
					if (tableInfo != null) {
						for (var j = 0, len = tableInfo.length; j < len; j++) {
							var fieldInfo = tableInfo[j];
							if (fieldValue == fieldInfo.fieldName) {
								fieldInitData = fieldInfo.fieldInitData;
								break;
							}
						}
						
					}
					var tempPk=eval("(" + obj[key][i] + ")").isNull;
					    tempPk=$.trim(tempPk);
					    tempPk =tempPk.toUpperCase();
					    
					if(tempPk=='PRIMARY'){
						havePKFlag=true;
					}
					// 字段的初始化数据，可使用公式定义器
					var fieldInitDataHtml = '<input type="text" size="13" onblur ="changeTabBorderColor(\'fieldInitData'+ key + i +'\')"; class="inputsgl" name="fieldInitData" '
							+ 'id="fieldInitData'+ key + i +'" value=\''+ fieldInitData +'\'/>'
							+ '<img src="'+Com_Parameter.ContextPath 
							+ 'tic/core/provider/resource/tree/img/edit.gif" style="cursor:pointer;" '
							+ 'onclick="getFieldInitData(\'fieldInitData'+ key + i +'\');" />';
					tabHtml += "<tr><td width='2'>&nbsp;<span class='tright'></span></td><td>"
							+ columnObj.fieldName
							+ "</td><td>"
							+ columnObj.dataType
							+ "</td><td>"
							+ columnObj.isNull
							+ "</td><td>"
							+ fieldInitDataHtml
							+ "</td></tr>";

				}
	            if(!havePKFlag){
	            	tempInfoHtml="<td>";
	            }
	            tabHtml += "</table></td></tr>";
	            tableInfoHtml+=tempHtml+tempInfoHtml+tabHtml;
			}
			
		}
		tableInfoHtml += "</table>";
		$("#_dest_table_field").children().remove();
		$("#_dest_table_field").append(tableInfoHtml);
		$("#_destTableField").css("display", "block");
		checkJsPlumbInit();
	}
}

//获取源数据库表的字段信息
function getTableField() {
	var fd_sourceSql = $("textarea[name='fdDataSourceSql']").val();
	var funcId = document.getElementsByName("fdFuncBaseId")[0].value;
	if (funcId.length < 1 || funcId == null) {
		alert('<bean:message bundle="tic-jdbc" key="ticJdbcMappManage.dataSourceMessageTip"/>');
		return false;
	}

	if (fd_sourceSql == null || fd_sourceSql.length < 1) {
		alert('<bean:message bundle="tic-jdbc" key="ticJdbcMappManage.dataSourceSqlMessageTip"/>');
		return false;
	}
	var data = new KMSSData();
	data.SendToUrl(Com_Parameter.ContextPath
			+ "tic/jdbc/tic_jdbc_mapp_manage/ticJdbcMappManage.do?method=getTabFieldInfo&funcId="
			+ funcId + "&sourceSql=" + encodeURI(fd_sourceSql),
	afterFieldFunction, false);
	
}

//显示源数据库表中的字段信息
function afterFieldFunction(http_request) {
	$("#_errorInfor").empty();
	var fdMappConfigJson = http_request.responseText;
	if (fdMappConfigJson != null && fdMappConfigJson.length > 0) {
		 var tabNameJson="";
		try{
		   tabNameJson = JSON.parse(fdMappConfigJson);
		}catch(err){
			$("#_errorInfor").text(alert('<bean:message bundle="tic-jdbc" key="ticJdbcMappManage.sqlError.infor"/>'));
			$("#_errorInfor").css('color','red');
			return;
		}
		var tableInfoHtml = "";
		var fd_message_1 = '<bean:message bundle="tic-jdbc" key="ticJdbcMappManage.tableField.allowNull"/>';
		var fd_message_2 = '<bean:message bundle="tic-jdbc" key="ticJdbcMappManage.tableField.dataType"/>';
		var fd_message_3 = '<bean:message bundle="tic-jdbc" key="ticJdbcMappManage.tableField.columnName"/>';
		tableInfoHtml = "<tr align='center'><td class='td_normal_title' style='display:none;'>tableName</td><td class='td_normal_title'>"
				+ fd_message_1 + "</td><td class='td_normal_title'>"
				+ fd_message_2
				+ "</td><td class='td_normal_title' colspan='2'>"
				+ fd_message_3 + "</td></tr>";
		var obj = tabNameJson[0];
		for ( var key in obj) {
			if (key != null && key != undefined) {
				for ( var i = 0; i < obj[key].length; i++) {
					if (key != null && key != undefined) {
						// tableInfoHtml+="<tr  align=left><td class='tleft'>"+eval("("+obj[key][i]+")").fieldName+"("+eval("("+obj[key][i]+")").dataType+","+eval("("+obj[key][i]+")").isNull+")</td></tr>";
						tableInfoHtml += "<tr align='left'><td style='display:none;'>"
								+ eval("(" + obj[key][i] + ")").tabName
							    + "</td><td>"
								+ eval("(" + obj[key][i] + ")").isNull
								+ "</td><td>"
								+ eval("(" + obj[key][i] + ")").dataType
								+ "</td><td>"
								+ eval("(" + obj[key][i] + ")").fieldName
								+ "</td><td width='2'>&nbsp;<span class='tleft'></span></td></tr>";
					}
				}
			}
		}

		$("#source_table_field tr").remove();
		$("#source_table_field").append(tableInfoHtml);
		$("#_source_table_field").css("display", "block");
		//为源数据表设置可拉线操作
		checkJsPlumbInit();

	}
}

function checkJsPlumbInit() {
	//$("div[class='_jsPlumb_endpoint  ui-draggable ui-droppable']").remove();
	var sourceTr = $("#source_table_field>tbody>tr");
	var desTab = $("#_dest_table_field").children("table");
	var sourceFlag = false;
	var desFlag = false;
	if (sourceTr != null && sourceTr.length > 0) {
		sourceFlag = true;
	}

	if (desTab != null && desTab.length > 0) {
		desFlag = true;
	}

	if (sourceFlag && desFlag) {
		//保存原来的连线信息
		getLinkedLineInfo();

		// 清空原来的连线节点
		jsPlumb.deleteEveryEndpoint();

		// 去掉原来的连线
		jsPlumb.detachEveryConnection();

		// 重新初始化表
		jsPlumbDemo.init();
		// 设置鼠标移动到圆点变粗
		jsPlumbDemo.setTicJsPlumb_endpoint();
		// 将原来字段的映射重新展现出来
		setFieldLinked();
	}
}


//连线处理
function setFieldLinked() {
	if (fdMappConfigJson != null && fdMappConfigJson.length > 0) {
		var tabNameJson = JSON.parse(fdMappConfigJson);
		var tableInfoHtml = "";
		for ( var key in tabNameJson) {
			if (key != null && key != undefined) {
				for ( var i = 0; i < tabNameJson[key].length; i++) {
					var fieldInfo = tabNameJson[key][i];
					var fieldName = fieldInfo.fieldName;
					var mappFieldName = fieldInfo.mappFieldName;
					key = $.trim(key);
					setFieldLinkedLine(key, fieldName, mappFieldName);
				}
			}
		}
	}
}
//处理字段映射连线
function setFieldLinkedLine(targetTab, targetField, soureField) {
	var trArray = $('#mapp_' + targetTab + '>tbody>tr');
	if (trArray != null && trArray.length > 0) {
		var targetSpan;

		// 找到目标字段所在的行
		for ( var i = 0; i < trArray.length; i++) {
			var contentValue = $(trArray[i]).children("td:eq(1)").text();
			var targetFieldName = contentValue;//contentValue.substring(0, contentValue.indexOf("("));

			if ($.trim(targetField) == $.trim(targetFieldName)) {
				targetSpan = $(trArray[i]).children("td:eq(0)").children("span");
				break;
			}
		}
	}

	//查找源字段所在的位置  
	var sourceSpan = "";
	var sourceTrArr = $("#source_table_field>tbody>tr");
	if (sourceTrArr != null && sourceTrArr.length > 0) {
		for ( var j = 0; j < sourceTrArr.length; j++) {
			var sourceContentVal = $(sourceTrArr[j]).children("td:eq(3)").text();
			var sourceFieldName = sourceContentVal;//sourceContentVal.substring(0,sourceContentVal.indexOf("("));
			if ($.trim(soureField) == $.trim(sourceFieldName)) {
				sourceSpan = $(sourceTrArr[j]).children("td:eq(4)").children("span");
				break;
			}
		}
	}

	//得到源字段和目标字段所在的连接点
	var endpointTarget;
	var endpointSource;
	if (targetSpan != null && targetSpan.length > 0) {
		endpointTarget = jsPlumb.getEndpoints(targetSpan);
	}
	if (sourceSpan != null && sourceSpan.length > 0) {
		endpointSource = jsPlumb.getEndpoints(sourceSpan);
	}
	if (endpointTarget != null && endpointSource != null) {
		jsPlumb.connect( {
			source : endpointSource[0],
			target : endpointTarget[0]
		});
	}
}

//=========================修改处理===========================================
//初始化数据
var Args_Dialog = {};
var dialogObject = null;
var fdMappConfigJson;
$(document).ready(function() {
	if(window.opener){
		Args_Dialog = window.opener.dialogObject;
	}else if(window.parent.opener){
		Args_Dialog = window.parent.opener.dialogObject;
	}else{
		alert("获取不到父页面信息");
	}
	dialogObject = Args_Dialog;
	// 设置页面默认数据及样式
	
	init_setInParams();
	if("" != Args_Dialog.fdCompDbcpId){
		$("select[name='fdCompDbcp']").val(Args_Dialog.fdCompDbcpId);
		setTimeout("jdbc_mapp_load();", 50);
	}
	if(""!= Args_Dialog.fdMappConfig){
		 fdMappConfigJson=Args_Dialog.fdMappConfig;
	}
	if(""!= Args_Dialog.fdSyncType){
		var fdSyncType=JSON.parse(Args_Dialog.fdSyncType);
		$("select[name='fdSyncSelectType").val(fdSyncType.syncType);
		getMappInfor();
		if(fdSyncType.syncType=='1'){
			if(fdSyncType.isDel=='1'){
				$("#fullDelId").prop("checked",true);
			}else{
				$("#fullDelId").prop("checked",false);
			}
		}else{
			$("select[name='filter").val(fdSyncType.filter);
			$("#deleteCondition").val(fdSyncType.deleteCondition)
		}
	}
});

function jdbc_mapp_load() {

	    //设置源数据源SQL
		var fdDataSourceSql =$("textarea[name='fdDataSourceSql']").val();
		//生成源映射数据表
		if (fdDataSourceSql != null && fdDataSourceSql.length > 0) {
			getTableField();
		}
		//获得目标数据库中的表
		getTargetTables();
		
		//设置目标数据源被选中的表
		if (fdMappConfigJson != null && fdMappConfigJson.length > 0) {
			var tabNameJson = JSON.parse(fdMappConfigJson);
			var tableInfoHtml = "";
			for ( var key in tabNameJson) {
				if (key != null && key != undefined) {
					tableInfoHtml += "<option id='" + key + "' value='" + key
							+ "' >" + key + "</option>";
				}
			}
			$('#have_selected_tableList').append(tableInfoHtml);
		}
		
		//生成目标数据表
		getTableFielInfor();
		
		setTabBorderColor();
		//设置字段连线
		//jsPlumb.deleteEveryEndpoint();
		//setTimeout("jsPlumbDemo.init();", 50);
		setTimeout("setFieldLinked();", 50);

}
//设置目标表的边框是否需要显示为红色
function setTabBorderColor(){
	var options = $("select[name='have_selected_tableList'] option");
	if (options != null && options.length > 0) {
		for ( var i = 0; i < options.length; i++) {
			var tableName = $(options[i]).val();
			var tabId="mapp_" + tableName;
			var trArray = $("#"+tabId + ">tbody>tr");
			if ($(trArray).length > 0) {
				for ( var j = 0; j < trArray.length; j++) {
					var fieldMessage = $(trArray[j]).children("td:eq(3)").text();
					if (fieldMessage.toUpperCase() == 'PRIMARY') {
						var targetSpan = $(trArray[j]).children("td:eq(0)").children("span");
						var fieldGenerate=$(trArray[j]).children("td:eq(4)").children("input[name='fieldInitData']").val();
						var endpointConn = jsPlumb.getEndpoints(targetSpan)[0].connections;
						if (endpointConn != null && endpointConn.length > 1 || (fieldGenerate!=null && fieldGenerate.length>0)) {
							 $("#"+tabId).parent().removeAttr("style");
							//$("#"+tabId).parent().css("border-color","none");
						}
				      }
				}
			}
		}
	}
}

// 解决火狐不能居中
function CalcShowModalDialogLocation(dialogWidth, dialogHeight) {
    var iWidth = dialogWidth;
    var iHeight = dialogHeight;
    var iTop = (window.screen.availHeight) / 2;
    var iLeft = (window.screen.availWidth-300) / 2;
    return 'dialogWidth:' + iWidth + 'px;dialogHeight:' + iHeight + 'px;dialogTop: ' + iTop + 'px; dialogLeft: ' + iLeft + 'px;center:yes;scroll:no;status:no;resizable:0;location:no';
}

function getFieldInitData(idField) {
	var idFieldValue = $("#"+ idField).val();
	//var params = {"_key": idFieldValue};
	dialogObject["_key"]=idFieldValue;
	dialogObject["_idField"]=idField;
	//var value = window.showModalDialog(Com_Parameter.ContextPath
	//		+ "tic/core/resource/jsp/showModalDialog_tree.jsp" 
	//		+ "?springBean=ticJdbcExpressionBean&value="+encodeURIComponent(idFieldValue), params,
	//		CalcShowModalDialogLocation("400px", "450px"));
	
	winStyle= "resizable=yes,scrollbars=yes,menubar=no,toolbar=no,status=no,width=400px,height=450px,screenX=10px,screenY=10px,top=300px,left=400px";
	window.open(Com_Parameter.ContextPath
			+ "tic/core/resource/jsp/showModalDialog_tree.jsp" 
			+ "?springBean=ticJdbcExpressionBean&value="+encodeURIComponent(idFieldValue),"",winStyle);
	
}

function getFieldInitData_callback(){
	var value = dialogObject["value"];
	if (value != undefined) {
		$("#"+ dialogObject["_idField"]).val(value._key);
		changeTabBorderColor(dialogObject["_idField"]);
	}
}

function changeTabBorderColor(idField){
	var value=$("input[id='"+idField+"']").attr("value");
	var tabId =$("#"+ idField).parent().parent().parent().parent().attr("id");
	var fieldContext =$("#"+ idField).parent().prev().text();
	if(fieldContext.toUpperCase() == 'PRIMARY') {
	  if(value!=null && value.length>0){
		  $("#"+tabId).parent().removeAttr("style");
	  }else{
		 var targetSpan = $("#"+ idField).parent().parent().children("td:eq(0)").children("span");
		 var endpointConn = jsPlumb.getEndpoints(targetSpan)[0].connections;
		 //var styleValue =$("#"+tabId).parent().attr("style");
		 //如果主键没有被映射设置的主键生成方式也为空，则设置表的边框颜色 
		if (endpointConn != null && endpointConn.length < 1 ) {
			 $("#"+tabId).parent().attr("style","border-color: #FF0000");
		}
      }
	}
}
</script>
<%@ include file="/resource/jsp/edit_down.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/list_top.jsp"%>
<div class="lui-relation-event-dialog">
<!-- #131272-选择框弹出窗口的确定按钮鼠标点击事件范围太小-开始 -->
	<%-- <div id="optBarDiv">
		<input type="button" id='btn_search'
		value="${lfn:message('button.search')}"
		onclick="searchSelect();">
		<input type="button"
		value="${lfn:message('button.ok')}"
		onclick="checkSelect();">
		
		
	</div> --%>
	<div id="choiceDiv">
		<input type="button" id='btn_search'
		value="${lfn:message('button.search')}"
		onclick="searchSelect();">
		<input type="button" id="btn_determine"
		value="${lfn:message('button.ok')}"
		onclick="checkSelect();">
		
		
	</div>
<!-- #131272-选择框弹出窗口的确定按钮鼠标点击事件范围太小-结束 -->
<style>
#My_List_Table{ margin:0px 0 !important; border:0; border-collapse:collapse;}
#My_List_Table td{ border:0; border-collapse:collapse;padding:0px 10px;}
#My_List_Table tr{magrin:0 10px}

.lui-relation-event-dialog {
    margin-top: 40px;
}

.pageto_txt{
  margin: 0 4px;
  padding: 0 4px;
  width: 28px;
  height: 24px;
  line-height: 28px;
  text-align:center;
  border-radius: 4px;
  border: 1px solid #d5d5d5;
  color:#9e9e9e;
  font-size: 12px;
  position: relative;
  top: 0;
}

.lui_paging_btn {
  width: 28px;
  height: 24px;
  padding-left: 4px;
  padding-right: 8px;
  color:#fff;
  border-radius: 4px;
  border: 1px solid #c8c8c8;
  background: #c8c8c8;
  position: relative;
  top: 0;
}

.lui_paging_btn:hover{
  color: #fff!important;
  border-color: #4285f4;
  background: #4285f4!important;
}

.lui_paging_btn:hover{
  background:#f8f8f8;
  color:#fd7708;
}

.page_no_cls:hover{
  color:#4285f4;
}

.selectWrap {
	margin-left: 10px;
	margin-top: 5px;
}

.selectedContent {
	margin-right: 10px;
}


</style>
<script type="text/javascript">
	Com_IncludeFile("jquery.js");
	Com_IncludeFile('json2.js');
</script>
<% %>
<script>
	var objData = null;
	var callback = null;
	var parentWindow = null;
	var paramType='${JsParam.paramType}';
	parentWindow = window.opener || window.parent;
	objData=parentWindow.Com_Parameter.Dialog[paramType].data;
	callback=parentWindow.Com_Parameter.Dialog[paramType].AfterShow;
	
	objData.dialogWindow = window; 
	
	var pageObjData={};
	//钉钉高级审批模式下引入样式
	if ("true" === parentWindow.Com_Parameter.dingXForm) {
		Com_IncludeFile("relation_event.css", Com_Parameter.ContextPath+"sys/xform/designer/relation_event/","css",true);
	}
	$(function(){
		if ("true" === parentWindow.Com_Parameter.dingXForm) {
			$(".lui-relation-event-buttons").show();
		}
	})
	
</script>
<script>
	//取消冒泡
	function cancelBubble(e){
		// 如果提供了事件对象，则这是一个非IE浏览器
	    if ( e && e.stopPropagation ) {
	        // 因此它支持W3C的stopPropagation()方法 
	        e.stopPropagation();
	    } else { 
	        // 否则，我们需要使用IE的方式来取消事件冒泡
	        window.event.cancelBubble = true;
	    }
	}
	//整行单击时选中
	function checkedTRRowsClick(tr){
		var obj=$(tr).find("input[name='List_Selected']")[0];
		if(typeof(obj.checked) != 'undefined'){
			obj.checked = obj.checked == true ? false : true;
		}
		//trigger会默认冒泡，而triggerHandler不会
		$(obj).triggerHandler($.Event("click"));
	}
	var checkedRows=[];
	//表单数据集成控件带有搜索条件时，多次查询无法多选
	var rowsContext = [];
	//行点击事件,选中或者取消选中
	function checkedRowsClick(obj,e){
		cancelBubble(e);
		if(obj.type=='radio'){
			checkedRows=[];
			rowsContext = [];
			var curRow=objData.data.rows[obj.value];
			curRow.pageSize=objData.paramsJSON.pageSize;
			curRow.checkedIndex=obj.value;
			checkedRows.push(curRow);
			//表单数据集成控件带有搜索条件时，多次查询无法多选
			var rowContext = objData.data.rowsContext[obj.value];
			rowsContext.push(rowContext);
		} else {
			var curRow=objData.data.rows[obj.value];
			//判断当前点击的行是否已经选中过
			var checked = isInCheckedRows(curRow);
			if(obj.checked && !checked){ //选中, 但没有添加到已选列表
				addCheckedRows(curRow, obj);
			} else { //已经选中则移除掉
				removeCheckedRow(curRow, obj);
			}
			//取消选中，取消全选
			if(document.getElementById("checkedAll_Box")){
				if(obj.checked){
					if(isCheckedAll(objData, checkedRows)){
						document.getElementById("checkedAll_Box").checked=true;
					}
				} else {
					document.getElementById("checkedAll_Box").checked=false;
				}
			}
		}
		createSelectedText();
	}
	
	function checkSelect(){
		
		if (checkedRows.length == 0) {
			alert("<bean:message key="page.noSelect"/>");
			return false;
		}
		if(callback){
			var rtn={};
			rtn.checkedRows=checkedRows;
			//表单数据集成控件带有搜索条件时，多次查询无法多选
			rtn.rowsContext=rowsContext;
			rtn.objData=objData;
			if (checkedRows && checkedRows.length>0 && checkedRows[0].pageSize) {
				rtn.objData = pageObjData[checkedRows[0].pageSize];
			}
			callback(rtn);
		}
		//window.returnValue=checkedValues;
		//window.opener.setValueByRowIndex(checkedValues,data);
		window.$dialog.hide();
		return true;
	}
	
	function cancelSelect(){
		window.$dialog.hide();
		return true;
	}
	
	//全选按钮点击事件
	function checkAll(obj){
		//没有id标识,清空已选列表,不然会重复
		if (!objData.paramsJSON.hasRowIdentity) {
			checkedRows = [];
		}
		
		if(obj.checked){
			$("input[name='List_Selected']").each(function(){
				var curRow=objData.data.rows[this.value];
				addCheckedRows(curRow, this);
				this.checked=true;
			});
		} else {
			//如果已经选中了,就需要移除
			$("input[name='List_Selected']").each(function(){
				if(this.checked){
					var curRow = objData.data.rows[this.value];
					removeCheckedRow(curRow, this.value);
				}
				
			});
			$("input[name='List_Selected']").removeAttr("checked");
		}
		createSelectedText();
	}
	
	function outerSerachSelect(){
		//执行搜索,清空已选列表
		//checkedRows=[];
		//勾了追加搜索选项,不清空已选列表
		if ((!objData.appendSearchResult || 
				objData.appendSearchResult == "false") && !objData.paramsJSON.hasRowIdentity) {
			checkedRows=[];
		}
		var searchsJSON=[];
		$("input[name='outerSearchCondition']").each(function(){
			for(var i=0; i<objData.outerSearchParamsJSON.length;i++){
				if($(this).attr("tagName")==objData.outerSearchParamsJSON[i].tagName || 
						($(this).attr("data-group") && objData.outerSearchParamsJSON[i].group && objData.outerSearchParamsJSON[i].group == $(this).attr("data-group"))){
					// 同组的属性值一样
					var obj={};
					$.extend(true,obj, objData.outerSearchParamsJSON[i]);
					obj.value=$(this).val();
					searchsJSON.push(obj);
				}
			}
		});
		//是否分页,分页则搜索后置为首页
		if(/[\d][1]/g.test(objData.paramsJSON.listRule)){
			objData.paramsJSON.pageSize=1;
		}
		objData.paramsJSON.outerSearchs=JSON.stringify(searchsJSON);
		parentWindow.loadEventRows(objData,reLoadTableRows);
	}
	function searchSelect(){
		//执行搜索,清空已选列表
		checkedRows=[];
		
		var searchsJSON=JSON.parse(objData.paramsJSON.searchs);
		$("input[name='searchText']").each(function(){
			var isIn=false;
			for(var i=0;i<searchsJSON.length;i++){
				if(searchsJSON[i].fieldId==this.id){
					searchsJSON[i].fieldValue=this.value;
					isIn=true;
					break;
				}
			}
			//不在搜索列表里面,初始化一个
			if(!isIn){
				var tempSearch={};
				tempSearch.fieldId=this.id;
				tempSearch.fieldValue=this.value;
				searchsJSON.push(tempSearch);
			}
		});
		//是否分页,分页则搜索后置为首页
		if(/[\d][1]/g.test(objData.paramsJSON.listRule)){
			objData.paramsJSON.pageSize=1;
		}
		objData.paramsJSON.searchs=JSON.stringify(searchsJSON);
		parentWindow.loadEventRows(objData,reLoadTableRows);
	}
</script>
<div id='outerSearchBar' style="margin-top:10px;padding:3px;border-bottom:1px dashed #d2d2d2;">
</div>
<div id='selectedDatas' class='selectWrap'></div>
<div id='tableContent'></div>
<div id='tablePage' style="margin:5px 0px"></div>
</div>
<!-- 提交按钮 -->
<div class="lui-relation-event-buttons" style="display:none;">
	<span onclick="checkSelect();">
		${ lfn:message('button.ok') }
	</span>
	<span onclick="cancelSelect();">
		${ lfn:message('button.cancel') }
	</span>
</div>					
<script>
$(function(){
	loadSelectedRows(objData);
	loadTable(objData);
	loadPage(objData);
	
	if(objData.outerSearchParamsJSON&&objData.outerSearchParamsJSON.length>0){
		var html="";
		var searchItemNum = 0;
		for(var i=0; i<objData.outerSearchParamsJSON.length;i++){
			// 如果设置了隐藏，则不显示			
			if(objData.outerSearchParamsJSON[i].isHidden && objData.outerSearchParamsJSON[i].isHidden == 'true'){
				continue;
			}
			html+="&nbsp;&nbsp;"+objData.outerSearchParamsJSON[i].sdesc+" ";
			//stype为0 这里默认都是用文本框 以后可能会有多种其他类型
			html += "<input type='text' class='inputsgl' style='height:22px;width:180px' name='outerSearchCondition' tagName='"+objData.outerSearchParamsJSON[i].tagName+"'  value='"+objData.outerSearchParamsJSON[i].value+"'";
			// 如果有组属性，则增加
			if(objData.outerSearchParamsJSON[i].group && objData.outerSearchParamsJSON[i].group != ''){
				html += " data-group='"+ objData.outerSearchParamsJSON[i].group +"'";
			}
			html += "/>";
			++searchItemNum;
			if (searchItemNum == objData.oneRowSearchNum){
				if (i != (objData.outerSearchParamsJSON.length - 1))
				html += "<br/>";
				searchItemNum = 0;
			}
		}
		var btnList = "${lfn:message('button.list')}";
		html+=' <input type="button" id="btn_outerSearch" style="height:22px;padding:0 10px;" value="'+btnList+'" onclick="outerSerachSelect();">';
		//html+=' <label><input type="checkbox" name="outerSearch_append" value="1"/>追加搜索结果</label>';
		$("#outerSearchBar").html(html);
		
		
	}
	//增加回车搜索功能
	$("input[name='outerSearchCondition']").keypress(function(event){
		 var keycode = (event.keyCode ? event.keyCode : event.which);  
		    if(keycode == '13'){
		    	outerSerachSelect();
		    }
		
	});
});
</script>

<script>
var win=this;
function loadPage(objData){
	//校验是否需要分页
	if(!/[\d][1]/g.test( objData.paramsJSON.listRule)){
		return;
	}
	var page=[];
	var totalRows=objData.data.totalRows;
	 var totalPage=0;
	 var currentPageSize=0;
	if(totalRows>0){
		var pageNum=objData.paramsJSON.pageNum;
	    currentPageSize=parseInt(objData.paramsJSON.pageSize);
	    totalPage=totalRows % pageNum == 0 ? (totalRows / pageNum) : Math.ceil(totalRows / pageNum) ;
	}
   
    page.push("<span class='page_no_cls lui-event-first-page' style='margin:5px 5px;cursor:pointer;' onclick='event_first_page();'>${lfn:message('page.first')}</span>");
    page.push("<span class='page_no_cls lui-event-prev-page' style='margin:5px 5px;cursor:pointer;' onclick='event_prve_page();'>${lfn:message('page.thePrev')}</span>");
    
    if(totalPage<= 15){
    	for(var z=0;z<totalPage;z++){
    	    var totalPageTemp="\'"+z+"\'";
    	    if((currentPageSize-1)==z){
                page.push('<span class="page_no_cls lui-event-page-size" style="margin:5px 5px;cursor:pointer;color:#F00;" onclick="event_pageSize_page('+z+');">'+(z+1)+'</span>');
            }else{
                page.push('<span class="page_no_cls lui-event-page-size" style="margin:5px 5px;cursor:pointer;" onclick="event_pageSize_page('+z+');">'+(z+1)+'</span>');
            }
    	}
    }else{
    	var beginPage=objData.paramsJSON.pageSize-1;
    	var endPage=objData.paramsJSON.pageSize+ 13;
    	//如果begin减1后为0,设置起始页为1,最大页为15
    	if(beginPage-1<= 0){
    		beginPage=1;
    		endPage=15;
    	}
    	
    	//如果end超过最大页,设置起始页=最大页-13
    	if(endPage>totalPage){
    		beginPage=totalPage-13;
    		endPage=totalPage;
    	}
    	
    	for(var z=beginPage;z<=endPage;z++){
    	    if(currentPageSize==z){
                page.push('<span class="page_no_cls lui-event-page-size lui-event-page-size" style="margin:5px 5px;cursor:pointer;color:#F00;" onclick="event_pageSize_page('+(z-1)+');">'+(z)+'</span>');
            }else{
                page.push('<span class="page_no_cls lui-event-page-size lui-event-page-size" style="margin:5px 5px;cursor:pointer;" onclick="event_pageSize_page('+(z-1)+');">'+(z)+'</span>');
            }
    	}
    }
	
	
    page.push("<span class='page_no_cls lui-event-next-page' style='margin:5px 5px;cursor:pointer;' onclick='event_next_page();'>${lfn:message('page.theNext')}</span>");
    page.push("<span class='page_no_cls lui-event-page-to' style='margin:5px 5px;cursor:pointer;'>${lfn:message('page.to')}<input type='text' class='pageto_txt lui-event-page-text' value='"+currentPageSize+"' name='txtPageTo' id='txtPageTo'/>${lfn:message('page.page')} <input type='button' class='lui_paging_btn' value='Go' style='cursor:pointer' onclick='event_pageto_page()'/></span>");
    page.push("<span class='page_no_cls lui-event-total-page' style='margin:5px 5px;cursor:pointer;'>${lfn:message('page.total')}"+totalPage+"${lfn:message('page.page')}</span>");
    page.push('<span class="page_no_cls lui-event-last-page" style="margin:5px 5px;cursor:pointer;" onclick="event_pageSize_page('+(totalPage-1)+');">${lfn:message('page.last')}</span>');
    
	$("#tablePage").html(page.join(""));
}
function event_first_page(){
	//分页跳转取消选中
	$("#checkedAll_Box").prop("checked",false);
	
	objData.paramsJSON.pageSize=1;
	parentWindow.loadEventRows(objData,reLoadTableRows);
}

function checkNum(opNum){
	if(opNum==""){
		return false;
	}
	//判断字符串是否为数字 ，判断正整数用/^[1-9]+[0-9]*]*$/
	var reg=/^[0-9]+.?[0-9]*$/; 
	if(!reg.test(opNum)){
	    return false;
   }
   return true;
}
	
	
function event_pageto_page(){
	var totalRows=objData.data.totalRows;
	var totalPage=0;
	if(totalRows>0){
		var pageNum=objData.paramsJSON.pageNum;
	    var currentPageSize=parseInt(objData.paramsJSON.pageSize);
	    totalPage=totalRows % pageNum == 0 ? (totalRows / pageNum) : Math.ceil(totalRows / pageNum) ;
	}
	
	if(!checkNum($("#txtPageTo").val())){
		alert("${lfn:message('page.must_be_int')}");
		return ;
	}
	//分页跳转取消选中
	$("#checkedAll_Box").prop("checked",false);
	
	var pageTo=parseInt($("#txtPageTo").val());
	if(pageTo>totalPage){
		pageTo=totalPage;
	}
	objData.paramsJSON.pageSize=pageTo;
	parentWindow.loadEventRows(objData,reLoadTableRows);
}
function event_prve_page(){
	if(objData.paramsJSON.pageSize==1){
		alert('<bean:message bundle="sys-xform-base" key="sysForm.relevance.firstPage" />');
		return ;
	}
	//分页跳转取消选中
	$("#checkedAll_Box").prop("checked",false);
	
	objData.paramsJSON.pageSize=parseInt(objData.paramsJSON.pageSize)-1;
	parentWindow.loadEventRows(objData,reLoadTableRows);
}
function event_next_page(){
	if(objData.data.rows.length<objData.paramsJSON.pageNum){
		alert('<bean:message bundle="sys-xform-base" key="sysForm.relevance.lastPage" />');
		return ;
	}
	//分页跳转取消选中
	$("#checkedAll_Box").prop("checked",false);
	
	objData.paramsJSON.pageSize=parseInt(objData.paramsJSON.pageSize)+1;
	parentWindow.loadEventRows(objData,reLoadTableRows);
}

function event_pageSize_page(pageSize){
	//分页跳转取消选中
	$("#checkedAll_Box").prop("checked",false);
    objData.paramsJSON.pageSize=parseInt(pageSize)+1;
    parentWindow.loadEventRows(objData,reLoadTableRows);
}


function loadTable(objData){
	var table=[];
	table.push('<table id="My_List_Table">');
	
	table.push(loadTableHeader(objData));
	table.push(loadTableSearch(objData));
	table.push(loadTableRows(objData));
	
	table.push('</table>');
	$("#tableContent").html(table.join(""));
	if(isCheckedAll(objData, checkedRows)){
		$("#checkedAll_Box").prop("checked",true);
	} else {
		$("#checkedAll_Box").prop("checked",false);
	}
}
function reLoadTableRows(objData){
	loadPage(objData);
	
	var rows=loadTableRows(objData);
	//保留标题行和搜索行
	$("#My_List_Table tr:gt(1)").remove();
	$("#My_List_Table").append(rows);
	if(isCheckedAll(objData, checkedRows)){
		$("#checkedAll_Box").prop("checked",true);
	} else {
		$("#checkedAll_Box").prop("checked",false);
	}
};

function loadTableHeader(objData){
	//加载表头
	var headers=[];
	headers.push("<tr class='tr_listfirst' style='white-space:nowrap'>")
	headers.push("<td style='width:25px;'>");
	if(/[0][\d]/g.test(objData.paramsJSON.listRule)){
		headers.push("&nbsp;");
	}
	else{
		headers.push("<input type='checkbox' id='checkedAll_Box' onclick='checkAll(this);'></input>");
	}
	headers.push("</td>");
	var len=objData.data.headers.length;
	for(var i=0;i<objData.data.headers.length;i++){
		if(objData.data.headers[i].hiddenFlag=='1'){
			len--;
		}
	}
	var width=100.0/len+"%";
	for(var i=0;i<objData.data.headers.length;i++){
		headers.push("<td style='width:"+width+";display:"+(objData.data.headers[i].hiddenFlag=='1'?'none':'')+"'>");
		headers.push(objData.data.headers[i].fieldNameForm);
		headers.push("</td>");
	}
	headers.push("</tr>")
	return headers.join("");
}
function loadTableSearch(objData){
	//加载搜索行
	var searchs=[];
	searchs.push("<tr class='tr_listfirst'>")
	searchs.push("<td style='width:25px;'>");
	searchs.push("&nbsp;");
	searchs.push("</td>");
	var len=objData.data.headers.length;
	for(var i=0;i<objData.data.headers.length;i++){
		if(objData.data.headers[i].hiddenFlag=='1'){
			len--;
		}
	}
	var width=100.0/len+"%";
	//是否存在搜索字段
	var hasSearch=false;
	var columnsCount=0;
	for(var i=0;i<objData.data.headers.length;i++){
		if(objData.data.headers[i].hiddenFlag=='1'){
			continue;
		}
		columnsCount++;
		searchs.push("<td style='width:"+width+"'>");
		if(objData.data.headers[i].canSearch){
			hasSearch=true;
			searchs.push("<input type='text' class='inputsgl' name='searchText' id='"+objData.data.headers[i].fieldId+"' value=''/>");
		}
		else{
			searchs.push("&nbsp;");
		}
		
		searchs.push("</td>");
	}
	searchs.push("</tr>")
	//没有搜索字段返回空串
	if(!hasSearch){
		//隐藏搜索按钮
		$("#btn_search").hide();
		return "<tr style='display:none'><td colspan="+(columnsCount+1)+"></td></tr>";
	}
	return  searchs.join("");
}

//判断是否选中全选按钮
function isCheckedAll(objData, checkedRows) {
	var isCheckAll = true;
	$("#tableContent").find("[name='List_Selected']").each(function(index, obj){
		if (!$(obj).prop("checked")) {
			isCheckAll = false;
		}
	});
	return isCheckAll;
}



//打开弹窗,或者跳转页面，渲染数据
function loadTableRows(objData){
	var rows=[];
	if(objData.data.rows.length == 0){
		var colspan = $("#My_List_Table tr:first").find("td").length;
		rows.push("<tr><td colspan="+ colspan +"><bean:message bundle='sys-xform-base' key='sysForm.relevance.noRecordMsg' /></td></tr>");
	}
	
	// #102617 ie8深度复制会报stackoverflow
	if(navigator.userAgent.indexOf("MSIE 8.0")>0 || navigator.appVersion.match(/9./i)=="9.")  
    {  
		pageObjData[objData.paramsJSON.pageSize]=objData;
    } else {
    	var tempObjData=$.extend(true,{},objData);
		pageObjData[objData.paramsJSON.pageSize]=$.extend(true,{},tempObjData);
    }
	for(var i=0;i<objData.data.rows.length;i++){
		var tr_class="tr_listrow"+(i%2+1);
		
		var curRow = objData.data.rows[i];
		updateCheckedRows(curRow, i);
		//判断是否要勾选
		var checked = isChecked(curRow);
		rows.push("<tr class='"+tr_class+"'" + " onclick='checkedTRRowsClick(this);'>");
		//处理复选框
		rows.push("<td>");
		if(/[0][\d]/g.test(objData.paramsJSON.listRule)){
			rows.push("<input type='radio'" + (checked ? " checked='true' " : " ") + "name='List_Selected' value='"+i+"' onclick='checkedRowsClick(this,event)'></input>");
		}else{
			rows.push("<input type='checkbox'" + (checked ? " checked='true' " : " ") + "name='List_Selected' value='"+i+"' onclick='checkedRowsClick(this,event)'></input>");
		}
		rows.push("</td>");
		for(var j=0;j<objData.data.headers.length;j++){
			var header = objData.data.headers[j];
			var isIdProp = false;
			if (objData.paramsJSON.hasRowIdentity && header.fieldName === "__rowId__") {
				isIdProp = true;
			}
			var tdVal = isIdProp ? objData.data.rows[i][j] : "";
			rows.push("<td val='" + tdVal + "' style='display:"+(header.hiddenFlag=='1'?'none':'') +"'>");
			rows.push(objData.data.rows[i][j]);
			rows.push("</td>");
		}
		rows.push("</tr>");
	}
	return rows.join("");
}

function createSelectedText() {
	if (objData.paramsJSON.hasRowIdentity) {
		var selectedDatas = [];
		var textIndex = "";
		for (var i = 0; i < objData.data.headers.length; i++) {
			var head = objData.data.headers[i];
			if (head.fieldIdForm === objData.paramsJSON.textId) {
				textIndex = i;
				break;
			}
		}
		if (textIndex !== "") {
			for (var j = 0; j < checkedRows.length; j++) {
				var checkedRow = checkedRows[j];
				var selectedData = {id: checkedRow.currentRecordId, name: checkedRow[textIndex]};
				selectedDatas.push(selectedData);
			}
		}
		var html = [];
		$("#selectedDatas").empty();
		if (selectedDatas && selectedDatas.length > 0) {
			for (var i = 0; i < selectedDatas.length; i++) {
				var selectedData = selectedDatas[i];
				var id = selectedData.id;
				var name = selectedData.name;
				html.push("<span class='selectedContent' id='"+ id +"'>" + name + "</span>");
			}
			$("#selectedDatas").append('<span class="selectedContent">已选:</span>');
			$("#selectedDatas").append(html.join(""));
		}
	}
}

//点开弹窗加载上一次选中的数据
function loadSelectedRows(objData) {
	if (typeof objData.selectedDatas != "undefined") {
		var selectedDatas = objData.selectedDatas;
		var _checkedRows = selectedDatas.checkedRows;
		if (_checkedRows && _checkedRows.length > 0) {
			checkedRows = _checkedRows;
		}
		var _rowsContext = selectedDatas.rowsContext;
		if (_rowsContext && _rowsContext.length > 0) {
			rowsContext = _rowsContext;
		}
		createSelectedText();
	}
}

function addCheckedRows(curRow, rowDom) {
	//如果有行标识,则要去重
	var isAdd = true;
	if (objData.paramsJSON.hasRowIdentity) {
		isAdd = !isInCheckedRows(curRow);
	}
	if (isAdd) { //没有在已选列表,则添加到已选列表
		var curRow=objData.data.rows[rowDom.value];
		curRow.pageSize=objData.paramsJSON.pageSize;
		curRow.checkedIndex=rowDom.value;
		checkedRows.push(curRow);
		//表单数据集成控件带有搜索条件时，多次查询无法多选
		if (objData.paramsJSON.hasRowIdentity || objData.appendSearchResult == "true") {
			var rowContext = objData.data.rowsContext[rowDom.value];
			rowsContext.push(rowContext);
		}
	} else { //在已选列表,则替换掉已选列表中的 
		if (objData.paramsJSON.hasRowIdentity) {
			for (var i = 0; i < checkedRows.length; i++) {
				var checkedRow = checkedRows[i];
				if (checkedRow.currentRecordId === curRow.currentRecordId) {
					curRow.pageSize=objData.paramsJSON.pageSize;
					curRow.checkedIndex=rowDom.value;
					checkedRows[i] = curRow;
				}
			}
		}
	}
}

//更新已选列表中的数据
function updateCheckedRows(curRow, i) {
	if (objData.paramsJSON.hasRowIdentity) {
		//是否已经添加到选中列表了
		var isAdded = false;
		var _checkedIndex = 0;
		for (var j = 0; j < checkedRows.length; j++) {
			var checkedRow = checkedRows[j];
			if (checkedRow.currentRecordId === curRow.currentRecordId) {
				isAdded = true;
				_checkedIndex = j;
				break;
			}
		}
		if (isAdded) {
			curRow.pageSize=objData.paramsJSON.pageSize;
			curRow.checkedIndex=i;
			checkedRows[_checkedIndex] = curRow;
			if (objData.data.hasRowsContext) {
				var rowContext = findRowContext(curRow);
				if (rowContext) {
					rowsContext[_checkedIndex] = rowContext;
				}
			}
		}
	}
}

//判断是否要勾选
function isChecked(rowData) {
	if (objData.paramsJSON.hasRowIdentity) {
		if (checkedRows.length > 0) { //翻页或者重新加载回显
			for (var j = 0; j < checkedRows.length; j++) {
				var checkedRow = checkedRows[j];
				if (checkedRow.currentRecordId === rowData.currentRecordId) {
					return true;
				}
			}
		}
	}
	return false;
}

//判断当前行是否已经在选中列表
function isInCheckedRows(rowData) {
	var isInCheckedRows = false;
	if (objData.paramsJSON.hasRowIdentity) {
		if (checkedRows.length == 0) {
			return isInCheckedRows;
		}
		for (var j = 0; j < checkedRows.length; j++) {
			var checkedRow = checkedRows[j];
			if (checkedRow.currentRecordId === rowData.currentRecordId) {
				isInCheckedRows = true;
				break;
			}
		}
	}
	return isInCheckedRows;
}


//从选中列表中移除数据
function removeCheckedRow(curRow, obj) {
	//如果有id标识,优先用id标识去匹配
	if (objData.paramsJSON.hasRowIdentity) {
		for (var i = 0; i < checkedRows.length; i++) {
			var checkedRow = checkedRows[i];
			if (checkedRow.currentRecordId === curRow.currentRecordId) {
				checkedRows.splice(i, 1);
				if (objData.data.hasRowsContext) {
					rowsContext.splice(i, 1);
				}
			}
		}
	} else { //没有id标识, 则通过页码跟索引移除
		for(var i=0;i<checkedRows.length;i++){
			if (checkedRows[i].pageSize==objData.paramsJSON.pageSize&&checkedRows[i].checkedIndex==obj.value) {
				checkedRows.splice(i, 1);
				if (objData.data.hasRowsContext) {
					rowsContext.splice(i, 1);
				}
			}
		}
	}
}

function findRowContext(curRow){
	var length = objData.data.rowsContext.length;
	if (objData.data.hasRowsContext) {
		for (var i = 0; i < length; i++) {
			var context = objData.data.rowsContext[i];
			var fdId = context.fdId || context.currentRecordId;
			if (curRow.currentRecordId === fdId) {
				return context;
			}
		}
	}
	return;
}

</script>
<%@ include file="/resource/jsp/list_down.jsp"%>
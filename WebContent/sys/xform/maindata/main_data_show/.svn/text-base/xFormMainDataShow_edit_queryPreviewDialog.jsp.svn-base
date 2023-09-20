<!DOCTYPE html>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page errorPage="/resource/jsp/jsperror.jsp" %>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<script>
	Com_IncludeFile("optbar.js", null, "js");
</script>
<body>
<center>
<link rel="stylesheet" href="${KMSS_Parameter_ContextPath}sys/xform/maindata/resource/css/xFormMainDatashow_edit_dialog.css">
	<script>
		
		var xform_main_data_allData = {};
		
		var _xform_main_data_queryPreview_rowSize = 8;
		
		//记录当前的页码
		var xform_main_data_queryPreview_currentPageNum = 1;
	
		//初始化
		function xform_main_data_queryPreviewInit(){
			if(typeof parent.xform_main_data_detailWithAllData != 'undefined'){				
				dataJSON = parent.xform_main_data_detailWithAllData(false);
				
				if(!parent.xform_main_data_validateTr(dataJSON)){
					//$dialog对象被延迟赋值了，故这里用了延迟
					setTimeout(function(){this.$dialog.hide();},3);
					return;
				}
				
				xform_main_data_allData = dataJSON;
				//处理查询条件
				var selects = dataJSON.select;
				// 入参的字段放到前面
				selects.sort(function(a,b){
					if(b.fieldValue && b.fieldValue == '!{dynamic}'){
						return 1;
					}else{
						return -1;
					}
				});
				var noDynamic = true;
				if(selects){				
					for(var i = 0;i < selects.length;i++){
						var select = selects[i];
						var inputJSON = {};
						inputJSON.field = select.field;
						inputJSON.fieldText = parent.xform_main_data_getFieldsText("xform_main_data_whereTable",select.field,"fdAttrField");
						inputJSON.fieldOperator = parent.xform_main_data_getFieldsText("xform_main_data_whereTable",select.fieldOperator,"fdWhereSelectFieldOperator");
						inputJSON.fieldInpuType = select.fieldValue;
						if(select.fieldValue){
							//如果输入是入参类型的
							if(select.fieldValue == '!{dynamic}'){	
								noDynamic = false;
								inputJSON.fieldValueHtml = "<input type='text' name='"+ select.field +"' onkeyup='enterTrigleSelect(event,this);'/>";
							}else if(select.fieldValue == '!{fix}'){
								inputJSON.fieldValueHtml = "\"" + select.fieldInputValue + "\"";
							}else if(select.fieldValue == '!{empty}'){
								inputJSON.fieldValueHtml = "空值";
							}	
						}
						
						// 构建HTML
						xform_main_data_queryPreview_buildInputDom(inputJSON);
						$(".xform_main_data_queryPreview_inputWrap").show();
					}
				}
				//构建标题栏
				var returnValue = dataJSON.returnValue;
				var $title = $("#List_ViewTable .tr_normal_title");
				if(returnValue){
					var width = "auto";
					if(returnValue.length > 0){
						width = parseInt(100/returnValue.length) + "%";
					}
					for(var i = 0;i < returnValue.length;i++){
						var td = "<td width='"+ width +"' >";
						td += parent.xform_main_data_getFieldsText("xform_main_data_returnValueTable",returnValue[i].field,"fdAttrField");
						td += "</td>";
						$title.append(td);
					}	
				}
				//如果没有动态输入，则直接查询
				if(noDynamic){
					xform_main_data_queryPreview_search(0);
				}
			}
		}
		
		//按enter即可触发搜索
		function enterTrigleSelect(event,self){
			if (event && event.keyCode == '13') {
				xform_main_data_queryPreview_search();
			}
		}
		
		//构建输入的dom
		function xform_main_data_queryPreview_buildInputDom(inputJSON){
			var html = "";			
			html += "<li>"
			html += "<span>\"" + inputJSON.fieldText + "\"</span>";
			html += "<span>" + inputJSON.fieldOperator + "</span>";
			html += inputJSON.fieldValueHtml ? inputJSON.fieldValueHtml : '';
			html += "</li>";
			$(".xform_main_data_queryPreview_inputContent_ul").append(html);
		}
		
		//查询
		function xform_main_data_queryPreview_search(pageno){
			if(pageno == null){
				pageno = 0;
			}
			if(pageno == 0){
				xform_main_data_queryPreview_currentPageNum = 1;
			}
			var fdModelName = parent.document.getElementsByName("fdModelName")[0];
			if($(fdModelName).val() != ''){
				//组装
				dataJSON.modelName = $(fdModelName).val();
				var $inputs = $(".xform_main_data_queryPreview_inputContent_ul li input");
				for(var i = 0;i < $inputs.length;i++){
					var input = $inputs[i];					
					var fieldName = $(input).attr('name');
					var selects = xform_main_data_allData.select;
					for(var j = 0;j < selects.length;j++){
						var select = selects[j];
						if(select.field == fieldName){
							select.fieldInputValue = $(input).val();
							break;
						}
					}
				}
				var url = Com_Parameter.ContextPath + "sys/xform/maindata/main_data_insystem/sysFormMainDatashow.do?method=executeQueryPreview&rowsize="+ _xform_main_data_queryPreview_rowSize +"&orderby=fdId&pageno="+pageno;
				$.ajax({
					url: url,
					type:"POST",
					data:{'data':JSON.stringify(dataJSON)},
					async:false,
					success:function(data){
						if(data){
							var result = {};
							try{
								result = $.parseJSON(data);					 
							}catch(e){
								result.dataArray = [];
								console.warn("查询出错:具体请查看后台信息！");
							}
							xform_main_data_queryPreview_fillTableContent(result);
						}
					}
				});
			}else{
				alert("父窗口的模块还未选择！");
			}
		}
		
		//填充查询结果的记录
		function xform_main_data_queryPreview_fillTableContent(result){
			var $title = $("#List_ViewTable .tr_normal_title");
			//清空原本的记录
			$title.siblings().remove();
			var dataArray = result.dataArray;
			var contentHtml = "";
			if(dataArray && dataArray.length > 0){
				for(var i = 0;i < dataArray.length;i++){
					var data = dataArray[i];
					contentHtml += "<tr>";
					if(data == null){
						data = '';
					}
					if(typeof(data) == 'string' || typeof(data) == 'boolean'){
						contentHtml += "<td>" + data + "</td>";
					}else{
						for(var j = 0;j < data.length;j++){
							//处理时间类型
							if(Object.prototype.toString.call(data[j]) == "[object Object]" && data[j].time){
								var time = new Date(data[j].time);
								contentHtml += "<td>" + time.getFullYear()+"-"+(time.getMonth()+1)+"-"+time.getDate()+" "+time.getHours()+":"+time.getMinutes()+":"+time.getSeconds() + "</td>";
							}else{
								if(data[j] == null){
									data[j] = '';
								}
								contentHtml += "<td>" + data[j] + "</td>";	
							}
							
						}
					}	
					contentHtml += "</tr>";
				}
			}else{
				var tdLength = $title.find("td").length;
				contentHtml = "<tr><td colspan='"+ tdLength +"'>未找到符合条件的记录！</td></tr>";
			}
			
			$title.after(contentHtml);
			//计算上下页的展示
			xform_main_data_queryPreview_calculatePage(result.maxResults,dataArray.length);
		}
		
		// 计算上下翻页
		function xform_main_data_queryPreview_calculatePage(maxResults,currentDataLength){
			if(xform_main_data_queryPreview_currentPageNum > 1){
				var num = currentDataLength + ((xform_main_data_queryPreview_currentPageNum - 1) * _xform_main_data_queryPreview_rowSize);
				//不是第一页，显示上一页
				$("#lastPageNum").show();
				if(maxResults > 0 && num < maxResults){				
					$("#nextPageNum").show();
				}else{
					$("#nextPageNum").hide();
				}
			}else{
				$("#lastPageNum").hide();
				if(currentDataLength == _xform_main_data_queryPreview_rowSize && maxResults > 0 && currentDataLength < maxResults){				
					$("#nextPageNum").show();
				}else{
					$("#nextPageNum").hide();
				}
			}
		}
		
		//上下翻页
		function xform_main_data_queryPreview_skipPage(type){
			//type为1是上一页，为2是下一页
			if(type == '1'){
				if(xform_main_data_queryPreview_currentPageNum != 0){
					xform_main_data_queryPreview_currentPageNum--;
				}
				xform_main_data_queryPreview_search(xform_main_data_queryPreview_currentPageNum);
			}else if(type == '2'){
				xform_main_data_queryPreview_currentPageNum++;
				xform_main_data_queryPreview_search(xform_main_data_queryPreview_currentPageNum);
			}
		}
		
		Com_AddEventListener(window,'load',xform_main_data_queryPreviewInit);
		
	</script>
	
	<!-- 操作栏 -->
	<div id="optBarDiv">
		<input type=button value="查询" onclick="xform_main_data_queryPreview_search(0);">
		<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
	</div>
	
	<div class="xform_main_data_queryPreview_inputWrap" style="display:none;">
		<span>查询条件</span>
		<div class="xform_main_data_queryPreview_inputContent" style="margin-top:8px;"><ul class="xform_main_data_queryPreview_inputContent_ul"></ul></div>
	</div>
	<!-- 查询结果 -->
	<div class="xform_main_data_queryPreview_dataList_wrap">
		<!-- 数据table -->
		<div class="xform_main_data_queryPreview_dataList">
			<table id="List_ViewTable" class="tb_normal" width="95%" style="margin:0px 0px;">	
				<tr class="tr_normal_title"></tr>	
			</table>
		</div>
		<!-- 上下翻页 -->
		<div id="pageOperation">
			<ul class="xform_main_data_queryPreview_pageUl">
				<li style="float:left;" id="lastPageNum" onclick="xform_main_data_queryPreview_skipPage('1');">上一页</li>
				<li id="nextPageNum" onclick="xform_main_data_queryPreview_skipPage('2');">下一页</li>
			</ul>
		</div>
	</div>
	
	<!-- 
	<div class="xform_main_data_queryPreview_dataWrap" style="height:320px;">
		<iframe id="xform_main_data_queryPreview_dataIframe" width="100%" height="100%" frameborder="no" onload="" style="border:0px;"></iframe>
	</div>
	 -->
</center>
</body>
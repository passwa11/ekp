<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<% response.addHeader("X-UA-Compatible", "IE=edge"); %>
<body>
<style>
	.xform_main_data_queryPreview_pageUl{
		list-style:none;
		float:right;
		width:110px;
		margin:10px 2.5% 0px 0px;
	}
	
	.xform_main_data_queryPreview_pageUl li{
		
		cursor:pointer;
		float:right;
		padding:0 5px;
	}
</style>
<center>
	<div class="listtable_box">
		<table id="List_ViewTable" class="tb_normal" width="95%" style="margin:0px 0px;">	
			<tr class="tr_normal_title"></tr>	
		</table>
	</div>
	<div id="pageOperation">
			<ul class="xform_main_data_queryPreview_pageUl">
				<li style="float:left;" id="lastPageNum" onclick="xform_main_data_queryPreview_skipPage('1');">上一页</li>
				<li id="nextPageNum" onclick="xform_main_data_queryPreview_skipPage('2');">下一页</li>
			</ul>
		</div>
</center>
<script>
seajs.use(['lui/jquery'],function($){
	window.xform_main_data_queryPreview_initList = function(){
		
		var data = '${dataList}';
		var title = '${title}';
		if(data && title){
			var dataArray = eval(data);
			//设置标题
			var titleArray = title.split(",");
			var titleHtml = "";
			var width = "auto";
			if(titleArray.length > 0){
				width = parseInt(100/titleArray.length) + "%";
			}
			for(var i = 0;i < titleArray.length;i++){
				titleHtml += "<td width='"+ width +"'>" + titleArray[i] + "</td>";
			}
			
			var $title = $(".tr_normal_title");
			
			$title.html(titleHtml);
			
			//设置内容
			var contentHtml = "";
			for(var i = 0;i < dataArray.length;i++){
				var data = dataArray[i];
				contentHtml += "<tr>";
				if(typeof(data) == 'string'){
					if(data == null){
						data = '';
					}
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
			$title.after(contentHtml);
			
		}
	}
	
	$(function(){
		window.xform_main_data_queryPreview_initList();	
	});
	
})
	
	
</script>
</body>

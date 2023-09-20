<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
request.setAttribute("sys.ui.theme", "default");
%>
<template:include ref="default.simple">
	<template:replace name="title">选择主题风格</template:replace>
	<template:replace name="body">
	<script>seajs.use(['theme!form']);</script>
	<script>	
		seajs.use( [ 'lui/jquery' ], function($) {
			$(document).ready(function() {
		        $.getJSON(Com_Parameter.ContextPath+"sys/mportal/sys_mportal_graphicFastLink/twoRowsAndThreeLattices/json/themeStyleConfig.json?s_cache=${LUI_Cache}",null,function(result){
			        buildThemeStyleContent(result);
		        });
			}); 
		});
		
		function buildThemeStyleContent(datas){
			var $themeStyleList = $("#themeStyleList");
			for(var i=0;i<datas.length;i++){
				var itemData = datas[i];
				var imgUrl = "<%=request.getContextPath()%>"+itemData.previewImageUrl;
				var $item = $("<div class=\"themeStyleItem\"></div>").appendTo($themeStyleList);
				$("<img src=\""+imgUrl+"\"/>").appendTo($item);
				$("<div class=\"themeStyleTitle\"></div>").text(itemData.title).appendTo($item);
				$item.click(itemData,function(event){
					var data = event.data;
					selectThemeStyle(data);
				});
			}			
		}
		
		function selectThemeStyle(data){
			window.$dialog.hide(data);
		}
	</script>
	<style>
		.themeStyleItem{
           width: 300px;
           height: 230px;
           margin-left: 20px;
           margin-top: 20px;
		   border: 1px solid #EEEEEE;
		   box-shadow: 0 3px 5px 0 rgba(0, 0, 0, 0.06);
		   border-radius: 4px;           
           cursor: pointer;
           float: left;
		}
		.themeStyleItem>img{
		   width: 300px;
		   height: 200px;
		}
		.themeStyleItem .themeStyleTitle{
		   text-align: center;
		}
	</style>	
	<table class="tb_normal" style="margin:20px auto;width:95%;height:460px;">
		<tr>
			<td valign="top">
			     <div id="themeStyleList" >
			     </div>
			</td>
		</tr>
	</table>			
	</template:replace>
</template:include>
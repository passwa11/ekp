<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
	seajs.use(['lui/jquery','lui/dialog'], function($,dialog) {
		//关闭
		window.close_view=function(id){
			$("#"+id).fadeOut();//隐藏对话框
		};
		
	});

</script>
<div class="lui_calendar calendar_view" id="calendar_view" style="display: none;position: absolute;">
   	<div class="lui_calendar_top">
   		<div id="header_title" class="lui_calendar_title">
   			
   		</div>
        <div class="lui_calendar_close" onclick="close_view('calendar_view');"></div>
   	</div>
	<div class="calendar_view_content">
		<div class="view_sched_wrapper">
			<input type="hidden"  name="fdId" />
	 		<table class="view_sched">
           		<%--课程名称--%>
	 			<tr>
                 	<td class="lservice_title" width="100px">
                     	课程名称：
                 	</td>
                 	<td>
                     	<div id="calendar_title" class="overDiv num"  ></div>
                 	</td>
             	</tr>
 				<%--时间--%>
	 			<tr>
               		<td class="lservice_title">
               			培训时间：
               		</td>
                 	<td>
                     	<div id="calendar_date" class="num"></div>
                 	</td>
             	</tr>
			</table>
		 </div>
	</div>
</div>

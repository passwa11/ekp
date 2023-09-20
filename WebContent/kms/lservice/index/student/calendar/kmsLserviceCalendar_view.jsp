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
               			学习时间：
               		</td>
                 	<td>
                     	<div id="calendar_date" class="num"></div>
                 	</td>
             	</tr>
             	<%--进度--%>
	 			<tr>
                 	<td class="lservice_title">
                 		进度：
                 	</td>
                 	<td>
                     	<div  style="margin-top: 10px;">
                     		<span style="display: inline-block;width: 65%;height: 6px;border-radius: 3px;background-color: #E2ECF9;">
                     			<em id="calendar_process1"  style="background-color: #2984FC;display: inline-block;height: 6px;vertical-align: top;border-radius: 3px;"></em>
                     		</span>
                     		<span id="calendar_process2" style="font-family:  MicrosoftYaHeiUI;font-size: 14px;color: #333333;letter-spacing: 0;text-align: right;vertical-align: 7px;margin-left: 10px;"></span>
                     	</div>
                 	</td>
             	</tr>
             	<%--讲师--%>
	 			<tr>
                 	<td class="lservice_title">
                     	讲师：
                 	</td>
                 	<td>
                     	<div id="calendar_lectuer" class="overDiv num" ></div>
                 	</td>
             	</tr>
             	<%--说明--%>
	 			<tr>
                 	<td class="lservice_title" valign="top">
                     	课程说明：
                 	</td>
                 	<td>
                     	<div id="calendar_description" class="viewPurpose num"></div>
                 	</td>
             	</tr>
			</table>
		 </div>
	</div>
</div>

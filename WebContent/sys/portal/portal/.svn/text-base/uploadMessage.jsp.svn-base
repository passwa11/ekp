<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple">
	<template:replace name="body">
	    <style type="text/css" >

			.confirm_close_button{
			    padding: 4px 20px;
			    height: 20px;
			    line-height: 20px;
			    cursor: pointer;
			    color: #999;
			    background-color: #fafafa;
			    border: 1px solid #d5d5d5;
			    border-radius: 4px;
			    overflow: hidden;
			    display: inline-block;
			    *display: inline;
			    *zoom: 1;
			    margin-left: 30px;		
			}
			.messageContent{
			   margin-top: 20px;
			   margin-left: 10px;
               margin-right: 10px;
			   color: #ea4335;
			   text-align: center;
			   font-weight: bold;
			}
	    </style>
	    <script>
			/**
			 * 点击关闭按钮 处理事件
			 */
			function closeUploadWindow(){
				seajs.use([ 'lui/jquery','lui/dialog'],function($, dialog) {
					window.$dialog.hide();
				});
			}
		</script>
		<div class="messageContent">
			${successMessage}
		</div>
		<div style="text-align:center;margin-top:40px;">
		   <!-- 关闭按钮 -->
		   <div class="confirm_close_button" onclick="closeUploadWindow()">${lfn:message('button.close')}</div>
		</div>
	</template:replace>
</template:include>
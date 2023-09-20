<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.profile.edit" sidebar="no">
	<template:replace name="title">
		更新kms日志库分类和文档记录数据
	</template:replace>
	<template:replace name="content">
	<style>
		.kmsLog_item {
			font-size: 14px;
			color: #484848;
			margin-bottom: 5px;
			margin-top: 0;
		}
		.kmsLog_desc {
			color: #999;
			line-height: 18px;
		}
		.kmsLog_option {
			
		}
		.kmsLog_operate {
			
		}
		.kmsLog_operate_btn {
		    display: inline-block;
		    width: 105px;
		    height: 30px;
		    line-height: 30px;
		    border-radius: 3px;
		    font-size: 14px;
		    cursor: pointer;
		    text-align: center;
		    border: 1px solid #37a8f5;
		    background: #37a8f5;
		    color: #fff;
		}
		.kmsLog_operate_btn_gray {
		    display: inline-block;
		    width: 105px;
		    height: 30px;
		    line-height: 30px;
		    border-radius: 3px;
		    font-size: 14px;
		    cursor: not-allowed;
		    text-align: center;
		    border: 1px solid #bdbdbd;
		    background: #bdbdbd;
		    color: #fff;
		}
	</style>
		<h2 align="center" style="margin: 10px 0">
			<span class="profile_config_title">更新kms日志库分类和文档记录数据</span>
		</h2>
		
		<html:form action="/kms/log/kms_log_admintool/kmsLogAdminTool.do">
		<center>
		<div style="margin:auto auto 60px;">
		<table class="tb_normal" width=95%>
            <tr>
                <td colspan="3">
                    <strong>说明：</strong>
                    <div>
							1、业务模块的文档信息会保存在kms日志库模块中。
                    </div>
                    <div>
							2、执行kms日志库定时任务的时候，会根据日志中涉及的文档信息新增或者更新kms日志库中对应的文档信息。
                    </div>
                    <div>
							3、更新信息包括标题、状态、分类、作者等信息。
                    </div>
                    <div>
							4、kms积分管理计算的时候，会读取kms日志库中文档的信息(分类、状态等)，来计算权重和积分状态等数据。
                    </div>
                    <div>
							5、如果发生某些异常，可能导致kms日志库中的文档信息和业务模块中的文档信息不一致。
                    </div>
                    <div>
							6、kms积分管理计算的时候，读取的是旧数据，影响积分的计算。
                    </div>
                    <div>
							7、本工具提供一个入口，来更新kms日志库中文档的信息。
                    </div>
                    <div>
							8、请先禁用“【KMS日志库】日志定时任务”和“【KMS积点管理】KMS积点计算”，再执行后续操作，全部执行完成之后，再启用这两个定时任务。
                    </div>
                    <div>
							9、可以在后台的日志管理->后台日志中查看执行情况，请在任务执行完成之后再开始下一次的操作。
                    </div>
                    <div>
							10、操作标识用来区分同一批次更新的文档，避免重复执行更新。同一批次更新，选择不同时间段的时候，请输入相同的操作标识。请自行编造操作标识文本。
                    </div>
                    <div>
							11、选择时间段，后台根据所选时间段的日志数据，来更新日志中涉及的文档信息。
                    </div>
                    
                </td>
            </tr>
			<tr>
				<td width="25%">使用过的操作标识</td>
				<td colspan="2">
					<span id="fdUsedMark" >${fdUsedMark }</span>
				</td>
			</tr>
			<tr>
				<td>本次操作标识</td>
				<td colspan="2">
				<xform:text property="fdMark" subject="操作标识" showStatus="edit" required="true" 
					validators="maxLength(10)" htmlElementProperties="id='fdMark'"></xform:text>
				</td>
			</tr>
			<tr>
				<td width="25%">选择时间段</td>
				<td width="30%">
					<xform:datetime property="fdStartTime" showStatus="edit" dateTimeType="date" 
					required="true" subject="开始时间"></xform:datetime>
					&nbsp;-&nbsp;
					<xform:datetime property="fdEndTime" showStatus="edit" dateTimeType="date" 
					required="true" subject="结束时间"></xform:datetime>
				</td>
				<td class="kmsLog_operate">
					<div class="kmsLog_operate_btn"
						onclick="updateDocByLog();">
							执行更新
					</div>
				</td>
			</tr>
		</table>
		</div>
		</center>
			<html:hidden property="method_GET" />
		</html:form>
		<script type="text/javascript">
		seajs.use(['lui/jquery', 'lui/dialog'], function($, d){
			$KMSSValidation();
			window.updateDocByLog = function(){
				var _fdMark = $("#fdMark").text();
				var _fdStartTime = $("input[name='fdStartTime']").val();
				var _fdEndTime = $("input[name='fdEndTime']").val();
				if (_fdMark == "" || _fdStartTime == "" || _fdEndTime == ""){
					// 必须校验，并且必填为空，使用提交自动触发校验提示
					Com_Submit(document.kmsLogConfigForm, "updateDocByLog");
				} else {
					var _mess = "提交";
					// 必填不为空，可以提交
	                d.confirm(_mess, function(flag) {
	                    if(flag) {
							Com_Submit(document.kmsLogConfigForm, "updateDocByLog");
	                    }
	                });
				}
			}
		});
		</script>
	</template:replace>
</template:include>

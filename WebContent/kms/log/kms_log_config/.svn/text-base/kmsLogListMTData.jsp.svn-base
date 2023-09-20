<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.profile.edit" sidebar="no">
	<template:replace name="title">
	修复kms日志库月份表记录数据
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
			<span class="profile_config_title">修复kms日志库月份表记录数据</span>
		</h2>
		
		<html:form action="/kms/log/kms_log_config/kmsLogConfig.do">
		<center>
		<div style="margin:auto auto 60px;">
		<table class="tb_normal" width=95%>
            <tr>
                <td colspan="3">
                    <strong>说明：</strong>
                    <div>
                        1、kms日志库月份表名格式为"kms_log_app_mon_" + 年月，例如：kms_log_app_mon_202012。
                    </div>
                    <div>
                        2、系统新建kms日志库月份表之后会在sys_app_config表中记录一下，例如：202007;202008;202009;202012。
                    </div>
                    <div>
                        3、需要配置WebContent/WEB-INF/KmssConfig/sys/common/hibernate.xml中"hibernate.hbm2ddl.auto"为"update"，才能自动生成月份表。
                    </div>
                    <div>
                        4、如果kms日志库月份表因hibernate配置未正确生成，可以先修复kms日志库月份表记录数据，在修改hibernate配置后重启环境，或者手动在数据库中执行创建表的sql。
                    </div>
                    <div>
                        5、执行修复：获取kms日志库月份表记录数据，去除重复记录数据，检查记录中的月份对应的表知否存在，最后保存月份表记录数据。
                    </div>
                    <div>
                        6、手动修改：根据用户输入的月份表记录数据，去除重复记录数据，检查记录中的月份对应的表知否存在，最后保存月份表记录数据。
                    </div>
                    <div>
                        7、可以在后台的日志管理->后台日志中查看执行情况。
                    </div>
                </td>
            </tr>
            <tr>
                <td width="25%">kms日志库月份表记录数据</td>
                <td width="50%">
                    <span >${fdMonthInfoStr }</span>
                </td>
                <td class="kmsLog_operate">
                    <div class="kmsLog_operate_btn"
                        onclick="updateMTData();">执行修复
                    </div>
                </td>
            </tr>
            <tr>
                <td width="25%">手动修改</td>
                <td width="50%">
                    <input type="text" id="fdMonthInfoStr" name="fdMonthInfoStr" 
                        class="inputsgl" style="width: 95%;"/>
                </td>
                <td class="kmsLog_operate">
                    <div class="kmsLog_operate_btn"
                        onclick="updateMTData2();">提交修改
                    </div>
                </td>
            </tr>
            <tr>
                <td width="25%">oracle</td>
                <td colspan="2">
CREATE TABLE "KMS_LOG_APP_MON_******" 
   (    "FD_ID" VARCHAR2(36 CHAR) NOT NULL , 
    "FD_SUBJECT" VARCHAR2(200 CHAR), 
    "FD_OPRATE_METHOD" VARCHAR2(60 CHAR) NOT NULL , 
    "FD_CREATE_TIME" TIMESTAMP (6) NOT NULL , 
    "FD_LAST_UPDATE_DATE" TIMESTAMP (6), 
    "FD_TARGET_ID" VARCHAR2(36 CHAR), 
    "MODEL_NAME" VARCHAR2(200 CHAR), 
    "MODULE_KEY" VARCHAR2(200 CHAR), 
    "FD_IP" VARCHAR2(36 CHAR), 
    "IS_MOBILE_AGENT" NUMBER(1,0), 
    "FD_PARAM" VARCHAR2(100 CHAR), 
    "FD_OPERATOR" VARCHAR2(36 CHAR) NOT NULL , 
    "FD_OPERATOR_NAME" VARCHAR2(100 CHAR), 
    "FD_OPERATOR_DEPART_ID" VARCHAR2(36 CHAR), 
    "FD_DATA_SOURCE" VARCHAR2(10 CHAR), 
    "FD_EXTEND_CREATOR" VARCHAR2(36 CHAR), 
     PRIMARY KEY ("FD_ID")  
   ) ;
                </td>
            </tr>
            <tr>
                <td width="25%">sql server</td>
                <td colspan="2">
CREATE TABLE kms_log_app_mon_****** (
fd_id nvarchar(36)  NOT NULL primary key,
fd_subject nvarchar(200)  NULL ,
fd_oprate_method nvarchar(60)  NOT NULL ,
fd_create_time datetime NOT NULL ,
fd_last_update_date datetime NULL ,
fd_target_id nvarchar(36)  NULL ,
model_name nvarchar(200)  NULL ,
module_key nvarchar(200)  NULL ,
fd_ip nvarchar(36)  NULL ,
is_mobile_agent tinyint NULL ,
fd_param nvarchar(100)  NULL ,
fd_operator nvarchar(36)  NOT NULL ,
fd_operator_name nvarchar(100)  NULL ,
fd_operator_depart_id nvarchar(36)  NULL ,
fd_data_source nvarchar(10)  NULL ,
fd_extend_creator nvarchar(36)  NULL 
);
                </td>
            </tr>
            <tr>
                <td width="25%">mysql</td>
                <td colspan="2">
CREATE TABLE kms_log_app_mon_****** (
  fd_id varchar(36) NOT NULL,
  fd_subject varchar(200) DEFAULT NULL,
  fd_oprate_method varchar(60) NOT NULL,
  fd_create_time datetime NOT NULL,
  fd_last_update_date datetime DEFAULT NULL,
  fd_target_id varchar(36) DEFAULT NULL,
  model_name varchar(200) DEFAULT NULL,
  module_key varchar(200) DEFAULT NULL,
  fd_ip varchar(36) DEFAULT NULL,
  is_mobile_agent bit(1) DEFAULT NULL,
  fd_param varchar(100) DEFAULT NULL,
  fd_operator varchar(36) NOT NULL,
  fd_operator_name varchar(100) DEFAULT NULL,
  fd_operator_depart_id varchar(36) DEFAULT NULL,
  fd_data_source varchar(10) DEFAULT NULL,
  fd_extend_creator varchar(36) DEFAULT NULL,
  PRIMARY KEY (fd_id)
) ;
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
	 		window.updateMTData = function(){
 				var _mess = "确定修复kms日志库月份表记录数据吗？";
                d.confirm(_mess, function(flag) {
	                    if(flag) {
						Com_Submit(document.kmsLogConfigForm, "updateMTData");
	                    }
	                });
	 		}
	 		window.updateMTData2 = function(){
	 			var _fdMonthInfoStr = $("#fdMonthInfoStr").val();
	 			if (!fdMonthInfoStr){
	 				dialog.alert("请输入kms日志库月份表记录数据！");
	 				return;
	 			}
 				var _mess = "确定修改kms日志库月份表记录数据吗？<br/>" + _fdMonthInfoStr;
                d.confirm(_mess, function(flag) {
	                    if(flag) {
						Com_Submit(document.kmsLogConfigForm, "updateMTData2");
	                    }
	                });
	 		}
		});
	 	</script>
	</template:replace>
</template:include>

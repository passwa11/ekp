<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.profile.edit" sidebar="no">
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
            width: 160px
        }
        .kmsLog_operate {
            width: 120px
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
            cursor: default;
            text-align: center;
            border: 1px solid #bdbdbd;
            background: #bdbdbd;
            color: #fff;
        }
    </style>
        <h2 align="center" style="margin: 10px 0">
        </h2>
        <center>
        <div style="margin:auto auto 60px;">
        <table class="tb_normal" width=95%>
            <tr>
                <td>
                    <h3 class="kmsLog_item">修复kms日志库月份表记录数据</h3>
                    <p class="kmsLog_desc">
                    修复kms日志库月份表记录数据中缺少、多余等错误数据。
                    </p>
                </td>
                <td class="kmsLog_operate">
                    <div class="kmsLog_operate_btn"
                        onclick="Com_OpenWindow('<c:url value="/kms/log/kms_log_config/kmsLogConfig.do" />?method=listMTData');">
                        进入
                    </div>
                </td>
            </tr>
            <tr>
                <td>
                    <h3 class="kmsLog_item">更新kms日志库分类和文档记录数据</h3>
                    <p class="kmsLog_desc">
                        更新kms日志库分类和文档记录数据，提供给其他统计数据使用。
                    </p>
                </td>
                <td class="kmsLog_operate">
                    <div class="kmsLog_operate_btn"
                         onclick="Com_OpenWindow('<c:url value="/kms/log/kms_log_admintool/kmsLogAdminTool.do" />?method=toQueryLogUpd');">
                        进入
                    </div>
                </td>
            </tr>
        </table>
        </div>
        </center>
    </template:replace>
</template:include>

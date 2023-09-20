<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/view_top.jsp"%>



<div id="optBarDiv"><input type="button"
                           value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
</div>

<p class="txttitle">${HtmlParam.name}说明</p>

<center>
    <br/>
    <table class="tb_normal" cellpadding="0" cellspacing="0"  style="width: 85%;margin-left: 40px;">
        <tr>
            <td class="td_normal_title" width="20%">接口url</td>
            <td style="padding: 0px;">
                <div style="margin: 8px;">
                    /api/sys-attachment/dianjuCallback/download
                </div>
            </td>
        </tr>
        <tr>
            <td class="td_normal_title" width="20%">接口说明</td>
            <td style="padding: 0px;">
                <div style="margin: 8px;">
                    点聚回调下载文件
                </div>
            </td>
        </tr>
        <tr>
            <td class="td_normal_title" width="20%">请求方式</td>
            <td style="padding: 0px;">
                <div style="margin: 8px;">
                    GET
                </div>
            </td>
        </tr>
        <tr>
            <td class="td_normal_title" width="20%">请求参数</td>
            <td style="padding: 0px;"><div style="margin: 8px;">详细说明如下：</div>
                <table class="tb_normal" width=100%>
                    <tr class="tr_normal_title">
                        <td align="center" style="width: 30%" ><b>字段名</b></td>
                        <td align="center" style="width: 30%"><b>说明</b></td>
                        <td align="center" style="width: 30%"><b>取值说明</b></td>
                    </tr>
                    <tr>
                        <td>si</td>
                        <td>EKP的下载签名</td>
                        <td>必填</td>
                    </tr>
                    <tr>
                        <td>fdId</td>
                        <td>文件id</td>
                        <td>必填</td>
                    </tr>
                </table>
            </td>
        </tr>
        <tr>
            <td class="td_normal_title" width="20%">请求示例</td>
            <td style="padding: 0px;">
                <div style="margin: 8px;">
                    <p>
                        /api/sys-attachment/dianjuCallback/download?fdId=12345&si=abc
                    </p>
                </div>
            </td>
        </tr>
        <tr>
            <td class="td_normal_title" width="20%">响应说明</td>
            <td style="padding: 0px;">
                <div style="margin: 8px;">
                    返回文件流
                </div>
            </td>
        </tr>
        <tr>
            <td class="td_normal_title" width="20%">返回参数</td>
            <td style="padding: 0px;">
                <div style="margin: 8px;">
                    无
                </div>
            </td>
        </tr>
    </table>
    <br/>
    <table class="tb_normal" cellpadding="0" cellspacing="0"  style="width: 85%;margin-left: 40px;">
        <tr>
            <td class="td_normal_title" width="20%">接口url</td>
            <td style="padding: 0px;">
                <div style="margin: 8px;">
                    /api/sys-attachment/dianjuCallback/convert
                </div>
            </td>
        </tr>
        <tr>
            <td class="td_normal_title" width="20%">接口说明</td>
            <td style="padding: 0px;">
                <div style="margin: 8px;">
                    点聚转换文件回调
                </div>
            </td>
        </tr>
        <tr>
            <td class="td_normal_title" width="20%">请求方式</td>
            <td style="padding: 0px;">
                <div style="margin: 8px;">
                    POST
                </div>
            </td>
        </tr>
        <tr>
            <td class="td_normal_title" width="20%">请求参数</td>
            <td style="padding: 0px;"><div style="margin: 8px;">详细说明如下：</div>
                <table class="tb_normal" width=100%>
                    <tr class="tr_normal_title">
                        <td align="center" style="width: 30%" ><b>字段名</b></td>
                        <td align="center" style="width: 30%"><b>说明</b></td>
                        <td align="center" style="width: 30%"><b>取值说明</b></td>
                    </tr>
                    <tr>
                        <td>SERIAL_NUMBER</td>
                        <td>下载文件id</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>RET_MSG</td>
                        <td>转换信息</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>retCode</td>
                        <td>转换状态</td>
                        <td></td>
                    </tr>
                </table>
            </td>
        </tr>
        <tr>
            <td class="td_normal_title" width="20%">请求示例</td>
            <td style="padding: 0px;">
                <div style="margin: 8px;">
                    <p>
                        /api/sys-attachment/dianjuCallback/convert
                    </p>
                    <p>
                        {
                    </p>
                    <p>
                        "SERIAL_NUMBER":"123456","RET_MSG":"成功","retCode":"1"
                    </p>
                    <p>
                        }
                    </p>
                </div>
            </td>
        </tr>
        <tr>
            <td class="td_normal_title" width="20%">响应说明</td>
            <td style="padding: 0px;">
                <div style="margin: 8px;">
                    返回响应状态
                </div>
            </td>
        </tr>
        <tr>
            <td class="td_normal_title" width="20%">返回参数</td>
            <td style="padding: 0px;">
                <div style="margin: 8px;">
                    <p>
                        {
                    </p>
                    <p>
                        "RET_CODE":1
                    </p>
                    <p>
                        }
                    </p>
                </div>
            </td>
        </tr>
    </table>
</center>
<%@ include file="/resource/jsp/view_down.jsp"%>
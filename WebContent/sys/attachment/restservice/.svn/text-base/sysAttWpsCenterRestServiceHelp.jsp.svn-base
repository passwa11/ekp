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
                    /api/sys-attachment/wpsCenterCallback/download
                </div>
            </td>
        </tr>
        <tr>
            <td class="td_normal_title" width="20%">接口说明</td>
            <td style="padding: 0px;">
                <div style="margin: 8px;">
                    WPS回调下载文件，详见WPS开发文档
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
                        <td>_w_l_token</td>
                        <td>EKP的权限token</td>
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
                        /api/sys-attachment/wpsCenterCallback/download?fdId=123456&_w_l_token=XXX
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
                    /api/sys-attachment/wpsCenterCallback/v1/3rd/user/info
                </div>
            </td>
        </tr>
        <tr>
            <td class="td_normal_title" width="20%">接口说明</td>
            <td style="padding: 0px;">
                <div style="margin: 8px;">
                    WPS回调获取当前用户信息，详见WPS开发文档
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
                        <td>_w_l_token</td>
                        <td>EKP的权限token</td>
                        <td>必填</td>
                    </tr>
                    <tr>
                        <td>ids</td>
                        <td>用户id</td>
                        <td>{
                            "ids": ["id1000", "id2000"]
                            }
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
        <tr>
            <td class="td_normal_title" width="20%">请求示例</td>
            <td style="padding: 0px;">
                <div style="margin: 8px;">
                    <p>
                        /api/sys-attachment/wpsCenterCallback/v1/3rd/user/info
                    </p>
                </div>
            </td>
        </tr>
        <tr>
            <td class="td_normal_title" width="20%">响应说明</td>
            <td style="padding: 0px;">
                <div style="margin: 8px;">
                    返回用户信息
                </div>
            </td>
        </tr>
        <tr>
            <td class="td_normal_title" width="20%">返回参数</td>
            <td style="padding: 0px;">
                <div style="margin: 8px;">
                    <p>{</p>
                    <p>"users": [
                        {
                        "id": "id1000",
                        "name": "wps-1000",
                        "avatar_url": "http://xxx.cn/?id=1000"
                        },
                        {
                        "id": "id2000",
                        "name": "wps-2000",
                        "avatar_url": "http://xxx.cn/?id=2000"
                        }
                        ]</p>
                    <p>}</p>
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
                    /api/sys-attachment/wpsCenterCallback/v1/3rd/file/info
                </div>
            </td>
        </tr>
        <tr>
            <td class="td_normal_title" width="20%">接口说明</td>
            <td style="padding: 0px;">
                <div style="margin: 8px;">
                    WPS回调获取文件信息，详见WPS开发文档
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
                        <td>_w_l_token</td>
                        <td>EKP的权限token</td>
                        <td>必填</td>
                    </tr>
                    <tr>
                        <td>_w_fileId</td>
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
                        /api/sys-attachment/wpsCenterCallback/v1/3rd/file/info
                    </p>
                </div>
            </td>
        </tr>
        <tr>
            <td class="td_normal_title" width="20%">响应说明</td>
            <td style="padding: 0px;">
                <div style="margin: 8px;">
                    返回文件信息
                </div>
            </td>
        </tr>
        <tr>
            <td class="td_normal_title" width="20%">返回参数</td>
            <td style="padding: 0px;">
                <div style="margin: 8px;">
                    <p>{</p>
                    <p>"file": {
                        "id": "132aa30a87064",
                        "name": "example.doc",
                        "version": 1,
                        "size": 200,
                        "creator": "id0",
                        "create_time": 1136185445,
                        "modifier": "id1000",
                        "modify_time": 1551409818,
                        "download_url": "http://www.xxx.cn/v1/file?fid=f132aa30a87064",
                        "preview_pages": 3,
                        "user_acl": {
                        "rename": 1,
                        "history": 1,
                        "copy": 1,
                        "export": 1,
                        "print": 1
                        },
                        "watermark": {
                        "type": 1,
                        "value": "禁止传阅\r\nwps-1000",
                        "fillstyle": "rgba( 192, 192, 192, 0.6 )",
                        "font": "bold 20px Serif",
                        "rotate": -0.7853982,
                        "horizontal": 50,
                        "vertical": 100
                        }
                        },
                        "user": {
                        "id": "id1000",
                        "name": "wps-1000",
                        "permission": "read",
                        "avatar_url": "http://xxx.cn/id=1000"
                        }</p>
                    <p>}</p>
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
                    /api/sys-attachment/wpsCenterCallback/v1/3rd/file/save
                </div>
            </td>
        </tr>
        <tr>
            <td class="td_normal_title" width="20%">接口说明</td>
            <td style="padding: 0px;">
                <div style="margin: 8px;">
                    WPS回调保存文件信息，详见WPS开发文档
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
                        <td>_w_l_token</td>
                        <td>EKP的权限token</td>
                        <td>必填</td>
                    </tr>
                    <tr>
                        <td>_w_userid</td>
                        <td>用户id</td>
                        <td>必填</td>
                    </tr>
                    <tr>
                        <td>file</td>
                        <td>文件对象</td>
                        <td>必填</td>
                    </tr>
                    <tr>
                        <td>_w_fileId</td>
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
                        /api/sys-attachment/wpsCenterCallback/v1/3rd/file/save
                    </p>
                </div>
            </td>
        </tr>
        <tr>
            <td class="td_normal_title" width="20%">响应说明</td>
            <td style="padding: 0px;">
                <div style="margin: 8px;">
                    返回保存后的文件信息
                </div>
            </td>
        </tr>
        <tr>
            <td class="td_normal_title" width="20%">返回参数</td>
            <td style="padding: 0px;">
                <div style="margin: 8px;">
                    <p>{</p>
                    <p>"file": {
                        "id": "f132aa30a87064",
                        "name": "example.doc",
                        "version": 2,
                        "size": 200,
                        "download_url": "http://www.xxx.cn/v1/file?fid=f132aa30a87064"
                        }</p>
                    <p>}</p>
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
                    /api/sys-attachment/wpsCenterCallback/integrate/dispatch
                </div>
            </td>
        </tr>
        <tr>
            <td class="td_normal_title" width="20%">接口说明</td>
            <td style="padding: 0px;">
                <div style="margin: 8px;">
                    WPS套红、清稿、转换回调的方法，详见WPS开发文档-格式处理
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
                        <td>task_id</td>
                        <td>转换任务id</td>
                        <td>必填</td>
                    </tr>
                    <tr>
                        <td>result</td>
                        <td>转换结果</td>
                        <td>必填</td>
                    </tr>
                    <tr>
                        <td>success</td>
                        <td>转换是否成功</td>
                        <td>必填</td>
                    </tr>
                    <tr>
                        <td>message</td>
                        <td>转换信息</td>
                        <td>非必填</td>
                    </tr>
                    <tr>
                        <td>download_id</td>
                        <td>文件下载id</td>
                        <td>非必填</td>
                    </tr>
                </table>
            </td>
        </tr>
        <tr>
            <td class="td_normal_title" width="20%">请求示例</td>
            <td style="padding: 0px;">
                <div style="margin: 8px;">
                    <p>
                        /api/sys-attachment/wpsCenterCallback/integrate/dispatch
                    </p>
                    <p>
                        {
                    </p>
                    <p>
                        "task_id": "string",
                        "result": {
                        "success": true,
                        "message": "string",
                        "download_id": "string"
                        }
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
                    无
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
</center>
<%@ include file="/resource/jsp/view_down.jsp"%>
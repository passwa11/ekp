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
                    /api/sys-notify/sysNotifyData/getSourceApp
                </div>
            </td>
        </tr>
        <tr>
            <td class="td_normal_title" width="20%">接口说明</td>
            <td style="padding: 0px;">
                <div style="margin: 8px;">
                    获取系统及模块信息
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
            <td style="padding: 0px;">无</td>
        </tr>
        <tr>
            <td class="td_normal_title" width="20%">响应说明</td>
            <td style="padding: 0px;">
                <div style="margin: 8px;">
                    响应返回对象SysNotifySourceAppVO
                </div>
            </td>
        </tr>
        <tr>
            <td class="td_normal_title" width="20%">返回参数</td>
            <td style="padding: 0px;">
                <div style="margin: 8px;">
                    <p>{</p>
                    <p>fdName:系统名称</p>
                    <p>fdCode:系统标识</p>
                    <p>fdSourceModules:模块列表{</p>
                    <p>fdName:模块名称</p>
                    <p>fdCode:模块标识</p>
                    <p>fdMd5:模块信息md5值</p>
                    <p>dynamicProps:动态属性</p>
                    <p>}</p>
                    <p>}</p>
                </div>
            </td>
        </tr>
        <tr>
            <td class="td_normal_title" width="20%">返回示例</td>
            <td style="padding: 0px;">
                <div style="margin: 8px;">
                    <p>
                        {
                        "fdName": "origin-ekp",
                        "fdCode": "origin-ekp",
                        "fdSourceModules": [
                            {
                            "fdName": "数据断链",
                            "fdCode": "component-bklink",
                            "dynamicProps": {
                                "fdNameJP": "数据断链",
                                "fdNameHK": "數據斷鏈",
                                "fdNameUS": "Broken data chain",
                                "fdNameCN": "数据断链"
                                }
                            },
                            {
                            "fdName": "数据源",
                            "fdCode": "component-dbop",
                            "dynamicProps": {
                                "fdNameJP": "数据源",
                                "fdNameHK": "數據源",
                                "fdNameUS": "Data source",
                                "fdNameCN": "数据源"
                                }
                            }
                        ]
                        }
                    </p>
                </div>
            </td>
        </tr>
    </table>
</center>
<%@ include file="/resource/jsp/view_down.jsp"%>
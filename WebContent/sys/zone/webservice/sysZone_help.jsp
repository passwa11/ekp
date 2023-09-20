<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform" %>
<%@ include file="/resource/jsp/view_top.jsp" %>
<script type="text/javascript">
    Com_IncludeFile("optbar.js|list.js");
</script>
<script>
    function expandMethod(thisObj) {
        var isExpand = thisObj.getAttribute("isExpanded");
        if (isExpand == null)
            isExpand = "0";
        var trObj = thisObj.parentNode;
        trObj = trObj.nextSibling;
        while (trObj != null) {
            if (trObj != null && trObj.tagName == "TR") {
                break;
            }
            trObj = trObj.nextSibling;
        }
        var imgObj = thisObj.getElementsByTagName("IMG")[0];
        if (trObj.tagName.toLowerCase() == "tr") {
            if (isExpand == "0") {
                trObj.style.display = "";
                thisObj.setAttribute("isExpanded", "1");
                imgObj.setAttribute("src", "${KMSS_Parameter_StylePath}icons/collapse.gif");
            } else {
                trObj.style.display = "none";
                thisObj.setAttribute("isExpanded", "0");
                imgObj.setAttribute("src", "${KMSS_Parameter_StylePath}icons/expand.gif");
            }
        }
    }
</script>


<div id="optBarDiv"><input type="button"
                           value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
</div>

<p class="txttitle">${param.name}接口说明</p>

<center>
    <div style="width: 95%;text-align: left;">
    </div>
    <br/>

    <table border="0" width="95%">
        <tr>
            <td>
                <table class="tb_normal" cellpadding="0" cellspacing="0" style="width: 85%;margin-left: 40px;">
                    <tr>
                        <td class="td_normal_title" width="20%">服务说明</td>
                        <td style="padding: 0px;">
                            <div style="margin: 8px;">更新头像服务：SysZoneWebService</div>
                            <table class="tb_normal" cellpadding="0" cellspacing="0" width=100%>
                                <tr class="tr_normal_title">
                                    <td align="center" width="20%"><b>服务方法名</b></td>
                                    <td align="center" width="20%"><b>功能说明</b></td>
                                    <td align="center" width="20%"><b>返回值</b></td>
                                    <td align="center" width="20%"><b>请求对象</b></td>
                                    <td align="center" width="20%"><b>请求异常</b></td>
                                </tr>
                                <tr>
                                    <td align="center">updateUserImage</td>
                                    <td align="center">更新用户头像</td>
                                    <td align="center">success</td>
                                    <td align="center">SysZoneWebService</td>
                                    <td align="center">KmsFaultException</td>
                                </tr>
                            </table>
                        </td>
                    </tr>

                    <tr>
                        <td class="td_normal_title" width="20%">请求信息说明</td>
                        <td style="padding: 0px;">
                            <div style="margin: 8px;">&nbsp;&nbsp;请求对象：Expert</div>
                            <table class="tb_normal" width=100%>
                                <tr class="tr_normal_title">
                                    <td align="center" style="width: 15%"><b>参数名</b></td>
                                    <td align="center" style="width: 15%"><b>参数类型</b></td>
                                    <td align="center" style="width: 15%"><b>是否必填</b></td>
                                    <td align="center" style="width: 55%"><b>参数描述</b></td>
                                </tr>
                                <tr>
                                    <td align="center">userId</td>
                                    <td align="center">String</td>
                                    <td align="center">是</td>
                                    <td align="center">用户登陆账号(sysOrgPerson.fdLoginName)</td>
                                </tr>
                                <tr>
                                    <td align="center">imagebyte</td>
                                    <td align="center">Object</td>
                                    <td align="center">是</td>
                                    <td align="center">上传头像图片对象AttachImage</td>
                                </tr>
                            </table>
                        </td>
                    </tr>

                    <tr>
                        <td class="td_normal_title" width="20%">返回信息说明</td>
                        <td style="padding: 0px;">
                            <div style="margin: 8px;">&nbsp;&nbsp;成功时默认返回success</div>
                            <div style="margin: 8px;">&nbsp;&nbsp;失败时抛出KmsFaultException异常</div>
                        </td>
                    </tr>

                    <tr>
                        <td colspan="2"><br/><b>1、测试描述</b></td>
                    </tr>
                    <tr>
                        <td colspan="2" style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);"
                            id="orgInfo"><br/>&nbsp;&nbsp;1.1&nbsp;&nbsp;rest服务须知
                            <img src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0" align="bottom"/>
                        </td>
                    </tr>
                    <tr style="display: none">
                        <td colspan="2">
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<c:out value="<soap:Header>"/> <br/>
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<c:out value="<tns:RequestSOAPHeader>"/><br/>
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<c:out value="<tns:user></tns:user>"/><br/>
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<c:out value="<tns:password></tns:password>"/><br/>
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<c:out value="<tns:service></tns:service>"/><br/>
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<c:out value="</tns:RequestSOAPHeader>"/><br/>
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<c:out value="</soap:Header>"/><br/>
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;必须要在SOAP请求头加入这一段，给定用户名和密码,密码就是经过md5加密过的那一串，不能明文传输,否则无法通过验证。
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);"
                            id="dataInfo"><br/>&nbsp;&nbsp;1.2&nbsp;&nbsp;更新用户头像
                            <img src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0" align="bottom"/>
                        </td>
                    </tr>
                    <tr style="display: none">
                        <td colspan="2">
                            <p>&nbsp;&nbsp;&nbsp;&nbsp;对应的接口方法为：updateUserImage</p>
                            <p>&nbsp;&nbsp;&nbsp;&nbsp;更新用户头像的SOAP请求示例如下:</p>
                            &nbsp;&nbsp;&nbsp;&nbsp;<c:out
                                value="<soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/' xmlns:web='http://webservice.zone.sys.kmss.landray.com/'>"/><br/>
                            &nbsp;&nbsp;&nbsp;&nbsp;<c:out value="</soap:Header>"/><br/>
                            &nbsp;&nbsp;&nbsp;&nbsp;<c:out value="<soap:Body>"/><br/>
                            &nbsp;&nbsp;&nbsp;&nbsp;<c:out value="<web:updateUserImage>"/><br/>
                            &nbsp;&nbsp;&nbsp;&nbsp;<c:out value="<arg0>admin</arg0>"/><br/>
                            &nbsp;&nbsp;&nbsp;&nbsp;<c:out
                                value="<arg1>  <imageByte>cid:1233293188977</imageByte> </arg1>(类似上传附件的写法)"/><br/>
                            &nbsp;&nbsp;&nbsp;&nbsp;<c:out value="/<web:updateUserImage>"/><br/>
                            &nbsp;&nbsp;&nbsp;&nbsp;<c:out value="</soap:Body>"/><br/>
                            &nbsp;&nbsp;&nbsp;&nbsp;<c:out value="</soap:Envelope>"/><br/>
                            -------------------------------------------------------------------------------<br/>
                        </td>
                    </tr>

                </table>
    </table>
</center>

<%@ include file="/resource/jsp/view_down.jsp" %>
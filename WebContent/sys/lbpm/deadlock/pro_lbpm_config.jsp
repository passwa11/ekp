<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<html:form action="/sys/appconfig/sys_appconfig/sysAppConfig.do">
    <div id="optBarDiv">
        <input type=button value="<bean:message key="button.update"/>"
               onclick="Com_Submit(document.sysAppConfigForm, 'update');">
    </div>
    <p class="txttitle">
        流程引擎参数配置
    </p>
    <center>
        <table class="tb_normal" width=95%>
            <tr>
                <td class="td_normal_title" colspan="2" style="font-size: 14px; font-weight: bold;">
                    死锁配置
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    死锁重试次数
                </td>
                <td>
                    <xform:text property="value(pro.lbpm.deadlock.retry.count)" required="true" validators="digits min(3) max(10)" style="width:5%" />
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    死锁错误信息的判断字符
                </td>
                <td>
                    <xform:textarea property="value(pro.lbpm.deadlock.message)" required="true" style="width:98%" />	<br>如Lock wait timeout exceeded,多值以回车分割
                </td>
            </tr>
        </table>
    </center>
</html:form>
<script>
    $KMSSValidation();
</script>
<%@ include file="/resource/jsp/edit_down.jsp"%>

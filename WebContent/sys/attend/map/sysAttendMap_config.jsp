<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@page import="com.landray.kmss.sys.notify.model.SysNotifyConfig" %>
<%

%>
<template:include ref="config.profile.edit" sidebar="no">
    <template:replace name="title">
        <bean:message bundle="sys-attend"
                      key="sysAttend.tree.map.config.title"/>
    </template:replace>
    <template:replace name="content">
        <h2 align="center" style="margin: 10px 0">
			<span class="profile_config_title"><bean:message
                    bundle="sys-attend" key="sysAttend.tree.map.config.title"/></span>
        </h2>
        <html:form action="/sys/attend/map/sysAttendMapConfig.do">
            <center>
                <table class="tb_normal" width=95%>
                    <tr>
                        <td class="td_normal_title" width="20%"><bean:message
                                bundle="sys-attend" key="sysAttend.tree.map.config.type"/></td>
                        <td>
                            <table class="tb_noborder" width=50%>
                                <tr>
                                    <td><xform:radio onValueChange="onMapTypeChange"
                                                     property="value(fdMapServiceType)" showStatus="edit">
                                        <xform:simpleDataSource value="amap">
                                            <bean:message bundle="sys-attend"
                                                          key="sysAttend.tree.map.config.amap"/>
                                            <bean:message bundle="sys-attend"
                                                          key="sysAttend.tree.map.config.tips"/>
                                        </xform:simpleDataSource>
                                    </xform:radio></td>
                                    <td><xform:radio onValueChange="onMapTypeChange"
                                                     property="value(fdMapServiceType)" showStatus="edit">
                                        <xform:simpleDataSource value="bmap">
                                            <bean:message bundle="sys-attend"
                                                          key="sysAttend.tree.map.config.bmap"/>
                                        </xform:simpleDataSource>
                                    </xform:radio></td>
                                    <td><xform:radio onValueChange="onMapTypeChange"
                                                     property="value(fdMapServiceType)" showStatus="edit">
                                        <xform:simpleDataSource value="qmap">
                                            <bean:message bundle="sys-attend"
                                                          key="sysAttend.tree.map.config.qmap"/>
                                        </xform:simpleDataSource>
                                    </xform:radio></td>
                                </tr>
                            </table>
                            <span style="color: red;"> <bean:message
                                    bundle="sys-attend" key="sysAttend.map.config.tips"/>
						</span>
                        </td>
                    </tr>
                    <tr id="tr_bmap" cfg-key="mapKey">
                        <td class="td_normal_title" width="20%"><bean:message
                                bundle="sys-attend" key="sysAttend.tree.map.config.key"/></td>
                        <td><xform:text property="value(fdMapServiceBMapKey)"
                                        style="width:95%;" required="true" showStatus="edit"/> <xform:checkbox
                                property="value(fdMapServiceBMapVer)" showStatus="edit">
                            <xform:simpleDataSource value="1">
                                <bean:message bundle="sys-attend"
                                              key="sysAttend.tree.map.config.bmap.version"/>
                            </xform:simpleDataSource>
                        </xform:checkbox>
                            <div style="margin-bottom: 8px; margin-top: 8px;">
                                    ${ lfn:message('sys-attend:sysAttend.tree.config.baidu0') }（<a
                                    href="http://lbsyun.baidu.com/" target="_blank"
                                    rel="noopener noreferrer">http://lbsyun.baidu.com/</a>）${ lfn:message('sys-attend:sysAttend.tree.config.baidu1') }
                            </div>
                        </td>
                    </tr>
                    <tr id="tr_qmap" cfg-key="mapKey" style="display: none;">
                        <td class="td_normal_title" width="20%"><bean:message
                                bundle="sys-attend" key="sysAttend.tree.map.config.key"/></td>
                        <td><xform:text property="value(fdMapServiceQMapKey)"
                                        style="width:95%;" required="true" showStatus="edit"/>
                            <div style="margin-bottom: 8px; margin-top: 8px;">
                                <bean:message bundle="sys-attend"
                                              key="sysAttend.tree.config.tencent0"/>
                                （<a href="http://lbs.qq.com/" target="_blank"
                                    rel="noopener noreferrer">http://lbs.qq.com/</a>）
                                <bean:message bundle="sys-attend"
                                              key="sysAttend.tree.config.allConfig"/>
                            </div>
                        </td>
                    </tr>
                    <tr id="tr_qmap_pc" cfg-key="mapKey" style="display: none;">
                        <td class="td_normal_title" width="20%">
                                ${lfn:message('sys-attend:sysAttend.tree.map.config.key.name') }
                        </td>
                        <td><xform:text property="value(fdMapServiceQMapName)"
                                        style="width:95%;" required="true" showStatus="edit"/>
                            <div style="margin-bottom: 8px; margin-top: 8px;">
                                <bean:message bundle="sys-attend"
                                              key="sysAttend.tree.config.tencent0"/>
                                （<a href="http://lbs.amap.com" target="_blank"
                                    rel="noopener noreferrer">http://lbs.qq.com/</a>）
                                    ${lfn:message('sys-attend:sysAttend.tree.config.allConfig.name') }
                            </div>
                        </td>
                    </tr>
                    <tr id="tr_amap" cfg-key="mapKey" style="display: none;">
                        <td class="td_normal_title" width="20%"><bean:message
                                bundle="sys-attend" key="sysAttend.tree.map.config.key"/></td>
                        <td><xform:text property="value(fdMapServiceAMapKey)"
                                        style="width:95%;" required="true" showStatus="edit"/>
                            <div style="margin-bottom: 8px; margin-top: 8px;">
                                <bean:message bundle="sys-attend"
                                              key="sysAttend.tree.config.gaode0"/>
                                （<a href="http://lbs.amap.com" target="_blank"
                                    rel="noopener noreferrer">http://lbs.amap.com</a>）
                                <bean:message bundle="sys-attend"
                                              key="sysAttend.tree.config.allConfig"/>
                            </div>
                        </td>
                    </tr>
                    <tr id="tr_amap_pc" cfg-key="mapKey" style="display: none;">
                        <td class="td_normal_title" width="20%">
                                ${lfn:message('sys-attend:sysAttend.tree.map.config.pc.key') }</td>
                        <td><xform:text property="value(fdMapServiceAMapPcKey)"
                                        style="width:95%;" required="true" showStatus="edit"/>
                            <div style="margin-bottom: 8px; margin-top: 8px;">
                                <bean:message bundle="sys-attend"
                                              key="sysAttend.tree.config.gaode0"/>
                                （<a href="http://lbs.amap.com" target="_blank"
                                    rel="noopener noreferrer">http://lbs.amap.com</a>）
                                    ${lfn:message('sys-attend:sysAttend.tree.config.pc.allConfig') }
                            </div>
                        </td>
                    </tr>
                    <tr id="tr_amap_security_key" cfg-key="mapKey" style="display: none;">
                        <td class="td_normal_title" width="20%">${lfn:message('sys-attend:sysAttend.tree.config.gaode.security') }</td>
                        <td><xform:text property="value(fdMapServiceAMapPcSecurityKey)"
                                        style="width:95%;" showStatus="edit"/>
                            <div style="margin-bottom: 8px; margin-top: 8px;">
                                    ${lfn:message('sys-attend:sysAttend.tree.config.gaode.security.tip') }
                            </div>
                        </td>
                    </tr>
                </table>
            </center>
            <html:hidden property="method_GET"/>
            <input type="hidden" name="modelName"
                   value="com.landray.kmss.sys.attend.model.SysAttendMapConfig"/>

            <center style="margin-top: 10px;">
                <!-- 保存 -->
                <ui:button text="${lfn:message('button.save')}" height="35"
                           width="120" onclick="onSave();"></ui:button>
            </center>

            <script type="text/javascript">
                var validation = $KMSSValidation();

                function _onLoad() {
                    var type = $(
                        'input[name="value(fdMapServiceType)"]:checked')
                        .val();
                    if (!type) {
                        $('input[value="bmap"]').attr('checked', 'checked');
                        $('tr[cfg-key="mapKey"]').hide();
                        $('#tr_bmap').show();
                    } else {
                        $('tr[cfg-key="mapKey"]').hide();
                        $('#tr_' + type).show();
                        $('#tr_' + type + '_pc').show();
                        $('#tr_' + type + '_security_key').show();
                    }
                }

                function onSave() {
                    var type = $(
                        'input[name="value(fdMapServiceType)"]:checked')
                        .val();
                    var keyInput = $('#tr_' + type + ' input')[0];
                    if (!validation.validateElement(keyInput)) {
                        return;
                    }
                    validation.removeElements(document.sysAppConfigForm,
                        'required');
                    //KMSSValidation_HideWarnHint(ele);
                    Com_Submit(document.sysAppConfigForm, 'update');
                }

                function onMapTypeChange(value) {
                    $('tr[cfg-key="mapKey"]').hide();
                    $('#tr_' + value).show();
                    $('#tr_' + value + '_pc').show();
                    $('#tr_' + value + '_security_key').show();
                    validation.addElements(document.sysAppConfigForm,
                        'required');
                }

                Com_AddEventListener(window, 'load', _onLoad);
            </script>
        </html:form>
    </template:replace>
</template:include>

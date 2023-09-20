<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.landray.kmss.sys.portal.util.*"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<!-- 人员信息列表 -->
<table class="lux-portal-layout-table">
    <tr>
        <td>
            <ui:panel layout="sys.ui.panel.default" toggle="false" height="240" scroll="false">
                <ui:content title="人员信息-默认">
                    <portal:portlet title="专家信息列表" subtitle="" titleicon="" var-rowsize="6" var-cateIds="">
                        <ui:dataview format="sys.ui.personInfo">
                         <ui:source ref="kms.expert.columnList.source" var-rowsize="6" var-cateIds=""></ui:source>
                         <ui:render ref="sys.ui.personInfo.default" var-columnNum="2"></ui:render>
                         <ui:var name="showNoDataTip" value="true"></ui:var>
                         <ui:var name="showErrorTip" value="true"></ui:var>
                        </ui:dataview>
                        <ui:operation href="javascript:(function(){seajs.use(['kms/expert/kms_expert_portlet_ui/js/goToMoreView.js'], function(goToMoreView) {         goToMoreView.goToView('!{cateIds}','');       });})();" name="{operation.more}" target="_self" type="more" align="right"></ui:operation>
                       </portal:portlet>
                </ui:content>
            </ui:panel>
        </td>
    </tr>
    <tr>
        <td>
            <div style="height: 10px;"></div>
        </td>
    </tr>
    <tr>
        <td>
            <ui:panel layout="sys.ui.panel.default" toggle="false" height="240" scroll="false">
                <ui:content title="人员信息-带提问">
                    <portal:portlet title="专家信息列表" subtitle="" titleicon="" var-rowsize="6" var-cateIds="">
                        <ui:dataview format="sys.ui.personInfo">
                         <ui:source ref="kms.expert.columnList.source" var-rowsize="6" var-cateIds=""></ui:source>
                         <ui:render ref="sys.ui.personInfo.expert" var-columnNum="2"></ui:render>
                         <ui:var name="showNoDataTip" value="true"></ui:var>
                         <ui:var name="showErrorTip" value="true"></ui:var>
                        </ui:dataview>
                        <ui:operation href="javascript:(function(){seajs.use(['kms/expert/kms_expert_portlet_ui/js/goToMoreView.js'], function(goToMoreView) {         goToMoreView.goToView('!{cateIds}','');       });})();" name="{operation.more}" target="_self" type="more" align="right"></ui:operation>
                       </portal:portlet>
                </ui:content>
            </ui:panel>
        </td>
    </tr>

    <tr>
        <td>
            <div style="height: 10px;"></div>
        </td>
    </tr>

    <tr>
        <td>
            <ui:panel layout="sys.ui.panel.default" toggle="false" height="240" scroll="false">
                <ui:content title="人员信息-图标文本表格展现">
                    <portal:portlet title="专家信息列表" subtitle="" titleicon="" var-rowsize="6" var-cateIds="">
                        <ui:dataview format="sys.ui.personInfo">
                         <ui:source ref="kms.expert.columnList.source" var-rowsize="6" var-cateIds=""></ui:source>
                         <ui:render ref="sys.ui.personInfo.default" var-columnNum="2"></ui:render>
                         <ui:var name="showNoDataTip" value="true"></ui:var>
                         <ui:var name="showErrorTip" value="true"></ui:var>
                        </ui:dataview>
                        <ui:operation href="javascript:(function(){seajs.use(['kms/expert/kms_expert_portlet_ui/js/goToMoreView.js'], function(goToMoreView) {         goToMoreView.goToView('!{cateIds}','');       });})();" name="{operation.more}" target="_self" type="more" align="right"></ui:operation>
                       </portal:portlet>
                </ui:content>
            </ui:panel>
        </td>
    </tr>
    <tr>
        <td>
            <div style="height: 10px;"></div>
        </td>
    </tr>
    <tr>
        <td>
            <ui:panel layout="sys.ui.panel.default" toggle="false" height="240" scroll="false">
                <ui:content title="人员信息-横向滚动">
                    <portal:portlet title="专家信息列表" subtitle="" titleicon="" var-rowsize="6" var-cateIds="">
                        <ui:dataview format="sys.ui.personInfo">
                         <ui:source ref="kms.expert.columnList.source" var-rowsize="6" var-cateIds=""></ui:source>
                         <ui:render ref="sys.ui.personInfo.solid" var-columnNum="2"></ui:render>
                         <ui:var name="showNoDataTip" value="true"></ui:var>
                         <ui:var name="showErrorTip" value="true"></ui:var>
                        </ui:dataview>
                        <ui:operation href="javascript:(function(){seajs.use(['kms/expert/kms_expert_portlet_ui/js/goToMoreView.js'], function(goToMoreView) {         goToMoreView.goToView('!{cateIds}','');       });})();" name="{operation.more}" target="_self" type="more" align="right"></ui:operation>
                       </portal:portlet>
                </ui:content>
            </ui:panel>
        </td>
    </tr>
    <tr>
        <td>
            <div style="height: 10px;"></div>
        </td>
    </tr>
    
</table>
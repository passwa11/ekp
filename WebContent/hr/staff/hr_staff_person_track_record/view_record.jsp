<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.view">
    <template:replace name="head">
        <style type="text/css">
   			.lui_paragraph_title{
   				font-size: 15px;
   				color: #15a4fa;
   		    	padding: 15px 0px 5px 0px;
   			}
   			.lui_paragraph_title span{
   				display: inline-block;
   				margin: -2px 5px 0px 0px;
   			}
   			.barCodeImg {
   				position:absolute;
   				right:20px;
   			}
        </style>
    </template:replace>
    <template:replace name="title">
        <c:out value="${hrStaffPersonExperienceContractForm.fdName} - "/>
        <c:out value="${lfn:message('hr-staff:hrStaffPersonExperience.type.contract')}" />
    </template:replace>
    <template:replace name="toolbar">
    	<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3" var-navwidth="90%">
    		<ui:button text="${lfn:message('button.close')}" onclick="Com_CloseWindow();" order="1" />
    	</ui:toolbar>
    </template:replace>
    <template:replace name="content">
    	<ui:tabpage expand="false" var-navwidth="90%">
            <table class="tb_normal" width="100%">
                <tr>
                    <td width="15%" class="td_normal_title">
                            ${lfn:message('hr-staff:hrStaffTrackRecord.startTime')}
                    </td>
                    <td width="35%" >
                        <xform:datetime required="true" dateTimeType="date" showStatus="view" property="fdEntranceBeginDate" style="width:98%;" validators="compareEntranceDate"></xform:datetime>
                    </td>
                    <td width="15%" class="td_normal_title">
                            ${lfn:message('hr-staff:hrStaffTrackRecord.finishTime')}
                    </td>
                    <td width="35%">
                        <xform:datetime required="true"  dateTimeType="date" showStatus="view" property="fdEntranceEndDate" style="width:98%;" validators="compareEntranceDate"></xform:datetime>
                    </td>
                </tr>
            </table>
        </ui:tabpage>
    </template:replace>
</template:include>
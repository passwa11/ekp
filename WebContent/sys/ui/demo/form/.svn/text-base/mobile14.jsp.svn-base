<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/sys/mobile/jsp/ajax-accept.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person" %>
<%
request.setAttribute("mobile", true);
%>
<template:include ref="mobile.edit" compatibleMode="true">
    <template:replace name="head">
    	<style>
    		#assistDiv select{
    			padding:0px 5px;
    		}
    	</style>
        <script type="text/javascript">
            var editOption = {
                formName: 'sysNewsMainForm',
                lang: {
                    "the": "${lfn:message('page.the')}",
                    "row": "${lfn:message('page.row')}"
                },
                dialogs: {
                    it_data_base_dialog: {
                        modelName: 'com.landray.kmss.sys.organization.model.SysOrgPerson',
                        sourceUrl: '/sys/ui/demo/form/dialogdata.jsp'
                    }
                }
            };
            window.onload = function(){
	            require(["mui/form"], function(){
	            	doOnLoad();
	    		});
            };
            Com_IncludeFile("mobile_edit.js", "${LUI_ContextPath}/sys/ui/demo/form/", 'js', true);
        </script>
    </template:replace>
    <template:replace name="content">
    <xform:config  orient="vertical">
        <html:form action="/sys/news/sys_news_main/sysNewsMain.do">
	        <xform:config orient="vertical">
	            <div id="scrollView" data-dojo-type="mui/view/DocScrollableView" data-dojo-mixins="mui/form/_ValidateMixin">
	                <div data-dojo-type="mui/panel/AccordionPanel">
	                    <div data-dojo-type="mui/panel/Content" data-dojo-props="title:'测试工具',icon:'mui-ul'">
	                    	<%@ include file="assist.jsp" %>
	                    </div>
	                    <div data-dojo-type="mui/panel/Content" data-dojo-props="title:'表单字段',icon:'mui-ul'">
	                        <div class="muiFormContent">
	                            <table class="muiSimple" cellpadding="0" cellspacing="0">
	                                <tr>
	                                    <td>
	                                    	<div id="_xform_fdText" _xform_type="text">
			                                <xform:text property="fdText" showStatus="${status}" required="${required}" subject="单行文本" mobile="true" style="width:95%;"/>
			                                </div>
	                                    </td>
	                                </tr>
	                                <tr>
	                                    <td>
	                                        <div id="_xform_fdSelect" _xform_type="select">
			                                <xform:select property="fdSelect" showStatus="${status}" required="${required}" subject="下拉选择" mobile="true">
			                                    <xform:enumsDataSource enumsType="common_status" />
			                                </xform:select>
			                                </div>
	                                    </td>
	                                </tr>
	                                <tr>
	                                    <td>
	                                        <div id="_xform_fdRadio" _xform_type="radio">
			                                <xform:radio property="fdRadio" showStatus="${status}" required="${required}" subject="单选按钮" mobile="true">
			                                    <xform:enumsDataSource enumsType="common_status" />
			                                </xform:radio>
			                                </div>
	                                    </td>
	                                </tr>
	                                <tr>
	                                    <td>
	                                        <div id="_xform_fdCheckboxIds" _xform_type="checkbox">
			                                <xform:checkbox property="fdCheckboxIds" showStatus="${status}" required="${required}" subject="多选按钮" mobile="true">
			                                    <xform:enumsDataSource enumsType="common_status" />
			                                </xform:checkbox>
			                                </div>
	                                    </td>
	                                </tr>
	                                <tr>
	                                    <td>
	                                        <div id="_xform_fdDate" _xform_type="datetime">
			                                <xform:datetime property="fdDate" showStatus="${status}" required="${required}" subject="日期选择" dateTimeType="date" style="width:95%;" mobile="true" />
			                            	</div>
	                                    </td>
	                                </tr>
	                                <tr>
	                                    <td>
	                                        <div id="_xform_fdOrgId" _xform_type="address">
			                                <xform:address propertyId="fdOrgId" propertyName="fdOrgName" orgType="ORG_TYPE_ALL" showStatus="${status}" required="${required}" subject="人员" mulSelect="true"  mobile="true" />
			                            	</div>
	                                    </td>
	                                </tr>
	                                <tr>
	                                    <td>
	                                        <div data-dojo-type="mui/form/CommonDialog" 
	                                        	data-dojo-props="orient:'vertical',idField:'fdDialogId',nameField:'fdDialogName',isMul:true,modelName:'com.landray.kmss.sys.organization.model.SysOrgPerson',dataURL:getSource('it_data_base_dialog'),subject:'对话框',curIds:'',curNames:''">
	                                        </div>
	                                    </td>
	                                </tr>
	                                <tr>
	                                    <td>
	                                        <div id="_xform_fdTextarea" _xform_type="textarea">
			                                <xform:textarea property="fdTextarea" showStatus="${status}" required="${required}" subject="多行文本" style="width:95%;" mobile="true" />
			                            	</div>
	                                    </td>
	                                </tr>
	                                <tr>
			                            <td>
			                            	<div id="_xform_fdRtf" _xform_type="textarea">
			                                <xform:rtf property="fdRtf" showStatus="${status}" required="${required}" subject="富文本" mobile="true"/>
			                            	</div>
			                            </td>
			                        </tr>
	                                <tr>
	                                    <td colspan="2">
	                                        <div data-dojo-type="dojox/mobile/ListItem" data-dojo-props='rightIcon:"mui mui-forward",clickable:true, noArrow:true, _setIconAttr:function(){},onClick:function(){expandDetail("TABLE_DocList_fdDetail_Form","scrollView");}'>
	                                            <div layout="left">明细表</div>
	                                            <div layout="right">详情</div>
	                                        </div>
	                                        <div class="detailTableView" data-dojo-type="mui/table/DetailTableScrollableView" data-dojo-mixins="mui/form/_ValidateMixin" id="TABLE_DocList_fdDetail_Form_view">
	                                            <div data-dojo-type="dojox/mobile/Heading" fixed="top" class="muiHeaderDetail">
	                                                <div class="muiHeaderDetailTitle">明细表
	                                                </div>
	                                                <div class="muiHeaderDetailBack" onclick="collapseDetail('TABLE_DocList_fdDetail_Form')">
	                                                    <bean:message key="button.save" />
	                                                </div>
	                                            </div>
	                
	                                            <table cellspacing="0" cellpadding="0" class="detailTableSimple">
	                                                <tr>
	                                                    <td class="detailTableSimpleTd">
	                                                        <table width="100%" border="0" cellspacing="0" cellpadding="0" id="TABLE_DocList_fdDetail_Form">
	                                                            <tr style="display:none;">
	                                                                <td>
	                                                                </td>
	                                                            </tr>
	                                                            <tr data-dojo-type="mui/form/Template" KMSS_IsReferRow="1" style="display:none;" border='0'>
	                                                                <td class="detail_wrap_td">
	                                                                    <xform:text showStatus="noShow" property="fdDetail_Form[!{index}].fdId" />
	                                                                    <table class="muiSimple">
	                                                                        <tr>
	                                                                            <td align="left" valign="middle" class="muiDetailTableNo">
	                                                                                <span>${lfn:message('page.the')}!{index}${ lfn:message('page.row') }</span>
	                                                                                <div class="muiDetailTableDel" onclick="deleteDetailRow('TABLE_DocList_fdDetail_Form',this);">
	                                                                                    删除
	                                                                                </div>
	                                                                            </td>
	                                                                        </tr>
	                                                                        <tr>
	                                                                            <td>
										                                            <div id="_xform_fdDetail_Form[!{index}].fdText" _xform_type="text">
										                                            <xform:text mobile="true" property="fdDetail_Form[!{index}].fdText" showStatus="${status}" required="${required}" subject="单行文本" style="width:95%;" />
										                                        	</div>
	                                                                            </td>
	                                                                        </tr>
	                                                                        <tr>
	                                                                            <td>
	                                                                                <div id="_xform_fdDetail_Form[!{index}].fdSelect" _xform_type="select">
										                                            <xform:select mobile="true" property="fdDetail_Form[!{index}].fdSelect" showStatus="${status}" required="${required}" subject="下拉选择">
										                                                <xform:enumsDataSource enumsType="common_status" />
										                                            </xform:select>
										                                            </div>
	                                                                            </td>
	                                                                        </tr>
	                                                                        <tr>
	                                                                            <td>
	                                                                                <div id="_xform_fdDetail_Form[!{index}].fdRadio" _xform_type="radio">
										                                            <xform:radio mobile="true" property="fdDetail_Form[!{index}].fdRadio" showStatus="${status}" required="${required}" subject="单选按钮" >
										                                                <xform:enumsDataSource enumsType="common_yesno" />
										                                            </xform:radio>
										                                            </div>
	                                                                            </td>
	                                                                        </tr>
	                                                                        <tr>
	                                                                            <td>
	                                                                                <div id="_xform_fdDetail_Form[!{index}].fdCheckboxIds" _xform_type="checkbox">
										                                            <xform:checkbox mobile="true" property="fdDetail_Form[!{index}].fdCheckboxIds" showStatus="${status}" required="${required}" subject="多选按钮" >
										                                                <xform:enumsDataSource enumsType="common_yesno" />
										                                            </xform:checkbox>
										                                            </div>
	                                                                            </td>
	                                                                        </tr>
	                                                                        <tr>
	                                                                            <td>
	                                                                                <div id="_xform_fdDetail_Form[!{index}].fdDate" _xform_type="datetime">
										                                            <xform:datetime mobile="true" property="fdDetail_Form[!{index}].fdDate" showStatus="${status}" required="${required}" subject="日期选择" dateTimeType="date" style="width:95%;" />
										                                        	</div>
	                                                                            </td>
	                                                                        </tr>
	                                                                        <tr>
	                                                                            <td>
	                                                                                <div id="_xform_fdDetail_Form[!{index}].fdOrgId" _xform_type="address">
										                                            <xform:address mobile="true" propertyId="fdDetail_Form[!{index}].fdOrgId" propertyName="fdDetail_Form[!{index}].fdOrgName" orgType="ORG_TYPE_PERSON" showStatus="${status}" required="${required}" subject="人员" style="width:95%;" />
										                                        	</div>
	                                                                            </td>
	                                                                        </tr>
	                                                                        <tr>
	                                                                            <td>
	                                                                                <div data-dojo-type="mui/form/CommonDialog" 
	                                                                                	data-dojo-props="orient:'vertical',idField:'fdDetail_Form[!{index}].fdDialogId',nameField:'fdDetail_Form[!{index}].fdDialogName',isMul:false,modelName:'com.landray.kmss.sys.organization.model.SysOrgPerson',dataURL:getSource('it_data_base_dialog'),subject:'对话框'">
	                                                                                </div>
	                                                                            </td>
	                                                                        </tr>
	                                                                    </table>
	                                                                </td>
	                                                            </tr>
	                                                        </table>
	                                                        <br/>
	                                                    </td>
	                                                </tr>
	                                            </table>
	                                     <!--        <br/>
	                                            <br/> -->
	                                            <ul data-dojo-type="mui/tabbar/TabBar" fixed="bottom" data-dojo-props='fill:"grid"'>
	                                                <li data-dojo-type="mui/tabbar/TabBarButton" class="muiBtnNext " data-dojo-props="colSize:4,onClick:function(){addDetailRow('TABLE_DocList_fdDetail_Form');}, label:'${lfn:message('doclist.add')}'" style="width:95%">
	                                                </li>
	                                            </ul>
	                                        </div>
	                                        <input type="hidden" name="fdDetail_Flag" value="1">
	                                        <script>
	                                            Com_IncludeFile("doclist.js");
	                                        </script>
	                                        <script>
	                                            DocList_Info.push('TABLE_DocList_fdDetail_Form');
	                                        </script>
	                                    </td>
	                                </tr>
	                            </table>
	                            <div style="height:200px;"></div>
	                        </div>
	                    </div>
	                </div>
	            </div>
	            <html:hidden property="fdId" />
	            <html:hidden property="method_GET" />
	        </xform:config>
        </html:form>
     </xform:config>
    </template:replace>
</template:include>
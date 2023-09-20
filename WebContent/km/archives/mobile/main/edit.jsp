<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Calendar"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/mobile/jsp/ajax-accept.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<template:include ref="mobile.edit" compatibleMode="true">
    <template:replace name="title" >
        <c:choose>
            <c:when test="${kmArchivesMainForm.method_GET == 'add' }">
                <c:out value="${ lfn:message('operation.create') } - ${ lfn:message('km-archives:table.kmArchivesMain') }" />
            </c:when>
            <c:otherwise>
                <c:out value="${kmArchivesMainForm.docSubject} - " />
                <c:out value="${ lfn:message('km-archives:table.kmArchivesMain') }" />
            </c:otherwise>
        </c:choose>
    </template:replace>
    <template:replace name="head">
        <script type="text/javascript">
            require(["dojo/store/Memory","dojo/topic","dijit/registry"],function(Memory,topic,registry){
                var navData = [{'text':'01  /  ${lfn:message("sys-mobile:mui.mobile.info") }',
                    'moveTo':'scrollView','selected':true},{'text':'02  /  ${lfn:message("sys-mobile:mui.mobile.review") }',
                    'moveTo':'lbpmView'}]
                window._narStore = new Memory({data:navData});
                var changeNav = function(view){
                    var wgt = registry.byId("_flowNav");
                    for(var i=0;i<wgt.getChildren().length;i++){
                        var tmpChild = wgt.getChildren()[i];
                        if(view.id == tmpChild.moveTo){
                            tmpChild.beingSelected(tmpChild.domNode);
                            return;
                        }
                    }
                }
                topic.subscribe("mui/form/validateFail",function(view){
                    changeNav(view);
                });
                topic.subscribe("mui/view/currentView",function(view){
                    changeNav(view);
                });
            });
       </script>
    </template:replace>
    <template:replace name="content">
        <html:form action="/km/archives/km_archives_main/kmArchivesMain.do">
            <div data-dojo-type="mui/fixed/Fixed" class="muiFlowEditFixed">
                <div data-dojo-type="mui/fixed/FixedItem"
                    class="muiFlowEditFixedItem">
                    <div data-dojo-type="mui/nav/NavBarStore" id="_flowNav"
                        data-dojo-props="store:_narStore"></div>
                </div>
            </div>
            <div data-dojo-type="mui/view/DocScrollableView" 
                data-dojo-mixins="mui/form/_ValidateMixin" id="scrollView"
                <html:hidden property="fdId" />
                <html:hidden property="method_GET" />
                <html:hidden property="docStatus"/>
                <html:hidden property="docTemplateId" />
                
                <div class="muiFlowInfoW muiFormContent">
                    <table class="muiSimple" cellpadding="0" cellspacing="0">
                        <!-- 档案名称  -->
                        <tr>
                            <td class="muiTitle">
                                ${lfn:message('km-archives:kmArchivesMain.docSubject')}
                            </td>
                            <td>
                                <xform:text property="docSubject" showStatus="edit" mobile="true" />
                            </td>
                        </tr>
                        <!-- 保管员  -->
                        <tr>
                            <td class="muiTitle">
                                ${lfn:message('km-archives:kmArchivesMain.fdStorekeeper')}
                            </td>
                            <td>
                                <xform:address propertyId="fdStorekeeperId" propertyName="fdStorekeeperName" orgType="ORG_TYPE_PERSON|ORG_TYPE_POST" showStatus="edit" required="true" subject="${lfn:message('km-archives:kmArchivesMain.fdStorekeeper')}" mobile="true" />
                            </td>
                        </tr>
                        <!-- 保管单位  -->
                        <tr>
                            <td class="muiTitle">
                                ${lfn:message('km-archives:kmArchivesMain.fdUnit')}
                            </td>
                            <td>
                                <xform:select property="fdUnit"  showStatus="edit" subject="${lfn:message('km-archives:kmArchivesMain.fdUnit')}" mobile="true">
                                     <xform:beanDataSource serviceBean="kmArchivesUnitService" selectBlock="fdName" whereBlock="" orderBy="fdOrder asc" />
                                 </xform:select>
                            </td>
                        </tr>
                        <!-- 归档人  -->
                        <tr>
                            <td class="muiTitle">
                                ${lfn:message('km-archives:kmArchivesMain.docCreator')}
                            </td>
                            <td>
                                <xform:text property="docCreatorName" showStatus="view" mobile="true" />
                            </td>
                        </tr>
                        <!-- 归档日期  -->
                        <tr>
                            <td class="muiTitle">
                                ${lfn:message('km-archives:kmArchivesMain.fdFileDate')}
                            </td>
                            <td>
                                <xform:datetime required="true" property="fdFileDate" showStatus="edit" dateTimeType="date" mobile="true" />
                            </td>
                        </tr>
                        <!-- 保管期限  -->
                        <tr>
                            <td class="muiTitle">
                                ${lfn:message('km-archives:kmArchivesMain.fdPeriod')}
                            </td>
                            <td>
                                <xform:select property="fdPeriod" showStatus="edit" subject="${lfn:message('km-archives:kmArchivesMain.fdPeriod')}" mobile="true">
                                     <xform:beanDataSource serviceBean="kmArchivesPeriodService" selectBlock="fdSaveLife,fdName" whereBlock="" orderBy="fdOrder" />
                                 </xform:select>
                            </td>
                        </tr>
                        <!-- 密级程度 -->
                        <tr>
                        	<td class="muiTitle">
                   				${lfn:message('km-archives:kmArchivesMain.fdDenseLevel')}
               				</td>
               				<td>
			                   <xform:select property="fdDenseId" showStatus="edit" subject="${lfn:message('km-archives:kmArchivesMain.fdDenseLevel')}" mobile="true">
			                       <xform:beanDataSource serviceBean="kmArchivesDenseService" orderBy="fdOrder" />
			                   </xform:select>
			               </td>
               			</tr>
                        <!-- 档案有效期  -->
                        <tr>
                            <td class="muiTitle">
                                ${lfn:message('km-archives:kmArchivesMain.fdValidityDate')}
                            </td>
                            <td>
                                <xform:datetime  property="fdValidityDate" showStatus="edit" dateTimeType="date" mobile="true" htmlElementProperties="id='fdValidityDate'"/>
                            </td>
                        </tr>
                        <!-- 所属卷库 -->
                        <tr>
                            <td class="muiTitle">
                                 ${lfn:message('km-archives:kmArchivesMain.fdLibrary')}
                            </td>
                            <td>
                                <xform:select property="fdLibrary" showStatus="edit" subject="${lfn:message('km-archives:kmArchivesMain.fdLibrary')}" mobile="true">
                                     <xform:beanDataSource serviceBean="kmArchivesLibraryService" selectBlock="fdName" orderBy="fdOrder" />
                                 </xform:select>
                            </td>
                        </tr>
                        <!-- 组卷年度  -->
                        <tr>
                            <td class="muiTitle">
                                ${lfn:message('km-archives:kmArchivesMain.fdVolumeYear')}
                            </td>
                            <td>
                                <xform:select property="fdVolumeYear" mobile="true">
                                <% int nowYear = Calendar.getInstance().get(Calendar.YEAR);
                                    for(int x = nowYear;x>=1967;x--) { 
                                    pageContext.setAttribute("selectYearIndex",x);%>
                                    <xform:simpleDataSource value="${selectYearIndex }"></xform:simpleDataSource>
                                    <%} %>
                                </xform:select>
                            </td>
                        </tr>
                        <!-- 备注  -->
                        <tr>
                            <td class="muiTitle">
                                ${lfn:message('km-archives:kmArchivesMain.fdRemarks')}
                            </td>
                            <td>
                                <xform:textarea property="fdRemarks" showStatus="edit" mobile="true" />
                            </td>
                        </tr>
                    </table>
                </div>
                <!-- 文件级 -->
                 <div data-dojo-type="mui/panel/AccordionPanel">
                    <div data-dojo-type="mui/panel/Content" data-dojo-props="title:'${ lfn:message('km-archives:kmArchivesMain.fileLevel') }',icon:'mui-ul'">
                        <c:if test="${not empty kmArchivesMainForm.extendFilePath }">
                      		<table class="muiSimple" cellpadding="0" cellspacing="0">
                                  <c:import url="/sys/property/include/sysProperty_pda.jsp" charEncoding="UTF-8">
                                      <c:param name="formName" value="kmArchivesMainForm" />
                                      <c:param name="fdDocTemplateId" value="${kmArchivesMainForm.docTemplateId}" />
                                  </c:import>
                           	</table>
                        </c:if>
                            <!-- 附件机制 -->
                        <table class="muiSimple">
                            <tr>
                                <td class="muiTitle">
                                    ${ lfn:message('km-archives:kmArchivesMain.attachement') }
                                </td>
                                <td>
                                    <c:import url="/sys/attachment/mobile/import/edit.jsp" charEncoding="UTF-8">
                                        <c:param name="formName" value="kmArchivesMainForm" />
                                        <c:param name="fdKey" value="attArchivesMain" />
                                        <c:param name="fdRequired" value="true" />
                                    </c:import>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
                <ul data-dojo-type="mui/tabbar/TabBar" fixed="bottom" data-dojo-props='fill:"grid"'>
                    <li data-dojo-type="mui/tabbar/TabBarButton" class="muiBtnNext " 
                        data-dojo-props='colSize:2,moveTo:"lbpmView",transition:"slide"'>
                        下一步
                    </li>
                </ul>
            </div>
            <!-- 流程机制 -->
            <c:import url="/sys/lbpmservice/mobile/import/edit.jsp" charEncoding="UTF-8">
                <c:param name="formName" value="kmArchivesMainForm" />
                <c:param name="fdKey" value="kmArchivesMain" />
                <c:param name="viewName" value="lbpmView" />
                <c:param name="backTo" value="scrollView" />
                <c:param name="onClickSubmitButton" value="submitForm();" />
            </c:import>
            <!-- 权限机制 -->
            <c:import url="/sys/right/mobile/edit_hidden.jsp" charEncoding="UTF-8">
                <c:param name="formName" value="kmArchivesMainForm" />
                <c:param name="moduleModelName" value="com.landray.kmss.km.archives.model.KmArchivesMain" />
            </c:import>
            <!-- 版本机制 -->
            <c:import url="/sys/edition/import/sysEditionMain_edit.jsp" charEncoding="UTF-8">
                <c:param name="formName" value="kmArchivesMainForm" />
            </c:import>
        </html:form>
    </template:replace>
</template:include>

<script type="text/javascript">

    require(['dojo/ready', 'dijit/registry', 'dojo/topic', 'dojo/request', 'dojo/date/locale',
             'dojo/dom', 'dojo/dom-attr', 'dojo/dom-style', 'dojo/dom-class', 'dojo/query', 'mui/form/ajax-form!kmArchivesMainForm'], 
            function(ready, registry, topic, request,locale, dom, domAttr, domStyle, domClass, query){
        topic.subscribe('/mui/form/valueChanged',function(widget,args){
            if(widget && widget.name=="fdPeriod"){
                var fdFileDate = query('[name="fdFileDate"]')[0].value;
                if(fdFileDate&&args.value){
                    var d=locale.parse(fdFileDate,{
                        selector : 'time',
                        timePattern : "${ lfn:message('date.format.date') }"
                    });
                    var fdYear = d.getFullYear();
                    var fdMonth = parseInt(d.getMonth());
                    var fdDate = d.getDate();
                    var dd = new Date(parseInt(fdYear)+parseInt(args.value),fdMonth+"",fdDate+"");
                    
                    registry.byId('fdValidityDate').set("value",locale.format(dd,{
                        selector : 'time',
                        timePattern : "${ lfn:message('date.format.date') }"
                    }));
                }
            }
            
            if(widget && widget.valueField=="fdFileDate"){
                var fdPeriod = query('[name="fdPeriod"]')[0].value;
                if(fdPeriod&&args.value){
                    var d=locale.parse(args.value,{
                        selector : 'time',
                        timePattern : "${ lfn:message('date.format.date') }"
                    });
                    var fdYear = d.getFullYear();
                    var fdMonth = parseInt(d.getMonth());
                    var fdDate = d.getDate();
                    var dd = new Date(parseInt(fdYear)+parseInt(fdPeriod),fdMonth+"",fdDate+"");
                    
                    registry.byId('fdValidityDate').set("value",locale.format(dd,{
                        selector : 'time',
                        timePattern : "${ lfn:message('date.format.date') }"
                    }));
                }
            }
        });
        
        //校验对象
        var validorObj = null;
        
        ready(function(){
            validorObj = registry.byId('scrollView');
            // 填充流程信息（临时解决方案）
            query('input[name="sysWfBusinessForm.fdParameterJson"]')[0].value = '${kmArchivesMainForm.sysWfBusinessForm.fdParameterJson}';

        });
        
        window.submitForm = function(){
            if(!validorObj.validate()){
                return;
            }
            var action = document.kmArchivesMainForm.action;
            document.kmArchivesMainForm.action = Com_SetUrlParameter(action,"docStatus", "20");
            var method_GET = '${kmArchivesMainForm.method_GET}';
            if(method_GET == 'add'){
                Com_Submit(document.kmArchivesMainForm, 'save');
            }else{
                Com_Submit(document.kmArchivesMainForm, 'update');
            }   
        }
        
    });
    

</script>
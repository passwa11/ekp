<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include ref="config.list">
    <template:replace name="content">
        <div style="margin:5px 10px;">
            <!-- 筛选 -->
			<list:criteria id="criteria1">
                <list:cri-criterion title="${lfn:message('fssc-expense:fsscExpenseItemConfig.fdCompany')}" key="fdCompanyName">
                    <list:box-select>
                        <list:item-select type="lui/criteria/criterion_input!TextInput">
                            <ui:source type="Static">
                                [{placeholder:'${lfn:message('fssc-expense:fsscExpenseItemConfig.fdCompany')}'}]
                            </ui:source>
                        </list:item-select>
                    </list:box-select>
                </list:cri-criterion>
                <list:cri-criterion title="${lfn:message('fssc-expense:fsscExpenseItemConfig.fdCategory')}" key="fdCategory.fdName">
                    <list:box-select>
                        <list:item-select type="lui/criteria/criterion_input!TextInput">
                            <ui:source type="Static">
                                [{placeholder:'${lfn:message('fssc-expense:fsscExpenseItemConfig.fdCategory')}'}]
                            </ui:source>
                        </list:item-select>
                    </list:box-select>
                </list:cri-criterion>
                <kmss:ifModuleExist path="/fssc/budget">
                <list:cri-auto modelName="com.landray.kmss.fssc.expense.model.FsscExpenseItemConfig" property="fdIsNeedBudget" />
                </kmss:ifModuleExist>
            </list:criteria>
            
            <!-- 操作 -->
            <div class="lui_list_operation">

                <div style="float:left;">
                    <list:paging layout="sys.ui.paging.top" />
                </div>
                <div style="float:right">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar count="7" id="btn">
							<kmss:auth requestURL="/fssc/expense/fssc_expense_item_config/fsscExpenseItemConfig.do?method=add" requestMethod="GET">
								<ui:button text="${lfn:message('button.add')}"  onclick="addDoc();" order="1" ></ui:button>
							</kmss:auth>
							 <kmss:auth requestURL="/fssc/expense/fssc_expense_item_config/fsscExpenseItemConfig.do?method=deleteall">
                                <c:set var="canDelete" value="true" />
                            </kmss:auth>
                            <!---->
                            <ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAll()" order="4" id="btnDelete" />
                        </ui:toolbar>
                    </div>
                </div>
            </div>
            <ui:fixed elem=".lui_list_operation" />
            <!-- 列表 -->
            <list:listview id="listview">
                <ui:source type="AjaxJson">
                    {url:appendQueryParameter('/fssc/expense/fssc_expense_item_config/fsscExpenseItemConfig.do?method=data')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" rowHref="/fssc/expense/fssc_expense_item_config/fsscExpenseItemConfig.do?method=view&fdId=!{fdId}" name="columntable">
                    <list:col-checkbox />
                    <list:col-serial/>
                    <list:col-auto props="fdCompany;fdCategory;fdIsNeedBudget;fdItemList" /></list:colTable>
            </list:listview>
            <!-- 翻页 -->
            <list:paging />
        </div>
        <script>
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                modelName: 'com.landray.kmss.fssc.expense.model.FsscExpenseItemConfig',
                templateName: '',
                basePath: '/fssc/expense/fssc_expense_item_config/fsscExpenseItemConfig.do',
                canDelete: '${canDelete}',
                mode: '',
                templateService: '',
                templateAlert: '${lfn:message("fssc-expense:treeModel.alert.templateAlert")}',
                customOpts: {

                    ____fork__: 0
                },
                lang: {
                    noSelect: '${lfn:message("page.noSelect")}',
                    comfirmDelete: '${lfn:message("page.comfirmDelete")}'
                }

            };
            Com_IncludeFile("list.js", "${LUI_ContextPath}/fssc/expense/resource/js/", 'js', true);
            seajs.use(['lui/dialog','lang!eop-basedata'],function(dialog,lang){
            	// 新建
		 		window.addDoc = function(fdId) {
		 			Com_OpenWindow('<c:url value="/fssc/expense/fssc_expense_item_config/fsscExpenseItemConfig.do?method=add" />');
		 		};
		 		//导出
		 		window.exportData = function(){
		 			var fdCompanyId = Com_GetUrlParameter(window.location.href,'fdCompanyId');
		 			fdCompanyId = fdCompanyId?fdCompanyId:'';
		 			window.open(Com_Parameter.ContextPath+"eop/basedata/eop_basedata_business/eopBasedataBusiness.do?method=exportData&modelName="+listOption.modelName+'&fdCompanyId='+fdCompanyId);
		 		}
		 		//导入
		 		window.importData = function(){
		 			var formName = listOption.modelName.split(".");
		 			formName = formName[formName.length-1];
		 			formName = formName.substring(0,1).toLowerCase()+formName.substring(1);
		 			dialog.iframe(
		 				'/eop/basedata/resource/jsp/eopBasedataImport.jsp?modelName='+listOption.modelName+"&tempate="+formName,
		 				lang['message.import.title'],
		 				function(){
		 				},{height:600,width:800}
		 			);
		 		}
		 		//下载模板
		 		window.downloadTemplate = function(){
		 			window.open(Com_Parameter.ContextPath+'eop/basedata/eop_basedata_business/eopBasedataBusiness.do?method=downloadTemplate&modelName='+listOption.modelName);
		 		}
            });
        </script>
        <c:import url="/eop/basedata/resource/jsp/eopBasedataImport_include.jsp">
        </c:import>
    </template:replace>
</template:include>

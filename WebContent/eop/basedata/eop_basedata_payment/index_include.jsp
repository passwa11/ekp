<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include ref="default.simple4list">
    <template:replace name="title">
        <c:out value="${ lfn:message('eop-basedata:module.fssc.base') }-${ lfn:message('eop-basedata:table.eopBasedataPayment') }" />
    </template:replace>
    <template:replace name="content">
    	<ui:tabpanel id="eopBasedataPaymentPanel" layout="sys.ui.tabpanel.list" cfg-router="true">
		<ui:content id="eopBasedataPaymentContent" title="${ lfn:message('eop-basedata:message.include.payment.content.title') }">
        <div style="margin:5px 10px;">
            <!-- 筛选 -->
            <list:criteria id="criteria1">
                <list:cri-ref key="fdSubject" ref="criterion.sys.docSubject" title="${lfn:message('eop-basedata:eopBasedataPayment.fdSubject')}" />
                <list:cri-criterion title="${lfn:message('eop-basedata:eopBasedataPayment.fdModelName')}" key="fdModelName" expand="true">
                    <list:box-select>
                        <list:item-select type="lui/criteria/select_panel!TabCriterionSelectDatas">
                            <ui:source type="AjaxJson">
                                {url:'/eop/basedata/eop_basedata_payment/eopBasedataPayment.do?method=getModelName'}
                            </ui:source>
                        </list:item-select>
                    </list:box-select>
                </list:cri-criterion>
                <list:cri-auto modelName="com.landray.kmss.eop.basedata.model.EopBasedataPayment" property="fdModelNumber"  expand="true"/>
                <list:cri-auto modelName="com.landray.kmss.eop.basedata.model.EopBasedataPayment" property="fdPaymentTime" />
                <list:cri-auto modelName="com.landray.kmss.eop.basedata.model.EopBasedataPayment" property="fdStatus" />

            </list:criteria>
            <!-- 操作 -->
            <div class="lui_list_operation">

                <div style='color: #979797;float: left;padding-top:1px;'>
                    ${ lfn:message('list.orderType') }：
                </div>
                <div style="float:left">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
                            <list:sort property="eopBasedataPayment.fdPaymentTime" text="${lfn:message('eop-basedata:eopBasedataPayment.fdPaymentTime')}" group="sort.list" />
                        </ui:toolbar>
                    </div>
                </div>
                <div style="float:left;">
                    <list:paging layout="sys.ui.paging.top" />
                </div>
                <div style="float:right">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar count="3">
							<kmss:auth requestURL="/eop/basedata/eop_basedata_payment/eopBasedataPayment.do?method=batchConfirmPayment">
                                <ui:button text="${lfn:message('eop-basedata:button.batchConfirmPayment')}" onclick="batchConfirmPayment()" order="2" />
                            </kmss:auth>
                            <kmss:auth requestURL="/eop/basedata/eop_basedata_payment/eopBasedataPayment.do?method=add">
                                <ui:button text="${lfn:message('button.add')}" onclick="addDoc()" order="2" />
                            </kmss:auth>
                            <kmss:auth requestURL="/eop/basedata/eop_basedata_payment/eopBasedataPayment.do?method=deleteall">
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
                    {url:appendQueryParameter('/eop/basedata/eop_basedata_payment/eopBasedataPayment.do?method=data')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" rowHref="/eop/basedata/eop_basedata_payment/eopBasedataPayment.do?method=view&fdId=!{fdId}" name="columntable">
                    <list:col-checkbox />
                    <list:col-serial/>
                    <list:col-auto props="fdModelName;fdSubject;fdModelNumber;fdPaymentMoney;fdPaymentTime;fdStatus.name" /></list:colTable>
            </list:listview>
            <!-- 翻页 -->
            <list:paging />
        </div>
        </ui:content>
        </ui:tabpanel>
        <script>
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataPayment',
                templateName: '',
                basePath: '/eop/basedata/eop_basedata_payment/eopBasedataPayment.do',
                canDelete: '${canDelete}',
                mode: '',
                customOpts: {

                    ____fork__: 0
                },
                lang: {
                    noSelect: '${lfn:message("page.noSelect")}',
                    comfirmDelete: '${lfn:message("page.comfirmDelete")}'
                }

            };
            Com_IncludeFile("list.js", "${LUI_ContextPath}/eop/basedata/resource/js/", 'js', true);
            seajs.use(['lui/dialog'],function(dialog){
            	window.batchConfirmPayment = function(){
            		var fdModelName = LUI('criteria1').findSelectedValuesByKey("fdModelName").values;
            		if(fdModelName.length>1){
            			dialog.alert("${lfn:message('eop-basedata:message.multiSelect')}");
            			return;
            		}
            		if(fdModelName.length==0){
            			dialog.alert("${lfn:message('eop-basedata:message.selectOne')}");
            			return;
            		}
            		fdModelName = fdModelName[0].value;
            		var fdPaymentStatus = LUI('criteria1').findSelectedValuesByKey("fdStatus").values;
            		if(fdPaymentStatus.length!=1||fdPaymentStatus[0].value!='1'){
            			dialog.alert("${lfn:message('eop-basedata:message.selectStatus')}");
            			return;
            		}
					var ids = [];
					$("[name=List_Selected]:checked").each(function(){
						ids.push(this.value);
					})
					if(ids.length==0){
						dialog.alert("${lfn:message('page.noSelect')}")
						return;
					}
					dialog.confirm("${lfn:message('eop-basedata:message.batchConfirmPayment')}",function(rtn){
						if(rtn){
							var modelIds = [],data = LUI('listview')._data.datas;
							for(var i=0;i<data.length;i++){
								for(var k=0;k<ids.length;k++){
									if(ids[k]==getValueByColName(data[i],'fdId')){
										modelIds.push(getValueByColName(data[i],'fdModelId'));
									}
								}
							}
							var dia = dialog.loading();
							$.ajax({
		                		url:'${LUI_ContextPath}/eop/basedata/eop_basedata_payment/eopBasedataPayment.do?method=batchConfirmPayment',
		                		data:{'ids':modelIds.join(';'),'fdModelName':fdModelName},
		                		dataType:'json',
		                		async:false,
		                		success:function(rtn){
		                			dia.hide();
		                			if(rtn.result == 'success'){
		                    			dialog.success("${lfn:message('return.success')}");
		                    			topic.publish('list.refresh');
		                			}else{
		                				dialog.alert(rtn.message);
		                			}
		                		}
		                	});
						}
					})
				}
            	window.getValueByColName = function(data,col){
                	for(var i=0;i<data.length;i++){
                		if(data[i].col==col){
                			return data[i].value;
                		}
                	}
                }
            })
        </script>
    </template:replace>
</template:include>

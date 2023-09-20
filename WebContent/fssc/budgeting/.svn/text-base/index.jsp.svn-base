<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/resource/jsp/htmlhead.jsp" %>
<script type="text/javascript">
Com_IncludeFile("document.js", "style/"+Com_Parameter.Style+"/doc/");
Com_IncludeFile("docutil.js|optbar.js|jquery.js|data.js");
Com_IncludeFile("jeui.js", "${LUI_ContextPath}/fssc/budgeting/resource/js/", "js", true);
</script>
<link rel="stylesheet" type="text/css" href="${LUI_ContextPath}/fssc/budgeting/resource/css/jeui.css" />
<template:include ref="default.list" spa="true" rwd="true">
    <template:replace name="title">
        <c:out value="${ lfn:message('fssc-budgeting:module.fssc.budgeting') }" />
    </template:replace>
    <template:replace name="nav">
    	<ui:combin ref="menu.nav.title">
	            <ui:varParam name="title" value="${ lfn:message('fssc-budgeting:module.fssc.budgeting') }" />
	            <ui:varParam name="operation">
					<ui:source type="Static">
						
					</ui:source>
				</ui:varParam>
	        </ui:combin>
    	<div id="menu_nav" class="lui_list_nav_frame">
            <ui:accordionpanel>
                <c:if test="${param.j_iframe=='true'}">
                    <c:set var="j_iframe" value="?j_iframe=true" />
                </c:if>
				<ui:content title="${ lfn:message('fssc-budgeting:module.fssc.budgeting') }">
					<div class="je-p20">
						<xform:select property="fdSchemeId" style="width:160px;" showStatus="edit" onValueChange="refresh"></xform:select>
				    	<div id="org" class="je-tree lui_accordionpanel_content_l"></div>
					</div>
				</ui:content>
				<kmss:authShow roles="ROLE_FSSCBUDGETING_SETTING;ROLE_FSSCBUDGETING_OPEN;ROLE_FSSCBUDGETING_PERIOD">
				<ui:content title="${ lfn:message('fssc-budget:py.QiTaCaoZuo') }">
						<ui:combin ref="menu.nav.simple">
							<ui:varParam name="source">
			  					<ui:source type="Static">
			  					[{
			  						"text" : "${ lfn:message('list.manager') }",
			  						"href" :  "javascript:LUI.pageOpen('${LUI_ContextPath }/sys/profile/moduleindex.jsp?nav=/fssc/budgeting/tree.jsp','_rIframe');",
				  					"icon" : "lui_iconfont_navleft_com_background"
			  					}]
			  					</ui:source>
			  				</ui:varParam>
			  			</ui:combin>
					</ui:content>
				</kmss:authShow>
            </ui:accordionpanel>
        </div>
<script type="text/javascript">
Com_AddEventListener(window,'load',function(){
	var paramlist=[];
	jeui.use(["jeTree"],function () {
	    $("#params").jeTree({
	    	 skin:"je-bg-navy",  
	    	datas:paramlist,
	        itemfun:function (item) {
	        	console.log(item.url)
	        }
	    });
	    var orglist;
	    //切换预算方案
	  	window.refresh=function(){
	  		var fdSchemeId=$("[name='fdSchemeId']").val();
	  		var val=fdSchemeId.split("|");
	  		var data = new KMSSData();
	  		data.UseCache = false;
	  		var rtn = data.AddBeanData("fsscBudgetingNavService&authCurrent=true&queryFlag=org&fdSchemeId="+val[0]).GetHashMapArray();
	  		if(rtn&&rtn.length > 0){
	  			orglist=rtn[1]["orglist"];
	  			orglist=JSON.parse(orglist);//将json字符串转为对象，才能正常显示
	  			$("[name='fdDimension']").val(fdSchemeId);
	  			  $("#org").jeTree({
	  				  skin:"je-bg-navy",  
	  				  datas:orglist,
	  			        itemfun:function (item) {
	  			        	item.url=item.url+"&fdSchemeId="+val[0];
	  			        }
	  			    })
	  		}else{
	  		  $("#org").jeTree({
	  			  skin:"je-bg-navy",    
	  			  datas:[],
	  		        itemfun:function (item) {
	  		        	console.log(item.url);
	  		        }
	  		    })
	  		}
	  	}
	});
});
function changeColorForTitle(){
	if($(".lui_accordionpanel_nav_text").css("color") != $(".je-bg-navy").find("a").css("color")){
		 $(".je-bg-navy").find("a").css("color",$(".lui_accordionpanel_nav_text").css("color"));
	  	 $(".je-bg-navy").find("a").on("mouseover",function(){
	  			$(this).css("color","rgb(66, 133, 244)")
	  		}).on("mouseout",function(){
  				$(this).css("color",$(".lui_accordionpanel_nav_text").css("color"))
  			}); 
		setTimeout(changeColorForTitle,100);
	}


};
</script>
	
        
    </template:replace>
    <template:replace name="content">
    <ui:tabpanel id="fsscBudgetingPanel" layout="sys.ui.tabpanel.list"  cfg-router="true">
		<ui:content id="fsscBudgetingContent" title="" cfg-route="{path:'/listCreate'}">
            <!-- 操作 -->
            <div class="lui_list_operation">
                <div style='color: #979797;float: left;padding-top:1px;'>
                    ${ lfn:message('list.orderType') }：
                </div>
                <div style="float:left">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
                            <list:sort property="fsscBudgetingMain.docNumber" text="${lfn:message('fssc-budgeting:fsscBudgetingMain.docNumber')}" group="sort.list" />
                        </ui:toolbar>
                    </div>
                </div>
                <div style="float:left;">
                    <list:paging layout="sys.ui.paging.top" />
                </div>
                <div style="float:right">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar count="5">
                            <ui:button text="${lfn:message('button.add')}" onclick="addDoc()" order="2" />
                            <ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAll()" order="4" id="btnDelete" />
                        </ui:toolbar>
                    </div>
                </div>
            </div>
            <ui:fixed elem=".lui_list_operation" />
            <!-- 列表 -->
            <list:listview id="listview">
                <ui:source type="AjaxJson">
                    {url:appendQueryParameter('/fssc/budgeting/fssc_budgeting_main/fsscBudgetingMain.do?method=data')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" rowHref="/fssc/budgeting/fssc_budgeting_main/fsscBudgetingMain.do?method=view&fdId=!{fdId}" name="columntable">
                    <list:col-checkbox />
                    <list:col-serial/>
                    <list:col-auto props="" /></list:colTable>
            </list:listview>
            <!-- 翻页 -->
            <list:paging />
            <!-- 隐藏上一个预算方案的value -->
            <xform:text property="fdDimension" showStatus="noShow"></xform:text>
        </ui:content>
        </ui:tabpanel>
        </template:replace>
        <template:replace name="script">
        <script>
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                modelName: 'com.landray.kmss.fssc.budgeting.model.FsscBudgetingMain',
                basePath: '/fssc/budgeting/fssc_budgeting_main/fsscBudgetingMain.do',
                canDelete: '${canDelete}',
                mode: 'main_scategory',
                customOpts: {

                    ____fork__: 0
                },
                lang: {
                    noSelect: '${lfn:message("page.noSelect")}',
                    comfirmDelete: '${lfn:message("page.comfirmDelete")}'
                }

            };
            Com_IncludeFile("list.js", "${LUI_ContextPath}/fssc/budgeting/resource/js/", 'js', true);
            seajs.use(['lui/framework/module','lui/dialog','lui/topic'],function(Module,dialog,topic){
				Module.install('fsscBudgeting',{
					//模块变量
					$var : {},
					//模块多语言
					$lang : {
						myCreate : "${ lfn:message('list.create') }",
						myApproval : "${ lfn:message('list.approval') }",
						myApproved : "${ lfn:message('list.approved') }",
						allFlow : "${ lfn:message('list.alldoc') }"
					},
					//搜索标识符
					$search : ''
				});
			});
            LUI.ready(function(){	
            	seajs.use(['lui/jquery','lui/dialog','lang!fssc-budgeting','lang!eop-basedata'],function($,dialog,lang,baseLang){
            		var data = new KMSSData();
            		data.UseCache = false;
            		var rtn = data.AddBeanData("fsscBudgetingNavService&authCurrent=true&queryFlag=scheme").GetHashMapArray();
            		if(rtn&&rtn.length > 0){
            			var html=$("[name='fdSchemeId']").html();
            			for(var i=0;i<rtn.length;i++){
            				if(rtn[i]["type"] == 0){
            					dialog.alert(lang['message.budgeting.switch']);
            					break;
            				}else if(!rtn[0]["budgetCurrency"]){
            					dialog.alert(baseLang['message.common.budget.currency.notSet']);
            					break;
            				}else{
            					if(i > 0){
            						html+="<option value=\""+rtn[i]["id"]+"\">"+rtn[i]["name"]+"</option>";
            					}
            				}
            			}
            			$("[name='fdSchemeId']").html(html);
            		}
            	});
            });
        </script>
    </template:replace>
</template:include>

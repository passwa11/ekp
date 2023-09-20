<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:replace name="head">
<style>
	.model_select_selected{
		position:fixed;
		max-height: 240px;
		overflow-y: scroll;
		width : 120px;
		display:none;
		padding-bottom: 3px;
	}
	.more_tag_li{
		text-align: center;
		height:43px;
		background-color:#c7c7c7;
		list-style: none;
		overflow: hidden;
		white-space: nowrap;
		text-overflow: ellipsis;
		margin-top:2px;
		cursor:pointer
	}
	.more_tag_li:hover {
		background-color:rgb(54, 54, 54);
		color: white;
	}
	#moreTag:hover .model_select_selected{
		display:block
	}
	.lui_tabpage_float_nav_item_c{
		text-overflow:ellipsis;
		word-break:keep-all;
		white-space:nowrap;
		overflow: hidden;
		max-width:112px;
	}
	.more_tag_li{
		opacity: 1 !important;
	}
</style>
</template:replace>
<template:replace name="content">
	<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/modeling/main/resources/css/edit.css?s_cache=${LUI_Cache }"/>
		<c:if test="${param.approveModel ne 'right'}">
			<form name="modelingAppModelMainForm" method="post" action="<c:url value="/sys/modeling/main/modelingAppModelMain.do"/>">
		</c:if>
		<html:hidden property="listviewId" value="${param.listviewId}"/>
		<html:hidden property="fdId" value="${modelingAppModelMainForm.fdId}"/>
		<html:hidden property="docStatus" />
		<html:hidden property="fdModelId" />
		<html:hidden property="docCreateTime"/>
		<html:hidden property="docCreatorId"/>
		<br/>
		<c:choose>
			<c:when test="${param.approveModel eq 'right'}">
				<c:choose>
					<%-- E签宝特殊处理 --%>
					<c:when test="${modelingAppModelMainForm.sysWfBusinessForm.fdIsHander == 'true' && modelingAppModelMainForm.sysWfBusinessForm.fdNodeAdditionalInfo.eqbSign =='true'}">
						<ui:tabpanel suckTop="true" layout="sys.ui.tabpanel.sucktop"
									 var-count="5" var-average='false' var-useMaxWidth='true'>
							<ui:content title="${ lfn:message('sys-modeling-main:modelingAppBaseModel.reviewContent') }" toggle="false">
								<div class="lui_form_content_frame">
										<%-- 表单 --%>
									<c:import url="/sys/xform/include/sysForm_view.jsp"
											  charEncoding="UTF-8">
										<c:param name="formName" value="modelingAppModelMainForm"/>
										<c:param name="fdKey" value="modelingApp"/>
										<c:param name="messageKey" value="sys-modeling-main:modelingAppBaseModel.reviewContent"/>
										<c:param name="useTab" value="false"/>
									</c:import>
								</div>
							</ui:content>
							<c:import url="/elec/eqb/elec_eqb_common_default/elecEqbCommonDefaultSign.do?method=getEqbSignPage" charEncoding="UTF-8">
								<c:param name="signId" value="${modelingAppModelMainForm.fdId }" />
								<c:param name="enable" value="true"></c:param>
							</c:import>
						</ui:tabpanel>
					</c:when>
					<c:otherwise>
						<div class="lui_form_content_frame">
								<%-- 表单 --%>
							<c:import url="/sys/xform/include/sysForm_view.jsp"
									  charEncoding="UTF-8">
								<c:param name="formName" value="modelingAppModelMainForm"/>
								<c:param name="fdKey" value="modelingApp"/>
								<c:param name="messageKey" value="sys-modeling-main:modelingAppBaseModel.reviewContent"/>
								<c:param name="useTab" value="false"/>
							</c:import>
						</div>
					</c:otherwise>
				</c:choose>
				<!-- 待审页面标签默认收起，148625，现在要打开，去掉 var-expand=属性 -->
				<ui:tabpanel suckTop="true" layout="sys.ui.tabpanel.sucktop" var-supportExpand="true"  var-useMaxWidth='true'>
					<c:import url="/sys/modeling/main/formview/model_main_viewContent.jsp" charEncoding="UTF-8">
		 		 		<c:param name="approveModel" value="${param.approveModel}"></c:param>
		  			</c:import>
				</ui:tabpanel>
			</c:when>
			<c:otherwise>
				<ui:tabpage expand="false" var-navwidth="90%" id = "modelViewTab">
					<c:import url="/sys/modeling/main/formview/model_main_viewContent.jsp" charEncoding="UTF-8">
		 		 	</c:import>
				</ui:tabpage>
			</c:otherwise>
		</c:choose>	
		<c:if test="${param.approveModel ne 'right'}">
			</form>
		</c:if>
		<html:hidden property="detailOperationAuthConfig" value="${detailOperationAuthConfig}" />
	<script>
		function edit(){
			Com_OpenWindow(Com_Parameter.ContextPath + 'sys/modeling/main/modelingAppModelMain.do?method=edit&fdId=${param.fdId}&listviewId=${param.listviewId}','_self');
		}
		function deleteOne(){
			if (!confirmDelete())
				return;
			Com_OpenWindow('modelingAppModelMain.do?method=delete&fdId=${param.fdId}', '_self');
		}
		function confirmDelete(msg){
			var del = confirm('<bean:message key="page.comfirmDelete"/>');
			return del;
		}
		function printDoc() {
			var printMode = "${fdPrintMode}";
			if(lbpm && lbpm.isSubForm && typeof subform_print_BySubformId != "undefined" && printMode!='2'){
				subform_print_BySubformId('${KMSS_Parameter_ContextPath}sys/modeling/main/modelingAppModelMain.do?method=print&fdId=${param.fdId}');
			}else{
				selectPrintTemp('${KMSS_Parameter_ContextPath}sys/modeling/main/modelingAppModelMain.do?method=print&fdId=${param.fdId}',
						'${modelingAppModelMainForm.fdModelId}',"com.landray.kmss.sys.modeling.base.model.ModelingAppModel",'${xformId}',${isSysPrint});
			}
			return
		}
		
		function getHost(){
			var host = location.protocol.toLowerCase()+"//" + location.hostname;
			if(location.port!='' && location.port!='80'){
				host = host+ ":" + location.port;
			}
			return host;
		}
		Com_IncludeFile("viewtab.js",Com_Parameter.ContextPath+'sys/modeling/base/resources/js/','js',true);
		Com_IncludeFile("viewoper.js",Com_Parameter.ContextPath+'sys/modeling/base/resources/js/','js',true);
		Com_IncludeFile("detailOperationAuth.js", "${LUI_ContextPath}/sys/modeling/main/resources/js/", 'js', true);
		Com_IncludeFile("printDoc.js","${LUI_ContextPath}/sys/print/resource/js/","js",true);
		$(function(){
			initDetailOperationAuth($("input[name='detailOperationAuthConfig']").val(), true, "${param.method}", "${nodeId}");
		});
		
		  $(document).ready(function () {
              var intervalEndCount = 100;
              var interval = setInterval(__interval, "1000");

              function __interval() {
                  if (intervalEndCount == 0) {
                      console.error("数据解析超时。。。");
                     clearInterval(interval);
                  }
                  intervalEndCount--;
                  var $navs = $("#modelViewTab").find(".lui_tabpage_float_nav_item");
                  if ($navs.length == null || $navs.length == 0) {
                      return;
                  }

                  rebuildNavs();
                  clearInterval(interval);
              }
          })
          var moreTag = "<div id='moreTag' class=\"lui_tabpage_float_nav_item selected\"><div class=\"lui_tabpage_float_nav_item_l\"><div class=\"lui_tabpage_float_nav_item_r\"><div class=\"lui_tabpage_float_nav_item_c \"style='width:96px'>更多</div></div></div>" +
               +
              "</div>"
         //添加更多标签
          function rebuildNavs() {
              var $navs = $("#modelViewTab").find(".lui_tabpage_float_nav_item");
              var $contianer = $("#modelViewTab").find(".lui_tabpage_float_navs_c");
              var cWidth = $contianer.width();
			  var nIdx = 0;
              var navAllWidth = 0;

              for (let i = 0; i < $navs.length; i++) {
            	  if(i ==0){            		  
                  navAllWidth += 113;
                  continue;
            	  }
            	  navAllWidth +=$navs[i].offsetWidth + 5;
				  $($navs[i]).attr("title", $($navs[i]).text());
                  //标签大于6个字就显示6个字加...
                  var value  = $($navs[i]).text();
                  if(value.length>6){
	                	var val = value.substring(0,6)+"...";	                
	              $($navs[i]).find(".lui_tabpage_float_nav_item_c").html(val);
	                }
                  if (navAllWidth > cWidth) {
                      break;
                  }

                  nIdx = i;
              }	               
              
              //判断不需要
              if (nIdx == $navs.length || navAllWidth < cWidth) {	                                 
                  return;
              }	              	                	             
              var $moreTag =$(moreTag);	              	                 
              $($navs[nIdx]).before($moreTag);
			  //下面都是适应3种主题的样式 经典主题，炫彩蓝，蓝天凌云3种主题。
              var moreSelectTag = "<div class='model_select_selected'></div>";
              var $moreSelectTag = $(moreSelectTag);
              var  color =   $(".selected .lui_tabpage_float_nav_item_l").css("background-color");
              var  color1 =  $(".lui_tabpage_float_nav_item.selected").css("background-color");
			  var  fontSize =  $(".lui_tabpage_float_nav_item_c").css("font-size");
			  var  offsetHeight = $moreTag.get(0).offsetHeight;
              var  moreTagHeight = $moreTag.height();
              if (offsetHeight  == moreTagHeight){
				  offsetHeight+=3;
			  }
			  var innerWidth =  $moreTag.innerWidth();
              var width = $moreTag.width();
              if(width == 96){
				  var paddingleft = -(innerWidth-96)/2;
				  $moreSelectTag.css("transform","translateX("+paddingleft+"px)");
			  }
			  if(color=="rgba(0, 0, 0, 0)"){
				  if(color1!="rgba(16, 27, 72)"){
					  color = "rgba(66, 133, 244, 1)";
				  }else {
					  color1 = "rgba(16, 27, 72, 1)";
				  }
				  color = color1;
			  }
			  $moreSelectTag.css("bottom",offsetHeight-2);
              //隐藏多余换行的标签
             for (let i = nIdx; i < $navs.length; i++) {
              	$($navs[i]).hide();
	                }
              var ul = "<ul id='more_tag_ul'></ul>";
              var $ul = $(ul);
             
              for (let i = nIdx; i < $navs.length; i++) {
	                var value  = $($navs[i]).text();
	                if(value.length>6){
	                	var val = value.substring(0,6)+"...";	

	                var li = "<li class = 'more_tag_li liSelected'title='"+value+"'>"+val+"</li>"
	                }else{
	                	 var li = "<li class = 'more_tag_li liSelected'title='"+value+"'>"+value+"</li>"
	                }
	                var $li = $(li);
	                $ul.append($li);
	              
	                $li.click(function(){
	                	if( $(this).hasClass("liSelected")){
	                        //有就删除
	                         $(this).removeClass("liSelected");
	                         $(this).css("background-color","");
	                    }else{
	                        //没有就添加
	                         $(this).addClass("liSelected");

	                         $(this).css("background-color",color);
	                    }
	                 $($navs[i]).trigger($.Event("click"));
	            	});
              }
              //默认被选中的标签添加的背景颜色
              var $selectLi = $ul.find(".liSelected");
              $selectLi.css("background-color",color);
			  $selectLi.css("font-size",fontSize);
              //增加更多
              $moreSelectTag.append($ul);
			  $moreTag.append($moreSelectTag);
            
          };


	</script>
</template:replace>

<c:choose>
	<c:when test="${param.approveModel eq 'right'}">
		<template:replace name="barRight">
			<c:choose>
				<c:when test="${modelingAppModelMainForm.docStatus>='30' || modelingAppModelMainForm.docStatus=='00'}">
					<ui:accordionpanel>
						<!-- 基本信息-->
						<c:import url="/sys/modeling/main/formview/model_main_viewBaseInfoContent.jsp" charEncoding="UTF-8">
						</c:import>
					</ui:accordionpanel>
				</c:when>
				<c:otherwise>
					<ui:tabpanel layout="sys.ui.tabpanel.vertical.icon" id="barRightPanel" >
						<%-- 流程 --%>
						<c:import url="/sys/workflow/import/sysWfProcess_view.jsp" charEncoding="UTF-8">
							<c:param name="formName" value="modelingAppModelMainForm" />
							<c:param name="fdKey" value="modelingApp" />
							<c:param name="showHistoryOpers" value="true" />
							<c:param name="onClickSubmitButton" value="Com_Submit(document.modelingAppModelMainForm, 'publishUpdate');" />
							<c:param name="isExpand" value="true" />
							<c:param name="approveType" value="right" />
							<c:param name="approvePosition" value="right" />
						</c:import>
						<!-- 审批记录 -->
						<c:import url="/sys/lbpmservice/support/lbpm_audit_note/lbpmAuditNote_list_content.jsp" charEncoding="UTF-8">
							<c:param name="formName" value="modelingAppModelMainForm" />
							<c:param name="fdModelId" value="${modelingAppModelMainForm.fdId}" />
							<c:param name="fdModelName" value="com.landray.kmss.sys.modeling.main.model.ModelingAppModelMain" />
						</c:import>
						<!-- 基本信息-->
						<c:import url="/sys/modeling/main/formview/model_main_viewBaseInfoContent.jsp" charEncoding="UTF-8">
						</c:import>
					</ui:tabpanel>
				</c:otherwise>
			</c:choose>
		</template:replace>
	</c:when>
</c:choose>
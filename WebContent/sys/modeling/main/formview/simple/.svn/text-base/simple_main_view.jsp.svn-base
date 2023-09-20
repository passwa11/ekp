<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	request.setAttribute("fdAppModelId", request.getParameter("fdAppModelId"));
%>
<template:include ref="default.edit" sidebar="auto">
	<template:replace name="title">
		<c:out value="${modelingAppSimpleMainForm.fdModelName}"></c:out>
	</template:replace>
	<template:replace name="head">
		<style>
			.lui_widget_btn_txt {
				white-space: nowrap;
				overflow: hidden;
				text-overflow:ellipsis;
				width:60px;
			}
			.model_select_selected{
				position:fixed;
				bottom:38px;
				max-height: 240px;
				overflow-y: scroll;
				width : 120px;
				display:none;
				padding-bottom: 3px
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
			.lui_tabpage_float_nav_item_c{
				text-overflow:ellipsis;
				word-break:keep-all;
				white-space:nowrap;
				overflow: hidden;
				max-width:112px;
			}
			.more_tag_li:hover {
				background-color:rgb(54, 54, 54);
				color: white;
			}
			#moreTag:hover .model_select_selected{
				display:block
			}
		</style>
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar var-navwidth="90%" id="toolbar" layout="sys.ui.toolbar.float" count="3">
			<%-- 业务操作按钮 --%>
			<%@ include file="/sys/modeling/base/view/ui/viewopers.jsp"%>
			<ui:button text="${lfn:message('button.close')}" order="5" onclick="window.close();" />
		</ui:toolbar>
	</template:replace>
	<template:replace name="content">
		<html:hidden property="fdModelId" />
		<html:hidden property="docCreateTime"/>
		<html:hidden property="docCreatorId"/>
		<br/>
		<%-- 表单 --%>
		<div id="sysModelingXform">
			<c:import url="/sys/xform/include/sysForm_view.jsp"
				charEncoding="UTF-8">
				<c:param name="formName" value="modelingAppSimpleMainForm" />
				<c:param name="fdKey" value="modelingApp" />
				<c:param name="messageKey" value="sys-modeling-base:kmReviewDocumentLableName.reviewContent" />
				<c:param name="useTab" value="false" />
			</c:import>
		</div>
		<template:replace name="nav">
			<!-- 传阅意见-->
			<c:if test="${existOpinion}">
				<ui:accordionpanel style="min-width:200px;min-height:100px;" layout="sys.ui.accordionpanel.simpletitle">
					<ui:content title="${ lfn:message('km-review:kmReviewMain.circulation.option') }" id="circulation" >
						<ui:iframe src="${KMSS_Parameter_ContextPath}sys/circulation/sys_circulation_opinion/sysCirculationOpinion.do?method=edit&fdModelId=${modelingAppSimpleMainForm.fdId}&fdModelName=com.landray.kmss.sys.modeling.main.model.ModelingAppSimpleMain">
						</ui:iframe>
					</ui:content>
				</ui:accordionpanel>
			</c:if>
		</template:replace>
		<br/>
		<ui:tabpage expand="false" var-navwidth="90%" id ="modelViewTab" >
			<%-- 查看视图定义标签 --%>
			<c:import url="/sys/modeling/base/view/ui/viewtabs.jsp" charEncoding="UTF-8">
				<c:param name="expand" value="true" />
			</c:import>
			<%-- 沉淀记录 
			<c:import url="/kms/multidoc/kms_multidoc_subside/subsideRecord.jsp" charEncoding="UTF-8">
				<c:param name="fdId" value="${modelingAppSimpleMainForm.fdId }" />
				<c:param name="order" value="${modelingAppSimpleMainForm.docStatus=='30'||modelingAppSimpleMainForm.docStatus=='00'||modelingAppSimpleMainForm.docStatus=='31' ? 50 : 65}" />
			</c:import> 	--%>
		</ui:tabpage>
		<script>
			function edit(){
				Com_OpenWindow(Com_Parameter.ContextPath + 'sys/modeling/main/modelingAppSimpleMain.do?method=edit&fdId=${param.fdId}&listviewId=${param.listviewId}','_self');
			}
			function deleteOne(){
				if (!confirmDelete())
					return;
				Com_OpenWindow('modelingAppSimpleMain.do?method=delete&fdId=${param.fdId}', '_self');
			}
			function confirmDelete(msg){
				var del = confirm('<bean:message key="page.comfirmDelete"/>');
				return del;
			}
			function printDoc() {
				selectPrintTemp('${KMSS_Parameter_ContextPath}sys/modeling/main/modelingAppSimpleMain.do?method=print&fdId=${param.fdId}',
						'${modelingAppSimpleMainForm.fdModelId}',"com.landray.kmss.sys.modeling.base.model.ModelingAppModel",'${xformId}',${isSysPrint});
			}
			Com_IncludeFile("viewtab.js",Com_Parameter.ContextPath+'sys/modeling/base/resources/js/','js',true);
			Com_IncludeFile("viewoper.js",Com_Parameter.ContextPath+'sys/modeling/base/resources/js/','js',true);
			Com_IncludeFile("printDoc.js","${LUI_ContextPath}/sys/print/resource/js/","js",true);
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
</template:include>
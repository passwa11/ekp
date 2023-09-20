<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	//固定为蓝天凌云主题
	request.setAttribute("sys.ui.theme", "sky_blue");
%>
<template:include ref="default.list" spa="true" j_rIframe='true'>
	<template:replace name="head">
		<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/ui/extend/theme/default/style/ding_list.css?s_cache=${LUI_Cache }"/>
	</template:replace>
	<template:replace name="script">
		<script type="text/javascript">
		//声明是否加载操作指引标记（如果一级分类不为空，则添加第二步操作指引 ）
		var isGuide = false;
		LUI.ready(function(){
				if (typeof(seajs) != 'undefined') {
					seajs.use(['lui/dialog'], function(dialog) {
						window._load = dialog.loading();
					});
				}
				//加载常用模板
				loadUsefulTemplate();
				//加载第一级模板分类
				loadFirstLevelTemplate();
				if (typeof window._load != "undefined") {
					window._load.hide(); 
				}
				//绑定查询事件
				$('#searchTemplateInputId').keypress(function(event){
					if(event.keyCode == '13')
					{
						searchTemplateFunc();
					}
				});
				
				/* 添加操作指引元素-开始 */
				/* 在分类中添加操作指引的显示文本 */
				setTimeout(function(){
					//如果一级分类不为空，则添加第二步操作指引
					if(isGuide){
						addGuidanceDiv();
					}
				}, 100);
				
				/* 在子分类中添加操作指引的显示文本 */
				setTimeout(function(){
					//如果子分类的图标存在，则添加第三步操作指引
					if($(".ld-review-icon-category").length > 0){
						addCategoryGuidanceDiv();
					}
				}, 100);
				/* 添加操作指引元素-结束 */
				
			});
		
			/**
			 *	加载第一级模板分类
			 */
			function loadFirstLevelTemplate(){
				$.ajax({
					type: "POST",
				   	url: "${LUI_ContextPath }/sys/category/criteria/sysCategoryCriteria.do?method=select&modelName=com.landray.kmss.km.review.model.KmReviewTemplate&type=03&getTemplate=1&authType=2",
				   	data: {},
				   	success: function(data){
				   		if(data && data.length>0){
				   			//如果第一级模板分类不为空那么就添加操作指引的第二步
				   			isGuide = true;
				   			var templateHtml = '';
				   			$.each(data[0], function(index, value) {
				   				var childCount = 0;
				   				var ulHtml = '<ul>';
				   				//获取子类级别
				   				$.ajax({
									type: "POST",
									async: false,
								   	url: "${LUI_ContextPath}/sys/category/criteria/sysCategoryCriteria.do?method=select&modelName=com.landray.kmss.km.review.model.KmReviewTemplate&parentId="+value.value+"&type=03&getTemplate=1&authType=2",
								   	data: {},
								   	success: function(childData){
								   		if(childData && childData.length>0){
								   			$.each(childData[0], function(index, cvalue) {
								   				if("category" == cvalue.nodeType){
								   					ulHtml += '<li><a href="javascript:;" onclick="loadChildTemplate(\''+value.value +'\',\''+cvalue.value+'\',\''+cvalue.text+'\')">'+
							                            '<i class="ld-review-icon ld-review-icon-category"></i>'+
							                            '<div class="ld-review-platform-template-title"><p>'+cvalue.text+'</p></div>'+
							                            '<i class="ld-review-icon-arrow"></i></a></li>';
								   		        }else if("template" == cvalue.nodeType){
								   		        	childCount += 1;
								   		        	var openUrl = "${LUI_ContextPath }/km/review/km_review_main/kmReviewMain.do?method=add&fdTemplateId="+cvalue.value+"&ddtab=true";
								   		        	ulHtml += '<li><a href="'+openUrl+'" target="_blank">'+
							                        '<i class="ld-review-icon ld-review-icon-template"></i>'+
							                        '<div class="ld-review-platform-template-title"><p>'+cvalue.text+'</p></div></a></li>';
								   		        }
								   			});
								   		}
								   	}
				   				});
				   				ulHtml += '</ul>';
				   				var countEmHtml = '';
				   				if(childCount > 0){
				   					countEmHtml = '<em>('+childCount+')</em>';
				   				}
				   				var navId = value.value+"-NavId";
				   				var itemHtml = '<div class="ld-review-platform-template-item"><div class="ld-review-platform-template-item-head"><div class="ld-review-head-l">';
				   				itemHtml += '<div class="ld-review-menu-frame-nav" id="'+navId+'"><a href="javascript:;" onclick="loadChildTemplate(\''+value.value+'\',\''+value.value+'\',\'\')"><div class="ld-review-menu-frame-item"><span>'+value.text+'</span>'+countEmHtml+'</div></a>'
				   				var iconId = value.value+"-IconId";
				   				var ulId = value.value+"-UlId";
				   				itemHtml += '</div></div><div class="ld-review-head-r" onclick="showOrHideEvent(\''+iconId+'\',\''+ulId+'\');"><i id="'+iconId+'" class="ld-icon-toggle-up"></i></div>';
				   				itemHtml += '</div><div class="ld-review-platform-template-item-content" id="'+ulId+'">'+ulHtml+'</div></div>';
				   				templateHtml += itemHtml;
				   			});
				   			$("#commonTemplateContentDivId").append(templateHtml);
				   		}
				   	}
				});
			}
			
			/**
			 * 加载常用模板
			 */
			function loadUsefulTemplate(){
				$.ajax({
					type: "POST",
				   	url: "${LUI_ContextPath }/sys/lbpmperson/SysLbpmPersonCreate.do?method=listUsual&mainModelName=com.landray.kmss.km.review.model.KmReviewMain",
				   	data: {},
				   	success: function(data){
				   		if(data){
				   			$("#usefulTemplateCountEmId").html("("+data.list.length+")");
				   			var templateHtml = '<ul>';
				   			$.each(data.list, function(index, value) {
				   				var openUrl = "${LUI_ContextPath }/"+value.addUrl+"&ddtab=true";
			   		        	templateHtml += '<li><a href="'+openUrl+'" target="_blank">'+
			                    '<i class="ld-review-icon ld-review-icon-template"></i>'+
			                    '<div class="ld-review-platform-template-title"><p>'+value.templateName+'</p></div></a></li>';
				   			});
				   			$("#usefulTemplateContentUl").html(templateHtml+'</ul>');
				   		}
				   	}
				});
			}
			/**
			 * 加载子模板
			 */
			function loadChildTemplate(domId,pId,titleValue){
				$.ajax({
					type: "POST",
				   	url: "${LUI_ContextPath }/sys/category/criteria/sysCategoryCriteria.do?method=select&modelName=com.landray.kmss.km.review.model.KmReviewTemplate&parentId="+pId+"&type=03&getTemplate=1&authType=2",
				   	data: {},
				   	success: function(data){
				   		if(data && data.length>0){
				   			var templateList = data[0];
				   			var templateCount = 0;
				   			var templateHtml = '<ul>';
				   			$.each(templateList, function(index, value) {
				   		        if("category" == value.nodeType){
				   		        	templateHtml += '<li><a href="javascript:;" onclick="loadChildTemplate(\''+domId+'\',\''+value.value+'\',\''+value.text+'\')">'+
			                            '<i class="ld-review-icon ld-review-icon-category"></i>'+
			                            '<div class="ld-review-platform-template-title"><p>'+value.text+'</p></div>'+
			                            '<i class="ld-review-icon-arrow"></i></a></li>';
				   		        }else if("template" == value.nodeType){
				   		        	templateCount += 1;
				   		        	var openUrl = "${LUI_ContextPath }/km/review/km_review_main/kmReviewMain.do?method=add&fdTemplateId="+value.value+"&ddtab=true";
				   		        	templateHtml += '<li><a href="'+openUrl+'" target="_blank">'+
			                        '<i class="ld-review-icon ld-review-icon-template"></i>'+
			                        '<div class="ld-review-platform-template-title"><p>'+value.text+'</p></div></a></li>';
				   		        }
				   		    });
				   			if(domId == pId){
				   				$("#"+domId+"-NavId > span").remove();
				   			}else{
				   				if(titleValue){
				   					templateCount
				   					var countEmHtml = '';
				   					if(templateCount > 0){
				   						countEmHtml = '<em>('+templateCount+')</em>';
				   					}
					   				var itemHtml = '<span class="itemSelectClass" id="categoryItem'+pId+'" onclick="changeCategoryItem(\''+domId+'\',\''+pId+'\',\''+titleValue+'\')"><a href="javascript:;"><div class="ld-review-menu-frame-item ld-review-menu-frame-icon ld-review-menu-frame-icon-arrow"><i></i></div>';
					   				itemHtml += '<div class="ld-review-menu-frame-item"><span>'+titleValue+'</span>'+countEmHtml+'</div></a></span>';
					   				$("#"+domId+"-NavId").append(itemHtml);
								}
				   			}
				   			$("#"+domId+"-UlId").html(templateHtml+"</ul>");
				   		}
				   	}
				});
			}
			
			
			/**
			 * 全部模板中上面item点击事件
			 */
			function changeCategoryItem(domId,pId,title){
				$("#categoryItem"+pId).nextAll().remove();
				loadChildTemplate(domId,pId,'');
			}
			
			/**
			 * 显示和隐藏事件
			 */
			function showOrHideEvent(iconId,eleId){
				if("ld-icon-toggle-up" == $("#"+iconId).attr("class")){
					$("#"+iconId).removeClass().addClass("ld-icon-toggle-down")
					$("#"+eleId).slideUp(500);
				}else{
					$("#"+iconId).removeClass().addClass("ld-icon-toggle-up")
					$("#"+eleId).slideDown(500);
				}
			}
			
			/**
			 * 模板搜索事件
			 */
			function searchTemplateFunc(){
				var searchText = $("#searchTemplateInputId").val().trim();
				if(searchText){
					$("#commonTemplateContentDivId").hide();
					$("#searchTemplateContentDivId").show();
					$.ajax({
						type: "POST",
					   	url: "${LUI_ContextPath}/sys/category/criteria/sysCategoryCriteria.do?method=select&modelName=com.landray.kmss.km.review.model.KmReviewTemplate&searchText="+encodeURI(searchText)+"&type=03&getTemplate=1&authType=2&qSearch=true",
					   	data: {},
					   	success: function(data){
					   		if(data && data[0].length > 0){
					   			var templateHtml = '<ul>';
					   			$.each(data[0], function(index, value) {
					   				var openUrl = "${LUI_ContextPath}/km/review/km_review_main/kmReviewMain.do?method=add&fdTemplateId="+value.value+"&ddtab=true";
				   		        	templateHtml += '<li><a href="'+openUrl+'" target="_blank">'+
				                    '<i class="ld-review-icon ld-review-icon-template"></i>'+
				                    '<div class="ld-review-platform-template-title"><p>'+value.text+'</p></div></a></li>';
					   			});
					   			$("#searchTemplateContentDivId").find("div").html(templateHtml+'</ul>');
					   		}else{
					   			$("#searchTemplateContentDivId").find("div").html("没找到匹配结果!!");
					   		}
					   	}
					});
				}else{
					$("#searchTemplateContentDivId").hide();
					$("#commonTemplateContentDivId").show();
				}
			}
			
			/* 在分类中添加操作指引的显示文本-开始 */
			function addGuidanceDiv(){
				var htmlTxt = "<div id='step-two-a' class='guide-region' style='display: none;'></div>"
							+ "<div id='step-two-b' class='guidance-box guidance-box-li' style='display: none;'>"
							+    "<div class='guidance-notes'>点击模板即可开始创建审批流程</div>"
							+	 "<div class='guidance-btn'>"
							+	   "<div class='guidance-skipAll'>跳过全部</div>"
							+ 	   "<div class='finish' style='display: none;'>完成</div>"
							+	   "<div class='guidance-next'>下一步</div>"
							+	   "<div class='guidance-up'>上一步</div>"
							+	  "</div>"
							+  "</div>";
							
				$("#commonTemplateContentDivId .ld-review-platform-template-item-content>ul").find("li").each(function(){
					if($(this).find("a").find("i").hasClass("ld-review-icon-template")){
						$(this).attr("id","step-two-li");
						$("#step-two-li").append(htmlTxt);
						return false;
					}
				});
			}
			/* 在分类中添加操作指引的显示文本-结束 */
			
			/* 在子分类中添加操作指引的显示文本-开始 */
			function addCategoryGuidanceDiv(){
				var htmlTxt = "<div id='step-three-a' class='guide-region' style='display: none;'></div>"
							+ "<div id='step-three-b' class='guidance-box guidance-box-li' style='display: none;'>"
							+    "<div class='guidance-notes'>点击分类可展开详情模板</div>"
							+	 "<div class='guidance-btn'>"
							+	   "<div class='guidance-skipAll'>跳过全部</div>"
							+	   "<div class='guidance-next'>完成</div>"
							+	   "<div class='guidance-up'>上一步</div>"
							+	  "</div>"
							+  "</div>";
							
			   $("#commonTemplateContentDivId .ld-review-platform-template-item-content>ul").find("li").each(function(){
					if($(this).find("a").find("i").hasClass("ld-review-icon-category")){
						$(this).attr("id","step-three-li");
						$("#step-three-li").append(htmlTxt);
						return false;
					}
			 	});
			}
			/* 在子分类中添加操作指引的显示文本-结束 */
			
			</script>
	</template:replace>
	<template:replace name="content">
		<div class="ld-review-platform-home">
			<div class="ld-review-platform-searchBar">
		            <i class="ld-review-icon ld-review-icon-search" onclick="searchTemplateFunc()"></i>
		            <input id="searchTemplateInputId" type="text" placeholder="请搜索要提交的表单">
		    </div>
			<div id="searchTemplateContentDivId" style="display: none" class="ld-review-platform-template-content">
				<div style="padding-top: 40px" class="ld-review-platform-template-item-content">
		        </div>
		    </div>
			<div id="commonTemplateContentDivId" class="ld-review-platform-template-content">
				<!-- 常用的模板 -->
		        <div class="ld-review-platform-template-item">
		            <div class="ld-review-platform-template-item-head">
		                <div class="ld-review-head-l">
		                    <!-- 面包屑 -->
		                    <div class="ld-review-menu-frame-nav">
		                        <div class="ld-review-menu-frame-item"><span>最近使用</span><em id="usefulTemplateCountEmId">(0)</em></div>
		                    </div>
		                </div>
		                <div class="ld-review-head-r" onclick="showOrHideEvent('usefulTemplateNavIconId','usefulTemplateContentUl');"><i id="usefulTemplateNavIconId" class="ld-icon-toggle-up" ></i></div>
		            </div>
		            <div class="ld-review-platform-template-item-content" id="usefulTemplateContentUl">
		                <ul>
		                </ul>
		            </div>
		        </div>
			
			</div>
		</div>
	</template:replace>
</template:include>
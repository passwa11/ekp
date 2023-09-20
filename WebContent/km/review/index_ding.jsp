<%@page import="org.apache.commons.lang.ObjectUtils"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.util.UserUtil"%>
<%@ page import="com.landray.kmss.util.StringUtil"%>
<%@ page import="com.landray.kmss.third.ding.util.DingUtil"%>
<%@ page import="com.landray.kmss.third.ding.model.DingConfig"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.lang.String" %>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="com.landray.kmss.sys.appconfig.service.ISysAppConfigService"%>
<%  
	request.setAttribute("isAdmin",UserUtil.getKMSSUser().isAdmin());
	//获取后台配置的高级版后台跳转url，setAttribute使js能取到
	ISysAppConfigService sysAppConfigService = (ISysAppConfigService) SpringBeanUtil.getBean("sysAppConfigService");
	Map orgMap = sysAppConfigService.findByKey("com.landray.kmss.third.ding.model.DingConfig");
	if (orgMap != null && orgMap.containsKey("dingPortalUrl")) {
		String dingPortalUrl = orgMap.get("dingPortalUrl")+"";
		String dingPortalUrlPc = dingPortalUrl;
		if(StringUtils.isBlank(dingPortalUrl)){
			dingPortalUrl = URLEncoder.encode(StringUtil.formatUrl("/km/review/km_review_ui/dingSuit/moduleindex.jsp?nav=/km/review/tree_ding.jsp&ddtab=true&showTopBar=true"));
			dingPortalUrlPc =StringUtil.formatUrl("/km/review/km_review_ui/dingSuit/moduleindex.jsp?nav=/km/review/tree_ding.jsp&ddtab=true&showTopBar=true");
		}else{
			dingPortalUrlPc =StringUtil.formatUrl(dingPortalUrl);
			dingPortalUrl = URLEncoder.encode(StringUtil.formatUrl(dingPortalUrl));
		}
		request.setAttribute("dingPortalUrl", dingPortalUrl);
		request.setAttribute("dingPortalUrlPc", dingPortalUrlPc);
		
	}
	//准备跳转外部浏览器的参数
	request.setAttribute("managerUrl",URLEncoder.encode(StringUtil.formatUrl("/moduleindex.jsp?nav=/km/review/tree_ding.jsp")));
	String url = StringUtil.formatUrl("/km/review/km_review_ui/dingSuit/moduleindex.jsp?nav=/km/review/tree_ding.jsp&ddtab=true&showTopBar=true");
	
	request.setAttribute("ssoJsp",StringUtil.formatUrl("/km/review/index_ding_sso.jsp"));
	String corpId =DingUtil.getCorpId();
	if(StringUtils.isBlank(corpId)){
		corpId = DingConfig.newInstance().getDingCorpid();
	}
	request.setAttribute("corpId",corpId);
	request.setAttribute("isDingPc", ObjectUtils.equals(MobileUtil.getClientType(request), MobileUtil.DING_PC));
%>
<template:include ref="default.list" j_rIframe='true' j_noPadding='true'>
	<template:replace name="head">
		<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/ui/extend/theme/default/style/ding_list.css?s_cache=${LUI_Cache }"/>
		<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/km/review/km_review_ui/dingSuit/css/tree.css?s_cache=${LUI_Cache}"/>
	</template:replace>
	<template:replace name="content">
		<div class="ld-review-platform-home-nav luiReviewHeadBarTitleFlag">
			<div class="luiReviewHeadBar luiReviewHeadBarTitleFlag" style="height: 0px;line-height: 0px;">
				<span><img src="${KMSS_Parameter_ContextPath}km/review/km_review_ui/dingSuit/images/ding_title.png" class="lui_title_config_img"></span>
				<span class="lui_title_config_span">审批高级版</span>
				<span><img src="${KMSS_Parameter_ContextPath}km/review/km_review_ui/dingSuit/images/ding_title_flag_big.png" class="lui_title_config_img_flag"></span>
			</div>		
	      <div class="ld-review-platform-home-nav-left ld-review-platform-home-nav-left-d">
	          <ul id="indexDingTopTabNavId">
	            <li class="active" onclick="tabIframePage(this,'/dingIndex.jsp');">
	                <span>首页</span>
	                <i></i>
	            </li>
	            <li onclick="tabIframePage(this,'/dinglist.jsp?mydoc=approval');">
	                <span>待处理</span>
	                <span class="tips"></span>
	                <i></i>
	            </li>
	            <li onclick="tabIframePage(this,'/dinglist.jsp?mydoc=approved');">
	              <span>已处理</span>
	              <i></i>
	            </li>
	            <li onclick="tabIframePage(this,'/dinglist.jsp?mydoc=create');">
	              <span>已发起</span>
	              <i></i>
	            </li>
	            <li onclick="tabIframePage(this,'/relative.jsp');">
	              <span>我相关的</span>
	              <i></i>
	            </li>
	            <li onclick="tabIframePage(this,'/moduleindex.jsp?nav=/km/review/km_review_ui/dingSuit/dataExport.jsp?fdModelName=com.landray.kmss.km.review.model.KmReviewMain&searchTitle=%25E6%25B5%2581%25E7%25A8%258B%25E6%259F%25A5%25E8%25AF%25A2');">
	              <span>数据导出</span>
	              <i></i>
	            </li>
	            <kmss:ifModuleExist path="/dbcenter/echarts/">
					<kmss:authShow roles="ROLE_DBCENTERECHARTS_DEFAULT">
			            <li onclick="tabIframePage(this,'/nav.jsp?mainModelName=com.landray.kmss.km.review.model.KmReviewTemplate&fdKey=kmReviewMainDoc');">
			              <span>数据报表</span>
			              <i></i>
			            </li>
		            </kmss:authShow>
	            </kmss:ifModuleExist>
	          </ul>
	      </div>
	      <kmss:authShow roles="ROLE_KMREVIEW_BACKSTAGE_MANAGER">
		      <div style="height: 48px;" class="ld-review-platform-home-nav-right">
		          <i></i>
		          <span onclick="onOpenManagers();" >进入后台管理</span>
		      </div>
	      </kmss:authShow>
	    </div>
	    <div id="dingSuitIframeDivId" style="width: 100%;">
	    	<iframe frameborder="no" border="0" marginwidth="0" marginheight="0" id="dingSuitIframeId" src="${LUI_ContextPath }/km/review/km_review_ui/dingSuit/dingIndex.jsp" style="width: 100%;height:100%;border: 0px;overflow:hidden;" ></iframe>
	    </div>
	    
	    <!-- 添加操作指引的遮罩层开始 -->
		<div id="step-one-a" class="guide-region" style="display: none;"></div>
		<div id="step-one-b" class="guidance-box" style="display: none;">
			<div class="guidance-notes">顶部Tab可以追踪详细的流程进度</div>	
			<div class="guidance-btn">
				<div class="guidance-skipAll">跳过全部</div>
				<div class="guidance-next">下一步</div>
				<div class="finish" style="display: none;">完成</div>
			</div>
		</div>
		<!-- 添加操作指引的遮罩层结束 -->
	
	</template:replace>
	
   	<template:replace name="script">
   		<script type="text/javascript" src="//g.alicdn.com/dingding/dingtalk-pc-api/2.3.1/index.js"></script>
      	<script type="text/javascript">
	      	var resizeTimer = null;
	      	
      		LUI.ready(function(){
      			//声明是否加载操作指引标记（如果一级分类不为空，则添加第二步操作指引 ）
      			if(!loadFirstLevelTemplate()){//没有第二步，第一个操作指引的“下一步”显示为“完成”
      				$("#step-one-b").find(".guidance-next").hide();
      				$("#step-one-b").find(".finish").show();
      			}
      			
      			setTimeout(function(){
	      			//如果子分类的图标不存在（没有第三步），第二个操作指引的“下一步”显示为“完成”
	      			if($("#dingSuitIframeId").contents().find(".ld-review-icon-category").length < 0){
	      				$("#dingSuitIframeId").contents().find("#step-two-b").find(".guidance-next").hide();
	      				$("#dingSuitIframeId").contents().find("#step-two-b").find(".finish").show();
	      			}
      			},300);
      			
      			setTimeout(function(){
      				updateCount();
  				},300);
      			$("#dingSuitIframeDivId").css("height",$(window).height()*0.9);
      			$(window).bind('resize', function (){
	      	        if (resizeTimer) clearTimeout(resizeTimer);
	      	        resizeTimer = setTimeout(function(){
	      	        	$("#dingSuitIframeDivId").css("height",$(window).height()*0.9);
	      	        } , 500);
	      		});
      			
      			/* 操作指引逻辑控制部分-开始 */
      			//是否需要显示
      			var operation_guide = localStorage.getItem("operation_guide");
      			if(!operation_guide){//需要显示
      				//显示遮罩
      				$("#step-one-a").show();
      				//显示文字提示
      				$("#step-one-b").show();
      				//添加高亮
      				$("#indexDingTopTabNavId").addClass("highlightArea");
      				localStorage.setItem("operation_guide",true);
      			}else {
					//不需要显示
				}
      			 
      			//“跳过全部”和“完成”隐藏所有的遮罩，移除相关信息
      			//第一个指引的“跳过全部”
      			$("#step-one-b").find(".guidance-skipAll").click(function(){
      				//隐藏遮罩
      				$("#step-one-a").hide();
      				//隐藏文字提示
      				$("#step-one-b").hide();
      				//移除高亮
      				$("#indexDingTopTabNavId").removeClass("highlightArea");
      				localStorage.setItem("operation_guide",true);
      			});
      			
      			//第一个指引的“完成”
      			$("#step-one-b").find(".finish").click(function(){
      				//隐藏遮罩
      				$("#step-one-a").hide();
      				//隐藏文字提示
      				$("#step-one-b").hide();
      				//移除高亮
      				$("#indexDingTopTabNavId").removeClass("highlightArea");
      				localStorage.setItem("operation_guide",true);
      			});
      			
      			//第二个指引的“跳过全部”
      			setTimeout(function(){
	      			$("#dingSuitIframeId").contents().find("#step-two-b").find(".guidance-skipAll").click(function(){
	      				//隐藏遮罩
	      				$("#dingSuitIframeId").contents().find("#step-two-a").hide();
	      				//隐藏文字提示
	      				$("#dingSuitIframeId").contents().find("#step-two-b").hide();
	      				//启用滚动条
	      				$("#dingSuitIframeId").contents().find("body").css("overflow-y","auto");
	      				localStorage.setItem("operation_guide",true);
	      			});
      			},300);
      			
      			//第二个指引的“完成”
      			setTimeout(function(){
	      			$("#dingSuitIframeId").contents().find("#step-two-b").find(".finish").click(function(){
	      				//隐藏遮罩
	      				$("#dingSuitIframeId").contents().find("#step-two-a").hide();
	      				//隐藏文字提示
	      				$("#dingSuitIframeId").contents().find("#step-two-b").hide();
	      				//启用滚动条
	      				$("#dingSuitIframeId").contents().find("body").css("overflow-y","auto");
	      				localStorage.setItem("operation_guide",true);
	      			});
      			},300);
      			
      			//第三个指引的“跳过全部”
      			setTimeout(function(){
	      			$("#dingSuitIframeId").contents().find("#step-three-b").find(".guidance-skipAll").click(function(){
	      				//隐藏遮罩
	      				$("#dingSuitIframeId").contents().find("#step-three-a").hide();
	      				//隐藏文字提示
	      				$("#dingSuitIframeId").contents().find("#step-three-b").hide();
	      				//启用滚动条
	      				$("#dingSuitIframeId").contents().find("body").css("overflow-y","auto");
	      				localStorage.setItem("operation_guide",true);
	      			});
      			},300);
      			
      			//第三个指引的“完成”
      			setTimeout(function(){
	      			$("#dingSuitIframeId").contents().find("#step-three-b").find(".guidance-next").click(function(){
	      				//隐藏遮罩
	      				$("#dingSuitIframeId").contents().find("#step-three-a").hide();
	      				//隐藏文字提示
	      				$("#dingSuitIframeId").contents().find("#step-three-b").hide();
	      				//启用滚动条
	      				$("#dingSuitIframeId").contents().find("body").css("overflow-y","auto");
	      				localStorage.setItem("operation_guide",true);
		      		});
      			},300);
      			
      			//第一步的时候点击“下一步”操作
      			$("#step-one-b").find(".guidance-next").click(function(){
					//隐藏第一步的操作
					$("#step-one-a").hide();
					$("#step-one-b").hide();
					$("#indexDingTopTabNavId").removeClass("highlightArea");
					//显示第二步的操作指引
      				if($("#dingSuitIframeId").contents().find("#step-two-li").offset().top + $("#dingSuitIframeId").contents().find("#step-two-li").height() > $(window).height()){
						$($("#dingSuitIframeId").contents().find("html"),$("#dingSuitIframeId").contents().find("body")).animate({scrollTop:$("#dingSuitIframeId").contents().find("#step-two-li").offset().top},'slow').promise().then(function(){
							if($(window).height() - ($("#dingSuitIframeId").contents().find("#step-two-li").offset().top - $("#dingSuitIframeId")[0].contentWindow.document.documentElement.scrollTop) - $("#dingSuitIframeId").contents().find("#step-two-li").height() > 140){
								$("#dingSuitIframeId").contents().find("#step-two-a").show();
								$("#dingSuitIframeId").contents().find("#step-two-b").show();
								//禁用滚动条
								$("#dingSuitIframeId").contents().find("body").css("overflow-y","hidden");
								//添加高亮显示
								$("#dingSuitIframeId").contents().find("#step-two-li").find("a").addClass("li-bright");
							}else{
								$("#dingSuitIframeId").contents().find("#step-two-b").addClass("guidance-box-adjust");
								$("#dingSuitIframeId").contents().find("#step-two-a").show();
								$("#dingSuitIframeId").contents().find("#step-two-b").show();
								//禁用滚动条
								$("#dingSuitIframeId").contents().find("body").css("overflow-y","hidden");
								//添加高亮显示
								$("#dingSuitIframeId").contents().find("#step-two-li").find("a").addClass("li-bright");
							}
						});
				    }else {
				    	$("#dingSuitIframeId").contents().find("#step-two-a").show();
						$("#dingSuitIframeId").contents().find("#step-two-b").show();
						//禁用滚动条
						$("#dingSuitIframeId").contents().find("body").css("overflow-y","hidden");
						//添加高亮显示
						$("#dingSuitIframeId").contents().find("#step-two-li").find("a").addClass("li-bright");
					} 
      			});
      			
      			//第二个指引的下一步（显示第三个操作指引文字）
      			setTimeout(function(){
      				$("#dingSuitIframeId").contents().find("#step-two-b").find(".guidance-next").click(function(){
      					//隐藏第二步的操作
      					$("#dingSuitIframeId").contents().find("#step-two-a").hide();
      					$("#dingSuitIframeId").contents().find("#step-two-b").hide();
      					//移除高亮显示
						$("#dingSuitIframeId").contents().find("#step-two-li").find("a").removeClass("li-bright");
      					//显示第三步的操作指引
      					var rollHeight =  $("#dingSuitIframeId").contents().find("#step-two-li").offset().top - $("#dingSuitIframeId").contents().find("#step-three-li").offset().top;
      					if(rollHeight >= 0){
      						rollHeight = "-"+rollHeight;
      					}else {
      						rollHeight = -(rollHeight) + 62 + 62;
						}
						$($("#dingSuitIframeId").contents().find("html"),$("#dingSuitIframeId").contents().find("body")).animate({scrollTop:rollHeight},'slow').promise().then(function(){
							if($(window).height() - ($("#dingSuitIframeId").contents().find("#step-three-li").offset().top - $("#dingSuitIframeId")[0].contentWindow.document.documentElement.scrollTop) - $("#dingSuitIframeId").contents().find("#step-three-li").height() > 140){
								$("#dingSuitIframeId").contents().find("#step-three-a").show();
								$("#dingSuitIframeId").contents().find("#step-three-b").show();
								//禁用滚动条
								$("#dingSuitIframeId").contents().find("body").css("overflow-y","hidden");
								//添加高亮显示
								$("#dingSuitIframeId").contents().find("#step-three-li").find("a").addClass("li-bright");
							}else{
								$("#dingSuitIframeId").contents().find("#step-three-b").addClass("guidance-box-adjust");
								$("#dingSuitIframeId").contents().find("#step-three-a").show();
								$("#dingSuitIframeId").contents().find("#step-three-b").show();
								//禁用滚动条
								$("#dingSuitIframeId").contents().find("body").css("overflow-y","hidden");
								//添加高亮显示
								$("#dingSuitIframeId").contents().find("#step-three-li").find("a").addClass("li-bright");
							}
						});
          			});
  				},300);
      			
      			//第二个操作指引的“上一步”（显示第一个操作指引）
      			setTimeout(function(){
      				$("#dingSuitIframeId").contents().find("#step-two-b").find(".guidance-up").click(function(){
      					//隐藏第二步的操作
      					$("#dingSuitIframeId").contents().find("#step-two-a").hide();
      					$("#dingSuitIframeId").contents().find("#step-two-b").hide();
      					//移除高亮显示
						$("#dingSuitIframeId").contents().find("#step-two-li").find("a").removeClass("li-bright");
      					//显示第一步的操作指引
      					//显示遮罩
          				$("#step-one-a").show();
          				//显示文字提示
          				$("#step-one-b").show();
          				//添加高亮
          				$("#indexDingTopTabNavId").addClass("highlightArea");
      				});
      			},300);
      			
      			//第三个操作指引的“上一步”（显示第二个操作指引）
      			setTimeout(function(){
      				$("#dingSuitIframeId").contents().find("#step-three-b").find(".guidance-up").click(function(){
      					//隐藏第三步的操作
      					//隐藏遮罩
	      				$("#dingSuitIframeId").contents().find("#step-three-a").hide();
	      				//隐藏文字提示
	      				$("#dingSuitIframeId").contents().find("#step-three-b").hide();
	      				//移除高亮显示
						$("#dingSuitIframeId").contents().find("#step-three-li").find("a").removeClass("li-bright");
	      				//显示第二步的操作指引
	      				var rollHeight =  $("#dingSuitIframeId").contents().find("#step-three-li").offset().top - $("#dingSuitIframeId").contents().find("#step-two-li").offset().top;
      					if(rollHeight <= 0){
      						rollHeight = -(rollHeight);
      					} else {
      						rollHeight = rollHeight - 62 - 62;
						}
						$($("#dingSuitIframeId").contents().find("html"),$("#dingSuitIframeId").contents().find("body")).animate({scrollTop:rollHeight},'slow').promise().then(function(){
							if($(window).height() - ($("#dingSuitIframeId").contents().find("#step-two-li").offset().top - $("#dingSuitIframeId")[0].contentWindow.document.documentElement.scrollTop) - $("#dingSuitIframeId").contents().find("#step-two-li").height() > 120){
								$("#dingSuitIframeId").contents().find("#step-two-a").show();
								$("#dingSuitIframeId").contents().find("#step-two-b").show();
								//禁用滚动条
								$("#dingSuitIframeId").contents().find("body").css("overflow-y","hidden");
								//添加高亮显示
								$("#dingSuitIframeId").contents().find("#step-two-li").find("a").addClass("li-bright");
							}else{
								$("#dingSuitIframeId").contents().find("#step-two-b").addClass("guidance-box-adjust");
								$("#dingSuitIframeId").contents().find("#step-two-a").show();
								$("#dingSuitIframeId").contents().find("#step-two-b").show();
								//禁用滚动条
								$("#dingSuitIframeId").contents().find("body").css("overflow-y","hidden");
								//添加高亮显示
								$("#dingSuitIframeId").contents().find("#step-two-li").find("a").addClass("li-bright");
							}
						});
      				});
      			},300);
      			/* 操作指引逻辑控制部分-结束 */
      			
  			});
      		function updateCount(){
	   			var countUrl = "${LUI_ContextPath }/km/review/km_review_index/kmReviewIndex.do?method=list&pagingSetting=&q.mydoc=approval"
				
  				$.getJSON(countUrl,function(data){
					var totalSize = data.page.totalSize;
					if(totalSize >0){
						$('.ld-review-platform-home-nav-left li .tips').addClass('show').html(totalSize);
					}else{
						$('.ld-review-platform-home-nav-left li .tips').removeClass('show');
					}
				});
      		}
      		//切换iframe页面
      		function tabIframePage(docObj,uri){
      			if($(docObj).attr("class") != 'active'){
      				$("#indexDingTopTabNavId").find(".active").removeClass("active");
      				$(docObj).addClass("active");
	     			document.getElementById('dingSuitIframeId').src = "${LUI_ContextPath }/km/review/km_review_ui/dingSuit"+uri;
      			}
      		}
      		//进入管理后台-钉钉内部打开
      		function onOpenManager(){
      			//获取配置的url
      			var dingPortalUrl = '${dingPortalUrl}';
      			 if(dingPortalUrl.indexOf("http") == -1) {
      				var serverPrefix = Com_Parameter.serverPrefix;
      				dingPortalUrl = serverPrefix+dingPortalUrl;
      		    }
      			 //原url
      			//var url =  '${LUI_ContextPath}/km/review/km_review_ui/dingSuit/moduleindex.jsp?nav=/km/review/tree_ding.jsp&ddtab=true&showTopBar=true';
  				//打开url
      			Com_OpenWindow(dingPortalUrl);
      		}
      		
      		//进入管理后台-跳外部浏览器
      		function onOpenManagers(){
      			var isDingPc = '${isDingPc}';
     		    console.log('${dingPortalUrl}');
     		    console.log('${dingPortalUrlPc}');
      			if(isDingPc=='true'){
	      			var corpId = '${corpId}';
	      			if(corpId){
		      			DingTalkPC.runtime.permission.requestAuthCode({
					        corpId: corpId,
					        onSuccess: function (info) {
					        	var managerUrl =  '${ssoJsp}'+"?code="+info.code+"&formDing=1&dingAppKey="+'${corpId}'+"&j_redirectto="+'${dingPortalUrl}';
					        	DingTalkPC.biz.util.openLink({
								    url:managerUrl,								    
								    onSuccess : function(result) {
								    },
								    onFail : function(err) {
								    }
								});
					        },
					    	onFail: function (err) {
					        }
					    }); 
	      			}else{
	      				DingTalkPC.biz.util.openLink({
						    url:'${dingPortalUrl}',
						    onSuccess : function(result) {
						    },
						    onFail : function(err) {
						    }
						}); 
	      			}
      			}else{
      				Com_OpenWindow('${dingPortalUrlPc}');
      			}
      		}
      		
      		//查询是否存在一级分类模板来判断是否存在第二步操作指引
			function loadFirstLevelTemplate(){
      			var flag;
				$.ajax({
					type: "POST",
				   	url: "${LUI_ContextPath }/sys/category/criteria/sysCategoryCriteria.do?method=select&modelName=com.landray.kmss.km.review.model.KmReviewTemplate&type=03&getTemplate=1&authType=2",
				   	data: {},
				   	async: false,
				   	success: function(data){
				   		if(data && data.length>0){
				   			flag = true;
				   			return flag;
				   		}else {
				   			flag = false;
				   			return flag;
						}
				   	}
				});
				return flag;
			}
      		
      	</script>
	</template:replace>
</template:include>
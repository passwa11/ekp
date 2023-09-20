<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.list" j_rIframe='true'>
	<template:replace name="head">
		<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/ui/extend/theme/default/style/ding_list.css?s_cache=${LUI_Cache }"/>
	</template:replace>
	<template:replace name="script">
		<script type="text/javascript">
		//切换iframe页面
  		function myselfTabIframePage(docObj,uri){
  			if($(docObj).attr("class") != 'active'){
  				$("#myselfTopTabNavId").find(".active").removeClass("active");
  				$(docObj).addClass("active");
     			document.getElementById('myselfIframeId').src = "${LUI_ContextPath }"+uri;
  			}
  		}
  		LUI.ready(function(){
  			$("#myselfIframeDivId").css("height",window.screen.height*0.9);
  		});
		</script>
	</template:replace>
	<template:replace name="content">
		<div class="ld-review-platform-home-nav">
	      <div class="ld-review-platform-home-nav-left">
	      	<ul id="myselfTopTabNavId">
	            <li class="active" onclick="myselfTabIframePage(this,'/km/review/km_review_ui/dingSuit/dinglist.jsp?mydoc=all');">
	                <span>全部</span>
	                <i></i>
	            </li>
	            <li onclick="myselfTabIframePage(this,'/km/review/km_review_ui/dingSuit/dinglist.jsp?doctype=follow');">
	                <span>我跟踪的</span>
	                <!-- <span class="tips">15</span> -->
	                <i></i>
	            </li>
	  <% if (com.landray.kmss.sys.subordinate.util.SubordinateUtil.getInstance().getModelByModuleAndUser("km-review:module.km.review").size() > 0) { %>
	            <li onclick="myselfTabIframePage(this,'/sys/subordinate/moduleindex.jsp?moduleMessageKey=km-review:module.km.review');">
	              <span>我下属的</span>
	              <i></i>
	            </li>
	  <% } %>        
	          </ul>
	      </div>
	    </div>
	    <div id="myselfIframeDivId" style="width: 100%">
	    	<iframe frameborder="no" border="0" marginwidth="0" marginheight="0" id="myselfIframeId" src="${LUI_ContextPath }/km/review/km_review_ui/dingSuit/dinglist.jsp?mydoc=all" style="width: 100%;height:100%;border: 0px;overflow:hidden;" ></iframe>
	    </div>
	</template:replace> 
</template:include>
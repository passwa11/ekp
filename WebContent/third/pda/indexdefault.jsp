<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<%@page import="java.util.List"%>
<%@page import="com.landray.kmss.third.pda.service.IPdaDataShowService"%>
<%
	session.setAttribute("S_PADFlag","1");
%>
<%@ include file="/third/pda/htmlhead4index.jsp"%>
<title><bean:message key="phone.module.title" bundle="third-pda" />
</title>
<style>
html {
	font-size: 62.5%;
	font-family: Tahoma,Arial,Roboto,"Droid Sans","Helvetica Neue","Droid Sans Fallback","Heiti SC",sans-self;
}
body {
	background-color: #f2f2f2;
	margin: 0;
	padding: 0;
}
.mui-square-gridlist:after {
  content: '';
  display: table;
  visibility: hidden;
  clear: both;
}

.mui-square-panel {
	margin-bottom: 1.4rem;
	background-color: #fff;
}
.mui-square-panel:last-child {
	margin-bottom: 0;
}
.mui-square-header-wrap {
	position: relative;
}
.mui-square-banner-wrap {
	overflow: hidden;
	max-height: 12rem;
}
.mui-square-banner-wrap>img {
	width: 100%;
  height: 100%;
  background-repeat: no-repeat;
}
.mui-square-logo-wrap {
	position: absolute;
	top: 1rem;
	left: 1rem;
	width: 6rem;
}
.mui-square-logo-wrap>img {
	width: 100%;
}
.mui-square-panel-heading {
	padding: 1rem 6.3rem 1rem 1rem;
	border-bottom: 1px solid #e0e0e0;
	background-image: none;
	position: relative;
}
@media screen and (-webkit-min-device-pixel-ratio: 2){
  .mui-square-panel-heading {
    border: 0;
    background-repeat: repeat-x;
      -webkit-background-size: 100% 1px;
    background-image: -webkit-gradient(linear, left bottom, left top, color-stop(0.5, transparent), color-stop(0.5, #e0e0e0), to(#e0e0e0)), -webkit-gradient(linear, left top, left bottom, color-stop(0.5, transparent), color-stop(0.5, #e0e0e0), to(#e0e0e0));
    background-position: bottom;
  }
}
.mui-square-panel-heading>h2 {
	margin: 0;
	font-weight: normal;
	font-size: 1.4rem;
  color: #999;
}
.mui-square-banner-wrap>img{
  -webkit-animation: fadeIn .3s ease-in-out .5s 1 both;
          animation: fadeIn .3s ease-in-out .5s 1 both;
}
.mui-square-logo-wrap>img{
  -webkit-animation: fadeInLeft .3s ease-in-out .5s 1 both;
          animation: fadeInLeft .3s ease-in-out .5s 1 both;
}

.mui-square-gridlist {
	list-style-type: none;
	margin: 0;
	padding: 0;
}
.mui-square-gridlist>li {
	float: left;
	width: 25%;
	height: 9rem;
	position: relative;
	text-align: center;
	overflow: hidden;
}
.mui-square-gridlist a {
	text-decoration: none;
}
.mui-square-gridlist img {
	width: 3rem;
	height: 3rem;
	margin-top: 1.5rem;
	position: relative;
	z-index: 1;
}
.mui-square-gridlist .txt {
   display: block;
  color: #3f3f3f;
  font-size: 1.2rem;
  padding: 0 0.5rem;
  position: relative;
  z-index: 1;
  overflow: hidden;
  margin-top: 0.5rem;
   margin-bottom: 0.5rem;
  overflow:hidden;
  text-overflow: ellipsis;
  word-break:break-all;
  word-wrap:break-word;
  -webkit-line-clamp: 2;
  display: -webkit-box;
  -webkit-box-orient: vertical;
          box-orient: vertical;
  -webkit-box-pack: center;
          box-pack: center;
  -webkit-box-align:center;
          box-align:center;
}
.mui-square-gridlist .red-point {
	background-color: #ff0000;
	color: #fff;
	width: 2rem;
	height: 2rem;
	line-height: 2.2rem;
  font-size: 1rem;
	text-align: center;
	border-radius: 50%;
	display: block;
	position: absolute;
	top: 10%;
	right: 10%;
	z-index: 1;
}

.mui-square-gridlist>li[data-href]:active:after{
  content: '';
  width: 40px;
  height: 40px;
  border-radius: 50%;
  background-color: #eee;
  display: block;
  position: absolute;
  left: 50%;
  top: 50%;
  margin-left: -20px;
  margin-top: -20px;
  z-index: 0;
  opacity: .75;
  -webkit-transform: scale(4);
          transform: scale(4);
  -webkit-animation: zoomIn .2s ease-in-out 0s 1 both;
          animation: zoomIn .2s ease-in-out 0s 1 both;
}
.mui-square-gridlist > li > *{
  -webkit-animation: fadeInDown .2s ease-in-out .5s 1 both;
          animation: fadeInDown .2s ease-in-out .5s 1 both;
}

@-webkit-keyframes fadeInDown {
  from {
    opacity: 0;
    -webkit-transform: translate3d(0, -100%, 0);
    transform: translate3d(0, -100%, 0);
  }

  to {
    opacity: 1;
    -webkit-transform: none;
    transform: none;
  }
}

@keyframes fadeInDown {
  from {
    opacity: 0;
    -webkit-transform: translate3d(0, -100%, 0);
    transform: translate3d(0, -100%, 0);
  }

  to {
    opacity: 1;
    -webkit-transform: none;
    transform: none;
  }
}
@-webkit-keyframes zoomIn {
  from {
    opacity: 0;
    -webkit-transform: scale3d(.3, .3, .3);
    transform: scale3d(.3, .3, .3);
  }

  50% {
    opacity: 1;
  }
}
@keyframes zoomIn {
  from {
    opacity: 0;
    -webkit-transform: scale3d(.3, .3, .3);
    transform: scale3d(.3, .3, .3);
  }

  50% {
    opacity: 1;
  }
}
@-webkit-keyframes fadeIn {
  from {
    opacity: 0;
  }

  to {
    opacity: 1;
  }
}

@keyframes fadeIn {
  from {
    opacity: 0;
  }

  to {
    opacity: 1;
  }
}
@-webkit-keyframes fadeInLeft {
  from {
    opacity: 0;
    -webkit-transform: translate3d(-100%, 0, 0);
    transform: translate3d(-100%, 0, 0);
  }

  to {
    opacity: 1;
    -webkit-transform: none;
    transform: none;
  }
}

@keyframes fadeInLeft {
  from {
    opacity: 0;
    -webkit-transform: translate3d(-100%, 0, 0);
    transform: translate3d(-100%, 0, 0);
  }

  to {
    opacity: 1;
    -webkit-transform: none;
    transform: none;
  }
}
</style>
<script type="text/javascript">
	function syncResource(_url){
		try{
			 var xmlhttp = {};
			 if (window.XMLHttpRequest){// 所有浏览器
				 xmlhttp = new XMLHttpRequest();
			 }else if (window.ActiveXObject){// IE5 和 IE6
				 xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
			 }
			 if(!Com_GetUrlParameter(_url,'s_cache')){
				 _url = Com_SetUrlParameter(_url, "s_cache", Com_Parameter.Cache);
			 }
			 xmlhttp.open("GET", _url, false);
			 xmlhttp.setRequestHeader("Accept", "text/plain");
			 xmlhttp.setRequestHeader("Content-Type","text/plain;charset=UTF-8");
		     xmlhttp.send(null);
		}catch(e){}
	}
	function preLoad(){
		//syncResource('<mui:min-cachepath name="ios7.css"/>');
		syncResource('<mui:min-cachepath name="font-mui.css"/>');
		syncResource('<mui:min-cachepath name="common.css"/>');
		syncResource('<mui:min-cachepath name="dojo.js"/>');
		syncResource('<mui:min-cachepath name="mobile.js"/>');
		syncResource('<mui:min-cachepath name="mui.js"/>');
		syncResource('<mui:min-cachepath name="list.css"/>');
	}
	function openModule(fdId){
		var url = '<c:url value="/third/pda/pda_module_config_main/pdaModuleConfigMain.do"/>?method=open&fdId='+fdId;
		var open_target = "_self";
		if("${JsParam.s_target}" != null && "${JsParam.s_target}" != ""){
			open_target = "${JsParam.s_target}";
		}
		window.open(url,open_target);
	}
</script>
</head>
<body onload="preLoad();">
	<%
		List moduleList = null;
		moduleList = (List) request.getAttribute("moduleList");
		if (moduleList == null || moduleList.size() <= 0) {
			IPdaDataShowService showService = (IPdaDataShowService) SpringBeanUtil
					.getBean("pdaDataShowService");
			moduleList = showService.getPdaModuleListByCate();
		}  
		pageContext.setAttribute("moduleList", moduleList);
	%>
<c:if test="<%=moduleList!=null && moduleList.size()>0%>">
<header class="mui-square-header-wrap">
    <div class="mui-square-banner-wrap">
      <img src="resource/images/banner.png">
    </div>
    <div class="mui-square-logo-wrap">
      <img src="resource/images/landray.png">
    </div>
</header>

 <c:forEach var="module" items="${moduleList}">
  	 <section class="mui-square-panel">
  	 	<c:if test="${module.cateName !='other'}">
	  		<div class="mui-square-panel-heading">
		      <h2 class="mui-square-panel-heading-title">
		      	${module.cateName}
		      </h2>
		    </div>
	  	 </c:if>
         <div class="mui-square-panel-body">
          <c:choose>
			<c:when test='${fn:length(module.list)<4}'>
		    <c:if test="${fn:length(module.list)==1}">
		      <ul class="mui-square-gridlist">
			    <c:forEach var="modulelist" items="${module.list}" varStatus="vstatus">
                    <li data-href="#" onclick="openModule('${modulelist.fdId}');">
						    <img src="<c:url value="${modulelist.fdIconUrl}"/>"/>
                            <span class="txt"><c:out value="${modulelist.fdName}"></c:out></span>
                    </li>
                </c:forEach>
                <li data-href="#"></li><li data-href="#"></li><li data-href="#"></li>
               </ul>
		    </c:if>
		    <c:if test="${fn:length(module.list)==2}">
		       <ul class="mui-square-gridlist">
			    <c:forEach var="modulelist" items="${module.list}" varStatus="vstatus">
                    <li data-href="#" onclick="openModule('${modulelist.fdId}');">
						    <img src="<c:url value="${modulelist.fdIconUrl}"/>"/>
                            <span class="txt"><c:out value="${modulelist.fdName}"></c:out></span>
                    </li>
                </c:forEach>
                <li data-href="#"></li><li data-href="#"></li>
               </ul>
		    </c:if>
		    <c:if test="${fn:length(module.list)==3}">
		      <ul class="mui-square-gridlist">
			    <c:forEach var="modulelist" items="${module.list}" varStatus="vstatus">
                   <li data-href="#" onclick="openModule('${modulelist.fdId}');">
						    <img src="<c:url value="${modulelist.fdIconUrl}"/>"/>
                            <span class="txt"><c:out value="${modulelist.fdName}"></c:out></span>
                    </li>
                </c:forEach>
                <li data-href="#"></li>
               </ul>
		    </c:if>
		</c:when>
		<c:otherwise>
			<c:forEach var="modulelist" items="${module.list}" varStatus="vstatus">
              <c:if test="${vstatus.index % 4 == 0}">
                <ul class="mui-square-gridlist">
              </c:if>
                    <li data-href="#" onclick="openModule('${modulelist.fdId}');"> 
						    <img src="<c:url value="${modulelist.fdIconUrl}"/>?s_cache=${MUI_Cache}"/>
                            <span class="txt"><c:out value="${modulelist.fdName}"></c:out></span>
                    </li>
               <c:if test="${(vstatus.index-3) % 4 == 0 or fn:length(module.list)<4}">
                </ul>
               </c:if> 
              </c:forEach>
		</c:otherwise>
	</c:choose>
    </div>
   </section>
</c:forEach>
</c:if>
</body>
</html>

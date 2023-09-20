<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="./ThirdMallConfig_common.jsp"%>
<template:include ref="config.profile.edit" sidebar="no">
	<template:replace name="head">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/third/mall/resource/css/mall.css?s_cache=${MUI_Cache}"></link>
	</template:replace>
	<template:replace name="content">
		<div class="luiMallBox">
    <div class="luiMallBoxWrap">
      <h3><bean:message bundle="third-mall" key="thirdMall.config.title" /></h3>
      <h4>
        <bean:message bundle="third-mall" key="thirdMallAuthorize.fdClientId" />：
            ${__fdClientId}
            ${_msg}
      </h4>
      <c:if test="${__fdMallEnable!='1'}">
     		<div class="luiMallOpt">
		        <div class="luiMallBtn" onclick="enableMall()"><bean:message bundle="third-mall" key="thirdMall.config.open" /></div>
		        <div class="luiMallOptInfo">
		          <div>
		          	  <bean:message bundle="third-mall" key="thirdMall.config.tips" />
		          </div>
		        </div>
	      </div>
      </c:if>
      <c:if test="${__fdMallEnable=='1'}">
      		<div class="cm-start-edit">
		        <p><bean:message bundle="third-mall" key="thirdMall.config.enterprise" />${_fdClientName }</p>
		        <div class="cm-start-btn-box">
		          <div class="luiMallBtn" onclick="onMallChange()"><bean:message bundle="third-mall" key="thirdMall.config.change.auth" /></div>
		          <div class="luiMallBtn close" onclick="onMallClose()"><bean:message bundle="third-mall" key="thirdMall.config.close" /></div>
		        </div>
		      </div>
      </c:if>

      <div class="luiMallCards">
        <div class="luiMallCardItem">
          <div class="luiMallCardItemWrap ">

            <c:set var="active" value=""/>
            <c:if test="${__fdMallEnable=='1' && fn:contains(__fdBusKeys,'sys_xform')}">
            <c:set var="active" value="active"/>
            </c:if>
            <div class="luiMallCardIcon icon_${active}"></div>
            <div class="luiMallCardDetail ${active}">
              <div>
                <p><bean:message bundle="third-mall" key="thirdMallAuthorize.xform.name" /></p>
                <c:choose>
                	<c:when test="${__fdMallEnable=='1' && fn:contains(__fdBusKeys,'sys_xform')}">
                		<span><bean:message bundle="third-mall" key="thirdMallAuthorize.opened" /></span>
                	</c:when>
                	<c:otherwise>
                		<span><bean:message bundle="third-mall" key="thirdMallAuthorize.closed" /></span>
                	</c:otherwise>
                </c:choose>
              </div>
              <span><bean:message bundle="third-mall" key="thirdMallAuthorize.xform.desc" /></span>
              <c:if test="${__fdMallEnable=='1'  && fn:contains(__fdBusKeys,'sys_xform')}">
              		<div>
		              	<a href="#" style="text-decoration:underline;color:blue" onclick="enterTemplateConf()"><bean:message bundle="third-mall" key="thirdMall.config.into.xform" />></a>
		            </div>	
              </c:if>
            </div>
          </div>
        </div>
        <div class="luiMallCardItem">
          <div class="luiMallCardItemWrap">

            <c:set var="portalActive" value=""/>
            <c:if test="${__fdMallEnable=='1' && fn:contains(__fdBusKeys,'sys_portal')}">
                <c:set var="portalActive" value="active"/>
            </c:if>
            <div class="luiMallCardIcon icon_${portalActive}"></div>
            <div class="luiMallCardDetail ${portalActive}">
              <div>
                <p><bean:message bundle="third-mall" key="thirdMallAuthorize.portal.name" /></p>
                <c:choose>
                	<c:when test="${__fdMallEnable=='1' && fn:contains(__fdBusKeys,'sys_portal')}">
                		<span><bean:message bundle="third-mall" key="thirdMallAuthorize.opened" /></span>
                	</c:when>
                	<c:otherwise>
                		<span><bean:message bundle="third-mall" key="thirdMallAuthorize.closed" /></span>
                	</c:otherwise>
                </c:choose>
              </div>
              <span><bean:message bundle="third-mall" key="thirdMallAuthorize.portal.desc" /></span>
                <c:if test="${__fdMallEnable=='1'  && fn:contains(__fdBusKeys,'sys_portal')}">
                    <div>
                        <a href="#" style="text-decoration:underline;color:blue"
                           onclick="enterPortalConf()">
                            <bean:message bundle="third-mall" key="thirdMall.config.into.portal" />>
                        </a>
                    </div>
                </c:if>
            </div>
          </div>
        </div>
          <div class="luiMallCardItem">
              <div class="luiMallCardItemWrap">
                  <c:set var="applicationActive" value=""/>
                  <c:if test="${__fdMallEnable=='1' && fn:contains(__fdBusKeys,'sys_application')}">
                      <c:set var="applicationActive" value="active"/>
                  </c:if>
                  <div class="luiMallCardIcon icon_${applicationActive}"></div>
                  <div class="luiMallCardDetail ${applicationActive}">
                      <div>
                          <p><bean:message bundle="third-mall" key="thirdMallAuthorize.application.name" /></p>
                          <c:choose>
                              <c:when test="${__fdMallEnable=='1' && fn:contains(__fdBusKeys,'sys_application')}">
                                  <span><bean:message bundle="third-mall" key="thirdMallAuthorize.opened" /></span>
                              </c:when>
                              <c:otherwise>
                                  <span><bean:message bundle="third-mall" key="thirdMallAuthorize.closed" /></span>
                              </c:otherwise>
                          </c:choose>
                      </div>
                      <span><bean:message bundle="third-mall" key="thirdMallAuthorize.application.desc" /></span>
                      <c:if test="${__fdMallEnable=='1'  && fn:contains(__fdBusKeys,'sys_application')}">
                          <div>
                              <a href="#" style="text-decoration:underline;color:blue" onclick="enterApplicationConf()"><bean:message bundle="third-mall" key="thirdMall.config.into.application" />></a>
                          </div>
                      </c:if>
                  </div>
              </div>
          </div>
      </div>
    </div>
  </div>
	</template:replace>
</template:include>
<script>
	seajs.use(['lui/dialog','lui/jquery'], function(dialog,$) {
		window.enableMall = function(){
			var url = "${KMSS_Parameter_ContextPath}third/mall/thirdMallAuthorize.do?method=authorize";
			Com_OpenWindow(url,'blank');
		}
		window.onMallChange = function(){
			var url = "${KMSS_Parameter_ContextPath}third/mall/thirdMallAuthorize.do?method=authorize";
			Com_OpenWindow(url,'blank');
		}
		window.onMallClose = function(){
			dialog.confirm('<bean:message bundle="third-mall" key="thirdMall.config.close.confirm" />', function(value){
				if(!value){
					return;
				}
				var datas = {};
				$.ajax({
		            type: "post", 
		            url: "${LUI_ContextPath}/third/mall/thirdMallAuthorize.do?method=disableMall", 
		            dataType: "json",
		            data:datas,
		            success: function (data) {
		            	if(data && data.errcode==0){
		            		dialog.success('<bean:message key="return.optSuccess" />');
		            		//window.location='${LUI_ContextPath}/sys/profile/index.jsp#integrate/saas/mall';
		            		location.reload();
		            	}else{
		            		dialog.failure('<bean:message key="return.optFailure" />');
		            	}
		            },
		            error: function(data){
						dialog.failure('<bean:message key="return.optFailure" />');
					}
				});
			});
			
		}
		
		window.enterTemplateConf = function() {
            window.top.location.href='${LUI_ContextPath}/sys/profile/index.jsp#app/ekp/km/review';
            window.top.location.reload();
            //Com_OpenWindow('${LUI_ContextPath}/sys/profile/index.jsp#app/ekp/km/review','1');
			//window.location='${LUI_ContextPath}/sys/profile/moduleindex.jsp?nav=/km/review/tree.jsp';
		}
        window.enterPortalConf = function() {
            //window.location='${LUI_ContextPath}/sys/profile/moduleindex.jsp?nav=/sys/profile/portal/maintenance.jsp';
            window.top.location.href='${LUI_ContextPath}/sys/profile/index.jsp#portal/templateCenter';
            window.top.location.reload();
        }
        window.enterApplicationConf = function() {
            //window.location='${LUI_ContextPath}/sys/modeling/base/profile/application.jsp';
            window.top.location.href='${LUI_ContextPath}/sys/profile/index.jsp#modeling/applicationMall';
            window.top.location.reload();
        }
	});
	
</script>
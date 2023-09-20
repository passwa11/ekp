<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.edit">
	<template:replace name="head">
		<meta charset="UTF-8">
	    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/profile/maintenance/behavior/css/config.css">
	</template:replace>
	<template:replace name="content">
		<body id="body">
		    <section class="right-modeleMain">
		        <section class="content">
		            <header class="integra-head">
		                <h3><bean:message key="sys.profile.behavior.config.base" bundle="sys-profile-behavior" /></h3>
		                <p><bean:message key="sys.profile.behavior.config.connection" bundle="sys-profile-behavior" /><span name="connectResult"></span></p>
		            </header>
		            
		            <section class="integra-con"  name="page1">
		                <p class="redp" name="succDesc"><bean:message key="sys.profile.behavior.config.succDesc" bundle="sys-profile-behavior" /></p>
		                <ul>
		                    <li class="active bdbuttom pdleft">
		                        <label>
		                            <div class="integra-list pdleft">
		                                <div><span class="corp-xitong"><bean:message key="sys.profile.behavior.config.DNS" bundle="sys-profile-behavior" /> </span>${sysAppConfigForm.map['behaviorDNS']}</div>
		                            </div>
		                        </label>
		                    </li>
		                    <li class="active bdbuttom pdleft">
		                        <label>
		                            <div class="integra-list pdleft">
		                                <div><span class="corp-xitong"><bean:message key="sys.profile.behavior.config.PWD" bundle="sys-profile-behavior" /> </span>******************************</div>
		                            </div>
		                        </label>
		                    </li>
		                </ul>
		                <div class="integra-btn-group">
		                	<button class="integra-btn active" onclick="Com_OpenWindow('<c:url value="/sys/profile/maintenance/behavior/online.jsp" />','_blank');"><bean:message key="sys.profile.behavior.config.goto" bundle="sys-profile-behavior" /></button>
			                <a class="integra-btn integra-btn-muted" href="javascript:showPage(4)"><bean:message key="sys.profile.behavior.config.edit" bundle="sys-profile-behavior" /></a>
		                </div>
		                
		            </section>
		            
		            <!-- 集成中心基本配置 -->
		            <section class="integra-con"  name="page2">
		            	<p class="redp" name="succDesc"><bean:message key="sys.profile.behavior.config.step1" bundle="sys-profile-behavior" /></p>
		                <ul>
		                    <li class="active bdbuttom pdleft">
		                        <label>
		                            <div class="integra-list pdleft">
										<bean:message key="sys.profile.behavior.config.step1.info" bundle="sys-profile-behavior" />
		                            </div>
		                        </label>
		                    </li>
		                </ul>
		                <div class="integra-btn-group">
			                <button class="integra-btn active" onclick="showPage(3);"><bean:message key="sys.profile.behavior.config.next" bundle="sys-profile-behavior" /></button>
		                </div>
		            </section>
		            
		            <!-- 集成中心基本配置 -->
		            <section class="integra-con"  name="page3">
		            	<p class="redp" name="succDesc"><bean:message key="sys.profile.behavior.config.step2" bundle="sys-profile-behavior" /></p>
		                <ul>
		                    <li class="active bdbuttom pdleft">
		                        <label>
		                            <div class="integra-list pdleft">
										<bean:message key="sys.profile.behavior.config.step2.info" bundle="sys-profile-behavior" />
		                            </div>
		                        </label>
		                    </li>
		                </ul>
		                <div class="integra-img">
        					<img src="<%=request.getContextPath()%>/sys/profile/maintenance/behavior/images/behavior_log.jpg">
		                </div>
						<div class="integra-btn-group">
              				<button class="integra-btn active" onclick="showPage(4);"><bean:message key="sys.profile.behavior.config.next" bundle="sys-profile-behavior" /></button>
						</div>
		            </section>
		            
		            <!-- 集成中心基本配置 -->
		            <section class="integra-con"  name="page4">
		            	<p class="redp" name="succDesc"><bean:message key="sys.profile.behavior.config.step3" bundle="sys-profile-behavior" /></p>
		            	<html:form action="/sys/appconfig/sys_appconfig/sysAppConfig.do">
			            	<html:hidden property="method_GET" />
							<input type="hidden" name="modelName" value="com.landray.kmss.sys.profile.behavior.model.BehaviorSettingConfig" />
			                <ul class="table-list">
			                    <li>
			                    	<div class="item"><span class="corp-span"><bean:message key="sys.profile.behavior.config.DNS" bundle="sys-profile-behavior" /></span></div>
			                    	<div class="item"><xform:text property="value(behaviorDNS)" className="integra-input"/></div>
			                    	<div class="item"><span class="depict-span"><bean:message key="sys.profile.behavior.config.DNS.desc" bundle="sys-profile-behavior" /></span></div>
			                    </li>
			                    <li>
			                    	<div class="item"><span class="corp-span"><bean:message key="sys.profile.behavior.config.PWD" bundle="sys-profile-behavior" /></span></div>
			                    	<div class="item"><xform:text property="value(behaviorPWD)" className="integra-input"/></div>
			                    	<div class="item"><span class="depict-span"><bean:message key="sys.profile.behavior.config.PWD.desc" bundle="sys-profile-behavior" /></span></div>
			                    </li>
			                </ul>
		                </html:form>
		                <div class="integra-btn-group">
			                <button class="integra-btn active" onclick="Com_Submit(document.sysAppConfigForm, 'update');"><bean:message key="sys.profile.behavior.config.done" bundle="sys-profile-behavior" /></button>
		                </div>
		            </section>
		            
		        </section>
		    </section>
		</body>

		<script type="text/javascript">
			$(function() {
				$("section[name^=page]").hide();
				if("true" == "${sysAppConfigForm.map['isReady']}") {
					$("section[name=page1]").show();
					testConnect();
				} else {
					$("span[name=connectResult]").text('<bean:message key="sys.profile.behavior.config.not.configured" bundle="sys-profile-behavior" />');
					$("section[name=page2]").show();
				}
			});
			
			function testConnect() {
				$("span[name=connectResult]").html("<img src='<c:url value="/sys/profile/maintenance/behavior/images/load.gif" />'/>");
				$.ajax({
				    url: '<c:url value="/sys/profile/maintenance/behavior/behaviorSetting.do?method=connectBehaviorServer" />',
				    dataType: 'json',
				    success: function(result){
				    	console.log("result:", result);
				    	if(result.state) {
				    		$("span[name=connectResult]").text(result.msg);
				    	} else {
				    		$("span[name=connectResult]").html("<font color='red'>"+result.msg+"</font>");
				    	}
				    }
				});
			}
			
			function showPage(index) {
				$("section[name^=page]").hide();
				$("section[name=page"+index+"]").show();
			}
		</script>
	</template:replace>
</template:include>


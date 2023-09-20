<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
    <link type="text/css" rel="stylesheet" href="${KMSS_Parameter_ContextPath}third/im/kk/resource/css/main.css"/>
    <script type="text/javascript" src="${KMSS_Parameter_ContextPath}resource/js/jquery.js"></script>
    
<body id="body">
	<section class="right-modeleMain" v-show="main.basePage">
       <section class="content">
           <header class="integra-head">
               <h3>配置信息</h3>
           </header>
           <section class="integra-con integra-conwarp" v-show="base.page2" style="width: 700px">
               <ul>
               		<c:forEach items="${list}" var="synList" varStatus="vstatus">
               			<c:if test="${synList ==  'org'}">
		                   <li>
		                   	<span><img src="${KMSS_Parameter_ContextPath}third/im/kk/resource/images/check.png" style="width: 15px; height: 15px;"><input type="hidden" name="synConfig" value="org" checked="checked"/>&nbsp;&nbsp;${lfn:message('third-im-kk:kk.config.info.org.success')}</span>
		                   </li>
		                 </c:if>
		                 <c:if test="${synList ==  'orgVisible'}">
		                   <li>
		                   		<span><img src="${KMSS_Parameter_ContextPath}third/im/kk/resource/images/check.png" style="width: 15px; height: 15px;"><input type="hidden" name="synConfig" value="orgVisible" checked="checked"/>&nbsp;&nbsp;${lfn:message('third-im-kk:kk.config.info.orgVisible.success')}</span>
		                   </li>
		                 </c:if>
		                 <c:if test="${synList ==  'sso'}">
		                   <li>
		                   	<span><img src="${KMSS_Parameter_ContextPath}third/im/kk/resource/images/check.png" style="width: 15px; height: 15px;"><input type="hidden" name="synConfig" value="sso" checked="checked"/>&nbsp;&nbsp;${lfn:message('third-im-kk:kk.config.info.sso.success')}</span>
		                   </li>
		                 </c:if>
		                 <c:if test="${synList ==  'mobileApp'}">
		                   <li>
		                   	<span><img src="${KMSS_Parameter_ContextPath}third/im/kk/resource/images/check.png" style="width: 15px; height: 15px;"><input type="hidden" name="synConfig" value="mobileApp" checked="checked"/>&nbsp;&nbsp;${lfn:message('third-im-kk:kk.config.info.mobile.success')}</span>
		                   </li>
		                 </c:if>
		                 <c:if test="${synList ==  'pcApp'}">
		                   <li>
		                   	<span><img src="${KMSS_Parameter_ContextPath}third/im/kk/resource/images/check.png" style="width: 15px; height: 15px;"><input type="hidden" name="synConfig" value="pcApp" checked="checked"/>&nbsp;&nbsp;${lfn:message('third-im-kk:kk.config.info.pc.success')}</span>
		                   </li>
		                 </c:if>
		                 <c:if test="${synList ==  'waitPush'}">
		                   <li>
		                   	<span><img src="${KMSS_Parameter_ContextPath}third/im/kk/resource/images/check.png" style="width: 15px; height: 15px;"><input type="hidden" name="synConfig" value="waitPush" checked="checked"/>&nbsp;&nbsp;${lfn:message('third-im-kk:kk.config.info.push.success')}</span>
		                   </li>
		                 </c:if>
		                 <c:if test="${synList ==  'sms'}">
		                   <li>
		                   	<span><img src="${KMSS_Parameter_ContextPath}third/im/kk/resource/images/check.png" style="width: 15px; height: 15px;"><input type="hidden" name="synConfig" value="sms" checked="checked"/>&nbsp;&nbsp;${lfn:message('third-im-kk:kk.config.info.sms.success')}</span>
		                   </li>
		                 </c:if>
		                 <!-- 智能助手 -->
                   		<kmss:ifModuleExist path="/third/intell/">
			                 <c:if test="${synList ==  'robot'}">
			                   <li>
			                   	<span><img src="${KMSS_Parameter_ContextPath}third/im/kk/resource/images/check.png" style="width: 15px; height: 15px;"><input type="hidden" name="synConfig" value="robot" checked="checked"/>&nbsp;&nbsp;${lfn:message('third-im-kk:kk.config.info.robot.success')}</span>
			                   </li>
			                 </c:if>
			           	</kmss:ifModuleExist>
			           	  <c:if test="${synList ==  'extend'}">
		                   <li>
		                   	<span><img src="${KMSS_Parameter_ContextPath}third/im/kk/resource/images/check.png" style="width: 15px; height: 15px;"><input type="hidden" name="synConfig" value="extend" checked="checked"/>&nbsp;&nbsp;${lfn:message('third-im-kk:kk.config.info.extend.success')}</span>
		                   </li>
		                 </c:if>
	                 </c:forEach>
               </ul>
               <button class="integra-btn active" onclick="submit();">${lfn:message('third-im-kk:kk.config.info.success')}</button>
           </section>
       </section>
   </section>
</body>

<script type="text/javascript">


//提交
function submit(){
	var selected="";
  	
  	$("input[name='synConfig']").each(function(){
  	    selected += $(this).val() + ",";
  	});
  	 $.ajax({
        type: "post",
        url: "${LUI_ContextPath}/third/im/kk/connKKConfig.do?method=syncNotify",
        data: {"selected":selected},
        async : true,
        dataType: "json",
        success: function (data ,textStatus, jqXHR)
        {
  
            if('0' == data.result ){
            	window.location.href = "${LUI_ContextPath}/third/im/kk/connKKConfig.do?method=configView";
            	return true;
            }else{
            	alert(data.error_msg);
                return false;
            }
        },
        error:function (XMLHttpRequest, textStatus, errorThrown) {      
            alert("请求失败！");
            return false;
        }
     });
}

</script>
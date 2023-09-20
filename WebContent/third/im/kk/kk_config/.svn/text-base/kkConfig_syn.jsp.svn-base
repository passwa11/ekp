<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
    <link type="text/css" rel="stylesheet" href="${KMSS_Parameter_ContextPath}third/im/kk/resource/css/main.css"/>
    <script type="text/javascript" src="${KMSS_Parameter_ContextPath}resource/js/jquery.js"></script>
    
<body id="body">
		<section class="right-modeleMain" v-show="main.basePage">
        <section class="content">
            <header class="integra-head">
                <h3>${lfn:message('third-im-kk:kk.config.syn.title')}</h3>
            </header>
            <section class="integra-con integra-conwarp" v-show="base.page2" style="width: 700px">
                <ul>
                    <li>
                    	<span><input type="checkbox" id="org" name="synConfig" value="org" onclick="chooseOrg();"/>&nbsp;&nbsp;<label for="org">${lfn:message('third-im-kk:kk.config.syn.org')}:
                       	${lfn:message('third-im-kk:kk.config.syn.org.tips')}</label></span>
                    </li>
                    <li>
                    	<span><input type="checkbox" id="orgVisible" name="synConfig" value="orgVisible" onclick="chooseOrgVisible();"/>&nbsp;&nbsp;<label for="orgVisible">${lfn:message('third-im-kk:kk.config.syn.orgVisible')}:
                      	  ${lfn:message('third-im-kk:kk.config.syn.orgVisible.tips')}</label></span>
                    </li>
                    <li>
                    	<span><input type="checkbox" id="sso" name="synConfig" value="sso"/>&nbsp;&nbsp;<label for="sso">${lfn:message('third-im-kk:kk.config.syn.sso')}:
                      	 ${lfn:message('third-im-kk:kk.config.syn.sso.tips')}</label></span>
                    </li>
                     <li>
                    	<span><input type="checkbox" id="mobileApp" name="synConfig" value="mobileApp"/>&nbsp;&nbsp;<label for="mobileApp">${lfn:message('third-im-kk:kk.config.syn.mobile')}:
                      	${lfn:message('third-im-kk:kk.config.syn.mobile.tips')}</label></span>
                    </li>
                     <li>
                    	<span><input type="checkbox" id="pcApp" name="synConfig" value="pcApp"/>&nbsp;&nbsp;<label for="pcApp">${lfn:message('third-im-kk:kk.config.syn.pc')}:
                      	  ${lfn:message('third-im-kk:kk.config.syn.pc.tips')}</label></span>
                    </li>
                    <li>
                    	<span><input type="checkbox" id="waitPush" name="synConfig" value="waitPush"/>&nbsp;&nbsp;<label for="waitPush">${lfn:message('third-im-kk:kk.config.syn.push')}:
                      	  ${lfn:message('third-im-kk:kk.config.syn.push.tips')}</label></span>
                    </li>
                     <li>
                    	<span><input type="checkbox" id="sms" name="synConfig" value="sms"/>&nbsp;&nbsp;<label for="sms">${lfn:message('third-im-kk:kk.config.syn.sms')}:
                      	  ${lfn:message('third-im-kk:kk.config.syn.sms.tips')}</label></span>
                    </li>
                   
                   <!-- 智能助手 -->
                   <kmss:ifModuleExist path="/third/intell/">
	                    <li>
	                    	<span><input type="checkbox" id="robot" name="synConfig" value="robot"/>&nbsp;&nbsp;<label for="robot">${lfn:message('third-im-kk:kk.config.syn.robot')}:
	                      	  ${lfn:message('third-im-kk:kk.config.syn.robot.tips')}</label></span>
	                    </li>
	               </kmss:ifModuleExist>
	               
	               <li>
                    	<span><input type="checkbox" id="extend" name="synConfig" value="extend"/>&nbsp;&nbsp;<label for="extend">${lfn:message('third-im-kk:kk.config.syn.extend')}:
                      	  ${lfn:message('third-im-kk:kk.config.syn.extend.tips')}</label></span>
                    </li>
                </ul>
                <button class="integra-btn active" onclick="submit();">${lfn:message('third-im-kk:kk.config.connect.next')}</button>
            </section>
        </section>
    </section>
</body>

<script type="text/javascript">

	$(function(){ 
		 $('input[name="synConfig"]').prop("checked",true); 
	})

	function chooseOrg(){
		if($("input[name='synConfig'][value='org']").is(':checked')){
			$("input[name='synConfig'][value='orgVisible']").prop("checked", true); 
		}else{
			$("input[name='synConfig'][value='orgVisible']").prop("checked", false);
		}
	}
	
	function chooseOrgVisible(){
		if($("input[name='synConfig'][value='orgVisible']").is(':checked')){
			$("input[name='synConfig'][value='org']").prop("checked", true); 
		}else{
			$("input[name='synConfig'][value='org']").prop("checked", false);
		}
	}
	

	//提交
	function submit(){
	  	var selected="";
	  	
	  	$("input[name='synConfig']:checked").each(function(){
      	    selected += $(this).val() + ",";
      	});
	  	
	  	if(selected == ""){
	  		alert("请勾选同步配置");
	  		return;
	  	}
	  	
	  	 $.ajax({
	        type: "post",
	        url: "${LUI_ContextPath}/third/im/kk/connKKConfig.do?method=syncDefine",
	        data: {"selected":selected},
	        async : false,
	        dataType: "json",
	        success: function (data ,textStatus, jqXHR)
	        {
	  
	            if('0' == data.result ){
	            	window.location.href = "${LUI_ContextPath}/third/im/kk/connKKConfig.do?method=infoConfigPage&selected="+selected;
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
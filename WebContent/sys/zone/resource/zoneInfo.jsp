<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.zone.model.SysZonePrivateConfig" %>

<script>
	seajs.use(['theme!zone','sys/fans/sys_fans_main/style/view.css']);
</script>
<div class="lui_hr_staff_pop_frame sys_zone_card_frame"  id="sys_zone_card_frame">
	<ui:dataview>
		<ui:source type="AjaxJson">
			{url:"/sys/person/sys_person_zone/sysPersonZone.do?method=info&fdId=${JsParam.fdId}"}
		</ui:source>
		<ui:render type="Template">
		if(data.fdIsAvailable != false){
			{$
				<div <%=new SysZonePrivateConfig().hideQrCode()?"style='display: none;'":"" %> class="staff_dropbox_toggle" data-url="{%data.fdName%}#{%data.fdPostName%}#{%data.fdWorkPhone%}#{%data.fdMobileNo%}#{%data.fdEmail%}"  onmouseenter="showQrCode(this);" onmouseleave="hideQrCode(this);">
					<div class="iconbox">
						<span class="icon_QRcode"></span>
						<span class="icon_PC"></span>
					</div>
				</div>
			 $}
		 }
		
			{$
				<div class="sys_zone_card_content sys_zone_card_detail_info">
					<div class="sys_zone_card_photo">
						<div class="card_photo">
			 $}
		 
				if(data.isFullPath == true) {
		            {$
		                <img src="{%data.imgUrl%}">
		            $}
		            }else{
		            {$
		                <img src="${LUI_ContextPath}{%data.imgUrl%}">
		            $}
	            }
	            if(data.fdSex && data.fdSex != ""){
	             {$
						<span class="staff_sex sex_{%data.fdSex%}">
						<i class="staff_sex_{%data.fdSex%} lui_icon_s" title="{%data.fdSexText%}"></i>
						</span>
				 $}
	          	}else{  
	             {$
						<span class="staff_sex sex_M">
						<i class="staff_sex_M lui_icon_s" title="{%data.fdSexText%}"></i>
						</span>
				 $}
				}			
				 {$			
					</div>
					<h3 class="staff_name" title="{%data.fdName%}"> {%data.fdName%}</h3>
				 $}
				if(data.isSelf && data.isSelf == false && data.fdIsAvailable != false) {
		             {$ <div data-lui-mark="follow_btn">$}
		              if(data.rela == 0){
			            {$
				            <a class="sys_zone_btn_focus icon_focusAdd" attent-model-name="com.landray.kmss.sys.zone.model.SysZonePersonInfo" 
				               fans-model-name="com.landray.kmss.sys.zone.model.SysZonePersonInfo"
			                   is-follow-person="true" fans-action-type="unfollowed" fans-action-id="${HtmlParam.fdId}" 
			                   href="javascript:void(0);" 
							   onclick="_layer_zone_follow_action(this);">
			                   <span><i></i><span>${lfn:message('sys-zone:sysZonePerson.cared1')}</span></span>
			                </a>
			             $}
			           }else if(data.rela == 1){
			             {$
				            <a class="sys_zone_btn_focus icon_focused">
			                   <span><i></i><span>${lfn:message('sys-zone:sysZonePerson.cancelCared1')}</span></span><em attent-model-name="com.landray.kmss.sys.zone.model.SysZonePersonInfo" 
				               fans-model-name="com.landray.kmss.sys.zone.model.SysZonePersonInfo"
			                   is-follow-person="true" fans-action-type="followed" fans-action-id="${HtmlParam.fdId}" 
			                   href="javascript:void(0);" onclick="_layer_zone_follow_action(this);">${lfn:message('sys-zone:sysZonePerson.cancelCared')}</em>
			                </a>
			             $}
			           }else if( data.rela == 2){
			             {$
				            <a class="sys_zone_btn_focus icon_unfocus" attent-model-name="com.landray.kmss.sys.zone.model.SysZonePersonInfo" 
				               fans-model-name="com.landray.kmss.sys.zone.model.SysZonePersonInfo"
			                   is-follow-person="true" fans-action-type="unfollowed" fans-action-id="${HtmlParam.fdId}" 
			                   href="javascript:void(0);" onclick="_layer_zone_follow_action(this);">
			                   <span><i></i><span>${lfn:message('sys-zone:sysZonePerson.cared1')}</span></span>
			                </a>
			             $}
			           }else if(data.rela == 3){
			             {$
				            <a class="sys_zone_btn_focus icon_eachFocus">
			                   <span><i></i><span>${lfn:message('sys-zone:sysZonePerson.follow.each')}</span></span><em attent-model-name="com.landray.kmss.sys.zone.model.SysZonePersonInfo" 
				               fans-model-name="com.landray.kmss.sys.zone.model.SysZonePersonInfo"
			                   is-follow-person="true" fans-action-type="followed" fans-action-id="${HtmlParam.fdId}" 
			                   href="javascript:void(0);" onclick="_layer_zone_follow_action(this);">${lfn:message('sys-zone:sysZonePerson.cancelCared')}</em>
			                </a>
			             $}
			           }
	          		 {$</div>$}
            	 }
			 {$</div>$}
			{$	
				<div class="sys_zone_card_info">
					<ul class="sys_zone_card_info_list">
			$}
			{$
			<li>
				<em>${lfn:message('sys-zone:sysZonePerson.dept')}</em>	
			$}			
				if(data.fdIsAvailable != false && (data.isDepInfoPrivate != true)){
					{$
						<span>
							<c:out value="{%data.fdDeptName%}"></c:out>
						</span>
					$}
				}else{
					{$
						<span class = "sys_zone_card_status_undisclosed">
							[ ${lfn:message('sys-zone:sysZonePerson.undisclosed')} ]
						</span>
					$}
				}	
								
			{$
			</li>
			$}
							
			{$
			<li>
				<em>${lfn:message('sys-zone:sysZonePerson.mobilePhone')}</em>	
			$}			
				if(data.fdIsAvailable != false && (data.isContactPrivate != true)){
					{$
						<span>
							<c:out value="{%data.fdMobileNo%}"></c:out>
						</span>
					$}
				}else{
					{$
						<span class = "sys_zone_card_status_undisclosed">
							[ ${lfn:message('sys-zone:sysZonePerson.undisclosed')} ]
						</span>
					$}
				}	
								
			{$
			</li>
			$}
			
			{$
			<li>
				<em>${lfn:message('sys-zone:sysZonePerson.shortNo')}</em>
			$}			
				if(data.fdIsAvailable != false && (data.isContactPrivate != true)){
					{$
						<span>
							<c:out value="{%data.fdShortNo%}"></c:out>
						</span>
					$}
				}else{
					{$
						<span class = "sys_zone_card_status_undisclosed">
							[ ${lfn:message('sys-zone:sysZonePerson.undisclosed')} ]
						</span>
					$}
				}	
								
			{$
			</li>
			$}
			
			{$
			<li>
				<em>${lfn:message('sys-zone:sysZonePerson.email')}</em>	
			$}			
				if(data.fdIsAvailable != false && (data.isContactPrivate != true)){
					{$
						<span>
							<c:out value="{%data.fdEmail%}"></c:out>
						</span>
					$}
				}else{
					{$
						<span class = "sys_zone_card_status_undisclosed">
							[ ${lfn:message('sys-zone:sysZonePerson.undisclosed')} ]
						</span>
					$}
				}	
								
			{$
			</li>
			$}
			{$
			<li>
				<em>${lfn:message('sys-zone:sysZonePerson.post')}</em>	
			$}			
				if(data.fdIsAvailable != false && (data.isDepInfoPrivate != true)){
					{$
						<span>
							<c:out value="{%data.fdPostName%}"></c:out>
						</span>
					$}
				}else{
					{$
						<span class = "sys_zone_card_status_undisclosed">
							[ ${lfn:message('sys-zone:sysZonePerson.undisclosed')} ]
						</span>
					$}
				}	
								
			{$
			</li>
			$}
			{$
					</ul>
					<a target="_blank"  href="${LUI_ContextPath }/sys/person/sys_person_zone/sysPersonZone.do?method=view&fdId=${HtmlParam.fdId}"  class="sys_zone_card_more" >${lfn:message('sys-zone:sysZonePerson.more')}</a>
				</div>
			$}	
			{$</div>$}
			{$
				<div class="sys_zone_card_code">
					<div id="personQrCode" class="sys_zone_card_code_img">
					</div>
					<p>${lfn:message('sys-zone:sysZonePerson.saveto.addressBook')}</p>
				</div>
			$}	
			{$
				<div class="sys_zone_card_footer">
			$}	
			if(data.fdIsAvailable == false) {
			   {$
			   	<p class="tips">${lfn:message('sys-zone:sysZonePerson.dimission')}</p>
			   $}	
			}else{
			
				{$
					<div id="contactBar"  data-person-role="contact"
						 data-person-param="&fdId=${HtmlParam.fdId}&fdName={%data.fdName%}&fdLoginName={%data.fdLoginName%}&fdEmail={%data.fdEmail%}&fdMobileNo={%data.fdMobileNo%}&dingUserid={%data.dingUserid%}&ldingUserid={%data.ldingUserid%}&dingCropid={%data.dingCropid%}">	
					</div>
				$}	
			}	
			{$
				</div>
			$}
		</ui:render>
		<ui:event event="load">
			setTimeout(function(){
				var datas = [];
				var personParam = $("#contactBar").attr("data-person-param");
				console.log("personParam=>"+personParam);
				datas.push({
					elementId :$("#contactBar").attr("id"),
					personId: Com_GetUrlParameter(personParam, "fdId"),
					personName:Com_GetUrlParameter(personParam, "fdName"),
					loginName :Com_GetUrlParameter(personParam, "fdLoginName"),
					email:Com_GetUrlParameter(personParam, "fdEmail"),
					mobileNo:Com_GetUrlParameter(personParam, "fdMobileNo"),
					isSelf : ("${KMSS_Parameter_CurrentUserId}" == Com_GetUrlParameter(personParam, "fdId")),
					isCard:true,
					dingUserid:Com_GetUrlParameter(personParam, "dingUserid"),
					ldingUserid:Com_GetUrlParameter(personParam, "ldingUserid"),
					dingCropid:Com_GetUrlParameter(personParam, "dingCropid")
				});
				$("#contactBar").html("");
				onRender(datas);
				
				<kmss:ifModuleExist  path = "/third/im/kk/">
					var fdId = "${JsParam.fdId }";
					var url = "${KMSS_Parameter_ContextPath}third/im/kk/user.do?method=getUserPresence";
					$.ajax({
				        type: "post",
				        url:  url,
				        data: {"fdId":fdId},
				        async : false,
				        dataType: "json",
				        success: function (results ,textStatus, jqXHR)
				        {
				        	if(results != null){
								if(results.pcState == '1'){
									$(".staff_name").append('<span class="pc_state_on">在线</span>');
								}else if(results.pcState == '2'){
									
								}else{
									$(".staff_name").append('<span class="pc_state_off">在线</span>');
								}
							}
				        },
				        error:function (XMLHttpRequest, textStatus, errorThrown) {      
				        	console.log("获取用户状态接口请求失败，请检查网络！");
				        }
				     });
				</kmss:ifModuleExist>
				
			},100);
		</ui:event>
	</ui:dataview>
</div>
<script>
	if(!window._layer_zone_follow_after) {
		window._layer_zone_follow_after =  function (data, fdUserId, isFollowed,
				 $element, config, isFollowPerson, attentModelName, fansModelName) {
			var outer = $element.parents("[data-lui-mark='follow_btn']")[0];
			if(outer &&  data.result == "success") {
			   if(data.relation == 0){
				   var html = '<a class="sys_zone_btn_focus icon_focusAdd" attent-model-name="com.landray.kmss.sys.zone.model.SysZonePersonInfo"'+ 
		               'fans-model-name="com.landray.kmss.sys.zone.model.SysZonePersonInfo"'+
	                   'is-follow-person="true" fans-action-type="unfollowed" fans-action-id="' + fdUserId +'"'+ 
	                   'href="javascript:void(0);" onclick="_layer_zone_follow_action(this);">'+
	                   '<span><i></i><span>${lfn:message("sys-zone:sysZonePerson.cared1")}<span></span></a>';
				    $(outer).html(html);
	           }else if(data.relation == 1){
	        	  var html = '<a class="sys_zone_btn_focus icon_focused"><span><i></i><span>${lfn:message("sys-zone:sysZonePerson.cancelCared1")}</span></span><em attent-model-name="com.landray.kmss.sys.zone.model.SysZonePersonInfo"'+ 
			               'fans-model-name="com.landray.kmss.sys.zone.model.SysZonePersonInfo"is-follow-person="true"'+ 
			               'fans-action-type="followed" fans-action-id="'+ fdUserId +'" href="javascript:void(0);"onclick="_layer_zone_follow_action(this);">${lfn:message("sys-zone:sysZonePerson.cancelCared")}</em></a>';
			      $(outer).html(html);
	           }else if( data.relation == 2){
	             var html = '<a class="sys_zone_btn_focus icon_unfocus" attent-model-name="com.landray.kmss.sys.zone.model.SysZonePersonInfo"'+ 
		                    'fans-model-name="com.landray.kmss.sys.zone.model.SysZonePersonInfo" is-follow-person="true"'+
		                    'fans-action-type="unfollowed" fans-action-id="'+ fdUserId +'"  href="javascript:void(0);"  onclick="_layer_zone_follow_action(this);">'+
	                        '<span><i></i><span>${lfn:message("sys-zone:sysZonePerson.cared1")}</span></span></a>';
	                $(outer).html(html);
	           }else if(data.relation == 3){
	        	   var html = '<a class="sys_zone_btn_focus icon_eachFocus"><span><i></i><span>${lfn:message("sys-zone:sysZonePerson.follow.each")}</span></span><em attent-model-name="com.landray.kmss.sys.zone.model.SysZonePersonInfo"'+ 
		                      'fans-model-name="com.landray.kmss.sys.zone.model.SysZonePersonInfo" is-follow-person="true" fans-action-type="followed"'+
		                      'fans-action-id="'+ fdUserId +'"  href="javascript:void(0);" onclick="_layer_zone_follow_action(this);">${lfn:message("sys-zone:sysZonePerson.cancelCared")}</em></a>';
	        	   $(outer).html(html);
			   }
			}
		}; 
	}
	if(!window._layer_zone_follow_action) {
		window._layer_zone_follow_action =  function (target) {
			seajs.use(['sys/fans/resource/sys_fans','lui/jquery'], function(follow, $) {
				var $this = $(target);
				var isFollowed = $this.attr("fans-action-type");
				var isFollowPerson = $this.attr("is-follow-person");
				var attentModelName = $this.attr("attent-model-name");
				var fansModelName = $this.attr("fans-model-name");
				if(isFollowed) {
					var userId = $this.attr("fans-action-id");
					follow.fansFollow(userId , isFollowed, isFollowPerson, attentModelName, fansModelName, 
							$this, _layer_zone_follow_after,$("#sys_zone_card_frame"));
				}
			});
		};
	}
	
		/*
		BEGIN:VCARD
		FN:user name
		TITLE:23424234
		ADR;HOME:;;hahahahhaha;;;;
		ORG:ahhfhahfhah
		TEL;WORK,VOICE:13433443322
		TEL;HOME,VOICE:0722-33344544
		URL;WORK:www.baidu.com
		EMAIL;INTERNET,HOME:234533444@qq.com
		END:VCARD
		*/
		
	function toUtf8(str) {    
		    var out, i, len, c;    
		    out = "";    
		    len = str.length;    
		    for(i = 0; i < len; i++) {    
		        c = str.charCodeAt(i);    
		        if ((c >= 0x0001) && (c <= 0x007F)) {    
		            out += str.charAt(i);    
		        } else if (c > 0x07FF) {    
		            out += String.fromCharCode(0xE0 | ((c >> 12) & 0x0F));    
		            out += String.fromCharCode(0x80 | ((c >>  6) & 0x3F));    
		            out += String.fromCharCode(0x80 | ((c >>  0) & 0x3F));    
		        } else {    
		            out += String.fromCharCode(0xC0 | ((c >>  6) & 0x1F));    
		            out += String.fromCharCode(0x80 | ((c >>  0) & 0x3F));    
		        }    
		    }    
		    return out;    
		} 
		
	function showQrCode(obj){
		var isBitch = navigator.userAgent.indexOf("MSIE") > -1
		&& document.documentMode == null || document.documentMode <= 8;
		if (isBitch) // 直接去除对ie8浏览器的支持
			return;
		$(obj).addClass("staff_dropbox_on");
		var aa = $(obj).attr("data-url")
		var xx = aa.split("#");
		var str = "BEGIN:VCARD\nVERSION:3.0";
		if(xx[0]){ // 名字
			str +="\nN:"+toUtf8(xx[0])+";;;";
			str +="\nFN: "+toUtf8(xx[0]);
		}
		if(xx[6]){
			str+="\nROLE:"+ toUtf8($.trim(xx[6]));
		}
		if(xx[1] && xx[1]!= ''){ // 岗位
			str +="\r\nTITLE:"+toUtf8(xx[1]).replace(/;/g,",");
		}
		if(xx[2] && xx[2]!= ''){ // 办公电话
			str +="\r\nTEL;WORK,VOICE:"+xx[2];
		}
		if(xx[3] && xx[3]!= ''){ // 手机号码
			str +="\r\nTEL;CELL,VOICE:"+xx[3];
		}
		if(xx[4] && xx[4]!= ''){ // 电子邮箱
			str +="\r\nEMAIL;INTERNET,HOME:"+xx[4];
		}
		str += "\r\nEND:VCARD";
		seajs.use(['lui/qrcode'], function(qrcode) {
			qrcode.Qrcode({
				text :str,
				element : $("#personQrCode"),
				render :  'canvas'
			});
		});
		$(".sys_zone_card_code").show();
	}
	
	function hideQrCode(obj){
		var isBitch = navigator.userAgent.indexOf("MSIE") > -1
		&& document.documentMode == null || document.documentMode <= 8;
		if (isBitch) // 直接去除对ie8浏览器的支持
			return;
		$(obj).removeClass("staff_dropbox_on");
		$("#personQrCode").html("");
		$(".sys_zone_card_code").hide();
	}
</script>
<%@ include file="/sys/zone/sys_zone_personInfo/sysZonePersonContact_include.jsp"%>
<script>
		$(document).ready(function() {
			
		});

</script>

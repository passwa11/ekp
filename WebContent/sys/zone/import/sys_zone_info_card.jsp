<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.zone.forms.SysZonePersonInfoForm" %>
<%@ page import="com.landray.kmss.util.StringUtil" %>
<%@ page import="com.landray.kmss.sys.zone.model.SysZonePrivateConfig" %>
<%@ page import="com.landray.kmss.sys.zone.util.SysZonePrivateUtil" %>

<script>
	seajs.use(['theme!zone']);
</script>
<div class="lui_staff_pop_frame sys_zone_person_card_frame"  id="sys_zone_person_card_frame">
				<div <%=new SysZonePrivateConfig().hideQrCode()?"style='display: none;'":"" %> class="staff_dropbox_toggle" data-url=" <c:out value='${sysZonePersonInfoForm.personName}'/>#<c:out value='${post}'/>#<c:out value='${mobilPhone }'/>#<c:out value='${fdCompanyPhone }'/>#<c:out value='${email }'/>#<c:out value='${organization}'/>#<c:out value='${dep}'/>"  onmouseover="showQrCode(this);" onMouseOut="hideQrCode(this)" >
					<div class="iconbox">
						<span class="icon_QRcode"></span>
						<span class="icon_PC"></span>
					</div>
				</div>
				<div class="sys_zone_person_card_content ">
					<div class="sys_zone_person_card_detail_info">
						<div class="sys_zone_person_card_photo">
							<div class="person_card_name">
							<div style="float:left">
								<p class="staff_name" title="${fn:escapeXml(sysZonePersonInfoForm.personName)}">
									<c:out value="${sysZonePersonInfoForm.personName}" />
								</p>
								
						<c:if test="${sysOrgPerson.fdSex == 'F' }">
							<span class="sys_zone_sex_f_icon" style="width:26px;height:15px" title="${lfn:message('sys-organization:sysOrgPerson.fdSex.F')}" ></span>
						</c:if>
						
						<c:if test="${sysOrgPerson.fdSex == 'M' }">
							<span class="sys_zone_sex_m_icon" style="width:26px;height:15px" title="${lfn:message('sys-organization:sysOrgPerson.fdSex.M')}" ></span>
						</c:if>

						<c:if test="${isExpert == true}"> 
							<span class="sys_zone_expert_icon" style="width:26px;height:23px" title="${lfn:message('sys-zone:sysZonePersonInfo.isExpert')}" ></span>
						</c:if>
						<c:if test="${isLecturer == true && fdLecturerAvailable!='0'}"> 
							<span class="sys_zone_lecturer_icon" style="width:26px;height:23px" title="${lfn:message('sys-zone:sysZonePersonInfo.isLecturer')}" ></span>
						</c:if>
						</div>
						<c:if test="${fdLecturerAvailable!='0'}"> 
							<c:if test="${not empty lectTitleName}"> 
								<p class="lecturer_title" title="${lectTitleName}" style="margin-top: 12px;width: 100px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;padding-left: 10px;"> 
									${lectTitleName}	
								</p>
							</c:if>
						</c:if>
							</div>
						</div>
					 
					  	<ul>
					  		<li style="width:95%">
					  			<%
					  				SysZonePersonInfoForm sysZonePersonInfoForm= (SysZonePersonInfoForm)request.getAttribute("sysZonePersonInfoForm");
						  			if(sysZonePersonInfoForm!=null){
						  				request.setAttribute("isDepInfoPrivate", SysZonePrivateUtil.isDepInfoPrivate(sysZonePersonInfoForm.getFdId()));
						  			}
					  			%>
					  			<c:choose>
					  				<c:when test='${isDepInfoPrivate}'>
					  					<span class="sys_zone_status_undisclosed">
											[ ${lfn:message('sys-zone:sysZonePerson.undisclosed')} ]
										</span>
					  				</c:when>
					  				<c:otherwise>
										<span onmouseover="showDescription(this);">
					  						<c:if test="${(not empty organization) ||(not empty dep)}">
												<span>
													<c:if test="${not empty organization}">
														<c:out value="${organization}"/>
													</c:if>
													<c:if test="${not empty dep}">
														<c:if test="${not empty organization}">
															<em>|</em>
														</c:if>
														<c:out value="${dep}"/>
													</c:if>
												</span>
												<c:if test="${not empty post}">
												 :
												</c:if>
											</c:if>
											<c:if test="${not empty post}"> 
												<span><c:out value="${post}"/></span>
											</c:if>
										</span>
									</c:otherwise>
								</c:choose>
					  		</li>
					  		<li style="width:55%">
						  		<span> ${lfn:message('sys-zone:sysZonePerson.mobilePhone')}</span>   :
								<c:choose>
									<c:when test="${mobilPhone eq 'undisclosed' || sysZonePersonInfoForm.isContactPrivate == true}">
										<span class="sys_zone_status_undisclosed">
											[ ${lfn:message('sys-zone:sysZonePerson.undisclosed')} ]
										</span>
									</c:when>
									<c:otherwise>
										<span title="${fn:escapeXml(mobilPhone)}">
											${fn:escapeXml(mobilPhone)}
										</span>
									</c:otherwise>
								</c:choose>
					  		</li>
					  		<li style="width:40%">
						  		<span> ${lfn:message('sys-zone:sysZonePerson.shortNo')}</span>   :
								<c:choose>
									<c:when test="${shortNo eq 'undisclosed' || sysZonePersonInfoForm.isContactPrivate == true}">
										<span class="sys_zone_status_undisclosed">
											[ ${lfn:message('sys-zone:sysZonePerson.undisclosed')} ]
										</span>
									</c:when>
									<c:otherwise>
										<span title="${fn:escapeXml(shortNo)}">
											${fn:escapeXml(shortNo)}
										</span>
									</c:otherwise>
								</c:choose>
					  		</li>
					  		<li style="width:55%">
						  		<span> ${lfn:message('sys-zone:sysZonePerson.email')}</span>   :
								<c:choose>
									<c:when test="${email eq 'undisclosed' || sysZonePersonInfoForm.isContactPrivate == true}">
										<span class="sys_zone_status_undisclosed">
											[ ${lfn:message('sys-zone:sysZonePerson.undisclosed')} ]
										</span>
									</c:when>
									<c:otherwise>
										<span title="${fn:escapeXml(email)}">
											${fn:escapeXml(email)}
										</span>
									</c:otherwise>
								</c:choose>
					  		</li>
					  		<li style="width:40%">
						  		<span> ${lfn:message('sys-zone:sysZonePerson.workPhone')}</span>   :
								<c:choose>
									<c:when test="${fdCompanyPhone eq 'undisclosed' || sysZonePersonInfoForm.isContactPrivate == true}">
										<span class="sys_zone_status_undisclosed">
											[ ${lfn:message('sys-zone:sysZonePerson.undisclosed')} ]
										</span>
									</c:when>
									<c:otherwise>
										<span title="${fn:escapeXml(fdCompanyPhone)}">
											${fn:escapeXml(fdCompanyPhone)}
										</span>
									</c:otherwise>
								</c:choose>
					  		</li>
					  		
					  	</ul>
					</div>
				</div>
				
				 <!-- 个性签名 -->
				 <c:if test="${ empty param.fromPage}">
					<c:if test="${not empty sysZonePersonInfoForm.fdSignature}">
							<p class="fdSignature_table">
								<bean:message bundle="sys-zone" key="sysZonePersonInfo.fdSignature"/>：
							<span title="${fn:escapeXml(sysZonePersonInfoForm.fdSignature)}">
								<c:out value="${sysZonePersonInfoForm.fdSignature}" escapeXml="true"/>				
							</span>
							</p>
					</c:if>
				</c:if>
				
				<div class="sys_zone_person_card_code" >
					<div id="personQrCode" class="sys_zone_person_card_code_img_new">
					</div>
					<p>${lfn:message('sys-zone:sysZonePerson.saveto.addressBook')}</p>
				</div>
</div>

<script>
	
		/*
		BEGIN:VCARD
		N:dfdf;dfdf;;;
		FN: dfdf  dfdf
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
		return str;
	    /* var out, i, len, c;    
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
	    return out;     */
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
	 	if(xx[0]){
			str +="\nN:"+toUtf8(xx[0])+";;;";
			str +="\nFN: "+toUtf8(xx[0]);
		}
		
		if(xx[6]){
			str+="\nROLE:"+ toUtf8($.trim(xx[6]));
		}
		if(xx[1]){ 
			str +="\nTITLE:"+(toUtf8($.trim(xx[1]))).replace(new RegExp(";","g"),",");
		}
	    if(xx[2]){
			str +="\nTEL;CELL,VOICE:"+toUtf8($.trim(xx[2]));
		} 
		if(xx[3]){
			str +="\nTEL;WORK,VOICE:"+toUtf8($.trim(xx[3]));
		}
		if(xx[4]){
			str +="\nEMAIL;PREF,INTERNET:"+toUtf8($.trim(xx[4]));
		}
		if(xx[5]){
			if(${email eq 'undisclosed' || sysZonePersonInfoForm.isContactPrivate == true}){
				str+="\nORG:"+ toUtf8("");
			}else{
				str+="\nORG:"+ toUtf8($.trim(xx[5]));	
			}
		} 
		str += "\nEND:VCARD"; 
		
		seajs.use(['lui/qrcode'], function(qrcode) {
			qrcode.Qrcode({
				text :str,
				element : $("#personQrCode"),
				render :  'custom',
				className:'sys_zone_person_card_code_img_size',
				genWidth:140,
				genHeight:140
			});
		});
		$(".sys_zone_person_card_code").show();
	}
	
	function hideQrCode(obj){
		var isBitch = navigator.userAgent.indexOf("MSIE") > -1
		&& document.documentMode == null || document.documentMode <= 8;
		if (isBitch) // 直接去除对ie8浏览器的支持
			return;
		$(obj).removeClass("staff_dropbox_on");
		$("#personQrCode").html("");
		$(".sys_zone_person_card_code").hide();
	}

	function addIconmess(mess){
		if(mess!=null&&mess=="lecturer"){
			$(".fd_lecturer_mess").show();
		}else if(mess!=null&&mess=="expert"){
			$(".fd_expert_mess").show();
		}
	}

	function delIconmess(mess){
		if(mess!=null&&mess=="lecturer"){
			$(".fd_lecturer_mess").hide();
		}else if(mess!=null&&mess=="expert"){
			$(".fd_expert_mess").hide();
		}
	}
	
	function showDescription(obj){
		var organization = "${organization }";
		var dep = "${dep }";
		var post = "${post }";
		var text = "";
		if (organization) {
			text += organization;
		}
		if (dep) {
			if (organization) {
				text += "|";
			}
			text += dep;
		}
		if (post) {
			text += " : " + post;
		}
		if ($.trim(text)) {
			$(obj).attr("title", text);
		}
	}
</script>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
{$<div class="imeeting_history_box">$}
var tmpDate="";
for(var i=0; i < data.length; i++){
	var date=data[i].date;
	
	if(!tmpDate ||  tmpDate.substring(0,10)!=date.substring(0,10) ){
		{$
			<div class="imeeting_history_div_dateBar">
				<div class="imeeting_history_dateBar">
					<span class="imeeting_history_dateBar_month">{%date.substring(0,10)%}</span>
				</div>
			</div>
		$}
	}
	
	var opt=data[i].opt;
	var content=LUI.toJSON(data[i].content);
	
	<%-- 发起会议--%>
	if(opt=='01'){
		{$
			<div class="imeeting_history_head">
				<span class="imeeting_history_time">{%date.substring(11)%}</span>
				<span class="imeeting_history_line"></span>
				<span class="imeeting_history_title icon_meeting_create" >
					${lfn:message('km-imeeting:enumeration_km_imeeting_main_history_fd_opt_type_add')}
				</span>
			</div>
		$}
		
		{$
			<div class="imeeting_history_contentWrap">
				<i class="imeeting_history_arrow M_arr"></i>
				<i class="imeeting_history_arrow C_arr"></i>
				
				<div class="imeeting_history_contentHeadL">
					<div class="imeeting_history_contentHeadR">
						<div class="imeeting_history_contentHeadC"></div>
					</div>
				</div>
				
				
				<div class="imeeting_history_content">
					<div class="imeeting_history_s_content_title">
						<img src="{%env.fn.formatUrl(data[i].optPersonHeadUrl)%}" class="author_img">
						<ul class="title_txt">
							<li>
								<span>${lfn:message('km-imeeting:kmImeetingMain.docCreator') }：{%data[i].optPersonName%}</span>
							</li>
						</ul>
						<span style="margin-right: 40px;"><bean:message bundle="km-imeeting" key="kmImeetingMain.fdTemplate"/>：{% env.fn.formatText(content.fdTemplate) %}</span>
						<span><bean:message bundle="km-imeeting" key="kmImeetingMain.fdMeetingNum"/>：{% env.fn.formatText(content.fdMeetingNum) %}</span>
					</div>
					<div class="imeeting_history_s_content_txt">
						<bean:message bundle="km-imeeting" key="kmImeetingSummary.fdName"/>：{% env.fn.formatText(content.fdName) %}<br/>
						<bean:message bundle="km-imeeting" key="kmImeetingMain.fdMeetingAim"/>：{% env.fn.formatText(content.fdMeetingAim) %}
					</div>
				</div>
				<div class="imeeting_history_contentFootL">
					<div class="imeeting_history_contentFootR">
						<div class="imeeting_history_contentFootC"></div>
					</div>
				</div>
			</div>
		$}
	}
	
	<%-- 通知会议--%>
	if(opt=='02'){
		{$
			<div class="imeeting_history_head">
				<span class="imeeting_history_time">{%date.substring(11)%}</span>
				<span class="imeeting_history_line"></span>
				<span class="imeeting_history_title icon_meeting_notify" >
					${lfn:message('km-imeeting:enumeration_km_imeeting_main_history_fd_opt_type_notify')}
				</span>
			</div>
		$}
		
		{$
			<div class="imeeting_history_contentWrap">
				<i class="imeeting_history_arrow M_arr"></i>
				<i class="imeeting_history_arrow C_arr"></i>
				
				<div class="imeeting_history_contentHeadL">
					<div class="imeeting_history_contentHeadR">
						<div class="imeeting_history_contentHeadC"></div>
					</div>
				</div>
				
				<div class="imeeting_history_content">
					<div class="fdName">{% env.fn.formatText(content.fdName) %}</div>
					<div class="imeeting_history_s_content_title">
						<img src="{%env.fn.formatUrl(data[i].optPersonHeadUrl)%}" class="author_img">
						<ul class="title_txt">
							<li>
								<span>${lfn:message('km-imeeting:kmImeetingMain.fdHost') }：{%data[i].optPersonName%}</span>
							</li>
						</ul>
						<span style="display: block">${lfn:message('km-imeeting:kmImeetingMain.fdDate') }：{%content.date%}</span>
						<span style="line-height: 20px;">${lfn:message('km-imeeting:kmImeetingMain.fdPlace') }：{%content.fdPlace%}</span>
					</div>
					<div class="imeeting_history_s_content_txt">
						<div style="line-height: 20px;">
							<bean:message bundle="km-imeeting" key="kmImeetingAgenda.docSubject"/>：
						</div>
		$}
					if(content.agendas){
						for(var m=0 ; m < content.agendas.length ; m++){
							{$
								{%m+1%}、{% env.fn.formatText(content.agendas[m]) %}<br/>
							$}
						}
					}
		{$
					</div>
				</div>
				<div class="imeeting_history_contentFootL">
					<div class="imeeting_history_contentFootR">
						<div class="imeeting_history_contentFootC"></div>
					</div>
				</div>
				
			</div>
		$}
	}
	
	<%-- 上会材料--%>
	if(opt=='03'){
		{$
			<div class="imeeting_history_head">
				<span class="imeeting_history_time">{%date.substring(11)%}</span>
				<span class="imeeting_history_line"></span>
				<span class="imeeting_history_title icon_meeting_upload" >
					${lfn:message('km-imeeting:enumeration_km_imeeting_main_history_fd_opt_type_upload')}
				</span>
			</div>
		$}
		
		{$
			<div class="imeeting_history_contentWrap">
				<i class="imeeting_history_arrow M_arr"></i>
				<i class="imeeting_history_arrow C_arr"></i>
				
				<div class="imeeting_history_contentHeadL">
					<div class="imeeting_history_contentHeadR">
						<div class="imeeting_history_contentHeadC"></div>
					</div>
				</div>
				
				<div class="imeeting_history_content">
					<div class="imeeting_history_s_content_title">
						<img src="{%env.fn.formatUrl(data[i].optPersonHeadUrl)%}" class="author_img">
						<ul class="title_txt">
							<li>
								<span>${lfn:message('km-imeeting:kmImeetingAgenda.docRespons')}：{%data[i].optPersonName%}</span>
							</li>
						</ul>
					</div>
					<div class="imeeting_history_s_content_txt">
						${lfn:message('km-imeeting:kmImeetingAgenda.attachment.submit') }：{%content.attachmentNames%}
					</div>
				</div>
				
				<div class="imeeting_history_contentFootL">
					<div class="imeeting_history_contentFootR">
						<div class="imeeting_history_contentFootC"></div>
					</div>
				</div>
				
			</div>
		$}
	
	}
	
	
	<%-- 取消会议--%>
	if(opt=='04'){
		{$
			<div class="imeeting_history_head">
				<span class="imeeting_history_time">{%date.substring(11)%}</span>
				<span class="imeeting_history_line"></span>
				<span class="imeeting_history_title icon_meeting_cancel" >
					${lfn:message('km-imeeting:enumeration_km_imeeting_main_history_fd_opt_type_cancel')}
				</span>
			</div>
		$}
		
		{$
			<div class="imeeting_history_contentWrap">
				<i class="imeeting_history_arrow M_arr"></i>
				<i class="imeeting_history_arrow C_arr"></i>
				
				<div class="imeeting_history_contentHeadL">
					<div class="imeeting_history_contentHeadR">
						<div class="imeeting_history_contentHeadC"></div>
					</div>
				</div>
				
				<div class="imeeting_history_content">
					<div class="imeeting_history_s_content_title">
						<img src="{%env.fn.formatUrl(data[i].optPersonHeadUrl)%}" class="author_img">
						<ul class="title_txt">
							<li>
								<span>${lfn:message('km-imeeting:kmImeetingMain.cancelPerson')}：{%data[i].optPersonName%}</span>
							</li>
						</ul>
					</div>
					<div class="imeeting_history_s_content_txt">
						${lfn:message('km-imeeting:kmImeetingMain.cancelMeetingReason')}：{% env.fn.formatText(content.cancelReason) %}
					</div>
				</div>
				
				<div class="imeeting_history_contentFootL">
					<div class="imeeting_history_contentFootR">
						<div class="imeeting_history_contentFootC"></div>
					</div>
				</div>
				
			</div>
		$}
	}
	
	<%-- 变更会议--%>
	if(opt=='05'){
		{$
			<div class="imeeting_history_head">
				<span class="imeeting_history_time">{%date.substring(11)%}</span>
				<span class="imeeting_history_line"></span>
				<span class="imeeting_history_title icon_meeting_change" >
					${lfn:message('km-imeeting:enumeration_km_imeeting_main_history_fd_opt_type_change')}
				</span>
			</div>
		$}
		
		{$
			<div class="imeeting_history_contentWrap">
				<i class="imeeting_history_arrow M_arr"></i>
				<i class="imeeting_history_arrow C_arr"></i>
				
				<div class="imeeting_history_contentHeadL">
					<div class="imeeting_history_contentHeadR">
						<div class="imeeting_history_contentHeadC"></div>
					</div>
				</div>
				
				<div class="imeeting_history_content">
					<div class="imeeting_history_s_content_title">
						<img src="{%env.fn.formatUrl(data[i].hostHeadUrl)%}" class="author_img">
						<ul class="title_txt">
							<li>
								<span><bean:message bundle="km-imeeting" key="kmImeetingMain.fdHost"/>：{%content.fdHostName%}</span>
							</li>
						</ul>
						<span style="display: block">${lfn:message('km-imeeting:kmImeetingMain.fdDate') }：{%content.date%}</span>
						<span style="line-height: 20px;">${lfn:message('km-imeeting:kmImeetingMain.fdPlace') }：{%content.fdPlace%}</span>
					</div>
					<div class="imeeting_history_s_content_title" style="border-bottom: 0px;padding: 0px;">
						<img src="{%env.fn.formatUrl(data[i].optPersonHeadUrl)%}" class="author_img">
						<ul class="title_txt">
							<li>
								<span><bean:message bundle="km-imeeting" key="kmImeetingMainHistory.fdChangePerson"/>：{%data[i].optPersonName%}</span>
							</li>
						</ul>
						<div style="padding: 5px 0px 0px;font-size: 12px;">${lfn:message('km-imeeting:kmImeetingMain.changeMeetingReason')}：{%env.fn.formatText(content.changeReason)%}</div>
					</div>
					<div class="imeeting_history_s_content_txt"></div>
				</div>
				
				<div class="imeeting_history_contentFootL">
					<div class="imeeting_history_contentFootR">
						<div class="imeeting_history_contentFootC"></div>
					</div>
				</div>
				
			</div>
		$}
	}
	
	<%-- 提前结束会议 --%>
	if(opt=='08'){
		{$
			<div class="imeeting_history_head">
				<span class="imeeting_history_time">{%date.substring(11)%}</span>
				<span class="imeeting_history_line"></span>
				<span class="imeeting_history_title icon_meeting_change" >
					${lfn:message('km-imeeting:enumeration_km_imeeting_main_history_fd_opt_type_earlyEnd')}
				</span>
			</div>
		$}
		
		{$
			<div class="imeeting_history_contentWrap">
				<i class="imeeting_history_arrow M_arr"></i>
				<i class="imeeting_history_arrow C_arr"></i>
				
				<div class="imeeting_history_contentHeadL">
					<div class="imeeting_history_contentHeadR">
						<div class="imeeting_history_contentHeadC"></div>
					</div>
				</div>
				
				<div class="imeeting_history_content">
					<div class="imeeting_history_s_content_title">
						<img src="{%env.fn.formatUrl(data[i].hostHeadUrl)%}" class="author_img">
						<ul class="title_txt">
							<li>
								<span><bean:message bundle="km-imeeting" key="kmImeetingMain.fdHost"/>：{%content.fdHostName%}</span>
							</li>
						</ul>
						<span style="display: block">${lfn:message('km-imeeting:kmImeetingMain.fdDate') }：{%content.date%}</span>
						<span style="line-height: 20px;">${lfn:message('km-imeeting:kmImeetingMain.fdPlace') }：{%content.fdPlace%}</span>
					</div>
					<div class="imeeting_history_s_content_title" style="border-bottom: 0px;padding: 0px;">
						<img src="{%env.fn.formatUrl(data[i].optPersonHeadUrl)%}" class="author_img">
						<ul class="title_txt">
							<li>
								<span><bean:message bundle="km-imeeting" key="kmImeetingMainHistory.fdOptPerson"/>：{%data[i].optPersonName%}</span>
							</li>
						</ul>
					</div>
					<div class="imeeting_history_s_content_txt"></div>
				</div>
				
				<div class="imeeting_history_contentFootL">
					<div class="imeeting_history_contentFootR">
						<div class="imeeting_history_contentFootC"></div>
					</div>
				</div>
				
			</div>
		$}
	}
	<%-- 纪要会议--%>
	if(opt=='06'){
		{$
			<div class="imeeting_history_head">
				<span class="imeeting_history_time">{%date.substring(11)%}</span>
				<span class="imeeting_history_line"></span>
				<span class="imeeting_history_title icon_meeting_summary" >
					${lfn:message('km-imeeting:enumeration_km_imeeting_main_history_fd_opt_type_summary')}
				</span>
			</div>
		$}
		
		{$
			<div class="imeeting_history_contentWrap">
				<i class="imeeting_history_arrow M_arr"></i>
				<i class="imeeting_history_arrow C_arr"></i>
				
				<div class="imeeting_history_contentHeadL">
					<div class="imeeting_history_contentHeadR">
						<div class="imeeting_history_contentHeadC"></div>
					</div>
				</div>
				
				<div class="imeeting_history_content">
					<div class="imeeting_history_s_content_title">
						<img src="{%env.fn.formatUrl(data[i].optPersonHeadUrl)%}" class="author_img">
						<ul class="title_txt">
							<li>
								<span>${lfn:message('km-imeeting:kmImeetingSummary.docCreator')}：{%data[i].optPersonName%}</span>
							</li>
						</ul>
					</div>
					<div class="imeeting_history_s_content_txt">
						${lfn:message('km-imeeting:kmImeetingSummary.fdHoldDate')}：{%content.date%}<br/>
						${lfn:message('km-imeeting:kmImeetingSummary.fdActualAttendPersons')}：{%content.attendPersonNames%}
					</div>
				</div>
				
				<div class="imeeting_history_contentFootL">
					<div class="imeeting_history_contentFootR">
						<div class="imeeting_history_contentFootC"></div>
					</div>
				</div>
				
			</div>
		$}
	}
	
	tmpDate=date;
}
{$</div>$}
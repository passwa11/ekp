<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil" %>
<%@page import="java.util.List" %>
<%@page import="java.util.ArrayList" %>
<template:include file="/third/weixin/chatdata/template/module/iframe.jsp">
	<template:replace name="title">
		<c:out value="企业微信会话归档"></c:out>
	</template:replace>

	<template:replace name="head">
		<link rel="stylesheet" href="css/common.css">
		<link rel="stylesheet" href="css/template.css">
		<script src="js/jquery-1.12.4.min.js"></script>
		<script src="js/template.js"></script>
		<script src="js/BenzAMRRecorder.js"></script>
		<script>Com_IncludeFile('calendar.js');</script>
	</template:replace>
	<template:replace name="content">
		<div class="lui_chattingRecords_main_body">
		<list:criteria id="criteria1">
			<list:cri-ref key="fdContent" ref="criterion.sys.docSubject" title="${lfn:message('third-weixin:thirdWeixinChatDataMain.fdContent')}" />
			<list:cri-ref key="fdChatGroupName" ref="criterion.sys.docSubject" title="${lfn:message('third-weixin:thirdWeixinChatGroup.fdMember')}" />
			<list:cri-auto expand="true" modelName="com.landray.kmss.third.weixin.model.ThirdWeixinChatGroup" title="会话时间"
						   property="msgTime"/>
			<list:cri-auto expand="true" modelName="com.landray.kmss.third.weixin.model.ThirdWeixinChatGroup" title="组织架构"
						   property="relateOrg"/>
		</list:criteria>
		<div class="lui_chattingRecords_main_container">
		<div class="lui_chattingRecords_left_content">

			 <list:listview >
				 <ui:source type="AjaxJson">
					 {url:appendQueryParameter('/third/weixin/third_weixin_chat_group/thirdWeixinChatGroup.do?method=data&rowsize=10&ordertype=down&orderby=fdNewestMsgTime')}
				 </ui:source>


				 <div data-lui-type="third/weixin/chatdata/js/chatgroup!ChatGroup" style="display:none;">

					 <div data-lui-type="lui/view/layout!Template" style="display:none;min-height: 200px;">
						 <script type="text/config">
						{
							"kind": "listview",
							"css": "/theme!listview"
						}
						</script>

					 </div>

					 <div data-lui-type="lui/listview/template!Template" style="display:none;">
						 <script type='text/code'>
{$
					 <li class="lui_chattingRecords_item" onclick="showGroupChatData('{%chatgroup['fdId']%}','{%chatgroup['fdNewestMsgId']%}','{%chatgroup['msgSeq']%}','{%chatgroup['fdChatGroupName']%}','{%chatgroup['groupNameImage']%}')">
						 <p class="lui_chattingRecords_item_profile">
							 {%chatgroup['groupNameImage']%}
						 </p>
						 <div class="lui_chattingRecords_item_content">
							 <div class="lui_chattingRecords_item_header clearfix">
								 <div class="lui_chattingRecords_item_name">
									 {%chatgroup['fdChatGroupName']%}
								 </div>
								 <div class="lui_chattingRecords_item_label" style="display:none;">
									 内部
								 </div>
								 <div class="lui_chattingRecords_item_update_time">
									 {%chatgroup['newestMsgTime']%}
								 </div>
							 </div>
							 <div class="lui_chattingRecords_item_chatcontent">
								 <span class="lui_chattingRecords_item_num"></span>
								 <div class="lui_chattingRecords_item_chatcontent_detail">
								 	{%chatgroup['newestMsg']%}
							     </div>
							 </div>
						 </div>
					 </li>
					 $}
</script>

					 </div>
				 </div>
				 <ul id="ul_datas" class="lui_chattingRecords_viewlist clearfix">
				 </ul>
	         </list:listview>
		</ul>
		</div>
			<div class="lui_chattingRecords_mask"></div>

		</div>

		<list:paging ></list:paging>


		<div class="lui_chattingRecords_mask_content">
		<div class="lui_chattingRecords_mask_title clearfix">
			<p class="lui_chattingRecords_item_profile">群名</p>
			<div class="lui_chattingRecords_item_name">
				群名称
			</div>
			<span class="lui_chattingRecords_mask_close"></span>
		</div>
		<div class="lui_chattingRecords_mask_filter clearfix">
			<div class="lui_chattingRecords_mask_inputbar">
				<input id="searchText" type="text" id="searchText" placeholder="搜索">
				<span class="lui_chattingRecords_mask_searchbar_icon" onclick="doSearch()"></span>
			</div>
			<div class="lui_chattingRecords_mask_datetime">
				<xform:datetime htmlElementProperties="id='startTime' autocomplete='off'" placeholder="开始时间" property="startTime" dateTimeType="date" showStatus="edit"></xform:datetime>
				<span>~</span>
                <xform:datetime htmlElementProperties="id='endTime' autocomplete='off'" placeholder="结束时间" property="endTime" dateTimeType="date" showStatus="edit"></xform:datetime>
            </div>

		</div>
		<ul class="lui_chattingRecords_detail">
			<li id="li_all" class="active" onclick="changeMsgType(this,'all')">全部</li>
			<li id="li_file" onclick="changeMsgType(this,'file')">文件</li>
			<li id="li_image" onclick="changeMsgType(this,'image')">图片和视频</li>
			<li id="li_link" onclick="changeMsgType(this,'link')">链接</li>

		</ul>
			<div class="lui_chattingRecords_detail_content">

			</div>
		</div>
		<script>
			var listOption = {
				contextPath: '${LUI_ContextPath}',
				jPath: 'chatdata_main',
				modelName: 'com.landray.kmss.third.weixin.model.ThirdWeixinChatDataMain',
				templateName: '',
				basePath: '/third/weixin/third_weixin_chat_group/thirdWeixinChatGroup.do',
				canDelete: 'false',
				mode: '',
				templateService: '',
				customOpts: {
					____fork__: 0
				}
			};

			Com_IncludeFile("list.js", "${LUI_ContextPath}/third/weixin/resource/js/", 'js', true);

			seajs.use(['lui/jquery','lui/dialog','lui/topic'], function($, dialog , topic) {

				window.init = function (){
					$(".lui_chattingRecords_mask_close").on("click", function(){
						$(".lui_chattingRecords_mask").hide();
						$(".lui_chattingRecords_mask_content").css("transform","translateX(100%)");
					});
				}

				window.toPreview = function (docId) {
					window.open(listOption.contextPath + listOption.basePath + "?method=toPreview&fdId="+docId,"_blank");
				};

				window.toView = function (docId) {
					window.open(listOption.contextPath + listOption.basePath + "?method=view&fdId="+docId,"_blank");
				};

				var currentGroup = null;
				var hasPre = true;
				var hasNext = true;
				var loading = false;
				var currentMsgType = "all";

				window.doSearch = function(){
					showNextPageGroupChatData(currentGroup,"init", currentMsgType);
				}

				window.changeMsgType = function(thisLi,msgType) {
					debugger;
					if(msgType==currentMsgType){
						return;
					}
					if(!$(thisLi).hasClass("active")){
						$(thisLi).addClass("active").siblings().removeClass("active");
					}
					showNextPageGroupChatData(currentGroup,"init", msgType);
				};

				window.showGroupChatData = function(groupId, msgId, currentMsgSeq, groupName, groupNameImage) {
					debugger;
					if(loading==true){
						return;
					}

					if(!currentMsgSeq){
						alert("当前会话没有聊天记录");
						return;
					}
					$(".lui_chattingRecords_mask_content .lui_chattingRecords_mask_title .lui_chattingRecords_item_profile").text(groupNameImage);
					$(".lui_chattingRecords_mask_content .lui_chattingRecords_mask_title .lui_chattingRecords_item_name").text(groupName);

					var getDataUrl = '<c:url value="/third/weixin/third_weixin_chat_data_main/thirdWeixinChatDataMain.do?method=listChatData"/>'+"&groupId="+groupId+"&currentMsgSeq="+currentMsgSeq;
					loading = true;
					$.ajax({
						url: getDataUrl,
						type: 'GET',
						data:{},
						dataType: 'json',
						error: function(data){
							dialog.result(JOSN.stringify(data));
							loading = false;
						},
						success: function(data){
							var isSuccess = data.success;
							if(isSuccess==true){
								currentMsgType = "all";
								currentGroup = groupId;
								hasPre = true;
								hasNext = true;
								var datas = data.datas;
								hasPre = data.hasMore;

								if(!$("#li_all").hasClass("active")){
									$("#li_all").addClass("active").siblings().removeClass("active");
								}

								$(".lui_chattingRecords_detail_content").html("");

								for(var i=datas.length;i>0;i--){
									var dataObj = datas[i-1];
									var div_out = buildChatDataRecord(dataObj);
									if(div_out){
										$(".lui_chattingRecords_detail_content").append($(div_out));
									}
								}
								var $objDiv = $("#div_"+currentMsgSeq); //找到要定位的地方
								var objDiv = $objDiv[0]; //转化为dom对象
								$(".lui_chattingRecords_detail_content").animate({scrollTop:objDiv.offsetTop},"fast");
							}else{
								alert("获取聊天记录发生错误，错误信息："+data.errorMsg);
							}
							loading = false;
						}
					});
				};

				window.showNextPageGroupChatData = function(groupId, direction, msgType) {
					debugger;
					if(loading==true){
						return;
					}
					var searchText = $("#searchText").val();
					var startTime = $("input[name='startTime']").val();
					var endTime = $("input[name='endTime']").val();
					if(!msgType){
						msgType = "";
					}
					var getDataUrl = '<c:url value="/third/weixin/third_weixin_chat_data_main/thirdWeixinChatDataMain.do?method=listChatData"/>'+"&groupId="+groupId+"&searchText="+searchText+"&startTime="+startTime+"&endTime="+endTime+"&msgType="+msgType;
					if(direction=="pre"){
						if(hasPre==false){
							return;
						}
						var startMsgSeq = $(".lui_chattingRecords_detail_content").children(":first").attr("seq");
						getDataUrl+="&startMsgSeq="+startMsgSeq;
					}else if(direction=="next"){
						if(hasNext==false){
							return;
						}
						var endMsgSeq = $(".lui_chattingRecords_detail_content").children(":last").attr("seq");
						getDataUrl+="&endMsgSeq="+endMsgSeq;
					}
					loading = true;

					$.ajax({
						url: getDataUrl,
						type: 'GET',
						data:{},
						dataType: 'json',
						error: function(data){
							dialog.result(JOSN.stringify(data));
							loading = false;
						},
						success: function(data){
							debugger;
							var isSuccess = data.success;
							if(isSuccess==true){
								if(msgType){
									currentMsgType = msgType;
								}
								if(direction=="init"){
									$(".lui_chattingRecords_detail_content").html("");
								}
								var datas = data.datas;
								if(direction=="pre" || direction=="init") {
									hasPre = data.hasMore;
								}else{
									hasNext = data.hasMore;
								}
								for(var i=0;i<datas.length;i++){
									var dataObj = datas[i];
									var div_out = buildChatDataRecord(dataObj);
									if(div_out){
										if(direction=="pre" || direction=="init") {
											$(".lui_chattingRecords_detail_content").prepend($(div_out));
										}else {
											$(".lui_chattingRecords_detail_content").append($(div_out));
										}
									}
								}
							}else{
								alert("获取聊天记录发生错误，错误信息："+data.errorMsg);
							}
							loading = false;
						}
					});
				};

				window.playVoice = function(url){
					var amr = new BenzAMRRecorder();
					amr.initWithUrl(url).then(function() {
						amr.play();
					});
				};

				window.buildChatDataRecord = function(chatDataObj){
					var msgType = chatDataObj.fdMsgType;
					var div_out;
					if(msgType=="text"){
						div_out = $('<div class="lui_chattingRecords_detail_item">    <p class="lui_chattingRecords_item_profile"></p>    <div class="lui_chattingRecords_item_content">        <div class="lui_chattingRecords_item_header clearfix">            <div class="lui_chattingRecords_item_name">            </div>            <div class="lui_chattingRecords_item_update_time">            </div>        </div>        <div class="lui_chattingRecords_item_type_textcontent clearfix">            <div class="lui_chattingRecords_item_type_text">             </div>        </div>    </div></div>');
						$(div_out).find(".lui_chattingRecords_item_type_text").text(chatDataObj.fdContent);
					}else if(msgType=="link"){
						div_out = $('<div class="lui_chattingRecords_detail_item"><p class="lui_chattingRecords_item_profile"></p><div class="lui_chattingRecords_item_content"><div class="lui_chattingRecords_item_header clearfix"><div class="lui_chattingRecords_item_name"></div><span class="lui_chattingRecords_item_suf"></span><div class="lui_chattingRecords_item_update_time"></div></div><div class="lui_chattingRecords_item_type_url"><div class="lui_chattingRecords_item_type_url_top clearfix"><i class="lui_chattingRecords_item_url_icon"></i><div class="lui_chattingRecords_item_type_url_right"><div class="lui_chattingRecords_item_type_url_title"></div><div class="lui_chattingRecords_item_type_url_add">   </div></div></div></div></div></div>');
						$(div_out).find(".lui_chattingRecords_item_type_url_title").text(chatDataObj.fdContent);
						$(div_out).find(".lui_chattingRecords_item_type_url_add").text(chatDataObj.fdLinkUrl);
					}else if(msgType=="image" || msgType=="emotion"){
						div_out = $('<div class="lui_chattingRecords_detail_item"><p class="lui_chattingRecords_item_profile"></p><div class="lui_chattingRecords_item_content">    <div class="lui_chattingRecords_item_header clearfix">   <div class="lui_chattingRecords_item_name"></div>   <div class="lui_chattingRecords_item_update_time">  2021/01/12 10:00   </div>    </div>    <div class="lui_chattingRecords_item_type_textcontent clearfix">   <div class="lui_chattingRecords_item_type_image_content">  <img class="lui_chattingRecords_item_type_image">   </div>    </div></div> </div>');
						var url = '<c:url value="/sys/attachment/sys_att_main/sysAttMain.do?method=readDownload&fdId=" />'+chatDataObj.attId;
						$(div_out).find(".lui_chattingRecords_item_type_image").attr("src", url);
					}else if(msgType=="video"){
						div_out = $('<div class="lui_chattingRecords_detail_item"><p class="lui_chattingRecords_item_profile"></p><div class="lui_chattingRecords_item_content">    <div class="lui_chattingRecords_item_header clearfix">   <div class="lui_chattingRecords_item_name"></div>   <span class="lui_chattingRecords_item_suf"></span>   <div class="lui_chattingRecords_item_update_time"></div>    </div>    <div class="lui_chattingRecords_item_type_textcontent clearfix">   <video width="168" height="128" controls></video>    </div></div> </div>');
						var url = '<c:url value="/sys/attachment/sys_att_main/sysAttMain.do?method=readDownload&fdId=" />'+chatDataObj.attId;
						$(div_out).find("video").attr("src", url);
					}else if(msgType=="voice"){
						var url = '<c:url value="/sys/attachment/sys_att_main/sysAttMain.do?method=readDownload&fdId=" />'+chatDataObj.attId;
						div_out = $('<div class="lui_chattingRecords_detail_item"><p class="lui_chattingRecords_item_profile"></p><div class="lui_chattingRecords_item_content">    <div class="lui_chattingRecords_item_header clearfix">   <div class="lui_chattingRecords_item_name"></div>   <span class="lui_chattingRecords_item_suf"></span>   <div class="lui_chattingRecords_item_update_time"></div>    </div>    <div class="lui_chattingRecords_item_type_textcontent clearfix">  <input type="button" name="button_voice_play" value="播放语音" />    </div></div> </div>');
						$(div_out).find("input[name='button_voice_play']").on('click', function(){playVoice(url)});
					}else if(msgType=="voice2"){
						var url = '<c:url value="/sys/attachment/sys_att_main/sysAttMain.do?method=readDownload&fdId=" />'+chatDataObj.attId;
						div_out = $('<div class="lui_chattingRecords_detail_item"><p class="lui_chattingRecords_item_profile"></p><div class="lui_chattingRecords_item_content">    <div class="lui_chattingRecords_item_header clearfix">   <div class="lui_chattingRecords_item_name"></div>   <span class="lui_chattingRecords_item_suf"></span>   <div class="lui_chattingRecords_item_update_time"></div>    </div>    <div class="lui_chattingRecords_item_type_textcontent clearfix">   <audio controls="controls">您的浏览器不支持 audio 标签</audio>    </div></div> </div>');
						$(div_out).find("audio").attr("src", url);
					}else if(msgType=="revoke"){
						div_out = $('<div><div name="time_info" class="lui_chattingRecords_detail_item_time_info"></div>  <div name="content_info" class="lui_chattingRecords_detail_item_time_info"> </div></div>');
						$(div_out).find("div[name='content_info']").text(chatDataObj.fdFromName+"撤回了一条消息");
						$(div_out).find("div[name='time_info']").text(chatDataObj.fdMsgTime);
					}else if(msgType=="weapp"){
						div_out = $('<div class="lui_chattingRecords_detail_item"><p class="lui_chattingRecords_item_profile"></p><div class="lui_chattingRecords_item_content"><div class="lui_chattingRecords_item_header clearfix"><div class="lui_chattingRecords_item_name"></div><div class="lui_chattingRecords_item_update_time"></div></div><div class="lui_chattingRecords_item_type_url"><div class="lui_chattingRecords_item_type_url_top clearfix"><i class="lui_chattingRecords_item_url_icon"></i><div class="lui_chattingRecords_item_type_url_right"><div class="lui_chattingRecords_item_type_url_title"></div><div class="lui_chattingRecords_item_type_url_add"></div></div></div><div class="lui_chattingRecords_item_type_url_source"><i class="lui_chattingRecords_item_type_source_icon"></i><span>小程序</span></div></div></div></div>');
						$(div_out).find(".lui_chattingRecords_item_type_url_title").text(chatDataObj.fdTitle);
						$(div_out).find(".lui_chattingRecords_item_type_url_add").text(chatDataObj.fdContent);
					}else if(msgType=="agree"){
						div_out = $('<divname="time_info" class="lui_chattingRecords_detail_item_time_info"></div><div name="content_info" class="lui_chattingRecords_detail_item_time_info">对方不同意存档会话内容，你将无法继续提供服务</div>');
						$(div_out).find("div[name='content_info']").text(chatDataObj.fdFromName+" 同意存档会话内容，你可以继续提供服务");
						$(div_out).find("div[name='time_info']").text(chatDataObj.fdMsgTime);
					}else if(msgType=="disagree"){
						div_out = $('<divname="time_info" class="lui_chattingRecords_detail_item_time_info"></div><div name="content_info" class="lui_chattingRecords_detail_item_time_info">对方不同意存档会话内容，你将无法继续提供服务</div>');
						$(div_out).find("div[name='content_info']").text(chatDataObj.fdFromName+" 不同意存档会话内容，你将无法继续提供服务");
						$(div_out).find("div[name='time_info']").text(chatDataObj.fdMsgTime);
					}else if(msgType=="card"){
						div_out = $('<div class="lui_chattingRecords_detail_item"><p class="lui_chattingRecords_item_profile_inner"></p><div class="lui_chattingRecords_item_content"><div class="lui_chattingRecords_item_header clearfix"><div class="lui_chattingRecords_item_name"></div><div class="lui_chattingRecords_item_update_time">2021/01/12 10:00</div></div><div class="lui_chattingRecords_item_type_card"><div class="lui_chattingRecords_item_type_card_top clearfix"><p class="lui_chattingRecords_item_profile"></p><div class="lui_chattingRecords_item_type_card_content"><strong></strong><i></i><p></p></div></div><div class="lui_chattingRecords_item_type_card_source"><span>个人名片</span></div></div></div></div>');
						$(div_out).find(".lui_chattingRecords_item_profile_inner").text(chatDataObj.fdUsernameImage);
						$(div_out).find(".lui_chattingRecords_item_type_card_content strong").text(chatDataObj.fdUserId);
						$(div_out).find(".lui_chattingRecords_item_type_card_content i").text(chatDataObj.fdCorpName);
						$(div_out).find(".lui_chattingRecords_item_type_card_content p").text(chatDataObj.fdUsername);
					}else if(msgType=="chatrecord"){
						div_out = $('<div class="lui_chattingRecords_detail_item"><p class="lui_chattingRecords_item_profile"></p><div class="lui_chattingRecords_item_content"><div class="lui_chattingRecords_item_header clearfix"><div class="lui_chattingRecords_item_name"></div><div class="lui_chattingRecords_item_update_time">   </div></div><div class="lui_chattingRecords_item_type_chatrecords"><div class="lui_chattingRecords_item_type_chatrecords_title"></div><div class="lui_chattingRecords_item_type_chatrecords_content"></div></div></div></div>');
                        $(div_out).find(".lui_chattingRecords_item_type_chatrecords_title").text(chatDataObj.fdTitle+"的聊天记录");
						var extendContent = chatDataObj.fdExtendContent;
						if(extendContent){
							var items = JSON.parse(extendContent).item;
							for(var j=0;j<items.length;j++){
								var item = items[j];
								var type = item.type;
								var innerContent = item.content;
								if(!innerContent){
									continue;
								}
								debugger;
								var innerContentObj = JSON.parse(innerContent);
								if(type=="ChatRecordText"){
									$(div_out).find(".lui_chattingRecords_item_type_chatrecords_content").append("<span>"+innerContentObj.content+"</span>");
								}else if(type=="ChatRecordImage"){
									var url = '<c:url value="/sys/attachment/sys_att_main/sysAttMain.do?method=readDownload&fdId=" />'+innerContentObj.attId;
									$(div_out).find(".lui_chattingRecords_item_type_chatrecords_content").append("<span><img src='"+url+"' class='lui_chattingRecords_item_type_image'/></span>");
								}else if(type=="ChatRecordFile"){
									var url = '<c:url value="/sys/attachment/sys_att_main/sysAttMain.do?method=readDownload&fdId=" />'+innerContentObj.attId;
									var filename = innerContentObj.filename;
									$(div_out).find(".lui_chattingRecords_item_type_chatrecords_content").append("<span>"+"<a href='"+url+"' target='_blank'>"+filename+"</a>"+"</span>");
								}else{
									$(div_out).find(".lui_chattingRecords_item_type_chatrecords_content").append("<span>"+item.content+"</span>");
								}
							}
						}
					}else if(msgType=="location"){
						div_out = $('<div class="lui_chattingRecords_detail_item clearfix"><p class="lui_chattingRecords_item_profile"></p><div class="lui_chattingRecords_item_content"><div class="lui_chattingRecords_item_header"><div class="lui_chattingRecords_item_name"></div><div class="lui_chattingRecords_item_update_time"></div></div><div class="lui_chattingRecords_item_type_location clearfix"><div class="lui_chattingRecords_item_type_location_top clearfix"><p class="lui_chattingRecords_item_app"></p><div class="lui_chattingRecords_item_type_location_content"><strong></strong><p></p></div></div><div class="lui_chattingRecords_item_type_location_source"><i class="lui_chattingRecords_item_app_icon"></i><span>高德地图</span></div></div></div></div>');
						$(div_out).find(".lui_chattingRecords_item_type_location_content strong").text(chatDataObj.fdContent);
						$(div_out).find(".lui_chattingRecords_item_type_location_content p").text(chatDataObj.fdTitle);
					}else if(msgType=="calendar"){
						div_out = $('<div class="lui_chattingRecords_detail_item clearfix"><p class="lui_chattingRecords_item_profile"></p><div class="lui_chattingRecords_item_content"><div class="lui_chattingRecords_item_header"><div class="lui_chattingRecords_item_name"></div><div class="lui_chattingRecords_item_update_time"></div></div><div class="lui_chattingRecords_item_type_schedule clearfix"><div class="lui_chattingRecords_item_type_schedule_top clearfix"><div class="lui_chattingRecords_item_type_schedule_title"></div><div class="lui_chattingRecords_item_type_schedule_add"></div><div class="lui_chattingRecords_item_type_schedule_add"></div><div class="lui_chattingRecords_item_type_schedule_source"><i class="lui_chattingRecords_item_type_schedule_icon"></i><span>日程</span></div></div></div></div></div>');
						$(div_out).find(".lui_chattingRecords_item_type_schedule_title").text(chatDataObj.fdTitle);
						var extendContent = chatDataObj.fdExtendContent;
						if(extendContent) {
							var calendar = JSON.parse(extendContent);
							$(div_out).find(".lui_chattingRecords_item_type_schedule_add").text(calendar.time);
							$(div_out).find(".lui_chattingRecords_item_type_schedule_add").text(calendar.place);
						}
					}else if(msgType=="redpacket" || msgType=="external_redpacket"){
						div_out = $('<div class="lui_chattingRecords_detail_item clearfix"><p class="lui_chattingRecords_item_profile"></p><div class="lui_chattingRecords_item_content"><div class="lui_chattingRecords_item_header"><div class="lui_chattingRecords_item_name"></div><div class="lui_chattingRecords_item_update_time"></div></div><div class="lui_chattingRecords_item_type_redpacket clearfix"><div class="lui_chattingRecords_item_type_redpacket_top"><div class="lui_chattingRecords_item_type_redpacket_icon"></div><div class="lui_chattingRecords_item_type_redpacket_content"><div class="lui_chattingRecords_item_type_redpacket_title"></div><div class="lui_chattingRecords_item_type_redpacket_detail"><p>领取红包</p><i></i><span></span></div></div></div><div class="lui_chattingRecords_item_type_redpacket_source">微信红包</div></div></div></div>');
						var extendContent = chatDataObj.fdExtendContent;
						if(extendContent) {
							var redpacket = JSON.parse(extendContent);
							$(div_out).find(".lui_chattingRecords_item_type_redpacket_title").text(redpacket.wish);
							$(div_out).find(".lui_chattingRecords_item_type_redpacket_detail i").text(redpacket.totalcnt);
							$(div_out).find(".lui_chattingRecords_item_type_redpacket_detail span").text("共"+(redpacket.totalamount/100)+"元");
						}
					}else if(msgType=="todo"){
						div_out = $('<div class="lui_chattingRecords_detail_item clearfix"><p class="lui_chattingRecords_item_profile"></p><div class="lui_chattingRecords_item_content"><div class="lui_chattingRecords_item_header"><div class="lui_chattingRecords_item_name"></div><div class="lui_chattingRecords_item_update_time"></div></div><div class="lui_chattingRecords_item_type_todo clearfix"><div class="lui_chattingRecords_item_type_todo_top"><div class="lui_chattingRecords_item_type_todo_title"><i class="lui_chattingRecords_item_type_todo_icon"></i><span></span></div><div class="lui_chattingRecords_item_type_todo_content"></div><div class="lui_chattingRecords_item_type_todo_source"></div></div></div></div></div>');
							$(div_out).find(".lui_chattingRecords_item_type_todo_title span").text(chatDataObj.fdTitle);
							$(div_out).find(".lui_chattingRecords_item_type_todo_content").text(chatDataObj.fdContent);
					}else if(msgType=="vote"){
						div_out = $('<div class="lui_chattingRecords_detail_item clearfix"><p class="lui_chattingRecords_item_profile"></p><div class="lui_chattingRecords_item_content"><div class="lui_chattingRecords_item_header"><div class="lui_chattingRecords_item_name"></div><div class="lui_chattingRecords_item_update_time"></div></div><div class="lui_chattingRecords_item_type_vote clearfix"><div class="lui_chattingRecords_item_type_vote_title"></div><ul class="lui_chattingRecords_item_type_vote_detail"></ul><div class="lui_chattingRecords_item_type_vote_source"><i class="lui_chattingRecords_item_type_vote_icon"></i><span>投票</span></div></div></div></div>');
						var extendContent = chatDataObj.fdExtendContent;
						if(extendContent) {
							var vote = JSON.parse(extendContent);
							$(div_out).find(".lui_chattingRecords_item_type_vote_title").text(vote.votetitle);
							var voteitem = vote.vote.voteitem;
							if(voteitem){
								for(var j=0;j<voteitem.length;j++){
									$(div_out).find(".lui_chattingRecords_item_type_vote_detail").append($('<li><i class="lui_chattingRecords_item_type_vote_checkbox"></i><span>'+voteitem[j]+'</span></li>'));
								}
							}
						}
					}else if(msgType=="markdown"){
						div_out = $('<div class="lui_chattingRecords_detail_item">    <p class="lui_chattingRecords_item_profile"></p>    <div class="lui_chattingRecords_item_content">        <div class="lui_chattingRecords_item_header clearfix">            <div class="lui_chattingRecords_item_name">            </div>            <div class="lui_chattingRecords_item_update_time">            </div>        </div>        <div class="lui_chattingRecords_item_type_textcontent clearfix">            <div class="lui_chattingRecords_item_type_text">             </div>        </div>    </div></div>');
						var extendContent = chatDataObj.fdExtendContent;
						if(extendContent) {
							var extendContentObj = JSON.parse(extendContent);
							if(extendContentObj.content){
								$(div_out).find(".lui_chattingRecords_item_type_text").html(extendContentObj.content);
							}
						}
					}else if(msgType=="news"){
						div_out = $('<div class="lui_chattingRecords_detail_item clearfix"><p class="lui_chattingRecords_item_profile"></p><div class="lui_chattingRecords_item_content"><div class="lui_chattingRecords_item_header"><div class="lui_chattingRecords_item_update_time"></div></div><div class="lui_chattingRecords_item_type_videoaccount clearfix"><image src="images/movie.mp4" width="168" height="128" controls=""></image><div class="lui_chattingRecords_item_type_videoaccount_bottom"><div class="lui_chattingRecords_item_type_videoaccount_title clearfix"><img src="images/lui_chattingRecords_add.svg" alt=""><span></span></div><div class="lui_chattingRecords_item_type_videoaccount_intro"></div></div></div></div></div>');
						var extendContent = chatDataObj.fdExtendContent;
						if(extendContent) {
							var extendContentObj = JSON.parse(extendContent);
							if(extendContentObj.item){
								var items = extendContentObj.item;
								if(items.length>0){
									$(div_out).find(".lui_chattingRecords_item_type_videoaccount image").attr("src",items[0].picurl);
									$(div_out).find(".lui_chattingRecords_item_type_videoaccount_title span").text("src",items[0].title);
									$(div_out).find(".lui_chattingRecords_item_type_videoaccount_intro").text("src",items[0].description);
								}
							}
						}
					}else if(msgType=="mixed"){
						div_out = $('<div class="lui_chattingRecords_detail_item"><p class="lui_chattingRecords_item_profile"></p><div class="lui_chattingRecords_item_content"><div class="lui_chattingRecords_item_header clearfix"><div class="lui_chattingRecords_item_name"></div><div class="lui_chattingRecords_item_update_time">   </div></div><div class="lui_chattingRecords_item_type_chatrecords"><div class="lui_chattingRecords_item_type_chatrecords_title" style="display: none;"></div><div class="lui_chattingRecords_item_type_chatrecords_content"></div></div></div></div>');
						$(div_out).find(".lui_chattingRecords_item_type_chatrecords_title").text("");
						var extendContent = chatDataObj.fdExtendContent;
						if(extendContent){
							var extendContentObj = JSON.parse(extendContent);
							var items = extendContentObj.item;
							for(var j=0;j<items.length;j++){
								var item = items[j];
								var type = item.type;
								var innerContent = item.content;
								if(!innerContent){
									continue;
								}
								var innerContentObj = JSON.parse(innerContent);
								if(type=="text"){
									$(div_out).find(".lui_chattingRecords_item_type_chatrecords_content").append("<span>"+innerContentObj.content+"</span>");
								}else if(type=="image" || type=="emotion"){
									var url = '<c:url value="/sys/attachment/sys_att_main/sysAttMain.do?method=readDownload&fdId=" />'+innerContentObj.attId;
									$(div_out).find(".lui_chattingRecords_item_type_chatrecords_content").append("<span><img src='"+url+"' class='lui_chattingRecords_item_type_image'/></span>");
								}else if(type=="file"){
									var url = '<c:url value="/sys/attachment/sys_att_main/sysAttMain.do?method=readDownload&fdId=" />'+innerContentObj.attId;
									var filename = innerContentObj.filename;
									$(div_out).find(".lui_chattingRecords_item_type_chatrecords_content").append("<span>"+"<a href='"+url+"' target='_blank'>"+filename+"</a>"+"</span>");
								}else{
									$(div_out).find(".lui_chattingRecords_item_type_chatrecords_content").append("<span>"+item.content+"</span>");
								}
							}
						}
					}else if(msgType=="file"){
						div_out = $('<div class="lui_chattingRecords_detail_item">    <p class="lui_chattingRecords_item_profile"></p>    <div class="lui_chattingRecords_item_content">        <div class="lui_chattingRecords_item_header clearfix">            <div class="lui_chattingRecords_item_name">            </div>            <div class="lui_chattingRecords_item_update_time">            </div>        </div>        <div class="lui_chattingRecords_item_type_textcontent clearfix">            <div class="lui_chattingRecords_item_type_text">             </div>        </div>    </div></div>');
						var url = '<c:url value="/sys/attachment/sys_att_main/sysAttMain.do?method=readDownload&fdId=" />'+chatDataObj.attId;
						var filename = chatDataObj.fdTitle;
						if(!filename || filename=='undefined'){
							filename = "语音文件";
						}
						$(div_out).find(".lui_chattingRecords_item_type_text").append("<a target='_blank' href='"+url+"'>"+filename+"</a>");
					}
					if(div_out){
						$(div_out).attr("id","div_"+chatDataObj.fdSeq);
						$(div_out).attr("seq",chatDataObj.fdSeq);
						$(div_out).find(".lui_chattingRecords_item_profile").text(chatDataObj.fdFromNameImage);
						$(div_out).find(".lui_chattingRecords_item_name").text(chatDataObj.fdFromName);
						$(div_out).find(".lui_chattingRecords_item_update_time").text(chatDataObj.fdMsgTime);
						return div_out;
					}
					return null;
				}

				$(".lui_chattingRecords_detail_content").scroll(function(){
					var $this =$(this),
							viewH =$(this).height(),//可见高度
							contentH =$(this).get(0).scrollHeight,//内容高度
							scrollTop =$(this).scrollTop();//滚动高度

					if(scrollTop/(contentH -viewH) >= 0.96){ //当滚动到距离底部5%时
						//alert(viewH+"---"+contentH+"---"+scrollTop);
						//alert("到底部了2");
						showNextPageGroupChatData(currentGroup,"next");
					}else if(scrollTop<200){
						//alert(scrollTop);
						showNextPageGroupChatData(currentGroup,"pre");
					}
				});

				window.triggerSearch = function(){
					if(event.keyCode ==13){
						$(".lui_chattingRecords_mask_searchbar_icon").trigger("click");
					}
				};

				$("#searchText").keyup(function(event){
					triggerSearch();
				});
				$("#startTime").keyup(function(event){
					triggerSearch();
				});
				$("#endTime").keyup(function(event){
					triggerSearch();
				});

				init();
			});

		</script>
	</template:replace>
</template:include>
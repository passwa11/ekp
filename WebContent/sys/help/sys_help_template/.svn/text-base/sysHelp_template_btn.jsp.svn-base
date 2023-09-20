<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<kmss:authShow roles="ROLE_SYSHELP_DEFAULT">
<c:if test="${ sysHelpIsLoad != 'true' }">
	<style>
		.helpImg{
			width: 40px !important;
		    height: 40px !important;
		    margin: 4px !important;
		}
		.sysHelpBtnFixed{
			visibility: visible !important;
		}

		.sysHelpBtnDiv{
			width: 56px;
			height: 56px;
			background: #FFFFFF;
			border: 1px solid #EEEEEE;
			box-shadow: 0px 3px 6px 0px rgba(153,153,153,0.1);
			border-radius: 8px;
			text-align: center;
			padding-top: 14px;
			font-size:12px;
			box-sizing: border-box;
		}

		.sysHelpBtnUl{
			width: 106px;
			position: absolute;
			left: -110px;
			bottom: 30px;
			background-color: transparent;
			border-radius: 8px;
			padding-right: 20px;
		}
		.sysHelpBtnUl>li{
			height: 56px;
			line-height: 56px;
			border: 1px solid #EEEEEE;
			margin:0 0 -1px;
			text-align: center;
			list-style: none;
			background: #fff;
		}
		.sysHelpBtnImg{
			width: 20px;
			height: 20px;
			margin: 0 auto;
			display: block;
		}
		.sysHelpLiImg{
			width: 20px;
			height: 20px;
		}

		.com_goto div.lui-praise-btn{
			border:0;
		}


		.sysHelpBtnUl>li:hover{
			color: #4285F4;
			background: rgb(240, 246, 255);
		}
		.sysHelpBtnUl>li span.li1{
			display: inline-block;
			width: 20px;
			height: 20px;
			background: url(${KMSS_Parameter_ContextPath}/sys/help/sys_help_template/image/buzOverview.png) no-repeat center;
			background-size: contain;
			position: relative;
			top: 5px;
			left: -2px;
		}
		.sysHelpBtnUl>li span.li2{
			display: inline-block;
			width: 20px;
			height: 20px;
			background: url(${KMSS_Parameter_ContextPath}/sys/help/sys_help_template/image/sceneExp.png) no-repeat center;
			background-size: contain;
			position: relative;
			top: 5px;
			left: -2px;
		}
		.sysHelpBtnUl>li span.li3{
			display: inline-block;
			width: 20px;
			height: 20px;
			background: url(${KMSS_Parameter_ContextPath}/sys/help/sys_help_template/image/helpManual.png) no-repeat center;
			background-size: contain;
			position: relative;
			top: 5px;
			left: -2px;
		}
		.sysHelpBtnUl>li:hover span.li1{
			background: url(${KMSS_Parameter_ContextPath}/sys/help/sys_help_template/image/buzOverview_h.png) no-repeat center;
			background-size: contain;
		}
		.sysHelpBtnUl>li:hover span.li2{
			background: url(${KMSS_Parameter_ContextPath}/sys/help/sys_help_template/image/sceneExp_h.png) no-repeat center;
			background-size: contain;
		}
		.sysHelpBtnUl>li:hover span.li3{
			background: url(${KMSS_Parameter_ContextPath}/sys/help/sys_help_template/image/helpManual_h.png) no-repeat center;
			background-size: contain;
		}

	</style>
	<ui:button parentId="top"  styleClass="lui-praise-btn sysHelpBtnFixed" order="9" id="sysHelpBtn" style="display:none">
		<div class="sysHelpBtnDiv">
			<img class="sysHelpBtnImg" src="${LUI_ContextPath}/sys/help/sys_help_template/image/help_icon.png">
			帮助中心
		</div>
		<ul id="sysHelpUl" style="display: none" class="sysHelpBtnUl">
			<li id="fdBuzImg" style="display: none">
				<span class="sysHelpLiImg li1"></span>
				<bean:message bundle='sys-help' key='sysHelpModule.buzOverview' />
			</li>
			<li id="fdScene" style="display: none">
				<span class="sysHelpLiImg li2"></span>
				<bean:message bundle='sys-help' key='sysHelpModule.sceneExp' />
			</li>
			<li id="fdHelpDoc" style="display: none">
				<span class="sysHelpLiImg li3"></span>
				<bean:message bundle='sys-help' key='sysHelpModule.helpManual' />
			</li>
		</ul>
	</ui:button>
	<script>
		var hasShow = false;

		function generateUuid() {
			var s = [];
			var hexDigits = "0123456789abcdef";
			for (var i = 0; i < 36; i++) {
				s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
			}
			s[14] = "4"; // bits 12-15 of the time_hi_and_version field to 0010
			s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1); // bits 6-7 of the clock_seq_hi_and_reserved to 01
			s[8] = s[13] = s[18] = s[23] = "-";
			var uuid = s.join("");
			return uuid;
		}

		function selectInAll(_w,id) {
			if (_w) {
				if (_w.document) {
					var el = _w.document.getElementById(id);
					if (el && $(el).css('display') != 'none') {
						hasShow = true;
						showTop($(el));
						return;
					}
				}
				if (_w.length > 0) {
					for (var i = 0; i < _w.length; i++) {
						selectInAll(_w[i], id);
					}
				}
			}
		}

		//top按钮不显示的场景
		function showTop($el) {
			var interval, intervalCount = 0;
			interval = setInterval(function() {
				var el = $el;
				if (el && el.parent()) {
					el.parent().css("display","block");
				}
				if(intervalCount >= 5) {
					clearInterval(interval);
				}
				intervalCount += 1;
			}, 300);
		}

		if (!top.sysHelp) {
			top.sysHelp = new Array();
		}

		LUI.ready(function(){
			// var uuid = generateUuid();
			// var pointEl = "<input type='hidden' id='" + uuid + "'>";
			// $("#sysHelpBtn").prepend(pointEl);
			$("#sysHelpBtn").hover(function() {
				$("#sysHelpUl").show();
			},function() {
				$("#sysHelpUl").hide();
			});
			seajs.use(['lui/jquery', 'lui/toolbar'], function($, toolbar){
				$.ajax({
					url: '${LUI_ContextPath}/sys/help/sys_help_module/sysHelpModule.do?method=getModuleConfig',
					type: 'post',
					dataType: 'json',
					data: {
						'url': window.location.pathname + window.location.search,
						'_f': "0"
					},
					success: function(data) {
						/**
						 * 如果当前模块已经存在帮助中心，则不执行
						 * 防止出现多个帮助中心
						 */
						var e = top.document.getElementById('sysHelpBtn');
						if (e) {
							if ($(e).css('display') != 'none') {
								hasShow = true;
								showTop($(e));
							}
						}else{
							if (top.frames && top.frames.length > 0) {
								for (var i = 0; i < top.frames.length; i++) {
									var _w = top.frames[i];
									var el = _w.document.getElementById('sysHelpBtn');
									if (el && $(el).css('display') != 'none') {
										hasShow = true;
										showTop($(el));
									}else{
										selectInAll(_w,'sysHelpBtn');
									}
								}
							}
						}
						if (!data || !data.fdModulePath) {
							$("#sysHelpBtn").hide();
							return;
						}
						if(data && data.fdModulePath){
							var hasPath = false;
							for (var i in top.sysHelp) {
								var m = top.sysHelp[i];
								if (m.path == data.fdModulePath) {
									hasPath = true;
									if (hasShow) {
										return;
									}
								}
							}
							showTop($("#sysHelpBtn"));
							var tabL = 0;
							var tabIndex = 0;
							if (data.fdIsOpen == true) {
								tabL++;
								$("#fdHelpDoc").show();
							}
							if (data.fdBuzImgIsOpen == true) {
								tabL++;
								var i = tabIndex;
								$("#fdBuzImg").click(function(){
									openHelpPage(0);
								});
								$("#fdBuzImg").show();
								tabIndex++;
							}
							if (data.fdSceneIsOpen == true) {
								tabL++;
								var i = tabIndex;
								$("#fdScene").click(function(){
									openHelpPage(1);
								});
								$("#fdScene").show();
								tabIndex++;
							}
							if (data.fdIsOpen == true && tabL == 1) {
								$("#sysHelpBtn").click(function(){
									toHelpPage();
								});
							}else{
								var i = tabIndex;
								$("#fdHelpDoc").click(function(){
									openHelpPage(2);
								});
							}
							if (tabL == 0) {
								return;
							}

							if (data.fdIsOpen != true || tabL != 1) {
								$("#sysHelpBtn").hover(function() {
									$("#sysHelpUl").show();
								},function() {
									$("#sysHelpUl").hide();
								});
							}
							if (!hasPath) {
								var module = {'path': data.fdModulePath,'display':$('#sysHelpBtn').css('display')};
								top.sysHelp.push(module);
							}

							// #96935,ie8图标位置错误
							var isAdminMenu =parent.window.location.href.indexOf("sys/profile") >=0;
							// alert("isAdminMenu=>"+isAdminMenu+","+window.location.href);
							if(checkIE8() && isAdminMenu){
								$('#sysHelpBtn').css('float', 'right');
							}

							// #97000 防止出现2个按钮
							var tIsAdminIfm = top.window.location.href.indexOf("management") >=0 ;
							// alert("tIsAdminIfm=>"+tIsAdminIfm);
							// alert("top.window.location.href=>"+top.window.location.href);
							// alert("window.location.href=>"+window.location.href);

                            // 极速模式
                            if (top.window.location.href.indexOf("j_start") >= 0) {
                                if (tIsAdminIfm) {
                                    // console.log("极速模式")
                                    if (parent.parent.window.document.getElementById("sysHelpBtn")) {
                                        var ppbtn = parent.parent.window.document.getElementById("sysHelpBtn");
                                        // console.log("parent.parent=>", parent.parent.window.document);
                                        // console.log("parent.parent btn=>", parent.parent.window.document.getElementById("sysHelpBtn"))
                                        // console.log($(ppbtn).css("display"));
                                        if ($(ppbtn).css("display") == "block") {
                                            // $(ppbtn).css("display","none");
                                            $('#sysHelpBtn').css('display', 'none');
                                            // $('#sysHelpBtn').css('display', 'block');

                                            $(ppbtn).removeAttr("onclick");
											$(ppbtn).removeAttr("hover");
                                            var childUrl = window.location.pathname + window.location.search;
                                            // alert("childUrl=>"+childUrl)
											if (data.fdIsOpen == true && tabL == 1) {
												$(ppbtn).attr("onclick", "toHelpPageIframe('"+childUrl+"');");
											}else{
												// parent.parent.window.document.getElementById("fdHelpDoc").attr("onclick", "openHelpPageIframe('" + childUrl + "',"+tabIndex+")");
												parent.parent.window.document.getElementById("fdHelpDoc").attr("onclick", "openHelpPageIframe('" + childUrl + "',2)");
												$(ppbtn).hover(function() {
													$("#sysHelpUl").show();
												},function() {
													$("#sysHelpUl").hide();
												});
											}
                                        }
                                    } else {
                                        $('#sysHelpBtn').css('display', 'block');
                                    }
                                } else {
                                    // 普通页面
                                    //console.log("parent.parent=>", parent.parent.window.document);
                                    //console.log("parent.parent btn=>", parent.parent.window.document.getElementById("sysHelpBtn"))
                                    //console.log($(ppbtn).css("display"));
                                    $('#sysHelpBtn').css('display', 'block');
                                }
                                return;
                            }

							// 兼容模式
							if(tIsAdminIfm && top.window.location.href!=window.location.href){
								var tHelpBtns = $(top.window.document).find(".sysHelpBtnFixed");
								if(tHelpBtns.length>0){
									var tHelpBtn=tHelpBtns[0];
									// alert($(tHelpBtn).css("display"))
									var tp = $(top.window.document).find("#top")[0];
									// alert($(tp).css("display"));
									if(tHelpBtn && $(tHelpBtn).css("display")=="block"){
										// chrome再次点击修复
										 if(tp&& $(tp).css("display")=="block"){
											 $('#sysHelpBtn').css('display', 'none');
										 }else{
											 $('#sysHelpBtn').css('display', 'block');
										 }
									}else{
										$('#sysHelpBtn').css('display', 'block');
									}
								}else{
									$('#sysHelpBtn').css('display', 'block');
								}
							}else{
								$('#sysHelpBtn').css('display', 'block');
							}

						}
					}
				});
			});
		});

		function openHelpPage(index, url) {
			if (url) {
				Com_OpenWindow('${LUI_ContextPath}/sys/help/sys_help_module/sysHelpModule.do?method=getModuleConfig&index=' + index + '&url=' + url, '_blank');
			}else{
				Com_OpenWindow('${LUI_ContextPath}/sys/help/sys_help_module/sysHelpModule.do?method=getModuleConfig&index=' + index + '&url=' + window.location.pathname + window.location.search, '_blank');
			}
		}

		function openHelpPageIframe(childUrl,index) {
			var fdUrl = window.location.pathname + window.location.search;
			if(top.window.location.href.indexOf("management")>=0){
				openHelpPage(index, childUrl);
			}else{
				openHelpPage(index, fdUrl);
			}
		}

		function checkIE8(){
			var isIE8=false;
			var DEFAULT_VERSION = 8.0;
            var ua = navigator.userAgent.toLowerCase();
            var isIE = ua.indexOf("msie")>-1;
            var ieVersion;
            if(isIE){
            	ieVersion = ua.match(/msie ([\d.]+)/)[1];
            }
            if(ieVersion <= DEFAULT_VERSION ){
                // alert('系统检测到您正在使用ie8以下内核的浏览器，不能实现完美体验，请更换或升级浏览器访问！');
            	isIE8=true;
            };
            return isIE8;
		}
	</script>

	<script>
		function toHelpPage(){
			var fdUrl = window.location.pathname + window.location.search;
			toHelpPageByUrl(fdUrl);
		}

		function toHelpPageIframe(childUrl) {
            // alert("admin里面修改过的click方法，"+(top.window.location.href.indexOf("management")));

            var fdUrl = window.location.pathname + window.location.search;

            if(top.window.location.href.indexOf("management")>=0){
            	// alert("childUrl=>"+childUrl);
            	toHelpPageByUrl(childUrl);
            }else{
            	// alert("fdUrl=>"+fdUrl);
            	toHelpPageByUrl(fdUrl);
            }
        }

		function toHelpPageByUrl(fdUrl){
			seajs.use(['lui/jquery', 'lui/dialog'], function($, dialog){
				var url = '${LUI_ContextPath}/sys/help/sys_help_config/sysHelpConfig.do?method=findConfigByUrl';
				$.ajax({
					url: url,
					type: 'post',
					dataType: 'json',
					data: {
						'fdUrl': fdUrl
					},
					success: function(data) {
						if(data.message == 'noData'){
							dialog.alert('<bean:message bundle="sys-help" key="sysHelpConfig.template.nodata" />');
						}else if(data.message == 'more'){
							dialog.alert('<bean:message bundle="sys-help" key="sysHelpConfig.template.more" />');
						}else{
							Com_OpenWindow('${LUI_ContextPath}/sys/help/sys_help_main/sysHelpMain.do?method=findHTMLById&fdId='+data.mainId+'&configId='+data.configId,'_blank')
						}
					}
				});
			});
		}
	</script>
</c:if>
</kmss:authShow>
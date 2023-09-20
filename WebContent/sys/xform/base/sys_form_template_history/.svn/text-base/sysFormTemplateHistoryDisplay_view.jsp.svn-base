<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.xform.XFormConstant"%>
<%@ include file="/resource/jsp/common.jsp"%>
<link rel="stylesheet" href="${KMSS_Parameter_ContextPath}sys/xform/base/resource/css/sysFormTemplateHistory.css">
<tr>
	<td class="td_normal_title" colspan="2" style="position:relative;height:25px;">
		<label>
			<input type="checkbox" onclick="Sys_Form_Template_loadHistory();"/>
				<bean:message  bundle="sys-xform" key="sysFormTemplate.changeRecord"/>
		</label>
		<div class="sys_xform_history_moduleSelect" style="right: 11%">
			<input type='text' class='sys_xform_moduleSelect_input' title="${lfn:message('sys-xform:sysFormMain.relevance.moduleSelect')}" onkeyup='enterTrigleSelect(event,this);' placeholder='<bean:message  bundle="sys-xform" key="sysFormTemplate.searchPlaceholder"/>'></input>
			<input type='button' class='sys_xform_moduleSelect_select' title="${lfn:message('sys-xform:sysFormMain.relevance.search')}" onclick='sys_xform_history_select();'></input>
			
		</div>
		<div class="sys_xform_history_moduleSelect" style="right: 0%;border: none;margin-top: 1px">
			<kmss:authShow roles="ROLE_SYSXFORM_CONTROL">
				<input class="btnopt" style="height:25px;width: 100px;padding: 0 2px 5px 2px;border: none;color: #fff;line-height: 22px;" type="button" value="${lfn:message('sys-xform-base:Designer_Lang.rebuildJsp')}" onclick="Designer_OptionRun_RebuildJsp();" />
			</kmss:authShow>
		</div>
	</td>
</tr>
<tr>
	<td colspan="2">
		<div id="sysFormTemplateChangeHistoryDiv_${param.fdKey}" style="display:none"></div>
		<div id="pageOperation" style="display:none">
			<ul class="sys_xform_history_pageUl">
				<li style="float:left;" id="lastPageNum" onclick="sys_xform_history_skipPage('1');">
					<bean:message  bundle="sys-xform" key="sysFormTemplate.previousPage"/>
				</li>
				<li id="nextPageNum" onclick="sys_xform_history_skipPage('2');">
					<bean:message  bundle="sys-xform" key="sysFormTemplate.nextPage"/>
				</li>
			</ul>
		</div>
	</td>
</tr>
<script>
	Com_IncludeFile("data.js");

	function sys_xform_history_skipPage(type){
		var div = $("#sysFormTemplateChangeHistoryDiv_${param.fdKey}");
		//type为1是上一页，为2是下一页
		if(type == '1'){
			if(sys_xform_history_currentPageNum != 0){
				sys_xform_history_currentPageNum = sys_xform_history_currentPageNum - 1;
			}
			sys_form_history_updateVersion(div);
		}else if(type == '2'){
			sys_xform_history_currentPageNum = sys_xform_history_currentPageNum + 1;
			sys_form_history_updateVersion(div);
		}
	}

	function sys_xform_history_delWord(){
		$('.sys_xform_moduleSelect_input').val('');	
		$('.sys_xform_moduleSelect_delWord').hide();
	}

	//搜索框按enter即可触发搜索
	function enterTrigleSelect(event,self){
		if(self.value != ''){
			$('.sys_xform_moduleSelect_delWord').css('display','inline');
		}else{
			$('.sys_xform_moduleSelect_delWord').hide();
		}	
		if (event && event.keyCode == '13') {
			sys_xform_history_select();
		}
	}

	function sys_xform_history_select(){
		var field = $(".sys_xform_moduleSelect_input").val();
		var div = $("#sysFormTemplateChangeHistoryDiv_${param.fdKey}");
		if(typeof(field) != 'undefined' && field == ''){
			sys_form_history_updateVersion(div);
			return ;
		}
		var url = Com_Parameter.ContextPath + "sys/xform/base/sys_form_template_history/sysFormTemplateHistory.do?method=searchModelHistoryVersion&mainModelName=${param.fdMainModelName}&fdId=${param.fdId}&field=" + encodeURIComponent(field);
		$.ajax({
			type : "GET",
			url : url,
			success : function(data){
				var dataJson = eval(data);
				if(dataJson){				
					var html = getTableHtml(dataJson);		
					div.html(html.join(''));
					addOperation(div);
					$("#lastPageNum").hide();
					$("#nextPageNum").hide();
				}
			}
		});
	}

	//记录当前最新的版本号
	var sys_xform_currentVersion = 0;

	//记录当前的页码
	var sys_xform_history_currentPageNum = 1;

	function getTableHtml(data,notSearch){
		var html = [];
		html.push('<table class="tb_normal" width="100%" style="text-align:center;">');
		html.push('<tr class="tr_normal_title">');
		html.push('<td width="40pt" class="td_normal_title">' + '<bean:message  bundle="sys-xform" key="sysFormTemplate.no"/>' + '</td>');
		html.push('<td class="td_normal_title">' + '<bean:message  bundle="sys-xform" key="sysFormTemplate.templateEdition"/>' + '</td>');
		html.push('<td class="td_normal_title">' + '<bean:message  bundle="sys-xform" key="sysFormTemplate.alteror"/>' + '</td>');
		html.push('<td class="td_normal_title">' + '<bean:message  bundle="sys-xform" key="sysFormTemplate.alterorTime"/>' + '</td>');		
		html.push('<td class="td_normal_title">' + '<bean:message  bundle="sys-xform" key="sysFormTemplate.versionType"/>' + '</td>');
		html.push('<td class="td_normal_title">' + '<bean:message  bundle="sys-xform" key="sysFormTemplate.version.modification.detailed.log"/>' + '</td>');
		//html.push('<td class="td_normal_title">' + '变更原因' + '</td>');
		html.push('</tr>');
		for(var i = 0; i < data.length; i++){
			if(data[i].maxResults){
				sys_xform_history_maxResults = data[i].maxResults;
				//赋值完，删除记录
				data.splice(i,1);
				i--;
				continue;
			}
			//i == 0表明该行为最新版本
			if(i == 0){
				//notSearch搜索框的标志位，如果是搜索框触发的，不需要更新最新版本号
				if(notSearch && notSearch == 'true'){
					if(parseInt(sys_xform_currentVersion) < parseInt(data[i].fdTemplateEdition)){
						sys_xform_currentVersion = data[i].fdTemplateEdition;
					}	
				}
				if(parseInt(sys_xform_currentVersion) == parseInt(data[i].fdTemplateEdition)){
					html.push('<tr type="version" id = "' + data[i].fdId + '" versionType="new">');
				}else{
					html.push('<tr type="version" id = "' + data[i].fdId + '" >');
				}
				//html.push('<tr id = "' + data[i].fdId + '" versionType="new">');
			}else{
				html.push('<tr type="version" id = "' + data[i].fdId + '" >');
			}				
			html.push('<td>' + (i + 1) + '</td>');
			html.push('<td>' + 'V' + data[i].fdTemplateEdition + '</td>');			
			html.push('<td>' + data[i].fdAlterorName + '</td>');
			html.push('<td>' + data[i].fdAlterTime + '</td>');
			if(parseInt(data[i].fdTemplateEdition) < parseInt(sys_xform_currentVersion)){
				html.push('<td>' + '<bean:message  bundle="sys-xform" key="sysFormTemplate.historyVersion"/>' + '</td>');
			}else{
				html.push('<td>' + '<bean:message  bundle="sys-xform" key="sysFormTemplate.currentVersion"/>' + '</td>');
			}
			//html.push('<td>' + data[i].fdChangeAsNewReason + '</td>');
			html.push('<td>');
			html.push(getMLogTableHtml(data[i].mLogs));
			html.push('</td>');
			html.push('</tr>');
		}
		html.push('</table>');
		return html;
	}
	
	
	// 变更日志
	function getMLogTableHtml(data,notSearch){
		var html = [];
		if (data) {
			data = JSON.parse(data);
			if(data && data.length > 0) {
				html.push('<table class="tb_normal" width="100%" style="text-align:center;">');
				html.push('<tr class="tr_normal_title">');
				html.push('<td width="40pt" class="td_normal_title">' + '<bean:message  bundle="sys-xform" key="sysFormTemplate.no"/>' + '</td>');
				html.push('<td class="td_normal_title">' + '<bean:message  bundle="sys-xform" key="sysFormTemplate.alteror"/>' + '</td>');
				html.push('<td class="td_normal_title">' + '<bean:message  bundle="sys-xform" key="sysFormTemplate.alterorTime"/>' + '</td>');
				html.push('<td class="td_normal_title">' + '<bean:message  bundle="sys-xform" key="sysFormTemplate.update.detail"/>' + '</td>');		
				html.push('</tr>');
				for (var i = 0; i < data.length; i++){
					html.push('<tr id = "' + data[i].fdId + '" >');
					html.push('<td>' + (i + 1) + '</td>');
					html.push('<td>' + data[i].fdAlterorName + '</td>');
					html.push('<td>' + data[i].fdAlterTime + '</td>');
					html.push('<td>' + '<a style="color: #47b5ea;border-bottom:1px solid #47b5ea;" mlogId="' + data[i].fdId + '" href="javascript:void(0);" onclick="viewLog(this)"><bean:message  bundle="sys-xform" key="sysFormTemplate.view"/></a>' + '</td>');
					html.push('</tr>');
				}
				html.push('</table>');
			}
		}
		return html.join("");
	}
	
	/** 查看变更日志详情 */
	function viewLog(src) {
		Com_EventStopPropagation();
		var mlogId = $(src).attr("mlogId");
		Com_OpenWindow(Com_Parameter.ContextPath+'sys/xform/base/sysFormModifiedLogAction.do?method=view&fdId=' + mlogId);
		return false;
	}

	var sys_xform_history_rowSize = 8;

	var sys_xform_history_maxResults = 0;

	function sys_form_history_updateVersion(div){
		div.html('<img src="' + Com_Parameter.ResPath + 'style/common/images/loading.gif" border="0" />');
		var url = "sysFormTemplateHistoryService&templateId=${sysFormTemplateForm.fdId}&key=${param.fdKey}&rowsize=" + sys_xform_history_rowSize + "&pageno=" + sys_xform_history_currentPageNum;
		//兼容多表单
		var fdModel = "${xFormTemplateForm.fdMode}";
		if(fdModel=="<%=XFormConstant.TEMPLATE_SUBFORM%>"){
			var tr = $("#TABLE_SUBFORM").find("tr[ischecked='true']");
			url += ("&fdTemplateId="+tr.find("input[name='subformfdId']").val());
		}else{
			url += ("&fdTemplateId=${xFormTemplateForm.fdId}");
		}
		var kmssdata = new KMSSData();
		if(fdModel=="<%=XFormConstant.TEMPLATE_SUBFORM%>"){
			kmssdata.UseCache = false;
		}
		var data = kmssdata.AddBeanData(url).GetHashMapArray();
		var html = getTableHtml(data,'true');		
		div.html(html.join(''));
		addOperation(div);
		//翻页
		if(sys_xform_history_currentPageNum > 1){
			var num = data.length + ((sys_xform_history_currentPageNum-1) * sys_xform_history_rowSize);
			//不是第一页，显示上一页
			$("#lastPageNum").show();
			if(sys_xform_history_maxResults > 0 && num < sys_xform_history_maxResults){				
				$("#nextPageNum").show();
			}else{
				$("#nextPageNum").hide();
			}
		}else{
			$("#lastPageNum").hide();
			if(sys_xform_history_maxResults > 0 && data.length < sys_xform_history_maxResults){	
				$("#nextPageNum").show();
			}else{
				$("#nextPageNum").hide();
			}
		}
	}
	
	function Sys_Form_Template_loadHistory(){
		var div = $("#sysFormTemplateChangeHistoryDiv_${param.fdKey}");
		var pageNum = $("#pageOperation");
		var fdModel = "${xFormTemplateForm.fdMode}";
		if(div.is(":hidden")) {
			div.show(); // 显示
			pageNum.show();
			if(div.html()&&fdModel!="<%=XFormConstant.TEMPLATE_SUBFORM%>") { //已经加载
				return;
			}
			sys_form_history_updateVersion(div);
			
		} else {
			div.hide(); // 隐藏
			pageNum.hide();
		}
		
		var customIframe = window.frames['IFrame_FormTemplate_${JsParam.fdKey}'].contentWindow;
		if(!customIframe){
			customIframe = window.frames['IFrame_FormTemplate_${JsParam.fdKey}'];
		}
		if(customIframe.Designer != null && customIframe.Designer.instance != null){
			//重新调整多表单div高度
			SubForm_AdjustViewHeight(customIframe);
		}
	}

	function addOperation(div){
		div.find('tr[type="version"]').mouseover(function(){
			$(this).addClass("sys_form_template_history_mouseover");
		}).mouseout(function(){
			$(this).removeClass("sys_form_template_history_mouseover");
		}).click(function(){
			var fdId = $(this).attr("id");
			var versionType = $(this).attr("versionType");
			Com_OpenWindow(Com_Parameter.ContextPath+'sys/xform/base/sys_form_template_history/sysFormTemplateHistory.do?method=view&fdId=' + fdId + '&fdMainModelName=${param.fdMainModelName}&fdModelTemplateId=${param.fdId}&versionType=' + versionType);
		});
	}
	
	//placeholder兼容
	function placeholderAdjust(){
		//如果不支持placeholder属性
		if(!("placeholder" in document.createElement("input"))){
			$("input[type='text']").each(function(e){
				var _this = $(this);
				var attr_plac=_this.attr("placeholder");
				if(attr_plac){//存在placeholder属性
                    var e_id=_this.attr("id");
                    if(!e_id){//如果该input不存在则创建id
                        var dt=new Date();
                        e_id=="lbl_"+dt.getSeconds()+dt.getMilliseconds();
                        _this.attr("id",e_id);
                    }
                    var new_lbl=document.createElement("label");
                    new_lbl.setAttribute("for",e_id);
                    new_lbl.className="sys_xform_history_placeholder";
                    $(new_lbl).css("padding",_this.css("padding"));
                    $(new_lbl).css("margin",_this.css("margin"));
                    $(new_lbl).css("line-height",_this.css("line-height"));
                    new_lbl.innerHTML=attr_plac;
                    $(new_lbl).insertBefore(_this);
                    $(new_lbl).bind("click",function(){_this.focus();});
                    _this.bind("focus",placeholderFocus);
                    _this.bind("click",placeholderFocus);
                    _this.bind("blur",placeholderBlur);
                }
			});
		}
	}
	
	function placeholderFocus(){
		 var _this=$(this);
         _this.siblings("label").hide();
	}
	
	function placeholderBlur(){
		var _this=$(this);
        if($.trim(_this.val()).length==0||_this.val()==_this.attr("placeholder")){
            _this.siblings("label").show();
        }
	}
	
	Com_AddEventListener(window,'load',placeholderAdjust);
	
	function Designer_OptionRun_RebuildJsp(){
		var message=confirm("${lfn:message('sys-xform-base:Designer_Lang.sure_to_rebuildJsp')}"); 
		var oldscrollTop;
		if(message==true){
			$.ajax( {
				url : Com_Parameter.ContextPath+'sys/xform/sys_form_common_template/sysFormCommonTemplate.do?method=rebuildJsp&fdId=${param.fdId}',
				type : 'GET',
				success : function(data) {
					if(data == "true"){
						alert("${lfn:message('sys-xform-base:Designer_Lang.successful_operation')}"); 
						var div = $("#sysFormTemplateChangeHistoryDiv_${param.fdKey}");
						if(!div.is(":hidden")&&"${xFormTemplateForm.fdMode}"=="<%=XFormConstant.TEMPLATE_SUBFORM%>") {
							sys_form_history_updateVersion(div);
						}
					}else{
						alert("${lfn:message('sys-xform-base:Designer_Lang.failure_operation')}"); 
					}
				},
				beforeSend : function() {
					var XForm_Loading_Msg = "${lfn:message('sys-xform-base:Designer_Lang.loading_Msg')}";
					var XForm_Loading_Img = document.createElement('img');
					XForm_Loading_Img.src = Com_Parameter.ContextPath + "sys/xform/designer/style/img/_loading.gif";
					var XForm_Loading_Div = document.createElement('div');
					XForm_Loading_Div.id = "Loading_Div";
					XForm_Loading_Div.style.height = "100%";
					XForm_Loading_Div.style.width = "100%";
					var XForm_loading_Text = document.createElement("label");
					XForm_loading_Text.id = 'XForm_loading_Text_Label';
					XForm_loading_Text.appendChild(document.createTextNode(XForm_Loading_Msg));
					XForm_loading_Text.style.color = "#00F";
					XForm_loading_Text.style.height = "16px";
					XForm_loading_Text.style.marginTop = '20%';
					XForm_Loading_Img.style.marginTop = '20%';
					oldscrollTop = document.documentElement.scrollTop||document.body.scrollTop;
					document.documentElement.scrollTop=document.body.scrollTop = 0;
					document.body.style.overflow = 'hidden';
					XForm_Loading_Div.appendChild(XForm_Loading_Img);
					XForm_Loading_Div.appendChild(XForm_loading_Text);
					XForm_Loading_Div.align = "center";
					with(document.body.appendChild(XForm_Loading_Div).style) {
						position = 'absolute'; filter = 'alpha(opacity=80)'; opacity = '0.8';
						border = '1px'; background = '#EAEFF3'; 
						top = '0'; left = '0';
						zIndex = '999';
					}
				},
				complete : function() {
					var div = document.getElementById('Loading_Div');
					if (div){
						document.body.removeChild(div);
					}
					document.body.style.overflow = 'auto';
					document.documentElement.scrollTop=document.body.scrollTop = oldscrollTop;
				}
			});
		} 
	}
</script>
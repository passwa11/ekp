<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="/resource/jsp/list_top.jsp"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<style type="text/css">
.tagclass {
	margin-top: 5px;
	background-color: orange;
	padding-left: 5px;
	margin-right: 5px;
	text-align: center;
	display: inline-block;
}

.deleicon {
	margin-left: 10px;
	text-decoration: none;
}

.tagsettingtitle {
	text-align: center;
	font-size: 18px;
}
.a{

	width:50px;
	text-align:center;
	display: inline-block; 
	*display: inline;
	height: 30px;
	line-height: 30px;
	background: #37a8f5;
	color: #fff;
	font-size: 14px;
	padding: 0 10px;
	position: relative;
	
}
.b{
color:#fff;
font-size:14px;
line-height:30px;
}
</style>
<script type="text/javascript">
	Com_IncludeFile("dialog.js|jquery.js");
</script>
<Script type="text/javascript">
function deletag(id) {

	$
			.ajax({
				type : "POST",
				url : "<c:url value='/sys/tag/sys_tag_tags/sysTagTags.do'/>?method=deletag",
				data : "fdTagId=" + id,
				success : function(msg) {
					var obj = GetID(id);
					obj.parentNode.removeChild(obj);
					seajs.use(['lui/dialog'],
							function(dialog) {
					dialog.alert("${lfn:message('return.optSuccess')}");
					
					window.location.reload();
				})
				}})}
			;
	function GetID(id) {
		return document.getElementById(id);
	}

	function afterSelectValue(datas,_dialog) {
		if (datas != null && typeof (datas) != "undefined"){
			var a = datas.length;
			var obj = GetID("selectedtag").getElementsByTagName('span').length;
			var b = a + obj;
			if (b > 60) {
				alert("${lfn:message('sys-tag:sysTag.tags.num.limit')}");
				return;
			}
			if(a>30){
				alert("${lfn:message('sys-tag:sysTag.tags.num.limit')}");
				return;
			}
			var docContent = "";

			for (var i = 0; i < datas.length; i++) {
				
				var newName = datas[i].fdName;
				var newId = datas[i].fdId;
				docContent = docContent + newName + ";";
			}
			$
					.ajax({
						type : "POST",
						url : "<c:url value='/sys/tag/sys_tag_tags/sysTagTags.do'/>?method=selectedtag",
						data : "docContent=" + encodeURIComponent(docContent),
						success : function(msg) {
							_dialog.hide(true);
							location.replace(location.href);
						}
					});
		}
	}

	function deletagall() {
		
		var obj = GetID("selectBox");
		seajs.use(['lui/dialog', 'lui/topic', 'lui/jquery'],
				function(dialog, topic, $) {
		if (obj.innerText=="") {
		
			dialog.alert("${lfn:message('page.noSelect')}");
			return;
			}
		else {dialog.confirm("${lfn:message('sys-tag:sysTag.tags.setting.confirm.delete')}",function(flag, d)  {
			
			if(flag){
			$
					.ajax({
						type : "POST",
						url : "<c:url value='/sys/tag/sys_tag_tags/sysTagTags.do'/>?method=deletagall",

						success : function(msg) {
							var obj = GetID("selectBox");
							while (obj.hasChildNodes()) {//当div下还存在子节点时 循环继续
								obj.removeChild(obj.firstChild);
							}
							dialog.success("${lfn:message('return.optSuccess')}");
							window.location.reload();
						}
					})
			    }
			});
		 }
        }
	)
}
	
	function addCloudTag(){
		var top = Com_Parameter.top || window.top;
		top.window.selectTagNames = $("input[name='fdTagNames']").val();
		top.window.selectTagIds = $("input[name='fdTagIds']").val();
		var url = "/sys/tag/sys_tag_cloud/addTagCloud.jsp?mulSelect=true&cloudTagSign=1";
		seajs.use(['lui/dialog', 'lui/util/env','lang!sys-tag','lang!sys-ui'],function(dialog, env,lang,ui_lang) {
			dialog.iframe(url, lang["sysTag.choiceTag"], null, {
				width : 900,
				height : 550,
				buttons : [
					{
						name : ui_lang["ui.dialog.button.ok"],
						value : true,
						focus : true,
						fn : function(value,_dialog) {
							if(_dialog.frame && _dialog.frame.length > 0){
								var _frame = _dialog.frame[0];
								var contentWindow = $(_frame).find("iframe")[0].contentWindow;
								if(contentWindow.onSubmit()) {
									var datas = contentWindow.onSubmit().slice(0);
									if(datas.length>=0){
										afterSelectValue(datas,_dialog);	
										
									}
								}
							}
							
						}
					}
					,{
						name :ui_lang["ui.dialog.button.cancel"],
						value : false,
						styleClass : 'lui_toolbar_btn_gray',
						fn : function(value, dialog) {
							dialog.hide(value);
						}
					}
				]	
			});
		});
	}
</Script>

<body>
<div id="optBarDiv">
		<kmss:auth requestURL="/sys/tag/sys_tag_category/sysTagCategory.do?method=add" requestMethod="GET">
			<input type="button" value="${lfn:message('sys-tag:sysTag.tags.cloud.setting.clear')}"
				onclick="deletagall();">
		</kmss:auth>
		
	</div>
	
	<input type="hidden" name="fdTagIds" value="${fdTagIds}" />
	<input type="hidden" name="fdTagNames" value="${fdTagNames}" />
	
	<div style="width: 90%; margin-top: 40px;height:100%">
		<div style="float:left;margin-left:5px;margin-top:60px;" >
		<b style="font-size: 14px;">${lfn:message('sys-tag:sysTag.tags.theSelectedTags') }：</b>
		</div>
			<div id="selectedtag" style=";float:left;width:80%;border:1px solid #ccc;height:140px;overflow:auto; " >
			<div id="selectBox" style="width:95%;margin:25px auto;text-align:justify;">
				<c:forEach items="${selectedList}" var="sysTagCloudSelected" varStatus="vstatus">
					<span class="tagclass" id="${sysTagCloudSelected.fdSysTagTags.fdId}">
						<c:out value="${sysTagCloudSelected.fdSysTagTags.fdName}" />  
						<a href="javascript:deletag('${sysTagCloudSelected.fdSysTagTags.fdId}')"
							class="deleicon">
							X
						</a> 
					</span>
				</c:forEach>
				</div>
			</div>
		
			<div class="a" style=" float:left;margin-left:5px;margin-top:60px;">
			<span class="btn_g"> 
			<a href="javascript:void(0);" onclick="addCloudTag()">
				<span class="b">${lfn:message('sys-tag:sysTag.tags.cloud.setting.add')}</span>
			</a>
		</span>  
		</div>
		
		<div style="margin-top:10px;margin-right:130px;float:right">
		<span ><b style="font-size: 13px;">${lfn:message('sys-tag:sysTag.tags.cloud.setting.please.msg')}</b></span>
		
	</div>
		</div>
	
</body>

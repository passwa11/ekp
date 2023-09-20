<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<% response.addHeader("X-UA-Compatible", "IE=Edge"); %>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<script type="text/javascript">
	Com_IncludeFile("jquery.js");
	
	//文件上传
	function upload(){
		var file = document.getElementsByName("file");
		if(file[0].value==null || file[0].value.length==0){
			seajs.use(['lui/dialog'],function(dialog){
				dialog.alert("<bean:message bundle='km-archives' key='kmArchivesMain.import.file.required'/>");
			});
			return false;
		}
		else{
			//修改状态为正在执行
			var uploadProcess=document.getElementById("div_uploadProcess");
			uploadProcess.innerHTML="<font color='blue'><bean:message bundle='km-archives' key='kmArchivesMain.title.uploadDoing'/></font>";
			var form=document.getElementsByName("kmArchivesMainForm")[0];
			form.target="file_frame";
			form.submit();
		}
	}
	
	//excel文件上传完毕,显示操作信息
	function callback(result){
		document.getElementById("div_errorCell").innerHTML=createTable(result);
		var uploadProcess=document.getElementById("div_uploadProcess");
		uploadProcess.innerHTML="<font color='red'>" + result.importMsg + "</font>";
	}
	
	// 兼容IE9以下不支持forEach
	function compatibleIEForEach(arrayObject) {
		if (!Array.prototype.forEach) {
			arrayObject.forEach = function(callback, thisArg) {
				var T, k;
			    if (this == null) {
			    	throw new TypeError(' this is null or not defined');
			    }
			    var O = Object(this);
			    var len = O.length >>> 0;
			    if (typeof callback !== "function") {
			    	throw new TypeError(callback + ' is not a function');
			    }
			    if (arguments.length > 1) {
			    	T = thisArg;
			    }
			    k = 0;
			    while (k < len) {
			    	var kValue;
			    	if (k in O) {
			        	kValue = O[k];
			       		callback.call(T, kValue, k, O);
			     	}
			     	k++;
			   }
			};
		}
	}
	
	function createTable(json) {
		if(json == null) return "";
		if(typeof json !== 'object') return "";
		if(json.error != true) return "";
		var htmlStr = "<table class='tb_normal' style='width:100%;'>";
		var colLength;
		//标题
		var titles = json.titles;
		if(titles != null) {
			compatibleIEForEach(titles);
			htmlStr += "<thead style='background:#ddd;'><tr>";
			var colLength = titles.length;
			titles.forEach(function(item,index) {
				htmlStr += "<th style='padding:5px 10px;text-align:center;border-left:1px solid #d1d1d1;'>"+item+"</th>"
			});
			htmlStr += "</tr></thead>";
		}
		htmlStr += "<tbody>";
		
		//内容
		var rows = json.errorRows;
		if(rows != null) {
			compatibleIEForEach(rows);
			rows.forEach(function(item,index) {
				var errColNumbers = item.errColNumbers;
				htmlStr += "<tr>";
				htmlStr += "<td>"+item.errRowNumber+"</td>";
				var contents = item.contents;
				compatibleIEForEach(contents);
				contents.forEach(function(it,idx) {
					if($.inArray(idx,errColNumbers) > -1)
						htmlStr += "<td style='color:#FF0000;'>"+formatNull(it)+"</td>";
					else
						htmlStr += "<td>"+formatNull(it)+"</td>";	
				});
				htmlStr += "<td style='color:#FF0000'>"+item.errInfos.join("<br>")+"</td>";
				htmlStr += "</tr>";
			});
		}
		//其他错误
		var otherErrors = json.otherErrors;
		if(otherErrors && otherErrors.length>0) {
			compatibleIEForEach(otherErrors);
			var colspanStr = colLength==null?"":"colspan='"+colLength+"'";
			htmlStr += "<tr><td "+colspanStr+"><p><b><bean:message bundle='km-archives' key='kmArchivesMain.import.otherErrors'/></b></p>";
			otherErrors.forEach(function(item,index) {
				htmlStr += "<p style='color:#FF0000;'>"+item+"</p>";
			});
			htmlStr += "</td></tr>";
		}
		htmlStr += "</tbody></table>";
		return htmlStr;
		
	}
	function formatNull(val) {
		if(val===null||val===undefined||val==="null"||val==="NULL") {
			return "";
		}
		return val;
	}
	//展开出错列表
	function showMoreErrInfo(srcImg){
		var obj = document.getElementById("div_errorCell");
		if(obj.style.display=="none"){
			obj.style.display="block";
			srcImg.src = Com_Parameter.StylePath + "msg/minus.gif";
		}else{
			obj.style.display="none";
			srcImg.src = Com_Parameter.StylePath + "msg/plus.gif";
		}
	}

	//改变上传附件,重置导出结果
	function resetResult(){
		var uploadProcess=document.getElementById("div_uploadProcess");
		uploadProcess.innerHTML="<bean:message bundle='km-archives' key='kmArchivesMain.title.uploadNotDo'/>";
		document.getElementById("div_errorCell").innerHTML="";
	} 
	$(document).ready(function() {  
        $(".upload").change(function() {  
            var arrs=$(this).val().split('\\');  
            var filename=arrs[arrs.length-1];  
            $(".show").html(filename); 
            resetResult();
        }).hover(function() {
        	$(".blueButton").css({'background-color':"#008de2"});
        },function() {
        	$(".blueButton").css({'background-color':"#00b3ee"});
        });
    });
</script>
<style>
html,body{
	background-color:#efefef;
}
/*蓝色按钮,绝对定位*/  
.blueButton {
	top:4px;  
    position: absolute;  
    display: block;  
    width: 80px;  
    height: 30px;  
    background-color: #00b3ee;  
    color: #fff;  
    text-decoration: none;  
    text-align: center;  
    font:normal normal normal 16px/30px 'Microsoft YaHei';  
    cursor: pointer;  
    border-radius: 4px;  
}  
.blueButton:hover {  
    text-decoration: none; 
}  
/*自定义上传,位置大小都和a完全一样,而且完全透明*/  
.upload  {  
	top:4px;
    position: absolute;  
    display: block;  
    width: 80px;  
    height: 30px;  
    opacity: 0; 
	filter:Alpha(opacity=0);
    cursor: pointer; 
}
/*显示上传文件夹名的Div*/  
.show {  
    position: absolute;  
    top:4px;
    left:85px;  
    width: 90%;  
    height: 30px;
    color:#1b83d8;  
    font:normal normal normal 14px/30px 'Microsoft YaHei';  
}
.upload-container {
	overflow:auto;
	width:1200px;
	padding:10px 20px;
	background-color:#fff;
	border-radius:10px;
	-webkit-border-radius:10px;
	-ms-border-radius:10px;
	-o-border-radius:10px;
	-moz-border-radius:10px;
	box-shadow: 3px 4px 10px 1px #888888;
	-webkit-box-shadow:3px 4px 10px 1px #888888;
	-ms-box-shadow:3px 4px 10px 1px #888888;
	-o-box-shadow:3px 4px 10px 1px #888888;
	-moz-box-shadow:3px 4px 10px 1px #888888;
}
</style>
<form name="downloadForm" action="${LUI_ContextPath }/km/archives/km_archives_main/kmArchivesMain.do" method="post" target="_blank">
	<input type="hidden" name="docTemplate" value="${HtmlParam.docTemplate }"/>
</form>
<html:form action="/km/archives/km_archives_main/kmArchivesMain.do?method=importArchives&docTemplate=${HtmlParam.docTemplate }" 
	enctype="multipart/form-data"	onsubmit="return validateForm(this);">
	<iframe name="file_frame" style="display:none;"></iframe>	
<div id="optBarDiv">
	<input type="button" value="<bean:message bundle="km-archives" key="kmArchivesMain.import.template.download"/>" onclick="Com_SubmitNoEnabled(document.downloadForm,'downloadTemplate');">
	<input type="button" value="<bean:message bundle="km-archives" key="kmArchivesMain.button.upload"/>" onclick="upload();">
	<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
</div>



<center>
<div class="upload-container">
<p class="txttitle"><bean:message  bundle="km-archives" key="kmArchivesMain.dataImport"/></p>
<hr>
<table style="width:100%;">
	<tr>
		<td class="td_normal_title" style="width:100px">
			<bean:message  bundle="km-archives" key="kmArchivesMain.upload.file"/>
		</td>
		<td>
			<div style="position:relative;height:34px;">
				<a href="javascript:void(0);" class="blueButton"><bean:message bundle="km-archives" key="kmArchivesMain.import.selectFile"/></a>  
				<input name="file" accept=".xls,.xlsx" type="file" class="upload" />
				<div class="show"></div>
			</div>
		</td>
	</tr>
	
	<tr>
		<td colspan="2" style="line-height:28px;">
			<bean:message bundle="km-archives" key="kmArchivesMain.tip.uploadResult" /><br />
			<table class="tb_noborder" width="100%">
				<tr height="25px">
					<td class="msglist" colspan="3">
						<bean:message bundle="km-archives" key="kmArchivesMain.title.uploadProcess"/>
						<span id="div_uploadProcess">
								<bean:message bundle="km-archives" key="kmArchivesMain.title.uploadNotDo"/>
						</span>
					</td>
				</tr>
				<tr height="25px">
					<td class="msglist" colspan="3" style="color: red;">
						<bean:message bundle="km-archives" key="kmArchivesMain.title.startPoint"/>
						<span id="div_uploadProcess">
								<bean:message bundle="km-archives" key="kmArchivesMain.title.point"/>
						</span>
					</td>
				</tr>
				<tr height="25px">
					<td class="msglist" colspan="3">
						<image src='${KMSS_Parameter_StylePath}msg/plus.gif' onclick='showMoreErrInfo(this);' style='cursor:pointer'>
						<bean:message bundle="km-archives" key="kmArchivesMain.title.detail"/>
						<div id="div_errorCell" style="display:none;margin-top: 5px;width:1200px;overflow:auto;"></div>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</div>
</center>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp"%>
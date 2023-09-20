function submitExportForm() {
	document.templet.submit();
}
//excel文件上传完毕,显示操作信息
function callback(result, msg){
	document.getElementById("div_errorCell").innerHTML=createTable(result,msg);
	var uploadProcess=document.getElementById("div_uploadProcess");
	uploadProcess.innerHTML="<font color='red'>" + msg + "</font>";
}
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
function createTable(result,msg) {
	var json;
	try{
		json = JSON.parse(result);
	}catch(err) {
		return result;
	}
	if(!json) return "";
	var htmlStr = "<table class='tb_normal' style='width:100%;'><thead style='background:#ddd;'><tr>";
	//标题
	var titles = json.titles;
	var colLength = titles.length;
	
	$.each(titles,function(index,item){ 
		htmlStr += "<th style='padding:5px 10px;text-align:center;border-left:1px solid #d1d1d1;'>"+item+"</th>";
    });
	
	htmlStr += "</tr></thead><tbody>";
	//内容
	var rows = json.rows;
	$.each(rows,function(index,item){ 
		var errColNumbers = $.map( item.errColNumbers.split(",") , function(item,index){return parseInt(item);} );
		htmlStr += "<tr>";
		htmlStr += "<td>"+item.errRowNumber+"</td>";
		var contents = item.contents;
		$.each(contents,function(idx,it){ 
			if($.inArray(idx,errColNumbers) > -1)
				htmlStr += "<td style='color:#FF0000;'>"+formatNull(it)+"</td>";
			else
				htmlStr += "<td>"+formatNull(it)+"</td>";	
		});
		htmlStr += "<td style='color:#FF0000'>"+item.errInfos+"</td>";
		htmlStr += "</tr>";
	});
	//其他错误
	var otherErrors = json.otherErrors;
	if(otherErrors && otherErrors.length>0) {
		htmlStr += "<tr><td colspan='"+colLength+"'><p><b>"+import_info_other_errors+"</b></p>";
		$.each(otherErrors,function(index,item){ 
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
//改变上传附件,重置导出结果
// function resetResult(){
// 	var uploadProcess=document.getElementById("div_uploadProcess");
// 	uploadProcess.innerHTML="";
// 	document.getElementById("div_errorCell").innerHTML="";
// 	upload()
// }
//文件上传
window.upload = function(){
	console.log("开始", new Date());
	var file = document.getElementsByName("file");
	if(file[0].value==null || file[0].value.length==0){
		seajs.use(['lui/dialog'],function(dialog){
			dialog.alert(langs.selectFile);
		});
		return false;
	}
	else{
		//修改状态为正在执行
		seajs.use(['lui/dialog'],function(dialog){
			dialog.loading(langs.uploading)
		});
		var form=document.getElementsByName("modelingImportConfigForm")[0];
		//form.target="file_frame";
		form.submit();
	}
}
window.change = function(){
	document.getElementById("upload_file_temp").value=document.getElementById("upload_file").value;
	$("#upload_file_msg").show();
	$("#upload_file_name").html(document.getElementById("upload_file").value)
	$("#upload_file_title").html(langs.reUpload)

	//upload();
};
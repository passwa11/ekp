   Com_IncludeFile("jquery.js");

   /**
	function validateForm() {
		if (document.sysTransportImportUploadForm.file.value == "") {
			alert(alert_upload_file_empty);
			return false;
		}
		else return true;
	}
	**/

	//文件上传
	function upload(){
		var file = document.getElementsByName("file");
		if(file[0].value==null || file[0].value.length==0){
			seajs.use(['lui/dialog'],function(dialog){
				dialog.alert(alert_msg_file_required);
			});
			return false;
		}
		else{
			//修改状态为正在执行
			var uploadProcess=document.getElementById("div_uploadProcess");
			uploadProcess.innerHTML="<font color='blue'>"+upload_doing_tip_desc+"</font>";
			var form=document.getElementsByName("sysTransportImportUploadForm")[0];
			form.target="file_frame";
			form.submit();
		}
	}
	
	//excel文件上传完毕,显示操作信息
	function callback(result, msg){
		document.getElementById("div_errorCell").innerHTML=createTable(result,msg);
		var uploadProcess=document.getElementById("div_uploadProcess");
		uploadProcess.innerHTML="<font color='red'>" + msg + "</font>";
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
			
			var errInfos = '';
			if(item.errInfos != null || typeof(item.errInfos) != "undefined"){
				//解码
				item.errInfos=htmlentities.decode(item.errInfos)
				errInfos = item.errInfos.replace(/&/g,"&amp;");
				errInfos = errInfos.replace(/</g,"&lt;");
				errInfos = errInfos.replace(/>/g,"&gt;");
				errInfos = errInfos.replace(/ /g,"&nbsp;");
				errInfos = errInfos.replace(/\'/g,"&#39;");
				errInfos = errInfos.replace(/\"/g,"&quot;"); 
			}
			htmlStr += "<td style='color:#FF0000'>"+errInfos+"</td>";
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
		uploadProcess.innerHTML=upload_not_do_desc;
		document.getElementById("div_errorCell").innerText="";
	} 
	$(document).ready(function() { 
        $("input[type='file'][name='file']").change(function() {  
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
	//html编码与解码
	window.htmlentities = {
			encode : function(str) {
				var buf = [];
				
				for (var i=str.length-1;i>=0;i--) {
					buf.unshift(['&#', str[i].charCodeAt(), ';'].join(''));
				}
				
				return buf.join('');
			},
			decode : function(str) {
				return str.replace(/&#(\d+);/g, function(match, dec) {
					return String.fromCharCode(dec);
				});
			}
	};
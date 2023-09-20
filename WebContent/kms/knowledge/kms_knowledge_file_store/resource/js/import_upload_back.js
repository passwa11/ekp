   Com_IncludeFile("jquery.js");

   /************************************
	*
	* 		下载进度相关 Starts
	*
	***********************************/

   if(window.onbeforeunload){
	   window.onbeforeunload = function () {
		   window.__isProgressing = false;
	   }
   }

   /**
	* 下载模板
	*/
   function submitExportTemplateForm() {
	   if(window.__isProgressing){
		   seajs.use(['lui/dialog'],function(dialog){
			   dialog.alert(upload_doing_tip_desc);
		   });
		   return;
	   }
	   var form=document.getElementsByName("downloadWithoutSuccessDataTemplate")[0];
	   window.__progressKey =  "download_" + Com_Parameter.CurrentUserId + "_" + new Date().getTime();
	   $("input[name='templateProgressKey']").val(window.__progressKey);
	   form.target = "file_template_frame";
	   var iframe = $("iframe[name='file_template_frame']")[0];
	   iframe.onload = function (){
		   seajs.use(["kms/knowledge/kms_knowledge_file_store/resource/js/commonAjaxSubmit"],function (commonAjaxSubmit){
			   var y=(iframe.contentWindow || iframe.contentDocument);
			   if (y.document)y=y.document;
			   var data = y.getElementsByTagName("html")[0].outerHTML;
			   commonAjaxSubmit.handleError(true,data);
			   window.__isProgressing = false;
		   })
	   }
	   window.__preProgressMsg = "";
	   window.__isProgressing = true;
	   window.__progressErrorCount = 0;
	   doDownloadProgressSearch();
	   form.submit();
   }

   /**
	* 更新进度描述
	* @param msg
	*/
   function updateDownloadDoingDescribe(msg){
	   showDownloadResultDiv();
	   var uploadProcess=document.getElementById("div_downloadProcess");
	   uploadProcess.innerHTML="<font color='blue'>"+msg+"</font>";
   }

   /**
	* 更新进度描述
	* @param msg
	*/
   function updateDownloadCompleteDescribe(msg){
	   showDownloadResultDiv();
	   var uploadProcess=document.getElementById("div_downloadProcess");
	   uploadProcess.innerHTML="<font color='red'>"+msg+"</font>";
   }

   /**
	* 隐藏下载结果进度
	*/
   function hiddenDownloadResultDiv(){
	   $("#downloadResultDiv").hide();
	   $("#uploadResultDiv").hide();
	   $("#tipDiv").show();
   }

   /**
	* 显示下载结果进度
	*/
   function showDownloadResultDiv(){
	   $("#downloadResultDiv").show();
	   $("#tipDiv").hide();
	   $("#uploadResultDiv").hide();
   }

   /**
	* 下载进度查询
	*/
   function doDownloadProgressSearch(){
	   //获取进度结束 或者 获取进度失败200次结束进度获取
	   if(!window.__isProgressing || window.__progressErrorCount > 5) {
		   setTimeout(function (){
			   hiddenDownloadResultDiv();
		   },100);
		   return;
	   }
	   seajs.use(["lui/util/env"],function (env){
		   var url = env.fn.formatUrl("/kms/knowledge/kms_knowledge_file_store/kmsKnowledgeFileStoreImportExport.do?method=getExcelHandleProgress")
		   $.ajax({
			   url : url,
			   type : 'POST',
			   dataType : 'json',
			   data : {
				   progressKey: window.__progressKey,
				   preProgressMsg: window.__preProgressMsg
			   },
			   timeout: 10000,
			   success : function(data,textStatus,xhr) {
				   handleProgressData(data,doDownloadProgressSearch,updateDownloadDoingDescribe,updateDownloadCompleteDescribe);
			   },
			   error : function(xhr,textStatus,errorThrown) {
				   window.__progressErrorCount++;
				   // 请求超时
				   if (textStatus == "timeout") {
					   doDownloadProgressSearch();
				   }
			   }
		   });
	   });
   }

   /**
	* 处理进度数据
	* @param data
	* @param progressFunction
	* @param doingMsgFunction
	* @param doneMsgFunction
	*/
   function handleProgressData(data, progressFunction, doingMsgFunction, doneMsgFunction){
	   if(data && data.result == "OK"){
		   window.__progressErrorCount = 0;
		   var msg = data.msg;
		   var type = data.type;
		   //正常结束
		   if("complete" == type || "failure" == type){
			   window.__isProgressing = false;
			   doneMsgFunction(msg);
		   }else{
			   doingMsgFunction(msg);
		   }
		   var progressStr = type + "::" + msg;
		   if(window.__preProgressMsg != progressStr){
			   window.__preProgressMsg = progressStr;
			   //避免请求过于频繁，设置50ms定时
			   setTimeout(function (){
				   progressFunction();
			   },50);
		   }else{ //结果内容相同，增加等待时间
			   setTimeout(function (){
				   progressFunction();
			   },250);
		   }
	   }else{
		   window.__progressErrorCount++;
		   progressFunction();
	   }
   }
   /************************************
	*
	* 		下载进度相关 Ends
	*
	***********************************/


   /************************************
	*
	* 		上传进度相关 Starts
	*
	***********************************/

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
		   if(window.__isProgressing){
			   seajs.use(['lui/dialog'],function(dialog){
				   dialog.alert(upload_doing_tip_desc);
			   });
			   return;
		   }
		   //修改状态为正在执行
		   var uploadProcess=document.getElementById("div_uploadProcess");
		   uploadProcess.innerHTML="<font color='blue'>"+upload_doing_tip_desc+"</font>";
		   var form=document.getElementsByName("sysTransportImportUploadForm")[0];
		   window.__progressKey =  "upload_" + Com_Parameter.CurrentUserId + "_" + new Date().getTime();
		   $("input[name='progressKey']").val(window.__progressKey);
		   form.target="file_frame";
		   window.__progressErrorCount = 0;
		   window.__isProgressing = true;
		   window.__preProgressMsg = "";
		   doUploadProgressSearch();
		   form.submit();
	   }
   }

   function f(i){
   	 var sum = i || 0;
   	 var add = function (j){
   	 	sum+=j;
   	 	return add;
	 }
	 add.count = function (){
		return sum;
	 }
	 return add;
   }

   /**
	* 更新进度
	*/
   function doUploadProgressSearch(){
	   if(!window.__isProgressing || window.__progressErrorCount > 5) {
	   	return;
	   }
	   seajs.use(["lui/util/env"],function (env){
		   var url = env.fn.formatUrl("/kms/knowledge/kms_knowledge_file_store/kmsKnowledgeFileStoreImportExport.do?method=getExcelHandleProgress")
		   $.ajax({
			   url : url,
			   type : 'POST',
			   dataType : 'json',
			   data : {
				   progressKey: window.__progressKey,
				   preProgressMsg: window.__preProgressMsg
			   },
			   timeout: 10000,
			   success : function(data,textStatus,xhr) {
				   handleProgressData(data,doUploadProgressSearch,updateDoingDescribe,updateCompleteDescribe);
			   },
			   error : function(xhr,textStatus,errorThrown) {
				   window.__progressErrorCount++;
				   if (textStatus == "timeout") {
					   doDownloadProgressSearch();
				   }
			   }
		   });
	   });
   }

   /**
	* 更新描述信息
	* @param msg
	*/
   function updateDoingDescribe(msg){
	   $("#downloadResultDiv").hide();
	   $("#uploadResultDiv").show();
	   var uploadProcess=document.getElementById("div_uploadProcess");
	   uploadProcess.innerHTML="<font color='blue'>"+msg+"</font>";
   }

   function updateCompleteDescribe(msg){
	   $("#downloadResultDiv").hide();
	   $("#uploadResultDiv").show();
	   var uploadProcess=document.getElementById("div_uploadProcess");
	   uploadProcess.innerHTML="<font color='red'>"+msg+"</font>";
   }

	//excel文件上传完毕,显示操作信息
	function callback(result, msg){
		window.__isProgressing = false;
		document.getElementById("div_errorCell").innerHTML=createTableSimple(result,msg);
		var uploadProcess=document.getElementById("div_uploadProcess");
		uploadProcess.innerHTML="<font color='red'>" + msg + "</font>";
	}

   function createTableSimple(result,msg) {
	   var json;
	   try {
		   json = JSON.parse(result);
		   console.log("json",json)
	   } catch (err) {
		   return result;
	   }
	   if(!json) return "";
	   //标题
	   var titles = json.titles;
	   var colLength = titles.length;
	   //内容
	   var rows = json.rows;
	   var htmlStr = "<table class='tb_normal' style='width:100%;'><thead style='background:#ddd;'><tr>";

	   if(titles && rows && rows.length > 0){
		   $.each(titles,function(index,item){
			   htmlStr += "<th style='padding:5px 10px;text-align:center;border-left:1px solid #d1d1d1;'>"+item+"</th>";
		   });
		   htmlStr += "</tr></thead><tbody>";

		   $.each(rows,function(index,item){
			   var row = item.rowNumber;
			   htmlStr += "<tr>";
			   htmlStr += "<td>"+row+"</td>";

			   var errInfos = '';
			   if(item.errorMsg != null || typeof(item.errorMsg) != "undefined"){
				   //解码
				   item.errorMsg=htmlentities.decode(item.errorMsg)
				   errInfos = item.errorMsg.replace(/&/g,"&amp;");
				   errInfos = errInfos.replace(/</g,"&lt;");
				   errInfos = errInfos.replace(/>/g,"&gt;");
				   errInfos = errInfos.replace(/ /g,"&nbsp;");
				   errInfos = errInfos.replace(/\'/g,"&#39;");
				   errInfos = errInfos.replace(/\"/g,"&quot;");
			   }
			   htmlStr += "<td style='color:#FF0000'>"+errInfos+"</td>";
			   htmlStr += "</tr>";
		   });
	   }

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
		$("input[type='file'][name='file']").click(function(event) {
			event.target.value = "";
		})
        $("input[type='file'][name='file']").change(function(event) {
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
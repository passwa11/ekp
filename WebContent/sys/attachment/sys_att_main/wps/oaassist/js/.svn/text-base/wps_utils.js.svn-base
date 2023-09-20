//wps加载项js
Com_IncludeFile("wps_util_ext.js",Com_Parameter.ContextPath + "sys/attachment/sys_att_main/wps/oaassist/js/","js",true);
Com_IncludeFile("webuploader.min.js",Com_Parameter.ContextPath + "sys/attachment/webuploader/","js",true);
Com_IncludeFile("json2.js",Com_Parameter.ContextPath + "sys/attachment/sys_att_main/wps/oaassist/resource/js/utils/","js",true);
Com_IncludeFile("wps_sdk.js",Com_Parameter.ContextPath + "sys/attachment/sys_att_main/wps/oaassist/resource/js/","js",true);
Com_IncludeFile("installWpsaddin.js",Com_Parameter.ContextPath + "sys/attachment/sys_att_main/wps/oaassist/js/","js",true);
Com_IncludeFile("wps.js",Com_Parameter.ContextPath + "sys/attachment/sys_att_main/wps/oaassist/resource/js/","js",true);
Com_IncludeFile("et.js",Com_Parameter.ContextPath + "sys/attachment/sys_att_main/wps/oaassist/resource/js/","js",true);
//Com_IncludeFile("wpp.js",Com_Parameter.ContextPath + "sys/attachment/sys_att_main/wps/oaassist/resource/js/","js",true);
Com_IncludeFile("highlight.pack.js",Com_Parameter.ContextPath + "sys/attachment/sys_att_main/wps/oaassist/resource/highlight/","js",true);

var editWpsEtWppAttMain=[];


function openWpsOAAssit(docId,wpsParam){
	var ekpcookie=getEkpCookie();
	var modelNameFlag = wpsParam['wpsExtAppModel'];
	var url = Com_Parameter.ContextPath+"sys/attachment/sys_att_main/sysAttMain.do?method=getWpsOAasisstUrl"; 
	$.ajax({    
	     type:"post",     
	     url:url,     
	     data:{fileId:docId,wpsExtAppModel:modelNameFlag},
	     async:false,    //用同步方式 
	     success:function(data){
	    	 var results =  eval("("+data+")");
	    	 if(results['downUrl']&&results['downUrl']!=""){
	    		 var downloadUrl=results['downUrl'];
				 userId=results['userId'];
				 var wpsWebDocumentHeart = results['wpsWebDocumentHeart'];
				 extendSession(docId,userId);//增加通讯，在延迟之前立即建立一次通讯
	    	     setInterval(function(){extendSession(docId,userId);},wpsWebDocumentHeart * 1000);//10秒请求一次，防止失效 
	    	     
	    	     var editwps=new Object();
	    	     editwps.type='wps';
	    	     editwps.userId=userId;
	    	     editwps.fdId=docId;
	    	     editwps.modelName=results['modelName'];
	    	     editwps.fdKey=results['fdKey'];
	    	     editWpsEtWppAttMain.push(editwps);

	    	     //新闻管理不需要打印
	    	     if(wpsParam['wpsExtAppModel'] != '' && wpsParam['wpsExtAppModel'] == 'sysNews')
		    	 {
		    	    	 results['canPrint'] = false;
		    	  }

	    	     //会议管理不需要打印
	    	     if(wpsParam['wpsExtAppModel'] != '' && wpsParam['wpsExtAppModel'] == 'imeetingSummary')
		    	 {
		    	    	 results['canPrint'] = false;
		    	  }

	    	     //知识管理不需要打印
	    	     if(wpsParam['wpsExtAppModel'] != '' && wpsParam['wpsExtAppModel'] == 'docKnowledge')
		    	 {
		    	    	 results['canPrint'] = false;
		    	  }

				 var ekpcanPrint = results['canPrint'];
				 if(wpsParam['wpsSysPrintApp'] == 'SysPrintApp'){
					 ekpcanPrint = true;
				 }

	    		 _WpsInvoke([{
	    		        "OpenDoc": {
	    		            "docId": docId, // 文档ID
	    		            "fileName": downloadUrl,
	    		            "openType":{
	    		            	// 文档保护类型，-1：不启用保护模式，0：只允许对现有内容进行修订，
	    		                // 1：只允许添加批注，2：只允许修改窗体域(禁止拷贝功能)，3：只读
	    		            	"protectType": 3
	    		            },
							"uploadPath": Com_Parameter.serverPrefix+"/sys/attachment/sys_att_main/sysAttMain.do?method=uploadWpsOaassist&useId="+results['userId']+"&fdId="+docId+"&wpsOasisstToken="+results['wpsoaassistToken'], // 保存文档上传路径
	    		            "userName":results['userName'],
	    		            "ekpUserId":results['userId'],
	    		            "ekpUserName":results['userName'],
	    		            "ekpAttMainId":results['attMainId'],
	    		            "ekpModelId":results['modelId'],
	    		            "ekpModelName":results['modelName'],
	    		            "ekpAttMainKey":results['fdKey'],
	    		            "ekpcookie":ekpcookie,
	    		            "ekpcanCopy":results['canCopy'],
	    		            "ekpcanPrint":ekpcanPrint,
	    		            "ekpdownload":results['download'],
	    		            "wpsoaassistToken":results['wpsoaassistToken'],
	    		            "ekpServerPrefix":Com_Parameter.serverPrefix,
							"signTrue":wpsParam['signTrue'],
							"bookMarks":wpsParam['bookMarks'],
							"wpsExtAppModel":wpsParam['wpsExtAppModel'],
							"fileOpenType":"read",
							"ekpFileFrom":"OA",
							"wpsSysPrintApp":wpsParam['wpsSysPrintApp'],
							"printMarkContent":wpsParam['printMarkContent'],
							"wpsPrintEdit":wpsParam['wpsPrintEdit'],
							"ekpAttMainId":results['attMainId'],
							"waterText":results['waterText'],
							"watermarkCfg":results['watermarkCfg']
	    		        }
	    		    }])
	    	 }

	     }
	});
}

function editWpsOAAssit(docId,wpsParam){
	var ekpcookie=getEkpCookie();
	var userId="";
	var isRequst = true;
	var modelNameFlag = wpsParam['wpsExtAppModel'];
	var url = Com_Parameter.ContextPath+"sys/attachment/sys_att_main/sysAttMain.do?method=getWpsOAasisstUrl";
	$.ajax({
	     type:"post",
	     url:url,
	     data:{fileId:docId,wpsExtAppModel:modelNameFlag},
	     async:false,    //用同步方式
	     success:function(data){
	    	 var results =  eval("("+data+")");
	    	 if(results['downUrl']&&results['downUrl']!=""){
	    		 var downloadUrl=results['downUrl'];
	    		 userId=results['userId'];
	    		 var wpsWebDocumentHeart = results['wpsWebDocumentHeart'];
	    		 extendSession(docId,userId);//增加通讯，在延迟之前立即建立一次通讯
	    	     setInterval(function(){extendSession(docId,userId);},wpsWebDocumentHeart * 1000);//10秒请求一次，防止失效

	    	     var editwps=new Object();
	    	     editwps.type='wps';
	    	     editwps.userId=userId;
	    	     editwps.fdId=docId;
	    	     editwps.modelName=results['modelName'];
	    	     editwps.fdKey=results['fdKey'];
	    	     editWpsEtWppAttMain.push(editwps);

	    	     var canPrint = true;
	    	   //新闻管理不需要打印
	    	     if(wpsParam['wpsExtAppModel'] != '' && wpsParam['wpsExtAppModel'] == 'sysNews')
		    	 {
	    	    	 canPrint = false;
		    	  }

	    	   //会议管理不需要打印
	    	     if(wpsParam['wpsExtAppModel'] != '' && wpsParam['wpsExtAppModel'] == 'imeetingSummary')
		    	 {
	    	    	 canPrint = false;
		    	  }
	    	     //知识管理不需要打印
	    	     if(wpsParam['wpsExtAppModel'] != '' && wpsParam['wpsExtAppModel'] == 'docKnowledge')
		    	 {
	    	    	 canPrint = false;
		    	  }
	    	     var funcs = [{
					 "OnlineEditDoc": {
						 "docId": docId, // 文档ID
						 "uploadPath": Com_Parameter.serverPrefix+"/sys/attachment/sys_att_main/sysAttMain.do?method=uploadWpsOaassist&useId="+results['userId']+"&fdId="+docId+"&wpsOasisstToken="+results['wpsoaassistToken'], // 保存文档上传路径
						 "fileName": downloadUrl,
						 "userName":results['userName'],
						 "ekpUserId":results['userId'],
						 "ekpUserName":results['userName'],
						 "ekpAttMainId":results['attMainId'],
						 "ekpModelId":results['modelId'],
						 "ekpModelName":results['modelName'],
						 "ekpAttMainKey":results['fdKey'],
						 "ekpcookie":ekpcookie,
						 "ekpcanCopy":true,
						 "ekpcanPrint":canPrint,
						 "ekpdownload":true,
						 "wpsoaassistToken":results['wpsoaassistToken'],
						 "ekpServerPrefix":Com_Parameter.serverPrefix,
						 "redhead":wpsParam['redhead'],
						 "bookMarks":wpsParam['bookMarks'],
						 "nodevalue":wpsParam['nodevalue'],
						 "signTrue":wpsParam['signTrue'],
						 "forceRevisions":wpsParam['forceRevisions'],
						 "saveRevisions":wpsParam['saveRevisions'],
						 "wpsExtAppModel":wpsParam['wpsExtAppModel'],
						 "cleardraft":wpsParam['cleardraft'],
						 "newFlag":wpsParam['newFlag'],
						 "ekpFileFrom":"OA"
					 }
				 }];
				 var success = attWpsAssitHandleEditor(funcs,'lock');
				 if(success){
					 lockQueen.push(editwps);
				 }else{
					 funcs = [{
						 "OpenDoc": {
							 "docId": docId, // 文档ID
							 "fileName": downloadUrl,
							 "openType":{
								 // 文档保护类型，-1：不启用保护模式，0：只允许对现有内容进行修订，
								 // 1：只允许添加批注，2：只允许修改窗体域(禁止拷贝功能)，3：只读
								 "protectType": 3
							 },
							 "uploadPath": Com_Parameter.serverPrefix+"/sys/attachment/sys_att_main/sysAttMain.do?method=uploadWpsOaassist&useId="+results['userId']+"&fdId="+docId+"&wpsOasisstToken="+results['wpsoaassistToken'], // 保存文档上传路径
							 "userName":results['userName'],
							 "ekpUserId":results['userId'],
							 "ekpUserName":results['userName'],
							 "ekpAttMainId":results['attMainId'],
							 "ekpModelId":results['modelId'],
							 "ekpModelName":results['modelName'],
							 "ekpAttMainKey":results['fdKey'],
							 "ekpcookie":ekpcookie,
							 "ekpcanCopy":true,
							 "ekpcanPrint":canPrint,
							 "ekpdownload":true,
							 "wpsoaassistToken":results['wpsoaassistToken'],
							 "ekpServerPrefix":Com_Parameter.serverPrefix,
							 "signTrue":wpsParam['signTrue'],
							 "bookMarks":wpsParam['bookMarks'],
							 "wpsExtAppModel":wpsParam['wpsExtAppModel'],
							 "fileOpenType":"read",
							 "ekpFileFrom":"OA",
							 "wpsSysPrintApp":wpsParam['wpsSysPrintApp'],
							 "printMarkContent":wpsParam['printMarkContent'],
							 "wpsPrintEdit":wpsParam['wpsPrintEdit'],
							 "ekpAttMainId":results['attMainId'],
							 "waterText":results['waterText'],
							 "watermarkCfg":results['watermarkCfg']
						 }
					 }];
					 var operatorName = curOperator;
					 curOperator = null;
					 alert(operatorName+' 正在编辑此文件，将以查看模式打开文件');
				 }
				 _WpsInvoke(funcs);
	    	 }

	     }
	});
}

function editDaXieWPSOAAssit(docId,wpsParam){
	var ekpcookie=getEkpCookie();
	var userId="";
	var isRequst = true;
	var url = Com_Parameter.ContextPath+"sys/attachment/sys_att_main/sysAttMain.do?method=getWpsOAasisstUrl";
	$.ajax({
		type:"post",
		url:url,
		data:{fileId:docId},
		async:false,    //用同步方式
		success:function(data){
			var results =  eval("("+data+")");
			if(results['downUrl']&&results['downUrl']!=""){
				var downloadUrl=results['downUrl'];
				userId=results['userId'];
				 var wpsWebDocumentHeart = results['wpsWebDocumentHeart'];
				extendSession(docId,userId);//增加通讯，在延迟之前立即建立一次通讯
				setInterval(function(){extendSession(docId,userId);},wpsWebDocumentHeart * 1000);//10秒请求一次，防止失效

				var editwps=new Object();
				editwps.type='wps';
				editwps.userId=userId;
				editwps.fdId=docId;
				editwps.modelName=results['modelName'];
				editwps.fdKey=results['fdKey'];
				editWpsEtWppAttMain.push(editwps);

				_WpsInvoke([{
					"OpenDoc": {
						"docId": docId, // 文档ID
						"uploadPath": Com_Parameter.serverPrefix+"/sys/attachment/sys_att_main/sysAttMain.do?method=uploadWpsOaassist&useId="+results['userId']+"&fdId="+docId+"&wpsOasisstToken="+results['wpsoaassistToken'], // 保存文档上传路径
						"fileName": downloadUrl,
						"userName":results['userName'],
						"ekpUserId":results['userId'],
						"ekpUserName":results['userName'],
						"ekpAttMainId":results['attMainId'],
						"ekpModelId":results['modelId'],
						"ekpModelName":results['modelName'],
						"ekpAttMainKey":results['fdKey'],
						"ekpcookie":ekpcookie,
						"ekpcanCopy":true,
						"ekpcanPrint":true,
						"ekpdownload":true,
						"wpsoaassistToken":results['wpsoaassistToken'],
						"ekpServerPrefix":Com_Parameter.serverPrefix,
						"redhead":wpsParam['redhead'],
						"bookMarks":wpsParam['bookMarks'],
						"nodevalue":wpsParam['nodevalue'],
						"signTrue":wpsParam['signTrue'],
						"forceRevisions":wpsParam['forceRevisions'],
						"saveRevisions":wpsParam['saveRevisions'],
						"wpsExtAppModel":wpsParam['wpsExtAppModel'],
						"cleardraft":wpsParam['cleardraft'],
						"newFlag":wpsParam['newFlag'],
						"ekpFileFrom":"OA"
					}
				}]);


			}

		}
	});
}
function openExcelOAAssit(docId){
	var ekpcookie=getEkpCookie();

	var url = Com_Parameter.ContextPath+"sys/attachment/sys_att_main/sysAttMain.do?method=getWpsOAasisstUrl";
	$.ajax({
		type:"post",
		url:url,
		data:{fileId:docId},
		async:false,    //用同步方式
		success:function(data){
			var results =  eval("("+data+")");
			if(results['downUrl']&&results['downUrl']!=""){
				var downloadUrl=results['downUrl'];
				var wpsWebDocumentHeart = results['wpsWebDocumentHeart'];
				userId=results['userId'];
				extendSession(docId,userId);//增加通讯，在延迟之前立即建立一次通讯
				setInterval(function(){extendSession(docId,userId);},wpsWebDocumentHeart * 1000);//10秒请求一次，防止失效

				 var editwps=new Object();
	    	     editwps.type='et';
	    	     editwps.userId=userId;
	    	     editwps.fdId=docId;
	    	     editwps.modelName=results['modelName'];
	    	     editwps.fdKey=results['fdKey'];
	    	     editWpsEtWppAttMain.push(editwps);

				_EtInvoke([{
					"OnlineEditDoc": {
						"docId": docId, // 文档ID
						"fileName": downloadUrl,
						"userName":results['userName'],
						"ekpUserId":results['userId'],
						"ekpUserName":results['userName'],
						"ekpAttMainId":results['attMainId'],
						"ekpModelId":results['modelId'],
						"ekpModelName":results['modelName'],
						"ekpAttMainKey":results['fdKey'],
						"ekpcookie":ekpcookie,
						"ekpcanCopy":results['canCopy'],
						"ekpcanPrint":results['canPrint'],
						"ekpdownload":results['download'],
						"wpsoaassistToken":results['wpsoaassistToken'],
						"ekpServerPrefix":Com_Parameter.serverPrefix,
						"fileOpenType":"read",
						"ekpFileFrom":"OA"
					}
				}])
			}

		}
	});
}
function openEtOAAssit(docId){
	var ekpcookie=getEkpCookie();

	var url = Com_Parameter.ContextPath+"sys/attachment/sys_att_main/sysAttMain.do?method=getWpsOAasisstUrl";
	$.ajax({
		type:"post",
		url:url,
		data:{fileId:docId},
		async:false,    //用同步方式
		success:function(data){
			var results =  eval("("+data+")");
			if(results['downUrl']&&results['downUrl']!=""){
				var downloadUrl=results['downUrl'];
				var wpsWebDocumentHeart = results['wpsWebDocumentHeart'];
				userId=results['userId'];
				extendSession(docId,userId);//增加通讯，在延迟之前立即建立一次通讯
				setInterval(function(){extendSession(docId,userId);},wpsWebDocumentHeart * 1000);//10秒请求一次，防止失效

				 var editwps=new Object();
	    	     editwps.type='et';
	    	     editwps.userId=userId;
	    	     editwps.fdId=docId;
	    	     editwps.modelName=results['modelName'];
	    	     editwps.fdKey=results['fdKey'];
	    	     editWpsEtWppAttMain.push(editwps);

				_EtInvoke([{
					"OpenDoc": {
						"docId": docId, // 文档ID
						"fileName": downloadUrl,
						"userName":results['userName'],
						"ekpUserId":results['userId'],
						"ekpUserName":results['userName'],
						"ekpAttMainId":results['attMainId'],
						"ekpModelId":results['modelId'],
						"ekpModelName":results['modelName'],
						"ekpAttMainKey":results['fdKey'],
						"ekpcookie":ekpcookie,
						"ekpcanCopy":results['canCopy'],
						"ekpcanPrint":results['canPrint'],
						"ekpdownload":results['download'],
						"wpsoaassistToken":results['wpsoaassistToken'],
						"ekpServerPrefix":Com_Parameter.serverPrefix,
						"fileOpenType":"read",
						"ekpFileFrom":"OA"
					}
				}])
			}

		}
	});
}

function editExcelOAAssit(docId){
	var ekpcookie=getEkpCookie();
	var userId="";
	var isRequst = true;
	var url = Com_Parameter.ContextPath+"sys/attachment/sys_att_main/sysAttMain.do?method=getWpsOAasisstUrl";
	$.ajax({
		type:"post",
		url:url,
		data:{fileId:docId},
		async:false,    //用同步方式
		success:function(data){
			var results =  eval("("+data+")");
			if(results['downUrl']&&results['downUrl']!=""){
				var downloadUrl=results['downUrl'];
				userId=results['userId'];
				var wpsWebDocumentHeart = results['wpsWebDocumentHeart'];
				extendSession(docId,userId);//增加通讯，在延迟之前立即建立一次通讯
				setInterval(function(){extendSession(docId,userId);},wpsWebDocumentHeart * 1000);//10秒请求一次，防止失效

				 var editwps=new Object();
	    	     editwps.type='et';
	    	     editwps.userId=userId;
	    	     editwps.fdId=docId;
	    	     editwps.modelName=results['modelName'];
	    	     editwps.fdKey=results['fdKey'];
	    	     editWpsEtWppAttMain.push(editwps);

	    	     var funcs = [{
					 "OpenDoc": {
						 "docId": docId, // 文档ID
						 "uploadPath": Com_Parameter.serverPrefix+"/sys/attachment/sys_att_main/sysAttMain.do?method=uploadWpsOaassist&useId="+results['userId']+"&fdId="+docId+"&wpsOasisstToken="+results['wpsoaassistToken'], // 保存文档上传路径
						 "fileName": downloadUrl,
						 showButton: "btnSaveFile",
						 "userName":results['userName'],
						 "ekpUserId":results['userId'],
						 "ekpUserName":results['userName'],
						 "ekpAttMainId":results['attMainId'],
						 "ekpModelId":results['modelId'],
						 "ekpModelName":results['modelName'],
						 "ekpAttMainKey":results['fdKey'],
						 "ekpcookie":ekpcookie,
						 "ekpcanCopy":true,
						 "ekpcanPrint":true,
						 "ekpdownload":true,
						 "wpsoaassistToken":results['wpsoaassistToken'],
						 "ekpServerPrefix":Com_Parameter.serverPrefix,
						 "ekpFileFrom":"OA"
					 }
				 }];
				var success = attWpsAssitHandleEditor(funcs,'lock');
				if(success){
					lockQueen.push(editwps);
				}else{
					funcs = [{
						"OnlineEditDoc": {
							"docId": docId, // 文档ID
							"fileName": downloadUrl,
							"userName":results['userName'],
							"ekpUserId":results['userId'],
							"ekpUserName":results['userName'],
							"ekpAttMainId":results['attMainId'],
							"ekpModelId":results['modelId'],
							"ekpModelName":results['modelName'],
							"ekpAttMainKey":results['fdKey'],
							"ekpcookie":ekpcookie,
							"ekpcanCopy":results['canCopy'],
							"ekpcanPrint":results['canPrint'],
							"ekpdownload":results['download'],
							"wpsoaassistToken":results['wpsoaassistToken'],
							"ekpServerPrefix":Com_Parameter.serverPrefix,
							"fileOpenType":"read",
							"ekpFileFrom":"OA"
						}
					}];
					var operatorName = curOperator;
					curOperator = null;
					alert(operatorName+' 正在编辑此文件，将以查看模式打开文件');
				}
				_EtInvoke(funcs);
			}
		}
	});
}
function openPptOAAssit(docId){
	var ekpcookie=getEkpCookie();

	var url = Com_Parameter.ContextPath+"sys/attachment/sys_att_main/sysAttMain.do?method=getWpsOAasisstUrl";
	$.ajax({
		type:"post",
		url:url,
		data:{fileId:docId},
		async:false,    //用同步方式
		success:function(data){
			var results =  eval("("+data+")");
			if(results['downUrl']&&results['downUrl']!=""){
				var downloadUrl=results['downUrl'];
				_WppInvoke([{
					"OnlineEditDoc": {
						"docId": docId, // 文档ID
						"fileName": downloadUrl,
						"userName":results['userName'],
						"ekpUserId":results['userId'],
						"ekpUserName":results['userName'],
						"ekpAttMainId":results['attMainId'],
						"ekpModelId":results['modelId'],
						"ekpModelName":results['modelName'],
						"ekpAttMainKey":results['fdKey'],
						"ekpcookie":ekpcookie,
						"ekpcanCopy":results['canCopy'],
						"ekpcanPrint":results['canPrint'],
						"ekpdownload":results['download'],
						"wpsoaassistToken":results['wpsoaassistToken'],
						"ekpServerPrefix":Com_Parameter.serverPrefix,
						"fileOpenType":"read",
						"ekpFileFrom":"OA"
					}
				}])
			}

		}
	});
}

function editPptOAAssit(docId){
	var ekpcookie=getEkpCookie();
	var userId="";
	var isRequst = true;
	var url = Com_Parameter.ContextPath+"sys/attachment/sys_att_main/sysAttMain.do?method=getWpsOAasisstUrl";
	$.ajax({
		type:"post",
		url:url,
		data:{fileId:docId},
		async:false,    //用同步方式
		success:function(data){
			var results =  eval("("+data+")");
			if(results['downUrl']&&results['downUrl']!=""){
				var downloadUrl=results['downUrl'];
				userId=results['userId'];
				extendSession(docId,userId);//增加通讯，在延迟之前立即建立一次通讯
				setInterval(function(){extendSession(docId,userId);},10 * 1000);//10秒请求一次，防止失效

				var editwps=new Object();
	    	     editwps.type='wpp';
	    	     editwps.userId=userId;
	    	     editwps.fdId=docId;
	    	     editwps.modelName=results['modelName'];
	    	     editwps.fdKey=results['fdKey'];
	    	     editWpsEtWppAttMain.push(editwps);
				
				 /*window.addEventListener('unload', function(){
	    	    		var url = Com_Parameter.serverPrefix+"/sys/attachment/sys_att_main/sysAttMain.do?method=expiredWpsOaassitEditMark&useId="+userId+"&fdId="+docId;
	    	    		if(navigator && navigator.sendBeacon){
	    	    			navigator.sendBeacon(url, {"d":new Date()});
	    	    		}else{
	    	    			var client = new XMLHttpRequest();
	    	    		    client.open("POST", url, false); // 第三个参数表明是同步的 xhr
	    	    		    client.setRequestHeader("Content-Type", "text/plain;charset=UTF-8");
	    	    		    client.send({"d":"1"});
	    	    		}
	    	     }, false);*/
				
				_WppInvoke([{
					"OpenDoc": {
						"docId": docId, // 文档ID
						"uploadPath": Com_Parameter.serverPrefix+"/sys/attachment/sys_att_main/sysAttMain.do?method=uploadWpsOaassist&useId="+results['userId']+"&fdId="+docId+"&wpsOasisstToken="+results['wpsoaassistToken'], // 保存文档上传路径
						"fileName": downloadUrl,
						showButton: "btnSaveFile",
						"userName":results['userName'],
						"ekpUserId":results['userId'],
						"ekpUserName":results['userName'],
						"ekpAttMainId":results['attMainId'],
						"ekpModelId":results['modelId'],
						"ekpModelName":results['modelName'],
						"ekpAttMainKey":results['fdKey'],
						"ekpcookie":ekpcookie,
						"ekpcanCopy":true,
						"ekpcanPrint":true,
						"ekpdownload":true,
						"wpsoaassistToken":results['wpsoaassistToken'],
						"ekpServerPrefix":Com_Parameter.serverPrefix,
						"ekpFileFrom":"OA"
					}
				}]);
				
				
			}
			
		}
	});
}

function newWpsOAAssit(fdKey,fdModelId,fdModelName,fdTemplateModelId,fdTemplateModelName,fdTemplateModelKey){
	var ekpcookie=getEkpCookie();
	var userId="";
	var isRequst = true;
	var url = Com_Parameter.ContextPath+"sys/attachment/sys_att_main/sysAttMain.do?method=addWpsOaassistOnlineFile";
	$.ajax({    
	     type:"post",     
	     url:url,     
	     data:{fdModelId:fdModelId,fdModelName:fdModelName,fdKey:fdKey,fdTemplateModelId:fdTemplateModelId,fdTemplateModelName:fdTemplateModelName,fdTemplateModelKey:fdTemplateModelKey},  
	     async:false,    //用同步方式 
	     success:function(data){
	    	if (data){
 	    		var results =  eval("("+data+")");
			    if(results['editOnlineAttId']!=null){
			    	fdAttMainId = results['editOnlineAttId'];
			    	editWpsOAAssit(fdAttMainId);
				}
			}
	    	 
	     }
	});
}

function getEkpCookie(){
	$.ajax({
		async : false,
		dataType : "script",
		url : Com_Parameter.ResPath+"js/session.jsp?_="+new Date().getTime()
	});
	var str = "";
	if(window.getSessionId){
		str = getSessionId();
	}
	return str;
}


function wpsOaasistOnUnload(docId,userId){
	var url = Com_Parameter.serverPrefix+"/sys/attachment/sys_att_main/sysAttMain.do?method=expiredWpsOaassitEditMark";
	if(navigator && navigator.sendBeacon){
		navigator.sendBeacon(url, {"useId":useId,"fdId":docId});
	}else{
		var client = new XMLHttpRequest();
	    client.open("POST", url, false); // 第三个参数表明是同步的 xhr
	    client.setRequestHeader("Content-Type", "text/plain;charset=UTF-8");
	    client.send({"useId":useId,"fdId":docId});
	}
}

function extendSession(docId,useId){
    $.ajax({
        url: Com_Parameter.ContextPath+"sys/attachment/sys_att_main/sysAttMain.do?method=extendWpsOaassitEditMark",
        data:{fdId:docId,useId:useId}
    });
}

/**
 * 检测是否安装了wps，根据promise返回值判断
 */
function checkWpsInstalled() {
    var httpPromise = promiseAjax('GET', 'http://127.0.0.1:58890/transferEcho/runParams');

    return new Promise(function (resolve, reject) {
        httpPromise.then(function (text) { // 如果AJAX成功，获得响应内容
            resolve(text);
        }, function (status) { // 如果AJAX失败，获得响应代码
            console.log('ERROR: ' + status);
            var httpsPromise = promiseAjax('GET', 'https://127.0.0.1:58891/transferEcho/runParams');
            httpsPromise.then(function (text) {
                resolve(text);
            }, function (status) {
                console.log('ERROR: ' + status);
                _WpsInvoke([{
                    "checkWps": {}
                }], function (cb) {
                    if (cb) {
                        if (cb.status == 0) {
                            resolve(cb.message);
                        } else {
                            alert("未安装wps或wps服务未启动，请重试一次：" + cb.message)
                            reject(cb.status);
                        }
                    } else {
                        alert("未安装wps");
                        reject(status);
                    }
                });
            });
        });
    });
}

// 校验配置项域名与页面域名来源是否一致
function checkDomain() {
//    var isDomainValid = true;
//    var config_domain = wps.PluginStorage.getItem("config_domain");
//    if (document.domain != config_domain) {
//        alert("加载项域名配置与请求域名不一致，请检查配置")
//        isDomainValid = false;
//    }
//    return isDomainValid;
	// 暂时去掉域名校验
	return true;
}

function checkWpsOaassist(userId, docId) {
    var url = Com_Parameter.serverPrefix + "/sys/attachment/sys_att_main/sysAttMain.do?method=checkWpsOaassist&useId=" + userId + "&fdId=" + docId;
    // alert("checkWpsOaassist,userId=" + userId + ",docId=" + docId);
    var promise = promiseAjax('GET', url);
    return new Promise(function (resolve, reject) {
        promise.then(function (text) { // 如果AJAX成功，获得响应内容
            var results = eval("(" + text + ")");
            if (results["flag"] == "error") {
                // alert(results["message"])
                reject("ekp会话已过期");
            } else {
                resolve(results);
            }
        }, function (status) { // 如果AJAX失败，获得响应代码
            console.log('ERROR: ' + status);
            reject("请求失败")
        });
    });
}

// ajax函数将返回Promise对象:
function promiseAjax(method, url, data) {
    var request = new XMLHttpRequest();
    return new Promise(function (resolve, reject) {
        request.onreadystatechange = function () {
            if (request.readyState === 4) {
                if (request.status === 200) {
                    resolve(request.responseText);
                } else {
                    reject(request.status);
                }
            }
        };
        request.open(method, url);
        request.send(data);
    });
}

function WPSOaassist_Attachment_Submit(docId,userId) {
	var flag = false;
	$.ajax({   
		 url : Com_Parameter.serverPrefix+"/sys/attachment/sys_att_main/sysAttMain.do?method=expiredWpsOaassitEditMark&useId="+userId+"&fdId="+docId,  
	     async:false,    //用同步方式 
	     success:function(data){
	    	if (data){
	    		var results =  eval("("+data+")");
	    		if(results['status']){
	    			flag = true;
	    		}
			}
		 }    
   });
   return flag;
	
}

function openByWpsOaassist(docId){
	var url = Com_Parameter.ContextPath+"sys/attachment/sys_att_main/sysAttMain.do?method=getWpsOAasisstUrl"; 
	$.ajax({    
	     type:"post",     
	     url:url,     
	     data:{fileId:docId},  
	     async:false,    //用同步方式 
	     success:function(data){
	    	 var results =  eval("("+data+")");
	    	 var fileName=results['attFileName'];
	    	 var fileExt = fileName.substring(fileName.lastIndexOf("."));
			 if(fileExt.toLowerCase()==".docx"||fileExt.toLowerCase()==".doc"||fileExt.toLowerCase()==".wps"){
				 openWpsOAAssit(docId,"");
			 }
			 if(fileExt==".xlsx"||fileExt==".xls"){
				openExcelOAAssit(docId);
			 }
			 if(fileExt.toLowerCase()==".et"||fileExt==".XLSX"||fileExt==".XLS"){
					openEtOAAssit(docId);
			 }
			 
	     }
	});
}
Com_AddEventListener(window, "beforeunload", function(e) {
	for (var int = 0; int < editWpsEtWppAttMain.length; int++) {
		var _json=editWpsEtWppAttMain[int];
		var execFlag = true;
		var agreeFlag = false;
		if (_json.modelName == 'com.landray.kmss.km.agreement.model.KmAgreementApply' 
				|| _json.modelName == 'com.landray.kmss.km.agreement.model.KmAgreementModel'
				||_json.modelName == 'com.landray.kmss.km.sample.model.KmSampleModel'
				|| _json.modelName == 'com.landray.kmss.km.sample.model.KmSampleOutline') {
			agreeFlag = true;
		}
		if (_json.fdKey == 'mainOnline' && agreeFlag) {
			//合同正文使用wps才执行操作
			if (formInitData && formInitData.ctrlType != 'wps/oaassist') {
				execFlag = false;
			}
		}
		if(_json.type=='wps' && execFlag){
			CloseWpsClient(_json.fdId);
				var url = Com_Parameter.serverPrefix+"/sys/attachment/sys_att_main/sysAttMain.do?method=expiredWpsOaassitEditMark&userId="+_json.userId+"&fdId="+_json.fdId+"&type="+_json.type;
				if(navigator && navigator.sendBeacon){
					navigator.sendBeacon(url, {"d":new Date()});
				}else{
					var client = new XMLHttpRequest();
				    client.open("POST", url, true); // 第三个参数表明是同步的 xhr
				    client.setRequestHeader("Content-Type", "text/plain;charset=UTF-8");
				    client.send({"d":"1"});
				}
				
		}
		if(_json.type=='et' && execFlag){
			CloseEtClient(_json.fdId);
				var url = Com_Parameter.serverPrefix+"/sys/attachment/sys_att_main/sysAttMain.do?method=expiredWpsOaassitEditMark&userId="+_json.userId+"&fdId="+_json.fdId+"&type="+_json.type;
		   		if(navigator && navigator.sendBeacon){
		   			navigator.sendBeacon(url, {"d":new Date()});
		   		}else{
		   			var client = new XMLHttpRequest();
		   		    client.open("POST", url, true); // 第三个参数表明是同步的 xhr
		   		    client.setRequestHeader("Content-Type", "text/plain;charset=UTF-8");
		   		    client.send({"d":"1"});
		   		}
		}
		
	}

});

/*针对公文使用*/
function refreshPage(){
	try{
		setTimeout(function(){
			refreshGwPage();
		}, 1000);

	} catch(e){
		
	}
	
}



    $('.ld-remember-modal-cancel').click(function(){
        $('.ld-remember-modal').removeClass('ld-remember-modal-show')
        ableScroll()
    });
    function editMobileLink(){
    		
    }
    $(document).ready(function(){
      if($("input[name='version']").val()){
    	setTimeout(function(){
    		var ua=navigator.userAgent.toLowerCase();
        	if(ua.match(/MicroMessenger/i) == "micromessenger"
        		||(ua.match(/MicroMessenger/i) == 'micromessenger' && ua.match(/wxwork/i) == 'wxwork')){ //个人微信或者企业微信
      		  	var _data = {
      				url : location.href.split('#')[0]
      			};
      		  	if(ua.match(/MicroMessenger/i) == 'micromessenger' && ua.match(/wxwork/i) == 'wxwork'){ //企业微信
      		  		_data.wxType='qywx';
      		  	}else if(ua.match(/MicroMessenger/i) == "micromessenger"){//个人微信
      		  		_data.wxType='wx';
      		  	}
      		  	// 获取微信签名
      			$.ajax({ 
      				url : Com_Parameter.ContextPath + 'fssc/common/fssc_common_data/fsscCommonData.do?method=getWxSign',
      				data : _data,
      				async:false,
    				type:"post",
      				success : function(res) {
      					var result = JSON.parse(res);
      					if (result.result == "success") {
      						wxConfig(result.timestamp, result.nonceStr, result.signature,result.appid);
      						wx.ready(function (){
      							
      						});
      						wx.error(function(res){
      							alert('config失败,请检查微信配置'+JSON.stringify(res))}
      						);
      					}else{
      						console.log(result.message);
      					}
      				},
      				error:function(res){
      					console.log(res);
      				}
      			});
      	   }
    	}, 1000 );
	    }
    });
    // 记一笔
    function rememberOne(){
        $('.ld-remember-modal').addClass('ld-remember-modal-show')
        forbiddenScroll()
    }
    function ableScroll(){
    		document.body.style.overflow='auto';
    }
    function forbiddenScroll(){
		document.body.style.overflow='hidden';
    }
   //----------------------------发票扫描start --------------------------------------------//
   function scanQrCode(){
	   ableScroll();
	   var ua = navigator.userAgent.toLowerCase();
	   if(kk.isKK()){  //蓝凌KK
		   scanQrCodeInKK();
	   } else if(!(dd.env.platform=="notInDingTalk")){  //阿里钉钉
		   scanQrCodeInDD();
	   }else if(ua.match(/MicroMessenger/i) == "micromessenger"){ //微信
		   scanCodeInWx();
	   }
   }
   function wxConfig(_timestamp, _nonceStr, _signature,_appid) {
	   wx.config({
		   	beta: true,// 必须这么写，否则wx.invoke调用形式的jsapi会有问题
			debug : false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
			appId : _appid, // 必填，公众号的唯一标识 
			timestamp : _timestamp, // 必填，生成签名的时间戳
			nonceStr : _nonceStr, // 必填，生成签名的随机串
			signature : _signature,// 必填，签名，见附录1
			jsApiList : ['scanQRCode','chooseImage','getLocalImgData','uploadImage','downloadImage','chooseInvoice']  //扫一扫，选择图片或者拍照
		});
	}
	function scanCodeInWx() {
		wx.scanQRCode({
			needResult : 1,  //直接返回
			scanType : [ "qrCode", "barCode" ],
			success : function(res) {
				var result = res.resultStr;
				var code =   result.split(",");
				   var params = {
						  "fplx":code[1],
				          "fdInvoiceNo":code[3],
				          "fdInvoiceCode":code[2],
				          "fdInvoiceMoney":code[4],
				          "fdInvoiceDate":code[5],
				          "fdCheckCode":code[6].substring(code[6].length-6)
					};
				  //保存发票信息
				  var invoices = JSON.stringify(params);
				   $.ajax({
						url:Com_Parameter.ContextPath + 'fssc/mobile/fssc_mobile_note/fsscMobileNote.do?method=scanQrCode',
						data:{fdInvoiceNo:invoices},
						async:false,
						type:"post",
						success:function(data){
							var rtn = JSON.parse(data);
							if(rtn.result=='success'){
								var params = JSON.stringify(rtn.data);
								window.open(formInitData['LUI_ContextPath']+"/fssc/mobile/fssc_mobile_note/fsscMobileNote.do?method=add&params="+encodeURIComponent(params)+"&flag=isScan", '_self');
							} else {
								jqtoast(rtn.message);
							}
						}
					});
			},
			fail : function(res) {
				alert("error"+JSON.stringify(res));

			}
		});
	}
   function scanQrCodeInKK(){
	   kk.scaner.scanTDCode(function(res){
		   var code =   res.code.split(",");
		   var params = {
				  "fplx":code[1],
		          "fdInvoiceNo":code[3],
		          "fdInvoiceCode":code[2],
		          "fdInvoiceMoney":code[4],
		          "fdInvoiceDate":code[5],
		          "fdCheckCode":code[6]
			};
		  //保存发票信息
		  var invoices = JSON.stringify(params);
		   $.ajax({
				url:Com_Parameter.ContextPath + 'fssc/mobile/fssc_mobile_note/fsscMobileNote.do?method=scanQrCode',
				data:{fdInvoiceNo:invoices},
				async:false,
				type:"post",
				success:function(data){
					var rtn = JSON.parse(data);
					if(rtn.result=='success'){
						var params = JSON.stringify(rtn.data);
						window.open(formInitData['LUI_ContextPath']+"/fssc/mobile/fssc_mobile_note/fsscMobileNote.do?method=add&params="+encodeURIComponent(params)+"&flag=isScan", '_self');
					} else {
						jqtoast(rtn.message);
					}
				}
			});
		});
   }
   
   function scanQrCodeInDD(){
	   dd.ready(function () {
	   dd.biz.util.scan({
		   type: 'all',
		   onSuccess:function (res) {
     		  var code = res.text.split(",");
     		  var params = {
     				  "fplx":code[1],
     		          "fdInvoiceNo":code[3],
     		          "fdInvoiceCode":code[2],
     		          "fdInvoiceMoney":code[4],
     		          "fdInvoiceDate":code[5],
     		          "fdCheckCode":code[6]
     			   };
     		  var invoices = JSON.stringify(params);
     		   $.ajax({
     				url:Com_Parameter.ContextPath + 'fssc/mobile/fssc_mobile_note/fsscMobileNote.do?method=scanQrCode',
     				data:{fdInvoiceNo:invoices},
     				async:false,
					type:"post",
     				success:function(data){
     					var rtn = JSON.parse(data);
     				 	if(rtn.result=='success'){
     				 		var params = JSON.stringify(rtn.data);
     						window.open(formInitData['LUI_ContextPath']+"/fssc/mobile/fssc_mobile_note/fsscMobileNote.do?method=add&params="+encodeURIComponent(params)+"&flag=isScan", '_self');
     					} else {
     						jqtoast(rtn.message);
     					}
     				}
     			}); 
           },
           onFail: function (err) {
        	   $('#ld-main').removeClass('#ld-main');
           }
	   });
	 }); 
   }
   //----------------------------发票扫描end--------------------------------------------//
    
   //----------------------------微信卡包--------------------------------------------//
   function chooseByWeixin(){
	   var ua=navigator.userAgent.toLowerCase();
	   if(kk.isKK()){
			kk.invoice.chooseByWeixin(function (args) {
				var user_id=args.user_id;
				var item_code_list = JSON.stringify(args.item_code_list);
				var item_list = encodeURI(JSON.stringify(args.item_list), "utf-8");
				$.ajax({
					url:Com_Parameter.ContextPath + 'fssc/wxcard/fssc_wxcard_invoice/fsscWxcardInvoice.do?method=addWxcardInvoice',
					data:{user_id:user_id,item_code_list:item_code_list,item_list:item_list},
					async:false,
					type:"post",
					success:function(data){
						var rtn = JSON.parse(data);
						if(rtn.result=='success'){
							window.open(formInitData['LUI_ContextPath']+'/fssc/mobile/fssc_mobile_note/fsscMobileNote.do?method=data','_self');
						} else {
							jqtoast(rtn.message);
							$('.ld-remember-modal').removeClass('ld-remember-modal-show');
						}
					}
				});
			}, function (code, msg) {
			  console.log('通过微信选择发票失败，失败回调 code: ' + code +' ,msg: ' + msg );
			});
	   }else if(ua.match(/MicroMessenger/i) == "micromessenger"
   		||(ua.match(/MicroMessenger/i) == 'micromessenger' && ua.match(/wxwork/i) == 'wxwork')){ //个人微信或者企业微信
 		  	var _data = {
 				url : location.href.split('#')[0]
 			};
 		  	if(ua.match(/MicroMessenger/i) == 'micromessenger' && ua.match(/wxwork/i) == 'wxwork'){ //企业微信
 		  		_data.wxType='qywx';
 		  	}else if(ua.match(/MicroMessenger/i) == "micromessenger"){//个人微信
 		  		_data.wxType='wx';
 		  	}
 		  	// 获取微信签名
 			$.ajax({ 
 				url : Com_Parameter.ContextPath + 'fssc/wxcard/fssc_wxcard_invoice/fsscWxcardInvoice.do?method=getWxCardSign',
 				data : _data,
 				async:false,
				type:"post",
 				success : function(res) {
 					var result = JSON.parse(res);
 					if (result.result == "success") {
						 wx.invoke('chooseInvoice', {
 					        'timestamp': result.timestamp, 
 					        'nonceStr': result.nonceStr, 
 					        'signType': 'SHA1', // 签名方式，默认'SHA1'
 					        'cardSign':   result.cardSign // 卡券签名
 					        }, function(chooseData) {
 					        	if(chooseData.err_msg&&((chooseData.err_msg).split(':')[1]=='ok'||chooseData.err_msg=='ok')){//选择成功,安卓和ios返回信息不一致
				        			var invoiceArr=JSON.parse(chooseData.choose_invoice_info);
 					        		var carIdsParams=[];
 					        		for(var k=0;k<invoiceArr.length;k++){
 					        			carIdsParams.push({
 					        				'card_id':invoiceArr[k].card_id,
 					        				'encrypt_code':invoiceArr[k].encrypt_code, 					        			
 					        			});
 					        		}
				        			//获取发票信息并保存
				        			$.ajax({
				        				url : formInitData['LUI_ContextPath']+'/fssc/wxcard/fssc_wxcard_invoice/fsscWxcardInvoice.do?method=getWxCardInvoice',
				        				data : {params:JSON.stringify(carIdsParams),accessToken:result.accessToken,initData:JSON.stringify(_data)},
				        				async:false,
				        				type:"post",
				        				success : function(data) {
				        					var rtn = JSON.parse(data);
	        								if(rtn.result=='success'){
	        									window.open(formInitData['LUI_ContextPath']+'/fssc/mobile/fssc_mobile_note/fsscMobileNote.do?method=data','_self');
	        								} else {
	        									jqtoast(rtn.message);
	        									$('.ld-remember-modal').removeClass('ld-remember-modal-show');
	        								}
				        				},
				        				error:function(res){
				        					console.log(res);
				        				}
				        			});
 					        	}
 					       });
 					}else{
 						console.log(result.message);
 					}
 				},
 				error:function(res){
 					alert(JSON.stringify(res));
 				}
 			});
 	   } else {
			alert("请用KK/微信操作");
	   }	
  }
  
   //---------------------------拍照识别start--------------------------------------------//
   
   $(function(){
		 var file  =  $('<input type=\"file\" id=\"attachId\" name=\"file\" multiple=\"multiple\" >');//附件上传
		 
		  $('#getPhoto').click(function(){
			   if(kk.isKK()||navigator.userAgent.toLowerCase().match(/MicroMessenger/i) == "micromessenger"){
				   //使用kk、微信附件上传
				   document.getElementById("ld-turnUpCamera").style.display="";
			   } else {
				   //附件上传
				   file.click();
			   }
			   $(file).val('');
		   });
		 
		 file.change(function(e){
			var select_file = file[0].files;
			 $.ajax({
					url:formInitData['LUI_ContextPath']+'/fssc/mobile/fssc_mobile_note/fsscMobileNote.do?method=getGeneratorId',
					data:{length:select_file.length},
					async:false,
					type:"post",
					success:function(data){
						var rtn = JSON.parse(data);
						var attid = rtn.fdIds.split(";");
						$("[class*='ld-remember-modal']").removeClass('ld-remember-modal-show');
						for(var i=0;i<attid.length-1;i++){
							upload(select_file[i],attid[i]);
						}
						$(file).val('');
					}
			  }); 
		  })
	 })
	 
   //取消
   $('.ld-turnUpCamera-modal-content-btn').click(function(){
	   document.getElementById("ld-turnUpCamera").style.display="none";
   });
   
   function getPhoto(sourceType){
	  document.getElementById("ld-turnUpCamera").style.display="none";
	  var filename = '';
	  var fileId = "";
	  if(kk.isKK()){
		  kk.media.getPicture({
		    sourceType: sourceType,
		    destinationType: 'data',
		    encodingType: 'jpg',
		    chooseOrignPic:true,
		    quality:100
		  }, function(res){
			   $.ajax({
					url:formInitData['LUI_ContextPath']+'/fssc/mobile/fssc_mobile_note/fsscMobileNote.do?method=getGeneratorId',
					data:{},
					async:false,
					type:"post",
					success:function(data){
						var rtn = JSON.parse(data);
						fileId = rtn.fdId;
					}
				});
			   filename='pic_'+new Date().getTime()+".jpg";
			   $('.ld-remember-modal').removeClass('ld-remember-modal-show');
			   document.getElementById("ld-main").style.display="";
			   dataURLtoFile(res.imageData,'image/jpeg',fileId,filename);
		  }, function(code, msg){
		      console.log('错误信息:' + msg + ', 错误代码:' + code);
		  });
		  
	  }else if(navigator.userAgent.toLowerCase().match(/MicroMessenger/i) == "micromessenger"){//微信
		  wx.chooseImage({
			  count: 1, // 默认9,微信浏览器只会刷新页面一次，导致数据展现有误，限定展现1次
			  sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
			  sourceType: [sourceType], // 可以指定来源是相册还是相机，默认二者都有
			  success: function (res) {
				  var localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
				  $('.ld-remember-modal').removeClass('ld-remember-modal-show');
				  document.getElementById("ld-main").style.display="";
				  for(var n=0;n<localIds.length;n++){
					  var localId=localIds[n];
					  $.ajax({
							url:formInitData['LUI_ContextPath']+'/fssc/mobile/fssc_mobile_note/fsscMobileNote.do?method=getGeneratorId',
							data:{},
							async:false,
							type:"post",
							success:function(data){
								var rtn = JSON.parse(data);
								fileId = rtn.fdId;
								filename='pic_'+new Date().getTime()+".jpg";
								urlTobase64(localId,fileId,filename);
							}
						});
				  }
			  },
			  fail:function(res){
				  alert(res.errMsg);
			  }
			});
	  } else {
		 alert("请用KK/微信操作");
	  }
   }
	//附件数据转换
   function dataURLtoFile(dataURI, type,id,name) {
       var binary = atob(dataURI.split(',')[1]);
       var array = [];
       for(var i = 0; i < binary.length; i++) {
         array.push(binary.charCodeAt(i));
       }
       var blob = new Blob([new Uint8Array(array)], {type:type });
       var reader = new FileReader();
       reader.readAsArrayBuffer(blob);
       reader.onload = function(){
           var binary = this.result;
           binary.name=name
           //图片上传
           upload(binary,id);
   	   }
    }
   //微信获取到的本地图片地址转为base64数据
   function urlTobase64(url,id,name){
	   wx.checkJsApi({
		    jsApiList: ['getLocalImgData'], // 需要检测的JS接口列表，所有JS接口列表见附录2,
		    success: function(res) {
		        // 以键值对的形式返回，可用的api值true，不可用为false
		        // 如：{"checkResult":{"chooseImage":true},"errMsg":"checkJsApi:ok"}
		        if (res.checkResult.getLocalImgData) {
		            wx.getLocalImgData({
		            	 localId: url,//图片的本地ID
			   	         success: function (res) {
			   	             var localData = res.localData;
			   	             if (localData.indexOf('data:image') != 0) {                       
			                        //判断是否有这样的头部                                               
			                        localData = 'data:image/jpeg;base64,' +  localData;                    
			   	             }                    
			   	             localData = localData.replace(/\r|\n/g, '').replace('data:image/jgp', 'data:image/jpeg'); // 此处的localData 就是你所需要的base64位
			   	             dataURLtoFile(localData,'image/jpeg',id,name);
			   	         },
			   		     fail:function(res){
			   		    	 alert(res.errMsg);
			   		     }
		            });
		        } else {
		            wx.uploadImage({
		                localId: url, // 需要上传的图片的本地ID，由chooseImage接口获得
		                isShowProgressTips: 0, // 默认为1，显示进度提示
		                success: function (res) {
		                    var serverId = res.serverId; // 返回图片的服务器端ID
		                    // 获取微信服务器图片base64
		          			$.ajax({ 
		          				url : Com_Parameter.ContextPath + 'fssc/common/fssc_common_data/fsscCommonData.do?method=getLocalImgData',
		          				data : {serverId:serverId},
		          				async:false,
		    					type:"post",
		          				success : function(data) {
		          					data= JSON.parse(data);
		          					if(data.result=='success'&&data.data){
		          						var localData = "data:image/png;base64,"+data.data; // 此处的localData 就是你所需要的base64位 
		   			   	             	dataURLtoFile(localData,'image/jpeg',id,name);
		          					}else{
		          						alert('获取图片信息错误'+data);
		          					}
		          				},
		          				error:function(res){
		          					alert(res);
		          				}
		          			});
		                }
		            });
		        }

		    }
		});
	 }
   
   //附件识别,保存
   function saveInvoiceInfoFormRayky(id){
	   $.ajax({
			url:formInitData['LUI_ContextPath']+'/fssc/mobile/fssc_mobile_note/fsscMobileNote.do?method=saveInvoiceInfoFormRayky',
			data:{"fdId":id},
			async:true,
			success:function(data){
				var rtn = JSON.parse(data);
				if(rtn.result=='success'){
					//识别成功
					jqtoast("识别成功");
					$("#ld-main").attr('style','display:none');
					window.open(formInitData['LUI_ContextPath']+'/fssc/mobile/fssc_mobile_note/fsscMobileNote.do?method=data','_self');
				} else {
					//识别失败
					jqtoast(rtn.message);
					$("#ld-main").attr('style','display:none');
				}
			},
			error:function(data,status){
				$("#ld-main").attr('style','display:none');
				jqtoast('识别失败statusText:'+data.statusText);
			}
		}); 
   }
   //-------------------------------拍照识别end--------------------------------------------//
   
   
   //-------------------------------上传附件start------------------------------------------//

   //文件上传
   function upload(binary,id){
	   ableScroll()
	   $("#ld-main-upload").attr('style','display:block');
       var xhr = new XMLHttpRequest();
       xhr.open("POST", formInitData['LUI_ContextPath']+"/fssc/mobile/fssc_mobile_note/fsscMobileNote.do?method=saveAtt&fdId="+id+"&fdModelName=com.landray.kmss.fssc.mobile.model.FsscMobileNote&filename="+binary.name);
       xhr.overrideMimeType("application/octet-stream");
       xhr.setRequestHeader('content-type', 'image/jpeg');
       //直接发送二进制数据
       if(xhr.sendAsBinary){
           xhr.sendAsBinary(binary);
       } else {
           xhr.send(binary);
       }
       // 监听变化
       xhr.onreadystatechange = function(e){
           if(xhr.readyState===4){
               if(xhr.status===200){
            	   $("#ld-main-upload").attr('style','display:none');
                   //上传成功
                   $("#ld-main").attr('style','display:block');
                   saveInvoiceInfoFormRayky(id);
               }else{
            	   $("#ld-main-upload").attr('style','display:none');
            	   jqtoast("附件上传失败");
               }
           }
       }
   }
   //-------------------------------------上传附件end----------------------------------------//
  
    //自定义连接
    function personJump(url){
    		window.open(Com_Parameter.ContextPath+url,'_self');
    }
    
    //模块连接
    function  shortcutJump(module){
    	var paths=module.replace('.index','').split('/');
    	var url;
    	if(paths.length==3){//两级
    		url=formInitData['LUI_ContextPath']+"/fssc/"+paths[2]+"/fssc_"+paths[2]+"_mobile/fssc"+(paths[2].substring(0,1).toUpperCase()+paths[2].substring(1))+"Mobile.do?method=data";
    	}else if(paths.length==4){//三级，如借款下的还款
    		url=formInitData['LUI_ContextPath']+'/fssc/'+paths[2]+'/fssc_'+paths[2]+'_'+paths[3]+'_mobile/fssc'+(paths[2].substring(0,1).toUpperCase()+paths[2].substring(1))+(paths[3].substring(0,1).toUpperCase()+paths[3].substring(1))+'Mobile.do?method=data';
    	}
    	window.open(url, '_self');
    	
    }
    
    //第三方
    function  thirdJump(url){
    	window.open(url, '_self');
    }
    
    //手工录入
    function rememberNoteByHand(){
    	window.open(formInitData['LUI_ContextPath']+"/fssc/mobile/fssc_mobile_note/fsscMobileNote.do?method=add", '_self');
    }
    
  	//未报销费用
    function  notExpenseData(){
    	window.open(formInitData['LUI_ContextPath']+"/fssc/mobile/fssc_mobile_note/fsscMobileNote.do?method=data", '_self');
    }
  	//待我审列表
    function notSubmitList(){
    	window.open( formInitData['LUI_ContextPath']+"/fssc/mobile/fssc_mobile_link/fsscMobileLink.do?method=getApprovalList", '_self');
    }
	//待我审的
	function listApproval(){
		window.open(formInitData['LUI_ContextPath']+"/fssc/mobile/fssc_mobile_link/fsscMobileLink.do?method=getNewApprovalList", '_self');
	}
    function serviceUrl(url){
    	if(url=='param'){
    		return '&fdModelName=com.landray.kmss.fssc.mobile.model.FsscMobileNote&filename=pic_'+new Date().getTime()+'.jpg';
    	}else if(url=='notSubmitList'){
    		return formInitData['LUI_ContextPath']+"/fssc/mobile/fssc_mobile_link/fsscMobileLink.do?method=getApprovalList";
    	}else if(url=='noteAdd'){
    		return formInitData['LUI_ContextPath']+"/fssc/mobile/fssc_mobile_note/fsscMobileNote.do?method=add";
    	}
    }
    function listData(){
    	window.open(formInitData['LUI_ContextPath']+"/eop/basedata/eop_basedata_authorize/eopMobileAuthorize.do?method=data", '_self');
    }
    function chooseDidiTravel(){
    	$.post(
    			formInitData['LUI_ContextPath']+"/fssc/mobile/fssc_mobile_note/fsscMobileNote.do?method=selectDidiOrder",
    			{},
    			function(data){
    				if(!data){
    					return;
    				}
    				data = JSON.parse(data);
    				var code = '';
    				for(var i=0;i<data.length;i++){
    					code += '<li class="ld-notSubmit-list-item"><label id="ld-label-1"><div class="ld-checkBox" style="display:inline-block;">';
    					code+='<input type="checkbox" onchange="checkSelectDidi(this)" name="didiDetail" value="'+data[i].fdId+'"/><span class="checkbox-label"></span></div>';
    					code+='<div class="ld-notSubmit-list-item-box"><div class="ld-notSubmit-list-top"><div>';
    					code+='<img src="'+formInitData['LUI_ContextPath']+'/fssc/mobile/resource/images/icon/taxi.png" alt="">';
    					code+='<span>'+data[i].fdPassenger+'</span></div></div><div class="ld-notSubmit-list-bottom">';
    					code+='<div class="ld-notSubmit-list-bottom-info"><div><span>'+data[i].fdStartTime+'</span>';
    					code+='<span class="ld-verticalLine"></span><span>'+(data[i].fdCarLevel||'')+'</span></div> ';
    					code+='<span class="price" id="price-1">'+data[i].fdMoney+'</span></div><p>'+data[i].fdStartPlace+'->'+data[i].fdEndPlace+'</p></div></div></label></li>';
    				}
    				$(".didi-order-list ul").html(code);
    				$(".didi-order-list").addClass("didi-order-list-show");
    		    	$('.ld-remember-modal').removeClass('ld-remember-modal-show')
    		    	$(".ld-didi-check").show();
    			}
    	);
    }
    
    function checkSelectDidi(radio){
    	var len = $("[name=didiDetail]").length;
    	var chlen = $("[name=didiDetail]:checked").length;
    	$("#selectAll").prop("checked",len==chlen);
    }
    
    function cancelSelectDidi(){
    	$(".didi-order-list").removeClass("didi-order-list-show");
    	$(".ld-didi-check").hide();
    }
    function confirmSelectDidi(){
    	var checked = $("input[name=didiDetail]:checked");
    	if(checked.length==0){
    		jqtoast(fsscLang['py.pleaseSelectDidiOrder']);
    		return;
    	}
    	var ids =  [];
    	for(var i=0;i<checked.length;i++){
    		ids.push(checked[i].value);
    	}
    	$(".ld-main").show();
    	$(".didi-order-list").removeClass("didi-order-list-show");
    	$(".ld-didi-check").hide();
    	$(".ld-main").find("span").html(fsscLang['fssc-mobile:py.processing']);//显示处理中
    	$.post(
    			formInitData['LUI_ContextPath']+"/fssc/mobile/fssc_mobile_note/fsscMobileNote.do?method=addNoteByDidiOrder",
    			{ids:ids.join(';')},
    			function(data){
    				$(".ld-main").hide();
    		    	$(".ld-main").find("span").html(fsscLang['fssc-mobile:fssc.mobile.list.scaning']);//还原提示文字
    				if(data.message){
    					jqtoast(data.message);
    					return;
    				}
    		    	window.open(formInitData['LUI_ContextPath']+'/fssc/mobile/fssc_mobile_note/fsscMobileNote.do?method=data','_self');
    			}
    	)
    }

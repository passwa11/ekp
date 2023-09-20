 var licenseSN = document.getElementsByName('licenseSN')[0].value;
 var licenseKey = document.getElementsByName('licenseKey')[0].value;

var readyWorker = preloadJrWorker({
            workerPath: Com_Parameter.ContextPath + 'sys/attachment/sys_att_main/foxit/foxit_resource/lib/',
            enginePath: Com_Parameter.ContextPath + 'sys/attachment/sys_att_main/foxit/foxit_resource/lib/jr-engine/gsdk',
            fontPath: Com_Parameter.ContextPath + 'sys/attachment/external/brotli',
            licenseSN: licenseSN,
            licenseKey: licenseKey
        });
var PDFUI = UIExtension.PDFUI;
 var Events = UIExtension.PDFViewCtrl.Events;
 var canPrint = document.getElementsByName('canPrint')[0].value;
 var download = document.getElementsByName('download')[0].value;
 var downLoadUrl = document.getElementsByName('downLoadUrl')[0].value;
 var filename = document.getElementsByName('filename')[0].value;
 
 //水印参数
 var markWordVar = document.getElementsByName('markWordVar')[0].value;
 markWordVar = markWordVar.replace(RegExp("\'", "g"),'\"');
 var markWorJsondata = JSON.parse(markWordVar);
 var showWaterMark = markWorJsondata.showWaterMark;
 if(showWaterMark == 'true') {
	 var waterType = markWorJsondata.markType;
	 var markOpacity = markWorJsondata.markOpacity;//透明度

	 if(waterType == 'word') {
		 var waterText = markWorJsondata.waterText;
		 var markWordFontFamily = markWorJsondata.markWordFontFamily;//字体
		 var markWordFontColor = markWorJsondata.markWordFontColor; //颜色
		 var  markWordFontSize =  markWorJsondata.markWordFontSize; //字体大小
	 } else if(waterType == 'pic') {
		 var picUrl = markWorJsondata.picUrl;
		 var markRotateAngel = markWorJsondata.markRotateAngel;
	 }
	 
	
 }

 
 var pdfui = new PDFUI({
            viewerOptions: {
                libPath: Com_Parameter.ContextPath + 'sys/attachment/sys_att_main/foxit/foxit_resource/lib',
                jr: {
                    readyWorker: readyWorker
                }
            },
            renderTo: '#pdf-ui',
            appearance: UIExtension.appearances.adaptive,
           
		    fragments: hideFragments(),
	        addons: UIExtension.PDFViewCtrl.DeviceInfo.isMobile ?
	        		Com_Parameter.ContextPath + 'sys/attachment/sys_att_main/foxit/foxit_resource/lib/uix-addons/allInOne.mobile.js':
	        		Com_Parameter.ContextPath + 'sys/attachment/sys_att_main/foxit/foxit_resource/lib/uix-addons/allInOne.js'
	        });
   
           //隐藏控件             
 function hideFragments(){
        	var fragments = [];
        	if(UIExtension.PDFViewCtrl.DeviceInfo.isMobile) { /*移动端*/
        		fragments = [{
        	    	  target: 'tabs',  //注释
          	    	  action: UIExtension.UIConsts.FRAGMENT_ACTION.REMOVE
          	    	},{
        	    	  target: 'hand-tool',  //手型
          	    	  action: UIExtension.UIConsts.FRAGMENT_ACTION.REMOVE
          	    	},{
        	    	  target: 'fv--mobile-toolbar-tabs',  //编辑工具
          	    	  action: UIExtension.UIConsts.FRAGMENT_ACTION.REMOVE
          	    	},{
        	    	  target: 'fv--open-localfile-button',  //打开本地文件
          	    	  action: UIExtension.UIConsts.FRAGMENT_ACTION.REMOVE
          	    	},{
        	    	  target: 'fv--open-fromurl-button',  //打开URL文件
          	    	  action: UIExtension.UIConsts.FRAGMENT_ACTION.REMOVE
          	    	},{
    	        	  target: 'sidebar-layers',  //左侧-层
    	        	  action: UIExtension.UIConsts.FRAGMENT_ACTION.REMOVE
    	        	},{
    	        	  target: 'comment-list-sidebar-panel',  //左侧-注释
    	        	  action: UIExtension.UIConsts.FRAGMENT_ACTION.REMOVE
    	        	}]
        		
        	}else{   /*PC端*/
        		fragments = [/*{  
        	    	  target: 'home-tab',  //主页
          	    	  action: UIExtension.UIConsts.FRAGMENT_ACTION.REMOVE
          	    	},*/{  
      	    	  target: 'comment-tab',  //注释
      	    	  action: UIExtension.UIConsts.FRAGMENT_ACTION.REMOVE
      	    	},{
      	            target: 'fv--comment-tab-paddle',
      	        	action: UIExtension.UIConsts.FRAGMENT_ACTION.REMOVE
      	         },{
      	            target: 'edit-tab',   //编辑
      	            action: UIExtension.UIConsts.FRAGMENT_ACTION.REMOVE
      	          },{
      	             target: 'fv--edit-tab-paddle',
      	             action: UIExtension.UIConsts.FRAGMENT_ACTION.REMOVE
      	           },{
      	              target: 'form-tab',   //表单
      	              action: UIExtension.UIConsts.FRAGMENT_ACTION.REMOVE
      	           },{
      	              target: 'fv--form-tab-paddle',
      	              action: UIExtension.UIConsts.FRAGMENT_ACTION.REMOVE
      	            },{
      	    	      target: 'protect-tab',  //保护
      	    	      action: UIExtension.UIConsts.FRAGMENT_ACTION.REMOVE
      	    	   },{
      	        	  target: 'fv--protect-tab-paddle',
      	        	  action: UIExtension.UIConsts.FRAGMENT_ACTION.REMOVE
      	        	},{
      	        	  target: 'sidebar-layers',  //左侧-层
      	        	  action: UIExtension.UIConsts.FRAGMENT_ACTION.REMOVE
      	        	},{
      	        	  target: 'comment-list-sidebar-panel',  //左侧-注释
      	        	  action: UIExtension.UIConsts.FRAGMENT_ACTION.REMOVE
      	        	},{
      	        	  target: 'sidebar-field',  //左侧-域
      	        	  action: UIExtension.UIConsts.FRAGMENT_ACTION.REMOVE
      	        	},{
        	        	  target: 'fv--page-contextmenu',  //左侧-域
          	        	  action: UIExtension.UIConsts.FRAGMENT_ACTION.REMOVE
          	        },{
            	          target: 'hand-tool',  //手
              	           action: UIExtension.UIConsts.FRAGMENT_ACTION.REMOVE
              	     },{
            	           target: 'home-tab-group-hand',  //箭头
              	           action: UIExtension.UIConsts.FRAGMENT_ACTION.REMOVE
              	      },{
        	        	  target: 'change-color-dropdown',  //皮肤
          	        	  action: UIExtension.UIConsts.FRAGMENT_ACTION.REMOVE
          	      },{
    	        	     target: 'home-tab-group-change-color',  //皮肤
      	        	     action: UIExtension.UIConsts.FRAGMENT_ACTION.REMOVE
      	      },{
	        	     target: 'open-file-button-list',  //上传文件
  	        	     action: UIExtension.UIConsts.FRAGMENT_ACTION.REMOVE
      	      }]
        	}
        	

    		if(download == 'false'){
    			fragments.push({target: 'download-file-button',  //下载
 	        	     action: UIExtension.UIConsts.FRAGMENT_ACTION.REMOVE});
    		}
    		
    		if(canPrint == 'false') {
    			fragments.push({target: 'print-button',  //打印
	        	     action: UIExtension.UIConsts.FRAGMENT_ACTION.REMOVE});
    		}
    		
        	
        	return fragments;
        		
        }
        
     //水印
     pdfui.getEventEmitter().then(eventEmitter => eventEmitter.on(UIExtension.UIEvents.openFileSuccess, () =>{
     pdfui.getPDFDocRender().then((docRender)=>{
     	debugger;
    	 if(showWaterMark == 'true') {
    		 if(waterType == 'word') {
        		 docRender.setWatermarkConfig(
        	       			{
        	       			    type:"text",
        	       			    content:waterText,
        	       			    watermarkSettings:{
        	       			        position:"Center",
        	       			        offsetX:150,
        	       			        offsetY:0,
        	       			        scaleX:1,
        	       			        scaleY:1,
        	       			        rotation:45,
        	       			        opacity:parseFloat(markOpacity) * 10
        	       			    },
        	       			    watermarkTextProperties:{
        	       			        font:markWordFontFamily,
        	       			        fontSize:markWordFontSize,
        	       			        color:markWordFontColor,
        	       			        fontStyle:"normal",
        	       			        lineSpace:10,
        	       			        alignment:"center"
        	       			    }
        	       			}
        	       	 )
        	} else if(waterType == 'pic') {
        		 docRender.setWatermarkConfig(
             			{
             			    type:"image",
             			    content:picUrl,
             			    watermarkSettings:{
             			        position:"Center",
             			        offsetX:10,
             			        offsetY:0,
             			        scaleX:1,
             			        scaleY:1,
             			        rotation:parseInt(markRotateAngel),
             			        opacity:parseFloat(markOpacity) * 10
             			    }
             			}
             	 )
        	}   	
           	 pdfui.redraw(true);
    	 }
    		 
       	})}));
     
        //是否全屏显示
        pdfui.addUIEventListener('fullscreenchange', function (isFullscreen) {
            if (isFullscreen) {
                document.body.classList.add('fv__pdfui-fullscreen-mode');
            } else {
                document.body.classList.remove('fv__pdfui-fullscreen-mode');
            }
        });

        //加载页面
        function openLoadingLayer() {
             //默认显示[缩略图]
        	if(!UIExtension.PDFViewCtrl.DeviceInfo.isMobile){
        		pdfui.getComponentByName("sidebar-thumbnail-panel").then(function(thumbnail){
          	      thumbnail.active();
          	      
          		 });
        		
        		 pdfui.getComponentByName("home-tab").then(function(homeTab){
                	 document.getElementsByName('home-tab')[0].style.visibility="hidden";//隐藏【主页】
             	      //显示【主页】  document.getElementsByName('home-tab')[0].style.visibility="visible";
           	      
           		 });
        	}/*else{
        		document.getElementsByClassName('readerTop')[0].style.visibility="hidden";
        	}*/
           
            return pdfui.loading();
        }
        
        var loadingComponentPromise = openLoadingLayer();
        var openFileError = null
        var passwordErrorCode = PDFViewCtrl.PDF.constant.Error_Code.password
        
        //初始加载
        function startLoading() {
            if(openFileError&&openFileError.error===passwordErrorCode)return
            if (loadingComponentPromise) {
                loadingComponentPromise = loadingComponentPromise
                    .then(function (component) {
                        component.close();
                    })
                    .then(openLoadingLayer);
            } else {
                loadingComponentPromise = openLoadingLayer();
            }
        }
        pdfui.addViewerEventListener(Events.beforeOpenFile, startLoading);
        pdfui.addViewerEventListener(Events.openFileSuccess, function () {
            openFileError = null
            loadingComponentPromise.then(function (component) {
                component.close();
            });
        });
        pdfui.addViewerEventListener(Events.openFileFailed, function (data) {
            openFileError = data
            if(openFileError&&openFileError.error===passwordErrorCode)return
            loadingComponentPromise.then(function (component) {
                component.close();
            });
        });

        pdfui.addViewerEventListener(Events.startConvert, startLoading);
        pdfui.addViewerEventListener(Events.finishConvert, function () {
            loadingComponentPromise.then(function (component) {
                component.close();
            });
        });

    //    function openFileByFoxi(reurl, filename, download, canPrint)
    //    {
       // 	this.download = download;
       // 	this.canPrint = canPrint;
        	 //打开文件
            pdfui.openPDFByHttpRangeRequest({
                range: {
                  url:downLoadUrl,
                }
            }, { fileName: filename })
    //    }
        
        function request(strParame) { 
        	var args = new Object( ); 
        	var query = location.search.substring(1); 
        	
        	var pairs = query.split("&"); // Break at ampersand 
        	for(var i = 0; i < pairs.length; i++) { 
        	var pos = pairs[i].indexOf('='); 
        	if (pos == -1) continue; 
        	var argname = pairs[i].substring(0,pos); 
        	var value = pairs[i].substring(pos+1); 
        	value = decodeURIComponent(value); 
        	args[argname] = value; 
        	} 
        	return args[strParame]; 
        }

        window.addEventListener(UIExtension.PDFViewCtrl.DeviceInfo.isDesktop ? 'resize' : 'orientationchange', function(e) {
            pdfui.redraw().catch(function(err) {console.log(err)});
        });

        //signature handlers
        var requestData = function(type, url, responseType, body){
            return new Promise(function(res, rej){
                var xmlHttp = new XMLHttpRequest();
                xmlHttp.open(type, url);

                xmlHttp.responseType = responseType || 'arraybuffer';
                var formData = new FormData();
                if (body) {
                    for (var key in body) {
                        if (body[key] instanceof Blob) {
                            formData.append(key, body[key], key);
                        } else {
                            formData.append(key, body[key]);
                        }
                    }
                }
                xmlHttp.onloadend = function(e) {
                    var status = xmlHttp.status;
                    if ((status >= 200 && status < 300) || status === 304) {
                        res(xmlHttp.response);
                    }else{
                        rej(new Error('Sign server is not available.'));
                    }
                };
                
                xmlHttp.send(body ? formData : null);
            });
        };

        pdfui.setVerifyHandler(function (signatureField, plainBuffer, signedData){
            return requestData('post', location.origin+'/signature/verify', 'text', {
                filter: signatureField.getFilter(),
                subfilter: signatureField.getSubfilter(),
                signer: signatureField.getSigner(),
                plainContent: new Blob([plainBuffer]),
                signedData: new Blob([signedData])
            });
        });
        pdfui.registerSignHandler({
            filter: 'Adobe.PPKLite',
            subfilter: 'adbe.pkcs7.sha1',
            flag: 0x100,
            distinguishName: 'e=support@foxitsoftware.cn',
            location: 'FZ',
            reason: 'Test',
            signer: 'web sdk',
            showTime: true,
            sign: function(setting, buffer) {
                return requestData('post', location.origin+'/signature/digest_and_sign', 'arraybuffer', {
                    plain: new Blob([buffer])
                })
            }
        });
        
        pdfui.changeLanguage('zh-CN'); //切换语言
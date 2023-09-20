define(function(require, exports, module) {
			var $ = require("lui/jquery");
			var env = require("lui/util/env");
			var topic = require("lui/topic");
			var imgList = new Array();//存附件对象用来展示第一个大图
			var picPageNum = 1;//当前页
			var picPageSize = 0;//总页数
			var picItem = 0;//页面上传的附件数
			var divIndex = 1;// 未加载内容项的div序列
			var idLists = new Array();// 所有可在播放器播放的文件集合
			var showedBorder = "#35a1d0 2px solid";//边框的样式
			var noshowBorder = "#f0f0f0 2px solid";//失去边框的样式
			var liId = 0;// 缩略图li的id
			var grayUrl=env.fn.formatUrl('/sys/attachment/sys_att_main/img/pic_prev_gray.cur');
			var leUrl=env.fn.formatUrl('/sys/attachment/sys_att_main/img/pic_prev.cur');
			var riUrl=env.fn.formatUrl('/sys/attachment/sys_att_main/img/pic_next.cur');
			var	leftUrl='url("'+leUrl+'"),auto';// 大图左箭头
			var rightUrl='url("'+riUrl+'"),auto';// 大图右箭头
			var leftUrl_gray='url("'+grayUrl+'"),auto';// 灰色左箭头
			var imgDisplayW = null;// 播放器宽度
			var showContent=$(".lui_atta_showPic_container .lui_atta_showPic_content");
			var isLoad=false;
			function beingResize(){
				
				imgDisplayW = $(".viewDiv").width();
				$(".viewDiv").find('iframe').each(function(index,iframe){
					$(iframe).attr('width',imgDisplayW+'px');
				});
			}
			function Attachment_display(attObj,fdModelId,fdModelName,fdAttHtmlIds){
	
				clickBind();
					$.post(
						env.fn.formatUrl('/sys/attachment/sys_att_main/sysAttMain.do?method=buildHtmlComplete&fdModelId='+fdModelId+'&fdModelName='+fdModelName),
						null,function(data){
							$(window).bind("resize", function() {
								beingResize();
							});
							var fdAttHtmlIds=data.fdAttHtmlIds;//获取成功转换的附件id集合
							window.VIEW_File_EXT_READ = File_EXT_READ + ';.pdf';
							var imgInfoWidith = $(".viewDiv").width();
							imgDisplayW = imgInfoWidith ;
							var isMSIE = !!window.ActiveXObject || "ActiveXObject" in window;// IE浏览器
							if(isMSIE){
								imgDisplayW = imgDisplayW;
							}
							var showDiv = document.createElement("div");
							var len = attObj.fileList.length;
							showContent.width(imgInfoWidith*len);// 初始化幻灯片宽度
							for(var i=0;i<len;i++) {
								
								var doc = attObj.fileList[i];
								var fileExt = doc.fileName.substring(doc.fileName.lastIndexOf("."));
								if(attObj.fileList[i].fileStatus > -1 && (attObj.fileList[i].fileType=="image/jpeg" ||attObj.fileList[i].fileType=="image/gif"||attObj.fileList[i].fileType=="image/bmp"||attObj.fileList[i].fileType=="image/png")) {
									
							        picItem++;
								    var div=GetLinkDiv(attObj, attObj.fileList[i]) ;
								    if(div!=""){
									 	showDiv.appendChild(div );
									 }
								    idLists.push(attObj.fileList[i]);
								}else if(attObj.fileList[i].fileStatus > -1 &&  (attObj.fileList[i].fileType.indexOf("audio/mpeg")>-1)){
									
									picItem++;
									GetMp3Div(attObj, attObj.fileList[i]) ;
									idLists.push(attObj.fileList[i]);
								}else if(attObj.fileList[i].fileStatus > -1 && (attObj.fileList[i].fileType.indexOf("video")>-1 || attObj.fileList[i].fileType.indexOf("audio")>-1 )){
									
										if(fdAttHtmlIds.indexOf(attObj.fileList[i].fdId)>-1){
											picItem++;
											GetVideoDiv(attObj, attObj.fileList[i]) ;
											idLists.push(attObj.fileList[i]);
										
									}
								}else if(attObj.fileList[i].fileStatus > -1 && VIEW_File_EXT_READ.indexOf(fileExt.toLowerCase()) > -1){
										
										if(fdAttHtmlIds.indexOf(attObj.fileList[i].fdId)>-1){
											picItem++;
											GetDocDiv(attObj, attObj.fileList[i]) ;
											idLists.push(attObj.fileList[i]);
										
									}
								} 
							}
							if(len>0){
								
								picPageSize = Math.ceil(picItem/7);
							}
							$("#picListItem li:eq(0) div:first").css("border",showedBorder);// 为第一个缩略图加蓝色边框

							// 根据可播放附件数设置相同数量的大图的div
							for(var i=4;i<=picItem;i++){ 
								var div_box = document.createElement("div");
								div_box.id = "bigImg"+i; 
								div_box.setAttribute('style','float:left;');
								$("#bigImg"+(i-1)).after(div_box);
							} 
							// 添加切换附件播放的箭头,当只有一个附件时，不加导向箭头
							if(idLists.length!=1){
								
								$(".insignia_left")[0].style.cursor=leftUrl;
								$(".insignia_right")[0].style.cursor=rightUrl;
							}else{
								$(".insignia_left").hide();
								$(".insignia_right").hide();
							}
							// 播放第一个附件时，大图的左箭头为灰色
							
							var leftSize = showContent[0].style.left; 
							if(leftSize.substring(0,leftSize.length-2)>=0&&idLists.length!=1){
								$(".insignia_left")[0].style.cursor=leftUrl_gray;
							}

						},'json'
					);
			}
			var mile;
			$(document).ready(function(){
				var width=$(".viewDiv").width()-34;
				var allWidth=width*0.23;
				mile= Math.floor(allWidth/8);
				
				
			})
			function GetVideoDiv( attachmentObject, doc,index){
				if(doc.fileType.indexOf("video")>-1 || doc.fileType.indexOf("audio")>-1){
					putVideoBrowser(doc) ;
				}
			}
			function GetMp3Div( attachmentObject, doc){
				if(doc.fileType.indexOf("audio/mpeg")>-1){
					putMp3Browser(doc) ;
				}
			}
			function GetDocDiv( attachmentObject, doc){
				putDocBrowser(doc) ; 
			} 
			function GetSrc(fileName) {
				
				var src=env.fn.formatUrl('/sys/attachment/sys_att_main/img/');
				if (fileName != null && fileName.length > 0){
					var doc= '.doc|.docx'.indexOf(fileName.substring(fileName.lastIndexOf(".")).toLowerCase()) > -1;
			        if(doc){
			            src = src+'doc_default.gif'
			        	return src  ;
			               }
			        var ppt= '.ppt|.pptx'.indexOf(fileName.substring(fileName.lastIndexOf(".")).toLowerCase()) > -1;
			        if(ppt){
			            src=  src+'ppt_default.gif'
			            return  src;
			            }
			        var txt= '.txt'.indexOf(fileName.substring(fileName.lastIndexOf(".")).toLowerCase()) > -1;
			        if(txt){
			        	src=src+'txt_default.gif'
			            return   src ;
			            }
			        var xls= '.xls|.xlsx'.indexOf(fileName.substring(fileName.lastIndexOf(".")).toLowerCase()) > -1;
			        if(xls){
			        	src=src+'xls_default.gif'
			            return   src ;
			            }
			        var pdf= '.pdf'.indexOf(fileName.substring(fileName.lastIndexOf(".")).toLowerCase()) > -1;
			        if(pdf){
			        	src=src+'pdf_default.gif'
			            return   src ; 
			            }

			        src=src+'default.gif'
			        return   src ; 
					}
				  
			}
			function GetLinkDiv( attachmentObject, doc) {
				
				 if(doc.fileType.indexOf("image")>-1) {
				    putImgBrowser(doc) ;
				    return "" ;
				 }
			}
         
		function putImgBrowser(obj){
				
				 imgList.push(obj) ;
				 if(imgList.length==1){ // 显示第一个大图
				   showBigImg(obj.fdId,"1") ;
				   document.getElementById("imgBrowser").style.display='table';
				 }
				 var imgSrc =  env.fn.formatUrl('/sys/attachment/sys_att_main/sysAttMain.do?method=view&fdId='+obj.fdId+'&filekey=image2thumbnail_s1');;
				 var sImgSrc =env.fn.formatUrl('/sys/attachment/sys_att_main/img/img_default.gif');
				 buildThumb(imgSrc,sImgSrc);
			}
			function putVideoBrowser(obj){
				 imgList.push(obj) ;
				 if(imgList.length==1){ 
				   showBigVideo(obj.fdId,"1") ;
				   document.getElementById("imgBrowser").style.display='table';
				 }
				 var imgSrc = env.fn.formatUrl('/sys/attachment/sys_att_main/sysAttMain.do?method=view&filethumb=yes&fdId='+obj.fdId); ;
				 var sImgSrc =env.fn.formatUrl('/sys/attachment/sys_att_main/img/vedioicon.jpg');
				 buildThumb(imgSrc,sImgSrc);
			}
			function putMp3Browser(obj){
				 imgList.push(obj) ;
				 if(imgList.length==1){ 
				   showBigMp3(obj.fdId,"1") ;
				   document.getElementById("imgBrowser").style.display='table';
				 }
				 var imgSrc =env.fn.formatUrl('/sys/attachment/sys_att_main/img/musicicon.jpg');;
				 var sImgSrc =  env.fn.formatUrl('/sys/attachment/sys_att_main/img/mp3_default.gif');
				 buildThumb(imgSrc,sImgSrc);
			}
			function putDocBrowser(obj){
				 imgList.push(obj) ;
				 if(imgList.length==1){ 
				   showBigDoc(obj.fdId,"1") ;
				   document.getElementById("imgBrowser").style.display='table';
				 }
				 var imgSrc =  env.fn.formatUrl('/sys/attachment/sys_att_main/sysAttMain.do?method=view&fdId='+obj.fdId+'&filethumb=yes');
				 var sImgSrc = GetSrc(obj.fileName);
				 buildThumb(imgSrc,sImgSrc);
			}
			function showBigVideo(videoId,index){
				// $("#iframeNode
				// iframe").contents().find("body").empty();//清除Iframe,解决IE下报错的问题
				var bi=document.getElementById("bigImg"+index) ;
				var url=env.fn.formatUrl('/sys/attachment/sys_att_main/sysAttMain.do');
				bi.innerHTML="<div id='iframeNode'><iframe allowfullscreen='true' src='"+url+"?method=view&fdId="+videoId+"&viewer=video_viewer' name='mainFrame' id='mainFrame'  width='"+imgDisplayW+"' height='565' frameborder=no scrolling='no'style='z-index:3'></iframe></div>";
			}
			function showBigMp3(videoId,index){
				// $("#iframeNode
				// iframe").contents().find("body").empty();//清除Iframe,解决IE下报错的问题
				var bi=document.getElementById("bigImg"+index) ;
				var url=env.fn.formatUrl('/sys/attachment/viewer/audio_mp3.jsp');
				bi.innerHTML="<div id='iframeNode'><iframe src='"+url+"?attId="+videoId+"' name='mainFrame' id='mainFrame' width='"+imgDisplayW+"' height='565' frameborder=no scrolling='no' style='background-color:white'></iframe></div>";
			}
			function showBigDoc(docId,index){ 
				// $("#iframeNode
				// iframe").contents().find("body").empty();//清除Iframe,解决IE下报错的问题
				var bi=document.getElementById("bigImg"+index) ;
				var url=env.fn.formatUrl('/sys/attachment/sys_att_main/sysAttMain.do');
				bi.innerHTML="<div id='iframeNode'><iframe src='"+url+"?method=view&fdId="+docId+"&viewer=htmlviewer&toolPosition=top&newOpen=true&inner=yes' name='mainFrame' id='mainFrame' width='"+imgDisplayW+"' height='565' style='border:none'></iframe></div>";
			} 
			 
			function showBigImg(imgId,index){ 
				// $("#iframeNode
				// iframe").contents().find("body").empty();//清除Iframe,解决IE下报错的问题
			    var bi=document.getElementById("bigImg"+index) ;
			    bi.innerHTML='';
			    var a= document.createElement("A"); 
			    var img= document.createElement("img"); 
			    var url=env.fn.formatUrl('/sys/attachment/sys_att_main/sysAttMain.do?method=view&filekey=image2thumbnail_s2&fdId='+imgId);
				img.src= url;
				img.setAttribute("onload","drawImage(this,this.parentNode)");
				a.setAttribute("id",imgId);
				a.title='点击查看原图';   
				var div= document.createElement("div");
				$(div).css({width:imgDisplayW,height:565});
				div.appendChild(img) ;
				a.appendChild(div) ;
				bi.appendChild(a) ;
				
				$("#bigImg"+index).on('click',a,function(){
					
					var id=this.getElementsByTagName('a')[0].id;
					showOriginalImg(id);
				})
				
				
			 }

			// doc-html播放跳转回调函数
			function gotoCallBack(){
				document.body.scrollTop = 0;
			}


			function showOriginalImg(imgId){
			
				var url=env.fn.formatUrl('/sys/attachment/sys_att_main/showOriginalImg.jsp');
			    window.open(url+"?fdId="+imgId,"_blank") ;
			 }

			function buildThumb(imgSrc,sImgSrc){
				
				var smallRow=document.getElementById("picListItem") ;
				var li=document.createElement("li");
				liId +=1;
				li.setAttribute("id","pic"+liId);
				li.setAttribute("style","margin-left:"+mile+'px');
				var a=document.createElement("A"); 
			    var $img= $('<img>'); 
			    $img.attr('src',imgSrc);
			    $img.addClass("smallPic");
			    var div= document.createElement("div");
			    $(div).addClass("smallDiv2");
			    a.appendChild(div);
			    div.appendChild($img[0]) ;
			    // img2--文档类型小图标
			    var img2= document.createElement("img"); 
			    img2.src= sImgSrc;
			    img2.border=1 ;
			    img2.width=15 ;
			    img2.height=15 ;
			    img2.id="sImg";
			    a.appendChild(img2) ;
			    a.setAttribute("id","BigPlayer"+liId); 
			    li.appendChild(a);
			    if(picItem<1 || picItem>7){
			    	$(li).css("display","none");
				 }
			    smallRow.appendChild(li);	
			    $('#BigPlayer'+liId ).click(function(){
			       isLoad=true;
				   var index= this.id.substring(9);
			    	liId = parseInt(index);
					var _target = imgDisplayW*(liId-1);
					showContent[0].style.display='none';
					showContent.css('left',"-"+_target+"px");
						for(var i=1;i<= picItem;i++) { 
							if( i <= 0  )
								continue;
							if($("#bigImg"+i)[0].innerHTML == ""){
								
								showFile(idLists[i-1],i);
							}
						}
						showContent[0].style.display='block';
						setLeftCursor();
					
					$("#picListItem li>a>div").css("border",noshowBorder);// 去掉所有缩略图蓝色边框
					$("#picListItem li:eq("+(liId-1)+") div:first").css("border",showedBorder);// 为缩略图加蓝色边框
			    }
			    	
			    );
			}
			
			// 设置左箭头为灰或亮
			function setLeftCursor(){
				// 添加切换附件播放的箭头,当只有一个附件时，不加导向箭头
				if(idLists.length!=1){
					var leftLen = showContent[0].style.left;  
					if(leftLen.substring(0,leftLen.length-2)>-imgDisplayW){  
						$(".insignia_left")[0].style.cursor=leftUrl_gray;// 箭头灰色，无法切换文件
					}else{
						$(".insignia_left")[0].style.cursor=leftUrl;
						
					}
				}
			}
            function clickBind(){
                 
            	$(".insignia_left.left").click(function(){
					var leftSize = showContent[0].style.left; 
					if(leftSize.substring(0,leftSize.length-2)>=0){  
						setLeftCursor();
					}else{
						moveBothSides('left',leftSize);
					}
				})
				
				$(".insignia_right.right").click(function(){
					
					var leftSize = showContent[0].style.left; 
					
					if(leftSize.substring(0,leftSize.length-2) <= -((picItem-1)*imgDisplayW)){
						var _divIndex = (Math.abs(leftSize.substring(0,leftSize.length-2)))/imgDisplayW;  
						showContent.animate({left:"0px"},"normal",function(){// 返回第一个附件
							if($("#picListItem li:eq(0)").is(":hidden")){// 判断下个附件是否被隐藏，如果是，则翻页
								picPageNum = 2;
								$('.img_small_btnl a').click();
							}
							divIndex = 2;
							$("#picListItem li:eq("+_divIndex+") div:first").css("border",noshowBorder);// 去除蓝色边框
							$("#picListItem li:eq(0) div:first").css("border",showedBorder);// 为缩略图加蓝色边框

							setLeftCursor();
						});

					}else{
						moveBothSides('right',leftSize);
					
					}
				});
				  
            	$('.img_small_btnl a').click(function(){
					
					if(picPageNum>1){
						picPageNum--;
						moveCommon()
					}else{
						picPageNum = picPageSize+1; 
						this.click();
					}
				 })
				
				 $('.img_small_btnr a').click(function(){
					
					if(picPageNum<picPageSize){
						picPageNum++;
						moveCommon()
					}else{
						picPageNum = 0; 
						this.click();
					}
				})
            }
            
			function moveBothSides(path,leftSize){
				
				var _divIndex = (Math.abs(leftSize.substring(0,leftSize.length-2)))/imgDisplayW;
				$("#picListItem li:eq("+_divIndex+") div:first").css("border",noshowBorder);
				if(path=='right'){
					_divIndex+=1
					divIndex+= 1;
					var leftNum="-="+imgDisplayW+"px";
				}
                if(path=='left'){
                	_divIndex-=1;
                	divIndex-=1;
                	var leftNum="+="+imgDisplayW+"px";
				}
				if(!showContent.is(":animated")){// 防止连续点击导致的重复动画问题
					showContent.animate({left:leftNum},"slow",function(){
						if($("#picListItem li:eq("+(_divIndex)+")").is(":hidden")){// 判断下个附件是否被隐藏，如果是，则翻页
							$('.img_small_btnr a').click();
						}
						$("#picListItem li:eq("+(_divIndex)+") div:first").css("border",showedBorder);// 为缩略图加蓝色边框
						
						if(!isLoad&&divIndex<=picItem && $("#bigImg"+divIndex)[0].innerHTML == ""){
							showFile(idLists[divIndex-1],divIndex);
						}
						setLeftCursor();
					});
				}
			}
			// 根据可播放附件加载相应附件的div位置
			function showFile(file,index){
				
				var fileExt = file.fileName.substring(file.fileName.lastIndexOf("."));
				if(file.fileStatus > -1 && (file.fileType=="image/jpeg" ||file.fileType=="image/gif"||file.fileType=="image/bmp"||file.fileType=="image/png")) {
					showBigImg(file.fdId,index); 
				}else if(file.fileStatus > -1 &&  (file.fileType.indexOf("audio/mpeg")>-1)){
					showBigMp3(file.fdId,index); 
				}else if(file.fileStatus > -1 && (file.fileType.indexOf("video")>-1 || file.fileType.indexOf("audio")>-1 )){
					showBigVideo(file.fdId,index);
				}else if(file.fileStatus > -1 && VIEW_File_EXT_READ.indexOf(fileExt.toLowerCase()) > -1){
					showBigDoc(file.fdId,index);
				}
			}

			function moveCommon(){
					
				$("#picListItem LI").css("display","none");
				$("#picListItem LI").slice((picPageNum-1)*7,picPageNum*7).css("display","");
			}

			topic.subscribe("asposeClick", function(evt) {
				if (!evt.data || !evt.data.hrefText) {
					return;
				}
				var link = evt.data.hrefText, linkArr = link.split(/[\\\/]/);
				var fileName = linkArr[linkArr.length - 1];
				for (var i = 0; i < idLists.length; i++) {
					if (idLists[i].fileName == fileName) {
						getBigPlayer(i + 1);
					}
				}
			});
			
			module.exports.Attachment_display  = Attachment_display;
		});

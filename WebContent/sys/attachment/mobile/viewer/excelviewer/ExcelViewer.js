define(
		[ "dojo/_base/declare", "dojo/json",
				"dojo/text!./tmpl/excelViewer.jsp", "dojo/dom-attr",
				"dojo/dom-style", "dojo/dom-construct",
				"sys/attachment/mobile/viewer/base/BaseViewer", "mui/util", "dojo/query","dojo/dom-geometry", "dojo/_base/window" ],
		function(declare, json, tmpl, domAttr, domStyle, domConstruct,
				BaseViewer, util,domQuery,domGeo,domWindow) {

			return declare(
					"sys.attachment.mobile.excelviewer.ExcelViewer",
					[ BaseViewer ],
					{
						templateString : tmpl,

						sheetFrame : null,
						
						curSheetNum:1,
						
						sheetULDom:null,

						buildRendering : function() {
							this.inherited(arguments);
							window.pageViewer = this;
							var jsonViewerParam = json.parse(this.viewerParam);
							var activeSheetNum = jsonViewerParam.activeSheetNum ? jsonViewerParam.activeSheetNum
									: 1;
							this.initSheets(jsonViewerParam, activeSheetNum)
							this.loadSheet(activeSheetNum);
						},
						postCreate :function(){
							var liWidth=0;
							var sheetCount=0;
							domQuery("li",this.sheetULDom).forEach(function(node,index,nodeList){
								var posNode=domGeo.position(node,false);
								sheetCount++;
								liWidth+=posNode.w;
							});
							domStyle.set(this.sheetULDom,"width",(liWidth+sheetCount)+"px");
						},
						initSheets : function(jsonViewerParam, activeSheetNum) {
							var sheetULHtml = "<ul>";
							var sheetNames = jsonViewerParam.sheetName
									.split(";");
							for ( var i in sheetNames) {
								var sheetNum = parseInt(i);
								var sheetName = sheetNames[i]
										.substring(sheetNames[i].indexOf("-") + 1);
								var clsName="sheet_"+(sheetNum+1);
								if(activeSheetNum == sheetNum + 1){
									clsName += " status_current";
								}
								sheetULHtml += "<li class='" + clsName + "' onclick='window.pageViewer.changeSheet("
										+ (sheetNum + 1)
										+ ")'>"
										+ sheetName
										+ "</li>";
							}
							sheetULHtml += "</ul>";
							this.sheetULDom = domConstruct.toDom(sheetULHtml);
							domConstruct.place(this.sheetULDom, this.sheetContent,
									'last');
						},
						
						changeSheet : function(sheetNum){
							this.loadSheet(sheetNum);
							this.setCurrentSheet(sheetNum);
						},
						setCurrentSheet : function(sheetNum){
							domQuery("li.status_current").removeClass("status_current");
							var newCurrentClsName="li.sheet_"+sheetNum;
							domQuery(newCurrentClsName).addClass("status_current");
						},
						loadSheet : function(sheetNum) {
							if (this.sheetFrame == null) {
								var frameHtml = "<iframe width='100%' height='100%' onload='window.pageViewer.sheetLoaded()' style='border: none;' scrolling='auto'></iframe>";
								this.sheetFrame = domConstruct.toDom(frameHtml);
								domConstruct.place(this.sheetFrame,
										this.pageContent, 'last');
							}
							this.curSheetNum=sheetNum;
							domAttr.set(this.sheetFrame, 'src', this
									.getSrc(sheetNum));
						},
						sheetLoaded : function() {
							if (this.waterMarkConfig.showWaterMark == "true") {
								this.setWaterMarkBody(domWindow.doc);
							}
						},
						
						setWaterMarkBody : function(doc) {
							domQuery(".mask_div").remove();
							var oTemp = doc.createDocumentFragment();
						    // 获取页面最大宽度
						    var mark_width = Math.max(doc.body.scrollWidth,doc.body.clientWidth);
						    // 获取页面最大长度
						    var mark_height = Math.max(doc.body.scrollHeight,doc.body.clientHeight)-46;
						    var markWidth=this.waterMarkConfig.otherInfos.markWidth+16;
						    var markHeight=this.waterMarkConfig.otherInfos.markHeight+6;
						    var markType=this.waterMarkConfig.markType;
						    var markOpacity = parseFloat(this.waterMarkConfig.markOpacity);
						    var colSpace=parseInt(this.waterMarkConfig.markColSpace);
						    var rowSpace=parseInt(this.waterMarkConfig.markRowSpace);
						    var cols=parseInt((mark_width-0+colSpace) / (markWidth + colSpace));
						    if(cols>1){
						    	colSpace=parseInt((mark_width-0 - markWidth * cols)/ (cols - 1));
						    }else{
						    	cols = 1;
						    }
						    var rows=parseInt((mark_height-46-46+rowSpace) / (markHeight + rowSpace));
						    if(rows>1){
						    	rowSpace=parseInt((mark_height-46-markHeight * rows)/ (rows - 1));
						    }
						    var rotateType=this.waterMarkConfig.markRotateType;
						    var angel=parseInt(this.waterMarkConfig.markRotateAngel);
						    var rad = angel * (Math.PI / 180);
						    var colLeft;
						    var rowTop;
						    for (var i = 0; i < rows; i++) {
						    	rowTop = 46 + (rowSpace + markHeight) * i;
						        for (var j = 0; j < cols; j++) {
						        	colLeft = 0 + (markWidth + colSpace) * j;
						            var mask_div = document.createElement('div');
						            mask_div.id = 'mask_div' + i + j;
						            if("word"==markType){
									    var markWord=this.waterMarkConfig.otherInfos.markWord;
									    var markWordFontFamily=this.waterMarkConfig.markWordFontFamily;
									    var markWordFontSize=this.waterMarkConfig.markWordFontSize;
									    var markWordFontColor=this.waterMarkConfig.markWordFontColor;
							            mask_div.appendChild(document.createTextNode(markWord));
							            mask_div.style.fontSize = markWordFontSize+'px';
							            mask_div.style.fontFamily = markWordFontFamily;
							            mask_div.style.color = markWordFontColor;
						            }
						            if("pic"==markType){
						            	var picUrl=this.waterMarkConfig.otherInfos.picUrl;
						            	var img=document.createElement("img");
						            	img.src=picUrl;
						            	mask_div.appendChild(img);
						            }
						            if("declining"==rotateType){
						            	var commonCss="rotate(" + angel + "deg)";
						            	var ieCss="progid:DXImageTransform.Microsoft.Matrix(sizingMethod='auto expand', M11=";
						            	ieCss+=Math.cos(rad)+",M12=";
						            	ieCss+=-Math.sin(rad)+",M21=";
						            	ieCss+=Math.sin(rad)+",M22=";
						            	ieCss+=Math.cos(rad)+")";
						            	domStyle.set(mask_div,{"-moz-transform":commonCss,"-o-transform":commonCss,"-webkit-transform":commonCss,"-ms-transform":commonCss,"transform":commonCss})
						            }
						            mask_div.style.visibility = "";
						            mask_div.style.position = "absolute";
						            mask_div.style.left = colLeft + 'px';
						            mask_div.style.top = rowTop+20 + 'px';
						            mask_div.style.overflow = "hidden";
						            mask_div.style.zIndex = "999999";
						            domStyle.set(mask_div,{"opacity":markOpacity,"pointer-events":"none","display":"block"});
						            mask_div.style.textAlign = "center";
						            mask_div.style.height = markHeight + 'px';
						            mask_div.className="mask_div";
						            oTemp.appendChild(mask_div);
						        };
						    };
						    doc.body.appendChild(oTemp);
						}
					});
		});

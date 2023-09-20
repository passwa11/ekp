var configWaterMarkFuncs={
	demoWaterMarkConfig : waterMarkConfig,
	globalTimer : null,
	initialConfig : function(){
		$("#colorText").spectrum({
			color: waterMarkConfig.markWordFontColor,
	        flat: true,
	        clickoutFiresChange: false,
	        showInput: true,
	        className: "full-spectrum",
	        showInitial: true,
	        showPalette: true,
	        showSelectionPalette: true,
	        maxPaletteSize: 10,
	        preferredFormat: "hex",
	        localStorageKey: "spectrum.example",
	        move: function (color) {
	        },
	        show: function () {

	        },
	        beforeShow: function () {

	        },
	        hide: function (color) {
	        },

	        palette: [
	            ["rgb(0, 0, 0)", "rgb(67, 67, 67)", "rgb(102, 102, 102)", /*"rgb(153, 153, 153)","rgb(183, 183, 183)",*/
	            "rgb(204, 204, 204)", "rgb(217, 217, 217)", /*"rgb(239, 239, 239)", "rgb(243, 243, 243)",*/ "rgb(255, 255, 255)"],
	            ["rgb(152, 0, 0)", "rgb(255, 0, 0)", "rgb(255, 153, 0)", "rgb(255, 255, 0)", "rgb(0, 255, 0)",
	            "rgb(0, 255, 255)", "rgb(74, 134, 232)", "rgb(0, 0, 255)", "rgb(153, 0, 255)", "rgb(255, 0, 255)"],
	            ["rgb(230, 184, 175)", "rgb(244, 204, 204)", "rgb(252, 229, 205)", "rgb(255, 242, 204)", "rgb(217, 234, 211)",
	            "rgb(208, 224, 227)", "rgb(201, 218, 248)", "rgb(207, 226, 243)", "rgb(217, 210, 233)", "rgb(234, 209, 220)",
	            "rgb(221, 126, 107)", "rgb(234, 153, 153)", "rgb(249, 203, 156)", "rgb(255, 229, 153)", "rgb(182, 215, 168)",
	            "rgb(162, 196, 201)", "rgb(164, 194, 244)", "rgb(159, 197, 232)", "rgb(180, 167, 214)", "rgb(213, 166, 189)",
	            "rgb(204, 65, 37)", "rgb(224, 102, 102)", "rgb(246, 178, 107)", "rgb(255, 217, 102)", "rgb(147, 196, 125)",
	            "rgb(118, 165, 175)", "rgb(109, 158, 235)", "rgb(111, 168, 220)", "rgb(142, 124, 195)", "rgb(194, 123, 160)",
	            "rgb(166, 28, 0)", "rgb(204, 0, 0)", "rgb(230, 145, 56)", "rgb(241, 194, 50)", "rgb(106, 168, 79)",
	            "rgb(69, 129, 142)", "rgb(60, 120, 216)", "rgb(61, 133, 198)", "rgb(103, 78, 167)", "rgb(166, 77, 121)",
	            /*"rgb(133, 32, 12)", "rgb(153, 0, 0)", "rgb(180, 95, 6)", "rgb(191, 144, 0)", "rgb(56, 118, 29)",
	            "rgb(19, 79, 92)", "rgb(17, 85, 204)", "rgb(11, 83, 148)", "rgb(53, 28, 117)", "rgb(116, 27, 71)",*/
	            "rgb(91, 15, 0)", "rgb(102, 0, 0)", "rgb(120, 63, 4)", "rgb(127, 96, 0)", "rgb(39, 78, 19)",
	            "rgb(12, 52, 61)", "rgb(28, 69, 135)", "rgb(7, 55, 99)", "rgb(32, 18, 77)", "rgb(76, 17, 48)"]
	        ]		    
		});
		seajs.use('lui/jquery', function($) {
			configWaterMarkFuncs.showWaterMarkChanged("initial");
			configWaterMarkFuncs.markTypeChanged(waterMarkConfig.markType,"initial");
			configWaterMarkFuncs.markWordTypeChanged(waterMarkConfig.markWordType,"initial");
			configWaterMarkFuncs.markRotateTypeChanged(waterMarkConfig.markRotateType,"initial");
			$("#colorText").val(configWaterMarkFuncs.demoWaterMarkConfig.markWordFontColor);
			configWaterMarkFuncs.loadDemoWaterMark(true);
		});
	},
	resizeHandler : function(){
		clearTimeout(configWaterMarkFuncs.globalTimer);
		configWaterMarkFuncs.globalTimer = setTimeout(configWaterMarkFuncs.resizeInnerHandler, 500);
	},
	resizeInnerHandler : function(){
		configWaterMarkFuncs.loadDemoWaterMark(false);
	},
	showWaterMarkChanged :function(obj){
		var isShowWaterMark=false;
		seajs.use('lui/jquery', function($) {
			isShowWaterMark=$("input:checkbox[name='_showWaterMark']").is(":checked");
			if(isShowWaterMark){
				$("#waterMarkConfig").css("display", "table");
			}else{
				$("#waterMarkConfig").css("display", "none");
			}
		});
		configWaterMarkFuncs.demoWaterMarkConfig.showWaterMark=isShowWaterMark;
	},
	markTypeChanged : function(value,obj){
		seajs.use('lui/jquery', function($) {
			$("#demoWaterMark").html("");
			var tr_word_type_config = $("tr[class='tr_word_type_config']");
			var tr_pic_type_config = $("tr[class='tr_pic_type_config']");
			if ("word" == value) {
				$(tr_pic_type_config).css("display", "none");
				$(tr_word_type_config).css("display", "");
			}
			if ("pic" == value) {
				$(tr_pic_type_config).css("display", "");
				$(tr_word_type_config).css("display", "none");
			}
		});
		configWaterMarkFuncs.demoWaterMarkConfig.markType=value;
		if(!(obj=="initial")){
			configWaterMarkFuncs.loadDemoWaterMark(false);
		}
	},
	markPicChanged : function(markPicEle){
		seajs.use('lui/jquery', function($) {
			var markPicEleValue=markPicEle.value;
			$("input:text[name='markPicFileName']").val(markPicEleValue.substring(markPicEleValue.lastIndexOf("\\")+1));
		});
	},
	markWordChanged : function(value,obj){
		configWaterMarkFuncs.demoWaterMarkConfig.markWord=value;
		if(!(obj=="initial")){
			configWaterMarkFuncs.loadDemoWaterMark(false);
		}
	},
	markWordTypeChanged : function(value,obj){
		seajs.use('lui/jquery', function($) {
			if(value=="fixed"){
				$("#markWordVarSpan").css("display","none");
				$("#markWordFixedSpan").css("display","");
			}
			if(value=="var"){
				$("#markWordVarSpan").css("display","");
				$("#markWordFixedSpan").css("display","none");
			}
			configWaterMarkFuncs.demoWaterMarkConfig.markWordType=value;
			if(!(obj=="initial")){
				configWaterMarkFuncs.loadDemoWaterMark(false);
			}
		});
	},
	markWordFontFamilyChanged : function(value,obj){
		configWaterMarkFuncs.demoWaterMarkConfig.markWordFontFamily=value;
		if(!(obj=="initial")){
			configWaterMarkFuncs.loadDemoWaterMark(false);
		}
	},
	markWordFontSizeChanged : function(value,obj){
		configWaterMarkFuncs.demoWaterMarkConfig.markWordFontSize=value;
		if(!(obj=="initial")){
			configWaterMarkFuncs.loadDemoWaterMark(false);
		}
	},
	markRotateTypeChanged : function(value,obj){
		seajs.use('lui/jquery', function($) {
			if(value=="declining"){
				$("#markRotateAngel").css("display","");
				$("#markRotateAngel").removeAttr("disabled");
			}
			if(value=="horizontal"){
				$("#markRotateAngel").css("display","none");
				$("#markRotateAngel").attr("disabled","disabled");
			}
		});
		configWaterMarkFuncs.demoWaterMarkConfig.markRotateType=value;
		if(!(obj=="initial")){
			configWaterMarkFuncs.loadDemoWaterMark(false);
		}
	},
	markRotateAngelChanged : function(value,obj){
		configWaterMarkFuncs.demoWaterMarkConfig.markRotateAngel=value;
		if(!(obj=="initial")){
			configWaterMarkFuncs.loadDemoWaterMark(false);
		}
	},
	markRowSpaceChanged : function(value,obj){
		configWaterMarkFuncs.demoWaterMarkConfig.markRowSpace=value;
		if(!(obj=="initial")){
			configWaterMarkFuncs.loadDemoWaterMark(false);
		}
	},
	markColSpaceChanged : function(value,obj){
		configWaterMarkFuncs.demoWaterMarkConfig.markColSpace=value;
		if(!(obj=="initial")){
			configWaterMarkFuncs.loadDemoWaterMark(false);
		}
	},
	markOpacityChanged : function(value,obj){
		configWaterMarkFuncs.demoWaterMarkConfig.markOpacity=value;
		if(!(obj=="initial")){
			configWaterMarkFuncs.loadDemoWaterMark(false);
		}
	},
	markWordFontColorChanged : function(value,obj){
		seajs.use('lui/jquery', function($) {
			var fcHiddenValue=$("input:hidden[name='markWordFontColorHidden']").val();
			if(fcHiddenValue!=value){
				$("input:hidden[name='markWordFontColorHidden']").val(value);
				configWaterMarkFuncs.demoWaterMarkConfig.markWordFontColor=value;
				if(!(obj=="initial")){
					configWaterMarkFuncs.loadDemoWaterMark(false);
				}
			}
		});
	},
	loadDemoWaterMark : function(initial){
		var demoFrame=document.getElementById("demoFrame");
		var demoFrameDoc=demoFrame.contentDocument||demoFrame.Document;
		if(!initial){
			seajs.use('lui/jquery', function($) {
				var postParams=$.param(configWaterMarkFuncs.demoWaterMarkConfig, true);
				$.post(Com_Parameter.ContextPath+"/sys/attachment/sys_att_watermark/sysAttWaterMark.do?method=setWaterMarkInfos",postParams,function(waterMarkInfos){
					configWaterMarkFuncs.demoWaterMarkConfig=waterMarkInfos;
				    setTimeout(function () { 
				    	configWaterMarkFuncs.setWaterMarkBody(demoFrameDoc,initial);
				    }, 1000);
					
				});
			});
		}else{
		    setTimeout(function () { 
		    	configWaterMarkFuncs.setWaterMarkBody(demoFrameDoc,initial);
		    }, 1000);
		}
	},
	saveConfig : function(){
		seajs.use('lui/jquery', function($) {
			Com_Submit(document.sysAttWaterMarkForm, 'saveWaterMark');
		});
	},
	setWaterMarkBody : function(doc,initial){
		seajs.use('lui/jquery',function($){
			$(".mask_div").remove();
			$(doc).find(".mask_div").remove();
		});
		var oTemp = doc.createDocumentFragment();
	    //获取页面最大宽度
	    var mark_width = $(doc).width();
	    //获取页面最大长度
	    var mark_height = $(doc).height();
	    var markWidth=configWaterMarkFuncs.demoWaterMarkConfig.otherInfos.markWidth+16;
	    var markHeight=configWaterMarkFuncs.demoWaterMarkConfig.otherInfos.markHeight+6;
	    var markType=configWaterMarkFuncs.demoWaterMarkConfig.markType;
	    var markOpacity = parseFloat(configWaterMarkFuncs.demoWaterMarkConfig.markOpacity);
	    var colSpace=parseInt(configWaterMarkFuncs.demoWaterMarkConfig.markColSpace);
	    var rowSpace=parseInt(configWaterMarkFuncs.demoWaterMarkConfig.markRowSpace);
	    var cols=parseInt((mark_width-0+colSpace) / (markWidth + colSpace));
	    if(cols<3)
	    	cols=3;
	    if(cols>=5){
	    	colSpace=parseInt((mark_width-0 - markWidth * cols)/ (cols - 1));
	    }else{
	    	colSpace+=40;
	    }
	    var rows=parseInt((mark_height+rowSpace) / (markHeight + rowSpace));
	    if(rows>1){
	    	rowSpace=parseInt((mark_height-markHeight * rows)/ (rows - 1));
		    var rotateType=configWaterMarkFuncs.demoWaterMarkConfig.markRotateType;
	    }
	    var angel=parseInt(configWaterMarkFuncs.demoWaterMarkConfig.markRotateAngel);
	    var rad = angel * (Math.PI / 180);
	    var colLeft;
	    var rowTop;
	    for (var i = 0; i < rows; i++) {
	    	rowTop =  (rowSpace + markHeight) * i;
	        for (var j = 0; j < cols; j++) {
	        	colLeft = 0 + (markWidth + colSpace) * j;
	            var mask_div = document.createElement('div');
	            mask_div.id = 'mask_div' + i + j;
	            if("word"==markType){
				    var markWord=configWaterMarkFuncs.demoWaterMarkConfig.otherInfos.markWord;
				    var markWordFontFamily=configWaterMarkFuncs.demoWaterMarkConfig.markWordFontFamily;
				    var markWordFontSize=configWaterMarkFuncs.demoWaterMarkConfig.markWordFontSize;
				    var markWordFontColor=configWaterMarkFuncs.demoWaterMarkConfig.markWordFontColor;
				    var fontP=doc.createElement('font');
				    fontP.style.fontFamily = markWordFontFamily;
				    fontP.appendChild(doc.createTextNode(markWord));
		            mask_div.style.fontSize = markWordFontSize+'px';
		            mask_div.style.color = markWordFontColor;
		            mask_div.appendChild(fontP);
	            }
	            if("pic"==markType){
	            	var picUrl=configWaterMarkFuncs.demoWaterMarkConfig.otherInfos.picUrl+"&"+$.param(configWaterMarkFuncs.demoWaterMarkConfig, true);
	            	var img=doc.createElement("img");
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
	            	$(mask_div).css("-moz-transform",commonCss);
	            	$(mask_div).css("-o-transform",commonCss);
	            	$(mask_div).css("-webkit-transform",commonCss);
	            	$(mask_div).css("-ms-transform",commonCss);
	            	$(mask_div).css("transform",commonCss);
	            	$(mask_div).css("filter",ieCss);
	            }
	            mask_div.style.visibility = "";
	            mask_div.style.position = "absolute";
	            mask_div.style.left = colLeft + 'px';
	            mask_div.style.top = rowTop+20 + 'px';
	            mask_div.style.overflow = "hidden";
	            mask_div.style.zIndex = "9";
				$(mask_div).css("opacity",markOpacity);
	            mask_div.style.textAlign = "center";
	            mask_div.style.height = markHeight + 'px';
	            $(mask_div).css('line-height',markHeight + 'px');
	            mask_div.style.display = "block";
	            $(mask_div).css('pointer-events','none');
	            if(cols<5)
	            	$(mask_div).css('text-overflow','clip');
	            mask_div.className="mask_div";
	            oTemp.appendChild(mask_div);
	        };
	    };
	    doc.body.appendChild(oTemp);
	}
}
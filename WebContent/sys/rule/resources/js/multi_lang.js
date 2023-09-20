var currentClickArea;
var preClickArea;//上一个区域
var intervalNumber;
var isInitMutilLang = false;

function changeEntryNameLang(event, obj){
	//初始化检查状态成功，跳出
	if(!isInitMutilLang){
		isInitMutilLang = true;
		clearInterval(intervalNumber);
	}
	//隐藏前一个点击的区域
	preClickArea = currentClickArea;
	if(preClickArea && preClickArea != obj){
		$(preClickArea).children().find(".lang_item").css("display","none");
		$(preClickArea).children().find(".lang_icon_other").css("display","");
		//失去焦点，修改官方语言的input的样式，避免被遮住
		$(preClickArea).children().find("input.lang_input_official").css("padding-right",(25 * (langs.length+1)) + 'px');
		checkEntryNameLangStatus(preClickArea);
	}
	if(currentClickArea != obj)
		currentClickArea = obj;
	var src = event.srcElement ? event.srcElement : event.target;
	
	if($(src)[0].localName==='input'||src.nodeName==='INPUT'){
		if($(src).attr("lang")===official){
			//官方语言，进行展示
			if($(src).attr("disabled") && $(src).attr("disabled") == "disabled"){
				return;
			}
			$(obj).children().find(".lang_item").css("display","");
			$(obj).children().find(".lang_icon_other").css("display","none");
			//获取焦点，修改inpu的样式，恢复
			$(obj).children().find("input.lang_input_official").css("padding-right", '25px');
			checkEntryNameLangStatus(obj);
		}
	}else if(($(src)[0].localName==='span' || src.nodeName==='SPAN') && $(src).attr("name") != 'lang_desc'){
		//如果是点击图标，则进行对应的展示
		if($(src).attr("name")==="multi_lang_icon"){
			var text = $(src).text();
			if(text.toLowerCase() === official || text.toUpperCase() === official){
				var pageX = event.clientX;
				var pageY = event.clientY;
				//官方，提示框
				toast('请在当前输入框输入官方语言!',2000,pageX,pageY);
				
				$(obj).children().find(".lang_item").hide();
				$(obj).children().find(".lang_icon_other").show();
				checkEntryNameLangStatus(obj);
				//改变图标颜色
				$(src).css("background-color","#000099");
				$(src).css("color","#FFFFFF");
			}else{
				//其他语言
				for(var i=0; i<langs.length; i++){
					var t = langs[i];
					if(t === text.toLowerCase() || t === text.toUpperCase()){
						//选择的语言
						$(obj).children().find(".lang_item").hide();
						checkEntryNameLangStatus(obj);
						$(obj).children().find(".lang_item."+t).show();
						//改变图标颜色
						$(src).css("background-color","#000099");
						$(src).css("color","#FFFFFF");
					}
				}
			}
		}
	}else{
		$(obj).children().find(".lang_item").css("display","none");
		$(obj).children().find(".lang_icon_other").css("display","");
		//失去焦点，修改官方语言的input的样式，避免被遮住
		$(obj).children().find("input.lang_input_official").css("padding-right",(25 * (langs.length+1)) + 'px');
		checkEntryNameLangStatus(obj);
	}
}

$(document).click(function(event){
	var src = event.srcElement ? event.srcElement : event.target;
	var langObjs = $(src).parents(".multiLang");
	if(!langObjs || langObjs.length <= 0){
		if(currentClickArea){
			$(currentClickArea).children().find(".lang_item").css("display","none");
			$(currentClickArea).children().find(".lang_icon_other").css("display","");
			//失去焦点，修改官方语言的input的样式，避免被遮住
			$(currentClickArea).children().find("input.lang_input_official").css("padding-right",(25 * (langs.length+1)) + 'px');
			checkEntryNameLangStatus(currentClickArea);
		}
		if(preClickArea){
			$(preClickArea).children().find(".lang_item").css("display","none");
			$(preClickArea).children().find(".lang_icon_other").css("display","");
			//失去焦点，修改官方语言的input的样式，避免被遮住
			$(preClickArea).children().find("input.lang_input_official").css("padding-right",(25 * (langs.length+1)) + 'px');
			checkEntryNameLangStatus(preClickArea);
		}
	}
});

//提醒框
function toast(msg,duration,pageX,pageY){
    duration=isNaN(duration)?3000:duration;
    var m = document.createElement('div');
    m.innerHTML = msg;
    m.setAttribute("style","max-width:60%;min-width: 160px;padding:0 14px;height: 30px;color: #FFFFFF;line-height: 30px;text-align: center;border-radius: 1px;position: fixed;top: "+(pageY-30)+"px;left: "+pageX+"px;transform: translate(-50%, -50%);z-index: 999999;background: #47b5e8;font-size: 12px;");
    document.body.appendChild(m);
    setTimeout(function() {
      var d = 0.5;
      m.style.webkitTransition = '-webkit-transform ' + d + 's ease-in, opacity ' + d + 's ease-in';
      m.style.opacity = '0';
      setTimeout(function() { document.body.removeChild(m) }, d * 1000);
    }, duration);
}

/*页面初始化*/
$(function(){
	var times = 0;
	var intervalNumber = setInterval(function(){
		times++;
		$(".multiLang").each(function(index,element) {
			//初始化页面，如果是disable的input，长度需要和span保持一直
			var multiLangInputs = $(element).children().find("input.lang_input_official");
			for(var i=0; i<multiLangInputs.length; i++){
				if($(multiLangInputs[i]).attr("disabled") || $(multiLangInputs[i]).attr("disabled") == 'disabled'){
					$(multiLangInputs[i]).css("width","100%");
					$(element).children().find("span[name='lang_desc']").css("display","none");
					//将按钮隐藏
					$(element).children().find(".multi_lang_icon").hide();
					//排除消息自定义
					$(".multiLang.notify_content").children().find("span[name='lang_desc']").css("display","");
				}
			}
			//初始化检查状态
			if(!isInitMutilLang){
				checkEntryNameLangStatus(this);
			}
		});
		if(times >= 20){
			isInitMutilLang = true;
			clearInterval(intervalNumber);
		}
	}, 100);
})

/*检查多语言是否有值来初始化按钮*/
function checkEntryNameLangStatus(langMul) {
	if($(langMul).children().find("input[lang='" + official + "']") && $(langMul).children().find("input[lang='" + official + "']").length > 0){
		($(langMul).children().find("input[lang='" + official + "']").val()) ? changeEntryNameLangStatus(
				'.multi_lang_icon.' + official, true, langMul)
				: changeEntryNameLangStatus('.multi_lang_icon.' + official, false, langMul);
		for(var i=0; i<langs.length; i++){
			var t = langs[i];
			($(langMul).children().find("input[lang_pull='" + t + "']")
					.val()) ? changeEntryNameLangStatus('.multi_lang_icon.' + t,
					true, langMul) : changeEntryNameLangStatus('.multi_lang_icon.'
					+ t, false, langMul);
		}
	}
}

function changeEntryNameLangStatus(langClassName, flag, langMul) {
	if (flag) {
		$(langMul).children().find(langClassName).css("color", "#4285f4");
		$(langMul).children().find(langClassName).css("background-color",
				"#D9E7FD");
	} else {
		$(langMul).children().find(langClassName).css("color", "#999");
		$(langMul).children().find(langClassName).css("background-color",
				"#DDD");
	}
}


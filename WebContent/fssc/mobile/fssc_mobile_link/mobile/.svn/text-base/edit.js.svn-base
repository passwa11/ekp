
function deleteLink(e){
	var tip = window.confirm("确定要删除当前常用链接吗？");
	if(!tip){
		return;
	}
	var e = e||window.event;
	e.stopPropagation?e.stopPropagation():e.cancelBubble=true;
	e = e.target||e.srcElement;
	$(e).parent().remove();
}
function addLink(){
	$(".fssc-mobile-link-add").removeClass("fssc-mobile-link-hidden");
	document.body.style.overflow = 'hidden';
	$(".ld-info-item div.icon").addClass("icon-none");
	if($(".ld-info-item").find("[name=fdIcon1]").length==0){
		$(".ld-info-item div.content:eq(1)").append('<input type="text" name="fdIcon1" readonly placeholder="请选择图标"/>');
	}
	$("[name=fdIconNew]").val('');
	$("[name=fdNameNew]").val('');
	$("[name=fdUrlNew]").val('');
	var icons = $(".fssc-mobile-links-link");
	var index = icons.length>1?$(icons[icons.length-2]).find("[name='icon-index']").val():0;
	$("[name='edit-index']").val(index+1);
}
function saveLink(){
	var fdName = $("[name=fdNameNew]").val();
	var fdUrl = $("[name=fdUrlNew]").val();
	var fdIcon = $("[name=fdIconNew]").val();
	if(!fdName){
		jqtoast('请填写名称');
		return;
	}
	if(!fdIcon){
		jqtoast('请选择图标');
		return;
	}
	if(!fdUrl){
		jqtoast('请选择链接');
		return;
	}
	var index = $("[name='edit-index']").val();
	if($("[name='icon-index'][value="+index+"]").length>0){
		var icon = $("[name='icon-index'][value="+index+"]").parent();
		icon.find("[name=fdName]").val(fdName);
		icon.find("[name=fdIcon]").val(fdIcon);
		icon.find("[name=fdUrl]").val(fdUrl);
		icon.find("span").html(fdName);
		icon.find("img").attr("src",Com_Parameter.ContextPath+"fssc/mobile/resource/images/icon/"+fdIcon);
	}else{
		var html = [];
		html.push('<div class="fssc-mobile-links-link" onclick="editLink(this)">');
		html.push('<img src="'+Com_Parameter.ContextPath+'fssc/mobile/resource/images/icon/'+fdIcon+'"/>');
		html.push('<span>'+fdName+'</span>');
		html.push('<i onclick="deleteLink();">-</i>');
		html.push('<input type="hidden" name="icon-index" value="'+index+'"/>');
		html.push('<input type="hidden" name="fdName" value="'+fdName+'"/>');
		html.push('<input type="hidden" name="fdUrl" value="'+fdUrl+'"/>');
		html.push('<input type="hidden" name="fdIcon" value="'+fdIcon+'"/>');
		html.push('<input type="hidden" name="fdId" value=""/></div>');
		$(".fssc-mobile-links-link.add").before(html.join(''));
	}
	cancelAddLink();
}
function cancelAddLink(){
	$(".fssc-mobile-link-add").addClass("fssc-mobile-link-hidden");
	document.body.style.overflow = 'auto';
}
function editLink(e){
	var index = $(e).find("[name=icon-index]").val();
	$("[name=edit-index]").val(index);
	$("[name=fdNameNew]").val($(e).find("[name=fdName]").val());
	$("[name=fdUrlNew]").val($(e).find("[name=fdUrl]").val());
	$("[name=fdIconNew]").val($(e).find("[name=fdIcon]").val());
	$(".ld-info-item").find("[name=fdIcon1]").remove();
	$(".ld-info-item div.icon").removeClass("icon-none");
	$(".ld-info-item div.icon").css({"background":"url('"+Com_Parameter.ContextPath+"fssc/mobile/resource/images/icon/"+$(e).find("[name=fdIcon]").val()+"')","background-size":"100%"});
	$(".fssc-mobile-link-add").removeClass("fssc-mobile-link-hidden");
	document.body.style.overflow = 'hidden';
}
function selectIcon(e){
	var cur = $(".ld-info-item").find("[name=fdIcon]").val();
	$(".fssc-mobile-link-icons-box>div").removeClass("active");
	if(cur){
		cur = cur.split("\.")[0];
		$(".fssc-mobile-link-icons-box>div").each(function(){
			if($(this).parent().find("input").val()==cur){
				$(this).addClass("active");
			}
		})
	}
	$(".fssc-mobile-link-icons").removeClass("fssc-mobile-link-icons-hidden");
	$(".fssc-mobile-icons-footer").removeClass("fssc-mobile-icons-footer-hidden");
	document.body.style.overflow = 'hidden';
}
function checkIcon(e){
	e = $(e).find("div");
	$(".fssc-mobile-link-icons-box>div").removeClass("active");
	e.addClass("active");
}
function cancelIcon(){
	$(".fssc-mobile-link-icons").addClass("fssc-mobile-link-icons-hidden");
	$(".fssc-mobile-icons-footer").addClass("fssc-mobile-icons-footer-hidden");
}
function saveIcon(){
	var e = $(".fssc-mobile-link-icons-box>div.active");
	if(e.length==0){
		jqtoast("您没有选择任何图标!");
		return;
	}
	e = e.parent().find("input").val()+".png";
	$(".fssc-mobile-link-icons").addClass("fssc-mobile-link-icons-hidden");
	$(".fssc-mobile-icons-footer").addClass("fssc-mobile-icons-footer-hidden");
	$(".ld-info-item div.icon").removeClass("icon-none");
	$(".ld-info-item div.icon").css({"background":"url('"+Com_Parameter.ContextPath+"fssc/mobile/resource/images/icon/"+e+"')","background-size":"100%"});
	$(".ld-info-item").find("[name=fdIconNew]").val(e);
	$(".ld-info-item").find("[name=fdIcon1]").remove();
}
function save(){
	var params = [];
	$(".fssc-mobile-links-link").each(function(){
		if($(this).hasClass("add"))return;
		params.push({
			fdName:$(this).find("[name=fdName]").val(),
			fdUrl:$(this).find("[name=fdUrl]").val(),
			fdIcon:$(this).find("[name=fdIcon]").val(),
			fdId:$(this).find("[name=fdId]").val()
		})
	})
	$("[name=params]").val(JSON.stringify(params));
	Com_Submit(document.forms[0],'saveMobileLink');
}
function selectLinkUrl(){
	$.ajax({
		type : 'post',
		url : Com_Parameter.ContextPath +'fssc/mobile/fssc_mobile_link/fsscMobileLink.do?method=getMobilUrl',
	}).success(function(data) {
		console.log(data)
		data = JSON.parse(data);
		var picker = new Picker({
			data: [data,data[0].sub]
		});
		picker.show();
		picker.on('picker.select', function (selectedVal, selectedIndex) {
			var fdName = data[selectedIndex[0]].sub[selectedIndex[1]].text;
			var fdUrl = data[selectedIndex[0]].sub[selectedIndex[1]].value;
			$("[name=fdNameNew]").val(fdName);
			$("[name=fdUrlNew]").val(fdUrl);
		});
		picker.on('picker.change',function(selectedVal,selectedIndex){
			if(selectedVal==0){
				picker.refillColumn(1, data[selectedIndex].sub);
			}
		})
		//回车搜索
        $("#search_input").keypress(function(e) {
			if (e.which == 13) {
				var keyword = $("[name='pick_keyword']").val();
				if (keyword) {
					$.ajax({
						type : 'post',
						url : Com_Parameter.ContextPath +'fssc/mobile/fssc_mobile_link/fsscMobileLink.do?method=getMobilUrl',
						data : {
							"keyword" : keyword
						},
					}).success(function(data) {
						data = JSON.parse(data);
						picker.refillColumn(0, data);
						picker.refillColumn(1, data[0].sub);
						picker.on('picker.change',function(selectedVal,selectedIndex){
							if(selectedVal==0){
								picker.refillColumn(1, data[selectedIndex].sub);
							}
						})
						picker.on('picker.select', function (selectedVal, selectedIndex) {
							var fdName = data[selectedIndex[0]].sub[selectedIndex[1]].text;
							var fdUrl = data[selectedIndex[0]].sub[selectedIndex[1]].value;
							$("[name=fdNameNew]").val(fdName);
							$("[name=fdUrlNew]").val(fdUrl);
						});
					}).error(function(data) {
					})
				}
			}
		});
        // 获取到焦点
		$("#search_input").focus(function() {
			$(".weui-icon-clear").attr("style", "display:block;");
		})
		// 取消
		$(".weui-icon-clear").click(function(e) {
			$.ajax({
				type : 'post',
				url : Com_Parameter.ContextPath +'fssc/mobile/fssc_mobile_link/fsscMobileLink.do?method=getMobilUrl',
			}).success(function(data) {
				data = JSON.parse(data);
				picker.refillColumn(0, data);
				picker.refillColumn(1, data[0].sub);
				picker.on('picker.change',function(selectedVal,selectedIndex){
					if(selectedVal==0){
						picker.refillColumn(1, data[selectedIndex].sub);
					}
				})
				picker.on('picker.select', function (selectedVal, selectedIndex) {
					var fdName = data[selectedIndex[0]].sub[selectedIndex[1]].text;
					var fdId = data[selectedIndex[0]].sub[selectedIndex[1]].value;
					var type = data[selectedIndex[0]].value;
					$("[name=fdNameNew]").val(fdName);
					if(type=='expense'){
						$("[name=fdUrlNew]").val('fssc/expense/fssc_expense_mobile/fsscExpenseMobile.do?method=add&docTemplateId='+selectedVal[1]);
					}else{
						$("[name=fdUrlNew]").val('fssc/fee/fssc_fee_mobile/fsscFeeMobile.do?method=add&i.docTemplate='+selectedVal[1]);
					}
				});
				$("[name='pick_keyword']").val('');
			}).error(function(data) {
				console.log('获取分类信息失败');
			})
		});
	}).error(function(data) {
	})
}

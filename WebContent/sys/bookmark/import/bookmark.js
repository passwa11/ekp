seajs.use([ 'sys/ui/js/dialog','lui/util/str', 'lang!sys-bookmark:sysBookmark.mechanism', 'theme!form'],function(dialog,str,lang) {
	window.CheckBooked = function(url,cateId){
		var booked = false;
			$.ajax({  
		          type : "post",  
		          url : Com_Parameter.ContextPath+"sys/bookmark/sys_bookmark_main/sysBookmarkMain.do?method=CheckBooked",  
		          data : {bookUrl:url,fdCateId:cateId},  
		          async : false,  
		          success : function(json){  
					 var rtn = str.toJSON(json);
					 if(rtn.booked == "true"){
						 booked = true;
					 }
		          } 
		     });
		return booked;
	},
	window.CheckBookedInAllCate = function(url){
		var booked = false;
			$.ajax({  
		          type : "post",  
		          url : Com_Parameter.ContextPath+"sys/bookmark/sys_bookmark_main/sysBookmarkMain.do?method=CheckBookedInAllCate",  
		          data : {url:url},  
		          async : false,  
		          success : function(json){ 
		        	 var rtn = str.toJSON(json);
					 if(rtn.booked == 'false'){
						$("#bookedBtn").show()
					 }if(rtn.booked == 'true'){
						$("#delbookedBtn").show();
						booked = true;
					 }
		          } 
		     });
		return booked;
	},
	window.deleteBookedMark = function(url){
		$.ajax({
			 type : "post",  
	         url : Com_Parameter.ContextPath+"sys/bookmark/sys_bookmark_main/sysBookmarkMain.do?method=deleteBookmarkByUrl",  
	         data : {url:url},  
	         async : true,  
	         success : function(json){
	        	var rtn = str.toJSON(json);
	        	if(rtn.flag == "true"){
	        		$("#bookedBtn").show()
	        		$("#delbookedBtn").hide();
	        		dialog.success(lang["sysBookmark.mechanism.opt.delSuccess"]);
	        	}else{
	        		dialog.failure(lang["sysBookmark.mechanism.opt.fail"]);
	        	}
	        	
	          } 
		})
	}
});



//param:url,subject,fdModelId,fdModelName
function ___BookmarkDialog(param) {
	var createBookMarkForm = function(param,toolbar,lang){
		var xdiv = LUI.$("<form id='bookmark_form' name='bookmark_form'></form>");
		var xtable = LUI.$("<table class='tb_simple' style='width:90%;margin:6px auto;'></table>");
		var xtr1 = LUI.$("<tr><td><img src='"+Com_Parameter.ContextPath+"sys/bookmark/import/img/bookmark.png'></td>"
					+ "<td colspan='2' style='line-height:25px;'><b>"+lang['sysBookmark.mechanism.add']
					+ "</b><br>" + lang['sysBookmark.mechanism.summary'] + "</td></tr>");
		var xtr2 = LUI.$("<tr></tr>");
		var xtd = LUI.$("<td></td>");
		xtd.append("<input type='hidden' name='fdModelName'  value='"+param.fdModelName+"' />");
		//单文档收藏
		var cateFieldName = "docCategoryId";
		if(param.url != null && param.url != ""){
			xtd.append("<input type='hidden' name='fdModelId'  />");
			xtd.append("<input type='hidden' name='fdUrl' />");
			xtd.append("<input text='text' name='docSubject' class='inputsgl' style='width:250px;' validate='maxLength(200)'/>");
			xtd.append("<input type='hidden' id='bookmark_optType' name='bookmark_optType' value='save'>");
			xtr2.append("<td>"+lang['sysBookmark.mechanism.label.name']+"</td>").append(xtd).append("<td></td>");
			cateFieldName = "docCategoryId";
			xtr2.find("[name='fdModelId']").val(param.fdModelId);
			xtr2.find("[name='fdUrl']").val(param.url);
			xtr2.find("[name='docSubject']").val(param.subject);
		}
		//批量收藏
		if(param.ids!=null && param.ids != ""){
			xtd.append("<input type='hidden' name='ids' value='"+param.ids+"' />");
			xtd.append("<input type='hidden' name='fdTitleProName' value='"+param.fdTitleProName+"' />");
			xtd.append("<input type='hidden' id='bookmark_optType' name='bookmark_optType' value='updateBookmarkAll'/>");
			xtr2.append("<td></td>").append(xtd).append("<td></td>");
			cateFieldName = "fdCategoryId";
			xtr2.find("[name='ids']").val(param.ids);
			xtr2.find("[name='fdTitleProName']").val(param.fdTitleProName);
		} 
		
		var xtr3 = LUI.$("<tr></tr>").append("<td>"+lang['sysBookmark.mechanism.create.pos']+"</td>");
		var std = LUI.$("<td></td>");
		var sel = LUI.$("<select id='bookmark_category' name='"+cateFieldName+"' class='inputsgl' style='width:252px;'></select>");
		std.append(sel);
		xtr3.append(std);
		var btd = LUI.$("<td style='width:50px;'></td>");
		//var btn = LUI.$("<input type='button' value='"+lang['sysBookmark.mechanism.create.cate']+"' class='lui_form_button'  id='bookmark_newcategory'></input>");
		//btd.append(btn);
		var  btn =  new toolbar.Button(
				{
					 id:"bookmark_newcategory",
					 text:lang['sysBookmark.mechanism.create.cate']
				 }
			 );
		btn.startup();
		btd.append(btn.element);
		btn.draw();
		xtr3.append(btd);
		xtable.append(xtr1).append(xtr2).append(xtr3);
		xdiv.append(xtable);
		xdiv.append("<div style='border-bottom: 1px #dedede solid;height:10px;'></div>");
		xdiv.find('a[data-type="bookmark"]').each(function() {
			this.href = Com_Parameter.ContextPath + "sys/person/setting.do?setting=sys_bookmark_person_cfg";
			this.target = "_blank";
			this.setAttribute('class', 'com_btn_link');
		});
		return xdiv;
	};
	
	var createSpace = function(x){
		var space = "";
		for(var i=0;i<x;i++){
			space += "&nbsp;&nbsp;";
		}
		return space;
	};
	var createOption = function(json,x){
		var temp = "<option value='"+json.value+"'>"+createSpace(x)+json.text+"</option>";
		if(json.children!=null&&json.children.length>0){
			for(var i =0 ;i<json.children.length;i++){
				temp += createOption(json.children[i],x+2);
			}
		}
		return temp;
	};
	
	seajs.use([ 'sys/ui/js/dialog', 'lui/toolbar', 'lui/topic', 'lang!sys-bookmark:sysBookmark.mechanism', 'theme!form'],function(dialog,toolbar,topic,lang) {
		var validation=$KMSSValidation();
		var top = Com_Parameter.top || window.top;
		var dia = dialog.build({
				config : {
					width : 520,
					height : 200,
					title : lang['sysBookmark.mechanism.add'],
					content : {
						type : "element",
						elem : createBookMarkForm(param,toolbar,lang),
						iconType : "",
						buttons : [{
							name : lang['sysBookmark.mechanism.button.ok'],
							value : true,
							focus : true,
							fn : function(value,dialog1) {
								var docSubjectObj = LUI.$("[name='docSubject']");
								if(!validation.validateElement(docSubjectObj[0])){
									return;
								}
								if(LUI.$("#bookmark_category",top.document).val()==null || LUI.$("#bookmark_category",top.document).val()==''){
									dialog.alert(lang['sysBookmark.mechanism.prompt']);
									return;
								}
								if(!CheckBooked(LUI.$("[name='fdUrl']",top.document).val(),LUI.$("#bookmark_category",top.document).val())){
									var xdata = LUI.$("#bookmark_form",top.document).serialize();
									var xtype = LUI.$("#bookmark_optType",top.document).val();
									LUI.$.post(Com_Parameter.ContextPath+"sys/bookmark/sys_bookmark_main/sysBookmarkMain.do?method="+xtype,xdata,function(json){
										if(json != null && json.status == true){
											// 收藏成功，获取该文档总收藏数量，并发布事件
											LUI.$.post(Com_Parameter.ContextPath + "sys/bookmark/sys_bookmark_main/sysBookmarkMain.do?method=markCountByModel", xdata, function(result) {
												// 发布事件，将获取到的收藏数量发布出去
												topic.publish('markCountByModel',{count: result.count});
											}, "json");
											$("#bookedBtn").hide();
											$("#delbookedBtn").show();
											dialog.success(lang["sysBookmark.mechanism.opt.sucess"]);
										}else{
											if (json.message && json.message.length > 0 && json.message[0].msg) {
												dialog.failure(json.message[0].msg);
											} else {
												dialog.failure(lang["sysBookmark.mechanism.opt.fail"]);
											}
										}
										dialog1.hide();
									},'json');
								}else{
									 dialog1.hide();
									 dialog.failure(lang["sysBookmark.mechanism.exsit"]);
								}
							}
						},{
							name : lang['sysBookmark.mechanism.button.cancel'],
							value : false,
							focus : true,
							styleClass:'lui_toolbar_btn_gray',
							fn : function(value,dialog) {
								dialog.hide();
							}
						}]
					},
					actor : {
						type : "default",
						animate:false
					}					
				}
		});
		
		dia.on("show",function(){
			var createIframe = null;
			window._bookmark_cate_refresh = function(refresh, fdSelectedId){
				if(refresh){
					LUI.$.getJSON(Com_Parameter.ContextPath+"sys/bookmark/sys_bookmark_main/sysBookmarkCategory.do?method=tree&fetchAll=true&type=all",function(json){
						LUI.$("#bookmark_category",top.document).html("");
						for(var i=0;i<json.length;i++){
							LUI.$("#bookmark_category",top.document).append(createOption(json[i],0));
						}
						if(fdSelectedId) {
							LUI.$("#bookmark_category",top.document).val(fdSelectedId);
						}
						if(createIframe!=null)
							createIframe.hide();
					});
				}else{
					if(createIframe!=null)
						createIframe.hide();
				}
			};
			LUI.$("#bookmark_newcategory",top.document).click(function(){
				createIframe = dialog.build({config:{
					width: 600,
					height: 300,
					lock: true,
					cache: false,
					opener : window,
					title : lang['sysBookmark.mechanism.dialog.title'],
					content : {
						id : 'bookMark_div',
						scroll : false,
						type : "iframe",
						url : '/sys/bookmark/sys_bookmark_person_category/sysBookmarkPersonCategory.do?method=add&forward=editUi'
					}
				}}).show();
			});
			window._bookmark_cate_refresh(true);
		});
		
		dia.show();
	});
}
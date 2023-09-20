/*压缩类型：标准*/
Com_IncludeFile("tag.css",Com_Parameter.ContextPath+"sys/tag/resource/css/","css",true);
function TagOpt(modelName,modelId,key,params){
	this.modelName = modelName;
	this.modelId = modelId;
	this.key = key;
	this.params = params;
	var self = this;
	
	this.onload = function(){
		
		if(self.params['model']=='view'){//阅读时
			
			var tagNames = $.trim(self.params['fdTagNames']);
			var areaNode = $('div.tag_area');
			
			if (!tagNames) {
				
				if (self.params['render']) 
					new Function('rtn', self.params['render'] + '(rtn)')([]);
				areaNode.hide();
				return;
				
			}
			
			var href = Com_Parameter.ContextPath
					+ "sys/ftsearch/searchBuilder.do?method=search&searchFields=tag&newLUI=true";
			if (this.modelName)
				href += ('&modelName=' + self.modelName);
			
			var tagNamess = tagNames.replace(/&#039;/g,"'")
									.replace(/&#034;/g,'"')
									.replace(/&lt;/g,"<")
									.replace(/&gt;/g,">")
									.replace(/&amp;/g,"&");
			var tags = tagNamess.split(/;|；/);
			
			var container = $("div.tag_content");
			container.empty();
			$
					.ajax({
						type : "post",
						url : Com_Parameter.ContextPath
								+ 'sys/tag/sys_tag_main/sysTagMain.do?method=checkSpecial',
						data : {
							tagNames : tagNamess
						},
						dataType : "json",
						success : function(data) {
							var rtn = [];
							for (var i = 0; i < tags.length; i++) {
								var tag = tags[i];
								//处理转义字符
								var queryTag = tag.replace(/&lt;/g,"<")
								.replace(/&gt;/g,">")
								.replace(/&#034;/g,"'")
								.replace(/&amp;/g,"&")
								.replace(/&quot;/g,"\"")
								;
								rtn
										.push({
											text : tags[i],
											href : (href + "&queryString=" + encodeURIComponent(queryTag)),
											isSpecial : data[i][i]
										})
							}

							// 阅读页面标签自定义展现
							if (self.params['render']) {
								new Function('rtn', self.params['render']
										+ '(rtn)')(rtn);
								areaNode.hide();
								return;
							}
							
							areaNode.show();
							for (var i = 0; i < rtn.length; i++) {
								if (rtn[i] != "") {
									var tagDom = $("<div/>");
									tagDom.addClass("tag_tagSign");
									if (rtn[i]['isSpecial'] == 1) {
										tagDom.addClass("tag_tagSignSpecial");
									}
									tagDom.html('<a href ="' + href
											+ "&queryString="
											+ encodeURIComponent(rtn[i].text.replace(/&lt;/g,"<")
													.replace(/&gt;/g,">")
													.replace(/&#034;/g,"'")
													.replace(/&amp;/g,"&")
													.replace(/&quot;/g,"\""))
											+ '" target="_blank"></a>');
									tagDom.find('a').text(rtn[i].text)
									container.append(tagDom);
								}
							}
						}
					});
				
		}
		if(self.params['model']=='edit'){//编辑时
			//初始化
			$("input[name='sysTagMainForm.fdTagNames']").bind("focus",function(){
				self._tag_showTagApplication(true);
			});
			$("input[name='sysTagMainForm.fdTagNames']").bind("click",function(evt){
				evt.stopPropagation();
			});
			$(document.body).bind('click',function(){
				self._tag_showTagApplication(false);
			});
			//提示关闭
//			$("#a_close").click(function(){
//				self._tag_showTagApplication(false);
//			});
			//标签选择
			$("#tag_selectItem").click(function(){
				 //          Dialog_Tree( true , null , null , ' ' ,
                //                        'sysTagCategorApplicationTreeService&fdCategoryId=!{value}',self.params['tree_title' ],
                //                       false ,self._tag_afterSelect, null, false, true ,null );
                /*var tags = $( "input[name='sysTagMainForm.fdTagNames']").val();
                $( "input[name='sysTagMainForm.fdTagIds']").val(tags);
                Dialog_TreeList( true, "sysTagMainForm.fdTagIds" , "sysTagMainForm.fdTagNames" , ' ' ,
                                  'sysTagCategorTreeService&type=1&fdCategoryId=!{value}' ,
                                  self.params[ 'tree_title' ],'sysTagByCategoryDatabean&type=getTag&fdCategoryId=!{value}' ,
                                  self._tag_afterSelect, 'sysTagByCategoryDatabean&key=!{keyword}&type=search' );*/
				var url = "";
				var top = Com_Parameter.top || window.top;
				if(top.addTagSign=="1"){
					url = "/sys/tag/import/addTag.jsp?mulSelect=true&addTagSign=1";
				}else if(top.addTagSign=="0"){
					url = "/sys/tag/import/addTag.jsp?mulSelect=true&addTagSign=0";
				}
				
				top.window.selectTagNames = $("input[name='sysTagMainForm.fdTagNames']").val();
				
				seajs.use(['lui/dialog', 'lui/util/env','lang!sys-tag','lang!sys-ui'],function(dialog, env,lang,ui_lang) {
				dialog.iframe(url,
						lang["sysTag.choiceTag"], null, {
					width : 900,
					height : 550,
					buttons : [
						{
							name : ui_lang["ui.dialog.button.ok"],
							value : true,
							focus : true,
							fn : function(value,_dialog) {
								if(_dialog.frame && _dialog.frame.length > 0){
									var _frame = _dialog.frame[0];
									var contentWindow = $(_frame).find("iframe")[0].contentWindow;
									if(contentWindow.onSubmit()) {
										var datas = contentWindow.onSubmit().slice(0);
										if(datas.length>=0){
											selectWordCallBack(datas);	
	
											setTimeout(function() {
												_dialog.hide(value);
											}, 200);
											
										}
									}
								}
								
							}
						}
						,{
							name :ui_lang["ui.dialog.button.cancel"],
							value : false,
							styleClass : 'lui_toolbar_btn_gray',
							fn : function(value, dialog) {
								dialog.hide(value);
							}
						}
					]	
				});
				});
			});
			//标签直接选择
			$("#id_application_div").bind("click", function(evt) {
				var $target = $(evt.target);
				if ($target.attr('name') == 'tag_hot_tag'
						|| $target.attr('name') == 'tag_used_tag')
					self._tag_onSelectValue($target);
				evt.stopPropagation();
			});
			//增加表单提交事件
			var events = Com_Parameter.event["submit"];
			events[events.length] = self._tag_submit;
		}
	};
	/******************************内部函数*************************************/
	this._tag_submit = function(){
		var queryConditionName = self.params['fdQueryCondition'];
		var queryCondition = "";
		if(queryConditionName!=null){
			var queryConditionNames = queryConditionName.split(";");
			for(var i = 0; i < queryConditionNames.length; i++){
				var condition = queryConditionNames[i];
				var conditionObj = document.getElementsByName(condition);
				if(conditionObj != null && conditionObj.length>0){
					queryCondition = queryCondition + conditionObj[0].value+";";
				}
			}
		}
		$("input[name='sysTagMainForm.fdQueryCondition']").val(queryCondition);
		return true;
	};
	
	this._tag_afterSelect = function(rtnVal){
		if(rtnVal == null)
			return;
		var names = $.trim($("input[name='sysTagMainForm.fdTagNames']").val());
		var ids = $.trim($("input[name='sysTagMainForm.fdTagIds']").val());	
		var nameOldArr = null;
		var idOldArr = null;
		if(names!=null && names!='' ){
			nameOldArr = names.split(' ');
			idOldArr = ids.split(' ');
		}else{
			nameOldArr = [];
			idOldArr = [];
		}
		var nameArr = nameOldArr;
		var idArr = idOldArr;
		for(var i=0;i< rtnVal.GetHashMapArray().length;i++){
			var newName = rtnVal.GetHashMapArray()[i]['name'];
			var newId = rtnVal.GetHashMapArray()[i]['id'];
			var hasCfg = false;
			$.each(nameOldArr,function(index,value){
				if(value == newName){
					hasCfg = true;
				}
			});
			if(!hasCfg){
				nameArr.push(newName);
				idArr.push(newId);
			}
		}
		$("input[name='sysTagMainForm.fdTagNames']").val(nameArr.join(' '));
		$("input[name='sysTagMainForm.fdTagIds']").val(idArr.join(' '));
	};
	
	this._tag_showTagApplication = function(isShow){
		var divObj = $("#id_application_div");
		if(isShow == true){
			var queryCondition = '';
			var queryConditionValue = self.params['fdQueryConditionValue'];
			if(queryConditionValue)
				queryCondition = queryConditionValue+";";
			if(!queryCondition){
				var queryConditionName = self.params['fdQueryCondition'];
				if(queryConditionName!=null){
					var queryConditionNames = queryConditionName.split(";");
					for(var i = 0; i < queryConditionNames.length; i++){
						var condition = queryConditionNames[i];
						var conditionObj = document.getElementsByName(condition);
						if(conditionObj != null){
							queryCondition = queryCondition + conditionObj[0].value+";";
						}
					}
				}
			}
			if(queryCondition){
				$("input[name='sysTagMainForm.fdQueryCondition']").val(queryCondition);
				var kmssData = new KMSSData(); 
				kmssData.AddBeanData("sysTagApplicationLogSupplyService&queryCondition="+queryCondition+"&modelName="+self.modelName);
				var templetData=kmssData.GetHashMapArray();		
				if(templetData.length > 0){	
					var hotTitle = templetData[0]['hotTitle'];
					hotTitle = hotTitle.replace(/&nbsp;/g,"");
					$("#hot_id").html('<span>'+self.params['tag_msg1']+'</span>'+hotTitle);
					var usedTitle = templetData[1]['usedTitle'];
					usedTitle = usedTitle.replace(/&nbsp;/g,"");
					$("#used_id").html( '<span>'+self.params['tag_msg2']+'</span>'+usedTitle);
				}
			}
			divObj.show();
		}else{
			divObj.hide();
		}
	};
	//选择已有标签
	this._tag_onSelectValue = function(obj){
		var tagObj = $("input[name='sysTagMainForm.fdTagNames']");
		var obj_value = $.trim(tagObj.val());//获取text里的标签并删除前后空格
		var selectVal = $(obj).text();
		var position = obj_value.indexOf(selectVal);//后来选择的标签
		if(obj_value==""){//如果text里没有标签
			obj_value = selectVal;
		}else{//如果text里有标签,加上后来选择的标签
			var values = obj_value.split(/[;；]/);
			
			if((values[values.length-1])==""){
				values.pop();
			}
			var hasVal = false;//原标签是否含有新选标签标记
			for(var i=0;i<values.length;i++){
				if(values[i]==selectVal){//如果原有此标签,标记，跳出循环
					hasVal = true;
					break;
				}
			}
			if(!hasVal){//如果原来没有该选择的标签,放入此新标签进标签数组
				values.push(selectVal);
			}
			obj_value = values.join(";");//标签数组加上;合并成长串标签
		}
		tagObj.val(obj_value);//将长串标签放入text中
	};
}

function selectWordCallBack(datas) {
	if (datas != null && typeof (datas) != "undefined") {
		var item;
		var str="";
		for(var i=0; i<datas.length; i++){
			item=datas[i];
			str=str+item.fdName+";";
		}
		str = str.substring(0,str.length-1);
		$("input[name='sysTagMainForm.fdTagNames']").val(str);
	}
}

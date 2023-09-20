<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@ page
	import="com.landray.kmss.kms.category.model.KmsCategoryConfig"%>
<script type="text/javascript">
	var kmsCategoryEnabled=false;
<%
	KmsCategoryConfig kmsCategoryConfigJs = new KmsCategoryConfig();
	String kmsCategoryEnabledJs = (String) kmsCategoryConfigJs.getDataMap().get("kmsCategoryEnabled");
	if ("true".equals(kmsCategoryEnabledJs)) {
%>
	kmsCategoryEnabled=true;
<%
	}
%>
	/**
	 * 初始化默认属性
	 */
	function initDefineProperty() {
		var url = this.location.href;
		var defineProperty = '${param.defineProperty}';
		if (defineProperty == "true") {
			var num = url.indexOf("defineProperty=true");
			var len = "defineProperty=true".length;
			//解析字符串默认属性
			anatomyKeyValue(url.substring(num + len + 1));
		}
	}

	/**
	 * 解析字符串设置属性
	 */
	function anatomyKeyValue(str) {

		var propertys = str.split("&");
		var len = propertys.length;
		var prefix = "extendDataFormInfo.value(";
		var suffix = ")";
		var keyValue, key, value, elem;
		for ( var i = 0; i < len; i++) {
			keyValue = propertys[i].split("=");
			key = prefix + keyValue[0] + suffix;
			value = keyValue[1];
			elem = document.getElementsByName(key);
			if (!elem || elem.length <= 0)
				continue;
			//文本框
			if (elem[0].type == "text") {
				elem[0].value = value;

			} else if (elem[0].type == "hidden") {
				var _elem = document.getElementsByName("_" + key);
				//文本选择（地址、组织架构）
				if (!_elem) {
					elem[0].value = value;
				}
				//复选框
				elem = _elem;
				var elen = elem.length;
				for ( var j = 0; j < elen; j++) {
					if (value.indexOf(elem[j].value) >= 0) {
						if (elem[j].type == "checkbox") {
							$(elem[j]).trigger("click");
						}
					}
				}

				//下拉框
			} else if (elem[0].type == "select-one") {
				elem = elem[0].children;
				var elen = elem.length;
				for ( var j = 0; j < elen; j++) {
					if (elem[j].value == value) {
						elem[j].selected = true;
						break;
					}
				}

				//单选
			} else if (elem[0].type == "radio") {
				var elen = elem.length;
				for ( var j = 0; j < elen; j++) {
					if (elem[j].value == value) {
						elem[j].checked = true;
						break;
					}
				}
			}
		}
	}

	//点击新版本
	function showNewEdtion(obj) {
		var url = obj.rev;
		var version = Dialog_PopupWindow(url, 497, 310);
		if (version != null) {
			var href = assemblyHref();
			href = href + "&version=" + version;
			window.location.href = href;
		}
	}

	function assemblyHref() {
		var href = window.location.href;
		var reg = /method=\w*/;
		href = href.replace(reg, "method=newEdition");
		var reg1 = /fdId/;
		href = href.replace(reg1, "originId");
		return href;
	}


	/*修改分类*/
	
	function modifyCate(unClose, isModify) {
		seajs.use(['lui/dialog', 'lui/util/env'],function(dialog, env) {
			if(isModify) {
				//修改分类，弹出确认框
				var confirmCategory="";
				if(kmsCategoryEnabled){
					confirmCategory="${lfn:message('kms-multidoc:kmsMultidoc.confirmCategory.categoryTrue')}";
				}else{
					confirmCategory="${lfn:message('kms-multidoc:kmsMultidoc.confirmCategory')}";
				}
				dialog.confirm(confirmCategory, 
						function(flag) {
							if(flag)
								dialogForNewFile();
						}
			    );
			} else dialogForNewFile();

				
			function dialogForNewFile() {
				var selectCategory="";
				if(kmsCategoryEnabled){
					selectCategory="${lfn:message('kms-multidoc:kmsMultidocTemplate.commonConfig.selectCategory.categoryTrue')}";
				}else{
					selectCategory="${lfn:message('kms-multidoc:kmsMultidocTemplate.commonConfig.selectCategory')}";
				}
				
				dialog.simpleCategoryForNewFile(
						{
							modelName : "com.landray.kmss.kms.knowledge.model.KmsKnowledgeCategory",
							urlParam:"/kms/multidoc/kms_multidoc_knowledge/kmsMultidocKnowledge.do?method=add&fdTemplateId=!{id}&fdTemplateName=!{name}&fdModelId=${param.fdModelId}&fdModelName=${param.fdModelName}&fdWorkId=${param.fdWorkId}&fdPhaseId=${param.fdPhaseId}",
							mulSelect : false,
							action : function(rtn) {
								// 无分类状态下（一般于门户快捷操作）创建文档，取消操作同时关闭当前窗口
								if (!unClose && !rtn)
									window.close();
							},
							winTitle : selectCategory,
							canClose : true,
							___urlParam : {'fdTemplateType': '1,3'},
							url : "/kms/knowledge/kms_knowledge_category/simple-category.jsp"
						}, "", false, null, "", LUI.$('input[name=docCategoryId]').val(), "_self", true);
				
				
			}

	    });
	}
	/*修改辅类别*/
	function modifySecondCate(canClose) {
		seajs.use(['lui/dialog', 'lui/jquery'], function(dialog, $) {
			dialog.simpleCategory({
				modelName: 'com.landray.kmss.kms.knowledge.model.KmsKnowledgeCategory',
				authType: 2,
				idField: 'docSecondCategoriesIds',
				nameField: 'docSecondCategoriesNames',
				mulSelect: true,
				canClose: true,
				notNull : false ,
				___urlParam: {
					'fdTemplateType': '1,3'
				}
			});
		});
	}

	/*切换作者类型*/
	function changeAuthorType(value) {
		LUI.$('#innerAuthor').hide();
		LUI.$('#outerAuthor').hide();
		if (value == 1) {
			if(!LUI.$('#innerAuthor input').val()) {
				LUI.$('.innerAuthor_td').find(".mf_list").html("");
			}
			LUI.$('#outerAuthor input').attr('validate', '');
			LUI.$('#innerAuthor input').attr('validate', 'required');
			LUI.$('#innerAuthor').show();
			
			LUI.$("#authorsArrary input").each(function(){
				$(this).attr('validate','required');
			});
			
		}
		if (value == 2) {
			LUI.$('#innerAuthor input').attr('validate', '');
			LUI.$('#outerAuthor input').attr('validate', 'required checkName maxLength(200)');
			LUI.$('#outerAuthor').show();
			
			LUI.$("#authorsArrary input").each(function(){
				$(this).attr('validate','');
			});
			changeAuthodInfo(null);
		}
	}
	
	function authorChange() {
		if($("[name='authorType']:checked").val() == "1") {
			$("[name='outerAuthor']").val(null);
		} else {
			$("[name='docAuthorId']").val(null);
			$("[name='docAuthorName']").val(null);
			$("#authorsArrary").remove();
		}
		return true;
	}
	
	if(Com_Parameter.event["submit"]) {
		Com_Parameter.event["submit"].push(authorChange);
	}

	seajs.use( [ 'lui/topic' ], function(topic) {
		topic.subscribe('JUMP.STEP', function(evt) {
			//验证基本信息
			if(evt.last==0 ) {
				if(pre_nextValidate(document.getElementById('validate_ele0'),evt)) {
					//添加标题
					addDocTitle();
					//添加右边信息
					addInfo();
				}
			}
			//验证完善知识属性
			if(evt.last==2 && evt.cur==3){
				pre_nextValidate(document.getElementById('validate_ele2'),evt);
			}
		});

	});

	//添加标题
	function addDocTitle() {
		var title = document.getElementsByName("docSubject")[0].value;
		seajs.use(['lui/util/env'],function(env){
			LUI.$('#title_span').html(env.fn.formatText(title));
			});
	}

	//右边信息是否显示

	//添加右边信息/
	function addInfo() {
		//让右侧信息显示
		showFlag = rightInfoShow();
		addRightInfo();
	}
	
	//右边信息显示切换
	function rightInfoShow() {
		LUI.$('#r_info').removeClass("lui_multidoc_hideLi");
		LUI.$('#r_info').addClass("lui_multidoc_showLi");

		LUI.$('#r_info1').removeClass("lui_multidoc_hideLi");
		LUI.$('#r_info1').addClass("lui_multidoc_showLi");

		if(!kmsCategoryEnabled){
			var secondCategoryValue = document.getElementsByName("docSecondCategoriesNames")[0].value;
			if(secondCategoryValue != ""){
				LUI.$('#r_info2').removeClass("lui_multidoc_hideLi");
				LUI.$('#r_info2').addClass("lui_multidoc_showLi");
			}
		}
		
	}
	
	//添加右边信息
	function addRightInfo(){
		var authorType = $("input[name='authorType']:checked").val();
		var author = document.getElementsByName("docAuthorName")[0].value;
		if(authorType == "2"){
			author = document.getElementsByName("outerAuthor")[0].value;
		}
		LUI.$('#author_span').html(author);

		var docTemplate = document.getElementsByName("docTemp")[0].innerHTML; 
		LUI.$('#docTemplate_span').html(docTemplate);

		//var secondaryCategories = document.getElementsByName("docSecondCategoriesNames")[0].value;
		//LUI.$('#secondaryCategories_span').html(secondaryCategories);
		
	}
	
	var ___validator;
	//点击上一步下一步的验证
	function pre_nextValidate(obj,_evt) {
		//去掉前面的验证
		if(!___validator){
			___validator = $KMSSValidation(obj);
			___validator.addValidators(validations);
		}
		___validator.form = obj;
		//附件上传完毕
		var isFilesLoaded = attachmentObject_attachment.isUploaded();
		if(!isFilesLoaded) {
			seajs.use(['lui/dialog'], function(dialog) {
				dialog.alert("${lfn:message('kms-multidoc:kmsMultidoc.upload.files.tip')}");
			});
			_evt.cancel = true;
			return false;
		}
		//只验证第一个
		if(!Com_Parameter.event["submit"][0]()) {
				_evt.cancel = true;
				return false;
		}
		return true;
	}

	//验证不过后的跳转
	var _afterFormValidate = function (result, form, first) {
		// 改造后没有下一步不需要跳转
		return
		if(!result)	{
			var t = LUI.$(first).parents('[data-lui-content-index]');
			LUI('__step').fireJump(t.attr('data-lui-content-index'));
		}		
	}

	/*根据类型提交*/
	function commitMethod(commitType, saveDraft) {
		seajs.use(['lui/dialog','lui/jquery'], function(dialog, $) {
			if(!___validator) {
				___validator = $KMSSValidation(document.forms['kmsMultidocKnowledgeForm'],
						{afterFormValidate:_afterFormValidate});
				___validator.addValidators(validations);
			}
			else {
				 ___validator.form = document.forms['kmsMultidocKnowledgeForm'];
				 ___validator.options.afterFormValidate = _afterFormValidate;
				 ___validator.addValidators(validations);
			}
			
			var formObj = document.kmsMultidocKnowledgeForm;
			var docStatus = document.getElementsByName("docStatus")[0];
			if (saveDraft == "true") {
				docStatus.value = "10";
				 //暂存调用,在暂存时,移除自定义表单部分的必填校验。
				___validator.removeElements($('#validate_ele2')[0],'required');
			} else {
				docStatus.value = "20";
			}

			var isNotExist = checkIsExist();
			if(isNotExist == "s_isnull" || isNotExist == false) {

				// 敏感词检测
				var docSubject = LUI.$('input[name="docSubject"]').val();
				// console.log("docSubject=>", docSubject)
				if(isSensitiveCheck && checkIsHasSenWords(docSubject, "<bean:message  bundle='kms-multidoc' key='kmsMultidoc.kmsMultidocKnowledge.docSubject'/>")==true) {
					return;
				}
				var fdDescription = LUI.$('textarea[name="fdDescription"]').val();
				console.log("fdDescription=>", fdDescription)
				if(isSensitiveCheck && checkIsHasSenWords(fdDescription, "<bean:message  bundle='kms-multidoc' key='kmsMultidocKnowledge.fdDescription'/>")==true) {
					return;
				}
				var docContent = RTF_GetContent("docContent");
				// console.log("docContent=>", docContent)
				if(isSensitiveCheck && checkIsHasSenWords(docContent, "<bean:message  bundle='kms-multidoc' key='kmsMultidoc.kmsMultidocKnowledge.docContent'/>")==true) {
					return;
				}

				Com_Submit(formObj, commitType, commitType=='save'?'fdId':null);
			}
		})
	}

	//检查知识是否存在
	function checkIsExist() {
		var flag = false;
		var docSubject = LUI.$('input[name="docSubject"]').val();
		var docCategoryId = LUI.$('input[name="docCategoryId"]').val();
		//知识标题为空不验证是否存在
		if(docSubject == "" || docSubject == null) 
			return "s_isnull";
		var url = "<c:url  value='/kms/multidoc/kms_multidoc_knowledge/kmsMultidocKnowledge.do?method=checkAddSubject'/>";
		var data = {
				"fdId" : "${kmsMultidocKnowledgeForm.fdId}",
				"docSubject" : docSubject,
				"cateId":docCategoryId
		};
		LUI.$.ajax({ 
			url:url,
			data: data,
			type : "POST",
			cache : false,
			dataType : "json",
			async:false,
			success : function(data) {
				flag = addCallBack(data);
			},
			error : function(error) {
				flag = false;
			}
		});

		return flag;
	}
	
	//新建回调函数
	function  addCallBack(data){
		if(data['fdIsExist'] == false){
			return false;
		}
		var fdId = data['existId'];
		seajs.use(['lui/dialog', 'lui/jquery'],function(dialog, $) {
			//已存在已发布的词条
			var _confirm_mess = "${lfn:message('kms-multidoc:kmsMultidoc.TitleRepeat')}" + "<br/>" + "${lfn:message('kms-multidoc:kmsMultidoc.TitleRepeat2')}";
	 		dialog.confirm(_confirm_mess, function(flag) {
 	 	 		if(flag) {
 	 	 			window.open('${LUI_ContextPath}/kms/multidoc/kms_multidoc_knowledge/kmsMultidocKnowledge.do?method=view&fdId='+ fdId,'_blank');
				}},
				null,
		        [{
					name : "${lfn:message('button.ok')}",
					value : true,
					focus : true,
					fn : function(value, dialog) {
						dialog.hide(value);
					}
				}, {
					name : "${lfn:message('button.close')}",
					value : false,
					fn : function(value, dialog) {
						dialog.hide(value);
					}
				}]
			);
		});
		return true;
	}
	
	
	
	//新建页面以第一个附件名为文档名
	attachmentObject_attachment.on("uploadSuccess",getFileName);
	attachmentObject_attachment.on("editDelete",getFileName);
	function getFileName(){
		var length = attachmentObject_attachment.fileList.length;
		for(var i = 0;i<length;i++){
			var fileName = attachmentObject_attachment.fileList[i].fileName;
			var fileBaseName = fileName.substring(0,fileName.lastIndexOf('.'));
			var _contain = contains(attachmentObject_attachment.fileList,LUI.$("[name='docSubject']")[0].value);
			if(LUI.$("[name='docSubject']")[0].value!=""&&!_contain){
				break;
			}else if(attachmentObject_attachment.fileList[i].fileStatus>-1){
				LUI.$("[name='docSubject']")[0].value=fileBaseName;
				break;
			}
		}
		try{if(typeof(eval(getDataByFileName))=='function') getDataByFileName();}catch(e){}
	}

	function contains(fileList,elem){
		for(var i = 0;i<fileList.length;i++){
			var fileName = fileList[i].fileName;
			var fileBaseName = fileName.substring(0,fileName.lastIndexOf('.'));
			if(fileBaseName == elem){
				return true;
			}
		}
		return false;
	}

	/**
	*将部门和岗位修改为作者的部门和岗位
	*/
	function changeAuthodInfo(value) {
		if(value) {//内部作者
			orderAuthor(value[0]);
			/* var authors=value[0].split(";");
			var authorId = authors[0];
			LUI.$.ajax({
				url : "<c:out value='${LUI_ContextPath}/kms/knowledge/kms_knowledge_base_doc/kmsKnowledgeBaseDoc.do?method=loadAuthodInfo'/>",
				type : 'post',
				dataType :'json',
				data: {fdId: authorId},
				success : function(data) {
					if (data) {
							LUI.$('input[name=docDeptId]').val(data.depId);
							LUI.$('input[name=docDeptName]').val(data.depName);
							LUI.$('input[name=docPostsIds]').val(data.postsIds);
							LUI.$('input[name=docPostsNames]').val(data.postsNames);
							
						  var obj = {	
									id:data.depId,
									img:"",
									info:"",
									isAvailable:"true",
									name:data.depName,
									orgType:"",
									parentName:"",
									length:0
								};
						  var obj1 = {	
									id:data.postsIds,
									img:"",
									info:"",
									isAvailable:"true",
									name:data.postsNames,
									orgType:"",
									parentName:"",
									length:0
								};
							resetNewAddress('docDeptId','docDeptName',';','ORG_TYPE_ORGORDEPT',false,[obj]);//重置部门
							resetNewAddress('docPostsIds','docPostsNames',';','ORG_TYPE_POST',false,[obj1]);//重置岗位
							
					} 
				},
				error : function(error) {
				}
			}); */
		} else {//外部作者
			LUI.$('input[name=docDeptId]').val('${kmsMultidocKnowledgeForm.docDeptId}');
			LUI.$('input[name=docDeptName]').val(document.getElementsByName("docDeptName")[0].value);
			LUI.$('input[name=docPostsIds]').val('${kmsMultidocKnowledgeForm.docPostsIds}');
			LUI.$('input[name=docPostsNames]').val('${kmsMultidocKnowledgeForm.docPostsNames}');
		}
		
	}
	
	function orderAuthor(authorId){
		var authors=authorId.split(";");
		if(authors.length>15){
			seajs.use(['lui/dialog', 'lui/jquery'],function(dialog, $) {
				dialog.confirm("${lfn:message('kms-multidoc:kmsMultidoc.selectAuthor.DataVolume')}", 
						function(flag) {
						},
						null,
						[{
							name : "关闭",
							value : false,
							fn : function(value, dialog) {
								
							   var docAuthorIds='${kmsMultidocKnowledgeForm.docAuthorId}';
							   var docAuthorNames='${kmsMultidocKnowledgeForm.docAuthorName}';
							   var html=$("#authorsArrary").html();
							   
							   var ids=docAuthorIds.split(";");
							   var names=docAuthorNames.split(";");
							   var values = [];
							   for(var i=0;i<ids.length;i++){
									var obj = {
										id:ids[i],
										name:names[i]
									};
								 values.push(obj);
							   }
								var address = Address_GetAddressObj("docAuthorName");
								address.emptyAddress(true);
								address.reset(";","ORG_TYPE_PERSON",true,values);
								$("#authorsArrary").html(html);
								dialog.hide(value);
							}
						}]
			    );
		    });
			
		}else{
			var htmlVL="";
			for(var i=0;i<authors.length;i++){
				htmlVL+="<tr><td><input type='hidden' name='fdDocAuthorList["+i+"].fdOrgId' value='"+authors[i]+"'><input type='hidden' name='fdDocAuthorList["+i+"].fdAuthorFag' value='"+i+"'></td></tr>";
			}
			$("#authorsArrary").html(htmlVL);
		}
		
		
	}
	
	//无分类情况下进入新建页面，自动弹框选择类别
	<c:if test="${kmsMultidocKnowledgeForm.docCategoryId eq null}">
		if('${param.fdTemplateId}'==''){
			window.modifyCate(false);
		}
	</c:if>

	var validations = {
			'checkDocEffectiveTime': {
				error: "<bean:message bundle='kms-multidoc' key='kmsMultidoc.compareDocEffectiveTime'/>",
				test: function(v,e,o) {
					//输入的时间
					var dateVal = $.trim(v);
					if(dateVal == "") {
						return true;
					}
					//当前时间
					var nowDate = new Date();
					var nowDateStr = nowDate.getFullYear() + "-" + (nowDate.getMonth() + 1) + "-" + nowDate.getDate()+" "+nowDate.getHours()+":"+nowDate.getMinutes()+":"+nowDate.getSeconds();
					nowDateStr = nowDateStr.replace(/\-/gi,"/");
					dateVal = dateVal.replace(/\-/gi,"/");
					//当前时间
					var time1 = new Date(nowDateStr).getTime();
					//输入的时间
					var time2 = new Date(dateVal).getTime();
					if(time2 <= time1) {
						return false;
					}
					return true;
				}
			},
			'checkDocFailureTime': {
				error: "<bean:message bundle='kms-multidoc' key='kmsMultidoc.compareDocFailureTime'/>",
				test: function(v,e,o) {
					//输入的时间
					var dateVal = $.trim(v);
					if(dateVal == "") {
						return true;
					}
					//开始时间
					if(document.getElementsByName("docEffectiveTime")[0] == null){
						return true;
					}
					var nowDate = document.getElementsByName("docEffectiveTime")[0].value;
					if(dateVal <= nowDate) {
						return false;
					}
					return true;
				}
			},
			'checkDocExpireTime': {
				error: "<bean:message bundle='kms-multidoc' key='kmsMultidoc.compareDocExpireTime'/>",
				test: function(v,e,o) {
					//输入的时间
					var dateVal = $.trim(v);
					if(dateVal == "") {
						return true;
					}
					//当前时间
					var nowDate = new Date();
					var nowDateStr = nowDate.getFullYear() + "-" + (nowDate.getMonth() + 1) + "-" + nowDate.getDate()+" "+nowDate.getHours()+":"+nowDate.getMinutes()+":"+nowDate.getSeconds();
					nowDateStr = nowDateStr.replace(/\-/gi,"/");
					dateVal = dateVal.replace(/\-/gi,"/");
					//当前时间
					var time1 = new Date(nowDateStr).getTime();
					//输入的时间
					var time2 = new Date(dateVal).getTime();
					if(time2 <= time1) {
						return false;
					}
					return true;
				}
			}
		};
/**
 * 新增的上一步下一步的操作
 */
 function clickSysUiStep(stepFlag){
	 $("input[data-lui-mark='step." + stepFlag + "']").click();
}
</script>

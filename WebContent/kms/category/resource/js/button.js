define(function(require, exports, module) {

	var $ = require("lui/jquery");
	var env = require('lui/util/env');
	var strutil = require('lui/util/str');
	var topic = require('lui/topic');
	var Spa = require('lui/spa/Spa');
	var dialog = require('lui/dialog');
	var create = require('kms/category/resource/js/create');

	var knowledge_lang = require('lang!kms-knowledge');
	var category_lang = require('lang!kms-category');
	var lang = require('lang!');
	var bookmark_lang = require('lang!sys-bookmark');
	var eval_lang = require('lang!sys-evaluation');

	var ui_lang = require('lang!sys-ui');

	var Module = require('lui/framework/module');

	Module.install('kmsKnowledge', {
		// 模块变量
		$var : {},
		// 模块多语言
		$lang : {},
		// 搜索标识符
		$search : ''
	});

	var knowledgeTypes = require('kms/knowledge/kms_knowledge_ui/js/knowledgeType.jsp#');
	var recycleknowledgeTypes = require('kms/knowledge/kms_knowledge_ui/js/recycleKnowledgeTypes.jsp#');
	
	var isIntroInit = false;
	// 是否精华
	$('input[name="introduce"]').on('click', function(evt) {

		var value = '';

		if (this.checked) {
			value = '1';
		}
		if (!isIntroInit)
			isIntroInit = true;
		Spa.spa.setValue('docIsIntroduced', value);
	});

	topic.subscribe('spa.change.values', function(evt) {

		if (isIntroInit)
			return;

		if (evt.value && evt.value.docIsIntroduced == '1') {
			$('input[name="introduce"]').attr('checked', true);
			isIntroInit = true;
		}
	});

	// 导出按钮相关操作
	function exportToExcel() {

		var dataview = LUI("listview");
		var fdUrl = dataview.source.url;
		var fdExportExcelUrl = env.fn.formatUrl(fdUrl.replace(
				/method=listChildren|method=list/, "method=exportExcel"));
		if (List_CheckSelect()) {
			var values = [];
			var selected;
			var obj = document.getElementsByName("List_Selected");
			for (var i = 0; i < obj.length; i++) {
				if (obj[i].checked) {
					values.push(obj[i].value);
					selected = true;
				}
			}

			// 没有选择数据则给出提示
			if (!selected) {
				dialog.alert(knowledge_lang['kmsKnowledge.page.noDataImport']);
				return;
			}

			// 超过500的话不让导出
			if (values.length > 500) {
				dialog.alert(knowledge_lang['kmsKnowledge.import.limit']);
				return;
			}

			if (selected) {
				dialog.confirm(knowledge_lang['kmsKnowledge.import.content'],
						function(flag, d) {

							if (flag) {

								var form = $('form[name="exportData"]');
								form.attr('action', fdExportExcelUrl);
								form.find('input[name="List_Selected"]').val(
										values);
								form[0].submit();
							}
						})
			}

		}
	}

	function List_CheckSelect() {

		var obj = document.getElementsByName("List_Selected");
		if (obj == null || obj.length == 0) {

			dialog.alert(knowledge_lang['kmsKnowledge.page.noDataImport']);
			return false;
		}
		return true;

	}

	// 导出按钮相关操作--结束

	// 置顶
	function setTop() {

		var values = [];
		var selected;
		var select = document.getElementsByName("List_Selected");
		for (var i = 0; i < select.length; i++) {
			if (select[i].checked) {
				values.push(select[i].value);
				selected = true;
			}
		}
		if (selected) {

			dialog.iframe(
					"/kms/knowledge/kms_knowledge_ui/kmsKnowledge_index_setTop.jsp?templateId="
							+ Spa.spa.getValue('docCategory') + "&docIds="
							+ values,
					knowledge_lang['kmsKnowledgeBaseDoc.setTop'], null, {
						width : 720,
						height : 370,
						buttons : [ {
							name : lang['button.ok'],
							value : true,
							focus : true,
							fn : function(value, _dialog) {

								commitForm(_dialog, values);
							}
						}, {
							name : lang['button.cancel'],
							styleClass : "lui_toolbar_btn_gray",
							value : false,
							fn : function(value, _dialog) {

								_dialog.hide();
							}
						} ]
					});
		} else {

			dialog.alert(lang['page.noSelect']);
		}
	}

	function commitForm(_dialog, values) {

		var fdSetTopReason = LUI.$('#dialog_iframe', top.document).find(
				'iframe')[0].contentDocument
				.getElementsByName('fdSetTopReason')[0].value;
		if (fdSetTopReason == "") {

			dialog.alert(knowledge_lang['kmsKnowledgeBaseDoc.setTopReason']);
			return false;
		}
		var fdSetTopLevel = $('#dialog_iframe', top.document).find('iframe')[0].contentDocument
				.getElementsByName('fdSetTopLevel');
		for (var i = 0; i < fdSetTopLevel.length; i++) {
			if (fdSetTopLevel[i].checked) {
				fdSetTopLevel = fdSetTopLevel[i].value;
				break;
			}
		}
		fdSetTopReason = encodeURIComponent(fdSetTopReason);
		$
				.ajax({
					url : env.fn
							.formatUrl('/kms/knowledge/kms_knowledge_base_doc/kmsKnowledgeBaseDocIndex.do?method=setTop&'
									+ 'docIds='
									+ values
									+ '&fdSetTopLevel='
									+ fdSetTopLevel
									+ '&fdSetTopReason='
									+ fdSetTopReason
									+ '&categoryId='
									+ Spa.spa.getValue('docCategory')),
					type : 'post',
					success : function(data) {

						if (data["hasRight"] == true) {
							var topWinHref = top.location.href;

							var loading = dialog.loading();
							_dialog.hide();
							loading.hide();
							dialog.success(ui_lang['ui.help.luiext.success'],
									'#listview');
							topic.publish('list.refresh');

						} else {
							setTopFail(_dialog);
						}
					}
				});
	}

	function setTopFail(_dialog) {

		_dialog.hide();
		dialog.alert(knowledge_lang['kmsKnowledgeBaseDoc.noRight']);
	}

	// 置顶相关操作--结束

	// 新建按钮

	function addDoc() {
		create.addDoc();
	}

	// 新建按钮--结束
	// 维基导入按钮--开始
	function wikiImport() {

		create.addWikiImport(null, "addWikiImport");
	}
	// 维基导入按钮--结束

	// 彻底删除

	function deleteall() {

		var comfirmMsg = Com_Parameter.ComfirmDelete;

		// 判断是否开启软删除

		if (knowledgeTypes && knowledgeTypes.length > 0) {
			var softDeleteNums = 0;

			for (var i = 0; i < knowledgeTypes.length; i++) {
				if (Com_Parameter.SoftDeleteEnableModules
						.indexOf(knowledgeTypes[i]) > -1) {
					softDeleteNums++;
				}
			}
			if (softDeleteNums != 0) {
				if (softDeleteNums == knowledgeTypes.length) {
					comfirmMsg = Com_Parameter.ComfirmSoftDelete;
				} else if (softDeleteNums < knowledgeTypes.length) {
					// 两个模块设置不一样
					dialog.alert("文档知识库和维基知识库的软删除设置不一致");
					return;
				}
			}
		}

		var url = '/kms/knowledge/kms_knowledge_base_doc/kmsKnowledgeBaseDoc.do?method=deleteall&categoryId='
				+ Spa.spa.getValue('docCategory');

		if (!url)
			return;
		var values = [], selected, select = document
				.getElementsByName("List_Selected");
		for (var i = 0; i < select.length; i++) {
			if (select[i].checked) {
				values.push(select[i].value);
				selected = true;
			}
		}
		if (selected) {

			var dataObj = $.extend({}, {
				"successForward" : "lui-source",
				"failureForward" : "lui-failure"
			}, {
				"List_Selected" : values
			});
			dialog.confirm(comfirmMsg, function(flag, d) {

				if (flag) {
					var loading = dialog.loading();
					$.ajax({
						url : env.fn.formatUrl(url),
						cache : false,
						data : $.param(dataObj, true),
						type : 'post',
						dataType : 'json',
						success : function(data) {

							if (data.flag) {
								loading.hide();
								if (data.errorMessage) {// 新版本锁定
									dialog.failure(data.errorMessage,
											'#listview');
								} else {// 删除成功
									dialog.success(lang['return.optSuccess'],
											'#listview');
									topic.publish('list.refresh');
								}
							} else {
								loading.hide();
							}
						},
						error : function(error) {// 删除失败

							loading.hide();
							dialog.failure(lang['return.optFailure'],
									'#listview');
						}
					});
				}
			});
		} else {

			dialog.alert(lang['page.noSelect']);
		}
	}
	// 删除--结束

	function changeExportBtn(obj) {

		setTimeout(function() {

			var btn = LUI("exportToExcel");
			if (btn) {
				btn.setVisible(obj);
			}

		}, 1)

	}

	// 属性修改

	function editProperty() {

		var docIds = findSelectId();

		if (docIds) {

			dialog
					.iframe(
							"/kms/knowledge/kms_knowledge_base_doc/kmsKnowledgeBaseDoc.do?method=editProperty&templateId="
									+ Spa.spa.getValue('docCategory')
									+ "&fdId=" + docIds,
							knowledge_lang['kmsKnowledge.button.editProperty'],
							null,
							{
								width : 750,
								height : 230,
								buttons : [
										{
											name : lang['button.ok'],
											value : true,
											focus : true,
											fn : function(value, _dialog) {

												// 获取弹出窗口的window对象
												var winObj = $(
														'#dialog_iframe',
														top.document).find(
														'iframe')[0].contentWindow;
												// 验证
												if (!editProValidate(winObj)) {
													return;
												}
												var loading = dialog.loading();
												// 获取弹出窗口的document对象里面的form
												var proObj = $(
														'#dialog_iframe',
														top.document).find(
														'iframe')[0].contentDocument
														.getElementsByName('kmsKnowledgeBaseDocForm')[0];
												var eFlag = editProSubmit(
														proObj, winObj);
												if (eFlag != null
														&& eFlag == 'yes') {
													_dialog.hide();
													loading.hide();
													dialog
															.success(knowledge_lang['kmsKnowledge.editPropertySuccess']);
												} else {
													_dialog.hide();
													loading.hide();
													dialog
															.success(knowledge_lang['kmsKnowledge.editPropertyFailure']);
												}
											}
										},
										{
											name : lang['button.cancel'],
											value : false,
											styleClass : 'lui_toolbar_btn_gray',
											fn : function(value, _dialog) {

												_dialog.hide();
											}
										} ]
							});
		}
	}

	// 调整属性验证
	function editProValidate(_winObj) {

		// 验证必填项
		if (_winObj != null && !_winObj.Com_Parameter.event["submit"][0]()) {
			return false;
		} else
			return true;
	}

	// 调整属性异步处理
	function editProSubmit(_obj) {

		var editFlag;
		$
				.ajax({
					url : env.fn
							.formatUrl('/kms/knowledge/kms_knowledge_base_doc/kmsKnowledgeBaseDoc.do?method=updateProperty&templateId='
									+ Spa.spa.getValue('docCategory')),
					type : 'POST',
					dataType : 'json',
					async : false,
					data : LUI.$(_obj).serialize(),
					success : function(data, textStatus, xhr) {

						if (data && data['flag'] === true) {
							// 调整成功
							editFlag = 'yes';
						}
					},
					error : function(xhr, textStatus, errorThrown) {

						// 调整失败
						editFlag = 'no';
					}
				});
		if (editFlag != null)
			return editFlag;
	}

	function findSelectId() {

		var values = [];
		var selected, template;
		var select = $("input[type='checkbox'][data-lui-mark='table.content.checkbox'][name='List_Selected']");
		for (var i = 0; i < select.length; i++) {
			if (select[i].checked) {
				values.push(select[i].value);
				selected = true;
				if (template
						&& template != LUI('listview').table.kvData[i].docCategoryId) {

					dialog.alert(knowledge_lang['kmsKnowledge.chooseSameCate.categoryTrue']);
					return null;
				} 
				if (!template) {
					template = LUI('listview').table.kvData[i].docCategoryId;
				}
			}
		}
		if (selected) {
			return values;
		} else {
			dialog.alert(lang['page.noSelect']);
		}

	}

	window.downloadAttAndLog = function(fdId) {

		var downloadUrl = Com_Parameter.ContextPath
				+ "sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId="
				+ fdId + "&downloadType=manual&downloadFlag="
				+ (new Date()).getTime();
		Com_OpenWindow(downloadUrl, "_blank");
	}

	// 属性修改--结束

	var module = Module.find('kmsKnowledge');

	module
			.controller(function($var, $lang, $function, $router) {

				// 修改属性
				$function.editProperty = function() {

					editProperty();
				}

				// 更改导出按钮显示状态
				$function.changeExportBtn = function() {

					changeExportBtn();
				}

				// 删除
				$function.deleteall = function() {

					deleteall();
				}

				// 维基库导入
				$function.wikiImport = function() {

					wikiImport();
				}

				// 新建文档
				$function.addDoc = function() {
					addDoc();

				}

				// 置顶
				$function.setTop = function() {

					setTop();
				}

				// 导出为excel
				$function.exportToExcel = function() {

					exportToExcel();
				}
				
				
				$router
						.define({
							startpath : '/all',

							routes : [
									{
										path : '/approval',// 待审
										action : {

											type : 'content',
											options : {
												cri : {
													mydoc : "approval,"
															+ lang['list.approval']
												}
											}

										}
									},
									{
										path : '/all',// 所有
										action : {
											type : 'content',
											options : {
												cri : {}
											}
										}
									},
									{
										path : '/approved',// 已审
										action : {

											type : 'content',
											options : {
												cri : {
													mydoc : "approved,"
															+ lang['list.approved']
												}
											}
										}
									},
									{
										path : '/draftBox',// 草稿箱
										action : function(){
											
											var options={
													cri : {
														docStatus : "10,"
															+ knowledge_lang['kmsKnowledgeBaseDoc.my.draftBox'],
														myDraft : "myDraft,",
														"cri.q" : "template:1"
														
													}
												};
											
											var self = this;
											if(!$.isPlainObject(options)){
												return;
											}
											LUI.pageHide("_rIframe");
											var cri = $.extend({},options.cri, options.$paramsCri);
											if(options.$isInit){
												return;
											}
											topic.publish('spa.change.reset', {
												value : cri,
												target : self
											});
										}

									},
									{
										path : '/readInfo',
										action : function(d) {
											LUI.pageOpen($var.$contextPath
																	+ "/kms/category/readLog/readLog_list.jsp",
															'_rIframe');
										}

									},
									
									{
										path : '/create',// 我的知识
										action :function(){
											var options={
													cri : {
														"mydoc" : "create,"
																+ category_lang['kmsCategory.index.myKnowlege'],
														"cri.q" : "template:1"
														
													}
												};
											
											var self = this;
											if(!$.isPlainObject(options)){
												return;
											}
											LUI.pageHide("_rIframe");
											var cri = $.extend({},options.cri, options.$paramsCri);
											if(options.$isInit){
												return;
											}
											topic.publish('spa.change.reset', {
												value : cri,
												target : self
											});
										}

									},

									{
										path : '/bookmark',// 收藏知识
										action : function(){
											var options={
													cri : {
														"myBookmark" : "myBookmark,"
															+ category_lang['kmsCategory.index.collectionKnowledge'],
														"cri.q" : "template:1"
														
													}
												};
											
											var self = this;
											if(!$.isPlainObject(options)){
												return;
											}
											LUI.pageHide("_rIframe");
											var cri = $.extend({},options.cri, options.$paramsCri);
											if(options.$isInit){
												return;
											}
											topic.publish('spa.change.reset', {
												value : cri,
												target : self
											});
											
										}

									},
									{
										path : '/multidoc',// 文档
										action : function(){
											var template=1;
											var options={
													cri : {
														type : "multidoc,"
															+ category_lang['kmsCategory.index.document'],
														"cri.q" : "template:"+template
														
													}
												};
											
											var self = this;
											if(!$.isPlainObject(options)){
												return;
											}
											LUI.pageHide("_rIframe");
											var cri = $.extend({},options.cri, options.$paramsCri);
											if(options.$isInit){
												return;
											}
											topic.publish('spa.change.reset', {
												value : cri,
												target : self
											});
											$(".criterion-expand").find('[data-criterion-key="template"]').remove();
										}

									},
									{
										path : '/wiki',//维基
										action : function(){
											var template=2;
											var options={
													cri : {
														type : "wiki,"
															+ category_lang['kmsCategory.index.wiki'],
														"cri.q" : "template:"+template
														
													}
												};
											
											var self = this;
											if(!$.isPlainObject(options)){
												return;
											}
											LUI.pageHide("_rIframe");
											var cri = $.extend({},options.cri, options.$paramsCri);
											if(options.$isInit){
												return;
											}
											topic.publish('spa.change.reset', {
												value : cri,
												target : self
											});
											$(".criterion-expand").find('[data-criterion-key="template"]').remove();
										}

									},
									{
										path : '/kem',// 原子
										action : function(){
											var template=3;
											var options={
													cri : {
														type : "kem,"
															+ category_lang['kmsCategory.index.kem'],
														kem:'kem',
														"cri.q" : "template:"+template
														
													}
												};
											
											var self = this;
											if(!$.isPlainObject(options)){
												return;
											}
											LUI.pageHide("_rIframe");
											var cri = $.extend({},options.cri, options.$paramsCri);
											if(options.$isInit){
												return;
											}
											topic.publish('spa.change.reset', {
												value : cri,
												target : self
											});
											$(".criterion-expand").find('[data-criterion-key="template"]').remove();
										}

									},{
										path : '/management',
										action : {
											type : 'pageopen',
											options : {
												url:$var.$contextPath+'/sys/profile/moduleindex.jsp?nav=/kms/category/tree.jsp',
												target:'_rIframe'
											}
										}
									}]
						})
			})

});
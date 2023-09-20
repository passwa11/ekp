define(function(require, exports, module) {

	var $ = require("lui/jquery");
	var env = require('lui/util/env');
	var strutil = require('lui/util/str');
	var topic = require('lui/topic');
	var Spa = require('lui/spa/Spa');
	var dialog = require('lui/dialog');
	var create = require('kms/knowledge/kms_knowledge_ui/js/create');
	var criteria = require('lui/criteria/base');
	var SpaConst = require('lui/spa/const');

	var knowledge_lang = require('lang!kms-knowledge');
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
			isIntroInit = true;
		}else{
			isIntroInit = false;
		}
		//if (!isIntroInit)
		//	isIntroInit = true;
		Spa.spa.setValue('docIsIntroduced', value);
	});


	var docIsBorrow = false;
	// 是否精华
	$('input[name="docIsBorrow"]').on('click', function(evt) {

		var value = '';

		if (this.checked) {
			value = '1';
			docIsBorrow = true;
		}else{
			docIsBorrow = false;
		}

		Spa.spa.setValue('docIsBorrow', value);
	});

	topic.subscribe('spa.change.values', function(evt) {
		if (isIntroInit){
			var isDocIsIntroduced= Spa.spa.getValue('docIsIntroduced');
			if(isDocIsIntroduced=='1')
				return;
			else
				Spa.spa.setValue('docIsIntroduced', '1');
		}

		if (evt.value && evt.value.docIsIntroduced == '1') {
			$('input[name="introduce"]').attr('checked', true);
			isIntroInit = true;
		}


		if (docIsBorrow){
			var isDocIsBorrow= Spa.spa.getValue('docIsBorrow');
			if(isDocIsBorrow=='1')
				return;
			else
				Spa.spa.setValue('docIsBorrow', '1');
		}

		if (evt.value && evt.value.docIsBorrow == '1') {
			$('input[name="docIsBorrow"]').attr('checked', true);
			docIsBorrow = true;
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
		var top = Com_Parameter.top || window.top;
		var fdSetTopReason = LUI.$('#dialog_iframe', top.document).find(
				'iframe')[0].contentDocument
				.getElementsByName('fdSetTopReason')[0].value;
		if (fdSetTopReason == "") {

			dialog.alert(knowledge_lang['kmsKnowledgeBaseDoc.setTopReason']);
			return false;
		}
		// 新增置顶字符长度校验
		var fdSetTopReasonLength = fdSetTopReason.length;
		if (fdSetTopReasonLength > 600) {
			dialog.alert(knowledge_lang['kmsKnowledgeBaseDoc.setTopReason.length.limit']);
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
						data=$.parseJSON(data);
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

	function addDoc(type,kmsCategoryEnabled) {
        var docCategory = null;
        if(Spa.spa){
            docCategory = Spa.spa.getValue('docCategory');
        }
		create.addDoc(docCategory, type,kmsCategoryEnabled);
	}

	// 新建按钮--结束
	// 维基导入按钮--开始
	function wikiImport() {

		create.addWikiImport(Spa.spa.getValue('docCategory'), "addWikiImport");
	}
	// 维基导入按钮--结束


	function deleteBookMarks(fdId){
		var values = [];
		var url = env.fn
			.formatUrl('/kms/knowledge/kms_knowledge_base_doc/kmsKnowledgeBaseDoc.do?method=deleteBookMarks');

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
			var config = {
				comfirmMsg: "确定取消收藏吗？",
				url : url,
				data : $.param({
					"List_Selected" : values
				}, true)
			};

			Com_Delete(config, function() {
				topic.publish('list.refresh');
			});
		} else {
			dialog.alert(lang['page.noSelect']);
		}
	}

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

        // #133363 草稿需要删除权限
        if(window.location.href.indexOf("myDraft") > -1){
            url = '/kms/knowledge/kms_knowledge_base_doc/kmsKnowledgeBaseDoc.do?method=deleteall&status=10&categoryId='
                + Spa.spa.getValue('docCategory');
        }

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

    function docBorrow(fdId) {
        var values = [], selected, select = document
            .getElementsByName("List_Selected");


		if(fdId) {
			values.push(fdId);
		} else {
			for (var i = 0; i < select.length; i++) {
				if (select[i].checked) {
					values.push(select[i].value);
					selected = true;
				}
			}
		}

        if ((fdId && values.length>0) || (selected && values.length>0)) {
            // 单选校验
            if(values.length>1){
                dialog.alert(knowledge_lang['kms.knowledge.borrow.single.tip']);
                return;
            }
            var fdDocId = values[0];
            // 判断是否选择了无需订阅的文档
			$.ajax({
				url : env.fn.formatUrl('/kms/knowledge/kms_knowledge_base_doc/kmsKnowledgeBaseDoc.do?method=checkDocPromission'),
				type : 'POST',
				dataType : 'json',
				async : false,
				data : {
					fdId: fdDocId
				},
				success : function(data, textStatus, xhr) {
					if (data && data['flag'] == true) {
						// 提示无需借阅
						if(data['docStatus'] == "30"){
			                dialog.alert(knowledge_lang['kms.knowledge.borrow.noneed.tip']);
						}else{
			                dialog.alert(knowledge_lang['kms.knowledge.borrow.noneed.otherStatus.tip']);
						}
						return;
					}
					
		            // 打开借阅页面
		            Com_OpenWindow(
		    	      env.fn.formatUrl(
		    	        '/kms/knowledge/kms_knowledge_borrow/kmsKnowledgeBorrow.do?method=add&fdDocId='+fdDocId
		    	      )
		            );
				},
				error : function(xhr, textStatus, errorThrown) {
					dialog.failure(lang['return.optFailure'],'#listview');
				}
			});
        }else{
            dialog.alert(lang['page.noSelect']);
        }
    }

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
												var top = Com_Parameter.top || window.top;
												// 获取弹出窗口的window对象
												var winObj = $('#dialog_iframe', top.document).find('iframe')[0].contentWindow;
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

					dialog.alert(knowledge_lang['kmsKnowledge.chooseSameCate']);
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

	function getPanelTitle(val) {
		var title = '<span class="lui_panel_title_main lui_tabpanel_navs_item_title">'+val+'</span>';
		return title;
	}

	var module = Module.find('kmsKnowledge');

	module.controller(function($var, $lang, $function, $router) {

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

				// 删除收藏
				$function.deleteBookMarks = function(fdId) {

					deleteBookMarks(fdId);
				}

				// 维基库导入
				$function.wikiImport = function() {

					wikiImport();
				}

				// 新建文档
				$function.addDoc = function(type,kmsCategoryEnabled) {
					// 文档类型
					if (!type) {
					    if(Spa.spa){
                            type = Spa.spa.getCriteriaValue('template');
                        }
					}

					if ('1' == type) {// 文档库
						type = '1,3';
					} else if ('2' == type) {// 维基库
						type = '2,3';
					}

					if (!type) {
						addDoc();
						return;
					}

					addDoc(type,kmsCategoryEnabled);

				}

				// 置顶
				$function.setTop = function() {

					setTop();
				}

				// 导出为excel
				$function.exportToExcel = function() {

					exportToExcel();
				}

				$function.docBorrow = function (value) {
					docBorrow(value);
				}

				var panelId = 'kms_index_panel';
				var contentId = "kms_index_panel_content";

				//作用于知识仓库UI优化页面
				if(location.pathname && location.pathname.indexOf("kms/knowledge") > -1){
					$router.define({
						startpath : '/index',
						routes : [
							{
								path : '/approval',// 待审
								action : {
									type : 'tabpanel',
									options : {
										panelId: panelId,
										contents: {
											"kms_index_panel_content" : {
												title: getPanelTitle('我的审批'),
												route: {
													path: '/approval'
												},
												cri: {
													'cri.q': 'mydoc:approval'
												},
												selected: true
											}
										},
									}
								}
							},
							{
								path : '/all',// 所有
								action : {
									type : 'tabpanel',
									options : {
										panelId: panelId,
										contents: {
											"kms_index_panel_content" : {
												title: getPanelTitle('全部知识'),
												route: {
													path: '/all'
												},
												cri: {},
												selected: true
											}
										},
									}
								}
							},
							{
								path : '/hot',// 最热
								action : {
									type : 'tabpanel',
									options : {
										panelId: panelId,
										contents: {
											"kms_index_panel_content" : {
												title: getPanelTitle('最热知识'),
												route: {
													path: '/hot'
												},
												cri: {},
												selected: true
											}
										},
									}
								}
							},
							{
								path : '/approved',// 已审
								action : {
									type : 'tabpanel',
									options : {
										panelId: panelId,
										contents: {
											"kms_index_panel_content" : {
												title: getPanelTitle('我的待审'),
												route: {
													path: '/approved'
												},
												cri: {
													'cri.q': 'mydoc:approved'
												},
												selected: true
											}
										},

									}
								}
							},
							{
								path : '/draftBox',// 草稿箱
								action : {
									type : 'tabpanel',
									options : {
										panelId: panelId,
										contents: {
											"kms_index_panel_content" : {
												title: getPanelTitle('我的草稿'),
												route: {
													path: '/create'
												},
												cri: {
													'cri.q': 'mydoc:create;docStatus:10'
												},
												selected: true,

											}
										},

									}
								}


							},
							{
								path : '/recover',
								action : function(d) {
									LUI.pageOpen($var.$contextPath
										+ "/sys/recycle/import/sysRecycle_index.jsp?modelName="
										+ recycleknowledgeTypes
											.join(";"),
										'_rIframe');
								}

							},
							{
								path : '/recoverwiki',
								action : function(d) {
									LUI.pageOpen($var.$contextPath
										+ "/sys/recycle/import/sysRecycle_index.jsp?modelName=com.landray.kmss.kms.wiki.model.KmsWikiMain",
										'_rIframe');
								}

							},
							{
								path : '/recovermultidoc',
								action : function(d) {
									LUI.pageOpen($var.$contextPath
										+ "/sys/recycle/import/sysRecycle_index.jsp?modelName=com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge",
										'_rIframe');
								}

							},
							{
								path : '/create',// 我的上传
								action : {
									type : 'tabpanel',
									options : {
										panelId: panelId,
										contents: {
											"kms_index_panel_content" : {
												title: getPanelTitle('我的上传'),
												route: {
													path: '/create'
												},
												cri: {
													'cri.q': 'mydoc:create'
												},
												selected: true
											}
										},

									}
								}
							},
							{
								path : '/bookmark',// 我的收藏
								action : {
									type : 'tabpanel',
									options : {
										panelId: panelId,
										contents: {
											"kms_index_panel_content" : {
												title: getPanelTitle('我的收藏'),
												route: {
													path: '/bookmark'
												},
												cri: {
													'cri.q': 'mydoc:myBookmark'
												},
												selected: true,

											}
										},

									}
								}
							},
							{
								path : '/eval',// 我的点评
								action : {
									type : 'tabpanel',
									options : {
										panelId: panelId,
										contents: {
											"kms_index_panel_content" : {
												title: getPanelTitle('我的点评'),
												route: {
													path: '/eval'
												},
												cri: {
													'cri.q': 'mydoc:myEval'
												},
												selected: true
											}
										},

									}
								}
							},
							{
								path : '/readInfo',
								action : function(d) {
									LUI.pageOpen($var.$contextPath
										+ "/kms/knowledge/readLog/readLog_list.jsp",
										'_rIframe');
									topic.publish('spa.change.reset');
								}

							},
							{
								path : '/index',
								action : function(d) {
									if(window.location.href.indexOf("nav")>-1 ||
										window.location.href.indexOf("kmsKnowledge_person.jsp")>-1 ||
										window.location.href.indexOf("manage")>-1 ||
										window.location.href.indexOf("portlet")>-1 ||
										window.location.href.indexOf("docCategory")>-1 ||
										(window.location.href.indexOf("toggleView")>-1 &&
											!(window.location.href.indexOf("index")>-1)
										) ||
										(window.location.href.indexOf("multidoc")>-1 &&
											!(window.location.href.indexOf("index")>-1)
										) ||
										(window.location.href.indexOf("wiki")>-1 &&
											!(window.location.href.indexOf("index")>-1)
										)
									){
										return;
									}
									if(window.pageOpenNew){
										window.pageOpenNew($var.$contextPath
											+ "/kms/knowledge/kms_knowledge_index_ui/kmsKnowledge_index_list.jsp",
											'_rIframe');
									}else{
										LUI.pageOpen($var.$contextPath
											+ "/kms/knowledge/kms_knowledge_index_ui/kmsKnowledge_index_list.jsp",
											'_rIframe');
									}
									// 极速模式下，主页链接不能定位到"首页"的内容，这里可能有异步加载问题
									// /ekp/sys/portal/page.jsp?j_module=true#j_start=%2Fkms%2Fknowledge%2Findex.jsp&j_target=_iframe
									if ("done" != window.kms_knowledge_index_routes_times_reset_do){
										window.kms_knowledge_index_routes_times_reset_do = "done";
										for(var t = Date.now();Date.now() - t <= 500;);
									}
										
									topic.publish('spa.change.reset');
								}

							},
							{
								path : '/myBorrow',
								action : function(d) {
									LUI.pageOpen($var.$contextPath+ "/kms/knowledge/kms_knowledge_borrow/kmsKnowledgeBorrow_myBorrow.jsp",
										'_rIframe');
									topic.publish('spa.change.reset');
								}

							},
							{
								path : '/multidocReadInfo',
								action : function(d) {
									LUI.pageOpen($var.$contextPath
										+ "/kms/knowledge/readLog/readLog_multidocList.jsp",
										'_rIframe');
									topic.publish('spa.change.reset');
								}

							},
							{
								path : '/wikiReadInfo',
								action : function(d) {
									LUI.pageOpen($var.$contextPath
										+ "/kms/knowledge/readLog/readLog_wikiList.jsp",
										'_rIframe');
									topic.publish('spa.change.reset');
								}

							},{
								path : '/management',
								action : {
									type : 'pageopen',
									options : {
										url:$var.$contextPath+'/sys/profile/moduleindex.jsp?nav=/kms/knowledge/tree.jsp',
										target:'_rIframe'
									}
								}
							}
						]
					});
				}else{ //作用于文档知识库和维基知识库页面
					$router.define({
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
									action : {
										type : 'content',
										options : {
											cri : {
												docStatus : "10,"
													+ knowledge_lang['kmsKnowledgeBaseDoc.my.draftBox'],
												myDraft : "myDraft,"
											}
										}
									}

								},
								{
									path : '/recover',
									action : function(d) {
										LUI.pageOpen($var.$contextPath
											+ "/sys/recycle/import/sysRecycle_index.jsp?modelName="
											+ recycleknowledgeTypes
												.join(";"),
											'_rIframe');
									}

								},
								{
									path : '/recoverwiki',
									action : function(d) {
										LUI.pageOpen($var.$contextPath
											+ "/sys/recycle/import/sysRecycle_index.jsp?modelName=com.landray.kmss.kms.wiki.model.KmsWikiMain",
											'_rIframe');
									}

								},
								{
									path : '/recovermultidoc',
									action : function(d) {
										LUI.pageOpen($var.$contextPath
											+ "/sys/recycle/import/sysRecycle_index.jsp?modelName=com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge",
											'_rIframe');
									}

								},
								{
									path : '/create',// 我的上传
									action : {
										type : 'content',
										options : {
											cri : {

												mydoc : "create,"
													+ lang['list.create']
											}
										}
									}

								},

								{
									path : '/bookmark',// 我的收藏
									action : {
										type : 'content',
										options : {
											cri : {

												type : "myBookmark,"
													+ bookmark_lang['header.msg.myfavorite']
											}
										}
									}

								},
								{
									path : '/eval',// 我的点评
									action : {
										type : 'content',
										options : {
											cri : {

												type : "myEval,"
													+ eval_lang['sysEvaluationMain.zone.my']

											}
										}
									}

								},
								{
									path : '/readInfo',
									action : function(d) {
										LUI.pageOpen($var.$contextPath
											+ "/kms/knowledge/readLog/readLog_list.jsp",
											'_rIframe');
										topic.publish('spa.change.reset');
									}

								},
								{
									path : '/myBorrow',
									action : function(d) {
										LUI.pageOpen($var.$contextPath+ "/kms/knowledge/kms_knowledge_borrow/myBorrow_list.jsp",
											'_rIframe');
										topic.publish('spa.change.reset');
									}

								},
								{
									path : '/multidocReadInfo',
									action : function(d) {
										LUI.pageOpen($var.$contextPath
											+ "/kms/knowledge/readLog/readLog_multidocList.jsp",
											'_rIframe');
										topic.publish('spa.change.reset');
									}

								},
								{
									path : '/wikiReadInfo',
									action : function(d) {
										LUI.pageOpen($var.$contextPath
											+ "/kms/knowledge/readLog/readLog_wikiList.jsp",
											'_rIframe');
										topic.publish('spa.change.reset');
									}

								},{
									path : '/management',
									action : {
										type : 'pageopen',
										options : {
											url:$var.$contextPath+'/sys/profile/moduleindex.jsp?nav=/kms/knowledge/tree.jsp',
											target:'_rIframe'
										}
									}
								}
							]
						})
				}
			});
	// 获取"更多操作"按钮的元素，重新初始化
	function _reLoadKmsKnowledgeMoreBtn(_reLoadIndex){
		if (_reLoadIndex > 10){
			// 获取10次没获取到就结束
			return;
		}
		if(LUI("kmsKnowledgeMoreBtn")){
			// 如果获取到元素，500毫秒后，重新初始化
			setTimeout(function() {_emitKmsKnowledgeMoreBtn();}, 500);
		} else {
			// 如果没有获取到，100毫秒后接着获获取
			_reLoadIndex++;
			setTimeout(function() {_reLoadKmsKnowledgeMoreBtn(_reLoadIndex);}, 100);
		}
	}
	// 重新初始化
	function _emitKmsKnowledgeMoreBtn(){
		if(LUI("kmsKnowledgeMoreBtn")){
			LUI("kmsKnowledgeMoreBtn").emit("redrawButton");
		}
	}
	_reLoadKmsKnowledgeMoreBtn(1);
});
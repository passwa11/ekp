define(function(require, exports, module) {

	var lang = require('lang!kms-knowledge');
	var langUi = require('lang!sys-ui');
	var $ = require("lui/jquery");
	var dialog = require('lui/dialog');
	var env = require('lui/util/env');
	var strutil = require('lui/util/str');
	function addDoc(categoryId, type,kmsCategoryEnabled) {
		var defaultId = "";
		if (categoryId) {
			var ids = categoryId.split(";");
			if (ids && ids.length != 1) {
				categoryId = "";
			}
		}
		if (categoryId) {
			defaultId = categoryId;
		}
		if ('1,3' == type) {
			// 从文档知识库来的新建
			addDocFromMultidoc(type, defaultId,kmsCategoryEnabled);
		} else if ('2,3' == type) {
			// 从维基知识库来的新建
			addDocFromWiki(type, defaultId,kmsCategoryEnabled);
		} else {
			
			var selectCategory=lang['kmsKnowledge.selectKnowledgeCategory'];
			if(kmsCategoryEnabled){
				selectCategory=lang['kmsKnowledge.selectKnowledgeCategory.categoryTrue'];
			}
			dialog
					.simpleCategoryForNewFile(
							{
								modelName : "com.landray.kmss.kms.knowledge.model.KmsKnowledgeCategory",
								mulSelect : false,
								action : checkTemplateType,
								winTitle : selectCategory,
								canClose : true,
								___urlParam : type ? {
									'fdTemplateType' : type
								} : null,
								url : "/kms/knowledge/kms_knowledge_category/simple-category.jsp"
							}, "", false, null, "", defaultId, null, true);
		}
	}

	/**
	 * 从文档知识库来的新建
	 * 
	 * @param type
	 * @param defaultId
	 * @returns
	 */
	function addDocFromMultidoc(type, defaultId,kmsCategoryEnabled) {
		var selectCategory=lang['kmsKnowledge.selectKnowledgeCategory'];
		if(kmsCategoryEnabled){
			selectCategory=lang['kmsKnowledge.selectKnowledgeCategory.categoryTrue'];
		}
		dialog
				.simpleCategoryForNewFile(
						{
							modelName : "com.landray.kmss.kms.knowledge.model.KmsKnowledgeCategory",
							mulSelect : false,
							action : addDocForMultidoc,
							winTitle : selectCategory,
							canClose : true,
							___urlParam : type ? {
								'fdTemplateType' : type
							} : null,
							url : "/kms/knowledge/kms_knowledge_category/simple-category.jsp"
						}, "", false, null, "", defaultId, null, true);
	}

	/**
	 * 从维基知识库来的新建
	 * 
	 * @param type
	 * @param defaultId
	 * @returns
	 */
	function addDocFromWiki(type, defaultId,kmsCategoryEnabled) {
		var selectCategory=lang['kmsKnowledge.selectKnowledgeCategory'];
		if(kmsCategoryEnabled){
			selectCategory=lang['kmsKnowledge.selectKnowledgeCategory.categoryTrue'];
		}
		dialog
				.simpleCategoryForNewFile(
						{
							modelName : "com.landray.kmss.kms.knowledge.model.KmsKnowledgeCategory",
							mulSelect : false,
							action : addDocForWiki,
							winTitle : selectCategory,
							canClose : true,
							___urlParam : type ? {
								'fdTemplateType' : type
							} : null,
							url : "/kms/knowledge/kms_knowledge_category/simple-category.jsp"
						}, "", false, null, "", defaultId, null, true);
	}
	/**
	 * 打开文档知识库新建页面
	 * 
	 * @param params
	 * @returns
	 */
	function addDocForMultidoc(params) {

		checkTemplateTypeByFrom(params, "1")
	}
	/**
	 * 打开维基知识库新建页面
	 * 
	 * @param params
	 * @returns
	 */
	function addDocForWiki(params) {

		checkTemplateTypeByFrom(params, "2")
	}

	// 判断分类对应的模版库为多个，则弹出选择框，供用户选择。否则直接打开对应模版库的文档创建页面
	function checkTemplateType(params) {

		checkTemplateTypeByFrom(params, null)
	}
	/**
	 * 判断分类对应的模版库为多个，则弹出选择框，供用户选择。否则直接打开对应模版库的文档创建页面 addFrom：从哪里来的新建
	 */
	function checkTemplateTypeByFrom(params, addFrom) {

		if (!params || !params.id) {
			return;
		}
		seajs
				.use(
						[ 'lui/jquery', 'lui/data/source', 'lui/util/env' ],
						function($, source, env) {

							if (addFrom) {
								var openUrl = strutil.variableResolver(
										getUrl(addFrom), params);
								window.open(env.fn.formatUrl(openUrl),
										params.target ? params.target
												: '_blank');
								return;
							}
							var url = "/kms/knowledge/kms_knowledge_category/kmsKnowledgeCategory.do?method=findTemplate";
							url += "&qq.id=" + params.id;
							$.ajax({
								url : env.fn.formatUrl(url),
								dataType : 'json',
								async : false,
								success : function(rtnData) {

									if (rtnData && rtnData.length == 1) {// 打开对应的模板库的创建页面
										var openUrl = strutil.variableResolver(
												getUrl(rtnData[0].value),
												params);
										window.open(env.fn.formatUrl(openUrl),
												params.target ? params.target
														: '_blank');
									} else {
										selectTemplate(params);// 打开模板库供用户选择
									}
								}
							});
						});
	}

	// 模版选择
	function selectTemplate(params) {
		if (!params || !params.id) {
			return;
		}
		var that = this;
		dialog
				.build(
						{
							config : {
								width : 400,
								height : 280,
								lock : true,
								cache : false,
								title : lang['kmsKnowledge.selectKnowledgeTemplate'],
								content : {
									id : 'dialog_iframe',
									scroll : true,
									type : "iframe",
									url : '/kms/knowledge/kms_knowledge_ui/template_select.jsp',
									params : params,
									iconType : ""
								}
							},
							callback : function(value, dialog) {

							},
							actor : {
								type : "default"
							},
							trigger : {
								type : "default"
							}
						}).show();
	}

	function getUrl(type) {

		var url;
		if (type == '1') {
			// 文档库新建链接
			url = "/kms/multidoc/kms_multidoc_knowledge/kmsMultidocKnowledge.do?method=add&fdTemplateId=!{id}";
		} else if (type == '2') {
			// 维基新建链接
			url = "/kms/wiki/kms_wiki_main/kmsWikiMain.do?method=add&fdCategoryId=!{id}";
		}
		return url;
	}

	function addWikiImport(categoryId, wikiMethod) {

		var defaultId = "";
		if (categoryId) {
			var ids = categoryId.split(";");
			if (ids && ids.length != 1) {
				categoryId = "";
			}
		}
		if (categoryId) {
			defaultId = categoryId;
		}
		var up = "/kms/wiki/kms_wiki_main/kmsWikiMain.do?method=add&fdCategoryId=!{id}";
		if ("addWikiImport" == wikiMethod) {
			up = "/kms/wiki/kms_wiki_main/kmsWikiMain.do?method=add&fdCategoryId=!{id}&wikiMethod=addWikiImport";
		}
		dialog
				.simpleCategoryForNewFile(
						{
							modelName : "com.landray.kmss.kms.knowledge.model.KmsKnowledgeCategory",
							urlParam : up,
							mulSelect : false,
							action : null,
							winTitle : null,
							canClose : null,
							___urlParam : {
								'fdTemplateType' : '2,3'
							},
							url : "/kms/knowledge/kms_knowledge_category/simple-category.jsp?"
						}, "", false, null, null, defaultId, null, null);
	}

	exports.addDoc = addDoc;
	exports.addWikiImport = addWikiImport;
	exports.getUrl = getUrl;
	exports.checkTemplateType = checkTemplateType;
});
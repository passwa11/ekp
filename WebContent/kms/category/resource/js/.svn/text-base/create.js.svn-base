define(function(require, exports, module) {

	var lang = require('lang!kms-knowledge');
	var langUi = require('lang!sys-ui');
	var $ = require("lui/jquery");
	var dialog = require('lui/dialog');
	var env = require('lui/util/env');
	var strutil = require('lui/util/str');
	var Spa = require('lui/spa/Spa');
	
	function addDoc() {
		var template=Spa.spa.getCriteriaValue('template');
		if(template!=null&&template!=''&&false){
			var url;
			if (template == '1') {
				// 文档库新建链接
				url = "/kms/multidoc/kms_multidoc_knowledge/kmsMultidocKnowledge.do?method=add";
			} else if (template == '2') {
				// 维基新建链接
				url = "/kms/wiki/kms_wiki_main/kmsWikiMain.do?method=add";
			}else if (template == '3') {
				// 原子新建链接
				url = "/kms/kem/kms_kem_main/kmsKemMain.do?method=add";
			}

			var target = '_blank';

			top.open(env.fn.formatUrl(url), target);
			
			return;
		}
		
		var params={};
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
									url : '/kms/category/kms_category_main_ui/template_select.jsp',
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
			url = "/kms/multidoc/kms_multidoc_knowledge/kmsMultidocKnowledge.do?method=add";
		} else if (type == '2') {
			// 维基新建链接
			url = "/kms/wiki/kms_wiki_main/kmsWikiMain.do?method=add";
		}else if (type == '3') {
			// 原子新建链接
			url = "/kms/kem/kms_kem_main/kmsKemMain.do?method=add";
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
});
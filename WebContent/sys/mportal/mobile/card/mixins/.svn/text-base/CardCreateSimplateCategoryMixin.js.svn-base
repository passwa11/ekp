define([ "dojo/_base/declare",  "mui/util", "mui/form/_CategoryBase",
		"mui/simplecategory/SimpleCategoryMixin", "mui/device/adapter", "dojo/_base/lang",
		"dijit/_WidgetBase"],
		function(declare, util, CategoryBase,
		SimpleCategoryMixin, adapter, lang, _WidgetBase) {
	
	var simpleCategory = declare([_WidgetBase, CategoryBase, SimpleCategoryMixin ],  {
		
		createUrl : "",
		modelName : "",
		___urlParam : "",
		key : "",
		
		postCreate : function() {
			this.inherited(arguments);
			this.eventBind();
		},

		selectCate : function() {
			this.defer(function() {
				this._selectCate();
			}, 350);
		},

		afterSelect : function(evt) {
			this.defer(function() {
				adapter.open(util.formatUrl(util.urlResolver(this.createUrl, evt)), "_self");
				this.curIds = "";
				this.curNames = "";
				//process.hide();
			}, 300);
		}
	});
	
	

	return declare("sys.mportal.CardCreateSimplateCategoryMixin", [ CategoryBase, SimpleCategoryMixin ], {
		
		bindClick : function(btn, create, vars, key) {
			if (!create || !btn)
				return;

			if(create.cfg && create.cfg.type == "simplecategory") { 
				var url = util.setUrlParameter(create.href, 'ownerId', dojoConfig.CurrentUserId);
				
				var urlParamName = create.cfg.urlParamName || "categoryId";
				
				if(create.cfg.varKey && vars && vars[create.cfg.varKey]) {
					url = util.setUrlParameter(url, urlParamName, vars[create.cfg.varKey]);
					this.proxyClick(btn, url, '_blank');
				} else {
					var category = new simpleCategory({
						createUrl : create.href + "&" + urlParamName + "=!{curIds}",
						modelName : create.cfg.cateModelName,
						___urlParam : create.cfg.___urlParam || "",
						key : key
					});
					
					this.connect(btn, "click", lang.hitch(category, category.selectCate));
				}
			}
		}

	});
});
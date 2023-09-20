require(['dojo/ready', "dojo/parser", 'dojo/dom', 'dojo/dom-construct', 'mui/util', 'dijit/registry', 'dojo/topic', 
         'dojo/query', 'dojo/NodeList-manipulate', 'dojo/NodeList-traverse'], 
		function(ready, parser, dom, domConstruct, util, registry, topic, query) {
	ready(function() {
		require([ "mui/form/ajax-form!" + editOption.formName ]);
	});
	var mainView = null;						//主视图
	var lastClick = 0;
	window.expandDetail=function(detailId, curView){
		if(!clickChecked()) return;
		var detailViewName = detailId + "_view";
		var tmpl = dom.byId(detailViewName);
		if(mainView==null && curView!=null){
			mainView = registry.byId(curView);
		}
		mainView.set('validateNext',false);
		if(mainView.domNode.parentNode == tmpl.parentNode){
			mainView.performTransition(detailViewName, 1, "slide");
			tmpl.style.display = 'block';
		}else{
			domConstruct.place(tmpl, mainView.domNode.parentNode, 'last');
			parser.parse(tmpl).then(function(){
				mainView.performTransition(detailViewName, 1, "slide");
				tmpl.style.display = 'block';
				try {
					if(DocList_TableInfo[detailId]==null){
						DocListFunc_Init();
					}
					lastClick = 0;
					addDetailRow(detailId);
				} catch (e) {
				}
			});
		}
	};
	
	window.collapseDetail=function(detailId){
		if(!clickChecked()) return;
		var view = registry.byId(detailId + '_view');
		view.performTransition(mainView.id, -1, "slide");
		mainView.set('validateNext',true);
		if(mainView.validate)
			mainView.validate();
	};
	
	window.addDetailRow = function(detailId){
		if(!clickChecked()) return;
		var newRow = DocList_AddRow(detailId);
		parser.parse(newRow).then(function(){
			var tabInfo = DocList_TableInfo[detailId];
			if(tabInfo['_getcols']== null){
				tabInfo.fieldNames=[];
				tabInfo.fieldFormatNames=[];
				DocListFunc_AddReferFields(tabInfo, newRow, "INPUT");
				DocListFunc_AddReferFields(tabInfo, newRow, "TEXTAREA");
				DocListFunc_AddReferFields(tabInfo, newRow, "SELECT");
				tabInfo['_getcols'] = 1;
			}
			fixDetailNo(detailId);
			var view = registry.byId(detailId + '_view');
			if(view.resize)
				view.resize();
			topic.publish('/mui/form/valueChanged',null,{row:newRow});
		});
	};
	
	window.deleteDetailRow = function(detailId, evtDomObj){
		if(!clickChecked()) return;
		var trDom = query(evtDomObj).parents('.detail_wrap_td').parent();
		var rowObj = null;
		if(trDom.length>0){
			rowObj = trDom[0];
		}
		if(rowObj == null){
			return
		}
		query('*[widgetid]',rowObj).forEach(function(widgetDom,idx){
			var widget = registry.byNode(widgetDom);
			if(widget && widget.destroy){
				widget.destroy();
			}
		});
		DocList_DeleteRow_ClearLast(rowObj);
		fixDetailNo(detailId);
		var view = registry.byId(detailId + '_view');
		if(view && view.resize)
			view.resize();
		topic.publish('/mui/form/valueChanged');
	};
	function clickChecked(){
		var now = new Date().getTime()
		if(now-lastClick>800){
			lastClick = now
			return true;
		}
		return false;
	}
	function fixDetailNo(detailId) {
		query('#' + detailId+' .muiDetailTableNo').forEach(function(domObj,i) {
			query("span",domObj).text(editOption.lang['the'] + (i + 1) + editOption.lang['row'])
		});
	}
	window.getSource = function(key){
		var context = editOption.dialogs[key];
		if(context){
			var callback = function(){
				var sourceUrl = context.sourceUrl;
				var params={};
				if(context.params){
		    		for(var i=0;i<context.params.length;i++){
		    			var argu = context.params[i];
		    			for(var field in argu){
		    				var tmpFieldObj = document.getElementsByName(field);
		    				if(tmpFieldObj.length>0){
		    					var wgtObj = registry.getEnclosingWidget(tmpFieldObj[0]);
		    					if(wgtObj!=null)
		    						params['c.' + argu[field] + '.'+field] = wgtObj.get("value");
		    				}
		    			}
		    		}
				}
				return util.setUrlParameterMap(sourceUrl,params)
			}
			return callback;
		}else{
			return null;
		}
	}
	window.form_submit = function() {
		var method = Com_GetUrlParameter(location.href, 'method');
		var statusObj = query("input[name='docStatus']");
		statusObj.val('20');
		if (method == 'add') {
			Com_Submit(document.forms[0], 'save');
		} else {
			Com_Submit(document.forms[0], 'update');
		}
	};
});
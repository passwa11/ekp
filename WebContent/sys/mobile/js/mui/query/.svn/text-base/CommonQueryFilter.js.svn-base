define([ "dojo/_base/declare", "dijit/_WidgetBase", 
         "dojo/dom-construct", "dojo/dom-class", "dojo/query", "dojo/dom-attr",  "mui/util", "dojo/topic"], 
		function(declare, WidgetBase, domConstruct, domClass, query, domAttr, util, topic) {
	return declare( "mui.query.CommonQueryFilter", [ WidgetBase ],{
		
		commonQuery : '',
		
		buildRendering : function() {

			this.inherited(arguments);
			
			this.buildCommonQuery();
			
		},
		
		buildCommonQuery : function(){
			
			var url = window.location.href;
			
			var filterItem = domConstruct.create('div', {
				className : 'filterItem'
			}, this.domNode);

			var filterItemTitle = domConstruct.create('h4', {
				className : 'filterItemTitle',
				innerHTML : '常用筛选'
			}, filterItem);

			var ul = domConstruct.create('ul', {}, filterItem);

			var selectedQuery = window.selectCommonQuery;
			
			for (var i = 0; i < this.commonQuery.length; i++) {

				var checked = false;
				
				if (selectedQuery == this.commonQuery[i].dataURL)
					checked = true

				var li = domConstruct.create('li', {
					className : checked ? 'CommonProperties selected'
							: 'CommonProperties',
					innerHTML : '<a>' + this.commonQuery[i].text + '</a>'
				}, ul);

				domAttr.set(li, 'dataUrl', this.commonQuery[i].dataURL);

				this.connect(li, 'onclick', 'clickProperties');

			}
		},
		
		clickProperties : function(e) {
			
			var target = e.currentTarget;
			
			var isSelected = false;
			
			if(domClass.contains(target, 'selected'))
				isSelected = true;
			
			query('.CommonProperties').forEach(function(item, index){
				
				if(domClass.contains(item, 'selected')){
					
					domClass.remove(item, 'selected');

					return;
				}
				
			})
			
			if(!isSelected){

				domClass.add(target, 'selected');
				
				topic.publish('/mui/property/changeCommonQuery', target.getAttribute('dataUrl'));
				
				window.selectCommonQuery = target.getAttribute('dataUrl');
				
			} else {
				
				topic.publish('/mui/property/changeCommonQuery', '');
				
				window.selectCommonQuery = '';

			}

		}
		
	});
});

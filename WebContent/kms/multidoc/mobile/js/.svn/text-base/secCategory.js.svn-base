define([
	"dojo/_base/declare",
	"mui/tabbar/CreateButton",
	"dojo/dom-construct",
	"dojo/query"
	], function(declare, CreateButton, domConstruct, query) {

	return declare("kms.multidoc.secCategory", [CreateButton], {

		buildRendering: function(){
			this.inherited(arguments);
			
			this.buildContent();
			
			this.buildIcon();
			
		},
		
		buildContent: function(){
			
			this.contentDom = domConstruct.create('div', {
				'class' : 'secCateContent'
			},this.domNode);
			
		},
		
		buildIcon: function(){
			
			this.iconDom = domConstruct.create('div', {
				'class' : 'secCateIcon'
			},this.domNode);
			
			domConstruct.create('i',{
				'class' : 'mui mui-forward'
			},this.iconDom);
			
		},
		
		afterSelectCate: function(evt){
			 var ids = [];
			 var names = [];
			 if(evt.curIds){
				 ids = evt.curIds.split(';');
			 }
			 if(evt.curNames){
				 names = evt.curNames.split(';');
			 }
			if(ids&&names&&ids.length==names.length){
				
				var idStr = '';
				
				this.contentDom.innerHTML = ''; // 清空原内容
				
				for(var i = 0;i < ids.length; i++){
					
					this.contentDom.innerHTML = this.contentDom.innerHTML + ' '  + names[i];
					
					idStr += ids[i] + ';'
					
				}
				
				if(idStr !=''){
					
					var inputDom = query('input[name="docSecondCategoriesIds"]');
					
					if(inputDom.length == 1)
						inputDom[0].value = idStr;
					else
						domConstruct.create('input',{
							'type' : 'hidden',	
							'name' : 'docSecondCategoriesIds',
							'value' : idStr
						},this.domNode);
				}
				
			}
		}
		
	});
});
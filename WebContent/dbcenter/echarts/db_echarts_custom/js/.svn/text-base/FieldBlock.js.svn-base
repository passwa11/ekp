/**
 * 
 */
(function (){
	
	/*******************FieldBlock start************************/
	function FieldBlock(wrap){
		this.source;
		this.domNode;
		this.titleDom;
		this.fieldArr = []; //[{"component":component,"dom":$html}]
		
		this.init = FieldBlock_Init;
		this.buildDomNode = FieldBlock_BuildDomNode;
		this.update = FieldBlock_Update;
		this.add = FieldBlock_Add;
		this.del = FieldBlock_Del;
		this.getField = FieldBlock_GetField;
		this.updateTitleTxt = FieldBlock_UpdateTitleTxt;
		
		this.init(wrap);
	}
	
	function FieldBlock_Init(wrap){
		if(!this.domNode){
			wrap.append(this.buildDomNode());
		}else{
			this.del();
		}
	}
	
	function FieldBlock_UpdateTitleTxt(txt){
		if(!this.titleDom){
			var $title = $("<dt>");
			$title.append(txt);
			this.titleDom = $title;
			this.domNode.append($title);
		}else{
			this.titleDom.html(txt);	
		}
		
	}
	
	function FieldBlock_GetField(component){
		for(var i = 0;i < this.fieldArr.length;i++){
			var field = this.fieldArr[i];
			if(field.component == component){
				return field;
			}
		}
	}
	
	function FieldBlock_Add(component){
		var self = this;
		if(!self.getField(component)){
			var text = component.fieldComponent.text;
			var $html = $("<dd><span class='lui-chartData-tag'>"+ text +"</span></dd>");
			self.domNode.append($html);
			$html.click(function(){
				var val = component.fieldComponent.val.replace("\|",".");
				val = self.source.titleVal + "." + val;
				CKEDITOR.instances.fdCustomText.insertText("$" + val + "$");
			});
			self.fieldArr.push({"component":component,"dom":$html});
			return $html;			
		}
	}
	
	function FieldBlock_Update(component,type){
		var field = this.getField(component);
		if(type == "delete"){
			field.dom.remove();
			for(var i = 0;i < this.fieldArr.length;i++){
				var f = this.fieldArr[i];
				if(f.component == component){
					this.fieldArr.splice(i,1);
					return;
				}
			}
		}else if(type == "change"){
			if(!field){
				this.add(component);
			}else{
				field.dom.find(".lui-chartData-tag").html(component.fieldComponent.text);
			}
		}
	}
	
	function FieldBlock_BuildDomNode(){
		var domNode = $("<dl>");
		domNode.addClass("lui-chartData-info-list");
		this.domNode = domNode;
		return domNode;
	}
	
	function FieldBlock_Del(){
		// 删除的时候，不能把DOMNode也删除，以免找不到位置
		if(this.domNode){
			this.domNode.find("dd").remove();
			this.fieldArr.length = 0;
		}
	}
	/********************FieldBlock end***********************/
	
	window.FieldBlock = FieldBlock;
})();
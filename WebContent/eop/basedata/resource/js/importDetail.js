Com_IncludeFile("iconfont.css", Com_Parameter.ContextPath+"eop/basedata/resource/iconfont/", 'css', true);
Com_IncludeFile("common.css", Com_Parameter.ContextPath+"fssc/common/resource/css/", 'css', true);
Com_IncludeFile("render.js", Com_Parameter.ContextPath+"eop/basedata/resource/js/", 'js', true);
var ImportDetailUtil = function(options){
	this.modelName = options.modelName;
	this.docTemplateId=options.docTemplateId;
	this.element = options.container;
	this.fdCompanyId = options.fdCompanyId;
	this.table = typeof(options.table)=="string"?$("#"+options.table):$(options.table);
	this.callback =  options.callback;
	this.getTemplate();
	this.fieldContains = function(fields,fieldName){
		for(var i=0;i<fields.length;i++){
			if(fields[i].name==fieldName){
				return true;
			}
		}
		return false;
	}
}
ImportDetailUtil.prototype.getProperties = function(){
	var fields = [],index = 0;
	for(var k=1;k<this.tbInfo.cells.length;k++){
		for(var i=0;i<this.tbInfo.fieldNames.length;i++){
			var name = this.tbInfo.fieldNames[i].split(".")[1];
			var dom = $(this.tbInfo.cells[k].innerHTML).find("[name$="+name+"]");
			if(dom.length>0){
				dom = dom[0];
				if(dom.getAttribute("type")!='hidden'&&!this.fieldContains(fields,name)){
					var validate = dom.getAttribute("validate");
					fields.push({name:name,required:validate!=null&&(validate.indexOf('required')>-1)});
				}
			}
		}
	}
	var data = new KMSSData();
	data = data.AddBeanData('eopBasedataBusinessService&type=getDetailImportCol&authCurrent=true&properties='+encodeURIComponent(JSON.stringify(fields))+"&modelName="+this.modelName);
	data = data.GetHashMapArray();
	if(data.length>0){
		this.properties = JSON.parse(data[0].properties);
	}else{
		this.properties = fields;
	}
}
ImportDetailUtil.prototype.getTemplate = function(){
	this.tbInfo = DocList_TableInfo[this.table.attr("id")];
	var tmp = "for(var i=0;i<data.length;i++){{$<tr>";
	for(var i=0;i<this.tbInfo.cells.length;i++){
		tmp+="<td align='center' ";
		if(this.tbInfo.cells[i].className){
			tmp+='class="'+this.tbInfo.cells[i].className+"\"";
		}
		tmp+=">";
		tmp+=this.tbInfo.cells[i].innerHTML.replace(/\s!{index}\s/g,"{%i+"+this.tbInfo.lastIndex+"%}").replace(/\!\{index\}/ig,"{%i+"+(this.tbInfo.lastIndex-1)+"%}");
		tmp+="</td>"
	}
	tmp += "</tr>$}}";
	this.template = tmp;
}
ImportDetailUtil.prototype.downloadTemplate = function(){
	window.open(Com_Parameter.ContextPath+'eop/basedata/eop_basedata_business/eopBasedataBusiness.do?method=downloadDetailTemplate&modelName='+this.modelName+"&properties="+encodeURI(JSON.stringify(this.properties)));
}
ImportDetailUtil.prototype.importData = function(){
	this.fdCompanyId = $("[name=fdCompanyId]").val();
	var self = this;
	this.importIndex = DocList_TableInfo[this.table.attr("id")].lastIndex-DocList_TableInfo[this.table.attr("id")].firstIndex;
	seajs.use(['lui/dialog','lang!eop-basedata'],function(dialog,lang){
		window.imptDialo = dialog.iframe(
			'/eop/basedata/resource/jsp/importDetail.jsp?modelName='+self.modelName+"&properties="+encodeURI(JSON.stringify(self.properties))+"&fdCompanyId="+self.fdCompanyId+"&docTemplateId="+self.docTemplateId,
			lang['button.import.detail'],
			function(data){
				self.handler(data);
			},
			{width:800,height:600}
		);
	})
};
ImportDetailUtil.prototype.handler = function(data){
	if(!data){
		return;
	}
	var cols = data.cols;
	data = data.data;
	this.getTemplate();
	for(var i in data[0]){
		var reg=new RegExp('(name=\"[^_].+'+i+'\"[^>]+value\=)\"[^"]*\"',"g");
		this.template=this.template.replace(reg,"$1\"{%data[i]."+i+"%}\"");
	}
	this.tbInfo.lastIndex+=data.length;
	var code = window.YayaTemplate(this.template).render({
		data:data
	});
	this.table.append(code);
	var htmls = [];
	for(var m=0;m<data.length;m++){
		for(var i in cols){
			if(cols[i].fdType.indexOf('SysOrg')>-1){//如果是组织架构类型，需要特殊处理
				var fdFieldId = cols[i].fdFieldId, fdFieldName = cols[i].fdFieldName;
				var addressInput = $("input.mf_input.mp_input[xform-name*="+fdFieldName+"]");
				if(cols[i].fdIsMulti){//列表
					var name = data[m][fdFieldName].split(';'),id = data[m][fdFieldId].split(';');
					var html = [];
					if(name.length>0){
						var addressValues = new Array();
						for(var k=0;k<name.length;k++){
						    addressValues.push({id:id[k],name:name[k]});
						}
						newAddressAdd(addressInput.get(m+this.importIndex),addressValues);
					}
					htmls.push(html.join(''));
				}else{
					var addressValues = new Array();
				    addressValues.push({id:data[m][fdFieldId],name:data[m][fdFieldName]});
				    newAddressAdd(addressInput.get(m+this.importIndex),addressValues);
				}
			}else if(cols[i].fdType=='Boolean'){//布尔类型，特殊处理
				 var fdFieldName = cols[i].fdFieldName,value = data[m][fdFieldName].split(";");
				 var srcElement = $("input[type=hidden][name$="+fdFieldName+"]").eq(m+this.importIndex);
				 if(srcElement.length>0){
					 var displayObj = $("[name='_"+srcElement.attr("name")+"']");
					 if(displayObj.eq(0).attr("type")=="checkbox"||displayObj.eq(0).attr("type")=="radio"){
						 displayObj.each(function(){
							 for(var n=0;n<value.length;n++){
								 if(this.value==value[n]){
									 this.checked = true;
									 if(this.onclick){
										 this.onclick();
									 }
								 }
							 }
						 })
					 }
				 }
			}
		}
	}
	if(this.callback){
		this.callback(data,cols,this.importIndex);
	}
	this.importIndex+=data.length;
}
ImportDetailUtil.prototype.addButton = function(){
	this.getProperties();
	this.getTemplate();
	this.buttons = {};
	var self = this;
	seajs.use(['lang!eop-basedata'],function(lang){
		var btns = [];
		btns.push('<div class="fssc_expense_btn">');
		btns.push('<span class="iconfont icon-daoru1"></span>');
		btns.push(lang['button.import.detail']);
		btns.push("</div>");
		self.buttons.import = {
			element : $(btns.join('')),
			fn : self.importData
		}
		
		btns = [];
		btns.push('<div class="fssc_expense_btn">');
		btns.push('<span class="iconfont icon-daoru"></span>');
		btns.push(lang['button.exportTemplate']);
		btns.push("</div>");
		self.buttons.downloadTemplate = {
			element : $(btns.join('')),
			fn : self.downloadTemplate
		}
		self.element.append(self.buttons.import.element);
		self.element.append(self.buttons.downloadTemplate.element);
		self.buttons.downloadTemplate.element.click(function(){self.downloadTemplate()});
		self.buttons.import.element.click(function(){self.importData()});
	})
}

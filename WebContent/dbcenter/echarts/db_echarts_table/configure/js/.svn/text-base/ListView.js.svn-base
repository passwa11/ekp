// 列表视图
(function(){
	function ConfigureListView(){
		// 当前表格
		this.domNode;
		
		this.items = [];
		
		this.init = ConfigureListView_Init;
		this.addItem = ConfigureListView_AddItem;
		this.changeItem = ConfigureListView_ChangeItem;
		this.deleteItem = ConfigureListView_DeleteItem;
		this.findItem = _ConfigureListView_FindItem;
		this.spliceArray = _ConfigureListView_SpliceArray;
		this.getKeyData = ConfigureListView_GetKeyData;
		this.converWidth = ConfigureListView_ConverWidth;
		
		this.fillData = ConfigureListView_FillData;
		
	}

	function ConfigureListView_Init(){
		
	}

	function ConfigureListView_AddItem(component){
		var $tr;
		var self = this;
		var config = {};
		config.field = component.fieldComponent.val;
		config.text = component.fieldComponent.text;
		config.component = component;
		var item = new ConfigureListViewItem(self,config);
		$tr = item.createItemDom();
		self.items.push(item);
		self.domNode.append($tr);
		return item;
	}

	function ConfigureListView_ChangeItem(component){
		var self = this;
		var item = self.findItem("component",component);
		if(item){
			// 如果存在，在更改label
			item.domNode.find("."+ item.fieldSpanClassName).html(component.fieldComponent.text);
			item.text = component.fieldComponent.text;
			item.field = component.fieldComponent.val;
		}else{
			// 如果不存在，则新建
			self.domNode.append(self.addItem(component));
		}
	}

	function ConfigureListView_DeleteItem(component){
		var self = this;
		var item = self.findItem("component",component);
		if(item){
			item.domNode.remove();
			self.spliceArray(self.items,item);
		}
	}

	function _ConfigureListView_FindItem(key,val){
		var self = this;
		for(var i = 0;i < self.items.length;i++){
			var item = self.items[i];
			if(item[key] == val){
				return item;
			}
		}
	}

	function ConfigureListView_GetKeyData(){
		var self = this;
		
		var datas = [];
		for(var i = 0;i < self.items.length;i++){
			var item = self.items[i];
			var data = item.getKeyData();
			datas.push(data);
		}
		// 补全宽度
		self.converWidth(datas);
		
		// 排序，保存顺序关系
		datas.sort(function(a,b){
			var o1 = a.order;
			var o2 = b.order;
			if(o1 > o2){
				return 1;
			}else{
				return -1;
			}
		});
		return datas;
	}
	
	function ConfigureListView_ConverWidth(datas){
		var total = 0;
		for(var i = 0;i < datas.length;i++){
			var data = datas[i];
			if(data.hidden == "false"){
				var width = 0;
				if(data.widthRatio){
					width = data.widthRatio;
				}
				total += parseInt(width);
			}
		}
		for(var i = 0;i < datas.length;i++){
			var data = datas[i];
			if(data.hidden == "false"){
				if(data.widthRatio){
					data.width = parseFloat(data.widthRatio/total*95).toFixed(2) + "%";//预留5%用于序列号
				}
			}
		}
	}

	function ConfigureListView_FillData(config){
		if(config){
			var self = this;
			for(var i = 0;i < config.length;i++){
				var data = config[i];
				var item = self.findItem("field",data.key);
				if(item){
					item.fillData(data);
					// 切换顺序
					item.moveTrToFixedCol(i+1);
				}
			}
		}
	}

	function _ConfigureListView_SpliceArray(array, spliced) {
		for (var i = 0; i < array.length; i++)	{
			if (array[i] == spliced) {
				array.splice(i, 1);
				break;
			}
		}
	};

	/***********************ConfigureListViewItem*******************************/
	function ConfigureListViewItem(listview,config){
		this.listView = listview ? listview : {};
		
		this.domNode;
		this.component;
		
		this.field;
		this.text;
		this.name;
		this.width;
		this.widthRatio;
		this.align;
		this.hidden = false;
		this.order;
		
		this.fieldSpanClassName = "field";
		this.columnInputName = "column";
		this.columnWidthName = "columnWidth";
		this.alignSelectName = "align";
		this.hiddenInputName = "hidden";
		this.sortInputName = "sort";
		
		this.defaultAlignOpt = {
				"center" : DbcenterLang.centerAlign,
				"left" : DbcenterLang.leftAlign,
				"right" : DbcenterLang.rightAlign
		};
		
		this.init = _ConfigureListViewItem_Init;
		this.createItemDom = ConfigureListViewItem_CreateItemDom;
		this.getKeyData = ConfigureListViewItem_GetKeyData;
		this.fillData = ConfigureListViewItem_FillData;
		this.moveTrToFixedCol = ConfigureListViewItem_MoveTrToFixedCol; 
		
		this.init(config);
	}

	function _ConfigureListViewItem_Init(config){
		if(config){
			this.field = config.field;
			this.text = config.text;
			this.component = config.component;
		}
	}

	function ConfigureListViewItem_GetKeyData(){
		var self = this;
		var data = {};
		// 为跟原来的配置模式的参数一致，field转为key
		data.key = self.field;
		data.text = self.text;
		var n = self.domNode.find("[name='"+ self.columnInputName +"']").val();
		data.name = n == '' ? data.text: n;// 为空默认去原来的文本
		data.widthRatio = self.domNode.find("[name='"+ self.columnWidthName +"']").val();
		data.align = self.domNode.find("select[name='"+ self.alignSelectName +"'] option:selected").val();
		data.hidden = self.domNode.find("[name='"+ self.hiddenInputName +"']").prop("checked") + "";
		data.sort = self.domNode.find("[name='"+ self.sortInputName +"']").prop("checked") + "";
		data.order = self.domNode.index();
		return data;
	}

	function ConfigureListViewItem_FillData(config){
		var self = this; 
		if(config.key){
			self.field = config.key;
		}
		if(config.text){
			self.text = config.text;
			self.domNode.find("."+ self.fieldSpanClassName).html(config.text);
		}
		if(config.name){
			self.domNode.find("[name='"+ self.columnInputName +"']").val(config.name);
		}
		if(config.widthRatio){
			self.domNode.find("[name='"+ self.columnWidthName +"']").val(config.widthRatio);
		}
		if(config.align){
			self.domNode.find("select[name='"+ self.alignSelectName +"'] option[value='"+ config.align +"']").attr("selected", "selected");
		}
		if(config.hidden){
			self.domNode.find("[name='"+ self.hiddenInputName +"']").attr("checked",config.hidden == 'true');
		}
		if(config.sort){
			self.domNode.find("[name='"+ self.sortInputName +"']").attr("checked",config.sort == 'true');
		}
	}

	function ConfigureListViewItem_CreateItemDom(){
		var $tr = $("<tr>");
		var self = this;
		var html = "";
		// 返回字段值
		html += "<td><span class='"+ self.fieldSpanClassName +"'>"+ self.text +"</span></td>";
		// 列名 默认是返回值字段名
		html += "<td><input type='text' class='inputsgl' name='"+ self.columnInputName +"' ></td>";
		// 列宽
		html += "<td><input type='text' class='inputsgl' name='"+ self.columnWidthName +"' validate='digits' value=\"1\"/>";
		html += "</td>";
		// 对齐方式
		html += "<td><select name='"+ self.alignSelectName +"'>";
		for(var opt in self.defaultAlignOpt){
			html += "<option value='"+ opt +"'>";
			html += self.defaultAlignOpt[opt];
			html += "</option>";
		}
		html += "</select></td>";
		// 隐藏
		html += "<td><input type='checkbox' name='"+ self.hiddenInputName +"'></td>";
		// 排序
		html += "<td><input type='checkbox' name='"+ self.sortInputName +"'></td>";
		// 操作
		var temp = "";
		html += "<td><a href='javascript:void(0);' onclick='ConfigureListViewItem_MoveTr(-1,this);' style='color:#1b83d8;'>"+ DbcenterLang.moveUp +"</a>";
		html += "&nbsp;<a href='javascript:void(0);' onclick='ConfigureListViewItem_MoveTr(1,this);' style='color:#1b83d8;'>"+ DbcenterLang.moveDown +"</a>";
		html += "</td>";
		$tr.append(html);
		self.domNode = $tr;
		return $tr;
	}
	
	function ConfigureListViewItem_MoveTrToFixedCol(index){
		var self = this;
		if(self.domNode.index() != index){
			var rows = self.domNode.closest("table")[0].rows;
			$(rows[index - 1]).after(self.domNode);
		}
	}
	
	function ConfigureListViewItem_MoveTr(direct,dom){
		var tb = $(dom).closest("table")[0];
		var $tr = $(dom).closest("tr");
		var curIndex = $tr.index();
		var lastIndex = tb.rows.length - 1;
		if(direct == 1){
			if(curIndex >= lastIndex){
				alert(DbcenterLang.moveToDown);
				return;
			}
			$tr.next().after($tr);
		}else{
			if(curIndex < 2){
				alert(DbcenterLang.moveToTop);
				return;
			}
			$tr.prev().before($tr);			
		}
	}
	
	window.ConfigureListView = ConfigureListView;
	window.ConfigureListViewItem_MoveTr = ConfigureListViewItem_MoveTr;
})()

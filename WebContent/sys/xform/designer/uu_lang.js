/**
 * 国际化对象
 * 
 * @param control
 *            控件对象
 */


//初始化国际化对象绑定数组
var UU_lang_arr = [];
//缓存中间件数组，为 setHtml 不丢失所有翻译进行缓存
var _UU_lang_arr_models = {};

var UU_DataSource;

if(window.parent.document.getElementById("uu_FdContent")){
	UU_DataSource = window.parent.document.getElementById("uu_FdContent").value.replace(/</g,"&lt;").replace(/>/g,"&gt;").replace(/\\"/g,'&quot;')||"[]";
}else{
	UU_DataSource = "[]";
}

var _UU_parent = window.parent;
function UU_lang(control) {
	//控件对象
	this.control = control;
	//调试模式
	this.debug = true;
	
	/*	绑定的 model 容器
	 *
	 *	this.model =  UU_item
	 * 	
	 */
	this.model;
}

/**
 * 是否绑定该控件
 * @param control 控件对象
 * @returns true or false
 */
UU_lang.prototype.isBindControl = function (control){
	if(this.control === control){
		return true
	}
	return false;
}

/**
 * 销毁 model,内部方法
 * 
 */
UU_lang.prototype._destroy =function (){
	
	//剔除注册的中间件
	UU_lang_arr=_.mapExtend(UU_lang_arr,function(uu_lang){
		if(uu_lang === this){
			return _.uubreaker;
		}
		return uu_lang;
	},this);
}
//国际化标识
UU_lang.prototype.flag = "lang";

//国际化语言
UU_lang.prototype.lang = window._langLanguage;

//数据源后台绑定的数据源

try{
	UU_lang.prototype.dataSource = JSON.parse(UU_DataSource);
}catch(e){
	UU_lang.prototype.dataSource = [];
}
//console.log(JSON.parse(UU_DataSource));
//注册控件对应的解析方法
UU_lang.prototype.create_method = {
		"defaultMethod":"execute"
			//,
		//"textLabel":"parse_lable",
		//"inputRadio":"parse_inputRadio"
}

//注册控件对应的销毁方法
UU_lang.prototype.remove_method = {
		"defaultMethod":"executeRemove"
		//,
		//"textLabel":"remove_lable",
		//"inputRadio":"remove_inputRadio"	
};

UU_lang.prototype.executeRemove = function(){
	if(typeof Designer.instance.parentWindow.Form_getModeValue == "undefined" || Designer.instance.parentWindow.Form_getModeValue(Designer.instance.fdKey)!=Designer.instance.template_subform){
		this.model&&this.model.destroy();
		this._destroy();
	}else{
		var self = this;
		setTimeout(function(){
			var mycontrols = Designer.instance.subFormControls;
			var myid = '';
			if(Designer.instance.subForm && Designer.instance.subForm.id){
				myid = Designer.instance.subForm.id;
			}else{
				myid = "subform_default";
			}
			var bool = false;
			for(var key in mycontrols){
				if(key != myid){
					for(var p = 0;p<mycontrols[key].length;p++){
						if(self.control.options.values.id == mycontrols[key][p].options.values.id){
							bool = true;
							break;
						}
					}
					if(bool){
						break;
					}
				}
			}
			if(!bool){
				self.model && self.model.destroy();
				self._destroy();
			}
		},0);
	}
};

UU_lang.prototype.showView = function(){
	
	var modelObj, //容器
		dataSource = this.dataSource;
	
	for(var i = 0 ; i< dataSource.length ; i++){
		modelObj = _.extend({},dataSource[i]);
		var model = new UU_Item();
		model.set(modelObj);
		listView.addItem(model);
	}
	//add by duf, 让 view 页面兼容 chrome
	var table = window.parent.document.getElementById("Lable_UU_Lang");
	if(table){
		$(table).html($(table).html().replace(/(<div>|<\/div>)/ig, ""));
	}
};

UU_lang.prototype.execute = function(){
	
	var control = this.control,      	 //控件
		attrs = control.attrs, 		 	 //属性
		model = this.model, 		 	 //临时 model
		languageObj={},				 	 //语言对象
		localflag = false,				 //标识 model 是否是第一次初始化
		modelObj = {
				"c_id":control.options.values.id,
				"c_type":control.type,
				"c_option":{},
				"c_lang":{
					"control":control.info.name
				}						 //model 容器 ,这里还有初始化语言对象。因为第一次需要根据语言对象生成模板
		};	  							
	
		
	//遍历 二重循环
	for(var key in attrs){
		//#38405 增加空值判断，如果需要国际化的属性内容默认值是空的，则跳过（tipInfo可以空的） by zhugr 2017-04-24
		//遍历所有属性，如果没有国际化标识，则跳出当前当前属性
		if(attrs[key][this.flag] === undefined || control.options.values[key] === undefined || control.options.values[key] == ''){
			continue;
		}
		//如果存在国际化标识,缓存中读取 model,不存在构建 model
//		c_id: '',
//    	c_type: '',
////    		c_language:{
////    				"default":"",
////    	    		"en":"",
////    	    		"jp":"",
////    	    		"fh":""	
////    		}	
//    	},
		
		
		//如果缓存不存在
		if(model === undefined){
			//NEW 出来更新缓存
			model = this.model = new UU_Item();
			localflag = true;
		}
		
		if(localflag){
			
			/*
			 * 说明：当改方法第一次执行时，那时候中间件的 MODEL 其实是空没有东西，所以需要初始化多语言属性；因为模板执行时，需要那些属性，不然无法渲染。
			 * 但是第二次执行时，由于需要深度拷贝对象，所以不能再次初始化多语言属性，因为它会覆盖掉 MODEL 以及修改的多语言的属性。
			 * 注意！这里的第二次，不是循环第二次，而是更新触发第二次执行。
			 */
			
			//初始化 model 中的语言对象,注意====》这里没有初始化默认内容
			languageObj[key]||(languageObj[key]={});
			for(var i = 0 ;i<this.lang.length;i++){
				//判断数据源里面是否有对应记录，判断的依据是控件的ID，不会出现重复的。如果出现重复的话，那我就无语了。
				var dataSource = this.dataSource;
				for(var j = 0 ; j< dataSource.length ; j++){
					if(dataSource[j].c_id == modelObj.c_id){
						if(dataSource[j].c_option && dataSource[j].c_option[key] && dataSource[j].c_option[key][this.lang[i]]){
							//获取对应数据源
							languageObj[key][this.lang[i]] = dataSource[j].c_option[key][this.lang[i]];
							
							//初始化时当有items，需初始化itemsList
							if(key == "items" && !this.model.itemsList){
								this.model.itemsList = {};
								var myValue = dataSource[j].c_option[key]["default"].split(/\r\n|\n/);
								for(var langKey in dataSource[j].c_option[key]){
									if(langKey != "default"){
										var obj = {};
										var vals = dataSource[j].c_option[key][langKey].split(/\r\n|\n/);
										for(var o = 0;o<myValue.length;o++){
											obj[myValue[o]] = vals[o]||'';
										}
										this.model.itemsList[langKey] = obj;
									}
								}
							}
						}
					}
				}
				
				if(languageObj[key][this.lang[i]] === undefined){
					languageObj[key][this.lang[i]] = "";
				}
			}
		}
		
		modelObj.c_option = languageObj;
		
		//初始化默认内容，假使缓存存在，意味着是更新，那么只需要更新默认内容；如果全部更新，会丢失翻译内容
		
		if(modelObj.c_option[key] === undefined){
			modelObj.c_option[key]={};
		}
		
		// 初始化或更新默认值 （控件里面填写的需要国际化的内容）
		
		modelObj.c_option[key]["default"] = control.options.values[key];
		
		//根据itemsList中的值设置相应语言的值，防止因增删改选项而造成数据错乱或丢失
		if(key == "items" && model.itemsList && !$.isEmptyObject(model.itemsList)){
			var defaultItems = modelObj.c_option[key]["default"].split(/\r\n|\n/);
			for(var langkey in model.itemsList){
				var temp_Array = [];
				for(var m = 0;m<defaultItems.length;m++){
					temp_Array.push(model.itemsList[langkey][defaultItems[m]]||'');
				}
				modelObj.c_option[key][langkey] = temp_Array.join("\n");
			}
		}
		
		
		modelObj.c_lang[key]||(modelObj.c_lang[key] = {});
		
		modelObj.c_lang[key].text = attrs[key].text;
		modelObj.c_lang[key].type = attrs[key].type;
	}
	
	//如果当前控件，没有国际化属性，不生成 MODEL
	if(model === undefined){
		return;
	}
	
	//合并对象
	modelObj = $.extend(true,{},model.attributes, modelObj);
	
	//兼容 setHtml 缓存数据，这段代码，只有执行 setHtml 之后才会执行，也有其会执行一次
	if(_UU_lang_arr_models[modelObj.c_id] !== undefined){
		if(modelObj.c_type == modelObj.c_type){
			//比较 default 是否一致，如果不一致丢弃;
			if(_UU_lang_arr_models[modelObj.c_id].c_option){
				var oldOption = _UU_lang_arr_models[modelObj.c_id].c_option;
				var newOption = modelObj.c_option;
				for(var key in newOption){
						if(oldOption[key]&&newOption[key]){
							var oldDefault = oldOption[key]['default'];
							var newDefault = newOption[key]['default'];
							if(oldDefault == newDefault){
								newOption[key] = oldOption[key];
							}
						}
				}
			}
		}
		delete _UU_lang_arr_models[modelObj.c_id]; 
	}
	
	
	model.set(modelObj);
	
	listView.addItem(model);
	
}



//============================================================
//	mvvm 搭建 开始
//============================================================

//初始化 underscore 模板关键字
_.templateSettings = {
        interpolate: /\<\@\=(.+?)\@\>/gim,
        evaluate: /\<\@([\s\S]+?)\@\>/gim,
        escape: /\<\@\-(.+?)\@\>/gim
    };
var $template_dom = $("#detailRow_extend").length>0? $("#detailRow_extend"):$(window.parent.document.getElementById("detailRow_extend"));
var template_detailRow = _.template($template_dom.html()||"");

var UU_Item = Backbone.Model.extend({
    defaults: {
    	c_id: '',
    	c_type: '',
    	//属性
    	c_option: {
//    		key:{
//    				"default":"",
//    	    		"en":"",
//    	    		"jp":"",
//    	    		"fh":""	
//    		}	
    	},
    	c_lang :{
//    		key:{
//				text:"",
//    			type:""
//			}
//			control:    	
    	},
    	c_numLanguage:window._langLanguage.length
    }
});

var UU_List = Backbone.Collection.extend({
    model: UU_Item,
    
    addList: function (jsonArr) {

        for (var i = 0; i < jsonArr.length; i++) {
            var item = new Item();
            item.set(jsonArr[i]);
            this.add(item);
        }

    }
});

var UU_ItemView = Backbone.View.extend({

    $itemContainer : {},
    
    events:{
        "change .inputsgl"      : "changeModel"
    },

    changeModel:function(target){
    	
    	var optionkey = $(target.currentTarget).attr("data-uu-optionkey");
    	var langkey = $(target.currentTarget).attr("data-uu-langkey");
    	if(optionkey !== undefined && langkey !== undefined){
    		/*
    		 * 针对特殊属性 items 属性进行定制处理
    		 * 思路如下：
    		 * 元数据格式 A|a \r\n B|b \r\n C|c 这种类型，通过 \r\n 区分。
    		 * （\r\n 这种分隔符根据浏览器的不同有细微的不同，比如 IE 就是\n）
    		 * 通过模板处理以后，传递到此方法的元数据，是已经被分割后的格式 A|a 这种类型，前提是客户确实按照这么输入的。
    		 * 目前这种情形，有两种实现：
    		 * 第一种是切割一次， 到 “A|a” 就结束。
    		 * 第二种是再次以“|”切割 到 A 结束。
    		 * 前者实现是更简单；后者对用户会比较友好，可是他需要参照原始模板，所以逻辑层面会更复杂。
    		 * 此处采用第二种。
    		 */
    		var temp = this.model.get("c_option");
    		//这里不可能存在为空的情况，所以不需要做判断；出现为空的意味着代码实现存在错误。
    		//仔细一向这里还确实存在为空的可能，就是在创建好之后，他直接就去编辑，但是这种操作逻辑是本身就是有问题的
    		var template = this.model.get("c_option")[optionkey]["default"];
    		
    		if(template === undefined){
    			//如果为空，不需要执行后续修改 model 操作。这里可以理解为误操作，直接跳出
    			return ;
    		}
    		
    		if(optionkey =='items'){
    			
    			//获取当前排序
    			var order = $(target.currentTarget).attr("data-uu-order");
    			var templateArr = template.split(/\r\n|\n/);//优先级按\r\n 或者是 \n 切割，前者不存在，使用后者
    			var _length = templateArr.length;
    			var temp_Array = [];
    			//翻译元数据
        		var sourceTemplate = temp[optionkey][langkey];
        		//切割翻译远数据，生成模板
        		var sourceTemplateArr = (sourceTemplate||'').split(/\r\n|\n/);
    			//这里不管浏览器是如何实现，我们组装翻译所对应的元数据一律是以 \n 连接
    			
    			//翻译元数据为空，那么意味着第一次操作，直接初始化翻译元数据
        		var obj = {};
				for(var i=0;i<_length;i++){
					if(i == parseInt(order)){
						//如果等于对应标识
						temp_Array.push(target.currentTarget.value);
						obj[templateArr[i]] = target.currentTarget.value;
					}else{
						//从模板里面取，如果存在获取，不存在赋值为空
						temp_Array.push(sourceTemplateArr[i]||'');
						obj[templateArr[i]] = sourceTemplateArr[i]||'';
					}
				}
				temp[optionkey][langkey] = temp_Array.join("\n");
				if(!this.model.itemsList){
					this.model.itemsList = {};
				}
				this.model.itemsList[langkey] = obj;
    		}else{
        		temp[optionkey][langkey] = target.currentTarget.value;
    		}
    	}
    	//this.model.set('c_translator',this.input.val());
    },
    
    initialize: function () {
    	_.bindAll(this, 'render', "remove"); // every function that uses 'this' as the current object should be in here
    	// 每次更新模型后重新渲染  
        this.model.bind('change', this.render, this);  
        // 每次删除模型之后自动移除UI  
        this.model.bind('destroy', this.remove, this); 
        // this.render();
    },
    
    render: function () {
    	//window.console&&window.console.log("itemview ------------> render");
    	//初始化所有的容器行
    	this.itemConstruct();
    	var json  = this.model.toJSON();
    	
    	for(var i = 0 ; i< window._langLanguage.length ; i++){
    		json.uukey = window._langLanguage[i];
    		this.$itemContainer[window._langLanguage[i]].html(template_detailRow(json))
    	}
    	
        //$(this.el).html(template_detailRow(this.model.toJSON()));
        return this; // for chainable calls, like .render().el
    },
    
    //一对多构造方法，对应的容器
    itemConstruct: function(){
    	//优于渲染方法，先执行
    	if(_.keys(this.$itemContainer).length == window._langLanguage.length){
    		return this;
    	}
    	//遍历配置语言数组生成，对应的容器
    	for(var i = 0 ; i< window._langLanguage.length ; i++){
    		this.$itemContainer[window._langLanguage[i]] = $(document.createElement('tr'));
    		this._itemDelegateEvents(this.$itemContainer[window._langLanguage[i]]);
    	}
    	return this; 
    },
    
    //绑定事件，让其支持原版 event api
    _itemDelegateEvents:function($dom){
    	var events;
    	if (!(events || (events = _.result(this, 'events')))) return this;
        this._itemUndelegateEvents($dom);
        for (var key in events) {
          var method = events[key];
          if (!_.isFunction(method)) method = this[events[key]];
          if (!method) continue;
          var match = key.match(/^(\S+)\s*(.*)$/);
          $dom.on(match[1] + '.delegateEvents' + this.cid, match[2], _.bind(method, this));
        }
        return this;
    },
    
    //解除绑定事件
    _itemUndelegateEvents:function($dom){
    	 if ($dom) $dom.off('.delegateEvents' + this.cid);
         return this;
    },
    
    remove: function(){
    	//window.console&&window.console.log("itemview ------------> remove");
    	_.each(this.$itemContainer, function($value,key){
    		this._itemUndelegateEvents($value);
    		$value.remove();
    	},this);
    }
});

var UU_ListView = Backbone.View.extend({
	
    initialize: function () {
    	this.el = 'container';
        this.collection = collection;
        _.bindAll(this, 'render', 'addItem', 'addItemView',"submit"); // every function that uses 'this' as the current object should be in here
        this.collection.bind('add', this.addItemView); // collection event binder
       
    },
    
    render: function () {
        var self = this;
        //$(this.el).append(template_titleRow);
        _(this.collection.models).each(function (item) { // in case collection is not empty
            self.addItemView(item);
        }, this);
    },

    addItem: function (item) {
        this.collection.add(item);
    },

    addItemView: function (item) {
        var itemView = new UU_ItemView({
            model: item,
            $itemContainer:{}
        });
        //由于 view 是类属性
        itemView.$itemContainer = {};	
        var temp =itemView.render();
        for(var i = 0 ; i< window._langLanguage.length ; i++){
        	 $("tbody",$("#container_"+window._langLanguage[i],$(_UU_parent.document))).eq(0).append(temp.$itemContainer[window._langLanguage[i]]);
    	}
    },
    
    submit: function(){
       //window.console&&window.console.log();
      
       return JSON.stringify(this.collection);
    }
});
var collection = new UU_List();
var listView = new UU_ListView();

window.parent.Com_Parameter.event["submit"].push(function(){
    if  (Designer.instance.isMobile) {
        return true;
    }
	var fdContent = window.parent.document.getElementById("uu_FdContent");
	fdContent&&(fdContent.value = listView.submit().replace(/&quot;/g,"\\\""));
	return true;
	//return true;
	//;
	//return true;
});

function resetLangInfo(){
	var fdContent = window.parent.document.getElementById("uu_FdContent");
	fdContent&&(fdContent.value = listView.submit().replace(/&quot;/g,"\\\""));
}
//============================================================
//mvvm 框架搭建 结束
//============================================================

/**
 * 添加回调函数工具类,针对没有参数的方法注入
 * @param past 过去的方法
 * @param now 现在的方法
 * @param globel 作用域		（可缺省）
 * @param local now 方法的作用域（可缺省）
 */
function add_CallBack(past,now,globel,local){
	var temp = globel[past];
	 globel[past] = function(){
		now.call(local);
		temp.call(globel);
	}
};

/**
 * 集成 redo undo 
 * 操作类型:
 * 	resize:控件拉伸
 * 	drag:控件拖拽
 * 	create:创建控件
 * 	update:更新控件(如字体大小、属性面板属性)
 * 	destory:销毁控件
 */

DesignerUndoSupport.on('redo',function(control,type){
	//todo
	
});

DesignerUndoSupport.on('undo',function(control,type){
	//todo
	
});

/**
 * 监听 setHtml 事件
 * 由于 setHtml 会将所有的控件对象进行初始化，那么 UU_lang_arr 中间件里面绑定的
 * 控件对象已经毫无用处，所以需要重新去清空 UU_lang_arr
 */
Designer.instance.addListener("setHtml", function(){
	//备份数据,清空数据
	for(var i = 0; i< UU_lang_arr.length; ++i){
		// _UU_lang_arr_models 缓存原始控件的 id 和  model 键值对
		
		if(UU_lang_arr[i].model !== undefined){
			
			var model = UU_lang_arr[i].model;
			
			// model 临时数据
			_UU_lang_arr_models[model.get("c_id")] = $.extend(true,{},model.attributes);
			// 销毁中间件
			//兼容多表单
			if(typeof Designer.instance.parentWindow.Form_getModeValue == "undefined" || Designer.instance.parentWindow.Form_getModeValue(Designer.instance.fdKey)!=Designer.instance.template_subform){
				model.destroy();
			}
		}
	}
	//其实是之前自己对于中间件里面绑定容器内控件，无法移除，防止出现BUG，先这么写。
	//兼容多表单
	if(typeof Designer.instance.parentWindow.Form_getModeValue == "undefined" || Designer.instance.parentWindow.Form_getModeValue(Designer.instance.fdKey)!=Designer.instance.template_subform){
		UU_lang_arr = [];
	}
});
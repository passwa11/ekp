define([
    "dojo/_base/declare",
    "dijit/_WidgetBase",
    "dijit/registry",
    "dojo/dom-construct",
    "dojo/dom-class",
    "dojo/dom-style",
    "dojo/topic",
    "dojo/request",
    "dojo/query",
    "dojo/_base/array",
    "mui/util",
    "mui/dialog/Dialog",
    "mui/dialog/Tip",
    "mui/i18n/i18n!sys-lbpmperson:lbpmperson.op.fastreview",
    "mui/i18n/i18n!sys-lbpmperson"
	], function(declare, WidgetBase, registry, domConstruct, domClass, domStyle , topic, request, query, array, util, Dialog, Tip, lbpmpersonMsg, msg) {
	
	return declare("sys.lbpmperson.mobile.FastReviewTodoHead", [WidgetBase], {
		
		selArr:[],
		
		tmp:'<div id="scroll_AllReview" class="muiAllReview">'
			+'   <div class="muiAllReviewReturn"><span><i class="fontmuis muis-to-left"></i>'+msg['mui.lbpmperson.fastreview.return']+'</span></div>'
			+'   <div class="muiAllReviewUsagesRow">'
			+'      <div class="muiAllReviewTitleNode">'+msg['mui.lbpmperson.fastreview.auditnote']+'</div>'
			+'		<div id="descriptionDiv">'
			+'			<div id="fdUsageContent" data-dojo-type=\'mui/form/Textarea\' '
			+'				data-dojo-props="\'subject\':\''+msg['mui.lbpmperson.fastreview.auditnote']+'\',\'placeholder\':\''+msg['mui.lbpmperson.fastreview.interAuditnote']+'\',\'name\':\'fdUsageContent\',opt:false,required:true" alertText="" key="auditNode">'
			+'			</div>'
			+'	 	</div>'
			+'	 	<div class="commonUsagesDiv">'
			+'			<div class="handingWay" id="commonUsages"><div class="iconArea"><i class="mui"></i></div><span class="iconTitle">'+msg['mui.lbpmperson.fastreview.commonUsage']+'</span></div>'
			+'		</div>'
			+'	 </div>'
			+'	 <div data-dojo-type="mui/tabbar/TabBar" fixed="bottom">'
			+'	 	<li data-dojo-type="sys/lbpmperson/mobile/resource/js/FastReviewTodoBtns" class="muiBtnDefault freeflowChartBack freeflowChartBackButton"'
			+'			data-dojo-props="type:\'batchrefuse\'">'
			+'			'+msg['mui.lbpmperson.fastreview.refuseOrBackAll']
			+'		</li>'
			+'		<li data-dojo-type="sys/lbpmperson/mobile/resource/js/FastReviewTodoBtns" class="muiBtnDefault freeflowChartBack freeflowChartBackButton"'
			+'			data-dojo-props="type:\'batchpass\'">'
			+'			'+msg['mui.lbpmperson.fastreview.passAll']
			+'		</li>'
			+'	 </div>'
			+'</div>',
		
		buildRendering:function(){
			this.inherited(arguments);
			this.titleNode = domConstruct.create( 'div', { className : 'fastReviewTodoListTitle muiFontSizeXL muiFontColor' },this.domNode);
			this.titleNode.innerHTML = lbpmpersonMsg["lbpmperson.op.fastreview"];
			this.buildSelArea();
			this.initialCommonUsages();
		},
		buildSelArea:function(){
			this.optNode = domConstruct.create( 'div', { className : 'muiFastReviewTodoOptArea' },this.domNode);
			this.selectArea = domConstruct.create("div", {className: "muiFastReviewTodoSelAllArea"}, this.optNode);
			this.selectNode = domConstruct.create("div", {className: "muiFastReviewTodoSelAllNode"}, this.selectArea);
			this.selectBtnNode = domConstruct.create("span", {className: "muiFontSizeM muiFontColorMuted",innerHTML:msg['mui.lbpmperson.fastreview.seleteAll']}, this.selectArea);
			this.connect(this.selectArea, "click", "_selectCate");
			this.reviewNode = domConstruct.create("span", {className: "muiFastReviewTodoReviewNode muiFontSizeM",innerHTML:msg['mui.lbpmperson.fastreview.reviewAll']}, this.optNode);
			this.connect(this.reviewNode, "click", "_reviewAll");
		},
		_reviewAll: function(evt) {
			if(this.selArr.length==0){
				Tip.warn({text: msg['mui.lbpmperson.fastreview.noData'], time: 1500});
				return;
			}
	        if (evt) {
	            if (evt.stopPropagation) evt.stopPropagation()
	            if (evt.cancelBubble) evt.cancelBubble = true
	            if (evt.preventDefault) evt.preventDefault()
	            if (evt.returnValue) evt.returnValue = false
	        }
			/* 连续点击不能超过500毫秒，防止快速双击
			（click事件未知原因会连续触发两次，下面的的 nowTime、clickTime的条件判断只是为了防止一次点击多次处理，是一种变通的方式来防止双击）*/
			var nowTime = new Date().getTime();
		    var lastClickTime = this.ctime;
		    if( (lastClickTime != 'undefined' && (nowTime - lastClickTime < 500)) || this.selectCateCalling ){
		        return false;
	        } else {
	        	this.ctime = new Date().getTime();
	        	this.selectCateCalling = true;
	            
	        	this.dialogDom=domConstruct.toDom(this.tmp);
				var self = this;
				this.dialog = Dialog.element({
					destroyAfterClose:true,
					canClose : false,
					showClass : 'muiDialogElementShow fastReviewListDialog',
					element:this.dialogDom,
					position:'bottom',
					'scrollable' : false,
					'parseable' : true,
					onDrawed:function(){
						self.initUsageContent();
					}
				});
	        	
	            this.ctime = new Date().getTime();
	            this.selectCateCalling = false;
	    	    return true;
	        }
	    },
		
	    initUsageContent : function(){
	    	var textNode = registry.byId("fdUsageContent");
			textNode.set("value",msg['mui.lbpmperson.fastreview.agree']);
			this.initialCommonUsageObj("commonUsages", this.usageContents);
			var processIds = '';
			array.forEach(this.selArr,function(selItem){
				processIds += ';' + selItem.modelId;
			});
			if(processIds!=''){
				this.processIds = processIds.substring(1);
			}
			var self = this;
			query(".muiAllReviewReturn span").on("touchend", function() {
				self.defer(function() {
					if(self.dialog){
						self.dialog.hide();
					}
				}, 100);
			});
	    },
	    
	    getUsageContents : function(usageContents) {
			return array.map(usageContents, function(usageContent) {
				while (usageContent.indexOf("nbsp;") != -1) {
					usageContent = usageContent.replace("&nbsp;", " ");
				}
				usageContent = usageContent.replace(/\'/g,"\\\'").replace(/\"/g, "&quot;");
				return {text: usageContent, value: usageContent};
			});
		},
		
		temp : '<input type="checkbox" data-dojo-type="mui/form/CheckBox" name="_select_box_commonUsageObjName" value="!{value}" data-dojo-props="mul:false,text:\'!{text}\'">',

		buildItemHtml : function(props) {
			return this.temp.replace(
					'!{text}', props.text).replace(
					'!{value}', props.value);
		},
		
		buildContentHtml : function(usageContents) {
			var ucs = this.getUsageContents(usageContents);
			if (ucs.length == 0) {
				return "<p>" + msg['mui.lbpmperson.fastreview.commonUsage.none'] + "</p>";
			}
			var self = this;
			var html = array.map(ucs, function(props) {
				return self.buildItemHtml(props);
			});
			return "<div class='muiFormSelectElement'>" + html.join("") + "</div>";
		},
		
		initialCommonUsageObj : function(commonUsageObjName, usageContents) {
			var self = this;
			var dialog = null, html = this.buildContentHtml(usageContents);
			query("#" + commonUsageObjName).on("touchend", function() {
				setTimeout(function(){
					dialog = Dialog.element({
						element : html,
						showClass: 'muiDialogSelect muiFormSelect',
						position:'bottom',
						'scrollable' : false,
						'parseable' : true,
						callback: function() {
							dialog = null;
						}
					});
				},300);
			});
			topic.subscribe("mui/form/checkbox/change", function(box, data) {
				if (data.name != '_select_box_commonUsageObjName') {
					return;
				}
				if (dialog)
					dialog.hide();
				dialog = null;
				var fdUsageContent = registry.byId('fdUsageContent');
				if(self.isappend=="true"){
					fdUsageContent.set('value', fdUsageContent.get('value') + data.value.replace(/\\\'/g, "&#39;"));
				} else {
					fdUsageContent.set('value', data.value.replace(/\\\'/g, "&#39;"));
				}
			});
		},
	    
		_selectCate: function(evt) {
	        if (evt) {
	            if (evt.stopPropagation) evt.stopPropagation()
	            if (evt.cancelBubble) evt.cancelBubble = true
	            if (evt.preventDefault) evt.preventDefault()
	            if (evt.returnValue) evt.returnValue = false
	        }

			/* 连续点击不能超过500毫秒，防止快速双击
			（click事件未知原因会连续触发两次，下面的的 nowTime、clickTime的条件判断只是为了防止一次点击多次处理，是一种变通的方式来防止双击）*/
			var nowTime = new Date().getTime();
		    var lastClickTime = this.ctime;
		    if( (lastClickTime != 'undefined' && (nowTime - lastClickTime < 500)) || this.selectCateCalling ){
		        return false;
	        } else {
	        	this.ctime = new Date().getTime();
	        	this.selectCateCalling = true;
	            if (this.selectNode) {
	                //存在选择区域时设置是否选中
	                if (this.checkedIcon != null) {
	                  this._toggleSelect(false)
	                } else {
	                  this._toggleSelect(true)
	                }
	            }
	            this.ctime = new Date().getTime();
	            this.selectCateCalling = false;
	    	    return true;
	        }
	    },
		
	    _cancelSelected: function(srcObj, evt) {
    		if (this.checkedIcon) {
    			domClass.remove(this.selectNode, "muiFastReviewTodoSeledAll");
  	            domConstruct.destroy(this.checkedIcon);
  	            this.checkedIcon = null;
  	        	topic.publish("/sys/lbpmperson/CancelFastReviewTodoSelectAll", this);
    		}
	   },
	    _setSelected: function(srcObj, evt) {
    		if (this.checkedIcon) {
    			domConstruct.destroy(this.checkedIcon);
	            this.checkedIcon = null;
    		}
    		if (!domClass.contains(this.selectNode, "muiFastReviewTodoSeledAll")) {
                domClass.add(this.selectNode, "muiFastReviewTodoSeledAll");
            }
    		this.checkedIcon = domConstruct.create(
               "i",
               {
                  className: "mui mui-checked muiFastReviewTodoSelectedAll"
               },
               this.selectNode
            )
            topic.publish("/sys/lbpmperson/FastReviewTodoSelectAll", this);
	    },
	    
	    _toggleSelect: function(select) {
	        if (select) {
	          this._setSelected(this, this)
	        } else {
	          this._cancelSelected(this, this)
	        }
	    },
	    initialCommonUsages : function() {
	    	var self = this;
			var url = util.formatUrl('/sys/lbpmservice/support/lbpm_usage/lbpmUsage.do?method=getUsagesInfo');
			request.get(url, {handleAs:'text'}).then(function(responseText) {
				var names = responseText ? decodeURIComponent(responseText) : null;
				var usageContents = [];
				if (names != null && names != "") {
					self.usageContents = names.split("\n");
				}
			});
			self.isappend = "true";
			url = util.formatUrl('/sys/lbpmservice/support/lbpm_usage/lbpmUsage.do?method=getUsagesIsAppend');
			request.get(url, {handleAs:'text'}).then(function(responseText) {
				var isAppend = responseText ? responseText : null;
				if (isAppend != null && isAppend != "") {
					self.isappend = isAppend;
				}
			});
		},
		postCreate : function() {
			this.inherited(arguments);
			this.subscribe("/sys/lbpmperson/mobile/FastReviewTodo/ListItemSelected","_itemSelected");
			this.subscribe("/sys/lbpmperson/mobile/FastReviewTodo/ListItemCancelSelected","_itemCancelSelected");
			this.subscribe("/sys/lbpmperson/mobile/FastReviewTodo/reviewSuccess","_reviewSuccess");
			this.subscribe("/sys/lbpmperson/mobile/FastReviewTodo/listReloadDataSuccess","_listReloadDataSuccess");
		},
		_reviewSuccess:function(){
			if(this.dialog){
				var self = this;
				self.defer(function() {
					if(self.dialog){
						self.dialog.hide();
					}
				}, 100);
			}
			this.selArr = [];
			this.processIds = '';
		},

		/**
		 * 列表刷新加载数据成功
		 * @private
		 */
		_listReloadDataSuccess: function (evt){
			this.selArr = [];
			this.processIds = '';
			//全选按钮取消选定
			if (this.checkedIcon) {
				domClass.remove(this.selectNode, "muiFastReviewTodoSeledAll");
				domConstruct.destroy(this.checkedIcon);
				this.checkedIcon = null;
			}
		},

		_itemSelected:function(evt){
			if(evt){
				if(!this._checkInSelArr(evt.modelId)){
					this.selArr.push(evt);
				}
			}
		},
		_itemCancelSelected:function(evt){
			if(evt && evt.modelId){
				for ( var i=0;i< this.selArr.length; i++) {
					if(this.selArr[i].modelId==evt.modelId){
						this.selArr.splice(i,1);
						break;
					}
				}
			}
		},
		_checkInSelArr:function(value){
			var flag = false;
			for(var i = 0;i<this.selArr.length;i++){
				if(this.selArr[i].modelId == value){
					flag = true;
					break;
				}
			}
			return flag;
		}
	});
});
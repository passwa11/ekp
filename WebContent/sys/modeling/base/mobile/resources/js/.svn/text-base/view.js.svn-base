/**
 * 移动列表视图的视图组件
 */
define(function(require, exports, module) {
	
	var $ = require('lui/jquery'),
		base = require('lui/base'),
		str = require('lui/util/str'),
		dialog = require('lui/dialog'),
		source = require('lui/data/source'),
		render = require('lui/view/render'),
		topic = require('lui/topic'),
		orderGenerator = require('sys/modeling/base/mobile/resources/js/orderGenerator'),
		whereGenerator = require('sys/modeling/base/mobile/resources/js/whereGenerator'),
		dropdownGenerator = require('sys/modeling/base/mobile/resources/js/dropdownGenerator');
	var modelingLang = require("lang!sys-modeling-base");
	var View = base.DataView.extend({
		
		initProps: function($super, cfg) {
			$super(cfg);
			this.container = cfg.container || null;
			this.storeData = cfg.data || {};
			// 增加ID，用于移动端页签的区分
			if(this.storeData.id){
				this.id = this.storeData.id;
			}else{
				this.id = parseInt(((new Date().getTime()+Math.random())*10000)).toString(16);
			}
			this.orderCollection = [];
			this.whereCollection = [];
			this.syswhereCollection = [];
		},
		
		startup : function($super, cfg) {
			if (!this.render) {
				this.setRender(new render.Template({
					src : "/sys/modeling/base/mobile/resources/js/viewRender.html#",
					parent : this
				}));
				this.render.startup();
			}
			if (!this.source) {
				this.setSource(new source.Static({
							datas : this.storeData,
							parent : this
						}));
				this.source.startup();
			}
			topic.channel("modeling").subscribe("order.delete",this.deleteOrder, this);
			topic.channel("modeling").subscribe("where.delete",this.deleteWhere, this);
			$super(cfg);
		},
		
		draw : function($super, cfg){
			if(this.isDrawed)
				return;
			this.isDrawed = true;
			
			this.element.appendTo(this.container).addClass("panel-tab-main-view");
			this.load();
		},

		renderSubject: function(){
			var data = JSON.stringify(this.storeData.fdSubject);
			var fdSubjectCfg = {
				container:$(".fdSubjectTr", this.element),
				data:data,
				channel:this.channel,
				name:"fdSubject",
				required:"true"
			};
			this.fdSubjectGenertor = new dropdownGenerator.DropdownGenerator(fdSubjectCfg);
		},

		doRender : function($super, cfg){
			$super(cfg);
			var $header = this.parent.element.find(".panel-tab-header");
			var self = this;
			self.renderBlock();
            if (this.id == "pc") return;
			/********************* 添加事件 start *****************/
			// tab名称同步
			this.element.find("input[name*='fdName']").on("input",function(){
				var val = $(this).val() || modelingLang['modeling.Undefined'];
				$header.find("[data-wgt-id='"+ self.id +"'] .model-tab-table-slide-tag-item p").html(val);
				$header.find("[data-wgt-id='"+ self.id +"'] .model-tab-table-slide-tag-item p").attr("title",val);
			});

			this.renderSubject();

			// // 标题下拉框样式
			// var $select = this.element.find(".model-mask-panel-table-select");
			// //#124714:移动列表进入后，标题颜色和摘要保持统一
			// if($select.find("[name*='fdSubject']").val() != ""){
			// 	$select.find(".model-mask-panel-table-select-val").addClass("active");
			// }
			// $select.on("click", function (event) {
            //     event.stopPropagation();
            //     $(this).toggleClass("active");
            //     var val = $(this).find("input[name*='fdSubject']").val();
            //     if (typeof self.channel != "undefined") {
            //         topic.channel(self.channel).publish("modeling.fdSubject.click");
            //     }
            //     var validateDom = $(this).parents("td").eq(0).find("#advice-_validate_fdSubject")[0];
            //     if(!val){
            //     	//提示不能为空
            //     	if(!validateDom){
            //     		var html = getValidateHtml('标题字段','fdSubject');
            //     		$(this).parents("td").eq(0).find(".validation-advice").remove();
            //         	$(this).parents("td").eq(0).append(html);
            //     	}else{
            //     		$(validateDom).show();
            //     	}
            //     }else{
            //     	$(validateDom).hide();
            //     }
            // });
			// $select.find(".model-mask-panel-table-option div").on("click", function () {
            //     var $tableSelect = $(this).parent().parent();
            //     var $p = $tableSelect.find(".model-mask-panel-table-select-val");
            //     $p.html($(this).html());
            //
            //     var selectVal = $(this).attr("option-value");
            //     $tableSelect.find("input").val(selectVal);
            //     $select.find(".model-mask-panel-table-select-val").addClass("active");
            // });
            if (this.id == "mobile") return;
			/*if(!this.storeData.fdSubject || $.isEmptyObject(this.storeData.fdSubject)){
				// 如果没有选，默认选中第一个项
				$select.find(".model-mask-panel-table-option div").first().trigger($.Event("click"));
			}*/
			
			// 显示项/搜索项
			this.element.find(".multiSelectDialog").on("click",function(){
				// 找到当前元素的值
				topic.publish("switchSelectPosition",{'dom':$(this)[0]});
				var $dom = $(this);
				var curVal = $dom.find("input[type='hidden']").val() || "";
				
				var fieldInfos = self.getFieldInfos();
				if($dom.attr("data-lui-position") === "fdDisplay"){
					fieldInfos = self.filterSubTableField(fieldInfos);
				}

				var mobileDialogFlag = $dom.attr("data-lui-position") || "";
				var url = "/sys/modeling/base/listview/config/dialog.jsp?type=normal&mobileDialogFlag="+mobileDialogFlag;
				dialog.iframe(url,modelingLang['listview.field.set'],function(data){
					if(!data){
						return; 
					}
					//创建者选中回显
					data = data.replace("docCreator.fdName","docCreator");
					data = $.parseJSON(data);
					var selectedDatas = data.selected;
					var textDatas = data.text;
					// 补充text
					if(selectedDatas.length === textDatas.length){
						for(var i = 0;i < selectedDatas.length;i++){
							selectedDatas[i].text = textDatas[i];
						}
					}
					//回调
					// 跟pc端的列表视图页面，保持一致
					$dom.find("input[type='hidden']").val(JSON.stringify(selectedDatas));
					$dom.find("input[type='text']").val(textDatas.join(";"));
					//刷新预览
					topic.publish("preview.refresh");
					topic.publish("switchSelectPosition",{'dom':$dom[0]});
				},{
					width : 720,
					height : 530,
					params : { 
						selected : curVal,
						allField : fieldInfos
					}
				});
			});
			
			// 排序设置的添加 | 预定义查询的添加
			this.element.find(".model-data-create").on("click",function(){
				var $wheretable = $(this).closest(".model-query-cont").find(".model-edit-view-oper-content-table");
				var $table = $(this).closest("td").find(".model-edit-view-oper-content-table");
				$table.parent().show();
				$wheretable.parent().show();
				var type = $table.attr("data-table-type");
				if(type === "order"){
					var orderWgt = new orderGenerator({container:$table,parent:self});
					orderWgt.startup();
					orderWgt.draw();
					self.orderCollection.push(orderWgt);
					//刷新预览
					topic.publish("preview.refresh");
					topic.publish("data-create-finish",{"table":$table});
					return false;
				}
				var $wheretype = $wheretable.attr("name");
				var whereWgt = new whereGenerator({container:$wheretable,parent:self,wheretype:$wheretype});
				whereWgt.startup();
				whereWgt.draw();
				if($wheretype == "sys_query"){
					self.syswhereCollection.push(whereWgt);
				}else if($wheretype == "custom_query"){
					self.whereCollection.push(whereWgt);
				}
				//刷新预览
				topic.publish("preview.refresh");
				topic.publish("data-create-finish",{"table":$wheretable});
			});
			
			// 列表穿透切换
			/*var $viewSelect = this.element.find("[name*='fdViewId']");
			this.element.find("[name*='fdViewFlag']").on("change",function(){
				if($(this).prop("checked")){
					if(this.value === '1'){
						$viewSelect.show();
					}else{
						$viewSelect.hide();
					}
				}
				return false;
			}).trigger($.Event("change"));*/
			/********************* 添加事件 end *****************/
			topic.publish("view_load_finish");
		},
		
		/*过滤明细表的字段*/
		filterSubTableField : function(allField){
			allField = allField || [];
			var newAllField = [];
			for(var i=0; i<allField.length; i++){
				var field = allField[i];
				if(field.isSubTableField){
					continue;
				}
				newAllField.push(field);
			}
			return newAllField;
		},
		
		// 渲染排序和预定义查询
		renderBlock : function(){
			var renderData = this.storeData;
			var $table = this.element.find("[data-table-type='order']");
			if(renderData.fdOrderBy && renderData.fdOrderBy.length){
				if(renderData.fdOrderBy.length > 0){
					$table.parent().show();
				}
				for(var i = 0;i < renderData.fdOrderBy.length;i++){
					var orderInfo = renderData.fdOrderBy[i];
					var orderWgt = new orderGenerator({container:$table ,parent:this,data:orderInfo});
					orderWgt.startup();
					orderWgt.draw();
					this.orderCollection.push(orderWgt);
				}				
			}
			
			if(renderData.fdWhereBlock && renderData.fdWhereBlock.length){
				$table = this.element.find("[data-table-type='where']");
				if(renderData.fdWhereBlock.length > 0){
					$table.parent().show();
				}
				for(var i = 0;i < renderData.fdWhereBlock.length;i++){
					var whereInfo = renderData.fdWhereBlock[i];
					var whereWgt = null;
					if(whereInfo.whereType == "0"){
						//自定义查询
						$table = this.element.find("[name='custom_query']");
						whereWgt = new whereGenerator({container:$table ,parent:this,data:whereInfo,wheretype:"custom_query"});
						whereWgt.startup();
						whereWgt.draw();
						this.whereCollection.push(whereWgt);
					}else if(whereInfo.whereType == "1"){
						//内置查询
						$table = this.element.find("[name='sys_query']");
						whereWgt = new whereGenerator({container:$table ,parent:this,data:whereInfo,wheretype:"sys_query"});
						whereWgt.startup();
						whereWgt.draw();
						this.syswhereCollection.push(whereWgt);
					}
				}
			}
		},
		
		// argu : {wgt:xxx,dom:xxx}
		deleteOrder : function(argu){
			for(var i = 0;i < this.orderCollection.length;i++){
				if(argu.wgt === this.orderCollection[i]){
					this.orderCollection.splice(i,1);
					break;
				}
			}
		},
		
		deleteWhere : function(argu){
			if(argu.name == "sys_query"){
				for(var i = 0;i < this.syswhereCollection.length;i++){
					if(argu.wgt === this.syswhereCollection[i]){
						this.syswhereCollection.splice(i,1);
						break;
					}
				}
			}else{
				for(var i = 0;i < this.whereCollection.length;i++){
					if(argu.wgt === this.whereCollection[i]){
						this.whereCollection.splice(i,1);
						break;
					}
				}
			}
		},
		//移动组件
		moveWgt: function(type,srcIndex,targetIndex){
			if(type == "orderby"){
				var orderCollection = this.orderCollection;
				var srcWgt = orderCollection[srcIndex];
				var targetWgt = orderCollection[targetIndex];
				orderCollection[srcIndex] = targetWgt;
				orderCollection[targetIndex] = srcWgt;
			}
		},
		
		// listviewOption在外面定义的全局变量
		getFieldInfos : function(){
			return listviewOption.baseInfo.fieldInfos;
		},
		
		getKeyData : function(){
			var keyData = {};
			keyData.id = this.id;
			
			keyData.fdName = str.encodeHTML(this.element.find("input[name*='fdName']").val()).replace(/'/g, "&apos;");
			
			keyData.fdSubject = {};
			var $subject = this.element.find("[name*='fdSubject']");
			var $divOption = $subject.closest(".model-mask-panel-table-select").find("[option-value='"+ $subject.val() +"']");
			keyData.fdSubject.text = $divOption.html();
			keyData.fdSubject.field = $subject.val();
			keyData.fdSubject.type = $divOption.attr("data-field-type");
			
			keyData.fdSummary =  $.parseJSON(this.element.find("input[name*='fdSummary']").val() || "[]");
			keyData.fdSummaryText = this.element.find("input[name*='fdSummaryText']").val();
			
			keyData.fdMobileWhereType = this.element.find("[name*='fdMobileWhereType']:checked").val();
			keyData.fdMobileInWhereType = this.element.find("[name*='fdMobileInWhereType']:checked").val();
			
			// 显示项由标题字段和摘要显示项组成
			var fdDisplay = [];
			$.extend(fdDisplay, keyData.fdSummary);
			var hasSubject = false;
			for(var i = 0;i < fdDisplay.length;i++){
				if(fdDisplay[i].field === keyData.fdSubject.field){
					hasSubject = true;
					fdDisplay[i].busType = "subject";	// 业务类型
				}else{
					fdDisplay[i].busType = "summary";					
				}
			}
			if(!hasSubject){
				fdDisplay.push($.extend({busType:"subject"},keyData.fdSubject));	
			}
			keyData.fdDisplay = fdDisplay;
			
			keyData.fdCondition =  $.parseJSON(this.element.find("input[name*='fdCondition']").val() || "[]");
			var textArr =[];
			for (var i = 0;i<keyData.fdCondition.length;i++){
				var fdCondition = keyData.fdCondition[i];
				textArr[i] = fdCondition.text;
			}
			var text =  textArr.join(";");
			this.element.find("input[name*='fdConditionText']").val(text);
			keyData.fdConditionText = this.element.find("input[name*='fdConditionText']").val() || "0";
			
			keyData.fdOrderBy = [];
			for(var i = 0;i < this.orderCollection.length;i++){
				var orderWgt = this.orderCollection[i];
				keyData.fdOrderBy.push(orderWgt.getKeyData());
			}
			
			keyData.fdWhereBlock = [];
			for(var i = 0;i < this.whereCollection.length;i++){
				var whereWgt = this.whereCollection[i];
				keyData.fdWhereBlock.push(whereWgt.getKeyData());
			}
			for(var i = 0;i < this.syswhereCollection.length;i++){
				var whereWgt = this.syswhereCollection[i];
				keyData.fdWhereBlock.push(whereWgt.getKeyData());
			}
			var fdViewId = "";
			keyData.fdViewFlag = this.element.find("input[name*='fdViewFlag']").val();
			if(keyData.fdViewFlag === '1'){
				fdViewId = this.element.find("[name*='fdViewId']").val();				
			}
			keyData.fdViewId = fdViewId;

			// 是否权限过滤
			keyData.fdAuthEnabled = this.element.find("input[name='fdAuthEnabled']").val();
			keyData.fdSummaryTitleShow = this.element.find("input[name*='fdSummaryTitleShow']").val();
			// 可使用者
			keyData.authSearchReaderIds = this.element.find("input[name*='authSearchReaderIds']").val() || "";
			keyData.authSearchReaderNames = this.element.find("input[name*='authSearchReaderNames']").val() || "";
			return keyData;
		},
		getModelingLang :function (){
			return modelingLang;
		}
	});
	
	exports.View = View;
		
})
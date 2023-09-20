define( [ "dojo/_base/declare","dojo/_base/lang", "dojo/query", "dojo/dom-construct", "sys/unit/mobile/selectdialog/_DialogCategoryBase"],
		function(declare, lang, query, domConstruct, _CategoryBase) {
				window.SYS_CATEGORY_TYPE_CATEGORY = 0; //"CATEGORY" 类别
				
				window.SYS_CATEGORY_TYPE_TEMPLATE = 1; //"TEMPLATE" 模板
	
				window.SYS_CATEGORY_TYPE_DOCUMENT = 2;//"DOC" 文档
	
				var addressMixin = declare("mui.selectdialog.DialogMixin", null, {
				//列表数据获取URL
				listDataUrl: null,
				//详细数据获取url
				detailUrl:null,
				//搜索获取URL
				searchDataUrl :null,
				//字段参数
				fieldParam:null,
				 
				showGroup:true,

				showAllGroup:false,
				// 发文单位id
				fdUnitId:null,

                // 是否展示全部交换单位（对接单位设置中的单位不展示）
                allUnitByDec: false,
				
				showCate:false,//显示所有分类
				
				isMul : false,

				templURL : "sys/unit/mobile/selectdialog/dialog_sgl.jsp" ,
				
				title:"",

				buildRendering : function() {
					this.inherited(arguments);
				},

				buildOptIcon : function(optContainer) {
					domConstruct.create("i", {
						className : 'mui mui-address'
					}, optContainer);
				},
				
				_setIsMulAttr:function(mul){
					this._set('isMul' , mul);
					if(this.isMul){
						this.templURL =  "sys/unit/mobile/selectdialog/dialog_mul.jsp?showGroup=!{showGroup}&showAllGroup=!{showAllGroup}&showCate=!{showCate}&fdUnitId=!{fdUnitId}&allUnitByDec=!{allUnitByDec}";
					}else{
						this.templURL =  "sys/unit/mobile/selectdialog/dialog_sgl.jsp?showGroup=!{showGroup}&showAllGroup=!{showAllGroup}&showCate=!{showCate}&fdUnitId=!{fdUnitId}&allUnitByDec=!{allUnitByDec}";
					}
				}
			});
			var exports = {
					address : function(mulSelect, idField, nameField, selectType,action) {
						var addressObj = new _CategoryBase();
						addressObj.isMul = mulSelect == true ? true : false;
						addressObj.templURL = (mulSelect == true ? "sys/unit/mobile/selectdialog/dialog_mul.jsp?showGroup=!{showGroup}&showAllGroup=!{showAllGroup}&showCate=!{showCate}&fdUnitId=!{fdUnitId}&allUnitByDec=!{allUnitByDec}"
								: "sys/unit/mobile/selectdialog/dialog_sgl.jsp?showGroup=!{showGroup}&showAllGroup=!{showAllGroup}&showCate=!{showCate}&fdUnitId=!{fdUnitId}&allUnitByDec=!{allUnitByDec}");
						addressObj.key = idField;
						addressObj.type = window.SYS_CATEGORY_TYPE_DOCUMENT;
						addressObj.listDataUrl = this.listDataUrl;
						addressObj.searchDataUrl = this.searchDataUrl;
						addressObj.detailUrl = this.detailUrl;
						addressObj.title = this.title;
						addressObj.fieldParam = this.fieldParam;
						var idObj = query("[name='" + idField + "']")[0];
						var nameObj = query("[name='" + nameField + "']")[0];
						addressObj.curIds = idObj.value;
						addressObj.curNames = nameObj.value;
						addressObj.afterSelect = function(obj) {
							idObj.value = obj.curIds;
							nameObj.value = obj.curNames;
							if (action) {
								action(obj);
							}
						};
						addressObj.eventBind();
						addressObj._selectCate();
					}
				};
		return lang.mixin(addressMixin, exports);
	});
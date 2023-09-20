define(function(require, exports, module) {
			var lang = require('lang!sys-ui');
			var select_panel = require('lui/criteria/select_panel');
			var render = require('lui/view/render');
			var source = require('lui/data/source');
			var $ = require('lui/jquery');
			var ajaxurl=Com_Parameter.ContextPath+'fssc/budget/fssc_budget_data/fsscBudgetData.do?method=customregion';
			//ADD BY WUZB 客户台账 省市二级联动 筛选器 
			var CriterionInputDatas = select_panel.CriterionInputDatas;
			var ProvinceAndCitySelector = CriterionInputDatas.extend({
					initProps : function($super, cfg) {
						$super(cfg);
						this.placeholder = cfg.placeholder;
					},
					supportMulti: function() {
						return false;
					},
					startup : function($super) {
						if (this.isStartup) {
							return;
						}
						if (!this.render) {
							this.setRender(new render.Template({
										src : require.resolve('./criteria_area_render.jsp#'),
										parent : this
									}));
							this.render.startup();
						}
						if (!this.source) {
							this.setSource(new source.Static({
										datas : [{
													'placeholder' : this.placeholder
												}],
										parent : this
									}));
							if(this.source.startup)
								this.source.startup();
						}
						$super();
					},
					onChanged : function(evt) {
						if(evt.values.length == 0){
							this.selectedValues.removeAll();
							this.createCountryOptions();
							this.createProvinceOptions('');
							this.createCityOptions('');
							this.hideValMsg();
							this.element.find('.commit-action').hide();
						}
					},
					doRender : function($super, html) {
						$super(html);
						var self = this;
						self.initProperties();
						self.createCountryOptions();
						self.createProvinceOptions('');
						self.createCityOptions('');
						
						this.element.find('.commit-action').bind('click', function(evt) {
							self.onClicked(evt);
						});
						
						LUI.placeholder(this.element);
						
						var defaultValue=self.getDefaultValue();
						if(typeof(defaultValue) != "undefined"){
							var strs= new Array();
							strs= defaultValue[0].split("、");
							
							$(".select-province option").each(function() {
								if ($(this).val() == strs[0]) {  
					                $(this).attr("selected", "selected");  
					            }
					        }); 
							
							if(!(strs[0] == null || strs[0] == '' || strs[0] == undefined)){
								self.createCityOptions(strs[0]);
								
								$(".select-city option").each(function() {
									if ($(this).val() == strs[1]) {  
						                $(this).attr("selected", "selected");  
						            }
						        }); 
							}
						}
					},
					onClicked: function(evt) {
						if(this.validate()){
							this.addValue();
							return true;
						}
						return false;
					},
					validate: function(){
						var country = this.element.find('.customregion-select-country').val(); 
						if(country == null || country == '' || country == undefined){
							this.showValMsg();
							return false;
						}
						this.hideValMsg();
						return true;
					},
					addValue: function() {
						var countrySelectValue = $(".customregion-select-country option:selected"); 
						var provinceSelectValue = $(".customregion-select-province option:selected");
//						var provinceSelectValue = this.element.find('.customregion-select-province').val();
						var fdParentId=countrySelectValue.val();
						var fdParentName=countrySelectValue.text();
						if (!countrySelectValue) {
							this.selectedValues.removeAll();
							return;
						}
						if(provinceSelectValue.val()){
							fdParentId=provinceSelectValue.val();
							fdParentName=provinceSelectValue.text();
						}
                         if(fdParentId){
                        	 LUI('fdCostCenter-id').source.setUrl("/sys/common/dataxml.jsp?s_bean=eopBasedataCostCenterService&flag=notGroup&fdGroupId="+fdParentId);
                             LUI('fdCostCenter-id').source.resolveUrl();
                             LUI('fdCostCenter-id').refresh();
                         }else{
                         LUI('fdCostCenter-id').source.setUrl("#");
                         LUI('fdCostCenter-id').source.resolveUrl();
                         LUI('fdCostCenter-id').refresh();
                         }
						this.selectedValues.setAll([{text:fdParentName,value:fdParentId}]);
					},
					showValMsg: function() {
						this.element.find('.lui_criteria_number_validate_container').show().find('.text').text(this.config.message);
					},
					hideValMsg: function() {
						this.element.find('.lui_criteria_number_validate_container').hide();
					},
					getParamsFromHash: function() {
						var localHash = location.hash;
						var re = new RegExp();
						var criMap = {};
						// 默认获取cri.q中的参数对
						var key = encodeURIComponent('cri.q');
						re.compile("[\\#&]"+key+"=([^&]*)", "i");
						var value = re.exec(localHash);
						if (value != null){
							var hashStr = decodeURIComponent(value[1]);
							var hashArray = hashStr.split(';');
							for (var i = 0; i < hashArray.length; i++) {
								var p = hashArray[i].split(':');
								var val = criMap[p[0]];
								if (val == null) {
									val = [];
									criMap[p[0]] = val;
								}
								val.push(p[1]);
							}
						}
						return criMap;
					},
					getDefaultValue: function()
					{
					   var self = this;
					   var key = encodeURIComponent(self.selectedValues.key);
					   var params = this.getParamsFromHash();
					   var value = params[key];
					   if(value == undefined || value == "" || value == null) return;
					   return value; 
					},
					properties: {
						defaultCountryElement:null,
						defaultProvinceElement:null,
						defaultCityElement:null,
					},
					initProperties : function(){
						var sourceUrl = this.config.sourceUrl;
						var defaultCountry = this.config.defaultCountry;
						var defaultProvince = this.config.defaultProvince;
						var defaultCity = this.config.defaultCity;
						
						this.defaultCountryElement=$("<OPTION value=''>"+defaultCountry+"</OPTION>");
						this.defaultProvinceElement = $("<OPTION value=''>"+defaultProvince+"</OPTION>");
						this.defaultCityElement =  $("<OPTION value=''>"+defaultCity+"</OPTION>");
						//this.defaultCountryElement=$("<OPTION value=''>===请选择国家===</OPTION>");
						//this.defaultProvinceElement = $("<OPTION value=''>===请选择省===</OPTION>");
						//this.defaultCityElement =  $("<OPTION value=''>===请选择市===</OPTION>");
					},
					ajaxAction: function(type,parentid){
						
					},
					createCountryOptions: function(){
						var self = this;
						var countrySelectElem = this.element.find('.customregion-select-country');
						var provinceSelectElem = this.element.find('.customregion-select-province');
						var citySelectElem = this.element.find('.customregion-select-city');
						
						countrySelectElem.empty();
						provinceSelectElem.empty();
						citySelectElem.empty();
						countrySelectElem.unbind('change');
						provinceSelectElem.unbind('change');
						citySelectElem.unbind('change');
						
						countrySelectElem.append(this.defaultCountryElement.clone());
						provinceSelectElem.append(this.defaultProvinceElement.clone());
						citySelectElem.append(this.defaultCityElement.clone());
						$.ajax({
							url: ajaxurl+'&type=Country',
							type: 'POST',
							dataType: 'json',
							success: function(data) {
								var isok=data.isok;
								var data=data.data;
								if(isok){
									var mydatas=eval(data);
									for(var i=0;i<mydatas.length;i++){  
										   var optionEle = $("<OPTION id="+mydatas[i].fdId+" value="+mydatas[i].fdvalue+" parentId="+mydatas[i].fdParentId+">"+mydatas[i].fdvaluetext+"</OPTION>");
										   countrySelectElem.append(optionEle);
									} 
								}
							},
							error: function(xhr, textStatus, errorThrown) {
							}
						});
						
						countrySelectElem.bind('change',function(){
							var selindex=this.selectedIndex;
							var selectValue=this.options[selindex].id;
							self.createProvinceOptions(selectValue);
							if(selectValue == null || selectValue == "" || selectValue == undefined){
								self.hideValMsg();
							}else{
								self.hideValMsg();
								self.element.find('.commit-action').show();
							}
						});
					},
					createProvinceOptions: function(countryID){
						var self = this;
						var provinceSelectElem = this.element.find('.customregion-select-province');
						var citySelectElem = this.element.find('.customregion-select-city');
						
						provinceSelectElem.empty();
						citySelectElem.empty();
						provinceSelectElem.unbind('change');
						citySelectElem.unbind('change');
						
						provinceSelectElem.append(this.defaultProvinceElement.clone());
						citySelectElem.append(this.defaultCityElement.clone());
						
						if(countryID == null || countryID == "" || countryID == undefined) return;
						
						$.ajax({
							url: ajaxurl+'&type=Province&parentid='+countryID,
							type: 'POST',
							dataType: 'json',
							success: function(data) {
								var isok=data.isok;
								var data=data.data;
								if(isok){
									var mydatas=eval(data);
									for(var i=0;i<mydatas.length;i++){  
										   var optionEle = $("<OPTION id="+mydatas[i].fdId+" value="+mydatas[i].fdvalue+" parentId="+mydatas[i].fdParentId+">"+mydatas[i].fdvaluetext+"</OPTION>");
										   provinceSelectElem.append(optionEle);
									} 
								}
							},
							error: function(xhr, textStatus, errorThrown) {
							}
						});
						
						provinceSelectElem.bind('change',function(){
							var selindex=this.selectedIndex;
							var selectValue=this.options[selindex].id;
							self.createCityOptions(selectValue);
							if(selectValue == null || selectValue == "" || selectValue == undefined){
								self.hideValMsg();
							}else{
								self.hideValMsg();
								self.element.find('.commit-action').show();
							}
						});
					},
					createCityOptions: function(provinceID){
						var self = this;
						var citySelectElem = this.element.find('.customregion-select-city');
						citySelectElem.empty();
						citySelectElem.unbind('change');
						citySelectElem.append(this.defaultCityElement.clone());
						
						if(provinceID == null || provinceID == "" || provinceID == undefined) return;
						
						$.ajax({
							url: ajaxurl+'&type=City&parentid='+provinceID,
							type: 'POST',
							dataType: 'json',
							success: function(data) {
								var isok=data.isok;
								var data=data.data;
								if(isok){
									var mydatas=eval(data);
									for(var i=0;i<mydatas.length;i++){  
										   var optionEle = $("<OPTION id="+mydatas[i].fdId+" value="+mydatas[i].fdvalue+" parentId="+mydatas[i].fdParentId+">"+mydatas[i].fdvaluetext+"</OPTION>");
										   citySelectElem.append(optionEle);
									} 
								}
							},
							error: function(xhr, textStatus, errorThrown) {
							}
						});
						
						citySelectElem.bind('change',function(){
							self.hideValMsg();
							self.element.find('.commit-action').show();
						});
					}
				});
			function ajaxActionLoad(type,parentid){
				alert(111);
			}
			exports.ProvinceAndCitySelector = ProvinceAndCitySelector;
});
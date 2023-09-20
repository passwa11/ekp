define([ 'dojo/_base/declare', 'dojo/_base/config', 'dojo/_base/array', 'dojo/request'],
	function(declare, config, array, request) {
		return declare('km.imeeting.maxhub.TemplateRadioMixin', null, {
		
			getOptions: function(cb) {
				
				request(config.baseUrl + 'sys/category/criteria/sysCategoryCriteria.do?method=criteria&' + 
						'showTemp=false&modelName=com.landray.kmss.km.imeeting.model.KmImeetingTemplate&' + 
						'parentId=&pAdmin=&nodeType=&authType=2&_all=true&s_ajax=true', {
					method: 'get',
					handleAs: 'json'
				}).then(function(res){
					
					var data = res ? (res.datas || []) : [];
					
					cb([{
						text: '不限',
						value: ''
					}].concat(data));
					
				}, function(err) {
					cb([{
						text: '不限',
						value: ''
					}]);
				});

			}
			
		});
	}
);
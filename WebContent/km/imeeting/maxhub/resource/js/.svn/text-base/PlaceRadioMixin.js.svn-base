define([ 'dojo/_base/declare', 'dojo/_base/config', 'dojo/_base/array', 'dojo/request'],
	function(declare, config, array, request) {
		return declare('km.imeeting.maxhub.PlaceRadioMixin', null, {
			
			getOptions: function(cb) {
				
				request(config.baseUrl + 'sys/simplecategory/criteria/sysSimpleCategoryCriteria.do?method=criteria&' + 
						'modelName=com.landray.kmss.km.imeeting.model.KmImeetingResCategory&' + 
						'parentId=&pAdmin=&authType=2&s_ajax=true', {
					method: 'get',
					handleAs: 'json'
				}).then(function(res){
					cb([{
						text: '不限',
						value: ''
					}].concat(res || []));
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
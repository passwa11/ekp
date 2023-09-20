define(
		[ "dojo/_base/declare"],
		function(declare) {
			return declare("hr.list.util",null,{
				_createItemProperties: function(/*Object*/item) {
					var props = this.inherited(arguments);
					props['personInfoId'] = Com_GetUrlParameter(this.url,"personInfoId");
					props['templateString'] = this.itemTemplateString;
					return props;
				}
			});
		});
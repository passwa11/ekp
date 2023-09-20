define([ "dojo/_base/declare", "mui/list/_TemplateItemListMixin",
		"./item/SummaryItemMixin", "dojo/dom-class" ], function(declare,
		_TemplateItemListMixin, SummaryItemMixin, domClass) {

	return declare("sys.mportal.SummaryListMixin", [ _TemplateItemListMixin ],
			{
				itemRenderer : SummaryItemMixin,

				buildRendering : function() {

					this.inherited(arguments);

					domClass.add(this.domNode, 'muiDripList muiDripTeachList')

				},

				createListItem : function(item) {

					item.forward = this.forward;

					return this.inherited(arguments);

				},

			});
});
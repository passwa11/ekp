define(
["dojo/_base/declare",
"dojo/dom-construct",
"dojo/dom-style",
"dojo/dom-class",
"dijit/_WidgetBase",
"dojo/dom-geometry",
"mui/util",
"dojo/query",
"dojo/_base/lang"],
function(declare, domConstruct, domStyle, domClass, widgetBase, domGeometry, util, query,
		lang, toggleMixin) {
	/** 节点 */
	return declare(
			'sys.lbpmservice.ding.audit.note.nodecontainer',
			[ widgetBase ],{

				baseClass : 'muiDingAuditNodeItem',

				store : '',
				
				_setStoreAttr : function(store) {
					for ( var key in store) {
						if (typeof (this[key]) != 'undefined')
							this.set(key, store[key]);
					}
				},

				startup : function() {
					this.inherited(arguments);
				},

				buildRendering : function() {
					this.domNode = this.containerNode = this.srcNodeRef;
					if (this.store.status) {
						this.baseClass += " " + this.store.status;
					}
					if (this.store.fdActionKey === "_pocess_end") {
						this.baseClass += " auditLastNodeItem";
					}
					this.inherited(arguments);
				}
			});
});
define( [ "dojo/_base/declare", "mui/header/HeaderItem", "mui/folder/_Folder",
		"sys/notify/mobile/resource/js/search/SearchBarDialogMixin" ], function(declare, HeaderItem, Folder,
		SearchBarDialogMixin) {

	return declare("notify.search.SearchButtonBar",
			[ HeaderItem, Folder, SearchBarDialogMixin ], {

				icon : "mui mui-search",

				baseClass : "muiSearchButton"

			});
});

define(["dojo/_base/declare", "sys/mportal/module/mobile/containers/header/CardMixin", "mui/i18n/i18n!km-archives:py"], 
function(declare, CardMixin, msg) {
	return declare("", [CardMixin], {
		datas:{
			menus: [
				[
					{
						text: msg['py.WoDeJieYue'], 
						countUrl:"/km/archives/km_archives_details/kmArchivesDetails.do?method=data&q.mydoc=all&rowsize=1", 
						countPath:"page.totalSize", 
						href: "/km/archives/mobile/borrow/"
					},
					{
						text: msg['py.WoLuRuDe'], 
						countUrl:"/km/archives/km_archives_main/kmArchivesMain.do?method=data&q.mydoc=create&rowsize=1", 
						countPath:"page.totalSize", 
						href: "/km/archives/mobile/main/create/"
					},
					{
						text: msg['py.DangAnKu'], 
						countUrl:"/km/archives/km_archives_main/kmArchivesMain.do?method=data&q.mydoc=all&rowsize=1", 
						countPath:"page.totalSize", 
						href: "/km/archives/mobile/main/kStatus/"
					}
				]
			]
		}
	})
})

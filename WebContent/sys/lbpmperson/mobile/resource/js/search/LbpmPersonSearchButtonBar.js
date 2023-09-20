define(
		["dojo/_base/declare","dojo/_base/lang","mui/util","sys/lbpmperson/mobile/resource/js/search/LbpmPersonSearchDbBar","mui/search/SearchButtonBar"],
		function(declare,lang,util,LbpmPersonSearchDbBar,SearchButtonBar) {
			return declare("sys.lbpmperson.mobile.list.LbpmPersonSearchButtonBar",[SearchButtonBar],{
				chooseSearch: function(vars) {
			      if (vars.searchType == "db") {
			    	  //数据库搜索
			    	  return new LbpmPersonSearchDbBar(vars)
			      } else {
			    	  this.inherited(arguments);
			      }
			    },
			});
		});

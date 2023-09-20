define(
		[ "dojo/_base/declare" ],
		function(declare) {
			var claz = declare(
					"sys.zone.AddressSeesionStorage", null, {

						setStorage : function(key, value) {
							if(window.sessionStorage) {
								var vstr = "";
								if(typeof value === "object") {
									vstr = JSON.stringify(value);
								}
								if(vstr) {
									try {
										window.sessionStorage.setItem(key, vstr);
									}catch (e) {}
								}
							}
						},
						
						getStorage : function(key) {
							if(window.sessionStorage) {
								var strv = window.sessionStorage.getItem(key), rtnobj = null;
								try{
									rtnobj = JSON.parse(strv);
								} catch(e) {}
								
								return (rtnobj ? rtnobj : strv);
							}
						},
						
						removeStorage :  function(key) {
							if(window.sessionStorage) {
								strv = window.sessionStorage.removeItem(key);
							}
						}
					});
			
			return new claz();
		});			
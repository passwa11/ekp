define( [ "dojo/_base/declare", "dojo/_base/lang", 
          "mui/address/AddressList", "dojo/topic", "dojo/dom-construct"], function(declare, lang,
        		  AddressList, topic, domConstruct) {
	return declare("mui.zone.address.AddressZoneList", [ AddressList ], {
		
	    postCreate: function() {
	        this.inherited(arguments)
            this.subscribe("/mui/search/submit","_searchKeyWordChange");
	    },

		onComplete : function(items) {
			if(this.tmpLoading){
				domConstruct.destroy(this.tmpLoading);
				this.tmpLoading = null;
			}
			this.busy = false;
			var list = this.resolveItems(items);
			var length = list.length;
			var isFirstPerson = true; 
			var hasHeader = false;
			for(var i=0;i<length;i++){
				var item = list[i];
				if(item["header"]&&item["header"]=="true"){
					hasHeader = true;
				} 
				if(item.type==8){ 
					// 如果没有类型标题头(搜索时)且含有机构数据的情况下，人员第一条数据前面需要添加一根与机构之间的分隔线
					if( hasHeader==false && i!=0 && isFirstPerson ){
						list.splice(i, 0, {"showDividerLine":true});	
					}
					isFirstPerson = false;
				}
			}
			var groupn = 50;
			if(length > groupn ) {
				this.append = true;
				this.delayAppend(items, list, groupn);
			} else {
				this.append = false;
				this.generateList(list);
				topic.publish('/mui/list/loaded', this, items);
			}
		},
		
		delayAppend : function(items , list, groupn) {
			//分批渲染，可以尽快看到数据
			var length = list.length,
				step = Math.ceil(length / groupn),
				i = 0, self = this;
			var append =  function() {
				var end = (i + 1) * groupn > length - 1 ? length : (i + 1) * groupn;
				var  tmpList = list.slice(i * groupn, end);
				self.generateList(tmpList);
				if(end === length) {
					topic.publish('/mui/list/loaded', self, items);
				}
				i++;
				if(i < step) {
					setTimeout(append , 1);
				}
			};
			
			append();
		}, 
		
		_searchKeyWordChange: function(srcObj, evt) {
	        if (srcObj.key == this.key) {
	          if (evt && evt.url) {
	            if (!this._addressUrl) {
	                this._addressUrl = this.url
	            }
	            this.showMore = false
	            this.url = evt.url
	          }
	          this.buildLoading()
	          this.reload()
	        }
	    }
		
	});
});
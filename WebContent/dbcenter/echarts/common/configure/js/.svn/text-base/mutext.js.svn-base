/**
 * 互斥元素 
 */
(function(){
	
	function Mutext(element){
		this.element = $(element);
	}
	
	Mutext.prototype.hide = function(){
		var $this = this.element;
		var pair = $this.data("pair-id");
		var $pairGroup = $(document).find("[data-pair-id='"+pair+"']");
		$pairGroup.each(function(){
			if(this === $this[0]){
				$this.hide();
			}else{
				$(this).show();
			}
		})
	}
	
	 function Plugin(option) {
		    return this.each(function () {
		      var $this = $(this);
		      var data  = $this.data('db.mutext');

		      if (!data){ $this.data('db.mutext', (data = new Mutext(this)));}
		      if (typeof option == 'string'){
		    	  data[option]();
		      }
		    })
		  }

	  var old = $.fn.mutext

	  $.fn.mutext             = Plugin;
	  $.fn.mutext.Constructor = Mutext;


	  // mutext NO CONFLICT
	  // ===============

	  $.fn.mutext.noConflict = function () {
	    $.fn.mutext = old;
	    return this;
	  }


	  // mutext DATA-API
	  // ============

	  var clickHandler = function (e) {
	    e.preventDefault();
	    Plugin.call($(this), 'hide');
	  };
	  
	  $(document).on('click.db.mutext.data-api', '[data-pair]', clickHandler);
	
})()
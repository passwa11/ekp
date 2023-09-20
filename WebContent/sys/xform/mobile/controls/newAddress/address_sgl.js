define(["./address_common"], function(common) {
  function tmplLoad(params, load) {
    var html = common.html({
    	mul: false
    });
    load(html)
  }

  return {
    load: function(params, require, load) {
      tmplLoad(params, load)
    }
  }
})

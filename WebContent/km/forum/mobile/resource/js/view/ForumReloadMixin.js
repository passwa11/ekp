define(["dojo/_base/declare"], function (declare) {
  return declare("km.forum.view.ForumReloadMixin", null, {
    startup: function () {
      this.inherited(arguments);
      this.reload();
    },
  });
});

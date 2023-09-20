define([
		"dojo/_base/kernel",
		"dojo/io-query",
		/* ===== "./declare", ===== */
		"dojo/dom",
		"dojo/dom-form",
		"dojo/_base/Deferred",
		"dojo/_base/lang",
		"dojo/on" ], function(dojo, ioq, /* ===== declare, ===== */
dom, domForm, Deferred, lang, on) {

	lang.mixin(dojo.xhr, {
		_ioSetArgs : function(/* dojo/main.__IoArgs */args,
		/* Function */canceller,
		/* Function */okHandler,
		/* Function */errHandler) {

			var ioArgs = {
				args : args,
				url : args.url
			};

			var formObject = null;
			if (args.form) {
				var form = dom.byId(args.form);
				var actnNode = form.getAttributeNode("action");
				ioArgs.url = ioArgs.url
						|| (actnNode ? actnNode.value
								: (dojo.doc ? dojo.doc.URL : null));
				formObject = domForm.toObject(form);
			}

			var miArgs = {};

			if (formObject) {
				lang.mixin(miArgs, formObject);
			}
			if (args.content) {
				lang.mixin(miArgs, args.content);
			}
			if (args.preventCache) {
				miArgs["dojo.preventCache"] = new Date().valueOf();
			}
			ioArgs.query = ioq.objectToQuery(miArgs);

			ioArgs.handleAs = args.handleAs || "text";
			var d = new Deferred(function(dfd) {

				dfd.canceled = true;
				canceller && canceller(dfd);

				var err = dfd.ioArgs.error;
				if (!err) {
					err = new Error("request cancelled");
					err.dojoType = "cancel";
					dfd.ioArgs.error = err;
				}
				return err;
			});
			d.addCallback(okHandler);

			var ld = args.load;
			if (ld && lang.isFunction(ld)) {
				d.addCallback(function(value) {

					return ld.call(args, value, ioArgs);
				});
			}
			var err = args.error;
			if (err && lang.isFunction(err)) {
				d.addErrback(function(value) {

					return err.call(args, value, ioArgs);
				});
			}
			var handle = args.handle;
			if (handle && lang.isFunction(handle)) {
				d.addBoth(function(value) {

					return handle.call(args, value, ioArgs);
				});
			}
			d.addErrback(function(error) {

				return errHandler(error, d);
			});

			if (cfg.ioPublish && dojo.publish
					&& ioArgs.args.ioPublish !== false) {
				d.addCallbacks(function(res) {

					dojo.publish("/dojo/io/load", [ d, res ]);
					return res;
				}, function(res) {

					dojo.publish("/dojo/io/error", [ d, res ]);
					return res;
				});
				d.addBoth(function(res) {

					dojo.publish("/dojo/io/done", [ d, res ]);
					return res;
				});
			}

			d.ioArgs = ioArgs;

			return d;
		}
	});

	return dojo.xhr;
});

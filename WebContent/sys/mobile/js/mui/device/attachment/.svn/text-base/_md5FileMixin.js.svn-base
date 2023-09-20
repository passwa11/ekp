/**
 * 文件MD5转码
 */
define([ "dojo/_base/declare", "dojo/topic" ], function(
		declare,
		topic) {

	return declare("mui.device.attachment._md5FileMixin", null,
			{

				// MD5计算完毕
				MD5_CACULATE_DONE : "MD5.CACULATE.DONE",

				// 根据文件流生成MD5码
				loadFromBlob : function(blob) {

					var me = this;

					// kK语音接口没有这个功能
					if (!(blob instanceof File)) {
						me.md5Next(blob, me.getResult());
						return;
					}

					var chunkSize = 2 * 1024 * 1024;
					var chunks = Math.ceil(blob.size / chunkSize);
					var chunk = 0;
					
					require(["lib/spark-md5"],function(SparkMD5){
						var spark = new SparkMD5.ArrayBuffer();
						var blobSlice = blob.mozSlice || blob.webkitSlice
								|| blob.slice, loadNext;
						var fr = new FileReader();

						loadNext = function() {

							var start, end;

							start = chunk * chunkSize;
							end = Math.min(start + chunkSize, blob.size);

							fr.onload = function(e) {

								spark.append(e.target.result);
							};

							fr.onloadend = function() {

								fr.onloadend = fr.onload = null;

								if (++chunk < chunks) {
									setTimeout(loadNext, 1);
								} else {
									setTimeout(function() {

										me.result = spark.end();
										loadNext = spark = null;

										me.md5Next(blob, me.getResult());

									}, 50);
								}
							};

							fr.readAsArrayBuffer(blobSlice.call(blob, start, end));
						};
						
						loadNext();
					})
				},

				md5Next : function() {

				},

				getResult : function() {

					return this.result || '';
				}
			});

})
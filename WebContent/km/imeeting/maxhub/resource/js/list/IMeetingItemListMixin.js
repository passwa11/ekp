define([ 'dojo/_base/declare', 'dojo/_base/array', 'dojo/_base/lang' , 'dojo/topic', 'dojo/request/xhr', './item/IMeetingItem',
         'mui/util', 'dojo/date', 'dojo/dom-class'],
	function(declare, array, lang, topic, request, IMeetingItem, util, date, domClass) {
		return declare('km.imeeting.maxhub.IMeetingItemListMixin', null ,{

			itemRenderer: IMeetingItem,
			
			generateOptions: function(data) {
				
				var ctx = this;
				
				data = data || [];
				
				var opts = {
			    	slidesPerView: 3,
			    	slidesPerGroup: 1,
			        spaceBetween: window.innerWidth * 0.036,
			        slidesOffsetBefore: window.innerWidth * 0.1728,
			        slidesOffsetAfter: window.innerWidth * 0.1728,
			        on:{
			        	slideChange: function(){
			        		var activeIndex = ctx.swiper.activeIndex;
			        		var slides = ctx.swiper.slides || [];
			        		
			        		array.forEach(slides, function(slide) {
			        			domClass.remove(slide, 'mhuiSwiperSlide_left');
			        			domClass.remove(slide, 'mhuiSwiperSlide_right');
			        		});
			        		
			        		if(slides[activeIndex-1]) {
			        			domClass.add(slides[activeIndex-1], 'mhuiSwiperSlide_left');
			        		}
			        		
			        		if(slides[activeIndex+2]) {
			        			domClass.add(slides[activeIndex+2], 'mhuiSwiperSlide_right');
			        		}
			        		
			            },
		        	}
				}
				
				if(data.length < 2) {
					delete opts.slidesOffsetBefore;
					delete opts.slidesOffsetAfter;
					opts.centeredSlides = true;
				}
				
				return opts;
			},
			
			afterRenderList: function(data) {
				if(data.length > 2) {
					
					var initialSlide = 0, now = new Date();
					var i = 0, l = data.length;
					
					for(i; i < l; i++) {
						
						initialSlide = i;
						if(!data[i+1] || now < new Date(data[i+1].start)) {
							break;
						}
						
					}
					
					this.swiper.slideTo(initialSlide);
					
				}
			}
			
			
		});
	}
);
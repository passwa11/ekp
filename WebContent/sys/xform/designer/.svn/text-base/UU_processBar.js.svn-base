 var UU_processBar = (function () {


        //长方形进度条默认属性
        var defaultRectOption = {

        }

        //圆圈默认属性
        var defaultArcOption = {
            x: 75,
            y: 75,
            r: 50,
            currentAngel: -Math.PI / 2,
            endAngel: Math.PI * 3 / 2
        }
        
        //div 默认属性

        var defaultDivOption = {
        		width : '500px',
            	height : '22px',
            	color : 'red',
            	processHeight : '20px'
        }
        
        var defaultOption = {
        		key:"Div",
        		Rect:defaultRectOption,
        		Arc:defaultArcOption,
        		Div:defaultDivOption
        }
        
        /**
         * @container 容器
         * @option 属性 key 不存在时采用 div.
         */
        function ProcessBar(container, option) {
            if (option !== null && typeof(option) == "object") {
            	defaultOption.key = option.key;
                for (var property in defaultArcOption) {
                	defaultOption[option.key][property] = option[property]||defaultOption[option.key][property];
                }
            }
            this.canvas = this.dom = container;
            container.getContext && (this.ctx = container.getContext("2d"));
        }

        ProcessBar.prototype.isSupportCanvas = function () {
            if (this.ctx == null) {
                return false;
            } else {
                return true;
            }
        }


        ProcessBar.prototype._getAngel = function (pecent) {
            return pecent.toFixed(2) * Math.PI * 2 - Math.PI / 2;
        }

        /**
         * @param pecent 百分比
         * @param width 进度条宽度 字符串 （非必填）默认 500px
         * @param height 进度条高度 字符串（非必填）默认 22px
         * @param color 进度条颜色 （非必填）
         */
        ProcessBar.prototype.divDrawProgressBar = function (pecent) {
        	
        	var width = defaultOption[defaultOption.key].width;
        	var height = defaultOption[defaultOption.key].height;
        	var color = defaultOption[defaultOption.key].color;
        	var processHeight = defaultOption[defaultOption.key].processHeight;
        	
            if(this.processParent === undefined){
                this.processParent = document.createElement("div");
                this.dom.appendChild(this.processParent);
                this.processParent.style.width = width;
                this.processParent.style.height = height;
                this.processParent.style.padding = '1px';
                this.processParent.style.textAlign = 'left';
                this.processParent.style.border = '1px solid #DDD';
            }

            if(this.process === undefined){
               this.process  = document.createElement("div");
               this.processParent.appendChild(this.process);
               this.process.style.height = processHeight;
               this.process.style.lineHeight = processHeight;
               this.process.style.backgroundColor = color;
               this.process.style.width = '0px';
            }
            
            if(this.spanText === undefined){
            	 this.spanText  = document.createElement("span");
            	 this.process.appendChild(this.spanText);
                 this.spanText.style.position= 'absolute';
                 this.spanText.style.height = processHeight;
                 this.spanText.style.textAlign = 'center';
                 this.spanText.style.width = width;
            }
            this.process.style.width = Number.parseInt?(Number.parseInt(this.processParent.style.width) * pecent.toFixed(2) + "px"):(parseInt(this.processParent.style.width) * pecent.toFixed(2)  + "px");

            if(this.textNode !== undefined){
               this.spanText.removeChild(this.textNode);
            }
            this.textNode  = document.createTextNode((pecent * 100).toFixed(2) + '%');
            this.spanText.appendChild(this.textNode);
            this.processParent.style.width = width;
            this.processParent.style.height = height;
        }



        ProcessBar.prototype.canvasDrawProgressBar = function (pecent) {
            if (!this.isSupportCanvas) {
                return;
            }
            this.ctx.clearRect(0, 0, 500, 500);
            this.ctx.font = "16px serif";
            this.ctx.strokeText((pecent * 100).toFixed(2) + '%', defaultArcOption.x - 16, defaultArcOption.y + 8);
            this.ctx.beginPath();
            this.ctx.arc(defaultArcOption.x, defaultArcOption.y, defaultArcOption.r, 0, Math.PI * 2, false); // Outer circle
            this.ctx.stroke();
            this.ctx.beginPath();
            this._canvasDrawProgressBar(pecent);
        }

        ProcessBar.prototype._canvasDrawProgressBar = function (pecent) {
            if (defaultArcOption.endAngel < this._getAngel(pecent)) {
                return;
            }
            this.ctx.fillStyle = 'rgba(0,153,255,0.4)';
            this.ctx.arc(defaultArcOption.x, defaultArcOption.y, defaultArcOption.r, defaultArcOption.currentAngel, this._getAngel(pecent), false);
            this.ctx.lineTo(defaultArcOption.x, defaultArcOption.y);
            this.ctx.fill();
        }

        ProcessBar.prototype.show = function () {
            this.canvas.style.display = '';
        }

        ProcessBar.prototype.hide = function () {
            this.canvas.style.display = 'none';
        }

        return ProcessBar;
    })();

    //example:
    //var bar = new UU_processBar(document.getElementById("canvas"));

//    for (var i = 1; i <= 10000; i++) {
//        setTimeout(test(i), i*10);
//    }
//
//    function test(i){
//        return function () {
//            bar.drawProgressBar(i / 10000);
//        }
//    }
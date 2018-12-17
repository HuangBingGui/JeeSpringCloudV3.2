(function($){
		$.fn.combox = function(options) {
			var defaults = {  
                borderCss: "combox_border",  
                inputCss: "combox_input",  
                buttonCss: "combox_button",  
                selectCss: "combox_select",
				datas:[]
            };
            var options = $.extend(defaults, options);
			
			function _initBorder($border) {//初始化外框CSS
				$border.css({"border":"1px solid #e5e6e7",'display':'inline-block', 'position':'relative'}).addClass(options.borderCss);
				$border.children(':text').focus(function(){
					$border.css({"border":"1px solid #1ab394",'display':'inline-block', 'position':'relative'}).addClass(options.borderCss);
				});
				$border.children(':text').blur(function(){
					$border.css({"border":"1px solid #e5e6e7",'display':'inline-block', 'position':'relative'}).addClass(options.borderCss);
				});
				return $border;
			}
			
			function _initInput($border){//初始化输入框
				var id = $border.attr("id") == undefined?"":$border.attr("id");
				var name = $border.attr("name") == undefined?"":$border.attr("name");
				var bdclass = $border.attr("class") == undefined?"":$border.attr("class");
				var value = $border.attr("value")== undefined?"":$border.attr("value");
				$border.append('<input type="text" id="'+id+'"  name="'+name+'" class="'+options.inputCss+" "+bdclass+'" value="'+value+'"/>');
				$border.append('<font class="ficomoon icon-angle-bottom '+options.buttonCss+'" style="display:inline-block"></font>');
				
				var labeId = id==""?name:id;
				$border.append('<br/><label id="'+labeId+'-error" class="error" for="'+labeId+'" style="display: none;"></label>');
				//绑定下拉特效
				$border.delegate('font', 'click', function() {
					$border.children(':text').focus();
					var $ul = $border.children('ul');
					if($ul.css('display') == 'none') {
						$ul.slideDown('fast');
						$(this).removeClass('icon-angle-bottom').addClass('icon-angle-top');
					}else {
						$ul.slideUp('fast');
						$(this).removeClass('icon-angle-top').addClass('icon-angle-bottom');
					}					
				});
				return $border;//IE6需要返回值
			}
			
			function _initSelect($border) {//初始化下拉列表
				$border.append('<ul style=" z-index:9999;border:1px solid #6798D1;position:absolute;left:-1px;display:none" class="'+options.selectCss+'">');
				
				var $ul = $border.children('ul');
				$ul.css('top',$border.height()+1);
				var length = options.datas.length;
				for(var i=0; i<length ;i++)
					$ul.append('<li><a href="javascript:void(0)">'+options.datas[i]+'</a></li>');
				$ul.delegate('li', 'click', function() {
					$border.children(':text').val($(this).text());
					$border.children(':text').focus();
					$border.find("label.error").css("display","none");
					$ul.hide();
					$border.children('font').removeClass('icon-angle-top').addClass('icon-angle-bottom');//确定的时候要将下拉的icon改变
				});
				return $border;
			}
			this.each(function() {
				var _this = $(this);
				if(_this.attr("class").indexOf("combox_border") >=0)//防止重复初始化
					return;
				_this = _initInput(_this);//初始化输入框
				_this = _initBorder(_this);//初始化外框CSS
				_initSelect(_this);//初始化下拉列表
			});
			
		
		};
})(jQuery);
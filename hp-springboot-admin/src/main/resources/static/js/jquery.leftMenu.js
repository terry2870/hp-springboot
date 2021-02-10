/**
 * 一个左侧菜单
 * 作者：黄平
 * 日期：2020-05-09
 * @param $
 * @returns
 */
(function($) {
	let pluginName = "leftMenu";
	let NAME_SPACE = "http://www.w3.org/2000/svg";
	$.fn[pluginName] = function(options, param) {
		let self = this;
		if (typeof (options) == "string") {
			let method = $.fn[pluginName].methods[options];
			if (method){
				return method.call(this, param);
			}
		}
		return this.each(function() {
			let opt = $.extend({}, $.fn[pluginName].defaults, $.fn[pluginName].events, options);
			self.data(pluginName, opt);
			_create($(self), opt);
		});
	};
	
	/**
	 * 创建
	 */
	function _create(jq, opt) {
		jq.addClass("nav-main");

		//生成伸缩条
		_createMenuTop(jq, opt);

		//生成导航菜单
		if (opt.ajaxParam && opt.ajaxParam.url) {
			$.ajax($.extend({}, {
				success : function(json) {
					if (!json) {
						return;
					}
					opt.dataList = json;
					if (opt.loadFilter) {
						opt.dataList = opt.loadFilter(json);
					}
					_createLeftMenu(jq, opt);
					if (opt.onLoadSuccess) {
						opt.onLoadSuccess.call(jq, opt.dataList);
					}
				},
				dataType : "json"
			}, opt.ajaxParam));
		} else {
			_createLeftMenu(jq, opt);
			if (opt.onLoadSuccess) {
				opt.onLoadSuccess.call(jq, opt.dataList);
			}
		}
	}

	/**
	 * 生成菜单顶部伸缩条
	 * @param {} jq 
	 * @param {} opt
	 */
	function _createMenuTop(jq, opt) {
		let navTop = $("<div>").addClass("nav-top").appendTo(jq);
		let miniDiv = $("<div id='mini'>").appendTo(navTop);
		let nimiImg = $("<img>").attr("src", opt.contextPath + "/image/mini.png").appendTo(miniDiv);
		nimiImg.click(function() {
			if (jq.hasClass("nav-mini")) {
				$(".nav-main.nav-mini .scroll-bar-warp").off("mouseover mouseout");
				jq.removeClass("nav-mini")
				_revert(jq);
			} else {
				jq.addClass("nav-mini");
				_mini(jq);
				$(".nav-main.nav-mini .scroll-bar-warp").on("mouseover", function() {
					_revert(jq);
				});
				$(".nav-main.nav-mini .scroll-bar-warp").on("mouseout", function() {
					_mini(jq);
				});
			}
		});
	}

	/**
	 * 生成左侧菜单
	 * @param {*} jq 
	 * @param {*} opt 
	 */
	function _createLeftMenu(jq, opt) {
		let scrollBarWarp = $("<div>").addClass("scroll-bar-warp").appendTo(jq);
		let ul = _createUL(jq, opt, opt.rootPid);
		scrollBarWarp.append(ul);

		//虚拟的滚动条
		_initScroll(jq);

		//默认展开
		if (opt.expandId !== undefined && opt.expandId !== null) {
			//_getMenuContainer(jq).find("li.lsm-sidebar-item[menuid="+ opt.expandId +"]>a").click();
		}
	}

	/**
	 * 生成子节点
	 * @param {*} jq 
	 * @param {*} opt 
	 * @param {*} pid 
	 */
	function _createUL(jq, opt, pid) {
		if (!opt.dataList || opt.dataList.length == 0) {
			return null;
		}

		//第一层菜单
		let childNode = _findChild(opt.dataList, pid, opt);
		if (!childNode || childNode.length == 0) {
			return null;
		}
		let ul = $("<ul>").addClass("nav-menu scroll-bar");

		if (pid == opt.rootPid) {
			ul.show();
		} else {
			ul.hide();
		}
		for (let i = 0; i < childNode.length; i++) {
			let node = childNode[i];
			let li = $("<li>").addClass("nav-item").attr({
				menuid : node[opt.idField],
				pid : node[opt.pidField]
			}).appendTo(ul);
			li.data("menu-data", node);

			let a = $("<a>").attr("href", "javascript:;").appendTo(li);
			a.click(function() {

				if ($(this).next().css('display') == "none") {
					_expandMenu(jq, $(this).parent());
				} else {
					_collapseMenu(jq, $(this).parent());
				}
			});
			//菜单图标
			let menuIcon = createSVGIcon(node[opt.iconField] || "gear", opt.contextPath, {
				class : "nav-icon",
				fill : "currentColor"
			})
			a.append(menuIcon);

			//菜单名称
			a.append($("<span>").text(node[opt.textField]));

			//检查是否有子节点
			let subChildNode = _findChild(opt.dataList, node[opt.idField], opt);
			if (!subChildNode || subChildNode.length == 0) {
				continue;
			}

			//more icon
			let moreIcon = createSVGIcon("chevron-right", opt.contextPath, {
				class : "nav-more",
				fill : "currentColor"
			});
			a.append(moreIcon);

			let subUl = $("<ul>").appendTo(li);
			//生成子菜单
			$(subChildNode).each(function(index, item) {
				let subLi = $("<li>").addClass("nav-sub-menu-item").appendTo(subUl);
				subLi.attr("menuid", item[opt.idField]);
				subLi.data("menu-sub-data", item);
				let subA = $("<a>").attr("href", "javascript:;").appendTo(subLi);

				//菜单图标
				let subMenuIcon = createSVGIcon(item[opt.iconField] || "justify", opt.contextPath, {
					class : "nav-sub-icon",
					fill : "currentColor"
				})
				subA.append(subMenuIcon);

				subA.append($("<span>").addClass("sub-menu").text(item[opt.textField]));

				subA.click(function() {
					_showActive(jq, $(this).parent().attr("menuid"));
					opt.onClickMenu.call(jq, item, index);
				});
			});

		}

		//显示菜单为选中状态
		_showActive(jq, opt.selectedId);

		//展开
		_expandMenu(jq, opt.expandId);

		return ul;
	}

	/**
	 * 查找子节点
	 * @param {*} dataList 
	 * @param {*} pid 
	 * @param {*} opt 
	 */
	function _findChild(dataList, id, opt) {
		let arr = [];
		if (!dataList || dataList.length == 0) {
			return arr;
		}
		for (let i = 0; i < dataList.length; i++) {
			if (dataList[i][opt.pidField] == id) {
				arr.push(dataList[i]);
				//i--;
			}
		}
		return arr;
	}

	/**
	 * 获取滚动条div
	 * @param {*} jq 
	 */
	function _getScroll(jq) {
		return jq.find("ul.scroll-bar");
	}

	/**
	 * 获取菜单的容器
	 * @param {*} jq 
	 */
	function _getMenuContainer(jq) {
		return jq.find("div.lsm-container");
	}

	/**
	 * 初始化滚动条
	 * @param {*} jq 
	 */
	function _initScroll(jq) {
		_getScroll(jq).slimscroll({
			height: 'auto',
			position: 'right',
			railOpacity: 1,
			size: "5px",
			opacity: .4,
			color: '#fffafa',
			wheelStep: 5,
			touchScrollStep: 50
		});
	}

	/**
	 * 展开菜单
	 * @param {*} jq 
	 * @param {*} obj
	 */
	function _expandMenu(jq, obj) {
		let item = _getLiByObject(jq, obj);
		if (!item || item.length == 0) {
			return;
		}
		item.parent().find('ul').slideUp(300);
		item.find('ul').slideDown(300);
		item.addClass('nav-show').siblings('li').removeClass('nav-show');
	}

	/**
	 * 收起菜单
	 * @param {*} jq 
	 * @param {*} obj
	 */
	function _collapseMenu(jq, obj) {
		let item = _getLiByObject(jq, obj);
		if (!item || item.length == 0) {
			return;
		}
		item.find('ul').slideUp(300);
		item.removeClass("nav-show");
	}

	/**
	 * 收起所有
	 * @param {*} jq 
	 */
	function _collapseAll(jq) {
		jq.find('ul.nav-menu li.nav-item ul').slideUp(300);
	}

	/**
	 * 设置为mini
	 * @param {*} jq 
	 */
	function _mini(jq) {
		jq.find(".nav-item>a span").hide();
		jq.find(".nav-more use").hide();
		_setWidth(jq, "60px");
	}
	
	/**
	 * 还原
	 * @param {*} jq 
	 */
	function _revert(jq) {
		jq.find(".nav-item>a span").show();
		jq.find(".nav-more use").show();
		_setWidth(jq, "220px");
	}
	
	/**
	 * 设置宽度
	 * @param {*} jq 
	 * @param {*} width 
	 */
	function _setWidth(jq, width) {
		jq.css({
			width : width,
			"max-width" : width
		});
	}

	/**
	 * 根据对象，获取li对象
	 * @param {*} jq 
	 * @param {*} obj 
	 */
	function _getLiByObject(jq, obj) {
		let opt = jq.data(pluginName);
		let ul = jq.find("ul.nav-menu");
		let li = null;
		if ($.type(obj) == "number") {
			li = ul.find(">li.nav-item[menuid='"+ obj +"']");
		} else if ($.type(obj) == "string") {
			let lis = ul.find(">li");
			if (!lis || lis.length == 0) {
				return;
			}
			for (let i = 0; i < lis.length; i++) {
				let d = lis[i].data("menu-data");
				if (d[opt.textField] == obj) {
					li = lis[i];
					break;
				}
			}
		} else if ($.type(obj) == "object") {
			li = obj;
		}
		return li;
	}

	/**
	 * 选择一个菜单
	 * @param {*} jq 
	 * @param {*} menuId
	 */
	function _select(jq, menuId) {
		let opt = jq.data(pluginName);
		
		let subLi = jq.find(".nav-menu .nav-item li.nav-sub-menu-item[menuid='"+ menuId +"']");
		if (!subLi || subLi.length == 0) {
			return;
		}
		
		//选中自己
		_showActive(jq, menuId);

		//展开父节点
		_expandMenu(jq, subLi.parent().parent());
	}

	/**
	 * 显示菜单为选中状态
	 * @param {*} jq 
	 * @param {*} menuId 
	 */
	function _showActive(jq, menuId) {
		if (menuId == null || menuId === "" || menuId === undefined) {
			return;
		}
		jq.find(".nav-menu .nav-active").removeClass("nav-active");
		let subA = jq.find(".nav-menu li.nav-sub-menu-item[menuid='"+ menuId +"'] a");
		subA.addClass("nav-active");
	}

	//方法
	$.fn[pluginName].methods = {
		/**
		 * 展开菜单
		 * @param {*} obj 
		 * obj为整形，则表示菜单id
		 * obj为字符串，则表示菜单名称
		 * obj为对象，则表示是li对象
		 */
		expand : function(obj) {
			let jq = this;
			return this.each(function() {
				_expandMenu(jq, obj);
			});
		},
		/**
		 * 收起菜单
		 * @param {*} obj 
		 * obj为整形，则表示菜单id
		 * obj为字符串，则表示菜单名称
		 * obj为对象，则表示是li对象
		 */
		collapse : function(obj) {
			let jq = this;
			return this.each(function() {
				_collapseMenu(jq, obj);
			});
		},
		/**
		 * 选择某一个节点
		 * @param {*} menuId
		 */
		select : function(menuId) {
			let jq = this;
			return this.each(function() {
				_select(jq, menuId);
			});
		}
	};
	
	
	//事件
	$.fn[pluginName].events = {
		/**
		 * 加载完成执行
		 * @param data
		 */
		onLoadSuccess : function(data) {},
		/**
		 * 点击菜单执行
		 * @param item
		 * @param index
		 */
		onClickMenu : function(item, index) {}
	};
	
	//属性
	$.fn[pluginName].defaults = $.extend({}, $.fn[pluginName].events, {
		expandId : null,							//展开的id
		selectedId : null,							//选中的id
		rootPid : 0,								//根节点的父id值
		idField : "id",								//节点id字段
		pidField : "pid",							//节点父id字段
		textField : "text",							//显示文本字段
		iconField : "icon",							//图标字段
		ajaxParam : {								//ajax请求参数
			dataType : "json"
		},
		dataList : [],								//本地加载时，数据
		loadFilter : function(data) {				//数据处理
			return data;
		},
		contextPath : ""							//contextPath
	});
})(jQuery);
/**
 * 基于layui的一个左侧边栏
 * 作者：黄平
 * 日期：2021-02-03
 * 继承与：
 * @param $
 * @returns
 */
(function($) {
	let pluginName = "nav";
	let MENU_ID_KEY = "menu-id";
	let MENU_DATA_KEY = "menu-data";
	$.fn[pluginName] = function(options, param) {
		let self = this;
		if (typeof (options) == "string") {
			let method = $.fn[pluginName].methods[options];
			if (method) {
				return method.call(this, param);
			} else {
				throw new Error(pluginName + " 没有此方法。");
			}
		}
		return this.each(function() {
			let opt = $.extend({}, $.fn[pluginName].defaults, $.fn[pluginName].events, options);
			self.data(pluginName, opt);
			_create(self, opt);
		});
	};
	
	/**
	 * 创建
	 * @param {*} jq 
	 * @param {*} opt 
	 */
	function _create(jq, opt) {
		jq.addClass("layui-side-scroll");

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
					_createNav(jq, opt);
					if (opt.onLoadSuccess) {
						opt.onLoadSuccess.call(jq, opt.dataList);
					}
				},
				dataType : "json"
			}, opt.ajaxParam));
		} else {
			_createNav(jq, opt);
			if (opt.onLoadSuccess) {
				opt.onLoadSuccess.call(jq, opt.dataList);
			}
		}
	}

	/**
	 * 生成左侧菜单
	 * @param {*} jq 
	 * @param {*} opt 
	 */
	function _createNav(jq, opt) {
		let dataList = opt.dataList;
		if (!dataList || dataList.length == 0) {
			return;
		}
		let ul = $("<ul>").addClass("layui-nav").appendTo(jq);
		ul.attr("lay-filter", opt.filter);
		if (opt.tree === true) {
			ul.addClass("layui-nav-tree");
		}
		if (opt.side === true) {
			ul.addClass("layui-nav-tree layui-nav-side");
		}
		if (opt.shrink) {
			ul.attr("lay-shrink", opt.shrink);
		}

		//获取根节点
		let rootList = _findChild(opt.dataList, opt.rootPid, opt);
		if (!rootList || rootList.length == 0) {
			return;
		}

		//循环遍历
		for (let i = 0; i < rootList.length; i++) {
			let node = rootList[i];
			let li = $("<li>").addClass("layui-nav-item").appendTo(ul);
			li.attr(MENU_ID_KEY, node[opt.idField]);
			li.data(MENU_DATA_KEY, node)
			let a = $("<a href='javascript:;'>").html(node[opt.textField]).appendTo(li);

			//查询子节点
			let subList = _findChild(opt.dataList, node[opt.idField], opt);
			if (!subList || subList.length == 0) {
				continue;
			}

			//子节点
			let subDl = $("<dl>").addClass("layui-nav-child").appendTo(li);
			for (let j = 0; j < subList.length; j++) {
				let subNode = subList[j];
				let dd = $("<dd>").appendTo(subDl);
				dd.attr(MENU_ID_KEY, subNode[opt.idField]);
				dd.data(MENU_DATA_KEY, subNode);
				let subA = $("<a href='javascript:;'>").html(subNode[opt.textField]).appendTo(dd);

				//点击子节点
				subA.click(function() {
					_showActive(jq, $(this).parent().attr(MENU_ID_KEY));
					opt.onClickMenu.call(jq, subNode, j);
				});
			}
		}

		//显示菜单为选中状态
		_showActive(jq, opt.selectedId);

		//展开
		_expandMenu(jq, opt.expandId);

		layui.element.render("nav", opt.filter);
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

		//先去掉所有的选中
		jq.find("dd.layui-this").removeClass("layui-this");

		//再选中自己
		let subDd = jq.find("dd["+ MENU_ID_KEY +"='"+ menuId +"']");
		subDd.addClass("layui-this");
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

		item.siblings().removeClass("layui-nav-itemed");
		item.addClass("layui-nav-itemed");
	}

	/**
	 * 根据对象，获取li对象
	 * @param {*} jq 
	 * @param {*} obj 
	 */
	function _getLiByObject(jq, obj) {
		let opt = jq.data(pluginName);
		let ul = jq.find("ul.layui-nav");
		let li = null;
		if ($.type(obj) == "number") {
			//表示menuid
			li = ul.find(">li.layui-nav-item["+ MENU_ID_KEY +"='"+ obj +"']");
		} else if ($.type(obj) == "string") {
			//表示menuName
			let lis = ul.find(">li");
			if (!lis || lis.length == 0) {
				return;
			}
			for (let i = 0; i < lis.length; i++) {
				let d = lis[i].data(MENU_DATA_KEY);
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
			}
		}
		return arr;
	}

	/**
	 * 选择一个菜单
	 * @param {*} jq 
	 * @param {*} menuId
	 */
	function _select(jq, menuId) {
		let opt = jq.data(pluginName);
		
		let subDD = jq.find(".layui-nav .layui-nav-item dd["+ MENU_ID_KEY +"='"+ menuId +"']");
		if (!subDD || subDD.length == 0) {
			return;
		}
		
		//选中自己
		_showActive(jq, menuId);

		//展开父节点
		_expandMenu(jq, subDD.parent().parent());
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
		tree : false,								//是否添加layui-nav-tree
		side : false,								//是否添加layui-nav-side
		shrink : "all",								//对应layui的 lay-shrink 属性
		filter : "",								//对应layui的 lay-filter
		expandId : null,							//展开的id
		selectedId : null,							//选中的id
		rootPid : 0,								//根节点的父id值
		idField : "id",								//节点id字段
		pidField : "pid",							//节点父id字段
		textField : "text",							//显示文本字段
		//iconField : "icon",							//图标字段
		ajaxParam : {								//ajax请求参数
			dataType : "json"
		},
		dataList : [],								//本地加载时，数据
		loadFilter : function(data) {				//数据处理
			return data;
		}
	});
})(jQuery);
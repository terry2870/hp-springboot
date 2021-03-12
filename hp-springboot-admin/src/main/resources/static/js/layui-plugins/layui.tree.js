/**
 * 树形菜单
 * 树的节点是一次性全部加载完，并且节点不再是children形式，而是通过id,pid的形式
 * 作者：黄平
 * 日期：2021-02-09
 * 继承与：
 * @param $
 * @returns
 */
(function($) {
	let pluginName = "tree";
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
			if (!opt.id) {
				opt.id = self.attr("id");
			}
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
		if (opt.ajaxParam && opt.ajaxParam.url) {
			// 通过ajax去后台请求数据
			$.ajax($.extend({}, {
				success : function(json) {
					if (!json) {
						return;
					}

					opt.dataList = json;
					if (opt.loadFilter) {
						opt.dataList = opt.loadFilter(json);
					}

					_createTree(jq, opt);

					if (opt.onLoadSuccess) {
						opt.onLoadSuccess.call(jq, opt.dataList);
					}
				},
				dataType : "json"
			}, opt.ajaxParam));
		} else {
			_createTree(jq, opt);
			if (opt.onLoadSuccess) {
				opt.onLoadSuccess.call(jq, opt.dataList);
			}
		}
	}

	/**
	 * 创建tree
	 * @param {*} jq 
	 * @param {*} opt 
	 */
	function _createTree(jq, opt) {
		let pid = opt.rootPid;
		let id = jq.attr("id");
		layui.tree.render({
			elem : "#" + id,
			data : _createChildRen(jq, pid),
			id : id,
			showCheckbox : opt.showCheckbox,
			edit : opt.edit,
			accordion : opt.accordion,
			onlyIconControl : opt.onlyIconControl,
			isJump : opt.isJump,
			showLine : opt.showLine,
			text : opt.text,
			click : opt.onclick,
			oncheck : opt.oncheck,
			operate : opt.operate
		});
	}

	/**
	 * 根据父节点id，递归创建树
	 * @param {*} jq 
	 * @param {*} pid 
	 */
	function _createChildRen(jq, pid) {
		let opt = jq.data(pluginName);
		let obj = {}, data = [];
		let parentNode = _findChild(opt, pid);
		if (!parentNode || parentNode.length == 0) {
			return data;
		}
		for (let i = 0; i < parentNode.length; i++) {
			let node = parentNode[i];

			//查询子节点
			let childredData = _createChildRen(jq, node[opt.idField]);
			obj = {
				id : node[opt.idField],
				title : node[opt.textField],
				children : childredData,
				checked : node[opt.checkedField],
				attribute : node,
				isLeaf : !childredData || childredData.length == 0
			};
			data.push(obj);
		}
		
		return data;
	}

	/**
	 * 根据pid，查找子节点
	 * @param {*} opt 
	 * @param {*} pid 
	 */
	function _findChild(opt, pid) {
		let arr = [];
		if (!opt.dataList || opt.dataList.length == 0) {
			return arr;
		}
		for (let i = 0; i < opt.dataList.length; i++) {
			if (opt.dataList[i][opt.pidField] == pid) {
				arr.push(opt.dataList.splice(i, 1)[0]);
				i--;
			}
		}
		return arr;
	}

	/**
	 * 获取选中的checkbox节点
	 * isLeaf=true，则只返回叶子节点
	 * @param {*} jq 
	 */
	function _getCheckedData(jq, isLeaf) {
		let opt = jq.data(pluginName);
		let data = layui.tree.getChecked(opt.id);

		let arr = [];
		_recursionGetCheckedData(data, isLeaf, arr);
		return arr;
	}

	/**
	 * 递归，平铺数据
	 * @param {*} data 
	 * @param {*} isLeaf 
	 * @param {*} arr 
	 */
	function _recursionGetCheckedData(data, isLeaf, arr) {
		if (!data || data.length == 0) {
			return;
		}
		for (let i = 0; i < data.length; i++) {
			if (isLeaf === true) {
				if (data[i].isLeaf == true) {
					arr.push(data[i].attribute);
				}
			} else {
				arr.push(data[i].attribute);
			}
			_recursionGetCheckedData(data[i].children, isLeaf, arr);
		}
	}

	/**
	 * 获取选中的id
	 * @param {*} jq 
	 * @param {*} isLeaf 
	 */
	function _getCheckedId(jq, isLeaf) {
		let dataArr = _getCheckedData(jq, isLeaf);
		if (!dataArr || dataArr.length ==0) {
			return [];
		}

		let opt = jq.data(pluginName);
		let arr = [];
		for (let i = 0; i < dataArr.length; i++) {
			arr.push(dataArr[i][opt.idField]);
		}
		return arr;
	}

	/**
	 * 设置选中状态
	 * @param {*} jq 
	 * @param {*} values 
	 */
	function _setChecked(jq, values) {
		let opt = jq.data(pluginName);
		layui.tree.setChecked(opt.id, values);
	}

	/**
	 * 重新加载树
	 * @param {*} jq 
	 * @param {*} data 
	 */
	function _reload(jq, data) {
		let opt = jq.data(pluginName);
		layui.tree.reload(opt.id, data);
	}

	/**
	 * 全部展开
	 * @param {*} jq 
	 */
	function _expandAll(jq) {
		jq.find(".layui-icon-addition").click();
	}

	/**
	 * 全部折叠
	 * @param {*} jq 
	 */
	function _collapseAll(jq) {
		jq.find(".layui-icon-subtraction").click();
	}

	//方法
	$.fn[pluginName].methods = {
		/**
		 * 获取选中的checkbox节点
		 * @param {为true，则只返回叶子节点} isLeaf 
		 */
		getCheckedData : function(isLeaf) {
			let jq = $(this);
			return _getCheckedData(jq, isLeaf);
		},
		/**
		 * 获取选中的checkbox节点（只返回id）
		 * @param {为true，则只返回叶子节点} isLeaf 
		 */
		getCheckedId : function(isLeaf) {
			let jq = $(this);
			return _getCheckedId(jq, isLeaf);
		},
		/**
		 * 设置选中状态
		 * @param {*} values 
		 */
		setChecked : function(values) {
			let jq = $(this);
			return jq.each(function() {
				_setChecked(jq, values);
			});
		},
		/**
		 * 重新加载树
		 * @param {*} data 
		 */
		reload : function(data) {
			let jq = $(this);
			return jq.each(function() {
				_reload(jq, data);
			});
		},
		/**
		 * 全部展开
		 */
		expandAll : function() {
			let jq = $(this);
			return jq.each(function() {
				_expandAll(jq);
			});
		},
		/**
		 * 全部折叠
		 */
		collapseAll : function() {
			let jq = $(this);
			return jq.each(function() {
				_collapseAll(jq);
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
		 * 当点击菜单节点时触发
		 * @param {*} obj 
		 */
		onclick : function(obj) {},
		/**
		 * 点击复选框时触发
		 * @param {*} obj 
		 */
		oncheck : function(obj) {},
		/**
		 * 节点进行增删改等操作时
		 * @param {*} data 
		 */
		operate : function(data) {}
	};
	
	//属性
	$.fn[pluginName].defaults = $.extend({}, $.fn[pluginName].events, {
		dataList : [],
		ajaxParam : {},
		loadFilter : function(data) {				//数据处理
			return data;
		},
		idField : "id",
		pidField : "pid",
		textField : "text",
		checkedField : "checked",
		rootPid : 0,
		showCheckbox : false,						//是否显示复选框
		edit : false,								//是否开启节点的操作图标
		accordion : false,							//是否开启手风琴模式
		onlyIconControl : false,					//是否仅允许节点左侧图标控制展开收缩
		isJump : false,								//是否允许点击节点时弹出新窗口跳转
		showLine : true,							//是否开启连接线
		text : {},									//自定义各类默认文本
	});
})(jQuery);
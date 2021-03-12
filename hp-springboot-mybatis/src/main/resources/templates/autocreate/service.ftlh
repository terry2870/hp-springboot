package ${package};

import ${requestModelPackage}.${modelName}RequestBO;
import ${responseModelPackage}.${modelName}ResponseBO;
import ${pageResponsePackage};
import ${statusEmunPackage};
import ${pageRequestPackage};

/**
 * ${tableComment}业务接口定义
 * @author ${author}
 * ${.now?string["yyyy-MM-dd"]}
 */
public interface I${modelName}Service {

	/**
	 * @Title: save${modelName}
	 * @Description: 保存${tableComment}
	 * @param request
	 */
	void save${modelName}(${modelName}RequestBO request);

	/**
	 * @Title: query${modelName}PageList
	 * @Description: 查询${tableComment}列表
	 * @param request
	 * @param pageRequest
	 * @return
	 */
	PageResponse<${modelName}ResponseBO> query${modelName}PageList(${modelName}RequestBO request, PageRequest pageRequest);

	/**
	 * @Title: delete${modelName}
	 * @Description: 删除${tableComment}
	 * @param id
	 */
	void delete${modelName}(Integer id);

	/**
	 * @Title: query${modelName}ById
	 * @Description: 根据id，查询${tableComment}
	 * @param id
	 * @return
	 */
	${modelName}ResponseBO query${modelName}ById(Integer id);
	
	/**
	 * @Title: change${modelName}Status
	 * @Description: 修改${tableComment}状态
	 * @param id
	 * @param status
	 */
	void change${modelName}Status(Integer id, StatusEnum status);

}

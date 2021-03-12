package ${package};

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import ${requestModelPackage}.${modelName}RequestBO;
import ${responseModelPackage}.${modelName}ResponseBO;
import ${servicePackage}.I${modelName}Service;
import ${pageResponsePackage};
import ${responsePackage};
import ${statusEmunPackage};
import ${pageRequestPackage};
import ${freeMarkerUtilPackage};

/**
 * 描述：${tableComment}控制器
 * 作者：${author}
 * 时间：${.now?string["yyyy-MM-dd"]}
 */
@RestController
@RequestMapping("/${modelName}Controller")
public class ${modelName}Controller {

	private static Logger log = LoggerFactory.getLogger(${modelName}Controller.class);

	@Autowired
	private I${modelName}Service ${modelNameFirstLow}Service;

	/**
	 * @Title: ${modelNameFirstLow}List
	 * @Description: 打开${tableComment}页面
	 * @return
	 */
	@RequestMapping("/${modelNameFirstLow}List")
	public ModelAndView ${modelNameFirstLow}List() {
		Map<String, Object> map = new HashMap<>();
		FreeMarkerUtil.addStaticTemplate(map);
		map.put("showPage", "other/${modelNameFirstLow}List");
		return new ModelAndView("/default", map);
	}
	
	/**
	 * @Title: query${modelName}PageList
	 * @Description: 查询${tableComment}列表
	 * @param request
	 * @param pageRequest
	 * @return
	 */
	@RequestMapping("/query${modelName}PageList")
	public Response<PageResponse<${modelName}ResponseBO>> query${modelName}PageList(${modelName}RequestBO request, PageRequest pageRequest) {
		log.info("enter query${modelName}PageList with request={}, page={}", request, pageRequest);
		PageResponse<${modelName}ResponseBO> list = ${modelNameFirstLow}Service.query${modelName}PageList(request, pageRequest);
		return Response.success(list);
	}

	/**
	 * @Title: save${modelName}
	 * @Description: 保存${tableComment}
	 * @param request
	 * @return
	 */
	@RequestMapping("/save${modelName}")
	public Response<Object> save${modelName}(${modelName}RequestBO request) {
		log.info("enter save${modelName} with request={}", request);
		${modelNameFirstLow}Service.save${modelName}(request);
		return Response.success();
	}

	/**
	 * @Title: delete${modelName}
	 * @Description: 删除${tableComment}
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete${modelName}")
	public Response<Object> delete${modelName}(Integer id) {
		log.info("enter delete${modelName} with id={}", id);
		${modelNameFirstLow}Service.delete${modelName}(id);
		return Response.success();
	}

	/**
	 * @Title: query${modelName}ById
	 * @Description: 根据id，查询${tableComment}详情
	 * @param id
	 * @return
	 */
	@RequestMapping("/query${modelName}ById")
	public Response<${modelName}ResponseBO> query${modelName}ById(Integer id) {
		log.info("enter query${modelName}ById with id={}", id);
		${modelName}ResponseBO bo = ${modelNameFirstLow}Service.query${modelName}ById(id);
		return Response.success(bo);
	}
	
	/**
	 * @Title: change${modelName}Status
	 * @Description: 修改状态
	 * @param id
	 * @param status
	 * @return
	 */
	@RequestMapping("/change${modelName}Status")
	public Response<Object> change${modelName}Status(Integer id, Integer status) {
		log.info("enter change${modelName}Status with id={}", id);
		${modelNameFirstLow}Service.change${modelName}Status(id, StatusEnum.getEnumByValue(status));
		return Response.success();
	}
}

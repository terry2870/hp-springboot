package ${package};

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ${baseConvertPackage};
import ${convertPackage}.${modelName}Convert;
import ${daoPackage}.I${modelName}DAO;
import ${daoModelPackage}.${modelName};
import ${requestModelPackage}.${modelName}RequestBO;
import ${responseModelPackage}.${modelName}ResponseBO;
import ${servicePackage}.I${modelName}Service;
import ${pageResponsePackage};
import ${statusEmunPackage};
import ${dateUtilPackage};
import ${numberUtilPackage};
import ${pageModelPackage};
import ${pageRequestPackage};
import ${SQLBuildersPackage};
import ${SQLWherePackage};

/**
 * 描述：${tableComment}业务操作接口实现
 * 作者：${author}
 * 时间：${.now?string["yyyy-MM-dd"]}
 */
@Service
public class ${modelName}ServiceImpl implements I${modelName}Service {

	private static Logger log = LoggerFactory.getLogger(${modelName}ServiceImpl.class);

	@Autowired
	private I${modelName}DAO ${modelNameFirstLow}DAO;

	@Override
	public void save${modelName}(${modelName}RequestBO request) {
		log.info("save${modelName} with request={}", request);
		${modelName} dal = ${modelName}Convert.boRequest2Dal(request);
		if (NumberUtil.isNullOrZero(request.getId())) {
			// 新增
			BaseConvert.convertModel(dal);
			${modelNameFirstLow}DAO.insertSelective(dal);
		} else {
			// 修改
			dal.setUpdateTime(DateUtil.getCurrentTimeSeconds());
			${modelNameFirstLow}DAO.updateByPrimaryKeySelective(dal);
		}
	}

	@Override
	public PageResponse<${modelName}ResponseBO> query${modelName}PageList(${modelName}RequestBO request, PageRequest pageRequest) {
		log.info("query${modelName}PageList with request={}", request);
		PageModel page = pageRequest.toPageModel();
		
		List<SQLWhere> whereList = SQLWhere.builder()
				// TODO 这里添加查询条件
				.build()
				;
		
		// 查询总数
		int total = ${modelNameFirstLow}DAO.selectCount(whereList);
		if (total == 0) {
			log.warn("query${modelName}PageList error. with total=0. with request={}", request);
			return null;
		}
		PageResponse<${modelName}ResponseBO> resp = new PageResponse<>();
		resp.setCurrentPage(pageRequest.getPage());
		resp.setPageSize(pageRequest.getLimit());
		resp.setTotal(total);

		SQLBuilders builders = SQLBuilders.create()
				.withWhere(whereList)
				.withPage(page)
				;
		// 查询列表
		List<${modelName}> list = ${modelNameFirstLow}DAO.selectList(builders);
		if (CollectionUtils.isEmpty(list)) {
			log.warn("query${modelName}PageList error. with list is empty. with request={}", request);
			return resp;
		}

		List<${modelName}ResponseBO> respList = new ArrayList<>(list.size());
		for (${modelName} a : list) {
			respList.add(${modelName}Convert.dal2BOResponse(a));
		}
		log.info("query${modelName}PageList success. with request={}", request);
		return new PageResponse<>(total, respList, page.getCurrentPage(), page.getPageSize());
	}

	@Override
	public void delete${modelName}(Integer id) {
		log.info("delete${modelName} with id={}", id);
		${modelNameFirstLow}DAO.deleteByPrimaryKey(id);
	}

	@Override
	public ${modelName}ResponseBO query${modelName}ById(Integer id) {
		log.info("query${modelName}ById with id={}", id);
		${modelName} dal = ${modelNameFirstLow}DAO.selectByPrimaryKey(id);
		if (dal == null) {
			log.warn("query${modelName}ById error. with result is null. with id={}", id);
			return null;
		}
		return ${modelName}Convert.dal2BOResponse(dal);
	}
	
	@Override
	public void change${modelName}Status(Integer id, StatusEnum status) {
		log.info("change${modelName}Status with id={}, status={}", id, status);
		
		${modelName} dal = new ${modelName}();
		dal.setStatus(status.getIntegerValue());
		dal.setId(id);
		dal.setUpdateTime(DateUtil.getCurrentTimeSeconds());
		${modelNameFirstLow}DAO.updateByPrimaryKeySelective(dal);
	}
}

package ${package};

/**
 * ${tableComment}对应的dao接口
 * @author ${author}
 * ${.now?string["yyyy-MM-dd"]}
 */
import ${baseMapperPackage};
import ${daoModelPackage}.${modelName};

public interface I${modelName}DAO extends BaseMapper<${modelName}, Integer> {

}

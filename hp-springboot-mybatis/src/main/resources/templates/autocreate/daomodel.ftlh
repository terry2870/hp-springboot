package ${package};

import javax.persistence.Id;

import ${baseModelPackage};

/**
 * ${tableComment}
 * @author ${author}
 * ${.now?string["yyyy-MM-dd"]}
 */
public class ${modelName} extends BaseModel {

	private static final long serialVersionUID = 1L;

	<#list columnList as column>
	<#if commonColumnName?seq_contains(column.fieldName)>
	<#else>
	/**
	 * ${column.columnComment}
	 */
	<#if primaryKey = column.columnName>
	@Id
	</#if>
	private ${column.javaType} ${column.fieldName};

	</#if>
	</#list>
	<#list columnList as column>
	<#if commonColumnName?seq_contains(column.fieldName)>
	<#else>
	public ${column.javaType} get${column.fieldNameFirstUpper}() {
		return ${column.fieldName};
	}

	public ${modelName} set${column.fieldNameFirstUpper}(${column.javaType} ${column.fieldName}) {
		this.${column.fieldName} = ${column.fieldName};
		return this;
	}

	</#if>
	</#list>
}

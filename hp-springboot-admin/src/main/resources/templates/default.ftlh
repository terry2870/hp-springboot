<#assign SecuritySessionUtil = javaClass["com.hp.springboot.admin.util.SecuritySessionUtil"]>
<#assign StatusEnum = javaClass["com.hp.springboot.common.enums.StatusEnum"] >
<#-- 引入布局指令的命名空间 -->
<#import "layout/defaultLayout.ftl" as defaultLayout>
<#-- 调用布局指令 -->
<@defaultLayout.layout>
	<#include '${showPage}.ftlh'> 
</@defaultLayout.layout>
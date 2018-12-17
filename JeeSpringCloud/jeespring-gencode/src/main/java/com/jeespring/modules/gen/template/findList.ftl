SELECT 
			<#assign columnField>
			<#list table.columnList as c>
		a.${c.name} AS "${c.javaFieldId}",
			</#list>
			<#list table.columnList as c>
				<#if c.showType?? && c.showType == "userselect">
					<#list c.javaFieldAttrs as a>
		u${c_index + 1}.${a[1]} AS "${c.simpleJavaField}.${a[0]}",
					</#list>
				<#elseif c.showType?? && c.showType == "officeselect">
					<#list c.javaFieldAttrs as a>
		o${c_index + 1}.${a[1]} AS "${c.simpleJavaField}.${a[0]}",
					</#list>
				<#elseif c.showType?? && c.showType == "areaselect">
					<#list c.javaFieldAttrs as a>
		a${c_index + 1}.${a[1]} AS "${c.simpleJavaField}.${a[0]}",
					</#list>
				</#if>
				<#-- 父表关联字段 -->
				<#if table.parentExists && table.parentTableFk == c.name>
					<#list c.javaFieldAttrs as a>
		b.${a[1]} AS "${c.simpleJavaField}.${a[0]}",
					</#list>
				</#if>
			</#list>
		</#assign>
${columnField?substring(0, columnField?last_index_of(","))}
		FROM ${table.name} a
		<#-- 关联父表 -->
		<#if table.parentExists>
		LEFT JOIN ${table.parent.name} b ON b.id = a.${table.parentTableFk}
		</#if>
		<#-- 关联系统表 -->
		<#list table.columnList as c>
			<#if c.showType?? && c.showType == "userselect">
		LEFT JOIN sys_user u${c_index + 1} ON u${c_index + 1}.id = a.${c.name}
			<#elseif c.showType?? && c.showType == "officeselect">
		LEFT JOIN sys_office o${c_index + 1} ON o${c_index + 1}.id = a.${c.name}
			<#elseif c.showType?? && c.showType == "areaselect">
		LEFT JOIN sys_area a${c_index + 1} ON a${c_index + 1}.id = a.${c.name}
			</#if>
		</#list>
		<where>
			<#if table.delFlagExists>a.del_flag = ${"#"}{DEL_FLAG_NORMAL}</#if>
			<#list table.columnList as c>
				<#if (c.isQuery?? && c.isQuery == "1") || (table.parentExists && table.parentTableFk == c.name)>
					<#if c.queryType ?? && c.queryType == 'between'>
			<if test="begin${c.simpleJavaField?cap_first} != null and end${c.simpleJavaField?cap_first} != null <#if c.simpleJavaField != c.javaFieldId>and begin${c.javaFieldId?cap_first} != null and end${c.javaFieldId?cap_first} != null </#if>and begin${c.javaFieldId?cap_first} != '' and end${c.javaFieldId?cap_first} != ''">
					<#else>
			<if test="${c.simpleJavaField} != null<#if c.simpleJavaField != c.javaFieldId> and ${c.javaFieldId} != null</#if> and ${c.javaFieldId} != ''">
					</#if>
					<#if c.queryType ?? && c.queryType == 'between'>
				AND a.${c.name} BETWEEN ${"#"}{begin${c.simpleJavaField?cap_first}} AND ${"#"}{end${c.simpleJavaField?cap_first}}
					<#elseif c.queryType ?? && c.queryType == 'like'>
				AND a.${c.name} LIKE 
					<if test="dbName == 'oracle'">'%'||${"#"}{${c.javaFieldId}}||'%'</if>
					<if test="dbName == 'mssql'">'%'+${"#"}{${c.javaFieldId}}+'%'</if>
					<if test="dbName == 'mysql'">concat('%',${"#"}{${c.javaFieldId}},'%')</if>
					<#elseif c.queryType ?? && c.queryType == 'left_like'>
				AND a.${c.name} LIKE 
					<if test="dbName == 'oracle'">'%'||${"#"}{${c.javaFieldId}}</if>
					<if test="dbName == 'mssql'">'%'+${"#"}{${c.javaFieldId}}</if>
					<if test="dbName == 'mysql'">concat('%',${"#"}{${c.javaFieldId}})</if>
					<#elseif c.queryType ?? && c.queryType == 'right_like'>
				AND a.${c.name} LIKE 
					<if test="dbName == 'oracle'">${"#"}{${c.javaFieldId}}||'%'</if>
					<if test="dbName == 'mssql'">${"#"}{${c.javaFieldId}}+'%'</if>
					<if test="dbName == 'mysql'">concat(${"#"}{${c.javaFieldId}},'%')</if>
					<#else>
				AND a.${c.name} ${c.queryType} ${"#"}{${c.javaFieldId}}
					</#if>
			</if>
				</#if>
			</#list>
		</where>
		<choose>
			<when test="page !=null and page.orderBy != null and page.orderBy != ''">
				ORDER BY ${"$"}{page.orderBy}
			</when>
			<otherwise>
				<#if table.parentExists>
					<#if table.createDateExists>
				ORDER BY a.create_date ASC
					</#if>
				<#else>
					<#if table.updateDateExists>
				ORDER BY a.update_date DESC
					</#if>
				</#if>
			</otherwise>
		</choose>
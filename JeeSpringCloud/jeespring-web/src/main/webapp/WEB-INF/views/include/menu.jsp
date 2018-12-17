<c:forEach items="menus" var="menu">
	<c:if test="${menu.isShow eq '1'}">
		<li>
			<c:choose>
			   <c:when test="${empty menu.href}">  
				   <a href="#"><i class="fa fa-gear"></i> <span class="nav-label">${menu.name}</span><span class="fa arrow"></span></a>  
			   </c:when>
			   <c:otherwise> 
				  <a  class="J_menuItem"  href="${ctx}${menu.href}"><i class="fa fa-gear"></i> <span class="nav-label">${menu.name}</span><span class="fa arrow"></span></a>
			   </c:otherwise>
			</c:choose>
			<ul class="nav nav-second-level">
						<c:set var="menus" value="${menu.children}" scope="request"/>
                 		<%@ include file="/WEB-INF/views/include/menu.jsp"%>
			</ul>
		</li>
		
	</c:if>
</c:forEach>
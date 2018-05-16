<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/table.css"/>"/>

<title>choose-broker</title>
</head>
<body>
	<p class="greeting"> Выберите брокера, с которым хотите сотрудничать </p>
	<div class="wrapp">
		<c:forEach var="broker" items="${firmList}" varStatus="loopStatus">
			<div class="imagesBox">
				<img src="${broker.getAvatarFirmUrl()}">
			</div>
		</c:forEach>
	</div>
</body>
</html>
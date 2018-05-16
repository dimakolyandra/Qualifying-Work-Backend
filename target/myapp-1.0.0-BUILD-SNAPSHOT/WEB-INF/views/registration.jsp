<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/input.css"/>"/>
<title>Home</title>
</head>

<body>
	<p class="greeting"> Заполните поля для регистрации </p> 	
	<form:form class="form" action="choose-broker" method="POST" commandName="newUser">
		<p class="name"> 
        		<form:input type="text" id="name" path="firstName" placeholder="Введите ваше имя" /> 
    			<label>Имя</label>
    	</p>
    	<p class="secname"> 
        		<form:input type="text" path="secondName" placeholder="Введите вашу фамилию" /> 
    			<label>Фамилия</label>
    	</p>
    	<p class="bdate"> 
        		<form:input type="date" path="bDate" placeholder="Введите дату рождения" /> 
    			<label>Дата рождения</label>
    	</p>
    	
    	<p class="phone"> 
        		<form:input type="text" path="phone" placeholder="Введите телефон" /> 
    			<label>Моб. телефон</label>
    	</p>
    	
    	<p class="phone"> 
        		<form:input type="text" path="passportData" placeholder="Серия и номер паспорта" /> 
    			<label>Пасспортные данные</label>
    	</p>
    	 
    	<p class="submit">
    		<input type="button" value="Далее" onclick="validate_form(this.form)">
    	</p>
	</form:form> 
    	<script type="text/javascript">
		
			function validate_form(form)
			{
				var submit = true;
				var inputs = form.querySelectorAll('input:not([type="button"])');
				for (var i = 0; i < inputs.length; i++){
					if (inputs[i].value == ""){ 
						inputs[i].style.borderColor = "red";
						inputs[i].setAttribute("placeholder", "Неправильный ввод");
						submit = false;
					}
					else{
						inputs[i].style.borderColor = "black";
					}				
				}
				if (submit){
					form.submit();
				}
			}
		</script>
</body>
</html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="jumbotron jumbotron-billboard">
	<div class="img"></div>
	<div class="container">
		<div class="row">
			<div class="col-lg-12">
				<h2>${Titolo}</h2>
				<p>${Titolo2}</p>
			</div>
		</div>
	</div>
</div>
<section class="container">
	<div class="portlet light bordered">
		 <div class="portlet-title">
		 	<div class="caption font-red-sunglo">
            	<i class="icon-settings oi oi-pencil"></i>
             	<span class="caption-subject bold uppercase"><spring:message code="insingrediente.form.titolo.label"/></span>
            </div>
            <section class = "locale-link"> 
            	<a href="?language=en"><img src="<c:url value="/img/US.png" />"></a> - 
            	<a href="?language=it"><img src="<c:url value="/img/IT.png" />"></a>                
      		</section> 
		 </div>
		<div class="portlet-body form">
				<form:form  method="POST" modelAttribute="newIngrediente">
				<div class="form-body">
				
					<div class="form-group">
						<label for="codArt"><spring:message code="insingrediente.form.codArt.label"/></label>
						<form:input id="codArt" path="codArt" type="text" class="form-control" placeholder="Codice Articolo"/>  
					</div>
					
					<div class="form-group">
						<label for="info"><spring:message code="insingrediente.form.info.label"/></label>
						<form:textarea id="info" path="info" type="text" class="form-control" placeholder="Info"/> 	 
					</div>
					
				</div>
				
				<hr class="line-form">
				
				<div class="form-actions">
					<input type="submit" id="btnAdd" class="btn btn-primary form-buttons" value = <spring:message code="insingrediente.form.btnAdd.label"/> />
					<input type="submit" id="btnAbort" class="btn btn-default form-buttons" value = <spring:message code="insingrediente.form.btnAbort.label"/> />
				</div>
			
				</form:form>
			</div>
	</div>
</section>
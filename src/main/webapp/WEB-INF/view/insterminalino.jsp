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
             	<span class="caption-subject bold uppercase"><spring:message code="insterminalino.form.titolo.label"/></span>
            </div>
            <section class = "locale-link"> 
            	<a href="?language=en"><img src="<c:url value="/img/US.png" />"></a> - 
            	<a href="?language=it"><img src="<c:url value="/img/IT.png" />"></a>                
      		</section> 
		 </div>
		<div class="portlet-body form">
			<form:form  method="POST" modelAttribute="newTerminalino" enctype="multipart/form-data">
				<form:errors path="*" cssClass="alert alert-danger" element="div"/>
				<c:if test="${isFileOk}">
					<ul class="list-group list-group-flush">
		 		 		<li class="list-group-item">Numero terminalino: <span class="info-art">${terminalinoResult.numeroTerminalino}</span></li>
		 		 		<li class="list-group-item">Codice punto vendita: <span class="info-art">${terminalinoResult.codicePuntoVendita}</span></li>
		 		 		<li class="list-group-item">Totale righe processate: <span class="info-art">${terminalinoResult.totalRowsCount}</span></li>
		 		 		<li class="list-group-item">Totale righe in errore: <span class="info-art">${terminalinoResult.totalRowsInErrorCount}</span></li>
						<c:if test="${terminalinoResult.totalRowsInErrorCount gt 0}">
							<div class="alert alert-danger">
								<spring:message code="insterminalino.form.errori.label" /><br/><br/>
								<c:forEach items="${terminalinoResult.errors}" var="e">
									<div><c:out value="${e}"/></div>
								</c:forEach>
							</div>
						</c:if>
	 		 		</ul>
				</c:if>
				<c:if test="${!isFileOk}">
					<div class="alert alert-danger">Necessario selezionare un file per l'elaborazione</div>
				</c:if>
				<div class="form-body">
					<div class="form-group">
						<label for="dataFile"><spring:message code="insterminalino.form.dataFile.label"/></label>
						<form:input id="dataFile" path="dataFile" type="file" class="form:input-large" />  
						<form:errors path="dataFile" cssClass="text-danger"/> 
					</div>
				</div>

				<hr class="line-form">

				<div class="form-actions">
					<input type="submit" id="btnLoad" class="btn btn-primary form-buttons" value = <spring:message code="insterminalino.form.btnProcess.label"/> />
					<input type="submit" id="btnAbort" class="btn btn-default form-buttons" value = <spring:message code="common.form.btnAbort.label"/> />
				</div>
			</form:form>
		</div>
	</div>
</section>
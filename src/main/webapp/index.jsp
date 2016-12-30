<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <base href="${pageContext.request.contextPath}/" />
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" href="webjars/bootstrap/3.3.7/css/bootstrap.min.css" />
    <link rel="stylesheet" href="webjars/bootstrap/3.3.7/css/bootstrap-theme.css" />
    <script src="webjars/jquery/1.11.1/jquery.min.js"></script>
    <script src="webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>

    <link rel="stylesheet" href="static/ma-styles.css" />

    <c:choose>
        <c:when test="${not empty requestScope.input}">
            <title>${requestScope.input} - mathalfa-web</title>
        </c:when>
        <c:otherwise>
            <title>mathalfa-web</title>
        </c:otherwise>
    </c:choose>
</head>
<body>
<div class="container">
    <div class="ma-header">
        <span class="ma-logo"></span>
    </div>
    <form method="get" action="" class="ma-form-input">
        <div class="input-group">
            <span class="input-group-addon" id="basic-addon1">Input</span>
            <input type="text" name="i" class="form-control"
                   placeholder="1+(2+3)" value="${requestScope.input}">
            <span class="input-group-btn">
                <button type="submit" class="btn btn-default">Compute</button>
            </span>
        </div>
    </form>

    <c:if test="${not empty requestScope.result}">
        <div class="panel panel-primary ma-panel-result">
            <div class="panel-heading">
                Result
            </div>
            <div class="panel-body">
                ${requestScope.result}
            </div>
        </div>
    </c:if>

    <c:if test="${not empty requestScope.error}">
        <div class="panel panel-danger ma-panel-error">
            <div class="panel-body">
                <pre style="color: #cc0000">${requestScope.error}</pre>
            </div>
        </div>
    </c:if>
</div>
</body>
</html>
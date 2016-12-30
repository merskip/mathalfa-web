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

    <link rel="stylesheet" href="static/mathalfa-web-styles.css" />

    <title>mathalfa-web</title>
</head>
<body>
<div class="container">
    <h1>
        <span class="ma-logo"></span>
    </h1>
    <div>
        <form method="get" action="">
            Input: <input name="i" placeholder="1+(2+3)" value="${requestScope.input}">
            <button>Compute</button>
        </form>
    </div>
    <div>
        <c:if test="${not empty requestScope.result}">
            <p>Result: </p>
            <span>${requestScope.input}</span> =
            <output>${requestScope.result}</output>
        </c:if>
    </div>
    <div>
        <c:if test="${not empty requestScope.error}">
            <pre style="color: #cc0000">${requestScope.error}</pre>
        </c:if>
    </div>
</div>
</body>
</html>
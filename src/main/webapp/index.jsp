<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>mathalfa-base</title>
</head>
<body>
<h1>mathalfa-base</h1>
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
</body>
</html>
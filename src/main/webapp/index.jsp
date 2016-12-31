<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <base href="${pageContext.request.contextPath}/" />
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap & jQuery -->
    <link rel="stylesheet" href="webjars/bootstrap/3.3.7/css/bootstrap.min.css" />
    <link rel="stylesheet" href="webjars/bootstrap/3.3.7/css/bootstrap-theme.css" />
    <script src="webjars/jquery/1.11.1/jquery.min.js"></script>
    <script src="webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script>
        $(function(){
            $('[data-toggle="tooltip"]').tooltip({html: true});
        });
    </script>

    <!-- MathJax -->
    <script async src="webjars/MathJax/2.6.1/MathJax.js?config=TeX-AMS_CHTML"> </script>
    <link rel="stylesheet" href="static/mjx-chtml.css" />
    <script type="text/x-mathjax-config">
        MathJax.Hub.Config({
            jax: ["input/TeX","output/HTML-CSS"],
            messageStyle: "none",
            showMathMenu: false,
            "fast-preview": {
                disabled: true
            },
            preRemoveClass: "ma\-math\-preview",
            tex2jax: {
                preview: "none"
            }
        });
    </script>
    <style><%--suppress CssUnusedSymbol --%>
        .mjx-chtml {
            outline: none;
        }
    </style>

    <!-- mathafla-web -->
    <link rel="stylesheet" href="static/ma-styles.css" />
    <script>
        $(function () {
            // Wersja MathJax 2.6.1 nie usuwa elementów,
            // tylko czyści treść
            $('.ma-math-preview').html(" ");
        });
    </script>

    <c:choose>
        <c:when test="${not empty requestScope.input}">
            <title>${requestScope.input} - mathalfa-web</title>
        </c:when>
        <c:otherwise>
            <title>mathalfa-web</title>
        </c:otherwise>
    </c:choose>
</head>
<body <c:if test="${empty requestScope.result and empty requestScope.error}">class="ma-start-page"</c:if>>
<div class="container">
    <div class="ma-header">
        <a href="${pageContext.request.contextPath}/">
            <span class="ma-logo"></span>
        </a>
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

    <fmt:formatNumber var="total_time" minFractionDigits="3" maxFractionDigits="3"
                      value="${requestScope.times['total'] * 1e-6}"/>
    <fmt:formatNumber var="calculation_time" minFractionDigits="3" maxFractionDigits="3"
                      value="${requestScope.times['calculation'] * 1e-6}"/>
    <fmt:formatNumber var="latex_time" minFractionDigits="3" maxFractionDigits="3"
                      value="${requestScope.times['latex'] * 1e-6}"/>

    <c:if test="${not empty requestScope.result}">
        <div class="panel panel-default ma-panel-result">
            <ul class="list-group">
                <c:forEach var="section" items="${requestScope.sections}">
                    <li class="list-group-item">
                        <div class="list-group-item-heading">
                            <p class="text-primary">${section.title}</p>
                        </div>
                        <div class="list-group-item-text">
                            <span class="ma-math-preview"></span>
                            <script type="math/tex">${section.latex}</script>
                        </div>
                    </li>
                </c:forEach>
            </ul>
            <div class="panel-footer">
                <span class="ma-result-time pull-right">
                    in ${total_time} ms
                    <span class="glyphicon glyphicon-info-sign" data-toggle="tooltip"
                          title="<strong>Total time - ${total_time} ms</strong><br/>
                                 Calculation time - ${calculation_time} ms<br/>
                                 Time generate LaTex  - ${latex_time} ms"></span>
                </span>
                <div class="clearfix"></div>
            </div>
        </div>
    </c:if>

    <c:if test="${not empty requestScope.error}">
        <div class="panel panel-danger ma-panel-error">
            <div class="panel-body">
                <pre style="color: #cc0000">${requestScope.error}</pre>
            </div>
            <div class="panel-footer">
                <span class="ma-result-time pull-right">
                    in ${total_time} ms
                </span>
                <div class="clearfix"></div>
            </div>
        </div>
    </c:if>
</div>
</body>
</html>
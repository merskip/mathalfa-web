<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:useBean id="section" scope="request" type="pl.merskip.mathalfa.web.SymbolSection"/>
<jsp:useBean id="latexRenderer" scope="request" type="pl.merskip.mathalfa.latex.core.RendererRegister"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<li class="ma-section list-group-item">
    <div class="ma-extra-actions btn-group btn-group-xs pull-right" role="group">
        <button type="button" class="btn btn-default"
                data-ma-section-show-latex>LaTeX</button>
        <button type="button" class="btn btn-default"
                data-ma-show-rpn="${fn:escapeXml(section.jsonRPN)}">RPN</button>
    </div>
    <div class="list-group-item-heading">
        <p class="text-primary">${section.title}</p>
    </div>
    <div class="list-group-item-text">
        <span class="ma-math-preview"></span>
        <script type="math/tex">${latexRenderer.renderSymbol(section.symbol)}</script>
    </div>
    <div class="clearfix"></div>
</li>
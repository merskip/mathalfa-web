<jsp:useBean id="section" scope="request" type="pl.merskip.mathalfa.web.SymbolSection"/>
<jsp:useBean id="latexRenderer" scope="request" type="pl.merskip.mathalfa.latex.core.RendererRegister"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<li class="list-group-item">
    <div class="list-group-item-heading">
        <p class="text-primary">${section.title}</p>
    </div>
    <div class="list-group-item-text">
        <span class="ma-math-preview"></span>
        <script type="math/tex">${latexRenderer.renderSymbol(section.symbol)}</script>
    </div>
</li>
package pl.merskip.mathalfa.web;

import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import pl.merskip.mathalfa.base.core.Symbol;
import pl.merskip.mathalfa.base.core.fragment.FragmentException;
import pl.merskip.mathalfa.base.infixparser.PostfixParser;
import pl.merskip.mathalfa.base.operation.CalculateOperation;
import pl.merskip.mathalfa.base.shared.SharedPostfixParser;
import pl.merskip.mathalfa.latex.core.RendererRegister;
import pl.merskip.mathalfa.latex.elementary.ElementaryRenderer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CalculateServlet", urlPatterns = "")
public class CalculateServlet extends HttpServlet {
    
    private PostfixParser parser;
    private RendererRegister latexRenderer;
    
    private HttpServletRequest request;
    private HttpServletResponse response;
    
    private Symbol inputRootSymbol, resultRootSymbol;
    private String inputLatex, resultLatex;
    private long totalTime, calculationTime, latexTime;
    
    @Override
    public void init() throws ServletException {
        super.init();
        parser = new SharedPostfixParser();
        latexRenderer = new ElementaryRenderer();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.request = request;
        this.response = response;
        
        String input = request.getParameter("i");
        if (input != null && !input.isEmpty()) {
            request.setAttribute("input", input);
    
            calculate(input);
            if (resultRootSymbol != null) {
                request.setAttribute("result", true);
                request.setAttribute("sections", ImmutableList.of(
                        new LatexSection("Input:", inputLatex),
                        new LatexSection("Result:", resultLatex)
                ));
                request.setAttribute("times", ImmutableMap.of(
                        "total", totalTime,
                        "calculation", calculationTime,
                        "latex", latexTime
                ));
            }
        }
        
        clearResults();
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
    
    private void calculate(String input) {
        totalTime = measure(() -> {
            try {
                calculationTime = measure(() -> {
                    inputRootSymbol = parser.parseAndGetRootSymbol(input);
                    resultRootSymbol = new CalculateOperation().executeForResult(inputRootSymbol);
                });
            
                latexTime = measure(() -> {
                    inputLatex = latexRenderer.renderSymbol(inputRootSymbol);
                    resultLatex = latexRenderer.renderSymbol(resultRootSymbol);
                });
            }
            catch (FragmentException e) {
                request.setAttribute("error", Throwables.getStackTraceAsString(e));
            }
        });
    }
    
    private void clearResults() {
        inputRootSymbol = null;
        resultRootSymbol = null;
        inputLatex = null;
        resultLatex = null;
        totalTime = 0;
        calculationTime = 0;
        latexTime = 0;
    }
    
    private static long measure(Runnable runnable) {
        long startTime = System.nanoTime();
        runnable.run();
        return System.nanoTime() - startTime;
    }
}
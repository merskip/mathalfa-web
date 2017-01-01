package pl.merskip.mathalfa.web;

import com.google.common.base.Throwables;
import pl.merskip.mathalfa.base.core.Symbol;
import pl.merskip.mathalfa.base.core.fragment.FragmentException;
import pl.merskip.mathalfa.base.infixparser.PostfixParser;
import pl.merskip.mathalfa.base.operation.CalculateOperation;
import pl.merskip.mathalfa.base.operation.SimplifyOperation;
import pl.merskip.mathalfa.base.shared.SharedPostfixParser;
import pl.merskip.mathalfa.latex.core.RendererRegister;
import pl.merskip.mathalfa.latex.elementary.ElementaryRenderer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "CalculateServlet", urlPatterns = "")
public class CalculateServlet extends HttpServlet {
    
    private PostfixParser parser;
    private RendererRegister latexRenderer;
    
    private HttpServletRequest request;
    
    private Symbol inputRootSymbol, resultRootSymbol, simplifyRootSymbol;
    private long totalTime;
    
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
        
        String input = request.getParameter("i");
        if (input != null && !input.isEmpty()) {
            request.setAttribute("input", input);
    
            calculate(input);
            if (resultRootSymbol != null) {
                request.setAttribute("result", true);
                request.setAttribute("latexRenderer", latexRenderer);
                request.setAttribute("total_time", totalTime);
                
                List<Section> sections = new ArrayList<>();
                sections.add(new EquationSection("Input:", inputRootSymbol));
                if (resultRootSymbol != simplifyRootSymbol) {
                    sections.add(new EquationSection("Result:",
                            resultRootSymbol, simplifyRootSymbol));
                }
                else {
                    sections.add(new EquationSection("Result:", resultRootSymbol));
                }
                
                request.setAttribute("sections", sections);
            }
        }
        
        clearResults();
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
    
    private void calculate(String input) {
        totalTime = measure(() -> {
            try {
                inputRootSymbol = parser.parseAndGetRootSymbol(input);
                resultRootSymbol = new CalculateOperation().executeForResult(inputRootSymbol);
                simplifyRootSymbol = new SimplifyOperation().executeForResult(resultRootSymbol);
            }
            catch (FragmentException e) {
                request.setAttribute("error", Throwables.getStackTraceAsString(e));
            }
        });
    }
    
    private void clearResults() {
        inputRootSymbol = null;
        resultRootSymbol = null;
        totalTime = 0;
    }
    
    private static long measure(Runnable runnable) {
        long startTime = System.nanoTime();
        runnable.run();
        return System.nanoTime() - startTime;
    }
}
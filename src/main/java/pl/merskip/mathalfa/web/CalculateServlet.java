package pl.merskip.mathalfa.web;

import com.google.common.base.Throwables;
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
    
    @Override
    public void init() throws ServletException {
        super.init();
        parser = new SharedPostfixParser();
        latexRenderer = new ElementaryRenderer();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String input = request.getParameter("i");
        if (input != null && !input.isEmpty()) {
            request.setAttribute("input", input);
    
            long timeStart = System.nanoTime();
            try {
                Symbol rootSymbol = parser.parseAndGetRootSymbol(input);
                Symbol result = new CalculateOperation().executeForResult(rootSymbol);
                long calculationEnd = System.nanoTime();
                request.setAttribute("calculation_nano_time", calculationEnd - timeStart);
                
                if (result != null) {
                    request.setAttribute("result", result.toPlainText());
                    request.setAttribute("result_latex", latexRenderer.renderSymbol(result));
                    request.setAttribute("input_latex", latexRenderer.renderSymbol(rootSymbol));
                    request.setAttribute("latex_nano_time", System.nanoTime() - calculationEnd);
                }
            }
            catch (FragmentException e) {
                request.setAttribute("error", Throwables.getStackTraceAsString(e));
            }
            finally {
                request.setAttribute("total_nano_time", System.nanoTime() - timeStart);
            }
        }
        
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}
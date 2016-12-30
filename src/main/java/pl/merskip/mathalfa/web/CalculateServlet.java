package pl.merskip.mathalfa.web;

import com.google.common.base.Throwables;
import pl.merskip.mathalfa.base.core.Symbol;
import pl.merskip.mathalfa.base.core.fragment.FragmentException;
import pl.merskip.mathalfa.base.infixparser.PostfixParser;
import pl.merskip.mathalfa.base.operation.CalculateOperation;
import pl.merskip.mathalfa.base.shared.SharedPostfixParser;
import pl.merskip.mathalfa.latex.LatexGenerator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CalculateServlet", urlPatterns = "")
public class CalculateServlet extends HttpServlet {
    
    private PostfixParser parser;
    private LatexGenerator latexGenerator;
    
    @Override
    public void init() throws ServletException {
        super.init();
        parser = new SharedPostfixParser();
        latexGenerator = new LatexGenerator();
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
                
                if (result != null) {
                    request.setAttribute("result", result.toPlainText());
                    request.setAttribute("result_base64", latexGenerator.base64RenderSymbol(result));
                }
            }
            catch (FragmentException e) {
                request.setAttribute("error", Throwables.getStackTraceAsString(e));
            }
            finally {
                request.setAttribute("nano_time", System.nanoTime() - timeStart);
            }
        }
        
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}
package pl.merskip.mathalfa.web;

import com.google.common.base.Throwables;
import pl.merskip.mathalfa.base.core.Symbol;
import pl.merskip.mathalfa.base.core.fragment.FragmentException;
import pl.merskip.mathalfa.base.infixparser.PostfixParser;
import pl.merskip.mathalfa.base.operation.CalculateOperation;
import pl.merskip.mathalfa.base.shared.SharedPostfixParser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CalculateServlet", urlPatterns = "")
public class CalculateServlet extends HttpServlet {
    
    private PostfixParser parser;
    
    @Override
    public void init() throws ServletException {
        super.init();
        parser = new SharedPostfixParser();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String input = request.getParameter("i");
        if (input != null && !input.isEmpty()) {
            request.setAttribute("input", input);
            
            try {
                request.setAttribute("result", calculate(input));
            } catch (FragmentException e) {
                request.setAttribute("error", Throwables.getStackTraceAsString(e));
            }
        }
        
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
    
    private String calculate(String plainText) {
        Symbol rootSymbol = parser.parseAndGetRootSymbol(plainText);
        Symbol result = new CalculateOperation().executeForResult(rootSymbol);
        return result != null ? result.toPlainText() : null;
    }
}
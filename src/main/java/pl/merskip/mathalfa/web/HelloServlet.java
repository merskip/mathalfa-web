package pl.merskip.mathalfa.web;

import pl.merskip.mathalfa.base.core.Symbol;
import pl.merskip.mathalfa.base.infixparser.PostfixParser;
import pl.merskip.mathalfa.base.operation.CalculateOperation;
import pl.merskip.mathalfa.base.shared.SharedPostfixParser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "HelloServlet", urlPatterns = "")
public class HelloServlet extends HttpServlet {
    
    private PostfixParser parser;
    
    @Override
    public void init() throws ServletException {
        super.init();
        parser = new SharedPostfixParser();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String plainText = "1+2";
        
        request.setAttribute("plainText", plainText);
        request.setAttribute("result", calculate(plainText));
        
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
    
    private String calculate(String plainText) {
        Symbol rootSymbol = parser.parseAndGetRootSymbol(plainText);
        return new CalculateOperation().executeForResult(rootSymbol).toPlainText();
    }
}
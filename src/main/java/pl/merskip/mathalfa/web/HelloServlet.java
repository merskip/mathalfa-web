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
import java.io.PrintWriter;

@WebServlet("")
public class HelloServlet extends HttpServlet {
    
    private PostfixParser parser;
    
    @Override
    public void init() throws ServletException {
        super.init();
        parser = new SharedPostfixParser();
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String plainText = "1+2";
        
        PrintWriter writer = resp.getWriter();
        writer.println("<!DOCTYPE html>");
        writer.println("<html>");
        writer.println("<body>");
        writer.println("<p>Hello World!</p>");
        writer.println("<p>" + plainText + " = " + calculate(plainText) + "</p>");
        writer.println("</body>");
        writer.println("</html>");
    }
    
    private String calculate(String plainText) {
        Symbol rootSymbol = parser.parseAndGetRootSymbol(plainText);
        return new CalculateOperation().executeForResult(rootSymbol).toPlainText();
    }
}
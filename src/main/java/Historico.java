import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Properties;

import org.json.JSONObject;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import service.PrevisaoService;

public class Historico extends HttpServlet{
    private JSONObject historico() throws Exception {
        PrevisaoService service = new PrevisaoService();
        Properties properties = new Properties();
        InputStream location = getServletContext().getResourceAsStream("/WEB-INF/App.properties");
        properties.load(location);
        final String URL_ORACLE = properties.getProperty("URL_ORACLE");
        return service.getPrevisoesOracleCloud(URL_ORACLE);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            out.print(historico().toString());
        } catch (Exception e) {
            out.print(e.getMessage());
            e.printStackTrace(out);
        }
    }
}
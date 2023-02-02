import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONObject;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Previsao;
import java.util.List;
import service.PrevisaoService;

public class Pesquisar extends HttpServlet{
    private JSONObject pesquisar(String cidade) throws Exception { 
        PrevisaoService service = new PrevisaoService();
        Properties properties = new Properties();
        InputStream location = getServletContext().getResourceAsStream("/WEB-INF/App.properties");
        properties.load(location);
        final String WHEATER_MAP_BASEURL = properties.getProperty("WHEATER_MAP_BASEURL");
        final String WHEATER_MAP_APPID = properties.getProperty("WHEATER_MAP_APPID");
        final String WEATHER_MAP_UNITS = properties.getProperty("WEATHER_MAP_UNITS");
        final String WEATHER_MAP_CNT = properties.getProperty("WEATHER_MAP_CNT");
        final String URL_ORACLE = properties.getProperty("URL_ORACLE");
        cidade = cidade.replaceAll("\\s", "+").replaceAll("[^a-zA-Z+]", "");
        List<Previsao> previsoes = service.obterPrevisoesWheaterMap(WHEATER_MAP_BASEURL, WHEATER_MAP_APPID,
        WEATHER_MAP_UNITS, WEATHER_MAP_CNT, cidade);
        service.armazenarPrevisaoNoHistoricoOraclaCloud(previsoes.get(0), URL_ORACLE);
        JSONObject mensagem = new JSONObject();
        JSONArray items = new JSONArray();
        mensagem.put("country", previsoes.get(0).getPais()).put("items", items);
        for (int i = 0; i < previsoes.size(); i++) {
            items.put(previsoes.get(i).toJSON());
        }
        return mensagem;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            out.print(pesquisar(request.getParameter("city")).toString());
        } catch (Exception e) {
            out.print(e.getMessage());
            e.printStackTrace(out);
        }
    }
}

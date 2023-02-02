import java.io.FileInputStream;
import java.util.Properties;

import org.json.JSONObject;

import service.PrevisaoService;

public class Historico {
    public JSONObject historico(String[] args) throws Exception {
        PrevisaoService service = new PrevisaoService();
        Properties properties = new Properties();
        properties.load(new FileInputStream("src/main/java/App.properties"));
        final String URL_ORACLE = properties.getProperty("URL_ORACLE");
        return service.getPrevisoesOracleCloud(URL_ORACLE);
    }
}
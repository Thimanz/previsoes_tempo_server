package model;

import org.json.JSONObject;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Previsao {
    private final double temperaturaMin;
    private final double temperaturaMax;
    @Getter
    private final String cidade;
    @Getter
    private final String pais;
    private final String data;

    public JSONObject toJSON() {
        JSONObject previsao = new JSONObject();
        previsao.put("date", data).put("temperaturaMin", temperaturaMin).put("temperaturaMax", temperaturaMax);
        return previsao;
    }
}
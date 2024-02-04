package com.cs309.quoridorApp.dataPackets.toClient;

import com.cs309.quoridorApp.dataPackets.websockets.NewTurnPacket;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import springfox.documentation.spring.web.json.Json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Packet used for testing formatting
 */
public class ReturnTestPacket extends ClientPacket
{

    private Map<String, Object> json = new HashMap<String, Object>()
    {
        {
            put("bleh", 1);
            put("blehh", 2);
        }
    };

    private String jsonString = json.toString();

    private Json basicJson = new Json("{ \"bleh\":\"bloo\" }");

    private List<String> list = new ArrayList<String>()
    {{
        add("abba");
        add("bccb");
        add(null);
    }};

    public ReturnTestPacket()
    {
        try {
            p = new ObjectMapper().writeValueAsString(new NewTurnPacket(null));

            json = new ObjectMapper().readValue("{ \"bleh\":\"bloo\", \"shhe\":{\"ddd\":false}}", Map.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public String getP() {
        return p;
    }

    public void setP(String p) {
        this.p = p;
    }

    private String p;

    public Map getJson() {
        return json;
    }

    public void setJson(Map json) {
        this.json = json;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public List<String> getList() {
        return list;
    }

    public Json getBasicJson() {
        return basicJson;
    }

    public void setBasicJson(Json basicJson) {
        this.basicJson = basicJson;
    }

    public String getJsonString() {
        return jsonString;
    }

    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
    }
}

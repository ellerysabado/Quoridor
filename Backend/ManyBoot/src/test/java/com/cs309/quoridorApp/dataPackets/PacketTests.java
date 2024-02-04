package com.cs309.quoridorApp.dataPackets;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

@SpringBootTest(webEnvironment =  SpringBootTest.WebEnvironment.RANDOM_PORT)

@RunWith(SpringRunner.class)
public class PacketTests {

    @TestConfiguration
    static class StringContextConfiguration { // can be named whatever

    }

    @LocalServerPort
    int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }

    public JSONObject mainPacketTest(String body) {
        // Send request and receive response
        Response response = RestAssured.given().
                contentType(ContentType.JSON).
                header("charset", "utf-8").
                body(body).
                when().
                request("POST", "/lobby");


        System.out.println(response.getBody() == null ? "body null" : response.getBody().asString());

        // Check status code
        int statusCode = response.getStatusCode();
        //assertEquals(200, statusCode);

        // Check response body for correct response
        String returnString = response.getBody().asString();
        JSONObject obj = null;
        try {
            Map<String, Object> map = new ObjectMapper().readValue(returnString, HashMap.class);
            obj = new JSONObject(map);

            for (String key : obj.keySet())
                System.out.println(key + " : " + obj.get(key));

            assertEquals("", obj.get("message"));
        } catch (Exception e) {
            e.printStackTrace();
        }


        return obj;
    }


//    @Test
//    public void player1Test() {
//
//        JSONObject obj = mainPacketTest("{" +
//                "\"function\":\"STARTSESSION\"," +
//                "\"data\":" +
//                "   {" +
//                "   \"login\":true," +
//                "   \"username\":\"player1\"," +
//                "   \"password\":\"111\"" +
//                "   }" +
//                "}");
//        for (String key : obj.keySet())
//            System.out.println(obj.get(key));
//    }
//
//    @Test
//    public void player2Test() {
//
//        JSONObject obj = mainPacketTest("{" +
//                "\"function\":\"STARTSESSION\"," +
//                "\"data\":" +
//                "   {" +
//                "   \"login\":true," +
//                "   \"username\":\"player2\"," +
//                "   \"password\":\"123\"" +
//                "   }" +
//                "}");
//        for (String key : obj.keySet())
//            System.out.println(obj.get(key));
//    }
//
//    @Test
//    public void player3Test() {
//
//        JSONObject obj = mainPacketTest("{" +
//                "\"function\":\"STARTSESSION\"," +
//                "\"data\":" +
//                "   {" +
//                "   \"login\":true," +
//                "   \"username\":\"player3\"," +
//                "   \"password\":\"123\"" +
//                "   }" +
//                "}");
//        for (String key : obj.keySet())
//            System.out.println(obj.get(key));
//    }
//
//    @Test
//    public void player4Test() {
//
//        JSONObject obj = mainPacketTest("{" +
//                "\"function\":\"STARTSESSION\"," +
//                "\"data\":" +
//                "   {" +
//                "   \"login\":true," +
//                "   \"username\":\"player4\"," +
//                "   \"password\":\"123\"" +
//                "   }" +
//                "}");
//        for (String key : obj.keySet())
//            System.out.println(obj.get(key));
//    }

//    @Test
//    public void game1() {
//        JSONObject obj = mainPacketTest("{" +
//                "\"function\":\"STARTSESSION\"," +
//                "\"data\":" +
//                "   {" +
//                "   \"login\":true," +
//                "   \"username\":\"player1\"," +
//                "   \"password\":\"111\"" +
//                "   }" +
//                "}");
//
//        String uuid1 = (String) obj.get("id");
////        for(String key : obj.keySet())
////            System.out.println(obj.get(key));
//
//        obj = mainPacketTest("{" +
//                "\"function\":\"STARTSESSION\"," +
//                "\"data\":" +
//                "   {" +
//                "   \"login\":true," +
//                "   \"username\":\"player2\"," +
//                "   \"password\":\"123\"" +
//                "   }" +
//                "}");
//        String uuid2 = (String) obj.get("id");
//
//        obj = mainPacketTest("{" +
//                "\"function\":\"STARTSESSION\"," +
//                "\"data\":" +
//                "   {" +
//                "   \"login\":true," +
//                "   \"username\":\"player3\"," +
//                "   \"password\":\"123\"" +
//                "   }" +
//                "}");
//        String uuid3 = (String) obj.get("id");
//
//        obj = mainPacketTest("{" +
//                "\"function\":\"STARTSESSION\"," +
//                "\"data\":" +
//                "   {" +
//                "   \"login\":true," +
//                "   \"username\":\"player4\"," +
//                "   \"password\":\"123\"" +
//                "   }" +
//                "}");
//        String uuid4 = (String) obj.get("id");
//
//        obj = mainPacketTest("{" +
//                "\"uuid\":\"" + uuid1 + "\"," +
//                "\"function\":\"CREATELOBBY\"," +
//                "\"data\":" +
//                "   {" +
//                "   }" +
//                "}");
//        String gameID = (String) obj.get("session");
//        for (String key : obj.keySet())
//            System.out.println(obj.get(key));
//
//
//        obj = mainPacketTest("{" +
//                "\"uuid\":\"" + uuid2 + "\"," +
//                "\"session\":\"" + gameID + "\"," +
//                "\"function\":\"JOINLOBBY\"," +
//                "\"data\":" +
//                "   {" +
//                "   }" +
//                "}");
//
//        obj = mainPacketTest("{" +
//                "\"uuid\":\"" + uuid1 + "\"," +
//                "\"session\":\"" + gameID + "\"," +
//                "\"function\":\"CHANGESETTINGS\"," +
//                "\"data\":" +
//                "   {" +
//                "\"player_count\": 4," +
//                "\"host\": 0" +
//                "   }" +
//                "}");
//
//
//        obj = mainPacketTest("{" +
//                "\"uuid\":\"" + uuid3 + "\"," +
//                "\"session\":\"" + gameID + "\"," +
//                "\"function\":\"JOINLOBBY\"," +
//                "\"data\":" +
//                "   {" +
//                "   }" +
//                "}");
//
//        obj = mainPacketTest("{" +
//                "\"uuid\":\"" + uuid4 + "\"," +
//                "\"session\":\"" + gameID + "\"," +
//                "\"function\":\"JOINLOBBY\"," +
//                "\"data\":" +
//                "   {" +
//                "   }" +
//                "}");
//
//    }


//    @Test
//    public void sessionTest() {
//
//        JSONObject obj = mainPacketTest("{" +
//                "\"function\":\"STARTSESSION\"," +
//                "\"data\":" +
//                "   {" +
//                "   \"login\":true," +
//                "   \"username\":\"sometester\"," +
//                "   \"password\":\"blarg\"" +
//                "   }" +
//                "}");
//    }

//    @Test
//    public void createLobbyTest() {
//
//        JSONObject obj = mainPacketTest("{" +
//                "\"function\":\"STARTSESSION\"," +
//                "\"data\":" +
//                "   {" +
//                "   \"login\":true," +
//                "   \"username\":\"slomslester\"," +
//                "   \"password\":\"blarg\"" +
//                "   }" +
//                "}");
//
//        String uuid = (String) obj.get("id");
//
//        obj = mainPacketTest("{" +
//                "\"uuid\":\"" + uuid + "\"," +
//                "\"function\":\"CREATELOBBY\"," +
//                "\"data\":" +
//                "   {" +
//                "   }" +
//                "}");
//    }

    @Test
    public void StartGameWin() {

        JSONObject obj = mainPacketTest("{" +
                "\"function\":\"STARTSESSION\"," +
                "\"data\":" +
                "   {" +
                "   \"login\":true," +
                "   \"username\":\"player1\"," +
                "   \"password\":\"111\"" +
                "   }" +
                "}");

        String uuid1 = (String) obj.get("id");
//        for(String key : obj.keySet())
//            System.out.println(obj.get(key));

        obj = mainPacketTest("{" +
                "\"function\":\"STARTSESSION\"," +
                "\"data\":" +
                "   {" +
                "   \"login\":true," +
                "   \"username\":\"player2\"," +
                "   \"password\":\"123\"" +
                "   }" +
                "}");
        String uuid2 = (String) obj.get("id");

        obj = mainPacketTest("{" +
                "\"uuid\":\"" + uuid1 + "\"," +
                "\"function\":\"CREATELOBBY\"," +
                "\"data\":" +
                "   {" +
                "   }" +
                "}");
        String gameID = (String) obj.get("session");

        obj = mainPacketTest("{" +
                "\"uuid\":\"" + uuid2 + "\"," +
                "\"session\":\"" + gameID + "\"," +
                "\"function\":\"JOINLOBBY\"," +
                "\"data\":" +
                "   {" +
                "   }" +
                "}");

        obj = mainPacketTest("{" +
                "\"uuid\":\"" + uuid1 + "\"," +
                "\"session\":\"" + gameID + "\"," +
                "\"function\":\"STARTGAME\"," +
                "\"data\":" +
                "   {" +
                "   }" +
                "}");

        obj = movePacket(uuid1, gameID, 9,5,-1,0 );
        obj = movePacket(uuid1, gameID, 9,5,-1,0 );
        obj = wallPacket(uuid1, gameID, 2, 2, 1);
        obj = movePacket(uuid1, gameID, 9,4,-1,0 );
        obj = movePacket(uuid1, gameID, 9,3,-1,0 );
        obj = wallPacket(uuid1, gameID, 9, 9, 1);
        obj = movePacket(uuid1, gameID, 9,3,0, 0);
//        obj = movePacket(uuid2, gameID, 0,5,0,4 );
//        obj = movePacket(uuid1, gameID, 8,5);
//        obj = movePacket(uuid1, gameID, 7,5,6,5 );
//        obj = movePacket(uuid1, gameID, 6,5,5,5 );
//        obj = movePacket(uuid1, gameID, 4,5,3,5 );
//        obj = movePacket(uuid1, gameID, 3,5,2,5 );
//        obj = movePacket(uuid1, gameID, 2,5,1,5 );
//        obj = movePacket(uuid1, gameID, 1,5,0,5 );








    }

//    @Test
//    public void sessionTest2() {
//
//        JSONObject obj = mainPacketTest("");
//
//        for (String key : obj.keySet())
//            System.out.println(obj.get(key));
//    }

//    @Test
//    public void gameTwoPlayer() {
//        JSONObject obj = mainPacketTest("{" +
//                "\"function\":\"STARTSESSION\"," +
//                "\"data\":" +
//                "   {" +
//                "   \"login\":true," +
//                "   \"username\":\"player1\"," +
//                "   \"password\":\"111\"" +
//                "   }" +
//                "}");
//
//        String uuid1 = (String) obj.get("id");
////        for(String key : obj.keySet())
////            System.out.println(obj.get(key));
//
//        obj = mainPacketTest("{" +
//                "\"function\":\"STARTSESSION\"," +
//                "\"data\":" +
//                "   {" +
//                "   \"login\":true," +
//                "   \"username\":\"player2\"," +
//                "   \"password\":\"123\"" +
//                "   }" +
//                "}");
//        String uuid2 = (String) obj.get("id");
//
//        obj = mainPacketTest("{" +
//                "\"function\":\"STARTSESSION\"," +
//                "\"data\":" +
//                "   {" +
//                "   \"login\":true," +
//                "   \"username\":\"player3\"," +
//                "   \"password\":\"123\"" +
//                "   }" +
//                "}");
//        String uuid3 = (String) obj.get("id");
//
//        obj = mainPacketTest("{" +
//                "\"function\":\"STARTSESSION\"," +
//                "\"data\":" +
//                "   {" +
//                "   \"login\":true," +
//                "   \"username\":\"player4\"," +
//                "   \"password\":\"123\"" +
//                "   }" +
//                "}");
//        String uuid4 = (String) obj.get("id");
//
//        obj = mainPacketTest("{" +
//                "\"uuid\":\"" + uuid1 + "\"," +
//                "\"function\":\"CREATELOBBY\"," +
//                "\"data\":" +
//                "   {" +
//                "   }" +
//                "}");
//        String gameID = (String) obj.get("session");
//        for (String key : obj.keySet())
//            System.out.println(obj.get(key));
//
//
//        obj = mainPacketTest("{" +
//                "\"uuid\":\"" + uuid2 + "\"," +
//                "\"session\":\"" + gameID + "\"," +
//                "\"function\":\"JOINLOBBY\"," +
//                "\"data\":" +
//                "   {" +
//                "   }" +
//                "}");
//    }

//    @Test
//    public void gameTwoPlayerKick() {
//        JSONObject obj = mainPacketTest("{" +
//                "\"function\":\"STARTSESSION\"," +
//                "\"data\":" +
//                "   {" +
//                "   \"login\":true," +
//                "   \"username\":\"player1\"," +
//                "   \"password\":\"111\"" +
//                "   }" +
//                "}");
//
//        String uuid1 = (String) obj.get("id");
////        for(String key : obj.keySet())
////            System.out.println(obj.get(key));
//
//        obj = mainPacketTest("{" +
//                "\"function\":\"STARTSESSION\"," +
//                "\"data\":" +
//                "   {" +
//                "   \"login\":true," +
//                "   \"username\":\"player2\"," +
//                "   \"password\":\"123\"" +
//                "   }" +
//                "}");
//        String uuid2 = (String) obj.get("id");
//
//        obj = mainPacketTest("{" +
//                "\"function\":\"STARTSESSION\"," +
//                "\"data\":" +
//                "   {" +
//                "   \"login\":true," +
//                "   \"username\":\"player3\"," +
//                "   \"password\":\"123\"" +
//                "   }" +
//                "}");
//        String uuid3 = (String) obj.get("id");
//
//        obj = mainPacketTest("{" +
//                "\"function\":\"STARTSESSION\"," +
//                "\"data\":" +
//                "   {" +
//                "   \"login\":true," +
//                "   \"username\":\"player4\"," +
//                "   \"password\":\"123\"" +
//                "   }" +
//                "}");
//        String uuid4 = (String) obj.get("id");
//
//        obj = mainPacketTest("{" +
//                "\"uuid\":\"" + uuid1 + "\"," +
//                "\"function\":\"CREATELOBBY\"," +
//                "\"data\":" +
//                "   {" +
//                "   }" +
//                "}");
//        String gameID = (String) obj.get("session");
//        for (String key : obj.keySet())
//            System.out.println(obj.get(key));
//
//        obj = mainPacketTest("{" +
//                "\"uuid\":\"" + uuid2 + "\"," +
//                "\"session\":\"" + gameID + "\"," +
//                "\"function\":\"JOINLOBBY\"," +
//                "\"data\":" +
//                "   {" +
//                "   }" +
//                "}");
//
//
//        obj = mainPacketTest("{" +
//                "\"uuid\":\"" + uuid1 + "\"," +
//                "\"session\":\"" + gameID + "\"," +
//                "\"function\":\"KICKPLAYER\"," +
//                "\"data\":" +
//                "   {" +
//                "\"player\": 1" +
//                "   }" +
//                "}");
//
//
//    }

//    @Test
//    public void gameFourPlayer() {
//        JSONObject obj = mainPacketTest("{" +
//                "\"function\":\"STARTSESSION\"," +
//                "\"data\":" +
//                "   {" +
//                "   \"login\":true," +
//                "   \"username\":\"player1\"," +
//                "   \"password\":\"111\"" +
//                "   }" +
//                "}");
//
//        String uuid1 = (String) obj.get("id");
////        for(String key : obj.keySet())
////            System.out.println(obj.get(key));
//
//        obj = mainPacketTest("{" +
//                "\"function\":\"STARTSESSION\"," +
//                "\"data\":" +
//                "   {" +
//                "   \"login\":true," +
//                "   \"username\":\"player2\"," +
//                "   \"password\":\"123\"" +
//                "   }" +
//                "}");
//        String uuid2 = (String) obj.get("id");
//
//        obj = mainPacketTest("{" +
//                "\"function\":\"STARTSESSION\"," +
//                "\"data\":" +
//                "   {" +
//                "   \"login\":true," +
//                "   \"username\":\"player3\"," +
//                "   \"password\":\"123\"" +
//                "   }" +
//                "}");
//        String uuid3 = (String) obj.get("id");
//
//        obj = mainPacketTest("{" +
//                "\"function\":\"STARTSESSION\"," +
//                "\"data\":" +
//                "   {" +
//                "   \"login\":true," +
//                "   \"username\":\"player4\"," +
//                "   \"password\":\"123\"" +
//                "   }" +
//                "}");
//        String uuid4 = (String) obj.get("id");
//
//        obj = mainPacketTest("{" +
//                "\"uuid\":\"" + uuid1 + "\"," +
//                "\"function\":\"CREATELOBBY\"," +
//                "\"data\":" +
//                "   {" +
//                "   }" +
//                "}");
//        String gameID = (String) obj.get("session");
//        for (String key : obj.keySet())
//            System.out.println(obj.get(key));
//
//
//        obj = mainPacketTest("{" +
//                "\"uuid\":\"" + uuid2 + "\"," +
//                "\"session\":\"" + gameID + "\"," +
//                "\"function\":\"JOINLOBBY\"," +
//                "\"data\":" +
//                "   {" +
//                "   }" +
//                "}");
//
//        obj = mainPacketTest("{" +
//                "\"uuid\":\"" + uuid1 + "\"," +
//                "\"session\":\"" + gameID + "\"," +
//                "\"function\":\"CHANGESETTINGS\"," +
//                "\"data\":" +
//                "   {" +
//                "\"player_count\": 4," +
//                "\"host\": 0" +
//                "   }" +
//                "}");
//
//
//        obj = mainPacketTest("{" +
//                "\"uuid\":\"" + uuid3 + "\"," +
//                "\"session\":\"" + gameID + "\"," +
//                "\"function\":\"JOINLOBBY\"," +
//                "\"data\":" +
//                "   {" +
//                "   }" +
//                "}");
//
//        obj = mainPacketTest("{" +
//                "\"uuid\":\"" + uuid4 + "\"," +
//                "\"session\":\"" + gameID + "\"," +
//                "\"function\":\"JOINLOBBY\"," +
//                "\"data\":" +
//                "   {" +
//                "   }" +
//                "}");
//
//
//    }

    @Test
    public void gameFourPlayerLeave() {
        JSONObject obj = mainPacketTest("{" +
                "\"function\":\"STARTSESSION\"," +
                "\"data\":" +
                "   {" +
                "   \"login\":true," +
                "   \"username\":\"player1\"," +
                "   \"password\":\"111\"" +
                "   }" +
                "}");

        String uuid1 = (String) obj.get("id");
//        for(String key : obj.keySet())
//            System.out.println(obj.get(key));

        obj = mainPacketTest("{" +
                "\"function\":\"STARTSESSION\"," +
                "\"data\":" +
                "   {" +
                "   \"login\":true," +
                "   \"username\":\"player2\"," +
                "   \"password\":\"123\"" +
                "   }" +
                "}");
        String uuid2 = (String) obj.get("id");

        obj = mainPacketTest("{" +
                "\"function\":\"STARTSESSION\"," +
                "\"data\":" +
                "   {" +
                "   \"login\":true," +
                "   \"username\":\"player3\"," +
                "   \"password\":\"123\"" +
                "   }" +
                "}");
        String uuid3 = (String) obj.get("id");

        obj = mainPacketTest("{" +
                "\"function\":\"STARTSESSION\"," +
                "\"data\":" +
                "   {" +
                "   \"login\":true," +
                "   \"username\":\"player4\"," +
                "   \"password\":\"123\"" +
                "   }" +
                "}");
        String uuid4 = (String) obj.get("id");

        obj = mainPacketTest("{" +
                "\"uuid\":\"" + uuid1 + "\"," +
                "\"function\":\"CREATELOBBY\"," +
                "\"data\":" +
                "   {" +
                "   }" +
                "}");
        String gameID = (String) obj.get("session");
        for (String key : obj.keySet())
            System.out.println(obj.get(key));


        obj = mainPacketTest("{" +
                "\"uuid\":\"" + uuid2 + "\"," +
                "\"session\":\"" + gameID + "\"," +
                "\"function\":\"JOINLOBBY\"," +
                "\"data\":" +
                "   {" +
                "   }" +
                "}");

        obj = mainPacketTest("{" +
                "\"uuid\":\"" + uuid1 + "\"," +
                "\"session\":\"" + gameID + "\"," +
                "\"function\":\"CHANGESETTINGS\"," +
                "\"data\":" +
                "   {" +
                "\"player_count\": 4," +
                "\"host\": 0" +
                "   }" +
                "}");


        obj = mainPacketTest("{" +
                "\"uuid\":\"" + uuid3 + "\"," +
                "\"session\":\"" + gameID + "\"," +
                "\"function\":\"JOINLOBBY\"," +
                "\"data\":" +
                "   {" +
                "   }" +
                "}");

        obj = mainPacketTest("{" +
                "\"uuid\":\"" + uuid4 + "\"," +
                "\"session\":\"" + gameID + "\"," +
                "\"function\":\"JOINLOBBY\"," +
                "\"data\":" +
                "   {" +
                "   }" +
                "}");

        obj = mainPacketTest("{" +
                "\"uuid\":\"" + uuid1 + "\"," +
                "\"session\":\"" + gameID + "\"," +
                "\"function\":\"KICKPLAYER\"," +
                "\"data\":" +
                "   {" +
                "\"player\": 3" +
                "   }" +
                "}");

        obj = mainPacketTest("{" +
                "\"uuid\":\"" + uuid1 + "\"," +
                "\"session\":\"" + gameID + "\"," +
                "\"function\":\"KICKPLAYER\"," +
                "\"data\":" +
                "   {" +
                "\"player\": 2" +
                "   }" +
                "}");

        obj = mainPacketTest("{" +
                "\"uuid\":\"" + uuid1 + "\"," +
                "\"session\":\"" + gameID + "\"," +
                "\"function\":\"KICKPLAYER\"," +
                "\"data\": " +
                "   {" +
                "\"player\": 1" +
                "   }" +
                "}");

        obj = mainPacketTest("{" +
                "\"uuid\":\"" + uuid2 + "\"," +
                "\"session\":\"" + gameID + "\"," +
                "\"function\":\"JOINLOBBY\"," +
                "\"data\":" +
                "   {" +
                "   }" +
                "}");

        obj = mainPacketTest("{" +
                "\"uuid\":\"" + uuid3 + "\"," +
                "\"session\":\"" + gameID + "\"," +
                "\"function\":\"JOINLOBBY\"," +
                "\"data\":" +
                "   {" +
                "   }" +
                "}");

        obj = mainPacketTest("{" +
                "\"uuid\":\"" + uuid4 + "\"," +
                "\"session\":\"" + gameID + "\"," +
                "\"function\":\"JOINLOBBY\"," +
                "\"data\":" +
                "   {" +
                "   }" +
                "}");

        obj = mainPacketTest("{" +
                "\"uuid\":\"" + uuid2 + "\"," +
                "\"session\":\"" + gameID + "\"," +
                "\"function\":\"LEAVELOBBY\"," +
                "\"data\":" +
                "   {" +
                "   }" +
                "}");

        obj = mainPacketTest("{" +
                "\"uuid\":\"" + uuid3 + "\"," +
                "\"session\":\"" + gameID + "\"," +
                "\"function\":\"LEAVELOBBY\"," +
                "\"data\":" +
                "   {" +
                "   }" +
                "}");
        obj = mainPacketTest("{" +
                "\"uuid\":\"" + uuid4 + "\"," +
                "\"session\":\"" + gameID + "\"," +
                "\"function\":\"LEAVELOBBY\"," +
                "\"data\":" +
                "   {" +
                "   }" +
                "}");

        obj = mainPacketTest("{" +
                "\"uuid\":\"" + uuid2 + "\"," +
                "\"session\":\"" + gameID + "\"," +
                "\"function\":\"JOINLOBBY\"," +
                "\"data\":" +
                "   {" +
                "   }" +
                "}");

        obj = mainPacketTest("{" +
                "\"uuid\":\"" + uuid3 + "\"," +
                "\"session\":\"" + gameID + "\"," +
                "\"function\":\"JOINLOBBY\"," +
                "\"data\":" +
                "   {" +
                "   }" +
                "}");

        obj = mainPacketTest("{" +
                "\"uuid\":\"" + uuid4 + "\"," +
                "\"session\":\"" + gameID + "\"," +
                "\"function\":\"JOINLOBBY\"," +
                "\"data\":" +
                "   {" +
                "   }" +
                "}");
    }

    @Test
    public void SettingsGet(){
        JSONObject obj = mainPacketTest("{" +
                "\"function\":\"STARTSESSION\"," +
                "\"data\":" +
                "   {" +
                "   \"login\":true," +
                "   \"username\":\"player1\"," +
                "   \"password\":\"111\"" +
                "   }" +
                "}");

        String uuid1 = (String) obj.get("id");
//        for(String key : obj.keySet())
//            System.out.println(obj.get(key));

        obj = mainPacketTest("{" +
                "\"function\":\"STARTSESSION\"," +
                "\"data\":" +
                "   {" +
                "   \"login\":true," +
                "   \"username\":\"player2\"," +
                "   \"password\":\"123\"" +
                "   }" +
                "}");
        String uuid2 = (String) obj.get("id");

        obj = mainPacketTest("{" +
                "\"function\":\"STARTSESSION\"," +
                "\"data\":" +
                "   {" +
                "   \"login\":true," +
                "   \"username\":\"player3\"," +
                "   \"password\":\"123\"" +
                "   }" +
                "}");
        String uuid3 = (String) obj.get("id");

        obj = mainPacketTest("{" +
                "\"function\":\"STARTSESSION\"," +
                "\"data\":" +
                "   {" +
                "   \"login\":true," +
                "   \"username\":\"player4\"," +
                "   \"password\":\"123\"" +
                "   }" +
                "}");
        String uuid4 = (String) obj.get("id");

        obj = mainPacketTest("{" +
                "\"uuid\":\"" + uuid1 + "\"," +
                "\"function\":\"CREATELOBBY\"," +
                "\"data\":" +
                "   {" +
                "   }" +
                "}");
        String gameID = (String) obj.get("session");
        for (String key : obj.keySet())
            System.out.println(obj.get(key));


        obj = mainPacketTest("{" +
                "\"uuid\":\"" + uuid2 + "\"," +
                "\"session\":\"" + gameID + "\"," +
                "\"function\":\"JOINLOBBY\"," +
                "\"data\":" +
                "   {" +
                "   }" +
                "}");

        obj = mainPacketTest("{" +
                "\"uuid\":\"" + uuid1 + "\"," +
                "\"session\":\"" + gameID + "\"," +
                "\"function\":\"CHANGESETTINGS\"," +
                "\"data\":" +
                "   {" +
                "\"player_count\": 4," +
                "\"host\": 0" +
                "   }" +
                "}");


        obj = mainPacketTest("{" +
                "\"uuid\":\"" + uuid3 + "\"," +
                "\"session\":\"" + gameID + "\"," +
                "\"function\":\"JOINLOBBY\"," +
                "\"data\":" +
                "   {" +
                "   }" +
                "}");

        obj = mainPacketTest("{" +
                "\"uuid\":\"" + uuid4 + "\"," +
                "\"session\":\"" + gameID + "\"," +
                "\"function\":\"JOINLOBBY\"," +
                "\"data\":" +
                "   {" +
                "   }" +
                "}");

        obj = mainPacketTest("{" +
                "\"uuid\":\"" + uuid1 + "\"," +
                "\"session\":\"" + gameID + "\"," +
                "\"function\":\"GETSETTINGS\"," +
                "\"data\":" +
                "   {" +
                "   }" +
                "}");

        obj = mainPacketTest("{" +
                "\"uuid\":\"" + uuid1 + "\"," +
                "\"session\":\"" + gameID + "\"," +
                "\"function\":\"GETLOBBYSTATE\"," +
                "\"data\":" +
                "   {" +
                "   }" +
                "}");
    }

//    @Test
//    public void gameStart(){
//        JSONObject obj = mainPacketTest("{" +
//                "\"function\":\"STARTSESSION\"," +
//                "\"data\":" +
//                "   {" +
//                "   \"login\":true," +
//                "   \"username\":\"player1\"," +
//                "   \"password\":\"111\"" +
//                "   }" +
//                "}");
//
//        String uuid1 = (String) obj.get("id");
////        for(String key : obj.keySet())
////            System.out.println(obj.get(key));
//
//        obj = mainPacketTest("{" +
//                "\"function\":\"STARTSESSION\"," +
//                "\"data\":" +
//                "   {" +
//                "   \"login\":true," +
//                "   \"username\":\"player2\"," +
//                "   \"password\":\"123\"" +
//                "   }" +
//                "}");
//        String uuid2 = (String) obj.get("id");
//
//        obj = mainPacketTest("{" +
//                "\"function\":\"STARTSESSION\"," +
//                "\"data\":" +
//                "   {" +
//                "   \"login\":true," +
//                "   \"username\":\"player3\"," +
//                "   \"password\":\"123\"" +
//                "   }" +
//                "}");
//        String uuid3 = (String) obj.get("id");
//
//        obj = mainPacketTest("{" +
//                "\"function\":\"STARTSESSION\"," +
//                "\"data\":" +
//                "   {" +
//                "   \"login\":true," +
//                "   \"username\":\"player4\"," +
//                "   \"password\":\"123\"" +
//                "   }" +
//                "}");
//        String uuid4 = (String) obj.get("id");
//
//        obj = mainPacketTest("{" +
//                "\"uuid\":\"" + uuid1 + "\"," +
//                "\"function\":\"CREATELOBBY\"," +
//                "\"data\":" +
//                "   {" +
//                "   }" +
//                "}");
//        String gameID = (String) obj.get("session");
//        for (String key : obj.keySet())
//            System.out.println(obj.get(key));
//
//
//        obj = mainPacketTest("{" +
//                "\"uuid\":\"" + uuid2 + "\"," +
//                "\"session\":\"" + gameID + "\"," +
//                "\"function\":\"JOINLOBBY\"," +
//                "\"data\":" +
//                "   {" +
//                "   }" +
//                "}");
//
//        obj = mainPacketTest("{" +
//                "\"uuid\":\"" + uuid1 + "\"," +
//                "\"session\":\"" + gameID + "\"," +
//                "\"function\":\"CHANGESETTINGS\"," +
//                "\"data\":" +
//                "   {" +
//                "\"player_count\": 4," +
//                "\"host\": 0" +
//                "   }" +
//                "}");
//
//
//        obj = mainPacketTest("{" +
//                "\"uuid\":\"" + uuid3 + "\"," +
//                "\"session\":\"" + gameID + "\"," +
//                "\"function\":\"JOINLOBBY\"," +
//                "\"data\":" +
//                "   {" +
//                "   }" +
//                "}");
//
//        obj = mainPacketTest("{" +
//                "\"uuid\":\"" + uuid4 + "\"," +
//                "\"session\":\"" + gameID + "\"," +
//                "\"function\":\"JOINLOBBY\"," +
//                "\"data\":" +
//                "   {" +
//                "   }" +
//                "}");
//
//        obj = mainPacketTest("{" +
//                "\"uuid\":\"" + uuid1 + "\"," +
//                "\"session\":\"" + gameID + "\"," +
//                "\"function\":\"STARTGAME\"," +
//                "\"data\": " +
//                "   {" +
//
//                "   }" +
//                "}");
//
//    }

//    @Test
//    public void LobbyState(){
//        JSONObject obj = mainPacketTest("{" +
//                "\"function\":\"STARTSESSION\"," +
//                "\"data\":" +
//                "   {" +
//                "   \"login\":true," +
//                "   \"username\":\"player1\"," +
//                "   \"password\":\"111\"" +
//                "   }" +
//                "}");
//
//        String uuid1 = (String) obj.get("id");
////        for(String key : obj.keySet())
////            System.out.println(obj.get(key));
//
//        obj = mainPacketTest("{" +
//                "\"function\":\"STARTSESSION\"," +
//                "\"data\":" +
//                "   {" +
//                "   \"login\":true," +
//                "   \"username\":\"player2\"," +
//                "   \"password\":\"123\"" +
//                "   }" +
//                "}");
//        String uuid2 = (String) obj.get("id");
//
//        obj = mainPacketTest("{" +
//                "\"function\":\"STARTSESSION\"," +
//                "\"data\":" +
//                "   {" +
//                "   \"login\":true," +
//                "   \"username\":\"player3\"," +
//                "   \"password\":\"123\"" +
//                "   }" +
//                "}");
//        String uuid3 = (String) obj.get("id");
//
//        obj = mainPacketTest("{" +
//                "\"function\":\"STARTSESSION\"," +
//                "\"data\":" +
//                "   {" +
//                "   \"login\":true," +
//                "   \"username\":\"player4\"," +
//                "   \"password\":\"123\"" +
//                "   }" +
//                "}");
//        String uuid4 = (String) obj.get("id");
//
//        obj = mainPacketTest("{" +
//                "\"uuid\":\"" + uuid1 + "\"," +
//                "\"function\":\"CREATELOBBY\"," +
//                "\"data\":" +
//                "   {" +
//                "   }" +
//                "}");
//        String gameID = (String) obj.get("session");
//        for (String key : obj.keySet())
//            System.out.println(obj.get(key));
//
//
//        obj = mainPacketTest("{" +
//                "\"uuid\":\"" + uuid2 + "\"," +
//                "\"session\":\"" + gameID + "\"," +
//                "\"function\":\"JOINLOBBY\"," +
//                "\"data\":" +
//                "   {" +
//                "   }" +
//                "}");
//
//        obj = mainPacketTest("{" +
//                "\"uuid\":\"" + uuid1 + "\"," +
//                "\"session\":\"" + gameID + "\"," +
//                "\"function\":\"CHANGESETTINGS\"," +
//                "\"data\":" +
//                "   {" +
//                "\"player_count\": 4," +
//                "\"host\": 0" +
//                "   }" +
//                "}");
//
//
//        obj = mainPacketTest("{" +
//                "\"uuid\":\"" + uuid3 + "\"," +
//                "\"session\":\"" + gameID + "\"," +
//                "\"function\":\"JOINLOBBY\"," +
//                "\"data\":" +
//                "   {" +
//                "   }" +
//                "}");
//
//        obj = mainPacketTest("{" +
//                "\"uuid\":\"" + uuid4 + "\"," +
//                "\"session\":\"" + gameID + "\"," +
//                "\"function\":\"JOINLOBBY\"," +
//                "\"data\":" +
//                "   {" +
//                "   }" +
//                "}");
//
//        obj = mainPacketTest("{" +
//                "\"uuid\":\"" + uuid1 + "\"," +
//                "\"session\":\"" + gameID + "\"," +
//                "\"function\":\"GETLOBBYSTATE\"," +
//                "\"data\":" +
//                "   {" +
//                "   }" +
//                "}");
//
//    }





//    @Test
//    public void LeaveLobby(){
//        JSONObject obj = mainPacketTest("{" +
//                "\"function\":\"STARTSESSION\"," +
//                "\"data\":" +
//                "   {" +
//                "   \"login\":true," +
//                "   \"username\":\"player1\"," +
//                "   \"password\":\"111\"" +
//                "   }" +
//                "}");
//
//        String uuid1 = (String) obj.get("id");
////        for(String key : obj.keySet())
////            System.out.println(obj.get(key));
//
//        obj = mainPacketTest("{" +
//                "\"function\":\"STARTSESSION\"," +
//                "\"data\":" +
//                "   {" +
//                "   \"login\":true," +
//                "   \"username\":\"player2\"," +
//                "   \"password\":\"123\"" +
//                "   }" +
//                "}");
//        String uuid2 = (String) obj.get("id");
//
//        obj = mainPacketTest("{" +
//                "\"function\":\"STARTSESSION\"," +
//                "\"data\":" +
//                "   {" +
//                "   \"login\":true," +
//                "   \"username\":\"player3\"," +
//                "   \"password\":\"123\"" +
//                "   }" +
//                "}");
//        String uuid3 = (String) obj.get("id");
//
//        obj = mainPacketTest("{" +
//                "\"function\":\"STARTSESSION\"," +
//                "\"data\":" +
//                "   {" +
//                "   \"login\":true," +
//                "   \"username\":\"player4\"," +
//                "   \"password\":\"123\"" +
//                "   }" +
//                "}");
//        String uuid4 = (String) obj.get("id");
//
//        obj = mainPacketTest("{" +
//                "\"uuid\":\"" + uuid1 + "\"," +
//                "\"function\":\"CREATELOBBY\"," +
//                "\"data\":" +
//                "   {" +
//                "   }" +
//                "}");
//        String gameID = (String) obj.get("session");
//        for (String key : obj.keySet())
//            System.out.println(obj.get(key));
//
//
//        obj = mainPacketTest("{" +
//                "\"uuid\":\"" + uuid2 + "\"," +
//                "\"session\":\"" + gameID + "\"," +
//                "\"function\":\"JOINLOBBY\"," +
//                "\"data\":" +
//                "   {" +
//                "   }" +
//                "}");
//
//        obj = mainPacketTest("{" +
//                "\"uuid\":\"" + uuid1 + "\"," +
//                "\"session\":\"" + gameID + "\"," +
//                "\"function\":\"CHANGESETTINGS\"," +
//                "\"data\":" +
//                "   {" +
//                "\"player_count\": 4," +
//                "\"host\": 0" +
//                "   }" +
//                "}");
//
//
//        obj = mainPacketTest("{" +
//                "\"uuid\":\"" + uuid3 + "\"," +
//                "\"session\":\"" + gameID + "\"," +
//                "\"function\":\"JOINLOBBY\"," +
//                "\"data\":" +
//                "   {" +
//                "   }" +
//                "}");
//
//        obj = mainPacketTest("{" +
//                "\"uuid\":\"" + uuid4 + "\"," +
//                "\"session\":\"" + gameID + "\"," +
//                "\"function\":\"JOINLOBBY\"," +
//                "\"data\":" +
//                "   {" +
//                "   }" +
//                "}");
//
//        obj = mainPacketTest("{" +
//                "\"uuid\":\"" + uuid4 + "\"," +
//                "\"session\":\"" + gameID + "\"," +
//                "\"function\":\"LEAVELOBBY\"," +
//                "\"data\":" +
//                "   {" +
//                "   }" +
//                "}");
//    }

//    @Test
//    public void SendChat(){
//        JSONObject obj = mainPacketTest("{" +
//                "\"function\":\"STARTSESSION\"," +
//                "\"data\":" +
//                "   {" +
//                "   \"login\":true," +
//                "   \"username\":\"player1\"," +
//                "   \"password\":\"111\"" +
//                "   }" +
//                "}");
//
//        String uuid1 = (String) obj.get("id");
////        for(String key : obj.keySet())
////            System.out.println(obj.get(key));
//
//        obj = mainPacketTest("{" +
//                "\"function\":\"STARTSESSION\"," +
//                "\"data\":" +
//                "   {" +
//                "   \"login\":true," +
//                "   \"username\":\"player2\"," +
//                "   \"password\":\"123\"" +
//                "   }" +
//                "}");
//        String uuid2 = (String) obj.get("id");
//
//        obj = mainPacketTest("{" +
//                "\"function\":\"STARTSESSION\"," +
//                "\"data\":" +
//                "   {" +
//                "   \"login\":true," +
//                "   \"username\":\"player3\"," +
//                "   \"password\":\"123\"" +
//                "   }" +
//                "}");
//        String uuid3 = (String) obj.get("id");
//
//        obj = mainPacketTest("{" +
//                "\"function\":\"STARTSESSION\"," +
//                "\"data\":" +
//                "   {" +
//                "   \"login\":true," +
//                "   \"username\":\"player4\"," +
//                "   \"password\":\"123\"" +
//                "   }" +
//                "}");
//        String uuid4 = (String) obj.get("id");
//
//        obj = mainPacketTest("{" +
//                "\"uuid\":\"" + uuid1 + "\"," +
//                "\"function\":\"CREATELOBBY\"," +
//                "\"data\":" +
//                "   {" +
//                "   }" +
//                "}");
//        String gameID = (String) obj.get("session");
//        for (String key : obj.keySet())
//            System.out.println(obj.get(key));
//
//
//        obj = mainPacketTest("{" +
//                "\"uuid\":\"" + uuid2 + "\"," +
//                "\"session\":\"" + gameID + "\"," +
//                "\"function\":\"JOINLOBBY\"," +
//                "\"data\":" +
//                "   {" +
//                "   }" +
//                "}");
//
//        obj = mainPacketTest("{" +
//                "\"uuid\":\"" + uuid1 + "\"," +
//                "\"session\":\"" + gameID + "\"," +
//                "\"function\":\"CHANGESETTINGS\"," +
//                "\"data\":" +
//                "   {" +
//                "\"player_count\": 4," +
//                "\"host\": 0" +
//                "   }" +
//                "}");
//
//
//        obj = mainPacketTest("{" +
//                "\"uuid\":\"" + uuid3 + "\"," +
//                "\"session\":\"" + gameID + "\"," +
//                "\"function\":\"JOINLOBBY\"," +
//                "\"data\":" +
//                "   {" +
//                "   }" +
//                "}");
//
//        obj = mainPacketTest("{" +
//                "\"uuid\":\"" + uuid4 + "\"," +
//                "\"session\":\"" + gameID + "\"," +
//                "\"function\":\"JOINLOBBY\"," +
//                "\"data\":" +
//                "   {" +
//                "   }" +
//                "}");
//        obj = mainPacketTest("{" +
//                "\"uuid\":\"" + uuid1 + "\"," +
//                "\"session\":\"" + gameID + "\"," +
//                "\"function\":\"STARTGAME\"," +
//                "\"data\": " +
//                "   {" +
//
//                "   }" +
//                "}");
//
//        obj = mainPacketTest("{" +
//                "\"uuid\":\"" + uuid1 + "\"," +
//                "\"session\":\"" + gameID + "\"," +
//                "\"function\":\"SENDCHAT\"," +
//                "\"data\":" +
//                "   {" +
//                "\"chatMessage\":\"YOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO\"" +
//                "   }" +
//                "}");
//    }

    @Test
    public void ChatGet(){
        JSONObject obj = mainPacketTest("{" +
                "\"function\":\"STARTSESSION\"," +
                "\"data\":" +
                "   {" +
                "   \"login\":true," +
                "   \"username\":\"player1\"," +
                "   \"password\":\"111\"" +
                "   }" +
                "}");

        String uuid1 = (String) obj.get("id");
//        for(String key : obj.keySet())
//            System.out.println(obj.get(key));

        obj = mainPacketTest("{" +
                "\"function\":\"STARTSESSION\"," +
                "\"data\":" +
                "   {" +
                "   \"login\":true," +
                "   \"username\":\"player2\"," +
                "   \"password\":\"123\"" +
                "   }" +
                "}");
        String uuid2 = (String) obj.get("id");

        obj = mainPacketTest("{" +
                "\"function\":\"STARTSESSION\"," +
                "\"data\":" +
                "   {" +
                "   \"login\":true," +
                "   \"username\":\"player3\"," +
                "   \"password\":\"123\"" +
                "   }" +
                "}");
        String uuid3 = (String) obj.get("id");

        obj = mainPacketTest("{" +
                "\"function\":\"STARTSESSION\"," +
                "\"data\":" +
                "   {" +
                "   \"login\":true," +
                "   \"username\":\"player4\"," +
                "   \"password\":\"123\"" +
                "   }" +
                "}");
        String uuid4 = (String) obj.get("id");

        obj = mainPacketTest("{" +
                "\"uuid\":\"" + uuid1 + "\"," +
                "\"function\":\"CREATELOBBY\"," +
                "\"data\":" +
                "   {" +
                "   }" +
                "}");
        String gameID = (String) obj.get("session");
        for (String key : obj.keySet())
            System.out.println(obj.get(key));


        obj = mainPacketTest("{" +
                "\"uuid\":\"" + uuid2 + "\"," +
                "\"session\":\"" + gameID + "\"," +
                "\"function\":\"JOINLOBBY\"," +
                "\"data\":" +
                "   {" +
                "   }" +
                "}");

        obj = mainPacketTest("{" +
                "\"uuid\":\"" + uuid1 + "\"," +
                "\"session\":\"" + gameID + "\"," +
                "\"function\":\"CHANGESETTINGS\"," +
                "\"data\":" +
                "   {" +
                "\"player_count\": 4," +
                "\"host\": 0" +
                "   }" +
                "}");


        obj = mainPacketTest("{" +
                "\"uuid\":\"" + uuid3 + "\"," +
                "\"session\":\"" + gameID + "\"," +
                "\"function\":\"JOINLOBBY\"," +
                "\"data\":" +
                "   {" +
                "   }" +
                "}");

        obj = mainPacketTest("{" +
                "\"uuid\":\"" + uuid4 + "\"," +
                "\"session\":\"" + gameID + "\"," +
                "\"function\":\"JOINLOBBY\"," +
                "\"data\":" +
                "   {" +
                "   }" +
                "}");
        obj = mainPacketTest("{" +
                "\"uuid\":\"" + uuid1 + "\"," +
                "\"session\":\"" + gameID + "\"," +
                "\"function\":\"STARTGAME\"," +
                "\"data\": " +
                "   {" +

                "   }" +
                "}");

        obj = mainPacketTest("{" +
                "\"uuid\":\"" + uuid1 + "\"," +
                "\"session\":\"" + gameID + "\"," +
                "\"function\":\"SENDCHAT\"," +
                "\"data\":" +
                "   {" +
                "\"chatMessage\":\"YOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO\"" +
                "   }" +
                "}");

        obj = mainPacketTest("{" +
                "\"uuid\":\"" + uuid1 + "\"," +
                "\"session\":\"" + gameID + "\"," +
                "\"function\":\"GETCHAT\"," +
                "\"data\":" +
                "   {" +

                "   }" +
                "}");

        obj = mainPacketTest("{" +
                "\"uuid\":\"" + uuid1 + "\"," +
                "\"session\":\"" + gameID + "\"," +
                "\"function\":\"GETPLAYERS\"," +
                "\"data\":" +
                "   {" +
                "   }" +
                "}");

        obj = mainPacketTest("{" +
                "\"uuid\":\"" + uuid1 + "\"," +
                "\"session\":\"" + gameID + "\"," +
                "\"function\":\"GETSETTINGS\"," +
                "\"data\":" +
                "   {" +
                "   }" +
                "}");

        obj = mainPacketTest("{" +
                "\"uuid\":\"" + uuid1 + "\"," +
                "\"session\":\"" + gameID + "\"," +
                "\"function\":\"GETLOBBYSTATE\"," +
                "\"data\":" +
                "   {" +
                "   }" +
                "}");
    }

    

//        JSONObject obj = mainPacketTest("{" +
//                "\"function\":\"STARTSESSION\"," +
//                "\"data\":" +
//                "   {" +
//                "   \"login\":true," +
//                "   \"username\":\"sometester\"," +
//                "   \"password\":\"blarg\"" +
//                "   }" +
//                "}");
//
//        String uuid = (String) obj.get("id");
//
//        obj = mainPacketTest("{" +
//                "\"uuid\":\"" + uuid + "\"," +
//                "\"function\":\"CREATELOBBY\"," +
//                "\"data\":" +
//                "   {" +
//                "   }" +
//                "}");
//
//        String gameId = (String) obj.get("session");
//
//        obj = mainPacketTest("{" +
//                "\"function\":\"STARTSESSION\"," +
//                "\"data\":" +
//                "   {" +
//                "   \"login\":true," +
//                "   \"username\":\"slomslester\"," +
//                "   \"password\":\"blarg\"" +
//                "   }" +
//                "}");
//
//        String uuid2 = (String) obj.get("id");
//
//        obj = mainPacketTest("{" +
//                "\"uuid\":\"" + uuid2 + "\"," +
//                "\"session\":\"" + gameId + "\"," +
//                "\"function\":\"JOINLOBBY\"," +
//                "\"data\":" +
//                "   {" +
//                "   }" +
//                "}");
//
//        obj = mainPacketTest("{" +
//                "\"uuid\":\"" + uuid + "\"," +
//                "\"session\":\"" + gameId + "\"," +
//                "\"function\":\"STARTGAME\"," +
//                "\"data\":" +
//                "   {" +
//                "   }" +
//                "}");
//
//        obj = movePacket(uuid2, gameId, 1, 4);
//
//        obj = movePacket(uuid, gameId, 7, 4);
//
//    }
//
    @Test
    public void gameWalls() {

        JSONObject obj = mainPacketTest("{" +
                "\"function\":\"STARTSESSION\"," +
                "\"data\":" +
                "   {" +
                "   \"login\":true," +
                "   \"username\":\"player1\"," +
                "   \"password\":\"111\"" +
                "   }" +
                "}");

        String uuid1 = (String) obj.get("id");
//        for(String key : obj.keySet())
//            System.out.println(obj.get(key));

        obj = mainPacketTest("{" +
                "\"function\":\"STARTSESSION\"," +
                "\"data\":" +
                "   {" +
                "   \"login\":true," +
                "   \"username\":\"player2\"," +
                "   \"password\":\"123\"" +
                "   }" +
                "}");
        String uuid2 = (String) obj.get("id");

        obj = mainPacketTest("{" +
                "\"function\":\"STARTSESSION\"," +
                "\"data\":" +
                "   {" +
                "   \"login\":true," +
                "   \"username\":\"player3\"," +
                "   \"password\":\"123\"" +
                "   }" +
                "}");
        String uuid3 = (String) obj.get("id");

        obj = mainPacketTest("{" +
                "\"function\":\"STARTSESSION\"," +
                "\"data\":" +
                "   {" +
                "   \"login\":true," +
                "   \"username\":\"player4\"," +
                "   \"password\":\"123\"" +
                "   }" +
                "}");
        String uuid4 = (String) obj.get("id");

        obj = mainPacketTest("{" +
                "\"uuid\":\"" + uuid1 + "\"," +
                "\"function\":\"CREATELOBBY\"," +
                "\"data\":" +
                "   {" +
                "   }" +
                "}");
        String gameID = (String) obj.get("session");
        for (String key : obj.keySet())
            System.out.println(obj.get(key));


        obj = mainPacketTest("{" +
                "\"uuid\":\"" + uuid2 + "\"," +
                "\"session\":\"" + gameID + "\"," +
                "\"function\":\"JOINLOBBY\"," +
                "\"data\":" +
                "   {" +
                "   }" +
                "}");

        obj = mainPacketTest("{" +
                "\"uuid\":\"" + uuid1 + "\"," +
                "\"session\":\"" + gameID + "\"," +
                "\"function\":\"CHANGESETTINGS\"," +
                "\"data\":" +
                "   {" +
                "\"player_count\": 4," +
                "\"host\": 0" +
                "   }" +
                "}");


        obj = mainPacketTest("{" +
                "\"uuid\":\"" + uuid3 + "\"," +
                "\"session\":\"" + gameID + "\"," +
                "\"function\":\"JOINLOBBY\"," +
                "\"data\":" +
                "   {" +
                "   }" +
                "}");

        obj = mainPacketTest("{" +
                "\"uuid\":\"" + uuid4 + "\"," +
                "\"session\":\"" + gameID + "\"," +
                "\"function\":\"JOINLOBBY\"," +
                "\"data\":" +
                "   {" +
                "   }" +
                "}");

        obj = mainPacketTest("{" +
                "\"uuid\":\"" + uuid1 + "\"," +
                "\"session\":\"" + gameID + "\"," +
                "\"function\":\"STARTGAME\"," +
                "\"data\":" +
                "   {" +
                "   }" +
                "}");

        obj = wallPacket(uuid1, gameID, 0, 0, 1);
        obj = movePacket(uuid1, gameID, 5, 9, 5,8);
        obj = wallPacket(uuid1, gameID, 9, 9, 1);
//        obj = movePacket(uuid2, gameID, 5, 8, 5,7);
    }

    private JSONObject wallPacket(String uuid, String gameId, int x, int y, int dir)
    {
        return mainPacketTest("{" +
                "\"uuid\":\"" + uuid + "\"," +
                "\"session\":\"" + gameId + "\"," +
                "\"function\":\"MOVEPLAYER\"," +
                "\"data\":" +
                "   {" +
                "    \"x\":" + x + "," +
                "    \"y\":" + y + "," +
                "    \"direction\":" + dir +
                "   }" +
                "}");
    }

    private JSONObject movePacket(String uuid, String gameId, int x1, int y1, int x2, int y2)
    {
        return mainPacketTest("{" +
                "\"uuid\":\"" + uuid + "\"," +
                "\"session\":\"" + gameId + "\"," +
                "\"function\":\"MOVEPLAYER\"," +
                "\"data\":" +
                "   {" +
                "    \"x2\":" + x2 + "," +
                "    \"y2\":" + y2 + "," +
                "    \"x1\":" + x1 + "," +
                "    \"y1\":" + y1 +
                "   }" +
                "}");
    }

    private JSONObject movePacket(String uuid, String gameId, int x, int y)
    {
        return movePacket(uuid, gameId, x, y, -1, -1);
    }
}



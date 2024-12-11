package com.dronerecon.ws;

        import javax.servlet.*;
        import javax.servlet.http.*;
        import java.io.*;
        import java.util.*;
        import java.security.SecureRandom;
        import java.util.HashMap;



public class DroneDataService extends HttpServlet{


    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json;charset=utf-8");
        response.addHeader("Access-Control-Allow-Origin", "*");

        PrintWriter out = response.getWriter();


        String area_id = request.getParameter("area_id");

        int tilex = Integer.parseInt(request.getParameter("tilex"));

        int tiley = Integer.parseInt(request.getParameter("tiley"));

        int totalcols = Integer.parseInt(request.getParameter("totalcols"));

        int totalrows = Integer.parseInt(request.getParamter("totalrows"));




        String sDirection = "right";


        if (tiley %2 == 0) {
            sDirection = "right";
            if (tilex == totalcols-1){
                sDirection = "left";
                tiley++;
            }

            else {
                tilex++;
            }
        }
        else {
            if (tilex == 0) {
                tiley++;
            }
            else {
                sDirection = "left";
                tilex--;
            }
        }

        if (tiley == totalrows) {
            sDirection = "stop";
        }


        String json = "{\"area_id\":\"" + area_id + "\", \"nextTileX\":\"" + tilex + "\", \"nextTileY\":\"" + tiley + "\", \"direction\":\"" + sDirection + "\"}";
        out.println(json);



    }



public class SensorDataMap {
    private HashMap<String, SensorData> sensorDataMap;

    public SensorDataMap() {
        sensorDataMap = new HashMap<>();
    }

    // Add sensor data with unique key
    public void addSensorData(String tileKey, SensorData data) {
        sensorDataMap.put(tileKey, data);
    }

    // Get sensor data by tile key
    public SensorData getSensorData(String tileKey) {
        return sensorDataMap.get(tileKey);
    }

    // Check if the map contains data for a specific tile
    public boolean containsTileData(String tileKey) {
        return sensorDataMap.containsKey(tileKey);
    }
}
}


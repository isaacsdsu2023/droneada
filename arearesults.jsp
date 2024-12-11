<%@ page import="com.dronerecon.ws.AreaGridTile" %>
<%@ page import="com.dronerecon.ws.DBManager" %>
<%@ page import="java.util.ArrayList" %>


<%
        String area_id = request.getParameter("area_id");
		DBManager oDBManager = new DBManager();
		
		oDBManager.DBLocation = System.getProperty("catalina.base") + "\\webapps\\dronereconportal\\db\\" + oDBManager.DBLocation;
		ArrayList<AreaGridTile> tiles = oDBManager.readAreaGridTiles(area_id);
		
		AreaGridTile maxR = tiles.get(0);
		AreaGridTile maxG = tiles.get(0);
		for (int i = 1; i < tiles.size(); i++){
			if (tiles.get(i).r > maxR.r){
				maxR = tiles.get(i);
			}
			if (tiles.get(i).g > maxG.g){
				maxG = tiles.get(i);
			}
		}
	  %>
	  
	  Highest R value is at (<%= maxR.x %>, <%= maxR.y %>)
	  <br>
	  Highest G value is at (<%= maxG.x %>, <%= maxG.y %>)
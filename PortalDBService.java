package com.dronerecon.ws;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;


public class PortalDBService extends HttpServlet{

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json;charset=utf-8");
        response.addHeader("Access-Control-Allow-Origin", "*");

        PrintWriter out = response.getWriter();

		
        String area_id = request.getParameter("area_id");

        int tilex = Integer.parseInt(request.getParameter("tilex"));

        int tiley = Integer.parseInt(request.getParameter("tiley"));

        int r = Integer.parseInt(request.getParameter("r"));

        int g = Integer.parseInt(request.getParameter("g"));

		
        DBManager oDBManager = new DBManager();

        oDBManager.DBLocation = System.getProperty("catalina.base") + "\\webapps\\dronereconportal\\db\\" + oDBManager.DBLocation;
        
        boolean insertSuccess = oDBManager.insertAreaGridTile(area_id, tilex, tiley, r, g);



        // Response with confirmation of DB record written.
        out.println("{\"success\":true}");
    }

    public class TileNode {
        int tileX;
        int tileY;
        TileNode left, right;
    
        public TileNode(int tileX, int tileY) {
            this.tileX = tileX;
            this.tileY = tileY;
            left = right = null;
        }
    }
    
    public class TileTree {
        private TileNode root;
    
        public TileTree() {
            root = null;
        }
    
        // Insert a new tile into the tree
        public void insert(int tileX, int tileY) {
            root = insertRec(root, tileX, tileY);
        }
    
        private TileNode insertRec(TileNode root, int tileX, int tileY) {
            if (root == null) {
                root = new TileNode(tileX, tileY);
                return root;
            }
    
            if (tileX < root.tileX) {
                root.left = insertRec(root.left, tileX, tileY);
            } else if (tileX > root.tileX) {
                root.right = insertRec(root.right, tileX, tileY);
            }
    
            return root;
        }
    
        // In-order traversal of the tree
        public void inorder() {
            inorderRec(root);
        }
    
        private void inorderRec(TileNode root) {
            if (root != null) {
                inorderRec(root.left);
                System.out.println("Tile (" + root.tileX + ", " + root.tileY + ")");
                inorderRec(root.right);
            }
        }
    }

public class TileGraph {
    private Map<Tile, List<Tile>> adjList;

    public TileGraph() {
        adjList = new HashMap<>();
    }

    // Add a vertex (tile) to the graph
    public void addTile(Tile tile) {
        adjList.putIfAbsent(tile, new ArrayList<>());
    }

    // Add an edge between two tiles (bidirectional)
    public void addEdge(Tile tile1, Tile tile2) {
        adjList.get(tile1).add(tile2);
        adjList.get(tile2).add(tile1);
    }

    // Get neighbors of a tile
    public List<Tile> getNeighbors(Tile tile) {
        return adjList.get(tile);
    }
}
}


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

// For running from an IDE w/ DB located in local project folder.

    
    private Connection connect() {

        // SQLite connection string
        String url = "jdbc:sqlite:" + DBLocation;
        Connection conn = null;

        try {
            Class.forName("org.sqlite.JDBC");

            // Connect to DB.
            conn = DriverManager.getConnection(url);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return conn;
    }



    public void insertAreaGridTile(String sAreaID, int iX, int iY, int iR, int iG){

        Connection c = connect();
        Statement stmt = null;

        try {
            c.setAutoCommit(false);

            stmt = c.createStatement();
            String sql = "INSERT INTO AreaGridTiles (area_id,x,y,r,g,timestamp) " +
                    "VALUES ('" + sAreaID + "'," + iX + "," + iY + "," + iR + "," + iG + ",datetime());";
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            e.printStackTrace();
        }
    }


    public ArrayList<AreaGridTile> readAreaGridTiles(String sAreaID){

        Connection c = connect();
        Statement stmt = null;

        // Used to hold tiles retrieved from DB.
        ArrayList<AreaGridTile> lstTiles = new ArrayList<>();

        try {
            c.setAutoCommit(false);

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM AreaGridTiles WHERE area_id = '" + sAreaID + "';" );

            while ( rs.next() ) {
                sAreaID = rs.getString("area_id");
                int iX = rs.getInt("x");
                int iY = rs.getInt("y");
                int iR = rs.getInt("r");
                int iG = rs.getInt("g");
                String sTimestamp = rs.getString("timestamp");

                AreaGridTile oTile = new AreaGridTile();
                oTile.areaID = sAreaID;
                oTile.x = iX;
                oTile.y = iY;
                oTile.r = iR;
                oTile.g = iG;
                oTile.timestamp = sTimestamp;

                lstTiles.add(oTile);

            }

            rs.close();
            stmt.close();
            c.close();

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }

        return lstTiles;
    }
    

    public class DroneMovementQueue {
    private Queue<String> movementQueue;

    public DroneMovementQueue() {
        movementQueue = new LinkedList<>();
    }

    // Add a movement task to the queue
    public void addMovement(String movement) {
        movementQueue.add(movement);
    }

    // Get the next movement task
    public String getNextMovement() {
        return movementQueue.poll(); // Poll returns null if queue is empty
    }

    // Check if the queue is empty
    public boolean isEmpty() {
        return movementQueue.isEmpty();
    }

    }


    // Only for testing in an IDE.
    public static void main(String[] args){

        DBManager oDBManager = new DBManager();
        oDBManager.insertAreaGridTile("abc123",10,10,243,109);
        System.out.println("Record inserted.");

        ArrayList<AreaGridTile> oTiles = oDBManager.readAreaGridTiles("23abc");

        for(AreaGridTile oTile: oTiles){
            System.out.println("tile: " + oTile.x + "," + oTile.y);
        }
    }

   

    public class GridArea {
    private ArrayList<Tile> gridTiles;

    public GridArea() {
        gridTiles = new ArrayList<>();
    }

    public void addTile(Tile tile) {
        gridTiles.add(tile);
    }

    public Tile getTile(int index) {
        return gridTiles.get(index);
    }

    // Example: Retrieve the tile at a given coordinates
    public Tile getTileByCoordinates(int tileX, int tileY) {
        for (Tile tile : gridTiles) {
            if (tile.getX() == tileX && tile.getY() == tileY) {
                return tile;
            }
        }
        return null; // Return null if tile not found
    }
}

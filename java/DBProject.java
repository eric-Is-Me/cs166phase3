/*
 * Template JAVA User Interface
 * =============================
 *
 * Database Management Systems
 * Department of Computer Science &amp; Engineering
 * University of California - Riverside
 *
 * Target DBMS: 'Postgres'
 *
 */


import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * This class defines a simple embedded SQL utility class that is designed to
 * work with PostgreSQL JDBC drivers.
 *
 */
public class DBProject {

   // reference to physical database connection.
   private Connection _connection = null;

   // handling the keyboard inputs through a BufferedReader
   // This variable can be global for convenience.
   static BufferedReader in = new BufferedReader(
                                new InputStreamReader(System.in));

   /**
    * Creates a new instance of DBProject
    *
    * @param hostname the MySQL or PostgreSQL server hostname
    * @param database the name of the database
    * @param username the user name used to login to the database
    * @param password the user login password
    * @throws java.sql.SQLException when failed to make a connection.
    */
   public DBProject (String dbname, String dbport, String user, String passwd) throws SQLException {

      System.out.print("Connecting to database...");
      try{
         // constructs the connection URL
         String url = "jdbc:postgresql://localhost:" + dbport + "/" + dbname;
         System.out.println ("Connection URL: " + url + "\n");

         // obtain a physical connection
         this._connection = DriverManager.getConnection(url, user, passwd);
         System.out.println("Done");
      }catch (Exception e){
         System.err.println("Error - Unable to Connect to Database: " + e.getMessage() );
         System.out.println("Make sure you started postgres on this machine");
         System.exit(-1);
      }//end catch
   }//end DBProject

   /**
    * Method to execute an update SQL statement.  Update SQL instructions
    * includes CREATE, INSERT, UPDATE, DELETE, and DROP.
    *
    * @param sql the input SQL string
    * @throws java.sql.SQLException when update failed
    */
   public void executeUpdate (String sql) throws SQLException {
      // creates a statement object
      Statement stmt = this._connection.createStatement ();

      // issues the update instruction
      stmt.executeUpdate (sql);

      // close the instruction
      stmt.close ();
   }//end executeUpdate

   /**
    * Method to execute an input query SQL instruction (i.e. SELECT).  This
    * method issues the query to the DBMS and outputs the results to
    * standard out.
    *
    * @param query the input query string
    * @return the number of rows returned
    * @throws java.sql.SQLException when failed to execute the query
    */
   public int executeQuery (String query) throws SQLException {
      // creates a statement object
      Statement stmt = this._connection.createStatement ();

      // issues the query instruction
      ResultSet rs = stmt.executeQuery (query);

      /*
       ** obtains the metadata object for the returned result set.  The metadata
       ** contains row and column info.
       */
      ResultSetMetaData rsmd = rs.getMetaData ();
      int numCol = rsmd.getColumnCount ();
      int rowCount = 0;

      // iterates through the result set and output them to standard out.
      boolean outputHeader = true;
      while (rs.next()){
   if(outputHeader){
      for(int i = 1; i <= numCol; i++){
    System.out.print(rsmd.getColumnName(i) + "\t");
      }
      System.out.println();
      outputHeader = false;
   }
         for (int i=1; i<=numCol; ++i)
            System.out.print (rs.getString (i) + "\t");
         System.out.println ();
         ++rowCount;
      }//end while
      stmt.close ();
      return rowCount;
   }//end executeQuery

   /**
    * Method to close the physical connection if it is open.
    */
   public void cleanup(){
      try{
         if (this._connection != null){
            this._connection.close ();
         }//end if
      }catch (SQLException e){
         // ignored.
      }//end try
   }//end cleanup

   /**
    * The main execution method
    *
    * @param args the command line arguments this inclues the <mysql|pgsql> <login file>
    */
   public static void main (String[] args) {
      if (args.length != 3) {
         System.err.println (
            "Usage: " +
            "java [-classpath <classpath>] " +
            DBProject.class.getName () +
            " <dbname> <port> <user>");
         return;
      }//end if
      
      Greeting();
      DBProject esql = null;
      try{
         // use postgres JDBC driver.
         Class.forName ("org.postgresql.Driver").newInstance ();
         // instantiate the DBProject object and creates a physical
         // connection.
         String dbname = args[0];
         String dbport = args[1];
         String user = args[2];
         esql = new DBProject (dbname, dbport, user, "");

         boolean keepon = true;
         while(keepon) {
            // These are sample SQL statements
        System.out.println("MAIN MENU");
        System.out.println("---------");
        System.out.println("1. Add new customer");
        System.out.println("2. Add new room");
        System.out.println("3. Add new maintenance company");
        System.out.println("4. Add new repair");
        System.out.println("5. Add new Booking"); 
        System.out.println("6. Assign house cleaning staff to a room");
        System.out.println("7. Raise a repair request");
        System.out.println("8. Get number of available rooms");
        System.out.println("9. Get number of booked rooms");
        System.out.println("10. Get hotel bookings for a week");
        System.out.println("11. Get top k rooms with highest price for a date range");
        System.out.println("12. Get top k highest booking price for a customer");
        System.out.println("13. Get customer total cost occurred for a give date range"); 
        System.out.println("14. List the repairs made by maintenance company");
        System.out.println("15. Get top k maintenance companies based on repair count");
        System.out.println("16. Get number of repairs occurred per year for a given hotel room");
        System.out.println("17. < EXIT");

            switch (readChoice()){
           case 1: addCustomer(esql); break;
           case 2: addRoom(esql); break;
           case 3: addMaintenanceCompany(esql); break;
           case 4: addRepair(esql); break;
           case 5: bookRoom(esql); break;
           case 6: assignHouseCleaningToRoom(esql); break;
           case 7: repairRequest(esql); break;
           case 8: numberOfAvailableRooms(esql); break;
           case 9: numberOfBookedRooms(esql); break;
           case 10: listHotelRoomBookingsForAWeek(esql); break;
           case 11: topKHighestRoomPriceForADateRange(esql); break;
           case 12: topKHighestPriceBookingsForACustomer(esql); break;
           case 13: totalCostForCustomer(esql); break;
           case 14: listRepairsMade(esql); break;
           case 15: topKMaintenanceCompany(esql); break;
           case 16: numberOfRepairsForEachRoomPerYear(esql); break;
           case 17: keepon = false; break;
           default : System.out.println("Unrecognized choice!"); break;
            }//end switch
         }//end while
      }catch(Exception e) {
         System.err.println (e.getMessage ());
      }finally{
         // make sure to cleanup the created table and close the connection.
         try{
            if(esql != null) {
               System.out.print("Disconnecting from database...");
               esql.cleanup ();
               System.out.println("Done\n\nBye !");
            }//end if
         }catch (Exception e) {
            // ignored.
         }//end try
      }//end try
   }//end main
   
   public static void Greeting(){
      System.out.println(
         "\n\n*******************************************************\n" +
         "              User Interface                       \n" +
         "*******************************************************\n");
   }//end Greeting

   /*
    * Reads the users choice given from the keyboard
    * @int
    **/
   public static int readChoice() {
      int input;
      // returns only if a correct value is given.
      do {
         System.out.print("Please make your choice: ");
         try { // read the integer, parse it and break.
            input = Integer.parseInt(in.readLine());
            break;
         }catch (Exception e) {
            System.out.println("Your input is invalid!");
            continue;
         }//end try
      }while (true);
      return input;
   }//end readChoice

   
   public static void addCustomer(DBProject esql){
    // Given customer details add the customer in the DB 
      int customerID;
      String firstName;
      String lastName;
      String addr;
      String phone;
      String dateOfBirth;
      String sex;
      String query;

      while (true)
      {
        System.out.print("Please input Customer ID: ");
        try 
        {
          customerID = Integer.parseInt(in.readLine());
          break;
        }
        catch (Exception e)
        {
          System.out.println("Invalid input! Your exception is: " + e.getMessage());
          continue;
        }
      }

      while (true)
      {
        System.out.print("Please input Customer First Name: ");
        try 
        {
          firstName = in.readLine();
          if (firstName.length() <= 0 || firstName.length() > 30) 
          {
            throw new RuntimeException("Your input is invalid! First Name cannot be empty, and cannot exceed 30 characters");
          }
          break;
        }
        catch (Exception e)
        {
          System.out.println("Invalid input! Your exception is: " + e.getMessage());
          continue;
        }
      }

      while (true)
      {
        System.out.print("Please input Customer Last Name: ");
        try 
        {
          lastName = in.readLine();
          if (lastName.length() <= 0 || lastName.length() > 30) 
          {
            throw new RuntimeException("Your input is invalid! Last Name cannot be empty, and cannot exceed 30 characters");
          }
          break;
        }
        catch (Exception e)
        {
          System.out.println("Invalid input! Your exception is: " + e.getMessage());
          continue;
        }
      }

      while (true)
      {
        System.out.print("Enter Customer Address: ");
        try
        {
          addr = in.readLine();
          break;
        }
        catch (Exception e)
        {
          System.out.println("Invalid input! Your exception is: " + e.getMessage());
          continue;
        }
      }

      while (true)
      {
        System.out.print("Enter Customer Phone Number: ");
        try
        {
          phone = in.readLine();
          break;
        }
        catch (Exception e)
        {
          System.out.println("Invalid input! Your exception is: " + e.getMessage());
          continue;
        }
      }

      while (true)
      {
        System.out.print("Enter Customer Date of Birth: ");
        try
        {
          dateOfBirth = in.readLine();
          break;
        }
        catch (Exception e)
        {
          System.out.println("Invalid input! Your exception is: " + e.getMessage());
          continue;
        }
      } 

      while (true)
      {
        System.out.print("Enter Customer Sex: ");
        try
        {
          sex = in.readLine();
          break;
        }
        catch (Exception e)
        {
          System.out.println("Invalid input! Your exception is: " + e.getMessage());
          continue;
        }
      }

      try
      {
        query = "INSERT INTO Customer VALUES (" + customerID + "," + firstName + "," + lastName + "," + addr + "," + phone + "," + dateOfBirth + "," + sex + ");";
        esql.executeQuery(query);
      }
      catch (Exception e)
      {
        System.err.println("Query failed: " + e.getMessage());
      }
   }//end addCustomer

   public static void addRoom(DBProject esql){
    // Given room details add the room in the DB
      // Your code goes here.
      // ...
      // ...
     
     
    while(true)
    {
      System.out.print("Please input HotelID: ")
      try
      {
        hotelID = Integer.parseInt(in.readLine());
        break;
      }
      catch (Exception e)
      {
        System.out.println("Invalid input! Your exception is: " + e.getMessage());
        continue; 
      }
    }

    while(true)
    {
      System.out.printIn("Please input RoomNo: ")
      try
      {
        roomNo = Integer.parseInt(in.readLine());
        break;
      }
      catch (Exception e)
      {
        System.out.println("Invalid input! Your exception is: " + e.getMessage());
        continue; 
      }
    }

    while(true)
    {
      System.out.println("Please input roomType: ")
      try
      {
        roomType = in.readLine();
        if (roomType.length() <= 0 || firstName.length() > 10)
        {
          throw new RuntimeException("Your input is invalid!")
        }
      }
      catch (Exception e)
      {
        System.out.println("Invalid input! Your exception is: " + e.getMessage());
      }
    }
   }//end addRoom

   public static void addMaintenanceCompany(DBProject esql){
      // Given maintenance Company details add the maintenance company in the DB
      int cmpID;
      String name;
      String address;
      String isCertified;

      while (true)
      {
        System.out.print("Please input Company ID: ");
        try 
        {
          customerID = Integer.parseInt(in.readLine());
          break;
        }
        catch (Exception e)
        {
          System.out.println("Invalid input! Your exception is: " + e.getMessage());
          continue;
        }
      }

      while (true)
      {
        System.out.print("Please input Company Name: ");
        try 
        {
          name = in.readLine();
          if (firstName.length() <= 0 || firstName.length() > 30) 
          {
            throw new RuntimeException("Your input is invalid! Name cannot be empty, and cannot exceed 30 characters");
          }
          break;
        }
        catch (Exception e)
        {
          System.out.println("Invalid input! Your exception is: " + e.getMessage());
          continue;
        }
      }

      while (true)
      {
        System.out.print("Please input Company Address: ");
        try 
        {
          address = in.readLine();
          break;
        }
        catch (Exception e)
        {
          System.out.println("Invalid input! Your exception is: " + e.getMessage());
          continue;
        }
      }

      while (true)
      {
        System.out.print("Is the company certified? Enter TRUE or FALSE: ");
        try
        {
          isCertified = in.readLine();
          if (isCertified !== "TRUE" || isCertified !== "FALSE")
          {
            System.out.println("Answer must be either TRUE or FALSE");
            continue;
          }
          break;
        }
        catch (Exception e)
        {
          System.out.println("Invalid input! Your exception is: " + e.getMessage());
          continue;
        }
      }

      try
      {
        query = "INSERT INTO MaintenanceCompany VALUES (" + cmpID + ", '" name + "', '" + address + "', '" + isCertified + "');";
        esql.executeQuery(query);
      }
      catch (Exception e)
      {
        System.err.println("Query failed: " + e.getMessage());
      }
   }//end addMaintenanceCompany

   public static void addRepair(DBProject esql){
    // Given repair details add repair in the DB
      // Your code goes here.
      // ...
      // ...
   }//end addRepair

   public static void bookRoom(DBProject esql){
    // Given hotelID, roomNo and customer Name create a booking in the DB 
      int bID;
      String firstName;
      String lastName;
      int hotelID;
      int roomNo;
      String bookingDate;
      int noOfPeople;
      double price;

      while (true)
      {
        System.out.print("Please input Hotel ID: ");
        try 
        {
          customerID = Integer.parseInt(in.readLine());
          break;
        }
        catch (Exception e)
        {
          System.out.println("Invalid input! Your exception is: " + e.getMessage());
          continue;
        }
      }

      while (true)
      {
        System.out.print("Please input Customer First Name: ");
        try 
        {
          firstName = in.readLine();
          if (firstName.length() <= 0 || firstName.length() > 30) 
          {
            throw new RuntimeException("Your input is invalid! First Name cannot be empty, and cannot exceed 30 characters");
          }
          break;
        }
        catch (Exception e)
        {
          System.out.println("Invalid input! Your exception is: " + e.getMessage());
          continue;
        }
      }

      while (true)
      {
        System.out.print("Please input Customer Last Name: ");
        try 
        {
          lastName = in.readLine();
          if (lastName.length() <= 0 || lastName.length() > 30) 
          {
            throw new RuntimeException("Your input is invalid! Last Name cannot be empty, and cannot exceed 30 characters");
          }
          break;
        }
        catch (Exception e)
        {
          System.out.println("Invalid input! Your exception is: " + e.getMessage());
          continue;
        }
      }

      while (true)
      {
        System.out.print("Enter the Room Number: ");
        try 
        {
          customerID = Integer.parseInt(in.readLine());
          break;
        }
        catch (Exception e)
        {
          System.out.println("Invalid input! Your exception is: " + e.getMessage());
          continue;
        }
      }

      try
      {
        query = "INSERT INTO Booking VALUES (" + bID + ", (SELECT customerID FROM Customer WHERE fName=" + "'firstName' AND lName='" + lastName +"'), '" + hotelID + "', '" + roomNo + "', '" + bookingDate + "', " + noOfPeople + ", " +  price +");";
        esql.executeQuery(query);
      }
      catch (Exception e)
      {
        System.err.println("Query failed: " + e.getMessage());
      }
   }//end bookRoom

   public static void assignHouseCleaningToRoom(DBProject esql){
    // Given Staff SSN, HotelID, roomNo Assign the staff to the room 
      // Your code goes here.
      // ...
      // ...
   }//end assignHouseCleaningToRoom
   
   public static void repairRequest(DBProject esql){
    // Given a hotelID, Staff SSN, roomNo, repairID , date create a repair request in the DB
   }//end repairRequest
   
   public static void numberOfAvailableRooms(DBProject esql){
    // Given a hotelID, get the count of rooms available 
      // Your code goes here.
      // ...
      // ...
   }//end numberOfAvailableRooms
   
   public static void numberOfBookedRooms(DBProject esql){
    // Given a hotelID, get the count of rooms booked
      int hotelID;

      while (true)
      {
        System.out.print("Please input Hotel ID: ");
        try 
        {
          hotelID = Integer.parseInt(in.readLine());
          break;
        }
        catch (Exception e)
        {
          System.out.println("Invalid input! Your exception is: " + e.getMessage());
          continue;
        }
      }

      try
      {
        query = "SELECT COUNT(*) FROM Booking B, Room R WHERE B.hotelID = R.hotelID AND R.roomNo IN (SELECT R.roomNo FROM Booking B WHERE R.roomNo = B.roomNo) AND R.hotelID=" + hotelID;
        esql.executeQuery(query);
      }
      catch (Exception e)
      {
        System.err.println("Query failed: " + e.getMessage());
      } 
   }//end numberOfBookedRooms
   
   public static void listHotelRoomBookingsForAWeek(DBProject esql){
    // Given a hotelID, date - list all the rooms available for a week(including the input date) 
      int hotelID;
      String bookingDate

      while (true)
      {
        System.out.print("Please input Hotel ID: ");
        try 
        {
          hotelID = Integer.parseInt(in.readLine());
          break;
        }
        catch (Exception e)
        {
          System.out.println("Invalid input! Your exception is: " + e.getMessage());
          continue;
        }
      }

      while (true)
      {
        System.out.print("Enter the date: ");
        try
        {
          bookingDate = in.readLine();
          break;
        }
        catch (Exception e)
        {
          System.out.println("Invalid input! Your exception is: " + e.getMessage());
          continue;
        }
      }

      try
      {
        query = "SELECT roomNo FROM Booking WHERE Booking.hotelID=" + hotelID + " AND '" + bookingDate + "'::date  BETWEEN '" + bookingDate + "'::date AND '" + bookingDate + "'::date + interval '1 week'";
        esql.executeQuery(query);
      }
      catch (Exception e)
      {
        System.err.println("Query failed: " + e.getMessage());
      }
   }//end listHotelRoomBookingsForAWeek
   
   public static void topKHighestRoomPriceForADateRange(DBProject esql){
    // List Top K Rooms with the highest price for a given date range
      // Your code goes here.
      // ...
      // ...
   }//end topKHighestRoomPriceForADateRange
   
   public static void topKHighestPriceBookingsForACustomer(DBProject esql){
    // Given a customer Name, List Top K highest booking price for a customer 
      String firstName;
      String lastName;
      int k;

      while (true)
      {
        System.out.print("Please input Customer First Name: ");
        try 
        {
          firstName = in.readLine();
          break;
        }
        catch (Exception e)
        {
          System.out.println("Invalid input! Your exception is: " + e.getMessage());
          continue;
        }
      }

      while (true)
      {
        System.out.print("Please input Customer Last Name: ");
        try 
        {
          lastName = in.readLine();
          break;
        }
        catch (Exception e)
        {
          System.out.println("Invalid input! Your exception is: " + e.getMessage());
          continue;
        }
      }
      
      while (true)
      {
        System.out.print("How many bookings do you want to see? ");
        try
        {
          k = Integer.parseInt(in.readLine());
          break;
        }
        catch (Exception e)
        {
          System.out.println("Invalid input! Your exception is: " + e.getMessage());
          continue;
        }
      }

      try
      {
        query = "SELECT price FROM (SELECT price FROM Booking WHERE Booking.customer=(SELECT customerID FROM Customer WHERE Customer.fName='" + firstName + "' AND Customer.lName='" + lastName +"') ORDER BY price DESC) LIMIT "+ k;
        esql.executeQuery(query);
      }
      catch (Exception e)
      {
        System.err.println("Query failed: " + e.getMessage());
      }
   }//end topKHighestPriceBookingsForACustomer
   
   public static void totalCostForCustomer(DBProject esql){
    // Given a hotelID, customer Name and date range get the total cost incurred by the customer
	  int hotelID;
	  String firstName;
	  String lastName;
	  String date1;
	  String date2;

      while (true)
      {
        System.out.print("Please input Customer First Name: ");
        try 
        {
          firstName = in.readLine();
          break;
        }
        catch (Exception e)
        {
          System.out.println("Invalid input! Your exception is: " + e.getMessage());
          continue;
        }
      }

      while (true)
      {
        System.out.print("Please input Customer Last Name: ");
        try 
        {
          lastName = in.readLine();
          break;
        }
        catch (Exception e)
        {
          System.out.println("Invalid input! Your exception is: " + e.getMessage());
          continue;
        }
      }
      
      while (true)
      {
        System.out.print("Enter Hotel ID: ");
        try
        {
          hotelID = Integer.parseInt(in.readLine());
          break;
        }
        catch (Exception e)
        {
          System.out.println("Invalid input! Your exception is: " + e.getMessage());
          continue;
        }
      }

      while (true)
      {
        System.out.print("Enter Starting Date: ");
        try 
        {
          date1 = in.readLine();
          break;
        }
        catch (Exception e)
        {
          System.out.println("Invalid input! Your exception is: " + e.getMessage());
          continue;
        }
      }
      
      while (true)
      {
        System.out.print("Enter Ending Date: ");
        try 
        {
          date2 = in.readLine();
          break;
        }
        catch (Exception e)
        {
          System.out.println("Invalid input! Your exception is: " + e.getMessage());
          continue;
        }
      }
      
      try
      {
        query = "SELECT SUM(price) FROM Booking WHERE Booking.hotelID='" + hotelID + "' AND Booking.customer=(SELECT customerID FROM Customer WHERE Customer.fName='" + firstName + "' AND Customer.lName='" + lastName + "') AND Booking.date BETWEEN '" + date1 + "'::date AND '" + date2 + "'::date";
        esql.executeQuery(query);
      }
      catch (Exception e)
      {
        System.err.println("Query failed: " + e.getMessage());
      }
   }//end totalCostForCustomer
   
   public static void listRepairsMade(DBProject esql){
    // Given a Maintenance company name list all the repairs along with repairType, hotelID and roomNo
	  String mName;

      while (true)
      {
        System.out.print("Please input Maintenance Company Name: ");
        try 
        {
          mName = in.readLine();
          break;
        }
        catch (Exception e)
        {
          System.out.println("Invalid input! Your exception is: " + e.getMessage());
          continue;
        }
      }
      
      try
      {
        query = "SELECT repairType, hotelID, roomNo FROM Repair WHERE Repair.mCompany=(SELECT cmpID FROM MaintenanceCompany WHERE MaintenanceCompany.name='" + mName + "')";
        esql.executeQuery(query);
      }
      catch (Exception e)
      {
        System.err.println("Query failed: " + e.getMessage());
      }
   }//end listRepairsMade
   
   public static void topKMaintenanceCompany(DBProject esql){
    // List Top K Maintenance Company Names based on total repair count (descending order)
      // Your code goes here.
      // ...
      // ...
   }//end topKMaintenanceCompany
   
   public static void numberOfRepairsForEachRoomPerYear(DBProject esql){
    // Given a hotelID, roomNo, get the count of repairs per year
	  int hotelID;
	  int roomNo;

      while (true)
      {
        System.out.print("Please input Hotel ID: ");
        try 
        {
          hotelID = in.readLine();
          break;
        }
        catch (Exception e)
        {
          System.out.println("Invalid input! Your exception is: " + e.getMessage());
          continue;
        }
      }
      
      while (true)
      {
        System.out.print("Please input Room Number: ");
        try 
        {
          roomNo = in.readLine();
          break;
        }
        catch (Exception e)
        {
          System.out.println("Invalid input! Your exception is: " + e.getMessage());
          continue;
        }
      }
      
      try
      {
        query = "SELECT DATE_PART('year', repairDate) AS \"Year\", COUNT(*) AS \"Number of Repairs\" FROM Repair WHERE Repair.hotelID='" + hotelID + "' AND Repair.roomNo='" + roomNo + "' GROUP BY DATE_PART('year', repairDate)";
        esql.executeQuery(query);
      }
      catch (Exception e)
      {
        System.err.println("Query failed: " + e.getMessage());
      }
   }//end listRepairsMade

}//end DBProject

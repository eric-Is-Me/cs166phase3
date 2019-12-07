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
          if (customerID < 0)
          {
			  System.out.print("Can't use negative numbers.");
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
        query = "INSERT INTO Customer VALUES (" + customerID + "," + "'" + firstName + "', '" + lastName + "', '" + addr + "', " + phone + ", '" + dateOfBirth + "'::date, '" + sex + "')";
        esql.executeQuery(query);
      }
      catch (Exception e)
      {
        System.err.println("Query failed: " + e.getMessage());
      }
   }//end addCustomer

   public static void addRoom(DBProject esql){
    // Given room details add the room in the DB
	int hotelID;
	int roomNo;
	String roomType;
	String query;
	
    while(true)
    {
      System.out.print("Please input HotelID: ");
      try
      {
        hotelID = Integer.parseInt(in.readLine());
		if (hotelID < 0)
		{
			System.out.print("Can't use negative numbers.");
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

    while(true)
    {
      System.out.println("Please input RoomNo: ");
      try
      {
        roomNo = Integer.parseInt(in.readLine());
        if (roomNo < 0)
		{
			System.out.print("Can't use negative numbers.");
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

    while(true)
    {
      System.out.println("Please input roomType: ");
      try
      {
        roomType = in.readLine();
        if (roomType.length() <= 0 || roomType.length() > 10)
        {
          throw new RuntimeException("Your input is invalid! Name cannot be empty, and cannot exceed 8 characters");
        }
        break;
      }
      catch (Exception e)
      {
        System.out.println("Invalid input! Your exception is: " + e.getMessage());
      }
    }
    
    try
      {
        query = "INSERT INTO Room VALUES ('" + hotelID + "', '" + roomNo + "', '" + roomType + "')";
        esql.executeQuery(query);
      }
      catch (Exception e)
      {
        System.err.println("Query failed: " + e.getMessage());
      }
   }//end addRoom

   public static void addMaintenanceCompany(DBProject esql){
	  int cmpID;
      String address;
      String isCertified;
      String name;
      String query;
 
      while (true)
      {
        System.out.print("Please input Company ID: ");
        try 
        {
          cmpID = Integer.parseInt(in.readLine());
		if (cmpID < 0)
		{
			System.out.print("Can't use negative numbers.");
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
    
      while (true)
      {
        System.out.print("Please input Company Name: ");
        try 
        {
          name = in.readLine();
          if (name.length() <= 0 || name.length() > 30) 
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
          if (isCertified.equals("TRUE") || isCertified.equals("FALSE"))
          {
            break;
          }
          else
          {
	    	System.out.println("Answer must be either TRUE or FALSE");
	        continue;
          }
        }
        catch (Exception e)
        {
          System.out.println("Invalid input! Your exception is: " + e.getMessage());
          continue;
        }
      }
     
      try
      {
        query = "INSERT INTO MaintenanceCompany VALUES (" + cmpID + ", '" + name + "', '" + address + "', '" + isCertified + "')";
        esql.executeQuery(query);
      }
      catch (Exception e)
      {
        System.err.println("Query failed: " + e.getMessage());
      }
   }//end addMaintenanceCompany

   public static void addRepair(DBProject esql){
    // Given repair details add repair in the DB
      int rID;
    int hotelID; 
    int roomNo;
    int mCompany; 
    String repairDate; 
    String description;
    String repairType; 
    String query;

      while (true)
      {
        System.out.print("Please input Repair ID: ");
        try 
        {
          rID = Integer.parseInt(in.readLine());
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
        System.out.print("Please input Room number: ");
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

      while (true)
      {
        System.out.print("Please input Company ID: ");
        try 
        {
          mCompany = Integer.parseInt(in.readLine());
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
        System.out.print("Please input Repair Date: ");
        try 
        {
          repairDate = in.readLine();
          if (repairDate.length() <= 0 || repairDate.length() > 10) 
          {
            throw new RuntimeException("Your input is invalid! Date cannot be empty, and cannot exceed 10 characters");
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
        System.out.print("Enter Description: ");
        try
        {
          description = in.readLine();
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
        System.out.print("Enter Repair Type: ");
        try 
        {
          repairType = in.readLine();
          if (repairDate.length() <= 0 || repairDate.length() > 10) 
          {
            throw new RuntimeException("Your input is invalid! Name cannot be empty, and cannot exceed 8 characters");
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
        query = "INSERT INTO Repair VALUES (" + rID + "," + hotelID + " , " + roomNo + " , '" + mCompany + "', '" + repairDate + "'::date , '" + description + "', '" + repairType + "')";
        esql.executeQuery(query);
      }
      catch (Exception e)
      {
        System.err.println("Query failed: " + e.getMessage());
      }
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
      String price;
      String query;

      while (true)
      {
        System.out.print("Please input Hotel ID: ");
        try 
        {
          hotelID = Integer.parseInt(in.readLine());
		if (hotelID < 0)
		{
			System.out.print("Can't use negative numbers.");
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
          roomNo = Integer.parseInt(in.readLine());
		if (roomNo < 0)
		{
			System.out.print("Can't use negative numbers.");
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
      
      while (true)
      {
        System.out.print("Enter the date of booking: ");
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
      
      while (true)
      {
        System.out.print("How many people are staying in the room? ");
        try 
        {
          noOfPeople = Integer.parseInt(in.readLine());
		if (noOfPeople < 0)
		{
			System.out.print("Can't use negative numbers.");
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
      
      while (true)
      {
        System.out.print("Enter the price of the room: ");
        try
        {
          price = in.readLine();
          int integerPlaces = price.indexOf('.');
          int decPlaces = price.length() - integerPlaces - 1;
          
          if (decPlaces > 2)
          {
        	  System.out.print("Prices can only have up to 2 decimal places");
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
    	String query2 = "SELECT MAX(bID) FROM Booking";
        Statement bID_Statement = esql._connection.createStatement();
        ResultSet bID_ResultSet = bID_Statement.executeQuery(query2);
        bID_ResultSet.next();
        int bID_Increment = bID_ResultSet.getInt(1) + 1;
    	String query3 = "SELECT customerID FROM Customer WHERE fName='" + firstName + "' AND lName='" + lastName + "'";
        Statement name_Statement = esql._connection.createStatement();
        ResultSet name_ResultSet = name_Statement.executeQuery(query3);
        int cid = 0;
        if (name_ResultSet.next())
        {
        	cid = name_ResultSet.getInt(1);
        }
        else
        {
        	System.out.print("User does not exist");
        }
        System.out.println(roomNo);
        query = "INSERT INTO Booking VALUES (" + bID_Increment + ", " + cid + ", " + hotelID + ", " + roomNo + ", '" + bookingDate + "'::date, " + noOfPeople + ", " +  price + ")";
        esql.executeQuery(query);
      }
      catch (Exception e)
      {
        System.err.println("Query failed: " + e.getMessage());
      }
   }//end bookRoom

   public static void assignHouseCleaningToRoom(DBProject esql){
    // Given Staff SSN, HotelID, roomNo Assign the staff to the room 
	    int staffID;
	    int hotelID;
	    int roomNo; 
	    int asgID; 
	    String query; 


	      while (true)
	      {
	        System.out.print("Enter the Staff SSN: ");
	        try 
	        {
	          staffID = Integer.parseInt(in.readLine());
				if (staffID < 0)
				{
					System.out.print("Can't use negative numbers.");
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

	      while (true)
	      {
	        System.out.print("Enter the Hotel ID: ");
	        try 
	        {
	          hotelID = Integer.parseInt(in.readLine());
				if (hotelID < 0)
				{
					System.out.print("Can't use negative numbers.");
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

	      while (true)
	      {
	        System.out.print("Enter the Room Number: ");
	        try 
	        {
	          roomNo = Integer.parseInt(in.readLine());
				if (roomNo < 0)
				{
					System.out.print("Can't use negative numbers.");
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
	    	String query2 = "SELECT MAX(asgID) FROM Assigned";
	        Statement asgID_Statement = esql._connection.createStatement();
	        ResultSet asgID_ResultSet = asgID_Statement.executeQuery(query2);
	        asgID_ResultSet.next();
	        int asgID_Increment = asgID_ResultSet.getInt(1) + 1; 
	        query = "INSERT INTO Assigned VALUES (" + asgID_Increment + ", " + staffID + ", " + hotelID + ", " + roomNo + ");";
	        esql.executeQuery(query);
	      }
	      catch (Exception e)
	      {
	        System.err.println("Query failed: " + e.getMessage());
	      }
   }//end assignHouseCleaningToRoom
   
   public static void repairRequest(DBProject esql){
    // Given a hotelID, Staff SSN, roomNo, repairID , date create a repair request in the DB
	  int repairDate;
      int managerID; 
      int repairID; 
      String query;  

      while (true)
      {
        System.out.print("Please input RepairID: ");
        try 
        {
          repairID = Integer.parseInt(in.readLine());
			if (repairID < 0)
			{
				System.out.print("Can't use negative numbers.");
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

      while (true)
      {
        System.out.print("Please input Manager ID: ");
        try 
        {
          managerID = Integer.parseInt(in.readLine());
			if (managerID < 0)
			{
				System.out.print("Can't use negative numbers.");
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

      while (true)
      {
        System.out.print("Please input Repair Date: ");
        try 
        {
          repairDate = Integer.parseInt(in.readLine());
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
        String query_reqID = "SELECT MAX(reqID) FROM Request";
        Statement reqID_Statement = esql._connection.createStatement();
        ResultSet reqID_ResultSet = reqID_Statement.executeQuery(query_reqID);
        reqID_ResultSet.next();
        int reqID_Increment = reqID_ResultSet.getInt(1) + 1;
        query = "INSERT INTO Request VALUES (" + reqID_Increment + managerID + ", '" + repairID + "', '" + repairDate + "');";
        esql.executeQuery(query);
      }
      catch (Exception e)
      {
        System.err.println("Query failed: " + e.getMessage());
      }
   }//end repairRequest
   
   public static void numberOfAvailableRooms(DBProject esql){
    // Given a hotelID, get the count of rooms available 
      // Your code goes here.
	  int hotelID;
      String query;

      while (true)
      {
        System.out.print("Please input Hotel ID: ");
        try 
        {
          hotelID = Integer.parseInt(in.readLine());
			if (hotelID < 0)
			{
				System.out.print("Can't use negative numbers.");
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
        query = "SELECT COUNT(*) FROM Room, Booking WHERE Room.hotelID='" + hotelID + "' AND Booking.hotelID='" + hotelID + "' AND Room.roomNo NOT IN (SELECT Room.roomNo FROM Booking WHERE Room.roomNo=Booking.roomNo)"; 
        esql.executeQuery(query);
      }
      catch (Exception e)
      {
        System.err.println("Query failed: " + e.getMessage());
      } 
   }//end numberOfAvailableRooms
   
   public static void numberOfBookedRooms(DBProject esql){
    // Given a hotelID, get the count of rooms booked
      int hotelID;
      String query;

      while (true)
      {
        System.out.print("Please input Hotel ID: ");
        try 
        {
          hotelID = Integer.parseInt(in.readLine());
			if (hotelID < 0)
			{
				System.out.print("Can't use negative numbers.");
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
        query = "SELECT COUNT(*) FROM Booking B WHERE B.hotelID = " + hotelID;
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
      String bookingDate;
      String query;

      while (true)
      {
        System.out.print("Please input Hotel ID: ");
        try 
        {
          hotelID = Integer.parseInt(in.readLine());
			if (hotelID < 0)
			{
				System.out.print("Can't use negative numbers.");
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
        query = "SELECT Booking.roomNo FROM Booking WHERE (Booking.hotelID=" + hotelID + " AND Booking.bookingDate BETWEEN '" + bookingDate + "'::date AND '" + bookingDate + "'::date + interval '1 week') GROUP BY Booking.roomNo";
        esql.executeQuery(query);
      }
      catch (Exception e)
      {
        System.err.println("Query failed: " + e.getMessage());
      }
   }//end listHotelRoomBookingsForAWeek
   
   public static void topKHighestRoomPriceForADateRange(DBProject esql){
    // List Top K Rooms with the highest price for a given date range
	  String query;
	  String date1;
      String date2;
      int k; 

       while (true)
      {
        System.out.print("Please input K: ");
        try 
        {
          k = Integer.parseInt(in.readLine());
			if (k < 0)
			{
				System.out.print("Can't use negative numbers.");
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
        query = "SELECT * FROM Booking B, Room R WHERE R.roomNo=B.roomNo AND R.hotelID=B.hotelID AND B.bookingDate BETWEEN '" + date1 + "'::date AND '" + date2 + "'::date ORDER By B.price DESC Limit " + k;
        esql.executeQuery(query);
      }
      catch (Exception e)
      {
        System.err.println("Query failed: " + e.getMessage());
      } 
   }//end topKHighestRoomPriceForADateRange
   
   public static void topKHighestPriceBookingsForACustomer(DBProject esql){
    // Given a customer Name, List Top K highest booking price for a customer 
      String firstName;
      String lastName;
      int k;
      String query;

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
			if (k < 0)
			{
				System.out.print("Can't use negative numbers.");
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
        query = "SELECT b.price FROM Booking AS b WHERE b.customer=(SELECT customerID FROM Customer WHERE Customer.fName='" + firstName + "' AND Customer.lName='" + lastName +"') ORDER BY b.price DESC LIMIT "+ k;
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
	  String query;

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
			if (hotelID < 0)
			{
				System.out.print("Can't use negative numbers.");
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
        query = "SELECT SUM(price) FROM Booking WHERE Booking.hotelID='" + hotelID + "' AND Booking.customer=(SELECT customerID FROM Customer WHERE Customer.fName='" + firstName + "' AND Customer.lName='" + lastName + "') AND Booking.bookingDate BETWEEN '" + date1 + "'::date AND '" + date2 + "'::date";
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
	  String query;

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
	String query;
	int k; 

       while (true)
      {
        System.out.print("How many top maintenance company name repairs do you want to see? : ");
        try 
        {
          k = Integer.parseInt(in.readLine());
			if (k < 0)
			{
				System.out.print("Can't use negative numbers.");
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
        query = "SELECT M.name FROM maintenanceCompany M, Repair R WHERE M.cmpID = R.mCompany GROUP BY M.name ORDER BY COUNT(*) DESC LIMIT "+ k;
        esql.executeQuery(query);
      }
      catch (Exception e)
      {
        System.err.println("Query failed: " + e.getMessage());
      }
   }//end topKMaintenanceCompany
   
   public static void numberOfRepairsForEachRoomPerYear(DBProject esql){
    // Given a hotelID, roomNo, get the count of repairs per year
	  int hotelID;
	  int roomNo;
	  String query;

      while (true)
      {
        System.out.print("Please input Hotel ID: ");
        try 
        {
          hotelID = Integer.parseInt(in.readLine());
			if (hotelID < 0)
			{
				System.out.print("Can't use negative numbers.");
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
      
      while (true)
      {
        System.out.print("Please input Room Number: ");
        try 
        {
          roomNo = Integer.parseInt(in.readLine());
			if (roomNo < 0)
			{
				System.out.print("Can't use negative numbers.");
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
        query = "SELECT DATE_PART('year', repairDate), COUNT(*) FROM Repair WHERE Repair.hotelID='" + hotelID + "' AND Repair.roomNo='" + roomNo + "' GROUP BY DATE_PART('year', repairDate)";
        esql.executeQuery(query);
      }
      catch (Exception e)
      {
        System.err.println("Query failed: " + e.getMessage());
      }
   }//end listRepairsMade

}//end DBProject

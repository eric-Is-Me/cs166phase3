/*Query 1*/ 
INSERT INTO Customer VALUES (customerID, firstName , lastName , addr, phone, dateOfBirth, sex);

/*Query 2*/ 
INSERT INTO Room VALUES (hotelID, roomNo, roomType);

/*Query 3*/ 
INSERT INTO MaintenanceCompany VALUES (cmpID, name, address, isCertified); 

/*Query 4*/ 
INSERT INTO Repair VALUES (rID, hotelID, roomNo, mCompany, repairDate, description, repairType);

/*Query 5 with Select Max(bID, Select CustomerID, and Select Max(asgID) */ 
SELECT MAX(bID) FROM Booking
SELECT customerID FROM Customer WHERE fName='rzqs' AND lName='eyeg'
INSERT INTO Booking VALUES (bID_Increment, cid, hotelID, roomNo, bookingDate, date, noOfPeople, price); 

/*Query 6*/ 
SELECT MAX(asgID) FROM Assigned
INSERT INTO Assigned VALUES (asgID_Increment, '3', '1', '4');

/*Query 7*/ 
SELECT MAX(reqID) FROM Request
INSERT INTO Request VALUES (reqID_Increment, '1', '1', '12/06/2019'); 

/*Query 8*/ 
SELECT COUNT(*) FROM Room, Booking WHERE Room.hotelID='145' AND Booking.hotelID='145' AND Room.roomNo NOT IN (SELECT Room.roomNo FROM Booking WHERE Room.roomNo=Booking.roomNo);

/*Query 9*/ 
SELECT COUNT(*) FROM Booking B WHERE B.hotelID='145';

/*Query 10*/ 
SELECT Booking.roomNo FROM Booking WHERE (Booking.hotelID=hotelID AND Booking.bookingDate BETWEEN bookingDate AND bookingDate::date interval '1 week') GROUP BY Booking.roomNo;

/*Query 11*/ 
SELECT * FROM Booking B, Room R WHERE R.roomNo=B.roomNo AND R.hotelID=B.hotelID AND B.bookingDate BETWEEN date1 AND date2 ORDER By B.price DESC Limit k;

/*Query 12*/
SELECT b.price FROM Booking AS b WHERE b.customer=(SELECT customerID FROM Customer WHERE Customer.fName='rzqs' AND Customer.lName='eyeg') ORDER BY b.price DESC LIMIT 4;

/*Query 13*/
SELECT SUM(price) FROM Booking WHERE Booking.hotelID='1' AND Booking.customer=(SELECT customerID FROM Customer WHERE Customer.fName='rzqs' AND Customer.lName='eyeg') AND Booking.bookingDate BETWEEN '01/01/2001' AND '02/01/2001';

/*Query 14*/
SELECT repairType, hotelID, roomNo FROM Repair WHERE Repair.mCompany=(SELECT cmpID FROM MaintenanceCompany WHERE MaintenanceCompany.name='iqcq'; 

/*Query 15*/ 
SELECT M.name FROM maintenanceCompany M, Repair R WHERE M.cmpID = R.mCompany GROUP BY M.name ORDER BY COUNT(*) DESC LIMIT 4;

/*Query 16*/ 
SELECT DATE_PART('year', repairDate), COUNT(*) FROM Repair WHERE Repair.hotelID='145' AND Repair.roomNo='4' GROUP BY DATE_PART('year', repairDate)";








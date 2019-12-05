INSERT INTO Customer
VALUES (customerID, fName, lName, Address, phNo, DOB, gender);

INSERT INTO Room 
VALUES (hotelID, roomNo, roomType);

INSERT INTO MaintenanceCompany
VALUES (cmpID, name, address , isCertified);

INSERT INTO Repair 
VALUES (rID, hotelID, roomNo, mCompany, repairDate, description, repairType);

/* number 5 */
INSERT INTO Booking
VALUES (bID, (SELECT customerID FROM Customer WHERE fName=firstName AND lName=lastName), hotelID, roomNo, bookingDate, noOfPeople, price);

/* Jose doing 6 + 7 */
INSERT INTO Assigned
VALUES(StaffID, HotelID, roomNo);

/* 7 */ 
INSERT INTO Repair
VALUES (hotelID, roomNo, repairDate);

INSERT INTO Request
VALUES (ManagerID, repairID, repairDate);

/* number 8 */
SELECT COUNT(hotelID)
FROM Room, Booking
WHERE Room.hotelID=hotelID AND (Room.hotelID, Room.roomNo) NOT IN (SELECT hotelID, roomNo FROM Booking)

/* number 9 */
SELECT COUNT(RoomNo)
FROM Hotel, Booking 
WHERE Booking.hotelID=hotelID AND (Room.hotelID, Room.BID) IN (SELECT HotelID, roomNo FROM Booking)

/* number 10 */
SELECT roomNo
FROM Booking
WHERE Booking.hotelID=hotelID AND bookingDate BETWEEN date AND DATE_ADD(date, INTERVAL 1 WEEK)

/*11*/ 
/*Get top k rooms with highest price for a date range*/
SELECT TOP K
FROM Booking B, Room R
WHERE B.bookingDate BETWEEN #FirstDate# AND #SecondDate# AND B.roomNo = R.roomNo
Order By B.price DESC 

/* num 12 */
/* Given a customer Name, List Top K highest booking price for a customer */
SELECT TOP k
FROM (SELECT price FROM Booking WHERE Booking.customer=(SELECT customerID FROM Customer WHERE Customer.fName=firstName AND Customer.lName=lastName) ORDER BY price DESC);

/* num 13 */
/* Given a hotelID, customer Name and date range get the total cost incurred by the customer */
SELECT SUM(price)
FROM Booking
WHERE Booking.hotelID=hotelID AND Booking.customer=(SELECT customerID FROM Customer WHERE Customer.fName=firstName AND Customer.lName=lastName) AND Booking.date BETWEEN date1 AND date2;

/* num 14 */
/* Given a Maintenance company name list all the repairs along with repairType, hotelID and roomNo */
SELECT repairType, hotelID, roomNo
FROM Repair
WHERE Repair.mCompany=(SELECT cmpID FROM MaintenanceCompany WHERE MaintenanceCompany.name=mName);

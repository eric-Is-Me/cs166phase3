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

/* number 10 */
SELECT roomNo
FROM Booking
WHERE Booking.hotelID=hotelID AND bookingDate BETWEEN date AND DATE_ADD(date, INTERVAL 1 WEEK)

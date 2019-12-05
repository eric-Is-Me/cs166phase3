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

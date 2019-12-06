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
SELECT COUNT(*)
FROM Room, Booking
WHERE Room.hotelID='1' AND Booking.hotelID='1' AND Room.roomNo NOT IN (SELECT Room.roomNo FROM Booking WHERE Room.roomNo=Booking.roomNo);

/* number 9 */
SELECT COUNT(*) 
FROM Booking B, Room R 
WHERE B.hotelID = R.hotelID AND R.roomNo IN (SELECT R.roomNo FROM Booking B WHERE R.roomNo = B.roomNo) AND R.hotelID 

/* number 10 */
SELECT roomNo     
FROM Booking
WHERE Booking.hotelID='1' AND bookingDate BETWEEN '6/20/2011'::date AND '6/20/2011'::date + interval '10 year';

/*11*/ 
/*Get top k rooms with highest price for a date range*/
SELECT price
FROM Booking B, Room R
WHERE R.roomNo = B.roomNo AND B.bookingDate BETWEEN 'FirstDate' AND 'SecondDate'
Order By B.price DESC LIMIT k; 

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

/* num 15 */
/*Get top k maintenance companies based on repair count */ 
SELECT Name
FROM Maintenance M, Repair R 
WHERE M.cmpID = R.mCompany
ORDER BY COUNT(rID) DESC LIMIT K

/* num 16 */
/* Given a hotelID, roomNo, get the count of repairs per year */
SELECT DATE_PART('year', repairDate) AS "Year", COUNT(*) AS "Number of Repairs"
FROM Repair
WHERE Repair.hotelID=hotelID AND Repair.roomNo=roomNo
GROUP BY DATE_PART('year', repairDate);

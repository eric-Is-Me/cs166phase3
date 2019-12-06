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

/* number 8 */
SELECT COUNT(hotelID)
FROM Room, Booking
WHERE Room.hotelID=hotelID AND (Room.hotelID, Room.roomNo) NOT IN (SELECT hotelID, roomNo FROM Booking);

/* number 10 */
SELECT roomNo
FROM Booking
WHERE Booking.hotelID=hotelID AND bookingDate BETWEEN date AND DATE_ADD(date, INTERVAL 1 WEEK);

/* num 12 */
/* Given a customer Name, List Top K highest booking price for a customer */
SELECT *
FROM (SELECT price FROM Booking WHERE Booking.customer=(SELECT customerID FROM Customer WHERE Customer.fName=firstName AND Customer.lName=lastName) ORDER BY price DESC);
LIMIT k

/* num 13 */
/* Given a hotelID, customer Name and date range get the total cost incurred by the customer */
SELECT SUM(price)
FROM Booking
WHERE Booking.hotelID=hotelID AND Booking.customer=(SELECT customerID FROM Customer WHERE Customer.fName=firstName AND Customer.lName=lastName) AND Booking.date BETWEEN date1 AND date2;

/* num 14 */
/* Given a Maintenance company name list all the repairs along with repairType, hotelID and roomNo */
SELECT repairType AS "Repair Type", hotelID AS "Hotel ID", roomNo AS "Room Number"
FROM Repair
WHERE Repair.mCompany=(SELECT cmpID FROM MaintenanceCompany WHERE MaintenanceCompany.name=mName);

/* num 16 */
/* Given a hotelID, roomNo, get the count of repairs per year */
SELECT DATE_PART('year', repairDate) AS "Year", COUNT(*) AS "Number of Repairs"
FROM Repair
WHERE Repair.hotelID=hotelID AND Repair.roomNo=roomNo
GROUP BY DATE_PART('year', repairDate);
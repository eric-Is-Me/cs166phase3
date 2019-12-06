CREATE INDEX Hotel_Index
ON Hotel
USING BTREE 
(hotel_ID);

CREATE INDEX Staff_Index
ON Staff
USING BTREE
(SSN);

CREATE INDEX Room_Index
ON Room
USING BTREE
(RoomNo);

CREATE INDEX Customer_Index
ON Customer
USING BTREE
(customerID);

CREATE INDEX MaintenanceCompany_Index
ON MaintenanceCompany
USING BTREE
(cmpID); 

CREATE INDEX Booking_Index
ON Booking
USING BTREE;
(bookingDate)

CREATE INDEX Repair_Index
ON Repair
USING BTREE
(repairType);

CREATE INDEX Request_Index
ON Request
USING BTREE
(reqID);

CREATE INDEX Assigned_Index
ON Assigned
USING BTREE
(asgID);

# hotel_billing_system


Explanation of the Code:-

To explain the working of this code, let us divide it into two parts.

Firstly, Let us look at the GUI first:

1. It consists of 3 text fields and 2 JCalendar that require the customer’s information to be added to the database.

2. Then, we add an “ADD” button, which inserts data into the database from the text fields.


Now in the retrieval part, we will apply the following:

1. Build a connection first with the database using the Connection object.

2. Inject the query that stores table data in ResultSet.

3. Finally, send data to Jtable.

4. Calculate the total bill by getting the number of days of stay from dates entered by the user & multiplying it by 1000, and finally adding the result to the room-service charge.

5. This information also gets stored in the database.


Requirements:-

Java Compiler IDE

Swing (GUI interface)



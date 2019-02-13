# election
The future election system

#Description
This web app purpose is to manage a voting system.
In the GUI window there is a chart that presents the top 10 candidates, and on it's left there's a room to enter userId, password and candidate the user chooses to vote to.
If the user voted already exist - the system will update his vote in the DB and reload the chart.

Regarding the DB- I chose to use h2. The table description is detailed in the assumptions. 





#assumptions: 

1. The DB has already an exist campaign. I will not deal with a several amount of different campaign due to lack of time, but in order to do so we could add another key to Votes which stand for which campaign the vote is related to
2. The DB has a table Votes which the following fields: (userId, vote)
   Table creation query:
   
	CREATE TABLE VOTES(userId INT PRIMARY KEY,vote INT NOT NULL);
	INSERT INTO VOTES VALUES(9, 100);
	INSERT INTO VOTES VALUES(7, 98);
	INSERT INTO VOTES VALUES(1, 92);
	INSERT INTO VOTES VALUES(2, 92);
	INSERT INTO VOTES VALUES(3, 92);
	INSERT INTO VOTES VALUES(4, 92);
	INSERT INTO VOTES VALUES(5, 92);
	INSERT INTO VOTES VALUES(57, 92);
	INSERT INTO VOTES VALUES(750, 87);
	INSERT INTO VOTES VALUES(640, 84);
	INSERT INTO VOTES VALUES(641, 84);
	INSERT INTO VOTES VALUES(79, 79);
	INSERT INTO VOTES VALUES(120, 75);
	INSERT INTO VOTES VALUES(200, 48);
	INSERT INTO VOTES VALUES(11, 45);
	INSERT INTO VOTES VALUES(201, 48);
	INSERT INTO VOTES VALUES(202, 48);
	INSERT INTO VOTES VALUES(203, 1);


3. Since I didn't want to spend time on finding the best way to do upsert in SQL I decided to try insert and if there is an error- use catch. An upsert feature will cost much less.
4. The user has entered a correct password. I skipped the validations part of the password
5. I did some mininal validations only due to lack of time. In addition- the gui doesn't indicate on errors- I decided to log them into the console(since I don't wanna waste a time on creating the popups) 


# user-info

Delprojekt till rent-a-thingy med scopet att hantera uppdatering av användares uppgifter. 

## Database
Projektet behöver en database, SQLite3, som ska finns i db-mappen med namnet user-info.db. Det ska finnas en tabell enligt följade:
CREATE TABLE "request-info" (id INTEGER PRIMARY KEY AUTOINCREMENT, "request-date" DATE NOT NULL, url VARCHAR (255) NOT NULL, type VARCHAR (255) NOT NULL);

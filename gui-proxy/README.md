# gui-proxy

Det ska finnas en databas i ./db med namn "gui-proxy" med en tabell med följande ddl för att det ska fungera.
CREATE TABLE "request-info" (
    id INTEGER PRIMARY KEY AUTOINCREMENT, 
    "request-date" DATE NOT NULL, 
    url VARCHAR (255) NOT NULL, 
    type VARCHAR (255) NOT NULL);

Tabellen måste finnas innan applikationen startar.

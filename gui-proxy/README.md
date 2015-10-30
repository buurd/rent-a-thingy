# gui-proxy

Det ska finnas en databas i ./db med namn "gui-proxy" med en tabell med följande ddl för att det ska fungera.
CREATE TABLE "user-registration" (
  id INTEGER PRIMARY KEY,
  "registration-date" DATE NOT NULL,
  username VARCHAR (255) NOT NULL,
  content TEXT);

Tabellen måste finnas innan applikationen startar.

# current-user

Given a timestamp this service will aggregate information from register-user and user-info into a "snapshot" of the users information at that time.

This service shall police it dependencies to implement caching, as hard as possible. Expected behaviour is slow first request and very fast second request.



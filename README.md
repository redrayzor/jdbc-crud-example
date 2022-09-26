# jdbc-mssql-example

This Spring Boot app is a simple example that connects to a PostgreSQL database and performs CRUD operations in response to HTTP requests on API endpoints.

In order to use this app, create an empty PostgreSQL database.  Edit src/main/resources/application.properties to match the username, password, and database name that you set up in your database.  After starting the database, initialize the articles table by running the following query:

CREATE TABLE articles
(
id BIGSERIAL PRIMARY KEY NOT NULL,
title VARCHAR(255),
description VARCHAR(255),
published BOOLEAN
);

You can now start the Spring Boot app and test the endpoints using Postman.  You may have to refresh the Maven project if the driver can't be found.

If you are running Postman on the same machine as the one you are using to run your Spring Boot app, the default endpoint is:

localhost:8080/api/articles

Example requests:
GET - read all objects.
POST - create a new object.  Requires a JSON body with the following format:

{
    "title": "First Article",
    "description": "Hello, World!"
}

An id will automatically be generated, and you can update and delete the objects that you created.  For example, if an object has an id of 1, the default endpoint would be:

localhost:8080/api/articles/1

Example requests:
GET - read the object with an id of 1.
PUT - update fields in the object with an id of 1.  The content format of the request body in should be similar to that of the POST request above.
DELETE - delete the object with that id.

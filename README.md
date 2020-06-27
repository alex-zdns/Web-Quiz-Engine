# Web Quiz Engine

A simple engine for creating and solving quizzes through HTTP API. <br>
It uses an embedded H2 database to store all data in the file system. <br>
It is studying project from [JetBrains Academy](https://hyperskill.org/) <br>

## Description
The service API supports creating, getting and solving quizzes.
Each quiz contains an id, title, text and some options. 
Some options are correct (from 0 to all). The answer is not returned to the API.

## Operations and their results
By default, the application runs on the port `8889`

To perform any actions with quizzes a user has to be registered and then authorized via HTTP Basic Auth.
Otherwise, the service returns the HTTP `401 (Unauthorized) code`.

---
### Register a new user
To register a new user, the client needs to send a JSON with `email` and `password` via 
`POST` request to `/api/register`: <br>
```json
{
  "email": "test@gmail.com",
  "password": "secret"
}
```
The service returns `200 (OK)` status code if the registration has been completed successfully. <br>
If the `email` is already taken by another user, the service will return the `400 (Bad request)` status code. <br>

Here are some additional restrictions to the format of user credentials:

* the email must have a valid format (with `@` and `.`);
* the password must have at __least five__ characters.

If any of them is not satisfied, the service will also return the `400 (Bad request)` status code.


All the following operations need a registered user to be successfully completed.

### Create a new quiz
To create a new quiz, the client needs to send a JSON as the request's body via `POST`
to `/api/quizzes`. The JSON should contain the four fields:


* `title`: a string, __required__;
* `text`: a string, __required__;
* `options`: an array of strings, required, should contain at least 2 items;
* `answer`: an array of indexes of correct options, optional, since all options can be wrong.


Here is a new JSON quiz as an example:
```json
{
  "title": "Coffee drinks",
  "text": "Select only coffee drinks.",
  "options": ["Americano","Tea","Cappuccino","Sprite"],
  "answer": [0,2]
}
```

The response contains the same JSON with generated `id`, but does not include `answer`. <br> 
Here is an example:
```json
{
  "id": 1,
  "title": "Coffee drinks",
  "text": "Select only coffee drinks.",
  "options": ["Americano","Tea","Cappuccino","Sprite"]
}
```
If the request JSON does not contain `title` or `text`, or they are empty strings (""),
then the response is `404`. If the number of options in the quiz is less than 2, the response is `404` as well.

### Get a quiz by id
To get a quiz by `id`, the client sends the `GET` request to `/api/quizzes/{id}`.

Here is a response example:
```json
{
  "id": 2,
  "title": "The Java Logo",
  "text": "What is depicted on the Java logo?",
  "options": ["Robot","Tea leaf","Cup of coffee","Bug"]
}
```

If the specified quiz does not exist, the server returns the `404 (Not found)` status code.

### Get all quizzes (with paging)
The number of stored quizzes can be very large. In this regard, obtaining all quizzes is performed page by page.
To get all existing quizzes in the service, the client sends the `GET` request to `/api/quizzes`.

We can pass the `page` param to navigate through pages `/api/quizzes?page=1`. Pages start from 0 (the first page). <br>
Also, we can pass the `pageSize` param to change size of page `/api/quizzes?pageSize=5`. By default, page size is 10.
If there is no quizzes, content is empty.

In all cases, the status code is HTTP 200 (OK).

### Solving a quiz
To solve a quiz, the client sends the `POST` request to `/api/quizzes/{id}/solve` with a JSON that
contains the indexes of all chosen options as the answer. This looks like a regular JSON object with
key `answer` and value as the array: `{"answer": [0,2]}`.

The service returns a JSON with two fields: `success` (`true` or `false`) and `feedback` (just a string). <br>
There are three possible responses:

* If the passed answer is correct:
```json
{"success":true,"feedback":"Congratulations, you're right!"}
```

* If the answer is incorrect:
```json
{"success":false,"feedback":"Wrong answer! Please, try again."}
```

* If the specified quiz does not exist, the server returns the `404 (Not found)` status code.

### Get all completions of quizzes (with paging)
The API provides an operation to get all completions of quizzes for a user.
A response is separated by pages, since the service may return a lot of data.

To get all completions of quizzes, the client sends
the `GET` request to `/api/quizzes/completed` together with the user auth data.

Since it is allowed to solve a quiz multiple times, the response may contain duplicate quizzes, but with different completion date.

### Deleting a quiz
It is possible to delete a quiz, but this can only be done by its creator.

To delete a quiz, the client sends the `DELETE` request to `/api/quizzes/{id}`.

There are three possible responses:
* If the operation was successful, the service returns the `204 (No content)` status code without any content.
* If the specified quiz does not exist, the server returns `404 (Not found)`. 
* If the specified user is not the author of this quiz, the response is the `403 (Forbidden)` status code.

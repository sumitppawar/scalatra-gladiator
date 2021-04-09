# scalatra-gladiator #

## Build & Run ##

```sh
git clone git@github.com:sumitppawar/scalatra-gladiator.git
$ cd scalatra-gladiator
$ sbt
> ~jetty:start
> browse
```

If `browse` doesn't launch your browser, manually open [http://localhost:8080/](http://localhost:8080/) in your browser.

What we are building ? `REST API for Users`

`POST   /users/login` 

```js
Request body 
{
  "userName": "",
  "pwd": "" 
}

Response 
{"taken": "abcrfiugrfu34if"}
```

`GET   /users`

```js

Header
Authrization -> bearer {token}

Response 
[
  {"fname", "lname", "dob", "address"}
]
```
`GET   /users/{id}`

```js
Header
Authrization -> bearer {token}

Response 
{"fname", "lname", "dob", "address"}
```

`PUT   /users/{id}`

```js
Header
Authrization -> bearer {token}
Request Body
{"fname", "lname", "dob", "address"}

Response 
{"fname", "lname", "dob", "address"}
```

`POST   /users`

```js
Header
Authrization -> bearer {token}
Request Body
{"fname", "lname", "dob", "address"}

Response 
{"fname", "lname", "dob", "address"}
```
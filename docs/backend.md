# Backend Documentation

## API Overwiew

#### Register User Endpoint

- purpose:

- example request:

  ```bash
  http://localhost:<port>/registerUser?email=<email>&password=<password>
  ```

- succesful response

  ```json
  {
    "result": "success",
    "email": "<email address>",
    "response": "<boolean>",
    "message": "<string>"
  }
  ```

#### Get Meik Endpoint

- purpose: to get a single meik entity by meikId

- example request:

  ```bash
  http://localhost:<port>/getMeikById?id=<meikId></meikId></port>
  ```

- succesful response

  ```json
  {
    "meikID": "<id>",
    "data": {
        "result":"success",
        "user":{
            "<meikAttribute>":"<value>",
            "..."
            }
        }
    "image": "<base64 encoded string>"
  }
  ```

#### Get All Meiks Endpoint

- purpose: to get all meik entities

- example request:

  ```bash
  http://localhost:<port>/getAllMeiks</port>
  ```

- succesful response

  ```json
  [
    {
      "image": "<base64 encoded string>",
      "year": "<string>",
      "imageURL": "<int>",
      "name": "<string>",
      "concentration": "<string>",
      "location": "<string",
      "id": "<string>",
      "text": "<string>",
      "email": "<string>",
      "tags": ["<tag0>", "<tag1>"]
    },

    "..."
  ]
  ```

#### Update Meik Endpoint

- purpose: To update existing meik entity

- example request:

  ```bash
      http://localhost:<port>/updateMeik?id=<user-uid>&collections=meiks&location=<location>&tag=<tags>
  ```

- succesful response example

  ```json
  {
    "uid": "KHwC5IemOqXjMG79IVcO8a6XBUK2",
    "result": { "location_status": "Success" },
    "status": "Success"
  }
  ```

#### New First Year Endpoint

- purpose: create first year entity

- example request:

  ```bash
      http://localhost:<port>/newFirstYear?name=BigDog&concentration=<concentration>&location=<Location>&tags=<tags>&email=<email>
  ```

- succesful response

  ```json
  {
    "result": "no_error"
  }
  ```

#### Get Rec Meiks Endpoint

- purpose: This uses our algorithm to recommend meiks for our first year.

- example request:

  ```bash
      http://localhost:<port>/getRecMeiks?uid=<uid>
  ```

- succesful response

  ```json
  {
    "uid": "<uid>",
    "result": "status",
    "meiks": ["List of meiks"],
    "images": { "meiks": "meiks images" }
  }
  ```

#### Update Search Endpoint

- purpose:

- example request:

  ```bash
      http://localhost:3232/updateSearch?uid=<uid>&searched=<values-searched>
  ```

- succesful response

  ```json
  {
    "uid": "uid",
    "result": {
      "result": "status",
      "new_search": {
        "Languanges": "3",
        "Dance": "7",
        "Pre-Med": "4",
        "Arts": "6"
      },
      "no_error": "boolean"
    },
    "status": "success"
  }
  ```

### The algorithm:

The algorithm we chose to recommend meiks have a few a few parts. First we use the given first year tags and given them a rating of 5 and save those ratings. Then we use the first years search values and use the number of times they search something to give those categories ratings, and then we use collaborative filtering to fill in the rest of the categories that are missing ratings. With those given ratings, we return meiks that have the first years highest ranking tag withing their tag.

### Cachcing

Caching is used when we get all users information and user images from our database. This saves time when we refresh our pages, when we need images for other functions, and saves us from making too many api calls and exhuasting our system.

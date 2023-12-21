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

- purpose: to update existing meik entity

- example request:

  ```bash
      http://localhost:<port>/**
  ```

- succesful response

  ```json
  {
    "": ""
  }
  ```

#### New First Year Endpoint

- purpose: create first year entity

- example request:

  ```bash
      http://localhost:<port>/**
  ```

- succesful response

  ```json
  {
    "": ""
  }
  ```

#### Get Rec Meiks Endpoint

- purpose:

- example request:

  ```bash
      http://localhost:<port>/**
  ```

- succesful response

  ```json
  {
    "": ""
  }
  ```

#### Update Search Endpoint

- purpose:

- example request:

  ```bash
      http://localhost:<port>/**
  ```

- succesful response

  ```json
  {
    "": ""
  }
  ```

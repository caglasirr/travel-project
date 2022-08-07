{
"info": {
"_postman_id": "21932798-4573-4bec-8de8-3814fbb184b1",
"name": "TravelCollection",
"schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
},
"item": [
{
"name": "AdminRequests",
"item": [
{
"name": "AddTripRequest",
"request": {
"method": "POST",
"header": [],
"body": {
"mode": "raw",
"raw": " {\r\n  \t  \"fromCity\":\"İstanbul\",\r\n\t  \"toCity\":\"Ankara\",\r\n       \"date\":\"14.06.1998\",\r\n       \"vehicleType\":\"PLANE\"\r\n }",
"options": {
"raw": {
"language": "json"
}
}
},
"url": {
"raw": "localhost:8088/admins/1/trip",
"host": [
"localhost"
],
"port": "8088",
"path": [
"admins",
"1",
"trip"
]
}
},
"response": []
},
{
"name": "AddTripTwoRequest",
"request": {
"method": "POST",
"header": [],
"body": {
"mode": "raw",
"raw": "{\r\n  \t  \"fromCity\":\"İstanbul\",\r\n\t  \"toCity\":\"İzmir\",\r\n       \"date\":\"14.06.1998\",\r\n       \"vehicleType\":\"PLANE\"\r\n }",
"options": {
"raw": {
"language": "json"
}
}
},
"url": {
"raw": "localhost:8088/admins/1/trip",
"host": [
"localhost"
],
"port": "8088",
"path": [
"admins",
"1",
"trip"
]
}
},
"response": []
},
{
"name": "CancelTripRequest",
"request": {
"method": "PUT",
"header": [],
"url": {
"raw": "localhost:8088/admins/1/trip/2",
"host": [
"localhost"
],
"port": "8088",
"path": [
"admins",
"1",
"trip",
"2"
]
}
},
"response": []
},
{
"name": "TicketNumberAndAmountRequest",
"request": {
"method": "GET",
"header": [],
"url": {
"raw": "localhost:8088/admins/tickets",
"host": [
"localhost"
],
"port": "8088",
"path": [
"admins",
"tickets"
]
},
"description": "lsllss"
},
"response": []
}
]
},
{
"name": "UserRequests",
"item": [
{
"name": "UserRegisterRequestForCorporateUser",
"request": {
"method": "POST",
"header": [],
"body": {
"mode": "raw",
"raw": "{\r\n    \"name\":\"Çağla\",\r\n    \"surname\":\"Sır\",\r\n    \"email\":\"deneme1@gmail.com\",\r\n    \"password\":\"password\",\r\n    \"phoneNumber\":\"5332563198\",\r\n    \"userType\":\"CORPORATE\"\r\n}",
"options": {
"raw": {
"language": "json"
}
}
},
"url": {
"raw": "localhost:8086/users/register",
"host": [
"localhost"
],
"port": "8086",
"path": [
"users",
"register"
]
}
},
"response": []
},
{
"name": "UserRegisterRequestForRetailUser",
"request": {
"method": "POST",
"header": [],
"body": {
"mode": "raw",
"raw": "{\r\n    \"name\":\"Çağla\",\r\n    \"surname\":\"Sır\",\r\n    \"email\":\"deneme@gmail.com\",\r\n   \t\"password\":\"password\",\r\n    \"phoneNumber\":\"5332563198\",\r\n    \"userType\":\"RETAIL\"\r\n}",
"options": {
"raw": {
"language": "json"
}
}
},
"url": {
"raw": "localhost:8086/users/register",
"host": [
"localhost"
],
"port": "8086",
"path": [
"users",
"register"
]
}
},
"response": []
},
{
"name": "UserLoginRequest",
"request": {
"method": "POST",
"header": [],
"body": {
"mode": "raw",
"raw": "{\r\n    \"email\":\"deneme@gmail.com\",\r\n    \"password\":\"password\"\r\n}",
"options": {
"raw": {
"language": "json"
}
}
},
"url": {
"raw": "localhost:8086/users/login",
"host": [
"localhost"
],
"port": "8086",
"path": [
"users",
"login"
]
}
},
"response": []
}
]
},
{
"name": "UserSearchTripRequests",
"item": [
{
"name": "SearchTripByCityRequest",
"request": {
"method": "GET",
"header": [],
"url": {
"raw": "localhost:8086/users/trip/İstanbul/Ankara",
"host": [
"localhost"
],
"port": "8086",
"path": [
"users",
"trip",
"İstanbul",
"Ankara"
]
}
},
"response": []
},
{
"name": "SearchTripByDateRequest",
"request": {
"method": "GET",
"header": [],
"url": {
"raw": "localhost:8086/users/trip/date/14.06.1998",
"host": [
"localhost"
],
"port": "8086",
"path": [
"users",
"trip",
"date",
"14.06.1998"
]
}
},
"response": []
},
{
"name": "SearchTripByVehicleTypeRequest",
"request": {
"method": "GET",
"header": [],
"url": {
"raw": "localhost:8086/users/trip/PLANE",
"host": [
"localhost"
],
"port": "8086",
"path": [
"users",
"trip",
"PLANE"
]
}
},
"response": []
}
]
},
{
"name": "UserTicketRequests",
"item": [
{
"name": "UserBuyTicketRequest",
"request": {
"method": "POST",
"header": [],
"body": {
"mode": "raw",
"raw": "{\r\n   \"tripId\":1,\r\n    \"paymentType\":\"CREDIT_CARD\",\r\n   \"passenger\": [\r\n    {\r\n       \"name\":\"Selen\",\r\n       \"surname\":\"Sır\",\r\n       \"sex\":\"WOMAN\"\r\n   },\r\n   {\r\n       \"name\":\"Ali\",\r\n       \"surname\":\"Toprak\",\r\n       \"sex\":\"MAN\"\r\n   }]\r\n}",
"options": {
"raw": {
"language": "json"
}
}
},
"url": {
"raw": "localhost:8086/users/1/ticket",
"host": [
"localhost"
],
"port": "8086",
"path": [
"users",
"1",
"ticket"
]
}
},
"response": []
},
{
"name": "UserFindTicketRequest",
"request": {
"method": "GET",
"header": []
},
"response": []
}
]
}
]
}
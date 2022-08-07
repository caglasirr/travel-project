# travel-project

<pre>
 {
        "link": "localhost:8088/admins/{adminId}/trip",
        "httpMethodType": "Post",
        "purpose": "Admin'in sefer eklemesini sağlayan endpoint.",
        "requestData": {
  	        "fromCity":"İstanbul",
	        "toCity":"Venedik",
            "date":"14.06.1998",
            "vehicleType":"PLANE"
         }
  },
    {
        "link": "localhost:8088/admins/{adminId}/trip/{tripId}",
        "httpMethodType": "Put",
        "purpose": "Admin'in path variable olarak verilen id'ye sahip seferi iptal etmesini sağlayan endpoint.",
    },
{
        "link": "localhost:8088/admins/tickets",
        "httpMethodType": "Get",
        "purpose": "Admin'in toplam bilet satışını ve bu satıştan elde edilen toplam ücreti görmesini sağlayan endpoint.",
},
{
        "link": "localhost:8086/users/register",
        "httpMethodType": "Post",
        "purpose": "User'ın register olmasını sağlayan endpoint. UserType CORPORATE ya da RETAIL olabilir.",
        "requestData": {
    	    "name":"Çağla",
            "surname":"Sır",
    	    "email":"deneme1@gmail.com",
   	        "password":"password",
    	    "phoneNumber":"5332563198",
    	    "userType":"CORPORATE"
        }
},
{
        "link": "localhost:8086/users/login",
        "httpMethodType": "Post",
        "purpose": "User'ın login olmasını sağlayan endpoint.",
        "requestData": {
    	    "email":"deneme@gmail.com",
    	    "password":"password"
         }
},
{
        "link": "localhost:8086/users/trip/date/{date}",
        "httpMethodType": "Get",
        "purpose": "User'ın tarihe göre sefer aramasını sağlayan endpoint. Tarih dd.mm.yyyy formatında verilir."
},
{
        "link": "localhost:8086/users/trip/{fromCity}/{toCity}",
        "httpMethodType": "Get",
        "purpose": "User'ın şehire göre sefer aramasını sağlayan endpoint."
},
{
        "link": "localhost:8086/users/trip/{vehicleType}",
        "httpMethodType": "Get",
        "purpose": "User'ın taşıt türüne göre sefer aramasını sağlayan endpoint. VehicleType BUS ya da PLANE olabilir."
},
{
        "link": "localhost:8086/users/{userId}/ticket",
        "httpMethodType": "Post",
        "purpose": "Path variable'da id'si verilen user'ın bilet almasını sağlayan endpoint. PaymentType CREDIT_CARD ya da EFT olarak verilebilir.",
        "requestData": {
   	        "tripId":1,
    	    "paymentType":"CREDIT_CARD",
   	        "passenger": [
    	        {
       	            "name":"Selen",
       	            "surname":"Sır",
       	            "sex":"WOMAN"
   	            },
   	            {
      	            "name":"Ali",
       	            "surname":"Toprak",
       	            "sex":"MAN"
   	            }]
        }
},
{
        "link": "localhost:8086/users/{userId}/tickets",
        "httpMethodType": "Get",
        "purpose": "Path variable'da id'si verilen user'ın biletlerini görüntülemesini sağlayan endpoint."
}
</pre>
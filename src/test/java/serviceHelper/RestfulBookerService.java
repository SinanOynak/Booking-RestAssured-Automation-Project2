package serviceHelper;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import models.TokenRequest;

public class RestfulBookerService {

    public Response createBooking(){
        String createBookingBody = "{\n" +
                "    \"firstname\" : \"Jim\",\n" +
                "    \"lastname\" : \"Brown\",\n" +
                "    \"totalprice\" : 111,\n" +
                "    \"depositpaid\" : true,\n" +
                "    \"bookingdates\" : {\n" +
                "        \"checkin\" : \"2018-01-01\",\n" +
                "        \"checkout\" : \"2019-01-01\"\n" +
                "    },\n" +
                "    \"additionalneeds\" : \"Breakfast\"\n" +
                "}";

       return RestAssured.given()
                .header("Content-Type","application/json")
                .body(createBookingBody)
                .log().all(true)
                .post("https://restful-booker.herokuapp.com/booking")
                .andReturn();
    }

    public Response authCreateToken(){
        TokenRequest tokenRequest = new TokenRequest("admin","password123");
        String requestToken = new Gson().toJson(tokenRequest);

       return RestAssured.given()
                .header("Content-Type","application/json")
                .body(requestToken)
                .log().all(true)
                .post("https://restful-booker.herokuapp.com/auth")
                .andReturn();
    }

    public Response deleteBooking(String token, String bookingid){
      return  RestAssured.given()
                .header("Content-Type","application/json")
                .header("Cookie","token="+token)
                .log().all(true)
                .delete("https://restful-booker.herokuapp.com/booking/" + bookingid);
    }

    public Response getBooking(){
        return RestAssured.given()
                .get("https://restful-booker.herokuapp.com/booking")
                .andReturn();
    }

    public Response putUpdateBooking(String token, String bookingid){
        String updateBookingBody = "{\n" +
                "    \"firstname\" : \"Sinan\",\n" +
                "    \"lastname\" : \"Oynak\",\n" +
                "    \"totalprice\" : 111,\n" +
                "    \"depositpaid\" : true,\n" +
                "    \"bookingdates\" : {\n" +
                "        \"checkin\" : \"2018-01-01\",\n" +
                "        \"checkout\" : \"2019-01-01\"\n" +
                "    },\n" +
                "    \"additionalneeds\" : \"Breakfast\"\n" +
                "}";

        return  RestAssured.given()
                .body(updateBookingBody)
                .header("Content-Type","application/json")
                .header("Cookie","token="+token)
                .header("Accept","application/json")
                .log().all(true)
                .put("https://restful-booker.herokuapp.com/booking/"+bookingid)
                .andReturn();
    }

}

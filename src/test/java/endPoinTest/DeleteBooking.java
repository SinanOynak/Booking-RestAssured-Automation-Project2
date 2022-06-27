package endPoinTest;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.*;
import models.TokenRequest;
import serviceHelper.RestfulBookerService;

public class DeleteBooking {
    String token;
    String bookingid;
    RestfulBookerService restfulBookerService = new RestfulBookerService();

    @BeforeMethod
    public void preConditions(){

        Response createTokenResponse = restfulBookerService.createBooking();
        Assert.assertEquals(createTokenResponse.jsonPath().getString("booking.firstname"),"Jim","Booking id bulunamadı");
        bookingid = createTokenResponse.jsonPath().getString("bookingid");

        Response authTokenResponse = restfulBookerService.authCreateToken();
        token = authTokenResponse.jsonPath().getString("token");

    }

    @Test
    public void deleteBookingHappyPath(){

        Response deleteBookingResponse = restfulBookerService.deleteBooking(token, bookingid);
        Assert.assertEquals(deleteBookingResponse.getBody().asString(),"Created","Assertion yanlış geldi");

    }

    @Test(dataProvider = "deleteBookingProvider")
    public void deleteBookingFailedCase(String token, String bookingid, int status, String errorMessage){
        Response deleteBookingResponse = restfulBookerService.deleteBooking(token, bookingid);
        Assert.assertEquals(deleteBookingResponse.statusCode(),status,"Response code" + status + "eşit değil");
    }

    @DataProvider(name = "deleteBookingProvider")
    public Object [][] deleteBookingProvider(){
        return new Object[][]{
                {"",bookingid,200,"Internal Server Error"},
                {"asddf",bookingid,403,"Internal Server"},
                {token,"asddf",403,"Internal"}

        };
    }
}

package endPoinTest;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import responseModel.GetBookingResponseModel;
import serviceHelper.RestfulBookerService;

public class GetBookingsIdTest {

    RestfulBookerService restfulBookerService = new RestfulBookerService();
    String id;

    @BeforeClass
    public void preConditions(){
        Response createdBookingResponse = restfulBookerService.createBooking();
        id = createdBookingResponse.jsonPath().getString("bookingid");
    }

    @Test
    public void getBookingHappyPath(){
        Response getBookingResponse = restfulBookerService.getBooking();
        GetBookingResponseModel[] responseModels = getBookingResponse.as(GetBookingResponseModel[].class);

        for (GetBookingResponseModel getBookingResponseModel: responseModels) {
            if(getBookingResponseModel.bookingid == Integer.parseInt(id)){
                return;
            }
        }

        Assert.fail("Aradığımız id yok");

    }

    @AfterClass
    public void postConditons(){
        Response autTokenResponse = restfulBookerService.authCreateToken();
        String token = autTokenResponse.jsonPath().getString("token");
        restfulBookerService.deleteBooking(token,id);
    }
}

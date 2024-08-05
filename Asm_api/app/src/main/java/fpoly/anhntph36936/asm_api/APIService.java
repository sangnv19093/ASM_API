package fpoly.anhntph36936.asm_api;

import java.util.List;

import fpoly.anhntph36936.asm_api.Model.productModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {


    // cap nhat ipv4
    String DOMAIN = "http://192.168.78.20:3000/";

    // get data
    @GET("/api/list")
    Call<List<productModel>> getProduct();

    @POST("/api/add")
    Call<List<productModel>> addPro(@Body productModel model);

    @DELETE("/api/xoa/{id}")
    Call<List<productModel>> delPro(@Path("id") String id);

    @PUT("/api/update/{id}")
    Call<List<productModel>> upPro(@Path("id") String id,@Body productModel model);






}

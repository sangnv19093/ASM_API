package fpoly.anhntph36936.asm_api;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import fpoly.anhntph36936.asm_api.Adapter.Product_ADT;
import fpoly.anhntph36936.asm_api.Model.productModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    ListView lv_view;
    EditText edt_name, edt_price, edt_img, edt_type;
    Button btn_add, btn_exit;
    List<productModel> list_pro;
    Product_ADT productAdt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(MainActivity.this);
        setContentView(R.layout.activity_main);
        lv_view = findViewById(R.id.lv_view);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIService.DOMAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // get data
        APIService apiService = retrofit.create(APIService.class);
        Call<List<productModel>> call = apiService.getProduct();

        call.enqueue(new Callback<List<productModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<productModel>> call, @NonNull Response<List<productModel>> response) {
                if (response.isSuccessful()){
                    list_pro = response.body();
                    productAdt = new Product_ADT(MainActivity.this, list_pro);
                    lv_view.setAdapter(productAdt);
                }
            }

            @Override
            public void onFailure(Call<List<productModel>> call, Throwable t) {

            }
        });


        findViewById(R.id.fab_them).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAdd();
            }
        });
    }
    private void showAdd(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_them, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();

        edt_name = view.findViewById(R.id.edt_name);
        edt_price = view.findViewById(R.id.edt_price);
        edt_img = view.findViewById(R.id.edt_img);
        edt_type = view.findViewById(R.id.edt_type);

        btn_add = view.findViewById(R.id.btn_add);
        btn_exit = view.findViewById(R.id.btn_out);



        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edt_name.getText().toString();
                String price = edt_price.getText().toString();
                String img = edt_img.getText().toString();
                String type = edt_type.getText().toString();

                productModel model = new productModel();
                model.setName(name);
                model.setPrice(Integer.parseInt(price));
                model.setImg(img);
                model.setPloai(type);

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(APIService.DOMAIN)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                // get data
                APIService apiService = retrofit.create(APIService.class);
                Call<List<productModel>> call = apiService.addPro(model);

                call.enqueue(new Callback<List<productModel>>() {
                    @Override
                    public void onResponse(Call<List<productModel>> call, Response<List<productModel>> response) {
                        if (response.isSuccessful()){
                            list_pro = response.body();
                            Toast.makeText(MainActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();

                            productAdt = new Product_ADT(MainActivity.this, list_pro);
                            reloadData();
                            lv_view.setAdapter(productAdt);
                            alertDialog.dismiss();
                        } else {
                            Toast.makeText(MainActivity.this, "Thêm không thành công", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<productModel>> call, Throwable t) {
                        Log.e("Main", t.getMessage());
                    }
                });


            }
        });

        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });


        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
    }
    public void reloadData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIService.DOMAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService apiService = retrofit.create(APIService.class);
        Call<List<productModel>> call = apiService.getProduct();

        call.enqueue(new Callback<List<productModel>>() {
            @Override
            public void onResponse(Call<List<productModel>> call, Response<List<productModel>> response) {
                if (response.isSuccessful()) {
                    List<productModel> productList = response.body();
                    productAdt.setData(productList);
                } else {
                    // Xử lý lỗi
                }
            }

            @Override
            public void onFailure(Call<List<productModel>> call, Throwable t) {
                // Xử lý lỗi
            }
        });
    }
}
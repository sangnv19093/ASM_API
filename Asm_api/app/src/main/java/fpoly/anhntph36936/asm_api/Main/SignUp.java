package fpoly.anhntph36936.asm_api.Main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import fpoly.anhntph36936.asm_api.R;

public class SignUp extends AppCompatActivity {
    EditText edt_email_sign, edt_pass_sign;
    Button btn_sign, btn_huy;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        edt_email_sign = findViewById(R.id.edt_email_sign);
        edt_pass_sign = findViewById(R.id.edt_pass_sign);
        btn_sign = findViewById(R.id.btn_sign);
        btn_huy = findViewById(R.id.btn_huy);



        mAuth  = FirebaseAuth.getInstance();

        btn_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edt_email_sign.getText().toString().trim();
                String pass = edt_pass_sign.getText().toString().trim();

                if (email.equals("") || pass.equals("")){
                    Toast.makeText(SignUp.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (pass.length() < 6){
                    Toast.makeText(SignUp.this, "Mật khẩu ít nhất có 6 kí tụ", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(SignUp.this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(SignUp.this, "Đăng kí không thành công", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        btn_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUp.this, Login.class));
            }
        });
    }
}
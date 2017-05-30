package info.kamlesh.Franzfactor;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ActionBar;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.Calendar;
import java.util.Map;
import java.util.HashMap;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class Signup extends AppCompatActivity {
    private static final String REGISTER_URL = "http://newtechgadget.in/fpRegister.php";

    public EditText firstname,lastname,password,email,confpassword,dob,city,state,pincode,contactno;
    private int year, month, day;
    private Calendar calendar;
    private RadioGroup radioGenderGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        firstname = (EditText) findViewById(R.id.firstname);
         lastname = (EditText) findViewById(R.id.lastname);
         password =(EditText) findViewById(R.id.password);
         email = (EditText) findViewById(R.id.email);
        confpassword = (EditText) findViewById(R.id.confpassword);
        dob = (EditText) findViewById(R.id.dob);
        city = (EditText) findViewById(R.id.city);
        state = (EditText) findViewById(R.id.state);
        radioGenderGroup = (RadioGroup) findViewById(R.id.radiogender);
        pincode = (EditText) findViewById(R.id.pincode);
        contactno = (EditText) findViewById(R.id.contactno);

        Button btn = (Button) findViewById(R.id.btn_register);

        dob.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                openDatePicker();
                return true;
            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

    }
    public void openDatePicker() {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        year  = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day   = c.get(Calendar.DAY_OF_MONTH);
        //launch datepicker modal
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        dob.setText(+day + "-" + (month + 1) + "-" + year);

                    }
                }, year, month, day);
        datePickerDialog.show();
        return;
    }

    private void registerUser() {
        final String fname = firstname.getText().toString().trim();
        final String lname = lastname.getText().toString().trim();
        final String pass = password.getText().toString().trim();
        final String emai = email.getText().toString().trim();
        final String confpass = confpassword.getText().toString().trim();
        final String gender = ((RadioButton) this.findViewById(radioGenderGroup.getCheckedRadioButtonId())).getText().toString();
        final String dobb = dob.getText().toString().trim();
        final String cityy = city.getText().toString().trim();
        final String statee = state.getText().toString().trim();
        final String pincodee = pincode.getText().toString().trim();
        final String cont = contactno.getText().toString().trim();

        if (TextUtils.isEmpty(fname)) {
            Toast.makeText(getApplicationContext(), "Enter first name!", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(lname)) {
            Toast.makeText(getApplicationContext(), "Enter Lastname!", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(pass)) {
            Toast.makeText(getApplicationContext(), "Enter Password!", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(confpass)) {
            Toast.makeText(getApplicationContext(), "Confirm Password!", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(emai)) {
            Toast.makeText(getApplicationContext(), "Enter Email!", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(dobb)) {
            Toast.makeText(getApplicationContext(), "Enter Date of Birth!", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(cityy)) {
            Toast.makeText(getApplicationContext(), "Enter city!", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(statee)) {
            Toast.makeText(getApplicationContext(), "enter state!", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(pincodee)) {
            Toast.makeText(getApplicationContext(), "Enter Pincode!", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(cont)) {
            Toast.makeText(getApplicationContext(), "Enter Contact no!", Toast.LENGTH_SHORT).show();
            return;
        } else if (pass.length() < 6) {
            Toast.makeText(getApplicationContext(), "minimum password length 6 character", Toast.LENGTH_SHORT).show();
            return;
        } else if (password.getText().toString().equals(confpassword.getText().toString())) {

        } else if (password.getText().toString() != confpassword.getText().toString()) {

            Toast.makeText(getApplicationContext(), "password not same!", Toast.LENGTH_SHORT).show();
            return;
        }

        register(fname, lname, emai, pass, dobb, cityy, statee, pincodee, cont, gender);
    }

    private void register(String fname, String lname, final String emai, String pass, String dobb, String cityy, String statee, String pincodee, String cont, String gender) {
        class RegisterUser extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            RegisterUserClass ruc = new RegisterUserClass();

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String,String>();
                data.put("firstname",params[0]);
                data.put("lastname",params[1]);
                data.put("email",params[2]);
                data.put("password",params[3]);
                data.put("dob",params[4]);
                data.put("city",params[5]);
                data.put("state",params[6]);
                data.put("pincode",params[7]);
                data.put("contact",params[8]);
                data.put("gender",params[9]);


                String result = ruc.sendPostRequest(REGISTER_URL,data);

                return  result;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Signup.this, "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String result) {
                String s = result.trim();
                loading.dismiss();
                if (s.equalsIgnoreCase("email already exist")) {
                    Toast.makeText(getApplicationContext(), "email already exist", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Successfully Registered", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Signup.this, Login.class);
                    startActivity(intent);
                }
            }

        }

        RegisterUser ru = new RegisterUser();
        ru.execute(fname, lname, emai, pass, dobb, cityy, statee, pincodee, cont, gender);
    }
}


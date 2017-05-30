package info.kamlesh.Franzfactor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Apply extends AppCompatActivity {
    private static final String REGISTER_URL = "http://newtechgadget.in/fpfranchiseapply.php";
    public EditText fnam,lnam,Emai,Cit,Stat,Pincod,contactn,flocatio,budge;
    Button apply;
    TextView textView;
    private ProgressDialog loading;

    public static String email = null;
    public void setemai(String string){
        email = string;
    }
    public static String fname = null;
    public void setfname(String string){
        fname = string;
    }
    public static String lname = null;
    public void setLnam(String string){
        lname = string;
    }
    public static String city = null;
    public void setcity(String string){
        city = string;
    }

    public static String state = null;
    public void setstate(String string){
        state = string;
    }

    public static String pincode = null;
    public void setpin(String string){
        pincode = string;
    }

    public static String contact = null;
    public void setContact(String string){
        contact = string;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply);
        fnam = (EditText) findViewById(R.id.firstname);
        lnam = (EditText) findViewById(R.id.lastname);
        Emai = (EditText) findViewById(R.id.email);
        Cit = (EditText) findViewById(R.id.city);
        Stat = (EditText) findViewById(R.id.state);
        Pincod = (EditText) findViewById(R.id.pincode);
        contactn = (EditText) findViewById(R.id.contactno);
        flocatio = (EditText) findViewById(R.id.fra_location);
        budge = (EditText) findViewById(R.id.budget);
        apply = (Button) findViewById(R.id.btn_apply);
        textView = (TextView) findViewById(R.id.fn);
        Bundle extras = getIntent().getExtras();
        String s = (String) extras.get("fname");
        textView.setText(s);

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
        fnam.setText(fname);
        lnam.setText(lname);
        Emai.setText(email);
        Cit.setText(city);
        Stat.setText(state);
        Pincod.setText(pincode);
        contactn.setText(contact);
    }
    private void registerUser() {
        Bundle extras = getIntent().getExtras();
        String s = (String) extras.get("fname");

        final String floc = flocatio.getText().toString().trim();
        final String budget = budge.getText().toString().trim();

        if (TextUtils.isEmpty(floc)) {
            Toast.makeText(getApplicationContext(), "Enter franchise location!", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(budget)) {
            Toast.makeText(getApplicationContext(), "Enter budget!", Toast.LENGTH_SHORT).show();
            return;
        }
        register(fname, lname, email, city, state, pincode, contact, s, floc, budget);
    }

    private void register(String fname, String lname, String email, String city, String state, String pincode, String contact, String s, String floc, String budget) {
        class RegisterUser extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            RegisterUserClass ruc = new RegisterUserClass();

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String,String>();
                data.put("firstname",params[0]);
                data.put("lastname",params[1]);
                data.put("email",params[2]);
                data.put("city",params[3]);
                data.put("state",params[4]);
                data.put("pincode",params[5]);
                data.put("contact",params[6]);
                data.put("fname",params[7]);
                data.put("floc",params[8]);
                data.put("budget",params[9]);

                String result = ruc.sendPostRequest(REGISTER_URL,data);

                return  result;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Apply.this, "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String result) {
                String s = result.trim();
                loading.dismiss();
                if (s.equalsIgnoreCase("oops! Please try again!")) {
                    Toast.makeText(getApplicationContext(), "oops! Please try again!", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Successfully Applied", Toast.LENGTH_LONG).show();
                    finish();
                }
            }

        }

        RegisterUser ru = new RegisterUser();
        ru.execute(fname, lname, email, city, state, pincode, contact, s, floc, budget);
    }

}
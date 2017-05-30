package info.kamlesh.Franzfactor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class Applynow extends AppCompatActivity {

    EditText fname,lname,Email,City,State,Pincode,contactno,flocation,budget;
    Button apply;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applynow);

        fname = (EditText) findViewById(R.id.firstname);
        lname = (EditText) findViewById(R.id.lastname);
        Email = (EditText) findViewById(R.id.email);
        City = (EditText) findViewById(R.id.city);
        State = (EditText) findViewById(R.id.state);
        Pincode = (EditText) findViewById(R.id.pincode);
        contactno = (EditText) findViewById(R.id.contactno);
        flocation = (EditText) findViewById(R.id.fra_location);
        budget = (EditText) findViewById(R.id.budget);
        apply = (Button) findViewById(R.id.btn_apply);

        fname.setText(fn);
        lname.setText(ln);
        Email.setText(email);
        City.setText(city);
        State.setText(state);
        Pincode.setText(pincode);
        contactno.setText(contact);

    }

    String fn,ln,email,city,state,pincode,contact = null;
    public void setFname(String string){
        fn = string;
    }
    public void setLname(String string){
        ln = string;
    }
    public void setemail(String string){
        email = string;
    }
    public void setCity(String string){
        city = string;
    }
    public void setstate(String string){
        state = string;
    }
    public void setPincode(String string){
        pincode = string;
    }
    public void setContact(String string){
        contact = string;
    }

}

package info.kamlesh.Franzfactor;

import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Profile.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Profile extends Fragment {
    private TextView fn, ln, gn, em, dob, cit, stat, pincod, contac;
    private ProgressDialog loading;
    Config session;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Profile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Profile.
     */
    // TODO: Rename and change types and number of parameters
    public static Profile newInstance(String param1, String param2) {
        Profile fragment = new Profile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        fn = (TextView) view.findViewById(R.id.fn);
        ln = (TextView) view.findViewById(R.id.ln);
        gn = (TextView) view.findViewById(R.id.gn);
        em = (TextView) view.findViewById(R.id.eml);
        dob = (TextView) view.findViewById(R.id.db);
        cit = (TextView) view.findViewById(R.id.ct);
        stat = (TextView) view.findViewById(R.id.state);
        pincod = (TextView) view.findViewById(R.id.pincode);
        contac = (TextView) view.findViewById(R.id.cn);

        getData();

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event)   {
                if (keyCode == KeyEvent.KEYCODE_BACK) {

                    Home fragment = new Home();

                    FragmentTransaction transaction=getFragmentManager().beginTransaction();
                    transaction.replace(R.id.mainFrame,fragment);

                    transaction.commit();

                    return true;
                }
                return false;
            }
        });

        return view;
    }

    public static String email = null;

    public void setName(String string) {
        email = string;
    }

    // get name
    // get email

    private void getData() {
        loading = ProgressDialog.show(getActivity(), "Please wait...", "Fetching...", false, false);

        String url = Config.DATA_URL + email;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response) {
        String fname = "";
        String lname = "";
        String gender = "";
        String email = "";
        String dobb = "";
        String city = "";
        String state = "";
        String pincode = "";
        String contact = "";
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);
            JSONObject Data = result.getJSONObject(0);
            fname = Data.getString(Config.KEY_FN);
            lname = Data.getString(Config.KEY_LN);
            gender = Data.getString(Config.KEY_GN);
            email = Data.getString(Config.KEY_EM);
            state = Data.getString(Config.KEY_ST);
            pincode = Data.getString(Config.KEY_PN);
            contact = Data.getString(Config.KEY_CN);
            dobb = Data.getString(Config.KEY_DB);
            city = Data.getString(Config.KEY_CT);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        fn.setText(fname);
        ln.setText(lname);
        gn.setText(gender);
        em.setText(email);
        dob.setText(dobb);
        cit.setText(city);
        contac.setText(contact);
        stat.setText(state);
        pincod.setText(pincode);

        Apply apply = new Apply();
        apply.setemai(email);
        apply.setfname(fname);
        apply.setLnam(lname);
        apply.setcity(city);
        apply.setstate(state);
        apply.setContact(contact);
        apply.setpin(pincode);

    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

package info.kamlesh.Franzfactor;

import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link editmyfranchise.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link editmyfranchise#newInstance} factory method to
 * create an instance of this fragment.
 */
public class editmyfranchise extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    EditText fnf,fif,scf,fdf,lnf;
    public TextView ffid;
    private static final String UPDATE_URL = "http://newtechgadget.in/fpupdatefranchise.php";
    private Button btn_update;
    private int PICK_IMAGE_REQUEST = 1;
    Spinner ct;
    ImageView img;
    private Bitmap bitmap;
    private ProgressDialog loading;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public editmyfranchise() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment editmyfranchise.
     */
    // TODO: Rename and change types and number of parameters
    public static editmyfranchise newInstance(String param1, String param2) {
        editmyfranchise fragment = new editmyfranchise();
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
        View view = inflater.inflate(R.layout.fragment_editmyfranchise, container, false);
        fnf = (EditText) view.findViewById(R.id.fn);
        scf = (EditText) view.findViewById(R.id.sc);
        fif = (EditText) view.findViewById(R.id.fi);
        fdf = (EditText) view.findViewById(R.id.fd);
        lnf = (EditText) view.findViewById(R.id.ln);
        img = (ImageView) view.findViewById(R.id.img);
        ffid = (TextView) view.findViewById(R.id.fid);
        ct = (Spinner) view.findViewById(R.id.ct);
        btn_update = (Button) view.findViewById(R.id.btn_updatee);

        view.setFocusableInTouchMode(true);
      view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event)   {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    myfranchise fragment = new myfranchise();

                    FragmentTransaction transaction=getFragmentManager().beginTransaction();
                    transaction.replace(R.id.mainFrame,fragment);

                    transaction.commit();

                    return true;
                }
                return false;
            }
        });


        String strtext=getArguments().getString("message");
        String sub =getArguments().getString("sub");
        String inv =getArguments().getString("inv");
        fnf.setText(strtext);
        scf.setText(sub);
        fif.setText(inv);
        getData();
        final String[] cta = {"Health",
                "Hotel",
                "Sports",
                "Fashion",
                "Business Services",
                "Auto",
                "Education",
                "Food"};
        ArrayAdapter<String> category =new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, cta);
        category.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ct.setAdapter(category);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser();
            }
        });
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatefranchise();
            }
        });

        return view;
    }
    private void updatefranchise() {
        final String franchise = fnf.getText().toString().trim();
        final String subcategory = scf.getText().toString().trim();
        final String franchiseinv = fif.getText().toString().trim();
        final String franchisedt = fdf.getText().toString().trim();
        final String licence = lnf.getText().toString().trim();
        final String category = ct.getSelectedItem().toString().trim();
        final String fid = ffid.getText().toString().trim();
        String image = getStringImagee(bitmap);

        if (TextUtils.isEmpty(franchise)) {
            Toast.makeText(getActivity().getApplicationContext(), "Enter franchise name!", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(subcategory)) {
            Toast.makeText(getActivity().getApplicationContext(), "Enter subcategory!", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(franchiseinv)) {
            Toast.makeText(getActivity().getApplicationContext(), "Enter Investment rs.!", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(franchisedt)) {
            Toast.makeText(getActivity().getApplicationContext(), "Enter franchise detail!", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(licence)) {
            Toast.makeText(getActivity().getApplicationContext(), "Enter licence no!", Toast.LENGTH_SHORT).show();
            return;
        }
        register(franchise, subcategory, franchiseinv, franchisedt, licence, category, image, fid);
    }

    private void register(String franchise, String subcategory, String franchiseinv, String franchisedt, String licence, String category, String image, String fid) {
        class Editmyfranchise extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            RegisterUserClass ruc = new RegisterUserClass();

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String,String>();
                data.put("fname",params[0]);
                data.put("subcat",params[1]);
                data.put("finv",params[2]);
                data.put("fdesc",params[3]);
                data.put("licence",params[4]);
                data.put("category",params[5]);
                data.put("logo",params[6]);
                data.put("id",params[7]);

                String result = ruc.sendPostRequest(UPDATE_URL,data);
                return  result;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getActivity(), "Please Wait", "Franchise Updating", true, true);
            }

            @Override
            protected void onPostExecute(String result) {
                String s = result.trim();
                loading.dismiss();
                if (s.equalsIgnoreCase("Franchise Updated Successfully")) {
                    Toast.makeText(getActivity().getApplicationContext(), "Franchise Updated", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getActivity().getApplicationContext(), "Sorry try again", Toast.LENGTH_LONG).show();

                }
            }

        }

        Editmyfranchise ru = new Editmyfranchise();
        ru.execute(franchise, subcategory, franchiseinv, franchisedt, licence, category, image, fid);
    }
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                //Setting the Bitmap to ImageView
                img.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public String getStringImagee(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void getData() {
        String strtext=getArguments().getString("message");
        loading = ProgressDialog.show(getActivity(), "Please wait...", "Fetching...", false, false);

        String url = Config.EDITFRANCHISE_URL + strtext;

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
        String fdesc = "";
        String licence = "";
        String categoryy = "";
        String imgurl = "";
        String id = "";

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);
            JSONObject Data = result.getJSONObject(0);
            fdesc = Data.getString(Config.KEY_FDESC);
            licence= Data.getString(Config.KEY_LICENCE);
            categoryy = Data.getString(Config.KEY_CATEGORY);
            imgurl = Data.getString(Config.KEY_IMGURL);
            id = Data.getString(Config.KEY_ID);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        fdf.setText(fdesc);
        lnf.setText(licence);
        Glide.with(this)
                .load(Config.IMG_LOADURL + imgurl)
                .into(img);
        ffid.setText(id);
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

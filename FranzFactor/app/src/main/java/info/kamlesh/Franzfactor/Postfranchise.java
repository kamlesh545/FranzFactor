package info.kamlesh.Franzfactor;

import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.app.Fragment;
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
import android.widget.RadioButton;
import android.widget.Spinner;
import android.graphics.Bitmap;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Postfranchise.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Postfranchise#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Postfranchise extends Fragment {
    private static final String POST_URL = "http://newtechgadget.in/fppostfranchise.php";

    EditText fn,fi,sc,fd,ln;
    Spinner ct;
    ImageView imgg;
    private Bitmap bitmap;

    private int PICK_IMAGE_REQUEST = 1;

    private String UPLOAD_URL ="http://newtechgadget.in/fpuploadlogo.php";

    private String KEY_IMAGE = "image";
    private String KEY_NAME = "name";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Postfranchise() {
        // Required empty public constructor
    }

    public static String email = null;

    public void setemail(String string) {
        email = string;
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Postfranchise.
     */
    // TODO: Rename and change types and number of parameters
    public static Postfranchise newInstance(String param1, String param2) {
        Postfranchise fragment = new Postfranchise();
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
        View view = inflater.inflate(R.layout.fragment_postfranchise, container, false);

        fn = (EditText) view.findViewById(R.id.fn);
        sc = (EditText) view.findViewById(R.id.sc);
        fi = (EditText) view.findViewById(R.id.fi);
        fd = (EditText) view.findViewById(R.id.fd);
        ln = (EditText) view.findViewById(R.id.ln);
        imgg = (ImageView) view.findViewById(R.id.img);
        ct = (Spinner) view.findViewById(R.id.ct);
        Button btn_post = (Button) view.findViewById(R.id.btn_post);
        final String[] cta = {"beautyandhealth",
                "hotel",
                "Sports",
                "fashion",
                "Business Services",
                "auto",
                "Education",
                "Food"};
        ArrayAdapter<String> category =new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, cta);
        category.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ct.setAdapter(category);
        imgg.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        showFileChooser();
    }
});

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

        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postfranchise();
            }
        });
        return view;
    }
    private void postfranchise() {
        final String franchisename = fn.getText().toString().trim();
        final String subcategory = sc.getText().toString().trim();
        final String franchiseinv = fi.getText().toString().trim();
        final String franchisedt = fd.getText().toString().trim();
        final String licence = ln.getText().toString().trim();
        final String category = ct.getSelectedItem().toString().trim();
        String image = getStringImage(bitmap);

        if (TextUtils.isEmpty(franchisename)) {
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
        register(franchisename, subcategory, franchiseinv, franchisedt, licence, category, image, email);
    }

    private void register(String franchisename, String subcategory, String franchiseinv, String franchisedt, String licence, String category, String image, String email) {
        class RegisterUser extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            RegisterUserClass ruc = new RegisterUserClass();

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String,String>();
                data.put("franchisename",params[0]);
                data.put("subcategory",params[1]);
                data.put("franchiseinv",params[2]);
                data.put("franchisedetail",params[3]);
                data.put("licence",params[4]);
                data.put("category",params[5]);
                data.put("image",params[6]);
                data.put("email",params[7]);

                String result = ruc.sendPostRequest(POST_URL,data);

                return  result;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getActivity(), "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String result) {
                String s = result.trim();
                loading.dismiss();
                if (s.equalsIgnoreCase("Franchise already listed")) {
                    Toast.makeText(getActivity().getApplicationContext(), "Franchise already listed", Toast.LENGTH_LONG).show();
                }
                else if (s.equalsIgnoreCase("you have already posted your Franchise you can post only one franchise")){
                    Toast.makeText(getActivity().getApplicationContext(), "you have already posted your Franchise you can post only one franchise", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getActivity().getApplicationContext(), "Successfully Posted", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getActivity(), Dashboard.class);
                    startActivity(intent);
                }
            }

        }

        RegisterUser ru = new RegisterUser();
        ru.execute(franchisename, subcategory, franchiseinv, franchisedt, licence, category, image, email);
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
                imgg.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
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

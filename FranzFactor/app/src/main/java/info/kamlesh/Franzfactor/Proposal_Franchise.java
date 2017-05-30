package info.kamlesh.Franzfactor;

import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Proposal_Franchise.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Proposal_Franchise#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Proposal_Franchise extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ProgressDialog loading;
    private RecyclerView mRVproposalview;
    private Adapterproposalfranchise mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    int position;
    public List<Dataproposal> data = new ArrayList<>();



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Proposal_Franchise() {
        // Required empty public constructor
    }

    public static String frname = null;

    public void setFrname(String string) {
        frname = string;
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Proposal_Franchise.
     */
    // TODO: Rename and change types and number of parameters
    public static Proposal_Franchise newInstance(String param1, String param2) {
        Proposal_Franchise fragment = new Proposal_Franchise();
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
        View myInflatedView = inflater.inflate(R.layout.fragment_proposal__franchise, container, false);

        mRVproposalview = (RecyclerView)myInflatedView.findViewById(R.id.recycler_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) myInflatedView.findViewById(R.id.activity_main_swipe_refresh_layout);
        TextView empty = (TextView) myInflatedView.findViewById(R.id.empty_view);
        getData();

        mSwipeRefreshLayout.setColorSchemeResources(R.color.orange, R.color.green, R.color.blue);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new android.os.Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        clearApplicationData();
                        getData();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 2500);
            }
        });

        myInflatedView.setFocusableInTouchMode(true);
        myInflatedView.requestFocus();
        myInflatedView.setOnKeyListener(new View.OnKeyListener() {
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


        return myInflatedView;
    }
    public void clearApplicationData()
    {
        File cache = getActivity().getCacheDir();
        File appDir = new File(cache.getParent());
        if (appDir.exists()) {
            String[] children = appDir.list();
            for (String s : children) {
                if (!s.equals("lib")) {
                    deleteDir(new File(appDir, s));
                    Log.i("TAG", "**************** File /data/data/APP_PACKAGE/" + s + " DELETED *******************");
                }
            }
        }
    }

    public static boolean deleteDir(File dir)
    {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }
    private void getData() {
        loading = ProgressDialog.show(getActivity(), "Please wait...", "Fetching...", false, false);

        String fr = frname.replaceAll("\\s+","");
        String url = Config.PROPOSAL_URL + fr;
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



    private void showJSON(String result) {
        data.clear();
        try {
            JSONArray jArray = new JSONArray(result);

            // Extract data from json and store into ArrayList as class objects
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject json_data = jArray.getJSONObject(i);
                Dataproposal dataproposal = new Dataproposal();
                dataproposal.franchise_name = json_data.getString(Config.KEY_FRNAME);
                dataproposal.firstname = json_data.getString(Config.KEY_fname);
                dataproposal.lastname = json_data.getString(Config.KEY_LN);
                dataproposal.emaill = json_data.getString(Config.KEY_EM);
                dataproposal.city_ = json_data.getString(Config.KEY_CT);
                dataproposal.state_ = json_data.getString(Config.KEY_ST);
                dataproposal.pincode_ = json_data.getString(Config.KEY_PN);
                dataproposal.contact_ = json_data.getString(Config.KEY_CN);
                dataproposal.flocation_ = json_data.getString(Config.KEY_FLOCATION);
                dataproposal.budget_ = json_data.getString(Config.KEY_BUDGET);
                data.add(dataproposal);

            }
            mAdapter = new Adapterproposalfranchise(getActivity(), data);
            mRVproposalview.setAdapter(mAdapter);
            mRVproposalview.setLayoutManager(new LinearLayoutManager(getActivity()));

        } catch (JSONException e) {
            Toast.makeText(getActivity()

                    , e.toString(), Toast.LENGTH_LONG).show();
        }
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

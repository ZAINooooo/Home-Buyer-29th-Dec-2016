package com.example.asad.homebuyerproject;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Builder_Broker_Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Builder_Broker_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Builder_Broker_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private AutoCompleteTextView mcity;
    private EditText mCompanyname,mCompanydetail;

    private String[] languages = {

            "Bagh",
            "Bhimber",
            "khuiratta",
            "Kotli",
            "Mangla",
            "Mirpur",
            "Muzaffarabad",
            "Plandri",
            "Rawalakot",
            "Punch",
            "Amir Chah",
            "Bazdar",
            "Bela",
            "Bellpat",
            "Bagh",
            "Burj",
            "Chagai",
            "Chah Sandan",
            "Chakku",
            "Chaman",
            "Chhatr",
            "Dalbandin",
            "Dera Bugti",
            "Diwana",
            "Duki",
            "Dushi",
            "Duzab",
            "Gajar",
            "Gandava",
            "Garhi Khairo",
            "Garruck",
            "Ghazluna",
            "Girdan",
            "Gulistan",
            "Gwadar",
            "Hab Chauki",
            "Hameedabad",
            "Harnai",
            "Jhal",
            "Jhal Jhao",
            "Jhatpat",
            "Jiwani",
            "Kalat",
            "Kamararod",
            "Kanpur",
            "Kappar",
            "Katuri",
            "Khuzdar",
            "Kohan",
            "Korak",
            "Lasbela",
            "Loralai",
            "Mand",
            "Mashki Chah",
            "Mastung",
            "Naseerabad",
            "Nushki",
            "Ormara",
            "Palantuk",
            "Panjgur",
            "Piharak",
            "Qamruddin Karez",
            "Qila Abdullah",
            "Qila Ladgasht",
            "Qila Safed",
            "Quetta",
            "Rakhni",
            "Robat Thana",
            "Rodkhan",
            "Saindak",
            "Sanjawi",
            "Saruna",
            "Shingar",
            "Shorap",
            "Sibi",
            "Sonmiani",
            "Spezand",
            "Sui",
            "Suntsar",
            "Surab",
            "Thalo",
            "Tump",
            "Turbat",
            "Umarao",
            "pirMahal",
            "Vitakri",
            "Washap",
            "Wasjuk",
            "Astor",
            "Hunza",
            "Gilgit",
            "Nagar",
            "Skardu",
            "Shangrila",
            "Shandur",
            "Bajaur",
            "Hangu",
            "Malakand",
            "Miram Shah",
            "Mohmand",
            "Khyber",
            "Kurram",
            "North Waziristan",
            "South Waziristan",
            "Abbottabad",
            "Ayubia",
            "Adezai",
            "Bannu",
            "Birote",
            "Chakdara",
            "Charsadda",
            "Darya Khan",
            "Dera Ismail Khan",
            "Drasan",
            "Hangu",
            "Haripur",
            "Kalam",
            "Karak",
            "Khanaspur",
            "Kohat",
            "Kohistan",
            "Lakki Marwat",
            "Lower Dir",
            "Malakand",
            "Mansehra",
            "Mardan",
            "Mongora",
            "Nowshera",
            "Peshawar",
            "Saidu Sharif",
            "Shangla",
            "Swabi",
            "Swat",
            "Tangi",
            "Thall",
            "Tordher",
            "Upper Dir",
            "Ali Pur",
            "Arifwala",
            "Attock",
            "Bhalwal",
            "Bahawalnagar",
            "Bahawalpur",
            "Bhakkar",
            "Chailianwala",
            "Chakwal",
            "Chichawatni",
            "Chiniot",
            "Daska",
            "Darya Khan",
            "Dhaular",
            "Dinga",
            "Dipalpur",
            "Faisalabad",
            "Gadar",
            "Ghakhar Mandi",
            "Gujranwala",
            "Gujrat",
            "Gujar Khan",
            "Hafizabad",
            "Haroonabad",
            "Jampur",
            "Jhang",
            "Jhelum",
            "Kalabagh",
            "Kasur",
            "Kamokey",
            "Khanewal",
            "Khanpur",
            "Khushab",
            "Kot Addu",
            "Jahania",
            "Jalla Araain",
            "Laar",
            "Lahore",
            "Lalamusa",
            "Layyah",
            "Lodhran",
            "Mamoori",
            "Mandi Bahauddin",
            "Makhdoom Aali",
            "Mian Channu",
            "Minawala",
            "Mianwali",
            "Multan",
            "Murree",
            "Muridke",
            "Muzaffargarh",
            "Narowal",
            "Okara",
            "Rajan Pur",
            "Pak Pattan",
            "Panjgur",
            "Pattoki",
            "Raiwind",
            "Rahim Yar Khan",
            "Rawalpindi",
            "Rohri",
            "Sadiqabad",
            "Safdar Abad",
            "Sahiwal",
            "Sangla Hill",
            "Samberial",
            "Sargodha",
            "Sohawa",
            "Talagang",
            "Islamabad",
            "Tarbela",
            "Taxila",
            "Toba Tek Singh",
            "Vehari",
            "Wah Cantonment",
            "Wazirabad",
            "Dadu",
            "Diplo",
            "Ghotki",
            "Hala",
            "Hyderabad",
            "Islamkot",
            "Jacobabad",
            "Jamesabad",
            "Jamshoro",
            "Karachi",
            "Kashmor",
            "Keti Bandar",
            "Khairpur",
            "Klupro",
            "Khokhropur",
            "Kotri",
            "Larkana",
            "Mathi",
            "Matiari",
            "Mirpur Batoro",
            "Mirpur Khas",
            "Mirpur Sakro",
            "Mithi",
            "Moro",
            "Nagar Parkar",
            "Naushara",
            "Noushero Feroz",
            "Nawabshah",
            "Pokran",
            "Qambar",
            "Ranipur",
            "Ratodero",
            "Rohri",
            "Sanghar",
            "Shahpur Chakar",
            "Shikarpur",
            "Sukkur",
            "Tando Adam",
            "Tando Allahyar",
            "Tando Bago",
            "Thatta",
            "Umarkot",
            "Warah",

    };


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button mNext;
    private View layout;
    private Button mRegister,mLogin;
    private String OwnerType;
    private ArrayList<String> propertyData=new ArrayList<>();


    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Builder_Broker_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Builder_Broker_Fragment newInstance(String param1, String param2) {
        Builder_Broker_Fragment fragment = new Builder_Broker_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Builder_Broker_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            OwnerType=getArguments().getString("OwnerType");//use this string also to save owner type

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        layout=inflater.inflate(R.layout.fragment_builder__broker, container, false);


        ///////////////////////////////////////////////////////////////////////////////////////

             /*
             panel is opening if user is not logged in

             panel2 is opening if user is logged in
              */


        ///////////////////////////////////////////////////////////////////////////////////////




        TypeCasting(layout);
        LoginButtonClickEvent();   //login button event
        RegisterButtonClickEvent(); //register button event
        ArrayAdapterMethod();  //autocomplete cities
        NextButtonclcikEvent();  //next button click event
        OpenLayoutbyCheckingUserStatus();  //open panel based on user status


        return  layout;

    }

    private void OpenLayoutbyCheckingUserStatus() {

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {

            LinearLayout r=(LinearLayout)layout.findViewById(R.id.Panel);
            r.setVisibility(View.GONE);
        }
        else
        {
            RelativeLayout r=(RelativeLayout)layout.findViewById(R.id.panel2);
            r.setVisibility(View.GONE);
            mNext.setVisibility(View.INVISIBLE);
        }
    }

    private void NextButtonclcikEvent() {



            mNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isValidName(mCompanyname.getText().toString())){
                        mCompanyname.setError("Company Name should not be Empty");
                    }
                    if (!isValidName(mCompanydetail.getText().toString())){
                        mCompanydetail.setError("Company Detail should not be Empty");
                    }
                    if (!isValidName(mcity.getText().toString())){
                        mcity.setError("City should not be Empty");
                    }
                    else {
                        propertyData.add("SellerType");
                        propertyData.add("Builder");
                        propertyData.add("CompanyName");
                        propertyData.add(mCompanyname.getText().toString());
                        propertyData.add("CompanyDetail");
                        propertyData.add(mCompanydetail.getText().toString());
                        propertyData.add("CompanyCity");
                        propertyData.add(mcity.getText().toString());
                        Intent next = new Intent(getActivity(), SellRentActivty.class);
                        next.putStringArrayListExtra("data", propertyData);
                        startActivity(next);
                    }
                }
            });

    }

    private void ArrayAdapterMethod() {
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, languages);
        mcity.setAdapter(adapter);
        mcity.setThreshold(1);
        mcity.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);

            }

        });

    }

    private void RegisterButtonClickEvent() {

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next=new Intent(getActivity(),Register.class);
                startActivity(next);
            }
        });

    }

    private void LoginButtonClickEvent() {
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next=new Intent(getActivity(),LoginRegister.class);
                startActivity(next);
            }
        });


    }
    private boolean isValidName(String name) {
        Pattern pattern;
        Matcher matcher;
        final String NAME_PATTERN = "(?=.*[a-z])(?=.*[A-Z]).+$";
        pattern = Pattern.compile(NAME_PATTERN);
        matcher = pattern.matcher(name);
        return matcher.matches();
    }
    private void TypeCasting(View layout) {

        mNext=(Button)layout.findViewById(R.id.Next);
        mCompanyname=(EditText)layout.findViewById(R.id.Company_name);
        mCompanydetail=(EditText)layout.findViewById(R.id.Company_detail);
        mcity=(AutoCompleteTextView)layout.findViewById(R.id.city);
        //come in use when user is not signed in
        mRegister=(Button)layout.findViewById(R.id.Register);
        mLogin=(Button)layout.findViewById(R.id.Login);
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}

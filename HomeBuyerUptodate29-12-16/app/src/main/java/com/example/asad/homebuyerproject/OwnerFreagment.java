package com.example.asad.homebuyerproject;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asad.homebuyerproject.utils.KeyboardHelper;
import com.hbb20.CountryCodePicker;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OwnerFreagment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OwnerFreagment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OwnerFreagment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String Data;
    private String mParam2;
    private ArrayList<String> propertyData=new ArrayList<>();
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
    private OnFragmentInteractionListener mListener;
    private TextView mOwner, mBuilder, mAgent;
    private String Type;
    private LinearLayout Panel;
    private LinearLayout Panel2;
    private Button mNext;
    private View layout;
    private Button mRegister, mlogin;
    //  CountryCodePicker ccp;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;
    private TextView mName, mEmail, mPassword, mPhone, email_id, Name;
    private GoogleApiClient client;
    private CountryCodePicker ccp;
    private String ccp1;
    private Boolean mgetValue;
    private TextView mTerms;
    private static final String TAG = "MainActivity";
    private String auth_failed = "Error";
    boolean msetvalue = false;
    private AutoCompleteTextView mCity;
    private String OwnerType;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OwnerFreagment.
     */
    // TODO: Rename and change types and number of parameters
    public static OwnerFreagment newInstance(String param1, String param2) {
        OwnerFreagment fragment = new OwnerFreagment();

        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    public OwnerFreagment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Data = getArguments().getString("Type");
            OwnerType=getArguments().getString("OwnerType");//use this string also to save owner type

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       // Inflate the layout for this fragment
        layout = inflater.inflate(R.layout.owner_freagment, container, false);
        //Dtabase reference and storage
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        //Datebase reference
        mAuth = FirebaseAuth.getInstance();
        OpenFragmetnBasedOnUserStatus(); //open panle by checking user status
        TypeCasting(layout);
        RegisterButtonClick(); //registration button event
        CountryCode();  //getting country code for registration
        ArrayAdapterMethod();//setting autocomplete cities
        //set data
        SettingPreviousActivityData();
        NextButtonClickEvent();
        OwnerAgentBuilderClickEvent();
        mAgent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAgent.setBackgroundColor(Color.parseColor("#E1F5FE"));
                mOwner.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mBuilder.setBackgroundColor(Color.parseColor("#FFFFFF"));
                Intent next = new Intent(getActivity(), Activity_Post_property_Click.class);
                Type = "Agent";
                next.putExtra("TypeSelected", Type);
                startActivity(next);
            }
        });
        return layout;
    }

    private void OwnerAgentBuilderClickEvent() {
        mOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOwner.setBackgroundColor(Color.parseColor("#E1F5FE"));
                mAgent.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mBuilder.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }
        });
        mBuilder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBuilder.setBackgroundColor(Color.parseColor("#E1F5FE"));
                mOwner.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mAgent.setBackgroundColor(Color.parseColor("#FFFFFF"));
                Intent next = new Intent(getActivity(), Activity_Post_property_Click.class);
                Type = "Builder";
                next.putExtra("TypeSelected", Type);
                startActivity(next);
            }
        });
    }

    private void NextButtonClickEvent() {
        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isValidName(mCity.getText().toString())) {
                    mCity.setError("City Should not be null");
                } else {
                    propertyData.add("SellerType");
                    propertyData.add("Owner");
                    propertyData.add("OwnerCity");
                    propertyData.add(mCity.getText().toString());
                    Intent next = new Intent(getActivity(), SellRentActivty.class);
                    next.putStringArrayListExtra("data", propertyData);
                    startActivity(next);
                }
            }
        });
    }
    private void CountryCode() {
        try {
            ccp1 = ccp.getDefaultCountryCodeWithPlus();
            ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
                @Override
                public void onCountrySelected() {
                    ccp1 = ccp.getSelectedCountryCodeWithPlus();
                }
            });
        } catch (Exception ex) {

        }
    }
    private void OpenFragmetnBasedOnUserStatus() {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Panel = (LinearLayout) layout.findViewById(R.id.panel);
            Panel.setVisibility(View.GONE);
        } else {
            Panel2 = (LinearLayout) layout.findViewById(R.id.panel2);
            Panel2.setVisibility(View.GONE);
        }
    }
    private void SettingPreviousActivityData() {
        if (Data.equals("Builder")) {
            mBuilder.setBackgroundColor(Color.parseColor("#E1F5FE"));
        } else if (Data.equals("Owner")) {
            mOwner.setBackgroundColor(Color.parseColor("#E1F5FE"));
        } else if (Data.equals("Agent")) {
            mAgent.setBackgroundColor(Color.parseColor("#E1F5FE"));
        } else {
            Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
        }
    }
    private void TypeCasting(View layout) {
        ccp = (CountryCodePicker) layout.findViewById(R.id.ccp);
        //come in use when user is signed in
        mAgent = (TextView) layout.findViewById(R.id.radio2);
        mOwner = (TextView) layout.findViewById(R.id.radio0);
        mBuilder = (TextView) layout.findViewById(R.id.radio1);
        mNext = (Button) layout.findViewById(R.id.Next);
        //only data ous save in this activity is this
        mCity=(AutoCompleteTextView)layout.findViewById(R.id.city);
        //use when user is not singed in
        mName = (TextView) layout.findViewById(R.id.name);
        mPhone = (TextView) layout.findViewById(R.id.mobile);
        mEmail = (TextView) layout.findViewById(R.id.email);
        mPassword = (TextView) layout.findViewById(R.id.password);
        mRegister = (Button) layout.findViewById(R.id.Readytogo);
    }
       //Validate the data
    public boolean ValidateRegisterData() {
        try {
            boolean valid = Validate();
            //successful Validation
            if (valid) {
                msetvalue = true;
            }

        } catch (Exception ex) {

        }
        return msetvalue;
    }
    public boolean Validate() {
        msetvalue = true;
        if (!isValidMail(mEmail.getText().toString())) {
            mEmail.setError("Invalid Email Address");
            msetvalue = false;
        }
        if (!isValidName(mName.getText().toString())) {
            mName.setError("Name Should not be null");
            msetvalue = false;
        }
        if (mPhone.getText().toString().length() != 11) {
            mPhone.setError("Enter Valid Number");
            msetvalue = false;
        }
        if (!isValidPassword(mPassword.getText().toString())) {
            mPassword.setError("Try Upper/Lower Case and Numbers");
            return msetvalue = false;
        }
        return msetvalue;
    }
    private boolean isValidName(String name) {
        Pattern pattern;
        Matcher matcher;
        final String NAME_PATTERN = "(?=.*[a-z])(?=.*[A-Z]).+$";
        pattern = Pattern.compile(NAME_PATTERN);
        matcher = pattern.matcher(name);
        return matcher.matches();
    }
    public static boolean isValidPassword(final String password) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20})";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();
    }
    //Validation on Email
    private boolean isValidMail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    //Register button click event
    public void RegisterButtonClick() {
        mRegister.setOnClickListener((new View.OnClickListener() {
            public void onClick(View v) {
                //Calling Validate Function
                mgetValue = ValidateRegisterData();
                try {
                    if (mgetValue) {
                     FirebaseSaveRegistrationData();
                    }
                } catch (Exception ex) {

                }
            }
        }));
    }
    //Firebase save registration detail of users
    public void FirebaseSaveRegistrationData() {
        //   Toast.makeText(Register.this,"asad",Toast.LENGTH_SHORT).show();
        final String mName1, mEmail1, mPassword1, mPhone1, mPhoneFinal;
        mName1 = mName.getText().toString();
        mEmail1 = mEmail.getText().toString();
        mPassword1 = mPassword.getText().toString();
        mPhone1 = mPhone.getText().toString();
        mPhoneFinal = ccp1.concat("-").concat(mPhone1);
        mAuth.createUserWithEmailAndPassword(mEmail1, mPassword1)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {

                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(getActivity(), "asad", Toast.LENGTH_SHORT).show();
                        try {
                            DatabaseReference current_user_db = mDatabase.child(mAuth.getCurrentUser().getUid());
                            current_user_db.child("Name").setValue(mName1);
                            current_user_db.child("Phone").setValue(mPhoneFinal);
                        }catch (Exception ex)
                        {

                        }
                        mAuth.signOut();
                                Intent intent = new Intent(getActivity(), LoginRegister.class);
                                startActivity(intent);
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(getActivity(), auth_failed, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
    ////////////////////////////////////////////////////////////////////////////////////////

    public void ArrayAdapterMethod() {
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, languages);
        mCity.setAdapter(adapter);
        mCity.setThreshold(1);
        mCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);

            }
        });
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}

package com.example.asad.homebuyerproject;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.security.PrivilegedAction;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Asad on 11/18/2016.
 */

public class Fragment_Rent_All_Part2 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button GetReady;
    private RadioGroup mRadioGroup;
    private RadioButton mImmeately,mSelectDate;
    private TextView mAvailableFrom,mAmountPreview;
    private EditText mAmount,AgeofConstruction;
    private ArrayList<String> propertyData=new ArrayList<>();
    private String RentData,RentData2;
    private LinearLayout Layouttoremove;
    private  String  return_val_in_english,availableddate;
    private Button mPostProperty;

    private Fragment_Rent_Commercial_CommercialLand.OnFragmentInteractionListener mListener;

    public Fragment_Rent_All_Part2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ResidentialFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ResidentialFragment newInstance(String param1, String param2) {
        ResidentialFragment fragment = new ResidentialFragment();
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
            //getting data from parent activity
            RentData = getArguments().getString("RentData");
            RentData2 = getArguments().getString("RentData2");
            propertyData=getArguments().getStringArrayList("data");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view=inflater.inflate(R.layout.fragment_rent_all_part2, container, false);

        TypeCasting(view);
        //Remove Fields which are not needed
        RemoveFields();
        AvailableFromRadioButtonEvent();
        AmountEvent();
        PosstporpetyClickEvent();


        return view;
    }

    private void PosstporpetyClickEvent() {

        mPostProperty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean valid=isValid(mAmount.getText().toString());
                if(!valid)
                {
                   mAmount.setError("Value Needed");
                }
                else
                {

                    //do work after validation this method works so do firebase work here



                    Intent next = new Intent(getActivity(), Additional_Detail_Activity.class);
                    propertyData.add("PropertyPrice");
                    propertyData.add(mAmount.getText().toString());
                    if (!availableddate.matches("")) {
                        propertyData.add("PropertyAvailablity");
                    }
                    propertyData.add(availableddate);
                    if(!AgeofConstruction.getText().toString().matches("")){
                    propertyData.add("AgeOfConstruction");
                    propertyData.add(AgeofConstruction.getText().toString());
                    }
                    next.putStringArrayListExtra("data",propertyData);
                    startActivity(next);

                }
            }
        });
    }

    private void AmountEvent()
    {
        mAmount.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
;
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(mAmount.getText().length() <=9) {
                    EnglishNumberToWords en = new EnglishNumberToWords();
                   // return_val_in_english = en.convert(Long.parseLong(mAmount.getText().toString()));
                    //  if(return_val_in_english !=null) {
                   // mAmountPreview.setText(return_val_in_english);
                    //  }
                    //  Toast.makeText(getActivity(),return_val_in_english,Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getActivity(),"Amount Exceed the limit",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    private void AvailableFromRadioButtonEvent() {
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if(checkedId == R.id.mSelectDate)
                {
                    DateDialog dialog=new DateDialog(mAvailableFrom);
                    FragmentTransaction ft=getFragmentManager().beginTransaction();
                    dialog.show(ft,"Date");
                    availableddate=mAvailableFrom.getText().toString();
                }
                if(checkedId == R.id.mImmediately)
                {
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat mdformat = new SimpleDateFormat("  dd - MM - yyyy  ");
                    String strDate =  mdformat.format(calendar.getTime());
                    mAvailableFrom.setText("Available From : " + strDate);
                    availableddate=mAvailableFrom.getText().toString();
                  //  String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                 //   mAvailableFrom.setText("Available From:" + currentDateTimeString);
                }
            }
        });


    }


    private void TypeCasting(View view) {

        mPostProperty=(Button)view.findViewById(R.id.postproperty);

        Layouttoremove=(LinearLayout)view.findViewById(R.id.PossessionRemove);
        mRadioGroup=(RadioGroup)view.findViewById(R.id.Avaialable);
        mSelectDate=(RadioButton)view.findViewById(R.id.mSelectDate);
        mImmeately=(RadioButton)view.findViewById(R.id.mImmediately);
        mAvailableFrom=(TextView)view.findViewById(R.id.AvailableFrom);
        mAmount=(EditText)view.findViewById(R.id.amount);
        mAmountPreview=(TextView)view.findViewById(R.id.amountpreview);
        AgeofConstruction=(EditText)view.findViewById(R.id.TestingValue);

    }

    private boolean isValid(String  mAmount) {

        Pattern pattern;
        Matcher matcher;
        final String AREA_PATTERN = "^(?=.*[0-9]).+$";
        pattern = Pattern.compile(AREA_PATTERN);
        matcher = pattern.matcher(mAmount);
        return matcher.matches();
    }


    private void RemoveFields() {

        try {



            if(RentData != null || RentData2!= null) {

                if (RentData.equals("RentCommercialLand") || RentData2.equals("RentIndustrialLand")) {
                    Layouttoremove.setVisibility(View.GONE);

                }
            }
        }catch (Exception ex)
        {

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
       /* if (context instanceof Fragment_Rent_Commercial_CommercialLand.OnFragmentInteractionListener) {
            mListener = (Fragment_Rent_Commercial_CommercialLand.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
        */
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



    public class EnglishNumberToWords {

        private final String[] tensNames = {
                "",
                " ten",
                " twenty",
                " thirty",
                " forty",
                " fifty",
                " sixty",
                " seventy",
                " eighty",
                " ninety"
        };

        private final String[] numNames = {
                "",
                " one",
                " two",
                " three",
                " four",
                " five",
                " six",
                " seven",
                " eight",
                " nine",
                " ten",
                " eleven",
                " twelve",
                " thirteen",
                " fourteen",
                " fifteen",
                " sixteen",
                " seventeen",
                " eighteen",
                " nineteen"
        };

        private EnglishNumberToWords() {
        }

        private String convertLessThanOneThousand(int number) {
            String soFar;

            if (number % 100 < 20) {
                soFar = numNames[number % 100];
                number /= 100;
            } else {
                soFar = numNames[number % 10];
                number /= 10;

                soFar = tensNames[number % 10] + soFar;
                number /= 10;
            }
            if (number == 0) return soFar;
            return numNames[number] + " hundred" + soFar;
        }


        public String convert(long number) {
            // 0 to 999 999 999 999
            if (number == 0) {
                return "zero";
            }

            String snumber = Long.toString(number);

            // pad with "0"
            String mask = "000000000000";
            DecimalFormat df = new DecimalFormat(mask);
            snumber = df.format(number);

            // XXXnnnnnnnnn
            int billions = Integer.parseInt(snumber.substring(0, 3));
            // nnnXXXnnnnnn
            int millions = Integer.parseInt(snumber.substring(3, 6));
            // nnnnnnXXXnnn
            int hundredThousands = Integer.parseInt(snumber.substring(6, 9));
            // nnnnnnnnnXXX
            int thousands = Integer.parseInt(snumber.substring(9, 12));

            String tradBillions;
            switch (billions) {
                case 0:
                    tradBillions = "";
                    break;
                case 1:
                    tradBillions = convertLessThanOneThousand(billions)
                            + " billion ";
                    break;
                default:
                    tradBillions = convertLessThanOneThousand(billions)
                            + " billion ";
            }
            String result = tradBillions;

            String tradMillions;
            switch (millions) {
                case 0:
                    tradMillions = "";
                    break;
                case 1:
                    tradMillions = convertLessThanOneThousand(millions)
                            + " million ";
                    break;
                default:
                    tradMillions = convertLessThanOneThousand(millions)
                            + " million ";
            }
            result = result + tradMillions;

            String tradHundredThousands;
            switch (hundredThousands) {
                case 0:
                    tradHundredThousands = "";
                    break;
                case 1:
                    tradHundredThousands = "one thousand ";
                    break;
                default:
                    tradHundredThousands = convertLessThanOneThousand(hundredThousands)
                            + " thousand ";
            }
            result = result + tradHundredThousands;

            String tradThousand;
            tradThousand = convertLessThanOneThousand(thousands);
            result = result + tradThousand;

            // remove extra spaces!
            return result.replaceAll("^\\s+", "").replaceAll("\\b\\s{2,}\\b", " ");
        }
    }


}
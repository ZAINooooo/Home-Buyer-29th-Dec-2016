package com.example.asad.homebuyerproject;

import android.app.Fragment;
import android.app.FragmentManager;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Asad on 11/18/2016.
 */

public class Fragment_Sell_All_Part2 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button GetReady;
    private View view;

    private String SellData, SellData2, SellData3;
    private LinearLayout Layouttoremove;
    private ArrayList<String> propertyData=new ArrayList<>();
    private RadioGroup mRadiogroup,mRadiogroup1;
    private RadioButton mUnderconstruction, mReadytomove;
    private EditText mAmount;
    private  TextView possessionFrom;
    private Button mPostProperyt;
    private  String  return_val_in_english;
    private TextView mAmountPreview;
    private String transactiontype,possessiontype,possession;
    private Fragment_Rent_Commercial_CommercialLand.OnFragmentInteractionListener mListener;

    public Fragment_Sell_All_Part2() {
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
            SellData = getArguments().getString("SellData");
            SellData2 = getArguments().getString("SellData2");
            SellData3 = getArguments().getString("SellData3");
            propertyData=getArguments().getStringArrayList("data");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_sell_all_part2, container, false);

        TypeCasting(view);
        //Remove Fields which are not needed
        RemoveFields();
        mRadiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.underconstruction) {
                    possessiontype="UnderConstruction";
                   /* Fragment_Possession_Sell_All_Part2 frag = new Fragment_Possession_Sell_All_Part2();
                    FragmentManager manager = getFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.ReplaceTest, frag, "Possession");
                    Bundle bundle=new Bundle();
                    bundle.putStringArrayList("data",propertyData);
                    frag.setArguments(bundle);
                    Toast.makeText(getActivity(), "Clicked and Property Data Size"+propertyData.size(), Toast.LENGTH_SHORT).show();
                    transaction.commit();*/
                    DateDialog dialog=new DateDialog(possessionFrom);
                    FragmentTransaction ft=getFragmentManager().beginTransaction();
                    dialog.show(ft,"Date");
                    possession=possessionFrom.getText().toString();

                }
                if (checkedId == R.id.readytomove) {
                    possessiontype="ReadyToMove";
                   /* Fragment_Readytomove_Sell_All_Part2 frag = new Fragment_Readytomove_Sell_All_Part2();
                    FragmentManager manager = getFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.ReplaceTest, frag, "ReadytoMove");
                    Bundle bundle=new Bundle();
                    bundle.putStringArrayList("data",propertyData);
                    frag.setArguments(bundle);
                    transaction.commit();*/
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat mdformat = new SimpleDateFormat("  dd - MM - yyyy  ");
                    String strDate =  mdformat.format(calendar.getTime());
                    possessionFrom.setText("Available From : " + strDate);
                    possession=possessionFrom.getText().toString();
                }
            }
        });
        mRadiogroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.newproperty) {
                    transactiontype="NewProperty";


                }
                if (checkedId == R.id.resaleproperty) {
                    transactiontype="ResaleProperty";

                }
            }
        });
        //Replace fragmets on Radio Button Click

        AmountEvent();
        PostPropertyClickEvent();


        return view;
    }

    private void PostPropertyClickEvent() {

        mPostProperyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean valid = isValid(mAmount.getText().toString());
                if (!valid) {

                    mAmount.setError("Value Needed");

                } else {


                    //do work after validation this method works so do work here
                    Intent next = new Intent(getActivity(), Additional_Detail_Activity.class);
                    propertyData.add("PropertyPrice");
                    propertyData.add(mAmount.getText().toString());
                    propertyData.add("TransactionType");
                    propertyData.add(transactiontype);
                    propertyData.add("PossessionType");
                    propertyData.add(possessiontype);
                    if(!possession.matches("")){
                        propertyData.add("PossessionDate");
                        propertyData.add(possessionFrom.getText().toString());
                    }
                    next.putStringArrayListExtra("data",propertyData);
                    startActivity(next);

                }

            }
        });

    }

    private boolean isValid(String mAmount) {

        Pattern pattern;
        Matcher matcher;
        final String AREA_PATTERN = "^(?=.*[0-9]).+$";
        pattern = Pattern.compile(AREA_PATTERN);
        matcher = pattern.matcher(mAmount);
        return matcher.matches();
    }


    private void TypeCasting(View view) {

        mPostProperyt = (Button) view.findViewById(R.id.postproperty);

        possessionFrom=(TextView)view.findViewById(R.id.PossessionFrom);
        Layouttoremove = (LinearLayout) view.findViewById(R.id.Possession);
        mRadiogroup = (RadioGroup) view.findViewById(R.id.radiogroup123);
        mRadiogroup1 = (RadioGroup) view.findViewById(R.id.radiogroup12);
        mUnderconstruction = (RadioButton) view.findViewById(R.id.underconstruction);
        mReadytomove = (RadioButton) view.findViewById(R.id.readytomove);
        mAmount=(EditText)view.findViewById(R.id.amount);
        mAmountPreview=(TextView)view.findViewById(R.id.amountpreview);

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

                    Fragment_Sell_All_Part2.EnglishNumberToWords en = new Fragment_Sell_All_Part2.EnglishNumberToWords();
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

    private void RemoveFields() {


        try {

            if (SellData != null || SellData2 != null || SellData3 != null) {

                if (SellData3.equals("SellPlot") || SellData.equals("SellCommercialLand") || SellData2.equals("SellIndustrialLand")) {

                    Layouttoremove.setVisibility(View.GONE);
                }
            }
        } catch (Exception ex) {

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

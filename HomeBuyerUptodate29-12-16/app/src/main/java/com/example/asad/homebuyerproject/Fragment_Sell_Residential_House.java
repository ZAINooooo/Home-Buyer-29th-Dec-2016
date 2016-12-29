package com.example.asad.homebuyerproject;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.net.Uri;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioButton;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by Asad on 11/16/2016.
 */

public class Fragment_Sell_Residential_House extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button GetReady;
    private EditText mCoveredarea;
    private RadioGroup mRadioGroup1;
    private RadioGroup mRadioGroup2;
    private LinearLayout mPanle3;
    private TextView mMore;
    private ArrayList<String> propertyData=new ArrayList<>();
    private String furnishedtype,corner;
    private TextView mBathrominc, mBathroomdec, mFloorinc, mFloordec, mTotalFloorinc, mTotalFloordec, mBedroomdec, mBedroominc, mBalconyinc, mBalconydec, mFloorsAllowedinc, mFloorsAlloweddec, mNumberSitesinc, mNumberSitesdecdec;
    private TextView mValueBathroom, mValueFloor, mTotalFloorValue, mValueBedroom, mValueBalcony, mValueFloorsAllowed, mValueNumberSites;
    private Integer BathroomCounter = 1, FloorCounter = 1, TotalFloorCounter = 1, BedroomCounter = 1, BalconyCounter = 1, FloorsAllowedCounter = 1, NumberSitesCounter = 1;

    private OnFragmentInteractionListener mListener;

    public Fragment_Sell_Residential_House() {
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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view = inflater.inflate(R.layout.fragment_sell_residential_house, container, false);
        propertyData=getArguments().getStringArrayList("data");
        TypeCasting(view);
        ButtonClick();


        mRadioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.fullfurnished) {

                    furnishedtype="FullFurnished";

                } else if(checkedId == R.id.semifurnished)
                {
                    furnishedtype="SemiFurnished";
                }else{
                    furnishedtype="NotFurnished";
                }

            }

        });
        mRadioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.corneryes) {

                    corner="YES";

                } else
                {
                    corner="NO";
                }

            }

        });
            /*
        *do not use these methods its not for your use
         */
        MoreTextViewclickEvent();
        MoreTextEvent();





         /*
        On Click for Counters on first panel
         */

        //////////////////////////////////////////////////////////////////////////////////

        mBathroomdec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int copy = DecrementFunction(BathroomCounter);
                mValueBathroom.setText(String.valueOf(copy));
                BathroomCounter = copy;

            }
        });

        mBathrominc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Integer copy = IncrementFunction(BathroomCounter);
                mValueBathroom.setText(String.valueOf(copy));
                BathroomCounter = copy;

            }
        });


        ///////////////////////////////////////////////////


        mBedroomdec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int copy = DecrementFunction(BedroomCounter);
                mValueBedroom.setText(String.valueOf(copy));
                BedroomCounter = copy;

            }
        });

        mBedroominc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Integer copy = IncrementFunction(BedroomCounter);
                mValueBedroom.setText(String.valueOf(copy));
                BedroomCounter = copy;

            }
        });

        /////////////////////////////////
        mFloordec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int copy = DecrementFunction(FloorCounter);
                mValueFloor.setText(String.valueOf(copy));
                FloorCounter = copy;
            }
        });
        mFloorinc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Integer copy = IncrementFunction(FloorCounter);
                mValueFloor.setText(String.valueOf(copy));
                FloorCounter = copy;
            }
        });


        ///////////////////////////////////////////////
        mTotalFloorinc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Integer copy = IncrementFunction(TotalFloorCounter);
                mTotalFloorValue.setText(String.valueOf(copy));
                TotalFloorCounter = copy;
            }
        });

        mTotalFloordec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                int copy = DecrementFunction(TotalFloorCounter);
                mTotalFloorValue.setText(String.valueOf(copy));
                TotalFloorCounter = copy;
            }
        });

        //////////////////////////////////////////////////////////////////////////////

        mBalconyinc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Integer copy = IncrementFunction(BalconyCounter);
                mValueBalcony.setText(String.valueOf(copy));
                BalconyCounter = copy;
            }
        });

        mBalconydec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                int copy = DecrementFunction(BalconyCounter);
                mValueBalcony.setText(String.valueOf(copy));
                BalconyCounter = copy;
            }
        });


        //////////////////////////////////////////////////////////////////////////////////

        mNumberSitesdecdec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int copy = DecrementFunction(NumberSitesCounter);
                mValueNumberSites.setText(String.valueOf(copy));
                NumberSitesCounter = copy;

            }
        });

        mNumberSitesinc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Integer copy = IncrementFunction(NumberSitesCounter);
                mValueNumberSites.setText(String.valueOf(copy));
                NumberSitesCounter = copy;

            }
        });

        /////////////////////////////////
        mFloorsAlloweddec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int copy = DecrementFunction(FloorsAllowedCounter);
                mValueFloorsAllowed.setText(String.valueOf(copy));
                FloorsAllowedCounter = copy;
            }
        });
        mFloorsAllowedinc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Integer copy = IncrementFunction(FloorsAllowedCounter);
                mValueFloorsAllowed.setText(String.valueOf(copy));
                FloorsAllowedCounter = copy;
            }
        });


        ////////////////////////////////////////////////////////////////////////////

        //////////////////////////////////////////////////////////////////////////////

        return view;
    }

    //Increment Counter for all counters
    private Integer IncrementFunction(int Counter) {


        if (Counter < 40) {
            Counter++;
            // mValueBathroom.setText(String.valueOf(Counter));
            // BathroomCounter++;
        } else {

            Toast.makeText(getActivity(), "Full", Toast.LENGTH_SHORT).show();
        }

        return Counter;
    }


    //Decrement Counter for all Counters
    private Integer DecrementFunction(int Counter) {

        if (Counter > 1) {
            Counter--;
            // mValueBathroom.setText(String.valueOf(Counter));
            // BathroomCounter--;
        } else {
            Toast.makeText(getActivity(), "Nothng Happen", Toast.LENGTH_SHORT).show();
        }

        return Counter;
    }


    private void MoreTextViewclickEvent() {

        mPanle3.setVisibility(View.GONE);


    }

    private void MoreTextEvent() {

        mMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPanle3.setVisibility(View.VISIBLE);
                mMore.setVisibility(View.GONE);
            }
        });
    }

    private boolean isValidArea(String name) {

        Pattern pattern;
        Matcher matcher;
        final String AREA_PATTERN = "^(?=.*[0-9]).+$";
        pattern = Pattern.compile(AREA_PATTERN);
        matcher = pattern.matcher(name);

        return matcher.matches();
    }
    private void ButtonClick() {
        GetReady.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!isValidArea(mCoveredarea.getText().toString()))
                {
                    mCoveredarea.setError("Value Required..");
                }
                else {
                    Intent next = new Intent(getActivity(), Activity_Uplaod_Property_Part2.class);
                    propertyData.add("Bedrooms");
                    propertyData.add(BedroomCounter.toString());
                    propertyData.add("Bathrooms");
                    propertyData.add(BathroomCounter.toString());
                    propertyData.add("Balcony");
                    propertyData.add(BalconyCounter.toString());
                    propertyData.add("FloorNumber");
                    propertyData.add(FloorCounter.toString());
                    propertyData.add("TotalFloor");
                    propertyData.add(TotalFloorCounter.toString());
                    propertyData.add("AllowedFloorForconstruction");
                    propertyData.add(FloorsAllowedCounter.toString());
                    propertyData.add("OpenSites");
                    propertyData.add(NumberSitesCounter.toString());
                    propertyData.add("Furnishing");
                    propertyData.add(furnishedtype);
                    propertyData.add("PropertyCorner");
                    propertyData.add(corner);
                    propertyData.add("PropertySize");
                    propertyData.add(mCoveredarea.getText().toString());
                    String r = "Sell";
                    next.putStringArrayListExtra("data", propertyData);
                    startActivity(next);
                }
            }
        });
    }

    private void TypeCasting(View view) {
        mCoveredarea=(EditText)view.findViewById(R.id.coveredareavalue);

        GetReady = (Button) view.findViewById(R.id.Readytogo);

        mBathrominc = (TextView) view.findViewById(R.id.decbathroom);
        mBathroomdec = (TextView) view.findViewById(R.id.incbathroom);
        mFloordec = (TextView) view.findViewById(R.id.incfloor);
        mFloorinc = (TextView) view.findViewById(R.id.decfloor);
        mTotalFloordec = (TextView) view.findViewById(R.id.inctotalfloor);
        mTotalFloorinc = (TextView) view.findViewById(R.id.dectotalfloor);
        mValueBathroom = (TextView) view.findViewById(R.id.valuebathroom);
        mValueFloor = (TextView) view.findViewById(R.id.valuefloor);
        mTotalFloorValue = (TextView) view.findViewById(R.id.valuetotalfloor);
        mBedroominc = (TextView) view.findViewById(R.id.decbedroom);
        mBedroomdec = (TextView) view.findViewById(R.id.incbedroom);
        mBalconyinc = (TextView) view.findViewById(R.id.decbalcony);
        mBalconydec = (TextView) view.findViewById(R.id.incbalcony);
        mValueBedroom = (TextView) view.findViewById(R.id.valuebedroom);
        mValueBalcony = (TextView) view.findViewById(R.id.valuebalcony);
        mRadioGroup1=(RadioGroup)view.findViewById(R.id.first);
        mRadioGroup2=(RadioGroup)view.findViewById(R.id.second);

        mFloorsAlloweddec = (TextView) view.findViewById(R.id.incfloorsallowed);
        mFloorsAllowedinc = (TextView) view.findViewById(R.id.decfloorsallowed);
        mNumberSitesinc = (TextView) view.findViewById(R.id.decopensites);
        mNumberSitesdecdec = (TextView) view.findViewById(R.id.incopensites);

        mValueFloorsAllowed = (TextView) view.findViewById(R.id.valuefloorsallowed);
        mValueNumberSites = (TextView) view.findViewById(R.id.valuesopensites);


         /*
        Not for your use
         */
        mPanle3 = (LinearLayout) view.findViewById(R.id.panle3);
        mMore = (TextView) view.findViewById(R.id.more);


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
}


package com.example.asad.homebuyerproject;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ResidentialFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ResidentialFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResidentialFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View view;
    private RadioGroup mRadioGroup;
    private RadioGroup mRadioGroup2;
    private OnFragmentInteractionListener mListener;
    private RadioButton r,r1;

    private String SellRent;
    private String ResidentialCommercial;
    private String thisActivityData;
    private ArrayList<String> propertyData=new ArrayList<>();
    public ResidentialFragment() {
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
        view = inflater.inflate(R.layout.fragment_residential, container, false);
       // SellRent = getArguments().getString("SellRentData");
        ResidentialCommercial = getArguments().getString("ResidentialCommercialData");
        propertyData=getArguments().getStringArrayList("data");
        SellRent=propertyData.get(propertyData.indexOf("SellType")+1);
        Toast.makeText(this.getActivity(), "Sellrent null"+SellRent, Toast.LENGTH_SHORT).show();
        TypeCast(view);
        if (SellRent == null) {
                Toast.makeText(this.getActivity(), "Sellrent null", Toast.LENGTH_SHORT).show();
            }else{
                if (SellRent.equals("Sell")) {

                    r1.setVisibility(View.GONE);

                } else {
                    r.setVisibility(View.GONE);
                }
            }
        Radio1();
        Radio2();

        //getting data from parent activity
      //  SellRent = getArguments().getString("SellRentData");
        //ResidentialCommercial = getArguments().getString("ResidentialCommercialData");
        return view;
    }
    private void Radio2() {
        mRadioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.Rflat) {
                    thisActivityData="Flat";
                    MovetoUoload(thisActivityData);
                } else if (checkedId == R.id.Rstudioaprtment) {
                    thisActivityData="Studio Apartment";
                    MovetoUoload(thisActivityData);
                } else if (checkedId == R.id.Rvilla) {
                    thisActivityData="Villa";
                    MovetoUoload(thisActivityData);
                }
            }

        });
    }
    private void Radio1() {
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.Rpghostel) {
                    thisActivityData="PG Hostel";
                    MovetoUoload(thisActivityData);
                }
                if (checkedId == R.id.Rplot) {
                    thisActivityData="Plot";
                    MovetoUoload(thisActivityData);
                }
                else if (checkedId == R.id.Rhouse) {
                    thisActivityData="House";
                    MovetoUoload(thisActivityData);

                } else if (checkedId == R.id.Rbuilderfloor) {
                    thisActivityData="Builder Floor";
                    MovetoUoload(thisActivityData);
                } else if (checkedId == R.id.Rpenthouse) {
                    thisActivityData="Pent House";
                    MovetoUoload(thisActivityData);
                }
            }

        });

    }
    private void MovetoUoload(String Activitydata){
        Intent next = new Intent(getActivity(), Upload_Property_Activity.class);
        next.putExtra("SellRentData",SellRent);
        next.putExtra("ResidentialCommercialData",ResidentialCommercial);
        next.putExtra("Type",Activitydata);
       /* propertyData.add("propertyType");
        propertyData.add(ResidentialCommercial);
        propertyData.add("propertycatagory");
        propertyData.add(Activitydata);*/
        next.putStringArrayListExtra("data",propertyData);
        startActivity(next);
    }
    private void TypeCast(View view) {
        r=(RadioButton) view.findViewById(R.id.Rplot);
        r1=(RadioButton) view.findViewById(R.id.Rpghostel);
        view.findViewById(R.id.Rplot);
        view.findViewById(R.id.Rflat);
        view.findViewById(R.id.Rhouse);
        view.findViewById(R.id.Rpghostel);
        view.findViewById(R.id.Rstudioaprtment);
        view.findViewById(R.id.Rpenthouse);
        view.findViewById(R.id.Rbuilderfloor);
        view.findViewById(R.id.Rvilla);
        mRadioGroup = (RadioGroup) view.findViewById(R.id.RadioGroup);
        mRadioGroup2 = (RadioGroup) view.findViewById(R.id.RadioGroup1);
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
       /* if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
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



package com.example.asad.homebuyerproject;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Gallery_Activity_Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Gallery_Activity_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Gallery_Activity_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button cApartment;
    private String[] imagesPath;
    private LinearLayout lnrImages;
    private Button btnAddPhots;
    private Button btnSaveImages;
    private ArrayList<String> imagesPathList;
    private Bitmap yourbitmap;
    private Bitmap resized;
    private int i = 1, j = 1;
    private static int RESULT_LOAD_IMAGE = 1;
    private LinearLayout linearLayout2;
    private String[] arr = new String[15];
    int[] ids = new int[15];
    private int k = 1;
    private int g = 1;
    private ImageView img1;
    private final int PICK_IMAGE_MULTIPLE = 1;

    private OnFragmentInteractionListener mListener;

    public Gallery_Activity_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Gallery_Activity_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Gallery_Activity_Fragment newInstance(String param1, String param2) {
        Gallery_Activity_Fragment fragment = new Gallery_Activity_Fragment();
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

        View layout = inflater.inflate(R.layout.fragment_gallery_activity, container, false);


        TypeCasting(layout);
        ButtonClickEvent();

            /*
        *do not use these methods its not for your use
         */


        return layout;

    }

    private void TypeCasting(View layout) {
        img1 = (ImageView) layout.findViewById(R.id.bigimageview);
        linearLayout2 = (LinearLayout) layout.findViewById(R.id.insidescroll);
        cApartment = (Button) layout.findViewById(R.id.cApartment);
        ImageView img1 = (ImageView) layout.findViewById(R.id.bigimageview);


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_IMAGE_MULTIPLE) {
                imagesPathList = new ArrayList<String>();
                imagesPath = data.getStringExtra("data").split("\\|");
                try {
                    lnrImages.removeAllViews();
                } catch (Throwable e) {
                    e.printStackTrace();
                }



                //You can get all images from here
                for (int i = 0; i < imagesPath.length; i++) {

                    imagesPathList.add(imagesPath[i]);
                    yourbitmap = BitmapFactory.decodeFile(imagesPath[i]);

                    //set image in horizontal scroll view
                    setimage(yourbitmap);

                }
            }
        }

    }


    private void ButtonClickEvent() {

        cApartment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), CustomPhotoGalleryActivity.class);
                startActivityForResult(intent, PICK_IMAGE_MULTIPLE);
            }
        });
    }


    private void setimage(Bitmap yourbitmap) {

        ImageView imageView = new ImageView(getActivity());
        imageView.setImageBitmap(yourbitmap);
        imageView.setAdjustViewBounds(true);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(220, 220));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);


        ids[i] = g;   //ids[i] assign dynamic id to each imageview
        imageView.setId(ids[i]);

        arr[j] = imageView.getDrawable().toString();

        linearLayout2.addView(imageView, 1);

        k = i;  //k takes counter
        i++;    //increment counter
        j++;    //takes images from each imageview and increment
        g++;    //ids counter


        img1.setImageDrawable(imageView.getDrawable());
        img1.setScaleType(ImageView.ScaleType.CENTER_CROP);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int l = 1; l <= k; l++) {
                    if (v.getId() == ids[l]) {

                        ImageView imag = (ImageView) getView().findViewById(ids[l]);

                        if (imag.getDrawable().toString() == (arr[l])) ;
                        {
                            img1.setImageDrawable(imag.getDrawable());
                        }

                    }
                }


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
        void onFragmentInteraction(Uri uri);
    }
}

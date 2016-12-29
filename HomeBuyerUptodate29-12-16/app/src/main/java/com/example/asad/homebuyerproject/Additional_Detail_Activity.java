package com.example.asad.homebuyerproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import nl.changer.polypicker.Config;
import nl.changer.polypicker.ImagePickerActivity;

public class Additional_Detail_Activity extends AppCompatActivity {

    private ImageView mUploadPhotos, mUploadPhotos12;
    private ArrayList<String> propertyData=new ArrayList<>();
    private ArrayList<String> Amenities=new ArrayList<>();
    private ArrayList<String> Tenent=new ArrayList<>();
    private EditText directionFacing,additionaldetail;
    private Button Addtodatabase;
    private LinearLayout layout1,layout2,layout3,layout4;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    HashMap<String, String> PropertyMap = new HashMap<String, String>();
    RelativeLayout layotphoto;
    private Button photoselect;
    private int REQUEST_VIDEO = 0, SELECT_FILE = 1,INTENT_REQUEST_GET_IMAGES=2;
    private Button btnSelect,btn;
    private ImageView ivImage;
    private String userChoosenTask;
    boolean addAllimage=false;
    private  ArrayList<Bitmap> captureimage=new ArrayList<Bitmap>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment__additional__detail);
        Bundle extras = getIntent().getExtras();
        if(extras==null){
            Toast.makeText(Additional_Detail_Activity.this, "Previous Data Not Found", Toast.LENGTH_SHORT).show();
        }else {
            propertyData = extras.getStringArrayList("data");
           // Toast.makeText(this, "PropertyDataSize"+propertyData.size(), Toast.LENGTH_SHORT).show();
        }
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        Typecasting();
        PostPropertyClickEvent();
        UploadPhotosEvent();


    }

    private void UploadPhotosEvent() {

        photoselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              /*  //GalleryFragment open
                Gallery_Activity_Fragment frag = new Gallery_Activity_Fragment();
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();

                //Gone View
                LinearLayout layout=(LinearLayout)findViewById(R.id.Replace_Gallery_Image12);
                layout.setVisibility(View.INVISIBLE);


                transaction.replace(R.id.Replace_Gallery_Image, frag, "Upload Photos");
                transaction.addToBackStack(null);
                transaction.commit();
                Toast.makeText(getApplication(), "clicked", Toast.LENGTH_SHORT).show();*/
                selectImage();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        switch (requestCode)
        {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    if(userChoosenTask.equals("Take Photo OR Select From Library"))
                        Imagesintent();
                    else if(userChoosenTask.equals("Take Video"))
                        galleryIntent();
                }
                else
                {
                    //code for deny
                }
                break;
        }
    }


    private void selectImage()
    {
        final CharSequence[] items =
                {
                        "Take Photo OR Select From Library", "Take Video", "Cancel"
                };


        AlertDialog.Builder builder = new AlertDialog.Builder(Additional_Detail_Activity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int item)
            {
                boolean result=Utility.checkPermission(Additional_Detail_Activity.this);
                if (items[item].equals("Take Photo OR Select From Library"))
                {
                    userChoosenTask ="Take Photo OR Select From Library";
                    if(result)
                        Imagesintent();
                }
                else if (items[item].equals("Take Video"))
                {
                    userChoosenTask ="Take Video";
                    if(result)
                        galleryIntent();
                }

                else if (items[item].equals("Cancel"))
                {
                    dialog.dismiss();
                }
            }
        });

        builder.show();
    }

    private void galleryIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 7);
        startActivityForResult(intent,REQUEST_VIDEO);

    }

    private void Imagesintent()
    {
        Context mContext=getApplicationContext();
        Intent intent = new Intent(mContext, ImagePickerActivity.class);
        Config config = new Config.Builder()
                .setTabBackgroundColor(R.color.white)    // set tab background color. Default white.
                .setTabSelectionIndicatorColor(R.color.blue)
                .setCameraButtonColor(R.color.green)
                .setSelectionLimit(5)    // set photo selection limit. Default unlimited selection.
                .build();
        ImagePickerActivity.setConfig(config);
        startActivityForResult(intent, INTENT_REQUEST_GET_IMAGES);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK)
        {
            if (requestCode == INTENT_REQUEST_GET_IMAGES) {
                Parcelable[] parcelableUris = data.getParcelableArrayExtra(ImagePickerActivity.EXTRA_IMAGE_URIS);

                if (parcelableUris == null) {
                    return;
                }
                // Java doesn't allow array casting, this is a little hack
                Uri[] uris = new Uri[parcelableUris.length];
                System.arraycopy(parcelableUris, 0, uris, 0, parcelableUris.length);
                Context mContext=getApplicationContext();
                if (uris != null) {
                    if (addAllimage) {
                        addAllimage = false;
                        captureimage.clear();
                    }for (Uri uri : uris) {
                        try {
                            captureimage.add(getThumbnail(Uri.parse("file://"+uri),mContext));
                        }
                        catch (IOException e){
                            Toast.makeText(this, "Uri is" +e.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.i("UriData", " uri: " + uri);
                        }

                    }
                    addImagesToThegallery(captureimage);

                }
            }
            if (requestCode == REQUEST_VIDEO) {
                if (data !=null)
                    try{
                        Uri vid = data.getData();
                        String path = getRealPathFromURI(getApplicationContext(),vid);
                        LinearLayout imageGallery = (LinearLayout) findViewById(R.id.imageGallery);
                        imageGallery.addView(getImageView(createVideoThumbNail(path)));

                    }
                    catch (Exception e){
                        Toast.makeText(this, "Sorry Something went Wrong" +e.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.i("UriData", " uri: " + e.getMessage());
                    }
            }

        }
    }
    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public Bitmap createVideoThumbNail(String path){
        return ThumbnailUtils.createVideoThumbnail(path,MediaStore.Video.Thumbnails.MINI_KIND);
    }
    private void addImagesToThegallery(ArrayList<Bitmap> galimg) {
        LinearLayout imageGallery = (LinearLayout) findViewById(R.id.imageGallery);
        addAllimage=true;
        for (Bitmap image : galimg) {
            imageGallery.addView(getImageView(image));
        }
    }
    private View getImageView(Bitmap image) {
        ImageView imageView = new ImageView(getApplicationContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        lp.setMargins(0, 0, 1, 0);
        imageView.setLayoutParams(lp);
        imageView.setImageBitmap(image);
        return imageView;
    }
    public static Bitmap getThumbnail(Uri uri , Context con) throws FileNotFoundException, IOException{
        int maxImageSize=512;
        InputStream input = con.getContentResolver().openInputStream(uri);

        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        onlyBoundsOptions.inDither=true;//optional
        onlyBoundsOptions.inPreferredConfig=Bitmap.Config.ARGB_8888;//optional
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        input.close();
        if ((onlyBoundsOptions.outWidth == -1) || (onlyBoundsOptions.outHeight == -1))
            return null;

        int originalSize = (onlyBoundsOptions.outHeight > onlyBoundsOptions.outWidth) ? onlyBoundsOptions.outHeight : onlyBoundsOptions.outWidth;

        double ratio = (originalSize > maxImageSize) ? (originalSize / maxImageSize) : 1.0;

        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = getPowerOfTwoForSampleRatio(ratio);
        bitmapOptions.inDither=true;//optional
        bitmapOptions.inPreferredConfig=Bitmap.Config.ARGB_8888;//optional
        input = con.getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
        input.close();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        Bitmap decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(out.toByteArray()));
        float ratio1 = Math.min(
                (float) maxImageSize / decoded.getWidth(),
                (float) maxImageSize / decoded.getHeight());
        int width = Math.round((float) ratio1 * decoded.getWidth());
        int height = Math.round((float) ratio1 * decoded.getHeight());

        Bitmap newBitmap = Bitmap.createScaledBitmap(decoded, width,
                height, true);
        return newBitmap;
    }

    private static int getPowerOfTwoForSampleRatio(double ratio){
        int k = Integer.highestOneBit((int)Math.floor(ratio));
        if(k==0) return 1;
        else return k;
    }


    private void PostPropertyClickEvent() {

        Addtodatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!directionFacing.getText().toString().matches("")){
                    propertyData.add("PropertyDirection");
                    propertyData.add(directionFacing.getText().toString());
                }
                if (!additionaldetail.getText().toString().matches("")){
                    propertyData.add("PropertyAdditionDetail");
                    propertyData.add(additionaldetail.getText().toString());
                }
                int count = layout1.getChildCount();
                View checkboxview = null;
                for(int i=0; i<count; i++) {
                    checkboxview = layout1.getChildAt(i);
                    boolean checked = ((CheckBox) checkboxview).isChecked();
                    if (checked){

                        //Toast.makeText(Additional_Detail_Activity.this,  "CheckBox id"+((CheckBox) checkboxview).getText().toString(), Toast.LENGTH_SHORT).show();
                        Amenities.add(((CheckBox) checkboxview).getText().toString());
                    }
                }
                int count2 = layout2.getChildCount();
                View checkboxview2 = null;
                for(int i=0; i<count2; i++) {
                    checkboxview2 = layout2.getChildAt(i);
                    boolean checked = ((CheckBox) checkboxview2).isChecked();
                    if (checked){

                       //Toast.makeText(Additional_Detail_Activity.this,  "CheckBox id"+((CheckBox) checkboxview2).getText().toString(), Toast.LENGTH_SHORT).show();
                        Amenities.add(((CheckBox) checkboxview2).getText().toString());
                    }
                }
                int count3 = layout3.getChildCount();
                View checkboxview3 = null;
                for(int i=0; i<count3; i++) {
                    checkboxview3 = layout3.getChildAt(i);
                    boolean checked = ((CheckBox) checkboxview3).isChecked();
                    if (checked){

                        //Toast.makeText(Additional_Detail_Activity.this,  "CheckBox id"+((CheckBox) checkboxview3).getText().toString(), Toast.LENGTH_SHORT).show();
                        Tenent.add(((CheckBox) checkboxview3).getText().toString());
                    }
                }int count4 = layout4.getChildCount();
                View checkboxview4 = null;
                for(int i=0; i<count4; i++) {
                    checkboxview4 = layout4.getChildAt(i);
                    boolean checked = ((CheckBox) checkboxview4).isChecked();
                    if (checked){


                        //Toast.makeText(Additional_Detail_Activity.this,  "CheckBox id"+((CheckBox) checkboxview4).getText().toString(), Toast.LENGTH_SHORT).show();
                        Tenent.add(((CheckBox) checkboxview4).getText().toString());

                    }
                }
                Toast.makeText(Additional_Detail_Activity.this,  "CheckBox id"+Amenities.size(), Toast.LENGTH_SHORT).show();
                Toast.makeText(Additional_Detail_Activity.this,  "CheckBox id"+Tenent.size(), Toast.LENGTH_SHORT).show();
                Long tsLong = System.currentTimeMillis()/1000;
                if (!propertyData.isEmpty()){
                    for (int i=0;i<propertyData.size();i=i+2){
                        PropertyMap.put(propertyData.get(i),propertyData.get(i+1));
                    }
                    mDatabase.child("Property").child(propertyData.get(propertyData.indexOf("SellType")+1)).
                            child(propertyData.get(propertyData.indexOf("propertyType")+1)).
                            child(propertyData.get(propertyData.indexOf("propertycatagory")+1)).
                            child(mAuth.getCurrentUser().getUid()+"Time"+tsLong.toString()).setValue(PropertyMap);
                }
                if (!Amenities.isEmpty()){
                    mDatabase.child("Property").child(propertyData.get(propertyData.indexOf("SellType")+1)).
                            child(propertyData.get(propertyData.indexOf("propertyType")+1)).
                            child(propertyData.get(propertyData.indexOf("propertycatagory")+1)).
                            child(mAuth.getCurrentUser().getUid()+"Time"+tsLong.toString()).child("Amentities").setValue(Amenities);
                }
                if (!Tenent.isEmpty()){
                    mDatabase.child("Property").child(propertyData.get(propertyData.indexOf("SellType")+1)).
                            child(propertyData.get(propertyData.indexOf("propertyType")+1)).
                            child(propertyData.get(propertyData.indexOf("propertycatagory")+1)).
                            child(mAuth.getCurrentUser().getUid()+"Time"+tsLong.toString()).child("Tenent").setValue(Tenent);
                }
                Intent next = new Intent(Additional_Detail_Activity.this, HomeActivity.class);
                startActivity(next);
            }

        });
    }
    private void addDataToDatabase(){

    }
    private void Typecasting() {

     //   mUploadPhotos = (ImageView) findViewById(R.id.Upload_Photos);
        directionFacing=(EditText)findViewById(R.id.DirectionFacingResult);
        additionaldetail=(EditText)findViewById(R.id.AddMoreDetailsResult);
        Addtodatabase=(Button)findViewById(R.id.Readytogo);
        layout1=(LinearLayout)findViewById(R.id.PanelOne);
        layout2=(LinearLayout)findViewById(R.id.Panel2);
        layout3=(LinearLayout)findViewById(R.id.panelthree);
        layout4=(LinearLayout)findViewById(R.id.panelfour);
        layotphoto=(RelativeLayout)findViewById(R.id.top1);
        photoselect=(Button) findViewById(R.id.photobutton);
    }
}

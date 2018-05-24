package com.otel.warehouseassistant;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.otel.warehouseassistant.fragment.RecyclerViewFragment;
import com.otel.warehouseassistant.model.Capture;
import com.otel.warehouseassistant.model.ImageInfo;
import com.otel.warehouseassistant.task.UploadCapturePostTask;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.List;


public class NewImagePostActivity extends AppCompatActivity {

    public static final String IN_BOUND = "INBOUND";
    public static final String OUT_BOUND = "OUTBOUND";
    public static final String DAMAGE = "DAMAGE";
    public static final String OTHERS = "OTHERS";

    String Storage_Path = "All_Image_Uploads/";

    ImageView readyToUploadImage;
    MaterialBetterSpinner spinner_capture_type;


    TextInputLayout input_layout_sku;
    TextInputLayout input_layout_order_no;
    TextInputLayout input_layout_lot_no;
    TextInputLayout input_layout_lpn;
    TextInputLayout input_layout_image_name;

    String capture_type;
    EditText editTextSKU;
    EditText editTextOrderNo;
    EditText editTextLotNo;
    EditText editTextLPN;
    EditText editTextImageName;
    EditText editTextRemarks;

    LinearLayout inOutboundLayout;
    LinearLayout damageLayout;

    Uri FilePathUri;

    ProgressDialog progressDialog;

    // Creating StorageReference and DatabaseReference object.
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_image_post);

        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        spinner_capture_type = findViewById(R.id.spinner_capture_type);
        editTextSKU = findViewById(R.id.editTextSKU);
        editTextOrderNo = findViewById(R.id.editTextOrderNo);
        editTextLotNo = findViewById(R.id.editTextLotNo);
        editTextLPN = findViewById(R.id.editTextLPN);
        editTextImageName = findViewById(R.id.editTextImageName);
        editTextRemarks = findViewById(R.id.editTextRemarks);

        inOutboundLayout = findViewById(R.id.inOutboundLayout);
        damageLayout = findViewById(R.id.damageLayout);

        input_layout_sku = findViewById(R.id.input_layout_sku);
        input_layout_order_no = findViewById(R.id.input_layout_order_no);
        input_layout_lot_no = findViewById(R.id.input_layout_lot_no);
        input_layout_lpn = findViewById(R.id.input_layout_lpn);
        input_layout_image_name = findViewById(R.id.input_layout_image_name);




        mToolbar.setNavigationIcon(R.drawable.ic_chevron_left_black_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        readyToUploadImage = findViewById(R.id.readyToUploadImage);

        // Assigning Id to ProgressDialog.
        progressDialog = new ProgressDialog(NewImagePostActivity.this);

        switchPanel(0);

        getExtra();

        initSpinner();

        setValidation();

    }

    private void setValidation() {
        editTextSKU.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 0)
                    input_layout_sku.setError(getString(R.string.SKU) + getString(R.string.noAllowEmpty));
                else
                    input_layout_sku.setError(null);
            }
        });
        editTextOrderNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 0)
                    input_layout_order_no.setError(getString(R.string.OrderNo) + getString(R.string.noAllowEmpty));
                else
                    input_layout_order_no.setError(null);
            }
        });
        editTextLotNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 0)
                    input_layout_lot_no.setError(getString(R.string.LotNo) + getString(R.string.noAllowEmpty));
                else
                    input_layout_lot_no.setError(null);
            }
        });
        editTextLPN.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 0)
                    input_layout_lpn.setError(getString(R.string.LPN) + getString(R.string.noAllowEmpty));
                else
                    input_layout_lpn.setError(null);
            }
        });
        editTextImageName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 0)
                    input_layout_image_name.setError(getString(R.string.ImageName) + getString(R.string.noAllowEmpty));
                else
                    input_layout_image_name.setError(null);
            }
        });
    }

    private Boolean Validation() {
        if (editTextSKU.length() == 0) {
            input_layout_sku.setError(getString(R.string.SKU) + getString(R.string.noAllowEmpty));
            return false;
        } else
            input_layout_sku.setError(null);

        if(spinner_capture_type.getText().toString().equals(getString(R.string.inbound)) || spinner_capture_type.getText().toString().equals(getString(R.string.outbound))) {
            if (editTextOrderNo.length() == 0) {
                input_layout_order_no.setError(getString(R.string.OrderNo) + getString(R.string.noAllowEmpty));
                return false;
            } else
                input_layout_order_no.setError(null);

        }
        else {
            if (editTextLotNo.length() == 0) {
                input_layout_lot_no.setError(getString(R.string.LotNo) + getString(R.string.noAllowEmpty));
                return false;
            } else
                input_layout_lot_no.setError(null);

            if (editTextLPN.length() == 0) {
                input_layout_lpn.setError(getString(R.string.LPN) + getString(R.string.noAllowEmpty));
                return false;
            } else
                input_layout_lpn.setError(null);
        }

        if (editTextImageName.length() == 0) {
            input_layout_image_name.setError(getString(R.string.ImageName) + getString(R.string.noAllowEmpty));
            return false;
        } else
            input_layout_image_name.setError(null);

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_list_confirm, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_confirm_post:
                UploadImageFileToFirebaseStorage();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getExtra() {
        Intent intent = this.getIntent();
        String filePath = intent.getExtras().getString(MediaStore.EXTRA_OUTPUT, "");
        //FilePathUri = intent.getExtras().getParcelable(MediaStore.EXTRA_MEDIA_TITLE);

        loadImage(filePath);
    }

    private void initSpinner() {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.type_array));
        spinner_capture_type.setAdapter(arrayAdapter);

        spinner_capture_type.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (getResources().getStringArray(R.array.type_array)[i].equals(getString(R.string.inbound))) {
                    switchPanel(R.string.inbound);
                    capture_type = IN_BOUND;
                } else if (getResources().getStringArray(R.array.type_array)[i].equals(getString(R.string.outbound))) {
                    switchPanel(R.string.outbound);
                    capture_type = OUT_BOUND;
                } else if (getResources().getStringArray(R.array.type_array)[i].equals(getString(R.string.damage))) {
                    switchPanel(R.string.damage);
                    capture_type = DAMAGE;
                } else {
                    switchPanel(R.string.others);
                    capture_type = OTHERS;
                }
            }
        });


        spinner_capture_type.setText(getString(R.string.inbound));
        switchPanel(R.string.inbound);
        capture_type = IN_BOUND;
    }

    private void switchPanel(int type) {
        if (type == 0)
            type = R.string.inbound;

        if (type == R.string.inbound || type == R.string.outbound) {
            inOutboundLayout.setVisibility(View.VISIBLE);
            damageLayout.setVisibility(View.GONE);
        } else {
            inOutboundLayout.setVisibility(View.GONE);
            damageLayout.setVisibility(View.VISIBLE);
        }
    }

    private void loadImage(String filePath) {
        // Get the dimensions of the View
        int targetW = 800;
        int targetH = 1280;

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(filePath, bmOptions);
        readyToUploadImage.setImageBitmap(bitmap);

        FilePathUri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, null,null));

    }

    private void saveNewPost(String ImageURL) {


        Capture capture = new Capture();

        capture.WarehouseCode = null;
        capture.Owner = null;
        capture.Type = capture_type;
        capture.OrderNo = editTextOrderNo.getText().toString();
        capture.SKU = editTextSKU.getText().toString();
        capture.LotNo = editTextLotNo.getText().toString();
        capture.LPN = editTextLPN.getText().toString();
        capture.Remarks = editTextRemarks.getText().toString();
        capture.CreateBy = "andy.chan";

        ImageInfo image = new ImageInfo();
        image.ImageCode = null;
        image.ImageName = editTextImageName.getText().toString();
        image.ImageURL = ImageURL;

        ArrayList<ImageInfo> listImage = new ArrayList<>();
        listImage.add(image);
        capture.ListImage = listImage;

        UploadCapturePostTask task = new UploadCapturePostTask(capture, new UploadCapturePostTask.OnSuccessListener() {
            @Override
            public void OnSuccess() {
                Toast.makeText(NewImagePostActivity.this, "image posted successfully", Toast.LENGTH_SHORT);

                finish();
            }
        }, new UploadCapturePostTask.OnFailListener() {
            @Override
            public void OnFail() {
                Toast.makeText(NewImagePostActivity.this, "save fail", Toast.LENGTH_SHORT);
            }
        });
        task.execute((Void) null);

    }

    public void UploadImageFileToFirebaseStorage() {

        if (Validation()) {
            // Checking whether FilePathUri Is empty or not.
            if (FilePathUri != null) {

                // Setting progressDialog Title.
                progressDialog.setTitle("Image is Uploading...");

                // Showing progressDialog.
                progressDialog.show();

                UploadTask uploadTask;
                StorageReference storageReference2nd;

                // Assign FirebaseStorage instance to storageReference.
                storageReference = FirebaseStorage.getInstance().getReference();

                // Creating second StorageReference.
                storageReference2nd = storageReference.child(Storage_Path + System.currentTimeMillis() + "." + GetFileExtension(FilePathUri));

                // Adding addOnSuccessListener to second StorageReference.
                uploadTask = storageReference2nd.putFile(FilePathUri);

                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Getting image name from EditText and store into string variable.
                        //String TempImageName = null;

                        // Hiding the progressDialog after done uploading.
                        progressDialog.dismiss();

                        // Showing toast message after done uploading.
                        //Toast.makeText(getApplicationContext(), "Image Uploaded Successfully ", Toast.LENGTH_LONG).show();


                        saveNewPost(taskSnapshot.getDownloadUrl().toString());
                    }
                })
                        // If something goes wrong .
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {

                                // Hiding the progressDialog.
                                progressDialog.dismiss();

                                // Showing exception error message.
                                Toast.makeText(NewImagePostActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        })

                        // On progress change upload time.
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                                // Setting progressDialog Title.
                                progressDialog.setTitle("Image is Uploading...");

                            }
                        });
            } else {

                Toast.makeText(NewImagePostActivity.this, "Please Select Image or Add Image Name", Toast.LENGTH_LONG).show();

            }
        }
    }

    // Creating Method to get the selected image file Extension from File Path URI.
    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();

        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        // Returning the file Extension.
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

    }
}
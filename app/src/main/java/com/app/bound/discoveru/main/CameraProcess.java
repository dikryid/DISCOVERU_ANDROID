package com.app.bound.discoveru.main;

import java.io.IOException;
import java.io.InputStream;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CameraProcess extends Activity {

	final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1888;
	
    
    Uri imageUri                      = null;
    static TextView imageDetails      = null;
    public  static ImageView showImg  = null;
    CameraProcess CameraActivity = null;
    
    private Button btnPhoto = null;
    private Button   btnMulai = null;

    private TextView txtTemplate = null;
    private EditText txtName = null;

	public final int LEBAR_GAMBAR = 640;
	public final int PANJANG_GAMBAR = 480;

	public final int LEBAR_CROP = 240;
	public final int PANJANG_CROP = 320;

	private Resources res;
    
    SQLController dbcon;
    Bitmap mBitmap = null;
    Bitmap cropBitmap = null;
    Bitmap ckBitmap = null;

	private Global global;
	private Member member;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera_process);
		CameraActivity = this;

		res = this.getResources();
		global = (Global)this.getApplication();
		member = global.member;
		
		txtTemplate = (TextView) findViewById(R.id.txtTemplate);
		
		imageDetails = (TextView) findViewById(R.id.imageDetails);
		showImg = (ImageView) findViewById(R.id.showImg);
		
		txtName = (EditText)findViewById(R.id.txtName);

		String name = member.first_name + " " + member.last_name;
		txtName.setText(name);

		btnPhoto = (Button) findViewById(R.id.btnPhoto);
		
		btnPhoto.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				/***** Define the file-name to save photo taken by Camera activity *******/
		        String fileName = "Camera_Example.jpg";
		        
		        // Create parameters for Intent with filename
		        
		        ContentValues values = new ContentValues();
		        
		        values.put(MediaStore.Images.Media.TITLE, fileName);
		        
		        values.put(MediaStore.Images.Media.DESCRIPTION,"Image capture by camera");
		        
		        /****** imageUri is the current activity attribute, define and save it for later usage  *****/
		        imageUri = getContentResolver().insert(
		        		MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
		        
		        /******   EXTERNAL_CONTENT_URI : style URI for the "primary" external storage volume. ******/
		
		        
		        /******  Standard Intent action that can be sent to have the camera application capture an image and return it. ******/ 
		        
		        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		        
		         intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		         
		         intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
		         
		        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
			}
		});
		
		btnMulai = (Button) findViewById(R.id.btnMulai);
		btnMulai.setText(res.getString(R.string.menu_inspect));

		
		btnMulai.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
//				Intent resultDetection = new Intent(CameraProcess.this, ResultDetection.class);
//
//				byte[] dataBitmap = global.getBytes(cropBitmap);
//				resultDetection.putExtra("dataBitmap", dataBitmap);
//
//				CameraProcess.this.startActivity(resultDetection);
//				CameraProcess.this.finish();
			}
		});
		
		txtName.setVisibility(EditText.INVISIBLE);
		btnMulai.setVisibility(Button.INVISIBLE);
		btnPhoto.setVisibility(Button.VISIBLE);
		txtTemplate.setVisibility(EditText.INVISIBLE);

	}
	
	
	private void saveRecordImage(){
//		if(cropBitmap.getWidth() != global.LEBAR_CROP ||
//				cropBitmap.getHeight() != global.PANJANG_CROP){
//			Toast.makeText(this, res.getString(R.string.pilih_resolusi_crop),
//					Toast.LENGTH_SHORT).show();
//			return;
//		}
//
//		String nama = txtName.getText().toString().toUpperCase().trim();
//
//		if(nama.equals(global.KOSONG)){
//			Toast.makeText(this, res.getString(R.string.nama_kosong),
//					Toast.LENGTH_SHORT).show();
//
//			return;
//		} else {
//
//			try{
//				dbcon = new SQLController(this);
//				dbcon.open();
//
//				if(mBitmap != null){
//					String name = nama.trim();
//					byte[] image = global.getBytes(cropBitmap);
//
//					Cursor c = dbcon.searchDataByName(name);
//
//					if(c.getCount()==0){
//						dbcon.insertData(name, image,  null);
//
//						Toast.makeText(this, res.getString(R.string.rekam_citra_tersimpan),
//								Toast.LENGTH_SHORT).show();
//
//						dbcon.close();
//						this.onBackPressed();
//					} else {
////						Toast.makeText(this, res.getString(R.string.rekam_citra_sudah_ada),
////								Toast.LENGTH_SHORT).show();
//						c.moveToFirst();
//						int id = c.getInt(0);
//						//byte[] voice = c.getBlob(3);
//						String voice = c.getString(3);
//
//						dbcon.updateData(id, name, image, voice);
//
//						Toast.makeText(this, res.getString(R.string.rekam_citra_tersimpan),
//								Toast.LENGTH_SHORT).show();
//
//						dbcon.close();
//						this.onBackPressed();
//					}
//
//				} else {
//					Toast.makeText(this, res.getString(R.string.rekam_citra_gagal_tersimpan),
//							Toast.LENGTH_SHORT).show();
//				}
//
//				dbcon.close();
//			} catch (Exception e){
//				Toast.makeText(this, res.getString(R.string.rekam_citra_gagal_tersimpan),
//						Toast.LENGTH_SHORT).show();
//			}
//		}
	}



	@Override
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	    {
	    	
			//Toast.makeText(this, String.valueOf(requestCode), Toast.LENGTH_SHORT).show();
			
			if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
	    		
	    	    if (resultCode == RESULT_OK) {
	    	    	
	    	    	btnMulai.setVisibility(Button.VISIBLE);
	    	    	btnPhoto.setVisibility(Button.INVISIBLE);

	    	    	txtTemplate.setVisibility(TextView.VISIBLE);
	    	    	txtName.setVisibility(EditText.VISIBLE);

	    	    	String imageId = convertImageUriToFile(imageUri,CameraActivity);
	    	    	
	    	    	//Bitmap photo = (Bitmap) data.getExtras().get("data"); 
	                //showImg.setImageBitmap(photo);
                    
	    	    	new LoadImagesFromSDCard().execute(""+imageId);
	    	    	
	    	   
	    	    } else if (resultCode == RESULT_CANCELED) {
	    	    	
	    	        Toast.makeText(this, "Ambil dari Kamera GAGAL dilakukan", Toast.LENGTH_SHORT).show();
		    	    //this.onBackPressed();
	    	    } else {
	    	    	
	    	        Toast.makeText(this, "Ambil dari Kamera GAGAL dilakukan", Toast.LENGTH_SHORT).show();
		    	    //this.onBackPressed();
	    	    }
	    	    
	    	    

	    	}
	    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.camera_process, menu);
		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent i;

		switch (item.getItemId()) {
			case R.id.tentang:
//				i = new Intent(CameraProcess.this,About.class);
//
//				CameraProcess.this.startActivity(i);
//				CameraProcess.this.finish();
//				return true;

			default:
				return super.onOptionsItemSelected(item);
		}
	}


	public String convertImageUriToFile (Uri imageUri, Activity activity)  {
    	Cursor cursor = null;
    	int imageID = 0;
    	
    	try {
    	    /*********** Which columns values want to get *******/
    		String [] proj={
    				         MediaStore.Images.Media.DATA, 
    				         MediaStore.Images.Media._ID,
    				         MediaStore.Images.Thumbnails._ID, 
    				         MediaStore.Images.ImageColumns.ORIENTATION
    				       };
    	    
    		cursor = getContentResolver().query( 
    				
		    				imageUri,   // Get data for specific image URI
		    	            proj,       // Which columns to return
		    	            null,       // WHERE clause; which rows to return (all rows)
		    	            null,       // WHERE clause selection arguments (none)
		    	            null        // Order-by clause (ascending by name)
		    	            
		    	         );      
    	    
    	    int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
    	    int columnIndexThumb = cursor.getColumnIndexOrThrow(MediaStore.Images.Thumbnails._ID);
    	    int file_ColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
    	    //int orientation_ColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.ORIENTATION);
    	    
    	    int size = cursor.getCount();
    	    
            /*******  If size is 0, there are no images on the SD Card. *****/
    	    
            if (size == 0) {
            	imageDetails.setText("Tidak Ada Gambar");
            }
            else
            {
    	   
	    	    int thumbID = 0;
	    	    if (cursor.moveToFirst()) {
	    	    	
	    	    	/**************** Captured image details ************/
	    	    	
	    	    	/*****  Used to show image on view in LoadImagesFromSDCard class ******/
	    	    	imageID = cursor.getInt(columnIndex);
	    	    	
	    	    	thumbID   = cursor.getInt(columnIndexThumb);
	    	    	
	    	    	String Path = cursor.getString(file_ColumnIndex);
	    	    	
	    	    	//String orientation =  cursor.getString(orientation_ColumnIndex);
	    	    	
//	    	    	String CapturedImageDetails = " Keterangan Gambar : \n\n"
//	    	    		                              +" ImageID :"+imageID+"\n"
//	    	    		                              +" ThumbID :"+thumbID+"\n"
//	    	    		                              +" Path :"+Path+"\n";
	    	    	
	    	    	String CapturedImageDetails = "Path :"+Path;
	    	    	
	    	    	// Show Captured Image detail on view
	    	    	imageDetails.setText(CapturedImageDetails);
	    	    	
	    	    }
            }    
    	} finally {
    	    if (cursor != null) {
    	        cursor.close();
    	    }
    	}
    	
    	return ""+imageID;
    }
 
 
     /**
     * Async task for loading the images from the SD card. 
     * 
     * @author Android Example
     *
     */
    // Class with extends AsyncTask class
    public class LoadImagesFromSDCard  extends AsyncTask<String, Void, Void> {
        
        private ProgressDialog Dialog = new ProgressDialog(CameraProcess.this);


		 public Bitmap rectifyImage(Bitmap originalBitmap, Uri uri){
			 try{
				 InputStream input = CameraProcess.this.getContentResolver().openInputStream(uri);
				 ExifInterface ei;

				 if (Build.VERSION.SDK_INT > 23)
					 ei = new ExifInterface(input);
				 else
					 ei = new ExifInterface(uri.getPath());

				 int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
				 switch (orientation) {
					 case ExifInterface.ORIENTATION_ROTATE_90:
						 return rotateImage(originalBitmap, 90);
					 case ExifInterface.ORIENTATION_ROTATE_180:
						 return rotateImage(originalBitmap, 180);
					 case ExifInterface.ORIENTATION_ROTATE_270:
						 return rotateImage(originalBitmap, 270);
					 default:
						 return originalBitmap;
				 }
			 }catch (Exception e){
				 return originalBitmap;
			 }
		 }


		 public Bitmap rotateImage(Bitmap source, float angle) {
			 Matrix matrix = new Matrix();
			 matrix.postRotate(angle);
			 return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
					 matrix, true);
		 }

        
        protected void onPreExecute() {
            /****** NOTE: You can call UI Element here. *****/
            
            //UI Element
            Dialog.setMessage("Taking Pictures from SD Card...");
            Dialog.show();
        }

        // Call after onPreExecute method
        protected Void doInBackground(String... urls) {
        	
        	Bitmap bitmap = null;
            Bitmap newBitmap = null;
            Uri uri = null;       
            	
                
                try {
                	
                	/**  Uri.withAppendedPath Method Description
                	* Parameters
                	*    baseUri  Uri to append path segment to 
                	*    pathSegment  encoded path segment to append 
                    * Returns
                	*    a new Uri based on baseUri with the given segment appended to the path
                	*/
                	
  	                uri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "" + urls[0]);
  	                
  	                /**************  Decode an input stream into a bitmap. *********/
                    bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
					bitmap = rectifyImage(bitmap, uri);
                    
                    if (bitmap != null) {
                    	
                    	/********* Creates a new bitmap, scaled from an existing bitmap. ***********/


                        newBitmap = Bitmap.createScaledBitmap(bitmap, LEBAR_GAMBAR,
                        		PANJANG_GAMBAR, true);
                        
                        bitmap.recycle();
                        
                        if (newBitmap != null) {
                        	
                        	mBitmap = newBitmap;
                        }
                    }
                } catch (IOException e) {
                    //Error fetching image, try to recover
                	
                	/********* Cancel execution of this task. **********/
                	cancel(true);
                }
            
            return null;
        }
        
        protected void onPostExecute(Void unused) {
        	
            // NOTE: You can call UI Element here.
            
            // Close progress dialog
            Dialog.dismiss();
            
            if(mBitmap != null){
            	
		            	int width = LEBAR_CROP;
		            	int height = PANJANG_CROP;
		              
		            	int x1 = (mBitmap.getWidth() / 2) - (width / 2);
		            	int y1 = (mBitmap.getHeight() / 2) - (height / 2);
		            	int x2 = x1 + width;
		            	int y2 = y1 + height;
		            	
		            	ckBitmap = Bitmap.createScaledBitmap(mBitmap, mBitmap.getWidth(), 
		        				mBitmap.getHeight(), true);
		            	Canvas tempCanvas = new Canvas(ckBitmap);
		            	
		            	Paint paint = new Paint();
		            	paint.setColor(Color.TRANSPARENT);
		                paint.setStyle(Paint.Style.FILL);
		                tempCanvas.drawRect(new RectF(x1,y1,x2,y2), paint);
		                
		                paint.setStrokeWidth(3);
		                paint.setColor(Color.RED);
		                paint.setStyle(Paint.Style.STROKE);
		                tempCanvas.drawRect(new RectF(x1,y1,x2,y2), paint);
		
		            	cropBitmap = Bitmap.createBitmap(mBitmap, x1, y1, LEBAR_CROP, PANJANG_CROP);

		            	showImg.setImageDrawable(new BitmapDrawable(getResources(), ckBitmap));
		            	Toast.makeText(CameraProcess.this, res.getString(R.string.info_inspect), Toast.LENGTH_LONG).show();

            }
        }
        
    }


    private void BackToMenu2(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage(res.getString(R.string.confirm_outcamera));
        dialog.setCancelable(false);

        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent menu2 = new Intent(CameraProcess.this,Menu2.class);
                CameraProcess.this.startActivity(menu2);
                CameraProcess.this.finish();

            }
        });

        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //
            }
        });

        dialog.show();
    }

	@Override
    public void onBackPressed() {
        //start activity here
		CameraProcess.this.BackToMenu2();
    }
}

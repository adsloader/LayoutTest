package com.campandroid.layouttest;

import java.io.File;
import java.io.FileOutputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends Activity {

	Button btnShare  = null;
	Button btnDelete = null;
	Button btnImage  = null;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // ȭ���� �������� �����Ѵ�.
        
        
        // ������ư����
        btnShare = (Button)findViewById(R.id.btnShare);
        btnShare.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				File f = SaveImage();
				ShareImage(f);
				
			}}
        
        );
        
        // ������ư����
        btnDelete = (Button)findViewById(R.id.btnClear);
        btnDelete.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DeleteTextAndImageFile();
			}}
        
        );
        
        // ��������ϱ� ��ư
        btnImage = (Button)findViewById(R.id.btnImage);
        btnImage.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SelectBackground();
			}}
        
        );


    }
    
    // EditBox�� ȭ���� �̹����� �����մϴ�.
    File SaveImage(){
    	EditText et = (EditText) findViewById(R.id.editText1);
    	et.setDrawingCacheEnabled(true);
    	et.buildDrawingCache(true);
    	
    	Bitmap b = Bitmap.createBitmap(et.getDrawingCache());
    	
    	et.setDrawingCacheEnabled(false);
    	et.buildDrawingCache(false);
    	
    	String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() +
    			"/campandroid";
    	File dir = new File(file_path);
    	if(!dir.exists())
    	    dir.mkdirs();
    	File file = new File(dir, "test.png");
    	FileOutputStream fOut;
    	try {
    		fOut = new FileOutputStream(file);
    		b.compress(Bitmap.CompressFormat.PNG, 85, fOut);
    		fOut.flush();
    		fOut.close();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	
    	return file;
    }
    
    // �̹����� �����ϱ�
    void ShareImage(File f){
    	Uri uri = Uri.fromFile(f);
    	Intent intent = new Intent();
    	intent.setAction(Intent.ACTION_SEND);
    	intent.setType("image/*");

    	intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
    	intent.putExtra(android.content.Intent.EXTRA_TEXT, "");
    	intent.putExtra(Intent.EXTRA_STREAM, uri);
    	startActivity(Intent.createChooser(intent, "Share Cover Image"));
    }
    
    // �̹��� ����� �����ϰ� �Ѵ�.
    void SelectBackground(){
		final CharSequence[] items  =  { "��Ʈ", "��", "õ��"};
		final int[]          Images = { R.drawable.heart, R.drawable.bear, R.drawable.angel};
		
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);     
    	builder.setTitle("��漳��")  
    	.setItems(items, new DialogInterface.OnClickListener(){ 
    	    public void onClick(DialogInterface dialog, int index){
    	    	
    	    	// 
    	    	EditText et = (EditText) findViewById(R.id.editText1);
    	    	
    	    	et.setBackgroundResource(Images[index]);
    	    	dialog.dismiss();
    	    }
    	});

    	AlertDialog dialog = builder.create();    // �˸�â ��ü ����
    	dialog.show();    // �˸�â ����

	}
    
    // �̹��� ������ ����� ���ڿ��� �ʱ�ȭ. 
    void DeleteTextAndImageFile(){
    	String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() +
    			"/campandroid";
    	File dir = new File(file_path);
    	File file = new File(dir, "test.png");
    	
    	try{
    		file.delete();	
    	} catch(Exception e){
    		
    	}
    	
    	// ȭ�鿡 ���ڿ��ʱ�ȭ
    	EditText et = (EditText) findViewById(R.id.editText1);
    	et.setText("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    
}

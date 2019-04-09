package com.gefins;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.cloudinary.Cloudinary;
import com.cloudinary.android.MediaManager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class UploadImg extends AsyncTask<Void, Void, Void> {
    String file;
    //String name;

    public UploadImg(String file){
        this.file = file;
      //  this.name = name;
    }

    @Override
    protected Void doInBackground(Void... Params) {
        //Map config = new HashMap();
        //config.put("cloud_name", "aso40");
        //MediaManager.init(config);
        //Cloudinary cloudinary = new Cloudinary(config);

        //cloudinary.uploader().unsignedUpload(file);

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid){
        super.onPostExecute(aVoid);
    }

}

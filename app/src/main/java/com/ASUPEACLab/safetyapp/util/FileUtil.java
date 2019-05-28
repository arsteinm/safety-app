package com.ASUPEACLab.safetyapp.util;

import android.content.Context;
import android.util.Log;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.s3.AmazonS3Client;

import java.io.File;

public class FileUtil {
    public static boolean deleteDb(Context context) {
        File file = new File("/data/data/com.ASUPEACLab.safetyapp/databases/appDB.db");
        File shm = new File("/data/data/com.ASUPEACLab.safetyapp/databases/appDB.db-shm");
        File wal = new File("/data/data/com.ASUPEACLab.safetyapp/databases/appDB.db-wal");

        if(file.exists()) {
            file.delete();
            shm.delete();
            wal.delete();
        }

        String s = "@@@" +  context.getDatabasePath("appDB");

        boolean deleted = file.exists();
        String b = "true";
        if(deleted == false) {
            b = "false";
        }
        Log.d(s, b);
        return true;
    }

    public static void download(Context context, String file, String path) {
        TransferUtility transferUtility =
                TransferUtility.builder()
                        .context(context)
                        .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                        .s3Client(new AmazonS3Client(AWSMobileClient.getInstance().getCredentialsProvider()))
                        .build();
        try {
            File localFile = new File(path);



            TransferObserver downloadObserver =
                    transferUtility.download(file, localFile);

            Log.d("FileUtil", localFile.toString());







            downloadObserver.setTransferListener(new TransferListener() {

                @Override
                public void onStateChanged(int id, TransferState state) {
                    if (TransferState.COMPLETED == state) {
                        // Handle a completed download

                        Log.d("FileUtil", "download complete");
                    }
                }


                @Override
                public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                    float percentDonef = ((float) bytesCurrent / (float) bytesTotal) * 100;
                    int percentDone = (int) percentDonef;
                    Log.d("FileUtil", "ID:" + id + " bytesCurrent: " + bytesCurrent
                            + " bytesTotal: " + bytesTotal + " " + percentDone + "%");
                }

                @Override
                public void onError(int id, Exception ex) {
                    // Handle errors
                    Log.d("FileUtil", ex.toString());
                }

            });
        }catch(Exception e) {
            e.printStackTrace();
        }


    }


    public static void upload(Context context, String file, String path) {

        TransferUtility transferUtility =
                TransferUtility.builder()
                        .context(context)
                        .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                        .s3Client(new AmazonS3Client(AWSMobileClient.getInstance().getCredentialsProvider()))
                        .build();

        TransferObserver uploadObserver =
                transferUtility.upload(file, new File(path));

        // Attach a listener to the observer to get state update and progress notifications
        uploadObserver.setTransferListener(new TransferListener() {

            @Override
            public void onStateChanged(int id, TransferState state) {
                if (TransferState.COMPLETED == state) {
                    // Handle a completed upload.
                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                float percentDonef = ((float) bytesCurrent / (float) bytesTotal) * 100;
                int percentDone = (int)percentDonef;

                Log.d("FileUtil", "ID:" + id + " bytesCurrent: " + bytesCurrent
                        + " bytesTotal: " + bytesTotal + " " + percentDone + "%");
            }

            @Override
            public void onError(int id, Exception ex) {
                // Handle errors
            }

        });

        // If you prefer to poll for the data, instead of attaching a
        // listener, check for the state and progress in the observer.
        if (TransferState.COMPLETED == uploadObserver.getState()) {
            // Handle a completed upload.
            Log.d("FileUtil", "Upload Complete");

        }

        Log.d("FileUtil", "Bytes Transferrred: " + uploadObserver.getBytesTransferred());
        Log.d("FileUtil", "Bytes Total: " + uploadObserver.getBytesTotal());
    }

}
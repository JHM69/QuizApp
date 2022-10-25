package com.jhm69.quizapp_hometask.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.jhm69.bjs_contact.Model.Notices;
import com.jhm69.bjs_contact.SendNotificationPack.APIService;
import com.jhm69.bjs_contact.SendNotificationPack.Client;
import com.jhm69.bjs_contact.SendNotificationPack.MyResponse;
import com.jhm69.bjs_contact.SendNotificationPack.NotificationData;
import com.jhm69.bjs_contact.SendNotificationPack.NotificationSender;
import com.jhm69.bjs_contact.SendNotificationPack.Token;
import com.jhm69.bjs_contact.messege.model.Chat;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UploadService extends Service {

    public static final String ACTION_START_FOREGROUND_SERVICE = "ACTION_START_FOREGROUND_SERVICE";
    public static final String ACTION_STOP_FOREGROUND_SERVICE = "ACTION_STOP_FOREGROUND_SERVICE";
    private static final String TAG_FOREGROUND_SERVICE = UploadService.class.getSimpleName();
    private int count;
    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            startMyOwnForeground();
        else
            startForeground(1, new Notification());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startMyOwnForeground() {
        String NOTIFICATION_CHANNEL_ID = "other_channel";
        String channelName = "other channel";
        NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_HIGH);
        chan.setLightColor(Color.BLUE);
        chan.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        manager.createNotificationChannel(chan);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            String action = intent.getAction();
            if (action.equals(ACTION_START_FOREGROUND_SERVICE)) {
                Notices notices = (Notices) intent.getSerializableExtra("notices");
                Uri imageUri = intent.getParcelableExtra("imageUri");
                Uri bn = intent.getParcelableExtra("fileData");

                Uri myUri = intent.getParcelableExtra("image");
                String userid = intent.getStringExtra("userid");
                String fuserId = intent.getStringExtra("fuser");


                if (fuserId != null && userid != null) {
                    Toast.makeText(this, "Sending Images", Toast.LENGTH_SHORT).show();
                    sendMessage((int) System.currentTimeMillis(), fuserId, userid, myUri, userid, fuserId);
                } else if (imageUri != null || bn != null || notices != null) {

                    if (imageUri != null && bn != null) {
                        Toast.makeText(this, "Uploading file and image", Toast.LENGTH_SHORT).show();
                        uploadFilesAndImages((int) System.currentTimeMillis(), notices, bn, imageUri);
                    } else if (imageUri != null) {
                        Toast.makeText(this, "Uploading image", Toast.LENGTH_SHORT).show();
                        uploadImages((int) System.currentTimeMillis(), imageUri, notices);
                    } else if (bn != null) {
                        Toast.makeText(this, "Uploading file", Toast.LENGTH_SHORT).show();
                        uploadFiles((int) System.currentTimeMillis(), notices, bn);
                    } else {
                        Toast.makeText(this, "Uploading post", Toast.LENGTH_SHORT).show();
                        uploadPost((int) System.currentTimeMillis(), notices);
                    }

                } else {
                    Toast.makeText(this, "not sending", Toast.LENGTH_SHORT).show();
                }

            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void uploadFilesAndImages(int notification_id, Notices notices, Uri file, Uri image) {
        notifyProgress(notification_id
                ,
                "Uploading attachment.."
                , getApplicationContext()
        );
        try {
            String file_name = new File(file.getPath()).getName();
            final StorageReference fileToUpload = FirebaseStorage.getInstance().getReference().child("files").child("bjs" + System.currentTimeMillis() + "_" + file_name);
            fileToUpload.putFile(file)
                    .addOnSuccessListener(taskSnapshot -> fileToUpload.getDownloadUrl()
                            .addOnSuccessListener(uri -> {
                                notices.setFileName(file_name);
                                notices.setFile(uri.toString());
                                uploadImages(notification_id, image, notices);
                            }))
                    .addOnFailureListener(Throwable::printStackTrace)
                    .addOnProgressListener(taskSnapshot -> {
                    });
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void uploadFiles(int notification_id, Notices notices, Uri file) {
        notifyProgress(notification_id
                ,
                "Uploading attachment.."
                , getApplicationContext()
        );

        String file_name = new File(file.getPath()).getName();
        Log.d("TAG", file_name);
        final StorageReference fileToUpload = FirebaseStorage.getInstance().getReference().child("Notices_files").child("bjs" + System.currentTimeMillis() + file_name);
        fileToUpload.putFile(file).addOnSuccessListener(taskSnapshot -> fileToUpload.getDownloadUrl()
                .addOnSuccessListener(uri -> {
                    notices.setFileName(file_name);
                    notices.setFile(uri.toString());
                    try {
                        uploadPost(notification_id, notices);
                    } catch (Exception e) {
                        e.printStackTrace();
                        uploadPost(notification_id, notices);
                    }
                }))
                .addOnProgressListener(taskSnapshot -> Toast.makeText(getApplicationContext(), "uploading...", Toast.LENGTH_SHORT).show()).addOnFailureListener(e -> {
            Log.d("ERROR:: ", e.toString());
            Toast.makeText(getApplicationContext(), "failed. try again later", Toast.LENGTH_SHORT).show();
            stopForegroundService();
        });
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void stopForegroundService() {
        Log.d(TAG_FOREGROUND_SERVICE, "Stop foreground service.");
        stopForeground(true);
        stopSelf();
    }


    private void notifyProgress(int id, String message, Context context) {


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "other_channel");

        // Create notification default intent.
        Intent intent = new Intent();
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        builder.setSmallIcon(android.R.drawable.stat_sys_upload)
                .setContentTitle("BJS Contact")
                .setContentText(message)
                .setOngoing(true)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setTicker(message)
                .setChannelId("other_channel")
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setProgress(100, 34, true)
                .setVibrate(new long[100]);

        startForeground(id, builder.build());
    }

    private void uploadImages(final int notification_id, final Uri image, Notices notices) {
        notifyProgress(notification_id
                ,
                "Uploading attachment.."
                , getApplicationContext()
        );

        Uri imageUri;
        try {
            imageUri = image;
        } catch (Exception e) {
            e.printStackTrace();
            imageUri = image;
        }
        final StorageReference fileToUpload = FirebaseStorage.getInstance().getReference().child("Notices_images").child("bjs" + System.currentTimeMillis() + ".png");
        fileToUpload.putFile(imageUri).addOnSuccessListener(taskSnapshot -> fileToUpload.getDownloadUrl()
                .addOnSuccessListener(uri -> {
                    notices.setImage(uri.toString());
                    try {
                        uploadPost(notification_id, notices);
                    } catch (Exception e) {
                        e.printStackTrace();
                        uploadPost(notification_id, notices);
                    }
                })).addOnFailureListener(Throwable::printStackTrace)
                .addOnProgressListener(taskSnapshot -> {

                });

    }

    private void uploadPost(int notification_id, Notices notices) {
        FirebaseFirestore.getInstance().collection("Notices").document(notices.getId()).set(notices).addOnCompleteListener(task -> {
            Toast.makeText(getApplicationContext(), "Posted Notices.", Toast.LENGTH_SHORT).show();
            NotificationData notificationData = new NotificationData(
                    notices.getId(),
                    "New post from " + notices.getName(),
                    "",
                    notices.getImage(),
                    notices.getText().length() < 100 ? notices.getText() : notices.getText().substring(0, 100) + "..."
            );
            new SendNotificationPostAsyncTask(notificationData).execute();

            stopForegroundService();
        });
    }


    public void sendMessage(int notification_id, String sender, final String receiver, Uri image, String userid, String fuser) {
        notifyProgress(notification_id
                ,
                "Sending attachment.."
                , getApplicationContext()
        );
        final StorageReference fileToUpload = FirebaseStorage.getInstance().getReference().child("message_images").child("dojo_troops_" + System.currentTimeMillis() + ".png");
        fileToUpload.putFile(image).addOnSuccessListener(taskSnapshot -> fileToUpload.getDownloadUrl()
                .addOnSuccessListener(uri -> {
                    String imsge = uri.toString();
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                    Chat chat = new Chat(sender, receiver, "Photo", imsge, false, System.currentTimeMillis());

                    reference.child("Chats").push().setValue(chat);


                    // add user to chat fragment
                    final DatabaseReference chatRef = FirebaseDatabase.getInstance().getReference("Chatlist")
                            .child(fuser)
                            .child(userid);

                    chatRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (!dataSnapshot.exists()) {
                                chatRef.child("id").setValue(userid);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            stopForegroundService();
                        }
                    });

                    final DatabaseReference chatRefReceiver = FirebaseDatabase.getInstance().getReference("Chatlist")
                            .child(userid)
                            .child(fuser);
                    chatRefReceiver.child("id").setValue(fuser);
                    NotificationData notificationData = new NotificationData(fuser, "Someone from BJS Phonebook", "", "", "Sent an image");
                    new SendNotificationAsyncTask(notificationData, userid).execute();
                    stopForegroundService();
                }));

    }

    private static class SendNotificationAsyncTask extends AsyncTask<Void, Void, Void> {
        final APIService apiService;
        String who;
        NotificationData notificationData;

        private SendNotificationAsyncTask(NotificationData notificationData, String who) {
            this.notificationData = notificationData;
            this.who = who;
            apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);
        }

        @Override
        protected Void doInBackground(Void... jk) {
            try {
                FirebaseDatabase.getInstance().getReference().child("Tokens").child(who).child("token").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String usertoken = dataSnapshot.getValue(String.class);
                        NotificationSender sender = new NotificationSender(notificationData, usertoken);
                        apiService.sendNotifcation(sender).enqueue(new Callback<MyResponse>() {
                            @Override
                            public void onResponse(@NonNull Call<MyResponse> call, @NonNull Response<MyResponse> response) {
                                if (response.code() == 200) {
                                    if (Objects.requireNonNull(response.body()).success != 1) {

                                    }
                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<MyResponse> call, @NotNull Throwable t) {

                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            } catch (NullPointerException ignored) {

            }
            return null;
        }
    }


    private static class SendNotificationPostAsyncTask extends AsyncTask<Void, Void, Void> {
        final APIService apiService;
        NotificationData notificationData;

        private SendNotificationPostAsyncTask(NotificationData notificationData) {
            this.notificationData = notificationData;
            apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);
        }

        @Override
        protected Void doInBackground(Void... jk) {
            try {
                FirebaseDatabase.getInstance().getReference().child("Tokens").limitToLast(50).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        try {
                            for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                                try {
                                    Token token = postSnapshot.getValue(Token.class);
                                    NotificationSender sender = new NotificationSender(notificationData, Objects.requireNonNull(token).getToken());
                                    apiService.sendNotifcation(sender).enqueue(new Callback<MyResponse>() {
                                        @Override
                                        public void onResponse(@NonNull Call<MyResponse> call, @NonNull Response<MyResponse> response) {
                                            if (response.code() == 200) {
                                                if (Objects.requireNonNull(response.body()).success != 1) {
                                                }
                                            }
                                        }

                                        @Override
                                        public void onFailure(@NonNull Call<MyResponse> call, @NotNull Throwable t) {

                                        }
                                    });
                                } catch (Exception ignored) {
                                }
                            }
                        } catch (Exception ignored) {

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            } catch (NullPointerException ignored) {

            }
            return null;
        }
    }
}

package com.example.discaount.firebase;


public class SendingFirebaseToken {
//    private final String TAG = "MyFirebaseInstanceIDSer";
//
//    public void send(Context context, final String token) {
//        Log.d(TAG, "Refreshed token: " + token);
//
//        if(token == null || token.isEmpty()) return;
//
//        SharedPreferencesLocalStorage localStorage = new SharedPreferencesLocalStorage(context);
//
//        boolean isLogged = localStorage.isLogged();
//        if (isLogged) {
//            ServiceGenerator.createService(APIService.class, localStorage.getToken())
//                    .uploadFirebaseToken(token, "android").enqueue(new Callback<String>() {
//                @Override
//                public void onResponse(Call<String> call, Response<String> response) {
//                    if (response.isSuccessful()) {
//                        Log.d(TAG, "Send The Token: " + token);
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<String> call, Throwable t) {
//
//                }
//            });
//        }
//    }
}
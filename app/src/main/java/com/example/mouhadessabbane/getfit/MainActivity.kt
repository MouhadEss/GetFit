package com.example.mouhadessabbane.getfit

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.annotation.SuppressLint
import android.content.Intent
import android.content.RestrictionsManager
import android.support.v4.app.FragmentActivity
import android.util.Log
import android.view.View
import android.widget.Button
import com.facebook.*
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.ResultCallback
import com.google.android.gms.common.api.Status
import org.json.JSONException
import org.json.JSONObject
import java.lang.invoke.MethodHandles
import java.net.MalformedURLException
import java.net.URL
import com.google.android.gms.common.api.OptionalPendingResult


class MainActivity : AppCompatActivity(), View.OnClickListener, GoogleApiClient.OnConnectionFailedListener  {



    lateinit var btnSignIn:SignInButton
    lateinit var mGoogleApiClient:GoogleApiClient
    val TAG="MainActivity"
    lateinit var callbackManager:CallbackManager
    lateinit var loginButton:LoginButton
    lateinit var google:Button
    lateinit var fb:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        callbackManager=CallbackManager.Factory.create()
        fb=findViewById<Button>(R.id.fb)
        google=findViewById<Button>(R.id.google)
        loginButton=findViewById<LoginButton>(R.id.login_button)
        var permissionNeeds= arrayListOf<String>("user_photos", "email", "user_birthday", "public_profile", "AccessToken")
        loginButton.registerCallback(callbackManager,object: FacebookCallback<LoginResult>{
            override fun onSuccess(result: LoginResult?) {
                var accessToken:String= result!!.accessToken.token
                Log.i("accessToken",accessToken)
                val request:GraphRequest= GraphRequest.newMeRequest(result.accessToken,
                    object :GraphRequest.GraphJSONObjectCallback {
                        override fun onCompleted(`object`: JSONObject?, response: GraphResponse?) {
                            Log.i("LoginActivity",response.toString())
                            try {
                                val id=`object`!!.getString("id")
                                try {
                                    val profile_pic = URL(
                                        "http://graph.facebook.com/$id/picture?type=large"
                                    )
                                    Log.i("profile_pic", profile_pic.toString() + "")
                                }catch (e: MalformedURLException){
                                    e.printStackTrace()
                                }
                                Log.e("UserDate", `object`!!.toString())
                            }catch (e:JSONException){
                                e.printStackTrace()
                            }
                        }
                    })

                val parameters = Bundle()
                parameters.putString("fields", "id,name,email,gender, birthday")
                request.parameters = parameters
                request.executeAsync()
            }

            override fun onCancel() {

            }

            override fun onError(error: FacebookException?) {
                Log.v("LoginActivity", error.toString())
            }
        })

        initializeControls();
        initializeGPlusSettings();
    }

    private fun initializeControls() {
        btnSignIn = findViewById<View>(R.id.btn_sign_in) as SignInButton
        btnSignIn.setOnClickListener(this)
    }
    private fun initializeGPlusSettings(){
        val gso=GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        mGoogleApiClient=GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API,gso).build()
        btnSignIn.setSize(SignInButton.SIZE_STANDARD)
        btnSignIn.setScopes(gso.scopeArray)
    }

    private fun signIn() {
        val signInIntent=Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
        startActivityForResult(signInIntent,430)

    }

    private fun signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(object : ResultCallback<Status> {
            override fun onResult(p0: Status) {
                updateUI(false)
            }
        })
    }

    private fun handleGPlusSignInResult(result: GoogleSignInResult){
        Log.d(TAG, "handleSignInResult:" + result.isSuccess())
        if (result.isSuccess){
            val acct=result.signInAccount
            val personName = acct!!.displayName
            val personPhotoUrl = acct!!.photoUrl.toString()
            val email = acct!!.email
            val familyName = acct!!.familyName
            Log.e(TAG, "Name: " + personName +", email: " + email + ", Image: " + personPhotoUrl +", Family Name: " + familyName);
            updateUI(true)

        }else{
            updateUI(false)
        }
    }



    override fun onStart(){
        super.onStart()
        val opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient)
        if (opr.isDone) {
            Log.d(TAG, "Got cached sign-in")
            val result = opr.get()
            handleGPlusSignInResult(result)
        } else {
            opr.setResultCallback(object : ResultCallback<GoogleSignInResult> {

                override fun onResult(googleSignInResult: GoogleSignInResult) {
                    handleGPlusSignInResult(googleSignInResult)
                }
            })
        }
    }


    override fun onConnectionFailed(p0: ConnectionResult) {
        Log.d(TAG, "onConnectionFailed:" + p0);
    }


    override fun onActivityResult(requestCode: Int ,responseCode:Int ,data: Intent?) {
        if (requestCode == 430) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleGPlusSignInResult(result);
        }else{
            super.onActivityResult(requestCode, responseCode, data);
            callbackManager.onActivityResult(requestCode, responseCode, data);
        }
    }

    override fun onClick(v: View?) {
        if (v === fb) {
            loginButton.performClick()
        } else if (v === google) {
            signIn()
        }
    }

    private fun updateUI(isSignedIn:Boolean){
        if (isSignedIn) {
            btnSignIn.setVisibility(View.GONE)
        } else {
            btnSignIn.setVisibility(View.VISIBLE)
        }
    }
}


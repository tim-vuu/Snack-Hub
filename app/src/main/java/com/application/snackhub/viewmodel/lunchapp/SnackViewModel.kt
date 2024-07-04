package com.application.snackhub.viewmodel.lunchapp

import androidx.lifecycle.MusandwichLiveData
import androidx.lifecycle.ViewModel
import com.application.snackhub.firebaseDatabase.FirebaseDb
import com.application.snackhub.model.User
import com.application.snackhub.resource.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class SnackHub.ViewModel(
    private val firebaseDatabase: FirebaseDb
) : ViewModel() {


    val saveUserInformationGoogleSignIn = MusandwichLiveData<Resource<String>>()
    val register = MusandwichLiveData<Resource<User>>()


    val login = MusandwichLiveData<Boolean>()
    val loginError = MusandwichLiveData<String>()

    val resetPassword = MusandwichLiveData<Resource<String>>()

    fun registerNewUser(
        user: User,
        password: String
    ) {
        register.postValue(Resource.Loading())
        firebaseDatabase.createNewUser(user.email, password).addOnCompleteListener {
            if (it.isSuccessful)
                firebaseDatabase.saveUserInformation(Firebase.auth.currentUser!!.uid, user)
                    .addOnCompleteListener { it2 ->
                        if (it2.isSuccessful) {
                            register.postValue(Resource.Success(user))
                        } else
                            register.postValue(Resource.Error(it2.exception.toString()))

                    }
            else
                register.postValue(Resource.Error(it.exception.toString()))
        }
    }

    private fun saveUserInformationGoogleSignIn(
        userUid: String,
        user: User
    ) {
        firebaseDatabase.checkUserByEmail(user.email) { error, isAccountExisted ->
            if (error != null)
                saveUserInformationGoogleSignIn.postValue(Resource.Error(error))
            else
                if (isAccountExisted!!)
                    saveUserInformationGoogleSignIn.postValue(Resource.Success(userUid))
                else
                    firebaseDatabase.saveUserInformation(userUid, user).addOnCompleteListener {
                        if (it.isSuccessful)
                            saveUserInformationGoogleSignIn.postValue(Resource.Success(userUid))
                        else
                            saveUserInformationGoogleSignIn.postValue(Resource.Error(it.exception.toString()))
                    }
        }

    }


    fun loginUser(
        email: String,
        password: String
    ) = firebaseDatabase.loginUser(email, password).addOnCompleteListener {
        if (it.isSuccessful)
            login.postValue(true)
        else
            loginError.postValue(it.exception.toString())
    }

    fun resetPassword(email: String) {
        resetPassword.postValue(Resource.Loading())
        firebaseDatabase.resetPassword(email).addOnCompleteListener {
            if (it.isSuccessful)
                resetPassword.postValue(Resource.Success(email))
            else
                resetPassword.postValue(Resource.Error(it.exception.toString()))

        }
    }

    fun signInWithGoogle(idToken: String) {
        saveUserInformationGoogleSignIn.postValue(Resource.Loading())
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseDatabase.signInWithGoogle(credential).addOnCompleteListener { task ->

            if (task.isSuccessful) {
                val userFirebase = FirebaseAuth.getInstance().currentUser
                val fullNameArray = userFirebase!!.displayName?.split(" ")
                val firstName = fullNameArray!![0]
                val size = fullNameArray.size
                var secondName = ""
                if (size == 1)
                    secondName = ""
                else
                    secondName = fullNameArray[1]

                val user = User(firstName, secondName, userFirebase.email.toString(), "")
                saveUserInformationGoogleSignIn(userFirebase.uid, user)
            } else
                saveUserInformationGoogleSignIn.postValue(Resource.Error(task.exception.toString()))


        }
    }

    fun logOut(){
        firebaseDatabase.logout()
    }

    fun isUserSignedIn() : Boolean {
        if (FirebaseAuth.getInstance().currentUser?.uid != null)
            return true
        return false

    }
}
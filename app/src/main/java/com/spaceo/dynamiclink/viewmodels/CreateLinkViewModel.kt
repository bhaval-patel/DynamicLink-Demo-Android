package com.spaceo.dynamiclink.viewmodels

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.dynamiclinks.ktx.androidParameters
import com.google.firebase.dynamiclinks.ktx.dynamicLink
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.dynamiclinks.ktx.shortLinkAsync
import com.google.firebase.ktx.Firebase

class CreateLinkViewModel : ViewModel() {

    val refferCode by lazy { MutableLiveData<String>() }
    val shortLink by lazy { MutableLiveData<String>() }
    val dynamicLink by lazy { MutableLiveData<String>() }

    fun onCreateLinkClick() {

        val invitationLink =
            "http://www.yopmail.com/?invitedby=${refferCode.value}"
        val dynamicLink = Firebase.dynamicLinks.dynamicLink {
            link =
                Uri.parse(invitationLink)
            domainUriPrefix = "https://spaceo.page.link"
            // Open links with this app on Android
            androidParameters {
                minimumVersion = 1
            }
            // Open links with com.example.ios on iOS
//            iosParameters("com.example.ios") { }
        }

        val dynamicLinkUri = dynamicLink.uri
        this.dynamicLink.value = dynamicLinkUri.toString()

        val shortLinkTask = Firebase.dynamicLinks.shortLinkAsync {
            link =
                Uri.parse(invitationLink)
            domainUriPrefix = "https://spaceo.page.link"

            androidParameters {
                minimumVersion = 1
            }
            // Set parameters
            // ...
        }.addOnSuccessListener { result ->
            // Short link created
            val shortLink = result.shortLink
//            val flowchartLink = result.previewLink
            this.shortLink.value = shortLink.toString()
        }.addOnFailureListener {
            // Error
            // ...
            Log.d("log_tag", "==> ${it.localizedMessage}", it)
            this.shortLink.value = it.localizedMessage
        }

    }

}
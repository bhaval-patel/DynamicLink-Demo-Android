package com.spaceo.dynamiclink

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.dynamiclinks.DynamicLink
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.google.firebase.dynamiclinks.ktx.*
import com.google.firebase.ktx.Firebase
import com.spaceo.dynamiclink.databinding.ActivityCreateLinkBinding
import com.spaceo.dynamiclink.viewmodels.CreateLinkViewModel

class CreateLinkActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateLinkBinding
    private val createLinkViewModel by lazy {
        ViewModelProvider(this).get(CreateLinkViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_link)
        binding.lifecycleOwner = this
        binding.createLinkViewModel = createLinkViewModel
        binding.createLinkActivity = this
    }

    fun onCopyLinkClick(type: Int) {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        if (type == 0) {
            val clip = ClipData.newPlainText("Link", createLinkViewModel.dynamicLink.value)
            clipboard.setPrimaryClip(clip)
        } else {
            val clip = ClipData.newPlainText("Short Link", createLinkViewModel.shortLink.value)
            clipboard.setPrimaryClip(clip)
        }
    }

    fun onShareLinkClick() {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, createLinkViewModel.shortLink.value)

        startActivity(Intent.createChooser(intent, "Share Link"))
    }
}
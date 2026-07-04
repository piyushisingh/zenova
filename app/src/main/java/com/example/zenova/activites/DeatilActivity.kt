package com.example.zenova.activites

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.zenova.databinding.ActivityDeatilBinding
import com.example.zenova.domain.ItemModel

class DeatilActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDeatilBinding
    private lateinit var item: ItemModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDeatilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        bundle()
    }

    private fun bundle() {
        item = intent.getSerializableExtra("object") as ItemModel

        binding.apply {
            Glide.with(this@DeatilActivity)
                .load(item.picUrl)
                .into(personImg)

            titleTxt.text = item.title
            priceTxt.text = "$${item.price}"
            oldPriceTxt.text = "$${item.oldPrice}"

            classicPriceTxt.text = "$${item.classicPrice}"
            classicOldPriceTxt.text = "$${item.classicOldPrice}"

            premiumPriceTxt.text = "$${item.premiumPrice}"
            premiumOldPriceTxt.text = "$${item.premiumOldPrice}"

            platinumPriceTxt.text = "$${item.platinumPrice}"
            platinumOldPriceTxt.text = "$${item.platinumOldPrice}"

            oldPriceTxt.paintFlags = oldPriceTxt.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            classicOldPriceTxt.paintFlags = classicOldPriceTxt.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            premiumOldPriceTxt.paintFlags = premiumOldPriceTxt.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            platinumOldPriceTxt.paintFlags = platinumOldPriceTxt.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

            NameTxT.text = item.name
            jobTxt.text = item.job
            aboutTxt.text = item.description

            Glide.with(this@DeatilActivity)
                .load(item.profilePic)
                .into(profilePicDetail)

            backBtn.setOnClickListener {
                finish()
            }

            callBtn.setOnClickListener {
                dialNumber(context = this@DeatilActivity, phoneNumber = item.phone.toString())
            }

            messageBtn.setOnClickListener {
                sendSms(context = this@DeatilActivity, phoneNumber = item.phone.toString())
            }
        }
    }

    fun dialNumber(context: Context, phoneNumber: String) {
        val number = phoneNumber.trim()
        val uri = Uri.parse("tel:${Uri.encode(number)}")
        val intent = Intent(Intent.ACTION_DIAL, uri)
        try {
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(context, "Dialer not found", Toast.LENGTH_SHORT).show()
        }
    }

    fun sendSms(context: Context, phoneNumber: String, body: String = "") {
        val number = phoneNumber.trim()
        val uri = Uri.parse("smsto:${Uri.encode(number)}")
        val intent = Intent(Intent.ACTION_SENDTO, uri).apply {
            putExtra("sms_body", body)
        }
        try {
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(context, "Sms app not found", Toast.LENGTH_SHORT).show()
        }
    }
}
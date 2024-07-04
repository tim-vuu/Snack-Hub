package com.application.snackhub.activities

import android.content.Intent
import android.icu.lang.UCharacter.GraphemeClusterBreak.L
import android.os.Build.VERSION_CODES.M
import android.os.Build.VERSION_CODES.S
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.application.snackhub.R
import com.application.snackhub.firebaseDatabase.FirebaseDb
import com.application.snackhub.model.Product
import com.application.snackhub.util.Constants
import com.application.snackhub.util.Constants.Companion.PIZZA_CATEGORY
import com.application.snackhub.util.Constants.Companion.BLACK
import com.application.snackhub.util.Constants.Companion.BURGER_CATEGORY
import com.application.snackhub.util.Constants.Companion.COLORS
import com.application.snackhub.util.Constants.Companion.TEA_CATEGORY
import com.application.snackhub.util.Constants.Companion.SNACK_CATEGORY
import com.application.snackhub.util.Constants.Companion.GREEN
import com.application.snackhub.util.Constants.Companion.IMAGES
import com.application.snackhub.util.Constants.Companion.LARGE
import com.application.snackhub.util.Constants.Companion.MEDIUM
import com.application.snackhub.util.Constants.Companion.ORANGE
import com.application.snackhub.util.Constants.Companion.ORDERS
import com.application.snackhub.util.Constants.Companion.PRODUCTS_COLLECTION
import com.application.snackhub.util.Constants.Companion.RED
import com.application.snackhub.util.Constants.Companion.SIZES
import com.application.snackhub.util.Constants.Companion.SANDWICHS_CATEGORY
import com.application.snackhub.util.Constants.Companion.XLARGE
import com.application.snackhub.viewmodel.lunchapp.SnackHubViewModel
import com.application.snackhub.viewmodel.lunchapp.ViewModelProviderFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LunchActivity : AppCompatActivity() {
    val viewModel by lazy {
        val firebaseDb = FirebaseDb()
        val viewModelFactory = ViewModelProviderFactory(firebaseDb)
        ViewModelProvider(this,viewModelFactory)[SnackHub.ViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lunch)



        supportActionBar?.hide()


//        val random = Random.nextInt(from = 10000, until = 99999)

//        saveNewProduct()
    }

    private fun saveNewProduct() {

        val title = "Bread VN"
        val description = "Delicious"




            val category = SNACK_CATEGORY
        val price = "300"
        val newPrice = "229"
        val seller = "ps mart"
        val orders = 3

        val images = HashMap<String,Any>()
        val imagesList = listOf(
            "https://wakefit-co.s3.ap-south-1.amazonaws.com/img/product-thumbnails/elara-double-drawer-bedside-sandwich-lifestyle-rectangle-new.webp",
            "https://wakefit-co.s3.ap-south-1.amazonaws.com/img/product-thumbnails/elara-double-drawer-bedside-sandwich-lifestyle-rectangle-new.webp",
            "https://wakefit-co.s3.ap-south-1.amazonaws.com/img/product-thumbnails/elara-double-drawer-bedside-sandwich-lifestyle-rectangle-new.webp"

        )

        images.put(IMAGES,imagesList.toList())

        val colors = HashMap<String,Any>()
        val colorsList = listOf<String>(
            "#8D4E38"
        )

        colors.put(COLORS, colorsList.toList())

        val sizes = HashMap<String,Any>()
        val sizeUnit = "Space"
        val sizesList = listOf(
            "1*2",
        )

        sizes.put(SIZES,sizesList.toList())

        val prodcut = Product(1208025,title, description, category, newPrice,price, seller, images, colors, sizes,orders,null,sizeUnit)

        Firebase.firestore.collection(PRODUCTS_COLLECTION)
            .document()
            .set(prodcut)
    }

}
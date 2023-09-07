package com.example.e_votingapplication

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.airbnb.lottie.LottieAnimationView
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions


class detech_face : AppCompatActivity() {
    var c: Canvas? = null


    companion object {
        private const val REQUEST_IMAGE_CAPTURE = 1
    }
    private var doubleBackToExitPressedOnce = false

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finish()
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Press back again", Toast.LENGTH_SHORT).show()

        // Reset the flag after a delay (e.g., 2 seconds)
        Handler().postDelayed({
            doubleBackToExitPressedOnce = false
        }, 2000)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detech_face)
        supportActionBar?.hide()

        val lottieAnimationView = findViewById<LottieAnimationView>(R.id.lottieAnimationView2)
        lottieAnimationView.setAnimation(R.raw.animation_lluicuhc)
        lottieAnimationView.playAnimation()
        lottieAnimationView.loop(true)
        var button: CardView = findViewById(R.id.login)
        button.setOnClickListener {
            dispatchTakePictureIntent()
        }
    }
    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val bitmap = data?.extras?.get("data") as Bitmap
            val image = FirebaseVisionImage.fromBitmap(bitmap)
            val options = FirebaseVisionFaceDetectorOptions.Builder()
                .setClassificationMode(FirebaseVisionFaceDetectorOptions.ACCURATE)
                .setLandmarkMode(FirebaseVisionFaceDetectorOptions.ALL_LANDMARKS)
                .setClassificationMode(FirebaseVisionFaceDetectorOptions.ALL_CLASSIFICATIONS)
                .setMinFaceSize(0.15f)
                .enableTracking()
                .build()
            val detector = FirebaseVision.getInstance()
                .getVisionFaceDetector(options)
            val result = detector.detectInImage(image)
                .addOnSuccessListener { faces ->
                    var images: ImageView = findViewById(R.id.image)
                    images.setImageBitmap(bitmap)
                    val mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
                    val c = Canvas(mutableBitmap)
                    for (i in 0 until faces.size) {
                        val bounds: Rect = faces.get(i).getBoundingBox()
                        var p = Paint()
                        p.color = Color.GREEN
                        p.strokeWidth = 3f
                        p.style = Paint.Style.STROKE
                        c.drawRect(bounds, p)
                        images.setImageDrawable(BitmapDrawable(resources, mutableBitmap))
                    }
                    Handler().postDelayed({
                        if (faces.size >= 1) {
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                            Toast.makeText(this, "Face Verification Successful", Toast.LENGTH_SHORT).show()
                        }
                        else{
                            Toast.makeText(this, "Please Try Again", Toast.LENGTH_SHORT).show()
                        }
                    },1000)



                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Something Went Wrong !", Toast.LENGTH_SHORT).show()
                }
        }
    }

}







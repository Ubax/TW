package com.example.qrcodetransmission

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.util.Size
import android.graphics.Matrix
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import java.util.Base64
import android.util.Log
import android.view.Surface
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.google.firebase.FirebaseApp
import com.google.firebase.ml.vision.FirebaseVision
import java.util.concurrent.Executors

private const val REQUEST_CODE_PERMISSIONS = 10
private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)

class MainActivity : AppCompatActivity(), LifecycleOwner {

    private lateinit var resultTextView: TextView
    private val executor = Executors.newSingleThreadExecutor()
    private lateinit var viewFinder: TextureView
    private val decoder = Base64.getDecoder()
    private lateinit var receivedFile: Array<ByteArray>
    private var initialized: Boolean = false

    private fun getFile(): ByteArray {
        return receivedFile.reduce { acc: ByteArray, bytes: ByteArray ->
            acc + bytes
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContentView(R.layout.activity_main)
        viewFinder = findViewById<View>(R.id.viewFinder) as TextureView
        resultTextView = findViewById<View>(R.id.result) as TextView
        resultTextView.movementMethod = ScrollingMovementMethod()

        // Request camera permissions
        if (allPermissionsGranted()) {
            viewFinder.post { startCamera() }
        } else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
            )
        }

        // Every time the provided texture view changes, recompute layout
        viewFinder.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
            updateTransform()
        }
    }


    @SuppressLint("RestrictedApi")
    private fun startCamera() {
        val previewConfig = PreviewConfig.Builder().apply {
            setTargetResolution(Size(640, 480))
            setLensFacing(CameraX.LensFacing.BACK)
        }.build()


        // Build the viewfinder use case
        val preview = Preview(previewConfig)

        // Every time the viewfinder is updated, recompute layout
        preview.setOnPreviewOutputUpdateListener {

            // To update the SurfaceTexture, we have to remove it and re-add it
            val parent = viewFinder.parent as ViewGroup
            parent.removeView(viewFinder)
            parent.addView(viewFinder, 0)

            viewFinder.surfaceTexture = it.surfaceTexture
            updateTransform()
        }

        FirebaseVision.getInstance()

        val imageAnalysisConfig = ImageAnalysisConfig.Builder()
            .build()
        val imageAnalysis = ImageAnalysis(imageAnalysisConfig)

        val qrCodeAnalyzer = QrCodeAnalyzer { qrCodes ->
            qrCodes.forEach {
                this.runOnUiThread {
                    val d: String = it.rawValue!!
                    val n: String = d.split("|")[0]
                    val size: String = d.split("|")[1]
                    if (!this.initialized) {
                        receivedFile = Array(size.toInt()) { ByteArray(0) }
                        initialized = true
                    }
                    val data: String = d.split("|")[2]
                    val decoded = decoder.decode(data)
                    receivedFile[n.toInt()] = decoded
                    resultTextView.text = n + String(getFile(), Charsets.UTF_8)
                }
                Log.d("MainActivity", "QR Code detected: ${it.rawValue}.")
            }
        }

        imageAnalysis.setAnalyzer(executor, qrCodeAnalyzer)

        // We need to bind preview and imageAnalysis use cases
        CameraX.bindToLifecycle(this as LifecycleOwner, preview, imageAnalysis)
    }

    private fun updateTransform() {
        val matrix = Matrix()

        // Compute the center of the view finder
        val centerX = viewFinder.width / 2f
        val centerY = viewFinder.height / 2f

        // Correct preview output to account for display rotation
        val rotationDegrees = when (viewFinder.display.rotation) {
            Surface.ROTATION_0 -> 0
            Surface.ROTATION_90 -> 90
            Surface.ROTATION_180 -> 180
            Surface.ROTATION_270 -> 270
            else -> return
        }
        matrix.postRotate(-rotationDegrees.toFloat(), centerX, centerY)

        // Finally, apply transformations to our TextureView
        viewFinder.setTransform(matrix)
    }

    /**
     * Process result from permission request dialog box, has the request
     * been granted? If yes, start Camera. Otherwise display a toast
     */
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                viewFinder.post { startCamera() }
            } else {
                Toast.makeText(
                    this,
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    /**
     * Check if all permission specified in the manifest have been granted
     */
    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it
        ) == PackageManager.PERMISSION_GRANTED
    }
}


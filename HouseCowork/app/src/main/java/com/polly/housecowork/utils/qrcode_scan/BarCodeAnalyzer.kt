package com.polly.housecowork.utils.qrcode_scan

import android.annotation.SuppressLint
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import javax.inject.Inject

class BarcodeAnalyzer @Inject constructor(
    private val onQrCodeScanned: (List<Barcode>) -> Unit,
    private val onError: (Exception) -> Unit,
): ImageAnalysis.Analyzer {
    private val scanner = BarcodeScanning.getClient(
        BarcodeScannerOptions.Builder()
            .setBarcodeFormats(
                Barcode.FORMAT_QR_CODE
            )
            .build()
    )
    @SuppressLint("UnsafeOptInUsageError")
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image

        mediaImage?.let {
            val image = InputImage.fromMediaImage(
                mediaImage,
                imageProxy.imageInfo.rotationDegrees
            )

            scanner.process(image)
                .addOnSuccessListener { barcodes ->
                    onQrCodeScanned(barcodes)
                }
                .addOnFailureListener { exception ->
                    onError(exception)
                }
                .addOnCompleteListener {
                    imageProxy.close()
                }
        }?: imageProxy.close()
    }
}
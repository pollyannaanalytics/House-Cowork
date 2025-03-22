package com.polly.housecowork.compose.house

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.camera.core.Preview
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.polly.housecowork.utils.qrcode_scan.BarcodeAnalyzer

@Composable
fun HouseQrScanScreen(
    modifier: Modifier = Modifier,
    barcodeAnalyzer: BarcodeAnalyzer = BarcodeAnalyzer(
        onQrCodeScanned = { barcodes ->
            // Handle scanned barcodes
            Log.d("BarcodeAnalyzer", "Scanned barcodes: $barcodes")
        },
        onError = { exception ->
            // Handle error
            Log.e("BarcodeAnalyzer", "Error: $exception")
        }
    )
) {
    var isCameraPermissionGranted by remember { mutableStateOf(false) }
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        isCameraPermissionGranted = isGranted
    }

    var preview by remember { mutableStateOf<Preview?>(null) }

    LaunchedEffect(requestPermissionLauncher) {
        requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
    }
    Scaffold { paddingValues ->
        Column(
            modifier = modifier.padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box {
                if (isCameraPermissionGranted) {
                    CameraView(
                        modifier = Modifier.fillMaxSize(),
                        barcodeAnalyzer = barcodeAnalyzer,
                        preview = preview,
                        onPreviewChange = {
                            preview = it
                        }
                    )


                } else {
                    Text("Camera permission is required to scan QR code")
                }
            }

            Text("Just scan the House's QR code to join the house")
        }
    }
}

@Composable
fun CameraView(
    modifier: Modifier = Modifier,
    barcodeAnalyzer: BarcodeAnalyzer,
    preview: Preview?,
    onPreviewChange: (Preview) -> Unit
) {

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    AndroidView(
        modifier = modifier,
        factory = { context ->
            PreviewView(context).apply {
                scaleType = PreviewView.ScaleType.FILL_CENTER
            }
        },
        update = { previewView ->
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
            cameraProviderFuture.addListener(
                {
                    val cameraProvider = cameraProviderFuture.get()
                    val imageAnalysis = ImageAnalysis.Builder()
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build()
                        .also {
                            it.setAnalyzer(
                                ContextCompat.getMainExecutor(context),
                                barcodeAnalyzer
                            )
                        }
                    onPreviewChange(Preview.Builder().build().also {
                        it.setSurfaceProvider(previewView.surfaceProvider)
                    })
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        cameraSelector,
                        preview,
                        imageAnalysis
                    )
                }, ContextCompat.getMainExecutor(context)
            )

        }
    )
}

@androidx.compose.ui.tooling.preview.Preview
@Composable
fun PreviewHouseQrScanScreen() {
    HouseQrScanScreen()
}


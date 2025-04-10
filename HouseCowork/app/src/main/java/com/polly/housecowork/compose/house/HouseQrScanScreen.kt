package com.polly.housecowork.compose.house

import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.camera.core.Preview
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.polly.housecowork.utils.qrcode_scan.BarcodeAnalyzer
import android.graphics.RectF
import androidx.compose.foundation.layout.fillMaxHeight
import com.polly.housecowork.ui.theme.LocalColorScheme
import com.polly.housecowork.ui.theme.LocalTypography


@Composable
fun HouseQrScanScreen(
    modifier: Modifier = Modifier
) {

    var isCameraPermissionGranted by remember { mutableStateOf(false) }
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        isCameraPermissionGranted = isGranted
    }
    var detectUrl by remember { mutableStateOf("") }
    val barcodeAnalyzer = BarcodeAnalyzer(
        onQrCodeScanned = { barcodes ->
            detectUrl = barcodes.firstOrNull()?.displayValue ?: ""
        }
    )

    var preview by remember { mutableStateOf<Preview?>(null) }


    LaunchedEffect(requestPermissionLauncher) {
        requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
    }
    Scaffold { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(LocalColorScheme.current.background)
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.8f),
                contentAlignment = Alignment.Center,
            ) {
                if (isCameraPermissionGranted) {
                    val context = LocalContext.current
                    val lifecycleOwner = LocalLifecycleOwner.current

                    if (detectUrl.isNotEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .background(Color.White),
                        ) {
                            Text(
                                text = detectUrl,
                                modifier = Modifier.align(Alignment.Center),
                                style = LocalTypography.current.bodySmall
                            )
                        }
                    }
                    AndroidView(
                        modifier = modifier.fillMaxSize(),
                        factory = { context ->
                            PreviewView(context).apply {
                                scaleType = PreviewView.ScaleType.FILL_CENTER
                            }
                        },
                        update = { previewView ->
                            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                            val cameraProviderFuture =
                                ProcessCameraProvider.getInstance(context)
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
                                    preview = Preview.Builder().build().also {
                                        it.setSurfaceProvider(previewView.surfaceProvider)
                                    }
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

                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val centerX = size.width / 2
                        val centerY = size.height / 2
                        val clearAreaLeft = centerX - 300
                        val clearAreaTop = centerY - 300
                        val clearAreaSize = 600f
                        val cornerRadius = 16.dp.toPx()
                        val borderWidth = 4.dp.toPx()

                        with(drawContext.canvas.nativeCanvas) {
                            save()

                            // Draw the black overlay around the center area
                            drawRect(
                                color = Color.Black.copy(alpha = 0.5f),
                                size = size
                            )

                            drawRoundRect(
                                RectF(
                                    clearAreaLeft - borderWidth / 2,
                                    clearAreaTop - borderWidth / 2,
                                    clearAreaLeft + clearAreaSize + borderWidth / 2,
                                    clearAreaTop + clearAreaSize + borderWidth / 2
                                ),
                                cornerRadius,
                                cornerRadius,
                                android.graphics.Paint().apply {
                                    color = android.graphics.Color.WHITE
                                    style = android.graphics.Paint.Style.STROKE
                                    strokeWidth = borderWidth
                                }
                            )

                            // Clear the area inside the rounded rectangle
                            drawRoundRect(
                                RectF(
                                    clearAreaLeft,
                                    clearAreaTop,
                                    clearAreaLeft + clearAreaSize,
                                    clearAreaTop + clearAreaSize
                                ),
                                cornerRadius,
                                cornerRadius,
                                Paint().apply {
                                    color = android.graphics.Color.TRANSPARENT
                                    xfermode =
                                        PorterDuffXfermode(PorterDuff.Mode.CLEAR)
                                }
                            )

                            restore()
                        }
                    }


                } else {
                    Text("Camera permission is required to scan QR code")
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                Text(
                    text = "Just scan the House's QR code to join house",
                    modifier = Modifier.align(Alignment.Center),
                    style = LocalTypography.current.bodySmall
                )
            }
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview
@Composable
fun PreviewHouseQrScanScreen() {
    HouseQrScanScreen()
}


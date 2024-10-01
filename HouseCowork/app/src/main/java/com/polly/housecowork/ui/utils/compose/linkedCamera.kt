package com.polly.housecowork.ui.utils.compose

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

public val Linked_camera: ImageVector
    get() {
        if (_Linked_camera != null) {
            return _Linked_camera!!
        }
        _Linked_camera = ImageVector.Builder(
            name = "Linked_camera",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(826f, 280f)
                quadToRelative(0f, -78f, -54f, -132f)
                reflectiveQuadToRelative(-132f, -54f)
                verticalLineToRelative(-54f)
                quadToRelative(100f, 0f, 170f, 70f)
                reflectiveQuadToRelative(70f, 170f)
                close()
                moveToRelative(-106f, 0f)
                quadToRelative(0f, -33f, -23.5f, -56.5f)
                reflectiveQuadTo(640f, 200f)
                verticalLineToRelative(-54f)
                quadToRelative(55f, 0f, 93.5f, 39f)
                reflectiveQuadToRelative(40.5f, 95f)
                close()
                moveTo(160f, 840f)
                quadToRelative(-33f, 0f, -56.5f, -23.5f)
                reflectiveQuadTo(80f, 760f)
                verticalLineToRelative(-480f)
                quadToRelative(0f, -33f, 23.5f, -56.5f)
                reflectiveQuadTo(160f, 200f)
                horizontalLineToRelative(126f)
                lineToRelative(74f, -80f)
                horizontalLineToRelative(240f)
                verticalLineToRelative(80f)
                horizontalLineTo(395f)
                lineToRelative(-73f, 80f)
                horizontalLineTo(160f)
                verticalLineToRelative(480f)
                horizontalLineToRelative(640f)
                verticalLineToRelative(-440f)
                horizontalLineToRelative(80f)
                verticalLineToRelative(440f)
                quadToRelative(0f, 33f, -23.5f, 56.5f)
                reflectiveQuadTo(800f, 840f)
                close()
                moveToRelative(320f, -140f)
                quadToRelative(75f, 0f, 127.5f, -52.5f)
                reflectiveQuadTo(660f, 520f)
                reflectiveQuadToRelative(-52.5f, -127.5f)
                reflectiveQuadTo(480f, 340f)
                reflectiveQuadToRelative(-127.5f, 52.5f)
                reflectiveQuadTo(300f, 520f)
                reflectiveQuadToRelative(52.5f, 127.5f)
                reflectiveQuadTo(480f, 700f)
                moveToRelative(0f, -80f)
                quadToRelative(-42f, 0f, -71f, -29f)
                reflectiveQuadToRelative(-29f, -71f)
                reflectiveQuadToRelative(29f, -71f)
                reflectiveQuadToRelative(71f, -29f)
                reflectiveQuadToRelative(71f, 29f)
                reflectiveQuadToRelative(29f, 71f)
                reflectiveQuadToRelative(-29f, 71f)
                reflectiveQuadToRelative(-71f, 29f)
            }
        }.build()
        return _Linked_camera!!
    }

private var _Linked_camera: ImageVector? = null

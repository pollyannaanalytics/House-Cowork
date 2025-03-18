package com.polly.housecowork.compose.createtask.content.timepicker

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun InfiniteWheel(
    modifier: Modifier = Modifier,
    isEndless: Boolean = true,
    itemSize: DpSize,
    rowOffset: Int,
    currentSelect: Int,
    onFocusItem: (Int) -> Unit,
    selectorOption: SelectorOptions,
    lazyWheelState: LazyListState? = null,
    userScrollEnabled: Boolean = true,
    itemCount: Int,
    content: @Composable LazyItemScope.(index: Int) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val haptic = LocalHapticFeedback.current

    val count = if (isEndless) itemCount else itemCount + 2 * rowOffset
    val rowOffsetCount = maxOf(1, minOf(rowOffset, 3))
    val rowCount = ((rowOffsetCount * 2) + 1)
    val startIndex =
        if (isEndless) currentSelect + (itemCount * 1000) - rowOffset else currentSelect

    val state = lazyWheelState ?: rememberLazyListState(startIndex)
    val size = DpSize(itemSize.width, itemSize.height * rowCount)

    val isScrollInProgress = state.isScrollInProgress
    val focusedIndex = remember {
        derivedStateOf { state.firstVisibleItemIndex + rowOffsetCount }
    }

    LaunchedEffect(itemCount) {
        coroutineScope.launch {
            state.scrollToItem(startIndex)
        }
    }

    LaunchedEffect(isScrollInProgress) {
        if (!isScrollInProgress) {
            calculateIndexToFocus(state, size.height).let {
                val indexToFocus = if (isEndless) {
                    (it + rowOffsetCount) % itemCount
                } else {
                    ((it + rowOffsetCount) % count) - rowOffset
                }

                onFocusItem(indexToFocus)
                if (state.firstVisibleItemScrollOffset != 0) {
                    coroutineScope.launch {
                        state.animateScrollToItem(it, 0)
                    }
                }
            }
        }
    }

    LaunchedEffect(state) {
        snapshotFlow { state.firstVisibleItemIndex }.collect {
            if (selectorOption.selectEffectEnabled) haptic.performHapticFeedback(HapticFeedbackType.LongPress)
        }
    }


    Box(
        modifier = modifier
            .height(size.height)
            .fillMaxWidth(),
    ) {

        LazyColumn(
            modifier = Modifier
                .height(size.height)
                .fillMaxWidth(),
            state = state,
            userScrollEnabled = userScrollEnabled,
        ) {


            items(if (isEndless) Int.MAX_VALUE else count) {
                val rotateDegree = calculateIndexRotation(focusedIndex.value, it, rowOffset)
                Box(
                    modifier = Modifier
                        .height(size.height / rowCount)
                        .fillMaxWidth()
                        .graphicsLayer {
                            this.rotationX = rotateDegree
                        },
                    contentAlignment = Alignment.Center,
                ) {
                    if (isEndless) {
                        content(it % itemCount)
                    } else if (it >= rowOffsetCount && it < itemCount + rowOffsetCount) {
                        content((it - rowOffsetCount) % itemCount)
                    }
                }

            }
        }

        if (selectorOption.enabled) {
            SelectionView(
                modifier
                    .height(size.height)
                    .fillMaxWidth(), selectorOptions = selectorOption, rowOffset
            )
        }

    }

}


private fun calculateIndexToFocus(listState: LazyListState, height: Dp): Int {
    val currentItem = listState.layoutInfo.visibleItemsInfo.firstOrNull()
    var index = currentItem?.index ?: 0

    if (currentItem?.offset != 0) {
        currentItem?.let {
            if (it.offset <= -height.value * 3 / 10) {
                index++
            }
        }
    }
    return index

}

@Composable
private fun calculateIndexRotation(focusedIndex: Int, index: Int, offset: Int): Float {
    return (6 * offset + 1).toFloat() * (focusedIndex - index)
}

@Preview
@Composable
fun PreviewInfiniteWheel() {
    InfiniteWheel(
        itemSize = DpSize(100.dp, 50.dp),
        rowOffset = 1,
        currentSelect = 0,
        onFocusItem = {},
        selectorOption = SelectorOptions(
            width = 80f.dp,
            alpha = 0.5f,
            color = Color.White
        ),
        itemCount = 12,
        content = {
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(50.dp))
        }
    )
}
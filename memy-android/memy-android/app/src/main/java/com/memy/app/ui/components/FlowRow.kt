package com.memy.app.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Simple flow-row layout — wraps items to the next line when they run out of horizontal space.
 * (Replace with Compose Foundation FlowRow when available on your compile SDK.)
 */
@Composable
fun FlowRow(
    modifier: Modifier = Modifier,
    horizontalGap: Dp = 8.dp,
    verticalGap: Dp = 8.dp,
    content: @Composable () -> Unit,
) {
    Layout(
        content  = content,
        modifier = modifier,
    ) { measurables, constraints ->
        val hGap = horizontalGap.roundToPx()
        val vGap = verticalGap.roundToPx()
        val placeables = measurables.map { it.measure(constraints.copy(minWidth = 0)) }

        var x = 0; var y = 0; var rowH = 0

        val positions = placeables.map { p ->
            if (x + p.width > constraints.maxWidth && x > 0) {
                x = 0; y += rowH + vGap; rowH = 0
            }
            val pos = Pair(x, y)
            x += p.width + hGap
            rowH = maxOf(rowH, p.height)
            pos
        }
        val totalHeight = y + rowH

        layout(constraints.maxWidth, totalHeight) {
            placeables.forEachIndexed { i, p ->
                p.placeRelative(positions[i].first, positions[i].second)
            }
        }
    }
}

package com.axlt.quotescanner.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.axlt.quotescanner.R

@Composable
fun ButtonGroup(
    buttons: List<ButtonGroupButton>,
    isExpanded: Boolean,
    onMainButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (isExpanded) {
            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                buttons.forEachIndexed { index, buttonGroupButton ->
                    val buttonModifier =
                    if (index == 1) {
                        Modifier
                            .padding(bottom = 20.dp, start = 20.dp, end = 20.dp)
                    } else {
                        Modifier
                    }

                    IconButtonWithText(
                        icon = buttonGroupButton.iconId,
                        text = buttonGroupButton.text,
                        modifier = buttonModifier,
                        onClick = {
                            buttonGroupButton.onButtonClicked()
                        },
                    )
                }
            }
        }

        OutlinedIconButton(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primary, CircleShape),
            border = BorderStroke(0.dp, MaterialTheme.colorScheme.primary),
            onClick = {
                onMainButtonClicked()
            },
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_plus),
                contentDescription = "",
                tint = Color.White
            )
        }
    }
}

@Composable
fun IconButtonWithText(
    icon: Int,
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(Color.White)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(color = MaterialTheme.colorScheme.primary)
                ) {
                    onClick()
                },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Icon(
                painterResource(id = icon),
                modifier = Modifier,
                contentDescription = ""
            )
        }
        Text(text = text, color = Color.Black)
    }
}

interface ButtonGroupButton {
    val iconId: Int
    val text: String
    fun onButtonClicked()
}

@Preview(showBackground = true)
@Composable
fun ButtonGroupPreview() {
    ButtonGroup(
        listOf(),
        true,
        {},
    )
}
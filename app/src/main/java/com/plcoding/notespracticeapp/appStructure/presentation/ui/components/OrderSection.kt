
package com.plcoding.notespracticeapp.appStructure.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.plcoding.notespracticeapp.R
import com.plcoding.notespracticeapp.appStructure.domain.util.NoteOrder
import com.plcoding.notespracticeapp.appStructure.domain.util.OrderType

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    onOrderChange: (NoteOrder) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        // Category Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                selected = noteOrder is NoteOrder.Title,
                onClick = { onOrderChange(NoteOrder.Title(noteOrder.orderType)) },
                label = { Text(stringResource(R.string.note_title)) },
                leadingIcon = {
                    if (noteOrder is NoteOrder.Title) {
                        Icon(Icons.Filled.Check, contentDescription = null)
                    }
                }
            )
            FilterChip(
                selected = noteOrder is NoteOrder.Date,
                onClick = { onOrderChange(NoteOrder.Date(noteOrder.orderType)) },
                label = { Text(stringResource(R.string.note_date)) },
                leadingIcon = {
                    if (noteOrder is NoteOrder.Date) {
                        Icon(Icons.Filled.Check, contentDescription = null)
                    }
                }
            )
            FilterChip(
                selected = noteOrder is NoteOrder.Colour,
                onClick = { onOrderChange(NoteOrder.Colour(noteOrder.orderType)) },
                label = { Text(stringResource(R.string.note_colour)) },
                leadingIcon = {
                    if (noteOrder is NoteOrder.Colour) {
                        Icon(Icons.Filled.AcUnit, contentDescription = null)
                    }
                }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Ascending/Descending Order Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                selected = noteOrder.orderType is OrderType.Ascending,
                onClick = { onOrderChange(noteOrder.copy(OrderType.Ascending)) },
                label = { Text(stringResource(R.string.order_Ascending)) },
                leadingIcon = {
                    if (noteOrder.orderType is OrderType.Ascending) {
                        Icon(Icons.Filled.Check, contentDescription = null)
                    }
                }
            )
            FilterChip(
                selected = noteOrder.orderType is OrderType.Descending,
                onClick = { onOrderChange(noteOrder.copy(OrderType.Descending)) },
                label = { Text(stringResource(R.string.order_Descending)) },
                leadingIcon = {
                    if (noteOrder.orderType is OrderType.Descending) {
                        Icon(Icons.Filled.Check, contentDescription = null)
                    }
                }
            )
        }
    }
}


//        Row(
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            DefaultRadioButton(
//                text = stringResource(R.string.note_title),
//                selected = noteOrder is NoteOrder.Title,
//                onSelect = { onOrderChange(NoteOrder.Title(noteOrder.orderType)) }
//            )
//            Spacer(modifier = Modifier.width(8.dp))
//            DefaultRadioButton(
//                text = stringResource(R.string.note_date),
//                selected = noteOrder is NoteOrder.Date,
//                onSelect = { onOrderChange(NoteOrder.Date(noteOrder.orderType)) }
//            )
//            Spacer(modifier = Modifier.width(8.dp))
//            DefaultRadioButton(
//                text = stringResource(R.string.note_colour),
//                selected = noteOrder is NoteOrder.Colour,
//                onSelect = { onOrderChange(NoteOrder.Colour(noteOrder.orderType)) }
//            )
//        }
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Row(
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            DefaultRadioButton(
//                text = stringResource(R.string.order_Ascending),
//                selected = noteOrder.orderType is OrderType.Ascending,
//                onSelect = {
//                    onOrderChange(noteOrder.copy(OrderType.Ascending))
//                }
//            )
//            Spacer(modifier = Modifier.width(8.dp))
//            DefaultRadioButton(
//                text = stringResource(R.string.order_Descending),
//                selected = noteOrder.orderType is OrderType.Descending,
//                onSelect = {
//                    onOrderChange(noteOrder.copy(OrderType.Descending))
//                }
//            )
//        }
//    }
//}




package com.example.mobil.Presentation.Screens.Details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.mobil.Domain.Models.Book
import com.example.mobil.R

@Composable
fun Books(book: Book, getUrl: (String) -> String) {
    var imageUrl by remember { mutableStateOf("") }

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val imageState = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current).data(imageUrl)
                    .size(Size.ORIGINAL).build()
            ).state

            LaunchedEffect(book) {
                imageUrl = getUrl(book.id)
            }
            if (imageState is AsyncImagePainter.State.Loading) {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(8.dp))
                ) {
                    CircularProgressIndicator()
                }
            }
            if (imageState is AsyncImagePainter.State.Error) {
                Image(

                    painter = painterResource(R.drawable.book),
                    contentDescription = book.title,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(8.dp)),

                    contentScale = ContentScale.Crop
                )
            }
            if (imageState is AsyncImagePainter.State.Success) {
                Image(

                    painter = imageState.painter,
                    contentDescription = book.title,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = book.title,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = book.description,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
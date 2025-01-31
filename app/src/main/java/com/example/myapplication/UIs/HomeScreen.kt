package com.example.myapplication.UIs

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.DataSource
import com.example.myapplication.Navigation.Navbar
import com.example.myapplication.R
import com.example.myapplication.models.Product
import kotlinx.coroutines.delay

@Composable
fun Home(navController: NavController){
    var offsetX by remember { mutableStateOf(1000.dp) } // Start off-screen
    val animatedOffsetX by animateDpAsState(
        targetValue = offsetX,
        animationSpec = tween(durationMillis = 500)
    )

    // Trigger the animation when the screen is displayed
    LaunchedEffect(Unit) {
        delay(100) // Optional delay to make the transition smoother
        offsetX = 0.dp // Move the screen into view
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .offset(x = animatedOffsetX), // Apply the animated offset

    ){
        Column (
            modifier = Modifier.fillMaxSize(),
        )
        {
            Spacer(modifier = Modifier.height(5.dp))

            Column (modifier = Modifier.weight(2f).verticalScroll(rememberScrollState()).fillMaxWidth().padding(bottom = 60.dp))
            {
                LogoAndSearchBar()
                Spacer(modifier = Modifier.height(10.dp))
                ReusableTextHeader(title = "Featured", buttonText = "View More", onButtonClick = {navController.navigate("products")})
                TrendingProductList(productList = DataSource().loadTrending(), navController = navController)
                Divider(modifier = Modifier.height(15.dp))

                Divider(modifier = Modifier.height(15.dp))
                ProductSectionUI()
                ReusableTextHeader(title = "Nike", buttonText = "View More", onButtonClick = {navController.navigate("products")})
                TrendingProductList(productList = DataSource().loadNike(), navController = navController)
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter) // Position at the bottom
                .fillMaxWidth()
        ) {
            Navbar(navController = navController)
        }

    }
}

@Composable
fun ReusableTextHeader(
    title: String,
    buttonText: String = "Show more", // Optional button text
    onButtonClick: () -> Unit = {}   // Optional click listener
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title, // Use the passed title
            fontSize = 20.sp,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.weight(1f))
        TextButton(
            onClick = onButtonClick, // Trigger the passed click action
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {
            Text(
                text = buttonText, // Use the passed button text
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.Gray,
                    fontWeight = FontWeight.Bold
                ),
                fontSize = 15.sp
            )
        }
    }
}

@Composable
fun TrendingProductCard(product: Product, modifier: Modifier = Modifier, navController: NavController) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .width(200.dp)
            .height(330.dp) // Adjust height to match the design
            .clip(RoundedCornerShape(16.dp)), // Rounded corners
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.scrim) // Black background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Product image
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(product.imagesResourceId),
                    contentDescription = null, // Content description for accessibility
                    modifier = Modifier.size(140.dp), // Image size
                    contentScale = ContentScale.Fit
                )
            }

            // Product details
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = stringResource(product.stringResourceId), // Replace with dynamic title
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = stringResource(product.categoryRes), // Replace with dynamic category
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = stringResource(product.priceRes), // Replace with dynamic price
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // View button
            Button(
                onClick = {navController.navigate("masterview/${product.imagesResourceId}") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
            ) {
                Text(
                    text = "View",
                    color = Color.Black
                )
            }
        }
    }
}
@Composable
fun ProductSectionUI() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xD0000000))
            .padding(15.dp)
    ) {
        // Top banner
        Image(
            painter = painterResource(id = R.drawable.shoe50off),
            contentDescription = "50% Off Banner",
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            contentScale = ContentScale.FillBounds
        )

        Spacer(modifier = Modifier.height(15.dp))

        // Product cards row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ProductCard(
                productName = "Adidas 4D Futurecraft",
                productType = "Men's shoes",
                oldPrice = "LKR 20,000",
                newPrice = "LKR 10,000",
                sizes = listOf("UK 5", "UK 5.5", "UK 7", "UK 8"),
                productDetails = "New Balance 327: '70s-inspired design with padded tongue, suede upper, and durable rubber outsole.",
                productImageRes = R.drawable.shoe50png
            )

            Spacer(modifier = Modifier.width(8.dp))

            ProductCard(
                productName = "Air Force 1",
                productType = "Men's shoes",
                oldPrice = "LKR 23,500",
                newPrice = "LKR 11,750",
                sizes = listOf("UK 5", "UK 7", "UK 8", "UK 9"),
                productDetails = "Air Force 1: From court legend to playground icon, its durable leather",
                productImageRes = R.drawable.image_11
            )
        }
    }
}

@Composable
fun ProductCard(
    productName: String,
    productType: String,
    oldPrice: String,
    newPrice: String,
    sizes: List<String>,
    productDetails: String,
    productImageRes: Int
) {
    Column(
        modifier = Modifier
            .width(200.dp) // Adjusted width
            .background(Color(0xFF1C1C1C), shape = RoundedCornerShape(16.dp)) // Darker card background
            .padding(12.dp)
    ) {
        // Product image
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(175.dp)
        ) {
            Image(
                painter = painterResource(id = productImageRes),
                contentDescription = "$productName Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Text(
                text = "50% OFF",
                color = Color.Red,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(4.dp)
                    .background(Color.White, shape = RoundedCornerShape(4.dp))
                    .padding(4.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Product details
        Text(text = productName, fontWeight = FontWeight.Bold, color = Color.White, fontSize = 14.sp)
        Text(text = productType, color = Color.Gray, fontSize = 12.sp)

        Spacer(modifier = Modifier.height(4.dp))

        // Price
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = oldPrice,
                color = Color.Gray,
                fontSize = 12.sp,
                textDecoration = TextDecoration.LineThrough
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = newPrice, fontWeight = FontWeight.Bold, color = Color.White, fontSize = 14.sp)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Sizes
        Text(text = "Select Size:", color = Color.White, fontSize = 12.sp)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            sizes.forEach { size ->
                Box(
                    modifier = Modifier
                        .background(Color.White, shape = RoundedCornerShape(4.dp))
                        .padding(4.dp)
                        .clickable { /* Handle size selection */ }
                ) {
                    Text(text = size, color = Color.Black, fontSize = 10.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Product description
        Text(
            text = productDetails,
            color = Color.White,
            fontSize = 10.sp,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Add to cart button
        Button(
            onClick = { /* TODO: Add to cart logic */ },
            colors = ButtonDefaults.buttonColors(Color.Red),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Add to cart", color = Color.White, fontSize = 12.sp)
        }
    }
}


@Composable
fun TrendingProductList(productList: List<Product>, navController: NavController){
    LazyRow()
    {
        items(productList){
            product -> TrendingProductCard(product = product, modifier = Modifier.padding(10.dp), navController = navController)
        }
    }
}

@Composable
fun LogoAndSearchBar() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 25.dp, vertical = 30.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Logo
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo), // Replace with your logo resource
                contentDescription = "Logo",
                modifier = Modifier.size(80.dp), // Adjust size as needed
                contentScale = ContentScale.Fit
            )
        }

        // Search Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(Color.Gray.copy(alpha = 0.2f))
                .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon",
                tint = Color.Gray,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "Search",
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray),
                modifier = Modifier.weight(1f)
            )
        }
    }
}
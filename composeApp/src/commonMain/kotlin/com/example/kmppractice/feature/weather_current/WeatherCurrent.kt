package com.example.kmppractice.feature.weather_current


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Dehaze
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kmppractice.generated.resources.Res
import com.example.kmppractice.generated.resources.house
import org.jetbrains.compose.resources.painterResource

// --- THEME DEFINITION (Theme.kt) ---


// --- UI COMPONENTS ---

// Data class to hold hourly forecast information
data class HourlyForecast(
    val time: String,
    val icon: ImageVector,
    val temperature: Int,
    val precipitation: Int? = null,
    val isNow: Boolean = false,
    val customIcon: @Composable (() -> Unit)? = null
)

// Main screen composable
@Composable
fun WeatherScreen() {
    // In a real app, you would use a proper background image.
    // Here we simulate it with a gradient.
    val starryNightBrush = Brush.verticalGradient(
        colors = listOf(Color(0xFF2C2A4D), Color(0xFF1A1830)),
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(starryNightBrush)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top section with current weather info
            CurrentWeatherInfo()

            // Spacer
            Spacer(modifier = Modifier.height(32.dp))

            // House illustration placeholder
            HouseIllustration()

            // Spacer
            Spacer(modifier = Modifier.weight(1f))

            // Bottom forecast section
            ForecastSection()
        }

        // Custom shaped Bottom Navigation Bar
        CustomBottomAppBar(modifier = Modifier.align(Alignment.BottomCenter))
    }
}

@Composable
fun CurrentWeatherInfo() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(top = 64.dp)
    ) {
        Text(
            text = "Montreal",
            style = MaterialTheme.typography.headlineLarge,
            color = Color.White
        )
        Text(
            text = "19°",
            fontSize = 96.sp,
            fontWeight = FontWeight.W200, // Thin font weight
            color = Color.White
        )
        Text(
            text = "Mostly Clear",
            style = MaterialTheme.typography.titleLarge,
            color = Color.White.copy(alpha = 0.7f)
        )
        Text(
            text = "H:24°   L:18°",
            style = MaterialTheme.typography.titleMedium,
            color = Color.White
        )
    }
}

@Composable
fun HouseIllustration() {
    // In a real app, this would be a more complex SVG, Lottie animation, or PNG.
    // For this preview, we use a placeholder that suggests a house shape.
    Box(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .aspectRatio(1.5f),
        contentAlignment = Alignment.Center
    ) {

        Image(
            painter = painterResource(Res.drawable.house),
            contentDescription = "Demo Image",
            //modifier = Modifier.width(200.dp).height(280.dp),
           // contentScale = ContentScale.Crop,
        )
        // This is a placeholder. You'd replace it with:
        // Image(painter = painterResource(id = R.drawable.house_illustration), contentDescription = "Weather condition illustration")
       // Text(text = "[Illustration of a house goes here]", color = Color.White.copy(alpha = 0.5f), textAlign = TextAlign.Center)
    }
}

@Composable
fun ForecastSection() {
    val hourlyData = listOf(
        HourlyForecast("12 AM", Icons.Default.WaterDrop, 19, 30),
        HourlyForecast("Now", Icons.Default.WaterDrop, 19, isNow = true),
        HourlyForecast("2 AM", Icons.Default.Dehaze, 18, customIcon = { FogIcon() }),
        HourlyForecast("3 AM", Icons.Default.WaterDrop, 19),
        HourlyForecast("4 AM", Icons.Default.WaterDrop, 19),
        HourlyForecast("5 AM", Icons.Default.NightsStay, 18),
    )

    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Hourly Forecast", "Weekly Forecast")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.2f))
            .padding(bottom = 80.dp) // Padding to not overlap with the bottom bar
    ) {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = Color.Transparent,
            contentColor = Color.White,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                    height = 2.dp,
                    color = Color.White
                )
            }
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = { Text(title) }
                )
            }
        }

        // Content for the selected tab
        if (selectedTabIndex == 0) {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(hourlyData) { forecast ->
                    HourlyForecastItem(forecast)
                }
            }
        } else {
            // Placeholder for Weekly Forecast
            Box(modifier = Modifier.fillMaxWidth().height(150.dp), contentAlignment = Alignment.Center) {
                Text("Weekly forecast view here", color = Color.White.copy(alpha = 0.7f))
            }
        }
    }
}


@Composable
fun HourlyForecastItem(forecast: HourlyForecast) {
    val backgroundColor = if (forecast.isNow) {
        MaterialTheme.colorScheme.surface.copy(alpha = 0.2f)
    } else {
        Color.Transparent
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(backgroundColor)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Text(
            text = forecast.time,
            fontWeight = if(forecast.isNow) FontWeight.Bold else FontWeight.Normal,
            color = Color.White
        )

        if (forecast.customIcon != null) {
            forecast.customIcon()
        } else {
            WeatherIconWithRain(forecast)
        }

        Text(
            text = "${forecast.temperature}°",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color.White
        )
    }
}

// A more accurate icon for rain
@Composable
fun WeatherIconWithRain(forecast: HourlyForecast) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.size(40.dp)) {
        Icon(
            imageVector = Icons.Default.NightsStay, // Represents a cloud
            contentDescription = "Weather Icon",
            tint = Color.White.copy(alpha = 0.9f),
            modifier = Modifier.size(30.dp).offset(x = (-4).dp, y = (-4).dp)
        )
        if (forecast.precipitation != null) {
            Column(modifier = Modifier.offset(y = 10.dp)) {
                Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                    Icon(imageVector = Icons.Default.WaterDrop, contentDescription = "Rain drop", tint = Color(0xFF64B5F6), modifier = Modifier.size(10.dp))
                    Icon(imageVector = Icons.Default.WaterDrop, contentDescription = "Rain drop", tint = Color(0xFF64B5F6), modifier = Modifier.size(10.dp).offset(y = 2.dp))
                }
                Text("${forecast.precipitation}%", fontSize = 10.sp, color = Color(0xFF64B5F6))
            }
        }
    }
}

// Custom icon for Fog at 2 AM
@Composable
fun FogIcon() {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.size(40.dp)) {
        Icon(
            imageVector = Icons.Default.NightsStay,
            contentDescription = "Cloud",
            tint = Color.White.copy(alpha = 0.9f),
            modifier = Modifier.size(30.dp).offset(y = (-5).dp)
        )
        Icon(
            imageVector = Icons.Default.Dehaze,
            contentDescription = "Fog",
            tint = Color.White.copy(alpha = 0.8f),
            modifier = Modifier.size(30.dp).offset(y = 5.dp)
        )
    }
}

// Custom Bottom App Bar with a wave shape for the FAB
@Composable
fun CustomBottomAppBar(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(
                    color = MaterialTheme.colorScheme.background.copy(alpha=0.9f),
                    shape = WavyBottomNavShape()
                )
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Default.LocationOn, contentDescription = "Location", tint = Color.White, modifier = Modifier.size(28.dp))
                }
                // Spacer for the central FAB
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.AutoMirrored.Filled.List, contentDescription = "Menu", tint = Color.White, modifier = Modifier.size(28.dp))
                }
            }
        }

        FloatingActionButton(
            onClick = { /*TODO*/ },
            modifier = Modifier.align(Alignment.TopCenter).offset(y = (-28).dp),
            shape = CircleShape,
            containerColor = Color.White,
            contentColor = Color(0xFF2E2A4F)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add", modifier = Modifier.size(32.dp))
        }
    }
}

// Custom shape for the wavy bottom navigation
class WavyBottomNavShape : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): androidx.compose.ui.graphics.Outline {
        return androidx.compose.ui.graphics.Outline.Generic(
            path = Path().apply {
                val fabDiameter = with(density) { 60.dp.toPx() }
                val fabRadius = fabDiameter / 2f
                val cutoutMargin = with(density) { 8.dp.toPx() }
                val cutoutRadius = fabRadius + cutoutMargin

                // Start from the top left
                moveTo(0f, cutoutRadius)

                // Draw the wave
                // This is a simplified wave. A real implementation might use more complex curves.
                lineTo(size.width / 2 - cutoutRadius * 1.5f, cutoutRadius)
                // Curve down
                cubicTo(
                    size.width / 2 - cutoutRadius, cutoutRadius, // control point 1
                    size.width / 2 - cutoutRadius, 0f, // control point 2
                    size.width / 2, 0f // end point
                )
                // Curve up
                cubicTo(
                    size.width / 2 + cutoutRadius, 0f, // control point 1
                    size.width / 2 + cutoutRadius, cutoutRadius, // control point 2
                    size.width / 2 + cutoutRadius * 1.5f, cutoutRadius // end point
                )

                // Line to the top right
                lineTo(size.width, cutoutRadius)
                // Line to bottom right
                lineTo(size.width, size.height)
                // Line to bottom left
                lineTo(0f, size.height)
                // Close the path
                close()
            }
        )
    }
}


// --- PREVIEW ---


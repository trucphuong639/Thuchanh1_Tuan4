package com.example.thuchanh1_tuan4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.*
import kotlin.random.Random
import androidx.compose.foundation.Image
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import java.time.format.TextStyle

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNavigation()
        }
    }
}

enum class Screen {
    Home, List, LargeList, Detail
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val items = remember { mutableStateListOf("The only way to do great work is to love what you do.",
        "The only way to do great work is to love what you do.",
        "The only way to do great work is to love what you do.",
        "The only way to do great work is to love what you do.",
        "The only way to do great work is to love what you do.") }

    NavHost(navController = navController, startDestination = Screen.Home.name) {
        composable(Screen.Home.name) { HomeScreen(navController) }
        composable(Screen.List.name) { ListScreen(navController, items) }
        composable(Screen.LargeList.name) { LargeListScreen(navController, items) }
        composable(Screen.Detail.name + "/{index}") { backStackEntry ->
            val index = backStackEntry.arguments?.getString("index")?.toIntOrNull() ?: 0
            DetailScreen(navController, items, index)
        }
    }
}

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp).background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.img),
            contentDescription = "Jetpack Compose Logo",
            modifier = Modifier.size(300.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Navigation", fontSize = 30.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "is a framework that simplifies the implementation of navigation between different UI components (activities, fragments, or composables) in an app",
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            color = Color.Gray,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(200.dp))
        Button(
            onClick = { navController.navigate(Screen.List.name) },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E88E5)),
            shape = RoundedCornerShape(50),
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Text(text = "PUSH", fontSize = 30.sp, color = Color.White)
        }
    }
}

@Composable
fun ListScreen(navController: NavController, items: SnapshotStateList<String>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 60.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.size(50.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.img_1),
                    contentDescription = "Back",
                    modifier = Modifier.size(40.dp)
                )
            }
            Text(
                text = "LazyColumn",
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E88E5),
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            itemsIndexed(items.take(5)) { index, item ->
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = String.format("%02d | ", index + 1),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        TextField(
                            value = items[index],
                            onValueChange = { items[index] = it },
                            textStyle = androidx.compose.ui.text.TextStyle(fontSize = 20.sp),
                            modifier = Modifier
                                .weight(1f)
                                .background(Color.Transparent),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color(0xFFE3F2FD),
                                unfocusedContainerColor = Color(0xFFE3F2FD),
                                disabledContainerColor = Color(0xFFE3F2FD),
                                cursorColor = Color.Black,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            )
                        )
                        IconButton(onClick = {
                            navController.navigate(Screen.Detail.name + "/$index")
                        }) {
                            Icon(
                                imageVector = Icons.Default.ArrowForward,
                                contentDescription = "Next",
                                tint = Color.White,
                                modifier = Modifier
                                    .background(Color.Black, shape = CircleShape)
                                    .padding(8.dp)
                            )
                        }
                    }
                }
            }
            item {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "1.000.000 | Load all random items",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(onClick = { navController.navigate(Screen.LargeList.name) }) {
                            Icon(
                                imageVector = Icons.Default.ArrowForward,
                                contentDescription = "Next",
                                tint = Color.White,
                                modifier = Modifier
                                    .background(Color.Black, shape = CircleShape)
                                    .padding(8.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LargeListScreen(navController: NavController, items: SnapshotStateList<String>) {
    val largeItems = remember { List(1_000_000) { "Item $it - Random ${Random.nextInt(1000)}" } }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(start = 16.dp, end = 16.dp, top = 60.dp) // Hạ toàn bộ giao diện xuống
    ) {
        // Thanh tiêu đề với nút Back
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Image(
                    painter = painterResource(R.drawable.img_1),
                    contentDescription = "Back",
                    modifier = Modifier.size(50.dp)
                )
            }
            Text(
                text = "Large List",
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E88E5),
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
        }

        // Card chứa danh sách
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)), // Màu xám nhạt
            modifier = Modifier
                .fillMaxWidth()
                .height(550.dp)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                items(largeItems.size) { index ->
                    Text(
                        text = largeItems[index],
                        fontSize = 20.sp,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(100.dp))

        // Nút BACK TO ROOT
        Button(
            onClick = { navController.navigate(Screen.Home.name) },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E88E5)),
            shape = RoundedCornerShape(50),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = "BACK TO ROOT", fontSize = 30.sp, color = Color.White)
        }
    }
}

@Composable
fun DetailScreen(navController: NavController, items: SnapshotStateList<String>, index: Int) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 60.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.size(50.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.img_1),
                    contentDescription = "Back",
                    modifier = Modifier.size(40.dp)
                )
            }
            Text(
                text = "Detail",
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E88E5),
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
            text = "\"${items[index]}\"",
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .width(300.dp)
                .padding(start = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF64B5F6)),
                modifier = Modifier
                    .width(300.dp)
                    .padding(vertical = 16.dp)
            ) {
                Text(
                    text = "\"${items[index]}\"",
                    fontSize = 50.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(100.dp))

        Button(
            onClick = { navController.navigate(Screen.Home.name) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E88E5))
        ) {
            Text(text = "BACK TO ROOT", fontSize = 30.sp, color = Color.White)
        }
    }
}


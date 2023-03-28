package com.example.jetpackanimationspresentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpackanimationspresentation.ui.theme.Green
import com.example.jetpackanimationspresentation.ui.theme.Orange
import kotlinx.coroutines.launch

enum class Tabs {
    Fruits, Vegetables
}

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val lazyListState = rememberLazyListState()
            val fabVisibility by remember {
                derivedStateOf {
                    lazyListState.firstVisibleItemIndex > 2
                }
            }
            val scope = rememberCoroutineScope()
            var tabSelected by remember { mutableStateOf(Tabs.Fruits) }
            var expanded by remember { mutableStateOf<FruitVegetableModel?>(null) }


            // TODO 1: Add animate*AsState

            // TODO: Without animation
            val backgroundColor = if (tabSelected == Tabs.Fruits) Color.Yellow else Green


            // TODO: With animation

//            val backgroundColor by animateColorAsState(
//                targetValue = if (tabSelected == Tabs.Fruits) Color.Yellow else Green,
//                animationSpec = tween(
//                    durationMillis = 2000,
//                    easing = LinearOutSlowInEasing
//                )
//            )


            // TODO 2: Add updateTransition Animation here

//            val transition = updateTransition(targetState = tabSelected, label = "")
//            val backgroundColor by transition.animateColor(label = "") { page ->
//                if (page == Tabs.Fruits) Color.Yellow else Green
//            }
//            val scaleFruits by transition.animateFloat(label = "") { page ->
//                if (page == Tabs.Fruits) 1f else 0.60f
//            }
//            val scaleVegetables by transition.animateFloat(label = "") { page ->
//                if (page == Tabs.Vegetables) 1f else 0.60f
//            }



            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(if (tabSelected == Tabs.Fruits) Color.Red else backgroundColor)
                ) {
                    TopPageBar(
                        modifier = Modifier.weight(0.5f),
                        tabIcon = R.drawable.fruits,
                        tabTitle = "Fruits",
//                        scaleSize = scaleFruits
                    ) {
                        tabSelected = Tabs.Fruits
                    }

                    TopPageBar(
                        modifier = Modifier.weight(0.5f),
                        tabIcon = R.drawable.vegetables,
                        tabTitle = "Vegetables",
//                        scaleSize = scaleVegetables
                    ) {
                        tabSelected = Tabs.Vegetables

                    }
                }
                Divider(
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.Black
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = if (tabSelected == Tabs.Fruits) {
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Red,
                                        backgroundColor,
                                        Orange
                                    )
                                )
                            } else {
                                Brush.verticalGradient(
                                    colors = listOf(
                                        backgroundColor,
                                        Orange,
                                        Color.Red
                                    )
                                )
                            }
                        )
                ) {
                    // TODO 4: AnimatedContent example
//                    AnimatedContent(
//                        targetState = tabSelected,
//                        transitionSpec = {
//                            slideIntoContainer(
//                                animationSpec = tween(300, easing = LinearEasing),
//                                towards = AnimatedContentScope.SlideDirection.Up
//                            ).with(
//                                slideOutOfContainer(
//                                    animationSpec = tween(300, easing = LinearEasing),
//                                    towards = AnimatedContentScope.SlideDirection.Down
//                                )
//                            )
//                        }
//                    ) {
                        // TODO: The part without the animation of AnimatedContent

                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 16.dp),
                            contentPadding = PaddingValues(
                                horizontal = 12.dp,
                                vertical = 12.dp
                            ),
                            verticalArrangement = Arrangement.spacedBy(24.dp),
                            state = lazyListState
                        ) {
                            when (tabSelected) {
                                Tabs.Fruits -> {
                                    items(items = fruits()) { fruit ->
                                        CustomFruitVegetable(
                                            onClick = {
                                                expanded = if (expanded == fruit) null else fruit
                                            },
                                            fruitVegetable = fruit,
                                            expanded = expanded == fruit
                                        )
                                    }
                                }
                                else -> {
                                    items(items = vegetables()) { vegetable ->
                                        CustomFruitVegetable(
                                            onClick = {
                                                expanded =
                                                    if (expanded == vegetable) null else vegetable
                                            },
                                            fruitVegetable = vegetable,
                                            expanded = expanded == vegetable
                                        )
                                    }
                                }
                            }
                        }
//                    }

                    // TODO 3: AnimatedVisibility example
                    Column(modifier = Modifier.align(Alignment.BottomEnd)) {
//                        AnimatedVisibility(
//                            visible = fabVisibility,
//                            enter = slideInHorizontally(
//                                initialOffsetX = {
//                                    it / 2
//                                }
//                            ),
//                            exit = slideOutHorizontally(
//                                targetOffsetX = {
//                                    it / 2
//                                }
//                            )
//                        ) {
                            // TODO: The part without the animation of AnimatedVisibility
                        if(fabVisibility){
                            MyFloatingActionButton(
                                tabSelected = tabSelected
                            ) {
                                scope.launch {
                                    lazyListState.animateScrollToItem(0)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun MyFloatingActionButton(
    tabSelected: Tabs,
    onClick: () -> Unit
) {
    FloatingActionButton(
        modifier = Modifier.padding(
            end = 16.dp, bottom = 32.dp
        ),
        backgroundColor = if (tabSelected == Tabs.Fruits) Orange else Color.Red,
        onClick = onClick
    ) {
        Icon(
            imageVector = Icons.Default.KeyboardArrowUp, contentDescription = null
        )
    }
}

@Composable
fun TopPageBar(
    modifier: Modifier,
    tabIcon: Int,
    tabTitle: String,
    scaleSize: Float = 1f,
    onTabClicked: () -> Unit
) {
    Row(
        modifier = modifier
            .scale(scaleSize)
            .padding(top = 32.dp)
            .clickable { onTabClicked() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = tabIcon), contentDescription = null
        )
        Spacer(modifier = Modifier.size(5.dp))
        Text(
            text = tabTitle,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun CustomFruitVegetable(
    fruitVegetable: FruitVegetableModel,
    expanded: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(16.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        // TODO 6: rememberInfiniteTransition
//        val infiniteTransition = rememberInfiniteTransition()
//        val scaleInfinite by infiniteTransition.animateFloat(
//            initialValue = 1f,
//            targetValue = 0.65f,
//            animationSpec = infiniteRepeatable(
//                animation = tween(
//                    durationMillis = 2000,
//                    easing = LinearOutSlowInEasing
//                ),
//                repeatMode = RepeatMode.Restart
//            )
//        )


        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Image(
                modifier = Modifier
                    .size(50.dp),
//                    .scale(scaleInfinite),
                painter = painterResource(id = fruitVegetable.image),
                contentDescription = null
            )
            Text(
                text = fruitVegetable.name, fontSize = 24.sp, fontWeight = FontWeight.Bold
            )
        }

        //TODO 5: animateContentSize() example

        Text(
            //TODO: The part without the animation of animateContentSize()

            modifier = Modifier
//                .animateContentSize(
//                    animationSpec = spring(
//                        dampingRatio = Spring.DampingRatioLowBouncy,
//                        stiffness = Spring.StiffnessLow
//                    )
//                )
                .clickable {
                    onClick()
                },
            text = fruitVegetable.description,
            maxLines = if (!expanded) 2 else fruitVegetable.description.length
        )
    }
}
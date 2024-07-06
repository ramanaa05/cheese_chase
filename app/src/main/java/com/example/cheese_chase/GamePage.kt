package com.example.cheese_chase

import android.media.MediaPlayer
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.delay

@Composable
fun GamePage(navController: NavController){
    val gameViewModel: MainViewModel = viewModel()
    val viewState by gameViewModel.infoState
    val context = LocalContext.current
    val preferencesManager = PreferencesManager(context)
    val digital = FontFamily(Font(R.font.digital))

    var bullets by remember { mutableStateOf(0) }
    var movingBullet by remember { mutableStateOf(0) }
    var lives by remember { mutableStateOf(2) }
    var open by remember { mutableStateOf(false) }
    var tempSpeed by remember { mutableStateOf(0f) }
    val powerupPage by animateFloatAsState(
        targetValue = if (open) 1f else 0f
    )
    var openTrapPage by remember { mutableStateOf(false) }
    val TrapPage by animateFloatAsState(
        targetValue = if (openTrapPage) 1f else 0f
    )
    var showBomb by remember{
        mutableStateOf(false)
    }
    var bombs by remember {
        mutableStateOf(0)
    }
    var powerTimer by remember { mutableStateOf(0) }
    var stopTom by remember {
        mutableStateOf(false)
    }
    var pause by remember {
        mutableStateOf(false)
    }
    var granted by remember {
        mutableStateOf(true)
    }
    var grantedPower by remember {
        mutableStateOf(-1)
    }
    var trap by remember {
        mutableStateOf(0)
    }
    var grantedTrap by remember {
        mutableStateOf(-1)
    }
    var jump by remember{
        mutableStateOf(false)
    }
    var jumpDetect by remember{
        mutableStateOf(false)
    }
    var speed by remember{
        mutableStateOf(10f)
    }
    var shieldsUp by remember{
        mutableStateOf(false)
    }
    var endgame by remember{
        mutableStateOf(0)
    }
    var column by remember{
        mutableStateOf(2)
    }
    var columnTom by remember {
        mutableStateOf(2)
    }
    var temp = viewState.obstacleLimit.obstacleLimit
    var tomState by remember {
        mutableStateOf(2 - viewState.obstacleLimit.obstacleLimit)
    }
    var collided by remember{
        mutableStateOf(-1)
    }
    val jerryx by animateIntAsState(
        targetValue = if (column == 1) -410 else if (column == 2) 325 else 1060
    )
    val gunx by animateFloatAsState(
        targetValue = if (column == 1) 100f else if (column == 2) 475f else 850f
    )
    var jerryy by remember{
        mutableStateOf(2150)
    }
    val tomx by animateIntAsState(
        targetValue = if (columnTom == 1) -30 else if (columnTom == 2) 305 else 650
    )
    var incr by remember {
        mutableStateOf(0f)
    }
    var collideIncr by remember {
        mutableStateOf(0f)
    }
    val tomy by animateFloatAsState(
        targetValue = if (tomState == 2) 1600f+incr else if (tomState == 1) 1800f+incr else 2000f+incr
    )
    var obstaclesx = remember{ mutableStateListOf(50f) }
    var obstaclesy = remember { mutableStateListOf(0f) }
    var type = remember{ mutableStateListOf(-2) }
    val bulletx = remember{ mutableStateListOf<Float>() }
    val bullety = remember{ mutableStateListOf<Float>() }

    var moving by remember {
        mutableStateOf(0)
    }
    var distance by remember {
        mutableStateOf(0)
    }
    val transition = rememberInfiniteTransition()
    val running by transition.animateFloat(
        initialValue = 0f,
        targetValue = 8f,
        animationSpec = infiniteRepeatable(
            animation = tween(100),
            repeatMode = RepeatMode.Reverse
        ),
        label = "running"
    )
    val jerry = ImageBitmap.imageResource(id = R.drawable.jerry)
    val tom = ImageBitmap.imageResource(id = R.drawable.tom)
    val box = ImageBitmap.imageResource(id = R.drawable.box)
    val cheese = ImageBitmap.imageResource(id = R.drawable.cheese)
    val bullet = ImageBitmap.imageResource(id = R.drawable.bullet)
    val guns = listOf(
        ImageBitmap.imageResource(id = R.drawable.machine_gun),
        ImageBitmap.imageResource(id = R.drawable.rifle)
    )
    val powerUps = listOf(
        ImageBitmap.imageResource(id = R.drawable.kennel),
        ImageBitmap.imageResource(id = R.drawable.big_heart)
    )
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xffb6b476))
    ){
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures { offset ->
                        println(offset)
                        if ((offset.x < 540f) and (column > 1)) {
                            column--
                        } else if ((offset.x > 540f) and (column < 3)) {
                            column++
                        }
                    }
                }
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        val (x, y) = dragAmount
                        when {
                            y < 0 -> {
                                jumpDetect = !jumpDetect
                                jump = true
                            }
                        }
                    }
                }
        ) {
            drawLine(
                color = Color.Black,
                start = Offset(x = 365f, y = 0f),
                end = Offset(x = 365f, y = 2400f),
                strokeWidth = 10f
            )
            drawLine(
                color = Color.Black,
                start = Offset(x = 725f, y = 0f),
                end = Offset(x = 725f, y = 2400f),
                strokeWidth = 10f
            )
            for (i in 0..<obstaclesy.size){
                var x = mutableStateOf(obstaclesx[i])
                var y = mutableStateOf(obstaclesy[i])
                if (type[i] == -2){
                    drawRect(
                        color = Color.Black,
                        topLeft = Offset(x = x.value, y = y.value),
                        size = Size(250f, 200f)
                    )
                }
                else if (type[i] == -1){
                    drawImage(
                        image = box,
                        topLeft = Offset(x = x.value, y = y.value)
                    )
                }
                else if (type[i] == -3){
                    drawImage(
                        image = cheese,
                        topLeft = Offset(x = x.value, y = y.value)
                    )
                }
                else{
                    drawImage(
                        image = powerUps[type[i]],
                        topLeft = Offset(x = x.value, y = y.value)
                    )
                }
            }
            if (!jump){
                drawImage(
                    image = guns[gun.value],
                    topLeft = Offset(x = gunx-150f, y = 1600f)
                )
                scale(scale = 0.5f){
                    drawImage(
                        image = jerry,
                        // -410    325    1060
                        topLeft = Offset(x = jerryx.toFloat(), y = jerryy.toFloat()+running),
                    )
                }
            }
            if (!stopTom){
                scale(scale = 1.1f){
                    drawImage(
                        image = tom,
                        // -410    325    1060
                        topLeft = Offset(x = tomx.toFloat(), y = tomy +running),
                    )
                }
            }
            for (i in 0..<bullets){
                drawImage(
                    image = bullet,
                    topLeft = Offset(x = bulletx[i], y = bullety[i])
                )
            }
        }
        if (mode.value == 0){
            Canvas(
                modifier = Modifier
                    .offset(x = -(20).dp, y = 50.dp)
                    .size(70.dp)
                    .align(Alignment.TopEnd)
            ) {
                drawArc(
                    color = Color.Gray,
                    startAngle = -215f,
                    sweepAngle = 250f,
                    useCenter = false,
                    size = size,
                    style = Stroke(width = 10f)
                )
                drawArc(
                    color = Color.White,
                    startAngle = -215f,
                    sweepAngle = 250f * (distance.toFloat()/ totalDistance.value),
                    useCenter = false,
                    size = size,
                    style = Stroke(width = 10f)
                )
            }
        }
        Text(
            modifier = Modifier
                .offset(x = (-45).dp, y = 70.dp)
                .align(Alignment.TopEnd),
            text = "${distance/50}",
            fontSize = 30.sp,
            color = Color.White,
            fontFamily = digital
        )

        //Bomb button
        if (showBomb){
            Box(
                modifier = Modifier
                    .offset(x = 20.dp, y = (-40).dp)
                    .align(Alignment.BottomStart)
            ){
                Button(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(60.dp),
                    shape = CircleShape,
                    onClick = {
                        bombs -= 1
                        if(bombs == 0) showBomb = false
                        obstaclesx.clear()
                        obstaclesy.clear()
                        type.clear()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                ) {
                }
                Image(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(40.dp),
                    painter = painterResource(id = R.drawable.bomb),
                    contentDescription = "bomb"
                )
                Text(
                    modifier = Modifier
                        .offset(x = 20.dp, y = 30.dp)
                        .align(Alignment.Center),
                    text = "$bombs",
                    fontSize = 25.sp,
                    color = Color.White
                )
            }
        }
        Box(
            modifier = Modifier
                .offset(x = 90.dp, y = (-40).dp)
                .align(Alignment.BottomStart)
        ){
            Button(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(60.dp),
                shape = CircleShape,
                onClick = {
                    val cheeseAmount = preferencesManager.getInteger("cheese", 0)
                    if (cheeseAmount > 0){
                        bullets += 1
                        preferencesManager.saveInteger("cheese", cheeseAmount-1)
                        bullety.add(1700f)
                        if (column == 1) bulletx.add(100f) else if (column == 2) bulletx.add(475f) else bulletx.add(850f)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
            ) {
            }
            Image(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(50.dp),
                painter = painterResource(id = R.drawable.target),
                contentDescription = "target"
            )
        }

        //power up timer
        if ((grantedPower == 0)){
            Canvas(
                modifier = Modifier
                    .offset(x = -(120).dp, y = 50.dp)
                    .size(70.dp)
                    .align(Alignment.TopEnd)
            ) {
                drawArc(
                    color = Color.Gray,
                    startAngle = -215f,
                    sweepAngle = 250f,
                    useCenter = false,
                    size = size,
                    style = Stroke(width = 10f)
                )
                drawArc(
                    color = Color.White,
                    startAngle = -215f,
                    sweepAngle = 250f * (powerTimer.toFloat()/ 5f),
                    useCenter = false,
                    size = size,
                    style = Stroke(width = 10f)
                )
            }
            Image(
                modifier = Modifier
                    .offset(x = (-135).dp, y = 60.dp)
                    .align(Alignment.TopEnd)
                    .size(40.dp),
                painter = painterResource(id = globalPowerUps[grantedPower]),
                contentDescription = "power up"
            )
        }
        if (grantedTrap in  0..2){
            Canvas(
                modifier = Modifier
                    .offset(x = -(220).dp, y = 50.dp)
                    .size(70.dp)
                    .align(Alignment.TopEnd)
            ) {
                drawArc(
                    color = Color.Gray,
                    startAngle = -215f,
                    sweepAngle = 250f,
                    useCenter = false,
                    size = size,
                    style = Stroke(width = 10f)
                )
                drawArc(
                    color = Color.White,
                    startAngle = -215f,
                    sweepAngle = 250f * (powerTimer.toFloat()/ 5f),
                    useCenter = false,
                    size = size,
                    style = Stroke(width = 10f)
                )
            }
            Image(
                modifier = Modifier
                    .offset(x = (-235).dp, y = 60.dp)
                    .align(Alignment.TopEnd)
                    .size(40.dp),
                painter = painterResource(id = globalTraps[grantedTrap]),
                contentDescription = "power up"
            )
        }

        Box(
            modifier = Modifier
                .offset(x = 20.dp, y = 50.dp)
                .align(Alignment.TopStart)
        ){
            val size1 by animateIntAsState(
                targetValue = if (tomState <= 1) 40 else 0,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioHighBouncy,
                    stiffness = Spring.StiffnessMedium
                )
            )
            val size2 by animateIntAsState(
                targetValue = if (tomState <= 0) 40 else 0,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioHighBouncy,
                    stiffness = Spring.StiffnessMedium
                )
            )
            for (i in 0..<lives){
                Image(
                    modifier = Modifier
                        .offset(x = (50 * i).dp)
                        .size(size1.dp),
                    painter = painterResource(id = R.drawable.heart),
                    contentDescription = "heart"
                )
            }
        }
        Box(
            modifier = Modifier
                .offset(x = 20.dp, y = 85.dp)
                .background(Color.Transparent)
                .align(Alignment.TopStart)
        ){
            Image(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(40.dp),
                painter = painterResource(id = R.drawable.cheese),
                contentDescription = "cheese"
            )
            Text(
                modifier = Modifier
                    .offset(x = 50.dp)
                    .align(Alignment.Center)
                    .padding(10.dp),
                text = "${preferencesManager.getInteger("cheese", 0)}",
                fontSize = 40.sp,
                color = Color.White,
                fontFamily = digital
            )
        }

        var remove= remember{ mutableStateListOf<Int>() }
        LaunchedEffect(key1 = moving, key2 = pause) {
            if (tomState > 1){
                pause = true
                score.value = distance/50
                pageFlag.value = 4
            }
            if(mode.value == 0){
                if(distance/50 == (totalDistance.value/50).toInt() -3){
                    endgame = 1
                    pause = true
                }
            }

            if (!jump){
                for (i in 0..<obstaclesy.size){
                    if (column == 1){
                        if((179.98926f in obstaclesx[i]..obstaclesx[i]+250f) and (1747.9248f in obstaclesy[i]..obstaclesy[i]+200f)){
                            if(i != collided){
                                if(type[i] == -2){
                                    collided = i
                                    if (!stopTom and !shieldsUp){
                                        if (grantedTrap == 1){
                                            tomState = 2
                                            lives = 0
                                        }
                                        else{
                                            tomState += 1
                                            lives -= 1
                                        }
                                    }
                                }
                                else if (type[i] >= 0){
                                    pause = true
                                    collided = i
                                    granted = !granted
                                    granted = !granted
                                    grantedPower = type[i]
                                    remove.add(i)
                                }
                                else if (type[i] == -3){
                                    collided = i
                                    remove.add(i)
                                    val cheeseTemp = preferencesManager.getInteger("cheese", 0)
                                    preferencesManager.saveInteger("cheese", cheeseTemp+1)
                                }
                                else{
                                    pause = true
                                    remove.add(i)
                                    collided = i
                                    trap += 1
                                }
                            }
                        }
                    }
                    else if( column == 2){
                        if((539.9668f in obstaclesx[i]..obstaclesx[i]+250f) and (1747.9248f in obstaclesy[i]..obstaclesy[i]+200f)){
                            if(i != collided){
                                if(type[i] == -2){
                                    collided = i
                                    if (!stopTom and !shieldsUp){
                                        if (grantedTrap == 1){
                                            tomState = 2
                                            lives = 0
                                        }
                                        else{
                                            tomState += 1
                                            lives -= 1
                                        }
                                    }
                                }
                                else if (type[i] >= 0){
                                    pause = true
                                    remove.add(i)
                                    collided = i
                                    granted = !granted
                                    grantedPower = type[i]
                                }
                                else if (type[i] == -3){
                                    collided = i
                                    remove.add(i)
                                    val cheeseTemp = preferencesManager.getInteger("cheese", 0)
                                    preferencesManager.saveInteger("cheese", cheeseTemp+1)
                                }
                                else{
                                    pause = true
                                    collided = i
                                    remove.add(i)
                                    trap += 1
                                }
                            }
                        }
                    }
                    else{
                        if((916.95215f in obstaclesx[i]..obstaclesx[i]+250f) and (1747.9248f in obstaclesy[i]..obstaclesy[i]+200f)){
                            if(i != collided){
                                if(type[i] == -2){
                                    collided = i
                                    if (!stopTom and !shieldsUp){
                                        if (grantedTrap == 1){
                                            tomState = 2
                                            lives = 0
                                        }
                                        else{
                                            tomState += 1
                                            lives -= 1
                                        }
                                    }
                                }
                                else if (type[i] >= 0){
                                    pause = true
                                    collided = i
                                    remove.add(i)
                                    granted = !granted
                                    grantedPower = type[i]
                                }
                                else if (type[i] == -3){
                                    collided = i
                                    remove.add(i)
                                    val cheeseTemp = preferencesManager.getInteger("cheese", 0)
                                    preferencesManager.saveInteger("cheese", cheeseTemp+1)
                                }
                                else{
                                    pause = true
                                    collided = i
                                    remove.add(i)
                                    trap += 1
                                }
                            }
                        }
                    }
                }
            }
            for (i in 0..<obstaclesy.size){
                obstaclesy[i] += speed
                if (obstaclesy[i] > 2400f){
                    remove.add(i)
                }
            }
            for (i in remove){
                if(i == collided){
                    collided = -1
                }
                else if(i < collided){
                    collided -= 1
                }
                obstaclesx.removeAt(i)
                obstaclesy.removeAt(i)
                type.removeAt(i)
            }
            remove.clear()

            var decider = (0..40).random()
            if (decider == 0){
                var typeDecider = (0..50).random()
                var add = true
                var path = 2
                var c1 = true
                var c2 = true
                var temp: Pair<Float, Float>
                var first = obstaclesx.count { it == 50f }
                var second = obstaclesx.count { it == 410f }
                var third = obstaclesx.count { it == 770f }
                do {
                    temp = obstacleGenerator()
                    if ((first > 1) and (second > 1) and (third > 1)){
                        break
                    }
                }while (((first > 1) and (temp.first == 50f)) or ((second > 1)and (temp.first == 410f)) or ((third > 1) and (temp.first == 770f)))

                for(i in 0..<obstaclesy.size){
                    if (obstaclesx[i] == temp.first){
                        if ((temp.second + 250f) > obstaclesy[i]){
                            add = false
                        }
                    }
                    else{
                        if (((temp.second + 600f) > obstaclesy[i]) and c1){
                            path -= 1
                            c1 = false
                        }
                        else if (((temp.second + 600f) > obstaclesy[i]) and c2){
                            path -= 1
                            c2 = false
                        }
                    }
                }
                if ((add) and (path != 0)){
                    obstaclesx.add(temp.first)
                    obstaclesy.add(temp.second)
                    if(typeDecider == 0){
                        type.add(-1)
                    }
                    else if (typeDecider == 1){
                        val addType = powerUps.indices.random()
                        type.add(addType)
                    }
                    else if (typeDecider == 2){
                        type.add(-3)
                    }
                    else{
                        type.add(-2)
                    }
                }
            }
            if (obstaclesy.size < 2){
                var temp = obstacleGenerator()
                obstaclesx.add(temp.first)
                obstaclesy.add(temp.second)
                type.add(-2)
            }
            if (!pause){
                moving = (moving + 1) % 2
                distance += 1
                speed += 0.001f
            }
        }
        LaunchedEffect(key1 = column) {
            var delay = if (tomState == 0) 350*(20/speed) else 100*(20/speed)
            if (endgame == 1){
                delay += 250
            }
            delay(delay.toLong())
            columnTom = column
        }
        LaunchedEffect(key1 = endgame, key2 = distance) {
            if((endgame == 1) and pause){
                jerryy -= 20
                collideIncr -= 10f
                incr -= 9.08f
                distance += 1
                if (tomState > 1){
                    pause = true
                    endgame = 0
                    score.value = distance/50
                    pageFlag.value = 1
                }
                if(jerryy <= -1000){
                    endgame = 0
                    score.value = distance/50
                    pageFlag.value = 2
                }
                if (!jump){
                    for (i in 0..<obstaclesy.size){
                        if (column == 1){
                            if((179.98926f in obstaclesx[i]..obstaclesx[i]+250f) and (1747.9248f in obstaclesy[i]..obstaclesy[i]+200f)){
                                if(i != collided){
                                    if(type[i] == -2){
                                        collided = i
                                        if (!stopTom and !shieldsUp){
                                            if (grantedTrap == 1){
                                                tomState = 2
                                                lives = 0
                                            }
                                            else{
                                                tomState += 1
                                                lives -= 1
                                            }
                                        }
                                    }
                                    else if (type[i] >= 0){
                                        collided = i
                                        granted = !granted
                                        grantedPower = type[i]
                                    }
                                    else{
                                        collided = i
                                        trap += 1
                                    }
                                }
                            }
                        }
                        else if( column == 2){
                            if((539.9668f in obstaclesx[i]..obstaclesx[i]+250f) and (1747.9248f in obstaclesy[i]..obstaclesy[i]+200f)){
                                if(i != collided){
                                    if(type[i] == -2){
                                        collided = i
                                        if (!stopTom and !shieldsUp){
                                            if (grantedTrap == 1){
                                                tomState = 2
                                                lives = 0
                                            }
                                            else{
                                                tomState += 1
                                                lives -= 1
                                            }
                                        }
                                    }
                                    else if (type[i] >= 0){
                                        collided = i
                                        granted = !granted
                                        grantedPower = type[i]
                                    }
                                    else{
                                        collided = i
                                        trap += 1
                                    }
                                }
                            }
                        }
                        else{
                            if((916.95215f in obstaclesx[i]..obstaclesx[i]+250f) and (1747.9248f in obstaclesy[i]..obstaclesy[i]+200f)){
                                if(i != collided){
                                    if(type[i] == -2){
                                        collided = i
                                        if (!stopTom and !shieldsUp){
                                            if (grantedTrap == 1){
                                                tomState = 2
                                                lives = 0
                                            }
                                            else{
                                                tomState += 1
                                                lives -= 1
                                            }
                                        }
                                    }
                                    else if (type[i] >= 0){
                                        collided = i
                                        granted = !granted
                                        grantedPower = type[i]
                                    }
                                    else{
                                        collided = i
                                        trap += 1
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        LaunchedEffect(key1 = jumpDetect) {
            delay((600*10/speed).toLong())
            jump = false
        }
        LaunchedEffect(key1 = granted ) {
            if (grantedPower != -1){
                powerTimer = 0
                open = true
                delay(3500)
                open = false
                delay(1000)
                pause = false
                if (grantedPower == 1){
                    stopTom = false
                    lives += 1
                    tomState -= 1
                }
                else if (grantedPower == 0){
                    stopTom = true
                    for (i in 0..4){
                        delay(1000)
                        powerTimer += 1
                    }
                    stopTom = false
                    powerTimer = 0
                }
                grantedPower = -1
            }
        }
        LaunchedEffect(key1 = trap) {
            if (trap > 0){
                powerTimer = 0
                shieldsUp = false
                grantedTrap = globalTraps.indices.random()
                openTrapPage = true
                delay(3500)
                openTrapPage = false
                delay(1000)
                pause = false
                if (grantedTrap == 0){
                    if (tempSpeed == 0f) tempSpeed = speed
                    speed = 30f
                    for (i in 0..4){
                        delay(1000)
                        powerTimer += 1
                    }
                    speed = tempSpeed
                    tempSpeed = 0f
                    powerTimer = 0
                }
                else if (grantedTrap == 1){
                    for (i in 0..4){
                        delay(1000)
                        powerTimer += 1
                    }
                    powerTimer = 0
                }
                else if (grantedTrap == 2){
                    shieldsUp = true
                    for (i in 0..4){
                        delay(1000)
                        powerTimer += 1
                    }
                    shieldsUp = false
                    powerTimer = 0
                }
                else if (grantedTrap == 3){
                    showBomb = true
                    bombs += 1
                }
                grantedTrap = -1
            }
        }
        var removeBullets = mutableListOf<Int>()
        LaunchedEffect(key1 = bullets, key2 = movingBullet) {
            if (bullets > 0){
                for (i in 0..<bullets) {
                    bullety[i] -= 30f
                    if (bullety[i] < 0f) {
                        removeBullets.add(i)
                    }
                    for (j in 0..<obstaclesy.size){
                        if (type[j] == -2){
                            if ((bullety[i] in obstaclesy[j]..obstaclesy[j]+200f) and (bulletx[i] in obstaclesx[j]..obstaclesx[j]+250f)){
                                if (i !in removeBullets){
                                    removeBullets.add(i)
                                    remove.add(j)
                                }
                            }
                        }
                    }
                }
                for (i in removeBullets){
                    bulletx.removeAt(i)
                    bullety.removeAt(i)
                    bullets -= 1
                }
                movingBullet = (movingBullet + 1)%2
            }
        }

        LaunchedEffect(key1 = temp) {
            tomState = 2 - temp
            lives = temp
        }

        if (revived.value){
            lives = 2
            tomState = 0
            pause = false
            obstaclesx.clear()
            obstaclesy.clear()
            type.clear()
            revived.value = false
        }
        if (reset.value){
            obstaclesx.clear()
            obstaclesy.clear()
            type.clear()
            column = 2
            columnTom = 2
            tomState = 2 - viewState.obstacleLimit.obstacleLimit
            collided = -1
            pause = false
            reset.value = false
            distance = 0
            jerryy = 2150
            collideIncr = 0f
            incr = 0f
            endgame = 0
            speed = 10f
            lives = viewState.obstacleLimit.obstacleLimit
            powerTimer = 0
            grantedPower = -1
            grantedTrap = -1
            showBomb = false
            bombs = 0
            stopTom = false
            shieldsUp = false
        }
        if (toHome.value){
            navController.navigate(Screen.MainPage.route)
            toHome.value = false
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
        ){
            Text(text = "$temp")
            Text(
                modifier = Modifier.align(Alignment.TopEnd),
                text = "${viewState.obstacleLimit.obstacleLimit}"
            )
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize(powerupPage)
            .background(Color.White.copy(alpha = 0.5f))
            .clickable { }
    ){
        if (grantedPower != -1){
            Image(
                modifier = Modifier
                    .align(Alignment.Center),
                painter = painterResource(id = globalPowerUps[grantedPower]),
                contentDescription = "power up"
            )
        }
        Text(
            modifier = Modifier
                .offset(y = 100.dp)
                .align(Alignment.Center)
                .padding(20.dp),
            text = if (grantedPower == 0) {
                "Spike caught jerry! For the next 5 seconds, tom won't be chasing you"
            } else if(grantedPower == 1) {
                "You have been granted an extra life!!"
            } else {
                ""
            },
            fontSize = 30.sp
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize(TrapPage)
            .background(Color.White.copy(alpha = 0.5f))
            .clickable { }
    ){
        if (grantedTrap != -1){
            Image(
                modifier = Modifier
                    .align(Alignment.Center),
                painter = painterResource(id = globalTraps[grantedTrap]),
                contentDescription = "power up"
            )
        }
    }
}
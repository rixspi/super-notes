/**
 * Created by https://github.com/fgiris
 * https://github.com/fgiris/ComposeGalaxy
 */
package com.rixspi.common.presentation.ui.galaxy

import androidx.compose.animation.core.DurationBasedAnimationSpec
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import kotlin.math.cos
import kotlin.math.hypot
import kotlin.math.sin

data class PlanetData(
    val numberOfPlanet: Int = DefaultNumberOfPlanet,
    val maxPlanetRadius: Float = DefaultMaxPlanetRadius,
    val maxPlanetAlpha: Float = DefaultMaxPlanetAlpha,
    val planetColors: List<Color> = DefaultPlanetColors,
    val planetAnimationSpec: DurationBasedAnimationSpec<Float> = DefaultTweenSpec
)

private const val DefaultMaxPlanetRadius = 5f
private const val DefaultNumberOfPlanet = 10
private const val DefaultMaxPlanetAlpha = 0.5f

private val DefaultPlanetColors = listOf(
    Color.LightGray,
    Color.Gray,
    Color.DarkGray
)

private val DefaultTweenSpec = TweenSpec<Float>(
    durationMillis = 30000,
    easing = LinearEasing
)

data class StarData(
    val numberOfStars: Int = DefaultNumberOfStars,
    val starColors: List<Color> = DefaultStarColors,
    val starShiningAnimationSpec: DurationBasedAnimationSpec<Float> = DefaultStarShiningAnimationSpec
)

private const val DefaultNumberOfStars = 500
private val DefaultStarShiningAnimationSpec = TweenSpec<Float>(
    durationMillis = 3000,
    easing = FastOutSlowInEasing
)

private val DefaultStarColors = listOf(
    Color.White,
    Color.Gray,
    Color.DarkGray
)

@Composable
fun Galaxy(
    modifier: Modifier = Modifier,
    planetData: PlanetData = PlanetData(),
    starData: StarData = StarData()
) {
    val planetRandomizers = remember {
        generateRandomPlanetDataset(
            planetData = planetData
        )
    }

    val starRandomizers = remember {
        generateRandomStarDataset(
            starData = starData
        )
    }

    val infiniteTransition = rememberInfiniteTransition()

    val planetShiftAnimation = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = planetData.planetAnimationSpec,
            repeatMode = RepeatMode.Reverse
        )
    )

    val starAlphaAnimation = infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = starData.starShiningAnimationSpec,
            repeatMode = RepeatMode.Reverse
        )
    )

    Canvas(
        modifier = modifier,
        onDraw = {
            drawGalaxy(
                planetRandomizers = planetRandomizers,
                planetShiftAnimationValue = planetShiftAnimation.value,
                starRandomizers = starRandomizers,
                starAlphaAnimationValue = starAlphaAnimation.value
            )
        }
    )
}

private fun DrawScope.drawGalaxy(
    planetRandomizers: List<PlanetRandomizer>,
    planetShiftAnimationValue: Float,
    starRandomizers: List<StarRandomizer>,
    starAlphaAnimationValue: Float
) {
    val diagonal = hypot(size.width, size.height)

    // Draw all planets
    planetRandomizers.forEachIndexed { index, planetRandomizer ->
        drawPlanet(
            radius = planetRandomizer.radius,
            center = getRandomPointOutsideGalaxy(
                planetCoefficientsData = planetRandomizer.planetCoefficientsData,
                shiftAnimationValue = planetShiftAnimationValue,
                diagonal = diagonal,
                // Half is starting from reverse the other half is not
                isStaringFromReverse = index < (planetRandomizers.size / 2)
            ),
            color = planetRandomizer.color,
            alpha = planetRandomizer.alpha,
        )
    }

    // Draw all stars
    starRandomizers.forEach {
        drawStar(
            sideLength = it.starCoefficientsData.sideLengthCoefficient * 7f,
            numberOfEdges = (it.starCoefficientsData.edgeNumberCoefficient * 10).toInt(),
            interiorAngle = it.starCoefficientsData.interiorAngleCoefficient * 90f,
            startOffset = Offset(
                x = it.starCoefficientsData.startPointXCoefficient * size.width,
                y = it.starCoefficientsData.startPointYCoefficient * size.height
            ),
            fillColor = it.color,
            alpha = it.starCoefficientsData.shiningAnimationCoefficient * starAlphaAnimationValue
        )
    }
}

private fun DrawScope.drawPlanet(
    radius: Float,
    center: Offset,
    color: Color,
    alpha: Float,
) {
    drawCircle(
        color = color,
        radius = radius,
        center = center,
        alpha = alpha
    )
}

private fun DrawScope.drawStar(
    sideLength: Float,
    numberOfEdges: Int,
    interiorAngle: Float,
    startOffset: Offset,
    fillColor: Color,
    alpha: Float
) {
    // Move to the start offset
    val starPath = Path().also {
        it.moveTo(startOffset.x, startOffset.y)
    }

    // Interior edge angle is the angle of the other edges
    // in the isosceles triangle of the end points of the star
    val starInteriorEdgeAngle = (180.0 - interiorAngle) / 2
    val starExteriorAngle = 360 - starInteriorEdgeAngle

    var firstPoint = Offset(
        x = startOffset.x,
        y = startOffset.y
    )

    for (i in (1..numberOfEdges)) {
        val lineRotationAngle = i * 360.0 / numberOfEdges

        val secondPointRotationAngleInRadians = Math.toRadians(
            starExteriorAngle + lineRotationAngle
        ).toFloat()

        val secondPoint = Offset(
            x = firstPoint.x + sideLength * sin(secondPointRotationAngleInRadians),
            y = firstPoint.y + sideLength * cos(secondPointRotationAngleInRadians)
        )

        val thirdPointRotationAngleInRadians = Math.toRadians(
            starInteriorEdgeAngle + lineRotationAngle
        ).toFloat()

        starPath.lineTo(
            x = secondPoint.x,
            y = secondPoint.y
        )

        val thirdPoint = Offset(
            x = secondPoint.x + sideLength * sin(thirdPointRotationAngleInRadians),
            y = secondPoint.y + sideLength * cos(thirdPointRotationAngleInRadians)
        )

        starPath.lineTo(
            x = thirdPoint.x,
            y = thirdPoint.y
        )

        firstPoint = thirdPoint
    }

    drawPath(
        path = starPath,
        color = fillColor,
        alpha = alpha
    )
}

private fun DrawScope.getRandomPointOutsideGalaxy(
    planetCoefficientsData: PlanetCoefficientsData,
    shiftAnimationValue: Float,
    diagonal: Float,
    isStaringFromReverse: Boolean
): Offset {
    // We will first pick a random point inside the screen and
    // move it outside of the screen by adding a factor of diagonal
    val randomXInGalaxy = planetCoefficientsData.coefficientX * size.width
    val randomYInGalaxy = planetCoefficientsData.coefficientY * size.height

    val (shiftValueX, shiftValueY) = if (isStaringFromReverse) {
        // Animate from the end point to the starting point
        Pair(
            diagonal * (shiftAnimationValue - 1) * -sin(planetCoefficientsData.shiftAngle),
            diagonal * (shiftAnimationValue - 1) * -cos(planetCoefficientsData.shiftAngle)
        )
    } else {
        // Animate from the starting point to the end point
        Pair(
            diagonal * shiftAnimationValue * sin(planetCoefficientsData.shiftAngle),
            diagonal * shiftAnimationValue * cos(planetCoefficientsData.shiftAngle)
        )
    }

    return Offset(
        x = randomXInGalaxy + shiftValueX,
        y = randomYInGalaxy + shiftValueY
    )
}

private fun generateRandomPlanetDataset(
    planetData: PlanetData
): List<PlanetRandomizer> = List(planetData.numberOfPlanet) { PlanetRandomizer(planetData = planetData) }

private fun generateRandomAngle(range: IntRange): Float {
    return Math.toRadians(
        range.random().toDouble()
    ).toFloat()
}

private fun generateRandomStarDataset(
    starData: StarData
): List<StarRandomizer> = List(starData.numberOfStars) { StarRandomizer(starData = starData) }

/**
 * Keeps values for randomization
 */
data class PlanetRandomizer(
    private val planetData: PlanetData
) {
    val planetCoefficientsData: PlanetCoefficientsData = generateRandomCoefficients()
    val radius: Float = getRandomPlanetRadius(planetData.maxPlanetRadius)
    val alpha: Float = getRandomPlanetAlpha(planetData.maxPlanetAlpha)
    val color: Color = getRandomPlanetColor(planetData.planetColors)

    private fun getRandomPlanetRadius(
        maxRadius: Float
    ): Float {
        return (0..100).random() / 100f * maxRadius
    }

    private fun getRandomPlanetAlpha(
        maxAlpha: Float
    ): Float {
        return (0..100).random() / 100f * maxAlpha
    }

    private fun getRandomPlanetColor(planetColors: List<Color>) = planetColors.random()

    private fun generateRandomCoefficients(): PlanetCoefficientsData {

        // These coefficients will be randomly selected for x and y
        // For instance if coefficient x is the first one then coefficient
        // for the y will be second one. This is to generate random offsets
        // which is maxPlanetRadius distance away from the screen edge
        val firstRandomCoefficient = (0..100).random() / 100f

        // Shifting coefficients by 0.1f will make the planet itself will not
        // be visible at the starting point which will be drawn outside of the
        // screen
        val secondRandomCoefficient = listOf(
            0f - 0.1f,
            1f + 0.1f
        ).random()

        val shuffledCoefficientList = listOf(
            firstRandomCoefficient,
            secondRandomCoefficient
        ).shuffled()

        val coefficientX = shuffledCoefficientList.first()
        val coefficientY = shuffledCoefficientList.last()

        val shiftCoefficient = generateRandomAngleForShiftCoefficient(
            coefficientX = coefficientX,
            coefficientY = coefficientY
        )

        return PlanetCoefficientsData(
            coefficientX = coefficientX,
            coefficientY = coefficientY,
            shiftAngle = shiftCoefficient
        )
    }

    /**
     * Shift coefficient will be used to scale the shift value used to move the planet.
     * It is important to move the planet (which will firstly be placed outside of the screen)
     * towards the screen.
     */
    private fun generateRandomAngleForShiftCoefficient(
        coefficientX: Float,
        coefficientY: Float
    ): Float {
        // Random angle (0, PI)
        val randomAngle = generateRandomAngle(range = (0..180))

        return when {
            coefficientX > 1f -> {
                // Bottom to the bottom of the screen

                // Rotate by 180deg
                randomAngle + 180
            }
            coefficientX < 0f -> {
                // Top to the top of the screen

                // No need any rotation
                randomAngle
            }
            coefficientY > 1f -> {
                // Right to the right of the screen

                // Rotate by 90deg
                randomAngle + 90
            }
            coefficientY < 0f -> {
                // Left to the left of the screen

                // Rotate by -90deg
                randomAngle - 90
            }
            else -> throw IllegalStateException(
                "One of the coefficients must satify the " +
                    "above conditions"
            )
        }
    }
}

data class PlanetCoefficientsData(
    val coefficientX: Float,
    val coefficientY: Float,
    val shiftAngle: Float
)

data class StarRandomizer(
    private val starData: StarData
) {
    // All coefficients are between (0f, 1f)
    val starCoefficientsData = StarCoefficientsData(
        startPointXCoefficient = (0..100).random() / 100f,
        startPointYCoefficient = (0..100).random() / 100f,
        sideLengthCoefficient = (0..100).random() / 100f,
        edgeNumberCoefficient = (0..100).random() / 100f,
        interiorAngleCoefficient = (0..100).random() / 100f,
        shiningAnimationCoefficient = (0..100).random() / 100f
    )

    val color = starData.starColors.random()
}

data class StarCoefficientsData(
    val startPointXCoefficient: Float,
    val startPointYCoefficient: Float,
    val sideLengthCoefficient: Float,
    val edgeNumberCoefficient: Float,
    val interiorAngleCoefficient: Float,
    val shiningAnimationCoefficient: Float
)
package observatory

import org.junit.Test

trait ManipulationTest extends MilestoneSuite {
  private val milestoneTest = namedMilestoneTest("data manipulation", 4) _

  // Implement tests for methods of the `Manipulation` object

  @Test def `"makes grid"` {
    val temperatures = List[(Location, Temperature)]((Location(10, 10), 10))
    val grid = Manipulation.makeGrid(temperatures)

    val gridLocation = GridLocation(10, 10)
    val computed = grid(gridLocation)
    val expected = 10

    assert(computed == expected)
  }

  @Test def `"average grid"` {
    val temperaturess = List(
      List[(Location, Temperature)]((Location(10, 10), 10)),
      List[(Location, Temperature)]((Location(10, 10), 20))
    )

    val grid = Manipulation.average(temperaturess)
    val gridLocation = GridLocation(10, 10)
    val computed = grid(gridLocation)
    val expected = 15

    assert(computed == expected)
  }

  @Test def `"deviation grid"` {
    val normalTemperatures = List[(Location, Temperature)](
      (Location(10, 10), 10), (Location(20, 20), 20))
    val normalGrid = Manipulation.makeGrid(normalTemperatures)

    val otherTemperatures = List[(Location, Temperature)](
      (Location(10, 10), 15), (Location(20, 20), 20))

    val deviatedGrid = Manipulation.deviation(otherTemperatures, normalGrid)

    val expected = 5
    val computed = deviatedGrid(GridLocation(10, 10))

    assert(computed == expected)
  }
}

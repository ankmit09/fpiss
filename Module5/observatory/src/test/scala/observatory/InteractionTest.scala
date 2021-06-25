package observatory

import org.junit.Test


trait InteractionTest extends MilestoneSuite {
  private val milestoneTest = namedMilestoneTest("interactive visualization", 3) _

  @Test def `"Generates image out of one tile"` {
    val temperatures = List[(Location, Temperature)]((Location(10, 10), 10))
    val colors = List[(Temperature, Color)](
      (60, Color(255, 255, 255)), (32, Color(255, 0, 0)), (12, Color(255, 255, 0)),
      (0, Color(0, 255, 255)), (-15, Color(0, 0, 255)), (-27, Color(255, 0, 255)),
      (-50, Color(33, 0, 107)), (-60, Color(0, 0, 0))
    )

    val tile = Tile(0, 0, 0)

    val image = Interaction.tile(temperatures, colors, tile)

    assert(image != null)
  }
}

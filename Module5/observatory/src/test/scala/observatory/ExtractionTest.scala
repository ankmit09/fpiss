package observatory

import org.apache.log4j.{Level, Logger}
import org.junit.Test


trait ExtractionTest extends MilestoneSuite {

  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)

  private val milestoneTest = namedMilestoneTest("data extraction", 1) _

  // Implement tests for the methods of the `Extraction` object
  @Test def `"2015: locating temperatures"` {
    val year = 2015
    val stationFile = "/stations.csv"
    val yearFile = "/2015.csv"

    val temperatures = Extraction.locateTemperatures(year, stationFile, yearFile)

    assert(temperatures.nonEmpty)
  }

  @Test def `"2015: averaging temperatures"` {
    val year = 2015
    val stationFile = "/stations.csv"
    val yearFile = "/2015.csv"

    val locatedTemperatures = Extraction.locateTemperatures(year, stationFile, yearFile)
    val averagedTemperatures = Extraction.locationYearlyAverageRecords(locatedTemperatures)

    assert(averagedTemperatures.nonEmpty)
  }

}

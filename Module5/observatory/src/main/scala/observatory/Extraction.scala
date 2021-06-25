package observatory

import java.time.LocalDate

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession

import scala.io.Source

/**
  * 1st milestone: data extraction
  */
object Extraction extends ExtractionInterface {

  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
  val spark: SparkSession = SparkSession.builder().appName("Observatory")
    .master("local[6]").getOrCreate()

  import spark.implicits._

  /**
    * @param year             Year number
    * @param stationsFile     Path of the stations resource file to use (e.g. "/stations.csv")
    * @param temperaturesFile Path of the temperatures resource file to use (e.g. "/1975.csv")
    * @return A sequence containing triplets (date, location, temperature)
    */
  def locateTemperatures(year: Year, stationsFile: String, temperaturesFile: String): Iterable[(LocalDate, Location, Temperature)] = {

    val stations = Source
      .fromInputStream(getClass.getResourceAsStream(stationsFile))
      .getLines
      .map(_.split(","))
      .filter(_.length == 4)
      .map(cols => {
        (cols(0), cols(1), cols(2).toDouble, cols(3).toDouble)
      }).toList.toDF("stnID", "wbanID", "latitude", "longitude")
      .filter('latitude.isNotNull && 'longitude.isNotNull)
      .as[StationSchema]

    val temperatures = Source
      .fromInputStream(getClass.getResourceAsStream(temperaturesFile))
      .getLines
      .map(_.split(","))
      .filter(_.length == 5)
      .map(cols => {
        (cols(0), cols(1), cols(2).toInt, cols(3).toInt, cols(4).toDouble)
      }).toList.toDF("stnID", "wbanID", "month", "day", "temperature")
      .as[TemperatureSchema]

    stations
      .joinWith(temperatures, stations("stnID") === temperatures("stnID") && stations("wbanID") === temperatures("wbanID"))
      .map{
        case(s,t) =>
          val temperature: Temperature = (t.temperature - 32) * 5 / 9
          val location: Location = Location(s.latitude, s.longitude)
          val localDate = StationDate(year.toInt, t.month, t.day)
          (localDate, location, temperature)
      }.collect().par
      .map(
        jf => (jf._1.toLocalDate, jf._2, jf._3)
      ).seq

  }

  /**
    * @param records A sequence containing triplets (date, location, temperature)
    * @return A sequence containing, for each location, the average temperature over the year.
    */
  def locationYearlyAverageRecords(records: Iterable[(LocalDate, Location, Temperature)]): Iterable[(Location, Temperature)] = {
    records
      .par
      .groupBy(_._2)
      .mapValues(
        l => l.foldLeft(0.0)(
          (t,r) => t + r._3) / l.size
      )
      .seq
  }
}

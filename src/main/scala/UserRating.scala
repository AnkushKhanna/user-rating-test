import org.apache.spark.SparkConf
import org.apache.spark.mllib.recommendation.{ALS, Rating}
import org.apache.spark.sql.SparkSession

object UserRating {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .set("spark.driver.maxResultSize", "3g")
      .setMaster("local[4]")
      .set("spark.driver.cores", "5")

    val sparkSession = SparkSession.builder()
      .appName("Movie Recommendation Engine")
      .config(conf)
      .getOrCreate()


    val df = sparkSession.read
      .option("header", "true")
      .csv("data/ratings.csv")

    print(df.count())

    val data = df.rdd.map { row =>
      Rating(row.getAs[String]("user_id").toInt, row.getAs[String]("movie_id").toInt, row.getAs[String]("rating").toDouble)
    }

    val array = data.randomSplit(weights = Array(6.0, 2.0, 2.0))

    val (train, cv, test) = (array(0), array(1), array(2))
    for (r <- Array(4, 8, 12)) {
      val model = ALS.train(train, r, 10, 0.1)

      val usersProducts = cv.map { case Rating(user, product, rate) =>
        (user, product)
      }

      val predictions =
        model.predict(usersProducts).map { case Rating(user, product, rate) =>
          ((user, product), rate)
        }

      val ratesAndPreds = cv.map { case Rating(user, product, rate) =>
        ((user, product), rate)
      }.join(predictions)

      val MSE = ratesAndPreds.map { case ((user, product), (r1, r2)) =>
        val err = (r1 - r2)
        err * err
      }.mean()

      println("Mean Squared Error = " + MSE)
    }
  }
}

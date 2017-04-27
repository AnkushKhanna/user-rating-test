import java.util.Scanner

import org.apache.spark.SparkConf
import org.apache.spark.mllib.recommendation.{MatrixFactorizationModel, Rating}
import org.apache.spark.sql.SparkSession

object PredictUserRating {
  def main(args: Array[String]): Unit = {
    val predictUserRating = new PredictUserRating
    val sc = new Scanner(System.in)
    while (true) {
      println("Enter p for prediction, r for recommendation, anything else to exit.")
      val input = sc.next()
      if (input == "p") {
        prediction()
      } else if (input == "r") {
        recommendation()
      } else {
        System.exit(1)
      }

    }

    def prediction() = {
      println("Enter user id [Int]")
      val userId = sc.nextInt()
      println("Enter item id [Int]")
      val itemId = sc.nextInt()
      val prediction = predictUserRating.predict(userId, itemId)
      println(s"Prediction is $prediction")
    }

    def recommendation() = {
      println("Enter user id [Int]")
      val userId = sc.nextInt()
      val recommendations = predictUserRating
        .recommendation(userId)
        .map(rating => (rating.product, rating.rating)).mkString(" - ")
      println(s"Recommendation is $recommendations")
    }
  }

}

class PredictUserRating {
  val conf = new SparkConf()
    .set("spark.driver.maxResultSize", "8g")
    .setMaster("local[4]")
    .set("spark.driver.cores", "5")

  val sparkSession = SparkSession.builder()
    .appName("Recommendation Engine Predict")
    .config(conf)
    .getOrCreate()


  val model: MatrixFactorizationModel = MatrixFactorizationModel.load(sparkSession.sparkContext, "data/als-model")

  def predict(userId: Int, itemId: Int): Double = {
    model.predict(userId, itemId)
  }

  def recommendation(userId: Int): Array[Rating] = {
    model.recommendProducts(userId, 5)
  }

}

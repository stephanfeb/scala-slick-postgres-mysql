/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 , Stephan M. February
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

import slick.driver.MySQLDriver.api._
//import slick.driver.PostgresDriver.api._

import scala.concurrent.{Future, Await}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success}

// Definition of the SUPPLIERS table
//This is a verbatim copy of an example Table config which you can find HERE:
//http://slick.typesafe.com/doc/3.1.0/gettingstarted.html
class Suppliers(tag: Tag) extends Table[(Int, String, String, String, String, String)](tag, "SUPPLIERS") {
  def id = column[Int]("SUP_ID", O.PrimaryKey) // This is the primary key column
  def name = column[String]("SUP_NAME")
  def street = column[String]("STREET")
  def city = column[String]("CITY")
  def state = column[String]("STATE")
  def zip = column[String]("ZIP")
  // Every table needs a * projection with the same type as the table's type parameter
  def * = (id, name, street, city, state, zip)
}

object Application extends  App {

  val suppliers = TableQuery[Suppliers]

  //Choose your flavour. One only. The config string refers
  //to settings in application.conf
  val db = Database.forConfig("mysqlDB")
  //val db = Database.forConfig("postgresDB")

  //User the schema definition to generate DROP statement
  val dropCmd = DBIO.seq(suppliers.schema.drop)

  //User the schema definition to generate a CREATE TABLE
  //command, followed by INSERTs
  val setup = DBIO.seq( suppliers.schema.create,
    suppliers += (101, "Acme, Inc.",      "99 Market Street", "Groundsville", "CA", "95199"),
    suppliers += ( 49, "Superior Coffee", "1 Party Place",    "Mendocino",    "CA", "95460")
  )

  def runQuery = {
      val queryFuture = Future {
        //A very naive query which is the equivalent of SELECT * FROM TABLE
        //and having the FRM map the columns to the params of a partial function
        //
        db.run(suppliers.result).map(_.foreach {
          case (id, name, street, city, state, zip) => println(s"${name}: ${street} : ${city}")
        })
      }

      //Everything runs asynchronously. Failure to wait for results
      //usually leads to no results :)
      //NOTE: Await does not block here!
      Await.result(queryFuture, Duration.Inf).andThen {
        case Success(_) =>  db.close()  //cleanup DB connection
        case Failure(err) => println(err); println("Oh Noes!")  //handy for debugging failure
      }

  }

  def dropDB = {

      //do a drop followed by create
      val dropFuture =  Future {
        db.run(dropCmd)
      }

      //Attemp to drop the table, and don't care if it
      //fails (NOT GOOD!)
      Await.result(dropFuture, Duration.Inf).andThen{
        case Success(_) =>  doSomething
        case Failure(_) =>  doSomething
      }

  }

  def doSomething = {

      //do a drop followed by create
      val setupFuture =  Future {
        db.run(setup)
      }

      //once our DB has finished initializing we are ready to roll !
      //NOTE: Await does not block here!
      Await.result(setupFuture, Duration.Inf).andThen{
        case Success(_) => runQuery
        case Failure(err) => println(err);
      }

      //Printing this just for fun. Keep an eye on your console to see this print
      // before the query results :)
      println("Seeya!")
  }

  dropDB //execution starts HERE. With a DROP.
}
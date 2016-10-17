package com.pcb.etl

import akka.actor.{Actor, ActorLogging}
import akka.camel.{CamelMessage, Consumer}
import akka.pattern.ask
import akka.util.Timeout
import com.pcb.messages._
import scala.concurrent.duration._
import scala.language.postfixOps

class Industry extends Actor with ActorLogging with Consumer {

  import context.dispatcher

  implicit val timeout = Timeout(2 seconds)

  val config = context.system.settings.config

  val directory = config.getString("etl.sources.directory")
  val file = config.getString("etl.sources.industry")
  def endpointUri = s"file:${directory}?include=${file}&delete=true"

  val dbPath = config.getString("etl.db.path")
  val data = context.actorSelection(dbPath)

  val NEW_LINE = "\\r?\\n"
  val DELIM = '|'

  def receive = {
    case msg: CamelMessage => {
      val lines = msg.bodyAs[String].split(NEW_LINE)
      for (line <- lines) {
        val crInd = genMsg(line.split(DELIM))
        ask(data, crInd) onFailure {
          case _ => log.error("Creation failed")
        }
      }
    }
  }

  def genMsg(arr:Array[String]) = CreateIndustry(arr(0), arr(1), arr(2))
}

class StatusType extends Actor with ActorLogging with Consumer {

  import context.dispatcher

  implicit val timeout = Timeout(2 seconds)

  val config = context.system.settings.config

  val directory = config.getString("etl.sources.directory")
  val file = config.getString("etl.sources.statusType")
  def endpointUri = s"file:${directory}?include=${file}&delete=true"

  val dbPath = config.getString("etl.db.path")
  val data = context.actorSelection(dbPath)

  val NEW_LINE = "\\r?\\n"
  val DELIM = '|'

  def receive = {
    case msg: CamelMessage => {
      val lines = msg.bodyAs[String].split(NEW_LINE)
      for (line <- lines) {
        val crSt = genMsg(line.split(DELIM))
        ask(data, crSt) onFailure {
          case _ => log.error("Creation failed")
        }
      }
    }
  }

  def genMsg(arr:Array[String]) = CreateStatusType(arr(0), arr(1))
}

class TaxRate extends Actor with ActorLogging with Consumer {

  import context.dispatcher

  implicit val timeout = Timeout(2 seconds)

  val config = context.system.settings.config

  val directory = config.getString("etl.sources.directory")
  val file = config.getString("etl.sources.taxRate")
  def endpointUri = s"file:${directory}?include=${file}&delete=true"

  val dbPath = config.getString("etl.db.path")
  val data = context.actorSelection(dbPath)

  val NEW_LINE = "\\r?\\n"
  val DELIM = '|'

  def receive = {
    case msg: CamelMessage => {
      val lines = msg.bodyAs[String].split(NEW_LINE)
      for (line <- lines) {
        val crTr = genMsg(line.split(DELIM))
        ask(data, crTr) onFailure {
          case _ => log.error("Creation failed")
        }
      }
    }
  }

  def genMsg(arr:Array[String]) = CreateTaxRate(arr(0), arr(1), arr(2).toDouble)
}

class TradeType extends Actor with ActorLogging with Consumer {

  import context.dispatcher

  implicit val timeout = Timeout(2 seconds)

  val config = context.system.settings.config

  val directory = config.getString("etl.sources.directory")
  val file = config.getString("etl.sources.tradeType")
  def endpointUri = s"file:${directory}?include=${file}&delete=true"

  val dbPath = config.getString("etl.db.path")
  val data = context.actorSelection(dbPath)

  val NEW_LINE = "\\r?\\n"
  val DELIM = '|'

  def receive = {
    case msg: CamelMessage => {
      val lines = msg.bodyAs[String].split(NEW_LINE)
      for (line <- lines) {
        val crTt = genMsg(line.split(DELIM))
        ask(data, crTt) onFailure {
          case _ => log.error("Creation failed")
        }
      }
    }
  }

  def genMsg(arr:Array[String]) = CreateTradeType(arr(0), arr(1), arr(2).toShort, arr(3).toShort)
}
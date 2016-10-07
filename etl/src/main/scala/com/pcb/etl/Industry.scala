package com.pcb.etl

import akka.actor.{Actor, ActorLogging}
import akka.camel.{CamelMessage, Consumer}
import akka.pattern.ask
import akka.util.Timeout
import com.pcb.messages.CreateIndustry
import scala.concurrent.duration._
import scala.language.postfixOps

class Industry extends Actor with ActorLogging with Consumer {

  import context.dispatcher

  implicit val timeout = Timeout(2 seconds)

  val fileName = "Industry.txt"

  def endpointUri = s"file:data/input?include=${fileName}&delete=true"

  val path = "akka.tcp://pcb-data@127.0.0.1:2552/user/tpcdidata/data-industry"
  val tcpdidata = context.actorSelection(path)

  def receive = {
    case msg: CamelMessage => {
      val lines = msg.bodyAs[String].split("\\r?\\n")
      for (line <- lines) {
        ask(tcpdidata, genMsg(line.split('|'))) onFailure {
          case _ => log.error("Industry creation failed") 
        }
      }
    }
  }

  def genMsg(arr:Array[String]) = 
    CreateIndustry(arr(0), arr(1), arr(2))
}
package com.dav.scala.chat
// inpired by http://seanchapel.blogspot.co.il/2009/05/simple-server-in-scala.html

import java.io._
import java.net.{ServerSocket, Socket, SocketException}
import scala.actors.Actor
import scala.collection.mutable


object Server {
  def main (args : Array[String]) {
    val port = 6669

    try {
      val listener = new ServerSocket(port)
      var clientNumber = 1

      println("Listening on port " + port)

      while (true) {
        new ClientHandler(listener.accept, clientNumber).start()
        clientNumber += 1
      }

      listener.close()
    }
    catch {
      case e : IOException =>
        System.err.println("Could not listen on port: " + port + ".")
        System.exit(-1)
    }

  }
}

case class Client(name: String, out: PrintWriter, in: BufferedReader){
  def print(s:String) = {
    out.print(s)
    out.flush()
  }
  def println(s:String) = {
    out.println(s)
    out.flush()
  }
  def ask(s:String) : String = {
    println(s + " ")
    in.readLine
  }
  def read : String = in.readLine
  def say (say :String) = {
    Clients.say(this, say)
  }
}

object Clients { // not thread safe, but in this case, i don't care
  private var clients = mutable.Set[Client]()
  def add (p :Client) { clients += p }
  def remove (p :Client) { clients -= p }
  def broadcast (s: String) {
    println(s)
    clients.foreach { _.println(s) }
  }
  def say (client: Client, s: String) {
    println(s)
    clients.filter(_ != client).foreach { _.println(s) }
  }
  
}

class ClientHandler (socket : Socket, clientId : Int) extends Actor {
  def act {
    try {
      val client : Client = createClient
      readInLoop(client)
      shutdown(client)
    }
    catch {
      case e : SocketException => System.err.println(e)
      case e : IOException => System.err.println(e.printStackTrace())
      case e => System.err.println("Unknown error " + e)
    }
  }


  private def shutdown (client : Client) {
    socket.close()
    client.say("Client " + clientId + " quit")
    Clients.remove(client)
  }

  private def readInLoop (client : Client) {
    client.say(s"Client (${client.name}) connected from ${socket.getInetAddress}:${socket.getPort}")
    var inputLine = ""
    while (inputLine != null) {
      inputLine = client.read
      client.say(f"${client.name}%-10s|  $inputLine")
    }
  }

  private def createClient : Client = {
    val out = new PrintWriter(socket.getOutputStream, true)
    val in = new BufferedReader(new InputStreamReader(socket.getInputStream))
    val initialClient = Client(clientId.toString, out, in)
    val clientName = initialClient.ask("please insert your name:")
    val client = initialClient.copy(name = clientName)
    Clients.add(client)
    client
  }
}
 

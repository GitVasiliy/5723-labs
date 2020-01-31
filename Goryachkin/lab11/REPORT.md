Требуется запустить сервер(java ServerMain 8080) и клиент(java ClientMain 127.0.0.1 8080). Написав у клиента команду @game, запускается игра.
Сервер загадывает число которое не знает клиент(My number 49). Клиенту приходит сообщение от сервера "Hello. Let's play. Guess a number from 0 to 100".
Далее клиент отправляет своё число на сервер. В зависимости от числа сервер отвечает клиенту больше оно загаданного или меньше.
Если клиент угадывает число, то сервер отправляет сообщение "Congratulations! You guessed!" и пишет какое количество ходов потребовалось.
Игра завершается.


Описание методов и классов.
public class ClientMain
  метод - public static void main(String[] args): проверяет входные аргументы(ip и port), запускает метод класса Client messaging().
  
public class ServerMain
  метод - public static void main(String[] args): проверяет входной аргумент(port), запускает метод класса Server messaging().
  
public class Client
  метод - public Client(String name, DatagramSocket socket, SocketAddress address): задаёт имя, сокет и адрес.
  метод - public void setName(String name): изменение имени.
  метод - public void setAddress(SocketAddress address): задаёт адрес.
  метод - public DatagramSocket getSocket(): возвращает сокет.
  метод - public SocketAddress getAddress(): возвращает адрес.
  метод - public Sender getSender(): возвращает объект класса Sender.
  метод - public Receiver getReceiver(): возвращает объект класса Receiver.
  метод - public String getName(): возвращает имя.
  метод - public void close(): закрывает сокет.
  метод - public void messaging(): запускаются потоки receiver и sender.
  
public class Server
  метод - public Server(String name, DatagramSocket sourceSocket): создаётся объект класса Client.
  метод - public void messaging(): вызывается метод класса Client messeging().
  
public class InputOutput
  метод - public void close(): закрытие потока чтения.
  метод - public String read(): считывание сообщения с командной строки.
  метод - public synchronized void write: вывод в командную строку сообщения.
  
public class Receiver extends Thread
  метод - public Receiver(InputOutput inputOutput, Client client): принимает объекты класса InputOutput и Client.
  метод - public DatagramPacket receive(): принятие пакета.
  метод - public String getClientName(): возвращает имя клиента.
  метод - public void run(): если принятое сообщение @game, то начинаем игру, иначе отправляем сообщение либо завершаем чат.
  метод - public void send(String message): отправка сообщения.
  метод - public void game(): игра по угадыванимю числа.
  
public class Sender extends Thread
  метод - public Sender(InputOutput inputOutput, Client client, Receiver r): принимаются объекты классов InputOutput, Client и Receiver.
  метод - public void send(String message): отправка сообщения.
  метод - public void checkCommand(String command): проверка на команду типа @.
  метод - public void run(): отправка сообщения либо завершения чата.

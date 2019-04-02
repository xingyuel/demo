# demo

This is to show: 1. How easy to use Netflix Archaius (dynamic configuration); 2. A bug in Spring Boot 1.5 and how to avoid it.

To run it, please do the following:

1. git clone https://github.com/xingyuel/demo.git (**If you are behind of a company firewall you may have to download the ZIP file**)
2. cd demo
3. mvn package
4. java -Darchaius.configurationSource.additionalUrls="file:///c:\temp\demo.properties" -jar target/demo-0.0.1-SNAPSHOT.jar

Here some configuration info will be read from c:\temp\demo.properties. If you use a different filename please replace file:///c:\temp\demo.properties accordingly. **Please make sure that file exists.**

### Test Dynamic Configuration

After the JVM is up, in a web browser enter http://localhost:8080/demo. When c:\temp\demo.properties is empty, you will see "got ya!", the default value. Then enter a line in that file. For example:

demo.name=Archaius is cool!

save the change and wait for a minute or less, you should see something like "2019-03-28 20:04:18.745  INFO 13876 --- [igurationSource] com.xingyueli.demo.DemoController         : **new value: Archaius is cool!**"

then if you refresh the browser you will also see the new value in the browser.

### Verify the Bug

In the browser, enter http://localhost:8080/test/9.08. Both the log file and the browser will show 9.0. However, http://localhost:8080/test/9.08/ (ending with a slash) will end up with 9.08. This is a bug in Spring 4 / Spring Boot 1.5. Since Spring Boot 2.0, this problem has been fixed.

To see the real problem, http://localhost:8080/test/9.08.Extra (without an ending slash) will show 9.08 and http://localhost:8080/test/9.08.Extra/ (with an endhing slash) will end up with the the following error:

*Failed to **convert value of type 'java.lang.String' to required type 'double'**; nested exception is java.lang.NumberFormatException: multiple points*

Here we see that Spring failed to convert a String to Double. To verify old Spring does not handle String with dot (".") very well, http://localhost:8080/string/9.08.Extra will show "9.08", but http://localhost:8080/string/9.08.Extra/ will show "9.08.Extra".

**If you have to use Spring Boot 1.5 or Spring 4, in this case you have to append slash ("/") to the URL.**

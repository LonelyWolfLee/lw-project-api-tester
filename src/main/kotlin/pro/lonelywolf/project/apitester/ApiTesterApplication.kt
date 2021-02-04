package pro.lonelywolf.project.apitester

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ApiTesterApplication

fun main(args: Array<String>) {
  runApplication<ApiTesterApplication>(*args)
}

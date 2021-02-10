package pro.lonelywolf.project.apitester

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.http.HttpMethod
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono


@SpringBootApplication
class ApiTesterApplication

fun main(args: Array<String>) {
  runApplication<ApiTesterApplication>(*args)
}

@RestController
@RequestMapping("api")
class ApiController {
  private val client: WebClient = WebClient.create()

  @PostMapping("request/call")
  fun callRequest(@RequestBody request: Request): Mono<Response> {
    val requestClient = client
      .method(request.method)
      .uri(request.uri)
      .bodyValue(request.body)

    request.headers.forEach { requestClient.header(it.key, it.value) }

    return requestClient.exchangeToMono { clientResponse ->
      clientResponse.bodyToMono(String::class.java).flatMap { body ->
        Mono.just(
          Response(
            clientResponse.statusCode().toString(),
            clientResponse.headers().asHttpHeaders().map { Header(it.key, it.value.toString()) },
            body
          )
        )
      }
    }
  }
}

data class Request(
  val method: HttpMethod,
  val uri: String,
  val headers: List<Header>,
  val body: String
)

data class Response(
  val status: String,
  val headers: List<Header>,
  val body: String?
)

data class Header(
  val key: String,
  val value: String
)
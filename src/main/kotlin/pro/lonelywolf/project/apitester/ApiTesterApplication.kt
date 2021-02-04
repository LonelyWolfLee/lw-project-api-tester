package pro.lonelywolf.project.apitester

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.core.io.Resource
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.server.RequestPredicates.GET
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions.route
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import reactor.core.publisher.Mono


@SpringBootApplication
class ApiTesterApplication {
  @Bean
  fun htmlRouter(@Value("classpath:/static/index.html") html: Resource): RouterFunction<ServerResponse> {
    return route(GET("/")) { ok().contentType(MediaType.TEXT_HTML).bodyValue(html) }
  }
}

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
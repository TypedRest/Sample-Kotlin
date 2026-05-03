package net.typedrest.sample

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ProblemDetail
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

class BadRequestException(message: String) : RuntimeException(message)

/**
 * Reports exceptions with appropriate HTTP status codes.
 */
@ControllerAdvice
class ApiExceptionHandler {
    private val logger = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(ex: NoSuchElementException) = build(ex, HttpStatus.NOT_FOUND)

    @ExceptionHandler(BadRequestException::class, IllegalArgumentException::class)
    fun handleBadRequest(ex: RuntimeException) = build(ex, HttpStatus.BAD_REQUEST)

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidation(ex: MethodArgumentNotValidException) = build(ex, HttpStatus.BAD_REQUEST)

    @ExceptionHandler(IllegalStateException::class)
    fun handleConflict(ex: IllegalStateException) = build(ex, HttpStatus.CONFLICT)

    @ExceptionHandler(Exception::class)
    fun handleOther(ex: Exception) = build(ex, HttpStatus.INTERNAL_SERVER_ERROR)

    private fun build(ex: Exception, status: HttpStatus): ResponseEntity<ProblemDetail> {
        logger.atLevel(if (status.is5xxServerError) org.slf4j.event.Level.ERROR else org.slf4j.event.Level.INFO)
            .setCause(ex)
            .log("Responded with {} due to exception", status)
        val problem = ProblemDetail.forStatus(status).apply {
            title = ex.javaClass.simpleName
            detail = ex.message
        }
        return ResponseEntity.status(status)
            .contentType(MediaType.APPLICATION_PROBLEM_JSON)
            .body(problem)
    }
}

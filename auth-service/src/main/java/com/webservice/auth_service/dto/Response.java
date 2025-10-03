package com.webservice.auth_service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {
  private String message;
  private Object payload; // actual data
  private int status = HttpStatus.OK.value();

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime timestamp = LocalDateTime.now();

  public Response(String message){
    this.message = message;
  }

  public Response(String message, Object payload){
    this.message = message;
    this.payload = payload;
  }
}

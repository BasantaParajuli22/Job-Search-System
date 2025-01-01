package com.example.springTrain.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


/**
 * This is where we handle the case in that a request exceeds Max Upload Size.
 *  The system will throw MaxUploadSizeExceededException and we’re gonna use
 *   @ControllerAdvice with @ExceptionHandlerannotation for handling the exceptions.
 */
@ControllerAdvice
public class FileUploadExceptionAdvice {

  @ExceptionHandler(MaxUploadSizeExceededException.class)
  public String handleMaxSizeException(RedirectAttributes redirectAttributes) {
	  
      redirectAttributes.addFlashAttribute("errorMessage", "File size exceeds the maximum allowed size of 1 MB.");

    return "redirect:/view/jobposts";
  }
}
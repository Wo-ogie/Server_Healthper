package umc.healthper.exception;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import umc.healthper.exception.comment.CommentAlreadyRemovedException;
import umc.healthper.exception.comment.CommentNotFoundException;
import umc.healthper.exception.member.MemberDuplicateException;
import umc.healthper.exception.member.MemberNotFoundByIdException;
import umc.healthper.exception.member.MemberNotFoundByKakaoKeyException;
import umc.healthper.exception.post.PostAlreadyRemovedException;
import umc.healthper.exception.post.PostNotFoundException;
import umc.healthper.exception.postlike.AlreadyPostLikeException;
import umc.healthper.exception.postlike.PostLikeNotFoundException;
import umc.healthper.exception.record.RecordNotFoundByIdException;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final MessageSource messageSource;
    /**
     * global
     */
    @ExceptionHandler//인자 타입이 잘못된 경우
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse illegalArgs(IllegalArgumentException e){
        log.error(String.valueOf(e));
        return new ExceptionResponse(
                HttpStatus.BAD_REQUEST,
                getMessage("illegalArgs.code"),
                getMessage("illegalArgs.message")
        );
    }

//    @ExceptionHandler
//    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)//로그인 해야하는 경우
//    public ExceptionResponse illegalAccess(AccessException e){
//        log.error(String.valueOf(e));
//        return new ExceptionResponse(
//                HttpStatus.NOT_ACCEPTABLE,
//                getMessage("illegalAccess.code"),
//                getMessage("illegalAccess.message")
//        );
//    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)//kakaID처럼 잘못된 인자 명을 넘긴 경우
    public ExceptionResponse illegalAccess(MissingServletRequestParameterException e){
        log.error(String.valueOf(e));
        return new ExceptionResponse(
                HttpStatus.BAD_REQUEST,
                getMessage("badRequest.code"),
                getMessage("badRequest.message")
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)//json 형식이 잘못된 경우
    public ExceptionResponse badJSON(MismatchedInputException e){
        log.error(String.valueOf(e));
        return new ExceptionResponse(
                HttpStatus.BAD_REQUEST,
                getMessage("badRequestJSON.code"),
                getMessage("badRequestJSON.message")
        );
    }
    /**
     * Member
     */
    @ExceptionHandler({MemberNotFoundByIdException.class, MemberNotFoundByKakaoKeyException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse memberNotFoundExceptionHandle(Exception e) {
        log.error(String.valueOf(e));
        return new ExceptionResponse(
                HttpStatus.NOT_FOUND,
                getMessage("memberNotFound.code"),
                getMessage("memberNotFound.message")
        );
    }

    @ExceptionHandler(MemberDuplicateException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse memberDuplicateExceptionHandle(MemberDuplicateException e) {
        log.error(String.valueOf(e));
        return new ExceptionResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                getMessage("memberDuplicate.code"),
                getMessage("memberDuplicate.message")
        );
    }

    /**
     * Post, PostLike
     */
    @ExceptionHandler(PostNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse postNotFoundExceptionHandle(PostNotFoundException e) {
        log.error(String.valueOf(e));
        return new ExceptionResponse(
                HttpStatus.NOT_FOUND,
                getMessage("postNotFound.code"),
                getMessage("postNotFound.message")
        );
    }

    @ExceptionHandler(PostAlreadyRemovedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse postAlreadyRemovedExceptionHandle(PostAlreadyRemovedException e) {
        log.error(String.valueOf(e));
        return new ExceptionResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                getMessage("postAlreadyRemoved.code"),
                getMessage("postAlreadyRemoved.message")
        );
    }

    @ExceptionHandler(PostLikeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse postLikeNotFoundExceptionHandle(PostLikeNotFoundException e) {
        log.error(String.valueOf(e));
        return new ExceptionResponse(
                HttpStatus.NOT_FOUND,
                getMessage("postLikeNotFound.code"),
                getMessage("postLikeNotFound.message")
        );
    }

    @ExceptionHandler(AlreadyPostLikeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse alreadyPostLikeExceptionHandle(AlreadyPostLikeException e) {
        log.error(String.valueOf(e));
        return new ExceptionResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                getMessage("alreadyPostLike.code"),
                getMessage("alreadyPostLike.message")
        );
    }

    /**
     * Comment
     */
    @ExceptionHandler(CommentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse commentNotFoundExceptionHandle(CommentNotFoundException e) {
        log.error(String.valueOf(e));
        return new ExceptionResponse(
                HttpStatus.NOT_FOUND,
                getMessage("commentNotFound.code"),
                getMessage("commentNotFound.message")
        );
    }

    @ExceptionHandler(CommentAlreadyRemovedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse commentAlreadyRemovedExceptionHandle(CommentAlreadyRemovedException e) {
        log.error(String.valueOf(e));
        return new ExceptionResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                getMessage("commentAlreadyRemoved.code"),
                getMessage("commentAlreadyRemoved.message")
        );
    }

    /**
     * record
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse illegalAccess(RecordNotFoundByIdException e){
        log.error(String.valueOf(e));
        return new ExceptionResponse(
                HttpStatus.BAD_REQUEST,
                getMessage("recordNotFound.code"),
                getMessage("recordNotFound.message")
        );
    }

    private String getMessage(String code) {
        return messageSource.getMessage(code, null, null);
    }
}

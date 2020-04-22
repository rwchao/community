package life.majiang.community.exception;

/**
 * Created by codedrinker on 2019/5/28.
 */
public interface ICustomizeErrorCode {
    String getMessage() ;
    //p38:实现回复功能的异常处理
    Integer getCode();
}

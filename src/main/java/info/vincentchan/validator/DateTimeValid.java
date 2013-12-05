package info.vincentchan.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 * @author Vincent.Chan
 * @since 2012.05.03 15:36
 */
@Documented
@Constraint(validatedBy = DateTimeValidator.class)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface DateTimeValid {
    String message() default "日期格式不对哦！";

    Class<?>[] groups() default {};

    String pattern() default "yyyy-MM-dd";

    Class<? extends Payload>[] payload() default {};
}

package info.vincentchan.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 可以工作，但是无法校验日期格式错误问题，原因是那是格式转化的问题，
 * 只有表单数据成功转化成相应属性值后才能做校验，所以这个Validator基本是没有用的
 *
 * @author Vincent.Chan
 * @since 2012.05.03 15:36
 */
public class DateTimeValidator implements
        ConstraintValidator<DateTimeValid, Date>, Validator {
    private DateFormat formatter;

    @Override
    public void initialize(DateTimeValid constraintAnnotation) {
        formatter = new SimpleDateFormat(constraintAnnotation.pattern());
    }

    @Override
    public boolean isValid(Date value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        /*try {
            formatter.parse(value);
        } catch (ParseException e) {
            return false;
        }*/
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAnnotationPresent(DateTimeValid.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
    }
}

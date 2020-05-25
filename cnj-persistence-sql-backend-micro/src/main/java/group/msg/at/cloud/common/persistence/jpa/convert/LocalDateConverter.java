package group.msg.at.cloud.common.persistence.jpa.convert;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * {@code JPA Attribute Converter} to have support for {@code LocalDate}
 * attributes and Oracle {@code DATE} columns.
 *
 * @author Michael Theis (michael.theis@hm.edu)
 * @version 1.0
 * @since release SS2019
 */
@Converter(autoApply = true)
public class LocalDateConverter implements AttributeConverter<LocalDate, Timestamp> {

    @Override
    public Timestamp convertToDatabaseColumn(LocalDate attribute) {
        Timestamp result = null;
        if (attribute != null) {
            result = Timestamp.valueOf(LocalDateTime.of(attribute, LocalTime.MIN));
        }
        return result;
    }

    @Override
    public LocalDate convertToEntityAttribute(Timestamp dbData) {
        LocalDate result = null;
        if (dbData != null) {
            result = dbData.toLocalDateTime().toLocalDate();
        }
        return result;
    }
}

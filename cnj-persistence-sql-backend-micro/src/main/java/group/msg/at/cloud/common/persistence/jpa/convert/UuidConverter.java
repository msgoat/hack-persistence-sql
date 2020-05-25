package group.msg.at.cloud.common.persistence.jpa.convert;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.UUID;

/**
 * {@code JPA Attribute Converter} to have support for UUID attribute and
 * PostgreSQL UUID columns.
 * <p>
 * Actually, the implementation looks a little awkward (same java type and db
 * type) but this is the only way to convince the PostgreSQL JDBC driver to map
 * {@code UUID} attribute values to {@code UUID} db column values.
 * </p>
 *
 * @author Michael Theis (michael.theis@hm.edu)
 * @version 1.0
 * @since release SS2019
 */
@Converter(autoApply = true)
public class UuidConverter implements AttributeConverter<UUID, UUID> {

    @Override
    public UUID convertToDatabaseColumn(UUID attribute) {
        return attribute;
    }

    @Override
    public UUID convertToEntityAttribute(UUID dbData) {
        return dbData;
    }

}

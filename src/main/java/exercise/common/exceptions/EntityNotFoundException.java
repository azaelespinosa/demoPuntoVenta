package exercise.common.exceptions;

import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class EntityNotFoundException extends ValidationException {

    public EntityNotFoundException(Class entityClass, String... searchParamsMap) {
        super(EntityNotFoundException.generateMessage(entityClass.getSimpleName(), toMap(String.class, String.class, searchParamsMap)));
    }

    public EntityNotFoundException(String entityName, long id, String... searchParamsMap) {
        super(EntityNotFoundException.generateMessage(entityName, toMap(String.class, String.class, searchParamsMap)));
    }

    private static String generateMessage(String entity, Map<String, String> searchParams) {
        return StringUtils.capitalize(entity.replace("Entity","")) +
                " was not found for parameters " +  searchParams;
    }

    private static <K, V> Map<K, V> toMap(Class<K> keyType, Class<V> valueType, Object... entries) {

        if (entries.length % 2 == 1) {
            return new HashMap<K, V>();
        }

        return IntStream.range(0, entries.length / 2).map(i -> i * 2)
                .collect(HashMap::new, (m, i) ->
                        m.put(keyType.cast(entries[i]), valueType.cast(entries[i + 1])), Map::putAll);
    }
}

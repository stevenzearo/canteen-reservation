package app.canteen.json;

import app.restaurant.api.meal.MealView;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.framework.internal.json.JSONMapper;
import core.framework.json.JSON;
import core.framework.util.Lists;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author steve
 */
public class JsonMapperTest {
    private final Logger logger = LoggerFactory.getLogger(JsonMapperTest.class);

    @Test
    void test() throws Exception {
        List<MealView> list = Lists.newArrayList();
        MealView mealView = new MealView();
        mealView.name = "noddles";
        list.add(mealView);
        ObjectMapper objectMapper = JSONMapper.OBJECT_MAPPER;
        objectMapper.readerFor(MealView.class);
        String s1 = objectMapper.writeValueAsString(list);
        logger.info(s1);
        ListTypeReference typeReference = new ListTypeReference();
        List<MealView> list1 = objectMapper.readValue(s1, typeReference);
        list1.forEach(mealView1 -> logger.info(JSON.toJSON(mealView1)));
    }

    private static class ListTypeReference extends TypeReference<List<MealView>> {
    }
}

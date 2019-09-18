package app.canteen.json;

import app.restaurant.api.meal.MealView;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.framework.internal.json.JSONMapper;
import core.framework.json.JSON;
import core.framework.util.Lists;

import java.util.List;

/**
 * @author steve
 */
public class JsonMapperTest {
    public static void main(String[] args) throws Exception {
        List<MealView> list = Lists.newArrayList();
        MealView mealView = new MealView();
        mealView.name = "noddles";
        list.add(mealView);
        ObjectMapper objectMapper = JSONMapper.OBJECT_MAPPER;
        objectMapper.readerFor(MealView.class);
        String s = JSON.toJSON(list);
        String s1 = objectMapper.writeValueAsString(list);
        System.out.println(s1);
        TypeReference<List<MealView>> typeReference = new TypeReference<>() {
        };
        List<MealView> list1 = objectMapper.readValue(s1, typeReference);
        list1.forEach(mealView1 -> System.out.println(JSON.toJSON(mealView1)));
    }
}

package ink.rubi.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author : Rubi
 * @version : 2019-05-22 18:50 下午
 */
public class Test {

    private static List<City> cities = new ArrayList<City>() {{
        add(new City(1, "中国", 0));
        add(new City(2, "广东", 1));
        add(new City(3, "广西", 1));
        add(new City(4, "广州", 2));
        add(new City(5, "佛山", 2));
        add(new City(6, "桂林", 3));
        add(new City(7, "桂林", 3));
        add(new City(8, "桂林", 3));
        add(new City(9, "桂林", 3));
    }};


    /*public static void main(String[] args) throws Exception {
        ink.rubi.test.CityVo parent = new ink.rubi.test.CityVo(cities.get(0));
        cities.remove(0);
        loadChildren(parent, cities);
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(parent));
    }*/

    private static CityVo loadChildren(CityVo parent, List<City> cities) {
        Iterator<City> it = cities.iterator();
        while (it.hasNext()) {
            System.out.println("a");
            City city = it.next();
            if (city.getPid() == parent.getValue()) {
                parent.getChildren().add(new CityVo(city));
                it.remove();
            }
        }
        if (!parent.getChildren().isEmpty()) {
            for (CityVo child : parent.getChildren()) {
                loadChildren(child, cities);
            }
        }
        return parent;
    }


    public static void main(String[] args) {
        System.out.println(TestEnum.TEST_ENUM1.name());
        System.out.println(TestEnum.valueOf("2").name());
    }
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class CityVo {
    private int value;
    private String label;
    private List<CityVo> children = new ArrayList<>();

    public CityVo(City city) {
        this.value = city.getId();
        this.label = city.getName();
    }

    public CityVo(int value, String label) {
        this.value = value;
        this.label = label;
    }
}


@Data
class City {
    private int id;
    private String name;
    private int pid;


    public City(int id, String name, int pid) {
        this.id = id;
        this.name = name;
        this.pid = pid;
    }
}

enum TestEnum {
    TEST_ENUM1(1), TEST_ENUM2(2);


    int key;

    TestEnum(int i) {
        this.key = i;
    }
}
